/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.jbeans.controller;

import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.Msg;
import com.khoders.pms.entities.SaleItem;
import com.khoders.pms.entities.Sales;
import com.khoders.pms.entities.StockReceiptItem;
import com.khoders.pms.jbeans.ReportFiles;
import com.khoders.pms.jbeans.dto.SalesReceipt;
import com.khoders.pms.listener.AppSession;
import com.khoders.pms.services.InventoryService;
import com.khoders.pms.services.SalesService;
import com.khoders.pms.services.XtractService;
import com.khoders.resource.reports.ReportManager;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author richa
 */
@Named(value = "salesApprovalController")
@SessionScoped
public class SalesApprovalController implements Serializable
{
    @Inject private AppSession appSession;
    @Inject private CrudApi crudApi;
    @Inject private SalesService salesService;
    @Inject private XtractService xtractService;
    @Inject private InventoryService inventoryService;
    @Inject private ReportManager reportManager;
    
    private List<Sales> unApprovedSalesList = new LinkedList<>();
    private StockReceiptItem stockReceiptItem = new StockReceiptItem();
    private String receiptNumber=null;
    private Sales sales= new Sales();
    
    public void init(){
        unApprovedSalesList = salesService.getUnapprovaedSales();
    }
    
    public void selectedSale(Sales sales){
        this.sales=sales;
    }
    
    public void approveSale()
    {
        List<SaleItem> saleItemList = inventoryService.getSales(sales);
        if(saleItemList.isEmpty()){
            Msg.error("Cannot approve empty transaction");
            return;
        }
        if(!sales.isApproval())
        {
        for (SaleItem salesCart : saleItemList)
        {
            double qtyPurchased = salesCart.getQuantity();
            double qtyInStock = salesCart.getStockReceiptItem().getPkgQuantity();

            double qtyAtHand = qtyInStock - qtyPurchased;

            try
            {
                stockReceiptItem = crudApi.getEm().find(StockReceiptItem.class, salesCart.getStockReceiptItem().getId());
                stockReceiptItem.setPkgQuantity(qtyAtHand);
                stockReceiptItem.setQtySold(stockReceiptItem.getQtySold() + qtyPurchased);
                stockReceiptItem.setQtyLeft(qtyAtHand);
                stockReceiptItem.setLastModifiedBy(appSession.getCurrentUser() != null ? appSession.getCurrentUser().getFullname() : "");
                stockReceiptItem.setLastModifiedDate(LocalDateTime.now());
                crudApi.save(stockReceiptItem);

                salesCart.genCode();
                salesCart.setSales(sales);
                crudApi.save(salesCart);

            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        sales.setApproval(true);
        if(crudApi.save(sales) != null){
            Msg.info("Sales Approved");
        }
        generatePOSReceipt(saleItemList,sales);
        }
        else{
            System.out.println("Approved___");
            generatePOSReceipt(saleItemList,sales);   
        }
    }

        
    public void filterByReceiptNumber()
    {
      if(receiptNumber==null){
          Msg.error("Please enter receipt number");
          return;
      }
      unApprovedSalesList = inventoryService.getSalesByReceipt(receiptNumber.trim()); 
    }
    
    public void generatePOSReceipt(List<SaleItem> saleItemList, Sales sales)
    {
        try
        {
            List<SalesReceipt> salesReceiptList = new LinkedList<>();
            
            if(saleItemList.isEmpty())
            {
                Msg.error("Cannot process an empty receipt!");
                return;
            }
            SalesReceipt extractedItem = xtractService.extractToPosReceipt(saleItemList, sales);

            salesReceiptList.add(extractedItem);
            ReportManager.reportParams.put("logo", ReportFiles.LOGO);
            reportManager.createReport(salesReceiptList, ReportFiles.RECEIPT_FILE, ReportManager.reportParams);

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
       
    public List<Sales> getUnApprovedSalesList()
    {
        return unApprovedSalesList;
    }

    public String getReceiptNumber()
    {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber)
    {
        this.receiptNumber = receiptNumber;
    }

}

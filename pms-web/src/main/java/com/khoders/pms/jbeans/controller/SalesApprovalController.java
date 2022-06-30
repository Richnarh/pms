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
import com.khoders.pms.services.InventoryService;
import com.khoders.pms.services.SalesService;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.web.context.annotation.SessionScope;

/**
 *
 * @author richa
 */
@Named(value = "salesApprovalController")
@SessionScope
public class SalesApprovalController implements Serializable
{
    @Inject private CrudApi crudApi;
    @Inject private SalesService salesService;
    @Inject private InventoryService inventoryService;
    
    private List<Sales> unApprovedSalesList = new LinkedList<>();
    private StockReceiptItem stockReceiptItem = new StockReceiptItem();
    private int salesQty = 0;
    
    @PostConstruct
    public void init(){
        unApprovedSalesList = salesService.getUnapprovaedSales();
    }
    
    public void approveSale(Sales sales)
    {
        List<SaleItem> saleItemList = inventoryService.getSales(sales);
        if(saleItemList.isEmpty()){
            Msg.info("Cannot approve empty transaction");
            return;
        }
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
    }

    public int getSalesQty()
    {
        return salesQty;
    }

    public List<Sales> getUnApprovedSalesList()
    {
        return unApprovedSalesList;
    }
    
}

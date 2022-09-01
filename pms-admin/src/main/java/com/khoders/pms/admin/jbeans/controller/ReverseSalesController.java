/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.admin.jbeans.controller;

import com.khoders.pms.admin.services.StockService;
import com.khoders.pms.entities.Sales;
import com.khoders.pms.enums.ApprovalOption;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.Msg;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author richa
 */
@Named(value = "reverseSalesController")
@SessionScoped
public class ReverseSalesController implements Serializable
{
    @Inject private CrudApi crudApi;
    @Inject private StockService stockService;
    List<Sales> salesList = new LinkedList<>();
    private String receiptNumber;
    private ApprovalOption approvalOption=null;
    private Sales sales = null;
    
    public void loadSales(){
        salesList = stockService.getSalesList(ApprovalOption.ALL);
    }
    
    public void fetchSales(){
        salesList = stockService.getSalesList(approvalOption);
    }

    
    public void filterByReceiptNumber()
    {
      if(receiptNumber==null){
          Msg.error("Please enter receipt number");
          return;
      }
      salesList = stockService.getSalesByReceipt(receiptNumber.trim()); 
    }
    
    public void selectedSale(Sales sales){
        this.sales=sales;
    }
    
    public void reverseSale(){
        System.out.println("Reversing......100%");
        Msg.warn("This function is yet to be completed, contact the developer!");
    }
        
    public List<Sales> getSalesList()
    {
        return salesList;
    }

    public ApprovalOption getApprovalOption()
    {
        return approvalOption;
    }

    public void setApprovalOption(ApprovalOption approvalOption)
    {
        this.approvalOption = approvalOption;
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

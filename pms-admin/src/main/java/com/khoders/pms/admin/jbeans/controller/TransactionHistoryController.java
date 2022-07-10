/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.admin.jbeans.controller;

import com.khoders.pms.admin.services.StockService;
import com.khoders.pms.entities.SaleItem;
import com.khoders.pms.entities.Sales;
import com.khoders.pms.entities.system.CompanyBranch;
import com.khoders.pms.entities.system.UserAccount;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.DateRangeUtil;
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
@Named(value = "transactionHistoryController")
@SessionScoped
public class TransactionHistoryController implements Serializable
{
    @Inject private CrudApi crudApi;
    @Inject private StockService stockService;
    
    private Sales selectedSale = new Sales();
    private UserAccount selectedUserAccount = new UserAccount();
    private List<Sales> salesList = new LinkedList<>();
    private List<SaleItem> saleItemList = new LinkedList<>();
    
    private DateRangeUtil dateRange = new DateRangeUtil();
    private CompanyBranch selectedBranch = new CompanyBranch();
    private String phoneNumber,clientName,receiptNumber;
    
    private LocalDateTime valueDate;
        
    public void fetchByParams()
    {
      salesList = stockService.getInvoiceList(selectedBranch, selectedUserAccount);
    }
    
    public void selectSale(Sales sales)
    {
        if(sales.getCustomer() != null){
            clientName = sales.getCustomer().getCustomerName();
            phoneNumber = sales.getCustomer().getPhone();
        }
      valueDate = sales.getPurchaseDate();
      
      saleItemList = stockService.getSalesDetailList(sales);
    }
    
    public void filterByReceiptNumber()
    {
      salesList = stockService.getSalesByReceipt(receiptNumber);  
    }
    
    public void fetchByDates()
    {
       salesList = stockService.getInvoiceByDates(dateRange);
    }
    
    public void fetchByBranch()
    {
        salesList = stockService.getSalesByBranch(selectedBranch);
    }
    
    public void fetchByEmployee()
    {
        salesList = stockService.getSalesByEmployee(selectedUserAccount);
    }
    
    public void resetPage()
    {
      selectedBranch = new CompanyBranch();
      selectedUserAccount = new UserAccount();
      
      clientName = null;
      phoneNumber = null;
      valueDate = null;
      
      salesList = new LinkedList<>();
      saleItemList = new LinkedList<>();
    }

    public Sales getSelectedSale()
    {
        return selectedSale;
    }

    public void setSelectedSale(Sales selectedSale)
    {
        this.selectedSale = selectedSale;
    }

    public List<Sales> getSalesList()
    {
        return salesList;
    }
    
    public DateRangeUtil getDateRange()
    {
        return dateRange;
    }

    public void setDateRange(DateRangeUtil dateRange)
    {
        this.dateRange = dateRange;
    }

    public CompanyBranch getSelectedBranch()
    {
        return selectedBranch;
    }

    public void setSelectedBranch(CompanyBranch selectedBranch)
    {
        this.selectedBranch = selectedBranch;
    }

    public UserAccount getSelectedUserAccount()
    {
        return selectedUserAccount;
    }

    public void setSelectedUserAccount(UserAccount selectedUserAccount)
    {
        this.selectedUserAccount = selectedUserAccount;
    } 

    public List<SaleItem> getSaleItemList()
    {
        return saleItemList;
    }
    
    public String getClientName()
    {
        return clientName;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public LocalDateTime getValueDate()
    {
        return valueDate;
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
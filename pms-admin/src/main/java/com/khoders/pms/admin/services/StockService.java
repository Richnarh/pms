/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.admin.services;

import com.khoders.pms.entities.SaleItem;
import com.khoders.pms.entities.Sales;
import com.khoders.pms.entities.StockReceiptItem;
import com.khoders.pms.entities.system.CompanyBranch;
import com.khoders.pms.entities.system.UserAccount;
import com.khoders.pms.enums.ApprovalOption;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.DateRangeUtil;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

/**
 *
 * @author richa
 */
@Stateless
public class StockService
{
 @Inject private CrudApi crudApi;
    
    public List<Sales> getInvoiceList(CompanyBranch companyBranch, UserAccount userAccount)
    {
        try
        {
            if(companyBranch != null || userAccount != null)
            {
                String qryString = "SELECT e FROM Sales e WHERE e.companyBranch=?1 AND e.userAccount=?2";
                TypedQuery<Sales> typedQuery = crudApi.getEm().createQuery(qryString, Sales.class)
                    .setParameter(1, companyBranch)
                    .setParameter(2, userAccount);
                return typedQuery.getResultList();  
            }
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return Collections.emptyList();
    }
    
     
    public List<Sales> getInvoiceByDates(DateRangeUtil dateRange)
    {
        try {
            if(dateRange.getFromDate() == null || dateRange.getToDate() == null)
            {
                String  queryString = "SELECT e FROM Sales e ";
                return crudApi.getEm().createQuery(queryString, Sales.class).getResultList();
            }
            
            String qryString = "SELECT e FROM Sales e WHERE e.valueDate BETWEEN ?1 AND ?2";
            
            TypedQuery<Sales> typedQuery = crudApi.getEm().createQuery(qryString, Sales.class)
                    .setParameter(1, dateRange.getFromDate())
                    .setParameter(2, dateRange.getToDate());
           return typedQuery.getResultList();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
     
    public List<Sales> getSalesByBranch(CompanyBranch companyBranch)
    {
        try {
           
            String qryString = "SELECT e FROM Sales e WHERE e.companyBranch=?1";
            
            TypedQuery<Sales> typedQuery = crudApi.getEm().createQuery(qryString, Sales.class)
                    .setParameter(1, companyBranch);
           return typedQuery.getResultList();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
  
     
    public List<Sales> getSalesByReceipt(String receiptNumber)
    {
        try {
           
            String qryString = "SELECT e FROM Sales e WHERE e.receiptNumber=?1";
            
            TypedQuery<Sales> typedQuery = crudApi.getEm().createQuery(qryString, Sales.class)
                    .setParameter(1, receiptNumber);
           return typedQuery.getResultList();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
  
    public List<Sales> getSalesByEmployee(UserAccount userAccount)
    {
        try 
        {
         
        String qryString = "SELECT e FROM Sales e WHERE e.userAccount=?1";
            
        TypedQuery<Sales> typedQuery = crudApi.getEm().createQuery(qryString, Sales.class)
                    .setParameter(1, userAccount);
        return typedQuery.getResultList();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    
    public List<SaleItem> getSalesDetailList(Sales sales)
    {
        try
        {
           String qryString = "SELECT e FROM SaleItem e WHERE e.sales=?1";
           return crudApi.getEm().createQuery(qryString, SaleItem.class)
                   .setParameter(1, sales)
                   .getResultList();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return Collections.emptyList();
    }
    
    public List<StockReceiptItem> getStockList(CompanyBranch companyBranch)
    {
        try
        {
          String qryString = "SELECT e FROM StockReceiptItem e WHERE e.companyBranch=?1 ORDER BY e.product ASC";
          return crudApi.getEm().createQuery(qryString, StockReceiptItem.class)
                  .setParameter(1, companyBranch)
                  .getResultList();
           
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return Collections.emptyList();
    }
    
    public List<StockReceiptItem> getStockShortageList(CompanyBranch companyBranch)
    {
        try
        {
          String qryString = "SELECT e FROM StockReceiptItem e WHERE e.companyBranch=?1 AND e.pkgQuantity <= 1 ORDER BY e.product ASC";
          return crudApi.getEm().createQuery(qryString, StockReceiptItem.class)
                  .setParameter(1, companyBranch)
                  .getResultList();
           
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return Collections.emptyList();
    }
    
      
    public List<Sales> getSales(DateRangeUtil dateRange)
    {
        try {
            if(dateRange.getFromDate() == null || dateRange.getToDate() == null)
            {
                  String  queryString = "SELECT e FROM Sales e ORDER BY e.valueDate DESC";
                  TypedQuery<Sales> typedQuery = crudApi.getEm().createQuery(queryString, Sales.class);
                                    return typedQuery.getResultList();
            }
            
            String qryString = "SELECT e FROM Sales e WHERE e.valueDate BETWEEN ?1 AND ?2 ORDER BY e.valueDate DESC";
            
            TypedQuery<Sales> typedQuery = crudApi.getEm().createQuery(qryString, Sales.class)
                    .setParameter(1, dateRange.getFromDate())
                    .setParameter(2, dateRange.getToDate());
           return typedQuery.getResultList();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    
   public List<Sales> getSalesList(ApprovalOption approvalOption)
    {
        if(approvalOption == ApprovalOption.ALL){
            return crudApi.getEm().createQuery("SELECT e FROM Sales e ORDER BY e.valueDate DESC", Sales.class)
                    .getResultList();
        }
        
        try
        {
            return crudApi.getEm().createQuery("SELECT e FROM Sales e WHERE e.approval=:approval ORDER BY e.valueDate DESC", Sales.class)
                    .setParameter("approval", (approvalOption == ApprovalOption.APPROVED))
                    .getResultList();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return Collections.emptyList();
    }
    public List<Sales> getTotalSumPerDateRange(DateRangeUtil dateRange)
    {
        try
        {
           return crudApi.getEm().createQuery("SELECT e FROM Sales e WHERE e.valueDate BETWEEN ?1 AND ?2 ", Sales.class)
                   .setParameter(1, dateRange.getFromDate())
                   .setParameter(2, dateRange.getToDate())
                   .getResultList();
           
        } catch (Exception e)
        {
           e.printStackTrace();
        }
        
        return Collections.emptyList();
    }
}

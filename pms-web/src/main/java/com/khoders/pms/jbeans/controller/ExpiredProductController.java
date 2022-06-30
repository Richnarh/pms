/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.jbeans.controller;

import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.CollectionList;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.SystemUtils;
import com.khoders.pms.entities.ExpiredProduct;
import com.khoders.pms.entities.Product;
import com.khoders.pms.entities.StockReceiptItem;
import com.khoders.pms.listener.AppSession;
import com.khoders.pms.services.StockService;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author richa
 */
@Named(value = "expiredProductController")
@SessionScoped
public class ExpiredProductController implements Serializable
{
    @Inject private CrudApi crudApi;
    @Inject private AppSession appSession;
    @Inject private StockService stockService;
    
    private StockReceiptItem selectedProduct = new StockReceiptItem();
    private ExpiredProduct expiredProduct = new ExpiredProduct();
    private List<ExpiredProduct> expiredProductList = new LinkedList<>();
    private List<StockReceiptItem> stockProductList = new LinkedList<>();
    
    String optionText = "Save Changes";
    
    @PostConstruct
    private void init(){
        stockProductList = stockService.getStockReceiptItems();
    }
    
    public void selectProduct(StockReceiptItem stockReceiptItem)
    {
       selectedProduct = stockReceiptItem;
       expiredProductList =  stockService.getExpiredProducts(stockReceiptItem.getProduct());
    }
    
    public void searchExpiredProduct()
    {
        LocalDate expiredDate = null;
        Product product = null;
        if(expiredProduct != null && selectedProduct != null)
        {
            expiredDate = expiredProduct.getExpiryDate();
            product = selectedProduct.getProduct();
        }
        expiredProductList = stockService.getExpiredProducts(product, expiredDate);
    }
    
    public void saveExpiredProduct(){
        try
        {
            expiredProduct.setStockReceiptItem(selectedProduct);
            if(crudApi.save(expiredProduct) != null)
            {
                StockReceiptItem item = crudApi.find(StockReceiptItem.class, selectedProduct.getId());
                item.setExpiredDateSet(true);
                crudApi.save(item);
                
                expiredProductList = CollectionList.washList(expiredProductList, expiredProduct);
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
            }
            clearForm();
        } catch (Exception e)
        {
           e.printStackTrace();
        }
    }
    
   public void deleteExpiredProduct(ExpiredProduct expiredProduct){
       try
       {
         if(crudApi.delete(expiredProduct))
         {
             expiredProductList.remove(expiredProduct);
             
             FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
         }  
       } catch (Exception e)
       {
         e.printStackTrace();
       }
   }
   
   public void editExpiredProduct(ExpiredProduct expiredProduct){
       this.expiredProduct = expiredProduct;
       selectedProduct = expiredProduct.getStockReceiptItem();
       optionText = "Update";
   }
   
   public void clearForm(){
       selectedProduct = null;
       expiredProduct = new ExpiredProduct();
       optionText = "Save Changes";
       SystemUtils.resetJsfUI();
   }
   
    public List<ExpiredProduct> getExpiredProductList()
    {
        return expiredProductList;
    }

    public StockReceiptItem getSelectedProduct()
    {
        return selectedProduct;
    }

    public void setSelectedProduct(StockReceiptItem selectedProduct)
    {
        this.selectedProduct = selectedProduct;
    }

    public ExpiredProduct getExpiredProduct()
    {
        return expiredProduct;
    }

    public void setExpiredProduct(ExpiredProduct expiredProduct)
    {
        this.expiredProduct = expiredProduct;
    }

    public String getOptionText()
    {
        return optionText;
    }

    public List<StockReceiptItem> getStockProductList()
    {
        return stockProductList;
    }
     
}


package com.khoders.pms.jbeans.controller;

import com.khoders.pms.services.InventoryService;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.SystemUtils;
import com.khoders.pms.entities.PurchaseOrder;
import com.khoders.pms.entities.PurchaseOrderItem;
import com.khoders.pms.entities.StockReceipt;
import com.khoders.pms.entities.StockReceiptItem;
import com.khoders.pms.jbeans.dto.StockReceiptDto;
import com.khoders.pms.listener.AppSession;
import com.khoders.pms.services.StockService;
import com.khoders.pms.services.XtractService;
import com.khoders.resource.utilities.ParseValue;
import com.khoders.resource.utilities.Stringz;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author richa
 */
@Named(value = "stockReceiptController")
@SessionScoped
public class StockReceiptController implements Serializable
{
   @Inject private CrudApi crudApi;
   @Inject private InventoryService inventoryService;
   @Inject private AppSession appSession;
   @Inject private StockService stockService;
   @Inject private XtractService xtractService;
   
   private StockReceipt stockReceipt = new StockReceipt();
   private PurchaseOrder selectedPurchaseOrder = null;
   private List<StockReceipt> stockReceiptList = new LinkedList<>();
   private List<PurchaseOrderItem> purchaseOrderItemList = new LinkedList<>();
   private String optionText;
   private LocalDate expiryDate;
   private boolean savedStock = false;
   
   @PostConstruct
   private void init()
   {
     clearStockReceipt();
     stockReceiptList = inventoryService.getStockReceiptList();
   }
    
    public void receivePurchaseOrder(PurchaseOrder purchaseOrder)
    {
      selectedPurchaseOrder = purchaseOrder;
      clearStockReceipt();
      stockReceipt = stockService.getStockReceipt(purchaseOrder);
      if(stockReceipt == null){
        stockReceipt = new StockReceipt();
        stockReceipt.setPurchaseOrder(purchaseOrder);
        stockReceipt.setTotalAmount(purchaseOrder.getTotalAmount());
        stockReceipt.setBatchNo(SystemUtils.generateCode());
        stockReceipt.setUserAccount(appSession.getCurrentUser());
        stockReceipt.setCompanyBranch(appSession.getCompanyBranch());
        stockReceipt.setStockSaved(true);
        savedStock = true;
      }else{
        savedStock = false;
      }
      
      purchaseOrderItemList = inventoryService.getPurchaseOrderItem(purchaseOrder);
      
    }
    
//    public void viewStockReceipt(PurchaseOrder purchaseOrder)
//    {
//        viewStockList = new LinkedList<>();
//        stockReceipt = stockService.getStockReceipt(purchaseOrder);
//        List<Object[]> objects = stockService.getStockReceiptItems(stockReceipt);
//        for (Object[] object : objects)
//        {
//          StockReceiptDto dto = new StockReceiptDto();
//          dto.setId(Stringz.objectToString(object[0]));
//          dto.setRefNo(Stringz.objectToString(object[1]));
//          dto.setProductName(Stringz.objectToString(object[2]));
//          dto.setPkgQuantity(ParseValue.parseDoubleValue(object[3]));
//          dto.setProductPackage(Stringz.objectToString(object[4]));
//          dto.setPackageFactor(ParseValue.parseDoubleValue(object[5]));
//          dto.setCostPrice(ParseValue.parseDoubleValue(object[6]));
//          
//          viewStockList.add(dto);
//        }
//        stockReceipt.setTotalAmount(purchaseOrder.getTotalAmount());
//    }  
    
   public void saveStockReceipt()
   {
       try
       {
           stockReceipt.setBatchNo(stockReceipt.getBatchNo());
           
           if (crudApi.save(stockReceipt) != null)
           {
               StockReceiptItem receiptItem = xtractService.saveStockReceipt(purchaseOrderItemList, stockReceipt);
              
               if (receiptItem != null)
               {
                   PurchaseOrder purchaseOrder = crudApi.find(PurchaseOrder.class, stockReceipt.getPurchaseOrder().getId());
                   if(purchaseOrder != null)
                   {
                       purchaseOrder.setStockFullyReceived(true);
                       
                       crudApi.save(purchaseOrder);
                   }
                   Msg.info(Msg.SUCCESS_MESSAGE);
               }
           }
       } catch (Exception e){
           e.printStackTrace();
       }
   }
   
   public void deleteStockReceipt(StockReceipt stockReceipt){
       try
       {
         if(crudApi.delete(stockReceipt))
         {
          stockReceiptList.remove(stockReceipt);
          Msg.info(Msg.SUCCESS_MESSAGE);
         }  
       } catch (Exception e)
       {
         e.printStackTrace();
       }
   }
   
   public void editStockReceipt(StockReceipt stockReceipt){
       this.stockReceipt = stockReceipt;
   }

    public void clearStockReceipt()
    {
        stockReceipt = new StockReceipt();
        stockReceipt.setUserAccount(appSession.getCurrentUser());
        stockReceipt.setCompanyBranch(appSession.getCompanyBranch());
        purchaseOrderItemList = new LinkedList<>();
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }

    public StockReceipt getStockReceipt()
    {
      return stockReceipt;
    }

    public void setStockReceipt(StockReceipt stockReceipt)
    {
        this.stockReceipt = stockReceipt;
    }

    public List<StockReceipt> getStockReceiptList()
    {
        return stockReceiptList;
    }

    public String getOptionText()
    {
        return optionText;
    }

    public List<PurchaseOrderItem> getPurchaseOrderItemList() {
        return purchaseOrderItemList;
    }
    
    public LocalDate getExpiryDate()
    {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate)
    {
        this.expiryDate = expiryDate;
    }

    public PurchaseOrder getSelectedPurchaseOrder()
    {
        return selectedPurchaseOrder;
    }

    public void setSelectedPurchaseOrder(PurchaseOrder selectedPurchaseOrder)
    {
        this.selectedPurchaseOrder = selectedPurchaseOrder;
    }

    public boolean isSavedStock()
    {
        return savedStock;
    }

    public void setSavedStock(boolean savedStock)
    {
        this.savedStock = savedStock;
    }
    
}

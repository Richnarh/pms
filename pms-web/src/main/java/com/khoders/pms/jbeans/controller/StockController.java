/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.jbeans.controller;

import com.khoders.resource.jpa.CrudApi;
import com.khoders.pms.entities.StockReceiptItem;
import com.khoders.pms.jbeans.dto.StockReceiptDto;
import com.khoders.pms.services.InventoryService;
import com.khoders.pms.services.StockService;
import com.khoders.resource.utilities.ParseValue;
import com.khoders.resource.utilities.Stringz;
import java.io.Serializable;
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
@Named(value = "stockController")
@SessionScoped
public class StockController implements Serializable
{
    @Inject private CrudApi crudApi;
    @Inject private StockService stockService;
    @Inject private InventoryService inventoryService;
    
    private List<StockReceiptItem> stockList = new LinkedList<>();
    private List<StockReceiptDto> viewStockList = new LinkedList<>();

    @PostConstruct
    public void init()
    {
        viewStockReceipt();
    }
    
    public void viewStockReceipt()
    {
        viewStockList = new LinkedList<>();
        List<Object[]> objects = stockService.getStockReceiptItems();
        for (Object[] object : objects)
        {
          StockReceiptDto dto = new StockReceiptDto();
          dto.setId(Stringz.objectToString(object[0]));
          dto.setRefNo(Stringz.objectToString(object[1]));
          dto.setProductName(Stringz.objectToString(object[2]));
          dto.setPkgQuantity(ParseValue.parseDoubleValue(object[3]));
          dto.setProductPackage(Stringz.objectToString(object[4]));
          dto.setPackageFactor(ParseValue.parseDoubleValue(object[5]));
          dto.setCostPrice(ParseValue.parseDoubleValue(object[6]));
          dto.setPackagePrice(ParseValue.parseDoubleValue(object[7]));
          dto.setReorderLevel(ParseValue.parseIntegerValue(object[8]));
          
          viewStockList.add(dto);
        }
    }  
      
    public void initStockList()
    {
        for (StockReceiptItem stockReceiptItem : stockList)
        {
            double qty = stockReceiptItem.getPkgQuantity();
            List<Object[]> pp = inventoryService.stockBreakDown();
            
            for (Object[] receiptItem : pp)
            {
//                Product product = (Product)receiptItem[0];
                System.out.println("packageFactor -> "+receiptItem[0]);
                System.out.println("PackageQty -> "+receiptItem[1]);
            }
//            int mainPkg = (int)(qty/pp.getPackageFactor());
//            
//            double subPkg = mainPkg * pp.getPackageFactor();
//            
//            double unitPkg = qty - subPkg;
             
        }
        
    }

    public List<StockReceiptItem> getStockList()
    {
        return stockList;
    }

    public List<StockReceiptDto> getViewStockList()
    {
        return viewStockList;
    }
    
}

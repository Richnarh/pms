/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.jbeans.controller;

import com.khoders.resource.jpa.CrudApi;
import com.khoders.pms.entities.StockReceiptItem;
import com.khoders.pms.services.InventoryService;
import com.khoders.pms.services.StockService;
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
    
    private List<StockReceiptItem> stockReceiptItemList = new LinkedList<>();

    @PostConstruct
    private void init()
    {
        stockReceiptItemList = stockService.getStockReceiptItems();
    }
    
    public void initStockList()
    {
        for (StockReceiptItem stockReceiptItem : stockReceiptItemList)
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
    
    public List<StockReceiptItem> getStockReceiptItemList()
    {
        return stockReceiptItemList;
    }
    
}

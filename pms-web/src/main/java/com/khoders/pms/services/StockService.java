/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.services;

import com.khoders.resource.jpa.CrudApi;
import com.khoders.pms.entities.ExpiredProduct;
import com.khoders.pms.entities.Product;
import com.khoders.pms.entities.PurchaseOrder;
import com.khoders.pms.entities.StockReceipt;
import com.khoders.pms.entities.StockReceiptItem;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author richa
 */
@Stateless
public class StockService
{
    @Inject private CrudApi crudApi;
    @Inject private InventoryService inventoryService;
    
    public List<ExpiredProduct> getExpiredProducts(Product product)
    {
        try
        {
            return crudApi.getEm().createQuery("SELECT e FROM ExpiredProduct e WHERE e.stockReceiptItem.product=?1", ExpiredProduct.class)
                    .setParameter(1, product)
                    .getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }
    
    public ExpiredProduct getExpiredProduct(Product product)
    {
        try
        {
            return crudApi.getEm().createQuery("SELECT e FROM ExpiredProduct e WHERE e.stockReceiptItem.product=?1", ExpiredProduct.class)
                    .setParameter(1, product)
                    .getResultStream().findFirst().orElse(null);
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<ExpiredProduct> getExpiredProducts(Product product, LocalDate expiredDate)
    {
        try
        {
            if(product == null && expiredDate == null)
            {
                return crudApi.getEm().createQuery("SELECT e FROM ExpiredProduct e", ExpiredProduct.class).getResultList();
            }
            
            return crudApi.getEm().createQuery("SELECT e FROM ExpiredProduct e WHERE e.stockReceiptItem.product=?1 OR e.expiryDate=?2", ExpiredProduct.class)
                    .setParameter(1, product)
                    .setParameter(2, expiredDate)
                    .getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }
    
    public List<StockReceiptItem> getStockReceiptItems(StockReceipt stockReceipt)
    {
        try
        {
            return crudApi.getEm().createQuery("SELECT e, SUM(e.pkgQuantity) FROM StockReceiptItem e WHERE e.stockReceipt=?1 GROUP BY e.product ORDER BY e.createdDate DESC", StockReceiptItem.class)
                    .setParameter(1, stockReceipt)
                    .getResultList();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    
    /*
    public List<Object[]> getStockReceiptItems(StockReceipt stockReceipt)
    {
        make the query from necessary tables and map into a dto and present it in UI
            Query query = crudApi.getEm().
                    createQuery("SELECT e.refNo, p, e.productPackage,  SUM(e.pkgQuantity) FROM StockReceiptItem e JOIN Product p on e.product=p WHERE e.stockReceipt=:stockReceipt GROUP BY p ORDER BY e.createdDate DESC", StockReceiptItem.class);
                    
           return query.setParameter("stockReceipt", stockReceipt).getResultList();

    }
    */
    
    public List<StockReceiptItem> getStockReceiptItems()
    {
        try
        {
            return crudApi.getEm().createQuery("SELECT e FROM StockReceiptItem e GROUP BY e.product ORDER BY e.product DESC", StockReceiptItem.class)
                    .getResultList();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    
    public StockReceipt getStockReceipt(PurchaseOrder purchaseOrder)
    {
        try
        {
            return crudApi.getEm().createQuery("SELECT e FROM StockReceipt e WHERE e.purchaseOrder=?1", StockReceipt.class)
                    .setParameter(1, purchaseOrder)
                    .getResultStream().findAny().orElse(null);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public StockReceipt getStockReceipt(String orderCode)
    {
        try
        {
            return crudApi.getEm().createQuery("SELECT e FROM StockReceipt e WHERE e.receiptNo=?1", StockReceipt.class)
                    .setParameter(1, orderCode)
                    .getResultStream().findAny().orElse(null);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}

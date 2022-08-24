/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.services;

import com.khoders.resource.jpa.CrudApi;
import com.khoders.pms.entities.ExpiredProduct;
import com.khoders.pms.entities.Packaging;
import com.khoders.pms.entities.Product;
import com.khoders.pms.entities.ProductPackage;
import com.khoders.pms.entities.ProductType;
import com.khoders.pms.entities.PurchaseOrder;
import com.khoders.pms.entities.StockReceipt;
import com.khoders.pms.entities.UnitMeasurement;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

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

    public List<Object[]> getStockReceiptItems()
    {
        String queryString = "SELECT e.id, e.`ref_no`, p.`product_name`, SUM(e.pkg_quantity) AS Quantity, u.`units`, pkg.`package_factor`, e.`cost_price`, pkg.`package_price`,p.`reorder_level` FROM `stock_recept_item` e \n" +
"		INNER JOIN product p ON p.id=e.product \n" +
"		INNER JOIN product_package pkg ON pkg.id=e.product_package \n" +
"		INNER JOIN `unit_measurement` u ON u.id=pkg.`units_measurement` \n" +
"		GROUP BY p.product_name, pkg.`units_measurement` ORDER BY p.product_name DESC";
        
        Query query = crudApi.getEm().createNativeQuery(queryString);
        return query.getResultList();
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
    public ProductPackage existProdctPackage(Product product, UnitMeasurement unitMeasurement)
    {
      return crudApi.getEm().createQuery("SELECT e FROM ProductPackage e WHERE e.product=:product AND e.unitMeasurement=:unitMeasurement", ProductPackage.class)
              .setParameter("product", product)
              .setParameter("unitMeasurement", unitMeasurement)
              .getResultStream().findFirst().orElse(null);
    }
    public ProductType getProductType(String prdtType){
       return crudApi.getEm().createQuery("SELECT e FROM ProductType e WHERE e.productTypeName=?1", ProductType.class)
                                            .setParameter(1, prdtType)
                                            .getResultStream().findFirst().orElse(null);
    }
    public UnitMeasurement getUnits(String units)
    {
       return crudApi.getEm().createQuery("SELECT e FROM UnitMeasurement e WHERE e.units=?1", UnitMeasurement.class)
                                            .setParameter(1, units)
                                            .getResultStream().findFirst().orElse(null);
    }
    public Packaging getPackage(String packageString)
    {
       return crudApi.getEm().createQuery("SELECT e FROM Packaging e WHERE e.packagingName=?1", Packaging.class)
                                            .setParameter(1, packageString)
                                            .getResultStream().findFirst().orElse(null);
    }
    public Product getProduct(String productName)
    {
       return crudApi.getEm().createQuery("SELECT e FROM Product e WHERE e.productName=?1", Product.class)
                                            .setParameter(1, productName)
                                            .getResultStream().findFirst().orElse(null);
    }

    public List<ProductPackage> segmentedProducts(Product product)
    {
       return crudApi.getEm().createQuery("SELECT e FROM ProductPackage e WHERE e.product=:product", ProductPackage.class)
               .setParameter("product", product)
               .getResultList();
    }
}

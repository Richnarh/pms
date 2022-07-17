package com.khoders.pms.services;

import com.khoders.pms.entities.Category;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.DateRangeUtil;
import com.khoders.pms.entities.SaleItem;
import com.khoders.pms.entities.Customer;
import com.khoders.pms.entities.Location;
import com.khoders.pms.entities.Manufacturer;
import com.khoders.pms.entities.Packaging;
import com.khoders.pms.entities.Potency;
import com.khoders.pms.entities.Product;
import com.khoders.pms.entities.ProductPackage;
import com.khoders.pms.entities.ProductType;
import com.khoders.pms.entities.PurchaseOrder;
import com.khoders.pms.entities.PurchaseOrderItem;
import com.khoders.pms.entities.Sales;
import com.khoders.pms.entities.StockReceipt;
import com.khoders.pms.entities.Warehouse;
import com.khoders.pms.entities.StockReceiptItem;
import com.khoders.pms.entities.UnitMeasurement;
import com.khoders.pms.entities.system.UserAccount;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author richard
 */
@Stateless
public class InventoryService
{
    private @Inject CrudApi crudApi;
    
    public List<Product> getProductList()
    {
        try
        {
            return crudApi.getEm().createQuery("SELECT e FROM Product e ORDER BY e.createdDate DESC", Product.class).getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
    public List<Potency> getPotencyList()
    {
        try
        {
            return crudApi.getEm().createQuery("SELECT e FROM Potency e ORDER BY e.createdDate DESC", Potency.class).getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
    
    public List<Packaging> getPackagingList()
    {
        try
        {
            return crudApi.getEm().createQuery("SELECT e FROM Packaging e ORDER BY e.createdDate DESC", Packaging.class).getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
    public List<Location> getLocationList()
    {
        try
        {
            return crudApi.getEm().createQuery("SELECT e FROM Location e ORDER BY e.createdDate DESC", Location.class).getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    public List<Manufacturer> getManufacturerList()
    {
        try
        {
            return crudApi.getEm().createQuery("SELECT e FROM Manufacturer e ORDER BY e.createdDate DESC", Manufacturer.class).getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    public List<ProductType> getProductTypeList()
    {
        try
        {
            return crudApi.getEm().createQuery("SELECT e FROM ProductType e ORDER BY e.createdDate DESC", ProductType.class).getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
    public List<Category> getCategoryList()
    {
        try
        {
            return crudApi.getEm().createQuery("SELECT e FROM Category e ORDER BY e.createdDate DESC", Category.class).getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
    public List<UnitMeasurement> getUnitMeasurementList()
    {
        try
        {
            return crudApi.getEm().createQuery("SELECT e FROM UnitMeasurement e ORDER BY e.createdDate DESC", UnitMeasurement.class).getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    
    public List<ProductPackage> getProductPackageList()
    {
        try
        {
            return crudApi.getEm().createQuery("SELECT e FROM ProductPackage e ORDER BY e.createdDate DESC", ProductPackage.class).getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    
    public List<Warehouse> getWarehouseList()
    {
        try
        {
            return crudApi.getEm().createQuery("SELECT e FROM Warehouse e ORDER BY e.createdDate DESC", Warehouse.class).getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    
    public Customer customertExist(String phone)
    {
        try
        {
            String qryString = "SELECT e FROM Customer e WHERE e.phone=?1";
            TypedQuery<Customer> typedQuery = crudApi.getEm().createQuery(qryString, Customer.class)
                    .setParameter(1, phone);
            
                    return typedQuery.getResultStream().findFirst().orElse(null);
                    
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
    
    public List<Customer> getCustomerList()
    {
        try
        {
            String qryString = "SELECT e FROM Customer e ORDER BY e.customerName ASC";
            TypedQuery<Customer> typedQuery = crudApi.getEm().createQuery(qryString, Customer.class);
                            return typedQuery.getResultList();
            
        } catch (Exception e)
        { 
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
    public List<UserAccount> getUserAccountList()
    {
        try
        {
            String qryString = "SELECT e FROM UserAccount e ORDER BY e.username ASC";
            TypedQuery<UserAccount> typedQuery = crudApi.getEm().createQuery(qryString, UserAccount.class);
                            return typedQuery.getResultList();
            
        } catch (Exception e)
        { 
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
    
    public List<PurchaseOrder> getPurchaseOrderList()
    {
        try
        {
            String qryString = "SELECT e FROM PurchaseOrder e";
            TypedQuery<PurchaseOrder> typedQuery = crudApi.getEm().createQuery(qryString, PurchaseOrder.class);
                            return typedQuery.getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
    
    public List<StockReceipt> getStockReceiptList()
    {
        try
        {
            String qryString = "SELECT e FROM StockReceipt e";
            TypedQuery<StockReceipt> typedQuery = crudApi.getEm().createQuery(qryString, StockReceipt.class);
                            return typedQuery.getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
    
    public List<StockReceiptItem> getStockReceiptItemList()
    {
        try
        {
            String qryString = "SELECT e FROM StockReceiptItem e JOIN Product p ON e.product=p GROUP BY p.productName";
            TypedQuery<StockReceiptItem> typedQuery = crudApi.getEm().createQuery(qryString, StockReceiptItem.class);
                            return typedQuery.getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
    
  public List<PurchaseOrderItem> getPurchaseOrderItem(PurchaseOrder purchaseOrder)
  {
        try
        {
           TypedQuery<PurchaseOrderItem> typedQuery = crudApi.getEm().createQuery("SELECT e FROM PurchaseOrderItem e WHERE e.purchaseOrder=?1", PurchaseOrderItem.class);
                            typedQuery.setParameter(1, purchaseOrder);
                            
                            return  typedQuery.getResultList();
           
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
 
    public List<Sales> getSales()
    {
        try
        {
           TypedQuery<Sales> typedQuery = crudApi.getEm().createQuery("SELECT e FROM Sales e WHERE e.valueDate BETWEEN ?1 AND ?2", Sales.class)
                   .setParameter(1, LocalDate.now())
                   .setParameter(2, LocalDate.now());
                    
           return  typedQuery.getResultList();
           
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    
    public List<SaleItem> getSales(Sales sales)
    {
        try
        {
           TypedQuery<SaleItem> typedQuery = crudApi.getEm().createQuery("SELECT e FROM SaleItem e WHERE e.sales=:sales", SaleItem.class);
                        typedQuery.setParameter("sales", sales);
                       return typedQuery.getResultList();
           
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    
    public Sales getTotalSumPerDateRange(DateRangeUtil dateRange)
    {
        try
        {
           return crudApi.getEm().createQuery("SELECT e FROM Sales e WHERE e.valueDate BETWEEN ?1 AND ?2 ", Sales.class)
                   .setParameter(1, dateRange.getFromDate())
                   .setParameter(2, dateRange.getToDate())
                   .getResultStream().findFirst().orElse(null);
           
        } catch (Exception e)
        {
           e.printStackTrace();
        }
        
        return null;
    }
    
    public StockReceiptItem getPackageQty(ProductPackage productPackage)
    {
        try 
        {
            return crudApi.getEm().createQuery("SELECT e FROM StockReceiptItem e WHERE e.productPackage=?1", StockReceiptItem.class)
                    .setParameter(1, productPackage)
                    .getResultStream().findFirst().orElse(null);
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Object[]> stockBreakDown()
    {
        try 
        {
//          Query query = crudApi.getEm().createQuery("SELECT s.pkgQuantity, p.productName, pp.packageFactor FROM StockReceiptItem s, Product p, ProductPackage pp WHERE pp.product = p.id AND s.productPackage = pp.id GROUP BY pp.id", StockReceiptItem.class);
          Query query = crudApi.getEm().createQuery("SELECT p.productName, s.pkgQuantity, pp.packageFactor FROM StockReceiptItem s, ProductPackage pp, Product p WHERE pp.product = p.id AND s.productPackage = pp.id GROUP BY pp.id", StockReceiptItem.class);
          return query.getResultList();
          
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    
    public List<Sales> getSalesByDates(DateRangeUtil dateRange)
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
            
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public Product existProdct(String productName)
    {
      return crudApi.getEm().createQuery("SELECT e FROM Product e WHERE e.productName=:productName", Product.class)
              .setParameter("productName", productName)
              .getResultStream().findFirst().orElse(null);
    }

    public ProductPackage existProdct(Product product, UnitMeasurement unitMeasurement)
    {
      return crudApi.getEm().createQuery("SELECT e FROM ProductPackage e WHERE e.product=:product AND e.unitMeasurement=:unitMeasurement", ProductPackage.class)
              .setParameter("product", product)
              .setParameter("unitMeasurement", unitMeasurement)
              .getResultStream().findFirst().orElse(null);
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
  
}

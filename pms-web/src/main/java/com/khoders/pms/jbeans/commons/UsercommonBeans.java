package com.khoders.pms.jbeans.commons;

import com.khoders.pms.entities.Category;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.pms.entities.Customer;
import com.khoders.pms.entities.Location;
import com.khoders.pms.entities.Manufacturer;
import com.khoders.pms.entities.Packaging;
import com.khoders.pms.entities.Potency;
import com.khoders.pms.entities.Product;
import com.khoders.pms.entities.ProductPackage;
import com.khoders.pms.entities.ProductType;
import com.khoders.pms.entities.StockReceiptItem;
import com.khoders.pms.entities.UnitMeasurement;
import com.khoders.pms.entities.Warehouse;
import com.khoders.pms.entities.system.UserAccount;
import com.khoders.pms.services.InventoryService;
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
@Named(value = "usercommonBeans")
@SessionScoped
public class UsercommonBeans implements Serializable
{
    @Inject private CrudApi crudApi;
    @Inject private InventoryService inventoryService;
    
    private List<Product> productList = new LinkedList<>();
    private List<ProductPackage> productPackageList = new LinkedList<>();
    private List<StockReceiptItem> stockReceiptItemList = new LinkedList<>();
    private List<UnitMeasurement> unitMeasurementList = new LinkedList<>();
    private List<Customer> customerList = new LinkedList<>();
    private List<UserAccount> userAccountList = new LinkedList<>();
    
    private List<ProductType> productTypeList = new LinkedList<>();
    private List<Potency> potencyList = new LinkedList<>();
    private List<Category> categoryList = new LinkedList<>();
    private List<Manufacturer> manufacturerList = new LinkedList<>();
    private List<Location> locationList = new LinkedList<>();
    private List<Packaging> packagingList = new LinkedList<>();
    private List<Warehouse> warehouseList = new LinkedList<>();
    
    @PostConstruct
    public void init()
    {
        productList = inventoryService.getProductList();
        productPackageList = inventoryService.getProductPackageList();
        stockReceiptItemList = inventoryService.getStockReceiptItemList();
        unitMeasurementList = inventoryService.getUnitMeasurementList();
        warehouseList = inventoryService.getWarehouseList();
        customerList = inventoryService.getCustomerList();
        userAccountList = inventoryService.getUserAccountList();
        
        productTypeList = inventoryService.getProductTypeList();
        potencyList = inventoryService.getPotencyList();
        categoryList = inventoryService.getCategoryList();
        manufacturerList = inventoryService.getManufacturerList();
        locationList = inventoryService.getLocationList();
        packagingList = inventoryService.getPackagingList();
    }

    public List<Product> getProductList()
    {
        return productList;
    }

    public List<UnitMeasurement> getUnitMeasurementList()
    {
        return unitMeasurementList;
    }

    public List<Customer> getCustomerList()
    {
        return customerList;
    }

    public List<StockReceiptItem> getStockReceiptItemList()
    {
        return stockReceiptItemList;
    }

    public void setStockReceiptItemList(List<StockReceiptItem> stockReceiptItemList)
    {
        this.stockReceiptItemList = stockReceiptItemList;
    }
    
    public List<ProductPackage> getProductPackageList()
    {
        return productPackageList;
    }

    public List<Manufacturer> getManufacturerList() {
        return manufacturerList;
    }

    public List<Location> getLocationList() {
        return locationList;
    }

    public List<UserAccount> getUserAccountList() {
        return userAccountList;
    }

    public List<Warehouse> getWarehouseList()
    {
        return warehouseList;
    }

    public List<ProductType> getProductTypeList()
    {
        return productTypeList;
    }

    public List<Potency> getPotencyList()
    {
        return potencyList;
    }

    public List<Category> getCategoryList()
    {
        return categoryList;
    }

    public List<Packaging> getPackagingList()
    {
        return packagingList;
    }
    
}

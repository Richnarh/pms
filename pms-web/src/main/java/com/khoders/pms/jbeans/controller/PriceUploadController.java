/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.jbeans.controller;

import com.khoders.pms.entities.Product;
import com.khoders.pms.entities.ProductPackage;
import com.khoders.pms.entities.ProductType;
import com.khoders.pms.entities.PurchaseOrder;
import com.khoders.pms.entities.PurchaseOrderItem;
import com.khoders.pms.entities.UnitMeasurement;
import com.khoders.pms.jbeans.dto.PriceUploadDetails;
import com.khoders.pms.listener.AppSession;
import com.khoders.pms.services.SalesService;
import com.khoders.pms.services.StockService;
import com.khoders.pms.services.UploadService;
import com.khoders.resource.enums.DeliveryMethod;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.BeansUtil;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.Stringz;
import com.khoders.resource.utilities.SystemUtils;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author richa
 */
@Named(value = "priceUploadController")
@SessionScoped
public class PriceUploadController implements Serializable
{
    @Inject private CrudApi crudApi;
    @Inject private AppSession appSession;
    @Inject private StockService stockService;
    @Inject private UploadService uploadService;
    @Inject private SalesService salesService;
        
    private PriceUploadDetails packagePrice = new PriceUploadDetails();
    private List<PriceUploadDetails> packagePriceList = new LinkedList<>();
    private List<ProductPackage> failedProductPackageList = new LinkedList<>();
    
    private PurchaseOrder selectedPurchaseOrder;
    
    private UploadedFile file = null;
    private boolean newPurchaseOrder = true;
    
    public String getFileExtension(String filename) {
    if(filename == null)
    {
        return null;
    }
    return filename.substring(filename.lastIndexOf(".") + 1, filename.length());
    }
    
    public void uploadPrice()
    {
        if (file.getSize() < 1)
        {
            Msg.error("No excel file is selected!");
            return;
        }

        try
        {
            String extension = getFileExtension(file.getFileName());
            System.out.println("type ==> " + extension);

            InputStream inputStream = file.getInputStream();
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);

            sheet.removeRow(sheet.getRow(0));
            Iterator<Row> iterator = sheet.iterator();
            System.out.println("Starting....");
            int c = 0;
            while (iterator.hasNext())
            {
                c++;
                packagePrice = new PriceUploadDetails();
                Row currentRow = iterator.next();
                String prodName = BeansUtil.objToString(currentRow.getCell(0));
                if(prodName == null || prodName.isEmpty()){
                    Msg.error("Please the excel has  packagePrice name field(s) being empty!");
                    return;
                }
                packagePrice.setProductName(prodName);
                
                String qtyPurchased = BeansUtil.objToString(currentRow.getCell(1));
                if(qtyPurchased != null && !qtyPurchased.isEmpty()){
                    packagePrice.setQtyPurchased(Double.parseDouble(qtyPurchased));
                }
                
                String productType = BeansUtil.objToString(currentRow.getCell(2));
                if(productType != null && !productType.isEmpty()){
                    packagePrice.setProductType(productType);
                }
                               
                String packaging = BeansUtil.objToString(currentRow.getCell(3));
                if(packaging != null && !packaging.isEmpty()){
                    packagePrice.setPackaging(packaging);
                }
                 
                String units = BeansUtil.objToString(currentRow.getCell(4));
                if(units != null && !units.isEmpty()){
                    packagePrice.setUnitsMeasurement(units);
                }
                
                String purchasedPrice = BeansUtil.objToString(currentRow.getCell(5));
                if(purchasedPrice != null && !purchasedPrice.isEmpty()){
                    packagePrice.setPurchasedPrice(BeansUtil.objToDouble(purchasedPrice));
                }
                
                String sellPrice = BeansUtil.objToString(currentRow.getCell(6));
                if(sellPrice != null && !sellPrice.isEmpty()){
                    packagePrice.setSellingPrice(BeansUtil.objToDouble(sellPrice));
                }
                String unitsInPackage = BeansUtil.objToString(currentRow.getCell(7));
                if(unitsInPackage != null && !unitsInPackage.isEmpty()){
                    packagePrice.setUnitsInPackage(BeansUtil.objToDouble(unitsInPackage));
                }
                                
                packagePriceList.add(packagePrice);
                
                System.out.println("Iteration "+c+" done!");
            }
        } catch (Exception e)
        {
           e.printStackTrace();
        }
    }
        
    @SuppressWarnings("null")
    public void saveUpload()
    {
        PurchaseOrder purchaseOrder = null;
        try
        {
            int c=0;
            if(packagePriceList.isEmpty()) return;
            boolean productUpload = uploadService.saveUpload(packagePriceList);
            if (productUpload)
            {
                if(newPurchaseOrder){
                    purchaseOrder = new PurchaseOrder();
                    purchaseOrder.setCustomer(salesService.walkinCustomer());
                    purchaseOrder.setDeliveryMethod(DeliveryMethod.ONSITE_PICK_UP);
                    purchaseOrder.setOrderCode(SystemUtils.generatePO());
                    purchaseOrder.setPurchasedDate(LocalDate.now());
                    purchaseOrder.setTotalAmount(packagePriceList.stream().mapToDouble(PriceUploadDetails::getPurchasedPrice).sum());
                    purchaseOrder.genCode();
                    purchaseOrder.setUserAccount(appSession.getCurrentUser());
                    purchaseOrder.setCompanyBranch(appSession.getCompanyBranch());
                    crudApi.save(purchaseOrder);
                }else{
                    purchaseOrder = selectedPurchaseOrder != null ? selectedPurchaseOrder : null;
                }
                
                if(newPurchaseOrder && purchaseOrder == null){
                    Msg.error("Please select Purchase order!");
                    return;
                }
                            
                for (PriceUploadDetails details : packagePriceList)
                {
                    UnitMeasurement measurement = stockService.getUnits(details.getUnitsMeasurement() != null ? details.getUnitsMeasurement().trim() : null);
                    if (measurement == null)
                    {
                        UnitMeasurement units = new UnitMeasurement();
                        units.genCode();
                        units.setUnits(details.getUnitsMeasurement());
                        units.setUserAccount(appSession.getCurrentUser());
                        units.setCompanyBranch(appSession.getCompanyBranch());
                        units.setLastModifiedBy(appSession.getCurrentUser() != null ? appSession.getCurrentUser().getFullname() : null);

                        crudApi.save(units);
                    }
                    
                    ProductType productType = stockService.getProductType(details.getProductType() != null && !details.getProductType().isEmpty() ? Stringz.capitalizeOnlyFirst(details.getProductType().trim()) : null);
                    if (productType == null)
                    {
                        ProductType type = new ProductType();
                        type.genCode();
                        type.setProductTypeName(details.getProductType() != null && !details.getProductType().isEmpty() ? Stringz.capitalizeOnlyFirst(details.getProductType().trim()): "");
                        type.setUserAccount(appSession.getCurrentUser());
                        type.setCompanyBranch(appSession.getCompanyBranch());
                        type.setLastModifiedBy(appSession.getCurrentUser() != null ? appSession.getCurrentUser().getFullname() : null);

                        crudApi.save(type);
                    }
                    
                    Product product = stockService.getProduct(details.getProductName() != null ? details.getProductName().trim() : null);
                    if (product == null)
                    {
                        Product newProduct = new Product();
                        newProduct.genCode();
                        newProduct.setProductName(details.getProductName());
                        newProduct.setUserAccount(appSession.getCurrentUser());
                        newProduct.setCompanyBranch(appSession.getCompanyBranch());
                        newProduct.setLastModifiedBy(appSession.getCurrentUser() != null ? appSession.getCurrentUser().getFullname() : null);

                        crudApi.save(newProduct);
                    }
                    ProductPackage pp = stockService.existProdctPackage(product, measurement);
                    if (pp == null)
                    {
                        ProductPackage productPackage = new ProductPackage();
                        productPackage.setProduct(product);
                        productPackage.setUnitMeasurement(measurement);
                        productPackage.setPackagePrice(details.getSellingPrice());
                        productPackage.setUnitsInPackage(details.getUnitsInPackage());
                        
                        if(crudApi.save(productPackage) != null){
                            PurchaseOrderItem orderItem = new PurchaseOrderItem();
                            orderItem.setCostPrice(details.getPurchasedPrice());
                            orderItem.setDescription(purchaseOrder.getOrderCode()+" occured on "+purchaseOrder.getPurchasedDate());
                            orderItem.setProduct(product);
                            orderItem.setProductPackage(productPackage);
                            orderItem.setPurchaseOrder(purchaseOrder);
                            orderItem.setQtyPurchased(details.getQtyPurchased());
                            orderItem.setSellingPrice(details.getSellingPrice());
                            orderItem.setSubTotal(details.getQtyPurchased() * details.getSellingPrice());
                            orderItem.genCode();

                            crudApi.save(orderItem);
                        }
                    } else
                    {
                        failedProductPackageList.add(pp);
                    }
                }
            }
            System.out.println("packagePriceList -- "+packagePriceList.size());
            System.out.println("failedProductPackageList -- "+failedProductPackageList.size());
            failedProductPackageList.forEach(i->System.out.println("Item: "+i.getProduct().getProductName()));
            Msg.info("Stock upload saved!");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void clear()
    {
        packagePriceList = new LinkedList<>();
        file = null;
        packagePrice = new PriceUploadDetails();
        SystemUtils.resetJsfUI();
    }
    
    public UploadedFile getFile()
    {
        return file;
    }

    public void setFile(UploadedFile file)
    {
        this.file = file;
    }

    public List<PriceUploadDetails> getPackagePriceList()
    {
        return packagePriceList;
    }

    public boolean isNewPurchaseOrder()
    {
        return newPurchaseOrder;
    }

    public void setNewPurchaseOrder(boolean newPurchaseOrder)
    {
        this.newPurchaseOrder = newPurchaseOrder;
    }

    public PurchaseOrder getSelectedPurchaseOrder()
    {
        return selectedPurchaseOrder;
    }

    public void setSelectedPurchaseOrder(PurchaseOrder selectedPurchaseOrder)
    {
        this.selectedPurchaseOrder = selectedPurchaseOrder;
    }

    public List<ProductPackage> getFailedProductPackageList()
    {
        return failedProductPackageList;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.services;

import com.khoders.pms.entities.Packaging;
import com.khoders.pms.entities.Product;
import com.khoders.pms.entities.ProductType;
import com.khoders.pms.jbeans.dto.PriceUploadDetails;
import com.khoders.pms.listener.AppSession;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.Stringz;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author richa
 */
@Stateless
public class UploadService
{
    @Inject private CrudApi crudApi;
    @Inject private AppSession appSession;
    @Inject private InventoryService inventoryService;
    @Inject private StockService stockService;
    
    private List<Product> failedProductList = new LinkedList<>();
    
    public boolean saveUpload(List<PriceUploadDetails> productList)
    {
        try
        {
            int c=0;
            if(productList.isEmpty()) return false;
            for (PriceUploadDetails details : productList)
            {
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
                
                Packaging packages = stockService.getPackage(details.getPackaging() != null ? details.getPackaging().trim() : null);
                if (packages == null)
                {
                    Packaging pack = new Packaging();
                    pack.genCode();
                    pack.setPackagingName(details.getPackaging());
                    pack.setUserAccount(appSession.getCurrentUser());
                    pack.setCompanyBranch(appSession.getCompanyBranch());
                    pack.setLastModifiedBy(appSession.getCurrentUser() != null ? appSession.getCurrentUser().getFullname() : null);
                
                    crudApi.save(pack);
                }
                
                Product prod = new Product();
                prod.setProductType(productType);
                prod.setPackaging(packages);
                prod.setProductName(details.getProductName().trim());
                Product oldProduct = inventoryService.existProdct(prod);
                if(oldProduct == null){
                    Product newProduct = new Product();
                    newProduct.genCode();
                    newProduct.setProductName(details.getProductName().trim());
                    newProduct.setProductType(productType);
                    newProduct.setPackaging(packages);
                    newProduct.setUserAccount(appSession.getCurrentUser());
                    newProduct.setCompanyBranch(appSession.getCompanyBranch());
                    newProduct.setLastModifiedBy(appSession.getCurrentUser() != null ? appSession.getCurrentUser().getFullname() : null);  
                    System.out.println("Creating products........"+c++);
                    crudApi.save(newProduct);
                }   
                else{
                   failedProductList.add(oldProduct);
                }
                
            }
            System.out.println("productList -- "+productList.size());
            System.out.println("failedProductList -- "+failedProductList.size());
            failedProductList.forEach(i->System.out.println("Item: "+i.getProductName()));
//            Msg.info("Product upload saved!");
            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
}

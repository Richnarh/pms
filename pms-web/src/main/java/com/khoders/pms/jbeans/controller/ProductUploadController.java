/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.jbeans.controller;

import com.khoders.pms.entities.Packaging;
import com.khoders.pms.entities.Product;
import com.khoders.pms.entities.ProductType;
import com.khoders.pms.jbeans.dto.ProductDetails;
import com.khoders.pms.listener.AppSession;
import com.khoders.pms.services.StockService;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.BeansUtil;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.SystemUtils;
import java.io.InputStream;
import java.io.Serializable;
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
@Named(value = "productUploadController")
@SessionScoped
public class ProductUploadController implements Serializable
{
    @Inject private CrudApi crudApi;
    @Inject private AppSession appSession;
    @Inject private StockService stockService;
    private ProductDetails product = new ProductDetails();
    private List<ProductDetails> productList = new LinkedList<>();
    
    private UploadedFile file = null;
    
    public String getFileExtension(String filename) {
        if(filename == null)
        {
            return null;
        }
        return filename.substring(filename.lastIndexOf(".") + 1, filename.length());
    }
    
    public void uploadProduct()
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
                product = new ProductDetails();
                Row currentRow = iterator.next();
                product.setProductName(BeansUtil.objToString(currentRow.getCell(0)));
                product.setProductType(BeansUtil.objToString(currentRow.getCell(1)));
                product.setPackaging(BeansUtil.objToString(currentRow.getCell(2)));

                productList.add(product);
                
                System.out.println("Iteration "+c+" done!");
            }
        } catch (Exception e)
        {
           e.printStackTrace();
        }
    }
        
    public void saveUpload()
    {
        try
        {
            if(productList.isEmpty()) return;
            for (ProductDetails details : productList)
            {
               Product newProduct = new Product();
               
                ProductType productType = stockService.getProductType(details.getProductType().trim());
                if (productType == null)
                {
                    ProductType type = new ProductType();
                    type.genCode();
                    type.setProductTypeName(details.getProductType());
                    type.setUserAccount(appSession.getCurrentUser());
                    type.setCompanyBranch(appSession.getCompanyBranch());
                    type.setLastModifiedBy(appSession.getCurrentUser() != null ? appSession.getCurrentUser().getFullname() : null);
                
                    crudApi.save(type);
                }
                
                Packaging packages = stockService.getPackage(details.getPackaging().trim());
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
                newProduct.genCode();
                newProduct.setProductName(details.getProductName().trim());
                newProduct.setProductType(productType);
                newProduct.setPackaging(packages);
                newProduct.setUserAccount(appSession.getCurrentUser());
                newProduct.setCompanyBranch(appSession.getCompanyBranch());
                newProduct.setLastModifiedBy(appSession.getCurrentUser() != null ? appSession.getCurrentUser().getFullname() : null);
                
                crudApi.save(newProduct);
            }
            
            Msg.info("Product upload saved!");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void clear()
    {
        productList = new LinkedList<>();
        file = null;
        product = new ProductDetails();
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

    public List<ProductDetails> getProductList()
    {
        return productList;
    }

}

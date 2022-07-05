
package com.khoders.pms.jbeans.controller;

import com.khoders.pms.services.InventoryService;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.CollectionList;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.SystemUtils;
import com.khoders.pms.entities.ProductPackage;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author richa
 */
@Named(value = "productPackageController")
@SessionScoped
public class ProductPackageController implements Serializable
{
   @Inject private CrudApi crudApi;
   @Inject private InventoryService inventoryService;
   
   private ProductPackage productPackage;
   private List<ProductPackage> productPackageList = new LinkedList<>();
   private String optionText;
   
   @PostConstruct
   private void init()
   {
     clearProductPackage();
     productPackageList = inventoryService.getProductPackageList();
   }
   
   public void saveProductPackage()
   {
       try
       {
           if(optionText.equals("Save Changes")){
              ProductPackage newPackage = inventoryService.existProdct(productPackage.getProduct(), productPackage.getUnitMeasurement());
              if (newPackage != null)
              {
                  Msg.error("product and the package already exist");
                  return;
              }
          }
           if(productPackage.getPackageFactor() == 0.0){
               Msg.error("Package factor is required");
               return;
           }
           if(productPackage.getPackagePrice() == 0.0){
               Msg.error("Package price is required");
               return;
           }
          productPackage.genCode();
          if(crudApi.save(productPackage) != null){
              productPackageList = CollectionList.washList(productPackageList, productPackage);
               Msg.info(Msg.SUCCESS_MESSAGE);
          }
          clearProductPackage();
       } catch (Exception e)
       {
          e.printStackTrace();
       }
   }
   
   public void deleteProductPackage(ProductPackage productPackage)
   {
       try
       {
         if(crudApi.delete(productPackage))
         {
             productPackageList.remove(productPackage);
             
             FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
         }  
       } catch (Exception e)
       {
         e.printStackTrace();
       }
   }
   
   public void editProductPackage(ProductPackage productPackage){
       this.productPackage = productPackage;
       this.optionText = "Update";
   }

    public void clearProductPackage()
    {
        productPackage = new ProductPackage();
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }

    public ProductPackage getProductPackage()
    {
        return productPackage;
    }

    public void setProductPackage(ProductPackage productPackage)
    {
        this.productPackage = productPackage;
    }

    public List<ProductPackage> getProductPackageList()
    {
        return productPackageList;
    }

    public String getOptionText()
    {
        return optionText;
    }
    
}

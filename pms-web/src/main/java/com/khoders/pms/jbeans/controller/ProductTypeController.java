
package com.khoders.pms.jbeans.controller;

import com.khoders.pms.entities.ProductType;
import com.khoders.pms.services.InventoryService;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.CollectionList;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.SystemUtils;
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
@Named(value = "productTypeController")
@SessionScoped
public class ProductTypeController implements Serializable
{
   @Inject private CrudApi crudApi;
   @Inject private InventoryService inventoryService;
   
   private ProductType productType;
   private List<ProductType> productTypeList = new LinkedList<>();
   private String optionText;
   
   @PostConstruct
   private void init()
   {
     clearProductType();
     productTypeList = inventoryService.getProductTypeList();
   }
   
   public void saveProductType()
   {
       try
       {
          productType.genCode();
          if(crudApi.save(productType) != null){
              productTypeList = CollectionList.washList(productTypeList, productType);
              
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
          }
          clearProductType();
       } catch (Exception e)
       {
          e.printStackTrace();
       }
   }
   
   public void deleteProductType(ProductType productType){
       try
       {
         if(crudApi.delete(productType))
         {
             productTypeList.remove(productType);
             
             FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
         }  
       } catch (Exception e)
       {
         e.printStackTrace();
       }
   }
   
   public void editProductType(ProductType productType){
       this.productType = productType;
       optionText = "Update";
   }

    public void clearProductType()
    {
        productType = new ProductType();
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }

    public ProductType getProductType()
    {
        return productType;
    }

    public void setProductType(ProductType productType)
    {
        this.productType = productType;
    }

    public List<ProductType> getProductTypeList()
    {
        return productTypeList;
    }

    public String getOptionText()
    {
        return optionText;
    }
    
}

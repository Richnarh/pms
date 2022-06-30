
package com.khoders.pms.jbeans.controller;

import com.khoders.pms.services.InventoryService;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.CollectionList;
import com.khoders.resource.utilities.FormView;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.SystemUtils;
import com.khoders.pms.entities.Product;
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
@Named(value = "productController")
@SessionScoped
public class ProductController implements Serializable
{
   @Inject private CrudApi crudApi;
   @Inject private InventoryService inventoryService;
   private Product product;
   private List<Product> productList = new LinkedList<>();
   private String optionText;
   
   private FormView pageView = FormView.listForm();
   
   @PostConstruct
   private void init()
   {
     clearProduct();
     productList = inventoryService.getProductList();
   }
   
   public void initProduct()
   {
       clearProduct();
       pageView.restToCreateView();
   }
   
   public void saveProduct()
   {
       try
       {
          product.genCode();
          if(crudApi.save(product) != null){
              productList = CollectionList.washList(productList, product);
              
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
          }
          closePage();
       } catch (Exception e)
       {
          e.printStackTrace();
       }
   }
   
   public void deleteProduct(Product product){
       try
       {
         if(crudApi.delete(product))
         {
             productList.remove(product);
             
             FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
         }  
       } catch (Exception e)
       {
         e.printStackTrace();
       }
   }
   
   public void editProduct(Product product){
       this.product = product;
       pageView.restToCreateView();
       this.optionText = "Update";
   }

    public void clearProduct()
    {
        product = new Product();
        product.genCode();
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }
    
    public void closePage()
    {
       product = new Product();
       optionText = "Save Changes";
       pageView.restToListView();
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public List<Product> getProductList()
    {
        return productList;
    }

    public String getOptionText()
    {
        return optionText;
    }

    public FormView getPageView() {
        return pageView;
    }

    public void setPageView(FormView pageView) {
        this.pageView = pageView;
    }
    
}

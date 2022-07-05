
package com.khoders.pms.jbeans.controller;

import com.khoders.pms.services.InventoryService;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.CollectionList;
import com.khoders.resource.utilities.FormView;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.SystemUtils;
import com.khoders.pms.entities.Product;
import com.khoders.pms.listener.AppSession;
import java.io.Serializable;
import java.time.LocalDateTime;
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
@Named(value = "productController")
@SessionScoped
public class ProductController implements Serializable
{
   @Inject private CrudApi crudApi;
   @Inject private AppSession appSession;
   @Inject private InventoryService inventoryService;
   private Product product = new Product();
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
          if(optionText.equals("Save Changes")){
              Product newProduct = inventoryService.existProdct(product.getProductName());
              if (newProduct != null)
              {
                  Msg.error("Product already exist");
                  return;
              }
          }
          product.genCode();
          if(crudApi.save(product) != null){
              productList = CollectionList.washList(productList, product);
              Msg.info(Msg.SUCCESS_MESSAGE);
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
            Msg.info(Msg.SUCCESS_MESSAGE);
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
       product.setUserAccount(appSession.getCurrentUser());
       product.setCompanyBranch(appSession.getCompanyBranch());
       product.setLastModifiedDate(LocalDateTime.now());
       product.setLastModifiedBy(appSession.getCurrentUser() != null ? appSession.getCurrentUser().getFullname() : null);
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

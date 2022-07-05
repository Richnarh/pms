
package com.khoders.pms.jbeans.controller;

import com.khoders.pms.entities.Category;
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
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author richa
 */
@Named(value = "categoryController")
@SessionScoped
public class CategoryController implements Serializable
{
   @Inject private CrudApi crudApi;
   @Inject private InventoryService inventoryService;
   
   private Category category = new Category();
   private List<Category> categoryList = new LinkedList<>();
   private String optionText;
   
   @PostConstruct
   private void init()
   {
     clearCategory();
     categoryList = inventoryService.getCategoryList();
   }
   
   public void saveCategory()
   {
       try
       {
          category.genCode();
          if(crudApi.save(category) != null)
          {
              categoryList = CollectionList.washList(categoryList, category);
              Msg.info(Msg.SUCCESS_MESSAGE);
          }
          clearCategory();
       } catch (Exception e)
       {
          e.printStackTrace();
       }
   }
   
   public void deleteCategory(Category category){
       try
       {
         if(crudApi.delete(category))
         {
            categoryList.remove(category);
            Msg.info(Msg.SUCCESS_MESSAGE);
         }  
       } catch (Exception e)
       {
         e.printStackTrace();
       }
   }
   
   public void editCategory(Category category){
       this.category = category;
       optionText = "Update";
       SystemUtils.resetJsfUI();
   }

    public void clearCategory()
    {
        category = new Category();
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }

    public Category getCategory()
    {
      return category;
    }

    public void setCategory(Category category)
    {
        this.category = category;
    }

    public List<Category> getCategoryList()
    {
        return categoryList;
    }

    public String getOptionText()
    {
        return optionText;
    }
}

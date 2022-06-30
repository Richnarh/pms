
package com.khoders.pms.jbeans.controller;

import com.khoders.pms.services.InventoryService;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.CollectionList;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.SystemUtils;
import com.khoders.pms.entities.Packaging;
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
@Named(value = "packagingController")
@SessionScoped
public class PackagingController implements Serializable
{
   @Inject private CrudApi crudApi;
   @Inject private InventoryService inventoryService;
   
   private Packaging packaging = new Packaging();
   private List<Packaging> packagingList = new LinkedList<>();
   private String optionText;
   
   @PostConstruct
   private void init()
   {
     clearPackaging();
     packagingList = inventoryService.getPackagingList();
   }
   
   public void savePackaging()
   {
       try
       {
          packaging.genCode();
          if(crudApi.save(packaging) != null)
          {
              packagingList = CollectionList.washList(packagingList, packaging);
              
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
          }
          clearPackaging();
       } catch (Exception e)
       {
          e.printStackTrace();
       }
   }
   
   public void deletePackaging(Packaging packaging){
       try
       {
         if(crudApi.delete(packaging))
         {
             packagingList.remove(packaging);
             
             FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
         }  
       } catch (Exception e)
       {
         e.printStackTrace();
       }
   }
   
   public void editPackaging(Packaging packaging){
       this.packaging = packaging;
       optionText = "Update";
   }

    public void clearPackaging()
    {
        packaging = new Packaging();
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }

    public Packaging getPackaging()
    {
      return packaging;
    }

    public void setPackaging(Packaging packaging)
    {
        this.packaging = packaging;
    }

    public List<Packaging> getPackagingList()
    {
        return packagingList;
    }

    public String getOptionText()
    {
        return optionText;
    }
}


package com.khoders.pms.jbeans.controller;

import com.khoders.pms.services.InventoryService;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.CollectionList;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.SystemUtils;
import com.khoders.pms.entities.Manufacturer;
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
@Named(value = "manufacturerController")
@SessionScoped
public class ManufacturerController implements Serializable
{
   @Inject private CrudApi crudApi;
   @Inject private InventoryService inventoryService;
   
   private Manufacturer manufacturer = new Manufacturer();
   private List<Manufacturer> manufacturerList = new LinkedList<>();
   private String optionText;
   
   @PostConstruct
   private void init()
   {
     clearManufacturer();
     manufacturerList = inventoryService.getManufacturerList();
   }
   
   public void saveManufacturer()
   {
       try
       {
          manufacturer.genCode();
          if(crudApi.save(manufacturer) != null)
          {
              manufacturerList = CollectionList.washList(manufacturerList, manufacturer);
              Msg.info(Msg.SUCCESS_MESSAGE);
          }
          clearManufacturer();
       } catch (Exception e)
       {
          e.printStackTrace();
       }
   }
   
   public void deleteManufacturer(Manufacturer manufacturer){
       try
       {
         if(crudApi.delete(manufacturer))
         {
             manufacturerList.remove(manufacturer);
             Msg.info(Msg.SUCCESS_MESSAGE);
         }  
       } catch (Exception e)
       {
         e.printStackTrace();
       }
   }
   
   public void editManufacturer(Manufacturer manufacturer){
       this.manufacturer = manufacturer;
       optionText = "Update";
   }

    public void clearManufacturer()
    {
        manufacturer = new Manufacturer();
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }

    public Manufacturer getManufacturer()
    {
      return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer)
    {
        this.manufacturer = manufacturer;
    }

    public List<Manufacturer> getManufacturerList()
    {
        return manufacturerList;
    }

    public String getOptionText()
    {
        return optionText;
    }
}

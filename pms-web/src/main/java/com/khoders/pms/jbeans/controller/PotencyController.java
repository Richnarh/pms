
package com.khoders.pms.jbeans.controller;

import com.khoders.pms.services.InventoryService;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.CollectionList;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.SystemUtils;
import com.khoders.pms.entities.Potency;
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
@Named(value = "potencyController")
@SessionScoped
public class PotencyController implements Serializable
{
   @Inject private CrudApi crudApi;
   @Inject private InventoryService inventoryService;
   
   private Potency potency = new Potency();
   private List<Potency> potencyList = new LinkedList<>();
   private String optionText;
   
   @PostConstruct
   private void init()
   {
     clearPotency();
     potencyList = inventoryService.getPotencyList();
   }
   
   public void savePotency()
   {
       try
       {
          potency.genCode();
          if(crudApi.save(potency) != null)
          {
              potencyList = CollectionList.washList(potencyList, potency);
              
              FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
          }
          clearPotency();
       } catch (Exception e)
       {
          e.printStackTrace();
       }
   }
   
   public void deletePotency(Potency potency){
       try
       {
         if(crudApi.delete(potency))
         {
             potencyList.remove(potency);
             
             FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
         }  
       } catch (Exception e)
       {
         e.printStackTrace();
       }
   }
   
   public void editPotency(Potency potency){
       this.potency = potency;
       optionText = "Update";
   }

    public void clearPotency()
    {
        potency = new Potency();
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }

    public Potency getPotency()
    {
      return potency;
    }

    public void setPotency(Potency potency)
    {
        this.potency = potency;
    }

    public List<Potency> getPotencyList()
    {
        return potencyList;
    }

    public String getOptionText()
    {
        return optionText;
    }
}

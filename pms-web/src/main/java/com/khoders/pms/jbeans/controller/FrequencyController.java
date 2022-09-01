
package com.khoders.pms.jbeans.controller;

import com.khoders.pms.services.InventoryService;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.CollectionList;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.SystemUtils;
import com.khoders.pms.entities.Frequency;
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
@Named(value = "frequencyController")
@SessionScoped
public class FrequencyController implements Serializable
{
   @Inject private CrudApi crudApi;
   @Inject private InventoryService inventoryService;
   
   private Frequency frequency = new Frequency();
   private List<Frequency> frequencyList = new LinkedList<>();
   private String optionText;
   
   @PostConstruct
   private void init()
   {
     clearFrequency();
     frequencyList = inventoryService.getFrequencyList();
   }
   
   public void saveFrequency()
   {
       try
       {
          frequency.genCode();
          if(crudApi.save(frequency) != null)
          {
              frequencyList = CollectionList.washList(frequencyList, frequency);
              Msg.info(Msg.SUCCESS_MESSAGE);
          }
          clearFrequency();
       } catch (Exception e)
       {
          e.printStackTrace();
       }
   }
   
   public void deleteFrequency(Frequency frequency){
       try
       {
         if(crudApi.delete(frequency))
         {
             frequencyList.remove(frequency);
              Msg.info(Msg.SUCCESS_MESSAGE);
         }  
       } catch (Exception e)
       {
         e.printStackTrace();
       }
   }
   
   public void editFrequency(Frequency frequency){
       this.frequency = frequency;
       optionText = "Update";
   }

    public void clearFrequency()
    {
        frequency = new Frequency();
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }

    public Frequency getFrequency()
    {
      return frequency;
    }

    public void setFrequency(Frequency frequency)
    {
        this.frequency = frequency;
    }

    public List<Frequency> getFrequencyList()
    {
        return frequencyList;
    }

    public String getOptionText()
    {
        return optionText;
    }
}

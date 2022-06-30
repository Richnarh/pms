
package com.khoders.pms.jbeans.controller;

import com.khoders.pms.services.InventoryService;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.CollectionList;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.SystemUtils;
import com.khoders.pms.entities.Warehouse;
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
@Named(value = "warehouseController")
@SessionScoped
public class WarehouseController implements Serializable
{
   @Inject private CrudApi crudApi;
   @Inject private InventoryService inventoryService;
   
   private Warehouse warehouse;
   private List<Warehouse> warehouseList = new LinkedList<>();
   private String optionText;
   
   @PostConstruct
   private void init()
   {
     clearWarehouse();
     warehouseList = inventoryService.getWarehouseList();
   }
   
   public void saveWarehouse()
   {
       try
       {
          warehouse.genCode();
          if(crudApi.save(warehouse) != null){
              warehouseList = CollectionList.washList(warehouseList, warehouse);
              Msg.info(Msg.SUCCESS_MESSAGE);
          }
          clearWarehouse();
       } catch (Exception e)
       {
          e.printStackTrace();
       }
   }
   
   public void deleteWarehouse(Warehouse warehouse)
   {
       try
       {
         if(crudApi.delete(warehouse))
         {
            warehouseList.remove(warehouse);
            Msg.info(Msg.SUCCESS_MESSAGE);
         }  
       } catch (Exception e)
       {
         e.printStackTrace();
       }
   }
   
   public void editWarehouse(Warehouse warehouse){
       this.warehouse = warehouse;
       this.optionText = "Update";
   }

    public void clearWarehouse()
    {
        warehouse = new Warehouse();
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }

    public Warehouse getWarehouse()
    {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse)
    {
        this.warehouse = warehouse;
    }

    public List<Warehouse> getWarehouseList()
    {
        return warehouseList;
    }

    public String getOptionText()
    {
        return optionText;
    }
    
}

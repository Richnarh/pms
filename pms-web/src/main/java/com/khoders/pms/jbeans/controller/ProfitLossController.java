package com.khoders.pms.jbeans.controller;

import com.khoders.pms.services.InventoryService;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.DateRangeUtil;
import com.khoders.pms.entities.SaleItem;
import com.khoders.pms.entities.Sales;
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
@Named(value = "profitLossController")
@SessionScoped
public class ProfitLossController implements Serializable
{
   @Inject private CrudApi crudApi;
   @Inject private InventoryService inventoryService;
   
   private DateRangeUtil dateRange = new DateRangeUtil();
   
   private List<Sales> salesCatalogueList = new LinkedList<>();
   private List<SaleItem> saleItemList = new LinkedList<>();
   
   private double profit,loss,totalProfit,totalLoss = 0.0;
    
   @PostConstruct
   private void init(){
       reset();
   }
   
   public void view(Sales salesCatalogue){
       saleItemList = inventoryService.getSales(salesCatalogue);
   }
   
   public void reset(){
       salesCatalogueList = new LinkedList<>();
       totalProfit = 0.0;
       totalLoss = 0.0;
       dateRange = new DateRangeUtil();
   }
   
   public void runSalesProfit(){
       double sp=0.0,cp=0.0,pProfit=0.0, pLoss=0.0;
       
       salesCatalogueList = inventoryService.getSalesByDates(dateRange);
       
       totalProfit = 0.0;
       totalLoss = 0.0;
       
       for (Sales salesCatalogue : salesCatalogueList)
       {
            System.out.println("have been here again!");
            profit = 0.0;
            loss = 0.0;
            
           saleItemList = inventoryService.getSales(salesCatalogue);
           
           
           for (SaleItem cart : saleItemList)
           {
               if(cart.getProductPackage() != null)
               {
                sp = cart.getProductPackage().getPackagePrice();
                cp = cart.getStockReceiptItem().getCostPrice();

                System.out.println("SP => "+sp +" CP => "+cp);

                if(cp > sp)
                {
                    pLoss = cp-sp;
                }
                else
                {
                    pProfit = sp-cp;
                }

                profit+=pProfit;
                loss+=pLoss;
 //               
                cart.setProfit(profit);
                cart.setLoss(loss);
                crudApi.save(cart);
                   
               }
           }
           
           System.out.println("Profit => "+profit +" Loss => "+pLoss);
           salesCatalogue.setProfit(profit);
           salesCatalogue.setLoss(loss);
//           
           crudApi.save(salesCatalogue);

            totalProfit += salesCatalogue.getProfit();
            totalLoss += salesCatalogue.getLoss();
       }
   }

    public DateRangeUtil getDateRange()
    {
        return dateRange;
    }

    public void setDateRange(DateRangeUtil dateRange)
    {
        this.dateRange = dateRange;
    }

    public List<Sales> getSalesCatalogueList()
    {
        return salesCatalogueList;
    }

    public List<SaleItem> getSaleItemList()
    {
        return saleItemList;
    }
    
    public double getTotalProfit() {
        return totalProfit;
    }

    public double getTotalLoss() {
        return totalLoss;
    }
    
}

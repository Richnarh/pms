package com.khoders.pms.jbeans.controller;

import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.DateRangeUtil;
import com.khoders.pms.entities.Sales;
import com.khoders.pms.services.InventoryService;
import java.io.Serializable;
import java.time.LocalDate;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author richa
 */
@Named(value = "dashboardController")
@SessionScoped
public class DashboardController implements Serializable
{

    @Inject
    private CrudApi crudApi;
    @Inject
    private InventoryService inventoryService;
    
    private DateRangeUtil dateRange = new DateRangeUtil();
    
    double monthlyTotalSum,weeklyTotalSum,dailyTotalSum=0.0;

    @PostConstruct
    private void init()
    {
       getDailySales();
       getTotalMonthlySales();
       getTotalWeeklySales();
    }
    private void getDailySales()
    {
        
        dateRange.setFromDate(LocalDate.now());
        dateRange.setToDate(LocalDate.now());
       
        Sales sales  = inventoryService.getTotalSumPerDateRange(dateRange);
        if(sales != null){
            dailyTotalSum = sales.getTotalAmount();
        }
    }
    
    private void getTotalMonthlySales()
    {
       int todayDateValue = LocalDate.now().getDayOfMonth() - 1;

       LocalDate fromDate = LocalDate.now().minusDays(todayDateValue);
       LocalDate todayDate = fromDate.plusDays(todayDateValue);
        
        dateRange.setFromDate(fromDate);
        dateRange.setToDate(todayDate);
       
        Sales salesCatalogue  = inventoryService.getTotalSumPerDateRange(dateRange);
        if(salesCatalogue != null){
            monthlyTotalSum = salesCatalogue.getTotalAmount();
        }
    }

    private void getTotalWeeklySales()
    {
       int todayDateValue = LocalDate.now().getDayOfWeek().getValue();
        
       LocalDate fromDate = LocalDate.now().minusDays(todayDateValue);
        
       LocalDate todayDate = fromDate.plusDays(6);
        
        dateRange.setFromDate(fromDate);
        dateRange.setToDate(todayDate);
       
        Sales salesCatalogue  = inventoryService.getTotalSumPerDateRange(dateRange);
        if(salesCatalogue != null){
            weeklyTotalSum = salesCatalogue.getTotalAmount();
        }
    }

    public double getMonthlyTotalSum()
    {
        return monthlyTotalSum;
    }

    public double getWeeklyTotalSum()
    {
        return weeklyTotalSum;
    }

    public double getDailyTotalSum()
    {
        return dailyTotalSum;
    }
    
    
}

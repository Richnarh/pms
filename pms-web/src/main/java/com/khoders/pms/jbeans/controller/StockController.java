/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.jbeans.controller;

import com.khoders.pms.jbeans.ReportFiles;
import com.khoders.pms.jbeans.dto.StockSummary;
import com.khoders.pms.services.XtractService;
import com.khoders.resource.reports.ReportManager;
import com.khoders.resource.utilities.Msg;
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
@Named(value = "stockController")
@SessionScoped
public class StockController implements Serializable
{
 @Inject private ReportManager reportManager;
    @Inject private XtractService xtractService;
    
    private List<StockSummary> viewStockList = new LinkedList<>();

    @PostConstruct
    public void init()
    {
       viewStockReceipt();
    }
    
    public void viewStockReceipt()
    {
       viewStockList = xtractService.extractStockSummary();
    }
    
    public void printStockSummary(){
        viewStockReceipt();
        if(viewStockList.isEmpty()){
            Msg.error("No record to print");
            return;
        }
        ReportManager.reportParams.put("logo", ReportFiles.LOGO);
        reportManager.createReport(viewStockList, ReportFiles.STOCK_SUMMARY, ReportManager.reportParams);
    }

    public List<StockSummary> getViewStockList()
    {
        return viewStockList;
    }
}

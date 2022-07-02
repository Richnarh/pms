/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.entities.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author richa
 */
@Entity
@Table(name = "page_action")
public class PageAction extends RefNo
{
    @JoinColumn(name = "pages", referencedColumnName = "id")
    @ManyToOne
    private AppPage appPage;
    
    @Column(name = "action_code")
    private String actionCode;
    
    @Column(name = "action_name")
    private String actionName;
    
    @Column(name = "action_status")
    private String actionStatus;

    public AppPage getAppPage()
    {
        return appPage;
    }

    public void setAppPage(AppPage appPage)
    {
        this.appPage = appPage;
    }
    
    public String getActionCode()
    {
        return actionCode;
    }

    public void setActionCode(String actionCode)
    {
        this.actionCode = actionCode;
    }

    public String getActionName()
    {
        return actionName;
    }

    public void setActionName(String actionName)
    {
        this.actionName = actionName;
    }

    public String getActionStatus()
    {
        return actionStatus;
    }

    public void setActionStatus(String actionStatus)
    {
        this.actionStatus = actionStatus;
    }
    
}

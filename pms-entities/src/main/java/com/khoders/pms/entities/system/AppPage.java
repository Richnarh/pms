/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.entities.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author richa
 */
@Entity
@Table(name = "app_page")
public class AppPage extends RefNo
{
    @Column(name = "page_code")
    private String pageCode;
    
    @Column(name = "page_name")
    private String pageName;
    
    @Column(name = "page_url")
    private String pageUrl;
    
    @Column(name = "page_status")
    private Boolean pageStatus = false;

    public String getPageName()
    {
        return pageName;
    }

    public void setPageName(String pageName)
    {
        this.pageName = pageName;
    }

    public String getPageUrl()
    {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl)
    {
        this.pageUrl = pageUrl;
    }

    public String getPageCode()
    {
        return pageCode;
    }

    public void setPageCode(String pageCode)
    {
        this.pageCode = pageCode;
    }

    public Boolean getPageStatus()
    {
        return pageStatus;
    }

    public void setPageStatus(Boolean pageStatus)
    {
        this.pageStatus = pageStatus;
    }

    @Override
    public String toString()
    {
        return pageName +" - "+pageCode;
    }
    
}

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
@Table(name = "pages")
public class Page extends RefNo
{
    @Column(name = "page_name")
    private String pageName;
    
    @Column(name = "page_url")
    private String pageUrl;

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

    @Override
    public String toString()
    {
        return pageName +" - "+getRefNo();
    }
    
}

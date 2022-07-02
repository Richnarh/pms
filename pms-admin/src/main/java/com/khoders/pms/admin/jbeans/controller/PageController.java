/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.admin.jbeans.controller;

import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.CollectionList;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.SystemUtils;
import com.khoders.pms.admin.services.CompanyService;
import com.khoders.pms.entities.system.AppPage;
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
@Named(value = "pageController")
@SessionScoped
public class PageController implements Serializable
{
    @Inject private CrudApi crudApi;
    @Inject private CompanyService companyService;
    private List<AppPage> pageList = new LinkedList<>();
    private AppPage page = new AppPage();
    private String optionText;
    
    @PostConstruct
    private void init()
    {
        optionText="Save Changes";
        pageList = companyService.getPageList();
    }
    
    public void savePage()
    {
        try
        {
            page.genCode();
            if(crudApi.save(page) != null)
            {
                pageList = CollectionList.washList(pageList, page);
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
            }
            clearPage();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void editPage(AppPage page)
    {
        this.page = page;
        optionText = "Update";
    }
    
    public void deletePage(AppPage page)
    {
        try
        {
            if(crudApi.delete(page))
            {
                pageList.remove(page);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void clearPage()
    {
        page = new AppPage();
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }
    
    public AppPage getPage()
    {
        return page;
    }

    public void setPage(AppPage page)
    {
        this.page = page;
    }

    public List<AppPage> getPageList()
    {
        return pageList;
    }

    public String getOptionText()
    {
        return optionText;
    }

    public void setOptionText(String optionText)
    {
        this.optionText = optionText;
    }
    
}

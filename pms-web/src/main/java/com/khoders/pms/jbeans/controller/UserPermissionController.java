/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.jbeans.controller;

import com.khoders.pms.entities.system.UserPage;
import com.khoders.pms.entities.system.UserPageAction;
import com.khoders.pms.jbeans.dto.PageInfo;
import com.khoders.pms.listener.AppSession;
import com.khoders.pms.services.UserAccountService;
import com.khoders.resource.jpa.CrudApi;
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
@Named
@SessionScoped
public class UserPermissionController implements Serializable
{
    @Inject private CrudApi crudApi;
    @Inject private AppSession appSession;
    @Inject private UserAccountService userAccountService;
    private List<UserPageAction> userPermissionList = new LinkedList<>();
    private List<UserPage> userPageList = new LinkedList<>();
    private List<PageInfo> pageInfoList = new LinkedList<>();
    
    @PostConstruct
    private void init(){
        userPermissionList = userAccountService.userPermissions(appSession.getCurrentUser());
        userPageList = userAccountService.userPages(appSession.getCurrentUser());
        
        for (UserPage userPage : userPageList)
        {
            PageInfo pageInfo = new PageInfo();
            if(userPage.getAppPage() != null)
            {
              pageInfo.setPageName(userPage.getAppPage().getPageName());
              pageInfo.setUrl(userPage.getAppPage().getPageUrl());  
            }
//            if(userPage.getPageAction() != null)
//            {
//               pageInfo.setActionCode(userPage.getPageAction().getActionCode());
//               pageInfo.setActionName(userPage.getPageAction().getActionName()); 
//            }
            
            pageInfoList.add(pageInfo);
        }
    }

    public List<PageInfo> getPageInfoList()
    {
        return pageInfoList;
    }
    
}

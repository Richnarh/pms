/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.admin.services;

import com.khoders.pms.entities.system.AppPage;
import com.khoders.pms.entities.system.PageAction;
import com.khoders.pms.entities.system.UserAccount;
import com.khoders.pms.entities.system.UserPage;
import com.khoders.pms.entities.system.UserPageAction;
import com.khoders.resource.jpa.CrudApi;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author richa
 */
@Stateless
public class PermissionService
{
    @Inject private CrudApi crudApi;

    public List<PageAction> getPageActionList()
    {
        return crudApi.getEm().createQuery("SELECT e FROM PageAction e ORDER BY e.appPage.reorder ASC", PageAction.class).getResultList();
    }
    public List<PageAction> getPageActionList(AppPage appPage)
    {
        return crudApi.getEm().createQuery("SELECT e FROM PageAction e WHERE e.appPage=:appPage", PageAction.class)
                .setParameter("appPage", appPage)
                .getResultList();
    }
    public List<UserPage> getUserPageList(UserAccount userAccount)
    {
        return crudApi.getEm().createQuery("SELECT e FROM UserPage e WHERE e.userAccount=:userAccount ORDER BY e.appPage.reorder ASC", UserPage.class)
                .setParameter("userAccount", userAccount)
                .getResultList();
    }

    public List<AppPage> getAppPageList()
    {
       return crudApi.getEm().createQuery("SELECT e FROM AppPage e ORDER BY e.reorder ASC", AppPage.class).getResultList();  
    }

    public List<UserPageAction> getUserPageActionList(UserAccount selectedUser)
    {
         return crudApi.getEm().createQuery("SELECT e FROM UserPageAction e WHERE e.userAccount=:selectedUser", UserPageAction.class)
                .setParameter("selectedUser", selectedUser)
                .getResultList();
    }
    
    
    public UserPage userPageExist(UserAccount selectedUser, AppPage appPage){
        return crudApi.getEm().createQuery("SELECT e FROM UserPage e WHERE e.userAccount=:selectedUser AND e.appPage=:appPage", UserPage.class)
                .setParameter("selectedUser", selectedUser)
                .setParameter("appPage", appPage)
                .getResultStream().findFirst().orElse(null);
    }

    public List<UserPageAction> userPageActionExist(PageAction pageAction)
    {
         return crudApi.getEm().createQuery("SELECT e FROM UserPageAction e WHERE e.pageAction=:pageAction", UserPageAction.class)
                .setParameter("pageAction", pageAction)
                .getResultList();
    }
}

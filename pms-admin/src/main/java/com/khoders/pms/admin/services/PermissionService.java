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
        return crudApi.getEm().createQuery("SELECT e FROM PageAction e ", PageAction.class).getResultList();
    }
    public List<PageAction> getPageActionList(AppPage appPage)
    {
        return crudApi.getEm().createQuery("SELECT e FROM PageAction e WHERE e.appPage=:appPage", PageAction.class)
                .setParameter("appPage", appPage)
                .getResultList();
    }
    public List<UserPage> getUserPageList(UserAccount userAccount)
    {
        return crudApi.getEm().createQuery("SELECT e FROM UserPage e WHERE e.userAccount=:userAccount", UserPage.class)
                .setParameter("userAccount", userAccount)
                .getResultList();
    }

    public List<AppPage> getAppPageList()
    {
       return crudApi.getEm().createQuery("SELECT e FROM AppPage e", AppPage.class).getResultList();  
    }
}

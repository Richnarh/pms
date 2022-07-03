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
import com.khoders.pms.admin.services.UserAccountService;
import com.khoders.pms.entities.system.Permission;
import com.khoders.pms.entities.system.UserAccount;
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
@Named(value = "permissionController222")
@SessionScoped
public class PermissionController222 implements Serializable
{
    @Inject private CrudApi crudApi;
    @Inject private UserAccountService userAccountService;
    @Inject private CompanyService companyService;
    
    private Permission permission = new Permission();
    private UserAccount selectedAccount = new UserAccount();
    private List<Permission> permissionList = new LinkedList<>();
    private List<UserAccount> userAccountList = new LinkedList<>();
    
    private String optionText = "Save Changes";
    
    @PostConstruct
    private void init(){
        userAccountList = userAccountService.getAccountList();
    }
    
    public void selectedAccountActn(UserAccount userAccount)
    {
        try
        {
            selectedAccount = userAccount;
            permissionList = companyService.getPermissions(userAccount);
        } catch (Exception e)
        {
           e.printStackTrace();
        }
    }
    
    public void savePermissions()
    {
        try
        {
            permission.setUserAccount(selectedAccount);
           if(crudApi.save(permission) != null)
           {
               permissionList = CollectionList.washList(permissionList, permission);
               FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
           }
           clear();
        } catch (Exception e)
        {
           e.printStackTrace();
        }
    }
    
    public void editPermissions(Permission permission)
    {
        this.permission = permission;
    }
    
    public void deletePermissions(Permission permission)
    {
        try
        {
          if(crudApi.delete(permission))
          {
             permissionList.remove(permission);
             FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
          }
        } catch (Exception e)
        {
          e.printStackTrace();
        }
    }
    
    public void clear(){
        permission = new Permission();
        SystemUtils.resetJsfUI();
    }

    public Permission getPermission()
    {
        return permission;
    }

    public void setPermission(Permission permission)
    {
        this.permission = permission;
    }

    public List<Permission> getPermissionList()
    {
        return permissionList;
    }

    public List<UserAccount> getUserAccountList()
    {
        return userAccountList;
    }

    public UserAccount getSelectedAccount()
    {
        return selectedAccount;
    }

    public void setSelectedAccount(UserAccount selectedAccount)
    {
        this.selectedAccount = selectedAccount;
    }

    public String getOptionText()
    {
        return optionText;
    }
    
}

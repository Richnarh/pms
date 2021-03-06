/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.admin.services;

import com.khoders.resource.jpa.CrudApi;
import com.khoders.pms.entities.system.CompanyBranch;
import com.khoders.pms.entities.system.CompanyProfile;
import com.khoders.pms.entities.system.AppPage;
import com.khoders.pms.entities.system.Permission;
import com.khoders.pms.entities.system.UserAccount;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author richa
 */
@Stateless
public class CompanyService
{
    @Inject private CrudApi crudApi;
    
   public List<CompanyProfile> getCompanyProfileList()
    {
        try
        {
            return crudApi.getEm().createQuery("SELECT e FROM CompanyProfile e", CompanyProfile.class).getResultList();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
   
   public List<Permission> getPermissions(UserAccount userAccount)
    {
        try
        {
            return crudApi.getEm().createQuery("SELECT e FROM Permission e WHERE e.userAccount=?1", Permission.class)
                    .setParameter(1, userAccount)
                    .getResultList();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    public List<CompanyBranch> getCompanyBranchList()
    {
        try
        {
            return crudApi.getEm().createQuery("SELECT e FROM CompanyBranch e ORDER BY e.branchName ASC", CompanyBranch.class).getResultList();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
    public List<UserAccount> getUserAccountList()
    {
        try
        {
            return crudApi.getEm().createQuery("SELECT e FROM UserAccount e", UserAccount.class).getResultList();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

}

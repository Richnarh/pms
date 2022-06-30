/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.config;

import com.khoders.pms.jbeans.UserModel;
import com.khoders.resource.jpa.CrudApi;
import static com.khoders.resource.utilities.SecurityUtil.hashText;
import com.khoders.pms.entities.system.UserAccount;
import com.khoders.pms.services.UserAccountService;
import java.time.LocalDateTime;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 *
 * @author richa
 */
@Singleton
@Startup
public class AppInit
{

    @Inject private CrudApi crudApi;
    @Inject private UserAccountService userAccountService;

    private UserModel userModel = new UserModel();

    @PostConstruct
    public void init()
    {
        System.out.println("******************************************");
        System.out.println("******************************************");

        System.out.println("application started at - " + LocalDateTime.now());
        System.out.println("****  Going to create default user *******");
        String defaultUser = "pms";

        System.out.println("******************************************");
        System.out.println("******************************************");

        try
        {
            userModel.setUsername(defaultUser);
            userModel.setPassword(defaultUser);
            UserAccount userAccount = userAccountService.login(userModel);

            if (userAccount != null)
            {
                return;
            }

            userAccount = new UserAccount();
            userAccount.setUsername(defaultUser);
            userAccount.setPassword(hashText(defaultUser));

            crudApi.save(userAccount);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.config;

import com.khoders.pms.entities.Customer;
import com.khoders.pms.jbeans.UserModel;
import com.khoders.resource.jpa.CrudApi;
import static com.khoders.resource.utilities.SecurityUtil.hashText;
import com.khoders.pms.entities.system.UserAccount;
import com.khoders.pms.enums.CustomerType;
import com.khoders.pms.services.UserAccountService;
import com.khoders.resource.enums.ClientType;
import java.time.LocalDateTime;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

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
        System.out.println("****  Going to create default data *******");

        System.out.println("******************************************");
        System.out.println("******************************************");

        createUser();
        initCustomer();
    }
    
    public void createUser(){
        try
        {
            String defaultUser = "pms";
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
    
    public void initCustomer()
    {
        Customer c = defaultCustomer(CustomerType.WALKIN_CUSTOMER);
        if(c != null)return;
        
        Customer customer = new Customer();
        customer.setClientType(ClientType.CUSTOMER);
        customer.setCustomerName(CustomerType.WALKIN_CUSTOMER.getLabel());
        customer.setPhone("");
        
        crudApi.save(customer);

        Customer back = defaultCustomer(CustomerType.BACK_LOG_SUPPLIER);
        if(back != null)return;
        
        Customer cc = new Customer();
        cc.setClientType(ClientType.CUSTOMER);
        cc.setCustomerName(CustomerType.BACK_LOG_SUPPLIER.getLabel());
        cc.setPhone("");
        
        crudApi.save(cc);
    }
    
    public Customer defaultCustomer(CustomerType customerType)
    {
        String qryString = "SELECT e FROM Customer e WHERE e.customerName=?1";
        TypedQuery<Customer> typedQuery = crudApi.getEm().createQuery(qryString, Customer.class)
                .setParameter(1, customerType.getLabel());
        return typedQuery.getResultStream().findFirst().orElse(null);
    }
}

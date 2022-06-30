/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.config;

import com.khoders.resource.enums.ClientType;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.pms.entities.Customer;
import com.khoders.pms.enums.CustomerType;
import java.time.LocalDateTime;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

/**
 *
 * @author Richard
 */
@Singleton
@Startup
public class InitSystemData
{
    @Inject private CrudApi crudApi;
    
    @PostConstruct
    public void init(){
        System.out.println("******************************************");
        System.out.println("******************************************");

        System.out.println("application started at - " + LocalDateTime.now());
        System.out.println("**** Going to initiate system defaults *******");

        System.out.println("******************************************");
        System.out.println("******************************************");
        
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

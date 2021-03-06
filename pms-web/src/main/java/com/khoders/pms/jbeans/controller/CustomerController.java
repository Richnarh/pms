/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.jbeans.controller;

import com.khoders.resource.jpa.CrudApi;
import com.khoders.resource.utilities.CollectionList;
import com.khoders.resource.utilities.FormView;
import com.khoders.resource.utilities.Msg;
import com.khoders.resource.utilities.SystemUtils;
import com.khoders.pms.entities.Customer;
import com.khoders.pms.services.InventoryService;
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
 * @author khoders
 */
@Named(value = "customerController")
@SessionScoped
public class CustomerController implements Serializable
{
    @Inject
    private CrudApi crudApi;
    @Inject
    private InventoryService inventoryService;

    private Customer customer = new Customer();
    private List<Customer> customerList = new LinkedList<>();

    private FormView pageView = FormView.listForm();
    private String optionText;

    @PostConstruct
    public void init()
    {
        clearCustomer();
        customerList = inventoryService.getCustomerList();
    }

    public void initCLient()
    {
        clearCustomer();
        pageView.restToCreateView();
    }

    public void saveCustomer()
    {
        if (!optionText.equals("Update"))
        {
            if (customer != null)
            {
                Customer object = inventoryService.customertExist(customer.getPhone());

                if (object != null)
                {
                   Msg.error("The customer with phone number: " + customer.getPhone() + " already exist");
                   return;
                }
            }
        }
        try
        {
            customer.genCode();
            if (crudApi.save(customer) != null)
            {
                customerList = CollectionList.washList(customerList, customer);

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
            } else
            {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.FAILED_MESSAGE, null));
            }
            clearCustomer();
            closePage();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void deleteCustomer(Customer customer)
    {
        try
        {
            if (crudApi.delete(customer))
            {
                customerList.remove(customer);

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, Msg.SUCCESS_MESSAGE, null));
            } else
            {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, Msg.FAILED_MESSAGE, null));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void editCustomer(Customer customer)
    {
        pageView.restToCreateView();
        this.customer = customer;
        optionText = "Update";
    }

    public void clearCustomer()
    {
        customer = new Customer();
        optionText = "Save Changes";
        SystemUtils.resetJsfUI();
    }

    public void closePage()
    {
        customer = new Customer();
        optionText = "Save Changes";
        pageView.restToListView();
    }

    public List<Customer> getCustomerList()
    {
        return customerList;
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer(Customer bird)
    {
        this.customer = bird;
    }

    public String getOptionText()
    {
        return optionText;
    }

    public void setOptionText(String optionText)
    {
        this.optionText = optionText;
    }

    public FormView getPageView()
    {
        return pageView;
    }

    public void setPageView(FormView pageView)
    {
        this.pageView = pageView;
    }

}

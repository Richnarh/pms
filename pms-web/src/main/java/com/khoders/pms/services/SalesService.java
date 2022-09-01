/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.khoders.pms.services;

import com.khoders.pms.entities.Customer;
import com.khoders.resource.jpa.CrudApi;
import com.khoders.pms.entities.Product;
import com.khoders.pms.entities.ProductPackage;
import com.khoders.pms.entities.Sales;
import com.khoders.pms.entities.SalesTax;
import com.khoders.pms.entities.Tax;
import com.khoders.pms.entities.UnitMeasurement;
import com.khoders.pms.enums.CustomerType;
import com.khoders.pms.listener.AppSession;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

/**
 *
 * @author richa
 */
@Stateless
public class SalesService
{
    @Inject private CrudApi crudApi;
    @Inject private AppSession appSession;

    public List<ProductPackage> queryPackagePrice(Product product)
    {
        try
        {
            return crudApi.getEm().createQuery("SELECT e FROM ProductPackage e WHERE e.product =?1", ProductPackage.class)
                    .setParameter(1, product)
                    .getResultList();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    
    
    public double queryPackagePrice(UnitMeasurement unitMeasurement, Product product)
    {
        try
        {
            return crudApi.getEm().createQuery("SELECT e FROM ProductPackage e WHERE e.unitMeasurement =?1 AND e.product=?2", ProductPackage.class)
                    .setParameter(1, unitMeasurement)
                    .setParameter(2, product)
                    .getSingleResult().getPackagePrice();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0.0;
    }
    public List<SalesTax> getSalesTaxList(Sales sales)
    {
        try
        {
          String query = "SELECT e FROM SalesTax e WHERE e.sales=?1 AND e.userAccount=?2 ORDER BY e.reOrder ASC";
        
        TypedQuery<SalesTax> typedQuery = crudApi.getEm().createQuery(query, SalesTax.class)
                                .setParameter(1, sales)
                                .setParameter(2, appSession.getCurrentUser());
                return typedQuery.getResultList();      
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    public List<Tax> getTaxList()
    {
        try
        {
            return crudApi.getEm().createQuery("SELECT e FROM Tax e ORDER BY e.reOrder ASC", Tax.class).getResultList();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
    
    public List<Sales> getUnapprovaedSales()
    {
        try
        {
            return crudApi.getEm().createQuery("SELECT e FROM Sales e WHERE e.approval=:approval ORDER BY e.valueDate DESC", Sales.class)
                    .setParameter("approval", false)
                    .getResultList();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
    public Customer walkinCustomer()
    {
        String qryString = "SELECT e FROM Customer e WHERE e.customerName=?1";
        TypedQuery<Customer> typedQuery = crudApi.getEm().createQuery(qryString, Customer.class)
                .setParameter(1, CustomerType.WALK_IN_CUSTOMER.getLabel());
        return typedQuery.getResultStream().findFirst().orElse(null);
    }
}

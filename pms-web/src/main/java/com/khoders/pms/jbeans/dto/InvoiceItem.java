/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.jbeans.dto;

/**
 *
 * @author richa
 */
public class InvoiceItem
{
    private String product;
    private double quantity;
    private double unitPrice;

    public String getProduct()
    {
        return product;
    }

    public void setProduct(String product)
    {
        this.product = product;
    }

    public double getQuantity()
    {
        return quantity;
    }

    public void setQuantity(double quantity)
    {
        this.quantity = quantity;
    }

    public double getUnitPrice()
    {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice)
    {
        this.unitPrice = unitPrice;
    }
}

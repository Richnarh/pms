/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.jbeans.dto;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author richard
 * 23/08/2022, 12:43AM
 */
// This goes into the package price table
public class PriceUploadDetails
{
    private String productName;
    private String productType;
    private String packaging;
    private String unitsMeasurement;
    private double sellingPrice;
    private double unitsInPackage;
    private double purchasedPrice;
    private double qtyPurchased;
    
    private List<ProductDetails> productDetailList = new LinkedList<>();

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getProductType()
    {
        return productType;
    }

    public void setProductType(String productType)
    {
        this.productType = productType;
    }

    public String getUnitsMeasurement()
    {
        return unitsMeasurement;
    }

    public void setUnitsMeasurement(String unitsMeasurement)
    {
        this.unitsMeasurement = unitsMeasurement;
    }

    public double getSellingPrice()
    {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice)
    {
        this.sellingPrice = sellingPrice;
    }

    public double getUnitsInPackage()
    {
        return unitsInPackage;
    }

    public void setUnitsInPackage(double unitsInPackage)
    {
        this.unitsInPackage = unitsInPackage;
    }

    public String getPackaging()
    {
        return packaging;
    }

    public void setPackaging(String packaging)
    {
        this.packaging = packaging;
    }

    public double getPurchasedPrice()
    {
        return purchasedPrice;
    }

    public void setPurchasedPrice(double purchasedPrice)
    {
        this.purchasedPrice = purchasedPrice;
    }

    public double getQtyPurchased()
    {
        return qtyPurchased;
    }

    public void setQtyPurchased(double qtyPurchased)
    {
        this.qtyPurchased = qtyPurchased;
    }
    
}

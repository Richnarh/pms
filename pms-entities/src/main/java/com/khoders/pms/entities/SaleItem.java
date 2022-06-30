/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.entities;

import com.khoders.pms.entities.system.UserAccountRecord;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author khoders
 */
@Entity
@Table(name = "sale_item")
public class SaleItem extends UserAccountRecord implements Serializable{
    
    @Column(name = "quantity")
    private double quantity=1;

    @Column(name = "unit_price")
    private double unitPrice;

    @Column(name = "profit")
    private double profit;
    
    @Column(name = "loss")
    private double loss;
    
    @Column(name = "sub_total")
    private double subTotal;

    @JoinColumn(name = "customer")
    @ManyToOne
    private Customer customer;
    
    @JoinColumn(name = "sales", referencedColumnName = "id")
    @ManyToOne
    private Sales sales;
    
    @JoinColumn(name = "stock_recept_item")
    @ManyToOne
    private StockReceiptItem stockReceiptItem;
    
    @JoinColumn(name = "product_package")
    @ManyToOne
    private ProductPackage productPackage;

    @Column(name = "description")
    private String description;

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
    
    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public double getSubTotal()
    {
        return subTotal;
    }

    public void setSubTotal(double subTotal)
    {
        this.subTotal = subTotal;
    }
    
    public StockReceiptItem getStockReceiptItem()
    {
        return stockReceiptItem;
    }

    public void setStockReceiptItem(StockReceiptItem stockReceiptItem)
    {
        this.stockReceiptItem = stockReceiptItem;
    }
    
    public Sales getSales()
    {
        return sales;
    }

    public void setSales(Sales sales)
    {
        this.sales = sales;
    }

    public ProductPackage getProductPackage()
    {
        return productPackage;
    }

    public void setProductPackage(ProductPackage productPackage)
    {
        this.productPackage = productPackage;
    }

    public double getLoss()
    {
        return loss;
    }

    public void setLoss(double loss)
    {
        this.loss = loss;
    }
    
}

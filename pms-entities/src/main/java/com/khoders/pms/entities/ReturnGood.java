/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.entities;

import com.khoders.pms.entities.system.UserAccountRecord;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author richa
 */
@Entity
@Table(name = "return_good")
public class ReturnGood extends UserAccountRecord implements Serializable
{
   @Column(name = "quantity")
   private int quantity;
   
   @Column(name = "updated_stock_receipt")
   private boolean updatedStockReceipt;
   
   @Column(name = "return_date")
   private LocalDate returnDate;
   
   @JoinColumn(name = "customer", referencedColumnName = "id")
   @ManyToOne
   private Customer customer;
   
   @Column(name = "description")
   @Lob
   private String description;

    public LocalDate getReturnDate()
    {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate)
    {
        this.returnDate = returnDate;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
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

    public boolean isUpdatedStockReceipt()
    {
        return updatedStockReceipt;
    }

    public void setUpdatedStockReceipt(boolean updatedStockReceipt)
    {
        this.updatedStockReceipt = updatedStockReceipt;
    }
    
}

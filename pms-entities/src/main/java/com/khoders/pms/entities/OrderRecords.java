package com.khoders.pms.entities;

import com.khoders.resource.utilities.SystemUtils;
import com.khoders.pms.entities.system.RefNo;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author richard
 */
@MappedSuperclass
public class OrderRecords extends RefNo
{
    @Column(name = "total_amount")
    private double totalAmount;
    
    @Column(name = "expiry_date")
    private double expiryDate;

    @Column(name = "batch_number")
    private String batchNumber;
    
    @Column(name = "description")
    @Lob
    private String description;
    
    @JoinColumn(name = "customer", referencedColumnName = "id")
    @ManyToOne
    private Customer customer;

    public double getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public String getBatchNumber()
    {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber)
    {
        this.batchNumber = batchNumber;
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

    public double getExpiryDate()
    {
        return expiryDate;
    }

    public void setExpiryDate(double expiryDate)
    {
        this.expiryDate = expiryDate;
    }
    
   public void genBatchNumber()
    {
        if (getBatchNumber() != null)
        {
            setBatchNumber(getBatchNumber());
        } else
        {
            setBatchNumber(SystemUtils.generateCode());
        }
    }  
}

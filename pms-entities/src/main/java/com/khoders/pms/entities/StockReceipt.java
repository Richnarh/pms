/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.entities;

import com.khoders.pms.entities.system.UserAccount;
import com.khoders.pms.entities.system.UserAccountRecord;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author richa
 */
@Entity
@Table(name = "stock_receipt")
public class StockReceipt extends UserAccountRecord implements Serializable{
  
    @Column(name = "receipt_no")
    private String receiptNo;
    
    @JoinColumn(name = "purchase_order", referencedColumnName = "id")
    @ManyToOne
    private PurchaseOrder purchaseOrder;
    
    @JoinColumn(name = "received_by", referencedColumnName = "id")
    @ManyToOne
    private UserAccount receivedBy;
    
    @JoinColumn(name = "warehouse", referencedColumnName = "id")
    @ManyToOne
    private Warehouse warehouse;
    
    @Column(name = "batch_no")
    private String batchNo;
    
    @Column(name = "tract_expiry")
    private boolean trackExpiry;
    
    @Column(name = "charges")
    private double charges;
    
    @Column(name = "total_amount")
    private double totalAmount;
    
    @Column(name = "description")
    private String description;
    
    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public UserAccount getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(UserAccount receivedBy) {
        this.receivedBy = receivedBy;
    }

    public double getCharges() {
        return charges;
    }

    public void setCharges(double charges) {
        this.charges = charges;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public boolean isTrackExpiry()
    {
        return trackExpiry;
    }

    public void setTrackExpiry(boolean trackExpiry)
    {
        this.trackExpiry = trackExpiry;
    }

    public Warehouse getWarehouse()
    {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse)
    {
        this.warehouse = warehouse;
    }
    
}

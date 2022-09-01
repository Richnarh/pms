/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.entities;

import com.khoders.resource.enums.PaymentMethod;
import com.khoders.resource.utilities.SystemUtils;
import com.khoders.pms.entities.system.UserAccountRecord;
import com.khoders.pms.enums.InvoiceType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author khoders
 */
@Entity
@Table(name = "sales")
public class Sales extends UserAccountRecord{
    
    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;
    
    @Column(name = "expiry_date")
    private LocalDate expiryDate;
    
    @JoinColumn(name = "customer", referencedColumnName = "id")
    @ManyToOne
    private Customer customer;
    
    @Column(name = "receipt_number")
    private String receiptNumber;
    
    @Column(name = "total_amount")
    private double totalAmount;
    
    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    
    @Column(name = "invoice_type")
    @Enumerated(EnumType.STRING)
    private InvoiceType invoiceType;
    
    @Column(name = "is_proforma_invoice")
    private boolean isProformaInvoice = false;
    
    @Column(name = "approval")
    private boolean approval = false;
    
    @Column(name = "profit")
    private double profit;
    
    @Column(name = "loss")
    private double loss;
    
    @Column(name = "qty_purchased")
    private double qtyPurchased;
    
    public LocalDateTime getPurchaseDate()
    {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate)
    {
        this.purchaseDate = purchaseDate;
    }
   
    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }
    
    public double getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public double getProfit()
    {
        return profit;
    }

    public void setProfit(double profit)
    {
        this.profit = profit;
    }

    public double getLoss()
    {
        return loss;
    }

    public void setLoss(double loss)
    {
        this.loss = loss;
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }

    public boolean isIsProformaInvoice()
    {
        return isProformaInvoice;
    }

    public void setIsProformaInvoice(boolean isProformaInvoice)
    {
        this.isProformaInvoice = isProformaInvoice;
    }

    public PaymentMethod getPaymentMethod()
    {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod)
    {
        this.paymentMethod = paymentMethod;
    }

    public LocalDate getExpiryDate()
    {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate)
    {
        this.expiryDate = expiryDate;
    }

    public InvoiceType getInvoiceType()
    {
        return invoiceType;
    }

    public void setInvoiceType(InvoiceType invoiceType)
    {
        this.invoiceType = invoiceType;
    }

    public boolean isApproval()
    {
        return approval;
    }

    public void setApproval(boolean approval)
    {
        this.approval = approval;
    }

    public double getQtyPurchased()
    {
        return qtyPurchased;
    }

    public void setQtyPurchased(double qtyPurchased)
    {
        this.qtyPurchased = qtyPurchased;
    }
    
    public void genReceipt()
    {
        if (getReceiptNumber() != null)
        {
            setReceiptNumber(getReceiptNumber());
        } else
        {
            setReceiptNumber(SystemUtils.generateRefNo());
        }
    } 
}

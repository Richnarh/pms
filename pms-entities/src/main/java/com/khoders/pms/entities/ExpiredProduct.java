/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.entities;

import com.khoders.pms.entities.system.UserAccountRecord;
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
@Table(name = "expired_product")
public class ExpiredProduct extends UserAccountRecord
{
    @JoinColumn(name = "stock_recept_item", referencedColumnName = "id")
    @ManyToOne
    private StockReceiptItem stockReceiptItem;
    
    @Column(name = "expired_date")
    private LocalDate expiryDate;
        
    @Column(name = "description")
    @Lob
    private String description;

    public StockReceiptItem getStockReceiptItem()
    {
        return stockReceiptItem;
    }

    public void setStockReceiptItem(StockReceiptItem stockReceiptItem)
    {
        this.stockReceiptItem = stockReceiptItem;
    }

    public LocalDate getExpiryDate()
    {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate)
    {
        this.expiryDate = expiryDate;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
    
}

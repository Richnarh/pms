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
@Table(name = "stock_recept_item")
public class StockReceiptItem extends UserAccountRecord implements Serializable{
    
    @JoinColumn(name = "stock_receipt", referencedColumnName = "id")
    @ManyToOne
    private StockReceipt stockReceipt;
    
    @JoinColumn(name = "product", referencedColumnName = "id")
    @ManyToOne
    private Product product;
    
    @JoinColumn(name = "purchase_order_item", referencedColumnName = "id")
    @ManyToOne
    private PurchaseOrderItem purchaseOrderItem;
    
    @JoinColumn(name = "product_package", referencedColumnName = "id")
    @ManyToOne
    private ProductPackage productPackage;
    
    @Column(name = "pkg_quantity")
    private double pkgQuantity;
    
    @Column(name = "pkg_factor")
    private double pkgFactor;
    
    @Column(name = "qty_sold")
    private double qtySold;
    
    @Column(name = "qty_left")
    private double qtyLeft;
    
    @Column(name = "cost_price")
    private double costPrice;
    
    @Column(name = "price_margin")
    private double priceMargin;
    
    @Column(name = "expiry_date")
    private LocalDate expiryDate;
    
    @Column(name = "sub_total")
    private double subTotal;
    
    @Column(name = "description")
    @Lob
    private String description;
    
    @Column(name = "expired_date_set")
    private boolean expiredDateSet = false;
    
    public StockReceipt getStockReceipt() {
        return stockReceipt;
    }

    public void setStockReceipt(StockReceipt stockReceipt) {
        this.stockReceipt = stockReceipt;
    }

    public PurchaseOrderItem getPurchaseOrderItem() {
        return purchaseOrderItem;
    }

    public void setPurchaseOrderItem(PurchaseOrderItem purchaseOrderItem) {
        this.purchaseOrderItem = purchaseOrderItem;
    }

    public double getPkgQuantity()
    {
        return pkgQuantity;
    }

    public double getQtySold()
    {
        return qtySold;
    }

    public void setQtySold(double qtySold)
    {
        this.qtySold = qtySold;
    }

    public double getQtyLeft()
    {
        return qtyLeft;
    }

    public void setQtyLeft(double qtyLeft)
    {
        this.qtyLeft = qtyLeft;
    }

    public void setPkgQuantity(double pkgQuantity)
    {
        this.pkgQuantity = pkgQuantity;
    }

    public double getPkgFactor() {
        return pkgFactor;
    }

    public void setPkgFactor(double pkgFactor) {
        this.pkgFactor = pkgFactor;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getSubTotal()
    {
        return subTotal;
    }

    public void setSubTotal(double subTotal)
    {
        this.subTotal = subTotal;
    }

    public double getPriceMargin()
    {
        return priceMargin;
    }

    public void setPriceMargin(double priceMargin)
    {
        this.priceMargin = priceMargin;
    }

    public ProductPackage getProductPackage() {
        return productPackage;
    }

    public void setProductPackage(ProductPackage productPackage) {
        this.productPackage = productPackage;
    }

    public boolean isExpiredDateSet()
    {
        return expiredDateSet;
    }

    public void setExpiredDateSet(boolean expiredDateSet)
    {
        this.expiredDateSet = expiredDateSet;
    }

    @Override
    public String toString()
    {
        return product+"";
    }
    
    
}

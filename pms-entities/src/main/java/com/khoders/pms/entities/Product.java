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
 * @author richa
 */
@Entity
@Table(name = "product")
public class Product extends UserAccountRecord implements Serializable
{
    @Column(name = "product_name")
    private String productName;
    
    @JoinColumn(name = "location", referencedColumnName = "id")
    @ManyToOne
    private Location location;
    
    @JoinColumn(name = "manufacturer", referencedColumnName = "id")
    @ManyToOne
    private Manufacturer manufacturer;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "selling_price")
    private double sellingPrice;
    
    @Column(name = "reorder_level")
    private int reorderLevel;

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    
    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getReorderLevel()
    {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel)
    {
        this.reorderLevel = reorderLevel;
    }

    @Override
    public String toString()
    {
        return productName;
    } 
}

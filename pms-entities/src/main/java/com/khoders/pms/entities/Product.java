package com.khoders.pms.entities;

import com.khoders.pms.entities.system.UserAccountRecord;
import com.khoders.pms.enums.Frequency;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
    
    @JoinColumn(name = "product_type", referencedColumnName = "id")
    @ManyToOne
    private ProductType productType; 
    
    @JoinColumn(name = "manufacturer", referencedColumnName = "id")
    @ManyToOne
    private Manufacturer manufacturer;
    
    @JoinColumn(name = "category", referencedColumnName = "id")
    @ManyToOne
    private Category category;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "frequency")
    @Enumerated(EnumType.STRING)
    private Frequency frequency;
    
    @Column(name = "route")
    private String route;
    
    @Column(name = "selling_price")
    private double sellingPrice;
    
    @Column(name = "reorder_level")
    private int reorderLevel;
    
    @JoinColumn(name = "potency", referencedColumnName = "id")
    @ManyToOne
    private Potency potency;
    
    @JoinColumn(name = "packaging", referencedColumnName = "id")
    @ManyToOne
    private Packaging packaging;
    
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

    public ProductType getProductType()
    {
        return productType;
    }

    public void setProductType(ProductType productType)
    {
        this.productType = productType;
    }

    public Category getCategory()
    {
        return category;
    }

    public void setCategory(Category category)
    {
        this.category = category;
    }

    public Frequency getFrequency()
    {
        return frequency;
    }

    public void setFrequency(Frequency frequency)
    {
        this.frequency = frequency;
    }
    
    public String getRoute()
    {
        return route;
    }

    public void setRoute(String route)
    {
        this.route = route;
    }

    public Potency getPotency()
    {
        return potency;
    }

    public void setPotency(Potency potency)
    {
        this.potency = potency;
    }

    public Packaging getPackaging()
    {
        return packaging;
    }

    public void setPackaging(Packaging packaging)
    {
        this.packaging = packaging;
    }

    @Override
    public String toString()
    {
        return productName +" "+potency +" "+packaging;
    } 
}

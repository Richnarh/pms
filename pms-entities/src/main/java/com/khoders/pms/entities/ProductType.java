package com.khoders.pms.entities;

import com.khoders.pms.entities.system.UserAccountRecord;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author richa
 */
@Entity
@Table(name = "product_type")
public class ProductType extends UserAccountRecord implements Serializable
{
    @Column(name = "product_type_name")
    private String productTypeName; 

    public String getProductTypeName()
    {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName)
    {
        this.productTypeName = productTypeName;
    }

    @Override
    public String toString()
    {
        return productTypeName;
    }
    
}

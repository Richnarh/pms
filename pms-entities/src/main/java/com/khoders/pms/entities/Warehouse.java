/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.entities;

import com.khoders.pms.entities.system.UserAccountRecord;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author richard
 */
@Entity
@Table(name = "warehouse")
public class Warehouse extends UserAccountRecord
{
    @Column(name = "warehouse_name")
    private String warehouseName;
   
    @Column(name = "address")
    private String address;

    public String getWarehouseName()
    {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName)
    {
        this.warehouseName = warehouseName;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    @Override
    public String toString()
    {
        return warehouseName;
    }
    
}

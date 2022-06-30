/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.entities;

import com.khoders.pms.entities.system.RefNo;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author richa
 */
@Entity
@Table(name = "potency")
public class Potency extends RefNo{
    @Column(name = "potency_name")
    private String potencyName;

    public String getPotencyName() {
        return potencyName;
    }

    public void setPotencyName(String potencyName) {
        this.potencyName = potencyName;
    }
    @Override
    public String toString() {
        return potencyName;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.entities.system;

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
@Table(name = "permission")
public class Permission extends UserAccountRecord implements Serializable{

    @Column(name = "grant_access")
    private boolean grantAccess;
    
    @JoinColumn(name = "pages", referencedColumnName = "id")
    @ManyToOne
    private Page page;

    public boolean isGrantAccess() {
        return grantAccess;
    }

    public void setGrantAccess(boolean grantAccess) {
        this.grantAccess = grantAccess;
    }

    public Page getPage()
    {
        return page;
    }

    public void setPage(Page page)
    {
        this.page = page;
    }
    
}

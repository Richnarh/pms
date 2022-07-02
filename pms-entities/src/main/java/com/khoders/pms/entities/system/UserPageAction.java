/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.entities.system;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author richa
 */
@Entity
@Table(name = "user_page_action")
public class UserPageAction extends RefNo
{
    @JoinColumn(name = "user_page", referencedColumnName = "id")
    @ManyToOne
    private UserPage userPage;
    
    @JoinColumn(name = "user_page", referencedColumnName = "id")
    @ManyToOne
    private PageAction pageAction;

    public UserPage getUserPage()
    {
        return userPage;
    }

    public void setUserPage(UserPage userPage)
    {
        this.userPage = userPage;
    }

    public PageAction getPageAction()
    {
        return pageAction;
    }

    public void setPageAction(PageAction pageAction)
    {
        this.pageAction = pageAction;
    }
    
}

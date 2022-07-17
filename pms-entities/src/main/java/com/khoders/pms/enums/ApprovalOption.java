/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.enums;

import com.khoders.resource.utilities.MsgResolver;

/**
 *
 * @author richa
 */
public enum ApprovalOption implements MsgResolver
{
   ALL("ALL","All"),
   APPROVED("APPROVED","Approved"),
   UNAPPROVED("UNAPPROVED", "Unapproved");
   
   private final String code;
   private final String label;
   
   ApprovalOption(String code, String label){
       this.code=code;
       this.label=label;
   }

    @Override
    public String getCode()
    {
        return code;
    }

    @Override
    public String getLabel()
    {
        return label;
    }

    @Override
    public String toString()
    {
       return label;
    }
   
}

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
public enum Frequency implements MsgResolver
{
   DAILY_1X("DAILY_1X", "1x Daily"),
   DAILY_2X("DAILY_2X", "2x Daily"),
   DAILY_3X("DAILY_3X", "3x Daily"),
   DAILY_4X("DAILY_4X", "4x Daily"),
   DAILY_5X("DAILY_5X", "5x Daily");

   private final String code;
   private final String label;
   private Frequency(String code, String label){
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

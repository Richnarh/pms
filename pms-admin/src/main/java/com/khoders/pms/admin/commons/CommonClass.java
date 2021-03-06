/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.pms.admin.commons;

import com.khoders.pms.enums.ApprovalOption;
import com.khoders.resource.enums.AccessLevel;
import com.khoders.resource.enums.Status;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author richa
 */
@Named(value = "commonClass")
@SessionScoped
public class CommonClass implements Serializable
{
    public List<Status> getStatusList(){
        return Arrays.asList(Status.values());
    }
    public List<AccessLevel> getAccessLevelList(){
        return Arrays.asList(AccessLevel.values());
    }
    public List<ApprovalOption> getApprovalOptionList(){
        return Arrays.asList(ApprovalOption.values());
    }
}

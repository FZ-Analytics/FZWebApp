/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author dwi.rangga
 */
public class PreRouteJobGetStatusDO {
    @SerializedName("RunId")
    @Expose
    public String RunId;
    
    @SerializedName("Customer_ID")
    @Expose
    public String Customer_ID;
    
    @SerializedName("DO_Number")
    @Expose
    public String DO_Number;
}

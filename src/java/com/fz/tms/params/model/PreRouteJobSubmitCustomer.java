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
public class PreRouteJobSubmitCustomer {
    @SerializedName("RunId")
    @Expose
    public String RunId;
    
    @SerializedName("Customer_ID")
    @Expose
    public String Customer_ID;
    
    @SerializedName("DO_Number")
    @Expose
    public String DO_Number;
    
    @SerializedName("lng")
    @Expose
    public String lng;
    
    @SerializedName("lat")
    @Expose
    public String lat;
    
    @SerializedName("ExcInc")
    @Expose
    public String ExcInc;
    
    @SerializedName("Customer_priority")
    @Expose
    public String Customer_priority;
     
    @SerializedName("Service_time")
    @Expose
    public String Service_time;      
      
    @SerializedName("deliv_start")
    @Expose
    public String deliv_start;
    
    @SerializedName("deliv_end")
    @Expose
    public String deliv_end;
    
    @SerializedName("vehicle_type_list")
    @Expose
    public String vehicle_type_list;
      
}

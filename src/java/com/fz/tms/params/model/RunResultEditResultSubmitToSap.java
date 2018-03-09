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
 * @author Administrator
 */
public class RunResultEditResultSubmitToSap {
    @SerializedName("runId")
    @Expose
    public String runId;
    
    @SerializedName("oriRunId")
    @Expose
    public String oriRunId;
    
    @SerializedName("vehicle_no")
    @Expose
    public String vehicle_no;
    
}

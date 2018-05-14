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
 * @author rifki.nurfaiz
 */
public class PopUpEditCustBfror {
    @SerializedName("runId")
    @Expose
    public String runId;
    
    @SerializedName("data")
    @Expose
    public String data;
    
    @SerializedName("excInc")
    @Expose
    public String excInc;
}

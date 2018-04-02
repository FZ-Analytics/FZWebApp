/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author dwi.rangga
 */
public class RunIdModel {
    @SerializedName("runId")
    @Expose
    private String runId;

    
    /**
     * @return the runId
     */
    public String getRunId() {
        return runId;
    }

    /**
     * @param runId the runId to set
     */
    public void setRunId(String runId) {
        this.runId = runId;
    }   
}

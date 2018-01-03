/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.model;

import java.util.List;

/**
 *
 * @author dwi.rangga
 */
public class returnTmProgress {
    public boolean success = true;
    public String msg = "";
    private List<tmProgress> tm; 

    /**
     * @return the tm
     */
    public List<tmProgress> getTm() {
        return tm;
    }

    /**
     * @param tm the tm to set
     */
    public void setTm(List<tmProgress> tm) {
        this.tm = tm;
    }
    
}

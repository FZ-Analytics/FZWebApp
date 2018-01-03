/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.model;

import com.fz.router.DeliveryAgent;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author dwi.rangga
 */
public class vehicle_attr {
    private Vehicle ve;
    private DeliveryAgent da;

    /**
     * @return the ve
     */
    public Vehicle getVe() {
        return ve;
    }

    /**
     * @param ve the ve to set
     */
    public void setVe(Vehicle ve) {
        this.ve = ve;
    }

    /**
     * @return the da
     */
    public DeliveryAgent getDa() {
        return da;
    }

    /**
     * @param da the da to set
     */
    public void setDa(DeliveryAgent da) {
        this.da = da;
    }
    
    
}

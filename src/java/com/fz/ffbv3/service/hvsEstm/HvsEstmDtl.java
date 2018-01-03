/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fz.ffbv3.service.hvsEstm;

/**
 *
 */
public class HvsEstmDtl {
    public String hveEstmDtlID = "";
    public String divID = "";
    public String block = "";
    public double size1 = 0;
    public String taskType = "ESTM";
    public Location loc1 = new Location();
    public Location loc2 = new Location();
    public HvsEstm parent;

    public String getBlock() {
        return block;
    }

    public String getSizeString() {
        return String.valueOf(size1);
    }

    public String getTaskType() {
        return taskType;
    }

    public Location getCenteroid() {
        
        double x1 = Double.parseDouble(loc1.x);
        double y1 = Double.parseDouble(loc1.y);
        double x2 = Double.parseDouble(loc2.x);
        double y2 = Double.parseDouble(loc2.y);
        
        double cx = x1 + ((x2-x1)/2);
        double cy = y1 + ((y2-y1)/2);
        Location c = new Location();
        c.x = String.valueOf(cx);
        c.y = String.valueOf(cy);
        return c;
    }
}

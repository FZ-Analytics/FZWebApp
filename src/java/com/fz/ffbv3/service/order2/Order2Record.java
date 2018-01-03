/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fz.ffbv3.service.order2;

/**
 *
 */
public class Order2Record {
    public String hvsDate = "";
    public String vehicleName = "";
    public String jobSeq = "";
    public String divID = "";
    public String block1 = "";
    public String block2 = "";
    public String readyTime = "";
    public double size = 0;
    public String jobStatus = "";
    public String planStart = "";
    public String planEnd = "";
    public String actualStart = "";
    public String actualEnd = "";
    public String jobID = "";
    public String runID = "";
    public String remark = "";
    public String vehicleID = "";
    public String vehicleRemark = "";
    public String Task1ReasonState = "";
    public String Task2ReasonState = "";
    public String createSource = "";
    public String Task2ReasonName = "";
    public String Task2ReasonID = "";
    public String isLastOrder = "";
    public String isLast2Order = "";
    public String assignedDate = "";
    public String takenDate = "";
    public String doneDate = "";
    public String createDate = "";
    public String dirLoc = "";
    public String estmFfb = "";
    
    public String getBlocks() {
        String r = "";
        if ((block1 != null) && (block2 != null)){
            r = block1 + ";" + block2;
        }
        else if (block1 != null){
            r = block1;
        }
        else if (block2 != null){
            r = block2;
        }
        else {
            r = "-";
        }
        if (r.endsWith(";")) r = r.substring(0,r.length()-1);
        r = r.replaceAll(";", ", ");
        return r;
    }
}

package com.fz.tms.service.run;

import com.fz.util.FZUtil;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 */
public class RouteJob {
    
    public String no = "";
    public String siteID = "";
    public String custID = "";
    public String DONum = "";
    public String itemNO = "";
    public String vehicleCode = "";
    public String activity = "";
    public int routeNb = 0;
    public int jobNb = 0;
    public String arrive = "";
    public String depart = "";
    public String runID = "";
    public String branch = "";
    public String shift = "";
    public String lon = "";
    public String lat = "";
    
    public RouteJob prevJob = null;
    public String mapLink = "";
    public String name1;
    public String distrChn;
    public String street;
    public String district;
    public String zip;
    public String city;
    public String custPriority;
    public String weight;
    public String volume;
    public String edit;
    public String transportCost;
    public String dist;
    public String rdd;
    
    public String getMapLink() {
        
        if (prevJob == null) return "";
        String s = "https://www.google.com/maps/dir/"
                + prevJob.lat + "," + prevJob.lon + "/"
                + lat + "," + lon + "/"
                ;
        
        return s;
    }
    
    public String getTripDur() {
        
        if (prevJob == null) return "";
        int prevDepart = FZUtil.clockToMin(prevJob.depart);
        int curArrive = FZUtil.clockToMin(arrive);
        String s = String.valueOf(curArrive - prevDepart);
        return s;
    }
    
    public String getServiceTime() {
        
        if ((depart.length() == 0) || (arrive.length() == 0)) return "0";
        int curDepart = FZUtil.clockToMin(depart);
        int curArrive = FZUtil.clockToMin(arrive);
        String s = String.valueOf(curDepart - curArrive);
        return s;
    }
}

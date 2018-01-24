/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params.model;

import java.util.ArrayList;

/**
 *
 * @author rifki.nurfaiz
 */
public class Delivery {
    public String no = "";
    public String vehicleCode;
    public ArrayList<String> arlistVehicleCode;
    public String custId; 
    public String custIdBefore;
    public String serviceTime = "";
    public String storeName;
    public String priority;
    public String distChannel;
    public String street;
    public String weight;
    public String volume;
    public int transportCost = 0;
    public String truckStartTime;
    public String truckEndTime;
    public String doNum;
    public String tripDuration = "";
    public double tripDistance = 0;
    public String depart = "";
    public String arrive = "";
    public String lat1;
    public String lon1;
    public String lat2;
    public String lon2;
    public String rdd;
    public String dist = "0.0";
    public String feasibleAccess = "-";
    public String feasibleTruck = "-";
    public String feasibleCustomer ="-";
    public boolean dbNull;
    
    public String getMapLink() {
        String s = "https://www.google.com/maps/dir/"
                + lat1 + "," + lon1 + "/"
                + lat2 + "," + lon2 + "/"
                ;
        return s;
    }

}

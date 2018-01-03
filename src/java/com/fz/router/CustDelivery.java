/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fz.router;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class CustDelivery {
    
    public String custID = "";
    public double lon = 0;
    public double lat = 0;
    public int serviceTime = 0;
    public String deliveryOrderID = ""; 
    public double totalCubication = 0;
    public double totalKg = 0;
    public int priority = 0;
    public String timeWindowStart = "07:00";
    public String timeWindowEnd = "17:00";
    public List<String> agentTypes = new ArrayList<String>();
    public int adminManMinuteSaved = 0;
    public String distChannelID = "";
    public String custCategory = "";
    public String branchCode = "";
    public String subDistrict = "";
    public String district = "";
    public String zipCode = "";
    public String address = "";
    public String custName = "";
    public double totalAmount = 0;
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fz.router;

import java.util.List;

/**
 *
 */
public class Router {
    
    /***
     * Run routing
     * Will put routing result in gbProgress
     * @param custDeliveries
     * @param deliveryAgents
     * @param maxIteration
     * @param timeDistSource "M":manhattan method, "G":google API, "P":Previouse google runID
     * @param costDistRunID if timeDistSource is "P", file createed by this runID will be used
     * @param workFolder
     * @throws Exception 
     */
    public void route(
            String runID
            , List<CustDelivery> custDeliveries
            , List<DeliveryAgent> deliveryAgents
            , int maxIteration
            , String timeDistSource // M:manhattan, G:google, P:Previouse google runID
            , String costDistRunID // previous google runID
            , String workFolder
            , String jndiConName
            ) throws Exception {
        
//CustID	Longitude	Latitude	Priority	ServiceTime	TotalKG	TimeWinStart	TimeWinEnd	  TotalAmt  	AdminManMinutes	DistrChannelId	CustCategory	BranchCode	Kelurahan	Kecamatan	Address	ZipCode	Nama Toko	TipeTruck

        String s;
        
        // convert cust
        s = "";
        for (CustDelivery d : custDeliveries){
            if (s.length() > 0)
                s += "\n";
            s += d.custID;
            s += "\t" + d.lon;
            s += "\t" + d.lat;
            s += "\t" + d.priority;
            s += "\t" + d.serviceTime;
            s += "\t" + d.totalKg;
            s += "\t" + d.timeWindowStart;
            s += "\t" + d.timeWindowEnd;
            s += "\t" + d.totalAmount;
            s += "\t" + d.adminManMinuteSaved;
            s += "\t" + d.distChannelID;
            s += "\t" + d.custCategory;
            s += "\t" + d.branchCode;
            s += "\t" + d.subDistrict;
            s += "\t" + d.district;
            s += "\t" + d.address;
            s += "\t" + d.zipCode;
            s += "\t" + d.custName;
            String ts = ""; // agentTypes
            for (String t : d.agentTypes){
                if (ts.length() > 0) 
                    ts += "|";
                ts += t;
            }
            s += "\t" + ts;
        }
        String custData = s;
        
        // create agent data
        s = "";
        
        // convert cust
//VehicleID	MaxKG	VehicleCateg	MinStartTime	MaxEndTime	StartLon	StartLat	EndLon	EndLat	MaxCubication
        s = "";
        for (DeliveryAgent a : deliveryAgents){
            if (s.length() > 0)
                s += "\n";
            s += a.agentID;
            s += "\t" + a.maxKg;
            s += "\t" + a.agentType;
            s += "\t" + a.earliestDepartTime;
            s += "\t" + a.latestArrivalTime;
            s += "\t" + a.startLon;
            s += "\t" + a.startLat;
            s += "\t" + a.endLon;
            s += "\t" + a.endLat;
            s += "\t" + a.maxCubication;
        }
        String agentData = s;
        
        
        // TODO: run algorithm (uncomment below line)
//        FZRouter.run(custData, agentData, maxIteration, timeDistSource
//          , costDistRunID, workFolder, jndiConName);

        // Output :
        // Progress -> gbProgress table
        // Solution -> tmSol table
    }
}

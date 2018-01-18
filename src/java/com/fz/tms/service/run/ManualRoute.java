/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.service.run;

import com.fz.generic.BusinessLogic;
import com.fz.tms.params.model.Delivery;
import com.fz.tms.params.service.ManualRouteDB;
import com.fz.util.FZUtil;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author rifki.nurfaiz
 */
public class ManualRoute implements BusinessLogic {
    @Override
    public void run(HttpServletRequest request, HttpServletResponse response, PageContext pc) throws Exception {
        ManualRouteDB mDB = new ManualRouteDB();
        String branch = FZUtil.getHttpParam(request, "branch");
        String shift = FZUtil.getHttpParam(request, "shift");
        String channel = FZUtil.getHttpParam(request, "channel");
        String oriRunId = FZUtil.getHttpParam(request, "oriRunId");
        String runId = getTimeID();
        
        ArrayList<String> custIdAndDoNoArlist = mDB.getDeliveryData(oriRunId);
        List<Delivery> listDelivery = new ArrayList<Delivery>();
        ArrayList<String> arlistVehicleNo = mDB.getVehicleNo(oriRunId);
        String currentCustId = "";
        String prevCustId = "";
        String longLatCurrentCust = "";
        String longLatPrevCust = "";
        String longLatTruckStart = "";
        String longLatTruckEnd = "";
        String tripDur = "";
        String vehicleCode = "";
        String time = "";
        String startTime = "";
        String endTime = "";
        int vehiIdx = 0;
        int noIdx = 1;
        for(int i = 0; i < custIdAndDoNoArlist.size(); i++) {
            Delivery d = new Delivery();
            String[] data = custIdAndDoNoArlist.get(i).split("split");
            currentCustId = data[0];
            try {
                vehicleCode = arlistVehicleNo.get(vehiIdx);
                d.vehicleCode = vehicleCode;
            }
            catch (Exception e) {
                
            }
            startTime = mDB.getTruckStartTime(vehicleCode);
            endTime = mDB.getTruckEndTime(vehicleCode);
            
            d.custId = currentCustId;
            d.doNum = data[1];
            d.storeName = data[2];
            d.street = data[3];
            d.distChannel = data[4];
            d.serviceTime = data[5];
            d.priority = data[6];
            d.weight = data[7];
            d.no = "" + noIdx;
            
            // If truck depart from customer location
            if(!prevCustId.equals("")) {
                longLatPrevCust = mDB.getLongLatCustomer(prevCustId);
                longLatCurrentCust = mDB.getLongLatCustomer(currentCustId);
                String[] longLatPrevCustSplit = longLatPrevCust.split(",");
                String[] longLatCurrentCustSplit = longLatCurrentCust.split(",");
                tripDur = mDB.getTripDuration(longLatPrevCustSplit[0], longLatPrevCustSplit[1], longLatCurrentCustSplit[0], longLatCurrentCustSplit[1]);
                time = addTime(time, convertTripDuration(tripDur));
                d.arrive = time;
                time = addTime(time, d.serviceTime);
                d.depart = time;
                try {
                    // Row with different truck
                    if(timeMoreThanEndTime(time, endTime)) {
                        noIdx = 1;
                        d.no = "" + noIdx;
                        longLatTruckStart = mDB.getLongLatVehicleStart(vehicleCode);
                        longLatCurrentCust = mDB.getLongLatCustomer(currentCustId);
                        String[] longLatiTruck = longLatTruckStart.split(",");
                        String[] longLatCurrentCustoSplit = longLatCurrentCust.split(",");
                        tripDur = mDB.getTripDuration(longLatiTruck[0], longLatiTruck[1], longLatCurrentCustoSplit[0], longLatCurrentCustoSplit[1]);
                        time = addTime(startTime, convertTripDuration(tripDur));
                        d.arrive = time;
                        time = addTime(time, d.serviceTime);
                        d.depart = time;
                        vehiIdx++;
                        try {
                            vehicleCode = arlistVehicleNo.get(vehiIdx);
                            d.vehicleCode = vehicleCode;
                        }
                        catch (Exception e) {

                        }
                    }
                }
                catch(Exception e) {
                    
                }
            }
            
            // If truck depart from DEPO (at very top of table)
            else {
                longLatTruckStart = mDB.getLongLatVehicleStart(vehicleCode);
                longLatCurrentCust = mDB.getLongLatCustomer(currentCustId);
                String[] longLatiTruck = longLatTruckStart.split(",");
                String[] longLatCurrentCustoSplit = longLatCurrentCust.split(",");
                tripDur = mDB.getTripDuration(longLatiTruck[0], longLatiTruck[1], longLatCurrentCustoSplit[0], longLatCurrentCustoSplit[1]);

                time = addTime(startTime, convertTripDuration(tripDur));
                d.arrive = time;
                time = addTime(time, d.serviceTime);
                d.depart = time;
            }
            
            listDelivery.add(d);
            prevCustId = currentCustId;
            noIdx++;
        }
        
        request.setAttribute("runId", runId);
        request.setAttribute("arlistVehicleNo", arlistVehicleNo);
        request.setAttribute("oriRunId", oriRunId);
        request.setAttribute("branch", branch);
        request.setAttribute("shift", shift);
        request.setAttribute("channel", channel);
        request.setAttribute("listDelivery", listDelivery);
    }
    
    public String addTime(String currentTime, String minToAdd) {
        String newTime = "";
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime lt = LocalTime.parse(currentTime);
            newTime = df.format(lt.plusMinutes(Long.parseLong(minToAdd)));
        }
        catch(Exception e) {
            
        }
        return newTime;
    }
    
    public String convertTripDuration(String min) {
        int minuteCeil = 0;
        try {
            double minute = Double.parseDouble(min);
            minuteCeil = (int) Math.ceil(minute);
        }
        catch(Exception e) {
            
        }
        return "" + minuteCeil;
    }
    
     // is added time > endtime
    public boolean timeMoreThanEndTime(String time, String endTime) {
        String[] timeSplit = time.split(":");
        String[] endTimeSplt = endTime.split(":");
        
        int timeHour = Integer.parseInt(timeSplit[0]);
        int endTimeHour = Integer.parseInt(endTimeSplt[0]);
        
        boolean ret;
        if(timeHour < endTimeHour) {
            ret = false;
        }
        else {
            ret = true;
        }
        return ret;
    }
    
    public static String getTimeID() {
        String id = (new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(
                        new java.util.Date()));
        return id;
    }
}

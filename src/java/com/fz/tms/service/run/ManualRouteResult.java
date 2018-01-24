/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.service.run;

import com.fz.generic.BusinessLogic;
import com.fz.tms.params.model.Delivery;
import com.fz.tms.params.model.DeliverySummary;
import com.fz.tms.params.service.ManualRouteDB;
import com.fz.util.FZUtil;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author rifki.nurfaiz
 */
public class ManualRouteResult implements BusinessLogic {
    @Override
    public void run(HttpServletRequest request, HttpServletResponse response, PageContext pc) throws Exception {
        ManualRouteDB mDB = new ManualRouteDB();
        String branch = FZUtil.getHttpParam(request, "branchId");
        String shift = FZUtil.getHttpParam(request, "shift");
        String channel = FZUtil.getHttpParam(request, "channel");
        String vehicle = FZUtil.getHttpParam(request, "vehicle");
        String oriRunId = FZUtil.getHttpParam(request, "oriRunId");
        String runId = FZUtil.getHttpParam(request, "runId");
        String array = FZUtil.getHttpParam(request, "array");
        
        ArrayList<String> arlistOfVehicleCode = mDB.getVehicleNo(oriRunId);
        
        String[] arraySplit = array.split("split");
        ArrayList<String> arlistOfDataArr = new ArrayList<String>();
        
        for(int i = 0; i < arraySplit.length; i++) {
            String entry = arraySplit[i];
            if(entry.charAt(0) == ',') {
                StringBuilder sb = new StringBuilder(entry).deleteCharAt(0);
                entry = sb.toString();
            }
            arlistOfDataArr.add(entry);
        }
        
        // Sort based on vehicle no
        Collections.sort(arlistOfDataArr);
        
        ArrayList<String> arlistOfDataArrSorted = new ArrayList<String>();
        int idx = 1;
        String currentVehicleNo = "";
        String prevVehicleNo = "";
        
        // Sort again based on no
        for(int i = 0; i < arlistOfDataArr.size(); i++) {
            for(int j = 0; j < arlistOfDataArr.size(); j++) {
                String[] data = arlistOfDataArr.get(j).split(",");
                int noUrut = Integer.parseInt(data[1]);
                currentVehicleNo = data[0];
                if(currentVehicleNo.equals(prevVehicleNo) || prevVehicleNo.equals("")) {
                    prevVehicleNo = currentVehicleNo;
                    if(idx == noUrut) {
                        arlistOfDataArrSorted.add(arlistOfDataArr.get(j));
                        String temp = arlistOfDataArr.get(j);
                        arlistOfDataArr.remove(j);
                        arlistOfDataArr.add(temp);
                        break;
                    }
                }
                else {
                    idx = 1;
                    prevVehicleNo = currentVehicleNo;
                    if(idx == noUrut) {
                        arlistOfDataArrSorted.add(arlistOfDataArr.get(j));
                        String temp = arlistOfDataArr.get(j);
                        arlistOfDataArr.remove(j);
                        arlistOfDataArr.add(temp);
                        break;
                    }
                }
            }
            idx++;
        }
        
        ArrayList<Delivery> arlistRetDeliveryData = new ArrayList<Delivery>();
        ArrayList<DeliverySummary> arlistDelivSummary = new ArrayList<>();
        String currentVehicleCode = "";
        String prevVehicleCode = "";
        String currentCustId = "";
        String prevCustId = "";
        String longLatCurrentCust = "";
        String longLatPrevCust = "";
        String longLatTruck = "";
        String tripDur = "";
        double tripDist = 0.0;
        double weight = 0.0;
        int serviceTime = 0;
        int custVisit = 0;
        double sumCapPercentage = 0.0;
        double sumTripDist = 0;
        double sumServiceTime = 0;
        int sumCustVisit = 0;
        double sumAmount = 0;
        boolean lastRow = false;
        String time = "";
        String startTime = "";
        String endTime = "";
        for(int i = 0; i < arlistOfDataArrSorted.size(); i++) {
            Delivery d = new Delivery();
            String[] data = arlistOfDataArrSorted.get(i).split(",");
            String[] dataDelivery = mDB.getDeliveryDataByCustIdAndRunId(data[2], oriRunId).split("split");
            
            d.vehicleCode = data[0];
            d.no = data[1];
            d.custId = data[2];
            d.storeName = dataDelivery[0];
            d.street = dataDelivery[1];
            d.distChannel = dataDelivery[2];
            d.serviceTime = dataDelivery[3];
            d.doNum = mDB.getDoCount(data[2], oriRunId);
            d.priority = dataDelivery[5];
            d.weight = "" + mDB.getWeight(data[2], oriRunId);
            
            currentCustId = data[2];
            
            try {
                currentVehicleCode = d.vehicleCode;
            }
            catch (Exception e) {
                
            }
            
            startTime = mDB.getTruckStartTime(currentVehicleCode);
            endTime = mDB.getTruckEndTime(currentVehicleCode);
            
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
                
                d.lon1 = longLatPrevCustSplit[0];
                d.lat1 = longLatPrevCustSplit[1];
                d.lon2 = longLatCurrentCustSplit[0];
                d.lat2 = longLatCurrentCustSplit[1];
                
                try {
                    //// IF CURRENT VEHICLE CODE DIFFERENT WITH PREVIOUS ROW ////
                    if(!currentVehicleCode.equals(prevVehicleCode)) {
                        longLatTruck = mDB.getLongLatVehicleStart(prevVehicleCode);
                        longLatCurrentCust = mDB.getLongLatCustomer(prevCustId);
                        String[] longLatitTruck = longLatTruck.split(",");
                        String[] longLatCurrentCustomSplit = longLatCurrentCust.split(",");
                        tripDur = mDB.getTripDuration(longLatCurrentCustomSplit[0], longLatCurrentCustomSplit[1], longLatitTruck[0], longLatitTruck[1]);
                        
                        time = substractTime(addTime(time, convertTripDuration(tripDur)), d.serviceTime);
                        
                        //ROW TRUCK END
                        Delivery dvp = new Delivery();
                        dvp.vehicleCode = prevVehicleCode;
                        dvp.arrive = time;
                        arlistRetDeliveryData.add(dvp);
                        
                        //ROW TRUCK START
                        Delivery dv = new Delivery();
                        dv.vehicleCode = currentVehicleCode;
                        dv.depart = startTime;
                        arlistRetDeliveryData.add(dv);
                        
                        //ROW AFTER TRUCK START
                        DeliverySummary ds = new DeliverySummary();
                        ds.vehicleNo = prevVehicleCode;
                        ds.tripDistance = String.format("%.1f", tripDist/1000);
                        ds.vehicleType = mDB.getVehicleType(ds.vehicleNo);
                        ds.weight = String.format("%.1f", weight);
                        ds.vehicleVolume = mDB.getVehicleVolume(prevVehicleCode);
                        ds.capPercentage = 100 * (double)Math.round(Double.parseDouble(ds.weight) / (double) (ds.vehicleVolume) * 100d) /100d;
                        ds.grossAmount = convertToCurrency(mDB.getAmount(currentCustId, oriRunId));
                        ds.serviceTime = serviceTime;
                        ds.numOfCustVisit = custVisit;
                        arlistDelivSummary.add(ds);
                        
                        sumCapPercentage += ds.capPercentage;
                        sumCustVisit += ds.numOfCustVisit;
                        sumServiceTime += ds.serviceTime;
                        //sumAmount += Double.parseDouble(convertToCurrency(mDB.getAmount(currentCustId, oriRunId)));
                        sumTripDist += Double.parseDouble(ds.tripDistance);
                        
                        // set weight to 0 for next vehicle code
                        weight = 0.0;
                        
                        // set service time to 0 for next vehicle code
                        serviceTime = 0;
                        
                        // set number of visited customer to 0 for next vehicle code
                        custVisit = 0;
                        
                        longLatTruck = mDB.getLongLatVehicleStart(currentVehicleCode);
                        longLatCurrentCust = mDB.getLongLatCustomer(currentCustId);
                        String[] longLatiTruck = longLatTruck.split(",");
                        String[] longLatCurrentCustoSplit = longLatCurrentCust.split(",");
                        tripDur = mDB.getTripDuration(longLatiTruck[0], longLatiTruck[1], longLatCurrentCustoSplit[0], longLatCurrentCustoSplit[1]);
                        time = addTime(startTime, convertTripDuration(tripDur));
                        d.arrive = time;
                        time = addTime(time, d.serviceTime);
                        d.depart = time;
                        
                        // If trip distance from DEPO to customer location available in DB
                        tripDist = 0.0;
                        if(!mDB.getTripDistance(longLatiTruck[0], longLatiTruck[1], longLatCurrentCustoSplit[0], longLatCurrentCustoSplit[1]).equals("")) {
                            tripDist += Double.parseDouble(mDB.getTripDistance(longLatiTruck[0], longLatiTruck[1], longLatCurrentCustoSplit[0], longLatCurrentCustoSplit[1]));
                        }
                    }
                    
                    // Summary for the different last truck
                    if(i == arlistOfDataArrSorted.size()-1) {
                        weight += mDB.getWeight(d.custId, oriRunId);
                        serviceTime += Double.parseDouble(d.serviceTime);
                        custVisit += 1;
                        DeliverySummary ds = new DeliverySummary();
                        ds.vehicleNo = currentVehicleCode;
                        ds.tripDistance = String.format("%.1f", tripDist/1000);
                        ds.vehicleType = mDB.getVehicleType(ds.vehicleNo);
                        ds.weight = String.format("%.1f", weight);
                        ds.vehicleVolume = mDB.getVehicleVolume(ds.vehicleNo);
                        ds.capPercentage = 100 * (double)Math.round(Double.parseDouble(ds.weight) / (double) (ds.vehicleVolume) * 100d) /100d;
                        ds.grossAmount = convertToCurrency(mDB.getAmount(currentCustId, oriRunId));
                        ds.serviceTime = serviceTime;
                        ds.numOfCustVisit = custVisit;
                        arlistDelivSummary.add(ds);
                        
                        sumCapPercentage += ds.capPercentage;
                        sumCustVisit += ds.numOfCustVisit;
                        sumServiceTime += ds.serviceTime;
                        //sumAmount += Double.parseDouble(convertToCurrency(mDB.getAmount(currentCustId, oriRunId)));
                        sumTripDist += Double.parseDouble(ds.tripDistance);
                        
                        lastRow = true;
                    }
                }
                catch(Exception e) {
                    
                }
                
                // If trip distance from customer1 to customer2 location available in DB
                // Distance accumulated
                if(!mDB.getTripDistance(longLatPrevCustSplit[0], longLatPrevCustSplit[1], longLatCurrentCustSplit[0], longLatCurrentCustSplit[1]).equals("")) {
                    tripDist += Double.parseDouble(mDB.getTripDistance(longLatPrevCustSplit[0], longLatPrevCustSplit[1], longLatCurrentCustSplit[0], longLatCurrentCustSplit[1]));
                }
                
                // Weight accumulated
                weight += mDB.getWeight(d.custId, oriRunId);
                
                // Service time accumulated
                serviceTime += Double.parseDouble(d.serviceTime);
                
                // Visited customer accumulated
                custVisit += 1;
            }
            
            // If truck start from DEPO (the very top of table)
            else {
                // ROW TRUCK START
                Delivery dv = new Delivery();
                dv.vehicleCode = currentVehicleCode;
                dv.depart = startTime;
                arlistRetDeliveryData.add(dv);
                
                longLatTruck = mDB.getLongLatVehicleStart(currentVehicleCode);
                longLatCurrentCust = mDB.getLongLatCustomer(currentCustId);
                String[] longLatiTruck = longLatTruck.split(",");
                String[] longLatCurrentCustoSplit = longLatCurrentCust.split(",");
                tripDur = mDB.getTripDuration(longLatiTruck[0], longLatiTruck[1], longLatCurrentCustoSplit[0], longLatCurrentCustoSplit[1]);

                time = addTime(startTime, convertTripDuration(tripDur));
                d.arrive = time;
                time = addTime(time, d.serviceTime);
                d.depart = time;
                
                d.lon1 = longLatiTruck[0];
                d.lat1 = longLatiTruck[1];
                d.lon2 = longLatCurrentCustoSplit[0];
                d.lat2 = longLatCurrentCustoSplit[1];
                
                // If trip distance from DEPO to customer location available in DB
                if(!mDB.getTripDistance(longLatiTruck[0], longLatiTruck[1], longLatCurrentCustoSplit[0], longLatCurrentCustoSplit[1]).equals("")) {
                    tripDist += Double.parseDouble(mDB.getTripDistance(longLatiTruck[0], longLatiTruck[1], longLatCurrentCustoSplit[0], longLatCurrentCustoSplit[1]));
                }
                
                // Weight accumulated
                weight += mDB.getWeight(d.custId, oriRunId);
                
                // Service time accumulated
                serviceTime += Double.parseDouble(d.serviceTime);
                
                // Visited customer accumulated
                custVisit += 1;
            }
            
            String customerDelivTime = mDB.getCustomerDelivTime(d.custId);
            String[] customerDelivTimeSplit = customerDelivTime.split(",");
            
            // In case null/"" data in DB use try catch
            try {
                if(timeArriveInCustomerRange(d.arrive, customerDelivTimeSplit[0], customerDelivTimeSplit[1])) {
                    d.feasibleCustomer = "1";
                }
            }
            catch(Exception e) {
                d.dbNull = true;
            }
            
            if(!timeMoreThanEndTime(d.depart, endTime)) {
                d.feasibleTruck = "1";
            }
            
            prevCustId = currentCustId;
            prevVehicleCode = currentVehicleCode;
            arlistRetDeliveryData.add(d);
            if(lastRow) {
                longLatTruck = mDB.getLongLatVehicleStart(prevVehicleCode);
                longLatCurrentCust = mDB.getLongLatCustomer(currentCustId);
                String[] longLatitTruck = longLatTruck.split(",");
                String[] longLatCurrentCustomSplit = longLatCurrentCust.split(",");
                tripDur = mDB.getTripDuration(longLatCurrentCustomSplit[0], longLatCurrentCustomSplit[1], longLatitTruck[0], longLatitTruck[1]);
                time = addTime(time, convertTripDuration(tripDur));
                
                //ROW TRUCK END
                Delivery dvp = new Delivery();
                dvp.vehicleCode = prevVehicleCode;
                dvp.arrive = time;
                arlistRetDeliveryData.add(dvp);
            }
            mDB.insertToRouteJob1(d, runId, branch, getTimeID(), shift);
        }

        request.setAttribute("transportCost", "0");
        request.setAttribute("sumAmount", "" + sumAmount);
        request.setAttribute("sumTripDist", "" + sumTripDist);
        request.setAttribute("sumCustVisit", "" + sumCustVisit);
        request.setAttribute("sumServiceTime", String.format("%.1f", sumServiceTime/60));
        request.setAttribute("avgCapPercentage", "" + (sumCapPercentage/arlistOfVehicleCode.size()));
        request.setAttribute("runId", runId);
        request.setAttribute("arlistDeliveSummary", arlistDelivSummary);
        request.setAttribute("arlistRetDeliveryData", arlistRetDeliveryData);
        request.setAttribute("oriRunId", oriRunId);
        request.setAttribute("branch", branch);
        request.setAttribute("shift", shift);
        request.setAttribute("channel", channel);
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
    
    public String substractTime(String currentTime, String minToSubstract) {
        String newTime = "";
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime lt = LocalTime.parse(currentTime);
            newTime = df.format(lt.minusMinutes(Long.parseLong(minToSubstract)));
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
    
     public boolean timeArriveInCustomerRange(String truckArrive, String custOpen, String custClose) {
        String[] truckArriveSplit = truckArrive.split(":");
        String[] custOpenSplit = custOpen.split(":");
        String[] custCloseSplit = custClose.split(":");
        
        int truckArriveHour = Integer.parseInt(truckArriveSplit[0]);
        int custOpenHour = Integer.parseInt(custOpenSplit[0]);
        int custCloseHour = Integer.parseInt(custCloseSplit[0]);
        
        boolean ret;
        if(custOpenHour < truckArriveHour && truckArriveHour < custCloseHour) {
            ret = true;
        }
        else {
            ret = false;
        }
        return ret;
    }
     
     public static String getTimeID() {
        String id = (new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(
                        new java.util.Date()));
        return id;
    }
     
    public String convertToCurrency(String num) {
        String numSubstr = num.substring(0, num.length()-3);
        String currency = "";
        int count = 1;
        for(int i = numSubstr.length()-1; i >= 0; i--) {
            if(count % 4 == 0) {
                currency += "." + numSubstr.charAt(i);
                count = 1;
            }
            else {
                currency += numSubstr.charAt(i);
            }
            count++;
        }
        return new StringBuffer(currency).reverse().toString();
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params.service;

import com.fz.generic.Db;
import com.fz.tms.params.model.Delivery;
import com.fz.tms.params.model.VehicleNo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author rifki.nurfaiz
 */
public class ManualRouteDB {
    
    public ArrayList<String> getDeliveryData(String runId) throws Exception {
        ArrayList<String> arlistData = new ArrayList<String>();
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){            
                String sql ;
                sql = "SELECT distinct Customer_ID FROM BOSNET1.dbo.TMS_PreRouteJob where RunId = '"+runId+"';";
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()) {
                        String custId = "";
                        String doNo = "";
                        String name1 = "";
                        String street = "";
                        String distChannel = "";
                        String serviceTime = "";
                        String totalKg = "";
                        String custPriority = "";
                        custId = rs.getString("Customer_ID");
                        
                        try (Statement stmt = con.createStatement()) {           
                            sql = "SELECT distinct DO_Number FROM BOSNET1.dbo.TMS_PreRouteJob where Customer_ID = '"+custId+"' and RunId = '"+runId+"';";
                            // query
                            try (ResultSet rst = stmt.executeQuery(sql)){
                                while (rst.next()) {
                                    doNo += rst.getString("DO_Number") + ";\n";
                                }
                            }
                        }
                        
                        try (Statement stmt = con.createStatement()) {            
                            sql = "SELECT distinct Name1, Street, Distribution_Channel, Service_time, Customer_priority FROM BOSNET1.dbo.TMS_PreRouteJob where Customer_ID = '"+custId+"' and RunId = '"+runId+"';";
                            // query
                            try (ResultSet rst = stmt.executeQuery(sql)){
                                while (rst.next()) {
                                    name1 = rst.getString("Name1");
                                    street = rst.getString("Street");
                                    distChannel = rst.getString("Distribution_Channel");
                                    serviceTime = rst.getString("Service_time");
                                    custPriority = rst.getString("Customer_priority");
                                }
                            }
                        }
                        
                        try (Statement stmt = con.createStatement()) {            
                            sql = "SELECT SUM(total_kg) as total_kg FROM BOSNET1.dbo.TMS_PreRouteJob where Customer_ID = '"+custId+"' and RunId = '"+runId+"' and Is_Edit = 'edit';";
                            // query
                            try (ResultSet rst = stmt.executeQuery(sql)){
                                while (rst.next()) {
                                    totalKg = rst.getString("total_kg");
                                }
                            }
                        }
                        arlistData.add(custId + "split" + doNo + "split" + name1 + "split" + street + "split" + distChannel + "split" + serviceTime + "split" + custPriority + "split" + String.format("%.1f", Double.parseDouble(totalKg)));
                    }
                }
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return arlistData;
    }
    
    public static ArrayList<String> getVehicleNo(String runId) {
        ArrayList<String> arlistVehicleNo = new ArrayList<String>();
      
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){            
                String sql ;
                sql = "SELECT vehicle_code FROM BOSNET1.dbo.TMS_PreRouteVehicle where RunId = '"+runId+"';";
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()){
                        arlistVehicleNo.add(rs.getString("vehicle_code"));
                    }
                }
            }
        }
        catch (Exception e){
            
        }
        return arlistVehicleNo;
    }
    
    public static String getVehicleType(String vehicle_code) {
        String vehicle_type = "";
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){            
                String sql ;
                sql = "SELECT vehicle_type FROM BOSNET1.dbo.TMS_VehicleAtr where vehicle_code = '"+vehicle_code+"';";
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()){
                        vehicle_type = rs.getString("vehicle_type");
                    }
                }
            }
        }
        catch (Exception e){
            
        }
        return vehicle_type;
    }
    
    public static int getVehicleVolume(String vehicle_code) {
        String vehicle_volume = "";
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){            
                String sql ;
                sql = "SELECT Weight FROM BOSNET1.dbo.Vehicle where vehicle_code = '"+vehicle_code+"';";
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()){
                        vehicle_volume = rs.getString("Weight");
                    }
                }
            }
        }
        catch (Exception e){
            
        }
      
        return (int) Math.ceil(Double.parseDouble(vehicle_volume));
    }
    
    public static ArrayList<VehicleNo> getVehicleNoAsObj(String runId) {
        ArrayList<VehicleNo> arlistVehicleNo = new ArrayList<VehicleNo>();
      
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){            
                String sql ;
                sql = "SELECT vehicle_code FROM BOSNET1.dbo.TMS_PreRouteVehicle where RunId = '"+runId+"';";
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()){
                        VehicleNo r = new VehicleNo();
                        r.vehicleNo = rs.getString("vehicle_code");
                        arlistVehicleNo.add(r);
                    }
                }
            }
        }
        catch (Exception e){
            
        }
        return arlistVehicleNo;
    }
    
    public String getTruckStartTime(String vehicleCode) throws Exception {
        String startTime = "";
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){            
                String sql ;
                sql = "SELECT * FROM BOSNET1.dbo.TMS_VehicleAtr where vehicle_code = '"+vehicleCode+"';";
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()) {
                        startTime = rs.getString("startTime");
                    }
                }
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return startTime;
    }
    
    public String getTruckEndTime(String vehicleCode) throws Exception {
        String startTime = "";
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){            
                String sql ;
                sql = "SELECT * FROM BOSNET1.dbo.TMS_VehicleAtr where vehicle_code = '"+vehicleCode+"';";
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()) {
                        startTime = rs.getString("endTime");
                    }
                }
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return startTime;
    }
    
    public String getTripDuration(String longCust1, String latCust1, String longCust2, String latCust2) throws Exception{
        String tripDur = "";
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){            
                String sql ;
                sql = "SELECT TOP 1 dur FROM BOSNET1.dbo.TMS_CostDist where lon1 = '"+longCust1+"' and lat1 = '"+latCust1+"' and lon2 = '"+longCust2+"' and lat2 = '"+latCust2+"';";
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()) {
                        tripDur = rs.getString("dur");
                    }
                }
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return tripDur;
    }
    
    public String getTripDistance(String longCust1, String latCust1, String longCust2, String latCust2) throws Exception{
        String tripDist = "";
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){            
                String sql ;
                sql = "SELECT TOP 1 dist FROM BOSNET1.dbo.TMS_CostDist where lon1 = '"+longCust1+"' and lat1 = '"+latCust1+"' and lon2 = '"+longCust2+"' and lat2 = '"+latCust2+"';";
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()) {
                        tripDist = rs.getString("dist");
                    }
                }
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return tripDist;
    }
    
    public String getDeliveryDataByCustIdAndRunId(String custId, String runId) throws Exception{
        String data = "";
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){
                String name1 = "";
                String street = "";
                String distChannel = "";
                String serviceTime = "";
                String doNo = "";
                String custPriority = "";
                
                String sql ;
                sql = "SELECT distinct Name1, Street, Distribution_Channel, Service_time, DO_Number, Customer_priority FROM BOSNET1.dbo.TMS_PreRouteJob where Customer_ID like '"+custId+"' and RunId = '"+runId+"';";
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()) {
                        name1 = rs.getString("Name1");
                        street = rs.getString("Street");
                        distChannel = rs.getString("Distribution_Channel");
                        serviceTime = rs.getString("Service_time");
                        doNo = rs.getString("DO_Number");
                        custPriority = rs.getString("Customer_priority");
                    }
                }
               data = (name1 + "split" + street + "split" + distChannel + "split" + serviceTime + "split" + doNo + "split" + custPriority);
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return data;
    }
    
    public Double getWeight(String custId, String runId) throws Exception {
        double totalKg = 0.0;
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){         
            try (Statement stm = con.createStatement()){            
                String sql ;
                sql = "SELECT SUM(total_kg) as total_kg FROM BOSNET1.dbo.TMS_PreRouteJob where Customer_ID = '"+custId+"' and RunId = '"+runId+"' and Is_Edit = 'ori';";
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()) {
                        totalKg = Double.parseDouble(rs.getString("total_kg"));
                    }
                }
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        DecimalFormat newFormat = new DecimalFormat("#.#");
        return Double.valueOf(newFormat.format(totalKg));
    }
    
    public String getLongLatVehicleStart(String vehicleCode) throws Exception{
        String startLon = "";
        String startLat = "";
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){            
                String sql ;
                sql = "SELECT TOP 1 startLon, startLat FROM BOSNET1.dbo.TMS_VehicleAtr where vehicle_code = '"+vehicleCode+"';";
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()) {
                        startLon = rs.getString("startLon");
                        startLat = rs.getString("startLat");
                    }
                }
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return startLon + "," + startLat;
    }
    
    public String getLongLatVehicleEnd(String vehicleCode) throws Exception{
        String endLon = "";
        String endLat = "";
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){            
                String sql ;
                sql = "SELECT TOP 1 endLon, endLat FROM BOSNET1.dbo.TMS_VehicleAtr where vehicle_code = '"+vehicleCode+"';";
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()) {
                        endLon = rs.getString("endLon");
                        endLat = rs.getString("endLat");
                    }
                }
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return endLon + "," + endLat;
    }
    
    public String getLongLatCustomer(String custId) throws Exception{
        String longitude = "";
        String latitude = "";
        String ret = "";
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){            
                String sql ;
                sql = "SELECT TOP 1 Longitude, Latitude FROM BOSNET1.dbo.Customer where Customer_ID = '"+custId+"';";
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()) {
                        longitude = rs.getString("Longitude");
                        latitude = rs.getString("Latitude");
                    }
                }
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return longitude + "," + latitude;
    }
    
    public String getDoCount(String custId, String runId) throws Exception {
        String doNo = "";
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){            
                String sql ;
                sql = "SELECT distinct DO_Number FROM BOSNET1.dbo.TMS_PreRouteJob where Customer_ID = '"+custId+"' and RunId = '"+runId+"';";
                // query
                try (ResultSet rst = stm.executeQuery(sql)){
                    while (rst.next()) {
                        doNo += rst.getString("DO_Number") + ";\n";
                    }
                }
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return doNo;
    }
    
    public String getCustomerDelivTime(String custId) throws Exception {
        String delivStart = "";
        String delivEnd = "";
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){            
                String sql ;
                sql = "SELECT distinct deliv_start, deliv_end FROM BOSNET1.dbo.TMS_CustAtr where Customer_ID = '"+custId+"';";
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()) {
                        delivStart = rs.getString("deliv_start");
                        delivEnd = rs.getString("deliv_end");
                    }
                }
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return delivStart + "," + delivEnd;
    }
    
    public String insertToRouteJob1(Delivery d, String runId, String branch, String createTime, String shift) throws Exception {
        String insert = "ERROR";
        
        String sql = "";
        sql = "INSERT INTO bosnet1.dbo.TMS_RouteJob1 "
                + "(customer_id, do_number, vehicle_code, arrive, depart, run_id, create_dtm, branch, shift, lon, lat, weight, volume, transport_cost, activity_cost, dist) "
                + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        
        try (Connection con = (new Db()).getConnection("jdbc/fztms"); PreparedStatement psHdr = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);)  { 
            psHdr.setString(1, d.custId);
            psHdr.setString(2, d.doNum);
            psHdr.setString(3, d.vehicleCode);
            psHdr.setString(4, d.arrive);
            psHdr.setString(5, d.depart);
            psHdr.setString(6, runId);
            psHdr.setString(7, createTime);
            psHdr.setString(8, branch);
            psHdr.setString(9, shift);
            psHdr.setString(10, d.lon2);
            psHdr.setString(11, d.lat2);
            psHdr.setString(12, "" + d.weight);
            psHdr.setString(13, d.volume);
            psHdr.setString(14, "" + d.transportCost);
            psHdr.setString(15, "");
            psHdr.setString(16, "");

            psHdr.executeUpdate();
            
            insert = "OK";
        }
        return insert;
    }
    
    public String getAmount(String custId, String runId) throws Exception {
        String amount = "";
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){
            try (Statement stm = con.createStatement()){            
                String sql ;
                sql = "SELECT SUM(Gross_Amount) as amount FROM BOSNET1.dbo.TMS_PreRouteJob where Customer_ID = '"+custId+"' and RunId = '"+runId+"' and Is_Edit = 'ori';";
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()) {
                        amount = (rs.getString("amount"));
                    }
                }
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return amount;
    }
}

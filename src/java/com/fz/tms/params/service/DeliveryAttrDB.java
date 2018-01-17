/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params.service;

import com.fz.generic.Db;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author rifki.nurfaiz
 */
public class DeliveryAttrDB {
    
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
    
    public String getStoreStreet(String custId) throws Exception{
        String custStreet = "";
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){            
                String sql ;
                sql = "SELECT TOP 1 Street FROM BOSNET1.dbo.Customer where Customer_ID = '"+custId+"';";
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()) {
                        custStreet = rs.getString("Street");
                    }
                }
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return custStreet;
    }
    
    public String getStoreName(String custId) throws Exception{
        String storeName = "";
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){            
                String sql ;
                sql = "SELECT * FROM BOSNET1.dbo.Customer where Customer_ID = '"+custId+"';";
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()) {
                        storeName = rs.getString("Name1");
                    }
                }
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return storeName;
    }
    
    public String getTripDuration(String custId1, String custId2) throws Exception{
        String tripDur = "";
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){            
                String sql ;
                sql = "SELECT TOP 1 dur FROM BOSNET1.dbo.TMS_CostDist where from1 like '"+custId1+"%"+"' and to1 like '"+custId2+"%"+"';";
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
    
    public String getLon1(String custId1, String custId2) throws Exception{
        String lon1 = "";
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){            
                String sql ;
                sql = "SELECT TOP 1 lon1 FROM BOSNET1.dbo.TMS_CostDist where from1 like '"+custId1+"%"+"' and to1 like '"+custId2+"%"+"';";
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()) {
                        lon1 = rs.getString("lon1");
                    }
                }
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return lon1;
    }
    
    public String getLat1(String custId1, String custId2) throws Exception{
        String lat1 = "";
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){            
                String sql ;
                sql = "SELECT TOP 1 lat1 FROM BOSNET1.dbo.TMS_CostDist where from1 like '"+custId1+"%"+"' and to1 like '"+custId2+"%"+"';";
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()) {
                        lat1 = rs.getString("lat1");
                    }
                }
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return lat1;
    }
    
    public String getLon2(String custId1, String custId2) throws Exception{
        String lon2 = "";
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){            
                String sql ;
                sql = "SELECT TOP 1 lon2 FROM BOSNET1.dbo.TMS_CostDist where from1 like '"+custId1+"%"+"' and to1 like '"+custId2+"%"+"';";
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()) {
                        lon2 = rs.getString("lon2");
                    }
                }
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return lon2;
    }
    
    public String getLat2(String custId1, String custId2) throws Exception{
        String lat2 = "";
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){            
                String sql ;
                sql = "SELECT TOP 1 lat2 FROM BOSNET1.dbo.TMS_CostDist where from1 like '"+custId1+"%"+"' and to1 like '"+custId2+"%"+"';";
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()) {
                        lat2 = rs.getString("lat2");
                    }
                }
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return lat2;
    }
}

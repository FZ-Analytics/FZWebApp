/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params.service;

import com.fz.generic.Db;
import com.fz.tms.params.model.Branch;
import com.fz.tms.params.model.OptionModel;
import com.fz.tms.params.model.Vehicle;
import com.fz.util.FZUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author dwi.rangga
 */
public class VehicleAttrDB {
    
    public String isVehicle(String str) throws Exception{
        String c = "ERROR";
        
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){            
                // create sql
                String sql ;
                sql = "SELECT * FROM BOSNET1.dbo.Vehicle where Plant = '"+str+"';";
                
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    if (rs.next()){
                        c = "OK";
                    }
                }
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return c;
    }
    
    public List<Vehicle> getVehicle(String str) throws Exception{
        Vehicle c = new Vehicle();
        List<Vehicle> ar = new ArrayList<Vehicle>();
        
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){
            
                // create sql
                String sql ;
                sql = "SELECT vh.vehicle_code, va.startLon, va.startLat, va.endLon, va.endLat, va.startTime, va.endTime, va.source1,"
                        + " case when vh.vehicle_code is null then va.weight else vh.weight end as weight, case when vh.vehicle_code is null then va.volume else vh.volume end as volume,"
                        + " case when vh.vehicle_code is null then va.branch else vh.plant end as plant, case when vh.vehicle_code is null then va.vehicle_type else vh.vehicle_type end as vehicle_type, va.included"
                        + " FROM BOSNET1.dbo.Vehicle vh left join BOSNET1.dbo.TMS_VehicleAtr va on vh.Vehicle_Code = va.vehicle_code where vh.Plant = '"+str+"';";
                
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()){
                        c = new Vehicle();
                        c.vehicle_code = rs.getString("Vehicle_Code");
                        c.branch = rs.getString("plant");
                        c.startLon  = rs.getString("startLon");
                        c.startLat = rs.getString("startLat");
                        c.endLon  = rs.getString("endLon");
                        c.endLat = rs.getString("endLat");
                        c.startTime = rs.getString("startTime");
                        c.endTime = rs.getString("endTime");
                        c.source1 = rs.getString("source1");
                        c.vehicle_type = rs.getString("vehicle_type");
                        c.weight = rs.getString("weight");
                        c.volume = rs.getString("volume");
                        c.included = rs.getString("included");
                        ar.add(c);
                    }
                }
            }
        }
        catch (Exception e){
            ar = new ArrayList<Vehicle>();
            throw new Exception(e.getMessage());            
        }
        return ar;
    }
    
    public String insert(Vehicle c, String flag) throws Exception{
        
        String insert = "ERROR";
        // open db connection and 1 statement to insert header
        String sql = "";
        
        if(flag.equalsIgnoreCase("insert")){
            sql = "INSERT INTO bosnet1.dbo.TMS_VehicleAtr "
                + "(vehicle_code, branch, startLon, startLat, endLon, endLat, startTime, endTime, source1, vehicle_type, weight, volume, included, costPerM, fixedCost, Channel, IdDriver, NamaDriver, DriverDates) "
                + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";            
        }else if(flag.equalsIgnoreCase("update")){
            sql = "update bosnet1.dbo.TMS_VehicleAtr "
                + " set branch = ?, startLon = ?, startLat = ?, endLon = ?, endLat = ?, startTime = ?, endTime = ?, source1 = ?, vehicle_type = ?, weight = ?, volume = ?, included = ?, costPerM = ?, fixedCost = ?, Channel = ?, IdDriver = ?, NamaDriver = ?, DriverDates = ?"
                + " where vehicle_code = ?;";
        }
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        c.DriverDates = dateFormat.format(date);
        
        try (
            Connection con = (new Db()).getConnection("jdbc/fztms");
            PreparedStatement psHdr = con.prepareStatement(sql
                    , Statement.RETURN_GENERATED_KEYS);
            )  {

            // start database transaction,
            // insert header to db    
            
            if(flag.equalsIgnoreCase("insert")){
                psHdr.setString(1, c.vehicle_code);
                psHdr.setString(2, c.branch);
                psHdr.setString(3, c.startLon);
                psHdr.setString(4, c.startLat);
                psHdr.setString(5, c.endLon);    
                psHdr.setString(6, c.endLat);
                psHdr.setString(7, c.startTime);
                psHdr.setString(8, c.endTime);
                psHdr.setString(9, c.source1);
                psHdr.setString(10, c.vehicle_type);    
                psHdr.setString(11, c.weight);
                psHdr.setString(12, c.volume);
                psHdr.setString(13, c.included);
                psHdr.setString(14, c.costPerM);
                psHdr.setString(15, c.fixedCost);
                psHdr.setString(16, c.Channel);
                psHdr.setString(17, c.IdDriver);
                psHdr.setString(18, c.NamaDriver);
                psHdr.setString(19, c.DriverDates);
            }else if(flag.equalsIgnoreCase("update")){
                psHdr.setString(1, c.branch);
                psHdr.setString(2, c.startLon);
                psHdr.setString(3, c.startLat);
                psHdr.setString(4, c.endLon);    
                psHdr.setString(5, c.endLat);
                psHdr.setString(6, c.startTime);
                psHdr.setString(7, c.endTime);
                psHdr.setString(8, c.source1);
                psHdr.setString(9, c.vehicle_type);    
                psHdr.setString(10, c.weight);
                psHdr.setString(11, c.volume);
                psHdr.setString(12, c.included);                
                psHdr.setString(13, c.costPerM);
                psHdr.setString(14, c.fixedCost);
                psHdr.setString(15, c.Channel);
                psHdr.setString(16, c.IdDriver);
                psHdr.setString(17, c.NamaDriver);
                psHdr.setString(18, c.DriverDates);
                psHdr.setString(19, c.vehicle_code);
            }
            
            
            // transaction needed because we have several sql 
            con.setAutoCommit(false);

            psHdr.executeUpdate();
            
            // commit transaction
            con.setAutoCommit(true);
            insert = "OK";
        }
        return insert;
    }
    
    public static List<OptionModel> getVehicleId( String Str) throws Exception{
        OptionModel c = new OptionModel();
        List<OptionModel> ar = new ArrayList<OptionModel>();
        
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){
            
                // create sql
                String sql ;
                sql = "select Vehicle_Code from bosnet1.dbo.vehicle where plant  = '"+Str+"'";
                
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()){   
                        c = new OptionModel();
                        c.Display = rs.getString("Vehicle_Code");
                        c.Value = rs.getString("Vehicle_Code");
                        ar.add(c);
                    }
                }
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return ar;
    }
    
    public String isExternal( String Str) throws Exception{
        String ex = "N"; 
        
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){
            
                // create sql
                String sql ;
                sql = "select count(*) isTrue from bosnet1.dbo.vehicle where vehicle_code = '"+Str+"';";
                
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()){   
                        ex = rs.getInt("isTrue") > 0 ? "Y" : "N";
                    }
                }
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return ex;
    }
    
    public List<Branch> getBranch() throws Exception{
        Branch c = new Branch();
        List<Branch> ar = new ArrayList<Branch>();
        
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){
            
                // create sql
                String sql ;
                sql = "SELECT SalOffCode, SalOffName FROM BOSNET1.dbo.TMS_SALESOFFICE order by SalOffName asc;";
                
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()){   
                        c = new Branch();
                        c.branchId = rs.getString("SalOffCode");
                        c.name = rs.getString("SalOffName");
                        ar.add(c);
                    }
                }
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return ar;
    }
    
    public List<Vehicle> getVehi(String vCode) throws Exception{
        Vehicle c = new Vehicle();
        List<Vehicle> ar = new ArrayList<Vehicle>();
        
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){            
                // create sql
                String sql ;
                sql = "  SELECT\n" +
                "	vh.vehicle_code,\n" +
                "	va.startLon,\n" +
                "	va.startLat,\n" +
                "	va.endLon,\n" +
                "	va.endLat,\n" +
                "	va.startTime,\n" +
                "	va.endTime,\n" +
                "	va.source1,\n" +
                "	CASE\n" +
                "		WHEN vh.vehicle_code IS NULL THEN va.weight\n" +
                "		ELSE vh.weight\n" +
                "	END AS weight,\n" +
                "	CASE\n" +
                "		WHEN vh.vehicle_code IS NULL THEN va.volume\n" +
                "		ELSE vh.volume\n" +
                "	END AS volume,\n" +
                "	CASE\n" +
                "		WHEN vh.vehicle_code IS NULL THEN va.branch\n" +
                "		ELSE vh.plant\n" +
                "	END AS plant,\n" +
                "	CASE\n" +
                "		WHEN vh.vehicle_code IS NULL THEN va.vehicle_type\n" +
                "		ELSE vh.vehicle_type\n" +
                "	END AS vehicle_type,\n" +
                "	va.included,\n" +
                "	va.costPerM,\n" +
                "	va.fixedCost,\n" +
                "	va.Channel,\n" +
                "	va.IdDriver,\n" +
                "	va.NamaDriver\n" +
                "FROM\n" +
                "	BOSNET1.dbo.Vehicle vh\n" +
                "LEFT JOIN BOSNET1.dbo.TMS_VehicleAtr va ON\n" +
                "	vh.Vehicle_Code = va.vehicle_code\n" +
                "WHERE\n" +
                "	vh.vehicle_code = '"+vCode+"';";
                
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    if (rs.next()){
                        c = new Vehicle();
                        int i = 1;
                        c.vehicle_code = FZUtil.getRsString(rs, i++, "");
                        c.startLon = FZUtil.getRsString(rs, i++, "");
                        c.startLat = FZUtil.getRsString(rs, i++, "");
                        c.endLon = FZUtil.getRsString(rs, i++, "");
                        c.endLat = FZUtil.getRsString(rs, i++, "");
                        c.startTime = FZUtil.getRsString(rs, i++, "");
                        c.endTime = FZUtil.getRsString(rs, i++, "");
                        c.source1 = FZUtil.getRsString(rs, i++, "");
                        c.weight = FZUtil.getRsString(rs, i++, "");
                        c.volume = FZUtil.getRsString(rs, i++, "");
                        c.branch = FZUtil.getRsString(rs, i++, "");
                        c.vehicle_type = FZUtil.getRsString(rs, i++, "");
                        c.included = FZUtil.getRsString(rs, i++, "");
                        c.costPerM = FZUtil.getRsString(rs, i++, "");
                        c.fixedCost = FZUtil.getRsString(rs, i++, "");
                        c.Channel = FZUtil.getRsString(rs, i++, "");
                        c.IdDriver = FZUtil.getRsString(rs, i++, "");
                        c.NamaDriver = FZUtil.getRsString(rs, i++, "");
                        ar.add(c);
                    }
                }
            }
        }
        catch (Exception e){
            ar = new ArrayList<Vehicle>();
            throw new Exception(e.getMessage());            
        }
        return ar;
    }
    
    public String isInsert(String vCode) throws Exception{
        String c = "ERROR";
        
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){            
                // create sql
                String sql ;
                sql = "select * FROM bosnet1.dbo.TMS_VehicleAtr where vehicle_code = '"+vCode+"';";
                
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    if (rs.next()){
                        c = "OK";
                    }
                }
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return c;
    }
    
        public List<Vehicle> getDriver(String str, String id) throws Exception{
        Vehicle c = new Vehicle();
        List<Vehicle> ar = new ArrayList<Vehicle>();
        
        String nt = "";
        if(id.length() > 0){
                nt = "WHERE       salesid = '" + id + "'\n";
        }
        
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){            
                // create sql
                String sql ;
                sql = "SELECT\n" +
                "	DISTINCT *\n" +
                "FROM\n" +
                "	(\n" +
                "		SELECT\n" +
                "			workplaceid,\n" +
                "			salesid,\n" +
                "			salesname\n" +
                "		FROM\n" +
                "			sysutil.IBACONSOL.dbo.BOSNET_FSR_TYPE\n" +
                "		WHERE\n" +
                "			TypeID = 10\n" +
                "			AND Active = 1\n" +
                "			AND SFA = 0\n" +
                "			AND WorkplaceId = '"+str+"'\n" +
                "	UNION ALL SELECT\n" +
                "			Branch COLLATE Database_Default AS workplaceid,\n" +
                "			Service_agent_id COLLATE Database_Default AS salesid,\n" +
                "			Driver_Name COLLATE Database_Default AS salesname\n" +
                "		FROM\n" +
                "			BOSNET1.dbo.TMS_ForwadingAgent\n" +
                "		WHERE\n" +
                "			Branch = '"+str+"'\n" +
                "			AND inc = 1\n" +
                "	) w\n" +
                nt;
                
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()){
                        c = new Vehicle();
                        c.IdDriver = rs.getString("salesid");
                        c.NamaDriver = rs.getString("salesname");
                        ar.add(c);
                    }
                }
            }
        }
        catch (Exception e){
            ar = new ArrayList<Vehicle>();
            throw new Exception(e.getMessage());            
        }
        return ar;
    }
}

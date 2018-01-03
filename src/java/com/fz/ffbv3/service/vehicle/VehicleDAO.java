/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.vehicle;

import com.fz.ffbv3.service.vehicle.Vehicle;
import com.fz.generic.Db;
import com.fz.util.FZUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
/**
 *
 * @author Eko
 */
public class VehicleDAO {
    
    public void save(Vehicle inp) throws Exception {
        
    }
    
    public Vehicle loadByID(String id) throws Exception {
        Vehicle v =  null;

        String sql = "select * from fbvehicle where VehicleID = ?" ;
        try (Connection con = (new Db()).getConnection("jdbc/fz");){

            try (PreparedStatement ps = con.prepareStatement(sql)){
                 ps.setString(1, id);
                 try(ResultSet rs = ps.executeQuery()){
                     if (rs.next()) {
                         v =  new Vehicle();
                         v.VehicleID =  rs.getString("VehicleID");
                         v.VehicleName = rs.getString("VehicleName");
                         v.Type = rs.getString("Type");
                         v.Weight = rs.getInt("Weight");
                         v.DefDivCode = rs.getString("DefDivCode");
                         v.DefDriverID = rs.getString("DefDriverID");
                         v.StartLocation = rs.getString("StartLocation");
                         v.StartTime = rs.getString("StartTime");
                         v.Status = rs.getString("Status");
                     }
                 }
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e) {
            throw e;
        }
        return v;
    }

    private Vehicle loadSelfAndChildren(
            Connection con, PreparedStatement psLoader
    ) 
            throws Exception {
        
        Vehicle v = null;
        try (ResultSet rs = psLoader.executeQuery()){

            // if has record
            if (rs.next()){

                v = new Vehicle();
                v.VehicleID = rs.getString("VehicleID");
                v.VehicleName = rs.getString("VehicleName");
                v.Type = rs.getString("Type");
                v.Weight = rs.getInt("Weight");
                v.DefDivCode = rs.getString("DefDivCode");
                v.DefDriverID = rs.getString("DefDriverID");
                v.StartLocation = rs.getString("StartLocation");
                v.StartTime = rs.getString("StartTime");
                v.Status = rs.getString("Status");

            }
            else { // else, no record
                // nothing
            }
        }
        return v;
    }
    
    public String findAvailVehicleID(String divID, String tanggal) {
        String VId = "";
        try {
            Vehicle v = findAvailVehicle(divID,tanggal);
            if (v!=null) VId=v.VehicleID;
        } catch (Exception ex) {
            String err = ex.getMessage();
        }
        return VId;
    }
    
    public ArrayList<Vehicle> getAvailVehicle(String divID, String tanggal) throws Exception {
        ArrayList<Vehicle> lv = new ArrayList<Vehicle>();
        Vehicle v = null;
        String sql = "select a.*\n" +
		",ifnull((select aa.JobSeq from fbjob aa \n" +
		"where aa.PlanTruckID=a.VehicleID \n" +
		"and cast(aa.assignedDt as date)='" + tanggal +"' " +
		"and aa.DoneStatus='AVLB'\n" +
		"order by JobSeq desc limit 1)\n" +
		",0) JSeq\n" +
                "from fbvehicle a\n" +
                " where a.DevDifCode='" + divID +"' " +
                "order by JSeq,a.VehicleID"; 
        try (Connection con = (new Db()).getConnection("jdbc/fz");){

            try (Statement stm = con.createStatement()){
                 
                 try(ResultSet rs = stm.executeQuery(sql)){
                     if (rs.next()) {
                         v =  setFromRS(rs);
                         lv.add(v);
                     }
                 }
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e) {
            throw e;
        }
        return lv;
    }

    public Vehicle findAvailVehicle(String divID, String tanggal) throws Exception {
        Vehicle v = null;
        String sql = "select a.*\n" +
		",ifnull((select aa.JobSeq from fbjob aa \n" +
		"where aa.PlanTruckID=a.VehicleID \n" +
		"and cast(aa.assignedDt as date)='" + tanggal +"' " +
		"and aa.DoneStatus='AVLB'\n" +
		"order by JobSeq desc limit 1)\n" +
		",0) JSeq\n" +
                "from fbvehicle a\n" +
                " where a.DevDifCode='" + divID +"' " +
                "order by JSeq,a.VehicleID\n" +
                "limit 1" ;
        try (Connection con = (new Db()).getConnection("jdbc/fz");){

            try (Statement stm = con.createStatement()){
                 
                 try(ResultSet rs = stm.executeQuery(sql)){
                     if (rs.next()) {
                         v =  setFromRS(rs);
                     }
                 }
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e) {
            throw e;
        }
        return v;
    }
    
    public Vehicle setFromRS(ResultSet rs) throws Exception {
        Vehicle v = new Vehicle();

        v.VehicleID = rs.getString("VehicleID");
        v.VehicleName = rs.getString("VehicleName");
        v.Type = rs.getString("Type");
        v.Weight = rs.getInt("Weight");
        v.DefDivCode = rs.getString("DefDivCode");
        v.DefDriverID = rs.getString("DefDriverID");
        v.StartLocation = rs.getString("StartLocation");
        v.StartTime = rs.getString("StartTime");
        v.Status = rs.getString("Status");

        return v;
    }
}

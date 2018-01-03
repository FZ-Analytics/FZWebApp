/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fz.ffbv3.service.vehicle2;

import com.fz.generic.Db;
import com.fz.util.FZUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Vehicle2DAO {
    
    public Vehicle2Record findByID(String vehicleID) throws Exception {
        
        List<Vehicle2Record> rs = findByCriteria(
                " vehicleID = " + vehicleID);
        if (rs.size() > 0){
            return (Vehicle2Record) rs.get(0);
        }
        else{
            return null;
        }
    }
    
    public List<Vehicle2Record> findAll(String vehicleID) throws Exception {
        
        return findByCriteria("");
    }
    
    public List<Vehicle2Record> findAllExceptTest() throws Exception {
        
        return findByCriteria(" defDivCode not like 'TEST%'");
    }
    
    private List<Vehicle2Record> findByCriteria(String where) throws Exception {
        
        if (where.trim().length() > 0) {
            where = " where " + where;
        }

        List<Vehicle2Record> vrs = new ArrayList<Vehicle2Record>();
        
        String sql = "select "
                + "\n v.vehicleID"
                + "\n, v.vehicleName"
                + "\n, v.status"
                + "\n, v.includeInRun"
                + "\n, v.defDivCode"
                + "\n, v.startLon"
                + "\n, v.startLat"
                + "\n, v.startTime"
                + "\n, v.remark"
                + "\n, v.startLocation"
                + "\n, v.lastRunID"
                + "\n, vehLastJob.jobID"
                + "\n, vehLastJob.doneStatus"
                + "\n from fbVehicle v"
                + "\n   inner join fbDiv d"
                + "\n       on v.defDivCode = d.divID"
                + "\n 	left outer join ("
                + "\n 			select "
                + "\n 				actualTruckID"
                + "\n 				, max(jobID) maxJobID"
                + "\n 				, count(jobID) jobCount"
                + "\n 			from fbjob"
                + "\n                   where hvsDt = curdate()"
                + "\n 				and doneStatus <> 'PLAN'"
                + "\n 				and reorderToJobID is null"
                + "\n 			group by actualTruckID "
                + "\n 		) vehJobSum on"
                + "\n 			v.vehicleID = vehJobSum.actualTruckID"
                + "\n 	left outer join fbJob vehLastJob"
                + "\n 			on vehJobSum.maxJobID = vehLastJob.jobID"
                + "\n " + where
                ;
        try (Connection con = (new Db()).getConnection("jdbc/fz");
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()){

            while (rs.next()){

                Vehicle2Record r = new Vehicle2Record();
                
                int i = 1;

                r.vehicleID = FZUtil.getRsString(rs, i++, "");
                r.vehicleName = FZUtil.getRsString(rs, i++, "");
                r.status = FZUtil.getRsString(rs, i++, "");
                r.includeInRun = FZUtil.getRsString(rs, i++, "");
                r.defDivCode = FZUtil.getRsString(rs, i++, "");
                r.startLon = FZUtil.getRsString(rs, i++, "");
                r.startLat = FZUtil.getRsString(rs, i++, "");
                r.startTime = FZUtil.getRsString(rs, i++, "");
                r.remark = FZUtil.getRsString(rs, i++, "");
                r.startLocation = FZUtil.getRsString(rs, i++, "");
                r.lastRunID = FZUtil.getRsString(rs, i++, "");
                r.lastJobID = FZUtil.getRsString(rs, i++, "");
                r.lastJobStatus = FZUtil.getRsString(rs, i++, "");

                vrs.add(r);
            }
        }
        return vrs;
    }
    
    public List<Vehicle2Record> findByMillAndToRun(String millID) throws Exception {
    
        String where = 
                "\n d.millID = '" + millID + "'"
                + "\n 	and (vehLastJob.doneStatus in ('DONE')"
                + "\n           or vehLastJob.doneStatus is null)"
                + "\n   and v.includeInRun = 'YES'"
                ;
        return this.findByCriteria(where);
        
//        List<Vehicle2Record> vrs = new ArrayList<Vehicle2Record>();
//        
//        String sql = "select "
//                + "\n v.vehicleID"
//                + "\n, v.vehicleName"
//                + "\n, v.status"
//                + "\n, v.includeInRun"
//                + "\n, v.defDivCode"
//                + "\n, v.startLon"
//                + "\n, v.startLat"
//                + "\n, v.startTime"
//                + "\n, v.remark"
//                + "\n, v.startLocation"
//                + "\n, v.lastRunID"
//                + "\n from fbVehicle v"
//                + "\n   inner join fbDiv d"
//                + "\n       on v.defDivCode = d.divID"
//                + "\n 	left outer join ("
//                + "\n 			select "
//                + "\n 				actualTruckID"
//                + "\n 				, max(jobID) maxJobID"
//                + "\n 				, count(jobID) jobCount"
//                + "\n 			from fbjob"
//                + "\n 				where hvsDt = curdate()"
//                + "\n 				and doneStatus <> 'PLAN'"
//                + "\n 			group by actualTruckID "
//                + "\n 		) vehJobSum on"
//                + "\n 			v.vehicleID = vehJobSum.actualTruckID"
//                + "\n 	left outer join fbJob vehLastJob"
//                + "\n 			on vehJobSum.maxJobID = vehLastJob.jobID"
//                + "\n where d.millID = '" + millID + "'"
//                + "\n 	and (vehLastJob.doneStatus in ('DONE')"
//                + "\n           or vehLastJob.doneStatus is null)"
//                + "\n   and v.includeInRun = 'YES'"
//                ;
//        try (Connection con = (new Db()).getConnection("jdbc/fz");
//                PreparedStatement ps = con.prepareStatement(sql);
//                ResultSet rs = ps.executeQuery()){
//
//            while (rs.next()){
//
//                Vehicle2Record r = new Vehicle2Record();
//                
//                int i = 1;
//
//                r.vehicleID = FZUtil.getRsString(rs, i++, "");
//                r.vehicleName = FZUtil.getRsString(rs, i++, "");
//                r.status = FZUtil.getRsString(rs, i++, "");
//                r.includeInRun = FZUtil.getRsString(rs, i++, "");
//                r.defDivCode = FZUtil.getRsString(rs, i++, "");
//                r.startLon = FZUtil.getRsString(rs, i++, "");
//                r.startLat = FZUtil.getRsString(rs, i++, "");
//                r.startTime = FZUtil.getRsString(rs, i++, "");
//                r.remark = FZUtil.getRsString(rs, i++, "");
//                r.startLocation = FZUtil.getRsString(rs, i++, "");
//                r.lastRunID = FZUtil.getRsString(rs, i++, "");
//
//                vrs.add(r);
//            }
//        }
//        return vrs;
    }
    
    public void save(Vehicle2Record r) throws Exception {
        
        if (r.vehicleID.length() == 0){
            // insert
            this.insert(r);
        }
        else {
            // update
            this.update(r);
        }
    } 

    private void insert(Vehicle2Record r) throws Exception {
        
        String sql = "insert into fbVehicle ("
                + "\n vehicleName"
                + "\n, status"
                + "\n, includeInRun"
                + "\n, defDivCode"
                + "\n, startLon"
                + "\n, startLat"
                + "\n, startTime"
                + "\n, remark"
                + "\n, startLocation"
                + "\n) "
                + " values("
                + "\n ?,?,?,?,?,?,?,?,?)"
                ;
        try (Connection con = (new Db()).getConnection("jdbc/fz");
                PreparedStatement ps = con.prepareStatement(sql)){
            
            int i = 1;

            ps.setString(i++, r.vehicleName);
            ps.setString(i++, r.status);
            ps.setString(i++, r.includeInRun);
            ps.setString(i++, r.defDivCode);
            ps.setString(i++, r.startLon);
            ps.setString(i++, r.startLat);
            ps.setString(i++, r.startTime);
            ps.setString(i++, r.remark);
            ps.setString(i++, r.startLocation);
            
            ps.executeUpdate();
        }

    }

    private void update(Vehicle2Record r) throws Exception {
        
        String sql = "update fbVehicle set"
                + "\n vehicleName = ?"
                + "\n, status = ?"
                + "\n, includeInRun = ?"
                + "\n, defDivCode = ?"
                + "\n, startLon = ?"
                + "\n, startLat = ?"
                + "\n, startTime = ?"
                + "\n, remark = ?"
                + "\n, startLocation = ?"
                + "\n, lastRunID = ?"
                + "\n where vehicleID = ?"
                ;
        try (Connection con = (new Db()).getConnection("jdbc/fz");
                PreparedStatement ps = con.prepareStatement(sql)){
            
            int i = 1;

            ps.setString(i++, r.vehicleName);
            ps.setString(i++, r.status);
            ps.setString(i++, r.includeInRun);
            ps.setString(i++, r.defDivCode);
            ps.setString(i++, r.startLon);
            ps.setString(i++, r.startLat);
            ps.setString(i++, r.startTime);
            ps.setString(i++, r.remark);
            ps.setString(i++, r.startLocation);
            ps.setString(i++, r.lastRunID);
            ps.setString(i++, r.vehicleID);
            
            ps.executeUpdate();
        }

    }

    void swap(String vehicleID1
            , String vehicleID2
            , String runID1
    ) throws Exception {
        
        try (Connection con = (new Db()).getConnection("jdbc/fz")){
            
            con.setAutoCommit(false);
            
            String sql = "update fbVehicle set "
                    + " includeInRun='YES'"
                    + ", status='AVLB'"
                    + ", lastRunID = '" + runID1 + "'"
                    + " where vehicleID = '" + vehicleID2 + "'";
            try (PreparedStatement ps = con.prepareStatement(sql)){
                ps.executeUpdate();
            }
            
            sql = "update fbVehicle set "
                    + " includeInRun='NO'"
                    + " where vehicleID = '" + vehicleID1 + "'";
            try (PreparedStatement ps = con.prepareStatement(sql)){
                ps.executeUpdate();
            }
            
            con.setAutoCommit(true);
        }
    }

    void updateRunID(String vehicleID, String newRunID) throws Exception {
        
        String sql = "update fbVehicle "
                + " set lastRunID = '" + newRunID + "'"
                + " , canGoHome = 0"
                + " where vehicleID = '" + vehicleID + "'"
                ;
        try (Connection con = (new Db()).getConnection("jdbc/fz");
                PreparedStatement ps = con.prepareStatement(sql)){
            
                ps.executeUpdate();
        }
    }
}

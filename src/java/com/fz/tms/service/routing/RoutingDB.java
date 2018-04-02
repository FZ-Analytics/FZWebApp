/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.service.routing;

import com.fz.generic.Db;
import com.fz.router.CustDelivery;
import com.fz.router.DeliveryAgent;
import com.fz.tms.model.Vehicle;
import com.fz.tms.model.vehicle_attr;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dwi.rangga
 */
public class RoutingDB {
    public static List<CustDelivery> getCustDelivery(String shift) throws Exception{
        List<CustDelivery> sr = new ArrayList<CustDelivery>();
        
        String sql = "select * from bosnet1.dbo.Tms_ShipmentPlan aw, bosnet1.dbo.customer ct "
                + "where aw.customer_id = ct.customer_id and aw.Already_Shipment = 'N' "
                + "And aw.NotUsed_Flag is null and shift = " + shift
                + "order by aw.DO_Number;";
        
        CustDelivery st = new CustDelivery();
        
        try (Connection con = (new Db()).getConnection("sap/tms")){
            
            try (Statement stm = con.createStatement()){
                try (ResultSet rs = stm.executeQuery(sql)){ 
                    while (rs.next()){
                        st = new CustDelivery();
                        st.custID = rs.getString("Customer_ID");
                        st.lon = new Double(cekString(rs.getString("Longitude")));
                        st.lat = new Double(cekString(rs.getString("Latitude")));
                        st.priority = new Integer(rs.getString("HighLevelBatch"));
                        st.serviceTime = rs.getInt("shift");
                        st.totalKg = new Double(cekString(rs.getString("Total_KG_Item")));
                        //st.timeWindowStart = rs.getString("DO_Number");
                        //st.timeWindowEnd = rs.getString("DO_Number");
                        st.totalAmount = new Double(cekString(rs.getString("Total_Amount")));
                        st.adminManMinuteSaved = 2;//rs.getInt("Customer_ID");
                        st.distChannelID = rs.getString("Sales_Distric");
                        st.custCategory = "c001";//rs.getString("DO_Number");
                        st.branchCode = "0321";//rs.getString("DO_Number");
                        st.subDistrict = rs.getString("Sales_Distric");
                        st.district = rs.getString("Distric");
                        st.address = rs.getString("street");
                        st.zipCode = rs.getString("postal_code");
                        st.custName = rs.getString("name1");
                        
                        sr.add(st);                       
                    } 
                    con.close();
                }catch (Exception e){
                    System.out.println(e.getMessage());
                    throw new Exception("Error parse param Tms_ShipmentPlan & customer SQL = " + sql, e);
                }
            }
        }
        catch (Exception e){            
            // re-throw the exception
            System.out.println(e.getMessage());
            throw new Exception("Error connect Tms_ShipmentPlan & customer. SQL = " + sql, e);
        }
        
        return sr;
    }
    
    public static List<DeliveryAgent> getDeliveryAgent() throws Exception{
        List<DeliveryAgent> sr = new ArrayList<DeliveryAgent>();
        
        try{
            List<vehicle_attr> va = (List<vehicle_attr>) getListvehicle_attr();
            List<Vehicle> v = (List<Vehicle>) getListVehicle();
            
            for(int a = 0; a<va.size();a++){
                for(int b = 0; b<v.size();b++){
                    if(va.get(a).getVe().Vehicle_Code.equalsIgnoreCase(v.get(b).Vehicle_Code)){
                        sr.add(va.get(a).getDa());                        
                    }
                }
            }
        }catch(Exception e){
            throw new Exception(e);
        }
        
        return sr;
    }
    
    public static String cekString(String str){
        String ds = "";
        if(str == null || str == "" || str.equalsIgnoreCase("n/a")){
            ds = "0";
        }else{
            ds = str;
        }
        return ds;
    }
    
    public static List<vehicle_attr> getListvehicle_attr() throws Exception{
        List<vehicle_attr> va = new ArrayList<vehicle_attr>();
        vehicle_attr v = new vehicle_attr();
        DeliveryAgent st = new DeliveryAgent();
        Vehicle g = new Vehicle();
        
        String sql = "select * from vehicle_attr;";
        try (Connection con = (new Db()).getConnection("sap/tms")){            
            try (Statement stm = con.createStatement()){
                try (ResultSet rs = stm.executeQuery(sql)){                    
                    while (rs.next()){
                        st = new DeliveryAgent();
                        st.agentID = rs.getString("agentID");
                        st.maxKg = rs.getDouble("weight");
                        st.agentType = rs.getString("agentType");
                        st.earliestDepartTime = rs.getString("earliestDepartTime");
                        st.latestArrivalTime = rs.getString("latestArrivalTime");
                        st.startLon = rs.getDouble("startLon");
                        st.startLat = rs.getDouble("startLat");
                        st.endLon = rs.getDouble("endLon");
                        st.endLat = rs.getDouble("endLat");
                        st.maxCubication = rs.getDouble("Total_Volume_Vehicle");
                        g.Vehicle_Code = rs.getString("Vehicle_Code");
                        v.setVe(g);
                        v.setDa(st);
                        va.add(v);
                    } 
                    con.close();
                }catch (Exception e){
                    System.out.println(e.getMessage());
                    throw new Exception("Error parse param vehicle_attr. SQL = " + sql, e);
                }
            }
        }
        catch (Exception e){            
            // re-throw the exception
            System.out.println(e.getMessage());
            throw new Exception("Error connect vehicle_attr. SQL = " + sql, e);
        }
        
        return va;
    }
    
    public static List<Vehicle> getListVehicle() throws Exception{
        List<Vehicle> va = new ArrayList<Vehicle>();
        Vehicle v = new Vehicle();
        
        String sql = "select * from bosnet1.dbo.Vehicle;";
        try (Connection con = (new Db()).getConnection("sap/tms")){            
            try (Statement stm = con.createStatement()){
                try (ResultSet rs = stm.executeQuery(sql)){  
                    while (rs.next()){  
                        v = new Vehicle();
                        v.Vehicle_Code = rs.getString("Vehicle_Code");
                        va.add((Vehicle) v);
                    } 
                    con.close();
                }catch (Exception e){
                    System.out.println(e.getMessage());
                    throw new Exception("Error parse param Vehicle. SQL = " + sql, e);
                }
            }
        }
        catch (Exception e){            
            // re-throw the exception
            System.out.println(e.getMessage());
            throw new Exception("Error connect Vehicle. SQL = " + sql, e);
        }
        
        return va;
    }
}

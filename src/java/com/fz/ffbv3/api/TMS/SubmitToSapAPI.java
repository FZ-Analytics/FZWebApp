/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.api.TMS;

import com.fz.generic.Db;
import com.fz.tms.params.model.ResultShipment;
import com.fz.tms.params.model.RunResultEditResultSubmitToSap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Administrator
 */
@Path("submitToSap")
public class SubmitToSapAPI {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of SubmitToSapResource
     */
    public SubmitToSapAPI() {
    }

    /**
     * Retrieves representation of an instance of
     * com.fz.ffbv3.Test.SubmitToSapResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of SubmitToSapResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }

    @POST
    @Path("submitToSap")
    @Produces(MediaType.APPLICATION_JSON)
    public String submitToSap(String content) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String ret = "";
        ResultShipment rs = new ResultShipment();
        try {
            RunResultEditResultSubmitToSap he = gson.fromJson(content.contains("json") ? decodeContent(content) : content, RunResultEditResultSubmitToSap.class);
            HashMap<String, String> hmPRV = getFromPreRouteVehicle(he.RunId, he.vehicle_no);
            ArrayList<String> alCustId = getCustomerId(he.RunId, he.vehicle_no);
            ArrayList<String> alStartAndEndTime = getStartAndEndTime(he.RunId, he.vehicle_no);
            for (int i = 0; i < alCustId.size(); i++) {
                ArrayList<HashMap<String, String>> alSP = getFromShipmentPlan(alCustId.get(i), getRdd(he.RunId, alCustId.get(i)));
                for(int j = 0; j < alSP.size(); j++) {
                    HashMap<String, String> hmSP = alSP.get(j); //HashMap Shipment Plan
                    
                    rs.Shipment_Type = hmPRV.get("source1");
                    rs.Plant = hmSP.get("Plant");
                    rs.Shipping_Type = "";
                    rs.Shipment_Route = hmSP.get("Route");
                    rs.Shipment_Number_Dummy = he.RunId.replace("_", "")+he.vehicle_no;
                    rs.Description = "";
                    rs.Status_Plan = parseRunId(he.RunId, true);
                    rs.Status_Check_In = null;
                    rs.Status_Load_Start = parseRunId(he.RunId, false) + " " + getArriveAndDepart(he.RunId, alCustId.get(i)).get("arrive");
                    rs.Status_Load_End = parseRunId(he.RunId, false) + " " + getArriveAndDepart(he.RunId, alCustId.get(i)).get("depart");
                    rs.Status_Complete = null;
                    rs.Status_Shipment_Start = parseRunId(he.RunId, false) + " " + alStartAndEndTime.get(0);
                    rs.Status_Shipment_End = parseRunId(he.RunId, false) + " " + alStartAndEndTime.get(1);
                    rs.Service_Agent_Id = hmPRV.get("IdDriver");
                    rs.No_Pol = he.vehicle_no;
                    rs.Driver_Name = hmPRV.get("NamaDriver");
                    rs.Delivery_Number = getDoNum(he.RunId, alCustId.get(i));
                    rs.Delivery_Item = hmSP.get("Item_Number");
                    rs.Delivery_Quantity_Split = 0.000;
                    rs.Delivery_Quantity = Double.parseDouble(hmSP.get("DOQty"));
                    rs.Delivery_Flag_Split = "";
                    rs.Material = hmSP.get("DOQtyUOM");
                    rs.Vehicle_Number = he.vehicle_no;
                    rs.Vehicle_Type = hmPRV.get("vehicle_type");
                    rs.Batch = hmSP.get("Batch");
                    rs.Time_Stamp = getTimeID();
                    rs.Shipment_Number_SAP = "";
                    rs.I_Status = "0";
                    rs.Shipment_Flag = "";
                    insertResultShipment(rs);
                }
            }

            ret = "OK";
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            ret = "ERROR";
        }
        String jsonOutput = gson.toJson(ret);
        return jsonOutput;
    }

    public static String decodeContent(String content) throws UnsupportedEncodingException {
        content = java.net.URLDecoder.decode(content, "UTF-8");
        content = content.substring(5);

        return content;
    }
    
    public Timestamp getTimeID() throws ParseException {
        String time = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
        Timestamp ts = Timestamp.valueOf(time);
        return ts;
    }
    
    public String parseRunId(String runId, boolean fullRunId) {
        String[] runIdSplit = runId.split("_");
        String date = runIdSplit[0];
        String time = runIdSplit[1];
        String y = "", m = "", d = "", h = "", min = "", s = "", ms = "";
        for(int i = 0; i < date.length(); i++) {
            if(i <= 3) {
                y += date.charAt(i);
            } else if (i >= 4 && i <= 5) {
                m += date.charAt(i);
            } else if(i >= 6) {
                d += date.charAt(i);
            }
        }
        if(fullRunId) {
            for(int i = 0; i < time.length(); i++) {
                if(i <= 1) {
                    h += time.charAt(i);
                } else if (i >= 2 && i <= 3) {
                    min += time.charAt(i);
                } else if(i >= 4 && i <= 5) {
                    s += time.charAt(i);
                } else if(i >= 6) {
                    ms += time.charAt(i);
                }
            }
            return y+"-"+m+"-"+d + " " + h + ":" + min + ":" + s + ":" + ms;
        } else {
            return y+"-"+m+"-"+d;
        }
    }

    public HashMap<String, String> getFromPreRouteVehicle(String runId, String vehicleNo) throws Exception {
        HashMap<String, String> hm = new HashMap<>();
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql;
                sql = "SELECT " +
                        "vehicle_type, " +
                        "IdDriver, " +
                        "NamaDriver, " +
                        "CASE " + 
                            "WHEN source1 = 'INT' THEN 'ZDSD' " +
                            "ELSE 'ZDSC' " +
                        "END as source1 " +
                        "FROM BOSNET1.dbo.TMS_PreRouteVehicle " +
                        "WHERE runID = '" + runId + "' and vehicle_code = '" + vehicleNo + "';";

                try (ResultSet rs = stm.executeQuery(sql)) {
                    while (rs.next()) {
                        hm.put("source1", rs.getString("source1"));
                        hm.put("vehicle_type", rs.getString("vehicle_type"));
                        hm.put("IdDriver", rs.getString("IdDriver"));
                        hm.put("NamaDriver", rs.getString("NamaDriver"));
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return hm;
    }

    public ArrayList<HashMap<String, String>> getFromShipmentPlan(String custId, String rdd) throws Exception {
        HashMap<String, String> hm = new HashMap<>();
        ArrayList<HashMap<String, String>> al = new ArrayList<>();
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql;
                sql = "SELECT Route, Item_Number, Plant, DOQty, DOQtyUOM, Batch FROM BOSNET1.dbo.TMS_ShipmentPlan "
                        + "WHERE Customer_ID = '" + custId + "' and Batch <> 'NULL' and Request_Delivery_Date = '" + rdd + "';";
                try (ResultSet rs = stm.executeQuery(sql)) {
                    while (rs.next()) {
                        hm.put("Route", rs.getString("Route"));
                        hm.put("Item_Number", rs.getString("Item_Number"));
                        hm.put("Plant", rs.getString("Plant"));
                        hm.put("DOQty", rs.getString("DOQty"));
                        hm.put("DOQtyUOM", rs.getString("DOQtyUOM"));
                        hm.put("Batch", rs.getString("Batch"));
                        
                        al.add(hm);
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return al;
    }
    
    public HashMap<String, String> getArriveAndDepart(String runId, String custId) throws Exception {
        HashMap<String, String> hm = new HashMap<>();
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql;
                sql = "SELECT arrive, depart FROM BOSNET1.dbo.TMS_RouteJob where runID = '" + runId + "' and customer_id = '" + custId + "';";
                try (ResultSet rs = stm.executeQuery(sql)) {
                    while (rs.next()) {
                        hm.put("arrive", rs.getString("arrive"));
                        hm.put("depart", rs.getString("depart"));
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return hm;
    }


    public ArrayList<String> getCustomerId(String runId, String vehicleCode) throws Exception {
        ArrayList<String> al = new ArrayList<>();
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql;
                sql = "SELECT customer_id FROM BOSNET1.dbo.TMS_RouteJob where runID = '" + runId + "' and vehicle_code = '" + vehicleCode + "' and customer_id != '';";
                try (ResultSet rs = stm.executeQuery(sql)) {
                    while (rs.next()) {
                        al.add(rs.getString("customer_id"));
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return al;
    }
    
    public String getDoNum(String runId, String custId) throws Exception {
        String doNum = "";
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql;
                sql = "SELECT TOP 1 DO_Number FROM BOSNET1.dbo.TMS_PreRouteJob where runID = '" + runId + "' and customer_id = '" + custId + "';";
                try (ResultSet rs = stm.executeQuery(sql)) {
                    while (rs.next()) {
                        doNum = rs.getString("DO_Number");
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return doNum;
    }
    
     public ArrayList<String> getStartAndEndTime(String runId, String vehicleCode) throws Exception {
        ArrayList<String> al = new ArrayList<>();
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql;
                sql = "SELECT arrive, depart FROM BOSNET1.dbo.TMS_RouteJob where runID = '" + runId + "' and vehicle_code = '" + vehicleCode + "' and customer_id = '';";
                try (ResultSet rs = stm.executeQuery(sql)) {
                    int i = 0;
                    while (rs.next()) {
                        if(i == 0) {
                            al.add(rs.getString("depart"));
                        } else {
                            al.add(rs.getString("arrive"));
                        }
                        i++;
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return al;
    }
    
    public String getRdd(String runId, String custId) throws Exception {
        String rdd = "";
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql;
                sql = "SELECT TOP 1 Request_Delivery_Date FROM BOSNET1.dbo.TMS_PreRouteJob where runID = '" + runId + "' and customer_id = '" + custId + "';";
                try (ResultSet rs = stm.executeQuery(sql)) {
                    while (rs.next()) {
                        rdd = rs.getString("Request_Delivery_Date");
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return rdd;
    }

    public void insertResultShipment(ResultShipment rs) throws Exception {
        String sql = "INSERT INTO bosnet1.dbo.TMS_Result_Shipment "
                + "(Shipment_Type, Plant, Shipping_Type, Shipment_Route, Shipment_Number_Dummy, Description, Status_Plan, Status_Check_In, Status_Load_Start, Status_Load_End, "
                + "Status_Complete, Status_Shipment_Start, Status_Shipment_End, Service_Agent_Id, No_Pol, Driver_Name, Delivery_Number, Delivery_Item, Delivery_Quantity_Split, "
                + "Delivery_Quantity, Delivery_Flag_Split, Material, Batch, Vehicle_Number, Vehicle_Type, Time_Stamp, Shipment_Number_SAP, I_Status, Shipment_Flag) "
                + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

        try (Connection con = (new Db()).getConnection("jdbc/fztms"); PreparedStatement psHdr = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            psHdr.setString(1, rs.Shipment_Type);
            psHdr.setString(2, rs.Plant);
            psHdr.setString(3, rs.Shipping_Type);
            psHdr.setString(4, rs.Shipment_Route);
            psHdr.setString(5, rs.Shipment_Number_Dummy);
            psHdr.setString(6, rs.Description);
            psHdr.setString(7, rs.Status_Plan);
            psHdr.setString(8, rs.Status_Check_In);
            psHdr.setString(9, rs.Status_Load_Start);
            psHdr.setString(10, rs.Status_Load_End);
            psHdr.setString(11, rs.Status_Complete);
            psHdr.setString(12, rs.Status_Shipment_Start);
            psHdr.setString(13, rs.Status_Shipment_End);
            psHdr.setString(14, rs.Service_Agent_Id);
            psHdr.setString(15, rs.No_Pol);
            psHdr.setString(16, rs.Driver_Name);
            psHdr.setString(17, rs.Delivery_Number);
            psHdr.setString(18, rs.Delivery_Item);
            psHdr.setDouble(19, rs.Delivery_Quantity_Split);
            psHdr.setDouble(20, rs.Delivery_Quantity);
            psHdr.setString(21, rs.Delivery_Flag_Split);
            psHdr.setString(22, rs.Material);
            psHdr.setString(23, rs.Batch);
            psHdr.setString(24, rs.Vehicle_Number);
            psHdr.setString(25, rs.Vehicle_Type);
            psHdr.setTimestamp(26, rs.Time_Stamp);
            psHdr.setString(27, rs.Shipment_Number_SAP);
            psHdr.setString(28, rs.I_Status);
            psHdr.setString(29, rs.Shipment_Flag);

            psHdr.executeUpdate();
        }
    }
}
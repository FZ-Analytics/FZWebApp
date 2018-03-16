/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.api.TMS;

import com.fz.generic.Db;
import com.fz.tms.params.model.ResultShipment;
import com.fz.tms.params.model.RunResultEditResultSubmitToSap;
import static com.fz.tms.service.run.LoadDelivery.calcMeterDist;
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

    String runId = "";
    String oriRunId = "";
    String vNo = "";
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
        String ret = "OK";
        ResultShipment rs = new ResultShipment();
        try {
            RunResultEditResultSubmitToSap he = gson.fromJson(content.contains("json") ? decodeContent(content) : content, RunResultEditResultSubmitToSap.class);
            vNo = he.vehicle_no;
            runId = he.runId;
            oriRunId = he.oriRunId;

            HashMap<String, String> hmPRV = getFromPreRouteVehicle(runId, he.vehicle_no);
            ArrayList<String> alCustId = getCustomerId(runId, he.vehicle_no);
            ArrayList<String> alStartAndEndTime = getStartAndEndTime(runId, he.vehicle_no);
            Timestamp time = getTimeID();
            String route = getLongestRoute(alCustId, he.vehicle_no, runId);
            boolean isAlreadyOnce = false;

            //check if any route is null
            boolean isRouteNull = false;
            for (int i = 0; i < alCustId.size(); i++) {
                if (getRoute(alCustId.get(i)) == null) {
                    ret = "Aborted: One of the route is empty on Customer ID: " + alCustId.get(i);
                    isRouteNull = true;
                    break;
                }
            }

            if (isRouteNull == false) {
                for (int i = 0; i < alCustId.size(); i++) {
                    ArrayList<HashMap<String, String>> alSP = getFromShipmentPlan(runId, alCustId.get(i));

                    for (int j = 0; j < alSP.size(); j++) {
                        HashMap<String, String> hmSP = alSP.get(j); //HashMap Shipment Plan

                        rs.Shipment_Type = hmPRV.get("source1");
                        rs.Plant = hmSP.get("Plant");
                        rs.Shipment_Route = route;
                        rs.Description = "";
                        rs.Status_Plan = parseRunId(runId, true);
                        rs.Status_Check_In = null;
                        rs.Status_Load_Start = null;
                        rs.Status_Load_End = null;
                        rs.Status_Complete = null;
                        rs.Status_Shipment_Start = parseRunId(runId, false) + " " + alStartAndEndTime.get(0);
                        rs.Status_Shipment_End = parseRunId(runId, false) + " " + alStartAndEndTime.get(1);
                        rs.Service_Agent_Id = hmPRV.get("IdDriver");
                        if (rs.Shipment_Type.equals("ZDSI")) {
                            rs.Shipment_Number_Dummy = runId.replace("_", "") + he.vehicle_no;
                            rs.No_Pol = he.vehicle_no;
                            rs.Driver_Name = hmPRV.get("NamaDriver");
                            rs.Vehicle_Number = he.vehicle_no;
                        } else {
                            rs.Shipment_Number_Dummy = runId.replace("_", "") + getVendorId(he.vehicle_no);
                            rs.No_Pol = getExtVehicleType(he.vehicle_no);
                            rs.Driver_Name = getVendorName(he.vehicle_no);
                            rs.Vehicle_Number = getExtVehicleType(he.vehicle_no);
                            vNo = getVendorId(he.vehicle_no);
                        }

                        rs.Delivery_Number = hmSP.get("DO_Number");
                        rs.Delivery_Item = hmSP.get("Item_Number");
                        rs.Delivery_Quantity_Split = 0.000;
                        rs.Delivery_Quantity = Double.parseDouble(hmSP.get("DOQty"));
                        rs.Delivery_Flag_Split = "";
                        rs.Material = hmSP.get("Product_ID");
                        rs.Vehicle_Type = hmPRV.get("vehicle_type");
                        rs.Batch = hmSP.get("Batch");
                        rs.Time_Stamp = time;
                        rs.Shipment_Number_SAP = "";
                        rs.I_Status = "0";
                        rs.Shipment_Flag = "";
                        if (isAlreadyOnce == false) {
                            rs.distance = "" + getTotalDist(runId, he.vehicle_no);
                            isAlreadyOnce = true;
                        } else {
                            rs.distance = null;
                        }
                        rs.distanceUnit = "M";

                        insertResultShipment(rs);
                    }
                    if (!ret.equals("OK")) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        String jsonOutput = gson.toJson(ret);
        return jsonOutput;
    }

    public static String decodeContent(String content) throws UnsupportedEncodingException {
        content = java.net.URLDecoder.decode(content, "UTF-8");
        content = content.substring(5);

        return content;
    }

    public String getVendorId(String v) {
        String[] vSplit = v.split("_");
        return vSplit[1] + vSplit[3];
    }

    public String getExtVehicleType(String v) {
        String[] vSplit = v.split("_");
        return vSplit[2];
    }

    public String getVendorName(String v) {
        String[] vSplit = v.split("_");
        return vSplit[0];
    }

    public Timestamp getTimeID() throws ParseException {
        String time = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
        Timestamp ts = Timestamp.valueOf(time);
        return ts;
    }

    public String reFormatLongLat(String longlat) {
        String ret = "";
        //Sometimes long lat use "," instead of "."
        if (!longlat.contains(",")) {
            ret = longlat;
        } else {
            ret = longlat.replaceAll(",", ".");
        }
        return ret;
    }

    public String parseRunId(String runId, boolean fullRunId) {
        String[] runIdSplit = runId.split("_");
        String date = runIdSplit[0];
        String time = runIdSplit[1];
        String y = "", m = "", d = "", h = "", min = "", s = "", ms = "";
        for (int i = 0; i < date.length(); i++) {
            if (i <= 3) {
                y += date.charAt(i);
            } else if (i >= 4 && i <= 5) {
                m += date.charAt(i);
            } else if (i >= 6) {
                d += date.charAt(i);
            }
        }
        if (fullRunId) {
            for (int i = 0; i < time.length(); i++) {
                if (i <= 1) {
                    h += time.charAt(i);
                } else if (i >= 2 && i <= 3) {
                    min += time.charAt(i);
                } else if (i >= 4 && i <= 5) {
                    s += time.charAt(i);
                } else if (i >= 6) {
                    ms += time.charAt(i);
                }
            }
            return y + "-" + m + "-" + d + " " + h + ":" + min + ":" + s + ":" + ms;
        } else {
            return y + "-" + m + "-" + d;
        }
    }

    public HashMap<String, String> getFromPreRouteVehicle(String runId, String vehicleNo) throws Exception {
        HashMap<String, String> hm = new HashMap<>();
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql;
                sql = "SELECT "
                        + "vehicle_type, "
                        + "IdDriver, "
                        + "NamaDriver, "
                        + "CASE "
                        + "WHEN source1 = 'INT' THEN 'ZDSI' "
                        + "ELSE 'ZDSH' "
                        + "END as source1 "
                        + "FROM BOSNET1.dbo.TMS_PreRouteVehicle "
                        + "WHERE runID = '" + runId + "' and vehicle_code = '" + vehicleNo + "';";

                try (ResultSet rs = stm.executeQuery(sql)) {
                    while (rs.next()) {
                        hm.put("source1", rs.getString("source1"));
                        hm.put("vehicle_type", rs.getString("vehicle_type"));
                        hm.put("IdDriver", rs.getString("IdDriver"));
                        if (rs.getString("NamaDriver") == null) {
                            hm.put("NamaDriver", "");
                        } else {
                            hm.put("NamaDriver", rs.getString("NamaDriver"));
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return hm;
    }

    public ArrayList<HashMap<String, String>> getFromShipmentPlan(String runId, String custId) throws Exception {
        ArrayList<HashMap<String, String>> al = new ArrayList<>();
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql;
                sql = "SELECT DISTINCT\n"
                        + "     sp.DO_Number,\n"
                        + "     sp.Route,\n"
                        + "     sp.Item_Number,\n"
                        + "     sp.Plant,\n"
                        + "     sp.DOQty,\n"
                        + "     sp.Product_ID,\n"
                        + "     sp.Batch,\n"
                        + "     sp.NotUsed_Flag,\n"
                        + "     sp.Incoterm,\n"
                        + "     sp.Order_Type,\n"
                        + "     sp.Create_Date,\n"
                        + "     rj.arrive,\n"
                        + "     rj.depart\n"
                        + "FROM\n"
                        + "     [BOSNET1].[dbo].[TMS_ShipmentPlan] sp\n"
                        + "INNER JOIN (\n"
                        + "	SELECT\n"
                        + "		rj.customer_id,\n"
                        + "		rj.arrive,\n"
                        + "		rj.depart\n"
                        + "	FROM\n"
                        + "		[BOSNET1].[dbo].[TMS_RouteJob] rj\n"
                        + "	WHERE\n"
                        + "		rj.runId = '" + runId + "'\n"
                        + "		AND rj.Customer_ID = '" + custId + "') rj ON rj.customer_id = sp.Customer_ID\n"
                        + "INNER JOIN (\n"
                        + "	SELECT DISTINCT\n"
                        + "		prj.Request_Delivery_Date\n"
                        + "	FROM \n"
                        + "		[BOSNET1].[dbo].[TMS_PreRouteJob] prj\n"
                        + "	WHERE \n"
                        + "		prj.runId = '" + runId + "'\n"
                        + "             AND prj.Customer_ID = '" + custId + "') prj ON sp.Request_Delivery_Date = prj.Request_Delivery_Date\n"
                        + "WHERE\n"
                        + "         sp.Customer_ID = '" + custId + "'\n"
                        + "         AND sp.Already_Shipment <> 'Y'\n"
                        + "         AND sp.Batch <> 'NULL'\n"
                        + "         AND sp.NotUsed_Flag is NULL\n"
                        + "         AND sp.incoterm = 'FCO'\n"
                        + "         AND(\n"
                        + "		sp.Order_Type = 'ZDCO'\n"
                        + "		OR sp.Order_Type = 'ZDTO'\n"
                        + "         )\n"
                        + "         AND sp.create_date >= DATEADD(\n"
                        + "		DAY,\n"
                        + "		- 7,\n"
                        + "		GETDATE()\n"
                        + "         )\n"
                        + "ORDER BY\n"
                        + "         sp.DO_Number";

                try (ResultSet rs = stm.executeQuery(sql)) {
                    while (rs.next()) {
                        HashMap<String, String> hm = new HashMap<>();
                        hm.put("arrive", rs.getString("arrive"));
                        hm.put("depart", rs.getString("depart"));
                        hm.put("DO_Number", rs.getString("DO_Number"));
                        hm.put("Route", rs.getString("Route"));
                        hm.put("Item_Number", rs.getString("Item_Number"));
                        hm.put("Plant", rs.getString("Plant"));
                        hm.put("DOQty", rs.getString("DOQty"));
                        hm.put("Product_ID", rs.getString("Product_ID"));
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

    public String getLongestRoute(ArrayList<String> alCustId, String vNo, String runId) throws Exception {
        double longestDist = 0;
        String custId = "";
        for (int i = 0; i < alCustId.size(); i++) {
            HashMap<String, String> hmLongLat = getLongLat(alCustId.get(i), runId, vNo);
            double distance1 = calcMeterDist(Double.parseDouble(hmLongLat.get("Long")), Double.parseDouble(hmLongLat.get("Lat")), Double.parseDouble(hmLongLat.get("Long")), Double.parseDouble(hmLongLat.get("startLat")));
            double distance2 = calcMeterDist(Double.parseDouble(hmLongLat.get("Long")), Double.parseDouble(hmLongLat.get("startLat")), Double.parseDouble(hmLongLat.get("startLon")), Double.parseDouble(hmLongLat.get("startLat")));
            double temp = distance1 + distance2;
            if (temp > longestDist) {
                longestDist = temp;
                custId = alCustId.get(i);
            }
        }
        return getRoute(custId);
    }

    public double getTotalDist(String runId, String vehicleCode) throws Exception {
        double totDist = 0;
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql;
                sql = "SELECT Dist FROM BOSNET1.dbo.TMS_RouteJob WHERE runID = '" + runId + "' and vehicle_code = '" + vehicleCode + "';";
                try (ResultSet rs = stm.executeQuery(sql)) {
                    while (rs.next()) {
                        totDist += Math.round(rs.getDouble("Dist"));
                    }
                }
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
        return totDist;
    }

    public String getRoute(String custId) throws Exception {
        String route = "";
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql;
                sql = "SELECT DISTINCT Route FROM BOSNET1.dbo.TMS_ShipmentPlan WHERE Customer_ID = '" + custId + "';";
                try (ResultSet rs = stm.executeQuery(sql)) {
                    while (rs.next()) {
                        route = rs.getString("Route");
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return route;
    }

    public HashMap<String, String> getLongLat(String custId, String runId, String vNo) throws Exception {
        HashMap<String, String> hmLongLat = new HashMap<>();
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql;
                sql = "SELECT DISTINCT "
                        + "     prj.Long, "
                        + "     prj.Lat, "
                        + "     (SELECT "
                        + "         prv.startLon "
                        + "     FROM "
                        + "         BOSNET1.dbo.TMS_PreRouteVehicle prv "
                        + "     WHERE "
                        + "         prv.RunID = '" + runId + "' "
                        + "         and prv.vehicle_code = '" + vNo + "') startLon, "
                        + "     (SELECT "
                        + "         prv.startLat "
                        + "     FROM "
                        + "         BOSNET1.dbo.TMS_PreRouteVehicle prv "
                        + "     WHERE "
                        + "         prv.RunID = '" + runId + "' "
                        + "         and prv.vehicle_code = '" + vNo + "') startLat "
                        + "FROM "
                        + "     BOSNET1.dbo.TMS_PreRouteJob prj "
                        + "WHERE "
                        + "     prj.RunID = '" + runId + "' "
                        + "     and prj.Customer_ID = '" + custId + "';";
                try (ResultSet rs = stm.executeQuery(sql)) {
                    while (rs.next()) {
                        hmLongLat.put("Long", reFormatLongLat(rs.getString("Long")));
                        hmLongLat.put("Lat", reFormatLongLat(rs.getString("Lat")));
                        hmLongLat.put("startLon", reFormatLongLat(rs.getString("startLon")));
                        hmLongLat.put("startLat", reFormatLongLat(rs.getString("startLat")));
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("OUCH: " + e.getMessage());
        }
        return hmLongLat;
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

    public ArrayList<String> getStartAndEndTime(String runId, String vehicleCode) throws Exception {
        ArrayList<String> al = new ArrayList<>();
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql;
                sql = "SELECT arrive, depart FROM BOSNET1.dbo.TMS_RouteJob where runID = '" + runId + "' and vehicle_code = '" + vehicleCode + "' and customer_id = '';";
                try (ResultSet rs = stm.executeQuery(sql)) {
                    int i = 0;
                    while (rs.next()) {
                        if (i == 0) {
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

    public String insertResultShipment(ResultShipment rs) throws Exception {
        int rowNum = 0;
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql = "SELECT "
                        + "   COUNT(*) total "
                        + "FROM "
                        + "   bosnet1.dbo.TMS_Result_Shipment "
                        + "WHERE "
                        + "   Delivery_Number = '" + rs.Delivery_Number + "'"
                        + "   AND Delivery_Item = '" + rs.Delivery_Item + "';";
                try (ResultSet rst = stm.executeQuery(sql)) {
                    while (rst.next()) {
                        rowNum += rst.getInt("total");
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        //It means current DO is failed to be submitted to SAP, the row at Result Shipment and Status Shipment will be deleted
        if (rowNum > 0) {
            deleteFromResultShipment(rs);
            deleteFromStatusShipment(rs);
        }

        String ret = "error";
        String sql = "INSERT INTO bosnet1.dbo.TMS_Result_Shipment "
                + "(Shipment_Type, Plant, Shipping_Type, Shipment_Route, Shipment_Number_Dummy, Description, Status_Plan, Status_Check_In, Status_Load_Start, Status_Load_End, "
                + "Status_Complete, Status_Shipment_Start, Status_Shipment_End, Service_Agent_Id, No_Pol, Driver_Name, Delivery_Number, Delivery_Item, Delivery_Quantity_Split, "
                + "Delivery_Quantity, Delivery_Flag_Split, Material, Batch, Vehicle_Number, Vehicle_Type, Time_Stamp, Shipment_Number_SAP, I_Status, Shipment_Flag, Distance, Distance_Unit) "
                + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

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
            psHdr.setString(30, rs.distance);
            psHdr.setString(31, rs.distanceUnit);

            psHdr.executeUpdate();

            ret = "ok";
        }
        return ret;
    }

    public int deleteFromStatusShipment(ResultShipment rs) {
        int ret = 0;
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql
                        = "DELETE "
                        + "FROM "
                        + "   bosnet1.dbo.TMS_Status_Shipment "
                        + "WHERE "
                        + "   Delivery_Number = '" + rs.Delivery_Number + "'"
                        + "   AND Delivery_Item = '" + rs.Delivery_Item + "';";
                ret = stm.executeUpdate(sql);
            }
        } catch (Exception e) {

        }
        return ret;
    }

    public int deleteFromResultShipment(ResultShipment rs) {
        int ret = 0;
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql
                        = "DELETE "
                        + "FROM "
                        + "   bosnet1.dbo.TMS_Result_Shipment "
                        + "WHERE "
                        + "   Delivery_Number = '" + rs.Delivery_Number + "'"
                        + "   AND Delivery_Item = '" + rs.Delivery_Item + "';";
                stm.executeQuery(sql);
            }
        } catch (Exception e) {

        }
        return ret;
    }
}
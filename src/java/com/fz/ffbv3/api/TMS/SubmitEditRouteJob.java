/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.api.TMS;

import com.fz.generic.Db;
import com.fz.tms.params.model.Delivery;
import com.fz.tms.params.model.RouteJobLog;
import static com.fz.tms.service.run.RouteJobListingResultEdit.getTimeStamp;
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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
@Path("submitEditRouteJob")
public class SubmitEditRouteJob {

    List<Delivery> ld = new ArrayList<>();

    String prevCustId = "";
    String previousCustId = "";
    String prevDepart = "";
    double long1, lat1, long2, lat2;
    boolean b = true;

    ArrayList<RouteJobLog> arlistR = new ArrayList<>();
    ArrayList<RouteJobLog> arlistOriR = new ArrayList<>();
    String prevVehiCode = "";
    int routeNb = 0;
    int jobNb = 1;

    String oriRunId, runId, branch, shift, dateDeliv;

    boolean hasBreak = false;

    double speedTruck, trafficFactor;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of SubmitEditRouteJob
     */
    public SubmitEditRouteJob() {
    }

    /**
     * Retrieves representation of an instance of
     * com.fz.ffbv3.api.TMS.SubmitEditRouteJob
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
     * PUT method for updating or creating an instance of SubmitEditRouteJob
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }

    @POST
    @Path("submitEditRouteJob")
    @Produces(MediaType.APPLICATION_JSON)
    public String submitEditRouteJob(String content) throws Exception {
        ArrayList<Double> alParam = getParam();
        speedTruck = alParam.get(0);
        trafficFactor = alParam.get(1);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String ret = "OK";
        try {
            String[] tableArrSplit = decodeContent(content);

            getRunIdAndOriRunId(tableArrSplit[tableArrSplit.length - 1]);
            for (int i = 0; i < tableArrSplit.length - 1; i++) {
                System.out.println(i + tableArrSplit[i]);
                String str = tableArrSplit[i];
                String data = str;
                if (i != 0) {
                    data = str.substring(0, 0) + str.substring(0 + 1);
                }
                String[] dataSplit = data.split(",");
                switch (dataSplit.length) {
                    case 3: //normal row or start
                        if (!dataSplit[2].equals("start")) {
                            setObjectValue(dataSplit[0], dataSplit[1], dataSplit[2], "");
                        } else {
                            setObjectValue(dataSplit[0], dataSplit[1], "", getVehiStart(dataSplit[1]));
                            prevCustId = dataSplit[1];
                        }
                        break;
                    case 2: //end row
                        setObjectValue(dataSplit[0], dataSplit[1], "", "");
                        break;
                    default: //truck break row
                        setObjectValue("", "", "", "");
                        break;
                }
            }
            updateRouteJob(arlistR, runId);
        } catch (Exception e) {
            System.out.println("ERROR" + e.getLocalizedMessage());
            ret = "ERROR";
        }
        String jsonOutput = gson.toJson(ret);
        return jsonOutput;
    }

    public static String[] decodeContent(String content) throws UnsupportedEncodingException {
        content = java.net.URLDecoder.decode(content, "UTF-8");
        content = content.substring(16);
        String[] contentSplit = content.split("split");
        return contentSplit;
    }

    public void getRunIdAndOriRunId(String arr) {
        String[] runIdArr = arr.split(",");
        String[] runIdSplit = runIdArr[1].split(":");
        String[] oriRunIdSplit = runIdArr[2].split(":");
        String[] branchSplit = runIdArr[3].split(":");
        String[] shiftSplit = runIdArr[4].split(":");
        runId = runIdSplit[1].replaceAll("\"", "");
        oriRunId = oriRunIdSplit[1].replaceAll("\"", "").replace("}", "");
        branch = branchSplit[1].replaceAll("\"", "");
        shift = shiftSplit[1].replaceAll("\"", "").replace("}", "");
    }

    public void setObjectValue(String no, String vNo, String custId, String depart) throws Exception {
        ArrayList<String> a = getRouteData(custId);
        String[] aSplit = a.get(0).split("split");
        //not a break-time row
        if (!vNo.equals("")) {
            Delivery d = new Delivery();
            if (!vNo.equals("NA")) {
                d.no = no;
            }
            d.vehicleCode = vNo;
            d.custId = custId;
            d.doNum = aSplit[0];
            d.serviceTime = aSplit[4];
            d.storeName = aSplit[5];
            d.priority = aSplit[1];
            d.distChannel = aSplit[7];
            d.street = aSplit[6];
            d.weight = "" + Math.round(Double.parseDouble(aSplit[9]) * 10) / 10.0;

            String[] doNumSplit = d.doNum.split(";");

            try {
                d.volume = "" + Math.round(Double.parseDouble(getVolume(custId, oriRunId)) * 1) / 1000000.0;
            } catch (Exception e) {
            }
            d.rdd = aSplit[8];
            if (!custId.equals("") || depart.equals("")) {
                d.arrive = prevDepart;
            }
            //start row
            if (d.custId.equals("")) {
                d.depart = depart;
            } else {
                try {
                    d.depart = addTime(d.arrive, Integer.parseInt(d.serviceTime));
                } catch (Exception e) {

                }
            }

            //set long lat of store name
            if (!d.vehicleCode.equals("NA")) {
                //start row
                if (!d.vehicleCode.equals("") && d.custId.equals("") && !d.depart.equals("")) {
                    previousCustId = d.vehicleCode;
                    b = false;
                } //normal row
                else if (!d.vehicleCode.equals("") && !d.custId.equals("") && !d.doNum.equals("")) {
                    //previousCustId is normal row
                    if (previousCustId.matches("[0-9]+")) {
                        String[] longlat1 = getLongLatCustomer(oriRunId, previousCustId).split("split");
                        d.lon1 = reFormatLongLat(longlat1[0]);
                        d.lat1 = reFormatLongLat(longlat1[1]);
                    } //previousCustId is a start row
                    else {
                        try {
                            String[] longlat1 = getLongLatVehicle(previousCustId).split("split");
                            d.lon1 = reFormatLongLat(longlat1[0]);
                            d.lat1 = reFormatLongLat(longlat1[1]);
                        } catch (Exception e) {
                        }
                    }
                    try {
                        String[] longlat2 = getLongLatCustomer(oriRunId, d.custId).split("split");
                        d.lon2 = reFormatLongLat(longlat2[0]);
                        d.lat2 = reFormatLongLat(longlat2[1]);
                        previousCustId = d.custId;
                    } catch (Exception e) {
                    }
                } //end row
                else if (!d.vehicleCode.equals("") && d.custId.equals("") && d.depart.equals("")) {
                    try {
                        String[] longlat1 = getLongLatCustomer(oriRunId, previousCustId).split("split");
                        d.lon1 = reFormatLongLat(longlat1[0]);
                        d.lat1 = reFormatLongLat(longlat1[1]);
                        String[] longlat2 = getLongLatVehicle(d.vehicleCode).split("split");
                        d.lon2 = reFormatLongLat(longlat2[0]);
                        d.lat2 = reFormatLongLat(longlat2[1]);
                        b = true;
                    } catch (Exception e) {
                    }
                }
                //set arrive, depart, distance using lon lat of store
                if (!custId.equals("") || depart.equals("")) {
                    //Manhattan
                    if (getTripCalc(oriRunId).equals("M")) {
                        try {
                            double distance1 = calcMeterDist(Double.parseDouble(d.lon1), Double.parseDouble(d.lat1), Double.parseDouble(d.lon1), Double.parseDouble(d.lat2));
                            double distance2 = calcMeterDist(Double.parseDouble(d.lon1), Double.parseDouble(d.lat2), Double.parseDouble(d.lon2), Double.parseDouble(d.lat2));
                            d.arrive = "" + addTime(prevDepart, Math.round(trafficFactor * calcTripMinutes(distance1 + distance2, speedTruck)));

                            d.dist = "" + Math.round(((distance1 + distance2) / 1000) * 10) / 10.0;
                            d.transportCost = (int) Math.round(getCostPerM(d.vehicleCode, oriRunId) * (distance1 + distance2));
                        } catch (Exception e) {
                        }
                    } //Google
                    else {
                        try {
                            ArrayList<Double> alDurDist = getDistDurByGoogle(d.lon1, d.lat1, d.lon2, d.lat2);
                            double distance = alDurDist.get(0);
                            d.arrive = addTime(prevDepart, Math.round(alDurDist.get(1)));
                            d.dist = "" + Math.round((distance / 1000) * 10) / 10.0;
                            d.transportCost = (int) ((int) Math.round((getCostPerM(d.vehicleCode, oriRunId) * distance) * 10) / 10.0);
                        } catch (Exception e) {
                        }
                    }
                }
                if (d.custId.equals("")) {
                    d.depart = depart;
                } else {
                    try {
                        d.depart = addTime(d.arrive, Integer.parseInt(d.serviceTime));
                    } catch (Exception e) {

                    }
                }
            }
            try {
                if (hasBreak) {
                    d.feasibleTruck = isTimeinRange(d.arrive, getTruckTime(runId, d.vehicleCode));
                    ArrayList<String> al = getCustomerTime(runId, d.custId);
                    String custEndTime = addTime(al.get(1), 60);
                    al.set(1, custEndTime);
                    d.feasibleCustomer = isTimeinRange(d.arrive, al);

                    String vehicleType = getVehicleType(runId, d.vehicleCode);
                    String vehicleTypeList = getAccessList(d.custId, runId);
                    if (vehicleTypeList.toLowerCase().contains(vehicleType.toLowerCase())) {
                        d.feasibleAccess = "Yes";
                    } else {
                        d.feasibleAccess = "No";
                    }
                } else {
                    d.feasibleTruck = isTimeinRange(d.arrive, getTruckTime(runId, d.vehicleCode));
                    d.feasibleCustomer = isTimeinRange(d.arrive, getCustomerTime(runId, d.custId));

                    String vehicleType = getVehicleType(runId, d.vehicleCode);
                    String vehicleTypeList = getAccessList(d.custId, runId);
                    if (vehicleTypeList.toLowerCase().contains(vehicleType.toLowerCase())) {
                        d.feasibleAccess = "Yes";
                    } else {
                        d.feasibleAccess = "No";
                    }
                }
            } catch (Exception e) {
            }

            prevDepart = d.depart;
            ld.add(d);

            /**
             * ******************************************
             * Data object Route_Job to be pushed to db *
             * ******************************************
             */
            if (!d.vehicleCode.equals("") || !d.custId.equals("")) {
                if (!prevVehiCode.equals(d.vehicleCode) && !d.vehicleCode.equals("NA")) {
                    jobNb = 1;
                    routeNb++;
                }
                RouteJobLog r = new RouteJobLog();
                String[] doSplit = d.doNum.split(";");
                if (!d.vehicleCode.equals("") && !d.vehicleCode.equals("NA") && d.custId.equals("")) {
                    r.jobId = "DEPO";
                } else {
                    r.jobId = d.custId + "-" + doSplit.length;
                }
                r.custId = d.custId;
                if (!r.jobId.equals("DEPO")) {
                    r.countDoNo = "" + doSplit.length;
                }
                r.vehicleCode = d.vehicleCode;
                if (!r.vehicleCode.equals("NA")) {
                    r.activity = "start";
                    r.routeNb = routeNb;
                    r.jobNb = jobNb;
                } else {
                    r.routeNb = 0;
                    r.jobNb = 0;
                }
                r.arrive = d.arrive;
                r.depart = d.depart;
                r.runId = runId;
                r.branch = branch;
                r.shift = shift;
                if (r.custId.equals("")) {
                    String[] longlatSplit = getLongLatVehicle(r.vehicleCode).split("split");
                    try {
                        r.lon = reFormatLongLat(longlatSplit[0]);
                        r.lat = reFormatLongLat(longlatSplit[1]);
                    } catch (Exception e) {

                    }
                } else {
                    String[] longlatSplit = getLongLatCustomer(oriRunId, r.custId).split("split");
                    try {
                        r.lon = reFormatLongLat(longlatSplit[0]);
                        r.lat = reFormatLongLat(longlatSplit[1]);
                    } catch (Exception e) {

                    }
                }
                r.weight = aSplit[9];
                r.volume = getVolume(d.custId, oriRunId);
                try {
                    r.transportCost = d.transportCost;
                } catch (Exception e) {
                    r.transportCost = 0;
                }
                try {
                    double distance1 = Math.round(calcMeterDist(Double.parseDouble(d.lon1), Double.parseDouble(d.lat1), Double.parseDouble(d.lon1), Double.parseDouble(d.lat2)) * 100.0) / 100.0;
                    double distance2 = Math.round(calcMeterDist(Double.parseDouble(d.lon1), Double.parseDouble(d.lat2), Double.parseDouble(d.lon2), Double.parseDouble(d.lat2)) * 100.0) / 100.0;
                    r.dist = distance1 + distance2;
                } catch (Exception e) {
                    r.dist = 0.00;
                }

                arlistR.add(r);
                arlistOriR.add(r);
                jobNb++;
                prevVehiCode = r.vehicleCode;
            }
        }
    }

    public String getVendorId(String v) {
        String[] vSplit = v.split("_");
        return vSplit[1] + vSplit[3];
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

    public int getBreakTime(String day) throws Exception {
        int breakTime = 0;
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql = "";
                if (day.equals("Friday")) {
                    sql = "SELECT value FROM BOSNET1.dbo.TMS_Params WHERE param = 'fridayBreak'";
                } else {
                    sql = "SELECT value FROM BOSNET1.dbo.TMS_Params WHERE param = 'defaultBreak'";
                }
                try (ResultSet rs = stm.executeQuery(sql)) {
                    while (rs.next()) {
                        breakTime = rs.getInt("value");
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return breakTime;
    }

    public boolean timeMoreThan(String currentTime, String comparedTime) {
        boolean moreThan = false;
        try {
            String[] currentTimeSplit = currentTime.split(":");
            String[] comparedTimeSplit = comparedTime.split(":");
            //Compare hour
            if (Integer.parseInt(currentTimeSplit[0]) > Integer.parseInt(comparedTimeSplit[0])) {
                moreThan = true;
            } //If hour is same than compare minutes
            else if (Integer.parseInt(currentTimeSplit[0]) == Integer.parseInt(comparedTimeSplit[0])) {
                if (Integer.parseInt(currentTimeSplit[1]) > Integer.parseInt(comparedTimeSplit[1])) {
                    moreThan = true;
                }
            }
        } catch (Exception e) {

        }
        return moreThan;
    }

    public String addTime(String currentTime, long minToAdd) {
        String newTime = "";
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime lt = LocalTime.parse(currentTime);
            newTime = df.format(lt.plusMinutes(minToAdd));
        } catch (Exception e) {

        }
        return newTime;
    }

    public String getDayByDate(String dates) throws ParseException {
        String[] dateParse = dates.split("-");

        int year = Integer.parseInt(dateParse[0]);
        int month = Integer.parseInt(dateParse[1]);
        int day = Integer.parseInt(dateParse[2]);

        // First convert to Date. This is one of the many ways.
        String dateString = String.format("%d-%d-%d", year, month, day);
        Date date = new SimpleDateFormat("yyyy-M-d").parse(dateString);

        // Then get the day of week from the Date based on specific locale.
        String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);

        return dayOfWeek;
    }

    public static double calcMeterDist(double lon1, double lat1, double lon2, double lat2) {
        double el1 = 0; // was in function param
        double el2 = 0; // was in function param
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    public static double calcTripMinutes(double distanceMtr, double speedKmPHr) {
        return ((distanceMtr / 1000) / speedKmPHr * 60);
    }

    public String isTimeinRange(String arrive, ArrayList<String> al) {
        String[] truckArriveSplit = arrive.split(":");
        String[] custOpenSplit = al.get(0).split(":");
        String[] custCloseSplit = al.get(1).split(":");

        int truckArriveHour = Integer.parseInt(truckArriveSplit[0]);
        int truckArriveMin = Integer.parseInt(truckArriveSplit[1]);
        int custOpenHour = Integer.parseInt(custOpenSplit[0]);
        int custCloseHour = Integer.parseInt(custCloseSplit[0]);
        int custCloseMin = Integer.parseInt(custCloseSplit[1]);

        String ret = "";
        if (custOpenHour <= truckArriveHour && truckArriveHour <= custCloseHour) {
            if (truckArriveHour == custCloseHour) {
                if (truckArriveMin < custCloseMin) {
                    ret = "Yes";
                } else {
                    ret = "No";
                }
            } else {
                ret = "Yes";
            }
        } else {
            ret = "No";
        }
        return ret;
    }

    private String getVehicleType(String runId, String vehicleCode) throws Exception {
        String vehicle_type = "";
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql = "SELECT vehicle_type FROM BOSNET1.dbo.TMS_PreRouteVehicle where RunId = '" + runId + "' and vehicle_code = '" + vehicleCode + "';";
                try (ResultSet rs = stm.executeQuery(sql)) {
                    while (rs.next()) {
                        vehicle_type = rs.getString("vehicle_type");
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return vehicle_type;
    }

    private String getAccessList(String custId, String runId) throws Exception {
        String vehicleTypeList = "";
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql = "SELECT vehicle_type_list FROM BOSNET1.dbo.TMS_PreRouteJob where Customer_ID = '" + custId + "' and RunId = '" + runId + "';";
                try (ResultSet rs = stm.executeQuery(sql)) {
                    while (rs.next()) {
                        vehicleTypeList = rs.getString("vehicle_type_list");
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return vehicleTypeList;
    }

    public ArrayList<Double> getParam() throws Exception {
        ArrayList<Double> alParam = new ArrayList<>();
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql = "SELECT value FROM BOSNET1.dbo.TMS_Params WHERE param = 'SpeedKmPHour' OR param = 'TrafficFactor'";
                try (ResultSet rs = stm.executeQuery(sql)) {
                    while (rs.next()) {
                        alParam.add(rs.getDouble("value")); // Index 0 = speed, index 1 = traffic factor
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return alParam;
    }

    public String getTripCalc(String oriRunId) throws Exception {
        String tripcalc = "";
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql;
                sql = "SELECT TOP 1 tripcalc FROM BOSNET1.dbo.TMS_Progress where runID = '" + oriRunId + "';";
                // query
                try (ResultSet rs = stm.executeQuery(sql)) {
                    while (rs.next()) {
                        tripcalc = rs.getString("tripcalc");
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return tripcalc;
    }

    public String getVehiStart(String vehiNo) throws Exception {
        String startTime = "";
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql;
                sql = "SELECT TOP 1 startTime FROM BOSNET1.dbo.TMS_VehicleAtr where vehicle_code = '" + vehiNo + "';";
                // query
                try (ResultSet rs = stm.executeQuery(sql)) {
                    while (rs.next()) {
                        startTime = rs.getString("startTime");
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return startTime;
    }

    public ArrayList<String> getTruckTime(String runId, String vNo) throws Exception {
        ArrayList<String> al = new ArrayList<>();
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql;
                sql = "SELECT TOP 1 startTime, endTime FROM BOSNET1.dbo.TMS_PreRouteVehicle WHERE RunId = '" + runId + "' and vehicle_code = '" + vNo + "';";
                try (ResultSet rs = stm.executeQuery(sql)) {
                    while (rs.next()) {
                        al.add(rs.getString("startTime"));
                        al.add(rs.getString("endTime"));
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return al;
    }

    public ArrayList<String> getCustomerTime(String runId, String custId) throws Exception {
        ArrayList<String> al = new ArrayList<>();
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql;
                sql = "SELECT TOP 1 deliv_start, deliv_end FROM BOSNET1.dbo.TMS_PreRouteJob WHERE RunId = '" + runId + "' and Customer_ID = '" + custId + "';";
                // query
                try (ResultSet rs = stm.executeQuery(sql)) {
                    while (rs.next()) {
                        al.add(rs.getString("deliv_start"));
                        al.add(rs.getString("deliv_end"));
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return al;
    }

    public String getLongLatCustomer(String runId, String custId) throws Exception {
        String longitude = "";
        String latitude = "";
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql = "SELECT DISTINCT Long, Lat FROM BOSNET1.dbo.TMS_PreRouteJob where Customer_ID = '" + custId + "' and runId = '" + runId + "';";
                try (ResultSet rs = stm.executeQuery(sql)) {
                    while (rs.next()) {
                        longitude = rs.getString("Long");
                        latitude = rs.getString("Lat");
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return longitude + "split" + latitude;
    }

    public ArrayList<Double> getDistDurByGoogle(String lon1, String lat1, String lon2, String lat2) throws Exception {
        ArrayList<Double> al = new ArrayList<>();
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql = "SELECT TOP 1 dist, dur FROM BOSNET1.dbo.TMS_CostDist where lon1 = '" + lon1 + "' and lat1 = '" + lat1 + "' and lon2 = '" + lon2 + "' and lat2 = '" + lat2 + "';";
                try (ResultSet rs = stm.executeQuery(sql)) {
                    while (rs.next()) {
                        al.add(rs.getDouble("dist"));
                        al.add(rs.getDouble("dur"));
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return al;
    }

    public double getCostPerM(String vehicleCode, String runId) throws Exception {
        double costPerM = 0;
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql = "SELECT TOP 1 costPerM FROM BOSNET1.dbo.TMS_PreRouteVehicle where vehicle_code = '" + vehicleCode + "' and RunId = '" + runId + "';";
                try (ResultSet rs = stm.executeQuery(sql)) {
                    while (rs.next()) {
                        costPerM = rs.getDouble("costPerM");
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return costPerM;
    }

    public String getLongLatVehicle(String vehicleCode) throws Exception {
        String startLon = "";
        String startLat = "";
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql = "SELECT TOP 1 startLon, startLat FROM BOSNET1.dbo.TMS_PreRouteVehicle where vehicle_code = '" + vehicleCode + "' and RunId = '" + oriRunId + "';";
                try (ResultSet rs = stm.executeQuery(sql)) {
                    while (rs.next()) {
                        startLon = rs.getString("startLon");
                        startLat = rs.getString("startLat");
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return startLon + "split" + startLat;
    }

    public ArrayList<String> getRouteData(String custId) throws Exception {
        ArrayList<String> d = new ArrayList<>();
        ArrayList<String> tempDO = new ArrayList<>();
        String doNumber = "";
        String priority = "";
        String storeOpen = "";
        String storeClose = "";
        String serviceTime = "";
        String storeName = "";
        String storeStreet = "";
        String distChannel = "";
        String rdd = "";
        double weight = 0.0;
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql = "SELECT "
                        + "     DO_Number, "
                        + "     Customer_priority, "
                        + "     deliv_start, "
                        + "     deliv_end, "
                        + "     Service_time, "
                        + "     Name1, Street, "
                        + "     Distribution_Channel, "
                        + "     Request_Delivery_Date, "
                        + "     SUM(total_kg) as total_kg "
                        + "FROM "
                        + "     BOSNET1.dbo.TMS_PreRouteJob "
                        + "WHERE "
                        + "     RunId = '" + oriRunId + "' "
                        + "     and Customer_ID = '" + custId + "' "
                        + "     and Is_Edit = 'edit' "
                        + "GROUP BY "
                        + "     DO_Number, "
                        + "     Customer_priority, "
                        + "     deliv_start, "
                        + "     deliv_end, "
                        + "     Service_time, "
                        + "     Name1, Street, "
                        + "     Distribution_Channel, "
                        + "     Request_Delivery_Date "
                        + "ORDER BY"
                        + "     Request_Delivery_Date DESC";
                try (ResultSet rs = stm.executeQuery(sql)) {
                    while (rs.next()) {
                        tempDO.add(rs.getString("DO_Number"));
                        if (priority.length() == 0) {
                            priority = rs.getString("Customer_priority");
                        }
                        storeOpen = rs.getString("deliv_start");
                        storeClose = rs.getString("deliv_end");
                        serviceTime = rs.getString("Service_time");
                        storeName = rs.getString("Name1");
                        storeStreet = rs.getString("Street");
                        distChannel = rs.getString("Distribution_Channel");
                        rdd = rs.getString("Request_Delivery_Date");
                        weight += rs.getDouble("total_kg");
                    }
                    Collections.sort(tempDO);
                    for (int i = 0; i < tempDO.size(); i++) {
                        doNumber += tempDO.get(i) + ";\n";
                    }
                    if (doNumber.length() > 0) {
                        doNumber = doNumber.substring(0, doNumber.length() - 2);
                    }

                    d.add(doNumber + "split" + priority + "split" + storeOpen + "split" + storeClose + "split" + serviceTime + "split" + storeName + "split"
                            + storeStreet + "split" + distChannel + "split" + rdd + "split" + weight);
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return d;
    }

    private String getVolume(String custId, String runId) throws Exception {
        String volume = "";
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql = "SELECT TOP 1 volume FROM BOSNET1.dbo.TMS_RouteJob where Customer_ID = '" + custId + "' and runID = '" + runId + "'";
                try (ResultSet rs = stm.executeQuery(sql)) {
                    while (rs.next()) {
                        volume = rs.getString("volume");
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return volume;
    }

    public void updateRouteJob(ArrayList<RouteJobLog> arlist, String runId) throws Exception {
        Timestamp createTime = getTimeStamp();
        int rowNum = 0;
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql = "SELECT COUNT(*) total FROM bosnet1.dbo.TMS_RouteJob WHERE runID = '" + runId + "';";
                try (ResultSet rs = stm.executeQuery(sql)) {
                    while (rs.next()) {
                        rowNum = rs.getInt("total");
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        if (rowNum > 0) {
            try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
                try (Statement stm = con.createStatement()) {
                    String sql = "DELETE FROM bosnet1.dbo.TMS_RouteJob WHERE runID = '" + runId + "';";
                    stm.executeQuery(sql);
                }
            } catch (Exception e) {

            }
        }
        String sql = "INSERT INTO bosnet1.dbo.TMS_RouteJob "
                + "(job_id, customer_id, do_number, vehicle_code, activity, routeNb, jobNb, arrive, depart, runID, create_dtm, branch, shift, lon, lat, weight, volume, transportCost, activityCost, Dist) "
                + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

        for (int i = 0; i < arlist.size(); i++) {
            RouteJobLog r = arlist.get(i);
            try (Connection con = (new Db()).getConnection("jdbc/fztms"); PreparedStatement psHdr = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                psHdr.setString(1, r.jobId);
                psHdr.setString(2, r.custId);
                psHdr.setString(3, r.countDoNo);
                psHdr.setString(4, r.vehicleCode);
                psHdr.setString(5, r.activity);
                psHdr.setInt(6, r.routeNb);
                psHdr.setInt(7, r.jobNb);
                psHdr.setString(8, r.arrive);
                psHdr.setString(9, r.depart);
                psHdr.setString(10, r.runId);
                psHdr.setTimestamp(11, createTime);
                psHdr.setString(12, r.branch);
                psHdr.setString(13, r.shift);
                psHdr.setString(14, r.lon);
                psHdr.setString(15, r.lat);
                psHdr.setString(16, r.weight);
                psHdr.setString(17, r.volume);
                psHdr.setDouble(18, r.transportCost);
                psHdr.setDouble(19, r.activityCost);
                psHdr.setDouble(20, r.dist);

                psHdr.executeUpdate();
            }
        }
    }
}

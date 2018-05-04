/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.service.run;

import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.tms.params.model.Delivery;
import com.fz.tms.params.model.PreRouteJobLog;
import com.fz.tms.params.model.PreRouteVehicleLog;
import com.fz.tms.params.model.RouteJobLog;
import com.fz.util.FZUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

public class RouteJobListingResultEdit implements BusinessLogic {

    String prevCustId = "";
    String previousCustId = "";
    String prevDepart = "";
    double long1, lat1, long2, lat2;
    boolean b = true;

    ArrayList<RouteJobLog> alRjl = new ArrayList<>();
    String prevVehiCode = "";
    int routeNb = 0;
    int jobNb = 1;

    String oriRunId, runId, branch, shift, dateDeliv;

    boolean hasBreak = false;

    double speedTruck, trafficFactor;

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response, PageContext pc) throws Exception {
        long startTime = System.nanoTime();
        oriRunId = FZUtil.getHttpParam(request, "OriRunID");
        runId = FZUtil.getHttpParam(request, "runId");
        branch = FZUtil.getHttpParam(request, "branch");
        shift = FZUtil.getHttpParam(request, "shift");
        dateDeliv = FZUtil.getHttpParam(request, "dateDeliv");
        String channel = FZUtil.getHttpParam(request, "channel");
        String vehicles = FZUtil.getHttpParam(request, "vehicles");
        String tableArr = FZUtil.getHttpParam(request, "tableArr");

        //String[] tableArrSplit = tableArr(oriRunId).split("split");
        ArrayList<Double> alParam = getParam();
        speedTruck = alParam.get(0);
        trafficFactor = alParam.get(1);
//        for (int i = 0; i < tableArrSplit.length; i++) {
//            String str = tableArrSplit[i];
//            String data = str;
//            if (i != 0) {
//                data = str.substring(0, 0) + str.substring(0 + 1);
//            }
//
//            String[] dataSplit = data.split(",");
//            switch (dataSplit.length) {
//                case 3: //normal row or start
//                    if (!dataSplit[2].equals("start")) {
//                        setObjectValue(dataSplit[0], dataSplit[1], dataSplit[2], "");
//                    } else {
//                        setObjectValue(dataSplit[0], dataSplit[1], "", getVehiStart(dataSplit[1]));
//                        prevCustId = dataSplit[1];
//                    }
//                    break;
//                case 2: //end row
//                    setObjectValue(dataSplit[0], dataSplit[1], "", "");
//                    break;
//                default: //truck break row
//                    setObjectValue("", "", "", "");
//                    break;
//            }
//        }
        ArrayList<Delivery> alTableData = getTableData(oriRunId);

        insertToRouteJob(alRjl, runId);
        insertToPreRouteJob(getListPreRouteJob(oriRunId, runId), runId);
        insertPreRouteVehicle(getListPreRouteVehicle(oriRunId, runId), runId);

        long endTime = System.nanoTime();
        long totalTime = (endTime - startTime) / 1000000000;
        System.out.println("TOTAL TIME " + totalTime);

        request.setAttribute("listDelivery", alTableData);
        request.setAttribute("branch", branch);
        request.setAttribute("shift", shift);
        request.setAttribute("channel", channel);
        request.setAttribute("dateDeliv", dateDeliv);
        request.setAttribute("vehicles", vehicles);
        request.setAttribute("runId", runId);
        request.setAttribute("oriRunId", oriRunId);
        request.setAttribute("tableArr", tableArr);
    }

    public ArrayList<Delivery> getTableData(String runId) throws Exception {
        ArrayList<Delivery> alDelivery = new ArrayList<>();
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql;
                sql = "SELECT\n"
                        + "    CASE WHEN rj.depart = '' THEN 0 ELSE rj.jobNb - 1 END No,\n"
                        + "    rj.vehicle_code,\n"
                        + "    rj.customer_id,\n"
                        + "    rj.arrive,\n"
                        + "    rj.depart,\n"
                        + "    CASE WHEN prj.DO_Number is null THEN '' ELSE prj.DO_number END DO_Number,\n"
                        + "    CASE WHEN rj.vehicle_code = 'NA' THEN 0 WHEN prj.Service_time is null THEN '' ELSE prj.Service_time END serviceTime,\n"
                        + "    CASE WHEN prj.Name1 is null THEN '' ELSE prj.Name1 END Name1,\n"
                        + "    CASE WHEN prj.Long is null THEN '' ELSE prj.Long END Long,\n"
                        + "    CASE WHEN prj.Lat is null THEN '' ELSE prj.Lat END Lat,\n"
                        + "    CASE WHEN prj.Customer_priority is null THEN '' ELSE prj.Customer_priority END Customer_priority,\n"
                        + "    CASE WHEN prj.Distribution_Channel is null THEN '' ELSE prj.Distribution_Channel END Distribution_Channel,\n"
                        + "    CASE WHEN prj.Street is null THEN '' ELSE prj.Street END Street,\n"
                        + "    rj.weight,\n"
                        + "    rj.volume,\n"
                        + "    prj.Request_Delivery_Date,\n"
                        + "    rj.transportCost TransportCost,\n"
                        + "    rj.Dist,\n"
                        + "    prv.startLon,\n"
                        + "    prv.startLat\n"
                        + "FROM \n"
                        + "	[BOSNET1].[dbo].[TMS_RouteJob] rj\n"
                        + "LEFT JOIN(\n"
                        + "	SELECT DISTINCT\n"
                        + "		prj.customer_ID,\n"
                        + "		(SELECT\n"
                        + "			(stuff(\n"
                        + "					(SELECT\n"
                        + "						'; ' + DO_Number\n"
                        + "					FROM\n"
                        + "						bosnet1.dbo.TMS_PreRouteJob\n"
                        + "					WHERE\n"
                        + "						Is_Edit = 'edit'\n"
                        + "						AND Customer_ID = prj.Customer_ID\n"
                        + "						AND RunId = '" + runId + "'\n"
                        + "					GROUP BY\n"
                        + "						DO_Number FOR xml PATH('')\n"
                        + "					),\n"
                        + "				1,\n"
                        + "				2,\n"
                        + "				''\n"
                        + "				)\n"
                        + "			)\n"
                        + "		) AS DO_number,\n"
                        + "		prj.Service_time,\n"
                        + "		prj.RunId,\n"
                        + "		prj.Name1,\n"
                        + "		prj.Long,\n"
                        + "		prj.Lat,\n"
                        + "		prj.Customer_priority,\n"
                        + "		prj.Distribution_Channel,\n"
                        + "		prj.Street,\n"
                        + "		prj.Request_Delivery_Date\n"
                        + "	FROM \n"
                        + "		[BOSNET1].[dbo].[TMS_PreRouteJob] prj\n"
                        + "	WHERE \n"
                        + "		Is_Edit = 'edit'\n"
                        + ") prj ON rj.runID = prj.RunId and rj.customer_id = prj.Customer_ID\n"
                        + "LEFT JOIN(\n"
                        + "	SELECT\n"
                        + "		prv.vehicle_code,\n"
                        + "		prv.startLon,\n"
                        + "		prv.startLat\n"
                        + "	FROM\n"
                        + "		[BOSNET1].[dbo].[TMS_PreRouteVehicle] prv\n"
                        + "	WHERE\n"
                        + "		prv.RunId = '" + runId + "'\n"
                        + ") prv ON prv.vehicle_code = rj.vehicle_code and rj.customer_id = ''\n"
                        + "WHERE \n"
                        + "	rj.runID = '" + runId + "'\n"
                        + "ORDER BY\n"
                        + "	rj.routeNb, rj.jobNb";
                try (ResultSet rs = stm.executeQuery(sql)) {
                    String prevLong = "";
                    String prevLat = "";
                    while (rs.next()) {
                        Delivery ld = new Delivery(); //Used in view
                        /**
                         * ********************
                         * Object used in view 
                         ********************
                         */
                        ld.no = rs.getString("no");
                        ld.vehicleCode = rs.getString("vehicle_code");
                        ld.custId = rs.getString("customer_id");
                        ld.arrive = rs.getString("arrive");
                        ld.depart = rs.getString("depart");
                        ld.doNum = rs.getString("DO_Number");
                        ld.serviceTime = rs.getString("serviceTime");
                        ld.storeName = rs.getString("Name1");
                        ld.lon1 = prevLong;
                        ld.lat1 = prevLat;
                        ld.lon2 = rs.getString("Long");
                        ld.lat2 = rs.getString("Lat");
                        ld.priority = rs.getString("Customer_priority");
                        ld.distChannel = rs.getString("Distribution_Channel");
                        ld.street = rs.getString("Street");
                        try {
                            ld.weight = "" + Math.round((rs.getDouble("weight")) * 10) / 10.0;
                        } catch (Exception e) {
                            ld.weight = "";
                        }
                        try {
                            ld.volume = "" + Math.round((rs.getDouble("volume") / 1000000) * 10) / 10.0;
                        } catch (Exception e) {
                            ld.volume = "";
                        }
                        ld.rdd = rs.getString("Request_Delivery_Date");
                        ld.transportCost = rs.getInt("TransportCost");
                        ld.dist = "" + Math.round((rs.getDouble("Dist") / 1000) * 10) / 10.0;

                        alDelivery.add(ld);

                        if (ld.no.equals("0") && !ld.vehicleCode.equals("NA")) {
                            prevLong = rs.getString("startLon");
                            prevLat = rs.getString("startLat");
                        } else {
                            prevLong = ld.lon2;
                            prevLat = ld.lat2;
                        }

                        //break if depart + 60 minutes is more than 11:30
                        if (hasBreak == false && !ld.depart.equals("") && timeMoreThan(addTime(addTime(ld.arrive, Integer.parseInt(ld.serviceTime)), 60), "11:30")) {
                            ld.no = "";
                            ld.vehicleCode = "";
                            ld.custId = "";
                            ld.doNum = "";
                            ld.serviceTime = "0";
                            ld.storeName = "";
                            ld.priority = "";
                            ld.distChannel = "";
                            ld.street = "";
                            ld.weight = "";
                            ld.volume = "";
                            ld.rdd = "null";
                            ld.transportCost = 0;
                            ld.dist = "null";
                            hasBreak = true;
                        } else if (ld.depart.equals("")) {
                            hasBreak = false;
                        }

                        /**
                         * *************************
                         * Object to be pushed to DB 
                         **************************
                         */
                        RouteJobLog rjl = new RouteJobLog(); //Used to push to DB
                        if (!prevVehiCode.equals(ld.vehicleCode) && !ld.vehicleCode.equals("NA")) {
                            jobNb = 1;
                            routeNb++;
                        }
                        if (!ld.vehicleCode.equals("") && !ld.vehicleCode.equals("NA") && ld.custId.equals("")) {
                            rjl.jobId = "DEPO";
                        } else {
                            rjl.jobId = ld.custId;
                        }
                        rjl.custId = ld.custId;
                        rjl.countDoNo = "" + countDoNo(ld.doNum);
                        rjl.vehicleCode = ld.vehicleCode;
                        if (!ld.vehicleCode.equals("NA")) {
                            rjl.activity = "start";
                            rjl.routeNb = routeNb;
                            rjl.jobNb = jobNb;
                        } else {
                            rjl.routeNb = 0;
                            rjl.jobNb = 0;
                        }
                        rjl.arrive = ld.arrive;
                        rjl.depart = ld.depart;
                        rjl.runId = this.runId;
                        //rjl.createDtm = getTimeStamp(); in insertRouteJob method
                        rjl.branch = branch;
                        rjl.shift = shift;
                        rjl.lon = ld.lon2;
                        rjl.lat = ld.lat2;
                        rjl.weight = ld.weight;
                        rjl.volume = ld.volume;
                        rjl.transportCost = ld.transportCost;
                        rjl.dist = Double.parseDouble(ld.dist);

                        alRjl.add(rjl);
                        jobNb++;
                        prevVehiCode = rjl.vehicleCode;
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return alDelivery;
    }

    public int countDoNo(String doNum) {
        String[] doSplit = doNum.split("; ");
        return doSplit.length;
    }

//    public String tableArr(String runId) throws Exception {
//        String tableArr = "";
//        String sql = "SELECT\n"
//                + "	concat(\n"
//                + "		',',\n"
//                + "		CASE\n"
//                + "			WHEN jobNb > 0\n"
//                + "			AND customer_id <> '' THEN jobNb - 1\n"
//                + "			ELSE ''\n"
//                + "		END,\n"
//                + "		',',\n"
//                + "		vehicle_code,\n"
//                + "		',',\n"
//                + "		CASE\n"
//                + "			WHEN customer_id = ''\n"
//                + "			AND jobNb = 1 THEN 'start'\n"
//                + "			ELSE customer_id\n"
//                + "		END,\n"
//                + "		'split'\n"
//                + "	) AS arr,\n"
//                + "	arrive,\n"
//                + "	depart\n"
//                + "FROM\n"
//                + "	BOSNET1.dbo.TMS_RouteJob\n"
//                + "WHERE\n"
//                + "	runID = '" + runId + "'\n"
//                + "ORDER BY\n"
//                + "	routeNb,\n"
//                + "	jobNb;";
//
//        try (Connection con = (new Db()).getConnection("jdbc/fztms");
//                PreparedStatement ps = con.prepareStatement(sql)) {
//            //ps.setString(1, runID);            
//            // get list
//            try (ResultSet rs = ps.executeQuery()) {
//                int n = 0;
//                String arv = "01:00";
//                while (rs.next()) {
//                    int i = 1;
//                    String arr = FZUtil.getRsString(rs, i++, "");
//                    arr = arr.replace(",0,", ",,");
//                    String arrive = FZUtil.getRsString(rs, i++, "");
//
//                    if (arrive.length() > 0) {
//                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//                        Date arv12 = sdf.parse("12:00");
//                        Date arvA = sdf.parse(arv);
//                        Date arvB = sdf.parse(arrive);
//                        if (arvA.before(arv12) && arvB.after(arv12)) {
//                            tableArr += ",,,split";
//                        }
//                        arv = arrive;
//                    }
//                    if (n == 0) {
//                        arr = arr.substring(1, arr.length());
//                    }
//                    tableArr += arr;
//                    n++;
//                }
//
//                /*String[] tableArrSplit = tableArr.split("split");
//                for(int a = 0 ; a<tableArrSplit.length;a++){
//                    System.out.println(tableArrSplit[a]);
//                }*/
//            }
//        }
//        return tableArr;
//    }
//
//    public void setObjectValue(String no, String vNo, String custId, String depart) throws Exception {
//        ArrayList<String> a = getRouteData(custId);
//        String[] aSplit = a.get(0).split("split");
//        //not a break-time row
//        if (!vNo.equals("")) {
//            Delivery d = new Delivery();
//            Delivery dl = new Delivery();
//            if (!vNo.equals("NA")) {
//                d.no = no;
//            }
//            d.vehicleCode = vNo;
//            d.custId = custId;
//            d.doNum = aSplit[0];
//            d.serviceTime = aSplit[4];
//            d.storeName = aSplit[5];
//            d.priority = aSplit[1];
//            d.distChannel = aSplit[7];
//            d.street = aSplit[6];
//            d.weight = "" + Math.round(Double.parseDouble(aSplit[9]) * 10) / 10.0;
//
//            try {
//                d.volume = "" + Math.round(Double.parseDouble(getVolume(custId, oriRunId)) * 1) / 1000000.0;
//            } catch (Exception e) {
//            }
//            d.rdd = aSplit[8];
//            if (!custId.equals("") || depart.equals("")) {
//                d.arrive = prevDepart;
//            }
//            //start row
//            if (d.custId.equals("")) {
//                d.depart = depart;
//            } else {
//                d.depart = addTime(d.arrive, Integer.parseInt(d.serviceTime));
//            }
//
//            //set long lat of store name
//            if (!d.vehicleCode.equals("NA")) {
//                //start row
//                if (!d.vehicleCode.equals("") && d.custId.equals("") && !d.depart.equals("")) {
//                    previousCustId = d.vehicleCode;
//                    b = false;
//                } //normal row
//                else if (!d.vehicleCode.equals("") && !d.custId.equals("") && !d.doNum.equals("")) {
//                    //previousCustId is normal row
//                    if (previousCustId.matches("[0-9]+")) {
//                        try {
//                            String[] longlat1 = getLongLatCustomer(oriRunId, previousCustId).split("split");
//                            d.lon1 = reFormatLongLat(longlat1[0]);
//                            d.lat1 = reFormatLongLat(longlat1[1]);
//                        } catch (Exception e) {
//                        }
//                    } //previousCustId is a start row
//                    else {
//                        try {
//                            String[] longlat1 = getLongLatVehicle(previousCustId).split("split");
//                            d.lon1 = reFormatLongLat(longlat1[0]);
//                            d.lat1 = reFormatLongLat(longlat1[1]);
//                        } catch (Exception e) {
//                        }
//                    }
//                    String[] longlat2 = getLongLatCustomer(oriRunId, d.custId).split("split");
//                    try {
//                        d.lon2 = reFormatLongLat(longlat2[0]);
//                        d.lat2 = reFormatLongLat(longlat2[1]);
//                    } catch (Exception e) {
//                    }
//                    previousCustId = d.custId;
//                } //end row
//                else if (!d.vehicleCode.equals("") && d.custId.equals("") && d.depart.equals("")) {
//                    try {
//                        String[] longlat1 = getLongLatCustomer(oriRunId, previousCustId).split("split");
//                        d.lon1 = reFormatLongLat(longlat1[0]);
//                        d.lat1 = reFormatLongLat(longlat1[1]);
//                        String[] longlat2 = getLongLatVehicle(d.vehicleCode).split("split");
//                        d.lon2 = reFormatLongLat(longlat2[0]);
//                        d.lat2 = reFormatLongLat(longlat2[1]);
//                        b = true;
//                    } catch (Exception e) {
//                    }
//                }
//                //set arrive, depart, distance using lon lat of store
//                if (!custId.equals("") || depart.equals("")) {
//                    //Manhattan
//                    if (getTripCalc(oriRunId).equals("M")) {
//                        try {
//                            double distance1 = calcMeterDist(Double.parseDouble(d.lon1), Double.parseDouble(d.lat1), Double.parseDouble(d.lon1), Double.parseDouble(d.lat2));
//                            double distance2 = calcMeterDist(Double.parseDouble(d.lon1), Double.parseDouble(d.lat2), Double.parseDouble(d.lon2), Double.parseDouble(d.lat2));
//                            d.arrive = "" + addTime(prevDepart, Math.round(trafficFactor * calcTripMinutes(distance1 + distance2, speedTruck)));
//                            d.dist = "" + Math.round(((distance1 + distance2) / 1000) * 10) / 10.0;
//                            d.transportCost = (int) Math.round(getCostPerM(d.vehicleCode, oriRunId) * (distance1 + distance2));
//                        } catch (Exception e) {
//                        }
//                    } //Google
//                    else {
//                        try {
//                            ArrayList<Double> alDurDist = getDistDurByGoogle(d.lon1, d.lat1, d.lon2, d.lat2);
//                            double distance = alDurDist.get(0);
//                            d.arrive = addTime(prevDepart, Math.round(alDurDist.get(1)));
//                            d.dist = "" + Math.round((distance / 1000) * 10) / 10.0;
//                            d.transportCost = (int) ((int) Math.round((getCostPerM(d.vehicleCode, oriRunId) * distance) * 10) / 10.0);
//                        } catch (Exception e) {
//                        }
//                    }
//
//                    //break if depart + 60 minutes is more than 11:30
//                    if (hasBreak == false && !d.depart.equals("") && timeMoreThan(addTime(addTime(d.arrive, Integer.parseInt(d.serviceTime)), 60), "11:30")) {
//                        dl.no = "";
//                        dl.vehicleCode = "";
//                        dl.custId = "";
//                        dl.doNum = "";
//                        dl.serviceTime = "0";
//                        dl.storeName = "";
//                        dl.priority = "";
//                        dl.distChannel = "";
//                        dl.street = "";
//                        dl.weight = "";
//                        dl.volume = "";
//                        dl.rdd = "null";
//                        dl.transportCost = 0;
//                        dl.dist = "null";
//                        hasBreak = true;
//                    } else if (d.depart.equals("")) {
//                        hasBreak = false;
//                    }
//                }
//                if (d.custId.equals("")) {
//                    d.depart = depart;
//                } else {
//                    d.depart = addTime(d.arrive, Integer.parseInt(d.serviceTime));
//                }
//            }
//            prevDepart = d.depart;
//            ld.add(d);
//            //Delivery object for break
//            if (dl.dist.equals("null")) {
//                ld.add(dl);
//                prevDepart = addTime(prevDepart, getBreakTime(getDayByDate(dateDeliv)));
//            }
//
//            /**
//             * *****************************************
//             * Data object Route_Job to be pushed to db *
//             * ******************************************
//             */
//            if (!d.vehicleCode.equals("") || !d.custId.equals("")) {
//                if (!prevVehiCode.equals(d.vehicleCode) && !d.vehicleCode.equals("NA")) {
//                    jobNb = 1;
//                    routeNb++;
//                }
//                RouteJobLog r = new RouteJobLog();
//                String[] doSplit = d.doNum.split(";");
//                if (!d.vehicleCode.equals("") && !d.vehicleCode.equals("NA") && d.custId.equals("")) {
//                    r.jobId = "DEPO";
//                } else {
//                    r.jobId = d.custId + "-" + doSplit.length;
//                }
//                r.custId = d.custId;
//                if (!r.jobId.equals("DEPO")) {
//                    r.countDoNo = "" + doSplit.length;
//                }
//                r.vehicleCode = d.vehicleCode;
//                if (!r.vehicleCode.equals("NA")) {
//                    r.activity = "start";
//                    r.routeNb = routeNb;
//                    r.jobNb = jobNb;
//                } else {
//                    r.routeNb = 0;
//                    r.jobNb = 0;
//                }
//                r.arrive = d.arrive;
//                r.depart = d.depart;
//                r.runId = runId;
//                r.branch = branch;
//                r.shift = shift;
//                if (r.custId.equals("")) {
//                    String[] longlatSplit = getLongLatVehicle(r.vehicleCode).split("split");
//                    try {
//                        r.lon = reFormatLongLat(longlatSplit[0]);
//                        r.lat = reFormatLongLat(longlatSplit[1]);
//                    } catch (Exception e) {
//
//                    }
//                } else {
//                    String[] longlatSplit = getLongLatCustomer(oriRunId, r.custId).split("split");
//                    try {
//                        r.lon = reFormatLongLat(longlatSplit[0]);
//                        r.lat = reFormatLongLat(longlatSplit[1]);
//                    } catch (Exception e) {
//
//                    }
//                }
//                r.weight = aSplit[9];
//                r.volume = getVolume(d.custId, oriRunId);
//                r.transportCost = d.transportCost;
//
//                try {
//                    double distance1 = Math.round(calcMeterDist(Double.parseDouble(d.lon1), Double.parseDouble(d.lat1), Double.parseDouble(d.lon1), Double.parseDouble(d.lat2)) * 100.0) / 100.0;
//                    double distance2 = Math.round(calcMeterDist(Double.parseDouble(d.lon1), Double.parseDouble(d.lat2), Double.parseDouble(d.lon2), Double.parseDouble(d.lat2)) * 100.0) / 100.0;
//                    r.dist = distance1 + distance2;
//                } catch (Exception e) {
//                    r.dist = 0.00;
//                }
//
//                arlistR.add(r);
//                jobNb++;
//                prevVehiCode = r.vehicleCode;
//            }
//        }
//    }
//
//    public String getDayByDate(String dates) throws ParseException {
//        String[] dateParse = dates.split("-");
//
//        int year = Integer.parseInt(dateParse[0]);
//        int month = Integer.parseInt(dateParse[1]);
//        int day = Integer.parseInt(dateParse[2]);
//
//        // First convert to Date. This is one of the many ways.
//        String dateString = String.format("%d-%d-%d", year, month, day);
//        Date date = new SimpleDateFormat("yyyy-M-d").parse(dateString);
//
//        // Then get the day of week from the Date based on specific locale.
//        String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);
//
//        return dayOfWeek;
//    }
//
//    public String getVehiStart(String vehiNo) throws Exception {
//        String startTime = "";
//        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
//            try (Statement stm = con.createStatement()) {
//                String sql;
//                sql = "SELECT TOP 1 startTime FROM BOSNET1.dbo.TMS_VehicleAtr where vehicle_code = '" + vehiNo + "';";
//                // query
//                try (ResultSet rs = stm.executeQuery(sql)) {
//                    while (rs.next()) {
//                        startTime = rs.getString("startTime");
//                    }
//                }
//            }
//        } catch (Exception e) {
//            throw new Exception(e.getMessage());
//        }
//        return startTime;
//    }
//
//    public ArrayList<Double> getDistDurByGoogle(String lon1, String lat1, String lon2, String lat2) throws Exception {
//        ArrayList<Double> al = new ArrayList<>();
//        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
//            try (Statement stm = con.createStatement()) {
//                String sql = "SELECT TOP 1 dist, dur FROM BOSNET1.dbo.TMS_CostDist where lon1 = '" + lon1 + "' and lat1 = '" + lat1 + "' and lon2 = '" + lon2 + "' and lat2 = '" + lat2 + "';";
//                try (ResultSet rs = stm.executeQuery(sql)) {
//                    while (rs.next()) {
//                        al.add(rs.getDouble("dist"));
//                        al.add(rs.getDouble("dur"));
//                    }
//                }
//            }
//        } catch (Exception e) {
//            throw new Exception(e.getMessage());
//        }
//        return al;
//    }
//
//    public String getTripCalc(String oriRunId) throws Exception {
//        String tripcalc = "";
//        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
//            try (Statement stm = con.createStatement()) {
//                String sql;
//                sql = "SELECT TOP 1 tripcalc FROM BOSNET1.dbo.TMS_Progress where runID = '" + oriRunId + "';";
//                // query
//                try (ResultSet rs = stm.executeQuery(sql)) {
//                    while (rs.next()) {
//                        tripcalc = rs.getString("tripcalc");
//                    }
//                }
//            }
//        } catch (Exception e) {
//            throw new Exception(e.getMessage());
//        }
//        return tripcalc;
//    }
//
//    public ArrayList<String> getRouteData(String custId) throws Exception {
//        ArrayList<String> d = new ArrayList<>();
//        ArrayList<String> tempDO = new ArrayList<>();
//        String doNumber = "";
//        String priority = "";
//        String storeOpen = "";
//        String storeClose = "";
//        String serviceTime = "";
//        String storeName = "";
//        String storeStreet = "";
//        String distChannel = "";
//        String rdd = "";
//        double weight = 0.0;
//        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
//            try (Statement stm = con.createStatement()) {
//                String sql = "SELECT "
//                        + "     DO_Number, "
//                        + "     Customer_priority, "
//                        + "     deliv_start, "
//                        + "     deliv_end, "
//                        + "     Service_time, "
//                        + "     Name1, Street, "
//                        + "     Distribution_Channel, "
//                        + "     Request_Delivery_Date, "
//                        + "     SUM(total_kg) as total_kg "
//                        + "FROM "
//                        + "     BOSNET1.dbo.TMS_PreRouteJob "
//                        + "WHERE "
//                        + "     RunId = '" + oriRunId + "' "
//                        + "     and Customer_ID = '" + custId + "' "
//                        + "     and Is_Edit = 'edit' "
//                        + "GROUP "
//                        + "     BY DO_Number, "
//                        + "     Customer_priority, "
//                        + "     deliv_start, "
//                        + "     deliv_end, "
//                        + "     Service_time, "
//                        + "     Name1, Street, "
//                        + "     Distribution_Channel, "
//                        + "     Request_Delivery_Date "
//                        + "ORDER BY"
//                        + "     Request_Delivery_Date DESC";
//                try (ResultSet rs = stm.executeQuery(sql)) {
//                    while (rs.next()) {
//                        tempDO.add(rs.getString("DO_Number"));
//                        if (priority.length() == 0) {
//                            priority = rs.getString("Customer_priority");
//                        }
//                        storeOpen = rs.getString("deliv_start");
//                        storeClose = rs.getString("deliv_end");
//                        serviceTime = rs.getString("Service_time");
//                        storeName = rs.getString("Name1");
//                        storeStreet = rs.getString("Street");
//                        distChannel = rs.getString("Distribution_Channel");
//                        rdd = rs.getString("Request_Delivery_Date");
//                        weight += rs.getDouble("total_kg");
//                    }
//                    Collections.sort(tempDO);
//                    for (int i = 0; i < tempDO.size(); i++) {
//                        doNumber += tempDO.get(i) + ";\n";
//                    }
//                    if (doNumber.length() > 0) {
//                        doNumber = doNumber.substring(0, doNumber.length() - 2);
//                    }
//
//                    d.add(doNumber + "split" + priority + "split" + storeOpen + "split" + storeClose + "split" + serviceTime + "split" + storeName + "split"
//                            + storeStreet + "split" + distChannel + "split" + rdd + "split" + weight);
//                }
//            }
//        } catch (Exception e) {
//            throw new Exception(e.getMessage());
//        }
//        return d;
//    }
//
//    public String getLongLatCustomer(String runId, String custId) throws Exception {
//        String longitude = "";
//        String latitude = "";
//        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
//            try (Statement stm = con.createStatement()) {
//                String sql = "SELECT DISTINCT Long, Lat FROM BOSNET1.dbo.TMS_PreRouteJob where Customer_ID = '" + custId + "' and runId = '" + runId + "';";
//                try (ResultSet rs = stm.executeQuery(sql)) {
//                    while (rs.next()) {
//                        longitude = rs.getString("Long");
//                        latitude = rs.getString("Lat");
//                    }
//                }
//            }
//        } catch (Exception e) {
//            throw new Exception(e.getMessage());
//        }
//        return longitude + "split" + latitude;
//    }
//
    public String addTime(String currentTime, double minToAdd) {
        String newTime = "";
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime lt = LocalTime.parse(currentTime);
            newTime = df.format(lt.plusMinutes((int) minToAdd));
        } catch (Exception e) {

        }
        return newTime;
    }
//
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
//
//    public int getBreakTime(String day) throws Exception {
//        int breakTime = 0;
//        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
//            try (Statement stm = con.createStatement()) {
//                String sql = "";
//                if (day.equals("Friday")) {
//                    sql = "SELECT value FROM BOSNET1.dbo.TMS_Params WHERE param = 'fridayBreak'";
//                } else {
//                    sql = "SELECT value FROM BOSNET1.dbo.TMS_Params WHERE param = 'defaultBreak'";
//                }
//                try (ResultSet rs = stm.executeQuery(sql)) {
//                    while (rs.next()) {
//                        breakTime = rs.getInt("value");
//                    }
//                }
//            }
//        } catch (Exception e) {
//            throw new Exception(e.getMessage());
//        }
//        return breakTime;
//    }
//
    public static Timestamp getTimeStamp() throws ParseException {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime());
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = (Date) formatter.parse(timeStamp);
        java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());

        return timeStampDate;
    }
//
//    public String getLongLatVehicle(String vehicleCode) throws Exception {
//        String startLon = "";
//        String startLat = "";
//        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
//            try (Statement stm = con.createStatement()) {
//                String sql = "SELECT TOP 1 startLon, startLat FROM BOSNET1.dbo.TMS_PreRouteVehicle where vehicle_code = '" + vehicleCode + "' and RunId = '" + oriRunId + "';";
//                try (ResultSet rs = stm.executeQuery(sql)) {
//                    while (rs.next()) {
//                        startLon = rs.getString("startLon");
//                        startLat = rs.getString("startLat");
//                    }
//                }
//            }
//        } catch (Exception e) {
//            throw new Exception(e.getMessage());
//        }
//        return startLon + "split" + startLat;
//    }
//
//    public String reFormatLongLat(String longlat) {
//        String ret = "";
//        //Sometimes long lat use "," instead of "."
//        if (!longlat.contains(",")) {
//            ret = longlat;
//        } else {
//            ret = longlat.replaceAll(",", ".");
//        }
//        return ret;
//    }
//
//    public static double calcMeterDist(double lon1, double lat1, double lon2, double lat2) {
//        double el1 = 0; // was in function param
//        double el2 = 0; // was in function param
//        final int R = 6371; // Radius of the earth
//
//        double latDistance = Math.toRadians(lat2 - lat1);
//        double lonDistance = Math.toRadians(lon2 - lon1);
//        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
//                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
//                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//        double distance = R * c * 1000; // convert to meters
//
//        double height = el1 - el2;
//
//        distance = Math.pow(distance, 2) + Math.pow(height, 2);
//
//        return Math.sqrt(distance);
//    }
//
//    public static double calcTripMinutes(double distanceMtr, double speedKmPHr) {
//        double dur = ((distanceMtr / 1000) / speedKmPHr * 60);
//        return dur;
//    }
//

    public ArrayList<PreRouteJobLog> getListPreRouteJob(String oriRunId, String runId) throws Exception {
        ArrayList<PreRouteJobLog> arlistPrj = new ArrayList<>();
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql = "SELECT RunId, Customer_ID, DO_Number, Long, Lat, Customer_priority, Service_time, deliv_start, deliv_end, vehicle_type_list, total_kg, "
                        + "total_cubication, DeliveryDeadline, DayWinStart, DayWinEnd, UpdatevDate, CreateDate, isActive, Is_Exclude, Is_edit, Product_Description, "
                        + "Gross_Amount, DOQty, DOQtyUOM, Name1, Street, Distribution_Channel, Customer_Order_Block_all, Customer_Order_Block, Request_delivery_date, MarketId "
                        + "FROM BOSNET1.dbo.TMS_PreRouteJob "
                        + "WHERE RunId = '" + oriRunId + "';";
                try (ResultSet rs = stm.executeQuery(sql)) {
                    while (rs.next()) {
                        PreRouteJobLog p = new PreRouteJobLog();
                        p.runId = runId;
                        p.custId = rs.getString("Customer_ID");
                        p.doNum = rs.getString("DO_Number");
                        p.lon = rs.getString("Long");
                        p.lat = rs.getString("Lat");
                        p.custPriority = rs.getString("Customer_priority");
                        p.serviceTime = rs.getInt("Service_time");
                        p.delivStart = rs.getString("deliv_start");
                        p.delivEnd = rs.getString("deliv_end");
                        p.vehicleTypeList = rs.getString("vehicle_type_list");
                        p.totalKg = rs.getDouble("total_kg");
                        p.totalCubication = rs.getDouble("total_cubication");
                        p.deliveryDeadline = rs.getString("DeliveryDeadline");
                        p.dayWinStart = rs.getString("DayWinStart");
                        p.dayWinEnd = rs.getString("DayWinEnd");
                        p.updatevDate = rs.getString("UpdatevDate");
                        p.createDate = rs.getString("CreateDate");
                        p.isActive = rs.getString("isActive");
                        p.isExclude = rs.getString("Is_Exclude");
                        p.isEdit = rs.getString("Is_edit");
                        p.productDescription = rs.getString("Product_Description");
                        p.grossAmount = rs.getDouble("Gross_Amount");
                        p.doQty = rs.getDouble("DOQty");
                        p.doQtyUom = rs.getString("DOQtyUOM");
                        p.name1 = rs.getString("Name1");
                        p.street = rs.getString("Street");
                        p.distChannel = rs.getString("Distribution_Channel");
                        p.custOrderBlockAll = rs.getString("Customer_Order_Block_all");
                        p.custOrderBlock = rs.getString("Customer_Order_Block");
                        p.rdd = rs.getString("Request_delivery_date");
                        p.marketId = rs.getString("MarketId");
                        arlistPrj.add(p);
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return arlistPrj;
    }
//
//    public double getCostPerM(String vehicleCode, String runId) throws Exception {
//        double costPerM = 0;
//        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
//            try (Statement stm = con.createStatement()) {
//                String sql = "SELECT TOP 1 costPerM FROM BOSNET1.dbo.TMS_PreRouteVehicle where vehicle_code = '" + vehicleCode + "' and RunId = '" + runId + "';";
//                try (ResultSet rs = stm.executeQuery(sql)) {
//                    while (rs.next()) {
//                        costPerM = rs.getDouble("costPerM");
//                    }
//                }
//            }
//        } catch (Exception e) {
//            throw new Exception(e.getMessage());
//        }
//        return costPerM;
//    }
//

    public ArrayList<PreRouteVehicleLog> getListPreRouteVehicle(String oriRunId, String runId) throws Exception {
        ArrayList<PreRouteVehicleLog> arlistPrv = new ArrayList<>();
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql = "SELECT vehicle_code, weight, volume, vehicle_type, branch, startLon, startLat, endLon, endLat, startTime, endTime, source1, UpdatevDate, "
                        + "CreateDate, isActive, fixedCost, costPerM, costPerServiceMin, costPerTravelMin, IdDriver, NamaDriver "
                        + "FROM BOSNET1.dbo.TMS_PreRouteVehicle "
                        + "WHERE RunId = '" + oriRunId + "';";
                try (ResultSet rs = stm.executeQuery(sql)) {
                    while (rs.next()) {
                        PreRouteVehicleLog p = new PreRouteVehicleLog();
                        p.runId = runId;
                        p.vehicleCode = rs.getString("vehicle_code");
                        p.weight = rs.getString("weight");
                        p.volume = rs.getString("volume");
                        p.vehicleType = rs.getString("vehicle_type");
                        p.branch = rs.getString("branch");
                        p.startLon = rs.getString("startLon");
                        p.startLat = rs.getString("startLat");
                        p.endLon = rs.getString("endLon");
                        p.endLat = rs.getString("endLat");
                        p.startTime = rs.getString("startTime");
                        p.endTime = rs.getString("endTime");
                        p.source1 = rs.getString("source1");
                        p.updatevDate = rs.getString("UpdatevDate");
                        p.createDate = rs.getString("CreateDate");
                        p.isActive = rs.getString("isActive");
                        p.fixedCost = rs.getDouble("fixedCost");
                        p.costPerM = rs.getDouble("costPerM");
                        p.costPerminService = rs.getDouble("costPerServiceMin");
                        p.costPerTravelMin = rs.getDouble("costPerTravelMin");
                        p.IdDriver = rs.getString("IdDriver");
                        p.NamaDriver = rs.getString("NamaDriver");

                        arlistPrv.add(p);
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return arlistPrv;
    }
//
//    private String getVolume(String custId, String runId) throws Exception {
//        String volume = "";
//        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
//            try (Statement stm = con.createStatement()) {
//                String sql = "SELECT TOP 1 volume FROM BOSNET1.dbo.TMS_RouteJob where Customer_ID = '" + custId + "' and runID = '" + runId + "'";
//                try (ResultSet rs = stm.executeQuery(sql)) {
//                    while (rs.next()) {
//                        volume = rs.getString("volume");
//                    }
//                }
//            }
//        } catch (Exception e) {
//            throw new Exception(e.getMessage());
//        }
//        return volume;
//    }
//

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

    public void insertPreRouteVehicle(ArrayList<PreRouteVehicleLog> arlist, String runId) throws Exception {
        int rowNum = 0;
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql;
                sql = "SELECT COUNT(*) total FROM bosnet1.dbo.TMS_PreRouteVehicle WHERE runID = '" + runId + "';";
                try (ResultSet rs = stm.executeQuery(sql)) {
                    while (rs.next()) {
                        rowNum = rs.getInt("total");
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        if (rowNum == 0) {
            String sql = "INSERT INTO bosnet1.dbo.TMS_PreRouteVehicle "
                    + "(RunId, vehicle_code, weight, volume, vehicle_type, branch, startLon, startLat, endLon, endLat, startTime, "
                    + "endTime, source1, UpdatevDate, CreateDate, isActive, fixedCost, costPerM, costPerServiceMin, costPerTravelMin, IdDriver, NamaDriver) "
                    + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

            for (int i = 0; i < arlist.size(); i++) {
                PreRouteVehicleLog p = arlist.get(i);
                try (Connection con = (new Db()).getConnection("jdbc/fztms"); PreparedStatement psHdr = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                    psHdr.setString(1, p.runId);
                    psHdr.setString(2, p.vehicleCode);
                    psHdr.setString(3, p.weight);
                    psHdr.setString(4, p.volume);
                    psHdr.setString(5, p.vehicleType);
                    psHdr.setString(6, p.branch);
                    psHdr.setString(7, p.startLon);
                    psHdr.setString(8, p.startLat);
                    psHdr.setString(9, p.endLon);
                    psHdr.setString(10, p.endLat);
                    psHdr.setString(11, p.startTime);
                    psHdr.setString(12, p.endTime);
                    psHdr.setString(13, p.source1);
                    psHdr.setString(14, p.updatevDate);
                    psHdr.setString(15, p.createDate);
                    psHdr.setString(16, p.isActive);
                    psHdr.setDouble(17, p.fixedCost);
                    psHdr.setDouble(18, p.costPerM);
                    psHdr.setDouble(19, p.costPerminService);
                    psHdr.setDouble(20, p.costPerTravelMin);
                    psHdr.setString(21, p.IdDriver);
                    psHdr.setString(22, p.NamaDriver);

                    psHdr.executeUpdate();
                }
            }
        }
    }

    public void insertToRouteJob(ArrayList<RouteJobLog> arlist, String runId) throws Exception {
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
        if (rowNum == 0) {
            String sql = "INSERT INTO bosnet1.dbo.TMS_RouteJob "
                    + "(job_id, customer_id, do_number, vehicle_code, activity, routeNb, jobNb, arrive, depart, runID, create_dtm, branch, shift, lon, lat, weight, volume, transportCost, activityCost, Dist) "
                    + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

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

    public void insertToPreRouteJob(ArrayList<PreRouteJobLog> arlist, String runId) throws Exception {
        int rowNum = 0;
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql = "SELECT COUNT(*) total FROM bosnet1.dbo.TMS_PreRouteJob WHERE runID = '" + runId + "';";
                try (ResultSet rs = stm.executeQuery(sql)) {
                    while (rs.next()) {
                        rowNum = rs.getInt("total");
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        if (rowNum == 0) {
            String sql = "INSERT INTO bosnet1.dbo.TMS_PreRouteJob "
                    + "(RunId, Customer_ID, DO_Number, Long, Lat, Customer_priority, Service_time, deliv_start, deliv_end, vehicle_type_list, total_kg, "
                    + "total_cubication, DeliveryDeadline, DayWinStart, DayWinEnd, UpdatevDate, CreateDate, isActive, Is_Exclude, Is_Edit, Product_Description, "
                    + "Gross_Amount, DOQTY, DOQTYUOM, Name1, Street, Distribution_Channel, Customer_Order_Block_All, Customer_Order_Block, Request_Delivery_Date, MarketId) "
                    + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

            for (int i = 0; i < arlist.size(); i++) {
                PreRouteJobLog p = arlist.get(i);
                try (Connection con = (new Db()).getConnection("jdbc/fztms"); PreparedStatement psHdr = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                    psHdr.setString(1, p.runId);
                    psHdr.setString(2, p.custId);
                    psHdr.setString(3, p.doNum);
                    psHdr.setString(4, p.lon);
                    psHdr.setString(5, p.lat);
                    psHdr.setString(6, p.custPriority);
                    psHdr.setInt(7, p.serviceTime);
                    psHdr.setString(8, p.delivStart);
                    psHdr.setString(9, p.delivEnd);
                    psHdr.setString(10, p.vehicleTypeList);
                    psHdr.setDouble(11, p.totalKg);
                    psHdr.setDouble(12, p.totalCubication);
                    psHdr.setString(13, p.deliveryDeadline);
                    psHdr.setString(14, p.dayWinStart);
                    psHdr.setString(15, p.dayWinEnd);
                    psHdr.setString(16, p.updatevDate);
                    psHdr.setString(17, p.createDate);
                    psHdr.setString(18, p.isActive);
                    psHdr.setString(19, p.isExclude);
                    psHdr.setString(20, p.isEdit);
                    psHdr.setString(21, p.productDescription);
                    psHdr.setDouble(22, p.grossAmount);
                    psHdr.setDouble(23, p.doQty);
                    psHdr.setString(24, p.doQtyUom);
                    psHdr.setString(25, p.name1);
                    psHdr.setString(26, p.street);
                    psHdr.setString(27, p.distChannel);
                    psHdr.setString(28, p.custOrderBlockAll);
                    psHdr.setString(29, p.custOrderBlock);
                    psHdr.setString(30, p.rdd);
                    psHdr.setString(31, p.marketId);

                    psHdr.executeUpdate();
                }
            }
        }
    }
}

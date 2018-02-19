/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fz.tms.service.run;

import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.tms.params.model.DODetil;
import com.fz.tms.params.model.SummaryVehicle;
import com.fz.tms.params.model.Vehicle;
import com.fz.tms.params.service.Other;
import com.fz.tms.params.service.VehicleAttrDB;
import com.fz.util.FZUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 */
public class RouteJobListing implements BusinessLogic {
    //DecimalFormat df = new DecimalFormat("##.0");
        
    @Override
    public void run(HttpServletRequest request, HttpServletResponse response
            , PageContext pc) throws Exception {
        
        String runID = FZUtil.getHttpParam(request, "runID");
        String OriRunID = FZUtil.getHttpParam(request, "OriRunID");
        String channel = FZUtil.getHttpParam(request, "channel");
        request.setAttribute("channel", channel);
        List<RouteJob> js = new ArrayList<RouteJob>();
        request.setAttribute("JobList", js);
        request.setAttribute("OriRunID", OriRunID);
        request.setAttribute("nextRunId", getTimeID());
        
        String sql = "SELECT\n" +
                "	j.customer_ID,\n" +
                "	(\n" +
                "		SELECT\n" +
                "			(\n" +
                "				stuff(\n" +
                "					(\n" +
                "						SELECT\n" +
                "							'; ' + DO_Number\n" +
                "						FROM\n" +
                "							bosnet1.dbo.TMS_PreRouteJob a\n" +
                "						WHERE\n" +
                "							Is_Edit = 'edit'\n" +
                "							AND Customer_ID = j.customer_ID\n" +
                "							AND RunId = j.runID\n" +
                "						GROUP BY\n" +
                "							DO_Number FOR xml PATH('')\n" +
                "					),\n" +
                "					1,\n" +
                "					2,\n" +
                "					''\n" +
                "				)\n" +
                "			)\n" +
                "	) AS DO_number,\n" +
                "	j.arrive,\n" +
                "	j.depart,\n" +
                "	j.lat,\n" +
                "	j.lon,\n" +
                "	j.vehicle_code,\n" +
                "	j.branch,\n" +
                "	j.shift,\n" +
                "	CASE\n" +
                "		WHEN d.name1 IS NULL\n" +
                "		AND Request_Delivery_Date IS NOT NULL THEN 'UNKNOWN'\n" +
                "		ELSE d.name1\n" +
                "	END name1,\n" +
                "	d.customer_priority,\n" +
                "	d.distribution_channel,\n" +
                "	d.street,\n" +
                "	CAST(\n" +
                "		CAST(\n" +
                "			(\n" +
                "				CAST(\n" +
                "					j.weight AS FLOAT\n" +
                "				)\n" +
                "			) AS NUMERIC(\n" +
                "				15,\n" +
                "				1\n" +
                "			)\n" +
                "		) AS VARCHAR\n" +
                "	) AS weight,\n" +
                "	CAST(\n" +
                "		CAST(\n" +
                "			(\n" +
                "				(\n" +
                "					CAST(\n" +
                "						j.volume AS FLOAT\n" +
                "					)/ 1000000\n" +
                "				)\n" +
                "			) AS NUMERIC(\n" +
                "				15,\n" +
                "				1\n" +
                "			)\n" +
                "		) AS VARCHAR\n" +
                "	) AS volume,\n" +
                "	CASE\n" +
                "		WHEN d.CreateDate <> d.UpdatevDate THEN 'edited'\n" +
                "		WHEN d.CreateDate = d.UpdatevDate THEN 'edit'\n" +
                "	END edit,\n" +
                "	CAST(\n" +
                "		CAST(\n" +
                "			j.transportCost AS NUMERIC(9)\n" +
                "		) AS VARCHAR\n" +
                "	) AS transportCost,\n" +
                "	CAST(\n" +
                "		Dist / 1000 AS NUMERIC(\n" +
                "			9,\n" +
                "			1\n" +
                "		)\n" +
                "	) AS Dist,\n" +
                "	Request_Delivery_Date,\n" +
                "	CASE\n" +
                "		WHEN len(j.customer_ID)> 3\n" +
                "		AND j.vehicle_code <> 'NA'\n" +
                "		AND(\n" +
                "			SELECT\n" +
                "				COUNT( Shipment_Number_Dummy )\n" +
                "			FROM\n" +
                "				BOSNET1.dbo.TMS_Result_Shipment\n" +
                "			WHERE\n" +
                "				Shipment_Number_Dummy = j.Shipment_Number_Dummy\n" +
                "		)= 0 THEN 'OK'\n" +
                "		WHEN j.vehicle_code = 'NA' THEN ''\n" +
                "		WHEN(\n" +
                "			SELECT\n" +
                "				COUNT(*)\n" +
                "			FROM\n" +
                "				BOSNET1.dbo.TMS_Result_Shipment\n" +
                "			WHERE\n" +
                "				Shipment_Number_Dummy = j.Shipment_Number_Dummy\n" +
                "		)> 0\n" +
                "		AND(\n" +
                "			SELECT\n" +
                "				COUNT( CASE WHEN SAP_Status IS NULL THEN 1 ELSE SAP_Status END )\n" +
                "			FROM\n" +
                "				BOSNET1.dbo.TMS_Status_Shipment\n" +
                "			WHERE\n" +
                "				SAP_Status IS NULL\n" +
                "				AND Shipment_Number_Dummy = j.Shipment_Number_Dummy\n" +
                "		)> 0 THEN 'SEND'\n" +
                "		WHEN(\n" +
                "			SELECT\n" +
                "				COUNT(*)\n" +
                "			FROM\n" +
                "				BOSNET1.dbo.TMS_Result_Shipment\n" +
                "			WHERE\n" +
                "				Shipment_Number_Dummy = j.Shipment_Number_Dummy\n" +
                "		)> 0\n" +
                "		AND(\n" +
                "			SELECT\n" +
                "				COUNT( CASE WHEN SAP_Status IS NULL THEN 1 ELSE SAP_Status END )\n" +
                "			FROM\n" +
                "				BOSNET1.dbo.TMS_Status_Shipment\n" +
                "			WHERE\n" +
                "				SAP_Status IS NULL\n" +
                "				AND Shipment_Number_Dummy = j.Shipment_Number_Dummy\n" +
                "		)= 0\n" +
                "		AND(\n" +
                "			SELECT\n" +
                "				DATEDIFF(\n" +
                "					MINUTE,\n" +
                "					Time_Stamp,\n" +
                "					TIMESTAMP\n" +
                "				) AS ts\n" +
                "			FROM\n" +
                "				(\n" +
                "					SELECT\n" +
                "						top 1 Time_Stamp,\n" +
                "						Shipment_Number_Dummy\n" +
                "					FROM\n" +
                "						BOSNET1.dbo.TMS_Result_Shipment\n" +
                "					WHERE\n" +
                "						Shipment_Number_Dummy = j.Shipment_Number_Dummy\n" +
                "				) rs\n" +
                "			INNER JOIN(\n" +
                "					SELECT\n" +
                "						top 1 TIMESTAMP,\n" +
                "						Shipment_Number_Dummy\n" +
                "					FROM\n" +
                "						BOSNET1.dbo.TMS_Status_Shipment\n" +
                "					WHERE\n" +
                "						Shipment_Number_Dummy = j.Shipment_Number_Dummy\n" +
                "				) ss ON\n" +
                "				ss.Shipment_Number_Dummy = rs.Shipment_Number_Dummy\n" +
                "		)> 0 THEN 'DELL'\n" +
                "		WHEN(\n" +
                "			SELECT\n" +
                "				COUNT( Shipment_Number_Dummy )\n" +
                "			FROM\n" +
                "				BOSNET1.dbo.TMS_Result_Shipment\n" +
                "			WHERE\n" +
                "				Shipment_Number_Dummy = j.Shipment_Number_Dummy\n" +
                "		)> 0\n" +
                "		AND(\n" +
                "			SELECT\n" +
                "				COUNT( CASE WHEN SAP_Status IS NULL THEN 1 ELSE SAP_Status END )\n" +
                "			FROM\n" +
                "				BOSNET1.dbo.TMS_Status_Shipment\n" +
                "			WHERE\n" +
                "				SAP_Status IS NULL\n" +
                "				AND Shipment_Number_Dummy = j.Shipment_Number_Dummy\n" +
                "		)= 0 THEN 'INPR'\n" +
                "	END,\n" +
                "	rt.batch\n" +
                "FROM\n" +
                "	(\n" +
                "		SELECT\n" +
                "			*,\n" +
                "			concat(\n" +
                "				REPLACE(\n" +
                "					runID,\n" +
                "					'_',\n" +
                "					''\n" +
                "				),\n" +
                "				vehicle_code\n" +
                "			) AS Shipment_Number_Dummy\n" +
                "		FROM\n" +
                "			bosnet1.dbo.tms_RouteJob\n" +
                "	) j\n" +
                "LEFT OUTER JOIN(\n" +
                "		SELECT\n" +
                "			RunId,\n" +
                "			Customer_ID,\n" +
                "			MIN( Customer_priority ) Customer_priority,\n" +
                "			CreateDate,\n" +
                "			UpdatevDate,\n" +
                "			name1,\n" +
                "			street,\n" +
                "			distribution_channel,\n" +
                "			MIN( Request_Delivery_Date ) Request_Delivery_Date\n" +
                "		FROM\n" +
                "			(\n" +
                "				SELECT\n" +
                "					DISTINCT RunId,\n" +
                "					Customer_ID,\n" +
                "					Customer_priority,\n" +
                "					CreateDate,\n" +
                "					UpdatevDate,\n" +
                "					name1,\n" +
                "					street,\n" +
                "					distribution_channel,\n" +
                "					Request_Delivery_Date\n" +
                "				FROM\n" +
                "					bosnet1.dbo.TMS_PreRouteJob\n" +
                "				WHERE\n" +
                "					Is_Edit = 'edit'\n" +
                "			) a\n" +
                "		GROUP BY\n" +
                "			RunId,\n" +
                "			Customer_ID,\n" +
                "			CreateDate,\n" +
                "			UpdatevDate,\n" +
                "			name1,\n" +
                "			street,\n" +
                "			distribution_channel\n" +
                "	) d ON\n" +
                "	j.runID = d.RunId\n" +
                "	AND j.customer_id = d.Customer_ID\n" +
                "LEFT OUTER JOIN(\n" +
                "		SELECT\n" +
                "			COUNT( CASE WHEN batch IS NULL THEN 1 ELSE 0 END ) AS batch,\n" +
                "			Customer_ID,\n" +
                "			runID\n" +
                "		FROM\n" +
                "			bosnet1.dbo.TMS_PreRouteJob\n" +
                "		WHERE\n" +
                "			Is_Edit = 'ori'\n" +
                "			AND batch IS NULL\n" +
                "		GROUP BY\n" +
                "			Customer_ID,\n" +
                "			runID\n" +
                "	) rt ON\n" +
                "	j.runID = rt.runID\n" +
                "	AND j.customer_id = rt.Customer_ID\n" +
                "WHERE\n" +
                "	j.runID = '"+runID+"'\n" +
                "ORDER BY\n" +
                "	j.routeNb,\n" +
                "	j.jobNb,\n" +
                "	j.arrive";
        
        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)){

            //ps.setString(1, runID);
            
            // get list
            try (ResultSet rs = ps.executeQuery()){
                BigDecimal bcW = new BigDecimal(0);
                BigDecimal bcV = new BigDecimal(0);
                // populate list
                RouteJob prevJ = null;
                int km = 0;
                Set<String> vehicles = new HashSet<String>();
                VehicleAttrDB ar = new VehicleAttrDB();
                int a = 1;
                    
                while (rs.next()) {
                    
                    RouteJob j = new RouteJob();
                    int i = 1;
                    j.runID = runID;
                    j.custID = FZUtil.getRsString(rs, i++, "");
                    j.DONum = FZUtil.getRsString(rs, i++, "");
                    j.arrive = FZUtil.getRsString(rs, i++, "");
                    j.depart = FZUtil.getRsString(rs, i++, "");
                    j.lat = FZUtil.getRsString(rs, i++, "");
                    j.lon = FZUtil.getRsString(rs, i++, "");
                    j.vehicleCode = FZUtil.getRsString(rs, i++, "");
                    j.branch = FZUtil.getRsString(rs, i++, "");
                    j.shift = FZUtil.getRsString(rs, i++, "");
                    
                    j.name1 = FZUtil.getRsString(rs, i++, "");
                    j.custPriority = FZUtil.getRsString(rs, i++, "");
                    j.distrChn = FZUtil.getRsString(rs, i++, "");
                    j.street = FZUtil.getRsString(rs, i++, "");
                    //j.district = FZUtil.getRsString(rs, i++, "");
                    //j.zip = FZUtil.getRsString(rs, i++, "");
                    //j.city = FZUtil.getRsString(rs, i++, "");  
                    
                    if(j.custID.length() > 0 && j.arrive.length() > 0){
                        j.no = a + "";
                        a++;
                    }else if(j.custID.length() == 0 && j.arrive.length() > 0){
                        a = 1;
                    }
                    
                    j.weight =  FZUtil.getRsString(rs, i++, "");
                    j.volume = FZUtil.getRsString(rs, i++, "");
                    j.edit = FZUtil.getRsString(rs, i++, "");
                    j.transportCost = FZUtil.getRsString(rs, i++, "");
                    j.dist = FZUtil.getRsString(rs, i++, "");
                    j.rdd = FZUtil.getRsString(rs, i++, "");
                    j.send = FZUtil.getRsString(rs, i++, "");
                    j.bat = FZUtil.getRsString(rs, i++, "").length() > 0 ? "1" : "0";
                    //System.out.println(j.custID +"_"+j.bat);
                    
                    js.add(j);
                    
                    if(j.custID.equalsIgnoreCase("5820001166")){
                        //System.out.println("com.fz.tms.service.run.RouteJobListing.run()");
                    }
                    
                    //System.out.println(j.toString());
                    //add break row
                    if(j.arrive != ""){
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        Date arv12 = sdf.parse("12:00");
                        Date arvA = sdf.parse(j.arrive);
                        RouteJob temp = null;
                        if(js.get((js.size()-2)).arrive != ""){
                            Date arvB = sdf.parse(js.get((js.size()-2)).arrive);
                            if(arvA.after(arv12) && arvB.before(arv12)){
                                //System.out.println(arvA + " " + arvB);
                                temp = j;
                                j = new RouteJob();
                                j.name1 = "";
                                j.custPriority = "";
                                j.distrChn = "";
                                j.street = "";
                                j.weight = "";
                                j.volume = "";
                                j.edit = "";     
                                j.rdd = "";
                                j.transportCost = "";
                                j.dist = "";
                                j.send = "";  
                                js.add((js.size()-1), j);
                                j = temp;
                            }
                        }
                    }
                    
                    if(!j.vehicleCode.equals("") && !j.vehicleCode.equals("NA"))    vehicles.add(j.vehicleCode);
                    
                    // if no prev job/first time
                    // , keep header infos in request attrib
                    if (prevJ == null){
                        request.setAttribute("runID", runID);
                        request.setAttribute("branch", j.branch);
                        request.setAttribute("shift", j.shift);
                        request.setAttribute("OriRunID", OriRunID);
                    }
                    // else if has prev job within same route
                    else if (prevJ.routeNb == j.routeNb){
                        
                        j.prevJob = prevJ;
                    }
                    
                    //5 =-> 7 
                    //link google map setelah break
                    if(js.get(js.size() - 1).prevJob == null && js.get(js.size() - 1).vehicleCode.length() > 2 && js.size() >= 3){
                        RouteJob pJ = new RouteJob();
                        pJ = (RouteJob) js.get(js.size() - 1);
                        pJ.prevJob = (RouteJob) js.get(js.size() - 3);
                    }
                    
                    // for next round
                    prevJ = j;
                }
                request.setAttribute("vehicleCount"
                        , String.valueOf(vehicles.size()));
                
            }
        }catch(Exception e){
            HashMap<String, String> pl = new HashMap<String, String>();
            pl.put("ID", OriRunID+ "_" +runID);
            pl.put("fileNmethod", "RouteJobListing&run Exc");
            pl.put("datas", sql);
            pl.put("msg", e.getMessage());
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            Date date = new Date();
            pl.put("dates", dateFormat.format(date).toString());
            Other.insertLog(pl);
        }
    }
    public static String getTimeID() {
        String id = (new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(
                        new java.util.Date()));
        return id;
    }

    public String sendSAP(Vehicle he) throws Exception{
        String str = "ERROR";
        
        String sql = "update\n"
                + "	bosnet1.dbo.TMS_RouteJob\n"
                + " set\n"
                + "	isFix = '1'\n"
                + " where\n"
                + "	runID = '" + he.RunId + "'\n"
                + "	and vehicle_code = '" + he.vehicle_code + "'\n"
                + "	and isFix is null";
        try (
            Connection con = (new Db()).getConnection("jdbc/fztms");
            PreparedStatement psHdr = con.prepareStatement(sql
                    , Statement.RETURN_GENERATED_KEYS);
            )  {
            con.setAutoCommit(false);

            psHdr.executeUpdate();
            
             con.setAutoCommit(true);
             str = "OK";
        }
        return str;
    }
    
    public String DeleteResultShipment(Vehicle he) throws Exception{
        String str = "ERROR";
        
        String sql = "DELETE\n" +
                "FROM\n" +
                "	BOSNET1.dbo.TMS_Result_Shipment\n" +
                "WHERE\n" +
                "	Shipment_Number_Dummy = (SELECT\n" +
                "		DISTINCT concat(\n" +
                "			REPLACE(\n" +
                "				runID,\n" +
                "				'_',\n" +
                "				''\n" +
                "			),\n" +
                "			vehicle_code\n" +
                "		)\n" +
                "	FROM\n" +
                "		bosnet1.dbo.tms_RouteJob\n" +
                "	WHERE\n" +
                "		runID = '" + he.RunId + "'\n" +
                "		AND vehicle_code = '" + he.vehicle_code + "')";
        try (
            Connection con = (new Db()).getConnection("jdbc/fztms");
            PreparedStatement psHdr = con.prepareStatement(sql
                    , Statement.RETURN_GENERATED_KEYS);
            )  {
            con.setAutoCommit(false);

            psHdr.executeUpdate();
            
             con.setAutoCommit(true);
             str = "OK";
        }
        return str;
    }
}

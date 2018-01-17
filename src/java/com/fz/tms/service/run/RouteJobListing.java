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
import com.fz.tms.params.service.Other;
import com.fz.tms.params.service.VehicleAttrDB;
import com.fz.util.FZUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                "				9,\n" +
                "				1\n" +
                "			)\n" +
                "		) AS VARCHAR\n" +
                "	) AS weight,\n" +
                "	CAST(\n" +
                "		CAST(\n" +
                "			(\n" +
                "				CAST(\n" +
                "					j.volume AS FLOAT\n" +
                "				)\n" +
                "			) AS NUMERIC(\n" +
                "				9,\n" +
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
                "	cast(Dist / 1000 as Numeric(9,1)) as Dist,\n" +
                "	Request_Delivery_Date\n" +
                "FROM\n" +
                "	bosnet1.dbo.tms_RouteJob j\n" +
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
                "			MIN(Request_Delivery_Date) Request_Delivery_Date\n" +
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
                    
                    js.add(j);
                    
                    //System.out.println(j.toString());
                    if(j.arrive != ""){
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        Date arv12 = sdf.parse("12:00");
                        Date arvA = sdf.parse(j.arrive);
                        if(js.get((js.size()-2)).arrive != ""){
                            Date arvB = sdf.parse(js.get((js.size()-2)).arrive);
                            if(arvA.after(arv12) && arvB.before(arv12)){
                                //System.out.println(arvA + " " + arvB);
                                j = new RouteJob();
                                j.name1 = "";
                                j.custPriority = "";
                                j.distrChn = "";
                                j.street = "";
                                j.weight = "";
                                j.volume = "";
                                j.edit = "";                                
                                js.add((js.size()-1), j);
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
                    
                    // for next round
                    prevJ = j;
                }
                request.setAttribute("vehicleCount"
                        , String.valueOf(vehicles.size()));
                
            }
        }catch(Exception e){
            HashMap<String, String> pl = new HashMap<String, String>();
            pl.put("ID", runID);
            pl.put("fileNmethod", "RouteJobListing&run Exc");
            pl.put("datas", "");
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
}

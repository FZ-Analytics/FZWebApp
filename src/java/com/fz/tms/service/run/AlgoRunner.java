/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.service.run;

import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.tms.params.service.Other;
import com.fz.util.FZUtil;
import com.fz.util.UrlResponseGetter;
import java.sql.Connection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;


public class AlgoRunner implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response,
            PageContext pc
    ) throws Exception {

        String branchCode = FZUtil.getHttpParam(request, "branch");
        String dateDeliv = FZUtil.getHttpParam(request, "dateDeliv");
        String shift = FZUtil.getHttpParam(request, "shift");
        String runId = FZUtil.getHttpParam(request, "runId");
        String reRun = FZUtil.getHttpParam(request, "reRun");
        String oriRunID = FZUtil.getHttpParam(request, "oriRunID");
        String channel = FZUtil.getHttpParam(request, "channel");

        String maxIter = FZUtil.getHttpParam(request, "shift");
        if (maxIter.length() == 0) {
            maxIter = "2000";
        }

        String tripCalc = FZUtil.getHttpParam(request, "tripCalc");
        if (tripCalc.length() == 0) {
            tripCalc = tripCalc(oriRunID);
        }
        
        
        String chn = "";
        if(channel.equalsIgnoreCase("GT")){
            chn = "'GT','FS','IT'";
        }else if(channel.equalsIgnoreCase("MT")){
            chn = "'MT'";
        }else if(channel.equalsIgnoreCase("ALL")){
            chn = "'GT','FS','MT','IT'";
        }
        
        String runID = FZUtil.getTimeID();
        if (reRun.equalsIgnoreCase("N")) {
            runId = runID;
            oriRunID = runID;
        } else if (reRun.equalsIgnoreCase("A")) {
            oriRunID = oriRunID;
        }
        String sql = "insert into bosnet1.dbo.TMS_progress"
                + "(runID"
                + ",status"
                + ",branch"
                + ",shift"
                + ",tripcalc"
                + ",lastupd"
                + ",created"
                + ",DelivDate"
                + ",Re_RunId"
                + ",OriRunId,"
                + "Channel) values(?,?,?,?,?,?,?,?,?,?,?)";

        boolean success = false;
        
        String errMsg = "";

        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)) {

            // TODO: insert to tms_progress
            ps.setString(1, runID);
            ps.setString(2, "NEW");
            ps.setString(3, branchCode);
            ps.setString(4, shift);
            ps.setString(5, tripCalc);
            ps.setString(6, FZUtil.getCurTime());
            ps.setString(7, FZUtil.getCurTime());
            ps.setString(8, dateDeliv);
            ps.setString(9, "-");
            ps.setString(10, oriRunID);
            ps.setString(11, channel);
            ps.executeUpdate();

            success = true;

            // call algo web
            StringBuffer url = new StringBuffer();
            url.append("http://");
            url.append(request.getServerName());
            url.append(":");
            url.append(request.getServerPort());
            url.append("/fztmsalgo/run.jsp?runID=");
            url.append(runID);
            url.append("&dateDeliv=");
            url.append(dateDeliv);

            String resp = "";
            if (success) {     
                if (reRun.equals("A")) {
                    List<HashMap<String, String>> px = selectCust(runId, runID);
                    
                    if(px.size() == 0){
                        errMsg = "Error Select cust algorunner";
                        resp = errMsg;
                    }else   resp = "OK";
                    
                    //cek error data
                    if (resp.equalsIgnoreCase("OK")){
                        errMsg = cekData(runID, runId, "ori", px);
                        resp = errMsg;
                    }
                    
                    if (resp.equalsIgnoreCase("OK")){
                        errMsg = cluster(runId, runID, px);
                        resp = errMsg;
                    }
                    
                    if (resp.equalsIgnoreCase("OK")){
                        errMsg = "Update Prev PreRouteJob Error";
                        resp = updatePrevPreRouteJob(runID, runId);
                    }
                    
                    if (resp.equalsIgnoreCase("OK")){
                        px = selectCust(runId, runID);
                        errMsg = "Insert PreRouteJob Copy ori Error";
                        resp = insertPreRouteJobCopy(runID, runId, branchCode, dateDeliv, "ori", px);
                    }
                    
                    if (resp.equalsIgnoreCase("OK")){
                        errMsg = "Insert PreRouteJob Copy edit Error";
                        resp = insertPreRouteJobCopy(runID, runId, branchCode, dateDeliv, "edit", px);
                    }
                    
                    if (resp.equalsIgnoreCase("OK")){
                        errMsg = "Insert PreRouteVehicle Copy Error";
                        resp = insertPreVehicleCopy(runID, runId, branchCode, dateDeliv, "ori");
                    }
                    
                    if (resp.equalsIgnoreCase("OK") && reRun.equals("A")) {
                        errMsg = "TMSAlgo Error" + url;
                        resp = UrlResponseGetter.getURLResponse(url.toString());
                    }
                    
                    if (resp.equals("OK")) {
                        errMsg = "runProgress Error";
                        response.sendRedirect("runProgress.jsp?runId=" + runID + "&dateDeliv=" + dateDeliv + "&oriRunID=" + oriRunID + "&channel=" + channel);
                    } 
                    
                    if (!resp.equalsIgnoreCase("OK")){
                        throw new Exception(); 
                    }
                    
                } else if (reRun.equals("N")) {
                    errMsg = "TMS_GetCustLongLat Error";
                    resp = prepareCustTable(branchCode);
                    
                    if (resp.equalsIgnoreCase("OK")){
                        errMsg = "Insert PreRouteVehicle Error";
                        resp = insertPreRouteVehicle(runID, branchCode, dateDeliv, chn);
                    }
                    
                    if (resp.equalsIgnoreCase("OK")){
                        errMsg = "Insert PreRouteJob ori Error";
                        resp = QueryCust(runID, branchCode, chn, dateDeliv, "ori");
                    }
                    
                    //resp = insertPreRouteJob(runID, branchCode, dateDeliv, "ori", chn);
                    if (resp.equalsIgnoreCase("OK")){
                        errMsg = "Insert PreRouteJob edit Error";
                        resp = QueryCust(runID, branchCode, chn, dateDeliv, "edit");
                    }
                    
                    //resp = insertPreRouteJob(runID, branchCode, dateDeliv, "edit", chn);
                    if (resp.equalsIgnoreCase("OK")){
                        errMsg = "";
                        response.sendRedirect("../Params/PopUp/popupEditCustBfror.jsp?oriRunID=" + oriRunID + "&dateDeliv="
                            + dateDeliv + "&shift=" + shift + "&reRun=A" + "&branchCode=" + branchCode + "&runId=" + runId + "&channel=" + channel + "&error=N");
                    }
                    
                    if (!resp.equalsIgnoreCase("OK")){
                        throw new Exception(); 
                    }

                }
                
            }
        } catch (Exception e) {
            HashMap<String, String> pl = new HashMap<String, String>();
            
            pl.put("ID", runId);
            pl.put("fileNmethod", "AlgoRunner&run Exc");
            pl.put("datas", "");
            String str = getStackTrace(e);
            pl.put("msg", errMsg +" | "+ str);
            System.out.println("Exception " + str);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            Date date = new Date();
            pl.put("dates", dateFormat.format(date).toString());
            Other.insertLog(pl);            
            
            response.sendRedirect("../Params/PopUp/popupEditCustBfror.jsp?oriRunID=" + oriRunID + "&dateDeliv="
                    + dateDeliv + "&shift=" + shift + "&reRun=A" + "&branchCode=" + branchCode + "&runId=" + runId + "&channel=" + channel  + "&error=Y" + "&errMsg=" + errMsg);
        }
    }

    private static String getStackTrace(Exception ex) {
        StringBuffer sb = new StringBuffer(500);
        StackTraceElement[] st = ex.getStackTrace();
        sb.append(ex.getClass().getName() + ": " + ex.getMessage() + "\n");
        for (int i = 0; i < st.length; i++) {
          sb.append("\t at " + st[i].toString() + "\n");
        }
        return sb.toString();
    }
    
    public String insertPreRouteJobCopy(String runID, String prevRunID, String branchCode,
            String dateDeliv, String str, List<HashMap<String, String>> zx)
            throws Exception {

        String cds = "ERROR insertPreRouteJob";
        List<HashMap<String, String>> asd = zx;
        HashMap<String, String> pl = new HashMap<String, String>();
        String sql = "";
        
        if(asd.size() > 0){
            sql = "INSERT\n" +
            "	INTO\n" +
            "		bosnet1.dbo.TMS_PreRouteJob(\n" +
            "			RunId,\n" +
            "			Customer_ID,\n" +
            "			DO_Number,\n" +
            "			Long,\n" +
            "			Lat,\n" +
            "			Customer_priority,\n" +
            "			Service_time,\n" +
            "			deliv_start,\n" +
            "			deliv_end,\n" +
            "			vehicle_type_list,\n" +
            "			total_kg,\n" +
            "			total_cubication,\n" +
            "			DeliveryDeadline,\n" +
            "			DayWinStart,\n" +
            "			DayWinEnd,\n" +
            "			UpdatevDate,\n" +
            "			CreateDate,\n" +
            "			isActive,\n" +
            "			Is_Exclude,\n" +
            "			Is_Edit,\n" +
            "			Product_Description,\n" +
            "			Gross_Amount,\n" +
            "			DOQty,\n" +
            "			DOQtyUOM,\n" +
            "			Name1,\n" +
            "			Street,\n" +
            "			Distribution_Channel,\n" +
            "			Customer_Order_Block_all,\n" +
            "			Customer_Order_Block,\n" +
            "			Request_Delivery_Date,\n" +
            "			Desa_Kelurahan,\n" +
            "			Kecamatan,\n" +
            "			Kodya_Kabupaten,\n" +
            "			Batch,\n" +
            "			Ket_DO\n" +
            "		)  values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            
            asd = treeSAP(asd);
            try (Connection con = (new Db()).getConnection("jdbc/fztms")){
                try (PreparedStatement ps = con.prepareStatement(sql) ){
                    ps.clearParameters(); 
                    for(int a = 0;a<asd.size();a++){ 
                        if(asd.get(a).size() > 0 && Integer.valueOf(asd.get(a).get("Customer_priority")) < 10){
                            int i = 1;
                            ps.setString(i++, asd.get(a).get("RunId"));
                            ps.setString(i++, asd.get(a).get("Customer_ID"));
                            ps.setString(i++, asd.get(a).get("DO_Number"));
                            ps.setString(i++, asd.get(a).get("Long"));
                            ps.setString(i++, asd.get(a).get("Lat"));                        
                            ps.setInt(i++, Integer.parseInt(asd.get(a).get("Customer_priority")));
                            ps.setInt(i++, Integer.parseInt(asd.get(a).get("Service_time")));
                            ps.setString(i++, asd.get(a).get("deliv_start"));
                            ps.setString(i++, asd.get(a).get("deliv_end"));
                            ps.setString(i++, asd.get(a).get("vehicle_type_list"));
                            ps.setDouble(i++, Double.valueOf(asd.get(a).get("total_kg")));
                            ps.setDouble(i++, Double.valueOf(asd.get(a).get("total_cubication")));
                            ps.setString(i++, asd.get(a).get("DeliveryDeadline"));
                            ps.setString(i++, asd.get(a).get("DayWinStart"));
                            ps.setString(i++, asd.get(a).get("DayWinEnd"));
                            ps.setString(i++, asd.get(a).get("UpdatevDate"));
                            ps.setString(i++, asd.get(a).get("CreateDate"));   
                            ps.setString(i++, "1");
                            ps.setString(i++, "inc");
                            ps.setString(i++, str);
                            ps.setString(i++, asd.get(a).get("Product_Description"));
                            ps.setDouble(i++, Double.valueOf(asd.get(a).get("Gross_Amount")));
                            ps.setDouble(i++, Double.valueOf(asd.get(a).get("DOQty")));
                            ps.setString(i++, asd.get(a).get("DOQtyUOM"));  
                            ps.setString(i++, asd.get(a).get("Name1"));
                            ps.setString(i++, asd.get(a).get("Street"));
                            ps.setString(i++, asd.get(a).get("Distribution_Channel"));
                            ps.setString(i++, asd.get(a).get("Customer_Order_Block_all"));
                            ps.setString(i++, asd.get(a).get("Customer_Order_Block"));   
                            ps.setString(i++, asd.get(a).get("Request_Delivery_Date")); 
                            //ps.setString(i++, asd.get(a).get("marketId")); 
                            ps.setString(i++, asd.get(a).get("Desa_Kelurahan"));   
                            ps.setString(i++, asd.get(a).get("Kecamatan")); 
                            ps.setString(i++, asd.get(a).get("Kodya_Kabupaten")); 
                            ps.setString(i++, asd.get(a).get("Batch")); 
                            ps.setString(i++, asd.get(a).get("Ket_DO")); 
                            ps.addBatch();
                        }
                    }
                    ps.executeBatch();
                }
            }
            cds = "OK";
        }

        return cds;
    }
    
    public String insertPreVehicleCopy(String runID, String prevRunID, String branchCode,
            String dateDeliv, String str)
            throws Exception {

        String cds = "ERROR insertPreRouteVehicle";

        String sql = "INSERT\n" +
                "	INTO\n" +
                "		BOSNET1.dbo.TMS_PreRouteVehicle SELECT distinct\n" +
                "			'"+runID+"' AS RunId,\n" +
                "			vehicle_code,\n" +
                "			weight,\n" +
                "			volume,\n" +
                "			vehicle_type,\n" +
                "			branch,\n" +
                "			startLon,\n" +
                "			startLat,\n" +
                "			endLon,\n" +
                "			endLat,\n" +
                "			startTime,\n" +
                "			endTime,\n" +
                "			source1,\n" +
                "			UpdatevDate,\n" +
                "			CreateDate,\n" +
                "			isActive,\n" +
                "			fixedCost,\n" +
                "			costPerM,\n" +
                "			costPerServiceMin,\n" +
                "			costPerTravelMin,\n" +
                "			IdDriver,\n" +
                "			NamaDriver,\n" +
                "			agent_priority,\n" +
                "			max_cust\n" +
                "		FROM\n" +
                "			BOSNET1.dbo.TMS_PreRouteVehicle\n" +
                "		WHERE\n" +
                "			RunId = '"+prevRunID+"' and isActive = 1";

        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)) {

            con.setAutoCommit(false);
            ps.executeUpdate();
            con.setAutoCommit(true);

            cds = "OK";
        }catch (Exception e) {
            HashMap<String, String> pl = new HashMap<String, String>();
            pl.put("ID", runID);
            pl.put("fileNmethod", "AlgoRunner&insertPreRouteJobCopy Exc");
            pl.put("datas", "");
            pl.put("msg", e.getMessage());
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            Date date = new Date();
            pl.put("dates", dateFormat.format(date).toString());
            Other.insertLog(pl);
            throw e;
        }

        return cds;
    }

    public String insertPreRouteVehicle(String runID, String branchCode,
            String dateDeliv, String shn)
            throws Exception {

        String cds = "ERROR insertPreRouteVehicle";

        String sql = "INSERT\n" +
                "	INTO\n" +
                "		bosnet1.dbo.TMS_PreRouteVehicle(\n" +
                "			RunId,\n" +
                "			vehicle_code,\n" +
                "			weight,\n" +
                "			volume,\n" +
                "			vehicle_type,\n" +
                "			branch,\n" +
                "			startLon,\n" +
                "			startLat,\n" +
                "			endLon,\n" +
                "			endLat,\n" +
                "			startTime,\n" +
                "			endTime,\n" +
                "			source1,\n" +
                "			UpdatevDate,\n" +
                "			CreateDate,\n" +
                "			isActive,\n" +
                "			fixedCost,\n" +
                "			costPerM,\n" +
                "			costPerServiceMin,\n" +
                "			costPerTravelMin,\n" +
                "			IdDriver,\n" +
                "			NamaDriver,\n" +
                "			agent_priority,\n" +
                "			max_cust\n" +
                "		) SELECT\n" +
                "			'"+runID+"' AS RunId,\n" +
                "			v.vehicle_code,\n" +
                "			CASE\n" +
                "				WHEN vh.vehicle_code IS NULL THEN va.weight\n" +
                "				ELSE vh.weight\n" +
                "			END AS weight,\n" +
                "			CAST(\n" +
                "				CASE\n" +
                "					WHEN vh.vehicle_code IS NULL THEN va.volume\n" +
                "					ELSE vh.volume\n" +
                "				END AS NUMERIC(\n" +
                "					18,\n" +
                "					3\n" +
                "				)\n" +
                "			)* 1000000 AS volume," +
                "			CASE\n" +
                "				WHEN vh.vehicle_code IS NULL THEN va.vehicle_type\n" +
                "				ELSE vh.vehicle_type\n" +
                "			END AS vehicle_type,\n" +
                "			CASE\n" +
                "				WHEN vh.vehicle_code IS NULL THEN va.branch\n" +
                "				ELSE vh.plant\n" +
                "			END AS branch,\n" +
                "			va.startLon,\n" +
                "			va.startLat,\n" +
                "			va.endLon,\n" +
                "			va.endLat,\n" +
                "			va.startTime,\n" +
                "			CONVERT(\n" +
                "				VARCHAR(5),\n" +
                "				dateadd(\n" +
                "					HOUR,\n" +
                "					- 1,\n" +
                "					CAST(\n" +
                "						va.endTime AS TIME\n" +
                "					)\n" +
                "				)\n" +
                "			) endTime,\n" +
                "			va.source1,\n" +
                "			'"+dateDeliv+"' AS UpdatevDate,\n" +
                "			'"+dateDeliv+"' AS CreateDate,\n" +
                "			'1' AS isActive,\n" +
                "			va.fixedCost,\n" +
                "			pr.value /(\n" +
                "				va.costPerM * 1000\n" +
                "			) AS costPerM,\n" +
                "			0 AS costPerServiceMin,\n" +
                "			0 AS costPerTravelMin,\n" +
                "			va.IdDriver,\n" +
                "			va.NamaDriver,\n" +
                "			CASE\n" +
                "				WHEN va.agent_priority is null THEN rt.value\n" +
                "				ELSE va.agent_priority\n" +
                "			END AS agent_priority,\n" +
                "			CASE\n" +
                "				WHEN va.max_cust is null THEN ry.value\n" +
                "				ELSE va.max_cust\n" +
                "			END AS max_cust\n" +
                "		FROM\n" +
                "			(\n" +
                "				SELECT\n" +
                "					DISTINCT vehicle_code\n" +
                "				FROM\n" +
                "					(\n" +
                "						SELECT\n" +
                "							vehicle_code\n" +
                "						FROM\n" +
                "							bosnet1.dbo.vehicle\n" +
                "						WHERE\n" +
                "							plant = '"+branchCode+"'\n" +
                "					UNION SELECT\n" +
                "							vehicle_code\n" +
                "						FROM\n" +
                "							bosnet1.dbo.TMS_VehicleAtr\n" +
                "						WHERE\n" +
                "							branch = '"+branchCode+"'\n" +
                "							AND included = 1\n" +
                "					) vi\n" +
                "			) v\n" +
                "		LEFT OUTER JOIN bosnet1.dbo.vehicle vh ON\n" +
                "			v.vehicle_code = vh.vehicle_code\n" +
                "		LEFT OUTER JOIN bosnet1.dbo.TMS_vehicleAtr va ON\n" +
                "			v.vehicle_code = va.vehicle_code\n" +
                "		LEFT OUTER JOIN bosnet1.dbo.TMS_Params pr ON\n" +
                "			pr.param = 'HargaSolar'\n" +
                "		LEFT OUTER JOIN bosnet1.dbo.TMS_Params bm ON\n" +
                "			bm.param = 'DefaultKonsumsiBBm'\n" +
                "		LEFT OUTER JOIN bosnet1.dbo.TMS_Params rt ON\n" +
                "			rt.param = 'Defaultagentpriority'\n" +
                "		LEFT OUTER JOIN bosnet1.dbo.TMS_Params ry ON\n" +
                "			ry.param = 'DefaultMaxCust'\n" +
                "		WHERE\n" +
                "			va.included = 1\n" +
                "			and va.Channel in (" + shn + ");";

        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)) {

            con.setAutoCommit(false);
            ps.executeUpdate();
            con.setAutoCommit(true);

            cds = "OK";
        } catch (Exception e) {
            HashMap<String, String> pl = new HashMap<String, String>();
            pl.put("ID", runID);
            pl.put("fileNmethod", "AlgoRunner&insertPreRouteVehicle Exc");
            pl.put("datas", "");
            pl.put("msg", e.getMessage());
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            Date date = new Date();
            pl.put("dates", dateFormat.format(date).toString());
            Other.insertLog(pl);
            throw e;
        }

        return cds;
    }

    public String updatePrevPreRouteJob(String RunId, String prevRunId) throws Exception {
        String str = "ERROR";
        String sql = "update bosnet1.dbo.TMS_progress"
                + " set Re_RunId = '" + RunId + "'"
                + " where runID = '" + prevRunId + "'";
        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)) {

            con.setAutoCommit(false);
            ps.executeUpdate();
            con.setAutoCommit(true);

            str = "OK";
        }catch (Exception e) {
            HashMap<String, String> pl = new HashMap<String, String>();
            pl.put("ID", RunId);
            pl.put("fileNmethod", "AlgoRunner&updatePrevPreRouteJob Exc");
            pl.put("datas", "");
            pl.put("msg", e.getMessage());
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            Date date = new Date();
            pl.put("dates", dateFormat.format(date).toString());
            Other.insertLog(pl);
            throw e;
        }

        return str;
    }
    
    private static String prepareCustTable(String branchCode) 
        throws Exception {
        String str = "ERROR";
        String sql = "EXEC bosnet1.dbo.TMS_GetCustLongLat ?";
        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setEscapeProcessing(true);
            ps.setQueryTimeout(15);
            ps.setString(1, branchCode);
            ps.execute();
            str = "OK";
        }catch (Exception e) {
            //HashMap<String, String> pl = new HashMap<String, String>();
            //pl.put("ID", "");
            //pl.put("fileNmethod", "AlgoRunner&prepareCustTable Exc");
            //pl.put("datas", "");
            //pl.put("msg", e.getMessage());
            //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            //Date date = new Date();
            //pl.put("dates", dateFormat.format(date).toString());
            //Other.insertLog(pl);
            //throw e;
            str = e.getMessage();
        }
        return str;
    }
    
    public String tripCalc(String OriRunId) throws Exception{
        String str = "";
        String sql = "SELECT tripcalc FROM BOSNET1.dbo.TMS_Progress where runID = '"+OriRunId+"'";
        
        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int i = 1;
                    str = FZUtil.getRsString(rs, i++, "");
                }
            }
        }
        return str;
    }
    
    public String QueryCust(String runId, String branchCode,
            String chn, String dateDeliv, String edt) throws Exception{
        String str = "ERROR";
        List<HashMap<String, String>> asd = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> pl = new HashMap<String, String>();
        
        String query = "";
        query = "	AND cs.Distribution_Channel IN("+chn+")";
        /*
        if(chn.equals("GT")){
            query = "	AND cs.Distribution_Channel NOT IN('MT')";
        }else if(chn.equals("MT")){
            query = "	AND cs.Distribution_Channel IN('MT')";
        }else if(chn.equals("ALL")){
            query = "";
        }*/
        
        String sql = "SELECT\n" +
                "	sp.Customer_ID,\n" +
                "	cl.MarketId,\n" +
                "	sp.DO_Number,\n" +
                "	CASE\n" +
                "		WHEN cl.Long IS NULL\n" +
                "		OR cl.Long = '' THEN 'n/a'\n" +
                "		ELSE cl.Long\n" +
                "	END AS Long,\n" +
                "	CASE\n" +
                "		WHEN cl.Lat IS NULL\n" +
                "		OR cl.Lat = '' THEN 'n/a'\n" +
                "		ELSE cl.Lat\n" +
                "	END AS Lat,\n" +
                "	CASE\n" +
                "		WHEN cs.Customer_priority IS NULL THEN df.value\n" +
                "		ELSE SUBSTRING( CAST( cs.Customer_priority AS VARCHAR ), 2, 1 )\n" +
                "	END Customer_priority,\n" +
                "	CASE\n" +
                "		WHEN ca.Service_time IS NULL THEN dr.value\n" +
                "		ELSE ca.Service_time\n" +
                "	END Service_time,\n" +
                "	CASE\n" +
                "		WHEN ca.deliv_start IS NULL THEN dg.value\n" +
                "		ELSE ca.deliv_start\n" +
                "	END deliv_start,\n" +
                "	CASE\n" +
                "		WHEN ca.deliv_end IS NULL THEN dt.value\n" +
                "		ELSE ca.deliv_end\n" +
                "	END deliv_end,\n" +
                "	CASE\n" +
                "		WHEN ca.vehicle_type_list IS NULL THEN dh.value\n" +
                "		ELSE ca.vehicle_type_list\n" +
                "	END vehicle_type_list,\n" +
                "	sp.total_kg_item,\n" +
                "	sp.total_cubication,\n" +
                "	CASE\n" +
                "		WHEN ca.DeliveryDeadline IS NULL THEN CASE\n" +
                "			WHEN cs.Distribution_Channel = 'MT' THEN dy.value\n" +
                "			ELSE dd.value\n" +
                "		END\n" +
                "		ELSE ca.DeliveryDeadline\n" +
                "	END DeliveryDeadline,\n" +
                "	CASE\n" +
                "		WHEN ca.DayWinStart IS NULL or ca.DayWinStart = '' THEN ds.value\n" +
                "		ELSE ca.DayWinStart\n" +
                "	END DayWinStart,\n" +
                "	CASE\n" +
                "		WHEN ca.DayWinEnd IS NULL or ca.DayWinEnd = '' THEN de.value\n" +
                "		ELSE ca.DayWinEnd\n" +
                "	END DayWinEnd,\n" +
                "	CAST(\n" +
                "		FORMAT(\n" +
                "			getdate(),\n" +
                "			'yyyy-MM-dd hh-mm'\n" +
                "		) AS VARCHAR\n" +
                "	) AS UpdatevDate,\n" +
                "	CAST(\n" +
                "		FORMAT(\n" +
                "			getdate(),\n" +
                "			'yyyy-MM-dd hh-mm'\n" +
                "		) AS VARCHAR\n" +
                "	) AS CreateDate,\n" +
                "	CAST(\n" +
                "		FORMAT(\n" +
                "			sp.Request_Delivery_Date,\n" +
                "			'yyyy-MM-dd'\n" +
                "		) AS VARCHAR\n" +
                "	) AS Request_Delivery_Date,\n" +
                "	sp.Product_Description,\n" +
                "	sp.Gross_Amount,\n" +
                "	sp.DOQty,\n" +
                "	sp.DOQtyUOM,\n" +
                "	CASE\n" +
                "		WHEN cs.Name1 IS NULL THEN 'UNKNOWN'\n" +
                "		ELSE cs.Name1\n" +
                "	END AS Name1,\n" +
                "	CASE\n" +
                "		WHEN cs.Street IS NULL THEN 'UNKNOWN'\n" +
                "		ELSE cs.Street\n" +
                "	END AS Street,\n" +
                "	cs.Distribution_Channel,\n" +
                "	cs.Customer_Order_Block_all,\n" +
                "	cs.Customer_Order_Block,\n" +
                "	df.value AS Priority_value,\n" +
                "	dn.value AS BufferEndDefault,\n" +
                "	dj.value AS SatDelivDefault,\n" +
                "	du.value AS ChannelNullDefault,\n" +
                "	cs.Desa_Kelurahan,\n" +
                "	cs.Kecamatan,\n" +
                "	cs.Kodya_Kabupaten,\n" +
                "	sp.Batch\n" +
                "FROM\n" +
                "	bosnet1.dbo.TMS_ShipmentPlan sp\n" +
                "LEFT OUTER JOIN(\n" +
                "		SELECT\n" +
                "			a.*\n" +
                "		FROM\n" +
                "			(\n" +
                "				SELECT\n" +
                "					ROW_NUMBER() OVER(\n" +
                "						PARTITION BY Customer_ID\n" +
                "					ORDER BY\n" +
                "						Customer_ID\n" +
                "					) AS noId,\n" +
                "					*\n" +
                "				FROM\n" +
                "					bosnet1.dbo.customer\n" +
                "				WHERE\n" +
                "					(\n" +
                "						Customer_Order_Block IS NULL\n" +
                "						OR Customer_Order_Block = ''\n" +
                "					)\n" +
                "					AND(\n" +
                "						Customer_Order_Block_all IS NULL\n" +
                "						OR Customer_Order_Block_all = ''\n" +
                "					)\n" +
                "			) a\n" +
                "		WHERE\n" +
                "			a.noid = 1\n" +
                "	) cs ON\n" +
                "	sp.customer_id = cs.customer_id\n" +
                "LEFT JOIN(\n" +
                "		SELECT\n" +
                "			*\n" +
                "		FROM\n" +
                "			(\n" +
                "				SELECT\n" +
                "					ROW_NUMBER() OVER(\n" +
                "						PARTITION BY custid\n" +
                "					ORDER BY\n" +
                "						custid\n" +
                "					) AS noId,\n" +
                "					*\n" +
                "				FROM\n" +
                "					bosnet1.dbo.TMS_CustLongLat\n" +
                "			) a\n" +
                "		WHERE\n" +
                "			a.noid = 1\n" +
                "	) cl ON\n" +
                "	sp.customer_id = cl.custID\n" +
                "LEFT OUTER JOIN(\n" +
                "		SELECT\n" +
                "			tu.Delivery_Number\n" +
                "		FROM\n" +
                "			BOSNET1.dbo.TMS_Result_Shipment ty\n" +
                "		INNER JOIN BOSNET1.dbo.TMS_Status_Shipment tu ON\n" +
                "			ty.Delivery_Number = tu.Delivery_Number\n" +
                "		WHERE\n" +
                "			tu.SAP_Status IS NULL\n" +
                "	) ss ON\n" +
                "	sp.DO_Number = ss.Delivery_Number\n" +
                "LEFT OUTER JOIN(\n" +
                "		SELECT\n" +
                "			ty.Delivery_Number\n" +
                "		FROM\n" +
                "			BOSNET1.dbo.TMS_Result_Shipment ty\n" +
                "		LEFT OUTER JOIN BOSNET1.dbo.TMS_Status_Shipment tu ON\n" +
                "			ty.Delivery_Number = tu.Delivery_Number\n" +
                "		WHERE\n" +
                "			tu.Delivery_Number IS NULL\n" +
                "	) sn ON\n" +
                "	sp.DO_Number = sn.Delivery_Number\n" +
                "LEFT OUTER JOIN bosnet1.dbo.TMS_CustAtr ca ON\n" +
                "	sp.customer_id = ca.customer_id\n" +
                "LEFT OUTER JOIN bosnet1.dbo.TMS_Params dd ON\n" +
                "	dd.param = 'DeliveryDeadLine'\n" +
                "LEFT OUTER JOIN bosnet1.dbo.TMS_Params ds ON\n" +
                "	ds.param = 'DayWinStart'\n" +
                "LEFT OUTER JOIN bosnet1.dbo.TMS_Params de ON\n" +
                "	de.param = 'DayWinEnd'\n" +
                "LEFT OUTER JOIN bosnet1.dbo.TMS_Params df ON\n" +
                "	df.param = 'DefaultCustPriority'\n" +
                "LEFT OUTER JOIN bosnet1.dbo.TMS_Params dr ON\n" +
                "	dr.param = 'DefaultCustServiceTime'\n" +
                "LEFT OUTER JOIN bosnet1.dbo.TMS_Params dg ON\n" +
                "	dg.param = 'DefaultCustStartTime'\n" +
                "LEFT OUTER JOIN bosnet1.dbo.TMS_Params dt ON\n" +
                "	dt.param = 'DefaultCustEndTime'\n" +
                "LEFT OUTER JOIN bosnet1.dbo.TMS_Params dh ON\n" +
                "	dh.param = 'DefaultCustVehicleTypes'\n" +
                "LEFT OUTER JOIN bosnet1.dbo.TMS_Params dy ON\n" +
                "	dy.param = 'MTDefault'\n" +
                "LEFT OUTER JOIN bosnet1.dbo.TMS_Params dn ON\n" +
                "	dn.param = 'BufferEndDefault'\n" +
                "LEFT OUTER JOIN bosnet1.dbo.TMS_Params dj ON\n" +
                "	dj.param = 'SatDelivDefault'\n" +
                "LEFT OUTER JOIN bosnet1.dbo.TMS_Params du ON\n" +
                "	du.param = 'ChannelNullDefault'\n" +
                "WHERE\n" +
                "	sp.plant = '"+branchCode+"'\n" +
                "	AND sp.already_shipment = 'N'\n" +
                "	AND sp.notused_flag IS NULL\n" +
                "	AND sp.incoterm = 'FCO'\n" +
                "	AND(\n" +
                "		sp.Order_Type = 'ZDCO'\n" +
                "		OR sp.Order_Type = 'ZDTO'\n" +
                "	)\n" +
                "	AND sp.create_date >= DATEADD(\n" +
                "		DAY,\n" +
                "		- 7,\n" +
                "		GETDATE()\n" +
                "	)\n" +
                "	AND ss.Delivery_Number IS NULL\n" +
                "	AND sn.Delivery_Number IS NULL\n" +
                query + "\n" +
                "ORDER BY\n" +
                "	sp.Customer_ID ASC\n";
        
        System.out.println("QueryCust()" + sql);
        //select
        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)) {
            System.out.println(sql);
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    pl = new HashMap<String, String>();
                    pl.put("Customer_ID", rs.getString("Customer_ID"));
                    pl.put("DO_Number", rs.getString("DO_Number"));
                    pl.put("Long", rs.getString("Long"));
                    pl.put("Lat", rs.getString("Lat"));
                    pl.put("Customer_priority", rs.getString("Customer_priority"));
                    pl.put("Service_time", rs.getString("Service_time"));
                    pl.put("deliv_start", rs.getString("deliv_start"));
                    pl.put("deliv_end", rs.getString("deliv_end"));
                    pl.put("vehicle_type_list", rs.getString("vehicle_type_list"));
                    pl.put("total_kg_item", rs.getString("total_kg_item"));
                    pl.put("total_cubication", rs.getString("total_cubication"));
                    pl.put("DeliveryDeadline", rs.getString("DeliveryDeadline"));
                    pl.put("DayWinStart", rs.getString("DayWinStart"));
                    pl.put("DayWinEnd", rs.getString("DayWinEnd"));
                    pl.put("Request_Delivery_Date", rs.getString("Request_Delivery_Date"));
                    pl.put("Product_Description", rs.getString("Product_Description"));
                    pl.put("Gross_Amount", rs.getString("Gross_Amount"));
                    pl.put("DOQty", rs.getString("DOQty"));
                    pl.put("DOQtyUOM", rs.getString("DOQtyUOM"));
                    pl.put("Name1", rs.getString("Name1"));
                    pl.put("Street", rs.getString("Street"));
                    pl.put("Distribution_Channel", rs.getString("Distribution_Channel"));
                    pl.put("Customer_Order_Block_all", rs.getString("Customer_Order_Block_all"));
                    pl.put("Customer_Order_Block", rs.getString("Customer_Order_Block"));
                    pl.put("Priority_value", rs.getString("Priority_value"));
                    pl.put("BufferEndDefault", rs.getString("BufferEndDefault"));
                    pl.put("SatDelivDefault", rs.getString("SatDelivDefault"));
                    pl.put("ChannelNullDefault", rs.getString("ChannelNullDefault"));
                    pl.put("marketId", rs.getString("marketId"));
                    pl.put("Desa_Kelurahan", rs.getString("Desa_Kelurahan"));
                    pl.put("Kecamatan", rs.getString("Kecamatan"));
                    pl.put("Kodya_Kabupaten", rs.getString("Kodya_Kabupaten"));
                    pl.put("Batch", rs.getString("Batch"));
                    //pl.put("DOCreationDate", rs.getString("DOCreationDate"));                    
                    asd.add(pl);
                    //System.out.println(pl.toString());
                    if(pl.get("DO_Number").equals("8020098809")){                        
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
                        Date rdd = sdf.parse(rs.getString("Request_Delivery_Date"));
                        //System.out.println(rdd.toString());
                        //System.out.println(pl.toString());
                    }
                    str = "OK";
                }
            }
        }
        
        //insert
        if(str.equals("OK")){
            sql = "INSERT\n" +
                "	INTO\n" +
                "		bosnet1.dbo.TMS_PreRouteJob(\n" +
                "			RunId,\n" +
                "			Customer_ID,\n" +
                "			DO_Number,\n" +
                "			Long,\n" +
                "			Lat,\n" +
                "			Customer_priority,\n" +
                "			Service_time,\n" +
                "			deliv_start,\n" +
                "			deliv_end,\n" +
                "			vehicle_type_list,\n" +
                "			total_kg,\n" +
                "			total_cubication,\n" +
                "			DeliveryDeadline,\n" +
                "			DayWinStart,\n" +
                "			DayWinEnd,\n" +
                "			UpdatevDate,\n" +
                "			CreateDate,\n" +
                "			isActive,\n" +
                "			Is_Exclude,\n" +
                "			Is_Edit,\n" +
                "			Product_Description,\n" +
                "			Gross_Amount,\n" +
                "			DOQty,\n" +
                "			DOQtyUOM,\n" +
                "			Name1,\n" +
                "			Street,\n" +
                "			Distribution_Channel,\n" +
                "			Customer_Order_Block_all,\n" +
                "			Customer_Order_Block,\n" +
                "			Request_Delivery_Date,\n" +
                "			MarketId,\n" +
                "			Desa_Kelurahan,\n" +
                "			Kecamatan,\n" +
                "			Kodya_Kabupaten,\n" +
                "			Batch,\n" +
                "			Ket_DO\n" +
                "		) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
            List<HashMap<String, String>> ins = new ArrayList<HashMap<String, String>>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");            
            Calendar c = Calendar.getInstance();
            Date dDeliv = sdf.parse(dateDeliv);
            System.out.println("QueryCust()");
            for(int a = 0;a<asd.size();a++){ 
                c = Calendar.getInstance();
                pl = new HashMap<String, String>();
                Date rdd = sdf.parse(asd.get(a).get("Request_Delivery_Date"));
                pl = asd.get(a);      
                //System.out.println(pl.toString());
                
                //cek hari buka
                
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                //System.out.println(dayOfWeek);
                if(pl.get("DO_Number").equals("8020089252")){
                    System.out.println(rdd);
                    String g = edt;
                    //System.out.println(dayOfWeek + "()" + Integer.parseInt(asd.get(a).get("DayWinStart")));
                    //System.out.println(pl.get("5820002148"));
                }
                if(dayOfWeek >= Integer.parseInt(asd.get(a).get("DayWinStart"))
                        && dayOfWeek <= Integer.parseInt(asd.get(a).get("DayWinEnd"))){
                    //System.out.println(asd.get(a).get("DayWinStart") + "()" + asd.get(a).get("DayWinEnd"));
                    //System.out.println(asd.get(a).get("DeliveryDeadline") + " " + asd.get(a).get("Request_Delivery_Date"));
                    
                    
                    //cek rule
                    /*
                    if(!asd.get(a).get("DeliveryDeadline").equals("AFTR")){
                        if(dDeliv.compareTo(rdd) <= 0){
                            pl = tree(asd.get(a), rdd, dDeliv, asd.get(a).get("DeliveryDeadline"), chn);
                        }else{
                            pl = new HashMap<String, String>();
                        }
                    }else if(asd.get(a).get("DeliveryDeadline").equals("AFTR")){
                        c.setTime(rdd);
                        c.add(Calendar.DATE, 7);
                        rdd = sdf.parse(sdf.format(c.getTime()));
                        if(dDeliv.compareTo(rdd) <= 0){
                            pl = tree(asd.get(a), rdd, dDeliv, asd.get(a).get("DeliveryDeadline"), chn);
                        }else{
                            pl = new HashMap<String, String>();
                        }
                    }*/
                    
                    if(dDeliv.compareTo(rdd) <= 7){
                        pl = tree(asd.get(a), dDeliv, asd.get(a).get("DeliveryDeadline"), chn);
                    }else{
                        pl = new HashMap<String, String>();
                    }
                    
                    if(pl != null){
                        ins.add(pl);
                    }
                    
                    //System.out.println("dDeliv.compareTo(rdd) " + dDeliv.compareTo(rdd));
                    //System.out.println(pl.toString());
                }                
            }
            
            ins = treeSAP(asd);
            //cekData(ins);
            
            try (Connection con = (new Db()).getConnection("jdbc/fztms")){
                try (PreparedStatement ps = con.prepareStatement(sql) ){
                        ps.clearParameters();                    
                    for(int a = 0;a<ins.size();a++){ 
                        if(ins.get(a).size() > 0 && Integer.valueOf(ins.get(a).get("Customer_priority")) < 10){
                            int i = 1;
                            ps.setString(i++, runId);
                            ps.setString(i++, ins.get(a).get("Customer_ID"));
                            ps.setString(i++, ins.get(a).get("DO_Number"));
                            ps.setString(i++, ins.get(a).get("Long"));
                            ps.setString(i++, ins.get(a).get("Lat"));                        
                            ps.setInt(i++, Integer.parseInt(ins.get(a).get("Customer_priority")));
                            ps.setInt(i++, Integer.parseInt(ins.get(a).get("Service_time")));
                            ps.setString(i++, ins.get(a).get("deliv_start"));
                            ps.setString(i++, ins.get(a).get("deliv_end"));
                            ps.setString(i++, ins.get(a).get("vehicle_type_list"));
                            ps.setDouble(i++, Double.valueOf(ins.get(a).get("total_kg_item")));
                            ps.setDouble(i++, Double.valueOf(ins.get(a).get("total_cubication")));
                            ps.setString(i++, ins.get(a).get("DeliveryDeadline"));
                            ps.setString(i++, ins.get(a).get("DayWinStart"));
                            ps.setString(i++, ins.get(a).get("DayWinEnd"));
                            ps.setString(i++, ins.get(a).get("UpdatevDate"));
                            ps.setString(i++, ins.get(a).get("CreateDate"));   
                            ps.setString(i++, "1");
                            ps.setString(i++, "inc");
                            ps.setString(i++, edt);
                            ps.setString(i++, ins.get(a).get("Product_Description"));
                            ps.setDouble(i++, Double.valueOf(ins.get(a).get("Gross_Amount")));
                            ps.setDouble(i++, Double.valueOf(ins.get(a).get("DOQty")));
                            ps.setString(i++, ins.get(a).get("DOQtyUOM"));  
                            ps.setString(i++, ins.get(a).get("Name1"));
                            ps.setString(i++, ins.get(a).get("Street"));
                            ps.setString(i++, ins.get(a).get("Distribution_Channel"));
                            ps.setString(i++, ins.get(a).get("Customer_Order_Block_all"));
                            ps.setString(i++, ins.get(a).get("Customer_Order_Block"));   
                            ps.setString(i++, ins.get(a).get("Request_Delivery_Date")); 
                            ps.setString(i++, ins.get(a).get("marketId")); 
                            ps.setString(i++, ins.get(a).get("Desa_Kelurahan"));   
                            ps.setString(i++, ins.get(a).get("Kecamatan")); 
                            ps.setString(i++, ins.get(a).get("Kodya_Kabupaten")); 
                            ps.setString(i++, ins.get(a).get("Batch")); 
                            ps.setString(i++, ins.get(a).get("Ket_DO")); 

                            ps.addBatch();
                        }
                    }
                    ps.executeBatch();
                }            
            }
        }        
        
        return str;
    }
    public HashMap<String, String> tree(HashMap<String, String> pl, Date dateDeliv, String DDl, String chn) throws Exception {
        pl = time(pl, dateDeliv);
        pl = priority(pl, dateDeliv, DDl, chn);
        pl = other(pl);
        return pl;
    }

    //int a = 0;
    public HashMap<String, String> priority(HashMap<String, String> pl, Date dateDeliv, String DDl, String chn) throws ParseException, Exception {
        try{
            if(pl.get("Customer_ID").equals("5820002166")){
                System.out.println();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(pl.get("Request_Delivery_Date"));

            //
            //if(dateDeliv.compareTo(rdd) < -3){        
            //int str = dateDeliv.compareTo(rdd);
            Calendar cal1 = new GregorianCalendar();
            Calendar cal2 = new GregorianCalendar();

            //SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");

            cal1.setTime(dateDeliv);
            cal2.setTime(date1);
            int str = daysBetween(cal1.getTime(),cal2.getTime());

            //System.out.println(pl.toString());
            //System.out.println(dateDeliv + " " + rdd + " = " + str);
            //System.out.println(str);
            //5820000348        

            if(pl.get("Distribution_Channel") == null){
                //System.out.println(a++);
                pl.replace("Distribution_Channel", pl.get("ChannelNullDefault"));
            }
            
            //delivdate to RDD
            if(pl.get("Distribution_Channel").equalsIgnoreCase("MT")){
                //System.out.println(pl.get("Distribution_Channel"));DeliveryDeadline
                if(pl.get("DeliveryDeadline").equalsIgnoreCase("ONDL")){
                    if(str == 0)                    pl.replace("Customer_priority", String.valueOf(1));
                    else                            pl.replace("Customer_priority", String.valueOf(10));
                }else if(pl.get("DeliveryDeadline").equalsIgnoreCase("BFOR")){
                    if(str == 0)                    pl.replace("Customer_priority", String.valueOf(1));
                    else if(str == 1)               pl.replace("Customer_priority", String.valueOf(2));
                    else if(str >= 2)               pl.replace("Customer_priority", String.valueOf(3));
                    else if(str < 0)                pl.replace("Customer_priority", String.valueOf(10));
                }else if(pl.get("DeliveryDeadline").equalsIgnoreCase("AFTR")){
                    if(str == 0)                    pl.replace("Customer_priority", String.valueOf(1));
                    else if(str > -3 && str < 0)    pl.replace("Customer_priority", String.valueOf(2));
                    else if(str > 0)                pl.replace("Customer_priority", String.valueOf(3));
                    else                            pl.replace("Customer_priority", String.valueOf(10));
                }
            }else{
                pl.replace("Customer_priority", String.valueOf(2));
                //repale channel to GT
                //pl.replace("Distribution_Channel", "GT");
                /*
                if(pl.get("DeliveryDeadline").equalsIgnoreCase("BFOR")){
                    if(str == 0)                    pl.replace("Customer_priority", String.valueOf(1));
                    else if(str > 0)                pl.replace("Customer_priority", String.valueOf(3));
                    else if(str < 0)                pl.replace("Customer_priority",  String.valueOf(10));
                }else if(pl.get("DeliveryDeadline").equalsIgnoreCase("AFTR")){
                    if(str == 0)                    pl.replace("Customer_priority", String.valueOf(1));
                    else if(str > 0)                pl.replace("Customer_priority", String.valueOf(3));
                    else if(str < 0)                pl.replace("Customer_priority", String.valueOf(2));
                }else if(pl.get("DeliveryDeadline").equalsIgnoreCase("ONDL")){
                    if(str == 0)            pl.replace("Customer_priority", String.valueOf(1));
                    else                    pl.replace("Customer_priority", String.valueOf(10));
                }*/
            }//System.out.println(pl.get("Customer_ID"));

            if(pl.get("Customer_ID").equals("5820001001") || pl.get("Customer_ID").equals("5820000348")){
                /*System.out.println(pl.get("Customer_ID"));
                System.out.println("DeliveryDeadline " + pl.get("DeliveryDeadline"));
                System.out.println("Distribution_Channel "+pl.get("Distribution_Channel"));
                System.out.println("dateDeliv "+dateDeliv);
                System.out.println("Request_Delivery_Date "+pl.get("Request_Delivery_Date"));
                System.out.println("Customer_priority :" + pl.get("Customer_priority"));
                System.out.println("str :" + str);*/
            }
        }catch(Exception e){
            throw new Exception(pl.toString() + ";" + e);
        }
        
        return pl;
    }
    
    public int daysBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }
    
    public HashMap<String, String> time(HashMap<String, String> pl, Date dateDeliv) throws ParseException{
        Calendar c = Calendar.getInstance();
        SimpleDateFormat shf = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String reference = pl.get("SatDelivDefault");
        
        //dateDeliv saturday 12:00
        Calendar deliv = Calendar.getInstance();
        deliv.setTime(dateDeliv);
        int day = deliv.get(Calendar.DAY_OF_WEEK);
        
        if(day == 7){
            c.setTime(shf.parse(pl.get("deliv_end")));
            deliv.setTime(shf.parse(reference));
            if(c.after(deliv) && pl.get("Distribution_Channel").equalsIgnoreCase("MT")){
                pl.replace("deliv_end", reference);
            }           
        } 
        
        //rdd sunday
        Calendar rdd = Calendar.getInstance();
        rdd.setTime(sdf.parse(pl.get("Request_Delivery_Date")));
        day = rdd.get(Calendar.DAY_OF_WEEK);
        if(day == 1){
            //System.out.println(pl.toString());
            //pl.replace("deliv_end", reference);
            rdd.add(Calendar.DATE, -1);
            //System.out.println(sdf.format(rdd.getTime()));
            pl.replace("Request_Delivery_Date", sdf.format(rdd.getTime()));
        }
        
        Calendar date2 = Calendar.getInstance();
        c.setTime(shf.parse(pl.get("deliv_start")));
        date2.setTime(shf.parse(reference));
        //-1 > 12:00 deliv_start
        if (c.after(date2)) {
            System.out.println("bfr" + pl.toString());
            //c.add(Calendar.HOUR, -1);
            //pl.replace("deliv_start", shf.format(c.getTime()).toString());
            //System.out.println("afr" + pl.toString());
        }
        
        c.setTime(shf.parse(pl.get("deliv_end")));
        //-1 > 12:00 deliv_end
        if (c.after(date2)) {
            //System.out.println("bfr" + pl.toString());
            //c.add(Calendar.HOUR, -1);
            //pl.replace("deliv_end", shf.format(c.getTime()).toString());
            //System.out.println("afr" + pl.toString());
        }
        
        //buffer-end time
        int end = Integer.parseInt(pl.get("BufferEndDefault"));
        c.add(Calendar.MINUTE, - end);
        pl.replace("deliv_end", shf.format(c.getTime()).toString());       
        
        return pl;
    }

    public HashMap<String, String> other(HashMap<String, String> pl){
        
        if(pl.get("Batch") == null){
            pl.put("Ket_DO", "Batch null tidak masuk SAP");
        }else{
            pl.put("Ket_DO", "-");
        }
        return pl;
    }
    
    public String cekRunId(String runId) throws Exception{
        String err = "ERROR";
        String sql = "select count(*) as num from bosnet1.dbo.TMS_progress where runID = '"+runId+"'";
        
        int num = 0;
        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)){
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()) 
                    num = rs.getInt("num");
                
                if(num == 0)
                    err = "OK";
                
            }
        }
        return err;
    }
    
    public List<HashMap<String, String>> treeSAP(List<HashMap<String, String>> px) throws Exception{
        callPI_DeliveryOrder(px);
        
        return px;
    }
    
    public List<HashMap<String, String>> callPI_DeliveryOrder(List<HashMap<String, String>> py) throws Exception{
        HashMap<String, String> px = new HashMap<String, String>();
        
        String s = "";
        for(int a = 0;a<py.size();a++){
            px = new HashMap<String, String>();
            px = py.get(a);
            
            if(s.length() > 0)  s = s + ",'" + px.get("DO_Number") + "'";
            else    s = s + "'" + px.get("DO_Number") + "'";            
        }
        
        String sql = "SELECT distinct DONumber, GoodsMovementStat, PODStatus\n" +
                "  FROM PICONSOL.dbo.PI_DeliveryOrder where DONumber in ("+s+")"
                + " and (GoodsMovementStat = 'C' or PODStatus = 'C')";
        
        //System.out.println(sql); 
        List<HashMap<String, String>> pl = new ArrayList<HashMap<String, String>>();        
        try (Connection con = (new Db()).getConnection("jdbc/sapDB");
                PreparedStatement ps = con.prepareStatement(sql)) {
            //System.out.println(sql);
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    px = new HashMap<String, String>();
                    px.put("DONumber", rs.getString("DONumber"));
                    px.put("GoodsMovementStat", rs.getString("GoodsMovementStat"));
                    px.put("PODStatus", rs.getString("PODStatus"));
                    
                    pl.add(px);
                }
            }
        }
        
        //do diexclude
        for(int a = 0;a<py.size();a++){
            px = new HashMap<String, String>();
            px = py.get(a);
            if(pl.size() > 0){
                for (HashMap<String, String> pl1 : pl) {                    
                    if(px.get("DO_Number").equalsIgnoreCase(pl1.get("DONumber"))){
                       //px.replace("Customer_priority", String.valueOf(10));
                       //System.out.println(px.get("DO_Number") + "()" + pl1.get("GoodsMovementStat") + "()" + pl1.get("PODStatus"));
                       py.get(a).replace("Customer_priority", String.valueOf(10));                           
                    }
                }
            }           
        }
        
        return py;
    }
    
    public String cekData(String runID, String prevRunID, String str, List<HashMap<String, String>> zx) throws Exception{
        String err = "";
        List<HashMap<String, String>> asd = zx;
        HashMap<String, String> py = new HashMap<String, String>();
        
        int i = 0;
        while(i < asd.size()){
            py = asd.get(i);
            
            int j = 0;
            String CustId = py.get("Customer_ID");
            String DO = py.get("DO_Number");
            
            Object[] keys = py.keySet().toArray();
            
            if(py.get("Customer_ID").equalsIgnoreCase("5810002739")){
                System.out.println("com.fz.tms.service.run.AlgoRunner.cekData()");
            }
            
            while(j <py.size()){
                String tmp = py.get(keys[j]) == null ? "-" : py.get(keys[j]);
                //System.out.print(j + str);
                
                if(tmp.contains("n/a")){
                    err += "n/a ";
                }
                
                if(keys[j].equals("Long") || keys[j].equals("Lat")){
                    if(py.get("Customer_ID").equalsIgnoreCase("5810110214")){
                        System.out.println("com.fz.tms.service.run.AlgoRunner.cekData()");
                    }
                    if(tmp.replaceAll("[^.]", "").length() > 1){
                        err += ". ";
                    }
                    if(tmp.contains(",")){
                        err += ", ";
                    }
                    if(tmp.equalsIgnoreCase("0")){
                        err += "0 ";
                    }
                }
                
                if(tmp.contains("\"")){
                    err += "\" ";
                }

                if(tmp.contains("'")){
                    err += "' ";
                }
                
                if(!err.equalsIgnoreCase(""))
                    break;
                
                j++;
            }
            if(!err.equalsIgnoreCase("")){
                err = "Error " + err + "CustomerId " + CustId;
                break;
            }
            //System.out.println();
            i++;
        }
        
        if(err.equalsIgnoreCase(""))    err = "OK";
        return err;
    }
    
    public String cluster(String runId, String nextRunID, List<HashMap<String, String>> zx) throws Exception{
        int x = 2;
        int y = 3;
        Double corA = new Double(0);
        Double corB = new Double(0);
        String str = "Error Cluster";
        
        
        List<HashMap<String, String>> asd = zx;
        HashMap<String, String> pl = new HashMap<String, String>();
        
        String sql = "";
        
        if(asd.size() > 0){            
            
            int i = 0;
            
            Double d = Double.parseDouble(asd.get(i).get("Long"));
            Boolean dd = false;
            Double g = Double.parseDouble(asd.get(i).get("Long"));
            Boolean dg = false;
            Double r = Double.parseDouble(asd.get(i).get("Lat"));
            Boolean dr = false;
            Double f = Double.parseDouble(asd.get(i).get("Lat"));
            Boolean df = false;
            
            String cust = "";
            //ambil titik pojok-pojok
            while(i < asd.size()){
                pl = asd.get(i);
                cust = pl.get("Customer_ID");
                //System.out.print(cust);
                if(Double.parseDouble(pl.get("Long")) < d
                        || Double.parseDouble(pl.get("Long")) > g
                        || Double.parseDouble(pl.get("Lat")) > r
                        || Double.parseDouble(pl.get("Lat")) < f){                    
                    //X----
                    if(Double.parseDouble(pl.get("Long")) < d)
                        d = Double.parseDouble(pl.get("Long"));
                    //----X
                    if(Double.parseDouble(pl.get("Long")) > g)
                        g = Double.parseDouble(pl.get("Long"));
                    //-Y---
                    if(Double.parseDouble(pl.get("Lat")) > r)
                        r = Double.parseDouble(pl.get("Lat"));
                    //---Y-
                    if(Double.parseDouble(pl.get("Lat")) < f)
                        f = Double.parseDouble(pl.get("Lat"));
                    
                    i = 0;
                    dd = false;
                    dg = false;
                    dr = false;
                    df = false;
                }else{
                    //System.out.println(d + " | " + Double.parseDouble(pl.get("Long")));
                    //X----
                    if(d <= Double.parseDouble(pl.get("Long")))
                        dd = true;
                    //----X
                    if(g >= Double.parseDouble(pl.get("Long")))
                        dg = true;
                    //-Y---
                    if(r >= Double.parseDouble(pl.get("Lat")))
                        dr = true;
                    //---Y-
                    if(f <= Double.parseDouble(pl.get("Lat")))
                        df = true;
                    
                    
                    if(dd && dg && dr && df)    i++;
                }   
                //System.out.println(" x " + i);
                //System.out.println(cust + " | " + i + " || " + d + " | " + g + " | " + r + " | " + f);
            }
            
            //mapping area
            corA = (g - d) / x;
            corB = (f - r) / y;
            
            List<HashMap<String, String>> px = new ArrayList<HashMap<String, String>>();
            int z = 1;
            Double td = d;
            Double tr = r;
            i = 0;
            while(z <= (x * y)){
                
                //System.out.println(z);
                //System.out.println(td + "()" + (td + corA));
                //System.out.println(tr + "()" + (tr + corB));                
                
                i = 0;
                while(i < asd.size()){
                    pl = asd.get(i);
                    cust = pl.get("Customer_ID");
                    Double ty = Double.parseDouble(pl.get("Long"));
                    Double tx = Double.parseDouble(pl.get("Lat"));     
                    
                    if(z == 4 && cust.equals("5810003533")){
                        //System.out.println("com.fz.tms.service.run.AlgoRunner.cluster()");
                        if(td < ty && ty <= (td + corA)){
                            //System.out.println("com.fz.tms.service.run.AlgoRunner.cluster()");
                        }
                        if(tr > tx && tx >= (tr + corB)){
                            //System.out.println("com.fz.tms.service.run.AlgoRunner.cluster()");
                        }
                    }
                    //System.out.println();
                    if(z == 1){
                        if(td <= ty && ty <= (td + corA)
                                && tr >= tx && tx >= (tr + corB)){
                            pl.put("AreaId", String.valueOf(z));
                            //System.out.println(td + "|"+ (td + corA) + "<>" + tr + "|" + (tr + corB));
                            //System.out.println(pl.toString());
                            px.add(pl);
                            asd.remove(i);
                            i = 0;
                        }else{
                            i++;
                        }
                    }else if(z == (x * y)){
                        pl.put("AreaId", String.valueOf(z));
                        //System.out.println(td + "|"+ (td + corA) + "<>" + tr + "|" + (tr + corB));
                        //System.out.println(pl.toString());
                        px.add(pl);
                        asd.remove(i);
                        i = 0;
                    }else{
                        if(td < ty && ty <= (td + corA)
                                && tr > tx && tx >= (tr + corB)){
                            pl.put("AreaId", String.valueOf(z));
                            //System.out.println(td + "|"+ (td + corA) + "<>" + tr + "|" + (tr + corB));
                            //System.out.println(pl.toString());
                            px.add(pl);
                            asd.remove(i);
                            i = 0;
                        }else{
                            i++;
                        }
                    }
                    
                    
                    //if(asd.size() > 1)i++;
                }
                //System.out.println(z);
                //System.out.println(td + "|" + tr);
                
                if(z % x == 0){
                    
                    td = d;
                    tr += corB;  
                    //tr = r;                    
                }else{                    
                    td += corA;                                      
                }
                z++;
            }
            
            //i = 0;
            //String json = "[";
            //System.out.println("[");
            //while(i < asd.size()){
                //pl = asd.get(i);
                //System.out.println(pl.toString());
                //{Customer_ID=5810108065, Long=106.977485, AreaId=4, Lat=-6.228626}
                //json += "{\"title\": '"+pl.get("Customer_ID")+"',\"lat\": '"+pl.get("Lat")+"',\"lng\": '"+pl.get("Long")+"',\"description\": '"+pl.get("AreaId")+"'},";
                
                //System.out.println("{\"title\": '"+pl.get("Customer_ID")+"',\"lat\": '"+pl.get("Lat")+"',\"lng\": '"+pl.get("Long")+"',\"description\": '"+pl.get("AreaId")+"'},");
                
                //i++;
            //}
            //System.out.println("]");
            //json += "]";
            System.out.println("json");
            
            sql = "INSERT\n" +
                "	INTO\n" +
                "		bosnet1.dbo.TMS_PreRouteCluster(\n" +
                "			runID,\n" +
                "			Customer_ID,\n" +
                "			Long,\n" +
                "			Lat,\n" +
                "			AreaId\n" +
                "		) values(?,?,?,?,?)";
            
            if(px.size() > 0){
                try (Connection con = (new Db()).getConnection("jdbc/fztms")){
                    try (PreparedStatement ps = con.prepareStatement(sql) ){
                            ps.clearParameters();                    
                        for(int a = 0;a<px.size();a++){ 
                            i = 1;
                            ps.setString(i++, nextRunID);
                            ps.setString(i++, px.get(a).get("Customer_ID"));
                            ps.setString(i++, px.get(a).get("Long"));
                            ps.setString(i++, px.get(a).get("Lat"));   
                            ps.setString(i++, px.get(a).get("AreaId")); 
                            ps.addBatch();
                        }
                        ps.executeBatch();
                        
                        str = "OK";
                    }            
                }
            }
            
        }
        asd = zx;
        return str;
    }
    
    public List<HashMap<String, String>> selectCust(String prev, String next) throws Exception{
        List<HashMap<String, String>> px = new ArrayList<HashMap<String, String>>();
        String sql = "SELECT\n" +
                "	'"+next+"' AS RunId,\n" +
                "	jb.Customer_ID,\n" +
                "	jb.DO_Number,\n" +
                "	jb.Long,\n" +
                "	jb.Lat,\n" +
                "	jb.Customer_priority,\n" +
                "	jb.Service_time,\n" +
                "	jb.deliv_start,\n" +
                "	jb.deliv_end,\n" +
                "	jb.vehicle_type_list,\n" +
                "	jb.total_kg,\n" +
                "	jb.total_cubication,\n" +
                "	jb.DeliveryDeadline,\n" +
                "	jb.DayWinStart,\n" +
                "	jb.DayWinEnd,\n" +
                "	CAST(\n" +
                "		FORMAT(\n" +
                "			getdate(),\n" +
                "			'yyyy-MM-dd hh-mm'\n" +
                "		) AS VARCHAR\n" +
                "	) AS UpdatevDate,\n" +
                "	CAST(\n" +
                "		FORMAT(\n" +
                "			getdate(),\n" +
                "			'yyyy-MM-dd hh-mm'\n" +
                "		) AS VARCHAR\n" +
                "	) AS CreateDate,\n" +
                "	jb.isActive,\n" +
                "	jb.Is_Exclude,\n" +
                "	jb.Product_Description,\n" +
                "	jb.Gross_Amount,\n" +
                "	jb.DOQty,\n" +
                "	jb.DOQtyUOM,\n" +
                "	jb.Name1,\n" +
                "	jb.Street,\n" +
                "	jb.Distribution_Channel,\n" +
                "	jb.Customer_Order_Block_all,\n" +
                "	jb.Customer_Order_Block,\n" +
                "	jb.Request_Delivery_Date,\n" +
                "	jb.Desa_Kelurahan,\n" +
                "	jb.Kecamatan,\n" +
                "	jb.Kodya_Kabupaten,\n" +
                "	jb.Batch,\n" +
                "	jb.Ket_DO\n" +
                "FROM\n" +
                "	bosnet1.dbo.TMS_PreRouteJob jb\n" +
                "INNER JOIN(\n" +
                "		SELECT\n" +
                "			DISTINCT DO_Number\n" +
                "		FROM\n" +
                "			bosnet1.dbo.TMS_ShipmentPlan\n" +
                "		WHERE\n" +
                "			already_shipment = 'N'\n" +
                "			AND notused_flag IS NULL\n" +
                "			AND incoterm = 'FCO'\n" +
                "			AND(\n" +
                "				Order_Type = 'ZDCO'\n" +
                "				OR Order_Type = 'ZDTO'\n" +
                "			)\n" +
                "			AND create_date >= DATEADD(\n" +
                "				DAY,\n" +
                "				- 7,\n" +
                "				GETDATE()\n" +
                "			)\n" +
                "	) sp ON\n" +
                "	jb.DO_Number = sp.DO_Number\n" +
                "LEFT OUTER JOIN(\n" +
                "		SELECT\n" +
                "			tu.Delivery_Number\n" +
                "		FROM\n" +
                "			BOSNET1.dbo.TMS_Result_Shipment ty\n" +
                "		INNER JOIN BOSNET1.dbo.TMS_Status_Shipment tu ON\n" +
                "			ty.Delivery_Number = tu.Delivery_Number\n" +
                "		WHERE\n" +
                "			tu.SAP_Status IS NULL\n" +
                "	) ss ON\n" +
                "	sp.DO_Number = ss.Delivery_Number\n" +
                "LEFT OUTER JOIN(\n" +
                "		SELECT\n" +
                "			ty.Delivery_Number\n" +
                "		FROM\n" +
                "			BOSNET1.dbo.TMS_Result_Shipment ty\n" +
                "		LEFT OUTER JOIN BOSNET1.dbo.TMS_Status_Shipment tu ON\n" +
                "			ty.Delivery_Number = tu.Delivery_Number\n" +
                "		WHERE\n" +
                "			tu.Delivery_Number IS NULL\n" +
                "	) sn ON\n" +
                "	sp.DO_Number = sn.Delivery_Number\n" +
                "WHERE\n" +
                "	ss.Delivery_Number IS NULL\n" +
                "	AND sn.Delivery_Number IS NULL\n" +
                "	AND jb.RunId = '"+prev+"'\n" +
                "	AND jb.Is_Exclude = 'inc'\n" +
                "	AND jb.Is_Edit = 'edit';";

        HashMap<String, String> pl = new HashMap<String, String>();
        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)) {
            //System.out.println(sql);
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    pl = new HashMap<String, String>();
                    pl.put("RunId", rs.getString("RunId"));
                    pl.put("Customer_ID", rs.getString("Customer_ID"));
                    pl.put("DO_Number", rs.getString("DO_Number"));
                    pl.put("Long", rs.getString("Long"));
                    pl.put("Lat", rs.getString("Lat"));
                    pl.put("Customer_priority", rs.getString("Customer_priority"));
                    pl.put("Service_time", rs.getString("Service_time"));
                    pl.put("deliv_start", rs.getString("deliv_start"));
                    pl.put("deliv_end", rs.getString("deliv_end"));
                    pl.put("vehicle_type_list", rs.getString("vehicle_type_list"));
                    pl.put("total_kg", rs.getString("total_kg"));
                    pl.put("total_cubication", rs.getString("total_cubication"));
                    pl.put("DeliveryDeadline", rs.getString("DeliveryDeadline"));
                    pl.put("DayWinStart", rs.getString("DayWinStart"));
                    pl.put("DayWinEnd", rs.getString("DayWinEnd"));
                    pl.put("UpdatevDate", rs.getString("UpdatevDate"));
                    pl.put("CreateDate", rs.getString("CreateDate"));
                    pl.put("isActive", rs.getString("isActive"));
                    pl.put("Is_Exclude", rs.getString("Is_Exclude"));
                    pl.put("Product_Description", rs.getString("Product_Description"));
                    pl.put("Gross_Amount", rs.getString("Gross_Amount"));
                    pl.put("DOQty", rs.getString("DOQty"));
                    pl.put("DOQtyUOM", rs.getString("DOQtyUOM"));
                    pl.put("Name1", rs.getString("Name1"));
                    pl.put("Street", rs.getString("Street"));
                    pl.put("Distribution_Channel", rs.getString("Distribution_Channel"));
                    pl.put("Customer_Order_Block_all", rs.getString("Customer_Order_Block_all"));
                    pl.put("Customer_Order_Block", rs.getString("Customer_Order_Block"));
                    pl.put("Request_Delivery_Date", rs.getString("Request_Delivery_Date"));
                    pl.put("Desa_Kelurahan", rs.getString("Desa_Kelurahan"));
                    pl.put("Kecamatan", rs.getString("Kecamatan"));
                    pl.put("Kodya_Kabupaten", rs.getString("Kodya_Kabupaten"));
                    pl.put("Batch", rs.getString("Batch"));
                    pl.put("Ket_DO", rs.getString("Ket_DO"));
                    px.add(pl);

                    //con.setAutoCommit(false);
                    //ps.executeUpdate();
                    //con.setAutoCommit(true);
                }
            }    
        }
        
        return px;
    }
}

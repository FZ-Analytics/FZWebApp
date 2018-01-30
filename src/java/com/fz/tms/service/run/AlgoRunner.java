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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 *
 */
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
                    resp = updatePrevPreRouteJob(runID, runId);
                    if (resp.equalsIgnoreCase("OK"))    
                        resp = insertPreRouteJobCopy(runID, runId, branchCode, dateDeliv, "ori");
                    if (resp.equalsIgnoreCase("OK"))    
                        resp = insertPreRouteJobCopy(runID, runId, branchCode, dateDeliv, "edit");
                    if (resp.equalsIgnoreCase("OK"))    
                        resp = insertPreVehicleCopy(runID, runId, branchCode, dateDeliv, "ori");
                    if (resp.equalsIgnoreCase("OK") && reRun.equals("A")) {
                        resp = UrlResponseGetter.getURLResponse(url.toString());
                        if (resp.equals("OK")) {
                            response.sendRedirect("runProgress.jsp?runId=" + runID + "&dateDeliv=" + dateDeliv + "&oriRunID=" + oriRunID + "&channel=" + channel);
                        } else {
                            HashMap<String, String> pl = new HashMap<String, String>();
                            pl.put("ID", runId);
                            pl.put("fileNmethod", "AlgoRunner&run");
                            pl.put("datas", "");
                            pl.put("msg", resp);
                            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                            Date date = new Date();
                            pl.put("dates", dateFormat.format(date).toString());
                            Other.insertLog(pl);
                            response.sendRedirect("../Params/PopUp/popupEditCustBfror.jsp?oriRunID=" + oriRunID + "&dateDeliv="
                                    + dateDeliv + "&shift=" + shift + "&reRun=A" + "&branchCode=" + branchCode + "&runId=" + runId + "&channel=" + channel + "&error=N");
                        }
                    }

                } else if (reRun.equals("N")) {
                    prepareCustTable(branchCode);
                    resp = insertPreRouteVehicle(runID, branchCode, dateDeliv, chn);
                    if (resp.equalsIgnoreCase("OK"))    
                        resp = QueryCust(runID, branchCode, channel, dateDeliv, "ori");
                    //resp = insertPreRouteJob(runID, branchCode, dateDeliv, "ori", chn);
                    if (resp.equalsIgnoreCase("OK"))    
                        resp = QueryCust(runID, branchCode, channel, dateDeliv, "edit");
                    //resp = insertPreRouteJob(runID, branchCode, dateDeliv, "edit", chn);
                    if (resp.equalsIgnoreCase("OK"))    
                        response.sendRedirect("../Params/PopUp/popupEditCustBfror.jsp?oriRunID=" + oriRunID + "&dateDeliv="
                            + dateDeliv + "&shift=" + shift + "&reRun=A" + "&branchCode=" + branchCode + "&runId=" + runId + "&channel=" + channel + "&error=N");

                }
                
            }
        } catch (Exception e) {
            HashMap<String, String> pl = new HashMap<String, String>();
            pl.put("ID", runId);
            pl.put("fileNmethod", "AlgoRunner&run Exc");
            pl.put("datas", "");
            pl.put("msg", e.getMessage());
            System.out.println("e.getMessage() " + e.getMessage());
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            Date date = new Date();
            pl.put("dates", dateFormat.format(date).toString());
            Other.insertLog(pl);
            response.sendRedirect("../Params/PopUp/popupEditCustBfror.jsp?oriRunID=" + oriRunID + "&dateDeliv="
                    + dateDeliv + "&shift=" + shift + "&reRun=A" + "&branchCode=" + branchCode + "&runId=" + runId + "&channel=" + channel  + "&error=Y");
        }
    }

    public String insertPreRouteJob(String runID, String branchCode,
            String dateDeliv, String str, String chn)
            throws Exception {

        String cds = "ERROR insertPreRouteJob";

        String sql = "INSERT\n" +
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
                "			Request_Delivery_Date\n" +
                "		) SELECT\n" +
                "			aq.RunId,\n" +
                "			aq.Customer_ID,\n" +
                "			aq.DO_Number,\n" +
                "			aq.Long,\n" +
                "			aq.Lat,\n" +
                "			aq.Customer_Priority,\n" +
                "			aq.service_time,\n" +
                "			CONVERT(\n" +
                "				VARCHAR(5),\n" +
                "				(\n" +
                "					CASE\n" +
                "						WHEN DATEDIFF(\n" +
                "							HOUR,\n" +
                "							CAST(\n" +
                "								aq.deliv_start AS TIME\n" +
                "							),\n" +
                "							CAST(\n" +
                "								'12:00' AS TIME\n" +
                "							)\n" +
                "						)< 1 THEN DATEADD(\n" +
                "							HOUR,\n" +
                "							- 1,\n" +
                "							CAST(\n" +
                "								aq.deliv_start AS TIME\n" +
                "							)\n" +
                "						)\n" +
                "						ELSE CAST(\n" +
                "							aq.deliv_start AS TIME\n" +
                "						)\n" +
                "					END\n" +
                "				),\n" +
                "				108\n" +
                "			),\n" +
                "			CONVERT(\n" +
                "				VARCHAR(5),\n" +
                "				(\n" +
                "					CASE\n" +
                "						WHEN DATEDIFF(\n" +
                "							HOUR,\n" +
                "							CAST(\n" +
                "								aq.deliv_end AS TIME\n" +
                "							),\n" +
                "							CAST(\n" +
                "								'12:00' AS TIME\n" +
                "							)\n" +
                "						)< 1 THEN DATEADD(\n" +
                "							HOUR,\n" +
                "							- 1,\n" +
                "							CAST(\n" +
                "								aq.deliv_end AS TIME\n" +
                "							)\n" +
                "						)\n" +
                "						ELSE CAST(\n" +
                "							aq.deliv_end AS TIME\n" +
                "						)\n" +
                "					END\n" +
                "				),\n" +
                "				108\n" +
                "			),\n" +
                "			aq.vehicle_type_list,\n" +
                "			aq.total_kg,\n" +
                "			aq.total_cubication,\n" +
                "			aq.DeliveryDeadline,\n" +
                "			aq.DayWinStart,\n" +
                "			aq.DayWinEnd,\n" +
                "			aq.UpdatevDate,\n" +
                "			aq.CreateDate,\n" +
                "			aq.isActive,\n" +
                "			aq.Is_Exclude,\n" +
                "			aq.Is_Edit,\n" +
                "			aq.Product_Description,\n" +
                "			aq.Gross_Amount,\n" +
                "			aq.DOQty,\n" +
                "			aq.DOQtyUOM,\n" +
                "			aq.Name1,\n" +
                "			aq.Street,\n" +
                "			aq.Distribution_Channel,\n" +
                "			aq.Customer_Order_Block_all,\n" +
                "			aq.Customer_Order_Block,\n" +
                "			aq.Request_Delivery_Date\n" +
                "		FROM\n" +
                "			(\n" +
                "				SELECT\n" +
                "					c.RunId,\n" +
                "					c.Customer_ID,\n" +
                "					c.DO_Number,\n" +
                "					c.Long,\n" +
                "					c.Lat,\n" +
                "					CASE\n" +
                "						WHEN priority < 4 THEN(\n" +
                "							CASE\n" +
                "								WHEN c.priority < 1 THEN c.priority + 1\n" +
                "								ELSE c.priority\n" +
                "							END\n" +
                "						)\n" +
                "						ELSE c.Customer_Priority\n" +
                "					END AS Customer_Priority,\n" +
                "					CASE\n" +
                "						WHEN c.service_time IS NULL THEN st.value\n" +
                "						ELSE c.service_time\n" +
                "					END AS service_time,\n" +
                "					CASE\n" +
                "						WHEN c.deliv_start IS NULL THEN ds.value\n" +
                "						ELSE c.deliv_start\n" +
                "					END AS deliv_start,\n" +
                "					CASE\n" +
                "						WHEN c.deliv_end IS NULL THEN de.value\n" +
                "						ELSE c.deliv_end\n" +
                "					END AS deliv_end,\n" +
                "					CASE\n" +
                "						WHEN c.vehicle_type_list IS NULL THEN vt.value\n" +
                "						ELSE c.vehicle_type_list\n" +
                "					END AS vehicle_type_list,\n" +
                "					c.total_kg,\n" +
                "					c.total_cubication,\n" +
                "					c.DeliveryDeadline,\n" +
                "					c.DayWinStart,\n" +
                "					c.DayWinEnd,\n" +
                "					c.UpdatevDate,\n" +
                "					c.CreateDate,\n" +
                "					c.isActive,\n" +
                "					c.Is_Exclude,\n" +
                "					c.Is_Edit,\n" +
                "					c.Product_Description,\n" +
                "					c.Gross_Amount,\n" +
                "					c.DOQty,\n" +
                "					c.DOQtyUOM,\n" +
                "					c.Name1,\n" +
                "					c.Street,\n" +
                "					c.Distribution_Channel,\n" +
                "					c.Customer_Order_Block_all,\n" +
                "					c.Customer_Order_Block,\n" +
                "					CAST(\n" +
                "						FORMAT(\n" +
                "							c.Request_Delivery_Date,\n" +
                "							'yyyy-MM-dd'\n" +
                "						) AS VARCHAR\n" +
                "					) AS Request_Delivery_Date\n" +
                "				FROM\n" +
                "					(\n" +
                "						SELECT\n" +
                "							b.*,\n" +
                "							CASE\n" +
                "								WHEN DeliveryDeadline = 'AFTR' THEN DATEDIFF(\n" +
                "									DAY,\n" +
                "									delivDate,\n" +
                "									FORMAT(\n" +
                "										DATEADD(\n" +
                "											DAY,\n" +
                "											7,\n" +
                "											b.Request_Delivery_Date\n" +
                "										),\n" +
                "										'yyyy-MM-dd'\n" +
                "									)\n" +
                "								)\n" +
                "								ELSE DATEDIFF(\n" +
                "									DAY,\n" +
                "									delivDate,\n" +
                "									b.Request_Delivery_Date\n" +
                "								)\n" +
                "							END AS priority\n" +
                "						FROM\n" +
                "							(\n" +
                "								SELECT\n" +
                "									'"+runID+"' AS RunId,\n" +
                "									a.Customer_ID,\n" +
                "									a.DO_Number,\n" +
                "									a.Long,\n" +
                "									a.Lat,\n" +
                "									CASE\n" +
                "										WHEN a.Customer_priority > 50 THEN SUBSTRING( CAST( a.Customer_priority AS VARCHAR ), 2, 1 )\n" +
                "										WHEN a.Customer_priority = 0 THEN a.value\n" +
                "										WHEN a.Customer_priority IS NULL THEN a.value\n" +
                "										ELSE a.Customer_priority\n" +
                "									END AS Customer_priority,\n" +
                "									a.Service_time,\n" +
                "									a.deliv_start,\n" +
                "									a.deliv_end,\n" +
                "									a.vehicle_type_list,\n" +
                "									SUM( a.total_kg_item ) total_kg,\n" +
                "									SUM( a.total_cubication ) total_cubication,\n" +
                "									a.DeliveryDeadline,\n" +
                "									a.DayWinStart,\n" +
                "									a.DayWinEnd,\n" +
                "									CAST(\n" +
                "										FORMAT(\n" +
                "											getdate(),\n" +
                "											'yyyy-MM-dd hh-mm'\n" +
                "										) AS VARCHAR\n" +
                "									) AS UpdatevDate,\n" +
                "									CAST(\n" +
                "										FORMAT(\n" +
                "											getdate(),\n" +
                "											'yyyy-MM-dd hh-mm'\n" +
                "										) AS VARCHAR\n" +
                "									) AS CreateDate,\n" +
                "									'1' AS isActive,\n" +
                "									'inc' AS Is_Exclude,\n" +
                "									'"+str+"' AS Is_Edit,\n" +
                "									a.Request_Delivery_Date,\n" +
                "									'"+dateDeliv+"' AS delivDate,\n" +
                "									a.Product_Description,\n" +
                "									a.Gross_Amount,\n" +
                "									a.DOQty,\n" +
                "									a.DOQtyUOM,\n" +
                "									a.Name1,\n" +
                "									a.Street,\n" +
                "									a.Distribution_Channel,\n" +
                "									a.Customer_Order_Block_all,\n" +
                "									a.Customer_Order_Block\n" +
                "								FROM\n" +
                "									(\n" +
                "										SELECT\n" +
                "											sp.Customer_ID,\n" +
                "											sp.DO_Number,\n" +
                "											CASE\n" +
                "												WHEN cl.Long IS NULL\n" +
                "												OR cl.Long = '' THEN 'n/a'\n" +
                "												ELSE cl.Long\n" +
                "											END AS Long,\n" +
                "											CASE\n" +
                "												WHEN cl.Lat IS NULL\n" +
                "												OR cl.Lat = '' THEN 'n/a'\n" +
                "												ELSE cl.Lat\n" +
                "											END AS Lat,\n" +
                "											cs.Customer_priority,\n" +
                "											ca.Service_time,\n" +
                "											ca.deliv_start,\n" +
                "											ca.deliv_end,\n" +
                "											ca.vehicle_type_list,\n" +
                "											sp.total_kg_item,\n" +
                "											sp.total_cubication,\n" +
                "											CASE\n" +
                "												WHEN ca.DeliveryDeadline IS NULL THEN dd.value\n" +
                "												ELSE ca.DeliveryDeadline\n" +
                "											END DeliveryDeadline,\n" +
                "											CASE\n" +
                "												WHEN ca.DayWinStart IS NULL THEN ds.value\n" +
                "												ELSE ca.DayWinStart\n" +
                "											END DayWinStart,\n" +
                "											CASE\n" +
                "												WHEN ca.DayWinEnd IS NULL THEN de.value\n" +
                "												ELSE ca.DayWinEnd\n" +
                "											END DayWinEnd,\n" +
                "											sp.Request_Delivery_Date,\n" +
                "											sp.Product_Description,\n" +
                "											sp.Gross_Amount,\n" +
                "											sp.DOQty,\n" +
                "											sp.DOQtyUOM,\n" +
                "											cs.Name1,\n" +
                "											cs.Street,\n" +
                "											cs.Distribution_Channel,\n" +
                "											cs.Customer_Order_Block_all,\n" +
                "											cs.Customer_Order_Block,\n" +
                "											df.value\n" +
                "										FROM\n" +
                "											bosnet1.dbo.TMS_ShipmentPlan sp\n" +
                "										LEFT OUTER JOIN(\n" +
                "												SELECT\n" +
                "													a.*\n" +
                "												FROM\n" +
                "													(\n" +
                "														SELECT\n" +
                "															ROW_NUMBER() OVER(\n" +
                "																PARTITION BY Customer_ID\n" +
                "															ORDER BY\n" +
                "																Customer_ID\n" +
                "															) AS noId,\n" +
                "															*\n" +
                "														FROM\n" +
                "															bosnet1.dbo.customer\n" +
                "														WHERE\n" +
                "															(\n" +
                "																Customer_Order_Block IS NULL\n" +
                "																OR Customer_Order_Block = ''\n" +
                "															)\n" +
                "															AND(\n" +
                "																Customer_Order_Block_all IS NULL\n" +
                "																OR Customer_Order_Block_all = ''\n" +
                "															)\n" +
                "													) a\n" +
                "												WHERE\n" +
                "													a.noid = 1\n" +
                "											) cs ON\n" +
                "											sp.customer_id = cs.customer_id\n" +
                "										LEFT JOIN(\n" +
                "												SELECT\n" +
                "													*\n" +
                "												FROM\n" +
                "													(\n" +
                "														SELECT\n" +
                "															ROW_NUMBER() OVER(\n" +
                "																PARTITION BY custid\n" +
                "															ORDER BY\n" +
                "																custid\n" +
                "															) AS noId,\n" +
                "															*\n" +
                "														FROM\n" +
                "															bosnet1.dbo.TMS_CustLongLat\n" +
                "													) a\n" +
                "												WHERE\n" +
                "													a.noid = 1\n" +
                "											) cl ON\n" +
                "											sp.customer_id = cl.custID\n" +
                "										LEFT OUTER JOIN bosnet1.dbo.TMS_CustAtr ca ON\n" +
                "											sp.customer_id = ca.customer_id\n" +
                "										LEFT OUTER JOIN bosnet1.dbo.TMS_Params dd ON\n" +
                "											dd.param = 'DeliveryDeadLine'\n" +
                "										LEFT OUTER JOIN bosnet1.dbo.TMS_Params ds ON\n" +
                "											ds.param = 'DayWinStart'\n" +
                "										LEFT OUTER JOIN bosnet1.dbo.TMS_Params de ON\n" +
                "											de.param = 'DayWinEnd'\n" +
                "										LEFT OUTER JOIN bosnet1.dbo.TMS_Params df ON\n" +
                "											df.param = 'DefaultCustPriority'\n" +
                "										WHERE\n" +
                "											sp.plant = '"+branchCode+"'\n" +
                "											AND sp.already_shipment = 'N'\n" +
                "											AND sp.notused_flag IS NULL\n" +
                "											AND sp.incoterm = 'FCO'\n" +
                "											AND(\n" +
                "												sp.Order_Type = 'ZDCO'\n" +
                "												OR sp.Order_Type = 'ZDTO'\n" +
                "											)\n" +
                "											AND sp.create_date >= DATEADD(\n" +
                "												DAY,\n" +
                "												- 7,\n" +
                "												GETDATE()\n" +
                "											)\n" +
                "											AND cs.Distribution_Channel IN("+chn+")\n" +
                "									) a\n" +
                "								WHERE\n" +
                "									datepart(\n" +
                "										dw,\n" +
                "										'"+dateDeliv+"'\n" +
                "									) BETWEEN DayWinStart AND DayWinEnd\n" +
                "									AND(\n" +
                "										(\n" +
                "											DeliveryDeadLine = 'ONDL'\n" +
                "											AND '"+dateDeliv+"' = Request_Delivery_Date\n" +
                "										)\n" +
                "										OR(\n" +
                "											DeliveryDeadLine = 'BFOR'\n" +
                "											AND '"+dateDeliv+"' <= Request_Delivery_Date\n" +
                "										)\n" +
                "										OR(\n" +
                "											DeliveryDeadLine = 'AFTR'\n" +
                "											AND '"+dateDeliv+"' < FORMAT(\n" +
                "												DATEADD(\n" +
                "													DAY,\n" +
                "													7,\n" +
                "													Request_Delivery_Date\n" +
                "												),\n" +
                "												'yyyy-MM-dd'\n" +
                "											)\n" +
                "										)\n" +
                "									)\n" +
                "								GROUP BY\n" +
                "									a.Customer_ID,\n" +
                "									a.DO_Number,\n" +
                "									a.Long,\n" +
                "									a.Lat,\n" +
                "									a.Customer_priority,\n" +
                "									a.Service_time,\n" +
                "									a.deliv_start,\n" +
                "									a.deliv_end,\n" +
                "									a.vehicle_type_list,\n" +
                "									a.DeliveryDeadline,\n" +
                "									a.DayWinStart,\n" +
                "									a.DayWinEnd,\n" +
                "									a.Request_Delivery_Date,\n" +
                "									a.Product_Description,\n" +
                "									a.Gross_Amount,\n" +
                "									a.DOQty,\n" +
                "									a.DOQtyUOM,\n" +
                "									a.Name1,\n" +
                "									a.Street,\n" +
                "									a.Distribution_Channel,\n" +
                "									a.Customer_Order_Block_all,\n" +
                "									a.Customer_Order_Block,\n" +
                "									a.value\n" +
                "							) b\n" +
                "					) c,\n" +
                "					(\n" +
                "						SELECT\n" +
                "							value\n" +
                "						FROM\n" +
                "							BOSNET1.dbo.TMS_Params\n" +
                "						WHERE\n" +
                "							param = 'DefaultCustServiceTime'\n" +
                "					) st,\n" +
                "					(\n" +
                "						SELECT\n" +
                "							value\n" +
                "						FROM\n" +
                "							BOSNET1.dbo.TMS_Params\n" +
                "						WHERE\n" +
                "							param = 'DefaultCustStartTime'\n" +
                "					) ds,\n" +
                "					(\n" +
                "						SELECT\n" +
                "							value\n" +
                "						FROM\n" +
                "							BOSNET1.dbo.TMS_Params\n" +
                "						WHERE\n" +
                "							param = 'DefaultCustEndTime'\n" +
                "					) de,\n" +
                "					(\n" +
                "						SELECT\n" +
                "							value\n" +
                "						FROM\n" +
                "							BOSNET1.dbo.TMS_Params\n" +
                "						WHERE\n" +
                "							param = 'DefaultCustVehicleTypes'\n" +
                "					) vt\n" +
                "				WHERE\n" +
                "					c.priority >= 0\n" +
                "			) aq\n" +
                "		ORDER BY\n" +
                "			aq.Customer_Priority";

        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)) {

            con.setAutoCommit(false);
            ps.executeUpdate();
            con.setAutoCommit(true);

            cds = "OK";
        }catch (Exception e) {            
            HashMap<String, String> pl = new HashMap<String, String>();
            pl.put("ID", runID);
            pl.put("fileNmethod", "AlgoRunner&insertPreRouteJob Exc");
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

    public String insertPreRouteJobCopy(String runID, String prevRunID, String branchCode,
            String dateDeliv, String str)
            throws Exception {

        String cds = "ERROR insertPreRouteJob";

        String sql = "insert\n"
                + "	into\n"
                + "		bosnet1.dbo.TMS_PreRouteJob(\n"
                + "			RunId,\n"
                + "			Customer_ID,\n"
                + "			DO_Number,\n"
                + "			Long,\n"
                + "			Lat,\n"
                + "			Customer_priority,\n"
                + "			Service_time,\n"
                + "			deliv_start,\n"
                + "			deliv_end,\n"
                + "			vehicle_type_list,\n"
                + "			total_kg,\n"
                + "			total_cubication,\n"
                + "			DeliveryDeadline,\n"
                + "			DayWinStart,\n"
                + "			DayWinEnd,\n"
                + "			UpdatevDate,\n"
                + "			CreateDate,\n"
                + "			isActive,\n"
                + "			Is_Exclude,\n"
                + "			Is_Edit\n,"
                + "			Product_Description,\n"
                + "			Gross_Amount,\n"
                + "			DOQty,\n"
                + "			DOQtyUOM,\n"
                + "			Name1,\n"
                + "			Street,\n"
                + "			Distribution_Channel,\n"
                + "			Customer_Order_Block_all,\n"
                + "			Customer_Order_Block,\n"
                + "			Request_Delivery_Date\n"
                + "		) select\n"
                + "			'" + runID + "' as RunId,\n"
                + "			Customer_ID,\n"
                + "			DO_Number,\n"
                + "			Long,\n"
                + "			Lat,\n"
                + "			Customer_priority,\n"
                + "			Service_time,\n"
                + "			deliv_start,\n"
                + "			deliv_end,\n"
                + "			vehicle_type_list,\n"
                + "			total_kg,\n"
                + "			total_cubication,\n"
                + "			DeliveryDeadline,\n"
                + "			DayWinStart,\n"
                + "			DayWinEnd,\n"
                + "			cast(\n"
                + "				FORMAT(\n"
                + "					getdate(),\n"
                + "					'yyyy-MM-dd hh-mm'\n"
                + "				) as varchar\n"
                + "			) as UpdatevDate,\n"
                + "			cast(\n"
                + "				FORMAT(\n"
                + "					getdate(),\n"
                + "					'yyyy-MM-dd hh-mm'\n"
                + "				) as varchar\n"
                + "			) as CreateDate,\n"
                + "			isActive,\n"
                + "			Is_Exclude,\n"
                + "			'" + str + "'Is_Edit,\n"
                + "			Product_Description,\n"
                + "			Gross_Amount,\n"
                + "			DOQty,\n"
                + "			DOQtyUOM,\n"
                + "			Name1,\n"
                + "			Street,\n"
                + "			Distribution_Channel,\n"
                + "			Customer_Order_Block_all,\n"
                + "			Customer_Order_Block,\n"
                + "			Request_Delivery_Date\n"
                + "		from\n"
                + "			bosnet1.dbo.TMS_PreRouteJob\n"
                + "		where\n"
                + "			RunId = '" + prevRunID + "'\n"
                + "			and Is_Exclude = 'inc'\n"
                + "			and Is_Edit = 'edit'";

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
                "			NamaDriver\n" +
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
                "			NamaDriver\n" +
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
                "			va.NamaDriver\n" +
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
                "		INNER JOIN bosnet1.dbo.vehicle vh ON\n" +
                "			v.vehicle_code = vh.vehicle_code\n" +
                "		INNER JOIN bosnet1.dbo.TMS_vehicleAtr va ON\n" +
                "			v.vehicle_code = va.vehicle_code\n" +
                "		LEFT OUTER JOIN bosnet1.dbo.TMS_Params pr ON\n" +
                "			pr.param = 'HargaSolar'\n" +
                "		LEFT OUTER JOIN bosnet1.dbo.TMS_Params bm ON\n" +
                "			bm.param = 'DefaultKonsumsiBBm'\n" +
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
    
    private static void prepareCustTable(String branchCode) 
        throws Exception {
        String sql = "EXEC bosnet1.dbo.TMS_GetCustLongLat ?";
        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setEscapeProcessing(true);
            ps.setQueryTimeout(15);
            ps.setString(1, branchCode);
            ps.execute();
        }catch (Exception e) {
            HashMap<String, String> pl = new HashMap<String, String>();
            pl.put("ID", "");
            pl.put("fileNmethod", "AlgoRunner&prepareCustTable Exc");
            pl.put("datas", "");
            pl.put("msg", e.getMessage());
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            Date date = new Date();
            pl.put("dates", dateFormat.format(date).toString());
            Other.insertLog(pl);
            throw e;
        }
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
        if(chn.equals("GT")){
            query = "	AND cs.Distribution_Channel NOT IN('MT')";
        }else if(chn.equals("MT")){
            query = "	AND cs.Distribution_Channel IN('MT')";
        }else if(chn.equals("ALL")){
            query = "";
        }
        
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
                "		WHEN ca.DayWinStart IS NULL THEN ds.value\n" +
                "		ELSE ca.DayWinStart\n" +
                "	END DayWinStart,\n" +
                "	CASE\n" +
                "		WHEN ca.DayWinEnd IS NULL THEN de.value\n" +
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
                "	sp.Request_Delivery_Date,\n" +
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
                "	du.value AS ChannelNullDefault\n" +
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
                query + "\n" +
                "ORDER BY\n" +
                "	sp.Customer_ID ASC\n";
        
        //select
        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)) {
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
                    //pl.put("DOCreationDate", rs.getString("DOCreationDate"));
                    asd.add(pl);
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
                "			MarketId\n" +
                "		) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
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
                
                //cek hari buka
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                //System.out.println(dayOfWeek);
                if(pl.get("Customer_ID").equals("5820002148")){
                    //System.out.println(edt);
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
                //System.out.println(pl.get("Distribution_Channel"));
                pl.replace("Distribution_Channel", "GT");
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
                }
            }//System.out.println(pl.get("Customer_ID"));

            if(pl.get("Customer_ID").equals("5820001001") || pl.get("Customer_ID").equals("5820000348")){
                System.out.println(pl.get("Customer_ID"));
                System.out.println("DeliveryDeadline " + pl.get("DeliveryDeadline"));
                System.out.println("Distribution_Channel "+pl.get("Distribution_Channel"));
                System.out.println("dateDeliv "+dateDeliv);
                System.out.println("Request_Delivery_Date "+pl.get("Request_Delivery_Date"));
                System.out.println("Customer_priority :" + pl.get("Customer_priority"));
                System.out.println("str :" + str);
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
        
        //saturday 12:00
        Calendar deliv = Calendar.getInstance();
        deliv.setTime(dateDeliv);
        int day = deliv.get(Calendar.DAY_OF_WEEK);
        
        if(day == 7){
            c.setTime(shf.parse(pl.get("deliv_end")));
            deliv.setTime(shf.parse(reference));
            if(c.after(deliv)){
                pl.replace("deliv_end", reference);
            }           
        }
        
        Calendar date2 = Calendar.getInstance();
        c.setTime(shf.parse(pl.get("deliv_start")));
        date2.setTime(shf.parse(reference));
        //-1 > 12:00 deliv_start
        if (c.after(date2)) {
            System.out.println("bfr" + pl.toString());
            c.add(Calendar.HOUR, -1);
            pl.replace("deliv_start", shf.format(c.getTime()).toString());
            //System.out.println("afr" + pl.toString());
        }
        
        c.setTime(shf.parse(pl.get("deliv_end")));
        //-1 > 12:00 deliv_end
        if (c.after(date2)) {
            //System.out.println("bfr" + pl.toString());
            c.add(Calendar.HOUR, -1);
            pl.replace("deliv_end", shf.format(c.getTime()).toString());
            //System.out.println("afr" + pl.toString());
        }
        
        //buffer-end time
        int end = Integer.parseInt(pl.get("BufferEndDefault"));
        c.add(Calendar.MINUTE, - end);
        pl.replace("deliv_end", shf.format(c.getTime()).toString());       
        
        return pl;
    }

}

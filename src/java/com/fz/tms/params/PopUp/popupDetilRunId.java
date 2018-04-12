/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params.PopUp;

import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.tms.params.model.SummaryVehicle;
import com.fz.tms.params.service.Other;
import com.fz.util.FZUtil;
import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author dwi.rangga
 */
public class popupDetilRunId implements BusinessLogic {

    DecimalFormat df = new DecimalFormat("##.0");
    DecimalFormat mn = new DecimalFormat("###,###");
    String branch = "";
    BigDecimal tcap = new BigDecimal(0);
    BigDecimal tkub = new BigDecimal(0);
    BigDecimal ttravel = new BigDecimal(0);
    BigDecimal tservice = new BigDecimal(0);
    BigDecimal tcust = new BigDecimal(0);
    BigDecimal tkm = new BigDecimal(0);
    BigDecimal tamount = new BigDecimal(0);
    BigDecimal ttransportCost = new BigDecimal(0);

    String runID = "";
    String oriRunID = "";

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response,
            PageContext pc
    ) throws Exception {
        //AccessGoogleDirection w = new AccessGoogleDirection();
        //w.renderLngLat();
        runID = FZUtil.getHttpParam(request, "runID");
        oriRunID = FZUtil.getHttpParam(request, "oriRunID");

        try {
            List<SummaryVehicle> asd = getSummary(runID);
            request.setAttribute("oriRunID", oriRunID);
            request.setAttribute("runID", runID);
            request.setAttribute("cap", df.format(tcap).toString());
            request.setAttribute("kub", df.format(tkub).toString());
            request.setAttribute("ttravel", df.format(ttravel).toString());
            request.setAttribute("tservice", df.format(tservice).toString());
            request.setAttribute("tcust", tcust.toString());
            request.setAttribute("tkm", df.format(tkm).toString());
            request.setAttribute("tamount", "Rp." + mn.format(tamount).toString());
            request.setAttribute("ttransportCost", "Rp." + mn.format(ttransportCost).toString());
            request.setAttribute("branch", branch);
            request.setAttribute("ListSum", asd);
        } catch (Exception e) {
            HashMap<String, String> pl = new HashMap<String, String>();
            pl.put("ID", runID);
            pl.put("fileNmethod", "popupDetilRunId&run Ex");
            pl.put("datas", "");
            pl.put("msg", e.getMessage());
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            Date date = new Date();
            pl.put("dates", dateFormat.format(date).toString());
            Other.insertLog(pl);
        }
    }

    public List<SummaryVehicle> getSummary(String runID) throws Exception {
        List<SummaryVehicle> asd = new ArrayList<>();
        SummaryVehicle sq = new SummaryVehicle();
        String sql = "SELECT\n"
                + "	a.vehicle_code,\n"
                + "	a.vehicle_type,\n"
                + "	CAST(\n"
                + "		a.aweight AS VARCHAR\n"
                + "	)+ ' / ' + CAST(\n"
                + "		a.weight AS VARCHAR\n"
                + "	),\n"
                + "	CAST(\n"
                + "		CAST(\n"
                + "			(\n"
                + "				a.aweight /(\n"
                + "					a.weight / 100\n"
                + "				)\n"
                + "			) AS NUMERIC(\n"
                + "				15,\n"
                + "				0\n"
                + "			)\n"
                + "		) AS VARCHAR\n"
                + "	),\n"
                + "	CAST(\n"
                + "		CAST(\n"
                + "			a.avolume AS NUMERIC(\n"
                + "				15,\n"
                + "				1\n"
                + "			)\n"
                + "		) AS VARCHAR\n"
                + "	)+ ' / ' + CAST(\n"
                + "		a.volume AS VARCHAR\n"
                + "	),\n"
                + "	CAST(\n"
                + "		CAST(\n"
                + "			(\n"
                + "				(\n"
                + "					a.avolume \n"
                + "				)/(\n"
                + "					a.volume / 100\n"
                + "				)\n"
                + "			) AS NUMERIC(\n"
                + "				15,\n"
                + "				0\n"
                + "			)\n"
                + "		) AS VARCHAR\n"
                + "	),\n"
                + "	a.gross_amount,\n"
                + "	a.do_number,\n"
                + "	a.branch,\n"
                + "	DATEDIFF(\n"
                + "		MINUTE,\n"
                + "		a.startTime,\n"
                + "		a.arrive\n"
                + "	)- a.Service_time,\n"
                + "	a.Service_time,\n"
                + "	CAST(\n"
                + "		a.transportCost AS NUMERIC(\n"
                + "			9,\n"
                + "			0\n"
                + "		)\n"
                + "	) AS transportCost,\n"
                + "	a.dist\n"
                + "FROM\n"
                + "	(\n"
                + "		SELECT\n"
                + "			ra.vehicle_code,\n"
                + "			SUM( CASE WHEN LEN( ra.weight )= 0 THEN 0 ELSE CAST( ra.weight AS NUMERIC( 15, 0 )) END ) AS aweight,\n"
                + "			SUM( CASE WHEN LEN( ra.volume )= 0 THEN 0 ELSE CAST( ra.volume AS NUMERIC( 15, 0 )) END ) AS avolume,\n"
                + "			SUM( CAST( pr.gross_amount AS NUMERIC( 9, 0 ))) AS gross_amount,\n"
                + "			SUM( pr.Service_time ) AS Service_time,\n"
                + "			COUNT( CASE ra.do_number WHEN '' THEN 0 ELSE CAST( ra.do_number AS NUMERIC( 9, 0 )) END )- 1 AS do_number,\n"
                + "			ra.branch,\n"
                + "			CAST(\n"
                + "				ve.weight AS NUMERIC(\n"
                + "					15,\n"
                + "					0\n"
                + "				)\n"
                + "			) AS weight,\n"
                + "			CAST(\n"
                + "				ve.volume AS NUMERIC(\n"
                + "					15,\n"
                + "					0\n"
                + "				)\n"
                + "			) AS volume,\n"
                + "			ve.vehicle_type,\n"
                + "			ve.startTime,\n"
                + "			ar.arrive,\n"
                + "			ar.transportCost,\n"
                + "			CAST((CAST(sum(Dist) AS NUMERIC( 9 ))/ 1000)AS NUMERIC( 9 )) AS Dist\n"
                //+ "			SUM( CAST( ra.Dist / 1000 AS NUMERIC( 9 ))) AS Dist\n"
                + "		FROM\n"
                + "			BOSNET1.dbo.TMS_RouteJob ra\n"
                + "		INNER JOIN BOSNET1.dbo.TMS_PreRouteVehicle ve ON\n"
                + "			ra.runID = ve.runID\n"
                + "			AND ra.vehicle_code = ve.vehicle_code\n"
                + "		INNER JOIN(\n"
                + "				SELECT\n"
                + "					runID,\n"
                + "					vehicle_code,\n"
                + "					MAX( arrive ) AS arrive,\n"
                + "					SUM( transportCost ) AS transportCost\n"
                + "				FROM\n"
                + "					BOSNET1.dbo.TMS_RouteJob\n"
                + "				GROUP BY\n"
                + "					runID,\n"
                + "					vehicle_code\n"
                + "			) ar ON\n"
                + "			ar.RunId = ra.runID\n"
                + "			AND ar.vehicle_code = ra.vehicle_code\n"
                + "		LEFT OUTER JOIN(\n"
                + "				SELECT\n"
                + "					DISTINCT runID,\n"
                + "					customer_id,\n"
                + "					Service_time,\n"
                + "					is_edit,\n"
                + "					SUM( gross_amount ) AS gross_amount\n"
                + "				FROM\n"
                + "					BOSNET1.dbo.TMS_PreRouteJob\n"
                + "				WHERE\n"
                + "					is_edit = 'edit'\n"
                + "				GROUP BY\n"
                + "					runID,\n"
                + "					customer_id,\n"
                + "					Service_time,\n"
                + "					is_edit\n"
                + "			) pr ON\n"
                + "			ra.runID = pr.runID\n"
                + "			AND ra.customer_id = pr.customer_id\n"
                + "		WHERE\n"
                + "			ra.runID = '" + runID + "'\n"
                + "			AND ra.vehicle_code <> 'NA'\n"
                + "			AND ra.arrive <> ''\n"
                + "		GROUP BY\n"
                + "			ra.vehicle_code,\n"
                + "			ra.branch,\n"
                + "			ve.weight,\n"
                + "			ve.volume,\n"
                + "			ve.vehicle_type,\n"
                + "			ve.startTime,\n"
                + "			ar.arrive,\n"
                + "			ar.transportCost\n"
                + "	) a;";
        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    sq = new SummaryVehicle();
                    int i = 1;
                    sq.truckid = FZUtil.getRsString(rs, i++, "");
                    sq.trucktype = FZUtil.getRsString(rs, i++, "");
                    sq.capacity = FZUtil.getRsString(rs, i++, "");
                    sq.capacityPer = FZUtil.getRsString(rs, i++, "");
                    tcap = tcap.add(BigDecimal.valueOf(Long.valueOf(sq.capacityPer)));
                    sq.kubikasi = FZUtil.getRsString(rs, i++, "");
                    sq.kubikasiPer = FZUtil.getRsString(rs, i++, "");
                    tkub = tkub.add(BigDecimal.valueOf(Long.valueOf(sq.kubikasiPer)));
                    String amount = FZUtil.getRsString(rs, i++, "");
                    sq.amount = mn.format(BigDecimal.valueOf(Long.valueOf(amount))).toString();
                    tamount = tamount.add(BigDecimal.valueOf(Long.valueOf(amount)));
                    sq.DOcount = FZUtil.getRsString(rs, i++, "");
                    tcust = tcust.add(BigDecimal.valueOf(Long.valueOf(sq.DOcount)));
                    branch = FZUtil.getRsString(rs, i++, "");
                    String time = FZUtil.getRsString(rs, i++, "");
                    //System.out.println("ttravel " + time + " " +df.format(BigDecimal.valueOf(Long.valueOf(time)).divide(BigDecimal.valueOf(60), MathContext.DECIMAL128)));
                    //System.out.println("tservice "+BigDecimal.valueOf(Long.valueOf(time)).divide(BigDecimal.valueOf(60), MathContext.DECIMAL128));
                    sq.time = df.format(BigDecimal.valueOf(Long.valueOf(time)).divide(BigDecimal.valueOf(60), MathContext.DECIMAL128));
                    BigDecimal bd = new BigDecimal(sq.time);
                    ttravel = ttravel.add(bd);
                    time = FZUtil.getRsString(rs, i++, "");
                    //System.out.println("ttravel " + time + " " +df.format(BigDecimal.valueOf(Long.valueOf(time)).divide(BigDecimal.valueOf(60), MathContext.DECIMAL128)));
                    //System.out.println("tservice "+BigDecimal.valueOf(Long.valueOf(time)).divide(BigDecimal.valueOf(60), MathContext.DECIMAL128));
                    sq.sctime = df.format(BigDecimal.valueOf(Long.valueOf(time)).divide(BigDecimal.valueOf(60), MathContext.DECIMAL128));
                    bd = new BigDecimal(sq.sctime);
                    tservice = tservice.add(bd);
                    String transportCost = FZUtil.getRsString(rs, i++, "");
                    ttransportCost = ttransportCost.add(BigDecimal.valueOf(Long.valueOf(transportCost)));
                    sq.transportCost = mn.format(BigDecimal.valueOf(Long.valueOf(transportCost))).toString();
                    BigDecimal km = BigDecimal.valueOf(Long.valueOf(FZUtil.getRsString(rs, i++, "")));
                    sq.km = km.toString();
                    tkm = tkm.add(km);

                    //Check error submit SAP
                    ArrayList<String> alDo = getDo(runID, sq.truckid);
                    for (int j = 0; j < alDo.size(); j++) {
                        String doNum = alDo.get(j);

                        int checkResultShiment = checkResultShipment(doNum);
                        //If submitted to Result_Shipment
                        if (checkResultShiment > 0) {
                            String checkStatusShipment = checkStatusShipment(doNum);
                            //If submitted to Status_Shipment
                            try {
                                long isNumber = Long.parseLong(checkStatusShipment);
                                sq.isFix = "" + isNumber;
                            } //If failed to be submitted to Status_Shipment
                            catch (Exception er) {
                                sq.isFix = "null";
                                sq.error = checkStatusShipment;
                                break;
                            }
                        }//If failed to be submitted to Result_Shipment
                        else {
                            sq.isFix = "null";
                            break;
                        }
                    }

                    //Check if volume or weight is overload
                    //volume
                    if (Integer.parseInt(sq.capacityPer) > 100) {
                        sq.isFix = "er";
                        sq.error = "Capacity overload";
                    }
                    //weight
                    if (Integer.parseInt(sq.kubikasiPer) > 100) {
                        sq.isFix = "er";
                        sq.error = "Kubikasi overload";
                    }
                    if (Integer.parseInt(sq.kubikasiPer) > 100 && Integer.parseInt(sq.capacityPer) > 100) {
                        sq.isFix = "er";
                        sq.error = "Capacity and kubikasi overload";
                    }

                    asd.add(sq);
                }
                tcap = tcap.divide(BigDecimal.valueOf(asd.size()), MathContext.DECIMAL128);
                tkub = tkub.divide(BigDecimal.valueOf(asd.size()), MathContext.DECIMAL128);
                //String numb = (ttravel.intValue() / asd.size() / 60) + "." + (ttravel.intValue() / asd.size() % 60);
                ttravel = ttravel.divide(BigDecimal.valueOf(asd.size()), MathContext.DECIMAL128);
                //numb = (tservice.intValue() / asd.size() / 60) + "." + (tservice.intValue() / asd.size() % 60);
                tservice = tservice.divide(BigDecimal.valueOf(asd.size()), MathContext.DECIMAL128);
                tkm = tkm.divide(BigDecimal.valueOf(asd.size()), MathContext.DECIMAL128);
                //tcust = tcust.divide(BigDecimal.valueOf(asd.size()));
            }
        }
        return asd;
    }

    public String getVendorId(String v) {
        String[] vSplit = v.split("_");
        return vSplit[1] + vSplit[3];
    }

    public ArrayList<String> getDo(String runId, String vehicleCode) throws Exception {
        ArrayList<String> alDo = new ArrayList<>();
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql;
                sql = "SELECT DISTINCT\n"
                        + "	prj.DO_Number\n"
                        + "FROM \n"
                        + "	[BOSNET1].[dbo].[TMS_PreRouteJob] prj\n"
                        + "INNER JOIN\n"
                        + "	(SELECT\n"
                        + "		rj.customer_id\n"
                        + "	 FROM \n"
                        + "		[BOSNET1].[dbo].[TMS_RouteJob] rj\n"
                        + "	 WHERE \n"
                        + "		rj.RunId = '" + runId + "' and rj.vehicle_code = '" + vehicleCode + "') \n"
                        + "		rj "
                        + "      ON\n"
                        + "             prj.Customer_ID = rj.customer_id\n"
//                        + "INNER JOIN\n"
//                        + "      (SELECT \n"
//                        + "             sp.DO_Number,\n"
//                        + "             sp.Request_Delivery_Date\n"
//                        + "       FROM\n"
//                        + "             [BOSNET1].[dbo].[TMS_ShipmentPlan] sp\n"
//                        + "       WHERE\n"
//                        + "             sp.Batch is not null) sp "
//                        + "       ON\n"
//                        + "             prj.Request_Delivery_Date = sp.Request_Delivery_Date\n"
//			+ "		and prj.DO_Number = sp.DO_Number\n"
                        + "WHERE \n"
                        + "	prj.RunId = '" + runId + "'\n"
                        + "     AND prj.Batch <> 'NULL'";

                try (ResultSet rs = stm.executeQuery(sql)) {
                    while (rs.next()) {
                        alDo.add(rs.getString("DO_Number"));
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return alDo;
    }

    public BigDecimal hitungKm(String ve, String runId) throws Exception {
        BigDecimal all = new BigDecimal(0);
        List<HashMap<String, String>> asd = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> pl = new HashMap<String, String>();

        String sql = "select lon, lat from BOSNET1.dbo.TMS_RouteJob "
                + "where runID = '" + runId + "' and vehicle_code = '" + ve + "' order by arrive asc;";
        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pl = new HashMap<String, String>();
                    int i = 1;
                    pl.put("lon", FZUtil.getRsString(rs, i++, ""));
                    pl.put("lat", FZUtil.getRsString(rs, i++, ""));
                    asd.add(pl);
                }
            }
        }
        FZUtil fz = new FZUtil();
        for (int i = 0; i < asd.size(); i++) {
            if (i <= (asd.size() - 2)) {
                //System.out.println(i + "hitungKm()" + asd.size() + "" + (i + 1));
                double a = fz.calcMeterDist(Double.parseDouble(asd.get(i).get("lon")),
                        Double.parseDouble(asd.get(i).get("lat")),
                        Double.parseDouble(asd.get(i + 1).get("lon")),
                        Double.parseDouble(asd.get(i + 1).get("lat")));
                all = all.add(BigDecimal.valueOf(a));
            }
        }

        return all;
    }

    public int checkResultShipment(String doNum) throws Exception {
        int rowNum = 0;
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql;
                sql = "SELECT COUNT(*) rowNum FROM BOSNET1.dbo.TMS_Result_Shipment WHERE Delivery_Number = '" + doNum + "'";

                try (ResultSet rs = stm.executeQuery(sql)) {
                    if (rs.next()) {
                        rowNum = rs.getInt("rowNum");
                    } else {
                        rowNum = 0; //Submitting
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return rowNum;
    }

    public String checkStatusShipment(String doNum) throws Exception {
        String msg = "";
        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                String sql;
                sql = "SELECT TOP 1 SAP_Message, Ship_No_SAP FROM BOSNET1.dbo.TMS_Status_Shipment WHERE Delivery_Number = '" + doNum + "'";

                try (ResultSet rs = stm.executeQuery(sql)) {
                    if (rs.next()) {
                        if (rs.getString("SAP_Message") != null) {
                            msg = rs.getString("SAP_Message"); //Error
                        } else {
                            msg = rs.getString("Ship_No_SAP"); //Submitted

                        }
                    } else {
                        msg = "1"; //Submitting
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return msg;
    }

    public List<HashMap<String, String>> getVehicle(String runID) throws Exception {
        List<HashMap<String, String>> asd = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> pl = new HashMap<String, String>();
        String sql = "select distinct ve.Vehicle_Code, ve.Vehicle_Type, ve.Volume, ve.Weight "
                + "from BOSNET1.dbo.TMS_RouteJob rj, BOSNET1.dbo.Vehicle ve "
                + "where rj.vehicle_code = ve.Vehicle_Code and rj.runID = '" + runID + "';";
        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pl = new HashMap<String, String>();
                    int i = 1;

                    pl.put("Vehicle_Code", FZUtil.getRsString(rs, i++, ""));
                    pl.put("Vehicle_Type", FZUtil.getRsString(rs, i++, ""));
                    pl.put("Volume", FZUtil.getRsString(rs, i++, ""));
                    pl.put("Weight", FZUtil.getRsString(rs, i++, ""));
                    asd.add(pl);
                }
            }
        }
        return asd;
    }
}

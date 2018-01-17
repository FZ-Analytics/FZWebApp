/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params.service;

import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.util.FZUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author Administrator
 */
public class IntegrationTracker implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response,
            PageContext pc
    ) throws Exception {
        String branchCode = FZUtil.getHttpParam(request, "branch");
        //2017-11-06
        String date = FZUtil.getHttpParam(request, "date");
        integrate(date);
    }

    public String integrate(String date) {
        String stat = "ERROR";
        try {            
            List<HashMap<String, String>> sls = sls(date);
            List<HashMap<String, String>> vehi = vehi(date);

            if (insrtsls(sls).equalsIgnoreCase("OK")) {
                if (insrtvehi(vehi).equalsIgnoreCase("OK")) {
                    stat = "OK";
                }
            }           

        } catch (Exception ex) {
            Logger.getLogger(IntegrationTracker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stat;
    }

    public List<HashMap<String, String>> sls(String date) throws Exception {
        List<HashMap<String, String>> plx = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> pl = new HashMap<String, String>();

        String sql = "DECLARE @DATE AS DATE\n" +
                "SET\n" +
                "@DATE = '2017-11-06' SELECT\n" +
                "	SalesmanId = TR.szSalesId,\n" +
                "	[DateTime] = TR.dtmActivity,\n" +
                "	Long = TR.szLongitude,\n" +
                "	Lat = TR.szLatitude,\n" +
                "	BranchId = F.WorkplaceId,\n" +
                "	BranchName = BR.SalOffName,\n" +
                "	SalesmanName = F.SalesName,\n" +
                "	TYPE = F.Type\n" +
                "FROM\n" +
                "	(\n" +
                "		SELECT\n" +
                "			*\n" +
                "		FROM\n" +
                "			BOSNET.dbo.SIN_SD_TrackingEmployee\n" +
                "		WHERE\n" +
                "			CAST(\n" +
                "				dtmActivity AS DATE\n" +
                "			)= @DATE\n" +
                "	) TR\n" +
                "INNER JOIN(\n" +
                "		SELECT\n" +
                "			*\n" +
                "		FROM\n" +
                "			SysUtil.IBACONSOL.dbo.Bosnet_FSR_Type\n" +
                "		WHERE\n" +
                "			Active = 1\n" +
                "			AND TYPE IN(\n" +
                "				'Farmer',\n" +
                "				'Hunter'\n" +
                "			)\n" +
                "	) F ON\n" +
                "	TR.szSalesId = F.SalesId COLLATE DATABASE_DEFAULT\n" +
                "INNER JOIN(\n" +
                "		SELECT\n" +
                "			*\n" +
                "		FROM\n" +
                "			SysUtil.IBACONSOL.dbo.Sales_Office\n" +
                "		WHERE\n" +
                "			Active = 1\n" +
                "			AND SFA = 1\n" +
                "	) BR ON\n" +
                "	F.WorkplaceId = BR.SalOffCode\n" +
                "WHERE\n" +
                "	CAST(\n" +
                "		TR.dtmActivity AS DATE\n" +
                "	)= '"+date+"'\n" +
                "ORDER BY\n" +
                "	BranchId,\n" +
                "	TYPE,\n" +
                "	SalesmanId,\n" +
                "	DateTime";

        try (Connection con = (new Db()).getConnection("jdbc/fz16");
                PreparedStatement ps = con.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pl = new HashMap<String, String>();
                    int i = 1;
                    pl.put("SalesmanId", FZUtil.getRsString(rs, i++, ""));
                    pl.put("DateTime", FZUtil.getRsString(rs, i++, ""));
                    pl.put("Long", FZUtil.getRsString(rs, i++, ""));
                    pl.put("Lat", FZUtil.getRsString(rs, i++, ""));
                    pl.put("BranchId", FZUtil.getRsString(rs, i++, ""));
                    pl.put("BranchName", FZUtil.getRsString(rs, i++, ""));
                    pl.put("SalesmanName", FZUtil.getRsString(rs, i++, ""));
                    plx.add(pl);
                }
            }
        }
        return plx;
    }

    public List<HashMap<String, String>> vehi(String date) throws Exception {
        List<HashMap<String, String>> plx = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> pl = new HashMap<String, String>();

        String sql = "DECLARE @DATE AS DATE\n" +
                "SET\n" +
                "@DATE = '2017-11-06' SELECT\n" +
                "	SalesmanId = TR.szSalesId,\n" +
                "	TruckNoPol = ED.szVehicleId,\n" +
                "	[DateTime] = TR.dtmActivity,\n" +
                "	Long = TR.szLongitude,\n" +
                "	Lat = TR.szLatitude,\n" +
                "	BranchId = F.WorkplaceId,\n" +
                "	BranchName = BR.SalOffName,\n" +
                "	SalesmanName = F.SalesName,\n" +
                "	TYPE = F.Type\n" +
                "FROM\n" +
                "	(\n" +
                "		SELECT\n" +
                "			*\n" +
                "		FROM\n" +
                "			BOSNET.dbo.SIN_SD_TrackingEmployee\n" +
                "		WHERE\n" +
                "			CAST(\n" +
                "				dtmActivity AS DATE\n" +
                "			)= @DATE\n" +
                "	) TR\n" +
                "INNER JOIN(\n" +
                "		SELECT\n" +
                "			*\n" +
                "		FROM\n" +
                "			SysUtil.IBACONSOL.dbo.Bosnet_FSR_Type\n" +
                "		WHERE\n" +
                "			Active = 1\n" +
                "			AND TYPE IN('Driver')\n" +
                "	) F ON\n" +
                "	TR.szSalesId = F.SalesId COLLATE DATABASE_DEFAULT\n" +
                "LEFT JOIN(\n" +
                "		SELECT\n" +
                "			*\n" +
                "		FROM\n" +
                "			BOSNET.dbo.BOS_PI_EmployeeDistribution\n" +
                "	) ED ON\n" +
                "	TR.szSalesId = ED.szDriverId\n" +
                "INNER JOIN(\n" +
                "		SELECT\n" +
                "			*\n" +
                "		FROM\n" +
                "			SysUtil.IBACONSOL.dbo.Sales_Office\n" +
                "		WHERE\n" +
                "			Active = 1\n" +
                "			AND SFA = 1\n" +
                "	) BR ON\n" +
                "	F.WorkplaceId = BR.SalOffCode\n" +
                "WHERE\n" +
                "	CAST(\n" +
                "		TR.dtmActivity AS DATE\n" +
                "	)= '"+date+"'\n" +
                "ORDER BY\n" +
                "	BranchId,\n" +
                "	TYPE,\n" +
                "	SalesmanId,\n" +
                "	DateTime";

        try (Connection con = (new Db()).getConnection("jdbc/fz16");
                PreparedStatement ps = con.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pl = new HashMap<String, String>();
                    int i = 1;
                    pl.put("SalesmanId", FZUtil.getRsString(rs, i++, ""));
                    pl.put("TruckNoPol", FZUtil.getRsString(rs, i++, ""));
                    pl.put("DateTime", FZUtil.getRsString(rs, i++, ""));
                    pl.put("Long", FZUtil.getRsString(rs, i++, ""));
                    pl.put("Lat", FZUtil.getRsString(rs, i++, ""));
                    pl.put("BranchId", FZUtil.getRsString(rs, i++, ""));
                    pl.put("BranchName", FZUtil.getRsString(rs, i++, ""));
                    pl.put("SalesmanName", FZUtil.getRsString(rs, i++, ""));
                    pl.put("Type", FZUtil.getRsString(rs, i++, ""));
                    plx.add(pl);
                }
            }
        }
        return plx;
    }

    public String insrtsls(List<HashMap<String, String>> sls) {
        String stat = "ERROR";

        String sql = "insert into bosnet1.dbo.TMS_tmGpsSls"
                + "(SalesmanId"
                + ",DateTime"
                + ",Long"
                + ",Lat"
                + ",BranchId"
                + ",BranchName"
                + ",SalesmanName) values(?,?,?,?,?,?,?)";
        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)) {

            // TODO: insert to tms_progress
            for (int a = 0; a < sls.size(); a++) {
                for (int b = 0; b < sls.get(a).size(); b++) {
                    ps.setString(1, sls.get(a).get("SalesmanId"));
                    ps.setString(2, sls.get(a).get("DateTime"));
                    ps.setString(3, sls.get(a).get("Long"));
                    ps.setString(4, sls.get(a).get("Lat"));
                    ps.setString(5, sls.get(a).get("BranchId"));
                    ps.setString(6, sls.get(a).get("BranchName"));
                    ps.setString(7, sls.get(a).get("SalesmanName"));
                    ps.executeUpdate();
                    stat = "OK";
                }
            }           
        } catch (Exception e) {

        }
        return stat;
    }
    
    public String insrtvehi(List<HashMap<String, String>> vehi) {
        String stat = "ERROR";

        String sql = "insert into bosnet1.dbo.TMS_tmGpsVehi"
                + "(SalesmanId"
                + ",TruckNoPol"
                + ",DateTime"
                + ",Long"
                + ",Lat"
                + ",BranchId"
                + ",BranchName"
                + ",SalesmanName"
                + ",Type) values(?,?,?,?,?,?,?,?,?)";
        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)) {

            // TODO: insert to tms_progress
            for (int a = 0; a < vehi.size(); a++) {
                for (int b = 0; b < vehi.get(a).size(); b++) {
                    ps.setString(1, vehi.get(a).get("SalesmanId"));
                    ps.setString(2, vehi.get(a).get("TruckNoPol"));
                    ps.setString(3, vehi.get(a).get("DateTime"));
                    ps.setString(4, vehi.get(a).get("Long"));
                    ps.setString(5, vehi.get(a).get("Lat"));
                    ps.setString(6, vehi.get(a).get("BranchId"));
                    ps.setString(7, vehi.get(a).get("BranchName"));
                    ps.setString(8, vehi.get(a).get("SalesmanName"));
                    ps.setString(9, vehi.get(a).get("Type"));
                    ps.executeUpdate();
                    stat = "OK";
                }
            }           
        } catch (Exception e) {

        }
        return stat;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params.PopUp;

import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.tms.params.model.Vehicle;
import com.fz.util.FZUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author dwi.oktaviandi
 */
public class ShowPreRouteVehicle implements BusinessLogic {
    //DecimalFormat df = new DecimalFormat("##.0");
        
    @Override
    public void run(HttpServletRequest request, HttpServletResponse response
            , PageContext pc) throws Exception {
        
        String runId = FZUtil.getHttpParam(request, "runId");
        
        String sql = "SELECT\n" +
                "	vehicle_code,\n" +
                "	weight,\n" +
                "	volume,\n" +
                "	vehicle_type,\n" +
                "	branch,\n" +
                "	startLon,\n" +
                "	startLat,\n" +
                "	startTime,\n" +
                "	endTime,\n" +
                "	source1,\n" +
                "	costPerM,\n" +
                "	IdDriver,\n" +
                "	NamaDriver,\n" +
                "	isActive\n" +
                "FROM\n" +
                "	BOSNET1.dbo.TMS_PreRouteVehicle\n" +
                "WHERE\n" +
                "	RunId = '"+runId+"'";
        
        Vehicle ve = new Vehicle();
        String br = "";
        List<Vehicle> js = new ArrayList<Vehicle>();
        
        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)){
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    ve = new Vehicle();
                    int i = 1;
                    ve.vehicle_code = FZUtil.getRsString(rs, i++, "");
                    ve.weight = FZUtil.getRsString(rs, i++, "");
                    ve.volume = FZUtil.getRsString(rs, i++, "");
                    ve.vehicle_type = FZUtil.getRsString(rs, i++, "");
                    br = FZUtil.getRsString(rs, i++, "");
                    ve.startLon = FZUtil.getRsString(rs, i++, "");
                    ve.startLat = FZUtil.getRsString(rs, i++, "");
                    ve.startTime = FZUtil.getRsString(rs, i++, "");
                    ve.endTime = FZUtil.getRsString(rs, i++, "");
                    ve.source1 = FZUtil.getRsString(rs, i++, "");
                    ve.costPerM = FZUtil.getRsString(rs, i++, "");
                    ve.IdDriver = FZUtil.getRsString(rs, i++, "");
                    ve.NamaDriver = FZUtil.getRsString(rs, i++, "");
                    ve.isActive = FZUtil.getRsString(rs, i++, "");
                    js.add(ve);
                }
                
                request.setAttribute("VehicleList", js);
                request.setAttribute("branch", br);
                request.setAttribute("runId", runId);
            }
        }
    }
    
}

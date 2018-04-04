/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params.PopUp;

import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.tms.params.model.Vehicle;
import com.fz.tms.params.service.VehicleAttrDB;
import com.fz.util.FZUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author dwi.oktaviandi
 */
public class popupEditPreRouteVehicle  implements BusinessLogic {
    //DecimalFormat df = new DecimalFormat("##.0");
        
    @Override
    public void run(HttpServletRequest request, HttpServletResponse response
            , PageContext pc) throws Exception {
        String runId = FZUtil.getHttpParam(request, "runId");
        String vCode = FZUtil.getHttpParam(request, "vCode");
        
        VehicleAttrDB dao = new VehicleAttrDB();        
        
        String sql = "SELECT\n" +
                "	re.vehicle_code,\n" +
                "	re.weight,\n" +
                "	re.volume,\n" +
                "	re.vehicle_type,\n" +
                "	re.branch,\n" +
                "	re.startLon,\n" +
                "	re.startLat,\n" +
                "	re.endLon,\n" +
                "	re.endLat,\n" +
                "	re.startTime,\n" +
                "	re.endTime,\n" +
                "	re.source1,\n" +
                "	re.costPerM,\n" +
                "	re.fixedCost,\n" +
                "	re.isActive,\n" +
                "	pr.value,\n" +
                "	va.costPerM,\n" +
                "	re.IdDriver,\n" +
                "	re.NamaDriver,\n" +
                "	re.agent_priority,\n" +
                "	re.max_cust\n" +
                "FROM\n" +
                "	BOSNET1.dbo.TMS_PreRouteVehicle re\n" +
                "LEFT OUTER JOIN bosnet1.dbo.TMS_Params pr ON\n" +
                "	pr.param = 'HargaSolar'\n" +
                "INNER JOIN bosnet1.dbo.TMS_vehicleAtr va ON\n" +
                "	re.vehicle_code = va.vehicle_code\n" +
                "WHERE\n" +
                "	re.RunId = '"+runId+"'\n" +
                "	AND re.vehicle_code = '"+vCode+"'";
        
        Vehicle ve = new Vehicle();
        
         try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)){
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    ve = new Vehicle();
                    int i = 1;
                    ve.vehicle_code = FZUtil.getRsString(rs, i++, "");
                    ve.weight = String.valueOf(Double.valueOf(FZUtil.getRsString(rs, i++, "")).intValue());
                    ve.volume = String.valueOf(Double.valueOf(FZUtil.getRsString(rs, i++, "")).intValue());
                    ve.vehicle_type = FZUtil.getRsString(rs, i++, "");
                    ve.branch = FZUtil.getRsString(rs, i++, "");
                    ve.startLon = FZUtil.getRsString(rs, i++, "");
                    ve.startLat = FZUtil.getRsString(rs, i++, "");
                    ve.endLon = FZUtil.getRsString(rs, i++, "");
                    ve.endLat = FZUtil.getRsString(rs, i++, "");
                    ve.startTime = FZUtil.getRsString(rs, i++, "");
                    ve.endTime = FZUtil.getRsString(rs, i++, "");
                    ve.source1 = FZUtil.getRsString(rs, i++, "");
                    ve.costPerM = FZUtil.getRsString(rs, i++, "");
                    ve.fixedCost = FZUtil.getRsString(rs, i++, "");
                    ve.isActive = FZUtil.getRsString(rs, i++, "");
                    ve.solar = FZUtil.getRsString(rs, i++, "");
                    ve.persentase = FZUtil.getRsString(rs, i++, "");
                    ve.IdDriver = FZUtil.getRsString(rs, i++, "");
                    ve.NamaDriver = FZUtil.getRsString(rs, i++, "");
                    ve.agent_priority = FZUtil.getRsString(rs, i++, "");
                    ve.max_cust = FZUtil.getRsString(rs, i++, "");
                }
                ve.RunId = runId;
                
                List<Vehicle> st = dao.getDriver(ve.branch, "");
                
                request.setAttribute("ListDriver", st);
                request.setAttribute("runId", runId);
                request.setAttribute("branch", ve.branch);
                request.setAttribute("vehicle_code", ve.vehicle_code);
                request.setAttribute("weight", ve.weight);
                request.setAttribute("volume", ve.volume);
                request.setAttribute("vehicle_type", ve.vehicle_type);
                request.setAttribute("branch", ve.branch);
                request.setAttribute("startLon", ve.startLon);
                request.setAttribute("startLat", ve.startLat);
                request.setAttribute("endLon", ve.endLon);
                request.setAttribute("endLat", ve.endLat);
                request.setAttribute("startTime", ve.startTime);
                request.setAttribute("endTime", ve.endTime);
                request.setAttribute("source1", ve.source1);
                request.setAttribute("costPerM", ve.costPerM);
                request.setAttribute("fixedCost", ve.fixedCost);
                request.setAttribute("isActive", ve.isActive);
                request.setAttribute("solar", ve.solar);
                request.setAttribute("persentase", ve.persentase);
                request.setAttribute("IdDriver", ve.IdDriver);
                request.setAttribute("NamaDriver", ve.NamaDriver);
                request.setAttribute("agent_priority", ve.agent_priority);
                request.setAttribute("max_cust", ve.max_cust);
            }
         }
    }
    
    public String submit(Vehicle ve) throws Exception{
        String str = "ERROR";
        
        String sql = "UPDATE\n" +
                "	BOSNET1.dbo.TMS_PreRouteVehicle\n" +
                "SET\n" +
                "	weight = '"+ve.weight+"',\n" +
                "	volume = '"+ve.volume+"',\n" +
                "	vehicle_type = '"+ve.vehicle_type+"',\n" +
                "	startLon = '"+ve.startLon+"',\n" +
                "	startLat = '"+ve.startLat+"',\n" +
                "	endLon = '"+ve.endLon+"',\n" +
                "	endLat = '"+ve.endLat+"',\n" +
                "	startTime = '"+ve.startTime+"',\n" +
                "	endTime = '"+ve.endTime+"',\n" +
                "	source1 = '"+ve.source1+"',\n" +
                "	costPerM = '"+ve.costPerM+"',\n" +
                "	fixedCost = '"+ve.fixedCost+"',\n" +
                "	IdDriver = '"+ve.IdDriver+"',\n" +
                "	NamaDriver = '"+ve.NamaDriver+"',\n" +
                "	agent_priority = '"+ve.agent_priority+"',\n" +
                "	max_cust = '"+ve.max_cust+"',\n" +
                "	isActive = '"+ve.isActive+"'\n" +
                "WHERE\n" +
                "	RunId = '"+ve.RunId+"'\n" +
                "	AND vehicle_code = '"+ve.vehicle_code+"'";
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

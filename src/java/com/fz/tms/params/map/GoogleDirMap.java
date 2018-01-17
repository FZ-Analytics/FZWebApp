/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params.map;

import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.tms.params.model.DODetil;
import com.fz.util.FZUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author dwi.rangga
 */
public class GoogleDirMap implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response
            , PageContext pc) throws Exception {
        
        String runID = FZUtil.getHttpParam(request, "runID");
        String vCode = FZUtil.getHttpParam(request, "vCode");
        
        String sql = "SELECT\n" +
                "	CASE\n" +
                "		WHEN aw.Name1 IS NULL THEN aq.vehicle_code\n" +
                "		ELSE aw.Name1\n" +
                "	END AS title,\n" +
                "	aq.lat,\n" +
                "	aq.lon,\n" +
                "	CASE\n" +
                "		WHEN aq.depart = '' THEN(\n" +
                "			SELECT\n" +
                "				startTime\n" +
                "			FROM\n" +
                "				BOSNET1.dbo.TMS_VehicleAtr\n" +
                "			WHERE\n" +
                "				vehicle_code = 'B9104TCH'\n" +
                "				AND included = 1\n" +
                "		)\n" +
                "		ELSE aq.arrive\n" +
                "	END AS arrive,\n" +
                "	CASE\n" +
                "		WHEN aq.depart = '' THEN aq.arrive\n" +
                "		ELSE aq.depart\n" +
                "	END AS depart,\n" +
                "	aw.Street + ',' + aw.City AS Street,\n" +
                "	aw.Distribution_Channel\n" +
                "FROM\n" +
                "	BOSNET1.dbo.TMS_RouteJob aq\n" +
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
                "			) a\n" +
                "		WHERE\n" +
                "			a.noid = 1\n" +
                "	) aw ON\n" +
                "	aq.customer_id = aw.Customer_ID\n" +
                "WHERE\n" +
                "	aq.runID = '"+runID+"'\n" +
                "	AND aq.vehicle_code = '"+vCode+"'\n" +
                "ORDER BY\n" +
                "	aq.routeNb,\n" +
                "	aq.jobNb,\n" +
                "	aq.arrive;";
        
        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)){

            try (ResultSet rs = ps.executeQuery()){
                List<HashMap<String, String>> asd = new ArrayList<HashMap<String, String>>();
               //ArrayList<HashMap<String, String>> ar = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> pl = new HashMap<String, String>();
                
                
                while (rs.next()) {
                    pl = new HashMap<String, String>();
                    int i = 1;
                    pl.put("title", FZUtil.getRsString(rs, i++, ""));
                    pl.put("lat", FZUtil.getRsString(rs, i++, ""));
                    pl.put("lng", FZUtil.getRsString(rs, i++, ""));
                    String arrive = FZUtil.getRsString(rs, i++, "");
                    String depart = FZUtil.getRsString(rs, i++, "");
                    String street = FZUtil.getRsString(rs, i++, "");
                    String str = "<h2>"+pl.get("title")+"</h2><p>Street : "+street+"</p><p>Arrive   : "+arrive+"<br />Depart: "+depart+"</p>";
                    pl.put("description", str);
                    pl.put("channel", FZUtil.getRsString(rs, i++, ""));
                    asd.add(pl);
                }
                
                asd.add(0, pl);
                asd.remove(1);
                //String str = asd.toString();
                
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String jsonOutput = gson.toJson(asd);
                //jsonOutput = "{\"myObj\":" + jsonOutput + "}";
                System.out.println(jsonOutput.toString());
                request.setAttribute("test", jsonOutput.toString());
                //request.setAttribute("branch", br);
                
            }
        }
    }
    
}

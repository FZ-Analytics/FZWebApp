/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.vehicle;

import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
//import org.apache.commons.dbutils.QueryRunner;
//import org.apache.commons.dbutils.handlers.MapListHandler;

/**
 *
 * @author Eko
 */
public class VehicleLogic implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response, PageContext pc) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        try (Connection con = (new Db()).getConnection("jdbc/fz")){
            String sql = "select * from fbvehicle";
            String res = resultSetToJson(con,sql);
            request.setAttribute("res", res);

        request
                .getRequestDispatcher("../vehicle/vehicleList.jsp")
                .forward(pc.getRequest(), pc.getResponse());
        }
    }
    
    public static String resultSetToJson(Connection connection, String query)
  {
    //List<Map<String, Object>> listOfMaps = null;
    Map<String, Object> Mapsa = null;
    ArrayList<Vehicle> vl = null;
    
    try (Statement stm = connection.createStatement()){
        ResultSet rs = stm.executeQuery(query);
        if (rs!=null) {
            vl = new ArrayList<Vehicle>();
            while (rs.next()) {
                Vehicle v = new Vehicle();
                v.VehicleID = rs.getString("VehicleID");
                v.VehicleName = rs.getString("VehicleName");
                v.Type = rs.getString("Type");
                v.Weight = rs.getInt("Weight");
                v.DefDivCode=rs.getString("DefDivCode");
                v.DefDriverID=rs.getString("DefDriverID");
                v.StartLocation=rs.getString("StartLocation");
                v.StartTime=rs.getString("StartTime");
                v.Status=rs.getString("Status");
            
                vl.add(v);
            }
        }
/*   
    try
    {
      QueryRunner queryRunner = new QueryRunner();
      listOfMaps = queryRunner.query(connection, query, new MapListHandler());
*/
    }
    catch (SQLException se)
    {
      throw new RuntimeException("Couldn't query the database.", se);
    } 
    return new Gson().toJson(vl);
  }
}

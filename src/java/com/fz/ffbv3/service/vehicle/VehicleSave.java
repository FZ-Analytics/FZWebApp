/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.vehicle;

import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.util.FZUtil;
import java.sql.Connection;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author Eko
 */
public class VehicleSave  implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response, PageContext pc) throws Exception {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String vehicleId = FZUtil.getHttpParam(request, "vehicleId");
        String vehicleName = FZUtil.getHttpParam(request, "vehicleName");
        
        //ResultSet rs = null;
        // construct sql/
            String sql;
            if (vehicleId==null || vehicleId.length() ==0) {
                sql = "insert into fbVehicle(VehicleName)"
                        + " values('" + vehicleName + "')";
            } else {
                sql = "update fbVehicle set " 
                        + " VehicleName = '" + vehicleName + "'" 
                        + " where VehicleId = " + vehicleId;
            }
        String d = "";
        int err = 0 ;
            // query insert 
            try (Connection con = (new Db()).getConnection("jdbc/fz")){
                try (Statement stm = con.createStatement()){
                    stm.executeUpdate(sql);
                }
            }
        if (err==0)
            request
                .getRequestDispatcher("vehicleFilter.jsp")
                .forward(pc.getRequest(), pc.getResponse());

    }
    
}

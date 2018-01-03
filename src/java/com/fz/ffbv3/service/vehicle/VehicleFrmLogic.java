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
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author Eko
 */
public class VehicleFrmLogic implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response, PageContext pc) throws Exception {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String vehicleId = FZUtil.getHttpParam(request, "VehicleID");
        Vehicle v = null;
        
        // if add
        if (vehicleId.length() == 0){
            v = new Vehicle();
            v.Weight=0;
        }
        else { // else update, load fields
            
            try (Connection con = (new Db()).getConnection("jdbc/fz");){
                
                // get hvsEstm from db
                VehicleDAO vDAO = new VehicleDAO();
                v = vDAO.loadByID(vehicleId);
            } catch (Exception e) {
                String s = e.getMessage();
            }
        }

                if (v != null){
                    
                    // keep values in session attribute
                    request.setAttribute("VehicleId" , v.VehicleID);
                    request.setAttribute("VehicleName" , v.VehicleName);
                    request.setAttribute("Type" , v.Type);
                    request.setAttribute("Weight" , v.Weight);
                    request.setAttribute("DefDivCode" , v.DefDivCode);
                    request.setAttribute("DefDriverID" , v.DefDriverID);
                    request.setAttribute("StartLocation" , v.StartLocation);
                    request.setAttribute("StartTime" , v.StartTime);
                    request.setAttribute("Status" , v.Status);
                }
    }
    
}

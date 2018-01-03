/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fz.ffbv3.service.vehicle2;

import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.util.FZUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 */
public class Vehicle2FormLogic implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response
            , PageContext pc) throws Exception {
        
        String vehicleID = FZUtil.getHttpParam(request, "vehicleID");
        
        Vehicle2Record r = null;
        if (vehicleID.trim().length() > 0) {
            
            Vehicle2DAO vDAO = new Vehicle2DAO();
            r = vDAO.findByID(vehicleID);
        }
        
        if (r == null){
            r = new Vehicle2Record();
        }
        
        request.setAttribute("VehicleRecord", r);
        
    }

}

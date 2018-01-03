/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fz.ffbv3.service.vehicle2;

import com.fz.ffbv3.service.usermgt.Auth;
import com.fz.generic.BusinessLogic;
import com.fz.util.FZUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 */
public class Vehicle2AddRmvRunIDLogic implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response
            , PageContext pc) throws Exception {
        
        Auth.check(pc, "Vehicle_Edit", true);

        String submitBtn = FZUtil.getHttpParam(request, "submitBtn");
        String vehicleID = FZUtil.getHttpParam(request, "vehicleID");
        String newRunID = "";
        
        if (submitBtn.equals("update")){
            newRunID = FZUtil.getHttpParam(request, "newRunID");
        }
        else if (submitBtn.equals("remove")){
            newRunID = "";
        }
        
        Vehicle2DAO vDAO = new Vehicle2DAO();
        vDAO.updateRunID(vehicleID, newRunID);
        
        request.getRequestDispatcher("../appGlobal/success.jsp")
                .forward(request, response);
        
    }

}

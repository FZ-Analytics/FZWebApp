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
public class Vehicle2SaveLogic implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response
            , PageContext pc) throws Exception {
        
        Auth.check(pc, "Vehicle_Edit", true);

        Vehicle2Record r = new Vehicle2Record();
        
        r.vehicleID = FZUtil.getHttpParam(request, "vehicleID");
        if (r.vehicleID.equals("(Auto)")) r.vehicleID = "";
        
        r.vehicleName = FZUtil.getHttpParam(request, "vehicleName");
        r.status = FZUtil.getHttpParam(request, "status");
        r.includeInRun = FZUtil.getHttpParam(request, "includeInRun");
        r.defDivCode = FZUtil.getHttpParam(request, "defDivCode");
        r.startLon = FZUtil.getHttpParam(request, "startLon");
        r.startLat = FZUtil.getHttpParam(request, "startLat");
        r.startTime = FZUtil.getHttpParam(request, "startTime");
        r.remark = FZUtil.getHttpParam(request, "remark");
        r.startLocation = FZUtil.getHttpParam(request, "startLocation");
        r.lastRunID = FZUtil.getHttpParam(request, "lastRunID");
        
        Vehicle2DAO vDAO = new Vehicle2DAO();
        vDAO.save(r);
        
        request.getRequestDispatcher("../appGlobal/success.jsp")
                .forward(request, response);
    }

}

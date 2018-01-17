/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params.Vehicle;

import com.fz.generic.BusinessLogic;
import com.fz.tms.params.model.Vehicle;
import com.fz.tms.params.service.VehicleAttrDB;
import com.fz.util.FZUtil;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author dwi.rangga
 */
public class ParamVehicleViewPre   implements BusinessLogic {    
    @Override
    public void run(
            HttpServletRequest request
            , HttpServletResponse response
            , PageContext pc
    ) throws Exception {
        String vehiId = FZUtil.getHttpParam(request, "vehiId");
        
        VehicleAttrDB dao = new VehicleAttrDB();
        List<Vehicle> ar = dao.getVehi(vehiId);
        
        request.setAttribute("vehicle_code", ar.get(0).vehicle_code);
        request.setAttribute("branch", ar.get(0).branch);
        request.setAttribute("startLon", ar.get(0).startLat);
        request.setAttribute("startLat", ar.get(0).startLat);
        request.setAttribute("endLon", ar.get(0).endLon);
        request.setAttribute("endLat", ar.get(0).endLat);
        request.setAttribute("startTime", ar.get(0).startTime);
        request.setAttribute("endTime", ar.get(0).endTime);
        request.setAttribute("source1", ar.get(0).source1);
        request.setAttribute("vehicle_type", ar.get(0).vehicle_type);
        request.setAttribute("weight", (String) ar.get(0).weight);
        request.setAttribute("volume", (String) ar.get(0).volume);
        request.setAttribute("included", (String) ar.get(0).included);
        request.setAttribute("flag", dao.isInsert(vehiId).equals("OK") ? "update" : "insert");
        request.setAttribute("extVe", "false");
    }
}

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
        String flag = FZUtil.getHttpParam(request, "flag");        
        
        VehicleAttrDB dao = new VehicleAttrDB();        
        
        if(flag.equalsIgnoreCase("insert")){
            String branchId = FZUtil.getHttpParam(request, "branchId");
            List<Vehicle> st = dao.getDriver(branchId, "");
            
            request.setAttribute("vehicle_code", "");
            request.setAttribute("branch", branchId);
            request.setAttribute("startLon", "");
            request.setAttribute("startLat", "");
            request.setAttribute("endLon", "");
            request.setAttribute("endLat", "");
            request.setAttribute("startTime", "");
            request.setAttribute("endTime", "");
            request.setAttribute("source1", "");
            request.setAttribute("vehicle_type", "");
            request.setAttribute("weight", "");
            request.setAttribute("volume", "");
            request.setAttribute("included", "");
            request.setAttribute("costPerM", "");
            request.setAttribute("fixedCost", "");
            request.setAttribute("Channel", "");
            request.setAttribute("ListDriver", st);
            //System.out.println(ar.get(0).IdDriver.length());
            request.setAttribute("IdDriver", st.get(st.size()-1).IdDriver);
            request.setAttribute("NamaDriver", st.get(st.size()-1).NamaDriver);
            request.setAttribute("agent_priority", "0");
            request.setAttribute("flag", "insert");
            request.setAttribute("extVe", "false");
        }else{
            String vehiId = FZUtil.getHttpParam(request, "vehiId");        
            
            List<Vehicle> ar = dao.getVehi(vehiId);

            List<Vehicle> st = dao.getDriver(ar.get(0).branch, "");

            request.setAttribute("vehicle_code", ar.get(0).vehicle_code);
            request.setAttribute("branch", ar.get(0).branch);
            request.setAttribute("startLon", ar.get(0).startLon);
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
            request.setAttribute("costPerM", (String) ar.get(0).costPerM);
            request.setAttribute("fixedCost", (String) ar.get(0).fixedCost);
            request.setAttribute("Channel", (String) ar.get(0).Channel);
            request.setAttribute("ListDriver", st);
            //System.out.println(ar.get(0).IdDriver.length());
            request.setAttribute("IdDriver", ar.get(0).IdDriver.length() > 0 ? ar.get(0).IdDriver : st.get(st.size()-1).IdDriver);
            request.setAttribute("NamaDriver", ar.get(0).IdDriver.length() > 0 ? ar.get(0).NamaDriver : st.get(st.size()-1).NamaDriver);
            request.setAttribute("agent_priority", ar.get(0).agent_priority);
            request.setAttribute("flag", dao.isInsert(vehiId).equals("OK") ? "update" : "insert");
            request.setAttribute("extVe", "false");
        }
        
    }
}

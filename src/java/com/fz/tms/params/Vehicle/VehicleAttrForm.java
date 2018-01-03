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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author dwi.rangga
 */
public class VehicleAttrForm  implements BusinessLogic {
    @Override
    public void run(
            HttpServletRequest request
            , HttpServletResponse response
            , PageContext pc
    ) throws Exception {
        Vehicle c = new Vehicle();
        c.setVehicle_code(FZUtil.getHttpParam(request, "vehicle_code"));
        c.setBranch(FZUtil.getHttpParam(request, "branch"));
        c.setStartLon(FZUtil.getHttpParam(request, "startLon"));
        c.setStartLat(FZUtil.getHttpParam(request, "startLat"));
        c.setEndLon(FZUtil.getHttpParam(request, "endLon"));
        c.setEndLat(FZUtil.getHttpParam(request, "endLat"));
        c.setStartTime(FZUtil.getHttpParam(request, "startTime"));
        c.setEndTime(FZUtil.getHttpParam(request, "endTime"));
        c.setSource1(FZUtil.getHttpParam(request, "source1"));
        c.setVehicle_type(FZUtil.getHttpParam(request, "vehicle_type"));
        c.setWeight(FZUtil.getHttpParam(request, "weight"));
        c.setVolume(FZUtil.getHttpParam(request, "volume"));
        String flag = FZUtil.getHttpParam(request, "flag");
        
        String rs = "";
        VehicleAttrDB db = new VehicleAttrDB();
        
        rs = cek(c);
        if(rs.equalsIgnoreCase("OK")){
            rs = db.insert(c, flag);
        
            if(rs.equalsIgnoreCase("OK")){
                request.getRequestDispatcher("../jspClaose.jsp")
                    .forward(pc.getRequest(), pc.getResponse());
                /*request.getRequestDispatcher("VehicleAttr.jsp")
                        .forward(pc.getRequest(), pc.getResponse());*/
            }
        }
        
        if(!rs.equalsIgnoreCase("OK")){
            request.setAttribute("errMsg"
                    , "Input attribut Vehicle Error");
            request.getRequestDispatcher("VehicleAttrView.jsp")
                    .forward(pc.getRequest(), pc.getResponse());
        }   
    }
    
    public String cek(Vehicle c){
        String str = "OK";
        
        if(c.getVehicle_code().length() == 0 || c.getBranch().length() == 0 || c.getStartLon().length() == 0 || 
                c.getStartLat().length() == 0 || c.getEndLon().length() == 0 || c.getEndLat().length() == 0 || 
                c.getStartTime().length() == 0 || c.getEndTime().length() == 0 || c.getSource1().length() == 0 
                //|| c.getVehicle_type().length() == 0 || c.getWeight().length() == 0 || c.getVolume().length() == 0
                ){
            str = "Lengkapi parameter";
        }
        
        return str;
    }
}

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
public class ParamVehicleView implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response
            , PageContext pc
    ) throws Exception {
       VehicleAttrDB dao = new VehicleAttrDB();
       String branchId = FZUtil.getHttpParam(request, "branchId");
       
       String sr = dao.isVehicle(branchId);
       //String ex = veID.equalsIgnoreCase("NOPOL_EKSTERNAL") ? "false" : "true";
       
       if(sr.equalsIgnoreCase("OK")){
            List<Vehicle> ar = dao.getVehicle(branchId);
            request.getSession().setAttribute("extVe", "true");
            if(ar.size() > 0){        
                request.setAttribute("listVehicle"
                        , ar);
                /*request.setAttribute("flag"
                        , "update");
                 request.setAttribute("flag"
                        , "insert");*/
            }

            //request.getRequestDispatcher("VehicleAttrView.jsp")
                    //.forward(pc.getRequest(), pc.getResponse());
       }else{
           request.setAttribute("errMsg"
                   , "Invalid Vehicle code");
           request.getRequestDispatcher("VehicleAttr.jsp")
                    .forward(pc.getRequest(), pc.getResponse());
       }
       
       
    }
    
}

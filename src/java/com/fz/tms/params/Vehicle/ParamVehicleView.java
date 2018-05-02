/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params.Vehicle;

import com.fz.generic.BusinessLogic;
import com.fz.tms.params.model.Branch;
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
        
        VehicleAttrDB lb = new VehicleAttrDB();
        String br = (String) pc.getSession().getAttribute("WorkplaceID");
        VehicleAttrDB dao = new VehicleAttrDB();
        String branchId = FZUtil.getHttpParam(request, "branchId");
        String flag = FZUtil.getHttpParam(request, "flag");
        
        try{
            List<Vehicle> ar = dao.getVehicle(branchId);
            if(flag.equalsIgnoreCase("view")){
                String sr = dao.isVehicle(branchId);
                if(sr.equalsIgnoreCase("OK")){
                    List<Branch> lBr = lb.getBranch(br);  
                    if(ar.size() > 0){        
                        List<Vehicle> st = lb.getDriver(branchId, "");
                        
                        request.setAttribute("flag", flag);
                        request.setAttribute("ListDriver", st);
                        request.setAttribute("listVehicle"
                                , ar);
                        //request.getSession().setAttribute("extVe", "false");
                        request.setAttribute("ListBranch", lBr);   
                    }

                    //request.getRequestDispatcher("VehicleAttrView.jsp")
                            //.forward(pc.getRequest(), pc.getResponse());
                }
            }else if(flag.equalsIgnoreCase("insert")){
                List<Branch> lBr = lb.getBranch(br);  
                if(ar.size() > 0){    
                    List<Vehicle> st = lb.getDriver(branchId, "");
                    
                    request.setAttribute("flag", flag);
                    request.setAttribute("ListDriver", st);
                    //request.getSession().setAttribute("extVe", "true");
                    request.setAttribute("branch", branchId);
                    request.setAttribute("ListBranch", lBr);   
                    request.setAttribute("listVehicle"
                                , ar);
                }
                
            }
        }catch(Exception e){
            request.setAttribute("errMsg"
                   , e.getMessage());
            request.getRequestDispatcher("VehicleAttr.jsp")
                    .forward(pc.getRequest(), pc.getResponse());
        }
       
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params.Vehicle;

import com.fz.generic.BusinessLogic;
import com.fz.tms.params.model.Branch;
import com.fz.tms.params.service.VehicleAttrDB;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author dwi.rangga
 */
public class LoadBranchVehi  implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response
            , PageContext pc) throws Exception {
        String br = (String) pc.getSession().getAttribute("WorkplaceID");
        VehicleAttrDB lb = new VehicleAttrDB();
        List<Branch> lBr = lb.getBranch(br);
        request.setAttribute("ListBranch", lBr);
    }
    
}

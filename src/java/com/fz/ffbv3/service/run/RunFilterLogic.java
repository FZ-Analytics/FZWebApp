/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fz.ffbv3.service.run;

import com.fz.ffbv3.service.hvsEstm.HvsEstm;
import com.fz.ffbv3.service.hvsEstm.HvsEstmDAO;
import com.fz.ffbv3.service.vehicle2.Vehicle2DAO;
import com.fz.ffbv3.service.vehicle2.Vehicle2Record;
import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.util.FZUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 */
public class RunFilterLogic implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response
            , PageContext pc) throws Exception {
        
        // get filter http params
        String submit = FZUtil.getHttpParam(request, "submit");
        
        // if add clicked
        if (submit.equals("add")){
            
            // fwd to add form
            request.setAttribute("hvsEstmID", "");
            request
                    .getRequestDispatcher("hvsEstmFrm.jsp")
                    .forward(pc.getRequest(), pc.getResponse());
            // finish
            return;
        }
        // if add clicked
        else if (submit.equals("run")){
        }

        // if reach here, search is clicked
        // get filter criteria
        String harvestDate = FZUtil.getHttpParam(request, "hvsDt").trim();
        String millID = FZUtil.getHttpParam(request, "millID").trim().toUpperCase();
        
        // if not in param
        if (harvestDate.length() == 0){
            
            // try in attribute
            harvestDate = (String) request.getAttribute("hvsDt");
            millID = (String) request.getAttribute("millID");
        }
        
        // handle security
        if (millID.length() > 10) throw new Exception("Mill ID too long");

        // find hvs estm
        HvsEstmDAO heDAO = new HvsEstmDAO();
        List<HvsEstm> heList = heDAO.findByDateAndMill(harvestDate, millID);
        request.setAttribute("hvsEstmList", heList);

        // find vehicle
        Vehicle2DAO vDAO = new Vehicle2DAO();
        List<Vehicle2Record> vList = vDAO.findByMillAndToRun(millID);
        request.setAttribute("vehicleList", vList);
        
        request
            .getRequestDispatcher("runList.jsp")
            .forward(pc.getRequest(), pc.getResponse());
    }

}

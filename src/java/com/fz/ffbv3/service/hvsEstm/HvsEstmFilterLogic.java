/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fz.ffbv3.service.hvsEstm;

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
public class HvsEstmFilterLogic implements BusinessLogic {

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
        String divID = FZUtil.getHttpParam(request, "divID").trim().toUpperCase();
        
        // if not in param
        if (harvestDate.length() == 0){
            
            // try in attribute
            harvestDate = (String) request.getAttribute("hvsDt");
            divID = (String) request.getAttribute("divID");
        }
        
        // handle security
        if (divID.length() > 10) throw new Exception("Division too long");
        
        HvsEstmDAO heDAO = new HvsEstmDAO();
        List<HvsEstm> heList = heDAO.findByDateAndDiv(harvestDate, divID);
        
        if (heList != null){
            // add list object to request attribute
            request.setAttribute("hvsEstmList", heList);
        }
        else {
            
            // put empty list so not null
            request.setAttribute("hvsEstmList", new ArrayList<HvsEstm>());
        }
            
        request
            .getRequestDispatcher("hvsEstmList.jsp")
            .forward(pc.getRequest(), pc.getResponse());
    }

}

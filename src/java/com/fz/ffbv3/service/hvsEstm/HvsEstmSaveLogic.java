/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fz.ffbv3.service.hvsEstm;

import com.fz.ffbv3.service.usermgt.Auth;
import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.generic.ReturnValue;
import com.fz.util.FZUtil;
import com.google.gson.Gson;
import java.sql.Connection;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 */
public class HvsEstmSaveLogic implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response
            , PageContext pc) throws Exception {
        
        String jsonInput = FZUtil.getHttpParam(request, "json").trim();
        
        // create json parser using Gson library from Google
        Gson gson = new Gson();
        
        // parse JSON input into java object
        HvsEstm he = gson.fromJson(jsonInput, HvsEstm.class);
        he.divID = he.divID.trim().toUpperCase();
        he.hvsDate = he.hvsDate.trim();
        
        Auth.check(pc, "Div_" + he.divID + ";Harvest_Estimate_Edit", true);
        
        HvsEstmDAO heDAO = new HvsEstmDAO();
        heDAO.save(he);

        // forward to success page
        // fwd to filter process
//        request.setAttribute("hvsDt", he.hvsDate);
//        request.setAttribute("divID", he.divID);
        request
                .getRequestDispatcher("../appGlobal/success.jsp")
                .forward(pc.getRequest(), pc.getResponse());
    }


}

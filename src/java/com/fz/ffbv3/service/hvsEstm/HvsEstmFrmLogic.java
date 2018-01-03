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
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 */
public class HvsEstmFrmLogic implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response
            , PageContext pc) throws Exception {
        
        String hvsEstmID = FZUtil.getHttpParam(request, "hvsEstmID").trim();
        
        // preset list attrib so not null
//        request.setAttribute("hvsEstm"
//                , new HvsEstm());
        request.setAttribute("hvsEstmDtlList"
                , new ArrayList<HvsEstmDtl>());
               
        // if add
        if (hvsEstmID.length() == 0){
            // nothing
        }
        else { // else update, load fields
            
            try (Connection con = (new Db()).getConnection("jdbc/fz");){
                
                // get hvsEstm from db
                HvsEstmDAO heDAO = new HvsEstmDAO();
                HvsEstm he = heDAO.loadByID(hvsEstmID);
                 
                if (he != null){
                    
                    // keep values in session attribute
                    request.setAttribute("hvsDt"
                            , he.hvsDate);
                    request.setAttribute("divID"
                            , he.divID);
                    request.setAttribute("status"
                            , he.status);
                    request.setAttribute("hvsEstmDtlList"
                            , he.dtl);
                    request.setAttribute("remark"
                            , he.remark);
                }

            }
        }
    }


}

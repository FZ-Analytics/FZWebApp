/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fz.ffbv3.service.order2;

import com.fz.ffbv3.service.usermgt.Auth;
import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.util.FZUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 */
public class Order2CancelLogic implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response
            , PageContext pc) throws Exception {
        
        String oldJobID = FZUtil.getHttpParam(request, "jobID");
        String divID = FZUtil.getHttpParam(request, "divID");
        
        Auth.check(pc, "div_" + divID + ";CancelJob", true);
        
        String sql = "";
        try (Connection con = (new Db()).getConnection("jdbc/fz")){
            
            con.setAutoCommit(false);
            
            sql = 
                "update fz.fbjob "
                    + " set reorderToJobID ='-1'"
                    + " , doneStatus ='CNCL'"
                    + " where jobID = '" + oldJobID + "'"
                ;
            try (PreparedStatement ps = con.prepareStatement(sql)){
                ps.executeUpdate();
            }
            
            con.setAutoCommit(true);
        }
        request.getRequestDispatcher("../appGlobal/success.jsp")
                .forward(request, response);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.order;

import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.util.FZUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Eko
 */
public class OrderSave implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response, PageContext pc) throws Exception {

        String divId = FZUtil.getHttpParam(request, "divId");
        String blokId1 = FZUtil.getHttpParam(request, "blokID1");
        String blokId2 = FZUtil.getHttpParam(request, "blokID2");

        //ResultSet rs = null;
        // construct sql
            String sql = "insert into fbjob(JobSeq, PlanTruckId, DoneStatus, divID, blockId1, blockId2)"
                    + " values(1, 1, 'NEW','" + divId +"', '" + blokId1 + "', '" +blokId2 +"')";
        String d = "";
        int err = 0 ;
            // query insert 
            try (Connection con = (new Db()).getConnection("jdbc/fz")){
                try (Statement stm = con.createStatement()){
                    stm.executeUpdate(sql);
                }
                /*
                try (Statement stm2 = con.createStatement()) {
                    rs = stm2.executeQuery("select * from fbjob order by JobId desc");
                    
                        request.getSession().setAttribute("rs", rs);
                }
                */
            }
        if (err==0)
            request
                .getRequestDispatcher("orderlist.jsp")
                .forward(pc.getRequest(), pc.getResponse());

            /*        
            
        }
        // create output json
        JSONObject result = new JSONObject();
        result.append("result", "OK");
        pc.getOut().println(result.toString());
*/
    }
    
}

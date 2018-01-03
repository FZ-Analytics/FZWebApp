/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.order;

import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author Eko
 */
public class OrderList implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response, PageContext pc) throws Exception {
        String sql = "";
        ResultSet rs = null;
        ArrayList<Order> ol = null;
        
        // get db con from pool
        try (Connection con = (new Db()).getConnection("jdbc/fz")){
            
            try (Statement stm = con.createStatement()){
            
                // create sql
                sql = "select * from fbjob order by JobID desc";

                // query
                rs = stm.executeQuery(sql);
                
                request.setAttribute("rs", rs);
                
                if (rs!=null) {
                    ol = new ArrayList<Order>();
                    while (rs.next()) {
                        Order or = new Order();
                        or.divID = rs.getString("divID");
                        or.blockId1 = rs.getString("blockId1");
                        or.blockId2 = rs.getString("blockId2");
                        //or.estTime = rs.getString("est_time");
                        ol.add(or);
                    }
                    request.setAttribute("res", ol);
                }
            }
        }
    }
    
}

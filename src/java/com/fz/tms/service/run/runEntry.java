/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.service.run;

import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.tms.params.model.Branch;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author dwi.oktaviandi
 */
public class runEntry implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response
            , PageContext pc) throws Exception {
        String br = (String) pc.getSession().getAttribute("WorkplaceID");
        List<Branch> lBr = getBranch(br);
        request.setAttribute("ListBranch", lBr);
    }
    
    public List<Branch> getBranch(String br) throws Exception{
        Branch c = new Branch();
        List<Branch> ar = new ArrayList<Branch>();
        
        String str = "";
        if(br.length() > 0){
            str = "and plant = '" + br + "'";
        }
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){
            
                // create sql
                String sql ;
                sql = "select distinct plant from BOSNET1.dbo.TMS_ShipmentPlan where plant like 'D%' "+str+" order by plant asc";
                
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()){   
                        c = new Branch();
                        c.branchId = rs.getString("plant");
                        ar.add(c);
                    }
                }
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return ar;
    }
    
}

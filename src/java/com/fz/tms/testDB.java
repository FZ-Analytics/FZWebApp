/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms;

import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.router.CustDelivery;
import static com.fz.tms.service.routing.RoutingDB.cekString;
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
 * @author dwi.rangga
 */
public class testDB implements BusinessLogic {
    String test = "";
    @Override
    public void run(
            HttpServletRequest request
            , HttpServletResponse response
            , PageContext pc
    ) throws Exception {
        //getTest110Customer(request);
        //getTest16Customer(request);
        //request.setAttribute("connectResult", test);
    }
    public void getTest110Customer(HttpServletRequest request) throws Exception{
        String sql = "select count(*) from bosnet1.dbo.customer ct;";
        
        CustDelivery st = new CustDelivery();
        
        try (Connection con = (new Db()).getConnection("sap/tms")){            
            try (Statement stm = con.createStatement()){
                try (ResultSet rs = stm.executeQuery(sql)){ 
                    if (rs.next()){
                        test += "110CustomerOK";
                    }
                    con.close();
                }catch (Exception e){
                    test += "110CustomerERROR;";
                }
            }
        }
        catch (Exception e){            
            // re-throw the exception
            test += "110CustomerERROR;";
        }
    }
    
    public void getTest16Customer(HttpServletRequest request) throws Exception{
        String sql = "select count(*) from bosnet1.dbo.customer ct;";
        
        CustDelivery st = new CustDelivery();
        
        try (Connection con = (new Db()).getConnection("prd/tms")){            
            try (Statement stm = con.createStatement()){
                try (ResultSet rs = stm.executeQuery(sql)){ 
                    if (rs.next()){
                        test += "16CustomerOK";
                    }
                    con.close();
                }catch (Exception e){
                    test += "16CustomerERROR;";
                }
            }
        }
        catch (Exception e){            
            // re-throw the exception
            test += "110CustomerERROR;";
        }
    }
}

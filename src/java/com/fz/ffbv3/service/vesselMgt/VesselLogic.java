/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fz.ffbv3.service.vesselMgt;

import com.fz.ffbv3.service.usermgt.*;
import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.util.FZUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;

/**
 *
 */
public class VesselLogic implements BusinessLogic
{    
  private final Logger logger = Logger.getLogger(this.getClass().getPackage().getName());

	@Override
    public void run(
            HttpServletRequest request
            , HttpServletResponse response
            , PageContext pc
    )
		throws Exception
		{
			Client client = ClientBuilder.newClient();
			String strRespon = client.target("http://services.marinetraffic.com/api/exportvesseltrack/a875f50c70d1a0b0409510777898cf90f9fae2b4/days:120/mmsi:565156000/protocol:jsono")
				.request(MediaType.APPLICATION_JSON)
				.get(String.class);
			
			logger.severe("[String Respon] -> " + strRespon);

        String result = "";
        
        // get user id and password from http param
        String userID = FZUtil.getHttpParam(request, "userID");
        String password = FZUtil.getHttpParam(request, "password");

        // TODO handle empty login
        
        String sql = "";
        
        // get db con from pool
        try (Connection con = (new Db()).getConnection("jdbc/fz")){
            
            try (Statement stm = con.createStatement()){
            
                // create sql
                sql = "select userName from gbUsr"
                        + " where userID ='" + userID + "'"
                        + " and password ='" + password + "'"
                        // TODO: hash the password in prod
                        //+ " and password ='" + password.hashCode() + "'"
                        ;
                
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    
                    // if record exist
                    if (rs.next()){

                        // keep user profile as session object, for next views
                        request.getSession()
                                .setAttribute("userName", rs.getString(1));
                        request.getSession()
                                .setAttribute("userID", userID);
                        
                        // remove password from request
                        // so not carried all the time
                        request.removeAttribute("password");
                        
                        // go to welcome page
                        request
                                .getRequestDispatcher("../main/main.jsp")
                                .forward(pc.getRequest(), pc.getResponse());
                    }
                    else {

                        // keep login msg to display in login page
                        request
                                .setAttribute("loginResult"
                                        , "Invalid user name or password");
                        
                        // forward to login page to display loginResult
                        request
                                .getRequestDispatcher("login.jsp")
                                .forward(pc.getRequest(), pc.getResponse());
                    }
                }
            }
        }
        catch (Exception e){
            
            // re-throw the exception
            throw new Exception("Error login. SQL = " + sql, e);
        }

    }
}

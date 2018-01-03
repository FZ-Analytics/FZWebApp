/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.dashRestan;

import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Eko
 */
public class dashRestanMain implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response, PageContext pc) throws Exception {
        String millID = "<All>"; //request.getAttribute("idMill").toString();
        String estateID = ""; //request.getAttribute("idEstate").toString();
        String divID = ""; //request.getAttribute("idDiv").toString();
        
        JSONObject aProd =  dashRestanDao.listDashRestan(0,millID,estateID,divID);
	        String s = aProd.toString();
		      request.setAttribute("pm",s);
			    aProd = dashRestanDao.listDashRestan(1,millID,estateID,divID);
				  s = aProd.toString();
	       request.setAttribute("pw",s);
		      aProd = dashRestanDao.listDashRestan(3,millID,estateID,divID);
			    s = aProd.toString();
				  request.setAttribute("pd", s);
    }    
}

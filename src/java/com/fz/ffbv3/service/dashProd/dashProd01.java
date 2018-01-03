/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.dashProd;

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
public class dashProd01 implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response, PageContext pc) throws Exception {
        String millID = request.getAttribute("idMill").toString();
        String estateID = request.getAttribute("idEstate").toString();
        String divID = request.getAttribute("idDiv").toString();
        
        JSONObject aProd = dashProdDAO.listDashProd(0,millID,estateID,divID);
        String s = aProd.toString();
        request.setAttribute("pm",s);
        aProd = dashProdDAO.listDashProd(1,millID,estateID,divID);
        s = aProd.toString();
        request.setAttribute("pw",s);
        aProd = dashProdDAO.listDashProd(3,millID,estateID,divID);
        s = aProd.toString();
        request.setAttribute("pd", s);

        aProd = dashProdDAO.listDashRestan(0);
        s = aProd.toString();
        request.setAttribute("r2m",s);
        aProd = dashProdDAO.listDashRestan(1);
        s = aProd.toString();
        request.setAttribute("r2w",s);
        aProd = dashProdDAO.listDashRestan(3);
        s = aProd.toString();
        request.setAttribute("r2d",s);

    }
    
}

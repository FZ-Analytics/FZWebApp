/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.dashProd;

import com.fz.ffbv3.service.division.divisionDAO;
import com.fz.generic.BusinessLogic;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import org.primefaces.json.JSONArray;

/**
 *
 * @author Eko
 */
public class dashProd1Logic implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response, PageContext pc) throws Exception {
        Object o  = request.getParameter("sgrup");
        String sgrup = (o==null)?"0":o.toString();
        if (sgrup==null || sgrup.isEmpty()) sgrup="0";
        String[] agrup = new String[3];
        String sMill = "";
        String sEst = "";
        String sDiv = "";
        agrup[0]="<All>";
        if (sgrup.compareTo("0")>0) {
            sMill = request.getParameter("idMill").toString();
            if (sMill.equals("<All>")) sgrup = "1";
            else {
                JSONArray je = divisionDAO.lstEstate(sMill);
                request.setAttribute("oEst",je);
                if(sgrup.compareTo("1")>0) {
                    sEst = request.getParameter("idEstate").toString();
                    if (sEst.equals("<All>")) sgrup = "2";
                    else {
                        JSONArray jd = divisionDAO.lstDivisions(sMill, sEst);
                        request.setAttribute("oDiv",jd);
                        if(sgrup.compareTo("2")>0) {
                            sDiv = request.getParameter("idDiv").toString();
                            if (sDiv.equals("<All>")) sgrup ="3";
                        } else sgrup="3";
                    }
                } else sgrup="2";
            }
        } else sgrup="1";
        
        String submit = request.getParameter("submit");
        String url = (submit!=null && submit.equals("show")) ?"dashProd1Chart.jsp":"dashProd1Filter.jsp";
        
        request.setAttribute("sgrup",sgrup);
        request.setAttribute("idMill",sMill);
        request.setAttribute("idEstate",sEst);
        request.setAttribute("idDiv", sDiv);
        request.getRequestDispatcher(url)
                .forward(pc.getRequest(),pc.getResponse());
    }
    
}

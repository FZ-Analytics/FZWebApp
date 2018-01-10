/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.dashAngkut;

import com.fz.generic.BusinessLogic;
import com.fz.util.FZUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import com.fz.ffbv3.service.order.OrderDAO;
import org.json.JSONArray;
/**
 *
 * @author Eko
 */
public class dashTrip1Logic implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response, PageContext pc) throws Exception {

         String submit = FZUtil.getHttpParam(request, "submit");
         
         String fromDate = FZUtil.getHttpParam(request, "fromDate");
         String toDate = FZUtil.getHttpParam(request, "toDate");
         JSONArray oTruck = new JSONArray();
         JSONArray oDiv = new JSONArray();
         
         if (!fromDate.isEmpty())
         {
             if (toDate.isEmpty()) toDate = fromDate;
            oTruck = OrderDAO.rdbTrips(fromDate,toDate, 2);
         }
            request.setAttribute("oTruck", oTruck);
    }
    
}

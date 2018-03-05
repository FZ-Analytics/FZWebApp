/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.service.routing;

import com.fz.generic.BusinessLogic;
import com.fz.router.CustDelivery;
import static com.fz.tms.service.routing.RoutingLogic.getDataCS;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author dwi.rangga
 */
public class routingPre implements BusinessLogic {    
    public static String runId = "";
    
    public static boolean isForm = false;
    
    @Override
    public void run(
            HttpServletRequest request
            , HttpServletResponse response
            , PageContext pc
    ) throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        
        request.setAttribute("txtDate", new Date().toString().substring(0, 10));
        request.setAttribute("txtBranch", "0321");
    }
    
}

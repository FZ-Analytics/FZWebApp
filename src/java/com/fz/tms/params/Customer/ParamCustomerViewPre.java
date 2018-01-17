/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params.Customer;

import com.fz.generic.BusinessLogic;
import com.fz.tms.params.model.Customer;
import com.fz.tms.params.service.CustomerAttrDB;
import com.fz.util.FZUtil;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author dwi.rangga
 */
public class ParamCustomerViewPre  implements BusinessLogic {    
    @Override
    public void run(
            HttpServletRequest request
            , HttpServletResponse response
            , PageContext pc
    ) throws Exception {        
        String custID = FZUtil.getHttpParam(request, "custId");
        
        CustomerAttrDB dao = new CustomerAttrDB();
        String rs = dao.isCustomer(custID);
        if(rs.equalsIgnoreCase("OK")){
            List<Customer> ar = dao.getCustomer(custID);
            if(ar.size() > 0){
                request.setAttribute("customer_id", custID);
                request.setAttribute("service_time"
                        , String.valueOf(ar.get(0).service_time));
                request.setAttribute("deliv_start"
                        , ar.get(0).deliv_start);
                request.setAttribute("deliv_end"
                        , ar.get(0).deliv_end);
                request.setAttribute("vehicle_type_list"
                        , ar.get(0).vehicle_type_list);
                request.setAttribute("dayWinStart"
                        , ar.get(0).DayWinStart);
                request.setAttribute("dayWinEnd"
                        , ar.get(0).DayWinEnd);
                request.setAttribute("deliveryDeadline"
                        , ar.get(0).DeliveryDeadline);
                request.setAttribute("flag"
                        , "update");
            }else{
                // keep login msg to display in login page
                request.setAttribute("customer_id"
                        , custID);
                request.setAttribute("service_time"
                        , String.valueOf(5));
                request.setAttribute("deliv_start"
                        , "");
                request.setAttribute("deliv_end"
                        , "");
                request.setAttribute("vehicle_type_list"
                        , "");
                request.setAttribute("flag"
                        , "insert");
            }
        }else{
            request.setAttribute("errMsg"
                    , "Invalid Customer id");
            request.getRequestDispatcher("LoadBranchCust.jsp")
                    .forward(pc.getRequest(), pc.getResponse());
        }
        
        /*request.setAttribute("customer_id", (String) request.getSession().getAttribute("customer_id"));
        request.setAttribute("service_time", (String) request.getSession().getAttribute("service_time"));
        request.setAttribute("deliv_start", (String) request.getSession().getAttribute("deliv_start"));
        request.setAttribute("deliv_end", (String) request.getSession().getAttribute("deliv_end"));
        request.setAttribute("vehicle_type_list", (String) request.getSession().getAttribute("vehicle_type_list"));
        request.setAttribute("flag", (String) request.getSession().getAttribute("flag"));
        System.out.println("com.fz.tms.params.ParamCustomerViewPre.run()"
        + FZUtil.getHttpParam(request, "customer_id").toString());*/
    }
}

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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author dwi.rangga
 */
public class CustomerAttrForm implements BusinessLogic {
    @Override
    public void run(
            HttpServletRequest request
            , HttpServletResponse response
            , PageContext pc
    ) throws Exception {
        Customer c = new Customer();
        c.customer_id = FZUtil.getHttpParam(request, "customer_id");
        c.service_time = Integer.parseInt(FZUtil.getHttpParam(request, "service_time"));
        c.deliv_start = FZUtil.getHttpParam(request, "deliv_start");
        c.deliv_end = FZUtil.getHttpParam(request, "deliv_end");
        c.vehicle_type_list = FZUtil.getHttpParam(request, "vehicle_type_list");
        String flag = FZUtil.getHttpParam(request, "flag");
        
        String rs = "";
        CustomerAttrDB db = new CustomerAttrDB();
        
        rs = cek(c);
                
        if(rs.equalsIgnoreCase("OK")){
            rs = db.insert(c, flag);
            if(rs.equalsIgnoreCase("OK")){
                request.getRequestDispatcher("../jspClaose.jsp")
                    .forward(pc.getRequest(), pc.getResponse());
                //request.getRequestDispatcher("CustomerAttr.jsp")
                        //.forward(pc.getRequest(), pc.getResponse());
            }
        }
        
        if(!rs.equalsIgnoreCase("OK")){
            request.setAttribute("errMsg"
                    , "Input attribut Customer Error");
            request.getRequestDispatcher("CustomerAttrView.jsp")
                    .forward(pc.getRequest(), pc.getResponse());
        }        
    }
    
    public String cek(Customer c){
        String str = "OK";
        
        if(c.customer_id.length() == 0 || c.service_time == 0 || c.deliv_start.length() == 0
                || c.deliv_end.length() == 0 || c.vehicle_type_list.length() == 0){
            str = "Lengkapi parameter";
        }
        return str;
    }
}

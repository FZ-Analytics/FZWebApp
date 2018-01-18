/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params.Customer;

import com.fz.generic.BusinessLogic;
import com.fz.tms.params.model.Branch;
import com.fz.tms.params.model.Customer;
import com.fz.tms.params.service.CustomerAttrDB;
import com.fz.util.FZUtil;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author dwi.rangga
 */
public class LoadBranchCust implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response
            , PageContext pc) throws Exception {
        
        //Branch br = new Branch();
        CustomerAttrDB lb = new CustomerAttrDB();
        //List<Branch> lBr = lb.getBranch();
        String custID = FZUtil.getHttpParam(request, "custID");
        List<Customer> ar = new ArrayList<Customer>();
        ar = lb.getCustomerAll(custID);
        
        if(ar.size() > 0){
            request.setAttribute("ListCustomer", ar);
        }else{
            request.setAttribute("errMsg"
                    , "Invalid Customer id");
            request.getRequestDispatcher("CustomerAttr.jsp")
                    .forward(pc.getRequest(), pc.getResponse());
        }
        
        //request.setAttribute("ListBranch", lBr);
    }
    
}

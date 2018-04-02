/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params.External.Driver;

import com.fz.generic.BusinessLogic;
import com.fz.tms.params.model.ForwadingAgent;
import com.fz.tms.params.service.ForwadingAgentDB;
import com.fz.util.FZUtil;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author dwi.oktaviandi
 */
public class ForwadingAgentLoad  implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response
            , PageContext pc) throws Exception {
        ForwadingAgentDB fb = new ForwadingAgentDB();
        
        String Service_agent_id = FZUtil.getHttpParam(request, "Service_agent_id");
        List<ForwadingAgent> ar = new ArrayList<ForwadingAgent>();
        ar = fb.loadAll(Service_agent_id);
        
        if(ar.size() > 0){
            request.setAttribute("ListForwadingAgent", ar);
        }else{
            request.setAttribute("errMsg"
                    , "Invalid Forwading Agent id");
            request.getRequestDispatcher("ForwadingAgentAttr.jsp")
                    .forward(pc.getRequest(), pc.getResponse());
        }
    }
    
}

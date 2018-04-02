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
public class ForwadingAgentView implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response
            , PageContext pc) throws Exception {
        String flag = FZUtil.getHttpParam(request, "flag");
        String agentId = FZUtil.getHttpParam(request, "agentId");
        request.setAttribute("flag", flag);
        
        if(flag.equalsIgnoreCase("update")){
            List<ForwadingAgent> ar = new ArrayList<ForwadingAgent>();
            ForwadingAgentDB fb = new ForwadingAgentDB();
            ar = fb.loadAll(agentId);
            
            if(ar.size() > 0){
                request.setAttribute("Service_agent_id", ar.get(0).Service_agent_id);
                request.setAttribute("Driver_Name", ar.get(0).Driver_Name);
                request.setAttribute("Branch", ar.get(0).Branch);
                request.setAttribute("Status", ar.get(0).Status);
                request.setAttribute("inc", ar.get(0).inc);
                
            }            
        }
        
        
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.progress;

import com.fz.generic.BusinessLogic;
import static com.fz.tms.service.routing.RoutingLogic.runId;
import com.fz.util.FZUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author dwi.rangga
 */
public class progressViewpost  implements BusinessLogic {    
    public static String runId = "";
    @Override
    public void run(
            HttpServletRequest request
            , HttpServletResponse response
            , PageContext pc
    ) throws Exception {
        runId = FZUtil.getHttpParam(request, "runId").toString();//20170924_141034658
        request.getSession().setAttribute("runId", runId);
        request.getRequestDispatcher("../progress/progress.jsp").forward(pc.getRequest(), pc.getResponse());
    }
    
}

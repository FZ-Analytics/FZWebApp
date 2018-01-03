/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fz.ffbv3.service.run;

import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.util.FZUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 */
public class FB2ProgressListing implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response
            , PageContext pc) throws Exception {
        
        String runID = FZUtil.getHttpParam(request, "runID");
        if (runID.length() == 0){
            
            // try attrib
            runID = (String) request.getAttribute("runID");
            runID = (runID == null ? "" : runID);
        }
        
        List<FB2ProgressRecord> prs = new ArrayList<FB2ProgressRecord>();
        // keep in attrib
        request.setAttribute("ProgressList", prs);
        
        StringBuffer sql = new StringBuffer(
                "select hvsDt, divIDs, status, runID, msg"
                + " from fbSchedRun ")
                ;
        
        if (runID.length() > 0){
            sql.append(" where runID = ? ");
        }
        else {
            sql.append(" where lastUpd > ? ");
        }
        sql.append(" order by hvsDt, divIDs, runID desc");
        String sql2 = sql.toString();
        
        try (Connection con = (new Db()).getConnection("jdbc/fz");
                PreparedStatement ps = con.prepareStatement(sql2)){
            
            // if by run id, set run id param
            if (runID.length() > 0){
                ps.setString(1, runID);
            }
            // else set date param
            else {
                final Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -1);
                java.sql.Date d = new java.sql.Date(cal.getTime().getTime());
                ps.setDate(1, d);
            }
            // get list
            try (ResultSet rs = ps.executeQuery()){
                
                // populate list
                while (rs.next()) {
                    FB2ProgressRecord pr = new FB2ProgressRecord();
                    pr.hvsDate = FZUtil.getRsString(rs, 1, "");
                    pr.divIDs = FZUtil.getRsString(rs, 2, "");
                    pr.status = FZUtil.getRsString(rs, 3, "");
                    pr.runID = FZUtil.getRsString(rs, 4, "");
                    pr.msg = FZUtil.getRsString(rs, 5, "");
                    prs.add(pr);
                }
                
            }
        }
        
    }

}

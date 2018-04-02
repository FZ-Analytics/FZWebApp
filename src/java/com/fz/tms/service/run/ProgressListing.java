/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fz.tms.service.run;

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
public class ProgressListing implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response
            , PageContext pc) throws Exception {
        
        String runID = FZUtil.getHttpParam(request, "runId");
        String OriRunID = FZUtil.getHttpParam(request, "oriRunID");
        String channel = FZUtil.getHttpParam(request, "channel");
        String dateDeliv = FZUtil.getHttpParam(request, "dateDeliv");
        List<ProgressRecord> prs = new ArrayList<ProgressRecord>();
        
        // keep in attrib
        request.setAttribute("ProgressList", prs);
        request.setAttribute("channel", channel);
        
        StringBuffer sql = new StringBuffer(
                "select branch, shift, status, runID, msg"
                + " from bosnet1.dbo.tms_progress ")
                ;
        
        if (runID.length() > 0){
            sql.append(" where runID = ? ");
        }
        else {
            sql.append(" where lastUpd > ? ");
        }
        sql.append(" order by branch, shift, runID desc");
        String sql2 = sql.toString();
        
        try (Connection con = (new Db()).getConnection("jdbc/fztms");
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
                    ProgressRecord pr = new ProgressRecord();
                    pr.branch = FZUtil.getRsString(rs, 1, "");
                    pr.shift = FZUtil.getRsString(rs, 2, "");
                    pr.status = FZUtil.getRsString(rs, 3, "");
                    pr.runID = FZUtil.getRsString(rs, 4, "");
                    pr.msg = FZUtil.getRsString(rs, 5, "");
                    pr.OriRunID = OriRunID;
                    pr.channel = channel;
                    prs.add(pr);
                }
                
                request.setAttribute("runId", runID);
                request.setAttribute("oriRunID", OriRunID);
                request.setAttribute("dateDeliv", dateDeliv);
            }
        }
    }
}

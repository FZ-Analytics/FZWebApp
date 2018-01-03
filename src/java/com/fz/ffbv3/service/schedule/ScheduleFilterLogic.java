/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fz.ffbv3.service.schedule;

import com.fz.ffbv3.service.hvsEstm.HvsEstm;
import com.fz.ffbv3.service.hvsEstm.HvsEstmDAO;
import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.util.FZUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 */
public class ScheduleFilterLogic implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response
            , PageContext pc) throws Exception {

        String hvsDate = FZUtil.getHttpParam(request, "hvsDt");
        String estaID = FZUtil.getHttpParam(request, "estaID");
        
        List<ScheduleRecord> srs = new ArrayList<ScheduleRecord>();
        request.setAttribute("ScheduleList", srs);
        
        String sql = "select hvsDt"
                + ", divIDs "
                + ", runID"
                + " from fbSol"
                + " where hvsDt = ?"
                + " and divIDs like '%" + estaID + "%'";
        
        try (Connection con = (new Db()).getConnection("jdbc/fz");
                PreparedStatement ps = con.prepareStatement(sql)){
            
            ps.setTimestamp(1, FZUtil.toSQLTimeStamp(hvsDate, "yyyy-MM-dd"));
            
            try (ResultSet rs = ps.executeQuery()){
                
                while (rs.next()){
                    
                    ScheduleRecord sr = new ScheduleRecord();
                    
                    sr.hvsDate = FZUtil.getRsString(rs, 1, "");
                    sr.divIDs = FZUtil.getRsString(rs, 2, "");
                    sr.runID = FZUtil.getRsString(rs, 3, "");
                    
                    srs.add(sr);
                }
            }
        }
        
        request.getRequestDispatcher("../schedule/scheduleList.jsp")
                .forward(request, response);
    }
}

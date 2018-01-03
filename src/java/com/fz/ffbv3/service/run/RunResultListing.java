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
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 */
public class RunResultListing implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response
            , PageContext pc) throws Exception {
        
        String runID = FZUtil.getHttpParam(request, "runID");
        String sql = "select "
                + "\n v.vehicleName "
                + "\n, t.blocks"
                + "\n, t.splyID"
                + "\n, t.size1"
                + "\n, t.startMin"
                + "\n, t.arrvSplyMin"
                + "\n, t.dprtSplyMin"
                + "\n, t.endMin"
                + "\n, t.millName"
                + "\n from fbSol s"
                + "\n   inner join fbTask t"
                + "\n       on s.solTimeID = t.solTimeID"
                + "\n   inner join fbVehicle v"
                + "\n       on v.vehicleID = t.agnID"
                + "\n where"
                + "\n   s.runID = ?"
                + "\n   and s.solType = ?"
                + "\n order by"
                + "\n   t.agnID"
                + "\n   , t.startMin"
                ;
        
        // keep list placeholder in attrib
        List<RunResultRecord> rrs = new ArrayList<RunResultRecord>();
        request.setAttribute("ResultList", rrs);
        
        try (Connection con = (new Db()).getConnection("jdbc/fz");
                PreparedStatement ps = con.prepareStatement(sql)){
            
            ps.setString(1, runID);
            ps.setString(2, "OPTI");
            try (ResultSet rs = ps.executeQuery()){
                
                while (rs.next()){
                    
                    RunResultRecord rr = new RunResultRecord();
                    int i = 1;
                    
                    rr.vehicleID = FZUtil.getRsString(rs, i++, "");
                    rr.blocks = FZUtil.getRsString(rs, i++, "");
                    
                    String supplyID = FZUtil.getRsString(rs, i++, "");
                    String[] divNSeq = supplyID.split("_");
                    rr.divID = divNSeq[0];
                    rr.seq = Integer.parseInt(divNSeq[1]);
                    
                    rr.size = FZUtil.getRsDouble(rs, i++, 0);
                    
                    rr.startTime = FZUtil.toClock(FZUtil.getRsInt(rs, i++, 0));
                    rr.arriveBlockTime = FZUtil.toClock(FZUtil.getRsInt(rs, i++, 0));
                    rr.departBlockTime = FZUtil.toClock(FZUtil.getRsInt(rs, i++, 0));
                    rr.endTime = FZUtil.toClock(FZUtil.getRsInt(rs, i++, 0));
                    
                    rrs.add(rr);
                }
            }
        }        
    }

}

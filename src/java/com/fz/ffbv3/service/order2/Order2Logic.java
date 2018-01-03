/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fz.ffbv3.service.order2;

import com.fz.ffbv3.service.usermgt.Auth;
import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.util.FZUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 */
public class Order2Logic implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response
            , PageContext pc) throws Exception {

//        // if not submit, return
//        String btnGo =  FZUtil.getHttpParam(request, "btnGo");
//        if (!btnGo.equals("btnGo")){
//            return;
//        }
//        
////        // if duplicate jobID, dont save, page should handle
////        String duplicateJobID = FZUtil.getSessionAtr(request, "duplicateJobID");
////        if (duplicateJobID.length() > 0){
////            return;
////        }
////        
//        // get input
//        String hvsDate = (new SimpleDateFormat("yyyy-MM-dd").format(
//                        new java.util.Date()));
//        
//        String divID = FZUtil.getHttpParam(request, "divID").trim().toUpperCase();
//        
//        String block1 = FZUtil.getHttpParam(request, "block1").trim().toUpperCase();
//        
//        String block2 = FZUtil.getHttpParam(request, "block2").trim().toUpperCase();
//        
//        String readyTime = FZUtil.getHttpParam(request, "readyTime").trim();
//        validateClock(readyTime);
//        
//        String userID = (String) request.getSession().getAttribute("userID");
//        if (userID == null) throw new Exception("UserID is null in session");
//        if (userID.trim().length() == 0) throw new Exception("UserID is empty in session");
//        
//        String estmKg = FZUtil.getHttpParam(request, "estmKg");
//
//        String dirLoc = FZUtil.getHttpParam(request, "dirLoc");
//
//        String isLastOrder = FZUtil.getHttpParam(request, "isLastOrder")
//                .trim().toLowerCase();
//        if (!(isLastOrder.equals("yes") ||  isLastOrder.equals("no"))) 
//            throw new Exception("Is last order should be 'yes' or 'no'");
//
//        String remark = FZUtil.getHttpParam(request, "remark");
//        if (remark.trim().length() == 0) 
//            throw new Exception("Bin Position / Remark empty");
//        
//        Auth.check(pc, "Div_" + divID + ";Order_Edit", true);
//        
////        String block2Criteria = "";
////        if (block2.length() > 0){
////            block2Criteria = " and betweenBlock2 = '" + block2 + "'";
////        }
//        
//        try (Connection con = (new Db()).getConnection("jdbc/fz")){
//
//            String sql = "";
//            
//            // check if order anyway clicked
//            boolean orderAnyway = 
//                    FZUtil.getHttpParam(request, "btnOrderAnyway")
//                            .equals("btnOrderAnyway");
//
//            if (!orderAnyway){
//                
//                // check similar job
//                sql = 
//                        "select jobID from fbJob "
//                        + " where "
//                        + " hvsDt = '" + hvsDate + "'"
//                        + " and divID = '" + divID + "'"
//                        + " and betweenBlock1 = '" + block1 + "'"
//                        + " and doneStatus <> 'PLAN'"
//                        + " and reorderToJobID is null"
//                        //+ block2Criteria
//                        ;
//                try (PreparedStatement ps = con.prepareStatement(sql);
//                        ResultSet rs = ps.executeQuery()){
//
//                    if (rs.next()){
//
//                        String jobID = FZUtil.getRsString(rs, 1, "");
//                        request.setAttribute("duplicateJobID", jobID);
//                        return;
//                    }
//                }
//
//            }
//
//            // insert
//            sql = "insert into fbjob("
//                    + "doneStatus"
//                    + ", divID"
//                    + ", betweenBlock1"
//                    + ", betweenBlock2"
//                    + ", readyTime"
//                    + ", EstmKg"
//                    + ", createDt"
//                    + ", hvsDt"
//                    + ", requesterID"
//                    + ", createSource"
//                    + ", runID"
//                    + ", dirLoc"
//                    + ", remark"
//                    + ", isLastOrder"
//                    + ")"
//                    + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
//                    ;
//            try (PreparedStatement ps = con.prepareStatement(sql)){
//
//                // get sequence number of job for a division, and runID
//                StringBuffer retVal = new StringBuffer();
//                getSeqAndRunID(hvsDate, divID, con, retVal);
//                String[] seqAndRunID = retVal.toString().split(";");
//                //int seq = Integer.parseInt(seqAndRunID[0]);
//                String runID = seqAndRunID[0];
//
//                // validate blocks
//                validateDivBlock(divID, block1, con);
////                if (block2.trim().length() > 0)
////                    validateDivBlock(divID, block2, con);
//
//                String todayStr = FZUtil.toDateString(
//                        new java.util.Date(), "yyyy-MM-dd");
//
//                int i=1;
//                ps.setString(i++, "NEW");
//                ps.setString(i++, divID);
//                ps.setString(i++, block1);
//                ps.setString(i++, block2);
//                ps.setString(i++, readyTime);
//                ps.setString(i++, estmKg);
//                ps.setTimestamp(i++, FZUtil.getCurSQLTimeStamp());
//                ps.setTimestamp(i++, FZUtil.toSQLTimeStamp(todayStr,"yyyy-MM-dd"));
//                ps.setString(i++, userID);
//                ps.setString(i++, "ORDR");
//    //            ps.setInt(i++, seq);
//                ps.setString(i++, runID);
//                ps.setString(i++, dirLoc);
//                ps.setString(i++, remark);
//                ps.setString(i++, isLastOrder);
//
//                ps.executeUpdate();
//            }
//        }
//        request.getRequestDispatcher("../order2/order2Ok.jsp")
//                .forward(request, response);
//            
    }

    private void validateDivBlock(String divID, String block1
            , Connection con) throws Exception {
        
        String sql = "select blockID "
                + " from fbBlock b"
                + " where "
                + "     blockID = '" + block1 + "'"
                + "     and divID = '" + divID + "'"
                ;
        
        try (PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()){
            
            if (!rs.next()){
                throw new Exception("Invalid block " 
                        + block1 + " for div " + divID);
            }
            
        }
    }

    private void getSeqAndRunID(String hvsDate, String divID, Connection con
            , StringBuffer retVal) 
            throws Exception {
        String sql = "select max(runID) "
                + " from fbJob"
                + " where hvsDt = '" + hvsDate + "'"
                + "     and divID = '" + divID + "'"
                + "     and reorderToJobID is null"
                ;
//        int seq = 0;
        String runID = "";
        try (PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()){
            rs.next();
//            seq = rs.getInt(1);
//            // if no order yet
//            if (seq == 0){
//                seq = 2;
//            }
//            else {
//                seq += 1;
//            }
            runID = rs.getString(1);
        }
//        retVal.append(seq).append(";").append(runID);
        retVal.append(runID);
    }

    private int validateClock(String readyTime) throws Exception {
        int readyTimeInt = FZUtil.clockToMin(readyTime);
        if ((readyTimeInt < 0) || (readyTimeInt > 24 * 60)){
            throw new Exception("Invalid ready time");
        }
        return readyTimeInt;
    }

}

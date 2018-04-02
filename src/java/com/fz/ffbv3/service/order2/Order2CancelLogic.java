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
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import com.fz.ffbv3.service.order.Order;
import com.fz.ffbv3.service.order.OrderDAO;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.json.JSONObject;

/**
 *
 */
public class Order2CancelLogic implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response
            , PageContext pc) throws Exception {
        
        String oldJobID = FZUtil.getHttpParam(request, "jobID");
        String divID = FZUtil.getHttpParam(request, "divID");
        OrderDAO oDao = new OrderDAO();
        Order o1 = oDao.findByID(oldJobID);

        Auth.check(pc, "div_" + divID + ";CancelJob", true);
        
        String sql = "";
        try (Connection con = (new Db()).getConnection("jdbc/fz")){
            
            con.setAutoCommit(false);
            
            sql = 
                "update fz.fbjob "
                    + " set reorderToJobID ='-1'"
                    + " , doneStatus ='CNCL'"
                    + " where jobID = '" + oldJobID + "'"
                ;
            try (PreparedStatement ps = con.prepareStatement(sql)){
                ps.executeUpdate();
            }
              
            con.setAutoCommit(true);
        }

        try { 
            Date[] dT = oldTasks(oldJobID);
            if (o1!=null && o1.JobSeq==1) { 
                JSONObject o2 = SearchNewOrder(o1.rundID);
                if (!o2.equals(null)) assign(o2,o1.TruckID,dT);
            }
        } catch (Exception e) { 
            String s = e.getMessage();
        }
        
        request.getRequestDispatcher("../appGlobal/success.jsp")
                .forward(request, response);
    }
    
    Date[] oldTasks(String jobID) { 
//        JSONObject o = null;
        Date[] d = new Date[4];
        String sql = "select *,timestampdiff(second,PlanStart,now()) tInt,now() sekarang from fbtask2 where JobID=" 
                    + jobID + " order by TaskSeq";
        try (Connection con = (new Db()).getConnection("jdbc/fz")) {
            try (Statement stm = con.createStatement()) {
                ResultSet rs = stm.executeQuery(sql);
                int taskSeq = 0;
//                o = new JSONObject();
                Calendar cal = Calendar.getInstance();
//                Date start1, end1, start2, end2;
                String s;
                int interval = 0;
                while (rs.next()) { 
                    taskSeq = rs.getInt("TaskSeq");
                    if (taskSeq==1) { 
                        d[0] = rs.getTimestamp("sekarang");
                        d[1] = rs.getTimestamp("PlanEnd");
                        interval = rs.getInt("tInt");
                    } else if (taskSeq==2) { 
                        d[2] = rs.getTimestamp("Planstart");
                        d[3] = rs.getTimestamp("PlanEnd");
                    }
                }
                d[1] = addSecond(d[1],interval);
                d[2] = addSecond(d[2],interval);
                d[3] = addSecond(d[3],interval);
            } catch (Exception e) { 
                String s = e.getMessage();
            }
            
        } catch (Exception e){ 
            String s = e.getMessage();
        }
        return d;
    }
    
    Date addSecond(Date date, int second) { 
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.SECOND, second);
        return c.getTime();
    }
    
    JSONObject SearchNewOrder(String runID) { 
        String jobID = "";
        JSONObject o = null;
        String sql = "select a.*, d.millID\n" +
                     "	from fbjob a\n" +
                     "  left join fbdiv d on a.divID=d.divID \n" +
                     "	where runID='" + runID + "'\n" +
                     "          and a.JobSeq=1 " +
                     "		and a.DoneStatus='NEW'\n" +
                     "		and a.reorderToJobID is null";
        try (Connection con = (new Db()).getConnection("jdbc/fz")){ 
            try (Statement stm = con.createStatement()) {
                ResultSet rs = stm.executeQuery(sql);
                if (rs.next()) {
                    o = new JSONObject();
                    o.put("JobID", rs.getString("JobID"));
                    o.put("betweenBlock1",rs.getString("betweenBlock1"));
                    o.put("betweenBlock2",rs.getString("betweenBlock2"));
                    o.put("runID",rs.getString("runID"));
                    o.put("EstmKg",rs.getFloat("EstmKg"));
                    o.put("millID",rs.getString("millID"));
                    o.put("hvsDt",rs.getString("hvsDt"));
                    jobID = rs.getString("JobID");
                }
            } catch (Exception e) { 
                String s = e.getMessage();
            }
        } catch (Exception e) { 
            String s = e.getMessage();
        }
        return o;
    }
    
    public  void assign(JSONObject o, String vehicleID, Date[] dt) throws Exception {
        try (Connection con = (new Db()).getConnection("jdbc/fz")){
            con.setAutoCommit(false);
            // sql to update job
            String sql = "update fbJob "
                    + "set doneStatus = 'ASGN'"
                    + ", planTruckID = '" + vehicleID + "'"
                    + ", actualTruckID = '" + vehicleID + "'"
                    + ", assignedDt = current_timestamp"
                    + " where jobID = '" + o.getString("JobID") + "'"
                    ;

            try (PreparedStatement ps = con.prepareStatement(sql)){
                ps.executeUpdate();
            }

            // sql to insert task
            sql = "INSERT INTO fbTask2"
                    + "(JobID"
                    + ", From1"
                    + ", To1"
                    + ", PlanStart"
                    + ", PlanEnd"
                    + ", DoneStatus"
                    + ", FromDesc"
                    + ", ToDesc"
                    + ", Tonnage"
                    + ", Blocks"
                    + ", TaskSeq"
                    + ", PhaseType"
                    + ", createDt"
                    + ")"
                    + " values (?,?,?,?,?,?,?,?,?,?,?,?,?)"
                    ;

            try(PreparedStatement ps = con.prepareStatement(sql)){

                // insert 1st task
                int i = 1;
                Date d = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(o.getString("hvsDt"));
                java.sql.Timestamp t = new java.sql.Timestamp(d.getTime());
                ps.setString(i++, o.getString("JobID")); //id
                ps.setString(i++, o.getString("millID")); // fr
                ps.setString(i++, o.getString("betweenBlock1") + " " + o.getString("betweenBlock2")); //to
                ps.setTimestamp(i++, new java.sql.Timestamp(dt[0].getTime()));
//                        FB2SolLogger.toLongTime(o.runInput.hvsDate, o.jobTiming.startTime)); //strt
                ps.setTimestamp(i++, new java.sql.Timestamp(dt[1].getTime()));
//                        FB2SolLogger.toLongTime(o.runInput.hvsDate, o.jobTiming.timeArriveAtDemand)); // end
                ps.setString(i++, "ASGN"); // stat
                ps.setString(i++, o.getString("millID")); // fr
                ps.setString(i++, o.getString("betweenBlock1") + " " + o.getString("betweenBlock2")); //to
                ps.setDouble(i++, o.getDouble("estmKg")); // ton
                ps.setString(i++, o.getString("betweenBlock1") + ";" + o.getString("betweenBlock2")); // blk
                ps.setInt(i++, 1); // seq
                ps.setString(i++, "ACTL"); // phs type
                ps.setTimestamp(i++, FZUtil.getCurSQLTimeStamp()); // crt dt
                ps.executeUpdate();

                // insert nd task
                i = 1;
                ps.setString(i++, o.getString("JobID")); //id
                ps.setString(i++, o.getString("betweenBlock1") + " " + o.getString("betweenBlock2")); //fr
                ps.setString(i++, o.getString("millID")); // to
                ps.setTimestamp(i++, new java.sql.Timestamp(dt[2].getTime()));
//                        FB2SolLogger.toLongTime(o.runInput.hvsDate, o.jobTiming.timeDepartToMill)); //strt
                ps.setTimestamp(i++, new java.sql.Timestamp(dt[3].getTime()));
//                        FB2SolLogger.toLongTime(o.runInput.hvsDate, o.jobTiming.endTime)); // end
                ps.setString(i++, "ASGN"); // stat
                ps.setString(i++, o.getString("betweenBlock1") + " " + o.getString("betweenBlock2")); //fr
                ps.setString(i++, o.getString("millID")); // to
                ps.setDouble(i++, o.getDouble("EstmKg")); // ton
                ps.setString(i++, o.getString("betweenBlock1") + ";" + o.getString("betweenBlock2")); // blk
                ps.setInt(i++, 2); // seq
                ps.setString(i++, "ACTL"); // phs type
                ps.setTimestamp(i++, FZUtil.getCurSQLTimeStamp()); // crt dt
                ps.executeUpdate();
            }

            // sql to update vehicle
            sql = "update fbVehicle "
                    + "set status = 'ASGN'"
                    + " where vehicleID = '" + vehicleID + "'"
                    ;

            try (PreparedStatement ps = con.prepareStatement(sql)){
                ps.executeUpdate();
            }

            // sql to update last order count
            sql = "update fbSchedRun "
                    + "set lastOdrCnt = "
                        + " case when lastOdrCnt > 0 then lastOdrCnt - 1 "
                        + " else lastOdrCnt end"
                    + " where runID = '" + o.getString("runID") + "'"
                    ;

            try (PreparedStatement ps = con.prepareStatement(sql)){
                ps.executeUpdate();
            }

            con.setAutoCommit(true);
        } catch (Exception e)  {
            
        } 
    }
}

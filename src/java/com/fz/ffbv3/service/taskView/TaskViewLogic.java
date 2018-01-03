/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.taskView;

import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.util.FZUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author Eko
 */
public class TaskViewLogic implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response, PageContext pc) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        ResultSet rs;
        ArrayList<TaskList> tl = null;
        
        //ResultSet rs = null;
        // construct sql
        String sql = "select a.JobID, a.PlanTruckID, a.DoneStatus JobStatus, v.VehicleName "
                    +", b.TaskID, b.From1, b.To1, b.PlanStart, b.PlanEnd, b.DoneStatus TaskStatus "
                    +", b.takenDt ActualStartTime, b.doneDt ActualEndTime"
                    +" from fbjob a "
                    +" inner join fbtask2 b on a.JobID=b.JobID"
                    +" left join fbvehicle v on a.PlanTruckID=v.VehicleID";
        String d = "";
        int err = 0 ;
            // query insert 
            try (Connection con = (new Db()).getConnection("jdbc/fz")){
                try (Statement stm = con.createStatement()){
                    rs = stm.executeQuery(sql);
                    if (rs!=null) {
                        tl = new ArrayList<TaskList>();
                        while (rs.next()) {
                            TaskList task = new TaskList();
                            task.JobID = rs.getString("JobID");
                            task.VehicleID = rs.getString("PlanTruckID");
                            task.VehicleName = rs.getString("VehicleName");
                            task.TaskID = rs.getString("TaskID");
                            task.TaskType = "From " + rs.getString("From1")+" To "+rs.getString("To1");
                            task.From = rs.getString("From1");
                            task.To = rs.getString("To1");
                            task.TargetStartTime = rs.getString("PlanStart");
                            task.TargetEndTime = rs.getString("PlanEnd");
                            //task.ActualStartTime = rs.getString("ActualStartTime");
                            //task.ActualEndTime = rs.getString("ActualEndTime");
                            task.JobStatus = rs.getString("JobStatus");
                            task.TaskStatus = rs.getString("TaskStatus");
                            /*
                            task.OrderEstmTime = rs.getString("");
                            task.AssignedTime = rs.getString("");
                            task.TakenTime = rs.getString("");
                            task.DoneTime = rs.getString("");
                            task.TaskReason = rs.getString("");
*/
                            tl.add(task);
                        }
                        request.setAttribute("res", tl);
                    }
                }
            }
    }
    
}

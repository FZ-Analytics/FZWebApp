/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fz.ffbv3.service.order2;

import com.fz.ffbv3.service.schedule.ScheduleRecord;
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
public class Order2FilterLogic implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response
            , PageContext pc) throws Exception {
        

        String hvsDate = FZUtil.getHttpParam(request, "hvsDt");
        String divID = FZUtil.getHttpParam(request, "divID");
        String runID = FZUtil.getHttpParam(request, "runID");
        
        List<Order2Record> ors = new ArrayList<Order2Record>();
        request.setAttribute("OrderList", ors);
        
        String sortBy = FZUtil.getHttpParam(request, "sortBy");
        request.setAttribute("SortBy", sortBy);

        String where = "";
        if (runID.length() > 0 ){
            where = " j.runID = '" + runID + "'"
                    + " and j.reOrderToJobID is null"
                    ;
        }
        else {
            where = 
                "\n j.hvsDt = '" + hvsDate + "'"
                + "\n   and j.divID like '%" + divID + "%'"
                    + " and j.reOrderToJobID is null"
                ;
        }

        String orderBy = ""
                + "\n       j.PlanTruckID"
                + "\n       , j.jobSeq"
                ;
        if (sortBy.equals("div")){
            orderBy = ""
                + "\n       j.divID"
                + "\n       , j.readyTime"
                    ;
        }
        
        String sql = "select distinct "
                + "\n date_format(j.hvsDt,'%Y-%m-%d')"
                + "\n, v.vehicleName"
                + "\n, j.jobSeq"
                + "\n, j.divID"
                + "\n, j.betweenBlock1"
                + "\n, j.betweenBlock2"
                + "\n, j.DoneStatus"
                + "\n, j.readyTime"
                + "\n, j.estmKg"
                + "\n, date_format(t.planStart,'%H:%i')"
                + "\n, date_format(t2.planEnd,'%H:%i')"
                + "\n, date_format(t.actualStart,'%H:%i')"
                + "\n, date_format(t2.actualEnd,'%H:%i')"
                + "\n, j.jobID"
                + "\n, j.runID"
                + "\n, j.remark"
                + "\n, v.vehicleID"
                + "\n, v.remark vehicleRemark"
                + "\n, t.reasonState"
                + "\n, t2.reasonState"
                + "\n, j.createSource"
                + "\n, case"
                + "\n         when t2.reasonState = 1 then rf.failName"
                + "\n         when t2.reasonState = 2 then rl.lateName"
                + "\n         else '' end reason"
                + "\n, t2.reasonID"
                + "\n, j.isLastOrder"
                + "\n, j.isLast2Order"
                + "\n, date_format(j.assignedDt,'%H:%i:%s')"
                + "\n, date_format(j.takenDt,'%H:%i:%s')"
                + "\n, date_format(j.doneDt,'%H:%i:%s')"
                + "\n, date_format(j.createDt,'%H:%i:%s')"
                + "\n, j.dirLoc"
                + "\n, j.estmFfb"
                + "\n, j.ActualKg"
                + "\n from fbJob j"
                + "\n   left outer join fbTask2 t"
                + "\n       on j.jobId = t.jobID"
                + "\n       and t.taskSeq = 1"
                + "\n   left outer join fbTask2 t2"
                + "\n       on j.jobId = t2.jobID"
                + "\n       and t2.taskSeq = 2"
                + "\n   left outer join fbVehicle v"
                + "\n       on j.ActualTruckID = v.vehicleID"
                + "\n   left outer join fbReasonFail rf"
                + "\n       on t2.reasonID = rf.failID"
                + "\n   left outer join fbReasonLate rl"
                + "\n       on t2.reasonID = rl.lateID"
                + "\n where " + where
                + "\n   order by "
                + "\n " + orderBy
                + "\n       , case "
                + "\n           when j.doneStatus='PLAN' then '' "
                + "\n           else j.doneStatus "
                + "\n         end"
                ;
        
        try (Connection con = (new Db()).getConnection("jdbc/fz");
                PreparedStatement ps = con.prepareStatement(sql)){
            
            //ps.setTimestamp(1, FZUtil.toSQLTimeStamp(hvsDate, "yyyy-MM-dd"));
            
            try (ResultSet rs = ps.executeQuery()){
                
                while (rs.next()){
                    
                    Order2Record or = new Order2Record();
                    
                    int i = 1;
                    or.hvsDate = FZUtil.getRsString(rs, i++, "");
                    or.vehicleName = FZUtil.getRsString(rs, i++, "");
                    or.jobSeq = FZUtil.getRsString(rs, i++, "");
                    or.divID = FZUtil.getRsString(rs, i++, "");
                    or.block1 = FZUtil.getRsString(rs, i++, "");
                    or.block2 = FZUtil.getRsString(rs, i++, "");
                    or.jobStatus = FZUtil.getRsString(rs, i++, "");
                    or.readyTime = FZUtil.getRsString(rs, i++, "");
                    or.size = (int) FZUtil.getRsDouble(rs, i++, 0);
                    or.planStart = FZUtil.getRsString(rs, i++, "");
                    or.planEnd = FZUtil.getRsString(rs, i++, "");
                    or.actualStart = FZUtil.getRsString(rs, i++, "");
                    or.actualEnd = FZUtil.getRsString(rs, i++, "");
                    or.jobID = FZUtil.getRsString(rs, i++, "");
                    or.runID = FZUtil.getRsString(rs, i++, "");
                    or.remark = FZUtil.getRsString(rs, i++, "");
                    or.vehicleID = FZUtil.getRsString(rs, i++, "");
                    or.vehicleRemark = FZUtil.getRsString(rs, i++, "");
                    or.Task1ReasonState = FZUtil.getRsString(rs, i++, "");
                    or.Task2ReasonState = FZUtil.getRsString(rs, i++, "");
                    or.createSource = FZUtil.getRsString(rs, i++, "");
                    or.Task2ReasonName = FZUtil.getRsString(rs, i++, "");
                    or.Task2ReasonID = FZUtil.getRsString(rs, i++, "");
                    or.isLastOrder = FZUtil.getRsString(rs, i++, "");
                    or.isLast2Order = FZUtil.getRsString(rs, i++, "");
                    or.assignedDate = FZUtil.getRsString(rs, i++, "");
                    or.takenDate = FZUtil.getRsString(rs, i++, "");
                    or.doneDate = FZUtil.getRsString(rs, i++, "");
                    or.createDate = FZUtil.getRsString(rs, i++, "");
                    or.dirLoc = FZUtil.getRsString(rs, i++, "");
                    or.estmFfb = FZUtil.getRsString(rs, i++, "");
                    or.ActualKg = (double) FZUtil.getRsDouble(rs, i++, 0);
                    
//                    or.from1 = FZUtil.getRsString(rs, i++, "");
//                    or.to1 = FZUtil.getRsString(rs, i++, "");
//                    or.taskDoneStatus = FZUtil.getRsString(rs, i++, "");
//                    or.vehicleRemark = FZUtil.getRsString(rs, i++, "");
//                    or.taskSeq = FZUtil.getRsString(rs, i++, "");
                    
                    ors.add(or);
                }
            }
        }
        
        request.getRequestDispatcher("../order2/order2List.jsp")
                .forward(request, response);
        
    }

}

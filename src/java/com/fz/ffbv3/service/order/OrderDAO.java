/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.order;

import com.fz.ffbv3.service.hvsEstm.HvsEstm;
import com.fz.ffbv3.service.hvsEstm.HvsEstmDAO;
import com.fz.ffbv3.service.hvsEstm.HvsEstmDtl;
import com.fz.ffbv3.service.task.Task;
import com.fz.ffbv3.service.task.TaskDAO;
import com.fz.generic.Db;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.validation.constraints.AssertTrue.List;

/**
 *
 * @author Eko
 */
public class OrderDAO {
    
    public String Save(Order o) throws Exception {

        String sql = "insert into fbjob(JobSeq, PlanTruckId, DoneStatus, divID, blockId1, blockId2)"
                    + " values(" + o.JobSeq + ", " + o.TruckID + ", '" + o.DoneStatus + "','" 
                    + o.divID + "', '" + o.blockId1 + "', '" +o.blockId2 + "')";
        String d = "";
        Integer i = 0;
        String rslt = "";
        int err = 0 ;
            // query insert 
            try (Connection con = (new Db()).getConnection("jdbc/fz")){
                try (Statement stm = con.createStatement()){
                    i = stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                    ResultSet rs = stm.getGeneratedKeys();
                    if(rs.next()) rslt = rs.getString(1);
                } catch(Exception e) {
                    String s = e.getMessage();
                }
            } catch (Exception e) { 
                String s = e.getMessage();
            }
        return rslt;
    }

    public void FromEstm(String id) throws Exception {
        HvsEstmDAO hDAO = new HvsEstmDAO();
        HvsEstm he = hDAO.loadByID(id);
        

        if (he!=null && he.hvsEstmID!=null) {
            
            HvsEstmDtl d;
            int n = he.dtl.size();
            for(int i = 0; i<n; i++) {
                d = he.dtl.get(i);

            }
//            Order o = new Order();
//            o.divID =  he.divID;
        }
    }
    
    public void SaveJobTask(Order o) {
        try { 
            TaskDAO tDAO = new TaskDAO();
            String i = Save(o);
            if (i!="")
            for(Task t : o.tasks) {
                t.JobID = i; 
                tDAO.Save(t);
            }
        } catch (Exception ex) { 
            String s = ex.getMessage();
        }
    }
    
    public Order findByID(String jobID) { 
        Order o = null;
        String sql = "select * from fbjob where JobID = " + jobID ;
        try (Connection con = (new Db()).getConnection("jdbc/fz");){
            try (Statement stm = con.createStatement()) { 
                ResultSet rs = stm.executeQuery(sql);
                if (rs!=null && rs.next()) {
                    o.JobID = rs.getString("JobID");
                    o.JobSeq = rs.getInt("JobSeq");
                    o.TruckID = rs.getString("ActualTruckID");
                    o.blockId1 = rs.getString("blockId1");
                    o.blockId2 = rs.getString("blockId2");
                    o.DoneStatus = rs.getString("DoneStatus");
                    o.divID = rs.getString("divID");
                }
            } catch(Exception ex) { 
                String s = ex.getMessage();
            }
        } catch (Exception ex) { 
            String s = ex.getMessage();
        }

        return o;
    }
}

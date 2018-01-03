/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.task;

import com.fz.generic.Db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 *
 * @author Eko
 */
public class TaskDAO {

    public boolean Save(Task task) throws Exception {
        boolean result = false;
        
        String sql = "insert into fbtask2 (JobID, From1, To1"
                    +", DoneStatus, Tonnage, Blocks"
                    +") values (" + task.JobID + ", '" + task.From1
                    +"', '" + task.To1 + "', '" + task.DoneStatus 
                    +"', " + task.Tonage +", '" + task.Block + "')";
        try (Connection con = (new Db()).getConnection("jdbc/fz");){

            try (Statement stm = con.createStatement()){
                stm.executeUpdate(sql);
            } 
        }
        return result;
    }
}

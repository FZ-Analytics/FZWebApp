/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params.service;

import com.fz.generic.Db;
import com.fz.tms.params.model.ForwadingAgent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author dwi.oktaviandi
 */
public class ForwadingAgentDB {
    
    public List<ForwadingAgent> loadAll(String str) throws Exception{
        ForwadingAgent c = new ForwadingAgent();
        List<ForwadingAgent> ar = new ArrayList<ForwadingAgent>();
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){
            
                // create sql
                String sql ;
                sql = "SELECT\n" +
                "	Service_agent_id,\n" +
                "	Driver_Name,\n" +
                "	Branch,\n" +
                "	Status,\n" +
                "	inc\n" +
                "FROM\n" +
                "	BOSNET1.dbo.TMS_ForwadingAgent\n" +
                "WHERE\n" +
                "	Service_agent_id like '%"+str+"%'\n" +
                "ORDER BY\n" +
                "	Service_agent_id;";
                
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()){
                        c = new ForwadingAgent();
                        c.Service_agent_id = rs.getString("Service_agent_id");
                        c.Driver_Name = rs.getString("Driver_Name");
                        c.Branch = rs.getString("Branch");
                        c.Status = rs.getString("Status");
                        c.inc = rs.getString("inc");
                        ar.add(c);
                    }
                }
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return ar;
    }
    
        public String insert(ForwadingAgent c, String flag) throws Exception{
        
        String insert = "ERROR";
        // open db connection and 1 statement to insert header
        String sql = "";
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        c.dates = dateFormat.format(date);
        if(flag.equalsIgnoreCase("insert")){
            sql = "INSERT INTO bosnet1.dbo.TMS_ForwadingAgent "
                + "(Service_agent_id, Driver_Name, Branch, Status, dates, inc) "
                + " values(?,?,?,?,?,?);";            
        }else if(flag.equalsIgnoreCase("update")){
            sql = "update bosnet1.dbo.TMS_ForwadingAgent "
                + " set Driver_Name = ?, Branch = ?, Status = ?, dates = ?, inc = ?"
                + " where Service_agent_id = ?;";
        }
        
        try (
            Connection con = (new Db()).getConnection("jdbc/fztms");
            PreparedStatement psHdr = con.prepareStatement(sql
                    , Statement.RETURN_GENERATED_KEYS);
            )  {

            // start database transaction,
            // insert header to db    
            
            if(flag.equalsIgnoreCase("insert")){
                psHdr.setString(1, c.Service_agent_id);
                psHdr.setString(2, c.Driver_Name);
                psHdr.setString(3, c.Branch);
                psHdr.setString(4, c.Status);
                psHdr.setString(5, c.dates);  
                psHdr.setString(6, c.inc);    
            }else if(flag.equalsIgnoreCase("update")){
                psHdr.setString(1, c.Driver_Name);
                psHdr.setString(2, c.Branch);
                psHdr.setString(3, c.Status);
                psHdr.setString(4, c.dates);
                psHdr.setString(5, c.inc);  
                psHdr.setString(6, c.Service_agent_id);  
            }
            
            
            // transaction needed because we have several sql 
            con.setAutoCommit(false);

            psHdr.executeUpdate();
            
            // commit transaction
            con.setAutoCommit(true);
            insert = "OK";
        }
        return insert;
    } 
}

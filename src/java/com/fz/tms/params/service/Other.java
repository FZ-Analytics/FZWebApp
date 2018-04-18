/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params.service;

import com.fz.generic.Db;
import com.fz.tms.params.model.OptionModel;
import com.fz.tms.params.model.history;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Administrator
 */
public class Other {

    public static String insertLog(HashMap<String, String> pl) throws Exception {

        String insert = "ERROR";
        // open db connection and 1 statement to insert header
        String sql = "insert into bosnet1.dbo.TMS_LogError(ID, fileNmethod, datas, msg, dates)"
                + "values(?,?,?,?,?);";

        try (
                Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement psHdr = con.prepareStatement(sql,
                         Statement.RETURN_GENERATED_KEYS);) {

            // start database transaction,
            // insert header to db    
            psHdr.setString(1, pl.get("ID"));
            psHdr.setString(2, pl.get("fileNmethod"));
            psHdr.setString(3, pl.get("datas"));
            psHdr.setString(4, pl.get("msg"));
            psHdr.setString(5, pl.get("dates"));

            // transaction needed because we have several sql 
            con.setAutoCommit(false);

            psHdr.executeUpdate();

            // commit transaction
            con.setAutoCommit(true);
            insert = "OK";
        }
        return insert;
    }
    
    public String InsertHistory(history he) throws Exception {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = new Date();
        String insert = "ERROR";
        // open db connection and 1 statement to insert header
        String sql = "insert into Bosnet1.dbo.TMS_History values(?,?,?);";
        
        String tr = CheckHistory(he);
        if(tr.equalsIgnoreCase("OK")){
            try (
                    Connection con = (new Db()).getConnection("jdbc/fztms");
                    PreparedStatement psHdr = con.prepareStatement(sql,
                             Statement.RETURN_GENERATED_KEYS);) {

                // start database transaction,
                // insert header to db    
                psHdr.setString(1, he.NIK);
                psHdr.setString(2, he.Value);
                psHdr.setString(3, dateFormat.format(date).toString());

                // transaction needed because we have several sql 
                con.setAutoCommit(false);

                psHdr.executeUpdate();

                // commit transaction
                con.setAutoCommit(true);
                insert = "OK";
            }
        }else if(tr.equalsIgnoreCase("ERROR")){
            insert = "OK";
        }
        
        return insert;
    }
    
    public String CheckHistory(history he) throws Exception {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = new Date();
        String insert = "ERROR";
        // open db connection and 1 statement to insert header
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){            
                // create sql
                String sql ;
                sql = "select Pages from Bosnet1.dbo.TMS_History where pages = '"+he.Value+"' AND NIK = '"+he.NIK+"'";
                
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    if (!rs.next()){
                        insert = "OK";
                    }
                }
            }
        }
        return insert;
    }
}

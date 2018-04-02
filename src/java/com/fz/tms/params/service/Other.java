/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params.service;

import com.fz.generic.Db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
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
}

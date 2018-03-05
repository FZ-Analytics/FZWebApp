/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.division;

import com.fz.generic.Db;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Eko
 */
public class divisionDAO {

    /**
     *
     * @param millID
     * @param estateID
     * @return
     */
    public static JSONArray lstDivisions(String millID, String estateID) 
        throws Exception { 
        JSONArray result = null;
        String sql = "select * from fbdiv";
        if (!millID.equals("")) {
            sql = sql + " where millID='" + millID+"'";
            if(!estateID.equals("")) 
                sql = sql + " and estateID='" + estateID + "'";
        }
        try (Connection con = (new Db()).getConnection("jdbc/fz");) {
            try (Statement stm = con.createStatement()) { 
                ResultSet rs = stm.executeQuery(sql);
                if (rs!=null) {
                    result = new JSONArray();
                    while (rs.next()) {
                        JSONObject o = new JSONObject();
                        o.put("millID",rs.getString("millID"));
                        o.put("estateID", rs.getString("estateID"));
                        o.put("divID",rs.getString("divID"));
                        result.put(o);
                    }
                }
            }
        }
        return result;
    }
    
    public static JSONArray lstEstate(String millID) throws Exception { 
        JSONArray result = null;
        String sql = "select distinct millID, EstateID from fbdiv";
        if (!millID.equals("")) sql = sql + " where millID='" + millID + "'";
        try (Connection con = (new Db()).getConnection("jdbc/fz");) { 
            try (Statement stm = con.createStatement()){ 
                ResultSet rs = stm.executeQuery(sql);
                if (rs!=null) {
                    result = new JSONArray();
                    while (rs.next()) {
                        JSONObject o = new JSONObject();
                        o.put("millID",rs.getString("millID"));
                        o.put("estateID", rs.getString("estateID"));
                        result.put(o);
                    }
                }
            }
        }
        return result;
    }
    
    public static JSONArray lstMill() throws Exception {
        JSONArray result = null;
        String sql = "select distinct millID from fbdiv";
        try (Connection con = (new Db()).getConnection("jdbc/fz");) {
            try (Statement stm = con.createStatement()) {
                ResultSet rs = stm.executeQuery(sql);
                if (rs!=null) {
                    result = new JSONArray();
                    while (rs.next()) {
                        JSONObject o = new JSONObject();
                        o.put("millID",rs.getString("millID"));
                        result.put(o);
                    }
                }
            }
        }
        return result;
    }
}

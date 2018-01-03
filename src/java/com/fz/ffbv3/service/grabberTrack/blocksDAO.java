/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.blocks;

import com.fz.generic.Db;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import org.json.JSONObject;

/**
 *
 * @author Eko
 */
public class blocksDAO {
    public String blockID = "";
    public double X1;
    public double Y1;
    public double X2;
    public double Y2;
    
    public static JSONObject getByCode(String blockID) {
        JSONObject result = null;
        
        String sql = "select * from fbblock where blockID='" +blockID+"'";

        try (Connection con = (new Db()).getConnection("jdbc/fz");) {
            try (Statement stm = con.createStatement()) { 
                ResultSet rs = stm.executeQuery(sql);
                if (rs.next()) {
                    result = new JSONObject();
                    result.put("block",rs.getString("Block"));
                    result.put("x1", rs.getDouble("x1"));
                    result.put("y1", rs.getDouble("y1"));
                    result.put("x2", rs.getDouble("x2"));
                    result.put("y2", rs.getDouble("y2"));
                }
            } catch (Exception e) {
                String err = e.getMessage();
            }
        } catch (Exception e) { 
            String err = e.getMessage();
        }
        return result;
    }

    public static JSONObject getRoadByCode(String blockID) {
        JSONObject result = null;
        
        String sql = "select * from fbroad where blok='" +blockID+"'";

        try (Connection con = (new Db()).getConnection("jdbc/fz");) {
            try (Statement stm = con.createStatement()) { 
                ResultSet rs = stm.executeQuery(sql);
                if (rs.next()) {
                    result = new JSONObject();
                    result.put("blockID",rs.getString("Blok"));
                    result.put("x1", rs.getDouble("Koordinat X_start"));
                    result.put("y1", rs.getDouble("Koordinat Y_start"));
                    result.put("x2", rs.getDouble("Koordinat X_end"));
                    result.put("y2", rs.getDouble("Koordinat Y_end"));
                }
            } catch (Exception e) {
                String err = e.getMessage();
            }
        } catch (Exception e) { 
            String err = e.getMessage();
        }
        return result;
    }
}

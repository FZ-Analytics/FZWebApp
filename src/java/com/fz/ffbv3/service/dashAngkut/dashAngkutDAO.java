/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.dashAngkut;

import com.fz.generic.Db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Eko
 */
public class dashAngkutDAO {
    
    public static JSONObject listAngkut(String divID) { 
        JSONObject result = null;
        String sql = "select DATE_FORMAT(a.Tgl,'%Y%m%d') Tanggal, a.* " + 
                    "from fbtransportsap a where a.divID='" + divID + "'" +
                    "order by a.Trip, Tanggal";
        try (Connection con = (new Db()).getConnection("jdbc/fz")) {
            try(Statement stm = con.createStatement()) { 
            ResultSet rs = stm.executeQuery(sql);
            result = rs2Json(rs);
            } catch (Exception e) { 
                String err = e.getMessage();
            }
        } catch (Exception e) {
            String err = e.getMessage();
        }
        return result;
    }

    static JSONObject rs2Json(ResultSet rs) throws Exception{ 
        JSONObject result = new JSONObject();
        if (rs!=null) {
            JSONObject odtl;
            JSONArray a = new JSONArray();
            JSONArray ds = new JSONArray();
            List<String> pers = new ArrayList<String>();
            List<String> flds = new ArrayList<String>();
            String per;
            String fld;
            Date wkt ;
            long l;
            while (rs.next()) { 
                per = rs.getString("Tanggal");
                fld = rs.getString("Trip");
                wkt = rs.getTime("JamTmbPKS");
                l =  wkt.getHours()*60 + wkt.getMinutes() + wkt.getSeconds()/60;
                // wkt.getTime()/1000; //
                if (!pers.contains(per)) pers.add(per);
                if (!flds.contains(fld)) flds.add(fld);
                odtl = new JSONObject();
                odtl.put("label", rs.getString("Tanggal"));
                odtl.put("value", l);
                odtl.put("grup",rs.getString("Trip"));
                a.put(odtl);
            }
///*
            double[][] dta = new double[flds.size()][pers.size()];

            int nc,nr;
            boolean go;
            for (int i=0;i<a.length();i++){
                odtl = a.getJSONObject(i);
                fld = odtl.getString("grup");
                nc = -1;
                go = true;
                while (go) { 
                    nc++;
                    if (nc>=flds.size()) go = false;
                    else if (fld.equals(flds.get(nc))) go = false;
                }
                if (nc>=0 && nc<flds.size()) {
                    nr = -1;
                    go = true;
                    per = odtl.getString("label");
                    while (go) { 
                        nr++;
                        if (nr>pers.size()) go = false;
                        else if(per.equals(pers.get(nr))) go = false;
                    }
                    if (nr>=0 && nr<pers.size()) dta[nc][nr]=odtl.getDouble("value");
                }
            }
//*/            

            JSONObject o = null;
            for (int i=0;i<flds.size();i++){
                o = new JSONObject();
                o.put("label",flds.get(i));
                o.put("data",dta[i]);
                ds.put(o);
            }
            result.put("labels", pers);
            result.put("datasets", ds);
        }
        return result;
    }
 
}

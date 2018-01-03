/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.dashRestan;

import com.fz.ffbv3.service.dashProd.*;
import com.fz.generic.Db;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Eko
 */
public class dashRestanDao {
    
    public static JSONObject listDashRestan(int typer) {
        return listDashRestan(typer,"","","");
    }
    
    public static JSONObject listDashRestan(int typer, String tMill, String tEst, String tDiv) {
        JSONObject result = null;
        String sper = "";
        String sfld = "";
        String swhr = "";
        
        if (typer==0) sper = "DATE_FORMAT(a.Tanggal,'%Y%m')";
        else if (typer==1) sper = "concat(year(a.Tanggal),lpad(week(a.Tanggal),2,'0'))";
        else if (typer==3) sper = "DATE_FORMAT(a.Tanggal,'%Y%m%d')";
        
        if (typer==3)
				{
					swhr = " where a.Tanggal between '2017-09-01' and '2017-09-30'";
        }

				String sql = "select " + sper + " period, 'Restan' fieldID, a.* from fbrestan a " + swhr + " group by period";
        
        try (Connection con = (new Db()).getConnection("jdbc/fz");) {
            try (Statement stm = con.createStatement()) {
                ResultSet rs = stm.executeQuery(sql);
                result = rs2Json(rs);
            } catch (Exception e) { 
                String s = e.getMessage();
            }
        } catch ( Exception e) { 
            String s = e.getMessage();
        }
        return result;
    }
/*    
    public static JSONObject listMillbyWeek(String tMill) {
        JSONObject result = new JSONObject();
        String sql = "select t.period, t.fieldID, " + 
                     "      sum(t.Size) tonnage " + 
                     "  from ( select " +  
                     "              concat(year(a.Tanggal),lpad(week(a.Tanggal),2,'0')) period, " +
                     "          a.*, b.divID, c.estateID, c.millID fieldID" +
                     "          from wproduction a " +
                     "          left join fbblock b on a.Block=b.blockID " +
                     "          left join fbdiv c on b.divID=c.divID " +
                     "         ) t " +
                     "  group by t.fieldID, t.period" +
                     "  order by t.fieldID, t.period";
        try (Connection con = (new Db()).getConnection("jdbc/fz");) {
            try (Statement stm = con.createStatement()) {
                ResultSet rs = stm.executeQuery(sql);
                result = rs2Json(rs);
            } catch (Exception e) { 
                
            }
        } catch ( Exception e) { 
            
        }
        return result;
    }
*/    
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
            while (rs.next()) { 
                per = rs.getString("period");
                fld = rs.getString("fieldID");
                if (!pers.contains(per)) pers.add(per);
                if (!flds.contains(fld)) flds.add(fld);
                odtl = new JSONObject();
                odtl.put("period", rs.getString("period"));
                odtl.put("tonnage", rs.getInt("Qty"));
                odtl.put("fieldID",rs.getString("fieldID"));
                a.put(odtl);
            }
            
            Collections.sort(pers);
            fld = "";
            JSONObject o = null;
            int iper = 0;
            ArrayList<Integer> tons = new ArrayList<Integer>();
            boolean lnjt;
            
            for (int i = 0; i<a.length(); i++) {
                odtl = a.getJSONObject(i);
                if (!fld.equals(odtl.getString("fieldID"))) {
                    fld = odtl.getString("fieldID");
                    o = new JSONObject();
                    o.put("label", fld);
                    iper = 0;
                    tons = new ArrayList<Integer>();
                }
                per = odtl.getString("period");
                lnjt = (iper<pers.size()); 
                while (lnjt == true) {
                        if (per.equals(pers.get(iper))) {
                            tons.add(odtl.getInt("tonnage"));
                            lnjt = false;
                        } else tons.add(0);
                        iper++;
                        if (iper>=pers.size()) lnjt = false;
                }
                    if (iper>=pers.size()) {
                        lnjt = false;
                        o.put("data",tons);
                        ds.put(o);
                    } 
            }
            result.put("labels", pers);
            result.put("datasets", ds);
        }
        return result;
    }
/*    
    public static JSONObject listMillbyMonth(String tMill) { 
        JSONArray rslt = new JSONArray();
        JSONObject result = new JSONObject();
        JSONArray a = new JSONArray();
        String sql= "select " +
                    "	date_format(a.Tanggal,\"%Y%m\") thbl, a.*," +
                    "	c.estateID, sum(a.Size) Size " +
                    "	from wproduction a " +
                    "	left join fbblock b on a.Block=b.blockID" +
                    "	left join fbdiv c on b.divID=c.divID" +
                    "	group by c.estateID,date_format(a.Tanggal,\"%Y%m\")" +
                    "	order by c.estateID,date_format(a.Tanggal,\"%Y%m\")" ;
        try (Connection con = (new Db()).getConnection("jdbc/fz");) {
            try (Statement stm = con.createStatement()) { 
                ResultSet rs = stm.executeQuery(sql);
                JSONObject odtl = null;
                List<String> l = new ArrayList<String>();
                List<String> e = new ArrayList<String>();
                String thbl;
                String est;
                if (rs!=null) {
                    while (rs.next()) {
                        thbl = rs.getString("thbl");
                        est = rs.getString("estateID");
                        if (!l.contains(thbl)) l.add(thbl);
                        if (!e.contains(est)) e.add(est);
                        odtl = new JSONObject();
                        odtl.put("thbl", rs.getString("thbl"));
                        odtl.put("Size", rs.getInt("Size"));
                        odtl.put("Estate",rs.getString("estateID"));
                        a.put(odtl);
                    }
                }
                odtl = new JSONObject();
                Collections.sort(l);
                odtl.put("labels",l);
                rslt.put(odtl);
                
                JSONObject o = null;
                est = "";
                    int n = l.size();
                int x = a.length();
                int ibln = 0 ;
                boolean lnjt = true;
                ArrayList<Integer> w = null;
                JSONArray a1 = null;
                
                for (int j=0; j<x; j++) {
                    odtl = a.getJSONObject(j);
                    if (!est.equals(odtl.getString("Estate"))) {
                        est = odtl.getString("Estate");
                        ibln = 0 ;
                        a1 = new JSONArray();
                        o = new JSONObject();
                        o.put("label", est);
                        w = new ArrayList<Integer>();
                    }
                    thbl = odtl.getString("thbl");
                    lnjt = (ibln<n);
                    while (lnjt==true) {
                        if (thbl==odtl.getString("thbl")) { 
                            w.add(odtl.getInt("Size"));
                            ibln = ibln + 1;
                            if (ibln>=n) {
                                if (a1!=null) {
                                    o.put("data", w);
                                    a1.put(o);
                                }
                                lnjt = false;
                            }
                            lnjt = false;
                        } else if(ibln<n) {
                            w.add(0);
                            ibln = ibln + 1;
                        }
                    }
                }
        rslt.put(a1);        
        rslt.put(a);
        result.put("labels", l);
        result.put("datasets", a1);
        
                
            }
        } catch (Exception ex) { 
            String s = ex.getMessage();
        }
        //return rslt;
        return result;
    }
*/    
}

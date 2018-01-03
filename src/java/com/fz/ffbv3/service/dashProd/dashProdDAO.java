/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.dashProd;

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
public class dashProdDAO {
    
    public static JSONObject listDashProd(int typer) {
        return listDashProd(typer,"","","");
    }
    
    public static JSONObject listDashProd(int typer, String tMill, String tEst, String tDiv) {
        JSONObject result = null;
        String sper = "";
        String sfld = "";
        String swhr = "";
        
        if (typer==0) sper = "DATE_FORMAT(a.Tanggal,'%Y%m')";
        else if (typer==1) sper = "concat(year(a.Tanggal),lpad(week(a.Tanggal),2,'0'))";
        else if (typer==3) sper = "DATE_FORMAT(a.Tanggal,'%Y%m%d')";
        
        if (tMill==null || tMill.isEmpty() || tMill.equals("<All>")) sfld = "c.millID";
        else {
            swhr = " where c.millID='" + tMill + "'";
            if (tEst==null || tEst.isEmpty() || tEst.equals("<All>")) sfld = "c.estateID";
            else { 
                swhr = swhr + " and c.estateID='" + tEst +"'";
                if (tDiv==null || tDiv.isEmpty() || tDiv.equals("<All>")) sfld = "b.divID";
                else {
                    swhr = swhr + " and b.divID='" + tDiv + "'";
                    sfld = "a.Block";
                }
            }
        }
        if (typer==3) {
            if (swhr.isEmpty()) swhr = " where "; else swhr = swhr + " and ";
            swhr = swhr + "a.Tanggal between '2017-09-01' and '2017-09-30'";
        }
        String sql = "select t.period, t.fieldID, sum(t.Size/1000.00) tonnage \n" + 
                     "  from ( select " + sper + " period, a.*, " + sfld + " fieldID \n" + 
                     "          from fbproduction a \n" +
                     "          left join fbblock b on a.Block=b.blockID \n" +
                     "          left join fbdiv c on b.divID=c.divID \n" +
                     swhr + 
                     "         ) t \n" +
                     "  group by t.fieldID, t.period \n" +
                     "  order by t.fieldID, t.period";
        
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

    public static JSONObject listDashRestan(int typer) {
        JSONObject result = null;
        String sper = "";
        String sfld = "";
        String swhr = "";
        
        if (typer==0) sper = "DATE_FORMAT(a.PostgDate,'%Y%m')";
        else if (typer==1) sper = "concat(year(a.PostgDate),lpad(week(a.PostgDate),2,'0'))";
        else if (typer==3) sper = "DATE_FORMAT(a.PostgDate,'%d')";
        
        if (typer==3) {
            swhr = " where a.PostgDate between '2017-10-01' and '2017-10-31' ";
        }
        String sql = "select " + sper + " period, 'Restan' fieldID, sum(a.Qty) tonnage \n" +
                     "  from fbleftover a \n" + swhr + 
                     "  group by fieldID, period \n" +
                     "  order by fieldID, period";
        
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
                odtl.put("tonnage", rs.getInt("tonnage"));
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
    
}

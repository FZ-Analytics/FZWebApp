/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.grabberTrack;

import com.fz.ffbv3.service.blocks.blocksDAO;
import com.fz.generic.Db;
import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date;
import java.sql.Timestamp;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 *
 * @author Eko
 */
public class GrabberTrackDAO {

    public static JSONArray lstHarvest(String sTgl) { 
        JSONArray result = null;
        String sql = "select * from tmp_panen where Tanggal='"+sTgl+"'";
        //if (!divID.isEmpty()) sql += "and divID='" +divID +"'";

        try (Connection con = (new Db()).getConnection("jdbc/fz");) {
            try (Statement stm = con.createStatement()) { 
                ResultSet rs = stm.executeQuery(sql);
                String blok;
                int kgs = 0 ;
                int no = 0;
                int totalKgs;
                int nod;
                if (rs!=null) {
                    result = new JSONArray();
                    JSONObject o;
                    while (rs.next()){
                        o = new JSONObject();
                        o.put("Tanggal",rs.getString("Tanggal"));
                        o.put("divID", rs.getString("divID"));
                        no++;
                        o.put("Harvester","["+rs.getString("NIP")+"] "+rs.getString("NamaPemanen"));
                        o.put("x",0);
                        o.put("y",0);
                        JSONArray aBlok = new JSONArray();
                        nod=0;
                        totalKgs = 0;
//                        o.put("dtCuont",0);
//                        o.put("totalKgs", 0);
                        for (int i=1;i<=10;i++) {
                            blok = rs.getString("Block"+String.valueOf(i).trim()).replace("-","");
                            kgs = rs.getInt("Brutto"+String.valueOf(i).trim());
                            if (!blok.isEmpty()) {
                                JSONObject oB = blocksDAO.getRoadByCode(blok);
                                if (oB!=null && oB.length()>0) {
                                    nod++;
                                    totalKgs += kgs;
                                    oB.put("Kgs",kgs);
                                    aBlok.put(oB);
                                }
                            }
                        }
                        o.put("dtCount",nod);
                        o.put("totalKgs",totalKgs);
                        o.put("Blocks", aBlok);
                        
                        
                        
                        result.put(o);
                    }
                }
            } catch(Exception e) { 
                String err= e.getMessage();
            }
        } catch (Exception e) { 
            String err = e.getMessage();
        }
        return result;
    }
    
    public static JSONArray lstTrip() {
        JSONArray result = null;
        String sql = "select\n" +
                    "		a.divID,a.BetweenBlock1 Block,cast(a.assignedDt as Date) Tanggal,\n" +
                    "		c.`Koordinat X_start` x1, c.`Koordinat Y_start` y1,\n" +
                    "		c.`Koordinat X_End` x2, c.`Koordinat Y_End` y2\n" +
                    "	from fbjob a \n" +
                    "	left join (select ba.* from fbtask2 ba where ba.TaskSeq=1) b on a.jobid=b.jobid\n" +
                    "	left join (select ca.* from fbroad ca group by ca.Blok) c on a.BetweenBlock1=c.Blok\n" +
                    "	where a.DoneStatus=\"DONE\"\n" +
                    "	order by a.divID,b.ActualEnd";

        try (Connection con = (new Db()).getConnection("jdbc/fz");) {
            try (Statement stm = con.createStatement()) { 
                ResultSet rs = stm.executeQuery(sql);
                if (rs!=null) {
                    result = new JSONArray();
                    JSONObject o;
                    while (rs.next()){
                        o = new JSONObject();
                        o.put("divID", rs.getString("divID"));
                        o.put("block",rs.getString("Block"));
                        o.put("Tanggal",rs.getDate("Tanggal"));
                        o.put("x1", rs.getDouble("x1"));
                        o.put("y1", rs.getDouble("y1"));
                        o.put("x2", rs.getDouble("x2"));
                        o.put("y2", rs.getDouble("y2"));
                        result.put(o);
                    }
                }
            } catch(Exception e) { 
                String err= e.getMessage();
            }
        } catch (Exception e) { 
            String err = e.getMessage();
        }
        return result;
    }
    
    public static boolean saveGPSGrabber(int divID, String blockID, Timestamp time, double lat, double lon){
        boolean result = false;
        String sql = "insert into fbgpsgrabbersim " +
                    "(Latitude, Longitude, EndDate, Name, Division, Status)" +
                    "value " +
                    "(?, ?, ?, ?, ?, 0)"
                ;
        try (Connection con = (new Db()).getConnection("jdbc/fz");) {
            try (PreparedStatement ps = con.prepareStatement(sql)) { 
                ps.setDouble(1, lat);
                ps.setDouble(2, lon);
                ps.setTimestamp(3, time);
                ps.setString(4, blockID);
                ps.setInt(5, divID);
                result = ps.execute();
            } catch (Exception e) { 
                String err = e.getMessage();
            }
        } catch (Exception e) { 
            String err = e.getMessage();
        }
        return result;
    }

    public static boolean saveGPSHarvester(int divID, String blockID, Timestamp time, double lat, double lon){
        boolean result = false;
        String sql = "insert into fbgpshvssim " +
                    "(Latitude, Longitude, EndDate, Name, Division, Status)" +
                    "value " +
                    "(?, ?, ?, ?, ?, 0)"
                ;
        try (Connection con = (new Db()).getConnection("jdbc/fz");) {
            try (PreparedStatement ps = con.prepareStatement(sql)) { 
                ps.setDouble(1, lat);
                ps.setDouble(2, lon);
                ps.setTimestamp(3, time);
                ps.setString(4, blockID);
                ps.setInt(5, divID);
                result = ps.execute();
            } catch (Exception e) { 
                String err = e.getMessage();
            }
        } catch (Exception e) { 
            String err = e.getMessage();
        }
        return result;
    }

    public static boolean save_old(String divID, String blockID, Timestamp time, double lat, double lon){
        boolean result = false;
        String sql = "insert into simGrabberTrack " +
                    "(Latitude, Longitude, Time, divID, blockID)" +
                    "value " +
                    "(?, ?, ?, ?, ?)"
                ;
        try (Connection con = (new Db()).getConnection("jdbc/fz");) {
            try (PreparedStatement ps = con.prepareStatement(sql)) { 
                ps.setDouble(1, lat);
                ps.setDouble(2, lon);
                ps.setTimestamp(3, time);
                ps.setString(4, divID);
                ps.setString(5, blockID);
                result = ps.execute();
            } catch (Exception e) { 
                String err = e.getMessage();
            }
        } catch (Exception e) { 
            String err = e.getMessage();
        }
        return result;
    }
    
    public static JSONObject getHgpsgrab(String divID, String blockID) {
        JSONObject result = null;
        String sql = "select * from tmp_gpsgrabh where blockID='" + blockID + "'";
        if (!divID.isEmpty()) sql += " and divID='" + divID + "'";

        try (Connection con = (new Db()).getConnection("jdbc/fz");) 
        {
            try (Statement stm = con.createStatement()) { 
                ResultSet rs = stm.executeQuery(sql);
                if (rs.next()) {
                    result = new JSONObject();
                    result.put("Tanggal",rs.getString("Tanggal"));
                    result.put("divID",rs.getString("divID"));
                    result.put("blockID",rs.getString("blockID"));
                    result.put("tph",rs.getInt("tph"));
                    
                }
            } catch (Exception e) { 
                String err = e.getMessage();
            }
            
        } catch (Exception e) { 
            String err = e.getMessage();
        }
        return result;
    }
    
    public static int getLastTph(String tanggal, String divID, String blockID) { 
        int result = 0;
        String sql = "";
        int n = 0;
        try { 
            JSONObject ocek = getHgpsgrab(divID,blockID);
            if (ocek==null || ocek.length()==0) {
                sql = "insert into tmp_gpsgrabh (tph, Tanggal, divID, blockID) " +
                      "values (?, ?, ?, ?)";
            } else 
            {
                n = ocek.getInt("tph") + 1;
                sql = "update tmp_gpsgrabh set tph = ? where Tanggal=? and divID=? and blockID=?";
                
            }
        } catch (Exception e) { 
            String err = e.getMessage();
        }
        try (Connection con = (new Db()).getConnection("jdbc/fz");) 
        {
            try (PreparedStatement ps = con.prepareStatement(sql)) 
            {
                ps.setInt(1,n);
                ps.setString(2, tanggal);
                ps.setString(3, divID);
                ps.setString(4, blockID);
                ps.execute();
                result = n ;
            } catch (Exception e) { 
                String err = e.getMessage();
            }
            
        } catch (Exception e) { 
            String err = e.getMessage();
        }
        return result;
    }
    
    public static JSONArray lstTmpGPSTruck() { 
        JSONArray result = null;
        
        String sql = "select * from tmp_gpstruck " +
                "where TIMESTAMPDIFF(minute,TimeStart,TimeEnd)<>0";
        //if (!divID.isEmpty()) sql += "and divID='" +divID +"'";

        try (Connection con = (new Db()).getConnection("jdbc/fz");) {
            try (Statement stm = con.createStatement()) { 
                ResultSet rs = stm.executeQuery(sql);
                JSONObject o;
                if (rs!=null) { 
                    result = new JSONArray();
                    double xMill = 105.477;
                    double yMill = -1.9012;
                    int taskseq;
                    while (rs.next()) {
                        o = new JSONObject();
                        taskseq = rs.getInt("TaskSeq");
                        o.put("divID",rs.getString("divID"));
                        o.put("VehicleID", rs.getInt("VehicleID"));
                        o.put("TaskSeq",rs.getInt("TaskSeq"));
                        o.put("TimeStart", rs.getString("TimeStart"));
                        o.put("TimeEnd",rs.getString("TimeEnd"));
                        if (taskseq==1) { 
                            o.put("X1",xMill);
                            o.put("Y1",yMill);
                            o.put("X2",rs.getDouble("xblok"));
                            o.put("Y2",rs.getDouble("yblok"));
                        } else { 
                            o.put("X1",rs.getDouble("xblok"));
                            o.put("Y1",rs.getDouble("yblok"));
                            o.put("X2",xMill);
                            o.put("Y2",yMill);
                        }
                        result.put(o);
                    }
                }
            } catch (Exception e) {
                String err = e.getMessage();
            }
        } catch (Exception e) { 
            String err = e.getMessage();
        }
                
       return result;
    }
    
    
    public static boolean saveGPSTruck(double lat, double lon, Timestamp time, int VehicleID) {
        boolean result = false;
        String sql = "insert into fbgpstrackersim " +
                    "(Latitude, Longitude, EndDate, UserID, VehicleID, Status)" +
                    "value " +
                    "(?, ?, ?, 1, ?, 1)"
                ;
        try (Connection con = (new Db()).getConnection("jdbc/fz");) {
            try (PreparedStatement ps = con.prepareStatement(sql)) { 
                ps.setDouble(1, lat);
                ps.setDouble(2, lon);
                ps.setTimestamp(3, time);
                ps.setInt(4, VehicleID);
                result = ps.execute();
            } catch (Exception e) { 
                String err = e.getMessage();
            }
        } catch (Exception e) { 
            String err = e.getMessage();
        }
        return result;
    }
}

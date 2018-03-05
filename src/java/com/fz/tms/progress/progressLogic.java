/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.progress;

import com.fz.generic.Db;
import com.fz.tms.model.tmProgress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dwi.rangga
 */
public class progressLogic {
    public static List<tmProgress> getData(String str) throws Exception{
        List<tmProgress> tr = new ArrayList<tmProgress>();
        tmProgress st = new tmProgress();
        String sql = "";
        
        // get db con from pool
        try (Connection con = (new Db()).getConnection("sap/tms")){            
            try (Statement stm = con.createStatement()){
            
                // create sql
                sql = "select * from tmprogress where runId = '"+str+"';";
                
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    while(rs.next()){
                        st = new tmProgress();
                        st.runID = rs.getString("runID");
                        st.status = rs.getString("status");
                        st.mustFinish = rs.getString("mustFinish");
                        st.iter = rs.getInt("iter");
                        st.maxIter = rs.getInt("maxIter");
                        st.subIter = rs.getInt("subIter");
                        st.maxSubIter = rs.getInt("maxSubIter");
                        st.msg = rs.getString("msg");
                        st.pct = rs.getInt("pct");
                        st.lastUpd = rs.getDate("lastUpd");
                        st.created = rs.getDate("created");
                        tr.add(st);
                    }
                }catch(Exception e){
                    throw new Exception("Error parse tmprogress. SQL = " + sql, e);
                }
            }catch(Exception e){
                throw new Exception("Error connect tmprogress. SQL = " + sql, e);
            }   
        }
        catch (Exception e){
            System.out.println("ERROR : "+e.getMessage());
            throw new Exception(e.getMessage());
        }
        return tr;
    } 
    
    public static String updateData(String str) throws Exception, Exception{
        String result = "";
        String sql = "";
        sql = "update tmprogress set mustFinish = 1 where  runId = ?";
        try (Connection con = (new Db()).getConnection("sap/tms")){            
            try (PreparedStatement ps = con.prepareStatement(sql)){
                con.setAutoCommit(false);
                ps.setString(1, str);
                ps.executeUpdate();
                con.setAutoCommit(true);
                result = "OK";
                /*try (ResultSet rs = stm.executeQuery(sql)){
                    
                }catch(Exception e){
                    System.out.println("ERROR : "+e.getMessage());
                    throw new Exception("Error update mustFinish tmprogress. SQL = " + sql, e);
                }*/
            }catch(Exception e){
                System.out.println("ERROR : "+e.getMessage());
                throw new Exception("Error connect tmprogress. SQL = " + sql, e);
            }   
        }
        catch (Exception e){
            System.out.println("ERROR : "+e.getMessage());
            throw new Exception(e.getMessage());
        }   
        return result;
    }
    
}

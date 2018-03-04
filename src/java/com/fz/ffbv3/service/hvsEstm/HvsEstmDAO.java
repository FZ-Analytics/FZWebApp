/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fz.ffbv3.service.hvsEstm;

import com.fz.generic.Db;
import com.fz.util.FZUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

/**
 *
 */
public class HvsEstmDAO {
    
    public HvsEstm loadByID(String hvsEstmID) 
            throws Exception {
        
        HvsEstm he = null;
        
        String sql = 
                "select"
                + " date_format(h.hvsDt, '%Y-%m-%d')"
                + "\n, h.divID"
                + "\n, h.hvsEstmID"
                + "\n, h.status"
                + "\n, m.lon"
                + "\n, m.lat"
                + "\n, h.remark"
                + "\n, (case when h.millID is null or h.millID='' then d.millID else h.millID end) millID"
                + "\n, h.note "
                + "\n from fbHvsEstm h"
                + "\n     left outer join fbDiv d"
                + "\n         on h.divID = d.divID"
                + "\n     left outer join fbMill m"
                + "\n         on d.millID = m.millID"
                + "\n where h.hvsEstmID = ?";

        try (Connection con = (new Db()).getConnection("jdbc/fz");){

            try (PreparedStatement ps = con.prepareStatement(sql)){

                // query hvsEstm
                int iHvsEstmID = Integer.parseInt(hvsEstmID);
                ps.setInt(1, iHvsEstmID);
                he = loadSelfAndChildren(con, ps);
            }
        }
        return he;
    }
    
    public HvsEstm loadByDateAndDiv(String hvsDate, String divID
        , Connection con) 
            throws Exception {
        
        HvsEstm he = null;
        
        String sql =
                "select"
                + "\n date_format(h.hvsDt, '%Y-%m-%d')"
                + "\n, h.divID"
                + "\n, h.hvsEstmID"
                + "\n, h.status"
                + "\n, m.lon"
                + "\n, m.lat"
                + "\n, h.status"
                + "\n from fbHvsEstm h"
                + "\n     left outer join fbDiv d"
                + "\n         on h.divID = d.divID"
                + "\n     left outer join fbMill m"
                + "\n         on d.millID = m.millID"
                + "\n where h.hvsDt = ?"
                + "\n and h.divID = ?"
                ;

        try (PreparedStatement ps = con.prepareStatement(sql)){

            // query hvsEstm
            ps.setString(1, hvsDate);
            ps.setString(2, divID);
            he = loadSelfAndChildren(con, ps);

        }
        return he;
    }
    
    private HvsEstm loadSelfAndChildren(
            Connection con, PreparedStatement psLoader
    ) 
            throws Exception {
        
        HvsEstm he = null;
        try (ResultSet rs = psLoader.executeQuery()){

            // if has record
            if (rs.next()){

                he = new HvsEstm();
                he.hvsDate = FZUtil.toDateString(
                                rs.getDate(1),"yyyy-MM-dd");
                he.divID = rs.getString(2);
                he.hvsEstmID = rs.getString(3);
                he.status = rs.getString(4);
                he.millLoc.x = rs.getString(5);
                he.millLoc.y = rs.getString(6);
                he.remark = rs.getString(7);

                // get the details
                loadChildren(he, con);
            }
            else { // else, no record
                // nothing
            }
        }
        return he;
    }
    
    private void loadChildren(HvsEstm he
            , Connection con
    ) 
            throws Exception {
        
        // prepare query
        String sql = "select d.hvsEstmDtlID"
                + "\n, d.taskType"
                + "\n, d.block"
                + "\n, d.size1"
                + "\n, b.x1"
                + "\n, b.y1"
                + "\n, b.x2"
                + "\n, b.y2"
                + "\n from fbHvsEstmDtl d"
                + "\n   left outer join fbHvsEstm he"
                + "\n       on he.hvsEstmID = d.hvsEstmID"
                + "\n   left outer join fbBlock b"
                + "\n       on d.block = b.blockID"
                + "\n       and he.divID = b.divID"
                + "\n where d.hvsEstmID = ?"
                + "\n order by d.estmSeq";
        
        try (PreparedStatement ps = con.prepareStatement(sql)){
            
            // query
            ps.setInt(1, Integer.parseInt(he.hvsEstmID));
            try(ResultSet rs = ps.executeQuery()){
                
                // for each record
                while (rs.next()){
                    
                    // populate object with database data
                    HvsEstmDtl hed = new HvsEstmDtl();
                    hed.hveEstmDtlID = rs.getString(1);
                    hed.parent = he;
                    hed.taskType = rs.getString(2);
                    hed.block = rs.getString(3);
                    hed.size1 = rs.getDouble(4);
                    
                    hed.loc1.x =  rs.getString(5);
                    hed.loc1.y =  rs.getString(6);
                    
                    hed.loc2.x =  rs.getString(5);
                    hed.loc2.y =  rs.getString(6);
                    
                    // add to list
                    he.dtl.add(hed);
                }
                
            }
        }
    }
    
    public void save(HvsEstm heEntered) throws Exception {
        
        // check duplicate bloc
        validateBlock(heEntered);
        
        try (Connection con = (new Db()).getConnection("jdbc/fz");){
            
            HvsEstm heExist = this.loadByDateAndDiv(heEntered.hvsDate
                    , heEntered.divID, con);

            // if not exist, add
            if (heExist == null){
                add(heEntered);
            }
            else { // else, update

                // if already final, cannot
                if (heExist.status.equals("FNAL")){
                    throw new Exception("Already final, cannot save again.");
                }
                else {
                    // else, not final
                    heEntered.hvsEstmID = heExist.hvsEstmID;
                    update(heEntered);
                }
            }
        }
            
    }
    private void add(HvsEstm he) throws Exception {
        
        // open db connection and 1 statement to insert header
        String sql = "insert into fbHvsEstm(hvsDt, status, divID, createdBy, remark, millID, grabbercondition)"
                + " values(?,?,?,?,?,?,?)";
        try (
            Connection con = (new Db()).getConnection("jdbc/fz");
            PreparedStatement psHdr = con.prepareStatement(sql
                    , Statement.RETURN_GENERATED_KEYS);
            )  {

            // start database transaction,
            // insert header to db
            psHdr.setDate(1, java.sql.Date.valueOf(he.hvsDate));
            psHdr.setString(2, he.status);
            psHdr.setString(3, he.divID);
            psHdr.setString(4, "");
            psHdr.setString(5, he.remark);
            psHdr.setString(6, he.millID);
            psHdr.setString(7, he.grabbercondition);
            
            // transaction needed because we have several sql 
            con.setAutoCommit(false);

            psHdr.executeUpdate();

            // get generated auto increment key from db
            int key = -1;
            try (ResultSet rs = psHdr.getGeneratedKeys()){
                rs.next();
                he.hvsEstmID = rs.getString(1);
            }

            insertNewChildren(he, con);
            checkChildrenBelongToDiv(he, con);
            
            // commit transaction
            con.setAutoCommit(true);

        }
    }

    private void update(HvsEstm he) throws Exception {
        
        // open db connection and 1 statement to insert header
        String sql = "update fbHvsEstm set status = ?"
                + ", remark = ?"
                + " where hvsDt = ?"
                + " and divID = ?"
                ;
        try (
            Connection con = (new Db()).getConnection("jdbc/fz");
            PreparedStatement psHdr = con.prepareStatement(sql);
            )  {

            // insert header to db
            psHdr.setString(1, he.status);
            psHdr.setString(2, he.remark);
            psHdr.setDate(3, java.sql.Date.valueOf(he.hvsDate));
            psHdr.setString(4, he.divID);
            
            // start database transaction,
            // transaction needed because we have several sql 
            con.setAutoCommit(false);

            psHdr.executeUpdate();

            deleteOldChildren(he, con);
            insertNewChildren(he, con);
            checkChildrenBelongToDiv(he, con);

            // commit transaction
            con.setAutoCommit(true);

        }
    }

    private void deleteOldChildren(HvsEstm he, Connection con) 
    throws Exception {
        String sql = "delete from fbHvsEstmDtl"
                + " where hvsEstmID = ?";
        try(PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, Integer.parseInt(he.hvsEstmID));
            ps.executeUpdate();
        }
    }

    private void insertNewChildren(HvsEstm he, Connection con) 
        throws Exception {
        
        // create child sql
        String sql = "insert into fbHvsEstmDtl"
                + "(taskType, block, size1, hvsEstmID, estmSeq)"
                + " values(?,?,?,?,?)";
        try(PreparedStatement ps = con.prepareStatement(sql)){

            // insert each rows
            int seq = 1;
            for (HvsEstmDtl hed : he.dtl){

                // set the values
                ps.clearParameters();
                ps.setString(1, hed.taskType); // type
                ps.setString(2, hed.block); // blocks
                ps.setDouble(3, hed.size1); // kg
                ps.setInt(4, Integer.parseInt(he.hvsEstmID)); // parent
                ps.setInt(5, seq++); // seq

                // add to sql batch
                ps.addBatch();

            }

            // execute sql batch
            ps.clearParameters();
            int[] results = ps.executeBatch();
            System.out.println("");
        }
    }

    public List<HvsEstm> findByDateAndDiv(String harvestDate, String divID) 
            throws Exception {
        
        List<HvsEstm> heList = null;
        
        // construct query
        String divIDCriteria = "";
        if (divID.length() > 0){
            divIDCriteria = " and a.divID like ?";
        }
        String sql = "select date_format(a.hvsDt, '%Y-%m-%d')"
                + "\n, a.status, a.divID"
                + "\n, a.hvsEstmID"
                + "\n, sum(b.size1) kg "
                + "\n, a.millID "
                + "\n, (case a.grabbercondition " 
                + "\n      when 0 then 'Ready' " 
                + "\n      when 1 then 'Break Down' "
                + "        else '?' end) grabberCondition"
                + "\n from fbHvsEstm a"
                + "\n inner join fbHvsEstmDtl b"
                + "\n     on a.hvsEstmID = b.hvsEstmID"
                + "\n where a.hvsDt = ?" 
                + "\n " + divIDCriteria
                + "\n group by a.hvsDt, a.status, a.divID"
                + "\n   , a.hvsEstmID" 
                ;
        
        // prepare sql db con & sql statement
        try (
                Connection con = (new Db()).getConnection("jdbc/fz");
                PreparedStatement ps = con.prepareStatement(sql);
                ){
            
            // set the sql params
            ps.setDate(1, java.sql.Date.valueOf(harvestDate));
            if (divIDCriteria.length() > 0){
                ps.setString(2, divID + "%");
            }
            
            // query
            try (ResultSet rs = ps.executeQuery()){
                
                // prepare list object for the page
                heList = new ArrayList<HvsEstm>();
                
                // for each record
                while(rs.next()){
                    
                    // populate the object
                    HvsEstm he = new HvsEstm();
                    he.hvsDate = rs.getString(1);
                    he.status = rs.getString(2);
                    he.divID = rs.getString(3);
                    he.hvsEstmID = rs.getString(4);
                    he.kg = rs.getDouble(5);
                    he.millID = rs.getString(6);
                    he.grabbercondition = rs.getString(7);
                    // add to list
                    heList.add(he);
                }
                
            }
        }
        return heList;
    }

//    public void loadByDateAndDivList(FB2RunInput runInput, RunContext cx)
//            throws Exception {
//        
//        // create div id list for sql
//        StringBuffer divIDList = new StringBuffer();
//        for (String divID : runInput.divList){
//            
////            if (divIDList.length() > 0)
////                divIDList.append(",");
////            
////            divIDList.append("'" + divID + "'");
////            
//            HvsEstmDAO heDAO = new HvsEstmDAO();
//            HvsEstm he = heDAO.loadByDateAndDiv(runInput.hvsDate, divID
//                    , cx.con);
//            cx.hvsEstms.add(he);
//        }
//        
////        // create the sql
////        String sql = "select "
////                + "\n he.divID"
////                + "\n, hed.block"
////                + "\n, hed.size1"
////                + "\n, hed.taskType"
////                + "\n from hvsEstm he"
////                + "\n   inner join hvsEstmDtl hed"
////                + "\n         on he.hvsEstID = hed.hvsEstmID"
////                + "\n where he.hvsEstmDt = ?"
////                + "\n   and hed.divID in (" + divIDList + ")"
////                + "\n order by "
////                + "\n   he.divID"
////                + "\n   , hed.block"
////                ;
////        
////        // get data
////        try (PreparedStatement ps = cx.con.prepareStatement(sql)){
////            ps.setDate(1, java.sql.Date.valueOf(runInput.hvsDate));
////            try (ResultSet rs = ps.executeQuery()){
////                while (rs.next()){
////                    
////                }
////            }
////        }
//    }

    private void checkChildrenBelongToDiv(HvsEstm he, Connection con)
    throws Exception {
        
        String sql = "select "
                + "\n he.divID"
                + "\n, hed.block"
                + "\n from fbHvsEstm he"
                + "\n   left outer join fbHvsEstmDtl hed"
                + "\n       on he.hvsEstmID = hed.hvsEstmID"
                + "\n   left outer join fbBlock b"
                + "\n       on hed.block = b.blockID"
                + "\n       and he.divID = b.divID"
                + "\n where he.hvsEstmID = ?"
                + "\n   and b.blockID is null" 
                ;
        
        // prepare sql db con & sql statement
        try (
                PreparedStatement ps = con.prepareStatement(sql)
                ){
            
            ps.setString(1, he.hvsEstmID);
            String err = "";
            
            try (ResultSet rs = ps.executeQuery()){
                
                while (rs.next()){
                    
                    String divID = FZUtil.getRsString(rs, 1, "-");
                    String blockID = FZUtil.getRsString(rs, 2, "-");
                    err = "Invalid block " + divID + "-" + blockID;
                    break;
                }
            }
            if (err.length() > 0) {
                throw new Exception(err);
            }
        }
    }

    private void validateBlock(HvsEstm he) throws Exception {
 
        List<String> ss = new ArrayList<String>();
        
        for (HvsEstmDtl hed : he.dtl){
            
            hed.block = hed.block.trim().toUpperCase();
            
            if (ss.contains(hed.block+':'+hed.taskType)){
                
                throw new Exception("Duplicate block " + hed.block);
            }
            else {
                ss.add(hed.block+':'+hed.taskType);
            }
        }
    }

    public List<HvsEstm> findByDateAndMill(String harvestDate
            , String millID) throws Exception {
        
        List<HvsEstm> heList = new ArrayList<HvsEstm>();
        
        // construct query
        String sql = "select date_format(a.hvsDt, '%Y-%m-%d')"
                + "\n, a.status, a.divID"
                + "\n, a.hvsEstmID"
                + "\n, sum(b.size1) kg "
                + "\n, a.MillID "
                + "\n, (case a.grabbercondition "
                + "\n        when '0' then 'Ready' "
                + "\n        when '1' then 'Break Down' "
                + "\n        else '?'"
                + "\n   end) grabbercondition "
                + "\n from fbHvsEstm a"
                + "\n   inner join fbHvsEstmDtl b"
                + "\n     on a.hvsEstmID = b.hvsEstmID"
                + "\n   inner join fbDiv d"
                + "\n     on d.divID = a.divID"
                + "\n where a.hvsDt = ?" 
                + "\n   and d.millID = ?"
                + "\n group by a.hvsDt, a.status, a.divID"
                + "\n   , a.hvsEstmID" 
                ;
        
        // prepare sql db con & sql statement
        try (
                Connection con = (new Db()).getConnection("jdbc/fz");
                PreparedStatement ps = con.prepareStatement(sql);
                ){
            
            // set the sql params
            ps.setDate(1, java.sql.Date.valueOf(harvestDate));
            ps.setString(2, millID);
            
            // query
            try (ResultSet rs = ps.executeQuery()){
                
                // for each record
                while(rs.next()){
                    
                    // populate the object
                    HvsEstm he = new HvsEstm();
                    he.hvsDate = rs.getString(1);
                    he.status = rs.getString(2);
                    he.divID = rs.getString(3);
                    he.hvsEstmID = rs.getString(4);
                    he.kg = rs.getDouble(5);
                    he.millID = rs.getString(6);
                    he.grabbercondition = rs.getString(7);
                    
                    // add to list
                    heList.add(he);
                }
                
            }
        }
        return heList;
    }
}

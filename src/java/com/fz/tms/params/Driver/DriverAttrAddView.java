/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params.Driver;

import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.tms.params.model.Branch;
import com.fz.tms.params.model.ForwadingAgent;
import com.fz.tms.params.service.VehicleAttrDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author dwi.oktaviandi
 */
public class DriverAttrAddView implements BusinessLogic {    
    @Override
    public void run(
            HttpServletRequest request
            , HttpServletResponse response
            , PageContext pc
    ) throws Exception {      
        List<ForwadingAgent> al = getDriver();
        VehicleAttrDB lb = new VehicleAttrDB();
        String br = (String) pc.getSession().getAttribute("WorkplaceID");
        List<Branch> lBr = lb.getBranch(br);
        request.setAttribute("ListBranch", lBr);
        request.setAttribute("ListDriver"
                        , al);
    }
    
    public List<ForwadingAgent> getDriver() throws Exception{
        List<ForwadingAgent> al = new ArrayList<ForwadingAgent>();
        ForwadingAgent ls = new ForwadingAgent();
        
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){            
                // create sql
                String sql = "SELECT\n" +
                "	d.Driver_ID,\n" +
                "	d.Driver_Name,\n" +
                "	case when da.Branch is null then 'D000' else da.Branch end Branch,\n" +
                "	case when da.Inc is null or da.Inc = '0' then '0' else '1' end Inc\n" +
                "FROM\n" +
                "	BOSNET1.dbo.Driver d\n" +
                "	left outer join BOSNET1.dbo.tms_DriverAtr da\n" +
                "	on d.Driver_ID = da.Id\n" +
                "WHERE\n" +
                "	d.Driver_ID LIKE '0008%'\n";
                
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()){
                        ls = new ForwadingAgent();
                        ls.Service_agent_id = rs.getString("Driver_ID");
                        ls.Driver_Name = rs.getString("Driver_Name");
                        ls.Branch = rs.getString("Branch");
                        ls.inc = rs.getString("Inc");
                        
                        al.add(ls);
                    }
                }
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return al;
    }
    
    public String submit(ForwadingAgent fd) throws Exception{
        String str = "ERROR";
        String sql = "insert into BOSNET1.dbo.TMS_DriverAtr values(?,?,?,?)\n";
        try (
            Connection con = (new Db()).getConnection("jdbc/fztms");
            PreparedStatement psHdr = con.prepareStatement(sql
                    , Statement.RETURN_GENERATED_KEYS);
            )  {
                psHdr.setString(1, fd.Service_agent_id);
                psHdr.setString(2, fd.Driver_Name);
                psHdr.setString(3, fd.Branch);
                psHdr.setString(4, fd.inc);
            
                con.setAutoCommit(false);

                psHdr.executeUpdate();

                // commit transaction
                con.setAutoCommit(true);
                str = "OK";
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        
        return str;
    }
    
}

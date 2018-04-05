/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params.service;

import com.fz.generic.Db;
import com.fz.tms.params.model.Branch;
import com.fz.tms.params.model.Customer;
import com.fz.tms.params.model.OptionModel;
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
public class CustomerAttrDB {
    
    public String isCustomer(String str) throws Exception{
        String c = "ERROR";
        
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){            
                // create sql
                String sql ;
                sql = "SELECT * FROM BOSNET1.dbo.Customer where customer_id = '"+str+"';";
                
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    if (rs.next()){
                        c = "OK";
                    }
                }
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return c;
    }
    
    public List<Customer> getCustomer(String str) throws Exception{
        Customer c = new Customer();
        List<Customer> ar = new ArrayList<Customer>();
        
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){
            
                // create sql
                String sql ;
                sql = "SELECT * FROM BOSNET1.dbo.TMS_CustAtr where customer_id = '"+str+"';";
                
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    if (rs.next()){
                        ar = new ArrayList<Customer>();
                        c.customer_id = str;
                        c.service_time = Integer.parseInt(rs.getString("service_time"));
                        c.deliv_start = rs.getString("deliv_start");
                        c.deliv_end = rs.getString("deliv_end");
                        c.vehicle_type_list = rs.getString("vehicle_type_list");
                        c.DayWinStart = rs.getString("DayWinStart");
                        c.DayWinEnd = rs.getString("DayWinEnd");
                        c.DeliveryDeadline = rs.getString("DeliveryDeadline");
                        ar.add(c);
                    }
                }
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return ar;
    }
    
    public Customer getCust(String str) throws Exception{
        Customer c = new Customer();
        List<Customer> ar = new ArrayList<Customer>();
        
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){
            
                // create sql
                String sql ;
                sql = "SELECT * FROM BOSNET1.dbo.TMS_CustAtr where customer_id = '"+str+"';";
                
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    if (rs.next()){
                        ar = new ArrayList<Customer>();
                        c.customer_id = str;
                        c.service_time = Integer.parseInt(rs.getString("service_time"));
                        c.deliv_start = rs.getString("deliv_start");
                        c.deliv_end = rs.getString("deliv_end");
                        c.vehicle_type_list = rs.getString("vehicle_type_list");
                        c.DayWinStart = rs.getString("DayWinStart");
                        c.DayWinEnd = rs.getString("DayWinEnd");
                        c.DeliveryDeadline = rs.getString("DeliveryDeadline");
                        c.flag = "update";
                        ar.add(c);
                    }else{
                        c.customer_id = str;
                        c.flag = "insert";
                    }
                }
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return c;
    }
    
    public String insert(Customer c, String flag) throws Exception{
        
        String insert = "ERROR";
        // open db connection and 1 statement to insert header
        String sql = "";
        
        if(flag.equalsIgnoreCase("insert")){
            sql = "INSERT INTO bosnet1.dbo.TMS_CustAtr "
                + "(customer_id, service_time, deliv_start, deliv_end, vehicle_type_list, DayWinStart, DayWinEnd, DeliveryDeadline) "
                + " values(?,?,?,?,?,?,?,?);";            
        }else if(flag.equalsIgnoreCase("update")){
            sql = "update bosnet1.dbo.TMS_CustAtr "
                + " set service_time = ?, deliv_start = ?, deliv_end = ?, vehicle_type_list = ?, DayWinStart = ?, DayWinEnd = ?, DeliveryDeadline = ? "
                + " where customer_id = ?;";
        }
        
        try (
            Connection con = (new Db()).getConnection("jdbc/fztms");
            PreparedStatement psHdr = con.prepareStatement(sql
                    , Statement.RETURN_GENERATED_KEYS);
            )  {

            // start database transaction,
            // insert header to db    
            
            if(flag.equalsIgnoreCase("insert")){
                psHdr.setString(1, c.customer_id);
                psHdr.setInt(2, c.service_time);
                psHdr.setString(3, c.deliv_start);
                psHdr.setString(4, c.deliv_end);
                psHdr.setString(5, c.vehicle_type_list);  
                psHdr.setString(6, c.DayWinStart);  
                psHdr.setString(7, c.DayWinEnd);  
                psHdr.setString(8, c.DeliveryDeadline);  
            }else if(flag.equalsIgnoreCase("update")){
                psHdr.setInt(1, c.service_time);
                psHdr.setString(2, c.deliv_start);
                psHdr.setString(3, c.deliv_end);
                psHdr.setString(4, c.vehicle_type_list);
                psHdr.setString(5, c.DayWinStart);  
                psHdr.setString(6, c.DayWinEnd);  
                psHdr.setString(7, c.DeliveryDeadline);  
                psHdr.setString(8, c.customer_id);
            }
            
            
            // transaction needed because we have several sql 
            con.setAutoCommit(false);

            psHdr.executeUpdate();
            
            // commit transaction
            con.setAutoCommit(true);
            insert = "OK";
        }
        return insert;
    } 
    
    public List<Branch> getBranch() throws Exception{
        Branch c = new Branch();
        List<Branch> ar = new ArrayList<Branch>();
        
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){
            
                // create sql
                String sql ;
                sql = "SELECT distinct Sales_Office FROM BOSNET1.dbo.Customer order by Sales_Office asc;;";
                
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()){   
                        c = new Branch();
                        c.branchId = rs.getString("Sales_Office");
                        ar.add(c);
                    }
                }
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return ar;
    }
    
    public static List<OptionModel> getCustomerId( String Str) throws Exception{
        OptionModel c = new OptionModel();
        List<OptionModel> ar = new ArrayList<OptionModel>();
        
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){
            
                // create sql
                String sql ;
                sql = "select distinct Customer_ID, Name1 from BOSNET1.dbo.Customer where Sales_Office = '"+Str+"'";
                
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()){   
                        c = new OptionModel();
                        c.Display = rs.getString("Name1");
                        c.Value = rs.getString("Customer_ID");
                        ar.add(c);
                    }
                }
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return ar;
    }
    
    public List<Customer> getCustomerAll(String Str) throws Exception{
        Customer c = new Customer();
        List<Customer> ar = new ArrayList<Customer>();
        try (Connection con = (new Db()).getConnection("jdbc/fztms")){            
            try (Statement stm = con.createStatement()){
            
                // create sql
                String sql ;
                sql = "SELECT\n" +
                "	DISTINCT Customer_ID,\n" +
                "	Name1,\n" +
                "	Street\n" +
                "FROM\n" +
                "	BOSNET1.dbo.Customer\n" +
                "WHERE\n" +
                "	(\n" +
                "		Customer_Order_Block_all IS NULL\n" +
                "		OR Customer_Order_Block = ''\n" +
                "	)\n" +
                "	AND(\n" +
                "		Customer_Order_Block_all IS NULL\n" +
                "		OR Customer_Order_Block_all = ''\n" +
                "	)\n" +
                "	and Customer_ID like '%"+Str+"%'\n" +
                "ORDER BY\n" +
                "	Customer_ID;";
                
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    while (rs.next()){
                        c = new Customer();
                        c.customer_id = rs.getString("Customer_ID");
                        c.Name1 = rs.getString("Name1");
                        c.Street = rs.getString("Street");
                        ar.add(c);
                    }
                }
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return ar;
    }
}

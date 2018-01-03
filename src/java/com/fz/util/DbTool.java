/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fz.util;

import com.fz.util.FZUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 */
public class DbTool {
    
    public static void insert(
            Connection con, String tableName, List<DbField> fields
            , HttpServletRequest request, StringBuffer key
    ) throws Exception {
        
        // create field name list
        String fn = "";
        String fv = "";
        int i = 0;
        for (DbField f : fields){
            
            // will not 1st field, expected as auto increment key 
            if (i++ == 0) continue;
            
            // handle comma
            if (fn.length() > 0){
                fn += ",";
                fv += ",";
            }
            // add field name
            fn += f.name;
            
            // get cur field value
            String val = FZUtil.getHttpParam(request, f.name);
            
            // if text quote, else plain
            if (f.type.equals("text")){
                fv += "'" + val + "'";
            }
            else {
                fv += val;
            }
        }
        String sql = "insert into " + tableName 
                + "(" + fn + ") values(" + fv + ")";
        
        // insert
        try (PreparedStatement stm = con.prepareStatement(sql
                , Statement.RETURN_GENERATED_KEYS)){
            
            stm.execute();
            
            // get resulting key
            try (ResultSet rs = stm.getGeneratedKeys()){
                key.append(rs.getString(1));
            }
        }
        
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params.PopUp;

import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.tms.params.model.OptionModel;
import com.fz.util.FZUtil;
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
public class ShowParams implements BusinessLogic {
    //DecimalFormat df = new DecimalFormat("##.0");
        
    @Override
    public void run(HttpServletRequest request, HttpServletResponse response
            , PageContext pc) throws Exception {
        
        
        String branch = (String) pc.getSession().getAttribute("WorkplaceID");
        List<OptionModel> js = showParam(branch);
        
        
        request.setAttribute("ParamsList", js);
    }
    
    public List<OptionModel> showParam(String branch) throws Exception{
        String str = CopyParam(branch);
        List<OptionModel> js = new ArrayList<OptionModel>();
            
        if(str.equalsIgnoreCase("OK")){
            String sql = "SELECT * FROM BOSNET1.dbo.TMS_Params where branch = '"+branch+"'";
            OptionModel op = new OptionModel();
            try (Connection con = (new Db()).getConnection("jdbc/fztms");
                    PreparedStatement ps = con.prepareStatement(sql)){
                try (ResultSet rs = ps.executeQuery()){
                    while (rs.next()) {
                        op = new OptionModel();
                        int i = 1;
                        op.Display = FZUtil.getRsString(rs, i++, "");
                        op.Value = FZUtil.getRsString(rs, i++, "");
                        op.Desc = FZUtil.getRsString(rs, i++, "");
                        js.add(op);
                    }
                }
            }
        }
        
        return js;
    }
    
    public String CopyParam(String branch) throws Exception{
        String str = "ERROR";
        String sql = "DECLARE @BrId VARCHAR(5)= '"+branch+"';\n" +
                "\n" +
                "DECLARE @N VARCHAR(5);\n" +
                "\n" +
                "SELECT\n" +
                "	@N = COUNT(*)\n" +
                "FROM\n" +
                "	BOSNET1.dbo.TMS_Params\n" +
                "WHERE\n" +
                "	param = 'DefaultMaxCust'\n" +
                "	AND branch = @BrId IF @N = 0 BEGIN INSERT\n" +
                "		INTO\n" +
                "			BOSNET1.dbo.TMS_Params SELECT\n" +
                "				param,\n" +
                "				value,\n" +
                "				Descriptions,\n" +
                "				@BrId\n" +
                "			FROM\n" +
                "				BOSNET1.dbo.TMS_Params\n" +
                "			WHERE\n" +
                "				param = 'DefaultMaxCust'\n" +
                "				AND branch = 'DEF';\n" +
                "END";
        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)) {
            con.setAutoCommit(false);
            ps.executeUpdate();
            con.setAutoCommit(true);
            str = "OK";
        }
        return str;
    }
    
    public static String submit(OptionModel he) throws Exception{
        String str = "ERROR";
        String sql = "update BOSNET1.dbo.TMS_Params set value = '"+he.Value+"' where param = '"+he.Display+"' and branch = '"+he.Branch+"'";
        try (
            Connection con = (new Db()).getConnection("jdbc/fztms");
            PreparedStatement psHdr = con.prepareStatement(sql
                    , Statement.RETURN_GENERATED_KEYS);
            )  {
            con.setAutoCommit(false);

            psHdr.executeUpdate();
            
             con.setAutoCommit(true);
             str = "OK";
        }
        return str;
    }
}

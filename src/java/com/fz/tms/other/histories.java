/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.other;

import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.tms.params.model.history;
import com.fz.util.FZUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author dwi.oktaviandi
 */
public class histories implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response,
            PageContext pc
    ) throws Exception {
        String NIK = (String) pc.getSession().getAttribute("EmpyID");
        List<history> js = getList(NIK);
        request.setAttribute("ListHistory", js);
    }
    
    public List<history> getList(String NIK) throws Exception{
        List<history> js = new ArrayList<history>();
        
        
        String sql = "select pages, Dates from Bosnet1.dbo.TMS_History where nik = '"+NIK+"' order by Dates desc";
        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    history hs = new history();
                    int i = 1;
                    String url = FZUtil.getRsString(rs, i++, "");   
                    if(url.contains("popupEditCustBfror.jsp"))  hs.Display = "Editor";
                    else if(url.contains("runResult.jsp"))  hs.Display = "IR Run Result";
                    else if(url.contains("runResultEdit.jsp"))  hs.Display = "What IF";
                    else if(url.contains("runResultEditResult.jsp"))  hs.Display = "Result What IF";
                    hs.Value = url;
                    hs.Dates = FZUtil.getRsString(rs, i++, "");   
                    js.add(hs);
                }
            }
        }
        
        return js;
    }
    
}

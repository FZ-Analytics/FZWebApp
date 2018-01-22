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
        String sql = "SELECT * FROM BOSNET1.dbo.TMS_Params";
        OptionModel op = new OptionModel();
        List<OptionModel> js = new ArrayList<OptionModel>();
        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)){
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    op = new OptionModel();
                    int i = 1;
                    op.Display = FZUtil.getRsString(rs, i++, "");
                    op.Value = FZUtil.getRsString(rs, i++, "");
                    js.add(op);
                }
                
                request.setAttribute("ParamsList", js);
            }
        }
    }
    
}

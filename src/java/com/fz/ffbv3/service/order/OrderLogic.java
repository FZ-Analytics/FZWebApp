/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.order;

import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.util.FZUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author Eko
 */
public class OrderLogic  implements BusinessLogic {

    @Override
    public void run(
            HttpServletRequest request, 
            HttpServletResponse response, 
            PageContext pc
    ) throws Exception {
        
        String result = "";
        
        String divId = "tesdiv"; //FZUtil.getHttpParam(request, "divID");
        String blokId = FZUtil.getHttpParam(request, "blokID");
        String estTime = FZUtil.getHttpParam(request,"estTime");
        
        request.getSession().setAttribute("divId", "tes");
        String sql = "";
        
        try (Connection con = (new Db()).getConnection("jdbc/fz")){
            try (Statement stm = con.createStatement()){
/*            
                sql = "select * from fbdiv";
                try (ResultSet rs = stm.executeQuery(sql)){
                    
                    // if record exist
                    if (rs.next()){
                        request.getSession().setAttribute("divId", rs.getString(1));

                    }
                }
*/
}
        }
        request
                .getRequestDispatcher("../order/orderinput.jsp")
                .forward(pc.getRequest(), pc.getResponse());

      }
    
}

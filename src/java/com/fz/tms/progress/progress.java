/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.progress;

import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.tms.model.tmProgress;
import com.fz.util.FZUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author dwi.rangga
 */
public  class progress  implements BusinessLogic {
    public static String rId = "";
    
    @Override
    public void run(
            HttpServletRequest request
            , HttpServletResponse response
            , PageContext pc
    ) throws Exception {
        try{
            runLoad(request);
        }catch(Exception e){
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            System.out.println(">>>>>Progress.java<<<<<");
            System.out.println(">>>>>DATE NOW : "+dateFormat.format(date)+"<<<<<"); //2016/11/16 12:08:43
            System.out.println("ERROR : "+e.getMessage());
        } 
    }
    
    public static void runLoad(HttpServletRequest request) throws Exception{
        rId = (String) request.getSession().getAttribute("runId");
        
        try{
            List<tmProgress> tr = (List<tmProgress>) progressLogic.getData(rId);
            request.setAttribute("runId", rId);
            request.setAttribute("status", tr.get(0).status);
            request.setAttribute("msg", tr.get(0).msg);
            request.setAttribute("lblStop", tr.get(0).status.equalsIgnoreCase("INPR") ?
                    "Stop" : "View");
            request.setAttribute("hrefStop", tr.get(0).status.equalsIgnoreCase("INPR") ?
                    "../../ffbv3/appGlobal/bodyTop.jsp" : "../progress/progressre.jsp");
            request.setAttribute("runDate", tr.get(0).lastUpd.toString());
            request.setAttribute("branch", "0321");
            request.setAttribute("shift", tr.get(0).msg);
            request.setAttribute("runStartTime", tr.get(0).created.toString());
            request.setAttribute("runEndTime", tr.get(0).lastUpd.toString());
            
        }catch(Exception e){
            throw new Exception(e);
        } 
    }
    
}

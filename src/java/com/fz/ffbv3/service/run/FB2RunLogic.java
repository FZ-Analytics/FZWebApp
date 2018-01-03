/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fz.ffbv3.service.run;

import com.fz.ffbv3.service.hvsEstm.HvsEstmDAO;
import com.fz.ffbv3.service.usermgt.Auth;
import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.util.FZUtil;
import com.fz.util.UrlResponseGetter;
import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 */
public class FB2RunLogic implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response
            , PageContext pc) throws Exception {

        Auth.check(pc, "Run_Algo", true);
        
        String runID = FZUtil.getTimeID();
        
        // get input json
        String jsonInput = FZUtil.getHttpParam(request, "json");
        
        // parse json
//        Gson gson = new Gson();
//        FB2RunInput runInput = gson.fromJson(jsonInput, FB2RunInput.class);
//        runInput.runID = runID;
//        formatInput(runInput);
        logStart(runID, jsonInput);
        
        // run it
        String resp = callCalc(request, runID, jsonInput);
        
        if (resp.equals("OK")){
            // redirect to progress page
            response.sendRedirect("../run/runProgress.jsp?runID=" 
                    + runID 
            );
        }
        else {
            // redirect to run entry page
            request.setAttribute("errMsg", resp);
            response.sendRedirect("../appGlobal/error.jsp");
        }
    }

//    private void formatInput(FB2RunInput runInput) {
//        
//        runInput.startFruitReadyForGrabber = 
//                FZUtil.clockToMin(runInput.startFruitReadyForGrabberStr);
//        runInput.millEndTime =
//                FZUtil.clockToMin(runInput.millEndTimeStr);
//        runInput.divIDList = "";
//        for (String divID : runInput.divList){
//            runInput.divIDList += divID + ";";
//        }
//    }

    static void logStart(String runID, String jsonInput) throws Exception {
        
        String sql = "insert into fbSchedRun(runID, msg, status, input1) "
                + " values(?,?,?,?)";
        try (Connection con = (new Db()).getConnection("jdbc/fz");
                PreparedStatement ps = con.prepareStatement(sql)){
            
            ps.setString(1, runID);
            ps.setString(2, "Starting ..");
            ps.setString(3, "INPG");
            ps.setString(4, jsonInput);
            ps.executeUpdate();
        }
    }

    private String callCalc(HttpServletRequest request, String runID
            , String jsonInput) throws Exception {
        
        // call algo web
        StringBuffer url = new StringBuffer();
        url.append("http://");
        url.append(request.getServerName());
        url.append(":");
        url.append(request.getServerPort());
        url.append("/fzffbcalc/run.jsp?runID=");
        url.append(runID);
//        url.append("&json=");
//        url.append(jsonInput);
        String resp = UrlResponseGetter.getURLResponse(url.toString());
        return resp;    
    }

}

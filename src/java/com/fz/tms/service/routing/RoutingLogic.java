/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.service.routing;

import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.generic.ReturnValue;
import com.fz.router.CustDelivery;
import com.fz.router.DeliveryAgent;
import com.fz.router.Router;
import com.fz.tms.model.tmProgress;
import com.fz.tms.progress.progressLogic;
import com.fz.util.FZUtil;
import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author dwi.rangga
 */
public class RoutingLogic implements BusinessLogic {    
    public static String runId = "";
    
    public static boolean isForm = false;
    
    @Override
    public void run(
            HttpServletRequest request
            , HttpServletResponse response
            , PageContext pc
    ) throws Exception {
        isForm = true;
        String shift = FZUtil.getHttpParam(request, "shift").toString();
        runBtn(request, pc, isForm, shift);        
    }
    
    public static void runBtn(HttpServletRequest request, PageContext pc, boolean out, String shift){
        String result = "";
        String sql = "";
        try{
            runId = FZUtil.getTimeID();//"20170924_141034658";

            List<CustDelivery> sr = getDataCS(shift);
            List<DeliveryAgent> da = getDataDA();

            Router tr = new Router();
            tr.route(runId, sr, da, 0, result, result, result, sql);                       
            
            request.getSession().setAttribute("runId", runId);
            //request.setAttribute("runId", runId);
            
            if(out){
                request.getRequestDispatcher("../progress/progress.jsp").forward(pc.getRequest(), pc.getResponse());
            }
        }catch(Exception e){
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            System.out.println(">>>>>RoutingLogic.java<<<<<");
            System.out.println(">>>>>DATE NOW : "+dateFormat.format(date)+"<<<<<"); //2016/11/16 12:08:43
            System.out.println("ERROR : "+e.getMessage());
        }
    }
    
    public static String runAPI(String shift) throws Exception{
        String result = "";
        String sql = "";
        try{
            runId = FZUtil.getTimeID();//20170924_141034658

            List<CustDelivery> sr = getDataCS(shift);
            List<DeliveryAgent> da = getDataDA();

            Router tr = new Router();
            tr.route(runId, sr, da, 0, result, result, result, sql);                       
            
            result = "OK";
        }catch(Exception e){
            result = "ERROR";
            throw new Exception(e);
        }
        return result;
    }
    
    public static List<CustDelivery> getDataCS(String shift) throws Exception{
        List<CustDelivery> sr = new ArrayList<CustDelivery>();
        try{
            sr = RoutingDB.getCustDelivery(shift);     
        }catch(Exception e){
            throw new Exception(e);
        }         
        
        return sr;
    }
    
    public static List<DeliveryAgent> getDataDA() throws Exception{
        List<DeliveryAgent> sr = new ArrayList<DeliveryAgent>();
        try{
            sr = RoutingDB.getDeliveryAgent();
        }catch(Exception e){
            throw new Exception(e);
        }
        
        return sr;
    }    
    
}

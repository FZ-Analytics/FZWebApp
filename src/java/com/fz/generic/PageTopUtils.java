/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fz.generic;

import com.fz.util.FZUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 */
public class PageTopUtils {
    
    public static String get(String paramName, PageContext pc) {
        String x = (String) pc.getRequest().getAttribute(paramName);
        if (x == null) return "";
        return x;
        
    }
    
    public static Object getList(String paramName, PageContext pc){
        HttpServletRequest request = (HttpServletRequest) pc.getRequest();
        Object o = request.getAttribute(paramName);
        return o;
    }
    
    public static Object getObj(String paramName, PageContext pc){
        HttpServletRequest request = (HttpServletRequest) pc.getRequest();
        Object o = request.getAttribute(paramName);
        return o;
    }
    
    public static void run(BusinessLogic logic, PageContext pc)
        throws Exception {
        HttpServletRequest request = (HttpServletRequest) pc.getRequest();
        HttpServletResponse response = (HttpServletResponse) pc.getResponse();
        try {
            logic.run(request, response, pc);
        }
        catch (Exception e){
            // forward to error page to display error message
            // TODO: beautify the page
            String msg = FZUtil.toStackTraceText(e);
            System.out.println(msg);
            request.setAttribute("errMsg", msg);
            request
                    .getRequestDispatcher("../appGlobal/error.jsp")
                    .forward(request, response);
        }
    }
    
    public static void runAPI(BusinessLogic logic, PageContext pc)
        throws Exception {
        HttpServletRequest request = (HttpServletRequest) pc.getRequest();
        HttpServletResponse response = (HttpServletResponse) pc.getResponse();
        try {
            logic.run(request, response, pc);
        }
        catch (Exception e){
            
            String msg = FZUtil.toStackTraceText(e);
            System.out.println(msg);
            pc.getOut().print(
                    "{\"result\" : \"ERR\""
                    + ", \"errMsg\" :"
                    + "\"" + FZUtil.escapeText(msg) 
                    + "\"}"
            );
        }
    }
    
    public static boolean checkLogin(PageContext pc)
        throws Exception {
        try {
            
            HttpServletRequest request 
                    = (HttpServletRequest) pc.getRequest();
            
            // if this is login page, ignore
            String url = request.getRequestURL().toString();
            if (url.contains("/usrMgt/login.jsp")) {
                return true;
            }
            if (url.contains("/usrMgt/entry.jsp")) {
                return true;
            }
            if (url.contains("/appGlobal/error.jsp")) {
                return true;
            }
            if (url.contains("/aic/aicView.jsp")) {
                return true;
            }
            if (url.contains("/tms/")) {
                return true;
            }
            
            // else, get userID from session variable
            String login = (String) pc.getSession().getAttribute("login");
            
            // if none
            if ((login == null) || (login.length() == 0)){
                
                // fwd to login page
                request.setAttribute("loginResult", "Please login");
                request.getRequestDispatcher("../usrMgt/login.jsp")
                        .forward(request
                                , (HttpServletResponse) pc.getResponse());
                return false;
            }
        }
        catch (Exception e){
            throw new Exception("Error checking login", e);
        }
        return false;
    }
    
    public static String makeOption(String param, String value, String text) {
        String sel = (param.equals(value) ? " selected" : "");
        String ret = "<option " + sel + " value='" + value + "'>" + text + "</option>";
        return ret;
    }
}

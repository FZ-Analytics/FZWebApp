/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fz.generic;

import com.fz.util.FZUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
            
            //custom untuk link dengan TMS
            if(url.contains("/tms/Params/Backgrounder/") || url.contains("/test")){ 
                return true;
            }else if(url.contains("/tms/")){ 
                String EmpyID = (String) pc.getSession().getAttribute("EmpyID");
                String Key = (String) pc.getSession().getAttribute("Key");
                String str = "ERROR";                
                
                if ((EmpyID == null) || (EmpyID.length() == 0)){
                    str = getLink(url, request, pc);
                }else{
                    str = "OK";
                    setDate(EmpyID, Key);
                }
                
                String isParam = "";
                    
                String IsMain = (String) pc.getSession().getAttribute("IsMain") == "1" ? 
                        (String) pc.getSession().getAttribute("IsMain") : "0";
                if(str.equalsIgnoreCase("OK") && url.contains("/tms/")){ 
                    //redirect main
                    if(IsMain.equalsIgnoreCase("1")){
                        if(!url.contains("/Params/") && url.contains("/tms/")){
                            isParam = "../main2/main2.jsp";
                        }else if(url.contains("/Params/")){
                            isParam = "../../main2/main2.jsp";
                            request.getSession()
                                    .setAttribute("IsMain", "1");
                        }
                        request.getSession()
                                    .setAttribute("IsMain", null);
                        request.getRequestDispatcher(isParam)
                            .forward(request, (HttpServletResponse) pc.getResponse());
                    }
                    return true;
                }else{
                    if(!url.contains("/Params/") && url.contains("/tms/")){
                        isParam = "../usrMgt/login.jsp";
                    }else if(url.contains("/Params/")){
                        isParam = "../../usrMgt/login.jsp";
                    }
                    request.setAttribute("loginResult", "Please login");
                    request.getRequestDispatcher(isParam)
                            .forward(request, (HttpServletResponse) pc.getResponse());
                    return false;
                }
                
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
    
    public static void setDate(String NIK, String KEYLogin) throws Exception{
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = new Date();
        String dt = dateFormat.format(date).toString();
        
        String sql = "UPDATE\n" +
                "	BOSNET1.dbo.TMS_UserStatus\n" +
                "SET\n" +
                "	UpdateTable = '"+dt+"'\n" +
                "WHERE\n" +
                "	NIK = '"+NIK+"'\n" +
                "	AND KEYLogin = '"+KEYLogin+"'\n" +
                "	AND Status = 1;\n" +
                "\n" +
                "UPDATE\n" +
                "	BOSNET1.dbo.TMS_UserStatus\n" +
                "SET\n" +
                "	Status = '0'\n" +
                "WHERE\n" +
                "	DATEDIFF(\n" +
                "		HOUR,\n" +
                "		UpdateTable,\n" +
                "		GETDATE()\n" +
                "	)> 2;\n" +
                "\n" +
                "DELETE\n" +
                "FROM\n" +
                "	BOSNET1.dbo.TMS_UserStatus\n" +
                "WHERE\n" +
                "	DATEDIFF(\n" +
                "		HOUR,\n" +
                "		UpdateTable,\n" +
                "		GETDATE()\n" +
                "	)> 5;";
        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)) {
            con.setAutoCommit(false);
            ps.executeUpdate();
            con.setAutoCommit(true);
        }
    }
    
    public static String getLink(String url, HttpServletRequest request, PageContext pc) throws Exception{
        String str = "ERROR";
        
        String UserID = FZUtil.getHttpParam(request, "UserID");
        String EmpyID = FZUtil.getHttpParam(request, "EmpyID");
        String UserName = FZUtil.getHttpParam(request, "UserName");
        String WorkplaceID = FZUtil.getHttpParam(request, "WorkplaceID");
        String Key = FZUtil.getHttpParam(request, "Key");
        
        if(UserID.length() > 0 && EmpyID.length() > 0 
                && UserName.length() > 0 && Key.length() > 0 ){
            String sql = "SELECT\n" +
                "	COUNT(*) as cnt\n" +
                "FROM\n" +
                "	BOSNET1.dbo.TMS_UserStatus\n" +
                "WHERE\n" +
                "	NIK = '"+EmpyID+"'\n" +
                "	AND Status = '1'\n" +
                "	AND KEYLogin = '"+Key+"'";

            try (Connection con = (new Db()).getConnection("jdbc/fztms");
                    PreparedStatement ps = con.prepareStatement(sql)){
                try (ResultSet rs = ps.executeQuery()){
                    if(rs.next()){
                        String cnt = rs.getString("cnt");

                        //cek jika count > 0
                        str = Integer.valueOf(cnt) > 0 ? "OK" : "ERROR";

                        if(str.equalsIgnoreCase("OK")){
                            // keep user profile as session object, for next views
                            request.getSession()
                                    .setAttribute("UserID", UserID);
                            request.getSession()
                                    .setAttribute("EmpyID", EmpyID);
                            request.getSession()
                                    .setAttribute("UserName", UserName);
                            request.getSession()
                                    .setAttribute("WorkplaceID", WorkplaceID);
                            request.getSession()
                                    .setAttribute("IsMain", "1");
                            request.getSession()
                                    .setAttribute("Key", Key);
                        }
                    }else{
                        throw new Exception(); 
                    }
                }
            }
        }        
        return str;
    }
}

<%-- 
    Document   : logout
    Created on : Oct 18, 2017, 8:59:59 PM
--%>

<%@page import="com.fz.tms.service.usermgt.LoginLogic"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%
    System.out.println(EmpyID+"()"+UserName+"()"+UserID+"()"+WorkplaceID);
    
    LoginLogic aw = new LoginLogic();
    String str = aw.LogOut(EmpyID, Key);
    if(str.equalsIgnoreCase("OK")){
        session.invalidate();
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Logout</title>
    </head>
    <body>
        <h1>Logged out</h1>
        <a href='../usrMgt/login.jsp'>Re-login</a>
    </body>
</html>

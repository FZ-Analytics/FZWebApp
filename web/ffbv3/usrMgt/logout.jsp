<%-- 
    Document   : logout
    Created on : Oct 18, 2017, 8:59:59 PM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
session.invalidate();
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

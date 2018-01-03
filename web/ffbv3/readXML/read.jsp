<%-- 
    Document   : read
    Created on : Dec 26, 2017, 10:24:15 AM
    Author     : Eko
--%>

<%@include file="../appGlobal/pageTop.jsp"%>
<%@include file="readXML.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h3>Read XML File</h3>
        <% baca(out, "d:\\tmp\\saksakelah.kml"); %>
    </body>
</html>

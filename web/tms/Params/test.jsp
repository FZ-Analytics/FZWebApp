<%-- 
    Document   : test
    Created on : Oct 24, 2017, 9:01:20 AM
    Author     : dwi.rangga
--%>

<%@page import="java.io.Reader"%>
<%@page import="java.nio.charset.Charset"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.net.URL"%>
<%@page import="java.io.InputStream"%>
<%@page import="org.json.JSONException"%>
<%@page import="java.io.IOException"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.fz.util.UrlResponseGetter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../appGlobal/pageTop.jsp"%>
<%run(new com.fz.tms.params.test());%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="../appGlobal/bodyTop.jsp"%>
        
        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

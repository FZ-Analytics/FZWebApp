<%-- 
    Document   : UpdateCostDistGoogleApi
    Created on : Mar 28, 2018, 9:52:29 AM
    Author     : dwi.oktaviandi
--%>

<%@page import="com.fz.tms.params.Backgrounder.UpdateCostDistGoogleApi"%>
<%@page import="com.fz.generic.Db"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="com.fz.util.FZUtil"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.net.URL"%>
<%@page import="java.util.concurrent.TimeUnit"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONArray"%>
<%!
    public String run(HttpServletRequest request, HttpServletResponse response
            , PageContext pc
    ) throws Exception{
        String result = "OK";
        System.out.println("className.methodName()");
        UpdateCostDistGoogleApi fin = new UpdateCostDistGoogleApi();
        fin.finalizeCust();
        
        return result;
    } 



    
%>
<%=run(request, response, pageContext)%>

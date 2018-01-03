<%-- 
    Document   : DailyWA
    Created on : Dec 7, 2017, 11:47:53 AM
    Author     : Eko
--%>

<%@page import="org.json.JSONObject"%>
<%@page import="com.fz.ffbv3.service.order.OrderDAO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Daily Reoprt</title>
    </head>
    <body>
        <%@include file="../appGlobal/bodyTop.jsp" %>
        <form method="get" action="DailyWA.jsp">
            <% 
                SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
                String tgl = "";
               try {  
                    tgl = request.getAttribute("tgl").toString();
               } catch (Exception e) { 
                   tgl = ft.format(new Date());
               }
            %>
            <h3>Daily Report</h3>
            <br/>
            <label class="fzlabel" >Date </label>
            <input class="fzInput" id='tgl' name="tgl" value="<%=tgl%>"/>
            <br/>
        <script>
            $(function () {
                var v1 = $( "#tgl" ).val();
                $( "#tgl" ).datepicker();
                $( "#tgl" ).datepicker( "option", "dateFormat", "yy-mm-dd");
                $( "#tgl" ).val(v1);
            });
        </script>
            <button type="submit" class="fzButton" id="btnRun" name="btnRun" value="Run">Run</button>
        </form>
        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

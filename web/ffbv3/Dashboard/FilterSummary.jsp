<%-- 
    Document   : FilterSummary
    Created on : Nov 28, 2017, 2:34:23 PM
    Author     : Administrator
--%>

<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONArray"%>
<%@page import="com.fz.ffbv3.service.division.divisionDAO"%>
<%@page import="com.fz.util.FZUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="../appGlobal/pageTop.jsp"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="../appGlobal/bodyTop.jsp"%>
        <script>
            function klik() {                
                var filter = document.getElementById("filter").value;
                var pnlDaily = document.getElementById('daily');
                var pnlPeriod = document.getElementById('period');
                //alert(filter=='Daily');
                pnlDaily.hidden = !(filter=='Daily');
                pnlPeriod.hidden = !(filter=='Period');
            }
            
            $(function () {
                $("#dateSummary").datepicker();
                $("#dateSummary").datepicker("option", "dateFormat", "yy-mm-dd");
                $("#dateSummary").val(yyyymmddDate(new Date()));
                
                $("#fromSummary").datepicker();
                $("#fromSummary").datepicker("option", "dateFormat", "yy-mm-dd");
                $("#fromSummary").val(yyyymmddDate(new Date()));
                
                $("#toSummary").datepicker();
                $("#toSummary").datepicker("option", "dateFormat", "yy-mm-dd");
                $("#toSummary").val(yyyymmddDate(new Date()));
            });
        </script>
        <div>
            <br><br>
            <label class="fzLabel">Filter Summary</label>
            <select class="fzInput" id="filter" name="filter" onchange="klik()">
                <option value="Daily">Daily</option>
                <option value="Period">Period</option>
            </select>
        <%
            String millId = FZUtil.getHttpParam(request, "millId");
            String estateId = FZUtil.getHttpParam(request, "estateId");
            JSONArray aMill = divisionDAO.lstMill();
            JSONArray aEst  = divisionDAO.lstEstate("");
            String slct = "";
            String s = "";
        %>
            <br>
            <div id="daily">
                <form class="container" action="DetilDailySummary.jsp" method="post">
                    <br><br>
                    <label class="fzLabel">Mill</label>
                    <select class="fzInput" id="millId" name="millId" >
                <%
                    slct = (millId.equals("<All>"))?"selected":"";
                %>
                        <option value ="<All>" <%=slct%>>--All--</option>
                <%
                    for(int i=0; i<aMill.length(); i++) {
                        JSONObject o = aMill.getJSONObject(i);
                        s = o.getString("millID");
                        slct = (s.equals(millId))?"selected":"";
                %>
                <option value="<%=s%>" <%=slct%>><%=s%></option>
                <%
                    }
                %>
                    </select>
                    <br>
                    <label class="fzLabel">Estate</label>
                    <select class="fzInput" id="estateId" name="estateId">
                <%
                    slct = (estateId.equals("<All>"))?"selected":"";
                %>
                        <option value ="<All>" <%=slct%>>--All--</option>
                <%
                    for(int i=0; i<aEst.length(); i++) {
                        JSONObject o = aEst.getJSONObject(i);
                        s = o.getString("estateID");
                        slct = (s.equals(estateId))?"selected":"";
                %>
                        <option value="<%=s%>" <%=slct%>><%=s%></option>
                <%
                    }
                %>
                    </select>
                    <br>
                    <label class="fzLabel">Date:</label>
                    <input class="fzInput" id="dateSummary" 
                           name="dateSummary" value=""/>

                    <br><br>
                    <input class="btn fzButton" type="submit" name="submit" value="submit"/>
                </form>
            </div>
            <div id="period" hidden="true">
                <form class="container" action="DetilPeriodSummary.jsp" method="post">
                    <br><br>
                    <label class="fzLabel">From Date:</label>
                    <input class="fzInput" id="fromSummary" 
                           name="fromSummary" value=""/>
                    <label class="fzLabel">To Date:</label>
                    <input class="fzInput" id="toSummary" 
                           name="toSummary" value=""/>

                    <br><br>
                    <input class="btn fzButton" type="submit" name="submit" value="submit" />
                </form>
            </div>
        </div>
    <%@include file="../appGlobal/bodyBottom.jsp"%>
</body>
</html>

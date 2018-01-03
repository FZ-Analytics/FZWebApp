<%-- 
    Document   : FilterSummary
    Created on : Nov 28, 2017, 2:34:23 PM
    Author     : Administrator
--%>

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

            <br>
            <div id="daily">
                <form class="container" action="DetilDailySummary.jsp" method="post">
                    <br><br>
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

<%-- 
    Document   : DailySummary
    Created on : Nov 28, 2017, 4:09:16 PM
    Author     : Administrator
--%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="../appGlobal/bodyTop.jsp"%>
        <script>
            $(function () {
                $("#fromDate").datepicker();
                $("#fromDate").datepicker("option", "dateFormat", "yy-mm-dd");
                $("#fromDate").val(yyyymmddDate(new Date()));

                $("#toDate").datepicker();
                $("#toDate").datepicker("option", "dateFormat", "yy-mm-dd");
                $("#toDate").val(yyyymmddDate(new Date()));
           });
        </script>
        <form class="container" action="DetilPeriodSummary.jsp" method="post">
            <br><br>
            <label class="fzLabel">Date From :</label>
            <input class="fzInput" id="fromDate" name="fromDate" value=""/>
            <label class="fzLabel">To:</label>
            <input class="fzInput" id="toDate" name="toDate" value=""/>

            <br><br>
            <button class="btn fzButton" type="submit" 
                    name="submit" value="run">Search</button>
        </form>
        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

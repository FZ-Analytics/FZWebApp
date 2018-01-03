<%-- 
    Document   : progressView
    Created on : Sep 28, 2017, 2:24:42 PM
    Author     : dwi.rangga
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../ffbv3/appGlobal/pageTop.jsp"%>
<%run(new com.fz.tms.progress.progressView());%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="../../ffbv3/appGlobal/bodyTop.jsp"%>
        <form id="form1" action="../progress/progressViewpost.jsp" method="post">
            <div class="fzErrMsg">
                <%=get("updateResult")%>
            </div>
            <br>
            <label class="fzLabel">Date</label>
            <input class="fzInput" type="text" id="runDate" name="runDate" value="<%=get("runDate")%>" readonly="true">
            <br>
            <label class="fzLabel">Branch</label>
            <input class="fzInput" type="text" id="branch" name="branch" value="<%=get("branch")%>" readonly="true">
            <br>
            <label class="fzLabel">Shift</label>
            <select name="shift" id="shift" >
                <option value="1">11.00 - 15.00</option>
                <option value="2">15.00 - 11.00</option>
            </select>
            <br>
            <label class="fzLabel">Run Id</label>
            <input class="fzInput" type="text" id="runId" name="runId" value="<%=get("runId")%>" readonly="true">
            <br>
            <br>
            <button class="btn fzButton" name="btn" type="submit" id="Search" value="Search">Search</button>
        </form> 
        <%@include file="../../ffbv3/appGlobal/bodyBottom.jsp"%>
    </body>
</html>

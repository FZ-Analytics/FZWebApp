<%-- 
    Document   : TaskFilter
    Created on : Sep 28, 2017, 2:06:13 PM
    Author     : dwi.rangga
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../ffbv3/appGlobal/pageTop.jsp"%>
<%run(new com.fz.tms.task.taskFilterpre());%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="../../ffbv3/appGlobal/bodyTop.jsp"%>
        <form id="form1" action="" method="post" action="TaskFilterPost.jsp">
            <div class="fzErrMsg">
                <%=get("updateResult")%>
            </div>
            <br>
            <label class="fzLabel">Date</label>
            <input class="fzInput" type="text" id="msg" name="runDate" value="<%=get("runDate")%>" readonly="true">
            <br>
            <label class="fzLabel">Branch</label>
            <input class="fzInput" type="text" id="msg" name="branch" value="<%=get("branch")%>" readonly="true">
            <br>
            <label class="fzLabel">Shift</label>
            <input class="fzInput" type="text" id="msg" name="shift" value="<%=get("shift")%>" readonly="true">
            <br>
            <br>
            <button class="btn fzButton" name="btn" type="submit" id="Search" value="Search">Search</button>
        </form> 
        <%@include file="../../ffbv3/appGlobal/bodyBottom.jsp"%>
    </body>
</html>

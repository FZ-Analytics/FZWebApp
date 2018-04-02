<%-- 
    Document   : routing
    Created on : Sep 23, 2017, 3:46:38 PM
    Author     : dwi.rangga
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../ffbv3/appGlobal/pageTop.jsp"%>
<%run(new com.fz.tms.service.routing.routingPre());%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>        
        <%@include file="../../ffbv3/appGlobal/bodyTop.jsp"%>
        <h3>Run Process Search</h3>
        <div class="fzErrMsg">
            <%=get("coneResult")%>
        </div>
        <form class="container" action="routingProcess.jsp" method="post">  
            <label class="fzLabel">Branch</label>
            <input class="fzInput" type="text" id="txtBranch" name="txtBranch" value="<%=get("txtBranch")%>" readonly="true">
            <br>
            <label class="fzLabel">Shift</label>
            <select name="shift" id="shift" >
                <option value="1">15.00 - 11.00</option>
                <option value="2">11.00 - 15.00</option>                
            </select>
            <br>
            <br>
            <button class="btn fzButton" type="submit">GO</button>
        </form>         
        <%@include file="../../ffbv3/appGlobal/bodyBottom.jsp"%>
    </body>
</html>

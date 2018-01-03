<%-- 
    Document   : vehicleList
    Created on : Sep 25, 2017, 10:38:01 AM
    Author     : Eko
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vehicle List</title>
    </head>
    <body>
        <form id="form1" method="POST" action="vehicle.jsp">
       <%@include file="../appGlobal/bodyTop.jsp"%>
        <h3>Vehicle List</h3>
        <br/>
        <label class="fzLabel">Mill</label>
        <input type="text" class="fzInput" id="Mill_code" name="Mill_code" value="LWSM"/>
        <br/>
        <label class="fzLabel">Estate</label>
        <input type="text" class="fzInput" id="div_code" name="div_code" value=""/>
        <br/>
        <label class="fzLabel">Division</label>
        <input type="text" class="fzInput" id="div_code" name="div_code" value=""/>
        <br/>
        <br/>
        <button class="btn fzButton" type="submit">GO</button>
    </form>
    <script src='table.js'></script>
    <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

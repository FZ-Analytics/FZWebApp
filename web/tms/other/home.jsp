<%-- 
    Document   : home
    Created on : Apr 4, 2018, 1:42:42 PM
    Author     : dwi.oktaviandi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <%@include file="../appGlobal/bodyTop.jsp"%>
        <form class="container" method="post">
            <br>
            <br>
            <label class="fzLabel">Name</label>
            <label class="fzLabel">: <%=UserName%></label>

            <br>
            <label class="fzLabel">Id</label>
            <label class="fzLabel">: <%=EmpyID%></label>

            <br>
            <label class="fzLabel">Branch</label>
            <label class="fzLabel">: <%=WorkplaceID%></label>
        </form>
        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

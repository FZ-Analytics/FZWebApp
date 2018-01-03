<%-- 
    Document   : error
    Created on : Sep 20, 2017, 8:14:57 AM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="../appGlobal/bodyTop.jsp"%>
        <h1>Error occured</h1>
        <div class="fzErrMsg">
            Detail for tech support:<br>
            <%=get("errMsg")%>
        </div>
        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

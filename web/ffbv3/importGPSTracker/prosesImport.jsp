<%-- 
    Document   : prosesImport
    Created on : Nov 14, 2017, 4:25:03 PM
    Author     : Eko
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%run(new com.fz.ffbv3.service.grabberTrack.GrabberTrackLogic());%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="../appGlobal/bodyTop.jsp" %>
                    <!--%=request.getAttribute("jGrab")%-->
        <%=request.getAttribute("Status")%>
    </body>
</html>

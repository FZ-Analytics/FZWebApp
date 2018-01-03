<%-- 
    Document   : importDataSAP
    Created on : Dec 18, 2017, 3:18:24 PM
    Author     : Eko
--%>

<%@include file="../appGlobal/pageTop.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Import Data</title>
    </head>
    <body>
        <%@include file="../appGlobal/bodyTop.jsp" %>
        <h3>Import Data</h3>
        <form method="post" action="uploadFile.jsp">
            <input type = "file" id="xfile" name = "xfile" size = "50" onchange="tes()" />
            <br><br>
            <button class="btn fzButton" type="submit" name="submit" value="Go">Go</button>
        </form>
        <%
            
        %>
        <script>
            function tes(){ 
                var xfile = document.getElementById('xfile').value;
                
                alert(xfile);
            }
        </script>
            
        <%@include file="../appGlobal/bodyBottom.jsp" %>
    </body>
</html>

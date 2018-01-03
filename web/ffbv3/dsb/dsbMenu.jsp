<%-- 
    Document   : main2
    Created on : Oct 9, 2017, 10:21:17 PM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menu</title>
    </head>
    <body>
        <%@include file="../appGlobal/bodyTop.jsp"%>
        <style>
            th, td {
                padding: 15px;
                text-align: center;
            }
        </style>
        <table>
            <tr>
                <td>
                    <img src="../img/dashboard.png">
                </td>
                <td>
                    <a href="../Dashboard/FilterSummary.jsp">Daily & By Period Summary Table</a>
                </td>
            </tr>
        </table>
        
        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

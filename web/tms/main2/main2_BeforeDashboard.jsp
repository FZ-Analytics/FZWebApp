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
                    <a href="../hvsEstm/hvsEstmFilter.jsp?title=Taxation">
                        <img src="../img/taxaView.png" width="70"/>
                    </a>
                </td>
                <td>
                    <a href="../hvsEstm/hvsEstmFrm.jsp">
                       <img src="../img/taxaEntry.png" width="70"/>
                    </a>
                </td>
                <td>
                    <a href="../run/runFilter.jsp?title=Run">
                       <img src="../img/algoRun.png" width="70"/>
                    </a>
                </td>
            </tr>
            <tr>
                <td>
                    Taxation View
                </td>
                <td>
                    Taxation Entry
                </td>
                <td>
                    Run Planner
                </td>
            </tr>
            <tr>
                <td>
                    <a href="../order2/order2Filter.jsp">
                       <img src="../img/jobView.png" width="70"/>
                    </a>
                </td>
                <td>
                    <a href="../order2/order2Frm.jsp">
                       <img src="../img/jobEntry.png" width="70"/>
                    </a>
                </td>
                <td>
                    <a href="../vehicle2/vehicle2List.jsp">
                       <img src="../img/vehicleView.png" width="70"/>
                    </a>
                </td>
            </tr>
            <tr>
                <td>
                    Job View
                </td>
                <td>
                    Job Entry
                </td>
                <td>
                    Vehicle View
                </td>
            </tr>
            <tr>
                <td>
                    <a href="../vehicle2/vehicle2Frm.jsp">
                       <img src="../img/vehicleEntry.png" width="70"/>
                    </a>
                </td>
                <td>
                    <a href="../usrMgt/logout.jsp">
                       <img src="../img/logout.png" width="70"/>
                    </a>
                </td>
                <td>
                    <!--a href="javascript:alert('Under development')">
                       <img src="../img/tracker.png" width="70"/>
                    </a-->
                </td>
            </tr>
            <tr>
                <td>
                    Vehicle Entry
                </td>
                <td>
                    Logout

                </td>
                <td>
                </td>
            </tr>
        </table>
        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

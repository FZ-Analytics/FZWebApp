<%-- 
    Document   : main
    Created on : Sep 19, 2017, 11:31:19 AM
    Author     : Eko
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vehicle Filter</title>
    </head>
    <body>
        <form clas="form" action="">
            <%@include file="../appGlobal/bodyTop.jsp"%>
            <p>
            <a href="../order/OrderFilter.jsp" >Order</a>
            <br/>
            <a href="../vehicle/vehicleFilter.jsp" >Vehicle</a>
            <br/>
            <a href="../hvsEstm/hvsEstmFilter.jsp" >Estimation List</a>
            <br/>
            <a href="../hvsEstm2Job/hvsEstm2JobFilter.jsp" >Process First Order</a>
            <br/>
            <a href="../taskView/taskViewFilter.jsp" >Task View</a>
            <br/>
            <a href="../dashProd/dashProd1.jsp">Graph : Productions Per Block</a>
            <br/>
            <a href="../dashAngkut/FilterSummary.jsp">Report : Trips of Trucks</a>
            <br/>
            <a href="../importGPSTracker/ImportGPSTracker.jsp">Import GPS Tracker</a>
        </form>
    </body>
</html>

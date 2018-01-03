<%-- 
    Document   : vehicleList
    Created on : Sep 25, 2017, 10:38:01 AM
    Author     : Eko
--%>

<%@page import="java.util.Arrays"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.fz.ffbv3.service.vehicle.Vehicle" %>
<%@include file="../appGlobal/pageTop.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vehicle List</title>
    </head>
    <body>
        <form id="form1">
             <%
                
                String res = (String)request.getAttribute("res");
 //               out.println(res);
                Gson gson = new Gson();
                Vehicle[] mcArray = gson.fromJson(res, Vehicle[].class);
                List<Vehicle> mcList = new ArrayList<>(Arrays.asList(mcArray));
//                out.println(mcList);
             %>
                    
       <%@include file="../appGlobal/bodyTop.jsp"%>
        <h3>Vehicle List</h3>
        <br/>
        <br/>
        <div id="tbVehicle" class="table-editable">
        <table  border="1">
            <tr>
                <th>Vehicle ID</th>
                <th>Vehicle Name</th>
                <th>Type</th>
                <th>Kg</th>
                <th>Default Division</th>
                <th>Default Driver</th>
                <th>Start Location</th>
                <th>Start Time</th>
                <th>Status</th>
                <th></th>
            </tr>
            <%for (Vehicle v : mcList) { %> 
            <tr>
                <td><%=v.VehicleID%></td>
                <td><%=v.VehicleName%></td>
                <td><%=v.Type%></td>
                <td><%=v.Weight%></td>
                <td><%=v.DefDivCode%></td>
                <td><%=v.DefDriverID%></td>
                <td><%=v.StartLocation%></td>
                <td><%=v.StartTime%></td>
                <td><%=v.Status%></td>
                <td><a href="vehicleinput.jsp?VehicleID=<%=v.VehicleID%>">Edit</a></td>
            </tr>
            <%} // for HvsEstm %>
        </table>
        <!--&nbsp;&nbsp;<span class="table-add fzTextButton">Add</span>-->
        <br/><br/><a href="vehicleinput.jsp">Add</a>
        </div>
    </form>
    <script src='table.js'></script>
    <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

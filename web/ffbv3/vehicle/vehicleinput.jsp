<%-- 
    Document   : vehicleinput
    Created on : Sep 25, 2017, 2:39:34 PM
    Author     : Eko
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%run(new com.fz.ffbv3.service.vehicle.VehicleFrmLogic());%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vehicle Form</title>
    </head>
    <body>
       <%@include file="../appGlobal/bodyTop.jsp"%>
        <h3>Vehicle Form</h3>
        <form class="container" action="vehicleSave.jsp" method="post">
            <br/>
        <br/>
        <table>
            <tr>
                <td>Vehicle ID</td>
                <td>
                    <input class="fzInput" id="vehicleId" name="vehicleId" value="<%= request.getAttribute("VehicleId") %>"/>
                </td>
            </tr>
            <tr>
                <td>Vehicle Name</td>
                <td>
                    <input class="fzInput" id="vehicleId" name="vehicleName" value="<%= request.getAttribute("VehicleName") %>"/>
                </td>
            </tr>
            <tr>
                <td>Type</td>
                <td>
                    <select >
                        <option value="BinTruck">Bin Truck</option>
                        <option value="Grabber">Grabber</option>
                    </select>
                    </td>
            </tr>
            <tr>
                <td>Kg</td><td>
                    <input class="fzInput" id="weight" name="weight" value="<%=request.getAttribute("weight")%>"/>
                    </td>
            </tr>
            <tr>
                <td>Default Division</td><td>
                    <input class="fzInput" id="DefDivCode" name="DefDivCode" value="<%=get("DefDivCode")%>"/>
                    </td>
            </tr>
            <tr>
                <td>Default Driver</td><td>
                    <input class="fzInput" id="driver" name="driver" value="<%=request.getAttribute("DefDriverID")%>"/>
                    </td>
            </tr>
            <tr>
                <td>Start Location</td><td>
                    <input class="fzInput" id="location" name="location" value="<%=request.getAttribute("StartLocation")%>"/>
                    </td>
            </tr>
            <tr>
                <td>Start Time</td><td>
                    <input class="fzInput" id="hvsDt" name="hvsDt" value="<%=get("hvsDt")%>"/>
                    </td>
            </tr>
            <tr>
                <td>Status</td><td>
                    <select>
                        <option value="AVLB">AVLB</option>
                        <option value="NAVL">NAVL</option>
                    </select>
                    </td>
            </tr>
            <tr>
                <td></td><td>
                    <input class="fzInput" id="hvsDt" name="hvsDt" value="<%=get("hvsDt")%>"/>
                    </td>
            </tr>
        </table>
    <!--/form-->    
        <br>
        
        <button class="btn fzButton" type="submit" 
                name="saveBtn" id="saveBtn" value="save">Save</button>
        <button class="btn fzButton" type="submit" 
                name="saveBtn" id="deleteBtn" value="delete">Delete</button>
    </form>            
    </body>
</html>

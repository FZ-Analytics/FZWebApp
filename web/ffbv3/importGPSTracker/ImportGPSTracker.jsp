<%-- 
    Document   : ImportGPSTracker
    Created on : Oct 26, 2017, 7:56:56 AM
    Author     : Eko
--%>

<%@include file="../appGlobal/pageTop.jsp"%>
<!--%run(new com.fz.ffbv3.service.grabberTrack.GrabberTrackLogic());%-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Import GPS Tracker</title>
    </head>
    <body>
        <%@include file="../appGlobal/bodyTop.jsp" %>
        <h3>Import GPS Tracker</h3>
        <form action="prosesImport.jsp" method="get" enctype="multipart/form-data">
            <p/>
            <p/>
            <select id="optTrip" name="optTrip">
                <option value="opt1">Grabber</option>
                <option value="opt2">Harvester</option>
                <option value="opt2">Truck</option>
            </select>
            <p/>
            <button class="fzButton" id="submit" name="submit" value="go">GO</button>
        </form>
    </body>
</html>

<%-- 
    Document   : hvsEstmList
    Created on : Sep 23, 2017, 5:07:33 AM
--%>

<%@page import="com.fz.ffbv3.service.vehicle2.Vehicle2Record"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%run(new com.fz.ffbv3.service.vehicle2.Vehicle2FormLogic());%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order</title>
    </head>
    <body>
  <%@include file="../appGlobal/bodyTop.jsp"%>
  <script>
  $( function() {
    $( "#hvsDt" ).datepicker();
    $( "#hvsDt" ).datepicker( "option", "dateFormat", "yy-mm-dd");
    $( "#hvsDt" ).val(yyyymmddDate(new Date()));
  } );
  </script>
        <h3>Vehicle</h3>
        <form class="container" action="vehicle2Save.jsp" method="post">

            <div class="fzErrMsg">
                <%=get("errMsg")%>
            </div>
            <%Vehicle2Record r = (Vehicle2Record) getObj("VehicleRecord");%>
            <br><br>
            <label class="fzLabel">Vehicle ID</label>
            <input class="fzInput" type="text" id="vehicleID" name="vehicleID" 
                   value="<%=r.vehicleID%>" readonly>
            
            <br>
            <label class="fzLabel"></label>
            <span class="fzLabelBottom">Set by system</span>
            
            <br><br>
            <label class="fzLabel">Vehicle Name</label>
            <input class="fzInput" type="text" id="vehicleName" name="vehicleName" 
                   value="<%=r.vehicleName%>">
            
            <br><br>
            <label class="fzLabel">Default Estate + Division</label>
            <input class="fzInput" type="text" id="defDivCode" name="defDivCode" 
                   value="<%=r.defDivCode%>">
            
            <br>
            <label class="fzLabel"></label>
            <span class="fzLabelBottom">e.g. "BINE1"</span>
            
            <br><br>
            <label class="fzLabel">Start Time</label>
            <input class="fzInput" type="text" id="startTime" name="startTime" 
                   value="<%=r.startTime%>">
            
            <br>
            <label class="fzLabel"></label>
            <span class="fzLabelBottom">e.g. "07:00", "07:30"</span>
            
            <br><br>
            <label class="fzLabel">Status</label>
            <input class="fzInput" type="text" id="status" name="status" 
                   value="<%=r.status%>" readonly>
            
            <br>
            <label class="fzLabel"></label>
            <span class="fzLabelBottom">Set by system</span>
            
            <br><br>
            <label class="fzLabel">Included In Run</label>
            <select class="fzInput" id="includeInRun" name="includeInRun">
                <%= makeOption(r.includeInRun, "YES", "YES")%>
                <%= makeOption(r.includeInRun, "NO", "NO")%>
            </select>
            
            <br><br>
            <label class="fzLabel">Remark / driver info</label>
            <input class="fzInput" type="text" id="remark" name="remark" 
                   value="<%=r.remark %>">
            
            <br><br>
            <label class="fzLabel">Start Lon</label>
            <input class="fzInput" type="text" id="startLon" name="startLon" 
                   value="<%=r.startLon %>">
            
            <br><br>
            <label class="fzLabel">Start Lat</label>
            <input class="fzInput" type="text" id="startLat" name="startLat" 
                   value="<%=r.startLat %>">
            
            <br><br>
            <label class="fzLabel">Start Location Name</label>
            <input class="fzInput" type="text" id="startLocation" name="startLocation" 
                   value="<%=r.startLocation%>">
            
            <!--br><br>
            <label class="fzLabel">Last RunID</label-->
            <input class="fzInput" type="hidden" id="lastRunID" name="lastRunID" 
                   value="<%=r.lastRunID%>" readonly>
            
            <!--br>
            <label class="fzLabel"></label>
            <span class="fzLabelBottom">Set by system</span-->
            
            <br><br>
            <button class="btn fzButton" type="submit" 
                    name="submit" value="add">Save</button>
            
        </form>         
  <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

<%@page import="com.fz.ffbv3.service.schedule.ScheduleRecord"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Schedule</title>
    </head>
    <body>
    <%@include file="../appGlobal/bodyTop.jsp"%>
        <h3>Schedule</h3>
        
        <br><br>
        <table class="table" border1="1" style="border-color: lightgray;">
            <tr style="background-color:orange">
                <th width="100px" class="fzCol">Harvest Date</th>
                <th width="100px" class="fzCol">Divisions</th>
                <th width="100px" class="fzCol"></th>
            </tr>
            
        <%for (ScheduleRecord r : (List<ScheduleRecord>) getList("ScheduleList")) { %> 
        
            <tr>
                <td class="fzCell"><%=r.hvsDate%></td>
                <td class="fzCell"><%=r.divIDs%></td>
                <td class="fzCell"><a href="../run/runResult.jsp?runID=<%=r.runID%>">View</a></td>
                <td class="fzCell"><a href="../schedule/addVehicle.jsp?runID=<%=r.runID%>">Add Vehicle</a></td>
            </tr>
            
        <%} // for ScheduleRecord %>
        
        </table>
        
    <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

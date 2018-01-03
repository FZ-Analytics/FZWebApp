<%@page import="com.fz.ffbv3.service.run.RunResultRecord"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%run(new com.fz.ffbv3.service.run.RunResultListing());%>
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
                <th width="100px" class="fzCol">Truck</th>
                <th width="100px" class="fzCol">DivID</th>
                <th width="100px" class="fzCol">Blocks</th>
                <th width="100px" class="fzCol">To Blk</th>
                <th width="100px" class="fzCol">In Blk</th>
                <th width="100px" class="fzCol">To Mill</th>
                <th width="100px" class="fzCol">Done</th>
                <th width="100px" class="fzCol">Size</th>
            </tr>
            
        <%for (RunResultRecord r : (List<RunResultRecord>) getList("ResultList")) { %> 
        
            <tr>
                <td class="fzCell"><%=r.vehicleID%></td>
                <td class="fzCell"><%=r.divID%></td>
                <td class="fzCell"><%=r.blocks%></td>
                <td class="fzCell"><%=r.startTime%></td>
                <td class="fzCell"><%=r.arriveBlockTime%></td>
                <td class="fzCell"><%=r.departBlockTime%></td>
                <td class="fzCell"><%=r.endTime%></td>
                <td class="fzCell"><%=r.size%></td>
            </tr>
            
        <%} // for RunResultRecord %>
        
        </table>
        
    <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

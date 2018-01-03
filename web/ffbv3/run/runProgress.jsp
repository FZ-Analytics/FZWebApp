<%@page import="com.fz.ffbv3.service.run.FB2ProgressRecord"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%run(new com.fz.ffbv3.service.run.FB2ProgressListing());%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Runs Progress</title>
    </head>
    <body>
    <%@include file="../appGlobal/bodyTop.jsp"%>

        <h3>Runs</h3>
        <table class="table">
            <tr>
                <th width="100px" class="fzCol">Date</th>
                <th width="100px" class="fzCol">Div</th>
                <th width="100px" class="fzCol">Scheduling RunID</th>
                <th width="100px" class="fzCol">Status</th>
                <th width="100px" class="fzCol">Msg</th>
                <th width="100px" class="fzCol"></th>
            </tr>
            
        <%for (FB2ProgressRecord pr : (List<FB2ProgressRecord>) getList("ProgressList")) { %> 
        
            <tr>
                <td class="fzCell"><%=pr.hvsDate%></td>
                <td class="fzCell"><%=pr.divIDs%></td>
                <td class="fzCell"><a href="../order2/order2FilterProcess.jsp?runID=<%=pr.runID%>"><%=pr.runID%></a></td>
                <td class="fzCell"><%=pr.status%></td>
                <td class="fzCell"><%=pr.msg%></td>
                <td class="fzCell"><a href="../run/runProgress.jsp?runID=<%=pr.runID%>">Refresh</a></td>
            </tr>
            
        <%} // for ProgressRecord %>
        
        </table>
        
    <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

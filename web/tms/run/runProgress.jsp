<%@page import="com.fz.tms.service.run.ProgressRecord"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%run(new com.fz.tms.service.run.ProgressListing());%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Runs Progress</title>
        <style>
            .hover:hover {
               cursor: pointer; 
            }
        </style>
    </head>
    <body>
        <%@include file="../appGlobal/bodyTop.jsp"%>
        <script>
            $(document).ready(function () {
                /*
                if($('#stat').text() == 'INPR'){
                    setInterval(function () {
                        window.location.reload(1);
                    }, 5000);                    
                }else if($('#stat').text() == 'DONE'){
                    window.location.replace('runResult.jsp?runID='+$('#runId').val()+'&OriRunID='+$('#oriRunID').val()+'&channel='+$('#channel').val());
                }
                */
            });
        </script>
        <h3>Runs <span class="glyphicon glyphicon-refresh hover" aria-hidden="true" onclick="location.reload();"></span></h3>
        
        <input class="fzInput" id="runId" 
               name="runId" value="<%=get("runId")%>" hidden="true"/>
        <input class="fzInput" id="oriRunID" 
               name="oriRunID" value="<%=get("oriRunID")%>" hidden="true"/>
        <input class="fzInput" id="channel" 
               name="channel" value="<%=get("channel")%>" hidden="true"/>
        <table class="table">
            <tr>
                <th width="100px" class="fzCol">Branch</th>
                <th width="100px" class="fzCol">Shift</th>
                <th width="100px" class="fzCol">RunID</th>
                <th width="100px" class="fzCol">Status</th>
                <th width="100px" class="fzCol">Msg</th>
            </tr>

            <%for (ProgressRecord pr : (List<ProgressRecord>) getList("ProgressList")) {%> 

            <tr>
                <td class="fzCell"><%=pr.branch%></td>
                <td class="fzCell"><%=pr.shift%></td>
                <td class="fzCell"><a href="runResult.jsp?runID=<%=pr.runID%>&OriRunID=<%=pr.OriRunID%>&dateDeliv=<%=get("dateDeliv")%>&channel=<%=pr.channel%>"><%=pr.runID%></a></td>
                <td class="fzCell" id="stat"><%=pr.status%></td>
                <td class="fzCell"><%=pr.msg%></td>
            </tr>

            <%} // for ProgressRecord %>

        </table>

        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

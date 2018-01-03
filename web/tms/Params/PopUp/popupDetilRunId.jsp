<%-- 
    Document   : popupDetilRunId
    Created on : Oct 31, 2017, 5:07:49 PM
    Author     : dwi.rangga
--%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.fz.tms.params.model.SummaryVehicle"%>
<%run(new com.fz.tms.params.PopUp.popupDetilRunId());%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="../appGlobal/bodyTop.jsp"%>
        <br>
        <label class="fzLabel">Branch:</label> 
        <label class="fzLabel"><%=get("branch")%></label>
        
        <br>
        <label class="fzLabel">RunID:</label> 
        <label class="fzLabel" id="runID"><%=get("runID")%></label> 
        
        <br><br>
        <table class="table" border1="1" style="border-color: lightgray;">
            <tr style="background-color:orange">
                <th width="100px" class="fzCol">truck id</th>
                <th width="100px" class="fzCol">truck type</th>
                <th width="100px" class="fzCol">capacity(KG)</th>
                <th width="100px" class="fzCol">(%)</th>
                <th width="100px" class="fzCol">kubikasi(M3)</th>
                <th width="100px" class="fzCol">(%)</th>
                <th width="100px" class="fzCol">amount</th>
                <th width="100px" class="fzCol">km</th>
                <th width="100px" class="fzCol">waktu travel</th>
                <th width="100px" class="fzCol">waktu service</th>
                <th width="100px" class="fzCol">Cust Visit</th>
                <th width="100px" class="fzCol">Transport Cost</th>
            </tr>
            <%for (SummaryVehicle s : (List<SummaryVehicle>) getList("ListSum")) {%> 
            <tr>
                <th width="100px" class="fzCol"><%=s.truckid%></th>
                <th width="100px" class="fzCol"><%=s.trucktype%></th>
                <th width="100px" class="fzCol"><%=s.capacity%></th>
                <th width="100px" class="fzCol"><%=s.capacityPer%></th>
                <th width="100px" class="fzCol"><%=s.kubikasi%></th>
                <th width="100px" class="fzCol"><%=s.kubikasiPer%></th>
                <th width="100px" class="fzCol"><%=s.amount%></th>
                <th width="100px" class="fzCol"><%=s.km%></th>
                <th width="100px" class="fzCol"><%=s.time%></th>
                <th width="100px" class="fzCol"><%=s.sctime%></th>
                <th width="100px" class="fzCol"><%=s.DOcount%></th>
                <th width="100px" class="fzCol"><%=s.transportCost%></th>
            </tr>
            <%} // for ProgressRecord %>
            <tr>
                <td class="fzCol">Summary: </td>
                <td class="fzCol"></td>
                <td class="fzCol"></td>
                <td class="fzCol"><%=get("cap")%></td>
                <td class="fzCol"></td> 
                <td class="fzCol"><%=get("kub")%></td>
                <td class="fzCol"><%=get("tamount")%></td>
                <td class="fzCol"><%=get("tkm")%></td>
                <td class="fzCol"><%=get("ttravel")%></td>
                <td class="fzCol"><%=get("tservice")%></td>
                <td class="fzCol"><%=get("tcust")%></td>
                <td class="fzCol"><%=get("ttransportCost")%></td>
            </tr>
        </table>
        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

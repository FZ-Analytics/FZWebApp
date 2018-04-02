<%-- 
    Document   : ForwadingAgentView
    Created on : Jan 25, 2018, 4:14:52 PM
    Author     : dwi.oktaviandi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../appGlobal/pageTop.jsp"%>
<%run(new com.fz.tms.params.External.Driver.ForwadingAgentView());%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forwading Agent View</title>
    </head>
    <body>
        <%@include file="../../appGlobal/bodyTop.jsp"%>
        <script>
            $(document).ready(function () {                
                $('#btn').click(function () {
                    var $apiAddress = '../../../api/ForwadingAgent/submit';
                    var jsonForServer = '{\"flag\": \"' + $("#flag").val() + '\",\"Service_agent_id\":\"' + 
                            $("#Service_agent_id").val() + '\",\"Driver_Name\":\"' + $("#Driver_Name").val()  + 
                            '\",\"Branch\":\"' + $("#Branch").val() + '\",\"Status\":\"' + $("#Status").val() + 
                            '\",\"inc\":\"' + $("#inc").val() + '\"}';
                    var data = [];
                    //alert(jsonForServer);
                    $.post($apiAddress, {json: jsonForServer}).done(function (data) {
                        if(data == 'OK'){
                            alert( 'sukses' );
                            location.reload();
                        }else{
                            alert( data ); 
                        }
                    });
                });
            });
        </script>
        <div class="fzErrMsg" id="errMsg">
            <%=get("errMsg")%>
        </div>
        
        <input class="fzInput" type="text" id="flag" name="flag" value="<%=get("flag")%>" hidden="true">
        <br>
        <label class="fzLabel">Service agent id:</label> 
        <input class="fzInput" type="text" id="Service_agent_id" name="Service_agent_id" value="<%=get("Service_agent_id")%>">
        
        <br>
        <label class="fzLabel">Driver Name:</label> 
        <input class="fzInput" type="text" id="Driver_Name" name="Driver_Name" value="<%=get("Driver_Name")%>">
        
        <br>
        <label class="fzLabel">Branch:</label> 
        <input class="fzInput" type="text" id="Branch" name="Branch" value="<%=get("Branch")%>">
        
        <br>
        <label class="fzLabel">Status:</label> 
        <input class="fzInput" type="text" id="Status" name="Status" value="<%=get("Status")%>">
        
        <br>
        <label class="fzLabel">Inc:</label> 
        <select id="inc" name="inc" >
            <option value="0" <%if (get("inc").equals("0")) {%> selected="true" <%}%>>0</option>
            <option value="1" <%if (get("inc").equals("1")) {%> selected="true" <%}%>>1</option>
        </select>
        
        <br><br>
        <button class="btn fzButton" type="submit" id="btn"><%=get("flag")%></button>
        <%@include file="../../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

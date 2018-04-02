<%-- 
    Document   : progress
    Created on : Sep 23, 2017, 11:56:47 PM
    Author     : dwi.rangga
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../ffbv3/appGlobal/pageTop.jsp"%>
<%run(new com.fz.tms.progress.progress());%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="../../ffbv3/appGlobal/bodyTop.jsp"%>
        <%--
        <script>
            $( window ).load(function() {
                $.post( '../../api/progressTrackAPI/getData'
            , {json : '{\"runId\": \"'+"<%=get("runId")%>"+'\"}'})
            .done(function( data ) {
                if (data.success){
                    alert('Success');
                    $('#errMsg').html('');
                }
                else{
                    alert('Error');
                    $('#errMsg')
                    .html('Error.<br><br>Detail for tech support:<br>' 
                    + data.msg);
                }
            });
            });
        </script>      
        --%>
        <h1>Progress!</h1>
        <form id="form1" action="../progress/progressre.jsp" method="post">
            <div class="fzErrMsg">
                <%=get("updateResult")%>
            </div>
            <br>
            <label class="fzLabel">Run Id</label>
            <input class="fzInput" type="text" id="runId" name="runId" value="<%=get("runId")%>" readonly="true">
            <br>
            <label class="fzLabel">Status</label>
            <input class="fzInput" type="text" id="status" name="status" value="<%=get("status")%>" readonly="true">
            <br>
            <label class="fzLabel">Message</label>
            <input class="fzInput" type="text" id="msg" name="msg" value="<%=get("msg")%>" readonly="true">
            <br>
            <label class="fzLabel"></label>
            <a href="<%=get("hrefStop")%>" ><%=get("lblStop")%></a>
            <br>
            <label class="fzLabel">Run Date</label>
            <input class="fzInput" type="text" id="runDate" name="runDate" value="<%=get("runDate")%>" readonly="true">
            <br>
            <label class="fzLabel">Branch</label>
            <input class="fzInput" type="text" id="branch" name="branch" value="<%=get("branch")%>" readonly="true">
            <br>
            <label class="fzLabel">Shift</label>
            <input class="fzInput" type="text" id="shift" name="shift" value="<%=get("shift")%>" readonly="true">
            <br>
            <label class="fzLabel">Run Start Time</label>
            <input class="fzInput" type="text" id="runStartTime" name="runStartTime" value="<%=get("runStartTime")%>" readonly="true">
            <br>
            <label class="fzLabel">Run End Time</label>
            <input class="fzInput" type="text" id="msg" runEndTime="runEndTime" value="<%=get("runEndTime")%>" readonly="true">
            <br>
            <br>
            <%--<select name="item">
                <option value="submit">submit</option>
                <option value="refresh">refresh</option>
                <option value="close">close</option>
            </select>--%>
            <button class="btn fzButton" name="btn" type="submit" id="refresh" value="refresh">refresh</button>
            <button class="btn fzButton" name="btn" type="submit" id="finish" value="finish">finish</button>
            <button class="btn fzButton" name="btn" type="submit" id="close" value="close">close</button>
        </form>  
            <%--<script src='../js/progress.js'></script>--%>
        <%@include file="../../ffbv3/appGlobal/bodyBottom.jsp"%>
    </body>
</html>

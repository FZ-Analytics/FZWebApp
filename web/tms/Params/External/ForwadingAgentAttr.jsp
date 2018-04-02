<%-- 
    Document   : ForwadingAgentAttr
    Created on : Jan 25, 2018, 4:13:53 PM
    Author     : dwi.oktaviandi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../appGlobal/pageTop.jsp"%>
<%run(new com.fz.tms.params.Error());%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forwading Agent</title>
    </head>
    <body>
        <%@include file="../../appGlobal/bodyTop.jsp"%>
        <form class="container" action="ForwadingAgentLoad.jsp" method="post">
            <div class="fzErrMsg">
                <%=get("errMsg")%>
            </div>
            
            <br>
            <label class="fzLabel">Id</label>
            <input class="fzInput" type="text" id="Service_agent_id" name="Service_agent_id">            
            
            <br><br>            
            <button class="btn fzButton" type="submit">GO</button>
            <a href="ForwadingAgentView.jsp?flag=insert">Add New</a>
        </form> 
        <%@include file="../../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

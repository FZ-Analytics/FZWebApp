<%-- 
    Document   : error
    Created on : Sep 20, 2017, 8:14:57 AM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
    </head>
    <body>
        <%@include file="../appGlobal/bodyTop.jsp"%>
        <h3>Error</h3>
        <%
            // TODO: make MVC
            String fullMsg = get("errMsg");
            String usrMsg = "";
            //try {
                String[] cols = fullMsg.split(":"); 
                if (cols.length >= 2){
                    String col1 = cols[1];
                    String[] ats = col1.split("at ");
                    if (ats.length >= 1){
                        usrMsg = ats[0]; 
                    }
                }
            //}catch(Exception e){}
        %>
        
        <div class="fzErrMsg">
            <%=usrMsg%>
        </div>
        
        <div id="dvDtlCmd" style="color:#cccccc;" >
            <br><br>Tech Detail
        </div>
        
        <script>
        $('#dvDtlCmd').click(function(){ 
            $('#dvFullMsg').toggle(); 
        });
        </script>
        
        <div id="dvFullMsg" style="color:#cccccc; display:block">
            <br>(For tech support team)
            <%=fullMsg%>
        </div>
        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

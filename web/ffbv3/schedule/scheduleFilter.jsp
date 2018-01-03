<%-- 
    Document   : hvsEstmList
    Created on : Sep 23, 2017, 5:07:33 AM
--%>

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
  <script>
  $( function() {
    $( "#hvsDt" ).datepicker();
    $( "#hvsDt" ).datepicker( "option", "dateFormat", "yy-mm-dd");
    $( "#hvsDt" ).val(yyyymmddDate(new Date()));
  } );
  </script>
        <h3>Schedule</h3>
        <form class="container" action="scheduleFilterProcess.jsp" method="post">

            <div class="fzErrMsg">
                <%=get("errMsg")%>
            </div>
            
            <br>
            <label class="fzLabel">Harvest Date</label>
            <input class="fzInput" id="hvsDt" name="hvsDt" 
                   value=""/>
            
            <br><br>
            <label class="fzLabel">Estate / Div</label>
            <input class="fzInput" type="text" id="estaID" name="estaID" 
                   value="BINE">
            
            <br>
            <label class="fzLabel"></label>
            <span class="fzLabelBottom">e.g. "BINE"</span>
            
            <br><br>
            <button class="btn fzButton" type="submit" 
                    name="submit" value="list">Search</button>

        </form>         
  <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

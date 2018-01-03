<%-- 
    Document   : hvsEstmList
    Created on : Sep 23, 2017, 5:07:33 AM
--%>

<%@page import="com.fz.util.FZUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View</title>
    </head>
    <body>
  <%@include file="../appGlobal/bodyTop.jsp"%>
  <script>
  $( function() {
    $( "#fromDt" ).datepicker();
    $( "#fromDt" ).datepicker( "option", "dateFormat", "yy-mm-dd");
    $( "#fromDt" ).val(yyyymmddDate(new Date()));
    $( "#toDt" ).datepicker();
    $( "#toDt" ).datepicker( "option", "dateFormat", "yy-mm-dd");
    $( "#toDt" ).val(yyyymmddDate(new Date()));
  } );
  </script>
        <h3><%=FZUtil.getHttpParam(request, "title")%></h3>
        <form class="container" action="divTimingFilterProcess.jsp" method="post">

            <div class="fzErrMsg">
                <%=get("errMsg")%>
            </div>
            
            <br>
            <label class="fzLabel">From Date</label>
            <input class="fzInput" 
                   id="fromDt" name="fromDt" value=""/>
            
            <br>
            <label class="fzLabel">To Date</label>
            <input class="fzInput" 
                   id="toDt" name="toDt" value=""/>
            
            <br><br>
            <label class="fzLabel">Estate + Division</label>
            <input class="fzInput" type="text" id="estateID" 
                   name="divID" value="BINE">
            
            <br>
            <label class="fzLabel"></label>
            <span class="fzLabelBottom">e.g. "BINE" or "BINE1"</span>
            
            <br><br>
            <button class="btn fzButton" type="submit" 
                    name="submit" value="list">Search</button>

            <!--button class="btn fzButton" type="submit" 
                    name="submit" value="add">Add</button-->
            
        </form>         
  <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

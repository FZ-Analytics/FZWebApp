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
    $( "#hvsDt" ).datepicker();
    $( "#hvsDt" ).datepicker( "option", "dateFormat", "yy-mm-dd");
    $( "#hvsDt" ).val(yyyymmddDate(new Date()));
  } );
  </script>
        <h3><%=FZUtil.getHttpParam(request, "title")%></h3>
        <form class="container" action="hvsEstmFilterProcess.jsp" method="post">

            <div class="fzErrMsg">
                <%=get("errMsg")%>
            </div>
            
            <br>
            <label class="fzLabel">Harvest Date</label>
            <input class="fzInput" id="hvsDt" 
                   name="hvsDt" value=""/>
            
            <br><br>
            <label class="fzLabel">Estate + Division</label>
            <input class="fzInput" type="text" id="estateID" 
                   name="divID" value="BINE1">
            
            <br>
            <label class="fzLabel"></label>
            <span class="fzLabelBottom">e.g. "BINE" or "BINE1" or blank for all</span>
            
            <br><br>
            <button class="btn fzButton" type="submit" 
                    name="submit" value="list">Search</button>

            <!--button class="btn fzButton" type="submit" 
                    name="submit" value="add">Add</button-->
            
        </form>         
  <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

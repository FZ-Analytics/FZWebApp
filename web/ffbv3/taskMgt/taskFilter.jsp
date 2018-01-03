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
        <title>Estimation</title>
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
        <h3>Task</h3>
        <form class="container" action="taskList.jsp" method="post">

            <div class="fzErrMsg">
                <%=get("errMsg")%>
            </div>
            
            <br>
            <label class="fzLabel">Harvest Date</label>
            <input class="fzInput" id="hvsDt" 
                   name="hvsDt" value=""/>
            
            <br><br>
            <label class="fzLabel">Mill</label>
            <input class="fzInput" type="text" id="millID" 
                   name="millID" value="LWSM">
            
            <br><br>
            <label class="fzLabel">Estate + Division</label>
            <input class="fzInput" type="text" id="divID" 
                   name="divID" value="BINE1">
            <br>
            <label class="fzLabel"></label>
            <span class="fzLabelBottom">e.g. "BINE" or "BINE1" or blank for all</span>
            
            <br><br>
            <label class="fzLabel">Truck</label>
            <input class="fzInput" type="text" id="truckID" 
                   name="truckID" value="BS 29">
            <br>
            <label class="fzLabel"></label>
            <span class="fzLabelBottom">e.g. "BS 29"</span>
            
            <br><br>
            <label class="fzLabel">Phase</label>
            <input class="fzInput" type="text" id="phase" 
                   name="phase" value="ACTL">
            <br>
            <label class="fzLabel"></label>
            <span class="fzLabelBottom">e.g. "PLAN" or "ACTL"</span>
            
            <br><br>
            <label class="fzLabel">Job status</label>
            <input class="fzInput" type="text" id="jobStatus" 
                   name="jobStatus" value="INPR">
            <br>
            <label class="fzLabel"></label>
            <span class="fzLabelBottom">e.g. "PLAN", "ORDR", "ASGN", "TKEN", "DONE", "FAIL", "LATE"</span>
            
            <br><br>
            <label class="fzLabel">Task status</label>
            <input class="fzInput" type="text" id="taskStatus" 
                   name="taskStatus" value="TKEN">
            <br>
            <label class="fzLabel"></label>
            <span class="fzLabelBottom">e.g. "PLAN", "ASGN", "TKEN", "INPR", "DONE", "FAIL", "LATE"</span>
            
            <br><br>
            <button class="btn fzButton" type="submit" 
                    name="submit" value="list">Search</button>

        </form>         
  <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

<%-- 
    Document   : taskViewFilter
    Created on : Sep 28, 2017, 11:26:58 AM
    Author     : Eko
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Task List</title>
    </head>
    <body>
        <form id="frmFilter" action="taskList.jsp" method="POST">
        <%@include file="../appGlobal/bodyTop.jsp"%>
        <%run(new com.fz.ffbv3.service.taskView.TaskViewLogic());%>
         <h3>Task List</h3>
         <p />
         <br />
         <label class="fzLabel" >Harvest Date</label>
         <br />
         <label class="fzLabel" >Mill</label>
         <input type="text" class="fzInput" id="Mill_code" name="Mill_code" value="LWSM"/>
         <br />
         <label class="fzLabel" >Estate</label>
         <input type="text" class="fzInput" id="Mill_code" name="Mill_code" value=""/>
         <br />
         <label class="fzLabel" >Divisi</label>
         <input type="text" class="fzInput" id="Mill_code" name="Mill_code" value=""/>
         <br />
         <label class="fzLabel" >Job Phase</label>
         <select id="jobPhase" >
             <option value="ALGO">ALGO</option>
             <option value="ACTL">ACTL</option>  
         </select>
         <br />
         <br/>
         <button class="btn fzButton" type="submit">GO</button>
        </form>
    </body>
</html>

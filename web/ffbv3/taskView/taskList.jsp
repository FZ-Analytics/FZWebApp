<%-- 
    Document   : taskViewFilter
    Created on : Sep 28, 2017, 11:26:58 AM
    Author     : Eko
--%>

<%@page import="com.fz.ffbv3.service.taskView.TaskList"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Task List</title>
    </head>
    <body>
       <%@include file="../appGlobal/bodyTop.jsp"%>
       <%run(new com.fz.ffbv3.service.taskView.TaskViewLogic());%>
        <h3>Task List</h3>
        <p />
        <br />
        <!--label class="fzLabel" >Harvest Date</label>
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
        <button class="btn fzButton" type="submit">GO</button-->
        
        <table id="tbTaskList" border="1">
            <tr>
                <th>Truck</th>
                <th>Driver</th>
                <th>JobID</th>
                <th>TaskID</th>
                <th>TaskType</th>
                <th>From</th>
                <th>To</th>
                <th>Target Start Time</th>
                <th>Target End Time</th>
                <th>Actual Start Time</th>
                <th>Actual End Time</th>
                <th>Job Status</th>
                <th>Task Status</th>
                <th>Order Estm Time</th>
                <th>Assigned Time</th>
                <th>Done Time</th>
                <th>Task Reason</th>
            </tr>
            <%
                ArrayList<TaskList> tl = (ArrayList<TaskList>)request.getAttribute("res");
                for (TaskList tsk : (List<TaskList>) getList("res")) { %>
            <tr>
                <td><%=tsk.VehicleID%></td>
                <td><%=tsk.VehicleName%></td>
                <td><%=tsk.JobID%></td>
                <td><%=tsk.TaskID%></td>
                <td><%=tsk.TaskType%></td>
                <td><%=tsk.From%></td>
                <td><%=tsk.To%></td>
                <td><%=tsk.TargetStartTime%></td>
                <td><%=tsk.TargetEndTime%></td>
                <td><%=tsk.ActualStartTime%></td>
                <td><%=tsk.ActualEndTime%></td>
                <td><%=tsk.JobStatus%></td>
                <td><%=tsk.TaskStatus%></td>
                <td><%=tsk.OrderEstmTime%></td>
                <td><%=tsk.AssignedTime%></td>
                <td><%=tsk.TakenTime%></td>
                <td><%=tsk.DoneTime%></td>
                <td><%=tsk.TaskReason%></td>
            </tr>
            <%
                }
            %>
        </table>
        <a href="taskViewFilter.jsp">Change Search</a>
    </body>
</html>

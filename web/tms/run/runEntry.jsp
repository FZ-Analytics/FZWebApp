<%-- 
    Document   : hvsEstmList
    Created on : Sep 23, 2017, 5:07:33 AM
--%>

<%@page import="com.fz.util.FZUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%@page import="com.fz.tms.params.model.Branch"%>
<%run(new com.fz.tms.service.run.runEntry());%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Run</title>
    </head>
    <body>
        <%@include file="../appGlobal/bodyTop.jsp"%>
        <script>
        $( function() {
          $( "#dateDeliv" ).datepicker();
          $( "#dateDeliv" ).datepicker( "option", "dateFormat", "yy-mm-dd");
          $( "#dateDeliv" ).val(yyyymmddDate(new Date()));
        } );
        </script>
        <br>
        <form class="container" action="runProcess.jsp" method="post">
            <div class="fzErrMsg">
                <%=get("errMsg")%>
            </div>
            <input class="fzInput" id="runId" 
                   name="runId" value="NA" hidden="true"/>
            <input class="fzInput" id="reRunId" 
                   name="reRun" value="N" hidden="true"/>
            <input class="fzInput" id="oriRunID" 
                   name="oriRunID" value="NA" hidden="true"/>

            <h4>Filter Intelligent Routing</h4>
            <br>
            <label class="fzLabel">Branch</label>
            <%--<input class="fzInput" id="branch" 
                    name="branch" value="<%=WorkplaceID%>" readonly="true"/>--%>
            <select class="fzInput" id="branch" name="branch">
                <%for (Branch hd : (List<Branch>) getList("ListBranch")) { %>
                <%--<%= makeOption(hd.branchId, hd.branchId, hd.name)%>--%>
                    <option value='<%=hd.branchId%>' <%if (hd.branchId.equals(WorkplaceID)) {%> selected="true" <%}%>><%=hd.branchId%></option>
                <% } /* end for Branch Id */ %>
            </select> 

            <br><br>            
            <label class="fzLabel">Date Deliv</label>
            <input class="fzInput" id="dateDeliv" 
                   name="dateDeliv" value=""/>

            <br><br>
            <label class="fzLabel">Shift</label>
            <select class="fzInput" id="shift" name="shift" />
                <option value="1">1</option>
                <option value="2">2</option>
            </select>

            <br><br>
            <label class="fzLabel">Channel</label>
            <select class="fzInput" id="channel" name="channel" />
                <option value="GT">GT</option>
                <option value="MT">MT</option>
                <option value="ALL">ALL</option>
            </select>

            <br><br>
            <label class="fzLabel">Trip calculation</label>
            <select class="fzInput" id="tripCalc" name="tripCalc" />
                <option value="M">Distance based (faster process)</option>
                <option value="G">Google's traffic based (longer, more precision)</option>
            </select>

            <br><br>
            <button class="btn fzButton" type="submit" 
                    name="submit" value="run">Run</button>

        </form>         
        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

<%-- 
    Document   : Vehicle Attr
    Created on : Oct 4, 2017, 1:53:11 PM
    Author     : dwi.rangga
--%>
    
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../appGlobal/pageTop.jsp"%>
<%@page import="com.fz.tms.params.model.Branch"%>
<%run(new com.fz.tms.params.Vehicle.LoadBranchVehi());%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vehicle Attr</title>
    </head>
    <body>
        <%@include file="../appGlobal/bodyTop.jsp"%>
        <script>
            $(document).ready(function() {
                $('#branchId').on('change', function () {
                    var a = document.getElementById('urls'); //or grab it by tagname etc
                    a.href = "VehicleAttrView.jsp?flag=insert"+"&branchId="+$("#branchId").val();
                });
            });
            
            window.onload = function(){
                var a = document.getElementById('urls'); //or grab it by tagname etc
                a.href = "VehicleAttrView.jsp?flag=insert"+"&branchId="+$("#branchId").val();
            }
        </script>
        <form class="container" action="LoadBranchVehi.jsp" method="post">
            <div class="fzErrMsg">
                <%=get("errMsg")%>
            </div>
            <label class="fzLabel">Branch Id</label>
            <select id="branchId" name="branchId">
                <%for (Branch hd : (List<Branch>) getList("ListBranch")) { %>
                <%--<%= makeOption(hd.branchId, hd.branchId, hd.name)%>--%>
                    <option value='<%=hd.branchId%>' <%if (hd.branchId.equals(WorkplaceID)) {%> selected="true" <%}%>><%=hd.name%></option>
                <% } /* end for Branch Id */ %>
            </select> 
            <br>
            <br><br>
            <button class="btn fzButton" type="submit">GO</button>
            <a id="urls">Add New</a>
        </form>        
        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>
<%-- 
    Document   : Vehicle Attr
    Created on : Oct 4, 2017, 1:53:11 PM
    Author     : dwi.rangga
--%>
    
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
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
                /*
                $('#branchId').on('change', function() {                
                    var $apiAddress = '../../../api/Params/getVehicleId';
                    var jsonForServer = '{\"branchId\": \"'+$(this).val()+'\"}';
                    var data = [];
                    //alert( jsonForServer ); 
                    $.post( $apiAddress, {json : jsonForServer}).done(function(data) {
                        $("#vVehicleId").get(0).options.length = 0;
                        $("#vVehicleId").get(0).options[0] = new Option("Pilih", "-1");                         
                        $.each(data, function(index, item) {
                            $("#vVehicleId").get(0).options[$("#vVehicleId").get(0).options.length] = new Option(item.Display, item.Value);
                        });
                    });
                });
                $('#myDiv').click(function(){
                    //Some code
                    alert( 'hello' ); 
                    window.open("../../Params/popup.jsp", null, 
                    "scrollbars=1,resizable=1,height=500,width=750");
                });
                 */
            });
        </script>
        <form class="container" action="LoadBranchVehi.jsp" method="post">
            <div class="fzErrMsg">
                <%=get("errMsg")%>
            </div>
            <label class="fzLabel">Branch Id</label>
            <select id="branchId" name="branchId">
                <%for (Branch hd : (List<Branch>) getList("ListBranch")) { %>
                <%= makeOption(hd.branchId, hd.branchId, hd.name)%>
                <% } /* end for Branch Id */ %>
            </select> 
            <br>
            <%--<label class="fzLabel">Vehicle Id</label>
            <select id="vVehicleId" name="vVehicleId" >
            </select> --%>
            <%--<label class="fzLabel" id="myDiv">test</label>--%>
            <%--<input class="fzInput" type="text" id="veID" name="veID">--%>
            <br><br>
            <button class="btn fzButton" type="submit">GO</button>
        </form>        
        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>
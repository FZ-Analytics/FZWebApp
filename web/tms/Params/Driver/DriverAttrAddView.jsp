<%-- 
    Document   : DriverAttrAddView
    Created on : Mar 14, 2018, 1:50:16 PM
    Author     : dwi.oktaviandi
--%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.fz.tms.params.model.ForwadingAgent"%>
<%run(new com.fz.tms.params.Driver.DriverAttrAddView());%>
<%@page import="com.fz.tms.params.model.Branch"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Driver External</title>
    </head>
    <body>
        <%@include file="../../appGlobal/bodyTop.jsp"%>
        <script src="../../appGlobal/jquery.dataTables.min.js"></script>
        <script src="../../appGlobal/datatables.min.js"></script>
        <script src="../../appGlobal/datatables.js"></script>
        <script>
            $(document).ready(function () {
                $('.datatable').dataTable({
                    "sPaginationType": "bs_four_button"
                });
                $('.datatable').each(function () {
                    var datatable = $(this);
                    // SEARCH - Add the placeholder for Search and Turn this into in-line form control
                    var search_input = datatable.closest('.dataTables_wrapper').find('div[id$=_filter] input');
                    search_input.attr('placeholder', 'Search');
                    search_input.addClass('form-control input-sm');
                    // LENGTH - Inline-Form control
                    var length_sel = datatable.closest('.dataTables_wrapper').find('div[id$=_length] select');
                    length_sel.addClass('form-control input-sm');
                });
                
                $('#btn').click(function () {
                    var $apiAddress = '../../../api/DriverAttrAddView/submit';
                    var jsonForServer = '{\"Service_agent_id\": \"' + $("#Service_agent_id").val() + '\",\"Driver_Name\":\"' + 
                            $("#Driver_Name").val() + '\",\"Branch\":\"' + $("#branchId").val()  + 
                            '\",\"inc\":\"' + $("#inc").val() + '\"}';
                    var data = [];
                    //alert(jsonForServer);
                    $.post($apiAddress, {json: jsonForServer}).done(function (data) {
                        if(data == 'OK'){
                            alert( 'sukses' );
                            location.reload()
                        }else{
                            alert( data ); 
                        }
                    });
                });
            });
            
            function cek(id, name, branch, inc) {
                $('#Service_agent_id').val(id);
                $('#Driver_Name').val(name);
                $('#branchId').val(branch);
                $('#inc').val(inc);
            }
        </script>
        <div style="width: 100%">
            <div style="width: 30%; float:left;">
                <div class="fzErrMsg" id="errMsg">
                    <%=get("errMsg")%>
                </div>

                <br>
                <label class="fzLabel">Driver Id:</label> 
                <input class="fzInput" type="text" id="Service_agent_id" name="Service_agent_id" readonly="true">

                <br>
                <label class="fzLabel">Name:</label> 
                <input class="fzInput" type="text" id="Driver_Name" name="Driver_Name" readonly="true">

                <br>
                <label class="fzLabel">Branch:</label> 
                <select id="branchId" name="branchId">
                    <option value="D000" >D000</option>
                    <%for (Branch hd : (List<Branch>) getList("ListBranch")) { %>
                        <%= makeOption(hd.branchId, hd.branchId, hd.branchId)%>
                        <%--<option value='<%=get("hd.branchId")%>' ><%=get("hd.name")%></option>--%>
                    <% } /* end for Branch Id */ %>
                </select> 

                <br>
                <label class="fzLabel">Inc:</label> 
                <select id="inc" name="inc" >
                    <option value="1" >Include</option>
                    <option value="0" >Exclude</option>
                </select>

                <br><br>
                <button class="btn fzButton" type="submit" id="btn">submit</button>
            </div>
            <div style="width: 70%; float:left;" >
                <table cellpadding="0" cellspacing="0" border="0" class="datatable table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th>Driver Id</th>
                            <th>Name</th>
                            <th>Branch</th>
                            <th>Inc</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%for (ForwadingAgent j : (List<ForwadingAgent>) getList("ListDriver")) {%> 
                        <tr>
                            <th class="fzCellClikck" onclick="cek('<%=j.Service_agent_id%>','<%=j.Driver_Name%>','<%=j.Branch%>','<%=j.inc%>')"><%=j.Service_agent_id%></th>
                            <th class="fzCellClikck" onclick="cek('<%=j.Service_agent_id%>','<%=j.Driver_Name%>','<%=j.Branch%>','<%=j.inc%>')"><%=j.Driver_Name%></th>
                            <th class="fzCellClikck" onclick="cek('<%=j.Service_agent_id%>','<%=j.Driver_Name%>','<%=j.Branch%>','<%=j.inc%>')"><%=j.Branch%></th>
                            <th class="fzCellClikck" onclick="cek('<%=j.Service_agent_id%>','<%=j.Driver_Name%>','<%=j.Branch%>','<%=j.inc%>')"><%=j.inc%></th>
                        </tr>
                        <%} // for ProgressRecord %>
                    </tbody>
                    <tfoot></tfoot>
                </table>      
            </div>
        </div>         
        
        <%@include file="../../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

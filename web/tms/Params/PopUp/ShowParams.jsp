<%-- 
    Document   : ShowParams
    Created on : Jan 10, 2018, 4:06:54 PM
    Author     : dwi.oktaviandi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../appGlobal/pageTop.jsp"%>
<%@page import="com.fz.tms.params.model.OptionModel"%>
<%run(new com.fz.tms.params.PopUp.ShowParams());%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Param</title>
    </head>
    <body>
        <%@include file="../appGlobal/bodyTop.jsp"%>
        <script src="../appGlobal/jquery.dataTables.min.js"></script>
        <script src="../appGlobal/datatables.js"></script>
        <script >
            $(document).ready(function () {
                $('.datatable').dataTable({
                    "sPaginationType": "bs_normal"
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
                    var $apiAddress = '../../../api/ShowParamsAPI/submit';
                    var jsonForServer = '{\"Display\": \"' + $("#Names").val() + '\",\"Value\":\"' + 
                            $("#Values").val() + '\",\"Branch\":\"' + $("#branch").val()  + '\"}';
                    var data = [];
                    //alert(jsonForServer);
                    $.post($apiAddress, {json: jsonForServer}).done(function (data) {
                        if(data == 'OK'){
                            alert( 'sukses' );
                            location.reload();
                        }else{
                            alert( data ); 
                        }
                    });
                });
            });
            function chow(param, value) {
                $('#Names').val(param);
                $('#Values').val(value);
            }
        </script>
        <input type="hidden" id="branch" value="<%=WorkplaceID%>"/>
        <h4>Params <span class="glyphicon glyphicon-refresh" aria-hidden="true" onclick="location.reload();"></span></h4>
        <br>
        <br>
        <div style="width: 100%;height: 700px;">
            <div style="width: 40%;float:left;">
                <div class="fzErrMsg" id="errMsg">
                    <%=get("errMsg")%>
                </div>
                <h4>Editor</h4>
                <br>
                <label class="fzLabel">Name:</label> 
                <input class="fzInput" type="text" id="Names" name="Names" readonly="true">
                
                <br>
                <label class="fzLabel">Value:</label> 
                <input class="fzInput" type="text" id="Values" name="Values">
                
                <br><br>
                <button class="btn fzButton" type="submit" id="btn">submit</button>
            </div>
            <div style="width: 40%;float:left;">
                <table cellpadding="0" cellspacing="0" border="0" class="datatable table table-striped table-bordered">
                    <thead>
                        <tr style="background-color:orange">
                            <th width="100px" class="fzCol">Param</th>
                            <th width="100px" class="fzCol">Value</th>
                            <th width="100px" class="fzCol">Description</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%for (OptionModel j : (List<OptionModel>) getList("ParamsList")) { %> 
                        <tr>
                            <td class="fzCell" onclick="chow('<%=j.Display%>','<%=j.Value%>')"><%=j.Display%></td>
                            <td class="fzCell"><%=j.Value%></td> 
                            <td class="fzCell"><%=j.Desc%></td> 
                        </tr>

                        <%} // for ProgressRecord %>
                    </tbody>
                </table>
            </div> 
        </div>
             
            
        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

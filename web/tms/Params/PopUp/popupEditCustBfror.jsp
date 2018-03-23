<%-- 
    Document   : popupEditCustBfror
    Created on : Nov 13, 2017, 12:22:11 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%@page import="com.fz.tms.params.model.Customer"%>
<%run(new com.fz.tms.params.PopUp.popupEditCustBfror());%>
<!DOCTYPE html>
<html>
    <head>
    </head>
    <body>
        <%@include file="../appGlobal/bodyTop.jsp"%>
        <div class="fzErrMsg" id="errMsg">
            <%=get("errMsg")%>
        </div>
        <br>
        <label class="fzLabel">Branch:</label> 
        <label class="fzLabel" id="branch"><%=get("branchCode")%></label>

        <br>
        <label class="fzLabel">Shift:</label> 
        <label class="fzLabel"><%=get("shift")%></label>

        <br>
        <label class="fzLabel">Date Deliv:</label> 
        <label class="fzLabel" id="dateDeliv"><%=get("dateDeliv")%></label>

        <br>
        <label class="fzLabel">RunID:</label> 
        <label class="fzLabel" id="runId"><%=get("runId")%></label> 
        
        <br>
        <label class="fzLabel">Ori RunID:</label> 
        <label class="fzLabel" id="oriRunID"><%=get("oriRunID")%></label> 
        
        <br>
        <label class="fzLabel">Prev RunID:</label> 
        <label class="fzLabel" id="reRun"><%=get("reRun")%></label> 
        
        <br>
        <label class="fzLabel">Channel:</label> 
        <label class="fzLabel" id="channel"><%=get("channel")%></label> 
        
        <br>
        <label class="fzLabel" id="re_Run" style="color: blue;">Routing</label>
        <label class="fzLabel" id="Vvehicle" style="color: blue;" onclick="Vklik();">Vehicle</label>
        <input id="clickMe" class="btn fzButton" type="button" value="Manual Route" onclick="openManualRoutePage();" />

        <br><br>
        <table cellpadding="0" cellspacing="0" border="0" class="datatable table table-striped table-bordered">
            <thead>
                <tr style="background-color:orange">
                    <th width="100px" class="fzCol">customer id</th>
                    <th width="100px" class="fzCol">do number</th>
                    <th width="100px" class="fzCol">long</th>
                    <th width="100px" class="fzCol">lat</th>
                    <th width="100px" class="fzCol">customer priority</th>
                    <th width="100px" class="fzCol">Channel</th>
                    <th width="100px" class="fzCol">RDD</th>
                    <th width="100px" class="fzCol">service time</th>
                    <th width="100px" class="fzCol">deliv start</th>
                    <th width="100px" class="fzCol">deliv end</th>
                    <th width="100px" class="fzCol">vehicle type list</th>
                    <th width="100px" class="fzCol">inc</th>
                    <th width="100px" class="fzCol">Edit</th>
                    <th width="100px" class="fzCol">remove</th>
                </tr>
            </thead>
            <tbody>
                <%for (Customer j : (List<Customer>) getList("CustList")) {%> 
                <tr >
                    <td class="fzCell" ><%=j.customer_id%></td>
                    <td class="fzCell" ><%=j.do_number%></td>
                    <td class="fzCell" ><%=j.lng%></td>
                    <td class="fzCell" ><%=j.lat%></td>
                    <td class="fzCell" ><%=j.customer_priority%></td>
                    <td class="fzCell" ><%=j.channel%></td>
                    <td class="fzCell" ><%=j.rdd%></td>
                    <td class="fzCell" ><%=j.service_time%></td>
                    <td class="fzCell" ><%=j.deliv_start%></td>
                    <td class="fzCell" ><%=j.deliv_end%></td>
                    <td class="fzCell" ><%=j.vehicle_type_list%></td>
                    <td class="fzCell" ><%=j.isInc%></td>
                    <td class="fzCell" onclick="klik('<%=j.customer_id%>')" >
                        <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                    </td>
                    <td class="fzCell" onclick="exclude('<%=j.customer_id%>','<%=j.do_number%>')">
                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                    </td>
                </tr>

                <%} // for ProgressRecord %>
            </tbody>
            <tfoot></tfoot>
        </table>
        
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
                $('#re_Run').click(function () {
                    //setTimeout(function () {
                        //alert($('#dateDeliv').text());
                        //var dateNow = $.datepicker.formatDate('yy-mm-dd', new Date());//currentDate.getFullYear()+"-"+(currentDate.getMonth()+1)+"-"+currentDate.getDate();
                        //alert($('#dateDeliv').text() + '&branch=' + $('#branch').text() + '&runId=' + $("#runId").text() + '&oriRunID=' + $("#oriRunID").text()  + '&reRun=' + $("#reRun").text() + '&channel=' + $("#channel").text());
                        //var win = window.open('../../run/runProcess.jsp?tripCalc=M&shift=1&dateDeliv=' + $('#dateDeliv').text() + '&branch=' + $('#branch').text() + '&runId=' + $("#runId").text() + '&oriRunID=' + $("#oriRunID").text()  + '&reRun=' + $("#reRun").text(), null);
                        var win = window.location.replace('../../run/runProcess.jsp?shift=1&dateDeliv=' + $('#dateDeliv').text() + '&branch=' + $('#branch').text() + '&runId=' + $("#runId").text() + '&oriRunID=' + $("#oriRunID").text()  + '&reRun=' + $("#reRun").text() + '&channel=' + $("#channel").text());
                        if (win) {
                            //Browser has allowed it to be opened
                            win.focus();
                        }
                    //}, 3000);
                });
            });

            function klik(kode) {
                window.open('popupEditCust.jsp?runId=' + $('#runId').text() + '&custId=' + kode, null,
                        'scrollbars=1,resizable=1,height=500,width=750');

            }
            
            function Vklik() {
                window.open('ShowPreRouteVehicle.jsp?runId=' + $('#runId').text(), null,
                        'scrollbars=1,resizable=1,height=500,width=950');

            }
            
            function openManualRoutePage() {
                var win = window.open('../../run/runManualRoute.jsp?branch='+$('#branch').text()+'&shift='+$('#shift').text()+'&oriRunId='+$('#runId').text()+'&channel='+$('#channel').val());
                if (win) {
                    //Browser has allowed it to be opened
                    win.focus();
                }
            }
            
            function exclude(custId, kode) {
                var $apiAddress = '../../../api/popupEditCustBfror/excludeDO';
                var jsonForServer = '{\"RunId\": \"' + $("#runId").text() + '\",\"Customer_ID\":\"' + 
                        custId + '\",\"DO_Number\":\"' + kode  + '\",\"ExcInc\":\"exc\"}';
                var data = [];

                $.post($apiAddress, {json: jsonForServer}).done(function (data) {
                    if(data == 'OK'){
                        alert( 'sukses' );
                        //location.reload();
                    }else{
                        alert( 'submit error' ); 
                    }
                });

            }
        </script>
        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

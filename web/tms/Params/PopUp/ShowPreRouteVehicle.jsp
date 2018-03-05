<%-- 
    Document   : ShowPreRouteVehicle
    Created on : Jan 10, 2018, 10:31:41 AM
    Author     : dwi.oktaviandi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%@page import="com.fz.tms.params.model.Vehicle"%>
<%run(new com.fz.tms.params.PopUp.ShowPreRouteVehicle());%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vehicle</title>
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
            });
            
            function klik(kode) {
                window.open('popupEditPreRouteVehicle.jsp?runId=' + $('#runId').text() + '&vCode=' + kode, null,
                        'scrollbars=1,resizable=1,height=500,width=750');

            }
        </script>
        <br>
        <label class="fzLabel">RunID:</label> 
        <label class="fzLabel" id="runId" ><%=get("runId")%></label> 
        
        <br>
        <label class="fzLabel">Branch:</label> 
        <label class="fzLabel" id="branch"><%=get("branch")%></label>
        
        <br>
        <br>
        <table cellpadding="0" cellspacing="0" border="0" class="datatable table table-striped table-bordered">
            <thead>
                <tr style="background-color:orange">
                    <th width="100px" class="fzCol">vehicle_code</th>
                    <th width="100px" class="fzCol">weight</th>
                    <th width="100px" class="fzCol">volume</th>
                    <th width="100px" class="fzCol">vehicle_type</th>
                    <th width="100px" class="fzCol">Lng</th>
                    <th width="100px" class="fzCol">Lat</th>
                    <th width="100px" class="fzCol">startTime</th>
                    <th width="100px" class="fzCol">endTime</th>
                    <th width="100px" class="fzCol">source</th>
                    <th width="100px" class="fzCol">costPerM</th>
                    <th width="100px" class="fzCol">Id Driver</th>
                    <th width="100px" class="fzCol">Nama Driver</th>
                    <th width="100px" class="fzCol">agent priority</th>
                    <th width="100px" class="fzCol">inc</th>
                    <th width="100px" class="fzCol">Edit</th>
                </tr>
            </thead>
            <tbody>
                <%for (Vehicle j : (List<Vehicle>) getList("VehicleList")) { %> 
                <tr>
                    <td class="fzCell"><%=j.vehicle_code%></td>
                    <td class="fzCell"><%=j.weight%></td>                    
                    <td class="fzCell"><%=j.volume%></td>
                    <td class="fzCell"><%=j.vehicle_type%></td>
                    <td class="fzCell"><%=j.startLon%></td>
                    <td class="fzCell"><%=j.startLat%></td>
                    <td class="fzCell"><%=j.startTime%></td>
                    <td class="fzCell"><%=j.endTime%></td>
                    <td class="fzCell"><%=j.source1%></td>
                    <td class="fzCell"><%=j.costPerM%></td>
                    <td class="fzCell"><%=j.IdDriver%></td>
                    <td class="fzCell"><%=j.NamaDriver%></td>
                    <td class="fzCell"><%=j.agent_priority%></td>
                    <td class="fzCell" ><%=j.isActive%></td>
                    <td class="fzCell"onclick="klik('<%=j.vehicle_code%>')" >
                        <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                    </td>
                </tr>

                <%} // for ProgressRecord %>
            </tbody>
        </table>
        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

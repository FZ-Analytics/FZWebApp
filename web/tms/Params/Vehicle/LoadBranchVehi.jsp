<%-- 
    Document   : VehicleAttrPre
    Created on : Oct 5, 2017, 10:01:20 AM
    Author     : dwi.rangga
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%@page import="com.fz.tms.params.model.Vehicle"%>
<%run(new com.fz.tms.params.Vehicle.ParamVehicleView());%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="../appGlobal/bodyTop.jsp"%>        
        <div class="fzErrMsg">
            <%=get("errMsg")%>
        </div>
        <br>
        <br>
        <div>
            <div>
                <table cellpadding="0" cellspacing="0" border="0" class="datatable table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th>vehicle code</th>
                            <th>branch</th>
                            <th>startLon</th>
                            <th>startLat</th>
                            <th>endLon</th>
                            <th>endLat</th>
                            <th>startTime</th>
                            <th>endTime</th>
                            <th>source1</th>
                            <th>vehicle type</th>
                            <th>weight</th>
                            <th>volume</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%for (Vehicle j : (List<Vehicle>) getList("listVehicle")) {%> 
                        <tr>
                            <th class="fzCellClikck"><%=j.vehicle_code%></th>
                            <th><%=j.branch%></th>
                            <th><%=j.startLon%></th>
                            <th><%=j.startLat%></th>
                            <th><%=j.endLon%></th>
                            <th><%=j.endLat%></th>
                            <th><%=j.startTime%></th>
                            <th><%=j.endTime%></th>
                            <th><%=j.source1%></th>
                            <th><%=j.vehicle_type%></th>
                            <th><%=j.weight%></th>
                            <th><%=j.volume%></th>
                        </tr>
                        <%} // for ProgressRecord %>
                    </tbody>
                    <tfoot></tfoot>
                </table>
            </div>
        </div>
        <script src="../appGlobal/jquery.dataTables.min.js"></script>
        <script src="../appGlobal/datatables.min.js"></script>
        <script src="../appGlobal/datatables.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                $('.fzCellClikck').click(function () {
                    //Some code
                    //alert( $(this).text() ); 
                    if ($(this).text().length > 0) {
                        window.open("../Vehicle/VehicleAttrView.jsp?vehiId=" + $(this).text() + "&flag=update", null,
                                "scrollbars=1,resizable=1,height=500,width=750");
                        return true;
                    }
                });
                
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
                 
            });
        </script>
        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

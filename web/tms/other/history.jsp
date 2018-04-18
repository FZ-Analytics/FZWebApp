<%-- 
    Document   : history
    Created on : Apr 18, 2018, 10:10:03 AM
    Author     : dwi.oktaviandi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%@page import="com.fz.tms.params.model.history"%>
<%run(new com.fz.tms.other.histories());%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>History</title>
    </head>
    <body>
        <%@include file="../appGlobal/bodyTop.jsp"%>
        <h4>History <span class="glyphicon glyphicon-refresh" aria-hidden="true" onclick="location.reload();"></span></h4>
        <br>
        <br>
        <table cellpadding="0" cellspacing="0" border="0" class="datatable table table-striped table-bordered" style="width: 500px">
            <thead>
                <tr style="background-color:orange">
                    <th width="100px" class="fzCol">History</th>
                    <th width="100px" class="fzCol">Date</th>
                    <%--<th width="100px" class="fzCol">Highlight</th>
                    <th width="100px" class="fzCol">Dell</th>--%>
                </tr>
            </thead>
            <tbody>
                <%for (history j : (List<history>) getList("ListHistory")) {%> 
                <tr >
                    <td class="fzCell" ><a target="iframe1" href="<%=j.Value%>" ><%=j.Display%></a></td>
                    <td class="fzCell" ><%=j.Dates%></td>
                    <%--<td class="fzCell" ><span class="fa fa-star-o" aria-hidden="true" onclick="location.reload();"></span></td>
                    <td class="fzCell" ><span class="fa fa-remove" aria-hidden="true" onclick="location.reload();"></span></td>--%>
                </tr>
                <%} // for ProgressRecord %>
            </tbody>
            <tfoot></tfoot>
        </table>
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
        </script>
        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

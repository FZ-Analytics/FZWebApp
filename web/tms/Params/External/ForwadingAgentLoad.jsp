<%-- 
    Document   : ForwadingAgentLoad
    Created on : Jan 25, 2018, 4:14:37 PM
    Author     : dwi.oktaviandi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../appGlobal/pageTop.jsp"%>
<%@page import="com.fz.tms.params.model.ForwadingAgent"%>
<%run(new com.fz.tms.params.External.Driver.ForwadingAgentLoad());%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forwading Agent Load</title>
    </head>
    <body>
        <%@include file="../../appGlobal/bodyTop.jsp"%>
        <div class="fzErrMsg">
            <%=get("errMsg")%>
        </div>
        <br>
        <br>
        <div id="wrap">
            <div class="container">
                <table cellpadding="0" cellspacing="0" border="0" class="datatable table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Nama</th>
                            <th>Branch</th>
                            <th>inc</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%for (ForwadingAgent j : (List<ForwadingAgent>) getList("ListForwadingAgent")) {%> 
                        <tr>
                            <th class="fzCellClikck"><%=j.Service_agent_id%></th>
                            <th><%=j.Driver_Name%></th>
                            <th><%=j.Branch%></th>
                            <th><%=j.inc%></th>
                        </tr>
                        <%} // for ProgressRecord %>
                    </tbody>
                    <tfoot></tfoot>
                </table>
            </div>
        </div>
        <script src="../../appGlobal/jquery.dataTables.min.js"></script>
        <script src="../../appGlobal/datatables.min.js"></script>
        <script src="../../appGlobal/datatables.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                $('.fzCellClikck').click(function () {
                    //Some code
                    //alert( $(this).text() ); 
                    if ($(this).text().length > 0) {
                        window.open("../External/ForwadingAgentView.jsp?flag=update&agentId=" + $(this).text(), null,
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
        <%@include file="../../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

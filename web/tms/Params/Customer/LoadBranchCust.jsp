<%-- 
    Document   : LoadBranchCust
    Created on : Oct 16, 2017, 2:35:33 PM
    Author     : dwi.rangga
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../appGlobal/pageTop.jsp"%>
<%@page import="com.fz.tms.params.model.Customer"%>
<%run(new com.fz.tms.params.Customer.LoadBranchCust());%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
                <h4>Load Customer</h4>
                <table cellpadding="0" cellspacing="0" border="0" class="datatable table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th>Customer Id</th>
                            <th>Nama</th>
                            <th>Street</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%for (Customer j : (List<Customer>) getList("ListCustomer")) {%> 
                        <tr>
                            <th class="fzCellClikck"><%=j.customer_id%></th>
                            <th><%=j.Name1%></th>
                            <th><%=j.Street%></th>
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
                        window.open("../Customer/CustomerAttrView.jsp?custId=" + $(this).text(), null,
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

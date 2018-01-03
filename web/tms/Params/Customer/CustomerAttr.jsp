<%-- 
    Document   : Customer Attr
    Created on : Oct 4, 2017, 1:52:29 PM
    Author     : dwi.rangga
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%--<%@page import="com.fz.tms.params.model.Branch"%>
<%@page import="com.fz.tms.params.model.Customer"%>--%>
<%run(new com.fz.tms.params.Customer.CustError());%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Customer Attr</title>
    </head>
    <body>
        <%@include file="../appGlobal/bodyTop.jsp"%>
        <%--script src="../appGlobal/jquery.dataTables.min.js"></script>
        <script src="../appGlobal/datatables.min.js"></script>
        <script src="../appGlobal/datatables.js"></script>--%>
        <script>
            $(document).ready(function () {
                /*
                $('.fzCellClikck').click(function () {
                    alert('ok1');
                    //hanya tampilan
                    $('.datatable').empty();
                    
                    //$('.datatable').empty();
                    //$('.datatable').re
                    $('.datatable').dataTable.
                    $('.datatable').dataTable({"filter": false, "destroy": true, "bDestroy": true});
                    $('.datatable').dataTable({"filter": false, "destroy": true, "bDestroy": true});
                    //$('.datatable').DataTable();
                    //$('.datatable').DataTable().fnDestroy();
                    //$('.datatable').DataTable().destroy();
                    alert('ok2');
                });
                $('#branchId').on('change', function () {
                    var $apiAddress = '../../../api/Params/getCustomerId';
                    var jsonForServer = '{\"branchId\": \"' + $(this).val() + '\"}';
                    var data = [];
                    
                    //alert( jsonForServer ); 
                    $.post($apiAddress, {json: jsonForServer}).done(function (data) {
                        $.each(data, function (index, item) {
                            //$("#vCustomerId").get(0).options[$("#vCustomerId").get(0).options.length] = new Option(item.Display, item.Value);
                            $('#here_table').append('<tr class=\"fzCellClikck\"><td>' + item.Value + '</td><td>' + item.Display + '</td></tr>');
                        });
                        $('.datatable').dataTable({
                            "sPaginationType": "bs_four_button"
                        });
                        $('.datatable').each(function () {
                            var datatable = $(this);
                            $(this).closest()
                            // SEARCH - Add the placeholder for Search and Turn this into in-line form control
                            var search_input = datatable.closest('.dataTables_wrapper').find('div[id$=_filter] input');
                            search_input.attr('placeholder', 'Search');
                            search_input.addClass('form-control input-sm');
                            // LENGTH - Inline-Form control
                            var length_sel = datatable.closest('.dataTables_wrapper').find('div[id$=_length] select');
                            length_sel.addClass('form-control input-sm');
                        });
                    });

                });
                /*$('.datatable').dataTable({
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
                 });*/
            });
        </script>
        <form class="container" action="LoadBranchCust.jsp" method="post">
            <div class="fzErrMsg">
                <%=get("errMsg")%>
            </div>
            <br>

            <%--<label class="fzLabel">Branch Id</label>
            <select id="branchId" name="branchId">
                <%for (Branch hd : (List<Branch>) getList("ListBranch")) {%>
                <%= makeOption(hd.branchId, hd.branchId, hd.branchId)%>
                <% }
                    /* end for Branch Id */%>
            </select> 
            <br>--%>

            <label class="fzLabel">Customer Id</label>
            <%--<select id="vCustomerId" name="vCustomerId" >
            </select> --%>
            <input class="fzInput" type="text" id="custID" name="custID">
            <%--
            <div id="wrap">
                <div class="container">
                    <h3>A demo of Bootstrap data table</h3>
                    <table cellpadding="0" cellspacing="0" border="0" class="datatable table table-striped table-bordered">
                        <thead>
                            <tr>
                                <th class="fzCellClikck">Customer Id</th>
                                <th>Nama</th>
                            </tr>
                        </thead>
                        <tbody id="here_table">  
                        </tbody>
                        <tfoot></tfoot>
                    </table>
                </div>
            </div>--%>
            <br><br>
            <button class="btn fzButton" type="submit">GO</button>
        </form>        
        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

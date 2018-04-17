<%-- 
    Document   : LoadBranchCust
    Created on : Oct 16, 2017, 2:35:33 PM
    Author     : dwi.rangga
--%>

<%@page import="com.fz.util.FZUtil"%>
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
        <h4>Load Customer <span class="glyphicon glyphicon-refresh" aria-hidden="true" onclick="location.reload();"></span></h4>
                
        <div  style="width: 100%;height: 500px;">
            <div style="width: 40%; float:left;" id="view">
                <div class="fzErrMsg" id="errMsg">
                    <%=get("errMsg")%>
                </div>
                <link rel="stylesheet" href="../../appGlobal/timepicker.css">
                <script src="../../appGlobal/timepicker.js"></script>
                <input type="text" id="flag" name="flag" value="<%=get("flag")%>" hidden="true">
                <br>
                <label class="fzLabel">Customer Id:</label> 
                <input class="fzInput" type="text" id="customer_id" name="customer_id" value="<%=get("customer_id")%>" readonly="true">

                <br>
                <label class="fzLabel">Service Time:</label> 
                <input class="fzInput" type="text" id="service_time" name="service_time" value="<%=get("service_time")%>" maxlength="2">

                <br>
                <label class="fzLabel">Deliv Start:</label> 
                <input class="fzInput" type="text" id="deliv_start" name="deliv_start" value="<%=get("deliv_start")%>" maxlength="5">

                <br>
                <label class="fzLabel">Deliv End:</label> 
                <input class="fzInput" type="text" id="deliv_end" name="deliv_end" value="<%=get("deliv_end")%>" maxlength="5">

                <br>
                <label class="fzLabel">Vehicle Type List:</label>             
                <input class="fzInput" type="text" id="vehicle_type_list" name="vehicle_type_list" value="<%=get("vehicle_type_list")%>" readonly="true">
                <select id="vVehiType" name="vVehiType" >
                    <option value="CDE4">CDE4</option>
                    <option value="VAN4">VAN4</option>
                    <option value="L300">L300</option>
                    <option value="CDD6">CDD6</option>
                    <option value="FUSO">FUSO</option>
                    <option value="TRNT">TRNT</option>
                </select>
                <label class="fzLabel" id="dell">Dell</label> 

                <br>
                <label class="fzLabel">Day Win Start:</label> 
                <input class="fzInput" type="text" id="dayWinStart" name="dayWinStart" value="<%=get("dayWinStart")%>" maxlength="5">

                <br>
                <label class="fzLabel">Day Win End:</label> 
                <input class="fzInput" type="text" id="dayWinEnd" name="dayWinEnd" value="<%=get("dayWinEnd")%>" maxlength="5">

                <br>
                <label class="fzLabel">Delivery Deadline:</label> 
                <input class="fzInput" type="text" id="deliveryDeadline" name="deliveryDeadline" value="<%=get("deliveryDeadline")%>" maxlength="5">

                <br><br>
                <button class="btn fzButton" type="submit" id="btn"><%=get("flag")%></button>
            </div>
            <%String flag = FZUtil.getHttpParam(request, "flag");%>
            <div style="width: 60%; float:left;" id="tbl"
                <%if (flag.equalsIgnoreCase("insert")) {%> hidden="true" <%}%> >
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
                    /*
                    if ($(this).text().length > 0) {
                        window.open("../Customer/CustomerAttrView.jsp?custId=" + $(this).text(), null,
                                "scrollbars=1,resizable=1,height=500,width=750");
                        return true;
                    }*/
                    var $apiAddress = '../../../api/customerAttrView/loadView';
                        var jsonForServer = '{\"customer_id\": \"' + $(this).text() + '\"}';
                        var data = [];
                        //alert(jsonForServer);
                        $.post($apiAddress, {json: jsonForServer}).done(function (data) {
                            //alert(data.vehicle_code);
                            $('#customer_id').val(data.customer_id);
                            $('#service_time').val(data.service_time);
                            $('#deliv_start').val(data.deliv_start);
                            $('#deliv_end').val(data.deliv_end);
                            $('#vehicle_type_list').val(data.vehicle_type_list);
                            $('#dayWinStart').val(data.DayWinStart);
                            $('#dayWinEnd').val(data.DayWinEnd);
                            $('#deliveryDeadline').val(data.DeliveryDeadline);
                            $('#btn').text(data.flag);
                            $('#flag').val(data.flag);
                        });
                    
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
                $('#vVehiType').on('change', function () {
                    var vTypeOp = $(this).val();
                    var vTypeVal = $("#vehicle_type_list").val() + '';
                    //alert( vTypeVal.length );
                    if (vTypeVal.indexOf(vTypeOp) == -1) {
                        if (vTypeVal.length > 0) {
                            $("#vehicle_type_list").val(vTypeVal + "|" + vTypeOp);
                        } else {
                            $("#vehicle_type_list").val(vTypeOp);
                        }
                        //alert($("#vehicle_type_list").val());
                    }
                });

                $('#dell').click(function () {
                    var vTypeOp = $("#vVehiType").val() + '';
                    var vTypeVal = $("#vehicle_type_list").val() + '';

                    if (vTypeVal.indexOf(vTypeOp) != -1
                            && vTypeVal.length > 0) {
                        //alert(vTypeVal.indexOf(vTypeOp) + vTypeVal.length);
                        if (vTypeVal.indexOf(vTypeOp) == 0) {
                            var rep = "";
                            if (vTypeVal.indexOf((vTypeOp + '|')) != -1) {
                                rep = vTypeVal.replace((vTypeOp + '|'), '');
                            } else {
                                rep = vTypeVal.replace(vTypeOp, '');
                            }
                            $("#vehicle_type_list").val(rep);
                        } else {
                            var rep = vTypeVal.replace(('|' + vTypeOp), '');
                            $("#vehicle_type_list").val(rep);
                        }
                    }
                });
                $('#deliv_start').timepicker();
                $('#deliv_end').timepicker();
                //$('.fzLabel').click(function(){
                //window.location.href = "../Customer/CustomerAttr.jsp";
                //});
                
                $('#btn').click(function () {
                    var $apiAddress = '../../../api/customerAttrView/submitCust';
                    var jsonForServer = '{\"flag\": \"' + $("#flag").val() + '\",\"customer_id\":\"' + 
                            $("#customer_id").val() + '\",\"service_time\":\"' + $("#service_time").val()  + 
                            '\",\"deliv_start\":\"' + $("#deliv_start").val() + '\",\"deliv_end\":\"' + $("#deliv_end").val() + 
                            '\",\"vehicle_type_list\":\"' + $("#vehicle_type_list").val() + 
                            '\",\"DayWinStart\":\"' + $("#dayWinStart").val() + '\",\"DayWinEnd\":\"' + $("#dayWinEnd").val() +
                            '\",\"DeliveryDeadline\":\"' + $("#deliveryDeadline").val() + '\"}';
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
        </script>
        <%@include file="../../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

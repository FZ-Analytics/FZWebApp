<%-- 
    Document   : CustomerAttrView
    Created on : Oct 4, 2017, 2:01:03 PM
    Author     : dwi.rangga
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%run(new com.fz.tms.params.Customer.ParamCustomerViewPre());%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Jobs</title>
    </head>
    <body>
        <%@include file="../../appGlobal/bodyTop.jsp"%>
        <script>
            $(document).ready(function () {
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
        <%@include file="../../appGlobal/bodyBottom.jsp"%>
    </body>
</html>
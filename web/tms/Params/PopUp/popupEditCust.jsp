<%-- 
    Document   : popupEditCust
    Created on : Nov 6, 2017, 1:44:56 PM
    Author     : dwi.rangga
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%run(new com.fz.tms.params.PopUp.popupEditCust());%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="../appGlobal/bodyTop.jsp"%>
        <link rel="stylesheet" href="../appGlobal/timepicker.css">
        <script src="../appGlobal/timepicker.js"></script>
        <script>
            $(document).ready(function () {
                $('#vCustDo').on('change', function () {
                    var $apiAddress = '../../../api/popupEditCust/getStatusDO';
                    var jsonForServer = '{\"RunId\": \"' + $("#runId").val() + '\",\"Customer_ID\":\"' + 
                            $("#custId").val() + '\",\"DO_Number\":\"' + $("#vCustDo").val() +'\"}';
                    //alert(jsonForServer);
                    var data = [];
                    //alert( jsonForServer ); 
                    $.post($apiAddress, {json: jsonForServer}).done(function (data) {
                        $('#vExcInc').val(data);
                    });
                });
                
                $('#btn').on('click', function () {
                    var $apiAddress = '../../../api/popupEditCust/submitCustomer';
                    var jsonForServer = '{\"RunId\": \"' + $("#runId").val() + '\",\"Customer_ID\":\"' + 
                            $("#custId").val() + '\",\"DO_Number\":\"' + $("#vCustDo").val()  + 
                            '\",\"lng\":\"' + $("#long").val() + '\",\"lat\":\"' + $("#lat").val() + 
                            '\",\"ExcInc\":\"' + $("#vExcInc").val() + '\",\"Customer_priority\":\"' + $("#Customer_priority").val() + 
                            '\",\"Service_time\":\"' + $("#service_time").val() + '\",\"deliv_start\":\"' + $("#deliv_start").val() +
                            '\",\"deliv_end\":\"' + $("#deliv_end").val() + '\",\"vehicle_type_list\":\"' + $("#vehicle_type_list").val() +'\"}';
                    var data = [];
                    
                    $.post($apiAddress, {json: jsonForServer}).done(function (data) {
                        if(data == 'OK'){
                            alert( 'sukses' );
                            location.reload()
                        }else{
                            alert( 'submit error' ); 
                        }
                    });
                });
                
                $('#exit').on('click', function () {
                    window.close();
                });
                
                $('#vVehiType').on('change', function () {
                    var vTypeOp = $(this).val();
                    var vTypeVal = $("#vehicle_type_list").val() + '';
                    //alert( vTypeVal.length );
                    if (vTypeVal.indexOf(vTypeOp) == -1) {
                        if(vTypeVal.length > 0){
                            $("#vehicle_type_list").val(vTypeVal + "|" + vTypeOp);                            
                        }else{
                            $("#vehicle_type_list").val(vTypeOp);
                        }
                        //alert($("#vehicle_type_list").val());
                    }
                });
                
                $('#dell').click(function(){
                    var vTypeOp = $("#vVehiType").val() + '';
                    var vTypeVal = $("#vehicle_type_list").val() + '';
                    
                    if(vTypeVal.indexOf(vTypeOp) != -1
                            && vTypeVal.length > 0){
                        //alert(vTypeVal.indexOf(vTypeOp) + vTypeVal.length);
                        if(vTypeVal.indexOf(vTypeOp) == 0){ 
                            var rep = "";
                            if(vTypeVal.indexOf((vTypeOp+'|')) != -1){
                                rep = vTypeVal.replace((vTypeOp+'|'), '');
                            }else{
                                rep = vTypeVal.replace(vTypeOp, '');
                            }
                            $("#vehicle_type_list").val(rep);        
                        }else{
                            var rep = vTypeVal.replace(('|'+vTypeOp), '');
                            $("#vehicle_type_list").val(rep);
                        }
                    }
                });
                
                $('#deliv_start').timepicker();
                $('#deliv_end').timepicker();
            });

            window.onload = function () {
                var $apiAddress = '../../../api/popupEditCust/getDO';
                var jsonForServer = '{\"RunId\": \"' + $("#runId").val() + '\",\"Customer_ID\":\"' + 
                        $("#custId").val() + '\"}';
                var data = [];
                //alert();
                $.post($apiAddress, {json: jsonForServer}).done(function (data) {
                    $("#vCustDo").get(0).options.length = 0;
                    //$("#vCustDo").get(0).options[0] = new Option("Pilih", "-1");
                    $.each(data, function (index, item) {
                        $("#vCustDo").get(0).options[$("#vCustDo").get(0).options.length] = new Option(item.Display, item.Value);
                    });
                });
            };
        </script>
        <h1>Edit Customer</h1>
        <div class="fzErrMsg">
            <%=get("errMsg")%>
        </div>
        <input class="fzInput" type="text" id="runId" name="runId" value='<%=get("runId")%>' hidden="true">
        <br>
        <label class="fzLabel">Customer Id:</label> 
        <input class="fzInput" type="text" id="custId" name="custId" value='<%=get("custId")%>' readonly="true">

        <br>
        <label class="fzLabel">Nama:</label> 
        <input class="fzInput" type="text" id="nama" name="nama" value='<%=get("Name1")%>' readonly="true">

        <br>
        <label class="fzLabel">Long:</label> 
        <input class="fzInput" type="text" id="long" name="long" value='<%=get("Long")%>'>

        <br>
        <label class="fzLabel">Lat:</label> 
        <input class="fzInput" type="text" id="lat" name="lat" value='<%=get("Lat")%>'>

        <br>
        <label class="fzLabel">Do:</label> 
        <select id="vCustDo" name="vCustDo" ></select>

        <br>
        <label class="fzLabel">Status Do:</label> 
        <select id="vExcInc" name="vExcInc" >
            <option value="inc" <%if (get("Is_Exclude").equals("inc")) {%> selected="true" <%}%>>Include</option>
            <option value="exc" <%if (get("Is_Exclude").equals("exc")) {%> selected="true" <%}%>>Exclude</option>
        </select>
        
        <br>
        <label class="fzLabel">Customer priority:</label> 
        <input class="fzInput" type="text" id="Customer_priority" name="Customer_priority" value='<%=get("Customer_priority")%>'>
        
        <br>
        <label class="fzLabel">Service time:</label> 
        <input class="fzInput" type="text" id="service_time" name="service_time" value='<%=get("Service_time")%>'>
        
        <br>
        <label class="fzLabel">deliv start:</label> 
        <input class="fzInput" type="text" id="deliv_start" name="deliv_start" value='<%=get("deliv_start")%>'>
        
        <br>
        <label class="fzLabel">deliv end:</label> 
        <input class="fzInput" type="text" id="deliv_end" name="deliv_end" value='<%=get("deliv_end")%>'>
        
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
        <label class="fzLabel">Name:</label> 
        <input class="fzInput" type="text" id="Name1" name="Name1" value='<%=get("Name1")%>' readonly="true">
        
        <br>
        <label class="fzLabel">Street:</label> 
        <input class="fzInput" type="text" id="Street" name="Street" value='<%=get("Street")%>' readonly="true">
        
        <br>
        <label class="fzLabel">Channel:</label> 
        <input class="fzInput" type="text" id="Distribution_Channel" name="Distribution_Channel" value='<%=get("Distribution_Channel")%>' readonly="true">
        
        <br>
        <label class="fzLabel">Kelurahan:</label> 
        <input class="fzInput" type="text" id="Desa_Kelurahan" name="Desa_Kelurahan" value='<%=get("Desa_Kelurahan")%>' readonly="true">
        
        <br>
        <label class="fzLabel">Kecamatan:</label> 
        <input class="fzInput" type="text" id="Kecamatan" name="Kecamatan" value='<%=get("Kecamatan")%>' readonly="true">
        
        <br>
        <label class="fzLabel">Kabupaten:</label> 
        <input class="fzInput" type="text" id="Kodya_Kabupaten" name="Kodya_Kabupaten" value='<%=get("Kodya_Kabupaten")%>' readonly="true">
        
        <br><br>
        <button class="btn fzButton" type="submit" id="btn">Submit</button>
        <button class="btn fzButton" type="submit" id="exit">Exit</button>

        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

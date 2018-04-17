<%-- 
    Document   : VehicleAttrPre
    Created on : Oct 5, 2017, 10:01:20 AM
    Author     : dwi.rangga
--%>

<%@page import="com.fz.util.FZUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%@page import="com.fz.tms.params.model.Vehicle"%>
<%run(new com.fz.tms.params.Vehicle.ParamVehicleView());%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body style="font-size: 12px">
        <%@include file="../appGlobal/bodyTop.jsp"%>    
        <script src="../appGlobal/jquery.dataTables.min.js"></script>
        <script src="../appGlobal/datatables.js"></script>
        <link rel="stylesheet" href="../appGlobal/timepicker.css">
        <script src="../appGlobal/timepicker.js"></script>
        <script>
            $(document).ready(function () {
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
                
                $('#startTime').timepicker();
                $('#endTime').timepicker();
                $('#btn').click(function () {
                    var $apiAddress = '../../../api/vehicleAttrView/submitVehi';
                    var jsonForServer = '{\"flag\": \"' + $("#flag").val() + 
                            '\",\"vehicle_code\":\"' + $("#vehicle_code").val() + '\",\"branch\":\"' + $("#branch").val()  + 
                            '\",\"vehicle_type\":\"' + $("#vehicle_type").val() + '\",\"weight\":\"' + $("#weight").val() + 
                            '\",\"volume\":\"' + $("#volume").val() + '\",\"startLon\":\"' + $("#startLon").val() + 
                            '\",\"startLat\":\"' + $("#startLat").val() + '\",\"endLon\":\"' + $("#endLon").val() + 
                            '\",\"endLat\":\"' + $("#endLat").val() + '\",\"startTime\":\"' + $("#startTime").val() + 
                            '\",\"endTime\":\"' + $("#endTime").val() + '\",\"source1\":\"' + $("#source1").val() +
                            '\",\"included\":\"' + $("#included").val() + '\",\"costPerM\":\"' + $("#costPerM").val() +
                            '\",\"fixedCost\":\"' + $("#fixedCost").val() + '\",\"Channel\":\"' + $("#Channel").val() +
                            '\",\"IdDriver\":\"' + $("#FixIdDriver").val() + '\",\"NamaDriver\":\"' + $("#NamaDriver").val() +
                            '\",\"agent_priority\":\"' + $("#agent_priority").val() + '\",\"max_cust\":\"' + $("#max_cust").val() + '\"}';
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
                
                $('#IdDriver').on('change', function () {
                    var $apiAddress = '../../../api/vehicleAttrView/getNamaDriver';
                    var jsonForServer = '{\"branch\":\"' + $("#branch").val()  + 
                            '\",\"IdDriver\":\"' + $("#IdDriver").val() + '\"}';
                    var data = [];
                    //alert(jsonForServer);
                    $.post($apiAddress, {json: jsonForServer}).done(function (data) {
                        
                        $.each(data, function(index, item) {
                            $('#FixIdDriver').val(item.Value);
                            $('#NamaDriver').val(item.Display);
                            //$("#vVehicleId").get(0).options[$("#vVehicleId").get(0).options.length] = new Option(item.Display, item.Value);
                        });
                    });
                });
                 
            });
            
            function klik(kode) {
                var $apiAddress = '../../../api/vehicleAttrView/loadView';
                var jsonForServer = '{\"flag\": \"' + 'update' + 
                        '\",\"vehicle_code\":\"' + kode + '\"}';
                var data = [];
                //alert(jsonForServer);
                $.post($apiAddress, {json: jsonForServer}).done(function (data) {
                    //alert(data.vehicle_code);
                    $('#vehicle_code').val(data.vehicle_code);
                    $('#branch').val(data.branch);
                    $('#vehicle_type').val(data.vehicle_type);
                    $('#weight').val(data.weight);
                    $('#volume').val(data.volume);
                    $('#startLon').val(data.startLon);
                    $('#startLat').val(data.startLat);
                    $('#endLon').val(data.endLon);
                    $('#endLat').val(data.endLat);
                    $('#startTime').val(data.startTime);
                    $('#endTime').val(data.endTime);
                    $('#source1').val(data.source1);
                    $('#included').val(data.included);
                    $('#costPerM').val(data.costPerM);
                    $('#fixedCost').val(data.fixedCost);
                    $('#Channel').val(data.Channel);
                    $('#FixIdDriver').val(data.IdDriver);
                    $('#IdDriver').val(data.IdDriver);
                    $('#NamaDriver').val(data.NamaDriver);
                    $('#agent_priority').val(data.agent_priority);
                    $('#max_cust').val(data.max_cust);
                    $('#btn').text(data.flag);
                    
                    
                    /*
                    if(data == 'OK'){
                        alert( 'sukses' );
                        location.reload();
                    }else{
                        alert( data ); 
                    }*/
                });
            }
        </script>
        <h4>Load Vehicle <span class="glyphicon glyphicon-refresh" aria-hidden="true" onclick="location.reload();"></span></h4>
        <br>
        <div style="width: 100%;height: 700px;">
            <div style="width: 40%; float:left;" id="view">
                <div class="fzErrMsg">
                    <%=get("errMsg")%>
                </div>
                <input type="text" id="flag" name="flag" value="<%=get("flag")%>" hidden="true">
                <br>
                <label class="fzLabel">vehicle Id:</label> 
                <input class="fzInput" type="text" id="vehicle_code" name="vehicle_code" 
                       <%if (get("flag").equals("view")){%> readonly="true" <%}%>>

                <br>
                <label class="fzLabel">branch:</label> 
                <input class="fzInput" type="text" id="branch" name="branch" 
                       maxlength="4" readonly="true"
                       <%if (get("flag").equals("insert")){%> value="<%=get("branch")%>" <%}%>>

                <br>
                <label class="fzLabel">vehicle_type:</label> 
                <input class="fzInput" type="text" id="vehicle_type" name="vehicle_type" 
                       <%if (get("flag").equals("view")){%> readonly="true" <%}%>>
                <%--<span class="fzLabelBottom">e.g. "CDE"</span>--%>

                <br>
                <label class="fzLabel">weight(/kg):</label> 
                <input class="fzInput" type="text" id="weight" name="weight" 
                       <%if (get("flag").equals("view")){%> readonly="true" <%}%>>

                <br>
                <label class="fzLabel">volume:</label> 
                <input class="fzInput" type="text" id="volume" name="volume" 
                       <%if (get("flag").equals("view")){%> readonly="true" <%}%>>

                <br>
                <label class="fzLabel">startLon:</label> 
                <input class="fzInput" type="text" id="startLon" name="startLon" >

                <br>
                <label class="fzLabel">startLat:</label> 
                <input class="fzInput" type="text" id="startLat" name="startLat" >

                <br>
                <label class="fzLabel">endLon:</label> 
                <input class="fzInput" type="text" id="endLon" name="endLon" >

                <br>
                <label class="fzLabel">endLat:</label> 
                <input class="fzInput" type="text" id="endLat" name="endLat" >

                <br>
                <label class="fzLabel">startTime:</label> 
                <input class="fzInput" type="text" id="startTime" name="startTime" maxlength="5">

                <br>
                <label class="fzLabel">endTime:</label> 
                <input class="fzInput" type="text" id="endTime" name="endTime" maxlength="5">

                <br>
                <label class="fzLabel">source1:</label> 
                <select id="source1" name="source1" >
                    <option value="EXT" <%if (get("source1").equals("EXT")) {%> selected="true" <%}%>>EXT</option>
                    <option value="INT" <%if (get("source1").equals("INT")) {%> selected="true" <%}%>>INT</option>
                </select>

                <br>
                <label class="fzLabel">included:</label> 
                <select id="included" name="included" >
                    <option value="1" <%if (get("included").equals("0")) {%> selected="true" <%}%>>yes</option>
                    <option value="0" <%if (get("included").equals("1")) {%> selected="true" <%}%>>no</option>
                </select>

                <br>
                <label class="fzLabel">costPerM:</label> 
                <input class="fzInput" type="text" id="costPerM" name="costPerM" maxlength="5">

                <br>
                <label class="fzLabel">fixedCost:</label> 
                <input class="fzInput" type="text" id="fixedCost" name="fixedCost" maxlength="5">

                <br>
                <label class="fzLabel">Channel:</label> 
                <select id="Channel" name="Channel" >
                    <option value="GT" <%if (get("Channel").equals("GT")) {%> selected="true" <%}%>>GT</option>
                    <option value="MT" <%if (get("Channel").equals("MT")) {%> selected="true" <%}%>>MT</option>
                </select>

                <br>
                <label class="fzLabel">Id Driver:</label> 
                <input class="fzInput" type="text" id="FixIdDriver" name="FixIdDriver" readonly="true">
                <select id="IdDriver" name="IdDriver" >
                <%for (Vehicle hd : (List<Vehicle>) getList("ListDriver")) { %>
                    <%= makeOption(hd.IdDriver, hd.IdDriver, hd.IdDriver)%>
                <% } /* end for Branch Id */ %>
                </select>

                <br>
                <label class="fzLabel">Nama Driver:</label> 
                <input class="fzInput" type="text" id="NamaDriver" name="NamaDriver" maxlength="50" readonly="true">

                <br>
                <label class="fzLabel">Vehicle Priority:</label> 
                <input class="fzInput" type="text" id="agent_priority" name="agent_priority" maxlength="50">

                <br>
                <label class="fzLabel">max cust:</label> 
                <input class="fzInput" type="text" id="max_cust" name="max_cust" maxlength="50">

                <br><br>
                <button class="btn fzButton" type="submit" id="btn">
                    <%if (get("flag").equals("insert")){%> <%=get("flag")%> <%}%>
                </button>
            </div>
            <%String flag = FZUtil.getHttpParam(request, "flag");%>
            <div style="width: 60%; float:left;" id="tbl"
                <%if (flag.equalsIgnoreCase("insert")) {%> hidden="true" <%}%> >
                <table cellpadding="0" cellspacing="0" border="0" class="datatable table table-striped table-bordered" >                       
                    <thead>
                        <tr >
                            <th width="100px" class="fzCol">Vehicle code</th>
                            <th width="100px" class="fzCol">Branch</th>
                            <th width="100px" class="fzCol">Source1</th>
                            <th width="100px" class="fzCol">Type</th>
                            <th width="100px" class="fzCol">Channel</th>
                            <th width="100px" class="fzCol">inc</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%for (Vehicle j : (List<Vehicle>) getList("listVehicle")) {%> 
                        <tr>
                            <th class="fzCell" onclick="klik('<%=j.vehicle_code%>')" ><%=j.vehicle_code%></th>
                            <th class="fzCell" ><%=j.branch%></th>
                            <th class="fzCell" ><%=j.source1%></th>
                            <th class="fzCell" ><%=j.vehicle_type%></th>
                            <th class="fzCell" ><%=j.Channel%></th>
                            <th class="fzCell" ><%=j.included%></th>
                        </tr>
                        <%} // for ProgressRecord %>
                    </tbody>
                    <tfoot></tfoot>
                </table>
            </div>
        </div>
        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

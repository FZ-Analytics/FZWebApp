<%-- 
    Document   : popupEditPreRouteVehicle
    Created on : Jan 19, 2018, 10:02:21 AM
    Author     : dwi.oktaviandi
--%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%run(new com.fz.tms.params.PopUp.popupEditPreRouteVehicle());%>
<%@page import="com.fz.tms.params.model.Vehicle"%>
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
                $('#exit').on('click', function () {
                    window.close();
                });
                
                $('#startTime').timepicker();
                $('#endTime').timepicker();
                
                $('#btn').on('click', function () {
                    var $apiAddress = '../../../api/popupEditPreRouteVehicle/Submit';
                    var jsonForServer = '{\"RunId\": \"' + $("#runId").val() + '\",\"vehicle_code\":\"' + 
                            $("#vehicle_code").val() + '\",\"weight\":\"' + $("#weight").val()  + 
                            '\",\"volume\":\"' + $("#volume").val() + '\",\"vehicle_type\":\"' + $("#vehicle_type").val() + 
                            '\",\"startLon\":\"' + $("#startLon").val() + '\",\"startLat\":\"' + $("#startLat").val() + 
                            '\",\"endLon\":\"' + $("#endLon").val() + '\",\"endLat\":\"' + $("#endLat").val() + 
                            '\",\"startTime\":\"' + $("#startTime").val() + '\",\"endTime\":\"' + $("#endTime").val() +
                            '\",\"source1\":\"' + $("#source1").val() + '\",\"costPerM\":\"' + $("#costPerM").val() +
                            '\",\"fixedCost\":\"' + $("#fixedCost").val() + '\",\"IdDriver\":\"' + $("#FixIdDriver").val() + 
                            '\",\"NamaDriver\":\"' + $("#NamaDriver").val() + '\",\"agent_priority\":\"' + $("#agent_priority").val() + 
                            '\",\"isActive\":\"' + $("#isActive").val() +'\"}';
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
            
            function cost() {
                var cost = parseFloat($("#solar").val() / ($("#persentase").val() * 1000)).toFixed(2);
               $("#costPerM").val(cost);
            }
        </script>
        
        <h1>Edit Route Vehicle</h1>
        <div class="fzErrMsg">
            <%=get("errMsg")%>
        </div>
        
        <input class="fzInput" type="text" id="runId" name="runId" value='<%=get("runId")%>' hidden="true">
        <input class="fzInput" type="text" id="branch" name="branch" value='<%=get("branch")%>' hidden="true">
        <br>
        <label class="fzLabel">Vehicle code:</label> 
        <input class="fzInput" type="text" id="vehicle_code" name="vehicle_code" value='<%=get("vehicle_code")%>' readonly="true">
        
        <br>
        <label class="fzLabel">weight:</label> 
        <input class="fzInput" type="text" id="weight" name="weight" value='<%=get("weight")%>'>
        <span class="fzLabelBottom">e.g. "kg"</span>
        
        <br>
        <label class="fzLabel">volume:</label> 
        <input class="fzInput" type="text" id="volume" name="volume" value='<%=get("volume")%>'>
        <span class="fzLabelBottom">e.g. "cm3"</span>
        
        <br>
        <label class="fzLabel">Vehicle type:</label> 
        <select id="vehicle_type" name="vehicle_type" >
            <option value="CDE4" <%if (get("vehicle_type").equals("CDE4")) {%> selected="true" <%}%>>CDE4</option>
            <option value="VAN4" <%if (get("vehicle_type").equals("VAN4")) {%> selected="true" <%}%>>VAN4</option>
            <option value="L300" <%if (get("vehicle_type").equals("L300")) {%> selected="true" <%}%>>L300</option>
            <option value="CDD6" <%if (get("vehicle_type").equals("CDD6")) {%> selected="true" <%}%>>CDD6</option>
            <option value="FUSO" <%if (get("vehicle_type").equals("FUSO")) {%> selected="true" <%}%>>FUSO</option>
            <option value="TRNT" <%if (get("vehicle_type").equals("TRNT")) {%> selected="true" <%}%>>TRNT</option>
        </select>
        
        <br>
        <label class="fzLabel">startLon:</label> 
        <input class="fzInput" type="text" id="startLon" name="startLon" value='<%=get("startLon")%>'>
        
        <br>
        <label class="fzLabel">startLat:</label> 
        <input class="fzInput" type="text" id="startLat" name="startLat" value='<%=get("startLat")%>' >
        
        <br>
        <label class="fzLabel">endLon:</label> 
        <input class="fzInput" type="text" id="endLon" name="endLon" value='<%=get("endLon")%>'>
        
        <br>
        <label class="fzLabel">endLat:</label> 
        <input class="fzInput" type="text" id="endLat" name="endLat" value='<%=get("endLat")%>' >
        
        <br>
        <label class="fzLabel">startTime:</label> 
        <input class="fzInput" type="text" id="startTime" name="startTime" value='<%=get("startTime")%>' >
        
        <br>
        <label class="fzLabel">endTime:</label> 
        <input class="fzInput" type="text" id="endTime" name="endTime" value='<%=get("endTime")%>' >
        
        <br>
        <label class="fzLabel">source1:</label> 
        <select id="source1" name="source1" >
            <option value="INT" <%if (get("source1").equals("INT")) {%> selected="true" <%}%>>INT</option>
            <option value="EXT" <%if (get("source1").equals("EXT")) {%> selected="true" <%}%>>EXT</option>
        </select>
        
        <br>
        <label class="fzLabel">costPerM:</label> 
        <input class="fzInput" type="text" id="persentase" name="persentase" value='<%=get("persentase")%>' onclick="cost()">
        <span class="fzLabelBottom">e.g. "persentase"</span>
        <br>
        <label class="fzLabel"></label> 
        <input class="fzInput" type="text" id="solar" name="solar" value='<%=get("solar")%>' onclick="cost()">
        <span class="fzLabelBottom">e.g. "solar"</span>
        <br>
        <label class="fzLabel"></label> 
        <input class="fzInput" type="text" id="costPerM" name="costPerM" value='<%=get("costPerM")%>' readonly="true">
        
        <br>
        <label class="fzLabel">fixedCost:</label> 
        <input class="fzInput" type="text" id="fixedCost" name="fixedCost" value='<%=get("fixedCost")%>'>
        
        <br>
        <label class="fzLabel">Id Driver:</label> 
        <input class="fzInput" type="text" id="FixIdDriver" name="FixIdDriver" value='<%=get("IdDriver")%>' readonly="true">
        <select id="IdDriver" name="IdDriver" >
            <%for (Vehicle hd : (List<Vehicle>) getList("ListDriver")) { %>
                <%= makeOption(hd.IdDriver, hd.IdDriver, hd.IdDriver)%>
            <% } /* end for Branch Id */ %>
        </select>
        
        <br>
        <label class="fzLabel">Driver name:</label> 
        <input class="fzInput" type="text" id="NamaDriver" name="NamaDriver" value='<%=get("NamaDriver")%>'readonly="true">
        
        <br>
        <label class="fzLabel">agent priority:</label> 
        <input class="fzInput" type="text" id="agent_priority" name="agent_priority" value='<%=get("agent_priority")%>'>
        
        <br>
        <label class="fzLabel">isActive:</label> 
        <select id="isActive" name="isActive" >
            <option value="0" <%if (get("isActive").equals("0")) {%> selected="true" <%}%>>0</option>
            <option value="1" <%if (get("isActive").equals("1")) {%> selected="true" <%}%>>1</option>
        </select>
        
        <br><br>
        <button class="btn fzButton" type="submit" id="btn">Submit</button>
        <button class="btn fzButton" type="submit" id="exit">Exit</button>
        
        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

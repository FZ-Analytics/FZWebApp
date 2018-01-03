<%-- 
    Document   : VehicleAttrView
    Created on : Oct 5, 2017, 10:02:24 AM
    Author     : dwi.rangga
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%run(new com.fz.tms.params.Vehicle.ParamVehicleViewPre());%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Jobs</title>
    </head>
    <body>
        <%@include file="../appGlobal/bodyTop.jsp"%>
         <script>
            $(document).ready(function () {
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
                            '\",\"included\":\"' + $("#included").val() + '\"}';
                    var data = [];
                    //alert(jsonForServer);
                    $.post($apiAddress, {json: jsonForServer}).done(function (data) {
                        if(data == 'OK'){
                            alert( 'sukses' );
                            location.reload()
                        }else{
                            alert( data ); 
                        }
                    });
                });
            });
        </script>
        
            <link rel="stylesheet" href="../appGlobal/timepicker.css">
            <script src="../appGlobal/timepicker.js"></script>
            
            <div class="fzErrMsg">
                <%=get("errMsg")%>
            </div>
            <input type="text" id="flag" name="flag" value="<%=get("flag")%>" hidden="true">
            <input type="text" id="extVe" name="extVe" value="<%=get("extVe")%>" hidden="true">
            <br>
            <label class="fzLabel">vehicle Id:</label> 
            <input class="fzInput" type="text" id="vehicle_code" name="vehicle_code" value="<%=get("vehicle_code")%>" readonly="true">
                
            <br>
            <label class="fzLabel">branch:</label> 
            <input class="fzInput" type="text" id="branch" name="branch" 
                   value="<%=get("branch")%>" maxlength="4" readonly="true">
                       
            <br>
            <label class="fzLabel">vehicle_type:</label> 
            <input class="fzInput" type="text" id="vehicle_type" name="vehicle_type" value="<%=get("vehicle_type")%>"
                   <%if (get("extVe").equals("true")){%> readonly="true" <%}%>>
            <span class="fzLabelBottom">e.g. "CDE"</span>
                
            <br>
            <label class="fzLabel">weight(/kg):</label> 
            <input class="fzInput" type="text" id="weight" name="weight" value="<%=get("weight")%>"
                   <%if (get("extVe").equals("true")){%> readonly="true" <%}%>>
                       
            <br>
            <label class="fzLabel">volume:</label> 
            <input class="fzInput" type="text" id="volume" name="volume" value="<%=get("volume")%>"
                   <%if (get("extVe").equals("true")){%> readonly="true" <%}%>>
                       
            <br>
            <label class="fzLabel">startLon:</label> 
            <input class="fzInput" type="text" id="startLon" name="startLon" value="<%=get("startLon")%>">
                
            <br>
            <label class="fzLabel">startLat:</label> 
            <input class="fzInput" type="text" id="startLat" name="startLat" value="<%=get("startLat")%>">
                
            <br>
            <label class="fzLabel">endLon:</label> 
            <input class="fzInput" type="text" id="endLon" name="endLon" value="<%=get("endLon")%>">
                
            <br>
            <label class="fzLabel">endLat:</label> 
            <input class="fzInput" type="text" id="endLat" name="endLat" value="<%=get("endLat")%>">
                
            <br>
            <label class="fzLabel">startTime:</label> 
            <input class="fzInput" type="text" id="startTime" name="startTime" value="<%=get("startTime")%>" maxlength="5">
            <span class="fzLabelBottom">e.g. "08:00"</span>
                
            <br>
            <label class="fzLabel">endTime:</label> 
            <input class="fzInput" type="text" id="endTime" name="endTime" value="<%=get("endTime")%>" maxlength="5">
            <span class="fzLabelBottom">e.g. "15:00"</span>
                
            <br>
            <label class="fzLabel">source1:</label> 
            <input class="fzInput" type="text" id="source1" name="source1" value="<%=get("source1")%>"  maxlength="4">
            <span class="fzLabelBottom">e.g. "INT"</span>
            
            <br>
            <label class="fzLabel">included:</label> 
            <input class="fzInput" type="text" id="included" name="included" value="<%=get("included")%>">
            <br><br>
            <button class="btn fzButton" type="submit" id="btn"><%=get("flag")%></button>
        
        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>
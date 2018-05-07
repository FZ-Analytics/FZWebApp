<%-- 
    Document   : GoogleMapAPI
    Created on : Apr 23, 2018, 2:35:42 PM
    Author     : dwi.oktaviandi
--%>

<%@page import="org.json.JSONArray"%>
<%@page import="com.fz.util.FZUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../appGlobal/pageTop.jsp"%>
<%run(new com.fz.tms.params.Backgrounder.GoogleMapAPIController());%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>GoogleMapAPI</title>
    </head>
    <body onload="calculateDistance()">   
        <input type="text" id="StringUrlGoogle" name="StringUrlGoogle" value="<%=get("StringUrlGoogle")%>"  readonly="true"><br>
        
        <%@include file="../appGlobal/bodyTop.jsp"%>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
        <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&key=AIzaSyBOsad8CCGx7acE9H_c-27JVH-qqKzei20"></script>
        
        <input type="text" id="result" /><br>
        <label class="fzLabel" id="txt"></label><br>
        <script>
            var origin = null;
            var dest = null;
            function calculateDistance() {
                var FullUrl = $('#StringUrlGoogle').val();
                var urls = FullUrl.split("||");
                
                //for (var i = 0; i < urls.length; i++) {
                    var url = urls[0];
                    var x = url.indexOf("origins");
                    var y = url.indexOf("&destinations");
                    var z = url.indexOf("&departure_time");
                    
                    origin = url.substring((x+8), y);
                    var destination = url.substring((y+14), z);                   
                    dest = destination.split("|");
                
                    serv(origin, dest);
                //}
            }
            
            function serv(origin, dest) {
                var service = new google.maps.DistanceMatrixService();
                service.getDistanceMatrix(
                {
                    origins: [origin],
                    destinations: dest,
                    travelMode: google.maps.TravelMode.DRIVING,
                    unitSystem: google.maps.UnitSystem.IMPERIAL,
                    avoidHighways: false,
                    avoidTolls: false
                }, callback);
            }

            function callback(response, status) {
                var obj = response;
                if (status == google.maps.DistanceMatrixStatus.OK) {            
                    var $apiAddress = '../../../api/GoogleMapAPI/submit';
                    $('#result').val(JSON.stringify(obj));
                    var jsonForServer = JSON.stringify(obj);
                    //$('#txt').text(jsonForServer);
                    var data = [];
                    //console.log(JSON.stringify(jsonForServer).length);
                    //alert(jsonForServer);
                    $.post($apiAddress, {json: jsonForServer}).done(function (data) {
                        if(data == 'OK'){
                            //alert( 'sukses' );
                            location.reload();
                        }else{
                            //alert( data ); 
                        }
                    });
                } else {
                    console.log("resend " + status);
                    serv(origin, dest);
                }
            }            
        </script>
    </body>
</html>

<%-- 
    Document   : GoogleMapAPI
    Created on : Apr 23, 2018, 2:35:42 PM
    Author     : dwi.oktaviandi
--%>

<%@page import="org.json.JSONArray"%>
<%@page import="com.fz.util.FZUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../appGlobal/pageTop.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>GoogleMapAPI</title>
    </head>
    <body id="result">   
        <%@include file="../appGlobal/bodyTop.jsp"%>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
        <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&key=AIzaSyBOsad8CCGx7acE9H_c-27JVH-qqKzei20"></script>
        <input id="origin" type="hidden" value="<%=origin%>" />
        <input id="destinations" type="hidden" value="<%=destinations%>" />
        <input id="test" />
        <script>
            function calculateDistance() {
                var origin = $('#origin').val();
                var destination = $('#destinations').val();
                //alert(destination);
                var dest = destination.split("split");
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
                if (status != google.maps.DistanceMatrixStatus.OK) {
                    console.log("resend ");
                    $('#result').html(err);
                } else {
                    var obj = response;

                    $('#result').text(JSON.stringify(obj));

                    /*var origin = response.originAddresses[0];
                    var destination = response.destinationAddresses[0];
                    if (response.rows[0].elements[0].status === "ZERO_RESULTS") {
                        $('#result').html("Better get on a plane. There are no roads between "
                                + origin + " and " + destination);
                    } else {
                        var distance = response.rows[0].elements[0].distance;
                        var distance_value = distance.value;
                        var distance_text = distance.text;
                        var miles = distance_text.substring(0, distance_text.length - 3);
                        $('#result').html("It is " + miles + " miles from " + origin + " to " + destination);
                    }*/
                }
            }            
        </script>
        <%!            
            String origin = "";
            String destinations = "";

            public String run(HttpServletRequest request, HttpServletResponse response,
                     PageContext pc) throws Exception {
                String str = "OK";

                origin = FZUtil.getHttpParam(request, "origin");
                destinations = FZUtil.getHttpParam(request, "destinations");
                if (origin.length() > 0 && destinations.length() > 0) {
                    System.out.println(origin + "()" + destinations);
                    %>
                        <script>
                            calculateDistance();
                        </script>
                    <%! 
                }
                    
                return "";
            }
        %>
        <%=run(request, response, pageContext)%>
        
    </body>
</html>

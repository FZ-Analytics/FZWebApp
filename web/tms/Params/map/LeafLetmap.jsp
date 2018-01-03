<%-- 
    Document   : map
    Created on : Oct 24, 2017, 4:19:31 PM
    Author     : dwi.rangga
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%run(new com.fz.tms.params.map.LeafLetmap());%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://unpkg.com/leaflet@1.2.0/dist/leaflet.css" />

        <style>
            #mapContainer {
                position:absolute;
                top:0;
                right:0;
                bottom:0;
                left: 0;
            }
        </style>
    </head>
    <body>
        <input type="text" hidden="true" id="txt" value="<%=get("testMap")%>"/>
        <%@include file="../appGlobal/bodyTop.jsp"%>
        <div id="mapContainer"></div>
        <%--<script src="https://unpkg.com/leaflet@1.2.0/dist/leaflet.js"></script>
        <script src="AnimatedMarker.js"></script>--%>
        <script src="../appGlobal/leaflet.js"></script>
        <script src="../appGlobal/AnimatedMarker.js"></script>
        <script type="text/javascript">
            var map = L.map('mapContainer').setView([-6.292103, 106.842926], 10);

            var layer = L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
                attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
            }).addTo(map);

            var txt = document.getElementById("txt").value.toString();
            //alert(txt);
            var latlngs2 = eval(txt);

            var line2 = L.polyline(latlngs2, {color: 'blue'});
            map.addLayer(line2);

            var animatedMarker2 = L.animatedMarker(line2.getLatLngs());
            map.addLayer(animatedMarker2);            
            
        </script>
        <h1>Hello World!</h1>
        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

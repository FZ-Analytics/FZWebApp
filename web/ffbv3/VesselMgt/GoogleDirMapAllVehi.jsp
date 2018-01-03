<%-- 
    Document   : testMap
    Created on : Oct 19, 2017, 3:41:53 PM
    Author     : dwi.rangga
--%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            #map {
                width:500px;
                height:500px;
            }
        </style>
    </head>
    <body> 
        <script src="../appGlobal/jquery.min.js?1"></script>        
        <%--<%@include file ="../appGlobal/bodyTop.jsp"%>--%>
        <input type="text" id="txt" value='<%=request.getAttribute("test")%>' hidden="true"/>
        <script>
            $(document).ready(function () {
                console.log("detikawal" + new Date().getTime());
                var map = null;
                var infowindow = new google.maps.InfoWindow();
                var bounds = new google.maps.LatLngBounds();

                //label dibuat beda
                var labels = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
                var labelx = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
                var labelIndex = 0;
                var labelxIndex = 0;
                var tr = null;

                //The list of points to be connected
                var txt = document.getElementById("txt").value;
                //var markers = eval(txt);
                //alert(txt);
                var mark = null;
                var markers = JSON.parse(txt);

                var service = new google.maps.DirectionsService();
                var directionsTaskTimer;

                //    var map;
                function initialize() {
                    mark = markers[0];
                    var mapOptions = {
                        center: new google.maps.LatLng(
                                parseFloat(mark[0].lat),
                                parseFloat(mark[0].lng)),
                        zoom: 30,
                        mapTypeId: google.maps.MapTypeId.ROADMAP
                    };

                    var infoWindow = new google.maps.InfoWindow();
                    map = new google.maps.Map(document.getElementById("map"), mapOptions);
                    var lat_lng = new Array();

                    
                    for (var a = 0; a < markers.length; a++) {
                        mark = null;
                        mark = markers[a];
                        labelIndex = 0;
                        //t = labelx[labelxIndex++ % labelx.length];
                        //sleep(a);
                        var nt = 1;
                        for (var i = 0; i < mark.length; i++) {
                            if ((i + 1) < mark.length) {
                                var clr = mark[i].color;

                                tr = labels[labelIndex++ % labels.length];
                                var src = new google.maps.LatLng(parseFloat(mark[i].lat),
                                        parseFloat(mark[i].lng));
                                var descriptionSrc = mark[i].description;
                                createMarker(src, descriptionSrc, nt++, clr);

                                tr = null;
                                var des = new google.maps.LatLng(parseFloat(mark[i + 1].lat),
                                        parseFloat(mark[i + 1].lng));
                                var descriptionDes = mark[i + 1].description;
                                //createMarker(des, descriptionDes, tr, mark[i].color);
                                //  poly.setPath(path);     

                                //directionsTaskTimer = setInterval(function () {                              
                                recursive(src, des, a, i, clr);
                            }
                        }

                        //sleep();
                    }
                    console.log("final?" + new Date().getTime());
                    //service = new google.maps.DirectionsService();
                }

                function recursive(src, des, a, b, clr)
                {
                    //window.clearInterval(directionsTaskTimer);
                    //console.log("clearInterval " + directionsTaskTimer);
                    var d = new Date();
                    //console.log("route awal " + a + " ; " + b + " status: awal" + "detik " + d.getSeconds());                    
                    //sleep(a);
                    console.log("send src " + src + " des " + des + " " + a + " " + b);
                    setTimeout(function () {
                        service.route({
                            origin: src,
                            destination: des,
                            travelMode: google.maps.DirectionsTravelMode.DRIVING
                        }, function (result, status) {
                            if (status === google.maps.DirectionsStatus.OK) {
                                console.log("recieve src " + src + " des " + des + " " + a + " " + b);
                                var path = new google.maps.MVCArray();
                                var poly = new google.maps.Polyline({
                                    map: map,
                                    strokeColor: '#'+clr//,
                                            //strokeWidth : 1,
                                            //width : 1
                                });
                                for (var i = 0, len = result.routes[0].overview_path.length; i < len; i++) {
                                    path.push(result.routes[0].overview_path[i]);
                                }
                                poly.setPath(path);
                                map.fitBounds(bounds);

                                //console.log("line" + a +" ; " + b);
                            } else if (status == google.maps.DirectionsStatus.OVER_QUERY_LIMIT) {
                                console.log("resend src " + src + " des " + des + " Status" + status);
                                //sleep(src, des);
                                recursive(src, des, a, b, clr);
                            }
                        });
                    }, 3000);
                }

                function createMarker(latLng, str, lbl, clr) {
                    //alert(clr);
                    if(lbl == 1){
                        str = 'DEPO'; 
                        var pinImage = new google.maps.MarkerImage('http://chart.apis.google.com/chart?chst=d_map_xpin_icon&chld=pin|civic-building|ffffff|000000',
                            new google.maps.Size(21, 34),
                            new google.maps.Point(0, 0),
                            new google.maps.Point(10, 34));
                    }else{
                        var pinImage = new google.maps.MarkerImage('http://chart.apis.google.com/chart?chst=d_map_spin&chld=0.5|0|'+clr+'|8|_|'+lbl,
                            new google.maps.Size(21, 34),
                            new google.maps.Point(0, 0),
                            new google.maps.Point(10, 34));
                    }
                    
                    var marker = new google.maps.Marker({
                        position: latLng,
                        map: map,
                        draggable: false,
                        icon: pinImage
                    });
                    bounds.extend(marker.getPosition());
                    google.maps.event.addListener(marker, "click", function (evt) {
                        infowindow.setContent(str);
                        infowindow.open(map, this);
                    });
                }
                console.log("detikakhira" + new Date().getTime());
                google.maps.event.addDomListener(window, 'load', initialize);
                console.log("detikakhirb" + new Date().getTime());
            });
        </script>        
        <script src='https://maps.googleapis.com/maps/api/js?key=<%=get("key")%>'></script>
        <div id='map'></div>
        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

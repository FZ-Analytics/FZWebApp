<%-- 
    Document   : testMap
    Created on : Oct 19, 2017, 3:41:53 PM
    Author     : dwi.rangga
--%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%run(new com.fz.tms.params.map.GoogleDirMap());%>
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
            <%-- 
            #nav_wrapper {

            }

            NAV {

            }

            NAV UL {
                background: #39a9a4;
                background: rgb(57,169,164);
                background: rgba(57,169,164, 0.95);
                color: #fff;
                margin: 4em 5em !important;
                padding: 1.5em;
                position: absolute;
                right: 0;
                text-align: left;
                width: 400px;
                z-index: 99;
            }--%>
        </style>
    </head>
    <body> 
        <script src="../appGlobal/jquery.min.js?1"></script>        
        <%--<%@include file ="../appGlobal/bodyTop.jsp"%>--%>
        <input type="text" id="txt" value='<%=request.getAttribute("test")%>' hidden="true"/>
        <script>
            $(document).ready(function () {

                var map = null;
                var infowindow = new google.maps.InfoWindow();
                var bounds = new google.maps.LatLngBounds();

                //label dibuat beda
                var labels = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
                var labelIndex = 0;
                var tr = null;
                var service = new google.maps.DirectionsService();

                //var parsed = JSON.parse($("#test").text());

                //The list of points to be connected
                var txt = document.getElementById("txt").value;
                //var markers = eval(txt);
                //alert(txt);
                var markers = JSON.parse(txt);/*[{
                 "title": 'Dueros',
                 "lat": '-6.2921',
                 "lng": '106.8429',
                 "description": '1'
                 }, {
                 "title": 'Duero',
                 "lat": '-6.2393788',
                 "lng": '106.8481213',
                 "description": '2'
                 }, {
                 "title": 'Reyes Catolicos',
                 "lat": '-6.29221552',
                 "lng": '106.84301975',
                 "description": '3'
                 }, {
                 "title": 'Guadarrama',
                 "lat": '-6.3043031',
                 "lng": '106.8447768',
                 "description": '4'
                 }, {
                 "title": 'Dueross',
                 "lat": '-6.2921',
                 "lng": '106.8429',
                 "description": '5'
                 }]*/
                ;


                //    var map;
                function initialize() {
                    var mapOptions = {
                        center: new google.maps.LatLng(
                                parseFloat(markers[0].lat),
                                parseFloat(markers[0].lng)),
                        zoom: 15,
                        mapTypeId: google.maps.MapTypeId.ROADMAP
                    };

                    var infoWindow = new google.maps.InfoWindow();
                    map = new google.maps.Map(document.getElementById("map"), mapOptions);
                    var lat_lng = new Array();
                    /*
                     var marker = new google.maps.Marker({
                     position: map.getCenter(),
                     map: map,
                     label: labels[labelIndex++ % labels.length],
                     draggable: false
                     });
                     bounds.extend(marker.getPosition());
                     google.maps.event.addListener(marker, "click", function (evt) {
                     infowindow.setContent("coord:" + marker.getPosition().toUrlValue(6));
                     infowindow.open(map, marker);
                     });
                     */
                    var nt = 1;
                    for (var i = 0; i < markers.length; i++) {
                        if ((i + 1) < markers.length) {
                            tr = labels[labelIndex++ % labels.length];
                            var src = new google.maps.LatLng(parseFloat(markers[i].lat),
                                    parseFloat(markers[i].lng));
                            var descriptionSrc = markers[i].description;
                            createMarker(src, descriptionSrc, nt++, markers[i].channel);

                            tr = null;
                            var des = new google.maps.LatLng(parseFloat(markers[i + 1].lat),
                                    parseFloat(markers[i + 1].lng));
                            var descriptionDes = markers[i + 1].description;
                            //createMarker(des, descriptionDes);
                            var d = new Date();
                            //console.log("route awal " + i +" ;status: awal" + " ;detik "+d.getSeconds());
                            //  poly.setPath(path);
                            recursive(src, des, i);
                        }
                    }
                }

                function recursive(src, des, b)
                {
                    //window.clearInterval(directionsTaskTimer);
                    //console.log("clearInterval " + directionsTaskTimer);
                    var d = new Date();
                    //console.log("route awal " + a + " ; " + b + " status: awal" + "detik " + d.getSeconds());                    
                    //sleep(a);
                    console.log("send src " + src + " des " + des + " " + b);
                    setTimeout(function () {
                        service.route({
                            origin: src,
                            destination: des,
                            travelMode: google.maps.DirectionsTravelMode.DRIVING
                        }, function (result, status) {
                            if (status === google.maps.DirectionsStatus.OK) {
                                console.log("recieve src " + src + " des " + des + " " + b);
                                var path = new google.maps.MVCArray();
                                var poly = new google.maps.Polyline({
                                    map: map,
                                    strokeColor: '#F3443C',
                                    strokeWidth: 1,
                                    width: 1
                                });
                                for (var i = 0, len = result.routes[0].overview_path.length; i < len; i++) {
                                    path.push(result.routes[0].overview_path[i]);
                                }

                                poly.setPath(path);
                                map.fitBounds(bounds);

                                //https://maps.googleapis.com/maps/api/directions/json?origin=-6.32302389,106.8199285&destination=-6.387107,106.82734
                                google.maps.event.addListener(poly, "click", function (evt) {
                                    infowindow.setContent('poly');
                                    infowindow.setPosition(evt.latLng);
                                    infowindow.open(map);
                                });

                                //console.log("line" + a +" ; " + b);
                            } else if (status == google.maps.DirectionsStatus.OVER_QUERY_LIMIT) {
                                console.log("resend src " + src + " des " + des + " Status" + status);
                                //sleep(src, des);
                                recursive(src, des, b);
                            }
                        });
                    }, 3000);
                }

                function createMarker(latLng, str, lbl, channel) {
                    if (lbl == 1) {
                        lbl = 'D';
                        var pinImage = new google.maps.MarkerImage('http://chart.apis.google.com/chart?chst=d_map_xpin_icon&chld=pin|civic-building|ffffff|000000',
                                new google.maps.Size(21, 34),
                                new google.maps.Point(0, 0),
                                new google.maps.Point(10, 34));
                    } else {
                        var pinImage = new google.maps.MarkerImage('http://chart.apis.google.com/chart?chst=d_map_spin&chld=0.5|0|ff5050|8|b|'+ lbl,
                                new google.maps.Size(21, 34),
                                new google.maps.Point(0, 0),
                                new google.maps.Point(10, 34));
                    }

                    var marker = new google.maps.Marker({
                        position: latLng,
                        map: map,
                        //label: tr,
                        draggable: false,
                        icon: pinImage
                    });
                    bounds.extend(marker.getPosition());
                    google.maps.event.addListener(marker, "click", function (evt) {
                        infowindow.setContent(str);
                        infowindow.open(map, this);
                    });
                }
                google.maps.event.addDomListener(window, 'load', initialize());
            });
        </script>        
        <script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyB_lu1GKTDK0Lc08lT6p1f4WFZaFvILIGY"></script>
        <%--
        <div id="wrapper">
            <div id="nav_wrapper">
                <nav>
                    <ul class="vertical">
                        <div id="txtHere"><a>asdasdasd</a></div>
                    </ul>
                </nav>
            </div>
        --%>
        <div id='map'></div>
        <%--</div>--%>

        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

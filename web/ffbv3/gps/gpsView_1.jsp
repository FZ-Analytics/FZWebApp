<%-- 
    Document   : hvsEstmList
    Created on : Sep 23, 2017, 6:14:45 AM
--%>
<%@page import="com.fz.util.FZUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%!
    String getCpo(String dt, String img, int w, int h) throws Exception {
    
        String sql = 
                "select objectname, lon, lat"
                + ", date_format(endtime,'%H:%i') tm"
                + " from fbGpsCpo"
                + " where date_format(endtime,'%Y-%m-%d') = '" + dt + "'"
                + " and objectname like 'BINT TK 0%'"
                + " order by objectname, date_format(endtime,'%H:%i')"
                ;
        return getSqlData(sql, img, w, h);
    }
    
    String getBinTruck(String dt, String img, int w, int h) throws Exception {
        String sql = 
                    "select vehicleID, longitude, latitude"
                    + ", date_format(endDate,'%H:%i') tm"
                    + " from fbGpsTracker"
                    + " where date_format(endDate,'%Y-%m-%d') = '" + dt + "'"
                    + " order by vehicleID, date_format(endDate,'%H:%i')"
                    ;

        return getSqlData(sql, img, w, h);
    }
    
    String getGrabber(String dt, String img, int w, int h) throws Exception {
        String sql = 
                    "select name, longitude, latitude"
                    + ", date_format(endDate,'%H:%i') tm"
                    + " from fbGpsGraber"
                    + " where date_format(endDate,'%Y-%m-%d') = '" + dt + "'"
                    + " and name like 'Graber%'"
                    + " order by name, date_format(endDate,'%H:%i')"
                    ;

        return getSqlData(sql, img, w, h);
    }
    
    String getHarvester(String dt, String img, int w, int h) throws Exception {
        String sql = 
                    "select name, longitude, latitude"
                    + ", date_format(endDate,'%H:%i') tm"
                    + " from fbGpsGraber"
                    + " where date_format(endDate,'%Y-%m-%d') = '" + dt + "'"
                    + " and name not like 'Graber%'"
                    + " order by name, date_format(endDate,'%H:%i')"
                    ;

        return getSqlData(sql, img, w, h);
    }
    
    String getSqlData(String sql, String img, int w, int h) throws Exception {
        
        String o = ""; // output
        javax.naming.InitialContext ctx = 
                new javax.naming.InitialContext();
        javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup(
                "java:/comp/env/" + "jdbc/fz");
        try (java.sql.Connection con = ds.getConnection();
                java.sql.PreparedStatement ps 
                    = con.prepareStatement(sql);
                java.sql.ResultSet rs = ps.executeQuery()
                ) {

            while (rs.next()){

                o += "\naddPos("
                        + "'" + FZUtil.getRsString(rs, 1, "") + "'"
                        + ",'" + FZUtil.getRsString(rs, 1, "") + "'"
                        + ",'" + FZUtil.getRsString(rs, 2, "") + "'"
                        + ",'" + FZUtil.getRsString(rs, 3, "") + "'"
                        + ",'" + FZUtil.getRsString(rs, 4, "") + "'"
                        + ",'" + img + "'," + w + "," + h
                        + ");"
                        ;
//../img/pickup-front-view.png
            }
        }
        return o;
    }
    
    String getData(HttpServletRequest request) {
        String o = "";
        try {

            String dt = FZUtil.getHttpParam(request, "dt");
            if (dt.length()==0) return "// No date";
            
            o += getBinTruck(dt,"../img/truck-green.png",15,25);
            o += getCpo(dt,"../img/truck-yellow.png",15,25);
            o += getGrabber(dt,"../img/red-tractor.svg",15,25);
            o += getHarvester(dt,"../img/people2.png",8,15);
            
            o += "\naddPos("
                    + "'Vessel1'"
                    + ",'Vessel1'"
                    + ",'106.16756'"
                    + ",'-2.08923'"
                    + ",'09:00'"
                    + ",'../img/ship.svg.png',45,35"
                    + ");"
                    ;

        }
        catch(Exception e){
            String m = FZUtil.toStackTraceText(e);
            o = "Error: " + m;
        }
        return o;
    }

%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>GPS</title>
	<!==link rel="stylesheet" href="http://cdn.leafletjs.com/leaflet-0.6.4/leaflet.css" /==>
	<!==[if lte IE 8]>
            <link rel="stylesheet" href="http://cdn.leafletjs.com/leaflet-0.6.4/leaflet.ie.css" />
        <![endif]==>
    </head>
    <body>
    <%@include file="../appGlobal/bodyTop.jsp"%>

        <h3><!--GPS Tracking--></h3>

        <div id='dvTimeCont'>
            
            Time: <input id='txTime' value='07:05' style='height:60px; width:200px; font-size: 35pt; font-weight:bolder ;color:blue; border:none; border-bottom:1px solid #ccc'/>
            
            <script>$('#txTime').attr('size', $('#txTime').val().length);</script>
            
            &nbsp;&nbsp;
            <button id='btStartStop'>Stop</button>
            
            &nbsp;&nbsp;&nbsp;&nbsp;
            Anim Speed (ms): <input id='txSpeed' value='300' style='width: 60px; border:none; border-bottom:1px solid #ccc'/>
            
            &nbsp;&nbsp;
            Ends: <input id='txEndTime' value='23:00' style='width: 60px; border:none; border-bottom:1px solid #ccc'/>
            
        </div>
        
        <br>
        <div id="map" style="width: 100%; height: 500px; visibility:hidden"></div>
        
        <script src="colors.js"></script>
    
	<link rel="stylesheet" href="https://unpkg.com/leaflet@1.0.3/dist/leaflet.css" />
	<script src="https://unpkg.com/leaflet@1.0.3/dist/leaflet.js"></script>
	<!==script src="http://cdn.leafletjs.com/leaflet-0.6.4/leaflet.js"></script==>
    
	<script src="catiline.js"></script>
	<script src="leaflet.shpfile.js"></script>
	<script>
	
            // -------------------- Leaflet ------------------------------
            var m = L.map('map').setView([-1.8760567, 105.5099874], 12);
            //var m = L.map('map').setView([42.09618442380296, -71.5045166015625], 8);
            var watercolor = L.tileLayer('http://{s}.tiles.wmflabs.org/bw-mapnik/{z}/{x}/{y}.png', {
                    maxZoom: 18,
                    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
            }).addTo(m);
            // -------------------- Leaflet ------------------------------
            
            
            var vhs = []; // vehicle objects
//            var hh;
//            var mm;
//            var tm;
            var boIsRunning = true;
    
            function debugIt(s){
                console.log(s);
            }
            
            var curColorIndex = 0;
            function getColor(){
                if (++curColorIndex >= 12) curColorIndex = 0;
                return colors[curColorIndex];
            }
            
            function getAnimTime(){
                return $('#txTime').val();
            }
            
            function getAnimEndTime(){
                return $('#txEndTime').val();
            }
            
            function setAnimTime(tm){
                $('#txTime').val(tm);
            }
            
            function addPos(vhID, vhNm, ln, lt, tm, icn, icw, ich){
                
                // find vh
                var vh = null;
                for ( var i = 0 ; i<vhs.length ; i++){
                    
                    var vh2 = vhs[i];
                    
                    if (vh2.vhID === vhID){
                        vh = vh2;
                        break;
                    }
                }
                debugIt("addPos find " + vhID + ", found " + vh);
                
                // if none, create
                if (vh == null){
                    
                    debugIt("if null entered");
                    
                    vh = {};
                    vh.vhID = vhID;
                    vh.vhNm = vhNm;
                    vh.color = getColor();
                    vh.marker = 'none';
                    vh.icn = icn;
                    vh.icw = icw;
                    vh.ich = ich;
                    vh.lastPosIdx = 0;
                    vh.poss = [];
                    vhs.push(vh);
                }
                // add pos to vh
                var pos = {};
                pos.ln = ln;
                pos.lt = lt;
                pos.tm = tm;
                vh.poss.push(pos);
            }
            
            var markers = new L.FeatureGroup();
            //var trails = new L.FeatureGroup();
            
            function drawPoss(tm, draw){
                
                debugIt("drawPoss");
                
                if (draw){
                    m.removeLayer(markers);
                    markers = new L.FeatureGroup();
                }
                
                // for vh
                for ( var i=0; i<vhs.length; i++){
                    
                    debugIt("got vh " + i);
                
                    // for pos
                    var vh = vhs[i];
                    
                    for ( var j=0; j<vh.poss.length; j++){
                        
                        debugIt("got pos " + j);
                
                        // if tm, draw
                        var pos = vh.poss[j];
                        
                        //debugIt("pos.tm " + pos.tm + ", tm " + getAnimTime());
                        if (pos.tm === tm){
                            
                            vh.lastPosIdx = j;
                        }
                    }
                    if (draw) drawPos(vh);
                }
                if (draw) m.addLayer(markers);
            }

            function drawPos(vh){
                
//                debugIt("draw pos.tm " + pos.tm + ", tm " + getAnimTime());                
                
                // determine last pos
                var pos;
                if (vh.lastPosIdx === -1){
                    return;
                }
                else {
                    pos = vh.poss[ vh.lastPosIdx ];
                }
                
                // create new pos layer
                var posLyr = new L.FeatureGroup;
                
                // create circle marker
                vh.color = 'yellow';
                var crc1 = L.circle(
                    [pos.lt, pos.ln]
                    ,{
                        color: vh.color
                        , fillColor: vh.color
                        , fillOpacity: 0.5
                        , radius: 50
                    }
                );
        
                // create object icon
                var theIcon = L.icon({
                        iconUrl: vh.icn,
                        shadowUrl: '',

                        iconSize:     [vh.icw, vh.ich], // size of the icon
                        shadowSize:   [0, 0], // size of the shadow
                        iconAnchor:   [4, 4], // point of the icon which will correspond to marker's location
                        shadowAnchor: [0, 0],  // the same for the shadow
                        popupAnchor:  [0, 0] // point the popup should open relative to iconAnchor
                });
                
                // add the icon to pos layer
                L.marker([pos.lt, pos.ln], {icon: theIcon}).addTo(posLyr);
                
                // add the circle to pos layer
                posLyr.addLayer(crc1);
        
                // add info to pos lyaer
                var msg = vh.vhNm 
                        + "<br>lon " + pos.ln 
                        + "<br>lat " + pos.lt;
                posLyr.bindPopup(msg);

                // save new marker
                markers.addLayer(posLyr);
                
            }

            function testAnimData(){
                
                var ln = 105.500;
                var lt = -1.876;
                for (var i=1;i<=59;i++){
                    ln += 0.001 * (Math.random() < 0.8 ? -1 : 1); 
                    lt += 0.001 * (Math.random() < 0.8 ? -1 : 1); 
                    
                    addPos('truck1','truck1',ln,lt,'07:' + pad(i,2)
                        ,'../img/tractor-back.png'
                        ,15,25);
//                    addPos('o1','o2',ln,lt,'07:' + pad(i,2)
//                        ,''
//                        ,25,30);
                }
                ln = 105.500;
                lt = -1.876;
                for (var i=3;i<=59;i+=3){
                    ln += 0.001 * (Math.random() < 0.8 ? 1 : -1); 
                    lt += 0.001 * (Math.random() < 0.8 ? 1 : -1); 
                    
                    addPos('truck2','truck2',ln,lt,'07:' + pad(i,2)
                        ,'../img/pickup-front-view.png'
                        ,15,25);
                }
                ln = 105.509;
                lt = -1.870;
                for (var i=3;i<=59;i+=3){
                    ln += 0.001 * (Math.random() < 0.8 ? 1 : -1); 
                    lt += 0.001 * (Math.random() < 0.8 ? 1 : -1); 
                    
                    addPos('p1','p1',ln,lt,'07:' + pad(i,2)
                        ,'../img/green-man-hi.png'
                        ,10,13);
                }
                
            }

            function initData(){
                
                debugIt("init");
                
                <%=getData(request)%>
                        
                debugIt("vhs len = " + vhs.length);
                
            }
            
            function pad (str, max) {
                str = str.toString();
                return str.length < max ? pad("0" + str, max) : str;
            }

            function OLD_incrementTime(){

                // get start time
                var tm = getAnimTime();
                tm = tm.trim();
                var hh = tm.substr(0,2);
                var mm = tm.substr(3,5);
                debugIt("tm " + tm + ", hh " + hh + ", mm " + mm );
                
                // get end time
                var etm = getAnimEndTime();
                etm = etm.trim();
                var ehh = etm.substr(0,2);
                var emm = etm.substr(3,5);
                debugIt("etm " + etm + ", ehh " + ehh + ", emm " + emm );
                
                // if end time
                if (hh === ehh){
                    if (mm === emm){
                        return false;
                    }
                }
                
                // if last min
                if (mm === '59'){ // should be 59
                    // 
                    // if last hour
                    if (hh === '23'){ // should be 23
                        // stop
                        return false;
                    }
                    else { // not hour boundary
                        // incre hour
                        hh++;
                        mm = 0;
                    }
                }
                else { // not min boundary
                    // incre min
                    mm++;
                }
                
                // set new time
                tm = pad(hh,2) + ':' + pad(mm,2);
                setAnimTime(tm);
                
                return true;
            }

            function tryIncrementTime(){

                var tm = incrementTime(getAnimTime());
                if (tm === getAnimEndTime()){
                    return false;
                }
                else if (tm === '00:00') {
                    return false;
                }
                else {
                    setAnimTime(tm);
                    return true;
                }
            }

            function incrementTime(tm){

                // get start time
                tm = tm.trim();
                var hh = tm.substr(0,2);
                var mm = tm.substr(3,5);
                debugIt("tm " + tm + ", hh " + hh + ", mm " + mm );
                
                // if last min
                if (mm === '59'){ // should be 59
                    // 
                    // if last hour
                    if (hh === '23'){ // should be 23
                        // stop
                        return '00:00';
                    }
                    else { // not hour boundary
                        // incre hour
                        hh++;
                        mm = 0;
                    }
                }
                else { // not min boundary
                    // incre min
                    mm++;
                }
                
                // set new time
                var rtm = pad(hh,2) + ':' + pad(mm,2);
                
                return rtm;
            }

            function nextTimer() {
                
                debugIt("next timer");
                
                if (!boIsRunning) return;
                
                // set next timer
                var speed = $('#txSpeed').val();
                var tmr = window.setInterval(
                    function(){
                        
                        window.clearInterval( tmr );

                        var timeIncremented = tryIncrementTime();
                        debugIt("time incr = " + timeIncremented);

                        if (timeIncremented){
                            
                            drawPoss(getAnimTime(), true);
                            nextTimer();
                        }
                        else {
                            
                            //alert('done');
                            $('#btStartStop').text('Start');
                        }
                    }
                    , speed
                );

            }
            
            function animFirstTime(){
                
                // load init data
                initData();
                animIt();
            }
            
            function animIt() {
                
                //debugIt("anim");
                
                // init locations of objects
                for (var i=0; i<vhs.length; i++){
                    
                    var vh = vhs[i];
                    vh.lastPosIdx = 0;
                }
                
                // toggle start stop button
                $('#btStartStop').text('Stop');

                // set timer
                nextTimer();
            }

            function drawLyr(){
                
                debugIt("loading shp");
                var shpfile = new L.Shapefile('bangka.zip', {
                        onEachFeature: function(feature, layer) {
                                if (feature.properties) {
                                        layer.bindPopup(Object.keys(feature.properties).map(function(k) {
                                                return k + ": " + feature.properties[k];
                                        }).join("<br />"), {
                                                maxHeight: 200
                                        });
                                }
                        }
                });
                shpfile.addTo(m);

                // show map
                document.getElementById("map").style.visibility = 'visible'; 
                shpfile.once("data:loaded", function() {
                        debugIt("finished loaded shapefile");

                        animFirstTime();
                });

            }
            
            $('#btStartStop').click(function(){
                
                boIsRunning = !boIsRunning;
                
                if (boIsRunning){
                    
                    animIt();
                }
                else {
                    
                    $('#btStartStop').text('Start');
                }
                
            });
            
            drawLyr();
            
        </script>
        
        
    <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

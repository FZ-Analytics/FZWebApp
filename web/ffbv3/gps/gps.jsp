<%-- 
    Document   : hvsEstmList
    Created on : Sep 23, 2017, 6:14:45 AM
--%>
<%@page import="com.fz.util.FZUtil"%>
<%@page import="com.fz.generic.Db"%>
<%@page import="com.fz.ffbv3.service.hvsEstm.HvsEstm"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%!
    String getData(HttpServletRequest request) {
    
        String o = ""; // output
        try {

            String dt = FZUtil.getHttpParam(request, "dt");
            String sql = 
                    "select name, latitude, longitude"
                    + ", date_format(endDate,'%H:%i') tm"
                    + " from fbGpsGraber"
                    //+ " where str_to_date(endDate,'%Y-%m-%d') = '" + dt + "'"
                    + " order by name, date_format(endDate,'%H:%i')"
                    + " limit 10000"
                    ;

            try (java.sql.Connection con = (new Db()).getConnection("jdbc/fz");
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
                            + ");"
                            ;

                }
            } 

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

            function initData(){
                
                debugIt("init");
/*                
//                var ln = 105.500;
//                var lt = -1.876;
//                for (var i=1;i<=59;i++){
//                    ln += 0.001 * (Math.random() < 0.8 ? -1 : 1); 
//                    lt += 0.001 * (Math.random() < 0.8 ? -1 : 1); 
//                    addPos('truck1','truck1',ln,lt,'07:' + pad(i,2));
//                }
//                ln = 105.500;
//                lt = -1.876
//                for (var i=3;i<=59;i+=3){
//                    ln += 0.001 * (Math.random() < 0.8 ? 1 : -1); 
//                    lt += 0.001 * (Math.random() < 0.8 ? 1 : -1); 
//                    addPos('truck2','truck2',ln,lt,'07:' + pad(i,2));
//                }
*/                
            
                addPos('v1','v1',105.5099874,-1.8760567,'07:12');
//                addPos('v2','v2',105.498,-1.89399,'07:41');
                
                <%=getData(request)%>

                debugIt("vhs len = " + vhs.length);
                
            }


var _0xe7f5=['<br>lon\x20','<br>lat\x20','bindPopup','new\x20circle\x20','addLayer','substr','tm\x20',',\x20hh\x20','trim','etm\x20',',\x20ehh\x20',',\x20emm\x20','next\x20timer','#txSpeed','clearInterval','time\x20incr\x20=\x20','#btStartStop','text','Start','anim','Stop','loading\x20shp','Shapefile','keys','properties','addTo','getElementById','style','visibility','visible','once','data:loaded','click','map','tileLayer','http://{s}.tiles.wmflabs.org/bw-mapnik/{z}/{x}/{y}.png','&copy;\x20<a\x20href=\x22http://www.openstreetmap.org/copyright\x22>OpenStreetMap</a>','log','val','#txEndTime','#txTime','length','vhID','addPos\x20find\x20',',\x20found\x20','if\x20null\x20entered','vhNm','color','marker','none','poss','push','FeatureGroup','got\x20pos\x20','remove\x20pos.tm\x20',',\x20tm\x20','removeLayer','draw\x20pos.tm\x20','<br>time\x20'];(function(_0xb6b285,_0x596e01){var _0x11393c=function(_0x506469){while(--_0x506469){_0xb6b285['push'](_0xb6b285['shift']());}};_0x11393c(++_0x596e01);}(_0xe7f5,0x97));var _0x5e7f=function(_0x593b20,_0x53e495){_0x593b20=_0x593b20-0x0;var _0x31763b=_0xe7f5[_0x593b20];return _0x31763b;};var m=L[_0x5e7f('0x0')](_0x5e7f('0x0'))['setView']([-1.8760567,105.5099874],0xc);var watercolor=L[_0x5e7f('0x1')](_0x5e7f('0x2'),{'maxZoom':0x12,'attribution':_0x5e7f('0x3')})['addTo'](m);var vhs=[];var boIsRunning=!![];function debugIt(_0x5d0d81){console[_0x5e7f('0x4')](_0x5d0d81);}var curColorIndex=0x0;function getColor(){if(++curColorIndex>=0xc)curColorIndex=0x0;return colors[curColorIndex];}function getAnimTime(){return $('#txTime')[_0x5e7f('0x5')]();}function getAnimEndTime(){return $(_0x5e7f('0x6'))[_0x5e7f('0x5')]();}function setAnimTime(_0x2c42fa){$(_0x5e7f('0x7'))['val'](_0x2c42fa);}function addPos(_0x72f58b,_0x4aadcc,_0x545cfd,_0x34bb03,_0x12082a){var _0x4d8b43=null;for(var _0x59f891=0x0;_0x59f891<vhs[_0x5e7f('0x8')];_0x59f891++){var _0x157d0e=vhs[_0x59f891];if(_0x157d0e[_0x5e7f('0x9')]===_0x72f58b){_0x4d8b43=_0x157d0e;break;}}debugIt(_0x5e7f('0xa')+_0x72f58b+_0x5e7f('0xb')+_0x4d8b43);if(_0x4d8b43==null){debugIt(_0x5e7f('0xc'));_0x4d8b43={};_0x4d8b43[_0x5e7f('0x9')]=_0x72f58b;_0x4d8b43[_0x5e7f('0xd')]=_0x4aadcc;_0x4d8b43[_0x5e7f('0xe')]=getColor();_0x4d8b43[_0x5e7f('0xf')]=_0x5e7f('0x10');_0x4d8b43[_0x5e7f('0x11')]=[];vhs[_0x5e7f('0x12')](_0x4d8b43);}var _0x3100e5={};_0x3100e5['ln']=_0x545cfd;_0x3100e5['lt']=_0x34bb03;_0x3100e5['tm']=_0x12082a;_0x3100e5['vh']=_0x4d8b43;_0x4d8b43[_0x5e7f('0x11')][_0x5e7f('0x12')](_0x3100e5);}var markers=new L[(_0x5e7f('0x13'))]();function drawPoss(){debugIt('drawPoss');for(var _0x1a1b17=0x0;_0x1a1b17<vhs[_0x5e7f('0x8')];_0x1a1b17++){debugIt('got\x20vh\x20'+_0x1a1b17);var _0x211e6e=vhs[_0x1a1b17];for(var _0x525986=0x0;_0x525986<_0x211e6e[_0x5e7f('0x11')][_0x5e7f('0x8')];_0x525986++){debugIt(_0x5e7f('0x14')+_0x525986);var _0x16fd60=_0x211e6e[_0x5e7f('0x11')][_0x525986];if(_0x16fd60['tm']===getAnimTime()){drawPos(_0x16fd60);}}}}function clearPos(_0x40c5d5){var _0x890892=_0x40c5d5['vh']['marker'];if(_0x890892===_0x5e7f('0x10')){}else{debugIt(_0x5e7f('0x15')+_0x40c5d5['tm']+_0x5e7f('0x16')+getAnimTime());markers[_0x5e7f('0x17')](_0x890892);}}function drawPos(_0xe7f92e){debugIt(_0x5e7f('0x18')+_0xe7f92e['tm']+_0x5e7f('0x16')+getAnimTime());var _0x4569e6=L['circle']([_0xe7f92e['lt'],_0xe7f92e['ln']],{'color':_0xe7f92e['vh'][_0x5e7f('0xe')],'fillColor':_0xe7f92e['vh'][_0x5e7f('0xe')],'fillOpacity':0.5,'radius':0x64});var _0x317bea=_0xe7f92e['vh'][_0x5e7f('0xd')]+_0x5e7f('0x19')+_0xe7f92e['tm']+_0x5e7f('0x1a')+_0xe7f92e['ln']+_0x5e7f('0x1b')+_0xe7f92e['lt'];_0x4569e6[_0x5e7f('0x1c')](_0x317bea);clearPos(_0xe7f92e);debugIt(_0x5e7f('0x1d')+_0x317bea);_0xe7f92e['vh']['marker']=_0x4569e6;markers[_0x5e7f('0x1e')](_0x4569e6);}function pad(_0x1c1036,_0x816d5a){_0x1c1036=_0x1c1036['toString']();return _0x1c1036[_0x5e7f('0x8')]<_0x816d5a?pad('0'+_0x1c1036,_0x816d5a):_0x1c1036;}function incrementTime(){var _0x22bf36=getAnimTime();_0x22bf36=_0x22bf36['trim']();var _0x332272=_0x22bf36[_0x5e7f('0x1f')](0x0,0x2);var _0x19a443=_0x22bf36[_0x5e7f('0x1f')](0x3,0x5);debugIt(_0x5e7f('0x20')+_0x22bf36+_0x5e7f('0x21')+_0x332272+',\x20mm\x20'+_0x19a443);var _0x47d2f2=getAnimEndTime();_0x47d2f2=_0x47d2f2[_0x5e7f('0x22')]();var _0x455145=_0x47d2f2['substr'](0x0,0x2);var _0x19fccd=_0x47d2f2[_0x5e7f('0x1f')](0x3,0x5);debugIt(_0x5e7f('0x23')+_0x47d2f2+_0x5e7f('0x24')+_0x455145+_0x5e7f('0x25')+_0x19fccd);if(_0x332272===_0x455145){if(_0x19a443===_0x19fccd){return![];}}if(_0x19a443==='59'){if(_0x332272==='23'){return![];}else{_0x332272++;_0x19a443=0x0;}}else{_0x19a443++;}_0x22bf36=pad(_0x332272,0x2)+':'+pad(_0x19a443,0x2);setAnimTime(_0x22bf36);return!![];}function nextTimer(){debugIt(_0x5e7f('0x26'));if(!boIsRunning)return;var _0x54fd72=$(_0x5e7f('0x27'))[_0x5e7f('0x5')]();var _0x1da401=window['setInterval'](function(){window[_0x5e7f('0x28')](_0x1da401);var _0x194d40=incrementTime();debugIt(_0x5e7f('0x29')+_0x194d40);if(_0x194d40){drawPoss();nextTimer();}else{$(_0x5e7f('0x2a'))[_0x5e7f('0x2b')](_0x5e7f('0x2c'));}},_0x54fd72);}function animIt(){debugIt(_0x5e7f('0x2d'));initData();$(_0x5e7f('0x2a'))[_0x5e7f('0x2b')](_0x5e7f('0x2e'));m[_0x5e7f('0x17')](markers);markers=new L['FeatureGroup']();m['addLayer'](markers);nextTimer();}function drawLyr(){debugIt(_0x5e7f('0x2f'));var _0x5aeab4=new L[(_0x5e7f('0x30'))]('bangka.zip',{'onEachFeature':function(_0x3f6a2d,_0x425b4f){if(_0x3f6a2d['properties']){_0x425b4f[_0x5e7f('0x1c')](Object[_0x5e7f('0x31')](_0x3f6a2d[_0x5e7f('0x32')])[_0x5e7f('0x0')](function(_0xbcb7c7){return _0xbcb7c7+':\x20'+_0x3f6a2d[_0x5e7f('0x32')][_0xbcb7c7];})['join']('<br\x20/>'),{'maxHeight':0xc8});}}});_0x5aeab4[_0x5e7f('0x33')](m);document[_0x5e7f('0x34')](_0x5e7f('0x0'))[_0x5e7f('0x35')][_0x5e7f('0x36')]=_0x5e7f('0x37');_0x5aeab4[_0x5e7f('0x38')](_0x5e7f('0x39'),function(){debugIt('finished\x20loaded\x20shapefile');animIt();});}$(_0x5e7f('0x2a'))[_0x5e7f('0x3a')](function(){boIsRunning=!boIsRunning;if(boIsRunning){animIt();}else{$(_0x5e7f('0x2a'))[_0x5e7f('0x2b')](_0x5e7f('0x2c'));}});drawLyr();            
        </script>
        
        
    <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

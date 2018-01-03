<%-- 
    Document   : hvsEstmList
    Created on : Sep 23, 2017, 6:14:45 AM
--%>
<%@page import="com.fz.util.EObject"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.fz.util.FZUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%!
    HashMap<String, EObject> objs = new HashMap<String, EObject>();
    String fbDb = "jdbc/fz";
    String tmsDb = "jdbc/fztms";

    String getDt(HttpServletRequest request) throws Exception {
        return FZUtil.getHttpParam(request, "dt");
    }
    
    String getCpo(HttpServletRequest request) 
        throws Exception {
    
        String id = "CpoTruck";
        if (!isObjSel(request, id)) return "";
        
        String sql = 
                "select objectname, objectname, lon, lat"
                + ", date_format(endtime,'%H:%i') tm"
                + ", '' html"
                + " from fbGpsCpo"
                + " where date_format(endtime,'%Y-%m-%d') = '" 
                    + getDt(request) +  "'"
                + " and objectname like 'BINT TK 0%'"
                + " order by objectname, date_format(endtime,'%H:%i')"
                ;
        return getSqlData(sql, id, fbDb);
    }
    
    String getBinTruck(HttpServletRequest request) 
        throws Exception {
    
        String id = "BinTruck";
        if (!isObjSel(request, id)) return "";
        
        String sql = 
                    "select t.vehicleID, v.vehicleName, t.longitude, t.latitude"
                    + ", date_format(t.endDate,'%H:%i') tm"
                    //+ ", 'test'"
                    + ", concat('Default ',v.DefDivCode,', ',v.remark) html"
                    + " from fbGpsTracker t"
                    + " left outer join fbVehicle v "
                        + " on t.vehicleID = v.vehicleID"
                    + " where date_format(endDate,'%Y-%m-%d') like '" 
                        + getDt(request) +  "%'"
                    + " order by vehicleID, date_format(endDate,'%H:%i')"
                    ;

        return getSqlData(sql, id, fbDb);
    }
    
    String getGrabber(HttpServletRequest request) 
            throws Exception {
    
        String id = "Tractor";
        if (!isObjSel(request, id)) return "";
        
        String sql = 
                    "select name, name, longitude, latitude"
                    + ", date_format(endDate,'%H:%i') tm"
                    + ", concat(divID,', ',blockID) html"
                    + " from fbGpsGrabberSim"
                    + " where date_format(endDate,'%Y-%m-%d') like '" 
                            + getDt(request) +  "%'"
                    + " and name = 'GB3'"
                    //+ " and substring(EndDate,16,2) in ('0:','3:','6:')"
                    + " order by name, date_format(endDate,'%H:%i')"
                    ;

        return getSqlData(sql, id, fbDb);
    }
    
    String getHarvester(HttpServletRequest request) 
            throws Exception {
    
        String id = "Harvester";
        if (!isObjSel(request, id)) return "";
        
        String sql = 
                    "select name, name, longitude, latitude"
                    + ", date_format(endDate,'%H:%i') tm"
                    + ", concat(divID,', ',blockID,'<br><a target1=\"blank\" href=\"../Dashboard/HvsLeaderBoard.jsp?hid=',name,'\">View in Leader Board</a>') html"
                    + " from fbGpsHvsSim"
                    + " where date_format(endDate,'%Y-%m-%d') like '" 
                        + getDt(request) +  "%'"
                    + " and divID in ('BINE3')"
                    //+ " and name not like 'Graber%'"
                    //+ " and right(LocationID,1) in ('1')"
                    + " order by name, date_format(endDate,'%H:%i')"
                    ;

        return getSqlData(sql, id, fbDb);
    }
    
    String getMill(HttpServletRequest request) 
            throws Exception {
    
        String id = "Mill";
        if (!isObjSel(request, id)) return "";
        EObject ob = objs.get(id);
        
        String objPos = 
            "\nap("
                    + "'LWSM'"
                    + ",'LWSM'"
                    + ",'Mill'"
                    + ",'105.477'"
                    + ",'-1.9012'"
                    + ",'00:00'"
                    + ",'" + ob.getStr("img") 
                    + "'," + ob.getInt("width")  
                    + "," + ob.getInt("height") 
                    + ",'<br>Leidong West <a href=\"a\">Mill</a>');"
                    ;
        return objPos;
    }
    
    String getPool(HttpServletRequest request) 
            throws Exception {
    
        String id = "Pool";
        if (!isObjSel(request, id)) return "";
        EObject ob = objs.get(id);

        String objPos = 
            "\nap("
                    + "'BINT'"
                    + ",'BINT'"
                    + ",'Transport Pool'"
                    + ",'105.5099487'" 
                    + ",'-1.876670003'"
                    + ",'00:00'"
                    + ",'" + ob.getStr("img") 
                    + "'," + ob.getInt("width")  
                    + "," + ob.getInt("height") 
                    + ",'');"
                    ;
        return objPos;
    }
    
    String getEstateOfc(HttpServletRequest request) 
            throws Exception {
    
        String id = "EstateOffice";
        if (!isObjSel(request, id)) return "";
        EObject ob = objs.get(id);

        String objPos = 
            "\nap("
                    + "'BINE Office'"
                    + ",'BINE Office'"
                    + ",'Estate office'"
                    + ",'105.5079487'" 
                    + ",'-1.877670003'"
                    + ",'00:00'"
                    + ",'" + ob.getStr("img") 
                    + "'," + ob.getInt("width")  
                    + "," + ob.getInt("height") 
                    + ",'');"
                    ;
        return objPos;
    }
    
    String getCpoVessel(HttpServletRequest request) 
            throws Exception {
    
        String id = "CpoVessel";
        if (!isObjSel(request, id)) return "";
        EObject ob = objs.get(id);

        String objPos = 
            "\nap("
                    + "'Vessel1'"
                    + ",'Vessel1'"
                    + ",'Vessel'"
                    + ",'106.16756'"
                    + ",'-2.08923'"
                    + ",'09:00'"
                    + ",'" + ob.getStr("img") 
                    + "'," + ob.getInt("width")  
                    + "," + ob.getInt("height") 
                    + ",'');"
                    ;
        return objPos;
    }
    
    String getBulking(HttpServletRequest request) 
            throws Exception {
    
        String id = "Bulking";
        if (!isObjSel(request, id)) return "";
        EObject ob = objs.get(id);

        String objPos = 
            "\nap("
                    + "'Bulking Bangka'"
                    + ",'Bulking Bangka'"
                    + ",'Bulking'"
                    + ",'106.1284637'"  //106.1284637 lat -2.097860098
                    + ",'-2.097860098'"
                    + ",'00:00'"
                    + ",'" + ob.getStr("img") 
                    + "'," + ob.getInt("width")  
                    + "," + ob.getInt("height") 
                    + ",'');"
                    ;
        return objPos;
    }
    
    String getSqlData(String sql, String id, String dbName) throws Exception {
        
        //String img, int w, int h    
        EObject ob = objs.get(id);
    
        String o = ""; // output
        javax.naming.InitialContext ctx = 
                new javax.naming.InitialContext();
        javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup(
                "java:/comp/env/" + dbName);
        try (java.sql.Connection con = ds.getConnection();
                java.sql.PreparedStatement ps 
                    = con.prepareStatement(sql);
                java.sql.ResultSet rs = ps.executeQuery()
                ) {

            while (rs.next()){

                o += "\nap("
                        + "'" + FZUtil.getRsString(rs, 1, "") + "'"
                        + ",'" + FZUtil.getRsString(rs, 2, "") + "'"
                        + ",'" + ob.getStr("name") + "'"
                        + ",'" + FZUtil.getRsString(rs, 3, "") + "'"
                        + ",'" + FZUtil.getRsString(rs, 4, "") + "'"
                        + ",'" + FZUtil.getRsString(rs, 5, "") + "'"
                        + ",'" + ob.getStr("img") 
                            + "'," + ob.getInt("width") 
                            + "," + ob.getInt("height")
                        + ",'" + FZUtil.getRsString(rs, 6, "") + "'"
                        + ");"
                        ;
            }
        }
        return o;
    }
    
    boolean isObjSel(HttpServletRequest request, String name) throws Exception{
        String val = FZUtil.getHttpParam(request, name);
        return (val.length()>0);
    }
    
    String getData(HttpServletRequest request) {
        String o = "";
        try {

            String dt = FZUtil.getHttpParam(request, "dt");
            if (dt.length()==0) return "// No date";
            
            o += getBinTruck(request);
            o += getCpo(request);
            o += getGrabber(request);
            o += getHarvester(request);
            o += getCpoVessel(request);
            o += getBulking(request);
            o += getEstateOfc(request);
            o += getPool(request);  
            o += getMill(request);

        }
        catch(Exception e){
            String m = FZUtil.toStackTraceText(e);
            o = "Error: " + m;
        }
        return o;
    }
    
    public String showCb(HttpServletRequest request, String name) 
        throws Exception {

        String ou = "";
        String checked = 
            (FZUtil.getHttpParam(request, name).length()>0? "checked" : "");

        EObject ob = objs.get(name);

        ou += "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";

        ou += "<input type='checkbox'";
            ou += "id='" + name + "' name='" + name + "'";
            ou += "value='1' " + checked + "/>";

        ou += "&nbsp;&nbsp;&nbsp;<img src='../img/" + ob.getStr("img") 
            + "' style='width:" + ob.getInt("width") 
            + "px;height:" + ob.getInt("height") + "px'>"
            ;
        ou += "&nbsp;&nbsp;&nbsp;" + ob.getStr("name");
        
        return ou;
    }

    public void addObj(String id, String name, String img, int w, int h)
        throws Exception {

        EObject ob = new EObject();

        ob.putStr("id", id);
        ob.putStr("name", name);
        ob.putStr("img", "../img/" + img);
        ob.putInt("width", w);
        ob.putInt("height", h);

        objs.put(id, ob);
    }

    public void initIt() throws Exception {
        addObj("Harvester", "Harvester","people2.png",8,15);
        addObj("PalmBlocks", "Plantation Blocks","palmblock.png",15,25);
        addObj("EstateOffice", "Estate Office","estateOfc.png",15,25);
        addObj("Tractor", "Tractor","red-tractor.svg",15,25);
        addObj("BinTruck", "Bin Truck","truck-green.png",15,25);
        addObj("Mill", "Mill","refinery2.png",15,25);
        addObj("CpoTruck", "CPO Truck","truck-yellow.png",15,25);
        addObj("Pool", "Transport Pool","carpool.png",15,25);
        addObj("CpoVessel", "CPO Vessel","ship.svg.png",20,30);
        addObj("Bulking", "Bulking Station","oil_storage-512.png",15,25);
        addObj("SalesPerson", "Sales Person","salesperson.png",8,15);
        addObj("DeliveryTruck", "Delivery Truck","truck-yellow.png",15,25);
        addObj("PlanDeliveryRoute", "Delivery Route","route.png",15,25);
        addObj("PlanSalesRoute", "Sales Route","route.png",15,25);
        addObj("Refinery", "Refinery","factory1600.png",15,25);
        addObj("Warehouse", "Warehouse","warehouse.png",15,25);
        addObj("POI", "Point of Interest","generic-cyan.png",15,25);
        addObj("GTWholeCust", "GT Wholesaler Outlet","wholesaler.png",15,25);
        addObj("GTRetailCust", "GT Retailer Outlet","shop.jpg",15,25);
        addObj("MTCust", "Modern Trade Outlet","supermarket.png",15,20);
        addObj("FSBCCust", "Food Services (FSBC) Outlet","forkknife.png",15,20);
        addObj("LocalDist", "Local Distributor","distributor.png",15,25);
        addObj("BranchTerritory", "Sales Branch Territory","generic-red.png",15,25);
        addObj("LDTerritory", "LD Territory","generic-blue.png",15,25);
        addObj("GovtTerritory", "Goverment Territory","generic-yellow.png",15,25);
        addObj("SalesOffice", "Sales Office","office-building-icon.png",15,25);
        addObj("PotentialArea", "Marketing & Sales Potential Area","dotonmap.png",15,25);
    }
%>
<%initIt();%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>AIC</title>
	<!==link rel="stylesheet" href="http://cdn.leafletjs.com/leaflet-0.6.4/leaflet.css" /==>
	<!==[if lte IE 8]>
            <link rel="stylesheet" href="http://cdn.leafletjs.com/leaflet-0.6.4/leaflet.ie.css" />
        <![endif]==>
    </head>
    <body>
    <%@include file="../appGlobal/bodyTop.jsp"%>
  <script>
  $( function() {
    var v = $( "#dt" ).val();
    $( "#dt" ).datepicker();
    $( "#dt" ).datepicker( "option", "dateFormat", "yy-mm-dd");
    //$( "#hvsDt" ).val(yyyymmddDate(new Date()));
    $( "#dt" ).val(v);
  } );
  </script>
        <!--div id='dvTimeCont'-->
            
            <span style="font-size: 22pt"><b>Command-Center<b></span> 
            
            &nbsp;&nbsp;|&nbsp;Time: 
            <input id='txTime' value='06:00' 
                   style='height:60px; width:200px; font-size: 35pt; font-weight:bolder ;color:blue; border:none; border-bottom:1px solid #ccc'/>
            
            <script>$('#txTime').attr('size', $('#txTime').val().length);</script>
            
            &nbsp;&nbsp;
            <button class="btn fzButton" id='btStartStop'>
                Stop</button>
            
            &nbsp;&nbsp;
            <button class="btn fzButton" id='btSetting'>
                Open setting..</button>

            <div id="dvSetting" style="display:none;">
                
                <br>
                <b>Timing</b>
                <br>
                
                <br>&nbsp;&nbsp;&nbsp;&nbsp;
                Speed (ms): 
                <input 
                        id='txSpeed' 
                        value='300' 
                        style='width: 60px; border:none; border-bottom:1px solid #ccc'
                        />

                &nbsp;&nbsp;
                Ends: 
                <input id='txEndTime' 
                        value='23:00' 
                        style='width: 60px; border:none; border-bottom:1px solid #ccc'
                        />
                
                <br><br>
                <b>Objects</b>
                <br>
                
                <form action="../aic/aicView.jsp" method="get">
                    
                    <br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    Date: 
                    <input type="text" name="dt" id="dt"
                           value="<%=FZUtil.getHttpParam(request, "dt")%>"
                           style='width: 150px; border:none; border-bottom:1px solid #ccc'
                           >
                    
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <button class="btn fzButton" 
                            id='btSubmit' 
                            name='btSubmit' 
                            type="submit">
                        Reload
                    </button>
                    
                    <%=showCb(request, "Mill")%>
                    <%=showCb(request, "Pool")%>
                    <%=showCb(request, "PalmBlocks")%>
                    <%=showCb(request, "EstateOffice")%>
                    <%=showCb(request, "Tractor")%>
                    <%=showCb(request, "BinTruck")%>
                    <%=showCb(request, "Harvester")%>
                    <%=showCb(request, "CpoTruck")%>
                    <%=showCb(request, "Bulking")%>
                    <%=showCb(request, "CpoVessel")%>
                    <%=showCb(request, "Refinery")%>
                    <%=showCb(request, "DeliveryTruck")%>
                    <%=showCb(request, "PlanDeliveryRoute")%>
                    <%=showCb(request, "SalesPerson")%>
                    <%=showCb(request, "PlanSalesRoute")%>
                    <%=showCb(request, "SalesOffice")%>
                    <%=showCb(request, "POI")%>
                    <%=showCb(request, "Warehouse")%>
                    <%=showCb(request, "LocalDist")%>
                    <%=showCb(request, "GovtTerritory")%>
                    <%=showCb(request, "LDTerritory")%>
                    <%=showCb(request, "BranchTerritory")%>
                    <%=showCb(request, "FSBCCust")%>
                    <%=showCb(request, "GTWholeCust")%>
                    <%=showCb(request, "GTRetailCust")%>
                    <%=showCb(request, "MTCust")%>
                    <%=showCb(request, "PotentialArea")%>
                    
                </form>
                
            </div> <!--setting-->
            
        <!--/div-->
        
        <br><br>
        <div id="map" style="width: 100%; height: 500px; visibility:hidden"></div>
        
        <script src="colors.js"></script>
    
	<link rel="stylesheet" href="https://unpkg.com/leaflet@1.0.3/dist/leaflet.css" />
	<script src="https://unpkg.com/leaflet@1.0.3/dist/leaflet.js"></script>
	<!==script src="http://cdn.leafletjs.com/leaflet-0.6.4/leaflet.js"></script==>
    
	<script src="catiline.js"></script>
	<script src="leaflet.shpfile.js"></script>
	<script>
	
var _0x98a8=['Close','Close\x20setting..','show','log','map','setView','tileLayer','http://{s}.tiles.wmflabs.org/bw-mapnik/{z}/{x}/{y}.png','&copy;\x20<a\x20href=\x22http://www.openstreetmap.org/copyright\x22>OpenStreetMap</a>','addTo','val','#txTime','length','vhID','vhType','vhNm','color','marker','none','icw','lastPosIdx','poss','push','FeatureGroup','drawPoss','addLayer','circle','icon','<br>Name:\x20','<br>Type:\x20','<br>ID:','<br>Desc:\x20','<br>Last\x20pos:\x20','<br>Time:\x20','bindPopup','random','truck1','BinTruck','07:','../img/tractor-back.png','truck2','../img/pickup-front-view.png','../img/green-man-hi.png','init','getData(request)','vhs\x20len\x20=\x20','substr','tm\x20',',\x20mm\x20','trim','etm\x20',',\x20emm\x20','00:00',',\x20hh\x20','#txSpeed','setInterval','clearInterval','time\x20incr\x20=\x20','#btStartStop','text','Start','Stop','loading\x20shp','bangka.zip','keys','properties','join','<br\x20/>','getElementById','style','visibility','once','data:loaded','#btSetting','Open\x20setting..','#dvSetting','hide','click','startsWith'];(function(_0x2e32a1,_0x236289){var _0x52fd89=function(_0x22088e){while(--_0x22088e){_0x2e32a1['push'](_0x2e32a1['shift']());}};_0x52fd89(++_0x236289);}(_0x98a8,0x13f));var _0x898a=function(_0x29f462,_0x359b1e){_0x29f462=_0x29f462-0x0;var _0x556f50=_0x98a8[_0x29f462];return _0x556f50;};function debugIt(_0x45ece7){console[_0x898a('0x0')](_0x45ece7);}var m=L[_0x898a('0x1')](_0x898a('0x1'))[_0x898a('0x2')]([-1.8760567,105.5099874],0xc);var watercolor=L[_0x898a('0x3')](_0x898a('0x4'),{'maxZoom':0x12,'attribution':_0x898a('0x5')})[_0x898a('0x6')](m);var vhs=[];var boIsRunning=!![];var curColorIndex=0x0;function getColor(){if(++curColorIndex>=0xc)curColorIndex=0x0;return colors[curColorIndex];}function getAnimTime(){return $('#txTime')[_0x898a('0x7')]();}function getAnimEndTime(){return $('#txEndTime')[_0x898a('0x7')]();}function setAnimTime(_0x4fbce5){$(_0x898a('0x8'))[_0x898a('0x7')](_0x4fbce5);}function ap(_0x2101b3,_0x96b636,_0x3150df,_0x388a47,_0x42faa4,_0x1a92de,_0x3806cb,_0x5d2de4,_0x3e1867,_0x502e95){var _0x30257f=null;for(var _0x5d5924=0x0;_0x5d5924<vhs[_0x898a('0x9')];_0x5d5924++){var _0x2c76b7=vhs[_0x5d5924];if(_0x2c76b7[_0x898a('0xa')]===_0x2101b3){_0x30257f=_0x2c76b7;break;}}debugIt('ap\x20find\x20'+_0x2101b3+',\x20found\x20'+_0x30257f);if(_0x30257f==null){debugIt('if\x20null\x20entered');_0x30257f={};_0x30257f[_0x898a('0xb')]=_0x3150df;_0x30257f[_0x898a('0xa')]=_0x2101b3;_0x30257f[_0x898a('0xc')]=_0x96b636;_0x30257f[_0x898a('0xd')]=getColor();_0x30257f[_0x898a('0xe')]=_0x898a('0xf');_0x30257f['icn']=_0x3806cb;_0x30257f[_0x898a('0x10')]=_0x5d2de4;_0x30257f['ich']=_0x3e1867;_0x30257f[_0x898a('0x11')]=0x0;_0x30257f['html']=_0x502e95;_0x30257f[_0x898a('0x12')]=[];vhs['push'](_0x30257f);}var _0x18d167={};_0x18d167['ln']=_0x388a47;_0x18d167['lt']=_0x42faa4;_0x18d167['tm']=_0x1a92de;_0x30257f['poss'][_0x898a('0x13')](_0x18d167);}var markers=new L[(_0x898a('0x14'))]();function drawPoss(_0x28cc63,_0x4ddd4d){debugIt(_0x898a('0x15'));if(_0x4ddd4d){m['removeLayer'](markers);markers=new L['FeatureGroup']();}for(var _0x3f8af4=0x0;_0x3f8af4<vhs[_0x898a('0x9')];_0x3f8af4++){var _0x58c3ab=vhs[_0x3f8af4];for(var _0x4d374b=0x0;_0x4d374b<_0x58c3ab[_0x898a('0x12')][_0x898a('0x9')];_0x4d374b++){var _0x38a8f0=_0x58c3ab[_0x898a('0x12')][_0x4d374b];if(_0x38a8f0['tm']===_0x28cc63){_0x58c3ab[_0x898a('0x11')]=_0x4d374b;}}if(_0x4ddd4d)drawPos(_0x58c3ab);}if(_0x4ddd4d)m[_0x898a('0x16')](markers);}function drawPos(_0x259541){var _0x483212;if(_0x259541[_0x898a('0x11')]===-0x1){return;}else{_0x483212=_0x259541[_0x898a('0x12')][_0x259541[_0x898a('0x11')]];}var _0x3cfd9c=new L[(_0x898a('0x14'))]();_0x259541[_0x898a('0xd')]='yellow';var _0x274cdf=L[_0x898a('0x17')]([_0x483212['lt'],_0x483212['ln']],{'color':_0x259541[_0x898a('0xd')],'fillColor':_0x259541[_0x898a('0xd')],'fillOpacity':0.5,'radius':0x32});var _0x3901ab=L[_0x898a('0x18')]({'iconUrl':_0x259541['icn'],'shadowUrl':'','iconSize':[_0x259541[_0x898a('0x10')],_0x259541['ich']],'shadowSize':[0x0,0x0],'iconAnchor':[0x4,0x4],'shadowAnchor':[0x0,0x0],'popupAnchor':[0x0,0x0]});L['marker']([_0x483212['lt'],_0x483212['ln']],{'icon':_0x3901ab})['addTo'](_0x3cfd9c);_0x3cfd9c[_0x898a('0x16')](_0x274cdf);var _0x1f4d86=_0x898a('0x19')+_0x259541[_0x898a('0xc')]+_0x898a('0x1a')+_0x259541[_0x898a('0xb')]+_0x898a('0x1b')+_0x259541[_0x898a('0xc')]+_0x898a('0x1c')+_0x259541['html']+_0x898a('0x1d')+_0x483212['lt']+',\x20'+_0x483212['ln']+_0x898a('0x1e')+_0x483212['tm'];_0x3cfd9c[_0x898a('0x1f')](_0x1f4d86);markers[_0x898a('0x16')](_0x3cfd9c);}function testAnimData(){var _0x3b7704=105.5;var _0x27f3ca=-1.876;for(var _0x20a3fe=0x1;_0x20a3fe<=0x3b;_0x20a3fe++){_0x3b7704+=0.001*(Math[_0x898a('0x20')]()<0.8?-0x1:0x1);_0x27f3ca+=0.001*(Math[_0x898a('0x20')]()<0.8?-0x1:0x1);ap(_0x898a('0x21'),_0x898a('0x21'),_0x898a('0x22'),_0x3b7704,_0x27f3ca,_0x898a('0x23')+pad(_0x20a3fe,0x2),_0x898a('0x24'),0xf,0x19,'');}_0x3b7704=105.5;_0x27f3ca=-1.876;for(var _0x20a3fe=0x3;_0x20a3fe<=0x3b;_0x20a3fe+=0x3){_0x3b7704+=0.001*(Math[_0x898a('0x20')]()<0.8?0x1:-0x1);_0x27f3ca+=0.001*(Math[_0x898a('0x20')]()<0.8?0x1:-0x1);ap(_0x898a('0x25'),_0x898a('0x25'),_0x898a('0x22'),_0x3b7704,_0x27f3ca,_0x898a('0x23')+pad(_0x20a3fe,0x2),_0x898a('0x26'),0xf,0x19,'');}_0x3b7704=105.509;_0x27f3ca=-1.87;for(var _0x20a3fe=0x3;_0x20a3fe<=0x3b;_0x20a3fe+=0x3){_0x3b7704+=0.001*(Math[_0x898a('0x20')]()<0.8?0x1:-0x1);_0x27f3ca+=0.001*(Math[_0x898a('0x20')]()<0.8?0x1:-0x1);ap('p1','p1','Person',_0x3b7704,_0x27f3ca,_0x898a('0x23')+pad(_0x20a3fe,0x2),_0x898a('0x27'),0xa,0xd,'');}}function initData(){debugIt(_0x898a('0x28'));<%=getData(request)%>;debugIt(_0x898a('0x2a')+vhs[_0x898a('0x9')]);}function pad(_0x45d854,_0x146f32){_0x45d854=_0x45d854['toString']();return _0x45d854[_0x898a('0x9')]<_0x146f32?pad('0'+_0x45d854,_0x146f32):_0x45d854;}function OLD_incrementTime(){var _0x5baf5b=getAnimTime();_0x5baf5b=_0x5baf5b['trim']();var _0x14e2dc=_0x5baf5b['substr'](0x0,0x2);var _0x3c9588=_0x5baf5b[_0x898a('0x2b')](0x3,0x5);debugIt(_0x898a('0x2c')+_0x5baf5b+',\x20hh\x20'+_0x14e2dc+_0x898a('0x2d')+_0x3c9588);var _0x354703=getAnimEndTime();_0x354703=_0x354703[_0x898a('0x2e')]();var _0x50c0e4=_0x354703['substr'](0x0,0x2);var _0x47966e=_0x354703[_0x898a('0x2b')](0x3,0x5);debugIt(_0x898a('0x2f')+_0x354703+',\x20ehh\x20'+_0x50c0e4+_0x898a('0x30')+_0x47966e);if(_0x14e2dc===_0x50c0e4){if(_0x3c9588===_0x47966e){return![];}}if(_0x3c9588==='59'){if(_0x14e2dc==='23'){return![];}else{_0x14e2dc++;_0x3c9588=0x0;}}else{_0x3c9588++;}_0x5baf5b=pad(_0x14e2dc,0x2)+':'+pad(_0x3c9588,0x2);setAnimTime(_0x5baf5b);return!![];}function tryIncrementTime(){var _0x3d3463=incrementTime(getAnimTime());if(_0x3d3463===getAnimEndTime()){return![];}else if(_0x3d3463===_0x898a('0x31')){return![];}else{setAnimTime(_0x3d3463);return!![];}}function incrementTime(_0x3e72e8){_0x3e72e8=_0x3e72e8[_0x898a('0x2e')]();var _0x4b07bc=_0x3e72e8[_0x898a('0x2b')](0x0,0x2);var _0x454c8c=_0x3e72e8['substr'](0x3,0x5);debugIt('tm\x20'+_0x3e72e8+_0x898a('0x32')+_0x4b07bc+_0x898a('0x2d')+_0x454c8c);if(_0x454c8c==='59'){if(_0x4b07bc==='23'){return _0x898a('0x31');}else{_0x4b07bc++;_0x454c8c=0x0;}}else{_0x454c8c++;}var _0xa45405=pad(_0x4b07bc,0x2)+':'+pad(_0x454c8c,0x2);return _0xa45405;}function nextTimer(){debugIt('next\x20timer');if(!boIsRunning)return;var _0x67114b=$(_0x898a('0x33'))['val']();var _0x516b8c=window[_0x898a('0x34')](function(){window[_0x898a('0x35')](_0x516b8c);var _0x477a9d=tryIncrementTime();debugIt(_0x898a('0x36')+_0x477a9d);if(_0x477a9d){drawPoss(getAnimTime(),!![]);nextTimer();}else{$(_0x898a('0x37'))[_0x898a('0x38')](_0x898a('0x39'));}},_0x67114b);}function animFirstTime(){initData();animIt();}function animIt(){for(var _0x10daf6=0x0;_0x10daf6<vhs[_0x898a('0x9')];_0x10daf6++){var _0x3292c2=vhs[_0x10daf6];_0x3292c2[_0x898a('0x11')]=0x0;}$(_0x898a('0x37'))[_0x898a('0x38')](_0x898a('0x3a'));nextTimer();}function drawLyr(){debugIt(_0x898a('0x3b'));var _0x21f307=new L['Shapefile'](_0x898a('0x3c'),{'onEachFeature':function(_0x11c437,_0x20cdbd){if(_0x11c437['properties']){_0x20cdbd[_0x898a('0x1f')](Object[_0x898a('0x3d')](_0x11c437[_0x898a('0x3e')])[_0x898a('0x1')](function(_0x2cd3c5){return _0x2cd3c5+':\x20'+_0x11c437[_0x898a('0x3e')][_0x2cd3c5];})[_0x898a('0x3f')](_0x898a('0x40')),{'maxHeight':0xc8});}}});_0x21f307['addTo'](m);document[_0x898a('0x41')](_0x898a('0x1'))[_0x898a('0x42')][_0x898a('0x43')]='visible';_0x21f307[_0x898a('0x44')](_0x898a('0x45'),function(){debugIt('finished\x20loaded\x20shapefile');animFirstTime();});}$(_0x898a('0x37'))['click'](function(){boIsRunning=!boIsRunning;if(boIsRunning){$(_0x898a('0x46'))['text'](_0x898a('0x47'));$(_0x898a('0x48'))[_0x898a('0x49')]();animIt();}else{$(_0x898a('0x37'))[_0x898a('0x38')]('Start');}});$(_0x898a('0x46'))[_0x898a('0x4a')](function(){if($('#btSetting')[_0x898a('0x38')]()[_0x898a('0x4b')](_0x898a('0x4c'))){$(_0x898a('0x46'))['text'](_0x898a('0x47'));$(_0x898a('0x48'))['hide']();}else{$(_0x898a('0x46'))[_0x898a('0x38')](_0x898a('0x4d'));$(_0x898a('0x48'))[_0x898a('0x4e')]();}});drawLyr();
    
        </script>
        
        
    <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

<%-- 
    Document   : testMap
    Created on : Oct 19, 2017, 3:41:53 PM
    Author     : dwi.rangga
--%>
<%@include file="../../appGlobal/pageTop.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%-- <%run(new com.fz.tms.params.map.GoogleDirMap());%>--%>
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
                var markers = /*[{
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
        [
{"title": '5810002808',"lat": '-6.150633',"lng": '106.806997',"description": '1'},
{"title": '5810003004',"lat": '-6.1483',"lng": '106.83',"description": '1'},
{"title": '5810003693',"lat": '-6.140579',"lng": '106.841920',"description": '1'},
{"title": '5810018192',"lat": '-6.1574',"lng": '106.828',"description": '1'},
{"title": '5810022590',"lat": '-6.18902',"lng": '106.812',"description": '1'},
{"title": '5810023634',"lat": '-6.13591681224875',"lng": '106.806904496565',"description": '1'},
{"title": '5810029807',"lat": '-6.15294114736655',"lng": '106.780025088122',"description": '1'},
{"title": '5810029887',"lat": '-6.15333785387866',"lng": '106.830340563645',"description": '1'},
{"title": '5810029905',"lat": '-6.17775',"lng": '106.806',"description": '1'},
{"title": '5810029940',"lat": '-6.15284',"lng": '106.829',"description": '1'},
{"title": '5810029957',"lat": '-6.15043',"lng": '106.797',"description": '1'},
{"title": '5810030347',"lat": '-6.15696',"lng": '106.86',"description": '1'},
{"title": '5810030473',"lat": '-6.123549',"lng": '106.798819',"description": '1'},
{"title": '5810035500',"lat": '-6.14899375708477',"lng": '106.79739460929',"description": '1'},
{"title": '5810036105',"lat": '-6.16721836223614',"lng": '106.837552713447',"description": '1'},
{"title": '5810036228',"lat": '-6.152071',"lng": '106.834504',"description": '1'},
{"title": '5810036254',"lat": '-6.18683623604117',"lng": '106.857669779486',"description": '1'},
{"title": '5810036568',"lat": '-6.181',"lng": '106.847',"description": '1'},
{"title": '5810041168',"lat": '-6.13156',"lng": '106.801',"description": '1'},
{"title": '5810041619',"lat": '-6.15218',"lng": '106.798',"description": '1'},
{"title": '5810043342',"lat": '-6.15912',"lng": '106.821',"description": '1'},
{"title": '5810043343',"lat": '-6.17911578427805',"lng": '106.843764366066',"description": '1'},
{"title": '5810043430',"lat": '-6.183598',"lng": '106.845034',"description": '1'},
{"title": '5810043665',"lat": '-6.15653919',"lng": '106.8003205',"description": '1'},
{"title": '5810045073',"lat": '-6.15396676442016',"lng": '106.828895717957',"description": '1'},
{"title": '5810045075',"lat": '-6.1574',"lng": '106.828',"description": '1'},
{"title": '5810045240',"lat": '-6.15149258',"lng": '106.83075155',"description": '1'},
{"title": '5810045257',"lat": '-6.1530366544914',"lng": '106.783189212297',"description": '1'},
{"title": '5810046241',"lat": '-6.15912',"lng": '106.821',"description": '1'},
{"title": '5810046719',"lat": '-6.15323435023012',"lng": '106.809709948317',"description": '1'},
{"title": '5810046720',"lat": '-6.15322421803939',"lng": '106.809686402151',"description": '1'},
{"title": '5810048277',"lat": '-6.15964730118618',"lng": '106.798403312892',"description": '1'},
{"title": '5810048283',"lat": '-6.1666499662437',"lng": '106.814069189022',"description": '1'},
{"title": '5810048413',"lat": '-6.15042426348273',"lng": '106.778771942305',"description": '1'},
{"title": '5810048414',"lat": '-6.15354',"lng": '106.781',"description": '1'},
{"title": '5810055471',"lat": '-6.178887',"lng": '106.843669',"description": '1'},
{"title": '5810058068',"lat": '-6.18467',"lng": '106.806',"description": '1'},
{"title": '5810058752',"lat": '-6.15042714574461',"lng": '106.778802464477',"description": '1'},
{"title": '5810061169',"lat": '-6.15143198959892',"lng": '106.805880176427',"description": '1'},
{"title": '5810061476',"lat": '-6.15979775169795',"lng": '106.785398162892',"description": '1'},
{"title": '5810062768',"lat": '-6.13164',"lng": '106.8',"description": '1'},
{"title": '5810062782',"lat": '-6.15660154',"lng": '106.79855608',"description": '1'},
{"title": '5810062821',"lat": '-6.18546866061835',"lng": '106.85563102234',"description": '1'},
{"title": '5810063387',"lat": '-6.16307641495512',"lng": '106.812356164856',"description": '1'},
{"title": '5810063461',"lat": '-6.13127',"lng": '106.801',"description": '1'},
{"title": '5810063462',"lat": '-6.13169',"lng": '106.802',"description": '1'},
{"title": '5810064334',"lat": '-6.14024295781898',"lng": '106.808150363049',"description": '1'},
{"title": '5810064368',"lat": '-6.13091',"lng": '106.799',"description": '1'},
{"title": '5810064709',"lat": '-6.19188385491535',"lng": '106.813946480255',"description": '1'},
{"title": '5810066187',"lat": '-6.19061',"lng": '106.796',"description": '1'},
{"title": '5810071427',"lat": '-6.18027770204416',"lng": '106.869041826115',"description": '1'},
{"title": '5810075424',"lat": '-6.17657',"lng": '106.837',"description": '1'},
{"title": '5810077846',"lat": '-6.16304',"lng": '106.812',"description": '1'},
{"title": '5810078904',"lat": '-6.14700542554454',"lng": '106.80212332075',"description": '1'},
{"title": '5810099363',"lat": '-6.15979444597104',"lng": '106.785394613848',"description": '1'},
{"title": '5810109890',"lat": '-6.167517',"lng": '106.795626',"description": '1'},
{"title": '5810109897',"lat": '-6.164346',"lng": '106.792942',"description": '1'},
{"title": '5810110193',"lat": '-6.193560',"lng": '106.762843',"description": '1'},
{"title": '5810110194',"lat": '-6.192135',"lng": '106.762310',"description": '1'},
{"title": '5810110195',"lat": '-6.192785',"lng": '106.793892',"description": '1'},
{"title": '5810110213',"lat": '-6.151950',"lng": '106.815737',"description": '1'},
{"title": '5810110285',"lat": '-6.162244',"lng": '106.797486',"description": '1'},
{"title": '5820000252',"lat": '-6.188959',"lng": '106.796499',"description": '1'},
{"title": '5820000265',"lat": '-6.188831',"lng": '106.755957',"description": '1'},
{"title": '5820000274',"lat": '-6.1371869',"lng": '106.8238911',"description": '1'},
{"title": '5820000279',"lat": '-6.156867',"lng": '106.853295',"description": '1'},
{"title": '5820000289',"lat": '-6.195981',"lng": '106.821782',"description": '1'},
{"title": '5820000297',"lat": '-6.1886870',"lng": '106.8242160',"description": '1'},
{"title": '5820000433',"lat": '-6.107330',"lng": '106.778483',"description": '1'},
{"title": '5820001625',"lat": '-6.189667',"lng": '106.824238',"description": '1'},
{"title": '5820002148',"lat": '-6.161473',"lng": '106.838569',"description": '1'},
{"title": '5810003315',"lat": '-6.17751309840395',"lng": '106.948356649397',"description": '2'},
{"title": '5810003499',"lat": '-6.19655418395996',"lng": '106.983451843262',"description": '2'},
{"title": '5810003516',"lat": '-6.1828169',"lng": '106.9178510',"description": '2'},
{"title": '5810003661',"lat": '-6.15787076',"lng": '106.9540777',"description": '2'},
{"title": '5810026795',"lat": '-6.18629637643784',"lng": '106.95812620391',"description": '2'},
{"title": '5810030218',"lat": '-6.195445',"lng": '106.922685',"description": '2'},
{"title": '5810030230',"lat": '-6.183060',"lng": '106.946000',"description": '2'},
{"title": '5810030259',"lat": '-6.18365321941316',"lng": '106.946201290065',"description": '2'},
{"title": '5810037481',"lat": '-6.181051',"lng": '106.945823',"description": '2'},
{"title": '5810049478',"lat": '-6.13918210455114',"lng": '106.870475998952',"description": '2'},
{"title": '5810050183',"lat": '-6.14023',"lng": '106.955',"description": '2'},
{"title": '5810050184',"lat": '-6.14023',"lng": '106.955',"description": '2'},
{"title": '5810051255',"lat": '-6.16459',"lng": '106.948',"description": '2'},
{"title": '5810054230',"lat": '-6.19312',"lng": '106.965',"description": '2'},
{"title": '5810054830',"lat": '-6.18005493716637',"lng": '106.948068382514',"description": '2'},
{"title": '5810057155',"lat": '-6.19553',"lng": '106.922',"description": '2'},
{"title": '5810057697',"lat": '-6.19548160726466',"lng": '106.922533474162',"description": '2'},
{"title": '5810062815',"lat": '-6.14023',"lng": '106.955',"description": '2'},
{"title": '5810062911',"lat": '-6.14023',"lng": '106.955',"description": '2'},
{"title": '5810072534',"lat": '-6.15998',"lng": '106.876',"description": '2'},
{"title": '5810073395',"lat": '-6.188389',"lng": '106.894175',"description": '2'},
{"title": '5810101089',"lat": '-6.17402',"lng": '106.953755',"description": '2'},
{"title": '5810106040',"lat": '-6.176401',"lng": '106.872665',"description": '2'},
{"title": '5810110012',"lat": '-6.193890',"lng": '106.883900',"description": '2'},
{"title": '5810110286',"lat": '-6.186380',"lng": '106.886883',"description": '2'},
{"title": '5820000391',"lat": '-6.194205',"lng": '106.890523',"description": '2'},
{"title": '5820000431',"lat": '-6.158286',"lng": '106.908558',"description": '2'},
{"title": '5820002166',"lat": '-6.188549',"lng": '106.874602',"description": '2'},
{"title": '5810002768',"lat": '-6.20073615',"lng": '106.77325478',"description": '3'},
{"title": '5810018293',"lat": '-6.25663',"lng": '106.866',"description": '3'},
{"title": '5810021549',"lat": '-6.25663',"lng": '106.866',"description": '3'},
{"title": '5810023720',"lat": '-6.20875658395176',"lng": '106.758981299886',"description": '3'},
{"title": '5810025048',"lat": '-6.20566158321203',"lng": '106.860666853078',"description": '3'},
{"title": '5810029895',"lat": '-6.20829109653412',"lng": '106.795959252952',"description": '3'},
{"title": '5810042730',"lat": '-6.207461',"lng": '106.802950',"description": '3'},
{"title": '5810046798',"lat": '-6.21869',"lng": '106.771',"description": '3'},
{"title": '5810047809',"lat": '-6.2007302',"lng": '106.77413012',"description": '3'},
{"title": '5810058066',"lat": '-6.218361',"lng": '106.766787',"description": '3'},
{"title": '5810059481',"lat": '-6.20071153',"lng": '106.77414586',"description": '3'},
{"title": '5810062780',"lat": '-6.21024790139485',"lng": '106.781237589246',"description": '3'},
{"title": '5810062804',"lat": '-6.20527',"lng": '106.789',"description": '3'},
{"title": '5810063664',"lat": '-6.20073626',"lng": '106.77380713',"description": '3'},
{"title": '5810064719',"lat": '-6.20149',"lng": '106.772',"description": '3'},
{"title": '5810065213',"lat": '-6.19728',"lng": '106.772',"description": '3'},
{"title": '5810110207',"lat": '-6.205307',"lng": '106.776078',"description": '3'},
{"title": '5810110209',"lat": '-6.205108',"lng": '106.775946',"description": '3'},
{"title": '5810110210',"lat": '-6.205213',"lng": '106.776019',"description": '3'},
{"title": '5820000256',"lat": '-6.2165317',"lng": '106.7652930',"description": '3'},
{"title": '5820000323',"lat": '-6.242027',"lng": '106.826071',"description": '3'},
{"title": '5820000327',"lat": '-6.2055398',"lng": '106.8343619',"description": '3'},
{"title": '5820000336',"lat": '-6.245029',"lng": '106.783403',"description": '3'},
{"title": '5820000339',"lat": '-6.226763',"lng": '106.801293',"description": '3'},
{"title": '5820000348',"lat": '-6.2572523',"lng": '106.8521895',"description": '3'},
{"title": '5820000353',"lat": '-6.262834',"lng": '106.783946',"description": '3'},
{"title": '5820000424',"lat": '-6.258133',"lng": '106.827382',"description": '3'},
{"title": '5820001054',"lat": '-6.243676',"lng": '106.797494',"description": '3'},
{"title": '5820001628',"lat": '-6.203728',"lng": '106.820460',"description": '3'},
{"title": '5820001631',"lat": '-6.224007',"lng": '106.823103',"description": '3'},
{"title": '5810001002',"lat": '-6.228626',"lng": '106.977485',"description": '4'},
{"title": '5810005649',"lat": '-6.2416396',"lng": '106.9481927',"description": '4'},
{"title": '5810022602',"lat": '-6.2834717',"lng": '106.9635868',"description": '4'},
{"title": '5810023664',"lat": '-6.19752',"lng": '106.949',"description": '4'},
{"title": '5810023807',"lat": '-6.23487779',"lng": '106.97304528',"description": '4'},
{"title": '5810025693',"lat": '-6.20038068999306',"lng": '106.935209120478',"description": '4'},
{"title": '5810029070',"lat": '-6.233112',"lng": '106.966205',"description": '4'},
{"title": '5810030250',"lat": '-6.23485351',"lng": '106.97306403',"description": '4'},
{"title": '5810030332',"lat": '-6.208011',"lng": '106.935066',"description": '4'},
{"title": '5810030469',"lat": '-6.2108498',"lng": '106.9647184',"description": '4'},
{"title": '5810041608',"lat": '-6.20938055',"lng": '106.90892265',"description": '4'},
{"title": '5810042891',"lat": '-6.21485',"lng": '106.878',"description": '4'},
{"title": '5810042892',"lat": '-6.23869',"lng": '106.903',"description": '4'},
{"title": '5810043341',"lat": '-6.219972',"lng": '106.951443',"description": '4'},
{"title": '5810045057',"lat": '-6.228626',"lng": '106.977485',"description": '4'},
{"title": '5810049917',"lat": '-6.248501',"lng": '106.945421',"description": '4'},
{"title": '5810053286',"lat": '-6.22001645364132',"lng": '106.951368913961',"description": '4'},
{"title": '5810053668',"lat": '-6.20984',"lng": '106.939',"description": '4'},
{"title": '5810057152',"lat": '-6.24106',"lng": '106.943',"description": '4'},
{"title": '5810060029',"lat": '-6.23947',"lng": '106.938',"description": '4'},
{"title": '5810062678',"lat": '-6.2276808',"lng": '106.9163674',"description": '4'},
{"title": '5810063799',"lat": '-6.223179',"lng": '106.958341',"description": '4'},
{"title": '5810064158',"lat": '-6.22175628728032',"lng": '106.931575671903',"description": '4'},
{"title": '5810064717',"lat": '-6.207595',"lng": '106.942015',"description": '4'},
{"title": '5810069040',"lat": '-6.20026830192276',"lng": '106.93512689304',"description": '4'},
{"title": '5810072385',"lat": '-6.21857999998344',"lng": '106.911643706063',"description": '4'},
{"title": '5810073878',"lat": '-6.23064102',"lng": '106.9432047',"description": '4'},
{"title": '5810080693',"lat": '-6.21214',"lng": '106.901',"description": '4'},
{"title": '5810091578',"lat": '-6.23459113',"lng": '106.97434367',"description": '4'},
{"title": '5810091825',"lat": '-6.23337466',"lng": '106.97373798',"description": '4'},
{"title": '5810108062',"lat": '-6.231406',"lng": '106.960414',"description": '4'},
{"title": '5810108065',"lat": '-6.228626',"lng": '106.977485',"description": '4'},
{"title": '5810108427',"lat": '-6.228626',"lng": '106.977485',"description": '4'},
{"title": '5810108429',"lat": '-6.240515',"lng": '106.956022',"description": '4'},
{"title": '5810110287',"lat": '-6.219957',"lng": '106.938927',"description": '4'},
{"title": '5810110290',"lat": '-6.231198',"lng": '106.928263',"description": '4'},
{"title": '5820000122',"lat": '-6.228626',"lng": '106.977485',"description": '4'},
{"title": '5820000124',"lat": '-6.228626',"lng": '106.977485',"description": '4'},
{"title": '5820000179',"lat": '-6.228626',"lng": '106.977485',"description": '4'},
{"title": '5820000282',"lat": '-6.228626',"lng": '106.977485',"description": '4'},
{"title": '5820000392',"lat": '-6.24709903',"lng": '106.91200584',"description": '4'},
{"title": '5820000393',"lat": '-6.228626',"lng": '106.977485',"description": '4'},
{"title": '5820000403',"lat": '-6.228626',"lng": '106.977485',"description": '4'},
{"title": '5820000505',"lat": '-6.263924',"lng": '106.949931',"description": '4'},
{"title": '5820000507',"lat": '-6.228626',"lng": '106.977485',"description": '4'},
{"title": '5820001100',"lat": '-6.228626',"lng": '106.977485',"description": '4'},
{"title": '5820001176',"lat": '-6.228626',"lng": '106.977485',"description": '4'},
{"title": '5820001736',"lat": '-6.228626',"lng": '106.977485',"description": '4'},
{"title": '5810059227',"lat": '-6.341194',"lng": '106.861975',"description": '5'},
{"title": '5810078461',"lat": '-6.32489020394119',"lng": '106.869226259872',"description": '5'},
{"title": '5820000319',"lat": '-6.289854',"lng": '106.778184',"description": '5'},
{"title": '5820001101',"lat": '-6.302327',"lng": '106.814410',"description": '5'},
{"title": '5810018273',"lat": '-6.29',"lng": '106.894',"description": '6'},
{"title": '5810030273',"lat": '-6.29',"lng": '106.894',"description": '6'},
{"title": '5810030279',"lat": '-6.34529551201304',"lng": '106.872922438102',"description": '6'},
{"title": '5810030296',"lat": '-6.33852',"lng": '106.88',"description": '6'},
{"title": '5810030315',"lat": '-6.33983637308419',"lng": '106.88482230593',"description": '6'},
{"title": '5810036031',"lat": '-6.34541006924705',"lng": '106.872934715006',"description": '6'},
{"title": '5810036199',"lat": '-6.29',"lng": '106.894',"description": '6'},
{"title": '5810045039',"lat": '-6.29',"lng": '106.894',"description": '6'},
{"title": '5810045701',"lat": '-6.30241116446196',"lng": '106.916755207404',"description": '6'},
{"title": '5810047503',"lat": '-6.332493',"lng": '106.883955',"description": '6'},
{"title": '5810050387',"lat": '-6.292735',"lng": '106.900894',"description": '6'},
{"title": '5810061716',"lat": '-6.342167',"lng": '106.886513',"description": '6'},
{"title": '5810066230',"lat": '-6.330693',"lng": '106.879331',"description": '6'},
{"title": '5810066447',"lat": '-6.33035',"lng": '106.871',"description": '6'},
{"title": '5810071428',"lat": '-6.33864',"lng": '106.884',"description": '6'},
{"title": '5810072985',"lat": '-6.35183182133837',"lng": '106.880923839967',"description": '6'},
{"title": '5810079221',"lat": '-6.321952',"lng": '106.883764',"description": '6'},
{"title": '5820000061',"lat": '-6.2982393',"lng": '106.9228310',"description": '6'},
{"title": '5820000517',"lat": '-6.333800',"lng": '106.923705',"description": '6'},
{"title": '5820001196',"lat": '-6.303768',"lng": '106.886258',"description": '6'}
]

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
                            //recursive(src, des, i);
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

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%@page import="com.fz.tms.service.run.RouteJob"%>
<%run(new com.fz.tms.service.run.RouteJobListing());%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Jobs</title>
    </head>
    <body>
        <style>
            tr { border-bottom: 2px solid lightgray;
            }
        </style>
        <%@include file="../appGlobal/bodyTop.jsp"%>
        <%
            url = request.getRequestURL().toString();
            String str =  url + "?" + request.getQueryString();
            str = str.replace("http://","");
            str = str.replace(":","9ETR9");
            str = str.replace(".","9DOT9");
            str = str.replace("/","9AK9");
            str = str.replace("?","9ASK9");
            str = str.replace("&","9END9");
            str = str.replace("=","9EQU9");
            str = str.replace("-","9MIN9");
        %>
        <input type="hidden" value="<%=str%>" id="urls"/>
        <link href="../appGlobal/eFreezeTable.css" rel="stylesheet">
        <script src="../appGlobal/eFreezeTable.js"></script>
        <script>
            $(document).ready(function () {
                $('#table').eFreezeTableHead();
                $('.custIDClick').click(function () {
                    //Some code
                    //alert( $(this).text() ); 
                    if ($(this).text().length > 0) {
                        window.open("../Params/PopUp/popupDetilDOCust.jsp?custId=" + $(this).text() + "&runId=" + $("#RunIdClick").text(), null,
                                "scrollbars=1,resizable=1,height=500,width=750");
                        return true;
                    }
                });
                $('.vCodeClick').click(function () {
                    //Some code
                    //alert( $("#runID").text()+"&vCode="+$(this).text() ); 
                    if ($(this).text().length > 2) {
                        window.open("../Params/map/GoogleDirMap.jsp?runID=" + $("#RunIdClick").text() + "&vCode=" + $(this).text(), null,
                                "scrollbars=1,resizable=1,height=530,width=530");
                        return true;
                    }
                });
                $('#RunIdClick').click(function () {
                    //Some code
                    //alert( $("#runID").text()+"&vCode="+$(this).text() ); 
                    if ($(this).text().length > 0) {
                        window.open("../Params/PopUp/popupDetilRunId.jsp?runID=" + $("#RunIdClick").text() + "&oriRunID=" + $("#nextRunId").text() + "&flag=runResult", null,
                                "scrollbars=1,resizable=1,height=500,width=850");
                        return true;
                    }
                });
                $('#mapAll').click(function () {
                    //Some code
                    //alert( $("#runID").text()+"&vCode="+$(this).text() ); 
                    if ($(this).text().length > 0) {
                        window.open("../Params/map/GoogleDirMapAllVehi.jsp?runID=" + $("#RunIdClick").text() + '&channel=' + $('#channel').text(), null,
                                "scrollbars=1,resizable=1,height=530,width=530");
                        return true;
                    }
                });

                $('#reRun').click(function () {
                    var dateNow = $.datepicker.formatDate('yy-mm-dd', new Date());//currentDate.getFullYear()+"-"+(currentDate.getMonth()+1)+"-"+currentDate.getDate();

                    var win = window.location.replace('runProcess.jsp?shift=1&dateDeliv=' + dateNow + '&branch=' + $('#branch').text() + '&runId=' + $("#RunIdClick").text() + '&oriRunID=' + $("#OriRunID").val() + '&reRun=A' + '&channel=' + $('#channel').text() + '&url=' + $("#urls").val(), '_blank');
                    if (win) {
                        //Browser has allowed it to be opened
                        win.focus();
                    }
                    //}, 3000);
                });
            });

            function openEditRoutePage() {
                var table = document.getElementById("table");

                var tableArr = [];
                for (var i = 1; i < table.rows.length; i++) {
                    var no = table.rows[i].cells[0].innerHTML; //no
                    var truck = table.rows[i].cells[1].innerHTML; //truck
                    var custId = "";
                    if ((table.rows[i].cells[1].innerHTML !== "") && (table.rows[i].cells[2].innerHTML === "") && (table.rows[i].cells[4].innerHTML !== "")) {
                        custId = "start" + "split";
                    } else {
                        custId = table.rows[i].cells[2].innerHTML + "split"; //custId
                    }
                    tableArr.push(
                            no,
                            truck,
                            custId
                            );
                }

                var win = window.location.replace('runResultEdit.jsp?&OriRunID=' + $('#RunIdClick').text() + '&runId=' + $('#nextRunId').text() + '&channel=' + $('#channel').text() +
                        '&branch=' + $('#branch').text() + '&shift=' + $('#shift').text() + '&vehicles=' + $('#vehicles').text() + '&dateDeliv=' + $('#dateDeliv').text());  
                        //'&tableArr=' + tableArr);

                if (win) {
                    //Browser has allowed it to be opened
                    win.focus();
                }
            }

            function klik(kode) {
                //alert('tes : ' + kode);
                window.open("../Params/PopUp/popupEditCust.jsp?runId=" + $("#RunIdClick").text() + "&custId=" + kode, null,
                        "scrollbars=1,resizable=1,height=500,width=750");
            }

            function sendSAP(kode, kd) {
                if(kd == 'DELL'){
                    deletes(kode);
                }else{
                    send(kode);
                }                               
                //var data = [];
                
            }
            
            function deletes(kode){
                var $apiAddress = '';
                var jsonForServer = '';
                
                $apiAddress = '../../api/runResult/DeleteShipmentPlan';
                jsonForServer = '{\"RunId\": \"' + $("#RunIdClick").text() + '\",\"vehicle_code\":\"' + kode + '\"}';
                $.post($apiAddress, {json: jsonForServer}).done(function (data) {
                    if (data == 'OK') {                            
                        send(kode);
                    }else {
                        alert('submit Status error');
                    }
                });
            }
            
            function send(kode){
                $apiAddress = '../../api/submitToSap/submitToSap';
                jsonForServer = '{\"RunId\": \"' + $("#RunIdClick").text() + 'split' + $("#RunIdClick").text() + '\",\"vehicle_no\":\"' + kode + '\"}';
                $.post($apiAddress, {json: jsonForServer}).done(function (data) {
                    if(data == 'OK'){
                        //alert('data: ' + data + 'stat: ' + stat);
                        $apiAddress = '../../api/runResult/SubmitShipmentPlan';
                        jsonForServer = '{\"RunId\": \"' + $("#RunIdClick").text() + '\",\"vehicle_code\":\"' + kode + '\"}';

                        $.post($apiAddress, {json: jsonForServer}).done(function (data) {
                            if (data == 'OK') {
                                alert('sukses');
                                location.reload();
                            } else {
                                alert('submit Status error');
                            }
                        });
                    }else{
                        alert( 'submit SAP error' ); 
                    }
                }); 
            }

            function fnExcelReport()
            {
                //var t = document.getElementById('btnHi');
                //t.hidden = true;
                var tab_text = "<table border='2px'><tr bgcolor='#87AFC6'>";
                var textRange;
                var j = 0;
                tab = document.getElementById('t_table'); // id of table

                for (j = 0; j < tab.rows.length; j++)
                {
                    tab_text = tab_text + tab.rows[j].innerHTML + "</tr>";
                    //tab_text=tab_text+"</tr>";
                }

                tab_text = tab_text + "</table>";
                tab_text = tab_text.replace(/<A[^>]*>|<\/A>/g, "");//remove if u want links in your table
                tab_text = tab_text.replace(/<img[^>]*>/gi, ""); // remove if u want images in your table
                tab_text = tab_text.replace(/<input[^>]*>|<\/input>/gi, ""); // reomves input params

                var ua = window.navigator.userAgent;
                var msie = ua.indexOf("MSIE ");

                if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))      // If Internet Explorer
                {
                    txtArea1.document.open("txt/html", "replace");
                    txtArea1.document.write(tab_text);
                    txtArea1.document.close();
                    txtArea1.focus();
                    sa = txtArea1.document.execCommand("SaveAs", true, "Say Thanks to Sumit.xls");
                } else                 //other browser not tested on IE 11
                    sa = window.open('data:application/vnd.ms-excel,' + encodeURIComponent(tab_text));

                return (sa);
            }
            
            function Vklik() {
                window.open('../Params/PopUp/ShowPreRouteVehicle.jsp?runId=' + $('#RunIdClick').text() + '&stat=run', null,
                        'scrollbars=1,resizable=1,height=500,width=950');

            }
        </script>
        <h4>Routing Result <span class="glyphicon glyphicon-refresh" aria-hidden="true" onclick="location.reload();"></span></h4>

        <label class="fzInput" id="nextRunId" hidden="true"><%=get("nextRunId")%></label>
        <label class="fzInput" id="dateDeliv" hidden="true"><%=get("dateDeliv")%></label>

        <input class="fzInput" id="OriRunID" 
               name="OriRunID" value="<%=get("OriRunID")%>" hidden="true"/>

        <br>
        <label class="fzLabel">Branch:</label> 
        <label class="fzLabel" id="branch"><%=get("branch")%></label>

        <br>
        <label class="fzLabel">Shift:</label> 
        <label class="fzLabel" id="shift"><%=get("shift")%></label>

        <br>
        <label class="fzLabel">Channel:</label> 
        <label class="fzLabel" id="channel"><%=get("channel")%></label> 

        <br>
        <label class="fzLabel">Vehicles:</label> 
        <label class="fzLabel" id="vehicles"><%=get("vehicleCount")%></label>

        <br>
        <label class="fzLabel">RunID:</label> 
        <label class="fzLabel" id="RunIdClick" style="color: blue;"><%=get("runID")%></label> 

        <br>
        <label class="fzLabel" id="mapAll" style="color: blue;">Map</label> 
        <label class="fzLabel" id="Vehicle" style="color: blue;" onclick="Vklik();">Vehicle</label>
        <label class="fzLabel" id="reRun" style="color: blue;">Re-Routing</label>
        <label class="fzLabel" id="test" style="color: blue;" onclick="fnExcelReport()">Convert Excel</label>

        <input id="clickMe" class="btn fzButton" type="button" value="Edit Route Manually" onclick="openEditRoutePage();" />
        
        <br><br>
        <table id="table" border1="1" style="border-color: lightgray;">
            <thead>
                <tr style="background-color:orange">
                    <th width="100px" class="fzCol">No.</th>
                    <th width="100px" class="fzCol">Truck</th>
                    <th width="100px" class="fzCol">CustID</th>
                    <th width="100px" class="fzCol">Arrv</th>
                    <th width="100px" class="fzCol">Depart</th>
                    <th width="100px" class="fzCol">DO Count</th>
                    <th width="100px" class="fzCol">Srvc Time</th>
                    <th width="100px" class="fzCol">Name</th>
                    <th width="100px" class="fzCol">Priority</th>
                    <th width="100px" class="fzCol">Dist Chl</th>
                    <th width="100px" class="fzCol">Street</th>
                    <th width="100px" class="fzCol">Weight (KG)</th>
                    <th width="100px" class="fzCol">Volume (CM3)</th>
                    <th width="100px" class="fzCol">RDD</th>
                    <th width="100px" class="fzCol">Transport Cost</th>
                    <th width="100px" class="fzCol">Dist</th>
<!--                    <th width="100px" class="fzCol">Send SAP</th>-->
                    <th width="100px" class="fzCol">Edit</th>
                </tr>
            </thead>
            <tbody>
                <%for (RouteJob j : (List<RouteJob>) getList("JobList")) { %> 
                <tr 
                    <%if (j.vehicleCode.equals("NA")) {%>
                    style="color: red"
                    <%} else if (j.arrive.length() == 0 && j.depart.length() > 0) {%>
                    style="background-color: lightyellow"
                    <%} else if (j.arrive.length() == 0 && j.name1.length() == 0) {%>
                    style="background-color: #e6ffe6"
                    <%} else if (j.bat == "1" ) {%>
                    style="background-color: #ffe6e6"
                    <%}%> >
                    <td class="fzCell"><%=j.no%></td>
                    <td class="vCodeClick" style="color: blue;"><%=j.vehicleCode%></td>
                    <td class="custIDClick" style="color: blue;"><%=j.custID%></td>
                    <td class="fzCell"><%=j.arrive%></td>
                    <td class="fzCell"><%=j.depart%></td>                    
                    <td class="fzCell"><%=j.DONum%></td>
                    <td class="fzCell"><%=j.getServiceTime()%></td>
                    <td class="fzCell">
                        <%if (j.arrive.length() > 0) {%>
                        <a href="<%=j.getMapLink()%>" target="_blank"><%=j.name1%></a>
                        <%} else {%><%=j.name1%><%}%>
                    <td class="fzCell"><%=j.custPriority%></td>
                    <td class="fzCell"><%=j.distrChn%></td>
                    <td class="fzCell"><%=j.street%></td>
                    <td class="fzCell"><%=j.weight%></td>
                    <td class="fzCell"><%=j.volume%></td>
                    <td class="fzCell"><%=j.rdd%></td>
                    <td class="fzCell"><%=j.transportCost%></td>
                    <td class="fzCell"><%=j.dist%></td>
<!--                    <td class="fzCell" 
                        <%if (j.send != null && (j.send.equalsIgnoreCase("OK") || j.send.equalsIgnoreCase("DELL"))) {%>
                        onclick="sendSAP('<%=j.vehicleCode%>','<%=j.send%>')" style="color: green;"
                        <%}%> ><%=j.send%></td>-->
                    <td class="editCust" onclick="klik(<%=j.custID%>)" style="color: blue;"><%=j.edit%></td>
                </tr>

                <%} // for ProgressRecord %>
            </tbody>
        </table>

        <br><br>
        <iframe id="txtArea1" style="display:none"></iframe>
        <table id="t_table" border1="1" style="border-color: lightgray;" hidden="true">
            <tr>
                <td>Branch : <%=get("branch")%></td>
            </tr>
            <tr>
                <td>Channel : <%=get("channel")%></td>
            </tr>
            <tr>
                <td>Truck : <%=get("vehicleCount")%></td>
            </tr>
            <tr>
                <td>Run Id : <%=get("runID")%></td>
            </tr>
            <tr>
                <td>
                    <table id="d_table" border1="1" style="border-color: lightgray;">
                        <thead>
                            <tr style="background-color:orange">
                                <th width="100px" class="fzCol">No.</th>
                                <th width="100px" class="fzCol">Truck</th>
                                <th width="100px" class="fzCol">CustID</th>
                                <th width="100px" class="fzCol">Arrv</th>
                                <th width="100px" class="fzCol">Depart</th>
                                <th width="100px" class="fzCol">DO Count</th>
                                <th width="100px" class="fzCol">Srvc Time</th>
                                <th width="100px" class="fzCol">Name</th>
                                <th width="100px" class="fzCol">Priority</th>
                                <th width="100px" class="fzCol">Dist Chl</th>
                                <th width="100px" class="fzCol">Street</th>
                                <th width="100px" class="fzCol">Weight</th>
                                <th width="100px" class="fzCol">Volume</th>
                                <th width="100px" class="fzCol">RDD</th>
                                <th width="100px" class="fzCol">Transport Cost</th>
                                <th width="100px" class="fzCol">Dist</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%for (RouteJob j : (List<RouteJob>) getList("JobList")) {%> 
                            <tr>
                                <td class="fzCell"><%=j.no%></td>
                                <td class="vCodeClick" style="color: blue;"><%=j.vehicleCode%></td>
                                <td class="custIDClick" style="color: blue;"><%=j.custID%></td>
                                <td class="fzCell"><%=j.arrive%></td>
                                <td class="fzCell"><%=j.depart%></td>                    
                                <td class="fzCell"><%=j.DONum%></td>
                                <td class="fzCell"><%=j.getServiceTime()%></td>
                                <td class="fzCell"><%=j.name1%></td>
                                <td class="fzCell"><%=j.custPriority%></td>
                                <td class="fzCell"><%=j.distrChn%></td>
                                <td class="fzCell"><%=j.street%></td>
                                <td class="fzCell"><%=j.weight%></td>
                                <td class="fzCell"><%=j.volume%></td>
                                <td class="fzCell"><%=j.rdd%></td>
                                <td class="fzCell"><%=j.transportCost%></td>
                                <td class="fzCell"><%=j.dist%></td>
                            </tr>

                            <%} // for ProgressRecord %>
                        </tbody>
                    </table>                    
                </td>
            </tr>
        </table>
        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

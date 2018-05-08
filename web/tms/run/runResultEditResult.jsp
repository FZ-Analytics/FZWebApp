<%-- 
    Document   : runResultEditResult
    Created on : Dec 11, 2017, 8:47:46 AM
    Author     : rifki.nurfaiz
--%>

<%@page import="com.fz.tms.params.model.DeliverySummary"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%@page import="com.fz.tms.params.model.Delivery"%>
<%run(new com.fz.tms.service.run.LoadDelivery());%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Jobs</title>
        <style>
            .hover:hover {
               cursor: pointer; 
            }
            .center {
                text-align: center;
            }
        </style>
    </head>
    <body>
        <style>
            tr { 
                border-bottom: 2px solid lightgray;
                padding-top: 3px;
                padding-bottom: 3px;
            }

        </style>
        <%@include file="../appGlobal/bodyTop.jsp"%>
        <%
            url = request.getRequestURL().toString();
            String str = url + "?" + request.getQueryString();
            str = str.replace("http://", "");
            str = str.replace(":", "9ETR9");
            str = str.replace(".", "9DOT9");
            str = str.replace("/", "9AK9");
            str = str.replace("?", "9ASK9");
            str = str.replace("&", "9END9");
            str = str.replace("=", "9EQU9");
            str = str.replace("-", "9MIN9");
            String urls = url + "?" + request.getQueryString();
        %>
        <link href="../appGlobal/eFreezeTable.css" rel="stylesheet">
        <script src="../appGlobal/eFreezeTable.js"></script>
        <script>
            $(document).ready(function () {
                $('#table').eFreezeTableHead();
                $('.custIDClick').click(function () {
                    if ($(this).text().length > 0) {
                        window.open("../Params/PopUp/popupDetilDOCust.jsp?custId=" + $(this).text() + "&runId=" + $("#OriRunID").val(), null,
                                "scrollbars=1,resizable=1,height=500,width=750");
                        return true;
                    }
                });
                $('.vCodeClick').click(function () {
                    if ($(this).text().length > 2) {
                        window.open("../Params/map/GoogleDirMap.jsp?runID=" + $("#RunIdClick").text() + "&vCode=" + $(this).text(), null,
                                "scrollbars=1,resizable=1,height=530,width=530");
                        return true;
                    }
                });
                $('#RunIdClick').click(function () {
                    if ($(this).text().length > 0) {
                        window.open("../Params/PopUp/popupDetilRunId.jsp?runID=" + $("#RunIdClick").text() + "&oriRunID=" + $("#OriRunID").val() + "&flag=runResultEditResult" + "&branch=" + $("#branch").text(), null,
                                "scrollbars=1,resizable=1,height=500,width=850");
                        return true;
                    }
                });
                $('#mapAll').click(function () {
                    if ($(this).text().length > 0) {
                        window.open("../Params/map/GoogleDirMapAllVehi.jsp?runID=" + $("#RunIdClick").text() + '&channel=' + $('#channel').text(), null,
                                "scrollbars=1,resizable=1,height=530,width=530");
                        return true;
                    }
                });
            });

            function klik(kode) {
                window.open("../Params/PopUp/popupEditCust.jsp?runId=" + $("#RunIdClick").text() + "&custId=" + kode, null,
                        "scrollbars=1,resizable=1,height=500,width=750");
            }

            function fnExcelReport() {
                var tab_text = "<table border='2px'><tr bgcolor='#87AFC6'>";
                var j = 0;
                tab = document.getElementById('t_table'); // id of table

                for (j = 0; j < tab.rows.length; j++)
                {
                    tab_text = tab_text + tab.rows[j].innerHTML + "</tr>";
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
            function saveHistory() {
                var $apiAddress = '../../api/popupEditCustBfror/savehistory';
                var jsonForServer = '{\"Value\": \"' + '<%=urls%>' + '\",\"NIK\":\"' + '<%=EmpyID%>' + '"}';
                var data = [];

                $.post($apiAddress, {json: jsonForServer}).done(function (data) {
                    if (data == 'OK') {
                        alert('sukses');
                        //location.reload();
                    } else {
                        alert('submit error');
                    }
                });
            }

        </script>
        <div id="body">
            <h4>Routing Result 
                <span class="glyphicon glyphicon-refresh hover" aria-hidden="true" onclick="location.reload();"></span>
                <span class="glyphicon glyphicon-list-alt hover" aria-hidden="true" onclick="saveHistory()"></span>
            </h4>

            <input class="fzInput" id="OriRunID" name="OriRunID" value="<%=get("oriRunId")%>" hidden="true"/>

            <br>
            <label class="fzLabel">Branch:</label> 
            <label class="fzLabel" id="branch"><%=get("branchId")%></label>

            <br>
            <label class="fzLabel">Shift:</label> 
            <label class="fzLabel"><%=get("shift")%></label>

            <br>
            <label class="fzLabel">Channel:</label> 
            <label class="fzLabel" id="channel"><%=get("channel")%></label> 

            <br>
            <label class="fzLabel">Vehicles:</label> 
            <label class="fzLabel"><%=get("vehicle")%></label>

            <br>
            <label class="fzLabel">RunID:</label> 
            <label class="fzLabel hover" id="RunIdClick" style="color: blue;"><%=get("runId")%></label> 

            <br>
            <label class="fzLabel hover" id="mapAll" style="color: blue;">Map</label>
            <label class="fzLabel hover" id="test" style="color: blue;" onclick="fnExcelReport()">Convert Excel</label>
            <br><br>

            <table id="table" border1="1" style="border-color: lightgray;">
                <thead>
                    <tr style="background-color:orange">
                        <th width="100px" class="fzCol center">No.</th>
                        <th width="100px" class="fzCol center">Truck</th>
                        <th width="100px" class="fzCol center">Cust. ID</th>
                        <th width="100px" class="fzCol center">Arrv</th>
                        <th width="100px" class="fzCol center">Depart</th>
                        <th width="100px" class="fzCol center">DO Count</th>
                        <th width="100px" class="fzCol center">Serv. Time</th>
                        <th width="100px" class="fzCol center">Name</th>
                        <th width="100px" class="fzCol center">Prty</th>
                        <th width="100px" class="fzCol center">Dist. Chl</th>
                        <th width="100px" class="fzCol center">Street</th>
                        <th width="100px" class="fzCol center">Weight</th>
                        <th width="100px" class="fzCol center">Volume</th>
                        <th width="100px" class="fzCol center">RDD</th>
                        <th width="100px" class="fzCol center">Cost</th>
                        <th width="100px" class="fzCol center">Dist</th>
                        <th width="100px" class="fzCol center">Access Feas.</th>
                        <th width="100px" class="fzCol center">Cust. Feas.</th>
                        <th width="100px" class="fzCol center">Truck Feas.</th>
                        <th width="100px" class="fzCol center">Edit</th>
                    </tr>
                </thead>
                <tbody>
                    <%for (Delivery j : (List<Delivery>) getList("listDelivery")) { %> 
                    <tr class="tableRows" id="tableRow"
                        <%if (j.vehicleCode.equals("NA")) {%>
                        style="color: red"
                        <%} else if (j.arrive.length() == 0 && j.depart.length() > 0) {%>
                        style="background-color: lightyellow"
                        <%} else if (j.arrive.length() == 0 && j.storeName.length() == 0) {%>
                        style="background-color: #e6ffe6"
                        <%}%> >
                        <td class="fzCell index center">
                            <%if (!j.no.equals("0")) {%>
                                <%=j.no%>
                            <%}%>
                        </td>
                        <td class="vCodeClick hover center" style="color: blue;">
                            <%=j.vehicleCode%>
                        </td>
                        <td class="custIDClick hover center" id="custId" style="color: blue;"><%=j.custId%></td>
                        <td class="fzCell center"><%=j.arrive%></td>
                        <td class="fzCell center"><%=j.depart%></td>                    
                        <td class="fzCell center"><%=j.doNum%></td>
                        <td class="fzCell center">
                            <%if (!j.vehicleCode.equals("NA")) {
                                if (!j.serviceTime.equals("0")) {%>
                                    <%=j.serviceTime%>
                                <%} else {
                                    out.print("");
                                }%>
                            <%} else {
                                out.print("0");
                            }%>  
                        </td>
                        <td class="fzCell center">
                            <%if (!j.vehicleCode.equals("NA")) {%>
                                <a href="<%=j.getMapLink()%>" target="_blank"><%=j.storeName%></a>
                            <%} else {%>
                                <%=j.storeName%>
                            <%}%>
                        <td class="fzCell center">
                            <%if (!j.priority.equals("0")) {%>
                                <%=j.priority%>
                            <%} else {
                                out.print("");
                            }%>
                        </td>
                        <td class="fzCell center"><%=j.distChannel%></td>
                        <td class="fzCell center">
                            <%if(!j.street.equals("")) {%>
                                <%=j.street%>
                            <%} else {%>
                                <p></p>
                            <%}%>
                        </td>
                        <td class="fzCell center">
                            <%if (!j.weight.equals("0.0")) {%>
                                <%=j.weight%>
                            <%} else  {
                                out.print("");
                            }%>
                        </td>
                        <td class="fzCell center"><%=j.volume%></td>
                        <td class="fzCell center">
                            <%if (j.rdd != null) {%>
                                <%=j.rdd%>
                            <%} else {
                                out.print("");
                            }%>
                        </td>
                        <td class="fzCell center">
                            <%if (!j.custId.equals("")) {%>
                                <%=j.transportCost%>
                            <%} else {
                                out.print("");
                            }%>
                        </td>
                        <td class="fzCell center">
                            <%if (!j.custId.equals("")) {%>
                                <%=j.dist%>
                            <%} else {
                                out.print("");
                            }%>
                        </td>
                        <td class="fzCell center"><%=j.feasibleAccess%></td>
                        <td class="fzCell center"><%=j.feasibleCustomer%></td>
                        <td class="fzCell center"><%=j.feasibleTruck%></td>
                        <%if (j.doNum.length() > 0 && !j.vehicleCode.equals("NA")) {%>
                        <td class="editCust hover center" onclick="klik(<%=j.custId%>)" style="color: blue;">
                            edit
                        </td>
                        <%} else {%>
                        <td class="editCust" onclick="" style="color: blue;"></td>
                        <%}%>
                    </tr>

                    <%} // for ProgressRecord %>
                </tbody>
            </table>

            <br>
            <br>

            <iframe id="txtArea1" style="display:none"></iframe>
            <table id="t_table" border1="1" style="border-color: lightgray;" hidden="true">
                <tr>
                    <td>Branch : <%=get("branchId")%></td>
                </tr>
                <tr>
                    <td>Channel : <%=get("channel")%></td>
                </tr>
                <tr>
                    <td>Truck : <%=get("vehicle")%></td>
                </tr>
                <tr>
                    <td>Run Id : <%=get("runId")%></td>
                </tr>
                <tr>
                    <td>
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
                                    <th width="100px" class="fzCol">Weight</th>
                                    <th width="100px" class="fzCol">Volume</th>
                                    <th width="100px" class="fzCol">RDD</th>
                                    <th width="100px" class="fzCol">Transport Cost</th>
                                    <th width="100px" class="fzCol">Dist</th>
                                    <th width="100px" class="fzCol">Access Feas.</th>
                                    <th width="100px" class="fzCol">Cust. Feas.</th>
                                    <th width="100px" class="fzCol">Truck Feas.</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%for (Delivery j : (List<Delivery>) getList("listDelivery")) { %> 
                                <tr class="tableRows" id="tableRow"
                                    <%if (j.vehicleCode.equals("NA")) {%>
                                    style="color: red"
                                    <%} else if (j.arrive.length() == 0 && j.depart.length() > 0) {%>
                                    style="background-color: lightyellow"
                                    <%} else if (j.arrive.length() == 0 && j.storeName.length() == 0) {%>
                                    style="background-color: #e6ffe6"
                                    <%}%> >
                                    <td class="fzCell index"><%=j.no%></td>
                                    <td class="vCodeClick" style="color: blue;"><%=j.vehicleCode%></td>
                                    <td class="custIDClick" id="custId" style="color: blue;"><%=j.custId%></td>
                                    <td class="fzCell"><%=j.arrive%></td>
                                    <td class="fzCell"><%=j.depart%></td>                    
                                    <td class="fzCell"><%=j.doNum%></td>
                                    <td class="fzCell">
                                        <%if (!j.vehicleCode.equals("NA")) {%>
                                        <%=j.serviceTime%>
                                        <%} else {
                                                out.print("0");
                                            }%>  
                                    </td>
                                    <td class="fzCell">
                                        <%if (!j.vehicleCode.equals("NA")) {%>
                                        <a href="<%=j.getMapLink()%>" target="_blank"><%=j.storeName%></a>
                                        <%} else {%>
                                        <%=j.storeName%>
                                        <%}%>
                                    <td class="fzCell><%=j.priority%></td>
                                    <td class="fzCell"><%=j.distChannel%></td>
                                    <td class="fzCell"><%=j.street%></td>
                                    <td class="fzCell"><%=j.weight%></td>
                                    <td class="fzCell"><%=j.volume%></td>
                                    <td class="fzCell"><%=j.rdd%></td>
                                    <%if (j.arrive.length() == 0 && j.storeName.length() == 0) {%>
                                    <td class="fzCell">null</td>
                                    <%} else {%>
                                    <td class="fzCell"><%=j.transportCost%></td>
                                    <%}%>
                                    <td class="fzCell"><%=j.dist%></td>
                                    <td class="fzCell"><%=j.feasibleAccess%></td>
                                    <td class="fzCell"><%=j.feasibleCustomer%></td>
                                    <td class="fzCell"><%=j.feasibleTruck%></td>
                                </tr>

                                <%} // for ProgressRecord %>
                            </tbody>
                        </table>                
                    </td>
                </tr>
            </table>

            <%@include file="../appGlobal/bodyBottom.jsp"%>
        </div>
    </body>
</html>

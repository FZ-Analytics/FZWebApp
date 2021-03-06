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
    </head>
    <body>
        <style>
            tr { 
                border-bottom: 2px solid lightgray;
            }
            
        </style>
        <%@include file="../appGlobal/bodyTop.jsp"%>
        <link href="../appGlobal/eFreezeTable.css" rel="stylesheet">
        <script src="../appGlobal/eFreezeTable.js"></script>
        <script src="jquery.ui.touch-punch.min.js"></script>
        <script>
            $(document).ready(function () {
                $('#table').eFreezeTableHead();
                $('.custIDClick').click(function () {
                    if ($(this).text().length > 0) {
                        window.open("../Params/PopUp/popupDetilDOCust.jsp?custId=" + $(this).text() + "&runId=" + $("#RunIdClick").text(), null,
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
                        window.open("../Params/PopUp/popupDetilRunId.jsp?runID=" + $("#RunIdClick").text(), null,
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

                $('#reRun').click(function () {
                    setTimeout(function () {
                        var dateNow = $.datepicker.formatDate('yy-mm-dd', new Date());//currentDate.getFullYear()+"-"+(currentDate.getMonth()+1)+"-"+currentDate.getDate();

                        var win = window.open('runProcess.jsp?tripCalc=M&shift=1&dateDeliv=' + dateNow + '&branch=' + $('#branch').text() + '&runId=' + $("#RunIdClick").text() + '&oriRunID=' + $("#OriRunID").val()  + '&reRun=A' + '&channel=' + $('#channel').text(), '_blank');
                        if (win) {
                            //Browser has allowed it to be opened
                            win.focus();
                        }
                    }, 3000);
                });
            });
            
            function jumpToResult() {
                //array of customer id at 4th column
                var arrayOfCustId = $('#table td:nth-child(4)').map(function() {
                   return $(this).text();
                }).get();
                var arrStr = encodeURIComponent(JSON.stringify(arrayOfCustId));
               
                var win = window.open('runResultEditResult.jsp?array='+arrStr);
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
        </script>
        <h3>Runs</h3>

        <input class="fzInput" id="OriRunID" 
               name="OriRunID" value="<%=get("OriRunID")%>" hidden="true"/>
        
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
        <label class="fzLabel" id="RunIdClick" style="color: blue;"><%=get("runId")%></label> 

        <br>
        <label class="fzLabel" id="mapAll" style="color: blue;">Map</label>

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
                    <th width="100px" class="fzCol">Weight</th>
                    <th width="100px" class="fzCol">Volume</th>
                    <th width="100px" class="fzCol">RDD</th>
                    <th width="100px" class="fzCol">Transport Cost</th>
                    <th width="100px" class="fzCol">Dist</th>
                    <th width="100px" class="fzCol">Edit</th>
                </tr>
            </thead>
            <tbody>
                <%for(Delivery j : (List<Delivery>) getList("listDelivery")) { %> 
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
                    <td class="fzCell"><%=j.serviceTime%></td>
                    <td class="fzCell">
                        <%if (!j.vehicleCode.equals("NA")) {%>
                        <a href="<%=j.getMapLink()%>" target="_blank"><%=j.storeName%></a>
                        <%} else {%>
                        <%=j.storeName%>
                        <%}%>
                    <td class="fzCell"><%=j.priority%></td>
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
                    <td class="editCust" onclick="klik(<%=j.custId%>)" style="color: blue;">
                        <%if(j.doNum.length() > 0 && !j.vehicleCode.equals("NA")) {%>
                            edit
                        <%}%>
                    </td>
                </tr>

                <%} // for ProgressRecord %>
            </tbody>
        </table>
            
        <br>
        <br>

        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>
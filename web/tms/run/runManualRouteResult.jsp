<%-- 
    Document   : runManualRouteResult
    Created on : Dec 20, 2017, 3:11:03 PM
    Author     : Administrator
--%>

<%@page import="com.fz.tms.params.model.DeliverySummary"%>
<%@page import="com.fz.tms.params.model.Delivery"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%run(new com.fz.tms.service.run.ManualRouteResult());%>
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
            .hamburger {
                width: 30px;
                height: 3px;
                background-color: black;
                margin: 6px 0;
            }
            .arrange:hover {
                cursor: pointer;
            }

            table tr td {
                padding: 2px 15px 2px 15px;
                width: 100px;
            }
            
        </style>
        <%@include file="../appGlobal/bodyTop.jsp"%>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <link href="../appGlobal/eFreezeTable.css" rel="stylesheet">
        <script src="../appGlobal/eFreezeTable.js"></script>
        <script src="jquery.ui.touch-punch.min.js"></script>
        <script>
            $(document).ready(function () {
                
                document.getElementById('oriRunId').style.display = 'none';
                
                $('#table').eFreezeTableHead();
                $('.custIDClick').click(function () {
                    if ($(this).text().length > 0) {
                        window.open("../Params/PopUp/popupDetilDOCust.jsp?custId=" + $(this).text() + "&runId=" + $("#oriRunId").text(), null,
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
                        window.open("../Params/PopUp/popupDetilRunId.jsp?runID=" + $("#oriRunId").text(), null,
                                "scrollbars=1,resizable=1,height=500,width=850");
                        return true;
                    }
                });
                $('#mapAll').click(function () {
                    if ($(this).text().length > 0) {
                        window.open("../Params/map/GoogleDirMapAllVehi.jsp?runID=" + $("#oriRunId").text() + '&channel=' + $('#channel').text(), null,
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
            
            function klik(kode) {
                window.open("../Params/PopUp/popupEditCust.jsp?runId=" + $("#oriRunId").text() + "&custId=" + kode, null,
                        "scrollbars=1,resizable=1,height=500,width=750");
            }
            
            function apply() {
                var branchId = document.getElementById("branch");
                var shift = document.getElementById("shift");
                var vehicle = document.getElementById("vehicle");
                var oriRunId = document.getElementById("oriRunId");
                
                var tableArr = [];
                var table = document.getElementById("table");
                for ( var i = 1; i < table.rows.length; i++ ) {
                    if(table.rows[i].cells[14].innerHTML !== "null") {
                        tableArr.push(
                            table.rows[i].cells[0].innerHTML, //no
                            table.rows[i].cells[1].childNodes[1].value, //truck
                            table.rows[i].cells[2].innerHTML + "split" //custId
                        );
                    }
                }
                
                var win = window.open('runManualRouteResult.jsp?oriRunId='+oriRunId+'&truckCustId='+truckCustIdArr+'&branchId='+branchId+'&shift='+shift+'&channel='+channel+'&vehicle='+vehicle+'&array='+tableArr);
                if (win) {
                    //Browser has allowed it to be opened
                    win.focus();
                }
            }
        </script>
        
        <div class="col-md-12">
            <div class="col-md-12">
                <h3>Runs</h3>

                <label class="fzLabel" id="oriRunId"><%=get("oriRunId")%></label>

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
                <label class="fzLabel" id="vehicle"></label>

                <br>
                <label class="fzLabel">Run ID:</label> 
                <label class="fzLabel"><%=get("runId")%></label>
                
                <br>

                <br><br>
            </div>
        </div>
        
        <div class="col-md-12" id="summary" style="margin-bottom: 50px;">
            <h3>Summary</h3>
            <br>
            <table border1="1" style="border-color: lightgray;">
                <thead>
                    <tr style="background-color:orange">
                        <th width="150px" class="fzCell">Truck Id</th>
                        <th width="150px" class="fzCell">Truck Type</th>
                        <th width="150px" class="fzCell">Capacity(Kg)</th>
                        <th width="150px" class="fzCell">(%)</th>
<!--                        <th width="150px" class="fzCell">Amount</th>-->
                        <th width="150px" class="fzCell">Km</th>
                        <th width="150px" class="fzCell">Serv. Time(Hour)</th>
                        <th width="150px" class="fzCell">Cust. Visit</th>
                        <th width="150px" class="fzCell">Trans. Cost</th>
                    </tr>
                </thead>
                <tbody>
                    <%for(DeliverySummary d : (List<DeliverySummary>) getList("arlistDeliveSummary")) { %>
                        <tr>
                            <td class="fzCell"><b><%=d.vehicleNo%></b></td>
                            <td class="fzCell"><b><%=d.vehicleType%></b></td>
                            <td class="fzCell"><b><%=d.weight%> / <%=d.vehicleVolume%></b></td>
                            <td class="fzCell"><b><%=d.capPercentage%></b></td>
<!--                            <td class="fzCell"><b><%=d.grossAmount%></b></td>-->
                            <td class="fzCell"><b><%=d.tripDistance%></b></td>
                            <td class="fzCell"><b><%=String.format("%.1f", d.serviceTime/60)%></b></td>
                            <td class="fzCell"><b><%=d.numOfCustVisit%></b></td>
                            <td class="fzCell"><b><%=d.transportCost%></b></td>
                        </tr>
                    <%}%>
                </tbody>
                <tbody>
                    <tr>
                        <td>Summary:</td>
                        <td></td>
                        <td></td>
                        <td><%=get("avgCapPercentage")%></td>
<!--                        <td><%=get("sumAmount")%></td>-->
                        <td><%=get("sumTripDist")%></td>
                        <td><%=get("sumServiceTime")%></td>
                        <td><%=get("sumCustVisit")%></td>
                        <td><%=get("transportCost")%></td>
                    </tr>
                </tbody>
            </table>
        </div>

        <table id="table" border1="1" style="border-color: lightgray;" class="table col-md-12">
            <thead>
                <tr style="background-color:orange; text-align: center">
                    <th width="100px">No.</th>
                    <th width="100px">Truck</th>
                    <th width="100px">CustID</th>
                    <th width="100px">Arrv</th>
                    <th width="100px">Depart</th>
                    <th width="100px">DO Count</th>
                    <th width="100px">Srvc Time</th>
                    <th width="100px">Name</th>
                    <th width="100px">Priority</th>
                    <th width="100px">Dist Chl</th>
                    <th width="100px">Street</th>
                    <th width="100px">Weight</th>
<!--                    <th width="100px">Volume</th>-->
                    <th width="100px">Cost</th>
                    <th width="100px">Truck Feas.</th>
                    <th width="100px">Cust Feas.</th>
                    <th width="100px">Edit</th>
                </tr>
            </thead>
            <tbody>
                <%for(Delivery j : (List<Delivery>) getList("arlistRetDeliveryData")) { %>
                <tr id="tableRow">
                    <td class="fzCell index"><%if(j.no != 0) out.print(j.no);%></td>
                    <td><span><%=j.vehicleCode%></span></td>
                    <td id="custId"><%if(j.no != 0) out.print(j.custId);%></td>
                    <td class="fzCell"><%=j.arrive%></td>
                    <td class="fzCell"><%=j.depart%></td>
                    <td class="fzCell"><%if(j.no != 0) out.print(j.doNum);%></td>
                    <td class="fzCell"><%if(j.no != 0) out.print(j.serviceTime);%></td>
                    <td class="fzCell"><%if(j.no != 0) out.print(j.storeName);%></td>
                    <td class="fzCell"><%if(j.no != 0) out.print(j.priority);%></td>
                    <td class="fzCell"><%if(j.no != 0) out.print(j.distChannel);%></td>
                    <td class="fzCell"><%if(j.no != 0) out.print(j.street);%></td>
                    <td class="fzCell"><%if(j.no != 0) out.print(j.weight);%></td>
<!--                    <td class="fzCell"><%=j.volume%></td>-->
                    <td class="fzCell"><%if(j.no != 0) out.print(j.transportCost);%></td>
                    <td class="fzCell">
                        <%if(j.feasibleTruck) { %>
                        Yes
                        <%} else {%>
                            <% if(j.no != 0) { %>
                            <span style="color: red;">No</span>
                            <%}%>
                        <%}%>
                    </td>
                    <td class="fzCell">
                        <%if(j.feasibleCustomer) { %>
                        Yes
                        <%} else if(j.dbNull) { %>
                        null
                        <%} else {%>
                            <% if(j.no != 0) { %>
                            <span style="color: red;">No</span>
                            <%}%>
                        <%}%>
                    </td>
                    <td class="editCust" onclick="klik(<%=j.custId%>)" style="color: blue;"><%if(j.no != 0)%>edit</td>
                </tr>
                <%}%>
            </tbody>
        </table>

        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>
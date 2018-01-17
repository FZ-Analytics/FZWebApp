    <%-- 
    Document   : runManualRoute
    Created on : Dec 15, 2017, 5:15:38 PM
    Author     : rifki.nurfaiz
--%>

<%@page import="com.fz.tms.params.model.Delivery"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%run(new com.fz.tms.service.run.ManualRoute());%>
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
        <%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c-rt" %>
        <link href="../appGlobal/eFreezeTable.css" rel="stylesheet">
        <script src="../appGlobal/eFreezeTable.js"></script>
        <script src="jquery.ui.touch-punch.min.js"></script>
        <script>
            var i = 0;
            var idx = 0;
            
            $(document).ready(function () {
                
                document.getElementById('oriRunId').style.display = 'none';
                
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
            
            function klik(kode) {
                //alert('tes : ' + kode);
                window.open("../Params/PopUp/popupEditCust.jsp?runId=" + $("#oriRunId").text() + "&custId=" + kode, null,
                        "scrollbars=1,resizable=1,height=500,width=750");
            }
            
            function apply() {
                var tableArr = [];
                var table = document.getElementById("table");
                
                for ( var i = 1; i < table.rows.length; i++ ) {
                    tableArr.push(
                        table.rows[i].cells[1].childNodes[1].value, //truck
                        table.rows[i].cells[0].childNodes[0].value, //no
                        table.rows[i].cells[2].innerHTML + "split" //custId
                    );
                }
                
                var win = window.open('runManualRouteResult.jsp?oriRunId='+$("#oriRunId").text()+'&branchId='+$("#branch").text()+'&shift='+$("#shift").text()+'&channel='+$("#channel").text()+'&vehicle='+$("#vehicle").text()+'&array='+tableArr+'&runId='+$("#runId").text());
                if (win) {
                    //Browser has allowed it to be opened
                    win.focus();
                }
            }
            
            function setSelect(vCode) {
                console.log(vCode);
                var runId = document.getElementById('oriRunId').innerHTML;
                
                var $apiAddress = '../../api/runManualRoute/getVehicleNo';
                var jsonForServer = '{\"RunId\": \"' + runId + '\"}';

                $.post($apiAddress, {json: jsonForServer}).done(function (data) {
                    $(".selectVehicle").get(idx).options.length = 0;
                    $.each(data, function (index, item) {
                        $(".selectVehicle").get(idx).options[$(".selectVehicle").get(idx).options.length] = new Option(item.vehicleNo);
                        $(".selectVehicle").get(idx).value = vCode;
                    });
                    idx++;
                });
            }
            
        </script>
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
        <label class="fzLabel">RunID:</label> 
        <label class="fzLabel" id="runId"><%=get("runId")%></label> 

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
                    <th width="100px" class="fzCol">Cost</th>
                    <th width="100px" class="fzCol">Edit</th>
                </tr>
            </thead>
            <tbody>
                <%for(Delivery j : (List<Delivery>) getList("listDelivery")) { %>
                <tr id="tableRow">
                    <td class="fzCell index"><input type="input" size="1" type="number" value="<%=j.no%>"></td>
                    <script>
                        var vehicleCode = "<%=j.vehicleCode%>";
                        setSelect(vehicleCode);
                        i++;
                    </script>
                    <td>
                        <select name="selectVehicle" class="selectVehicle"></select>
                    </td>
                    <td id="custId"><%=j.custId%></td>
                    <td class="fzCell"><%=j.arrive%></td>
                    <td class="fzCell"><%=j.depart%></td>
                    <td class="fzCell"><%=j.doNum%></td>
                    <td class="fzCell"><%=j.serviceTime%></td>
                    <td class="fzCell"><%=j.storeName%></td>
                    <td class="fzCell"><%=j.priority%></td>
                    <td class="fzCell"><%=j.distChannel%></td>
                    <td class="fzCell"><%=j.street%></td>
                    <td class="fzCell"><%=j.weight%></td>
                    <td class="fzCell">-</td>
                    <td class="fzCell">-</td>
                    <td class="editCust" onclick="klik(<%=j.custId%>)" style="color: blue;">edit</td>
                </tr>
                <%}%>
            </tbody>
        </table>
            
        <br>
        <br>
            
        <input id="submit" class="btn fzButton" type="button" value="Apply" width="200" height="48" onclick="apply();" 
               style="padding-left: 50px; padding-right: 50px; padding-bottom: 10px; padding-top: 10px; font-size: 16px" />

        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

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
                padding-top: 3px;
                padding-bottom: 3px;
            }
            .sk-cube-grid {
                width: 50px;
                height: 50px;
                margin: 50px auto;
            }

            .sk-cube-grid .sk-cube {
                width: 33%;
                height: 33%;
                background-color: orange;
                float: left;
                -webkit-animation: sk-cubeGridScaleDelay 1.3s infinite ease-in-out;
                        animation: sk-cubeGridScaleDelay 1.3s infinite ease-in-out; 
            }
            
            .sk-cube-grid .sk-cube1 {
                -webkit-animation-delay: 0.2s;
                        animation-delay: 0.2s; 
            }
            
            .sk-cube-grid .sk-cube2 {
                -webkit-animation-delay: 0.3s;
                        animation-delay: 0.3s; 
            }
            
            .sk-cube-grid .sk-cube3 {
                -webkit-animation-delay: 0.4s;
                        animation-delay: 0.4s; 
            }

            .sk-cube-grid .sk-cube4 {
                -webkit-animation-delay: 0.1s;
                        animation-delay: 0.1s; 
            }
            
            .sk-cube-grid .sk-cube5 {
                -webkit-animation-delay: 0.2s;
                        animation-delay: 0.2s; 
            }
              
            .sk-cube-grid .sk-cube6 {
                -webkit-animation-delay: 0.3s;
                        animation-delay: 0.3s; 
            }
             
            .sk-cube-grid .sk-cube7 {
                -webkit-animation-delay: 0s;
                        animation-delay: 0s; 
            }
            
            .sk-cube-grid .sk-cube8 {
                -webkit-animation-delay: 0.1s;
                        animation-delay: 0.1s; 
            }
            
            .sk-cube-grid .sk-cube9 {
                -webkit-animation-delay: 0.2s;
                        animation-delay: 0.2s; 
            }

            @-webkit-keyframes sk-cubeGridScaleDelay {
                0%, 70%, 100% {
                  -webkit-transform: scale3D(1, 1, 1);
                          transform: scale3D(1, 1, 1);
                } 35% {
                  -webkit-transform: scale3D(0, 0, 1);
                          transform: scale3D(0, 0, 1); 
                }
            }

            @keyframes sk-cubeGridScaleDelay {
                0%, 70%, 100% {
                  -webkit-transform: scale3D(1, 1, 1);
                          transform: scale3D(1, 1, 1);
                } 35% {
                  -webkit-transform: scale3D(0, 0, 1);
                          transform: scale3D(0, 0, 1);
                } 
            }
        </style>
        <%@include file="../appGlobal/bodyTop.jsp"%>
        <link href="../appGlobal/eFreezeTable.css" rel="stylesheet">
        <script src="../appGlobal/eFreezeTable.js"></script>
        <script src="jquery.ui.touch-punch.min.js"></script>
        <script>
            $(document).ready(function () {
                $(".submitToSap").hide();
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
                $(".submitBtn").click(function() {
                    $("#body").hide();
                    $(".submitToSap").show();
                    var elementId = this.id;
                    var vNo = $(this).val();
                    var runId = $("#RunIdClick").text();
                    
                    var $apiAddress = '../../api/submitToSap/submitToSap';
                    
                    var jsonForServer = '{\"RunId\": \"' + runId + '\",\"vehicle_no\":\"' + vNo+ '\"}';
                    
                    $.post($apiAddress, {json: jsonForServer}).done(function (data) {
                        if(data == 'OK'){
                            $("#body").show();
                            $(".submitToSap").hide();
                            alert( 'sukses' );
                            $('#'+elementId).html('Submitted');
                            $('#'+elementId).addClass('disabled');
                            $('#'+elementId).off('click');
                        } else{
                            $("#body").show();
                            $(".submitToSap").hide();
                            alert( 'submit error' ); 
                        }
                    });
                });
                
                $("#success-alert").hide();
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
                window.open("../Params/PopUp/popupEditCust.jsp?runId=" + $("#RunIdClick").text() + "&custId=" + kode, null,
                        "scrollbars=1,resizable=1,height=500,width=750");
            }
            
        </script>
        <div id="body">
            <h3>Runs</h3>
        
        <input class="fzInput" id="OriRunID" name="OriRunID" value="<%=get("OriRunID")%>" hidden="true"/>
        
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
                    <th width="100px" class="fzCol">Access Feas.</th>
                    <th width="100px" class="fzCol">Cust. Feas.</th>
                    <th width="100px" class="fzCol">Truck Feas.</th>
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
                    <td class="fzCell">
                        <%if(!j.vehicleCode.equals("NA")) {%>
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
                    <td class="fzCell"><%=j.feasibleAccess%></td>
                    <td class="fzCell"><%=j.feasibleCustomer%></td>
                    <td class="fzCell"><%=j.feasibleTruck%></td>
                    <%if(j.doNum.length() > 0 && !j.vehicleCode.equals("NA")) {%>
                        <td class="editCust" onclick="klik(<%=j.custId%>)" style="color: blue;">
                            edit
                        </td>
                    <%} else if(j.arrive.length() > 0) {%>
                        <%if(j.isFix.equals("1")) {%>
                            <td class="" onclick="" style="">
                                <button id="<%=j.vehicleCode%>" class="btn btn-success btn-xs disabled">Submitted</button>
                            </td>
                        <%} else {%>
                            <td class="" onclick="" style="">
                                <button id="<%=j.vehicleCode%>" class="btn btn-success btn-xs submitBtn" type="submit" value="<%=j.vehicleCode%>">Submit</button>
                            </td>
                        <%}%>
                    <%} else {%>
                        <td class="editCust" onclick="" style="color: blue;">
                            
                        </td>
                    <%}%>
                    </td>
                </tr>

                <%} // for ProgressRecord %>
            </tbody>
        </table>
            
        <br>
        <br>

        <%@include file="../appGlobal/bodyBottom.jsp"%>
        </div>
        <div class="submitToSap" style="text-align: center;">
            <div class="sk-cube-grid">
                <div class="sk-cube sk-cube1"></div>
                <div class="sk-cube sk-cube2"></div>
                <div class="sk-cube sk-cube3"></div>
                <div class="sk-cube sk-cube4"></div>
                <div class="sk-cube sk-cube5"></div>
                <div class="sk-cube sk-cube6"></div>
                <div class="sk-cube sk-cube7"></div>
                <div class="sk-cube sk-cube8"></div>
                <div class="sk-cube sk-cube9"></div>
            </div>
            <p>Submitting to SAP</p>
        </div>
    </body>
</html>
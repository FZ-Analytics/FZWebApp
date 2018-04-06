<%-- 
    Document   : popupDetilRunId
    Created on : Oct 31, 2017, 5:07:49 PM
    Author     : dwi.rangga
--%>
<%@include file="../../appGlobal/pageTop.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.fz.tms.params.model.SummaryVehicle"%>
<%run(new com.fz.tms.params.PopUp.popupDetilRunId());%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="../appGlobal/bodyTop.jsp"%>
        <style>
            .lds-ellipsis {
                display: inline-block;
                position: relative;
                width: 64px;
                height: 64px;
            }
            .lds-ellipsis div {
                position: absolute;
                top: 27px;
                width: 11px;
                height: 11px;
                border-radius: 50%;
                background: orange;
                animation-timing-function: cubic-bezier(0, 1, 1, 0);
            }
            .lds-ellipsis div:nth-child(1) {
                left: 6px;
                animation: lds-ellipsis1 0.6s infinite;
            }
            .lds-ellipsis div:nth-child(2) {
                left: 6px;
                animation: lds-ellipsis2 0.6s infinite;
            }
            .lds-ellipsis div:nth-child(3) {
                left: 26px;
                animation: lds-ellipsis2 0.6s infinite;
            }
            .lds-ellipsis div:nth-child(4) {
                left: 45px;
                animation: lds-ellipsis3 0.6s infinite;
            }
            @keyframes lds-ellipsis1 {
                0% {
                    transform: scale(0);
                }
                100% {
                    transform: scale(1);
                }
            }
            @keyframes lds-ellipsis3 {
                0% {
                    transform: scale(1);
                }
                100% {
                    transform: scale(0);
                }
            }
            @keyframes lds-ellipsis2 {
                0% {
                    transform: translate(0, 0);
                }
                100% {
                    transform: translate(19px, 0);
                }
            }

        </style>
        <script>
            $(document).ready(function () {
                $("#loader").hide();
                $(".submitBtn").click(function () {
                    var vNo = $(this).val();

                    var $apiAddress = '../../../api/submitToSap/submitToSap';

                    //if opened from runResult, runID is run id and oriRunID is next run id
                    //if opened from whatIf, runID is run id and ori run id is ori run id
                    var jsonForServer = '{\"runId\": \"' + $("#runID").text() + '\", \"oriRunId\":\"' + $("#oriRunID").val() + '\", \"vehicle_no\":\"' + vNo + '\"}';

                    $("#body").fadeOut();
                    $("#loader").fadeIn();
                    //Submit to Result_Shipment
                    $.post($apiAddress, {json: jsonForServer}).done(function (data) {
                        if (data == 'OK') {
                            $("#body").fadeIn();
                            $("#loader").fadeOut();
                            location.reload();
                        } else {
                            $("#body").fadeIn();
                            $("#loader").fadeOut();
                            alert(data);
                        }
                    });
                });
            });
        </script>
        <div id="loader" style="text-align: center;">
            <div class="lds-ellipsis"><div></div><div></div><div></div><div></div></div>
            <p>Processing</p>
        </div>
        <img src="../img/ffbtoplogo.png">
        <br>
        <h4>Summary <span class="glyphicon glyphicon-refresh" aria-hidden="true" onclick="location.reload();"></span></h4>
        <div id="body">
            <br>
            <label class="fzLabel">Branch:</label> 
            <label class="fzLabel"><%=get("branch")%></label>

            <br>
            <input class="fzInput" id="oriRunID" name="oriRunID" value="<%=get("oriRunID")%>" hidden="true"/>
            <label class="fzLabel">RunID:</label> 
            <label class="fzLabel" id="runID"><%=get("runID")%></label> 

            <br><br>
            <table class="table" border1="1" style="border-color: lightgray;">
                <tr style="background-color:orange">
                    <th width="100px" class="fzCol">Truck id</th>
                    <th width="100px" class="fzCol">Truck type</th>
                    <th width="100px" class="fzCol">Capacity(KG)</th>
                    <th width="100px" class="fzCol">(%)</th>
                    <th width="100px" class="fzCol">Kubikasi(CM3)</th>
                    <th width="100px" class="fzCol">(%)</th>
                    <th width="100px" class="fzCol">Amount</th>
                    <th width="100px" class="fzCol">Jarak tempuh (km)</th>
                    <th width="100px" class="fzCol">Waktu travel</th>
                    <th width="100px" class="fzCol">Waktu service</th>
                    <th width="100px" class="fzCol">Cust visit</th>
                    <th width="100px" class="fzCol">Transport cost</th>
                    <th width="100px" class="fzCol">Submit to SAP</th>
                    <th width="100px" class="fzCol">Error msg.</th>
                </tr>
                <%for (SummaryVehicle s : (List<SummaryVehicle>) getList("ListSum")) {%> 
                <tr>
                    <th width="100px" class="fzCol"><%=s.truckid%></th>
                    <th width="100px" class="fzCol"><%=s.trucktype%></th>
                    <th width="100px" class="fzCol"><%=s.capacity%></th>
                    <th width="100px" class="fzCol"><%=s.capacityPer%></th>
                    <th width="100px" class="fzCol"><%=s.kubikasi%></th>
                    <th width="100px" class="fzCol"><%=s.kubikasiPer%></th>
                    <th width="100px" class="fzCol"><%=s.amount%></th>
                    <th width="100px" class="fzCol"><%=s.km%></th>
                    <th width="100px" class="fzCol"><%=s.time%></th>
                    <th width="100px" class="fzCol"><%=s.sctime%></th>
                    <th width="100px" class="fzCol"><%=s.DOcount%></th>
                    <th width="100px" class="fzCol"><%=s.transportCost%></th>
                    <%if (s.isFix.equals("1")) {%>
                    <th class="" onclick="" style="">
                        <button id="<%=s.truckid%>" class="btn btn-success btn-xs disabled">Submitting</button>
                    </th>
                    <%} else if (Character.isDigit(s.isFix.charAt(0)) && s.isFix.length() == 10) {%>
                    <th class="" onclick="" style="">
                        <button id="<%=s.truckid%>" class="btn btn-default btn-xs disabled" value="<%=s.isFix%>"><%=s.isFix%></button>
                    </th>
                    <%} else if(s.isFix.equals("er")) {%>
                    <th class="" onclick="" style="">
                        <button id="<%=s.truckid%>" class="btn btn-default btn-xs disabled" value="<%=s.isFix%>">Submit</button>
                    </th>
                    <%} else {%>
                    <th class="" onclick="" style="">
                        <button id="<%=s.truckid%>" class="btn btn-success btn-xs submitBtn" type="submit" value="<%=s.truckid%>">Submit</button>
                    </th>
                    <%}%>
                    <th class="fzCell" style="font-size: 10px;"><%=s.error%></th>
                </tr>
                <%} // for ProgressRecord %>
                <tr>
                    <td class="fzCol">Summary: </td>
                    <td class="fzCol"></td>
                    <td class="fzCol"></td>
                    <td class="fzCol"><%=get("cap")%></td>
                    <td class="fzCol"></td> 
                    <td class="fzCol"><%=get("kub")%></td>
                    <td class="fzCol"><%=get("tamount")%></td>
                    <td class="fzCol"><%=get("tkm")%></td>
                    <td class="fzCol"><%=get("ttravel")%></td>
                    <td class="fzCol"><%=get("tservice")%></td>
                    <td class="fzCol"><%=get("tcust")%></td>
                    <td class="fzCol"><%=get("ttransportCost")%></td>
                </tr>
            </table>
        </div>

        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

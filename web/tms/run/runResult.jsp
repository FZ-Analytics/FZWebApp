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
                        window.open("../Params/PopUp/popupDetilRunId.jsp?runID=" + $("#RunIdClick").text(), null,
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
                    //setTimeout(function () {
                        //Some code
                        //alert( $("#runID").text()+"&vCode="+$(this).text() ); 
                        //if ($(this).text().length > 0) {
                        //window.open("../Params/map/GoogleDirMapAllVehi.jsp?runID=" + $("#RunIdClick").text(), null,
                        // "scrollbars=1,resizable=1,height=530,width=530");
                        //return true;
                        //}
                        //var currentDate = new Date();
                        //currentDate.setDate(currentDate.getDate() + 1);
                        var dateNow = $.datepicker.formatDate('yy-mm-dd', new Date());//currentDate.getFullYear()+"-"+(currentDate.getMonth()+1)+"-"+currentDate.getDate();

                        var win = window.open('runProcess.jsp?shift=1&dateDeliv=' + dateNow + '&branch=' + $('#branch').text() + '&runId=' + $("#RunIdClick").text() + '&oriRunID=' + $("#OriRunID").val()  + '&reRun=A' + '&channel=' + $('#channel').text(), '_blank');
                        if (win) {
                            //Browser has allowed it to be opened
                            win.focus();
                        }
                    //}, 3000);
                });
                /*
                $('#branch').click(function () {
                    var $apiAddress = '../../api/customerAttrView/submitTest';
                    var jsonForServer = '{\"flag\": \"flag\"}';
                    var data = [];
                    //alert(jsonForServer);
                    $.post($apiAddress, {json: jsonForServer}).done(function (data) {
                        if(data == 'OK'){
                            alert( 'sukses' );
                            location.reload()
                        }else{
                            alert( data ); 
                        }
                    });
                });
                */
            });

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
        <label class="fzLabel" id="branch"><%=get("branch")%></label>

        <br>
        <label class="fzLabel">Shift:</label> 
        <label class="fzLabel"><%=get("shift")%></label>
        
        <br>
        <label class="fzLabel">Channel:</label> 
        <label class="fzLabel" id="channel"><%=get("channel")%></label> 

        <br>
        <label class="fzLabel">Vehicles:</label> 
        <label class="fzLabel"><%=get("vehicleCount")%></label>

        <br>
        <label class="fzLabel">RunID:</label> 
        <label class="fzLabel" id="RunIdClick" style="color: blue;"><%=get("runID")%></label> 

        <br>
        <label class="fzLabel" id="mapAll" style="color: blue;">Map</label> 
        <label class="fzLabel" id="reRun" style="color: blue;">Re-Routing</label> 

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
                <%for (RouteJob j : (List<RouteJob>) getList("JobList")) { %> 
                <tr 
                    <%if (j.vehicleCode.equals("NA")) {%>
                    style="color: red"
                    <%} else if (j.arrive.length() == 0 && j.depart.length() > 0) {%>
                    style="background-color: lightyellow"
                    <%} else if (j.arrive.length() == 0 && j.name1.length() == 0) {%>
                    style="background-color: #e6ffe6"
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
                    <td class="editCust" onclick="klik(<%=j.custID%>)" style="color: blue;"><%=j.edit%></td>
                </tr>

                <%} // for ProgressRecord %>
            </tbody>
        </table>

        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

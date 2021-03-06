<%@page import="com.fz.tms.params.model.Delivery"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%@page import="com.fz.tms.service.run.RouteJob"%>
<%run(new com.fz.tms.service.run.RouteJobListingResultEdit());%>

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
            
            #oriRunId {
                display:none;
                visibility:hidden;
            }
            
            .menu {
                width: 170px;
                background-color: #FFFFFF;
                color: #000000;
                position:absolute;
                display: none;
                box-shadow: 0 0 5px #713C3C;
            }
            .menu ul {
                list-style: none;
                padding: 0;
                margin:0;
            }
            
            .menu ul {
                text-decoration: none;
            }
            
            .menu ul li {
                padding: 6%;
                background-color: #FFFFFF;
                color: #000000;
                font-size: 10px;
            }
            
            .menu ul li:hover {
                background-color: orange;
                color: black;
                cursor: pointer;
        </style>
        <%@include file="../appGlobal/bodyTop.jsp"%>
        <link href="../appGlobal/eFreezeTable.css" rel="stylesheet">
        <script src="../appGlobal/eFreezeTable.js"></script>
        <script src="jquery.ui.touch-punch.min.js"></script>
        <script>
            var rowIdx = 0;
            var vNoTop = "";
            var vNoBottom = "";
            var arrOfRow = [];
            var boolCanCut = 1;
            var vehicleCode = "";
            
            $(document).ready(function () {
                
                //draggable table row
//                $("tbody").sortable({
//                    appendTo: "parent",
//                    helper: "clone"
//                });
                
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
                
                initContextMenu();
            });
            
            function initContextMenu() {
                boolCanCut = 1;
                $(".tableRows").on("contextmenu",function(e){
                    //prevent default context menu for right click
                    e.preventDefault();
                    
                    rowIdx = this.rowIndex;
                    vNoTop = document.getElementById('table').rows[rowIdx-1].cells[1].innerHTML;
                    vNoBottom = document.getElementById('table').rows[rowIdx+1].cells[1].innerHTML;

                    var menu = $(".menu"); 
                    
                    //hide menu if already shown
                    menu.hide(); 
		       
		    //get x and y values of the click event
		    var pageX = e.pageX;
		    var pageY = e.pageY;

		    //position menu div near mouse cliked area
		    menu.css({top: pageY , left: pageX});

		    var mwidth = menu.width();
		    var mheight = menu.height();
		    var screenWidth = $(window).width();
		    var screenHeight = $(window).height();

        	    //if window is scrolled
		    var scrTop = $(window).scrollTop();

		    //if the menu is close to right edge of the window
		    if(pageX+mwidth > screenWidth){
                        menu.css({left:pageX-mwidth});
		    }

		    //if the menu is close to bottom edge of the window
		    if(pageY+mheight > screenHeight+scrTop){
		       	menu.css({top:pageY-mheight});
		    }
                    
                    if(boolCanCut) {
                        $("#pasteAtTop").css("color", "grey");
                        $("#pasteAtBottom").css("color", "grey");
                        $("#cut").css("color", "black");
                    }
                    else {
                        $("#pasteAtTop").css("color", "black");
                        $("#pasteAtBottom").css("color", "black");
                        $("#cut").css("color", "grey");
                    }

		    //finally show the menu
		    menu.show();
		}); 
		
		$("html").on("click", function(){
                    $(".menu").hide();
		});
            }
            
            function deleteRow() {
                vehicleCode = document.getElementById('table').rows[rowIdx].cells[1].innerHTML;
                if(boolCanCut) {
                    //put data row to array
                    for(i = 0; i <= 16; i++) {
                        arrOfRow[i] = document.getElementById('table').rows[rowIdx].cells[i].innerHTML;
                    }
                    document.getElementById("table").deleteRow(rowIdx);
                }
                boolCanCut = 0;
                orderNo();
            }
            
            function paste(s) {
                if(!boolCanCut) {
                    var table = document.getElementById("table");
                    if(s === 'top') {
                        var row = table.insertRow(rowIdx);
                    }
                    else {
                        var row = table.insertRow(rowIdx+1);
                    }
                    for(i = 0; i <= 16; i++) {
                        //vehicle code row
                        if(i == 1) {
                            var cell = row.insertCell(i);
                            //vehicle no row not empty
                            if(arrOfRow[i] !== "") {
                                //row move within the same vehicle no
                                if(vNoBottom === vNoTop) {
                                    //row move at different vehicle no
                                    if(vNoBottom !== arrOfRow[i]) {
                                        cell.innerHTML = vNoBottom;
                                        vehicleCode = vNoBottom;
                                    }
                                    else {
                                        cell.innerHTML = arrOfRow[i];
                                        vehicleCode = arrOfRow[i];
                                    }
                                }
                                //row move at the same vehicle no, but near break time
                                else if(vNoBottom === "" && vNoTop === arrOfRow[i]) {
                                    cell.innerHTML = vNoTop;
                                    vehicleCode = vNoTop;
                                }
                                //row move at the same vehicle no, but near break time
                                else if(vNoTop === "" && vNoBottom === arrOfRow[i]) {
                                    cell.innerHTML = vNoBottom;
                                    vehicleCode = vNoBottom;
                                }
                                //row move between two different vehicle no
                                else if(vNoBottom !== vNoTop) {
                                    if(vNoTop === "NA") {
                                        if(s === "top") {
                                            cell.innerHTML = vNoTop;
                                            vehicleCode = vNoTop;
                                        }
                                        else {
                                            if(document.getElementById('table').rows[rowIdx].cells[1].innerHTML === "NA") {
                                                cell.innerHTML = "NA";
                                                vehicleCode = "NA";
                                            }
                                            else {
                                                cell.innerHTML = vNoBottom;
                                                vehicleCode = vNoBottom;
                                            }
                                        }
                                    }
                                    else if(vNoTop !== "Truck") {
                                        //row move between two different vehicle no and put at top
                                        if(s === "top") {
                                            cell.innerHTML = vNoTop;
                                            vehicleCode = vNoTop;
                                            if(vNoTop === "") {
                                                cell.innerHTML = vNoBottom;
                                                vehicleCode = vNoBottom;
                                            }
                                        }
                                        //row move between two different vehicle no and put at bottom
                                        if(s === "bottom") {
                                            cell.innerHTML = vNoBottom;
                                            vehicleCode = vNoBottom;
                                            if(vNoBottom === "") {
                                                cell.innerHTML = vNoTop;
                                                vehicleCode = vNoTop;
                                            }
                                        }
                                    }
                                    else if(vNoTop === "Truck" && vNoBottom !== "NA") {
                                        if(s === "top") {
                                            cell.innerHTML = "NA";
                                            vehicleCode = "NA";
                                        }
                                        else {
                                            cell.innerHTML = vNoBottom;
                                            vehicleCode = vNoBottom;
                                        }
                                    }
                                    else {
                                        cell.innerHTML = vNoBottom;
                                        vehicleCode = vNoBottom;
                                    }
                                }
                                else {
                                    cell.innerHTML = vNoBottom;
                                    vehicleCode = vNoBottom;
                                }
                                cell.style.color = "blue";
                                cell.className = "vCodeClick";
                            }
                            else {
                                row.style.backgroundColor = "#e6ffe6";
                            }
                        }
                        //customer id row
                        else if(i == 2) {
                            var cell = row.insertCell(i);
                            cell.innerHTML = arrOfRow[i];
                            cell.style.color = "blue";
                            cell.className = "custIdClick";
                            cell.id = "custId";
                        }
                        //edit row
                        else if(i == 16) {
                            var cell = row.insertCell(i);
                            cell.innerHTML = arrOfRow[i];
                            cell.style.color = "blue";
                            cell.className = "editCust";
                        }
                        else {
                            row.insertCell(i).innerHTML = arrOfRow[i];
                        }
                    }
                    row.setAttribute('id', 'tableRow');
                    row.setAttribute('class', 'tableRows');
                }
                boolCanCut = 1;
                initContextMenu();
                orderNo();
            }
            
            function orderNo() {
                var tableLength = document.getElementById('table').rows.length;
                var idx = 1;
                for(i = 0; i <= tableLength; i++) {
                    var currentVehicleCode = document.getElementById('table').rows[i].cells[1].innerHTML;
                    if(currentVehicleCode !== "NA") {
                        var custId = document.getElementById('table').rows[i].cells[2].innerHTML;
                        if(currentVehicleCode === vehicleCode && custId !== "") {
                            document.getElementById('table').rows[i].cells[0].innerHTML = idx;
                            idx++;
                        }
                    }
                    else {
                        document.getElementById('table').rows[i].cells[0].innerHTML = "";
                    }
                }
            }
            
            function jumpToResult() {            
                var table = document.getElementById("table");
                
                var tableArr2 = [];
                for (var i = 1; i < table.rows.length; i++ ) {
                    var no = table.rows[i].cells[0].innerHTML; //no
                    var truck = table.rows[i].cells[1].innerHTML; //truck
                    var custId = "";
                     if((table.rows[i].cells[1].innerHTML !== "") && (table.rows[i].cells[2].innerHTML === "") && (table.rows[i].cells[4].innerHTML !== "")) {
                            custId = "start" + "split";
                        }
                        else {
                            custId = table.rows[i].cells[2].innerHTML + "split"; //custId
                        }
                    tableArr2.push(
                        no,
                        truck,
                        custId
                    );
                }
                
                var win = window.open('runResultEditResult.jsp?runId='+$('#RunIdClick').text()+'&oriRunId='+$('#oriRunId').text()+'&branchId='+$('#branch').text()+
                        '&shift='+$('#shift').text()+'&channel='+$('#channel').text()+'&vehicle='+$('#vehicles').text()+'&array='+tableArr2);
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
        <label class="fzLabel" id="vehicles"><%=get("vehicles")%></label>

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
                        <%if (j.arrive.length() > 0) {%>
                        <a href="<%=j.getMapLink()%>" target="_blank"><%=j.storeName%></a>
                        
                        <%} else {%><%=j.storeName%><%}%>
                    <td class="fzCell"><%=j.priority%></td>
                    <td class="fzCell"><%=j.distChannel%></td>
                    <td class="fzCell"><%=j.street%></td>
                    <td class="fzCell"><%=j.weight%></td>
                    <td class="fzCell"><%=j.volume%></td>
                    <td class="fzCell"><%=j.rdd%></td>
                    <td class="fzCell"><%=j.transportCost%></td>
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
        
        <div class="menu">
            <ul>
                <li id="cut" onclick="deleteRow()">Cut</li>
                <li id="pasteAtTop" onclick="paste('top')">Paste at Top of This Row</li>
                <li id="pasteAtBottom" onclick="paste('bottom')">Paste at Bottom of This Row</li>
            </ul>
        </div>
            
        <input id="submit" class="btn fzButton" type="button" value="Edit" width="200" height="48" onclick="jumpToResult();" 
               style="padding-left: 50px; padding-right: 50px; padding-bottom: 10px; padding-top: 10px; font-size: 16px" />

        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>
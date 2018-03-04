<%-- 
    Document   : hvsEstmList
    Created on : Sep 23, 2017, 6:14:45 AM
--%>
<%@page import="com.fz.ffbv3.service.hvsEstm.HvsEstm"%>
<%@page import="com.fz.ffbv3.service.vehicle2.Vehicle2Record"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>List</title>
    </head>
    <body>
    <%@include file="../appGlobal/bodyTop.jsp"%>

        <h3>Estimation</h3>
        <table class="table" id="tbData">
            <tr>
                <th width="100px" class="fzCol">To Run</th>
                <th width="100px" class="fzCol">Harvest Date</th>
                <th width="100px" class="fzCol">Status</th>
                <th width="100px" class="fzCol">Div</th>
                <th width="100px" class="fzCol">Kg</th>
                <th width="100px" class="fzCol">Mill</th>
                <th width="100px" class="fzCol">Grabber Condition</th>
                <th width="100px" class="fzCol"><!--view--></th>
            </tr>
            
        <%boolean showRunBtn = false;%>
        
        <%for (HvsEstm he : (List<HvsEstm>) getList("hvsEstmList")) { %> 
        
            <tr>
                <td class="fzCell">
                    
                    <%if (he.status.equals("FNAL")){%>
                    
                        <%showRunBtn = true;%>
                        
                        <input type="checkbox" value="toRun" class="toRun">
                        
                    <%} // end if he.status%>
                </td>
                <td class="fzCell"><%=he.hvsDate%></td>
                <td class="fzCell"><%=he.status%></td>
                <td class="fzCell"><%=he.divID%></td>
                <td class="fzCell"><%=he.kg%></td>
                <td class="fzCell"><%=he.millID%></td>
                <td class="fzCell"><%=he.grabbercondition%></td>
                <td class="fzCell"><a href="../hvsEstm/hvsEstmFrm.jsp?hvsEstmID=<%=he.hvsEstmID%>">View</a></td>
            </tr>
            
        <%} // end for HvsEstm %>
        
        </table>
        
        <br>
        <h3>Vehicle to consider</h3>
        <table class="table" id="tbVehicle">
            <tr>
                <th width="100px" class="fzCol">To Run</th>
                <th width="100px" class="fzCol">Vehicle ID</th>
                <th width="100px" class="fzCol">Vehicle Name</th>
                <th width="100px" class="fzCol">Default Div</th>
                <th width="100px" class="fzCol">Start Time</th>
                <!--th width="100px" class="fzCol">Status</th-->
                <th width="100px" class="fzCol">Include In Run</th>
                <th width="100px" class="fzCol">Remark / Driver</th>
                <th width="100px" class="fzCol">Start Lon</th>
                <th width="100px" class="fzCol">Start Lat</th>
                <th width="100px" class="fzCol">Start Loc</th>
                <th width="100px" class="fzCol"></th>
            </tr>
            
        <%for (Vehicle2Record vr : (List<Vehicle2Record>) getList("vehicleList")) { %> 
        
            <tr>
                        
                <td class="fzCell">
                    <input type="checkbox" value="toRun" class="toRun">
                </td>
                <td class="fzCell"><%=vr.vehicleID%></td>
                <td class="fzCell"><%=vr.vehicleName%></td>
                <td class="fzCell"><%=vr.defDivCode%></td>
                <td class="fzCell"><%=vr.startTime%></td>
                <!--td class="fzCell"><=vr.status></td-->
                <td class="fzCell"><%=vr.includeInRun%></td>
                <td class="fzCell"><%=vr.remark%></td>
                <td class="fzCell"><%=vr.startLon%></td>
                <td class="fzCell"><%=vr.startLat%></td>
                <td class="fzCell"><%=vr.startLocation%></td>
                <td class="fzCell"><a href="../vehicle2/vehicle2Frm.jsp?vehicleID=<%=vr.vehicleID%>">View</a></td>
            </tr>
            
        <%} // end for Vehicle2Record %>
        
        </table>
        
        <%if (showRunBtn){%>

            <button class="btn fzButton" type="button" 
                    name="runBtn" id="runBtn" value="save">Run Algo</button>

            &nbsp;&nbsp;<span class="fzTextButton" id="paramsBtn">View Params</span>
            
            <div id="debug"></div>
            
            <div id="dvParam" style="display: none">
            </div>
            <script>
                function addParam(id, tx, vl){
                    var o = '';
                    o += '<br>';
                    o += '<label for="' + id + '" class="fzLabel">' + tx + ':</label>';
                    o += '<input id="' + id + '" name="' + id + '" value="' + vl + '" class="fzInput">';
                    document.getElementById("dvParam").innerHTML += o;
                }
                addParam("binCapacity", "Bin Capacity", "9000");
                addParam("durToFillBin", "Dur To Fill Bin", "90");
                addParam("durToLoadBinToVehicle", "Dur To Load Bin To Vehicle", "12");
                addParam("durToUnloadInBlock", "Dur To Unload In Block", "15");
                addParam("durToWeight", "Dur To Weight", "2");
                addParam("durToUnloadInMill", "Dur To Unload In Mill", "3");
                addParam("durWaitingBridge", "Dur Waiting Bridge", "2");
                addParam("millEndTimeStr", "Mill End Time", "21:00");
                addParam("speedKmPHr", "Speed Km/Hour", "20");
                addParam("startFruitReadyForGrabberStr", "Start Fruit Ready for Grabber", "07:40");
                addParam("multiDivPerVehicle", "Multi Div Per Vehicle", "Yes");
                addParam("orderByAlgo", "Order By Algo", "No");
                addParam("maxIteration", "Max Iteration", "50");
            </script>

            <form id="form1" action="../run/runProcess.jsp">
                <input id="json" name="json" type="hidden">
            </form>
                    
        <%} // end if showRunBtn %>
        
    <script src="../run/runList.js?10"></script>
    
    <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

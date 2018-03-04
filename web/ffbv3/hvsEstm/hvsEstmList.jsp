<%-- 
    Document   : hvsEstmList
    Created on : Sep 23, 2017, 6:14:45 AM
--%>
<%@page import="com.fz.ffbv3.service.hvsEstm.HvsEstm"%>
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

        <h3>Estimation List</h3>
        <table class="table" id="tbData">
            <tr>
                <th width="100px" class="fzCol">Harvest Date</th>
                <th width="100px" class="fzCol">Status</th>
                <th width="100px" class="fzCol">Div</th>
                <th width="100px" class="fzCol">Kg</th>
                <!--th width="100px" class="fzCol">To Run</th-->
                <th width="100px" class="fzCol">Mill</th>
                <th width="100px" class="fzCol">Grabber Condition</th>
                <th width="100px" class="fzCol"><!--view--></th>
            </tr>
            
        <%boolean showRunBtn = false;%>
        
        <%for (HvsEstm he : (List<HvsEstm>) getList("hvsEstmList")) { %> 
        
            <tr>
                <td class="fzCell"><%=he.hvsDate%></td>
                <td class="fzCell"><%=he.status%></td>
                <td class="fzCell"><%=he.divID%></td>
                <td class="fzCell"><%=he.kg%></td>
                <td class="fzCell"><%=he.millID%></td>
                <td class="fzCell"><%=he.grabbercondition%></td>
                <!--td class="fzCell">
                    
                    <%if (he.status.equals("FNAL")){%>
                    
                        <%showRunBtn = true;%>
                        
                        <input type="checkbox" value="toRun" class="toRun">
                        
                    <%} // end if he.status%>
                </td-->
                <td class="fzCell"><a href="hvsEstmFrm.jsp?hvsEstmID=<%=he.hvsEstmID%>">View</a></td>
            </tr>
            
        <%} // end for HvsEstm %>
        
        </table>
        
        <%if (showRunBtn){%>

            <!--button class="btn fzButton" type="button" 
                    name="runBtn" id="runBtn" value="save">Run Algo</button-->

            &nbsp;&nbsp;<!--span class="fzTextButton" id="paramsBtn">View Params</span-->
            
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
                addParam("durToLoadBinToVehicle", "Dur To Load Bin To Vehicle", "2");
                addParam("durToWeight", "Dur To Weight", "2");
                addParam("durToUnloadInMill", "Dur To Unload In Mill", "3");
                addParam("durWaitingBridge", "Dur Waiting Bridge", "2");
                addParam("millEndTimeStr", "Mill End Time", "21:00");
                addParam("speedKmPHr", "Speed Km/Hour", "20");
                addParam("startFruitReadyForGrabberStr", "Start Fruit Ready for Grabber", "07:30");
                addParam("multiDivPerVehicle", "Multi Div Per Vehicle", "Yes");
                addParam("maxIteration", "Max Iteration", "50");
            </script>

            <form id="form1" action="../run/runProcess.jsp">
                <input id="json" name="json" type="hidden">
            </form>
                    
        <%} // end if showRunBtn %>
        
    <script src="hvsEstmList.js?20"></script>
    
    <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

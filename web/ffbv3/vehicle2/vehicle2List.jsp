<%@include file="../appGlobal/pageTop.jsp"%>
<%run(new com.fz.ffbv3.service.vehicle2.Vehicle2ListLogic());%>
<%@page import="com.fz.ffbv3.service.vehicle2.Vehicle2Record"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vehicle</title>
    </head>
    <body>
    <%@include file="../appGlobal/bodyTop.jsp"%>
        <h3>Vehicle</h3>
        
        <br><br>
        <%            
        String runIDOptions = "<option value=''>Remove from any</option>";
        String vehicleOptions = "";

        for (Vehicle2Record r : (List<Vehicle2Record>) getList("VehicleList")) { 
            
            if (!runIDOptions.contains("'" + r.lastRunID + "'")){

                runIDOptions += 
                        "<option value='" + r.lastRunID + "'>" 
                        + "Add to " + r.lastRunID 
                        + "</option>";
            }
            if (!vehicleOptions.contains("'" + r.vehicleID + "'")){

                vehicleOptions += 
                        "<option value='" + r.vehicleID + "'>" 
                        + r.vehicleName + " - Vehicle ID " + r.vehicleID
                        + "</option>";
            }
        } // for Vehicle2Record runIDs 
        %>
				
				<!-- Awal, Dropdown untuk pilihan divisi dari vehicle, by ignat 23012018-->

					<label class="fzLabel">Division</label>
          <select name='selectedBy' class='fzInput'>
						<option value='LWSM' selected>LWSM</option>
            <option value='TESM'>TESM</option>
            <option value='BPRM'>BPRM</option>
          </select>
          <br>
				<!-- Akhir, Dropdown untuk pilihan divisi dari vehicle, by ignat 23012018-->

        <div id='dvChgScheBtn' class='fzTextButton'>Add / remove vehicle in scheduling ></div>
        <div id='dvChgSchePane' style='display:none'>
            <form action='vehicle2AddRmvRunID.jsp' method='post'>

                <!--b>Include/exclude in scheduling:</b-->
                <br><br>
                <label class="fzLabel">Vehicle</label>
                <select name='vehicleID' class='fzInput'>
                    <%=vehicleOptions%>
                </select>

                <br><br>
                <label class="fzLabel">Scheduling RunID</label>
                <select name='newRunID' class='fzInput'>
                    <%=runIDOptions%>
                </select>

                <!--br>
                <label class="fzLabel"></label>
                <span class="fzLabelBottom">
                    <br>- To INCLUDE in NEW schedule: copy other vehicle Schedule RunID
                    <br>
                    <br>- To EXCLUDE from EXISTING schedule: empty it
                </span-->

                <br><br>
                <button type='submit' name='submitBtn' value="update" 
                        class='btn fzButton'>Go</button>
                <br><br>
            </form>
        </div>
        <script>
            $('#dvChgScheBtn').click(function() {
                $('#dvChgSchePane').toggle();
            });
        </script>
                
        <br><br>
        <table class="table" border1="1" style="border-color: lightgray;">
            <tr style="background-color:orange">
                <th width="100px" class="fzCol">Vehicle ID</th>
                <th width="100px" class="fzCol">Vehicle Name</th>
                <th width="100px" class="fzCol">Default Div</th>
                <th width="100px" class="fzCol">Start Time</th>
                <th width="100px" class="fzCol">Include In Run</th>
                <th width="100px" class="fzCol">Last Job Status</th>
                <th width="100px" class="fzCol">Last Job ID</th>
                <th width="100px" class="fzCol">Remark / Driver</th>
                <th width="100px" class="fzCol">Start Lon</th>
                <th width="100px" class="fzCol">Start Lat</th>
                <th width="100px" class="fzCol">Start Loc</th>
                <th width="100px" class="fzCol">Scheduling RunID</th>
                <th width="100px" class="fzCol"></th>
            </tr>
            
        <%for (Vehicle2Record r : (List<Vehicle2Record>) getList("VehicleList")) {%>
<tr>
                <td class="fzCell"><%=r.vehicleID%></td>
                <td class="fzCell"><%=r.vehicleName%></td>
                <td class="fzCell"><%=r.defDivCode%></td>
                <td class="fzCell"><%=r.startTime%></td>
                <td class="fzCell"><%=r.includeInRun%></td>
                <td class="fzCell"><%=r.lastJobStatus%></td>
                <td class="fzCell"><%=r.lastJobID%></td>
                <td class="fzCell"><%=r.remark%></td>
                <td class="fzCell"><%=r.startLon%></td>
                <td class="fzCell"><%=r.startLat%></td>
                <td class="fzCell"><%=r.startLocation%></td>
                <td class="fzCell"><%=r.lastRunID%></td>
                <td class="fzCell"><a href="../vehicle2/vehicle2Frm.jsp?vehicleID=<%=r.vehicleID%>">View</a></td>
            </tr>
            
        <%} // for Vehicle2Record table %>
        
        </table>
        
    <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

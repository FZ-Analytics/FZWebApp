<%@page import="java.text.DecimalFormat"%>
<%@page import="com.fz.util.FZUtil"%>
<%@page import="com.fz.ffbv3.service.order2.Order2Record"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order</title>
    </head>
    <body>
    <%@include file="../appGlobal/bodyTop.jsp"%>
    <script>
        function toggle_more(jobID) { 
            var x = document.getElementById("more"+jobID);
            x.hidden = !x.hidden;
            document.getElementById("tgl"+jobID).innerHTML=(x.hidden)?"More":"Less";
        }
        
        function batal(jobID,divID) { 
            var x = confirm("Cancel Job " + jobID + " ? ");
            if (x == true) 
                document.location.href="order2Cancel.jsp?jobID=" + jobID + "&divID=" +divID;
        }

        function reOrder(jobID,divID) { 
            var x = confirm("Reorder Job " + jobID + " ? ");
            if (x == true) 
                document.location.href="order2Reorder.jsp?jobID=" + jobID + "&divID=" +divID;
        }
    </script>
        <h3>Job / Order</h3>
        
        <br><br>
        <a href='../order2/order2Filter.jsp'>Change filter / sort</a>
        
        <br><br>
        <table class="table" border1="1" style="border-color: lightgray;">
            <tr style="background-color:orange">
                <th class="fzCol">Vehicle</th>
                <th class="fzCol">Status</th>
                <th class="fzCol">Div</th>
                <th class="fzCol">Block</th>
                <th class="fzCol">Bin Avail</th>
                <th class="fzCol">Job ID</th>
                <th class="fzCol"></th>
            </tr>
           
        <%
            
        // TODO: make MVC
        
        // for each record
        String prevVehicleName = "";
        String prevDivID = "";
        String sortBy = (get("SortBy").equals("div") ? "div" : "vehicle");
        for (Order2Record r : (List<Order2Record>) getList("OrderList")) {

            // determine reorder code & color
            String reorderRef = 
                    "<button class='btn fzButton' type='button' onclick=\"reOrder(" + r.jobID 
                    + ",'" + r.divID + "')\">Re-Order</button>";
            String cancelRef = 
                    "<button class='btn fzButton' type='button' onclick=\"batal(" + r.jobID 
                    + ",'" + r.divID + "')\">Cancel</button>";
            String reorderCode = ""; 
            String cancelCode = ""; 
            String statusColor = "";
            String statusText = r.jobStatus;
            
            if (r.jobStatus.equals("PLAN")){
                statusColor = "grey";
                reorderCode = "";
                cancelCode = "";
            }
            else if (r.jobStatus.equals("ASGN")){
                statusColor = "blue";
                reorderCode = reorderRef;
                cancelCode = cancelRef;
            }
            else if (r.jobStatus.equals("NEW")){
                statusColor = "black";
                reorderCode = "";
                cancelCode = "";
            }
            else if (r.jobStatus.equals("TKEN")){
                statusColor = "purple";
                reorderCode = reorderRef;
                cancelCode = cancelRef;
            }
            else if (r.jobStatus.equals("DONE")){
                
                if (r.Task2ReasonState.equals("0") || (r.actualEnd.compareTo("10:00")<=0 && r.jobSeq.equals("1"))){
                    statusColor = "green";
                    reorderCode = "";
                    cancelCode = "";
                    statusText = "DONE";
                }
                else if (r.Task2ReasonState.equals("1")){
                    statusColor = "red";
                    reorderCode = reorderRef;
                    cancelCode = cancelRef;
                    statusText = "FAIL";
                }
                else if (r.Task2ReasonState.equals("2")){
                    
                    if (r.Task2ReasonID.equals("11")){
                        statusColor = "magenta";
                        reorderCode = "";
                        cancelCode = "";
                        statusText = "EMPB";
                    }
                    else {
                        statusColor = "maroon";
                        reorderCode = "";
                        cancelCode = "";
                        statusText = "LATE";
                    }
                }
                else {
                    statusColor = "brown";
                    reorderCode = reorderRef;
                    cancelCode = cancelRef;
                    statusText = "HANG";
                }
            }
            else {
                statusColor = "darkred";
                reorderCode = reorderRef;
                cancelCode = cancelRef;
                statusText = "UNKN";
            }

            // if new truck, add empty row
            if (
                (sortBy.equals("vehicle") && !prevVehicleName.equals(r.vehicleName)) 
                ||
                (sortBy.equals("div") && !prevDivID.equals(r.divID)) 
                )
            {%>
            
                <tr style="background-color : lightgrey;">
                    <td class="fzCell"></td>
                    <td class="fzCell"></td>
                    <td class="fzCell"></td>
                    <td class="fzCell"></td>
                    <td class="fzCell"></td>
                    <td class="fzCell"></td>
                    <td class="fzCell"></td>
                </tr>
                
            <%  
                prevVehicleName = r.vehicleName;  
                prevDivID = r.divID;  
                
            }// if sortby equal
            %>
            
            <tr>
                <td class="fzCell"><%=r.vehicleName%></td>
                <td class="fzCell"
                    style="background-color:<%=statusColor%>; color:white; text-align:center"
                    ><%=statusText%></td>
                <td class="fzCell"><%=r.divID%></td>
                <td class="fzCell"><%=r.getBlocks()%></td>
                <td class="fzCell"><%=r.readyTime%></td>
                <td class="fzCell"><%=r.jobID%></td>
                <td class="fzCell">
                    <a href="javascript:toggle_more('<%=r.jobID%>');" id="tgl<%=r.jobID%>">More</a>
                </td>
            </tr>
            <div>
            <tr id="more<%=r.jobID%>" name="more<%=r.jobID%>" hidden>
                    <form id="frm_batal" name="frm_batal" action="order2Cancel.jsp" method="get">
                        <input type="hidden" id="jobID" name="jobID" value="<%=r.jobID%>">
                        <input type="hidden" id="divID" name="divID" value="<%=r.divID%>">
                    </form>
                    <form id="frm_reorder" name="frm_reorder" action="order2Reorder.jsp" method="get">
                        <input type="hidden" id="jobID" name="jobID" value="<%=r.jobID%>">
                        <input type="hidden" id="divID" name="divID" value="<%=r.divID%>">
                    </form>
                <td colspan="100%" >
                    <%
                        DecimalFormat df = new DecimalFormat("#,###,###");
                    %>
                    <table frame="box" style="border: lightgray inset medium;">
                        <tr>
                            <td>&nbsp;</td>
                            <td colspan='3'><font style='color: brown;'><b>Order Detail</b></font></td>
                            <td>&nbsp;</td>
                            <td colspan='3'></td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td style="padding: 2px;">Create Source</td><td style="padding: 2px;">:</td><td><%=r.createSource%></td>
                            <td>&nbsp;</td>
                            <td style="padding: 2px;">RunID</td><td style="padding: 2px;">:</td><td><%=r.runID%></td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>Ordered</td><td style="padding: 2px;">:</td><td><%=r.createDate%></td>
                            <td>&nbsp;</td>
                            <td>Asigned</td><td style="padding: 2px;">:</td><td><%=r.assignedDate%></td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>Taken</td><td style="padding: 2px;">:</td><td><%=r.takenDate%></td>
                            <td>&nbsp;</td>
                            <td>Done</td><td style="padding: 2px;">:</td><td><%=r.doneDate%></td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td style="padding: 2px;">DirLoc</td><td style="padding: 2px;">:</td><td><%=r.dirLoc%></td>
                            <td>&nbsp;</td>
                            <td style="padding: 2px;">BinLoc</td><td style="padding: 2px;">:</td><td><%=r.remark%></td>
                        </tr>
                        <tr><td colspan='7>'>&nbsp;</td></tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td colspan='3'><font style='color: brown;'><b>Order Status</b></font></td>
                            <td>&nbsp;</td>
                            <td colspan='3'></td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td style="padding: 2px;">Driver </td><td>:</td><td colspan="4"><%=r.vehicleRemark%></td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td style="padding: 2px;">Plan Start</td><td style="padding: 2px;">:</td><td><%=r.planStart%></td>
                            <td>&nbsp;</td>
                            <td>Actual Start</td><td style="padding: 2px;">:</td><td style="padding: 2px; width: 50px;"><%=r.actualStart%></td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td style="padding: 2px;">Plan End</td><td style="padding: 2px;">:</td><td><%=r.planEnd%></td>
                            <td>&nbsp;</td>
                            <td>Actual End</td><td style="padding: 2px;">:</td><td style="padding: 2px;"><%=r.actualEnd%></td>
                        </tr>
			<tr>
                            <td>&nbsp;</td>
                            <td style="padding: 2px;">Estm Kg</td><td style="padding: 2px;">:</td><td><%=df.format(r.size)%></td>
                            <td>&nbsp;</td>
                            <td>Actual Kg</td><td style="padding: 2px;">:</td><td style="padding: 2px;"><%=df.format(r.ActualKg)%></td>
                        </tr>
			<tr>
                            <td>&nbsp;</td>
                            <td>Is Last 2 Order</td><td style="padding: 2px;">:</td><td width="50px"><%=r.isLast2Order%></td>
                            <td>&nbsp;</td>
                            <td style="padding: 2px;">Reason</td><td style="padding: 2px;">:</td><td><%=r.Task2ReasonName%></td>
                        </tr>
                        <tr><td colspan='7>'>&nbsp;</td></tr>
			<tr>
                            <td>&nbsp;</td>
                            <td colspan="3" style='text-align: left;'><%=reorderCode%></td>
							<td></td>
                            <td colspan="3" style='text-align: right; padding-right: 10px;'><%=cancelCode%></td>
                        </tr>
                        <tr>
                            <td colspan="100%" style="text-align: center;">
                                <a href="javascript:toggle_more('<%=r.jobID%>');" id="tgl<%=r.jobID%>">
                                <img src="../img/hline.png"/><br/>Close
                                </a>
                        </tr>
                    </table>
                </td>
                </td>
            </tr>
            </div>
        <%} // for Order2Record %>
        
        </table>
        
    <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

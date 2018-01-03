<%@page import="com.fz.util.FZUtil"%>
<%@page import="com.fz.ffbv3.service.order2.Order2Record"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%@include file="../order2/order2ListProcess.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order</title>
    </head>
    <body>
    <%@include file="../appGlobal/bodyTop.jsp"%>
    <script src='../order2/order2List.js'></script>
        <h3>Job / Order</h3>
        
        <br><br>
        <!--a href='../order2/order2Filter.jsp'>Change filter / sort</a-->
        <form class="container" action="order2List.jsp" method="post">

            <div class="fzErrMsg">
                <%=get("errMsg")%>
            </div>
            
            <br>
            <label class="fzLabel">Harvest Date</label>
            <input class="fzInput" id="hvsDt" name="hvsDt" 
                   value=""/>
            
            <br><br>
            <label class="fzLabel">Estate + Division</label>
            <input class="fzInput" type="text" id="divID" name="divID" 
                   value="BINE1">
            
            <br>
            <label class="fzLabel"></label>
            <span class="fzLabelBottom">e.g. "BINE" or "BINE1" or blank for all</span>
            
            <br><br>
            <label class="fzLabel">Sort by</label>
            <select name='sortBy' class='fzInput'>
                <option value='vehicle' selected>Vehicle</option>
                <option value='div'>Division</option>
            </select>
            
            <br><br>
            <button class="btn fzButton" type="submit" 
                    name="submit" value="search">Search</button>

        </form>         
        
        <br><br>
        <table class="table" border1="1" style="border-color: lightgray;">
            <tr style="background-color:orange">
                <!--th width="100px" class="fzCol">Harvest Date</th-->
                <th width="100px" class="fzCol">Vehicle</th>
                <th width="100px" class="fzCol">Trip</th>
                <th width="100px" class="fzCol">Job Status</th>
                <th width="100px" class="fzCol">Div</th>
                <th width="100px" class="fzCol">Blocks</th>
                <th width="100px" class="fzCol">Bin Ready Estm</th>
                <th width="100px" class="fzCol">Size</th>
                <th width="100px" class="fzCol">Plan Start</th>
                <th width="100px" class="fzCol">Plan End</th>
                <th width="100px" class="fzCol">Actual Start</th>
                <th width="100px" class="fzCol">Actual End</th>
                <th width="100px" class="fzCol">Job ID</th>
                <th width="100px" class="fzCol"><!--more--></th>
                <th width="100px" class="fzCol"><!--reord--></th>
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
                    "<a href='../order2/order2Reorder.jsp?jobID=" 
                    + r.jobID + "&divID=" + r.divID
                    + "'>Reorder</a>";
            String reorderCode = ""; 
            String statusColor = "";
            String statusText = r.jobStatus;
            
            if (r.jobStatus.equals("PLAN")){
                statusColor = "grey";
                reorderCode = "";
            }
            else if (r.jobStatus.equals("ASGN")){
                statusColor = "blue";
                reorderCode = "";
            }
            else if (r.jobStatus.equals("NEW")){
                statusColor = "orange";
                reorderCode = "";
            }
            else if (r.jobStatus.equals("TKEN")){
                statusColor = "purple";
                reorderCode = reorderRef;
            }
            else if (r.jobStatus.equals("DONE")){
                
                if (r.Task2ReasonState.equals("0")){
                    statusColor = "green";
                    reorderCode = "";
                    statusText = "DONE";
                }
                else if (r.Task2ReasonState.equals("1")){
                    statusColor = "red";
                    reorderCode = reorderRef;
                    statusText = "FAIL";
                }
                else if (r.Task2ReasonState.equals("2")){
                    statusColor = "maroon";
                    reorderCode = "";
                    statusText = "LATE";
                }
                else {
                    statusColor = "brown";
                    reorderCode = reorderRef;
                    statusText = "HANG";
                }
            }
            else {
                statusColor = "black";
                reorderCode = reorderRef;
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
                    <!--td class="fzCell"></td-->
                    <td class="fzCell"></td>
                    <td class="fzCell"></td>
                    <td class="fzCell"></td>
                    <td class="fzCell"></td>
                    <td class="fzCell"></td>
                    <td class="fzCell"></td>
                    <td class="fzCell"></td>
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
                <!--td class="fzCell"><=r.hvsDate></td-->
                <td class="fzCell"><%=r.vehicleName%></td>
                <td class="fzCell"><%=r.jobSeq%></td>
                <td class="fzCell"
                    style="background-color:<%=statusColor%>; color:white; text-align:center"
                    ><%=statusText%></td>
                <td class="fzCell"><%=r.divID%></td>
                <td class="fzCell"><%=r.getBlocks()%></td>
                <td class="fzCell"><%=r.readyTime%></td>
                <td class="fzCell"><%=r.size%></td>
                <td class="fzCell"><%=r.planStart%></td>
                <td class="fzCell"><%=r.planEnd%></td>
                <td class="fzCell"><%=r.actualStart%></td>
                <td class="fzCell"><%=r.actualEnd%></td>
                <td class="fzCell"><%=r.jobID%></td>
                <td class="fzCell">
                    <a href="javascript:shw('<%=r.jobID%>','<%=r.runID%>','<%=r.hvsDate%>','<%=FZUtil.escapeText(r.remark)%>','<%=r.vehicleRemark%>','<%=r.createSource%>');">More..</a></td>
                <td class="fzCell"><%=reorderCode%></td>
            </tr>
            
        <%} // for Order2Record %>
        
        </table>
        
    <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

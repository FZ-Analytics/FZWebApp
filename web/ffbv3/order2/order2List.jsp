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
        function shw(jobID
            , runID
            , hvsDt
            , rmk
            , vhRmk
            , crtSrc
            , task2ReasonName
            , size
            , planStart
            , planEnd
            , actualStart
            , actualEnd
            , blocks
            , isLastOrder
            , isLast2Order
            , assignedDt
            , takenDt
            , doneDt
            , createDt
            , dirLoc
            , estmFfb
            ){
        
            var s = 
                    '\nJobID = ' + jobID
                    + '\nPlan Start = ' + planStart
                    + '\nPlan End = ' + planEnd
                    + '\nActual Start = ' + actualStart
                    + '\nActual End = ' + actualEnd
                    + '\nEstm Kg = ' + size
                    + '\nEstm FFB = ' + estmFfb
                    + '\nIs Last 2 Order = ' + isLast2Order
                    + '\nDirLoc = ' + dirLoc
                    + '\nReason = ' + task2ReasonName
                    + '\n'
                    + '\nRunID = ' + runID
                    //+ '\nBlocks = ' + blocks
                    + '\nCreate Source = ' + crtSrc
                    + '\nHarvest Date = ' + hvsDt
                    + '\nJob Remark = ' + rmk
                    + '\nVehicle Remark = ' + vhRmk
                    + '\n'
                    + '\nOrdered = ' + createDt
                    + '\nAssigned = ' + assignedDt
                    + '\nTaken = ' + takenDt
                    + '\nDone = ' + doneDt
            ;
            alert(s);
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
                <th class="fzCol"></th>
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
                    "<a href='../order2/order2Reorder.jsp?jobID=" 
                    + r.jobID + "&divID=" + r.divID
                    + "'>Re-Order</a>";
            String cancelRef = 
                    "<a href='../order2/order2Cancel.jsp?jobID=" 
                    + r.jobID + "&divID=" + r.divID
                    + "'>Cancel</a>";
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
                reorderCode = "";
                cancelCode = "";
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
                
                if (r.Task2ReasonState.equals("0")){
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
                    <a href="javascript:shw('<%=r.jobID%>','<%=r.runID%>','<%=r.hvsDate%>','<%=FZUtil.escapeText(r.remark)%>','<%=r.vehicleRemark%>','<%=r.createSource%>','<%=r.Task2ReasonName%>','<%=r.size%>','<%=r.planStart%>','<%=r.planEnd%>','<%=r.actualStart%>','<%=r.actualEnd%>','<%=r.getBlocks()%>','<%=r.isLastOrder%>','<%=r.isLast2Order%>','<%=r.assignedDate%>','<%=r.takenDate%>','<%=r.doneDate%>','<%=r.createDate%>','<%=r.dirLoc%>','<%=r.estmFfb%>');"
                       >More</a></td>
                <td class="fzCell" align="center"><%=reorderCode%></td><td class="fzCell" align="center"><%=cancelCode%></td>
            </tr>
            
        <%} // for Order2Record %>
        
        </table>
        
    <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

<%-- 
    Document   : DetilPeriodSummary
    Created on : Nov 28, 2017, 7:41:19 PM
    Author     : Administrator
--%>
<%@page import="java.math.MathContext"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="org.json.JSONArray"%>
<%@page import="com.fz.util.FZUtil"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.fz.ffbv3.service.dashAngkut.DetilSummary"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Period Summary</title>
    </head>
    <body>
        <style>
            tr { border-bottom: 2px solid lightgray;
            }
        </style>
        <%@include file="../appGlobal/bodyTop.jsp"%>        
        <br><br>
        <%  
            String fromDate = FZUtil.getHttpParam(request, "fromSummary");
            String toDate = FZUtil.getHttpParam(request, "toSummary");
            DecimalFormat df = new DecimalFormat("#,##0");
        %>
        <label class="fzLabel">From Date:</label>
        <label class="fzLabel"><%=fromDate%></label>
        <label class="fzLabel">To Date:</label>
        <label class="fzLabel"><%=toDate%></label>

        <br>
        <label class="fzLabel">BS Summary</label>

        <br><br>
        <table id="table" border1="1" style="border-color: lightgray; font-weight: bold;">
            <thead>
                <tr style="background-color: orange">
                    <th width="100px" class="fzCol">Vehicle <br/> Name</th>
                    <th width="100px" class="fzCol">#Days in Operation</th>
                    <th width="100px" class="fzCol">Total Trips</th>
                    <th width="100px" class="fzCol">Avg. <br/> Trips/Day</th>
                    <!--th width="100px" class="fzCol">Total Kg Actual (from TPB)</th>
                    <th width="100px" class="fzCol">Avg. Kg/Trip</th-->
                </tr>
            </thead>
            <tbody>
                <%             
                    int tTripA = 0;
                    Double tAct = new Double("0");
                    if (!fromDate.isEmpty() && !toDate.isEmpty()) {
                        JSONArray o = DetilSummary.rdbTrip3(fromDate, toDate);
                        JSONObject x;                        
                        int tOp = 0;
                        System.out.println("aaaaaa"+o.length());
                        
                        HashMap<String, String> pl = new HashMap<String, String>();
                        
                        if (o != null && o.length() > 0) {
                            for (int i = 0; i < o.length(); i++) {
                                x = o.getJSONObject(i);
                                tTripA = tTripA + x.getInt("TripCount");
                                tOp = tOp + x.getInt("DaysCount"); 
                                tAct = tAct + x.getDouble("ActualKgs");
                %>
                <tr>                    
                    <td class="fzCell"><%=x.getString("VehicleName")%></td>
                    <td class="fzCell" align="center"><%=x.getInt("DaysCount")%></td>
                    <td class="fzCell" align="center"><%=x.getInt("TripCount")%></td>
                    <td class="fzCell" align="center"><%=x.getDouble("avgTrip")%></td>
                    <!--td class="fzCell" align="right"><%=df.format(x.getDouble("ActualKgs"))%></td>
                    <td class="fzCell" align="right"><%=df.format(x.getDouble("avgKgs"))%></td-->
                </tr>
                <%
                    }
                %>
            </tbody>
            <!--tfoot><tr style="background-color: #f2f2f2">                    
                    <td class="fzCell" align="center">Total</td>
                    <td class="fzCell"></td>
                    <td class="fzCell" align="center"><%=tTripA%></td>
                    <td class="fzCell" align="center"><%=df.format(new BigDecimal(tTripA).divide(new BigDecimal(tOp), MathContext.DECIMAL128))%></td>
                    <!td class="fzCell" align="right"><%=df.format(tAct)%></td>
                    <td class="fzCell" align="right"><%=df.format(new BigDecimal(tAct).divide(new BigDecimal(tTripA), MathContext.DECIMAL128))%></td>
                </tr>
            </tfoot-->
                <%
                        }
                    }

                %>
        </table>

        <br>
        <label >Division Trips Summary</label>
        <table id="itable" border1="1" style="border-color: lightgray; font-weight: bold;">
            <thead>
                <tr style="background-color: orange">
                    <th width="100px" class="fzCol">Division</th>
                    <th width="100px" class="fzCol">#Days in Operation</th>
                    <th width="100px" class="fzCol">#Days <br/>Trip #1 < 10:00</th>
                    <th width="100px" class="fzCol">#Days <br/>Trip #3 < 13:00</th>
                    <th width="100px" class="fzCol">#Days <br/>Trip #6 < 18:00</th>
                </tr>
            </thead>
            <tbody>
                <%  
                    JSONArray o = null;
                    JSONObject x;
                    if (!fromDate.isEmpty() && !toDate.isEmpty()) {
                        o = DetilSummary.rdbTrip4(fromDate, toDate);
                        int a = 0;
                        int tDays = 0;
                        Double ndA = new Double("0");
                        Double ndB = new Double("0");
                        Double ndC = new Double("0");
                        if (o != null && o.length() > 0) {
                            for (int i = 0; i < o.length(); i++) {
                                x = o.getJSONObject(i);
                                a = a + 1;
                                tDays = tDays + x.getInt("DaysCount");
                                ndA = ndA + x.getInt("nTrip1");
                                ndB = ndB + x.getInt("nTrip2");
                                ndC = ndC + x.getInt("nTrip3");
                %>
                <tr>                    
                    <td class="fzCell"><%=x.getString("divID")%></td>
                    <td class="fzCell" align="center"><%=x.getInt("DaysCount")%></td>
                    <td class="fzCell" align="center"><%=x.getInt("nTrip1")%></td>
                    <td class="fzCell" align="center"><%=x.getInt("nTrip2")%></td>
                    <td class="fzCell" align="center"><%=x.getInt("nTrip3")%></td>
                </tr>
                <%
                    }
                %>
            </tbody>
            <!--tfoot>
                <tr style="background-color: #f2f2f2">                    
                    <td class="fzCell" align="center">Total</td>
                    <td class="fzCell" align="center"><%=df.format(tDays)%></td>
                    <td class="fzCell" align="center"><%=df.format(new BigDecimal(ndA).divide(new BigDecimal(a), MathContext.DECIMAL128))%></td>
                    <td class="fzCell" align="center"><%=df.format(new BigDecimal(ndB).divide(new BigDecimal(a), MathContext.DECIMAL128))%></td>
                    <td class="fzCell" align="center"><%=df.format(new BigDecimal(ndC).divide(new BigDecimal(a), MathContext.DECIMAL128))%></td>
                </tr>
            </tfoot-->
                <%
                        }
                    }

                %>
        </table>
        <br><br>

<!-- production -->        
        <br>
        <label >Division Productions Summary</label>
        <table id="itable" border1="1" style="border-color: lightgray; font-weight: bold;">
            <thead>
                <tr style="background-color: orange">
                    <th width="100px" class="fzCol">Division</th>
                    <th width="100px" class="fzCol">#Days in Operation </th>
                    <th width="100px" class="fzCol">Total Trips</th>
					<th widht="100px" class="fzCol">Total Kgs <br/>(from TPB)</th>
                    <th width="100px" class="fzCol">Kgs/Day </th>
                    <th width="100px" class="fzCol">Avg. Kg/Trip Served</th>
                </tr>
            </thead>
            <tbody>
                <%  
                        int a = 0;
                        int tTrip = 0;
                        int tDays = 0;
                        Double tActKg = new Double("0");
                        Double tTaxKg = new Double("0");
                        Double tRes = new Double("0");
/*
                        Double ndA = new Double("0");
                        Double ndB = new Double("0");
                        Double ndC = new Double("0");
*/                        
                        if (o != null && o.length() > 0) {
                            for (int i = 0; i < o.length(); i++) {
                                x = o.getJSONObject(i);
                                a = a + 1;
                                tTrip = tTrip + x.getInt("TripsCount");
                                tDays = tDays + x.getInt("DaysCount");
                                tActKg = tActKg + x.getDouble("ActKgs");
                                tTaxKg = tTaxKg + x.getDouble("TaxKgs");
                                tRes = tRes + x.getDouble("KgsRes");
/*
                                ndA = ndA + x.getInt("nTrip1");
                                ndB = ndB + x.getInt("nTrip2");
                                ndC = ndC + x.getInt("nTrip3");
*/
                %>
                <tr>                    
                    <td class="fzCell"><%=x.getString("divID")%></td>
                    <td class="fzCell" align="center"><%=df.format(x.getInt("DaysCount"))%></td>
                    <td class="fzCell" align="center"><%=df.format(x.getInt("TripsCount"))%></td>                    
					<td class="fzCell" align="right"><%=df.format(x.getDouble("ActKgs"))%></td>
                    <td class="fzCell" align="right"><%=df.format((x.getInt("DaysCount")==0)?0:x.getDouble("ActKgs")/x.getInt("DaysCount"))%></td>
                    <td class="fzCell" align="right"><%=df.format(x.getDouble("kgPerTrip"))%></td>
                </tr>
                <%
                    }
                %>
            </tbody>
            <!--tfoot>
                <tr style="background-color: #f2f2f2">                    
                    <td class="fzCell" align="center">Total</td>
                    <td class="fzCell" align="center"><%=df.format(tDays)%></td>
                    <td class="fzCell" align="center"><%=df.format(tTrip)%></td>
                    <td class="fzCell" align="right"><%=df.format(new BigDecimal(tActKg).divide(new BigDecimal(tDays), MathContext.DECIMAL128))%></td>
                    <td class="fzCell" align="right"><%=df.format(new BigDecimal(tActKg).divide(new BigDecimal(tTrip), MathContext.DECIMAL128))%></td>
                </tr>
            </tfoot-->
                <%
                        }
                %>
        </table>
        <br><br>
        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

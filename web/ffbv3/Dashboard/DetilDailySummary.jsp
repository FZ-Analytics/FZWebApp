<%-- 
    Document   : DetilDailySummary
    Created on : Nov 28, 2017, 1:47:49 PM
    Author     : Administrator
--%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.fz.ffbv3.service.dashAngkut.DetilSummary"%>
<%@page import="org.json.JSONArray"%>
<%@page import="com.fz.util.FZUtil"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.util.HashMap"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Daily Summary</title>
    </head>
    <body>
        <style>
            tr { border-bottom: 2px solid lightgray;
            }
        </style>
        <%@include file="../appGlobal/bodyTop.jsp"%>        
        <br><br>
        <%
            String dateSummary = FZUtil.getHttpParam(request, "dateSummary");
            DecimalFormat df = new DecimalFormat("#,##0");
        %>
        <label class="fzLabel">Date:</label>
        <label class="fzLabel"><%=dateSummary%></label>

        <br>
        <label class="fzLabel">BS Summary</label>

        <br><br>
        <table id="table" border1="1" style="border-color: lightgray; font-weight: bold;">
            <thead>
                <tr style="background-color: orange">
                    <th width="100px" class="fzCol" align="center">Vehicle Name</th>
                    <th width="100px" class="fzCol">Number of Trip</th>
                    <th width="100px" class="fzCol">Total Kg Actual (from TPB)</th>
                    <th width="100px" class="fzCol">Avg. Kg/Trip</th>
                </tr>
            </thead>
            <tbody>
                <%
                    int tTrip = 0;
                    int tAct = 0;
                    int tAvg = 0;

                    if (!dateSummary.isEmpty()) {
                        JSONArray o = DetilSummary.rdbTrips01(dateSummary);
                        JSONObject x;
                        HashMap<String, String> pl = new HashMap<String, String>();
                        int TripsCount = 0;
                        int Kgs = 0;
                        int AvgKgs = 0;
                        if (o != null && o.length() > 0) {
                            for (int i = 0; i < o.length(); i++) {
                                x = o.getJSONObject(i);
                                TripsCount = TripsCount + x.getInt("TripsCount");
                                Kgs = Kgs + x.getInt("Kgs");
                                AvgKgs = AvgKgs + x.getInt("AvgKgs");
                                tTrip = TripsCount;
                                tAct = Kgs;
                                tAvg = Kgs / TripsCount;
                %>
                <tr>                    
                    <td class="fzCell"><%=x.getString("VehicleName")%></td>
                    <td class="fzCell" align="center"><%=df.format(x.getInt("TripsCount"))%></td>
                    <td class="fzCell" align="right"><%=df.format(x.getInt("Kgs"))%></td>
                    <td class="fzCell" align="right"><%=df.format(x.getInt("AvgKgs"))%></td>
                </tr>
                <%
                    }
                %>
            </tbody>
            <tfoot><tr style="background-color: #f2f2f2">                    
                    <td class="fzCell" align="center">Total</td>
                    <td class="fzCell" align="center"><%=df.format(TripsCount)%></td>
                    <td class="fzCell" align="right"><%=df.format(Kgs)%></td>
                    <td class="fzCell" align="right"><%=df.format((TripsCount==0)?0:(Kgs / TripsCount))%></td>
                </tr>
                <%
                        }
                    }

                %>
            </tfoot>
        </table>

        <br>
        <label >Division Trips Summary</label>

        <br>
        <table id="itable" border1="1" style="border-color: lightgray;  font-weight: bold;">
            <thead>
                <tr style="background-color: orange">
                    <th width="100px" class="fzCol">Division</th>
                    <th width="100px" class="fzCol">Total Trips</th>
                    <th width="100px" class="fzCol">Trips <10:00 (Target = 1)</th>
                    <th width="100px" class="fzCol">Trips <13:00 (Target = 3)</th>
                    <th width="100px" class="fzCol">Trips <18:00 (Target = 6)</th>
                </tr>
            </thead>
            <tbody>
                <%
                    JSONArray o = null;
                    JSONObject x;
                    int TripsCount = 0;
                    if (!dateSummary.isEmpty()) {
                        o = DetilSummary.rdbTrips02(dateSummary);
                        HashMap<String, String> pl = new HashMap<String, String>();
                        if (o != null && o.length() > 0) {
                            for (int i = 0; i < o.length(); i++) {
                                x = o.getJSONObject(i);
                                TripsCount = TripsCount + x.getInt("TripsCount");
                %>
                <tr>                    
                    <td class="fzCell"><%=x.getString("divID")%></td>
                    <td class="fzCell" align="center"><%=x.getInt("TripsCount")%></td>
                    <td class="fzCell" align="center" <%if (x.getInt("trip1") < 1) {%>
                        style="background-color: #ffb3b3"
                        <%} else if (x.getInt("trip1") == 1) {%>
                        style="background-color: #ffffb3"
                        <%} else if (x.getInt("trip1") > 1) {%>
                        style="background-color: #c2f0c2"
                        <%}%> ><%=x.getInt("trip1")%></td>
                    <td class="fzCell" align="center" <%if (x.getInt("trip2") < 3) {%>
                        style="background-color: #ffb3b3"
                        <%} else if (x.getInt("trip2") == 3) {%>
                        style="background-color: #ffffb3"
                        <%} else if (x.getInt("trip2") > 3) {%>
                        style="background-color: #c2f0c2"
                        <%}%> ><%=x.getInt("trip2")%></td>
                    <td class="fzCell" align="center" <%if (x.getInt("trip3") < 6) {%>
                        style="background-color: #ffb3b3"
                        <%} else if (x.getInt("trip3") == 6) {%>
                        style="background-color: #ffffb3"
                        <%} else if (x.getInt("trip3") > 6) {%>
                        style="background-color: #c2f0c2"
                        <%}%> ><%=x.getString("trip3")%></td>
                </tr>
                <%
                    }
                %>
            </tbody>
                <%
                        }
                    }

                %>
        </table>
            
        <br>
        <label >Division Production Summary</label>

        <br>
        <table id="itable" border1="1" style="border-color: lightgray; font-weight: bold;">
            <thead>
                <tr style="background-color: orange">
                    <th width="100px" class="fzCol">Division</th>
                    <th width="100px" class="fzCol">Total Kg Actual (from TPB)</th>
                    <th width="100px" class="fzCol">Avg. Kg/Trip Served</th>
                    <th width="100px" class="fzCol">Estimation Kg</th>
                    <th width="100px" class="fzCol">Actual vs Estimation (%)</th>
                    <th width="100px" class="fzCol">Restan Kg</th>
                </tr>
            </thead>
            <tbody>
                <%
                    if (o!=null) {
                        HashMap<String, String> pl = new HashMap<String, String>();
                        int TotalKgs = 0;
                        Double AvgKgs = new Double("0");
                        Double AvgTrip = new Double("0");
                        Double TaxKgs = new Double("0");
                        Double AktKgs = new Double("0");
                        Double ResKgs = new Double("0");
                        if (o != null && o.length() > 0) {
                            for (int i = 0; i < o.length(); i++) {
                                x = o.getJSONObject(i);
                                AvgKgs = AvgKgs + x.getDouble("ActualKgs");
                                AvgTrip = AvgTrip + x.getDouble("avgTrip");
                                TaxKgs = TaxKgs + x.getDouble("KgsTax");
                                AktKgs = AktKgs + x.getDouble("avgTax");
                                ResKgs = ResKgs + x.getDouble("kgsRestan");
                %>
                <tr>                    
                    <td class="fzCell"><%=x.getString("divID")%></td>
                    <td class="fzCell" align="right"><%=df.format(x.getDouble("ActualKgs"))%></td>
                    <td class="fzCell" align="right"><%=df.format(x.getDouble("avgTrip"))%></td>
                    <td class="fzCell" align="right"><%=df.format(x.getDouble("KgsTax"))%></td>                    
                    <td class="fzCell" align="right"><%=df.format(x.getDouble("avgTax"))%> %</td>
                    <td class="fzCell" align="right"><%=df.format(x.getDouble("kgsRestan"))%></td>
                </tr>
                <%
                            }
                %>
            </tbody>
            <tfoot>
                <tr style="background-color: #f2f2f2">                    
                    <td class="fzCell" align="center">Total</td>
                    <td class="fzCell" align="right"><%=df.format(AvgKgs)%></td>
                    <td class="fzCell" align="right"><%=df.format((TripsCount==0)?0:(AvgKgs / TripsCount))%></td>
                    <td class="fzCell" align="right"><%=df.format(TaxKgs)%></td>
                    <td class="fzCell" align="right"><%=df.format((AvgKgs - TaxKgs)*100 / TaxKgs)%> %</td>
                    <td class="fzCell" align="right"><%=df.format(ResKgs)%></td>
                </tr>
            </tfoot>
                <% 
                        }
                    }
                %>
        </table>
        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

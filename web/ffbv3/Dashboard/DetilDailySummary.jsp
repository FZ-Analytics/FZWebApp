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
            String millId = FZUtil.getHttpParam(request, "millId");
            String estateId = FZUtil.getHttpParam(request, "estateId");
            DecimalFormat df = new DecimalFormat("#,##0");
        %>
        <label class="fzLabel">Date:</label></td>
        <label class="fzLabel"><%=dateSummary%></label>
        <br><label clas="FzLabel">Mill : <%=millId%> </label>
        <br><label clas="FzLabel">Estate : <%=estateId%> </label>
        <br>
        <label class="fzLabel">BS Summary</label>
        <br/>
        <table id="table" border1="1" style="border-color: lightgray; font-weight: bold;">
            <thead>
                <tr style="background-color: orange">
                    <th width="100px" class="fzCol" style="border-right: 2px solid lightgray;">Vehicle Name</th>
                    <th width="100px" class="fzCol" style="border-right: 2px solid lightgray; text-align: center;">Number of Trip</th>
                    <th width="100px" class="fzCol" style="border-right: 2px solid lightgray; text-align: right; padding: 2px">Total Kg Actual</th>
                    <th width="100px" class="fzCol" style="border-right: 2px solid lightgray; text-align: right; padding: 2px">Avg. Kg/Trip</th>
                </tr>
            </thead>
            <tbody>
                <%
                    int tTrip = 0;
                    int tAct = 0;
                    int tAvg = 0;

                    if (!dateSummary.isEmpty()) {
                        JSONArray o = DetilSummary.rdbTrips01(dateSummary,millId,estateId);
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
        <label >Division Summary</label>

        <br>
        <table id="itable" border1="1" style="border-color: lightgray;  font-weight: bold;">
            <thead>
                <tr style="background-color: orange; ">
                    <th width="100px" class="fzCol">Division</th>
                    <th width="100px" style="border-right: 2px solid lightgray; text-align: right; padding: 2px"></th>
                    <th width="100px" class="fzCol" style="border-right: 2px solid lightgray; text-align: right; padding: 2px"><10am</th>
                    <th width="100px" class="fzCol" style="border-right: 2px solid lightgray; text-align: right; padding: 2px">10am - 14pm</th>
                    <th width="100px" class="fzCol" style="border-right: 2px solid lightgray; text-align: right; padding: 2px">14pm - 18pm</th>
                    <th width="100px" class="fzCol" style="border-right: 2px solid lightgray; text-align: right; padding: 2px">>18pm</th>
                    <th width="100px" class="fzCol" style="text-align: right; padding: 2px">Total</th>
                    <th width="100px" class="fzCol" style="text-align: right;">Remaining Bin</th>
                </tr>
            </thead>
            <tbody>
                <%
                    JSONArray o = null;
                    JSONObject x;
                    int TripsCount = 0;
                    Double TotalKgs = new Double("0");
                    Double Kgs1 = new Double("0");
                    Double Kgs2 = new Double("0");
                    Double Kgs3 = new Double("0");
                    Double Kgs4 = new Double("0");
                    int trips1 = 0;
                    int trips2 = 0;
                    int trips3 = 0;
                    int trips4 = 0;
                        String sBin = "";
                        int iBin = 0;
                        int tBin = 0;
                    if (!dateSummary.isEmpty()) {
                        o = DetilSummary.rdbTrips02(dateSummary,millId,estateId);
                        HashMap<String, String> pl = new HashMap<String, String>();
                        if (o != null && o.length() > 0) {
                            for (int i = 0; i < o.length(); i++) {
                                x = o.getJSONObject(i);
                                trips1 = trips1 + x.getInt("trip1");
                                trips2 = trips2 + x.getInt("trip2");
                                trips3 = trips3 + x.getInt("trip3");
                                trips4 = trips4 + x.getInt("trip4");
                                TripsCount = TripsCount + x.getInt("TripsCount");
                                Kgs1 = Kgs1 + x.getInt("kg1");
                                Kgs2 = Kgs2 + x.getInt("kg2");
                                Kgs3 = Kgs3 + x.getInt("kg3");
                                Kgs4 = Kgs4 + x.getInt("kg4");
                                TotalKgs = TotalKgs + x.getInt("ActualKgs");
                                iBin = x.getInt("remainingBin");
                                sBin = String.valueOf(iBin).trim();
                                if (tBin >= 999) sBin = "N/A";
                                tBin = tBin + iBin;
                %>
                <tr>                    
                    <td class="fzCell" rowspan="2"><%=x.getString("divID")%></td>
                    <td class="fzCell">Trips</td>
                    <td class="fzCell" align="right" ><%=x.getInt("trip1")%></td>
                    <td class="fzCell" align="right" ><%=x.getInt("trip2")%></td>
                    <td class="fzCell" align="right" ><%=x.getInt("trip3")%></td>
                    <td class="fzCell" align="right" ><%=x.getInt("trip4")%></td>
                    <td class="fzCell" align="right" ><%=x.getInt("TripsCount")%></td>
                    <td class="fzCell" align="right" ><%=sBin%></td>
                </tr>
                <tr>
                    <td>Kgs</td>
                    <td class="fzCell" align="right" ><%=df.format(x.getInt("kg1"))%></td>
                    <td class="fzCell" align="right" ><%=df.format(x.getInt("kg2"))%></td>
                    <td class="fzCell" align="right" ><%=df.format(x.getInt("kg3"))%></td>
                    <td class="fzCell" align="right" ><%=df.format(x.getInt("kg4"))%></td>
                    <td class="fzCell" align="right" ><%=df.format(x.getInt("ActualKgs"))%></td>
                    <td></td>
                </tr>
                <%
                    }
                %>
            </tbody>
                <%
                        }
                        String stBin = (tBin>=999)?"N/A":String.valueOf(tBin);
                 %>
            <tfoot>
                <tr style="background-color: #f2f2f2">                    
                    <td class="fzCell" rowspan="2">T O T A L</td>
                    <td class="fzCell">Trips</td>
                    <td class="fzCell" align="right" ><%=trips1%></td>
                    <td class="fzCell" align="right" ><%=trips2%></td>
                    <td class="fzCell" align="right" ><%=trips3%></td>
                    <td class="fzCell" align="right" ><%=trips4%></td>
                    <td class="fzCell" align="right" ><%=TripsCount%></td>
                    <td class="fzCell" align="right" ><%=stBin%></td>
                </tr>
                <tr style="background-color: #f2f2f2">
                    <td>Kgs</td>
                    <td class="fzCell" align="right" ><%=df.format(Kgs1)%></td>
                    <td class="fzCell" align="right" ><%=df.format(Kgs2)%></td>
                    <td class="fzCell" align="right" ><%=df.format(Kgs3)%></td>
                    <td class="fzCell" align="right" ><%=df.format(Kgs4)%></td>
                    <td class="fzCell" align="right" ><%=df.format(TotalKgs)%></td>
                    <td></td>
                </tr>
            </tfoot>
                 <%
                    }

                %>
        </table>
            
        <br>
        <label >Division Production Summary</label>

        <br>
        <table id="itable" border1="1" style="border-color: lightgray; font-weight: bold;">
            <thead>
                <tr style="background-color: orange">
                    <th width="100px" class="fzCol" style="border-right: 2px solid lightgray;">Division</th>
                    <th width="100px" class="fzCol" style="border-right: 2px solid lightgray; text-align: right; padding: 2px">Total Trip</th>
                    <th width="100px" class="fzCol" style="border-right: 2px solid lightgray; text-align: right; padding: 2px">Avg. Kg/Trip</th>
                    <th width="100px" class="fzCol" style="border-right: 2px solid lightgray; text-align: right; padding: 2px">Total Kg Actual</th>
                    <th width="100px" class="fzCol" style="border-right: 2px solid lightgray; text-align: right; padding: 2px">Estimation Kg</th>
                    <th width="100px" class="fzCol" style="text-align: right; padding: 2px">Actual vs Estimation (%)</th>
                    <!--th width="100px" class="fzCol">Restan Kg</th-->
                </tr>
            </thead>
            <tbody>
                <%
                    if (o!=null) {
                        HashMap<String, String> pl = new HashMap<String, String>();
                        TotalKgs = new Double("0");
                        Double AvgKgs = new Double("0");
                        Double AvgTrip = new Double("0");
                        Double TaxKgs = new Double("0");
                        Double AktKgs = new Double("0");
                        Double ResKgs = new Double("0");
                        //TripsCount = 0;
                        if (o != null && o.length() > 0) {
                            for (int i = 0; i < o.length(); i++) {
                                x = o.getJSONObject(i);
                                AvgKgs = AvgKgs + x.getDouble("ActualKgs");
                                AvgTrip = AvgTrip + x.getDouble("avgTrip");
                                TaxKgs = TaxKgs + x.getDouble("KgsTax");
                                AktKgs = AktKgs + x.getDouble("avgTax");
//                                ResKgs = ResKgs + x.getDouble("kgsRestan");
                %>
                <tr>                    
                    <td class="fzCell"><%=x.getString("divID")%></td>
                    <td class="fzCell" align="right"><%=df.format(x.getDouble("TripsCount"))%></td>
                    <td class="fzCell" align="right"><%=df.format(x.getDouble("avgTrip"))%></td>
                    <td class="fzCell" align="right"><%=df.format(x.getDouble("ActualKgs"))%></td>
                    <td class="fzCell" align="right"><%=df.format(x.getDouble("KgsTax"))%></td>                    
                    <td class="fzCell" align="right"><%=df.format(x.getDouble("avgTax"))%> %</td>
                    <!--td class="fzCell" align="right">< %=df.format(x.getDouble("kgsRestan"))%></td-->
                </tr>
                <%
                            }
                %>
            </tbody>
            <tfoot>
                <tr style="background-color: #f2f2f2">                    
                    <td class="fzCell" align="center">Total</td>
                    <td class="fzCell" align="right"><%=df.format((TripsCount))%></td>
                    <td class="fzCell" align="right"><%=df.format((TripsCount==0)?0:(AvgKgs / TripsCount))%></td>
                    <td class="fzCell" align="right"><%=df.format(AvgKgs)%></td>
                    <td class="fzCell" align="right"><%=df.format(TaxKgs)%></td>
                    <td class="fzCell" align="right"><%=df.format((AvgKgs)*100 / TaxKgs)%> %</td>
                    <!--td class="fzCell" align="right">< %=df.format(ResKgs)%></td-->
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

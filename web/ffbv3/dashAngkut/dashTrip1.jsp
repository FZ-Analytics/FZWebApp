<%@page import="org.json.JSONObject"%>
<%@page import="com.fz.ffbv3.service.order.OrderDAO"%>
<%@page import="org.json.JSONArray"%>
<%@page import="com.fz.util.FZUtil"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%-- 
    Document   : dashTrip1
    Created on : Nov 27, 2017, 4:20:21 PM
    Author     : Eko
--%>

<%@include file="../appGlobal/pageTop.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Trips of Trucks</title>
    </head>
    <body>
        <form action="dashTrip1.jsp" type="get">
            <!--%run(new com.fz.ffbv3.service.dashAngkut.dashTrip1Logic());%-->
            <%@include file="../appGlobal/bodyTop.jsp" %>
            <script>
                $(function () {
                    $("#fromDate").datepicker();
                    $("#fromDate").datepicker("option", "dateFormat", "yy-mm-dd");
                    $("#fromDate").val(yyyymmddDate(new Date()));

                    $("#toDate").datepicker();
                    $("#toDate").datepicker("option", "dateFormat", "yy-mm-dd");
                    $("#toDate").val(yyyymmddDate(new Date()));
                });
            </script>
            <h3>Trips of Trucks</h3>
            <%
                String fromDate = FZUtil.getHttpParam(request, "fromDate");
                String toDate = FZUtil.getHttpParam(request, "toDate");
               
                JSONArray aTruck = new JSONArray();
                JSONArray aDiv = new JSONArray();
                JSONObject o;
                %>
            Date : <input class="fzInput" type="text" id="fromDate" name="fromDate" values="<%=fromDate%>"/>
            To : <input class="fzInput" type="text" id="toDate" name="toDate" values="<%=toDate%>"/>
            <br/>
            <br/>
            <table border="1">
                <thead>
                    <tr>
                        <th>Div</th>
                        <th>Trip < 10 <br/> Target 1</th>
                        <th>Trip < 13 <br/> Target 3</th>
                        <th>Trip < 18 <br/> Target 6</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        if (!fromDate.isEmpty())
                        {
                            String divID = "";
                            int j1, j2, j3;
                            String[] clr = {"rgb(0,255,0)","rgb(128,0,0)","rgb(0,128,128)"};
                            String c1, c2, c3;
                            
                           if (toDate.isEmpty()) toDate = fromDate;
                           aDiv = OrderDAO.rdbTrips(fromDate,toDate, 1);
                           for(int i = 0; i<aDiv.length();i++) {
                             o = aDiv.getJSONObject(i);
                             divID = o.getString("divID");
                             j1 = o.getInt("j1");
                             j2 = o.getInt("j2");
                             j3 = o.getInt("j3");
                             c1 = (j1<1)?clr[0]:(j1==1)?clr[1]:clr[2];
                             c2 = (j2<1)?clr[0]:(j2==1)?clr[1]:clr[2];
                             c3 = (j3<1)?clr[0]:(j3==1)?clr[1]:clr[2];
                    %>
                    <tr>
                        <td><%=o.getString("divID")%></td>
                        <td style="background-color: <%=c1%>"><%=o.getInt("j1")%></td>
                        <td style="background-color: <%=c2%>"><%=o.getInt("j2")%></td>
                        <td style="background-color: <%=c3%>"><%=o.getInt("j3")%></td>
                    </tr>
                    <%
                           }
                        }
                    %>
                </tbody>
            </table>
            <br/>
            <button class="btn fzButton" type="submit" name="submit" value="run">Run</button>
            <script>
                <%= request.getAttribute("oTruck")%>
            </script>
            <%@include file="../appGlobal/bodyBottom.jsp"%>
        </form>
    </body>
</html>

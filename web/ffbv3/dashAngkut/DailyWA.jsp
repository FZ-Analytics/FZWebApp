<%-- 
    Document   : DailyWA
    Created on : Dec 7, 2017, 11:47:53 AM
    Author     : Eko
--%>

<%@page import="org.json.JSONObject"%>
<%@page import="com.fz.ffbv3.service.order.OrderDAO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Daily Reoprt</title>
    </head>
    <body>
        <%@include file="../appGlobal/bodyTop.jsp" %>
            <h3>Daily Report</h3>
            <br/>
            <label class="fzlabel" >Date : <%=request.getParameter("tgl")%></label>
            
            <br/>
            <div>
                <%
                    try {
                        String tgl = request.getParameter("tgl");
                        JSONObject o1 = OrderDAO.rdbTrip5(tgl);
                        JSONObject o2 = OrderDAO.rdbTrip6(tgl);
                        if (o1!=null) 
                            out.println("1. Jumlah Trip PM: " + o1.getString("nTrips") 
                                        + " trip, " + o1.getString("nMovers") + " Primes Movers");
                        if (o2!=null) {
                            out.println("<br/>3. 1 Trip < Jam 10:" + o2.getString("t1"));
                            out.println("<br/>4. 1 Trip < Jam 13:" + o2.getString("t2"));
                            out.println("<br/>5. 1 Trip < Jam 18:" + o2.getString("t3"));
                        }
                    } catch (Exception e) { 
                        String err = e.getMessage();
                    }
                %>
            </div>
        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

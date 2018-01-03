<%-- 
    Document   : order
    Created on : Sep 19, 2017, 1:17:01 PM
    Author     : Eko
--%>

<%@page import="com.fz.ffbv3.service.order.Order"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pick Order</title>
    </head>
    <body>
        <form action="orderlist.jsp" method="get">
       <%@include file="../appGlobal/bodyTop.jsp"%>
        <script>
        $( function() {
          $( "#tanggal" ).datepicker();
          $( "#tanggal" ).datepicker( "option", "dateFormat", "yy-mm-dd");
          $( "#tanggal" ).val(yyyymmddDate(new Date()));
        } );
<%run(new com.fz.ffbv3.service.order.OrderList());%>
        </script>
        <h3>Pick Order</h3>
        <form action="" method="post">
            <!--label class="fzlabel">Order Date</label>
            <input class="fzInput" id="tanggal" name="tanggal" value=""/>
            <br/>
            <lable class="fzlabel">Division</lable> 
            <input class="fzInput" id="divID" name="divID" value=""/>
            <br/>
            <br/>
            <button class="btn fzButton" type="submit" 
                    name="submit" value="list">Search</button>

            <button class="btn fzButton" type="submit" 
                    name="submit" value="add">Add</button-->
            <br/>
            <br/>
            <table border="1" class="table">
            <tr>
                <th>BINE</th>
                <th>Block #1</th>
                <th>Block #2</th>
                <th>Est Time</th>
            </tr>
            <%
                ArrayList<Order> ol = (ArrayList<Order>)request.getAttribute("res");
                for (Order o : (List<Order>) getList("res")) { %>

            <tr>
                <td><%=o.divID%></td>
                <td><%=o.blockId1%></td>
                <td><%=o.blockId2%></td>
                <td><%=o.estTime%></td>
            </tr>
            <%
                }
            %>
        </table>
        </form>
  <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

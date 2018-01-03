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
        <form action="order.jsp" method="get">
       <%@include file="../appGlobal/bodyTop.jsp"%>
        <script>
        $( function() {
          $( "#tanggal" ).datepicker();
          $( "#tanggal" ).datepicker( "option", "dateFormat", "yy-mm-dd");
          $( "#tanggal" ).val(yyyymmddDate(new Date()));
        } );
        </script>
        <h3>Pick Order</h3>
        <form action="" method="post">
            <label class="fzlabel">Order Date</label>
            <input class="fzInput" id="tanggal" name="tanggal" value=""/>
            <br/>
            <lable class="fzlabel">Division</lable> 
            <input class="fzInput" id="divID" name="divID" value=""/>
            <br/>
            <br/>
            <button class="btn fzButton" type="submit" 
                    name="submit" value="list">Search</button>

            <button class="btn fzButton" type="submit" 
                    name="submit" value="add">Add</button>
            <br/>
            <br/>
        </form>
  <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

<%-- 
    Document   : hvsEstm2
    Created on : Oct 4, 2017, 11:06:15 AM
    Author     : Eko
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
  <%@include file="../appGlobal/pageTop.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Process Order</title>
    </head>
    <body>
        <%@include file="../appGlobal/bodyTop.jsp"%>
        <script>
        $( function() {
          $( "#hvsDt" ).datepicker();
          $( "#hvsDt" ).datepicker( "option", "dateFormat", "yy-mm-dd");
          $( "#hvsDt" ).val(yyyymmddDate(new Date()));
        } );
        </script>
        <form action="hvsEstm2Job.jsp" method="post">
            <p />
            <h3>Process First Order of the day</h3>
            <label class="fzLabel">Tanggal </label>
            <input class="fzInput" id="hvsDt" name="hvsDt" value=""/>

            <br><br>
            <button class="btn fzButton" type="submit" name="submit" value="go">Go</button>

            <%@include file="../appGlobal/bodyBottom.jsp"%>
        </form>
    </body>
</html>

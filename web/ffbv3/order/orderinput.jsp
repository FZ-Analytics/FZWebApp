<%-- 
    Document   : orderinput
    Created on : Sep 19, 2017, 3:04:31 PM
    Author     : Eko
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Input Order</title>
    </head>
    <body>
       <%@include file="../appGlobal/bodyTop.jsp"%>
  <script>
  $( function() {
    $( "#harvestDate" ).datepicker();
    $( "#harvestDate" ).datepicker( "option", "dateFormat", "yy-mm-dd");
  } );
  </script>
       <h3>Input Order</h3>
       <form id="frmOrder" method="POST" action="orderSave.jsp">
           <div class="container">
            <table>
                <tr><td>Divisi</td>
                <a href="../../../src/java/com/fz/ffbv3/service/harvestplan/HarvestPlanSave.java"></a>
                    <td class="fzCell celVal">
                      <select id="divId" name="divId">
                          <option value="BINE1" selected>BINE 1</option>
                          <option value="BINE2">BINE 2</option>
                          <option value="BINE3">BINE 3</option>
                          <option value="BINE4">BINE 4</option>
                          <option value="BINE5">BINE 5</option>
                      </select>
                    </td>
                </tr>
                <tr><td>Location between</td>
                    <td><input type="text" class="fzInput" id="blokID1" name="blokID1"/></td></tr>
                <tr><td> and</td>
                    <td><input type="text" class="fzInput" id="blokID2" name="blokID2"/></td></tr>
                <tr> <td>Est.Bin Readey</td><td><input type="text" class="fzInput" id="estTime" name="estTime"/></td></tr>
            </table>
           <br>
           <br>
            <button class="btn fzButton" type="submit">GO</button>
           </div>
       </form>
</html>

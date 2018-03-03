<%-- 
    Document   : hvsEstmList
    Created on : Sep 23, 2017, 5:07:33 AM
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="com.fz.generic.Db"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.fz.ffbv3.service.order2.Order2Record"%>
<%@page import="com.fz.util.FZUtil"%>
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
  <%
      String wkt = (new SimpleDateFormat("HH:mm")).format(new Date());
      
      try (Connection con = (new Db()).getConnection("jdbc/fz"); 
              Statement stm = con.createStatement()) {
          ResultSet rs = stm.executeQuery("select current_time() wkt");
          if (rs.next()) 
              wkt = rs.getString("wkt");
      }

  %>
  <script>
  $( function() {
    $( "#hvsDt" ).datepicker();
    $( "#hvsDt" ).datepicker( "option", "dateFormat", "yy-mm-dd");
    $( "#hvsDt" ).val(yyyymmddDate(new Date()));
    showRemainingBin();
    //showrestan();
  } );
  
  function showRemainingBin() {
      var d = new Date();
      if (d.getHours() >= 15) 
        $("#dLastOrder").show();
      else 
        $("#dLastOrder").hide();
      showRestan();
  }
  function showrestan() { 
      var lstOrd = $("#lastOrders").val().trim();
      if (lstOrd=="0") $("#dRestanKgs").show();
        else {
            $("#restanKg").val("0");
            $("#dRestanKgs").hide();
        }
  }
  </script>
        <h3>Job (Order)</h3>
        <form class="container" action="order2FrmAdd.jsp" method="post">
            <input type="hidden" id="curTime" name="curTime" value="<%=wkt%>">
            <div class="fzErrMsg">
                <%
//                    String dupIDCode = "";
//                    String dupID = get("duplicateJobID");
//                    if (dupID.length() > 0){
//                        dupIDCode = "Duplicate order, with jobID " + dupID;
////                        dupIDCode += 
////                                "<br><button "
////                                + " name='orderAnyway' "
////                                + " value='orderAnyway'"
////                                + " class='btn fzButton' "
////                                + ">Order Anyway</button>"
////                                ;
//                    }
//                    out.println(dupIDCode);
                %>
            </div>
            
            <div class="fzErrMsg">
                <%=get("errMsg")%>
            </div>
            
            <br>
            <label class="fzLabel">Estate + Division</label>
            <input class="fzInput" type="text" id="divID" name="divID" 
                   value="" required>
            
            <br>
            <label class="fzLabel"></label>
            <span class="fzLabelBottom">e.g. "BINE1"</span>
            
            <br><br>
            <label class="fzLabel">Between Block</label>
            <input class="fzInput" type="text" id="block1" name="block1" 
                   value="" required>
            
            <br>
            <label class="fzLabel"></label>
            <span class="fzLabelBottom">e.g. "A05"</span>
            
            <br><br>
            <label class="fzLabel">And Block</label>
            <input class="fzInput" type="text" id="block1" name="block2" 
                   value="">
            
            <!--br><br>
            <label class="fzLabel">Mill</label>
            <select class="fzInput" id="millID" name="millID">
                <option value="LWSM">LWSM</option>
                <option value="BPRM">BPRM</option>
            </select-->

            <br><br>
            <label class="fzLabel">Direction Location</label>
            <select class="fzInput" id="dirLoc" name="dirLoc">
                <option value="Utara">Utara (North)</option>
                <option value="Tengah">Tengah (Center)</option>
                <option value="Selatan">Selatan (South)</option>
            </select>
            
            <br><br>
            <label class="fzLabel">Bin Ready Estimate</label>
            <input class="fzInput" type="text" id="readyTime" name="readyTime" 
                   value="" required>
            
            <br>
            <label class="fzLabel"></label>
            <span class="fzLabelBottom">e.g. "07:45", "13:20"</span>
            
            <br><br>
            <label class="fzLabel">Estimate Kg</label>
            <input class="fzInput" type="number" id="estmKg" name="estmKg" 
                   value="" required>
            
            <br><br>
            <label class="fzLabel">Estimate FFB</label>
            <input class="fzInput" type="number" id="estmFfb" name="estmFfb" 
                   value="" required>
            
            <br><br>
            <label class="fzLabel">Bin Position</label>
            <input class="fzInput" type="text" id="remark" name="remark" 
                   value="" required>
            
            <!--br><br>
            <label class="fzLabel">Is last order</label>
            <input class="fzInput" type="text" 
                   id="isLastOrder" name="isLastOrder" value="no">
            
            <br>
            <label class="fzLabel"></label>
            <span class="fzLabelBottom">"yes" or "no"</span-->
            
            <!--br><br>
            <label class="fzLabel">Is last 2 order</label>
            <select class="fzInput" 
                   id="isLast2Order" name="isLast2Order" >
                <option value="yes">Yes</option>
                <option value="no" selected>No</option>
            </select-->

            <div id="dLastOrder" name="dLastOrder" hidden>
            <br><br>
            <label class="fzLabel">Bins remaining</label>
            <input class="fzInput" type="number" id="lastOrders" name="lastOrders" value="999" onchange="showrestan()">
            </div>

            <div id="dRestanKgs" name="dRestanKgs" hidden>
            <br><br>
            <label class="fzLabel">Restans (Kgs)</label>
            <input class="fzInput" type="number" id="restanKg" name="restanKg" value="0">
            </div>
                
            <br><br>
            <button class="btn fzButton" type="submit" 
                    name="submit" value="add">Add</button>
            
        </form> 
  <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

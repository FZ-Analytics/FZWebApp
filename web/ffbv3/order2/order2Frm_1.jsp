<%-- 
    Document   : hvsEstmList
    Created on : Sep 23, 2017, 5:07:33 AM
--%>

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
  <script>
  $( function() {
    $( "#hvsDt" ).datepicker();
    $( "#hvsDt" ).datepicker( "option", "dateFormat", "yy-mm-dd");
    $( "#hvsDt" ).val(yyyymmddDate(new Date()));
  } );
  </script>
        <h3>Job (Order)</h3>
        <form class="container" action="order2FrmAdd.jsp" method="post">

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
            
            <br><br>
            <label class="fzLabel">Estate + Division</label>
            <input class="fzInput" type="text" id="divID" name="divID" 
                   value="">
            
            <br>
            <label class="fzLabel"></label>
            <span class="fzLabelBottom">e.g. "BINE1"</span>
            
            <br><br>
            <label class="fzLabel">Between Block</label>
            <input class="fzInput" type="text" id="block1" name="block1" 
                   value="">
            
            <br>
            <label class="fzLabel"></label>
            <span class="fzLabelBottom">e.g. "A05"</span>
            
            <br><br>
            <label class="fzLabel">And Block</label>
            <input class="fzInput" type="text" id="block1" name="block2" 
                   value="">
            
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
                   value="">
            
            <br>
            <label class="fzLabel"></label>
            <span class="fzLabelBottom">e.g. "07:45", "13:20"</span>
            
            <br><br>
            <label class="fzLabel">Estimate Kg</label>
            <input class="fzInput" type="text" id="estmKg" name="estmKg" 
                   value="">
            
            <br><br>
            <label class="fzLabel">Estimate FFB (Janjang Buah)</label>
            <input class="fzInput" type="text" id="estmFfb" name="estmFfb" 
                   value="">
            
            <br><br>
            <label class="fzLabel">Bin Position / Remark</label>
            <input class="fzInput" type="text" id="remark" name="remark" 
                   value="">
            
            <!--br><br>
            <label class="fzLabel">Is last order</label>
            <input class="fzInput" type="text" 
                   id="isLastOrder" name="isLastOrder" value="no">
            
            <br>
            <label class="fzLabel"></label>
            <span class="fzLabelBottom">"yes" or "no"</span-->
            
            <br><br>
            <label class="fzLabel">Is last 2 order</label>
            <select class="fzInput" 
                   id="isLast2Order" name="isLast2Order" >
                <option value="yes">Yes</option>
                <option value="no" selected>No</option>
            </select>
            
            <br><br>
            <button class="btn fzButton" type="submit" 
                    name="submit" value="add">Add</button>
            
        </form>         
  <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

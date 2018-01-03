<%-- 
    Document   : dashProd1
    Created on : Oct 15, 2017, 10:20:13 AM
    Author     : Eko
--%>

<%@include file="../appGlobal/pageTop.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../js/chart.js"></script>
<script src="../js/chart.min.js"></script>
<script src="../js/Chart.bundle.js"></script>
<script src="../js/Chart.bundle.min.js"></script>
<!--link rel="stylesheet" href="../js/myChartAdd.css"-->
<style> 
.containerxx {
  width: 400px;
  height: 200px;
  margin: 15px auto;
}
</style>
    <title>Production Per Block</title>
    </head>
    <body>
        <%@include file="../appGlobal/bodyTop.jsp"%>
        <% 
					/*
            String sMill = request.getAttribute("idMill").toString();
            String sEst  = request.getAttribute("idEstate").toString();
            String sDiv  = request.getAttribute("idDiv").toString();
						*/
        %>
        <%run(new com.fz.ffbv3.service.dashRestan.dashRestanMain());%>
        <!--input type="hidden" id ="opm" value='< %=request.getAttribute("pm")%>'> 
        <input type="hidden" id ="opw" value='< %=request.getAttribute("pw")%>'> 
        <input type="hidden" id ="opd" value='< %=request.getAttribute("pd")%>'--> 
        <br>
        <h3>Restan</h3>

                <form action='dashRestanRun.jsp'>
                <button class="btn fzButton" type="submit" >Change Search</button>
                </form>
        <br>
        <!--table border='0' align='center'>
            <tr>
                <td width='100px'><label clas='fzlabel' >Mill : < %=sMill%></label></td>
                <td width='100px'><label clas='fzlabel' >Estate : < %=sEst%></label></td>
                <td width='100px'><label clas='fzlabel' >Division : < %=sDiv%></label></td>
            </tr>
        </table-->
    <style>
ul {
    list-style-type: none;
    margin: 0;
    padding: 0;
    overflow: hidden;
    background-color: rgb(64,64,64);
}

li {
    float: left;
}

li a {
    display: block;
    color: white;
    text-align: center;
    padding: 16px;
    text-decoration: none;
}

li a:hover {
    background-color: #111111;
}
</style>    
            <!--table border="0">
                <tr>
                    <td><div class="containerxx" ><canvas id="pmChart"></canvas></div></td>
                    <td><div class="containerxx" ><canvas id='pwChart'></canvas></div></td>
                    <td><div class="containerxx" ><canvas id='pdChart'></canvas></div></td>
                </tr>
            </table-->
            <ul>
                    <li><div class="containerxx" ><canvas id="pmChart"></canvas></div></li>
                    <li><div class="containerxx" ><canvas id='pwChart'></canvas></div></li>
                    <li><div class="containerxx" ><canvas id='pdChart'></canvas></div></li>
            </ul>
        <script>
           var s = document.getElementById("opm").value;
           var j = setColor(jQuery.parseJSON(s));
           var ctx = document.getElementById('pmChart').getContext('2d');
           var pmChart = new Chart(ctx, {
              type: 'bar',
              data: j,
              options : {
                title : {
                    display : true,
                    text: 'Months'
                },
                scales: {
                    yAxes: {}
                }
              }
           });
           
           s = document.getElementById("opw").value;
           j = setColor(jQuery.parseJSON(s));
           ctx = document.getElementById("pwChart").getContext("2d");
           var pwChart = new Chart(ctx,{
               type: 'bar',
               data:j,
               options:{
                   title: {
                       display:true,
                       text:'Weeks'
                   }
               }
           });
           
           s = document.getElementById("opd").value;
           j = setColor(jQuery.parseJSON(s));
           ctx = document.getElementById("pdChart").getContext("2d");
           var pdChart = new Chart(ctx,{
               type: 'bar',
               data:j,
               options:{
                   title: {
                       display:true,
                       text:'Days'
                   }
               }
           });
           
           function setColor(data) {
            var i = 1;
           
                var bgColor = [
                     'rgba(23, 12, 254, 0.8)',
                     'rgba(0, 128, 255, 0.8)',
                     'rgba(26, 167, 1, 0.8)',
                     'rgba(160, 7, 34, 0.8)',
                     'rgba(128, 128, 128, 0.8)',
                     'rgba(255, 128, 0, 0.8)',
                     'rgba(64, 0, 64, 0.8)'
                 ];
                 var d;
                 $(data.datasets).each(function(){
                     if (i>=bgColor.length) i = 0;
                     this["backgroundColor"]=bgColor[i];
                     i = i + 1;
                 });
            
              
               return data;
           }
        </script>

    </body>
</html>

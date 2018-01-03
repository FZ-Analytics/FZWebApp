<%-- 
    Document   : dashAngkutChart
    Created on : Nov 22, 2017, 10:28:54 AM
    Author     : Eko
--%>

<%@page import="org.json.JSONObject"%>
<%@page import="com.fz.ffbv3.service.dashAngkut.dashAngkutDAO"%>
<%@page import="java.util.Date"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%@page import="com.fz.generic.Db"%>
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
          width: 600px;
          height: 300px;
          margin: 15px auto;
        }
        </style>
        <title>Angkut</title>
        <%
            String divID = request.getParameter("divID");// "BINE1";
            JSONObject o = dashAngkutDAO.listAngkut(divID);
        %>
    </head>
    <body>
        <%@include file="../appGlobal/bodyTop.jsp"%>
        <h3>Trip</h3>
        Div : 
        <%= divID %>
        <br/>
        <div id="tes" name="tes"></div>
        <div>
        <canvas id="pmChart"></canvas>
        </div>
        <script>
           var s = <%= o %>; //document.getElementById("opm").value;
           var j = setColor(s);
           var tes = document.getElementById("tes");
           tes.innerHTML=JSON.stringify(j);
           var ctx = document.getElementById('pmChart').getContext('2d');
///*
           var pmChart = new Chart(ctx, {
               type:'bar',
              data: j,
              options : {
                title : {
                    display : true,
                    text: 'Trips of Trucks'
                },
///*
                scales: {
                    xAxes: [{
                        stacked: true,
                    }],
                    yAxes: [{
                        stacked: true
                    }]
                },  
//*/
                annotation: {
                  annotations: [{
                    type: 'line',
                    mode: 'horizontal',
                    scaleID: 'y-axis-0',
                    value: 2000,
                    borderColor: 'rgb(75, 192, 192)',
                    borderWidth: 4,
                    label: {
                      enabled: false,
                      content: 'Test label'
                    },
                  }]
                },
           }
        });
//*/
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
                     this["type"]="bar";
                     this["backgroundColor"]=bgColor[i];
                     i = i + 1;
                 });
              
               return data;
           }

        </script>
    </body>
</html>

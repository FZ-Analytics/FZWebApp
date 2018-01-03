<%-- 
    Document   : LeaderDashboard
    Created on : Nov 30, 2017, 11:39:50 AM
    Author     : Administrator
--%>

<%@page import="org.json.JSONObject"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="com.fz.generic.Db"%>
<%@page import="java.sql.Connection"%>
<%@page import="org.json.JSONArray"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="../appGlobal/pageTop.jsp"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View</title>
        <style type="text/css">
        canvas{
            -moz-user-select: none;
            -webkit-user-select: none;
            -ms-user-select: none;
        }
        </style>
        <script src="../appGlobal/Chart.bundle.js"></script>
    </head>
    <body>        
        <%@include file="../appGlobal/bodyTop.jsp"%>         
        <%
            String str = "";
            String sql = "SELECT\n" +
                    "	concat(NamaPemanen,' - ',divID) as NamaPemanen,\n" +
                    "	SUBSTRING(divId, 5,1) as divId,\n" +
                    "	SUM( Brutto ) AS x,\n" +
                    "	FORMAT(\n" +
                    "		SUM( Brutto )/ COUNT( DISTINCT blockID ),\n" +
                    "		0\n" +
                    "	) AS y\n" +
                    "FROM\n" +
                    "	fz.fbhvsprod\n" +
                    "WHERE\n" +
                    "	divID like 'BINE%'\n" +
                    //"	and month(tanggal) = 11\n" +
                    "GROUP BY\n" +
                    "	NamaPemanen,\n" +
                    "	divId";
            
            try (Connection con = (new Db()).getConnection("jdbc/fz");) {
                try (Statement stm = con.createStatement()) {
                    ResultSet rs = stm.executeQuery(sql);
                    JSONObject o = new JSONObject();                    
                    JSONArray datasety = new JSONArray();                    
                    JSONObject datasetx = new JSONObject();
                    while (rs.next()) {
                        JSONArray result = new JSONArray();
                        datasetx = new JSONObject();
                        o = new JSONObject();
                        o.put("r", rs.getInt("divId"));
                        o.put("x", rs.getInt("x"));
                        o.put("y", rs.getInt("y"));
                        result.put(o);
                        
                        datasetx.put("label", rs.getString("NamaPemanen"));
                        datasetx.put("borderWidth", 1);
                        datasetx.put("data", result);
                        
                        datasety.put(datasetx);
                    }
                    
                    JSONObject bubbleChartData = new JSONObject();
                    JSONObject duration = new JSONObject();
                    JSONArray durations = new JSONArray();  
                    duration.put("duration", 1000);
                    durations.put(duration);
                    bubbleChartData.put("animation", durations);                    
                    bubbleChartData.put("datasets", datasety);
                    
                    str = bubbleChartData.toString(); 
                    System.out.println(str);
                    %>
                    <input type="text" hidden="true" id="Jtxt" value="<%=str%>">
                    
                    <%
                } catch (Exception e) {
                    str = e.getMessage();
                }
            } catch (Exception e) {
                str = e.getMessage();
            }            
        %>        
        
        <script>
            var s = <%=str%>
            //var j = JSON.stringify(<%=str%>);
            var x = setColor(s);
            
            window.onload = function() {
                var ctx = document.getElementById("canvas").getContext("2d");
                window.myChart = new Chart(ctx, {
                    type: 'bubble',
                    data: x,
                    options: {
                        responsive: true,
                        legend: {
                            display: false
                        },
                        title:{
                            display:true,
                            text:'Harvester Leader Board'
                        },
                        tooltips: {
                            mode: 'point'
                        }
                    }
                });
            };
            
            function setColor(data) {
                var i = 1;
                var bgColor = [
                     'rgba(255, 0, 0, 0.8)',
                     'rgba(0, 255, 0, 0.8)',
                     'rgba(26, 64, 255, 0.8)',
                     'rgba(255, 0, 255, 0.8)',
                     'rgba(255, 255, 0, 0.8)'
                 ];
                 var d;
                 $(data.datasets).each(function(index, value){
                     //var y = this["y"];
                     var data = value.data[0]["r"];  
                     if(data == 1){
                         i = 0;
                     }else if(data == 2){
                         i = 1;
                     }else if(data == 3){
                         i = 2;
                     }else if(data == 4){
                         i = 3;
                     }else{
                         i = 4;
                     }
                     $(value.data[0]).each(function(){                         
                         this["r"]=7;
                     });
                     
                     //console.log(index+ ' div ' + ': ' + data); 
                     this["backgroundColor"]=bgColor[i];
                 });
                   return data;
               }
        </script>
        
        <div id="container" style="width: 75%;">
            <canvas id="canvas"></canvas>
        </div>
        
        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

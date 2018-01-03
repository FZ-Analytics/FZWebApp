<%-- 
    Document   : Leaderboard
    Created on : Nov 30, 2017, 11:39:50 AM
    Author     : Administrator
--%>

<%@page import="java.util.Iterator"%>
<%@page import="com.fz.util.FZUtil"%>
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
        <title>Truck Board</title>
        <style type="text/css">
        canvas{
            -moz-user-select: none;
            -webkit-user-select: none;
            -ms-user-select: none;
        }
        </style>
    </head>
    <body>        
        <%@include file="../appGlobal/bodyTop.jsp"%> 
        <script src="../appGlobal/Chart.bundle.js"></script>
        <%
            String listVehiId = FZUtil.getHttpParam(request, "listVehiId");
            String month = FZUtil.getHttpParam(request, "month");
            String str = "";
            
            String query = "";
            /*if(listVehiId != ""){
                String replaceString=listVehiId.replaceAll(",","','");
                query = " AND VehicleID in ('"+replaceString+"')" + " ";
            }*/      
            
            if(month != ""){
                query = query + " AND MONTH(tgl) = " + month + " ";
            }
            
            String sql = "SELECT\n" +
                    "       count(Trip) trip,\n" +
                    "       CONCAT(divId, ' - ', VehicleID) as VehicleID,\n" +
                    "       SUBSTRING(divID, 5,1) as divId,\n" +
                    "       sum(QtyTerima) QtyTerima,\n" +
                    "       VehicleID as vId\n" +
                    "	FROM\n" +
                    "       fz.fbtransportsap\n" +
                    "   WHERE\n" +
                    "       divId like 'BINE%'\n" +
                    query +
                    "   GROUP BY \n" +
                    "       CONCAT(divId, ' - ', VehicleID) ,\n" +
                    "       SUBSTRING(divID, 5,1) ,\n" +
                    "       VehicleID\n"
                    ;
            
            //System.out.println("sql" + sql);
            
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
                        o.put("r", "0"+rs.getInt("divId"));
                        o.put("x", rs.getInt("QtyTerima"));
                        o.put("y", rs.getInt("Trip"));
                        result.put(o);
                        
                        datasetx.put("label", rs.getString("VehicleID"));
                        datasetx.put("borderWidth", 1);
                        datasetx.put("data", result);
                        
                        datasety.put(datasetx);
                        
                        //param
                        if(listVehiId.contains(rs.getString("vId"))){
                            System.out.println(listVehiId + "&&" + rs.getString("vId"));
                            result = new JSONArray();
                            o = new JSONObject();
                            datasetx = new JSONObject();
                            o.put("r", "99");
                            o.put("x", rs.getInt("QtyTerima"));
                            o.put("y", rs.getInt("Trip"));
                            result.put(o);

                            datasetx.put("label", rs.getString("VehicleID"));
                            datasetx.put("borderWidth", 1);
                            datasetx.put("data", result);
                            
                            datasety.put(datasetx);
                        }
                    }
                    
                    JSONObject bubbleChartData = new JSONObject();
                    JSONObject duration = new JSONObject();
                    JSONArray durations = new JSONArray();  
                    duration.put("duration", 1000);
                    durations.put(duration);
                    bubbleChartData.put("animation", durations);                    
                    bubbleChartData.put("datasets", datasety);
                    
                    str = bubbleChartData.toString(); 
                    //////////////////
                    //System.out.println(str);
                    
                    JSONArray data = bubbleChartData.getJSONArray("datasets");
                    System.out.println(data.length());
                    //int tm = 0;
                    
                    
                    String strCopy = "";
                    
                    for(int i = 0 ; i < data.length() ; i++) {
                        JSONObject jo = new JSONObject(data.get(i).toString());
                        JSONArray ja = jo.getJSONArray("data");
                        JSONObject jd = new JSONObject(ja.get(0).toString());
                        //JSONObject jr = ;
                        System.out.println(jd.get("r").toString());
                        
                        //if(jd.get("r").toString()){
                            //copy
                        //}
                            //System.out.println(tm++);
                            //Thread.sleep(3 * 1000);
                    }                    
                    
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
                            text:'Truck Board'
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
                     'rgba(255, 255, 0, 0.8)',
                     'rgba(242, 242, 242, 0.8)'
                 ];
                 var d;
                 $(data.datasets).each(function(index, value){
                     //var y = this["y"];
                     var data = value.data[0]["r"];  
                     if(data == '01'){
                         i = 0;
                     }else if(data == '02'){
                         i = 1;
                     }else if(data == '03'){
                         i = 2;
                     }else if(data == '04'){
                         i = 3;
                     }else if(data == '99'){
                         //param
                         i = 5;
                     }else {
                         i = 4;
                     }
                     
                     if(data == '99'){
                         //param
                         $(value.data[0]).each(function(){                         
                            this["r"]=20;
                        });
                     }else{
                         $(value.data[0]).each(function(){                         
                            this["r"]=10;
                        });
                     }
                     
                     
                     //console.log(index+ ' div ' + ': ' + data); 
                     this["backgroundColor"]=bgColor[i];
                 });
                   return data;
               }
        </script>
        
        <div id="container" style="height: 25vh; width: 80%">
            <canvas id="canvas"></canvas>
        </div>
        
        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

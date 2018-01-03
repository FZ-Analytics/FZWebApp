<%-- 
    Document   : hvsEstmList
    Created on : Sep 23, 2017, 5:07:33 AM
--%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.HashSet"%>
<%@page import="com.fz.util.FZUtil"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="com.fz.util.EObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%!
    List<EObject> os = new ArrayList<EObject>();
    HashMap<String, EObject> divs = new HashMap<String, EObject>();
    
    void createData(String divID) throws Exception {
        String ou = "";
        
        EObject divCode = new EObject();
        divCode.putStr("divID", divID);
        
        for (EObject ob : os){
            if (ob.getStr("divID").equals(divID)){
                if (ou.length() > 0){
                    ou += ",";
                }
                ou += "\n{";
                ou += "\n x:" + ob.getInt("qty");
                ou += "\n, y:" + ob.getInt("trip");
                ou += "\n, r:10";
                ou += "\n,}";
            }
        }
        divCode.putStr("data", ou);
        divs.put(divID, divCode);
    }
%>
<%    // connect
    javax.naming.InitialContext ctx = 
            new javax.naming.InitialContext();
    javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup(
            "java:/comp/env/" + "jdbc/fz");
    String sql = 
        "select "
        + " vehicleid "
        + " , tglBuat "
        + " , concat(estateID,`div`) divID "
        + " , sum(`   Qtykirim`) qty "
        + " , count(distinct spb) trip "
        + "  from fbtransport  "
        + "  where estateID = 'BINE' "
        + " and length(vehicleId) > 0 "
        + " and tglBuat > '2017-11-01' "
        + " group by  "
        + " concat(estateID,`div`) "
        + " , vehicleid "
        + " , tglBuat "
        + " order by  "
        + " vehicleid "
        + " , tglBuat "
        + " , concat(estateID,`div`) "
        ;
        
    try (java.sql.Connection con = ds.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
        while (rs.next()){
            EObject ob = new EObject();
            ob.putStr("vehicleid", FZUtil.getRsString(rs, 1, ""));
            ob.putStr("date", FZUtil.getRsString(rs, 2, ""));
            ob.putStr("divID", FZUtil.getRsString(rs, 3, ""));
            ob.putInt("qty", FZUtil.getRsInt(rs, 4, 0));
            ob.putInt("trip", FZUtil.getRsInt(rs, 5, 0));
            os.add(ob);//1727 vkp er
        }
        createData("BINE1");
        createData("BINE2");
        createData("BINE3");
        createData("BINE4");
        createData("BINE5");
        
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View</title>
        <script src="Chart.bundle.js"></script>
        <script src="utils.js"></script>
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
        <h3>Dashboard</h3>
    <div id="container" style="width: 75%;">
        <canvas id="canvas"></canvas>
    </div>
    <!--button id="randomizeData">Randomize Data</button>
    <button id="addDataset">Add Dataset</button>
    <button id="removeDataset">Remove Dataset</button>
    <button id="addData">Add Data</button>
    <button id="removeData">Remove Data</button-->
    <script>
        var DEFAULT_DATASET_SIZE = 7;

        var addedCount = 0;
        var color = Chart.helpers.color;
        var bubbleChartData = {
            animation: {
                duration: 10000
            },
            datasets: [
            {
                label: "BINE1",
                backgroundColor: color(window.chartColors.red).alpha(0.5).rgbString(),
                borderColor: window.chartColors.red,
                borderWidth: 1,
                data: [<%=divs.get("BINE1").getStr("data")%>]
            }, {
                label: "BINE2",
                backgroundColor: color(window.chartColors.orange).alpha(0.5).rgbString(),
                borderColor: window.chartColors.orange,
                borderWidth: 1,
                data: [<%=divs.get("BINE2").getStr("data")%>]
            }, {
                label: "BINE3",
                backgroundColor: color(window.chartColors.orange).alpha(0.5).rgbString(),
                borderColor: window.chartColors.blue,
                borderWidth: 1,
                data: [<%=divs.get("BINE3").getStr("data")%>]
            }, {
                label: "BINE4",
                backgroundColor: color(window.chartColors.orange).alpha(0.5).rgbString(),
                borderColor: window.chartColors.green,
                borderWidth: 1,
                data: [<%=divs.get("BINE4").getStr("data")%>]
            }, {
                label: "BINE5",
                backgroundColor: color(window.chartColors.orange).alpha(0.5).rgbString(),
                borderColor: window.chartColors.green,
                borderWidth: 1,
                data: [<%=divs.get("BINE5").getStr("data")%>]
            }
            ]
        };

        window.onload = function() {
            var ctx = document.getElementById("canvas").getContext("2d");
            window.myChart = new Chart(ctx, {
                type: 'bubble',
                data: bubbleChartData,
                options: {
                    responsive: true,
                    title:{
                        display:true,
                        text:'Chart.js Bubble Chart'
                    },
                    tooltips: {
                        mode: 'point'
                    }
                }
            });
        };

        document.getElementById('randomizeData').addEventListener('click', function() {
            bubbleChartData.datasets.forEach(function(dataset) {
                dataset.data = dataset.data.map(function() {
                    return {
                        x: randomScalingFactor(),
                        y: randomScalingFactor(),
                        r: Math.abs(randomScalingFactor()) / 5,
                    };
                });
            });
            window.myChart.update();
        });

        var colorNames = Object.keys(window.chartColors);
        document.getElementById('addDataset').addEventListener('click', function() {
            ++addedCount;
            var colorName = colorNames[bubbleChartData.datasets.length % colorNames.length];;
            var dsColor = window.chartColors[colorName];
            var newDataset = {
                label: "My added dataset " + addedCount,
                backgroundColor: color(dsColor).alpha(0.5).rgbString(),
                borderColor: dsColor,
                borderWidth: 1,
                data: []
            };

            for (var index = 0; index < DEFAULT_DATASET_SIZE; ++index) {
                newDataset.data.push({
                    x: randomScalingFactor(),
                    y: randomScalingFactor(),
                    r: Math.abs(randomScalingFactor()) / 5,
                });
            }

            bubbleChartData.datasets.push(newDataset);
            window.myChart.update();
        });

        document.getElementById('addData').addEventListener('click', function() {
            if (bubbleChartData.datasets.length > 0) {
                for (var index = 0; index < bubbleChartData.datasets.length; ++index) {
                    bubbleChartData.datasets[index].data.push({
                        x: randomScalingFactor(),
                        y: randomScalingFactor(),
                        r: Math.abs(randomScalingFactor()) / 5,
                    });
                }

                window.myChart.update();
            }
        });

        document.getElementById('removeDataset').addEventListener('click', function() {
            bubbleChartData.datasets.splice(0, 1);
            window.myChart.update();
        });

        document.getElementById('removeData').addEventListener('click', function() {
            bubbleChartData.datasets.forEach(function(dataset, datasetIndex) {
                dataset.data.pop();
            });

            window.myChart.update();
        });
    </script>
        
  <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

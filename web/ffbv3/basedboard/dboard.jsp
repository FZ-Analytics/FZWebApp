<%-- 
    Document   : dboard
    Created on : Dec 5, 2017, 10:33:11 AM
    Author     : Eko
--%>

<%@include file="../appGlobal/pageTop.jsp"%>
<%@include file="basedboard.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Test</title>
        <script src="../js/chart.js"></script>
        <script src="../js/chart.min.js"></script>
        <script src="../js/Chart.bundle.js"></script>
        <script src="../js/Chart.bundle.min.js"></script>
        <style> 
        .containerxx {
          width: 300px;
          height: 150px;
          border: 1px solid #000;
        }
        </style>
        <script>
            function allowDrop(ev) { ev.preventDefault(); }

            function drag(ev)
            {
                ev.dataTransfer.setData("src",ev.target.id);
            }

            function drop(ev)
            {
                ev.preventDefault();
                var src = document.getElementById(ev.dataTransfer.getData("src"));
                var srcParent = src.parentNode;
                var tgt = ev.currentTarget.firstElementChild;

                ev.currentTarget.replaceChild(src, tgt);
                srcParent.appendChild(tgt);
            }
        </script>
    </head>
    <body>
        <%@include file="../appGlobal/bodyTop.jsp" %>
        <h3>Graph</h3>
        <div id="divChart1" class="containerxx" ondrop='drop(event)' ondragover='allowDrop(event)' style=padding: 2px; l"" >
        <%
            String sql3 = "select DATE_FORMAT(a.PostgDate,'%m') period, divID fieldID, sum(a.Qty) tonnage \n" +
                        "  from fbleftover a \n" + 
                        "  group by fieldID, DATE_FORMAT(a.PostgDate,'%Y%m') \n" +
                        "  order by fieldID, DATE_FORMAT(a.PostgDate,'%Y%m')";

            String[][] as = {{"stackedbar", sql3,"period","tonnage","fieldID"}};
            createGraph(out, "t", as);
        %>
        </div>

        <div id="divChart2" class="containerxx" ondrop='drop(event)' ondragover='allowDrop(event)' >
        <%
            String sql = "select DATE_FORMAT(a.PostgDate,'%m') period, divID fieldID, sum(a.Qty) tonnage \n" +
                        "  from fbleftover a \n" + 
                        "  where divID='BINE1' \n" +
                        "  group by fieldID, DATE_FORMAT(a.PostgDate,'%Y%m') \n" +
                        "  order by fieldID, DATE_FORMAT(a.PostgDate,'%Y%m')";

            String sql2 = "select DATE_FORMAT(a.PostgDate,'%m') period, 't' fieldID, sum(a.Qty) tonnage \n" +
                        "  from fbleftover a \n" + 
                        "  group by DATE_FORMAT(a.PostgDate,'%Y%m') \n" +
                        "  order by DATE_FORMAT(a.PostgDate,'%Y%m')";

            String[][] as2 = {{"stackedbar", sql,"period","tonnage","fieldID"},
                             {"line", sql2,"period","tonnage","fieldID"}}; 
            createGraph(out, "t2", as2);
        %>
        </div>
        <%@include file="../appGlobal/bodyBottom.jsp" %>
    </body>
</html>

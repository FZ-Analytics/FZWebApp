<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="../appGlobal/jquery-ui.min.css?1">
<script src="../appGlobal/jquery.min.js?1"></script>

<link rel='stylesheet prefetch' href='../appGlobal/bootstrap.min.css?1'>
<link rel="stylesheet" href="../appGlobal/generic.css?2">

<%if (request.getServletPath().contains("login.jsp")){%>
    <a href="../main2/main2.jsp"><img src="../img/ffbtoplogo-subtitled.png?1" width="350"></a>
<%} else {
    if (request.getServletPath().contains("aic/aic1")){%>
        <a href="../main2/main2.jsp"><img src="../img/fztoplogo.png?1" width="350"></a>
    <%} else {%>
        <a href="../main2/main2.jsp"><img src="../img/ffbtoplogo.png?2" width="350"></a>
    <%}
}%>
    
<br><br>
<script src="../appGlobal/generic.js?1"></script>

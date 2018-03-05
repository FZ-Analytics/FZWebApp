<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../ffbv3/appGlobal/pageTop.jsp"%>
<%run(new com.fz.tms.testDB());%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <%@include file="../../ffbv3/appGlobal/bodyTop.jsp"%>
        <h3>Login</h3>
        <form class="container" action="entry.jsp" method="post">
            <div class="fzErrMsg">
                <%=get("connectResult")%>
            </div>
            <div class="fzErrMsg">
                <%=get("loginResult")%>
            </div>
            <br>
            <label class="fzLabel">User ID</label>
            <input class="fzInput" type="text" id="userID" name="userID">
            <br>
            <label class="fzLabel">Password</label>
            <input class="fzInput" type="password" id="password" name="password">
            <br>
            <br>
            <button class="btn fzButton" type="submit">GO</button>
        </form>         
        <%@include file="../../ffbv3/appGlobal/bodyBottom.jsp"%>
    </body>
</html>

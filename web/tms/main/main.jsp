<%-- 
    Document   : main
    Created on : Sep 23, 2017, 3:12:56 PM
    Author     : dwi.rangga
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../ffbv3/appGlobal/pageTop.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Main Menu</title>
        <style>
        div.container {
            width: 100%;
        }

        header, footer {
            padding: 1em;
            color: white;
            background-color: white;
            clear: left;
            text-align: left;
        }

        nav {
            float: left;
            max-width: 160px;
            margin: 0;
            padding: 1em;
        }

        nav ul {
            list-style-type: none;
            padding: 0;
        }

        nav ul a {
            text-decoration: none;
        }

        iframe {
            margin-left: 170px;
            border-left: 1px solid gray;
            padding: 1em;
            overflow: hidden;
            height: 500px;
            width: 800px;
        }
        
        p {
            color: black;
            text-align: left;
        }
        </style>
    </head>
    <body>   
        <script>
            /*
        window.onload  = function() {
            // /1 mnt
            setInterval(function(){ 
                // and th   e formula is:
                var random = Math.floor(Math.random() * 2) + 1;
                var $apiAddress = '../../api/progressTrackAPI/runAPIRouting';
                var jsonForServer = '{\"shift\": \"'+random+'\"}';
                var data = [];
                
                $.post( $apiAddress, {json : jsonForServer})
                    .done(function( data ) {
                          //if (data.success){
                              //alert('Success'+random);
                          //}
                          //else{
                              //alert('Error'+random);
                          //}
                    });
            }, 3000);
        };
        */
        </script>
        <div class="container">            
            <header>
                <%@include file="../../ffbv3/appGlobal/bodyTop.jsp"%> 
            </header>
            <p><font size="3">Menu</font></p>
            <nav>
                <ul>
                    <%--<li><a href="#"><p>Home</p></a></li>--%>
                    <li><a href="../routing/routing.jsp"><p>Run Process</p></a></li>
                    <li hidden="true"><a href="../progress/progressView.jsp"><p>View Run Process</p></a></li>
                </ul>
                <ul>
                    <li hidden="true"><a href="../task/TaskFilter.jsp"><p>View Task</p></a></li>
                </ul>
            </nav>
            <%--<iframe src="https://www.google.co.id" name="iframe1">
            </iframe>
            <footer><p>Copyright &copy; SMART Tbk</p></footer>--%>
            <%@include file="../../ffbv3/appGlobal/bodyBottom.jsp"%>    
        </div>
    </body>
</html>
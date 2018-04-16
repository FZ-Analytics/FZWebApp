<%-- 
    Document   : main2
    Created on : Oct 9, 2017, 10:21:17 PM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>IR</title>
        <style>
            @import url(http://fonts.googleapis.com/css?family=Lato:300,400,700);
            /* Starter CSS for Flyout Menu */
            #cssmenu,
            #cssmenu ul,
            #cssmenu ul li,
            #cssmenu ul ul {
                list-style: none;
                margin: 0;
                padding: 0;
                border: 0;
            }
            #cssmenu ul {
                position: relative;
                z-index: 597;
                float: left;
            }
            #cssmenu ul li {
                float: left;
                min-height: 1px;
                line-height: 1em;
                vertical-align: middle;
            }
            #cssmenu ul li.hover,
            #cssmenu ul li:hover {
                position: relative;
                z-index: 599;
                cursor: default;
            }
            #cssmenu ul ul {
                margin-top: 1px;
                visibility: hidden;
                position: absolute;
                top: 1px;
                left: 99%;
                z-index: 598;
                width: 100%;
            }
            #cssmenu ul ul li {
                float: none;
            }
            #cssmenu ul ul ul {
                top: 1px;
                left: 99%;
            }
            #cssmenu ul li:hover > ul {
                visibility: visible;
            }
            #cssmenu ul li {
                float: none;
            }
            #cssmenu ul ul li {
                font-weight: normal;
            }
            /* Custom CSS Styles */
            /* menu induk */
            #cssmenu {
                font-family: 'Lato', sans-serif;
                font-size: 12px;
                width: 100px;
                display:inline-block;
            }
            #cssmenu ul a,
            #cssmenu ul a:link,
            #cssmenu ul a:visited {
                display: block;
                color: #848889;
                text-decoration: none;
                font-weight: 300;
            }
            #cssmenu > ul {
                float: none;
            }
            #cssmenu ul {
                background: #fff;
            }
            #cssmenu > ul > li {
                border-left: 3px solid #d7d8da;
            }
            #cssmenu > ul > li > a {
                padding: 10px 20px;
            }
            #cssmenu > ul > li:hover {
                border-left: 3px solid #3dbd99;
            }
            #cssmenu ul li:hover > a {
                color: #3dbd99;
            }
            #cssmenu > ul > li:hover {
                background: #f6f6f6;
            }
            /* Sub Menu */
            #cssmenu ul ul a:link,
            #cssmenu ul ul a:visited {
                font-weight: 400;
                font-size: 12px;
            }
            #cssmenu ul ul {
                width: 180px;
                background: none;
                border-left: 20px solid transparent;
            }
            #cssmenu ul ul a {
                padding: 8px 0;
                border-bottom: 1px solid #eeeeee;
            }
            #cssmenu ul ul li {
                padding: 0 20px;
                background: #fff;
            }
            #cssmenu ul ul li:last-child {
                border-bottom: 3px solid #d7d8da;
                padding-bottom: 10px;
            }
            #cssmenu ul ul li:first-child {
                padding-top: 10px;
            }
            #cssmenu ul ul li:last-child > a {
                border-bottom: none;
            }
            #cssmenu ul ul li:first-child:after {
                content: '';
                display: block;
                width: 0;
                height: 0;
                position: absolute;
                left: -20px;
                top: 13px;
                border-left: 10px solid transparent;
                border-right: 10px solid #fff;
                border-bottom: 10px solid transparent;
                border-top: 10px solid transparent;
            }
        </style>
        <script>
            function opens(link) {
                $('#iframe1').attr('src', link);
            }
        </script>   
    </head>
    <body style="width: 100%!important; height: 100%!important;">
        <%@include file="../appGlobal/bodyTop.jsp"%>
        <img src="../img/ffbtoplogo.png">
        <%--<% if(!EmpyID.equalsIgnoreCase("null") && !url.contains("/logout.")){%>
            <br>
            <label clas="FzLabel"><%=UserName%>(<%=EmpyID%>) </label>            
            <a href='<%=urlLogout%>'>Logout</a>
            <br>
        <%}%>--%>
        <div style="width: 100%;height: 70%;">
            <div id='cssmenu' style="float: left;width: 10%;height: 100%;">
                <ul>
                    <li><a target="iframe1" onClick="opens('../other/home.jsp')"><span>Home</span></a></li>
                    <li><a target="iframe1" onClick="opens('../run/runEntry.jsp')"><span>Routing</span></a></li>
                        <%--<li><a href='#'><span>Param</span></a></li>--%>
                    <li class='active has-sub'><a ><span>Attribute</span></a>
                        <ul>
                            <li class='has-sub'><a target="iframe1" onClick="opens('../Params/Customer/CustomerAttr.jsp')"><span>Customer Attribute</span></a>
                                <%--<ul>
                                   <li><a href='#'><span>Sub Product</span></a></li>
                                   <li class='last'><a href='#'><span>Sub Product</span></a></li>
                                </ul>--%>
                            </li>
                            <li class='has-sub'><a target="iframe1" onClick="opens('../Params/Vehicle/VehicleAttr.jsp')"><span>Vehicle Attribute</span></a>
                                <%--<ul>
                                   <li><a href='#'><span>Sub Product</span></a></li>
                                   <li class='last'><a href='#'><span>Sub Product</span></a></li>
                                </ul>--%>
                            </li>
                            <%--<li class='has-sub'><a target="iframe1" onClick="opens('../Params/External/ForwadingAgentAttr.jsp')"><span>External Attribute</span></a></li>--%>
                            <li class='has-sub'><a target="iframe1" onClick="opens('../Params/Driver/DriverAttrAddView.jsp')"><span>Vendor Attribute</span></a></li>
                        </ul>
                    </li>
                    <li class='last'><a onClick="window.location.replace('<%=urlLogout%>')">Log Out</a></li>
                </ul>
            </div>
            <div id="countainer" style="float: left; width: 90%;height: 100%;">
                <%--<object data="../run/runEntry.jsp" style="width: 100%; height: 100%;position: absolute;"></object>--%>
                <%--<embed src = "../run/runEntry.jsp" style="width: 100%; height: 100%;position: absolute;" />--%>
                <%--position: absolute;--%>
                <iframe frameBorder="1" name="iframe1" id="iframe1" src="../other/home.jsp" style="width: 88%; height: 70%;position: absolute;"></iframe>
            </div>
        </div>        
        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

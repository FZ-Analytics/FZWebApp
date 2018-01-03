<%-- 
    Document   : dashProd1
    Created on : Oct 15, 2017, 10:20:13 AM
    Author     : Eko
--%>

<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONArray"%>
<%@page import="com.google.gson.Gson"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Production Per Block</title>
    </head>
    <body>
        <%@include file="../appGlobal/bodyTop.jsp"%>
<%--
        <%run(new com.fz.ffbv3.service.division.millLogic());%>
        <h3>Restan</h3>
        <form action="dashRestanRun.jsp">
            <% String sgrup = request.getAttribute("sgrup").toString(); %>
            <input type="hidden" id="sgrup" name="sgrup" value='<%=sgrup%>'>
        <div class="container" id="pnlMill" name="pnlMill">
            <label class="fzlabel">Mill</label>
            <select id="idMill" name="idMill">
                <%
                    String sMill = request.getAttribute("idMill").toString();
                    String sEst = request.getAttribute("idEstate").toString();
                    String sDiv = request.getAttribute("idDiv").toString();
                    String slct = (sMill.equals("<All>"))?"selected":"";
                %>
                <option value="<All>" <%=slct%>>--- All ---</option>
                <%
                    String s = request.getAttribute("oMill").toString();
                    JSONArray aj = new JSONArray(s);
                    for(int i=0; i<aj.length(); i++) {
                        JSONObject o = aj.getJSONObject(i);
                        s = o.getString("millID");
                        slct = (s.equals(sMill))?"selected":"";
                %>
                <option value="<%=s%>" <%=slct%>><%=s%></option>
                <%
                    }
                    s = "";
                %>
            </select>
        </div>
            
        <% if (sgrup.compareTo("1")>0) { %>
        <div class="container" id="pnlEstate" name="pnlEstate">
            <label class="fzlabel">Estate</label>
            <select id="idEstate" name="idEstate">
                <%
                    slct = (sEst.equals("<All>"))?"selected":"";
                %>
                <option value="<All>" <%=slct%>>--- All ---</option>
                <%
                    if (sgrup.compareTo("1")>0) {
                        s = request.getAttribute("oEst").toString();
                        aj = new JSONArray(s);
                        for (int i=0; i<aj.length(); i++) {
                            JSONObject o = aj.getJSONObject(i);
                            s = o.getString("estateID");
                            slct = (sEst.equals(s)) ? "selected" : "";
                %>
                <option value='<%=s%>' <%=slct%>><%=s%></option>
                <%
                        }
                    }
                %>
            </select>
        </div>

                <% if (sgrup.compareTo("2")>0) { %>
        <div class="container" id="pnlDiv" name="pnlDiv">
            <label class="fzlabel">Division</label>
            <select id="idDiv" name="idDiv">
                <% slct = (sDiv.equals("<All>"))?"selected":""; %>
                <option value="<All>" <%=slct%>>--- All ---</option>
                <%
                    if (sgrup.compareTo("2")>0) {
                        s = request.getAttribute("oDiv").toString();
                        aj = new JSONArray(s);
                        for (int i=0; i<aj.length(); i++) {
                            JSONObject o = aj.getJSONObject(i);
                            s = o.getString("divID");
                            slct = (sEst.equals(s)) ? "selected" : "";
                %>
                <option value='<%=s%>' <%=slct%>><%=s%></option>
                <%
                        }
                    }
                %>
            </select>
        </div>
                <%
                    }
                }
                %>
--%>								
            <br><br>
            <button class="btn fzButton" type="submit" 
                    name="submit" value="show">Show</button>
<%--
            <button class="btn fzButton" type="submit" 
                    name="submit" value="next">Next Filter</button>
        </form>
--%>
<%@include file="../appGlobal/bodyBottom.jsp"%>
        
    </body>
</html>

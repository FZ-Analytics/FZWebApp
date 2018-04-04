<%-- 
    Document   : popup
    Created on : Oct 9, 2017, 10:11:59 AM
    Author     : dwi.rangga
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../appGlobal/pageTop.jsp"%>
<%run(new com.fz.tms.params.PopUp.PopupDetilDo());%>
<%@page import="com.fz.tms.params.model.DODetil"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="../appGlobal/bodyTop.jsp"%>
        <br>
        <label class="fzLabel">Branch:</label> 
        <label class="fzLabel"><%=get("branch")%></label>        
        <br>
        <label class="fzLabel">Customer:</label> 
        <label class="fzLabel"><%=get("Name")%></label>

        <table class="table" border1="1" style="border-color: lightgray;">
            <tr style="background-color:orange">
                <th width="100px" class="fzCol">No</th>
                <th width="100px" class="fzCol">DO Number</th>
                <th width="100px" class="fzCol">Description</th>
                <th width="100px" class="fzCol">Kg</th>
                <th width="100px" class="fzCol">Qty</th>
                <th width="100px" class="fzCol">Send SAP</th>
                <%--<th width="100px" class="fzCol">Pck</th>--%>
            </tr>

            <%for (DODetil j : (List<DODetil>) getList("ListDODetil")) {%> 
            <tr>
                <td class="fzCell"><%=j.no%></td>
                <td class="fzCell"><%=j.DO_Number%></td>
                <td class="fzCell"><%=j.Product_Description%></td>
                <td class="fzCell"><%=j.Total_KG_Item%></td>
                <td class="fzCell"><%=j.DOQty%> <%=j.DOQtyUOM%></td>
                <th width="100px" class="fzCol"><%=j.sap%></th>
                <%--<td class="fzCell"><%=j.pck%></td>--%>
            </tr>

            <%} // for ProgressRecord %>
            <tr>
                <td class="fzCell">Total</td>
                <td class="fzCell"></td>
                <td class="fzCell"></td>
                <td class="fzCell"><%=get("total")%></td> 
                <td class="fzCell"></td>
                <%--<td class="fzCell"></td>--%>
            </tr>
        </table>
        <button class="btn fzButton" type="submit"
                onclick="openPage()">Exit</button>
        <script type="text/javascript">
            function openPage()
            {
                window.location.href = "jspClaose.jsp";
            }
        </script>
        <%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

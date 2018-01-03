<%-- 
    Document   : hvsEstmList
    Created on : Sep 23, 2017, 5:07:33 AM
--%>
<%@page import="com.fz.ffbv3.service.hvsEstm.HvsEstm"%>
<%@page import="com.fz.ffbv3.service.hvsEstm.HvsEstmDtl"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%run(new com.fz.ffbv3.service.hvsEstm.HvsEstmFrmLogic());%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Form</title>
    </head>
    <body>
  <%@include file="../appGlobal/bodyTop.jsp"%>
  <script>
  $( function() {
    var v = $( "#hvsDt" ).val();
    $( "#hvsDt" ).datepicker();
    $( "#hvsDt" ).datepicker( "option", "dateFormat", "yy-mm-dd");
    //$( "#hvsDt" ).val(yyyymmddDate(new Date()));
    $( "#hvsDt" ).val(v);
  } );
  </script>
        <h3>Estimation / Restan Form</h3>
            <div class="fzErrMsg" id="errMsg">
                <%=get("errMsg")%>
            </div>

            <br>
            <label class="fzLabel">Harvest Date</label>
            <input class="fzInput" id="hvsDt" 
                   name="hvsDt" value="<%=get("hvsDt")%>"/>
            
            <br><br>
            <label class="fzLabel">Estate + Division</label>
            <input class="fzInput" type="text" id="divID" 
                   name="divID" value="<%=get("divID")%>">

            <br>
            <label class="fzLabel"></label>
            <span class="fzLabelBottom">e.g. "BINE1"</span>
            
            <br><br>
            <label class="fzLabel">Remark / 1st empty bin location</label>
            <input class="fzInput" type="text" id="remark" 
                   name="remark" value="<%=get("remark")%>">

            <br><br>
            <label class="fzLabel">Status</label>
            <span class="fzInput" id="status"><%=get("status")%></span>

            <input type="hidden" id="hvsEstmID" 
                   name="hvsEstmID" value="<%=get("hvsEstmID")%>">
            
            <br><br>
            <div id="tbData" class="table-editable">
              <table class="table" border1="1">
                  <tr>
                      <th width="100px" class="fzCol">Type</th>
                      <th width="100px" class="fzCol">Block</th>
                      <th width="100px" class="fzCol">Kg</th>
                      <th></th>
                  </tr>
                  <%for (HvsEstmDtl hd : (List<HvsEstmDtl>) getList("hvsEstmDtlList")) { %>
                  
                    <tr>
                        <td class="fzCell celVal">
                          <select>
                              <%= makeOption(hd.taskType, "ESTM", "ESTM")%>
                              <%= makeOption(hd.taskType, "RSTN", "RSTN")%>
                          </select>
                        </td>
                        <td contenteditable="true" class="fzCell celVal"><%=hd.getBlock()%></td>
                        <td contenteditable="true" class="fzCell celVal"><%=hd.getSizeString()%></td>
                        <td class="fzCell">
                            <span class="table-up fzTextButton">Up</span>
                            <span class="table-down fzTextButton">Down</span>

                            <%if (!get("status").equals("FNAL")) { %>

                                <span class="table-remove ">Del</span>

                            <% } /* if get status */ %>

                        </td>
                    </tr>
                    
                  <% } /* end for HvsElmDtl */ %>
                        
                  <!-- clonable table line -->
                  <tr class="hide">
                      <td class="fzCell celVal">
                        <select>
                            <option selected>ESTM</option>
                            <option>RSTN</option>
                        </select>
                      </td>
                      <td contenteditable="true" class="fzCell celVal">A01</td>
                      <td contenteditable="true" class="fzCell celVal">10000</td>
                      <td class="fzCell">
                        <span class="table-up fzTextButton">Up</span>
                        <span class="table-down fzTextButton">Down</span>
                        <span class="table-remove fzTextButton">Del</span>
                      </td>
                  </tr>
              </table>
                  
              <%if (!get("status").equals("FNAL")) { %>
              
                    &nbsp;&nbsp;<span class="table-add fzTextButton">Add</span>
                    
              <% } /* if get status */ %>
              
            </div>
              
        <form id="form1" class="container" action="hvsEstmFrmProcess.jsp" 
              method="get">
            <input id="json" name="json" type="hidden">
        </form>
                  
        <br>
        <%if (!get("status").equals("FNAL")) { %>
        
            <button class="btn fzButton" type="button" 
                    name="saveDrftBtn" id="saveDrftBtn" value="save">Save Draft</button>
            <button class="btn fzButton" type="button" 
                    name="saveFnalBtn" id="saveFnalBtn" value="save">Save Final</button>
                    
        <% } /* if get status */ %>
        <div id="debug"></div>
        
      <script src='hvsEstmFrm.js?10'></script>
      
<%@include file="../appGlobal/bodyBottom.jsp"%>
    </body>
</html>

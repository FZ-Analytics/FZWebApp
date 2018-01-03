<%-- 
    Document   : test
    Created on : Sep 3, 2017, 2:24:55 PM
    Author     : Eri Fizal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html >
<head>
  <meta charset="UTF-8">
  <title>HTML5 Editable Table</title>
  
  
    <link rel='stylesheet prefetch' href='../tableref/jquery-ui.css'>
    <link rel='stylesheet prefetch' href='../tableref/bootstrap.min.css'>
    <link rel="stylesheet" href="../tableref/table.css">
    <link rel="stylesheet" href="../tableref/table2.css">
    <style>
        .etable {
            width:300px
        }
    </style>
  
</head>

<body>
    <img src="../img/ffbtoplogo3.png" width="350" height="90">
    <form id="form1">
    <p>Rencana Kerja
    <div class1="container">
      <div id="tbDemand" class="table-editable">
        <table class="table" border="1">
          <tr>
            <th width="100px">Block</th>
            <th width="100px">Kg</th>
            <th style="display:none;">Param</th>
            <th></th>
          </tr>
          <tr>
            <td contenteditable="true">N29</td>
            <td contenteditable="true">10000</td>
            <td style="display:none;">Hidden</td>
            <td>
              <span class="table-remove ">Del</span>
              <span class="table-up ">Up</span>
              <span class="table-down ">Down</span>
            </td>
          </tr>
          <!-- This is our clonable table line -->
          <tr class="hide">
            <td contenteditable="true">Isi Block</td>
            <td contenteditable="true">0</td>
            <td style="display:none;">Hidden</td>
            <td>
              <span class="table-remove ">Del</span>
              <span class="table-up ">Up</span>
              <span class="table-down ">Down</span>
            </td>
          </tr>
        </table>
        &nbsp;&nbsp;<span class="table-add">Add</span>
      </div>

    </div>
    <br><br>
    <br><br>
    <p id="export"></p>
    
    </form>
    <button id="export-btn" class="btn btn-primary">Export Data</button>
<!--script src='../tableref/jquery.min.js'></script>
<script src='../tableref/jquery-ui.min.js'></script>
<script src='../tableref/bootstrap.min.js'></script>
<script src='../tableref/underscore.js'></script-->
<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
<script src='http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/jquery-ui.min.js'></script>
<script src='https://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js'></script>
<script src='http://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.6.0/underscore.js'></script>

<script src='../tableref/table3.js'></script>

</body>
</html>

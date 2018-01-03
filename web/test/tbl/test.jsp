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
        <table class="table">
          <tr>
            <th width="100px">Block</th>
            <th width="100px">Kg</th>
            <th></th>
            <th></th>
          </tr>
          <tr>
            <td contenteditable="true">N29</td>
            <td contenteditable="true">10000</td>
            <td>
              <span class="table-remove ">[Del]</span>
              <span class="table-up ">[Up]</span>
              <span class="table-down ">[Down]</span>
            </td>
          </tr>
          <!-- This is our clonable table line -->
          <tr class="hide">
            <td contenteditable="true">[Isi Block]</td>
            <td contenteditable="true">0</td>
            <td>
              <span class="table-remove ">[Del]</span>
              <span class="table-up ">[Up]</span>
              <span class="table-down ">[Down]</span>
            </td>
          </tr>
        </table>
        &nbsp;&nbsp;<span class="table-add">[Add]</span>
      </div>

    </div>
    <br><br>
    <div class1="container">
      <div id="tbVehicle" class="table2-editable">
        <table class="table">
          <tr>
            <th>VehicleID</th>
            <th>Capacity</th>
            <th>Type</th>
            <th>Longitude Mulai</th>
            <th>Latitude Mulai</th>
            <th>DepartTime</th>
            <th></th>
          </tr>
          <tr>
            <td contenteditable="false">Truck1</td>
            <td contenteditable="false">10000</td>
            <td contenteditable="true">BinTruck</td>
            <td contenteditable="true">105.477</td>
            <td contenteditable="true">-1.9012</td>
            <td contenteditable="true">07:30</td>
            <td>
              <span class="table2-remove ">[Del]</span>
              <span class="table2-up ">[Up]</span>
              <span class="table2-down ">[Down]</span>
            </td>
          </tr>
          <!-- This is our clonable table2 line -->
          <tr class="hide">
            <td contenteditable="true">[Isi Truck]</td>
            <td contenteditable="true">10000</td>
            <td contenteditable="true">BinTruck</td>
            <td contenteditable="true">105.477</td>
            <td contenteditable="true">-1.9012</td>
            <td contenteditable="true">07:30</td>
            <td>
              <span class="table2-remove ">[Del]</span>
              <span class="table2-up ">[Up]</span>
              <span class="table2-down ">[Down]</span>
            </td>
          </tr>
        </table>
        &nbsp;&nbsp;<span class="table2-add">[Add]</span>
      </div>

    </div>
    <br><br>
    <button id="export-btn" class="btn btn-primary">Export Data</button>
    <p id="export"></p>
    
    </form>
<script src='../tableref/jquery.min.js'></script>
<script src='../tableref/jquery-ui.min.js'></script>
<script src='../tableref/bootstrap.min.js'></script>
<script src='../tableref/underscore.js'></script>

<script src='../tableref/table.js'></script>
<script src='../tableref/table2.js'></script>

</body>
</html>

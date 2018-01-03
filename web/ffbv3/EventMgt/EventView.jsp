<%-- 
    Document   : EventView
    Created on : Nov 28, 2017, 4:10:58 PM
    Author     : Agustinus Ignat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Untitled Page</title>
<meta name="generator" content="WYSIWYG Web Builder 12 - http://www.wysiwygwebbuilder.com">
<link href="base/jquery-ui.min.css" rel="stylesheet">
<link href="Event.css" rel="stylesheet">
<link href="index.css" rel="stylesheet">
<script src="jquery-1.12.4.min.js"></script>
<script src="jquery-ui.min.js"></script>
<script src="wwb12.min.js"></script>
<script>
$(document).ready(function()
{
   $("#cbKategori").change(function()
   {
      ShowObject('', 1);
   });
   $("#cbKategori").trigger('change');
   $("#cbKejadian").change(function()
   {
      ShowObject('', 1);
   });
   $("#cbKejadian").trigger('change');
   var jQueryDatePicker1Options =
   {
      dateFormat: 'dd/mm/yy',
      changeMonth: true,
      changeYear: true,
      showButtonPanel: false,
      showAnim: 'slideDown'
   };
   $("#jQueryDatePicker1").datepicker(jQueryDatePicker1Options);
   $("#jQueryDatePicker1").datepicker("setDate", "new Date()");
});
</script>
</head>
<body>
<select name="cbKategori" size="1" id="cbKategori" style="position:absolute;left:160px;top:13px;width:213px;height:28px;z-index:0;">
<option value="kategori_event">Kategori Event</option>
<option value="kendala_grabber">Kendala grabber</option>
<option value="kondisi_cuaca_lingkungan">Kondisi cuaca & lingkungan</option>
<option value="kendala_truk_bs">Kendala Truk BS</option>
<option value="kendala_jaringan_sistem">Kendala jaringan dan sistem</option>
</select>
<label for="" id="Label1" style="position:absolute;left:3px;top:13px;width:149px;height:20px;line-height:20px;z-index:1;">Kategori Event (Kejadian)</label>
<label for="" id="Label2" style="position:absolute;left:3px;top:50px;width:149px;height:20px;line-height:20px;z-index:2;">Kejadian</label>
<select name="cbKejadian" size="1" id="cbKejadian" style="position:absolute;left:160px;top:50px;width:213px;height:28px;z-index:3;">
<option value="kejadian">Kejadian</option>
<option value="Kerusakan_crane_grabber">Kerusakan crane grabber</option>
<option value="Kerusakan_bagian_tractor">Kerusakan di bagian tractor</option>
<option value="Kendala_operator">Kendala di operator</option>
<option value="Lain_lain">Lain-lain</option>
</select>
<input type="text" id="jQueryDatePicker1" style="position:absolute;left:160px;top:88px;width:209px;height:18px;line-height:18px;z-index:4;" name="qdtpEvent" value="28/11/2017" spellcheck="false">
<label for="" id="Label3" style="position:absolute;left:3px;top:85px;width:149px;height:20px;line-height:20px;z-index:5;">Waktu Mulai</label>
</body>
</html>
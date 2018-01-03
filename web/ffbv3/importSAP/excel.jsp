
<%@ page import="org.apache.poi.hssf.usermodel.HSSFSheet"%>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFWorkbook"%>
<%@page import=" org.apache.poi.hssf.usermodel.HSSFRow"%>
<%@page import=" org.apache.poi.hssf.usermodel.HSSFCell"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%
    try{
        HSSFWorkbook hwb = new HSSFWorkbook();
        HSSFSheet sheet1 = hwb.createSheet("Sheet1");
        HSSFSheet sheet2 = hwb.createSheet("Sheet2");
        HSSFRow rowhead = sheet1.createRow((short)0);
        rowhead.createCell((short) 0).setCellValue("Roll No");
        rowhead.createCell((short) 1).setCellValue("Name");
        rowhead.createCell((short) 2).setCellValue("Marks");
        rowhead.createCell((short) 3).setCellValue("Grade");

        HSSFRow row1 = sheet1.createRow((short)1);
        row1.createCell((short)0).setCellValue("1");
        row1.createCell((short)1).setCellValue("AAAA");
        row1.createCell((short)2).setCellValue("90");
        row1.createCell((short)3).setCellValue("A");

        HSSFRow row2 = sheet1.createRow((short)2);
        row2.createCell((short) 0).setCellValue("2");
        row2.createCell((short) 1).setCellValue("BBBB");
        row2.createCell((short) 2).setCellValue("70");
        row2.createCell((short) 3).setCellValue("B");

        HSSFRow rowhead1 = sheet2.createRow((short)0);
        rowhead1.createCell((short) 0).setCellValue("Class");
        rowhead1.createCell((short) 1).setCellValue("Age");
        rowhead1.createCell((short) 2).setCellValue("Address");
        rowhead1.createCell((short) 3).setCellValue("Phone Number");

        HSSFRow row3 = sheet2.createRow((short)1);
        row3.createCell((short)0).setCellValue("5");
        row3.createCell((short)1).setCellValue("10");
        row3.createCell((short)2).setCellValue("Rohini,Delhi");
        row3.createCell((short)3).setCellValue("1111111111");

        HSSFRow row4 = sheet2.createRow((short)2);
        row4.createCell((short) 0).setCellValue("6");
        row4.createCell((short) 1).setCellValue("11");
        row4.createCell((short) 2).setCellValue("Vasant kunj,Delhi");
        row4.createCell((short) 3).setCellValue("2222222222");
        sheet1.setSelected(true);

        sheet2.setSelected(false);

        FileOutputStream fileOut = new FileOutputStream("d:\\docs\\selectingSheet.xls");
        hwb.write(fileOut);
        fileOut.close();
        out.println("Your excel file has been generated.");
    } catch ( Exception ex ) {
        out.println(ex);
    }
%>
<br/>
<a href="ReadExcel.jsp">Read And Edit Excel File</a> 
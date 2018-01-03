<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import=" org.apache.poi.hssf.usermodel.HSSFSheet"%>
<%@page import=" org.apache.poi.hssf.usermodel.HSSFWorkbook"%>
<%@page import=" org.apache.poi.hssf.usermodel.HSSFRow"%>
<%@page import=" org.apache.poi.hssf.usermodel.HSSFCell"%>
<form >
    <%
        short a=0;
        short b=1;
        short c=2;
        short d=3;
        int i=0;
        String filename = "d:\\docs\\datasap\\" + request.getParameter("xfile") ; 
        //"d:\\docs\\datasap\\Angkut_Desember.xls";
        if (filename != null && !filename.equals("")) {
            try{
                FileInputStream fs =new FileInputStream(filename);
                HSSFWorkbook wb = new HSSFWorkbook(fs);
                for (int k = 0; k < wb.getNumberOfSheets(); k++){
                    int j=i+1;
    %>
    Sheet <%=j%>
    <%
                    HSSFSheet sheet = wb.getSheetAt(k);
                    int rows = sheet.getPhysicalNumberOfRows();
                    for (int r = 1; r < rows; r++){
                        HSSFRow row = sheet.getRow(r);
                        if (row != null) {
                            int cells = row.getPhysicalNumberOfCells(); 
    %>
    <br>
    <%
                            HSSFCell cell1 = row.getCell(a);
                            if (cell1 != null){
                            String value = null;
                                switch (cell1.getCellType()){
                                    case HSSFCell.CELL_TYPE_FORMULA :
                                        value = "FORMULA ";
                                        break;
                                    case HSSFCell.CELL_TYPE_NUMERIC :
                                        value = ""+cell1.getNumericCellValue();
                                        break;
                                    case HSSFCell.CELL_TYPE_STRING :
                                        value = cell1.getStringCellValue();
                                        break;
                                }
    %>
    <input type="text" name="roll" value="<%=value%>">
    <%
                            }
                            HSSFCell cell2 = row.getCell(b);
                            if (cell2 != null){
                                String value = null;
                                switch (cell2.getCellType()){
                                    case HSSFCell.CELL_TYPE_FORMULA :
                                        value = "FORMULA ";
                                        break;
                                    case HSSFCell.CELL_TYPE_NUMERIC :
                                        value = ""+cell2.getNumericCellValue();
                                        break;
                                    case HSSFCell.CELL_TYPE_STRING :
                                        value = cell2.getStringCellValue();
                                        break;
                                }
    %>
    <input type="text" name="name" value="<%=value%>">
    <%
                            }
                            HSSFCell cell3 = row.getCell(c);
                            if (cell3 != null){
                                String value = null;
                                switch (cell3.getCellType()){
                                    case HSSFCell.CELL_TYPE_FORMULA :
                                        value = "FORMULA ";
                                        break;
                                    case HSSFCell.CELL_TYPE_NUMERIC :
                                        value = ""+cell3.getNumericCellValue();
                                        break;
                                    case HSSFCell.CELL_TYPE_STRING :
                                        value = cell3.getStringCellValue();
                                        break;
                                } 
    %>
    <input type="text" name="marks" value="<%=value%>">
    <%
                            }
                            HSSFCell cell4 = row.getCell(d);
                            if (cell4 != null){
                                String value = null;
                                switch (cell4.getCellType()){
                                    case HSSFCell.CELL_TYPE_FORMULA :
                                        value = "FORMULA ";
                                        break;
                                    case HSSFCell.CELL_TYPE_NUMERIC :
                                        value = ""+cell4.getNumericCellValue();
                                        break;
                                    case HSSFCell.CELL_TYPE_STRING :
                                        value = cell4.getStringCellValue();
                                        break;
                                }
    %> <input type="text" name="grade" value="<%=value%>">
    <%
                            }
                        }
    %>
    <input type="submit" value="Edit">
    <%              }%>
    <br><br><br><br>
    <%
                    i++; 
                }
            } catch (Exception ex){
                out.println("Catch Error : " + ex.getMessage());
            } 
        }
    %>
</form>
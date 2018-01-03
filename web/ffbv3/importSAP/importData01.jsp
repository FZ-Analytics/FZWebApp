<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Statement"%>
<%@page import="com.fz.generic.Db"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import=" org.apache.poi.hssf.usermodel.HSSFSheet"%>
<%@page import=" org.apache.poi.hssf.usermodel.HSSFWorkbook"%>
<%@page import=" org.apache.poi.hssf.usermodel.HSSFRow"%>
<%@page import=" org.apache.poi.hssf.usermodel.HSSFCell"%>
<form >
    <%
        int i=0;
        String filename = "d:\\docs\\datasap\\Angkut_Desember.xls";
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
                    String cname;
                    HSSFCell cell1;
                    String divID ;
                    String sql = "insert into fbtransport select ?,?,?,?,?,?,?,?,?,?,?";
                    int rows = sheet.getPhysicalNumberOfRows();
                    try (Connection con = (new Db()).getConnection("jdbc/fz")) {
                        try (Statement stm = con.createStatement()) {
                            stm.execute("delete from fbtransport");
                        } catch (Exception e) { 
                            String err = e.getMessage();
                        }
                        try (PreparedStatement ps = con.prepareStatement(sql)) {
                            String stmp;
                            for (int r = 2; r < rows; r++){
                                HSSFRow row = sheet.getRow(r);
                                if (row != null) {
                                    try { 
                                    ps.clearParameters();
                                    int cells = row.getPhysicalNumberOfCells();
                                    divID = row.getCell(24).getStringCellValue().replace(" ","");
                                    stmp = row.getCell(14).getStringCellValue();
    %>
    <br>
    <input type="text" name="row" value="<%=r%>">
    <input type="text" name="QtyKrm" value="<%=row.getCell(17).getStringCellValue()%>">
    <input type="text" name="QtTrm" value="<%=row.getCell(18).getStringCellValue()%>">
    <input type="text" name="Wkt" value="<%=row.getCell(22).getDateCellValue() %>">
    <input type="text" name="Tgl" value="<%=row.getCell(23).getStringCellValue()%>">
    <input type="text" name="VID" value="<%=row.getCell(14).getStringCellValue().replace("-","")%>">
    <input type="text" name="jns" value="<%=row.getCell(15).getStringCellValue()%>">
    <input type="text" name="estKd" value="<%=divID%>">
    <%
                                    ps.setString(1, divID.substring(0,4));
                                    stmp = row.getCell(17).getStringCellValue();
                                    stmp = stmp.replace(".", "");
                                    stmp = stmp.replace(",", ".");
                                    ps.setDouble(2, Double.parseDouble(stmp));
                                    stmp = row.getCell(18).getStringCellValue();
                                    stmp = stmp.replace(".", "");
                                    stmp = stmp.replace(",", ".");
                                    ps.setDouble(3, Double.parseDouble(stmp));
                                    ps.setString(4, row.getCell(19).getStringCellValue());
                                    Date d = row.getCell(22).getDateCellValue();
                                    ps.setTime(5, new java.sql.Time(d.getTime()));
                                    d = (new SimpleDateFormat("dd.MM.yyyy")).parse(row.getCell(23).getStringCellValue());
                                    ps.setDate(6, java.sql.Date.valueOf((new SimpleDateFormat("yyyy-MM-dd")).format(d)));
                                    Date d2 = (new SimpleDateFormat("yyyy-MM-dd")).parse("2017-11-25");
                                    ps.setString(7, ((d.compareTo(d2)<=0)?"Before":"After"));
                                    ps.setInt(8, 0);
                                    ps.setString(9,row.getCell(14).getStringCellValue().replace("-",""));
                                    ps.setString(10, row.getCell(15).getStringCellValue());
                                    ps.setString(11, divID);
                                    
                                    //ps.addBatch();
                                    ps.executeUpdate();
                                    } catch (Exception e) {
                                        String err = e.getMessage();
                                    }
                                }
                            }
                            ps.executeBatch();
                        } catch (Exception e) { 
                            String err = e.getMessage();
                        }
                    } catch ( Exception e) { 
                        String err = e.getMessage();
                    }
    %>
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
<%!
    String cetaksel(HSSFRow row, int n) {
        String value = null;
        HSSFCell cell1 = row.getCell(n);
        if (cell1 != null){
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
        }
        return value;
    }
%>

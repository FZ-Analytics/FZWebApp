<%-- 
    Document   : q
    Created on : Oct 16, 2017, 9:21:02 AM
--%>
<%@page import="com.fz.util.FZUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Q</title>
    </head>
    <body>
        <%!
            String process(HttpServletRequest request) {
                String o = "";
                try {

                    String q = request.getParameter("q");
                    if (q == null) return "";

                    String lq = q.toLowerCase();
                    if (lq.contains("insert")) return "Error i";
                    if (lq.contains("update")) return "Error u";
                    if (lq.contains("delete")) return "Error de";
                    if (lq.contains("drop")) return "Error dr";
                    if (lq.contains("alter")) return "Error a";
                    if (lq.contains("exec")) return "Error e";
                    if (lq.contains("truncate")) return "Error t";

                    // connect
                    javax.naming.InitialContext ctx = 
                            new javax.naming.InitialContext();
                    javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup(
                            "java:/comp/env/" + "jdbc/fz");
                    
                    try (java.sql.Connection con = ds.getConnection()){
                        
                        // query
                        String sql = "select " + q + " limit 200";
                        try (java.sql.PreparedStatement ps = con.prepareStatement(sql);
                                java.sql.ResultSet rs = ps.executeQuery()){
                            
                            // print heading
                            java.sql.ResultSetMetaData rsm = rs.getMetaData();
                            int colCount = rsm.getColumnCount();
                            
                            o += "\n<table border=1><tr>";
                            for (int i = 1 ; i<=colCount; i++){
                                
                                o += "\n<th>" + rsm.getColumnName(i) + "</th>";
                                
                            }
                            o += "\n</tr>";
                            
                            // print body
                            while (rs.next()){
                                o += "\n<tr>";
                                for (int i=1; i <= colCount; i++){
                                    o += "\n<td>" + rs.getString(i) + "</td>";
                                }
                                o += "\n</tr>";
                                
                            }
                            o += "\n</table>";
                        }
                    }

                } catch(Exception e){
                    
                    return FZUtil.toStackTraceText(e);
                }
                
                return o;
            }
        %>
        <form action="">
            <textarea name="q" rows="5" cols="100"><%=request.getParameter("q")%></textarea>
            <br><button type="submit">Go</button>
            <br>
            <br>
            <div id="qResult">
                <%=process(request)%>
            </div>
        </form>
    </body>
</html>

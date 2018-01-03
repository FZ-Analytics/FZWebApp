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
                String o = "OK";
                try {

                    String q = request.getParameter("q");
                    String k = request.getParameter("k");
                    if (q == null) return "";
                    if (k == null) return "";

                    String lq = q.toLowerCase();
                    if (lq.contains("insert")) return "Error i";
                    if (lq.contains("update")) return "Error u";
                    if (lq.contains("delete")) return "Error de";
                    if (lq.contains("drop")) return "Error dr";
                    if (lq.contains("alter")) return "Error a";
                    if (lq.contains("exec")) return "Error a";
                    if (lq.contains("truncate")) return "Error t";
                    
                    // check key
                    String dg = k.substring(1,2);
                    int idg = Integer.parseInt(dg);
                    String[] ch = new String[4];
                    if (idg % 2 == 0){
                        ch[0] = q.substring(9,10);
                        ch[1] = q.substring(7,8);
                        ch[2] = q.substring(5,6);
                        ch[3] = q.substring(3,4);
                    }
                    else{
                        ch[0] = q.substring(8,9);
                        ch[1] = q.substring(6,7);
                        ch[2] = q.substring(4,5);
                        ch[3] = q.substring(2,3);
                    }
                    if (
                            ch[0].equals(k.substring(2,3))
                            && ch[1].equals(k.substring(4,5))
                            && ch[2].equals(k.substring(6,7))
                            && ch[3].equals(k.substring(8,9))
                            ){}
                    else return "Error k";
                        
                    String fq = "";
                    if (q.startsWith("n")){
                        fq = "insert into " + q.substring(1, q.length());
                    }
                    else if (q.startsWith("p")){
                        fq = "update " + q.substring(1, q.length());
                    }
                    else if (q.startsWith("e")){
                        fq = "delete from " + q.substring(1, q.length());
                    }
                    
                    // connect
                    javax.naming.InitialContext ctx = 
                            new javax.naming.InitialContext();
                    javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup(
                            "java:/comp/env/" + "jdbc/fz");
                    
                    try (java.sql.Connection con = ds.getConnection()){
                        
                        // query
                        String sql = fq;
                        try (java.sql.PreparedStatement ps = con.prepareStatement(sql)){
                            ps.executeUpdate();
                            //o = fq;
                        }
                    }

                    return o;
                    
                } catch(Exception e){
                    
                    return FZUtil.toStackTraceText(e);
                }
                
            }
        %>
        <form action="" method='post'>
            <textarea name="q" rows="5" cols="100"><%=request.getParameter("q")%></textarea>
            <br><br><input name="k" type='text'>
            <br><button type="submit">Go</button>
            <br>
            <br>
            <div id="qResult">
                <%=process(request)%>
            </div>
        </form>
    </body>
</html>

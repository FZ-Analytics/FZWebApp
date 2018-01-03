
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.fz.util.FZUtil"%>
<%@page import="com.fz.util.EObject"%>
<%
    // connect
    javax.naming.InitialContext ctx = 
            new javax.naming.InitialContext();
    javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup(
            "java:/comp/env/" + "jdbc/fz");
    String sql = "select locationID, longitude, latitude, name "
            + " from fbGpsGrabberSim order by locationID"
            + " ";
    try (java.sql.Connection con = ds.getConnection();
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            java.sql.ResultSet rs = ps.executeQuery()
            ){
        List<EObject> os = new ArrayList<EObject>();
        while (rs.next()){
            EObject o = new EObject();
            o.putInt("loc", FZUtil.getRsInt(rs, 1, 0));
            o.putDbl("lon", FZUtil.getRsDouble(rs, 2, 0));
            o.putDbl("lat", FZUtil.getRsDouble(rs, 3, 0));
            o.putStr("name", FZUtil.getRsString(rs, 4, ""));
            os.add(o);
        }
        int n = os.size();
        int i = 0;
        for (EObject o : os){
            i++;
            if (i<n-2){
                EObject nextO = os.get(i+1);
                int rand = FZUtil.randBetween(0, 1);
                if ((rand == 1) && (o.getStr("name").equals(nextO.getStr("name")))){
                    String sql2 = 
                            "update fbGpsGrabberSim "
                            + "set "
                            + " longitude=" + nextO.getDbl("lon")
                            + ", latitude=" + nextO.getDbl("lat")
                            + " where locationID = " + o.getInt("loc")
                            ;
                    try (java.sql.PreparedStatement ps2 = con.prepareStatement(sql2)){
                        ps2.executeUpdate();
                    }
                }
            }
        }
    }
    out.println("OK");
%>                    

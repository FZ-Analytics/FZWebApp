<%@page import="javax.servlet.jsp.JspWriter"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.fz.generic.Db"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="org.json.JSONArray"%>

<%!
    public void createGraph(JspWriter out, String lmnid, String[][] ax) { 
//    String tipe, String sql, String xfld, String yfld, String gfld){
//        JSONObject oresult = grpQuery(sql, xfld, yfld, gfld);
        JSONObject oresult;
        JSONObject oc = new JSONObject();
        JSONObject opt = new JSONObject();
        JSONArray ac = new JSONArray();
        JSONObject oTmp;

        try { 
                oc = new JSONObject();
                opt.put("draggable", true);
                JSONObject scales = new JSONObject();
                JSONArray Axes = new JSONArray();
oTmp = new JSONObject();
oTmp.put("display", false);
opt.put("legend", oTmp);
oTmp = new JSONObject();
oTmp.put("display",true);
oTmp.put("text", lmnid);
opt.put("title", oTmp);
                boolean isStacked;
                JSONObject stacked;
                String tipe;
                int i = 0;
                JSONArray adt = new JSONArray();
                boolean fill = true;

                for(String[] as : ax) {
                    isStacked = false;
                    if (as[0]=="stackedbar") {
                        tipe = "bar" ;
                        isStacked = true ;
                    } else  tipe = as[0];
                    if (as[0]=="line") {
                        JSONObject xLine = new JSONObject();
                        xLine.put("fill", false);
                        JSONObject lmn = new JSONObject();
                        lmn.put("line", xLine);
                        opt.put("elements", lmn);
                    }

                    oc.put("type","bar");
                    oresult = grpQuery2(as[1], tipe, as[2], as[3], as[4]);
                    stacked = new JSONObject();
                    stacked.put("stacked",isStacked);
                    Axes.put(stacked);
                    if(i==0) {
                        oc.put("data",oresult); 
                    }
                    else {
                        adt = oc.getJSONObject("data").getJSONArray("datasets");
                        JSONArray act = oresult.getJSONArray("datasets");
                        for(int n=0;n<act.length(); n++) {
                            JSONObject odt = act.getJSONObject(n);
                            adt.put(odt);
                        }
                    }

                    ac.put(oresult.get("datasets"));
                    i++;
                }

                stacked = new JSONObject();
                stacked.put("xAxes",Axes);
                stacked.put("yAxes",Axes);
                opt.put("scales",stacked);
                oc.put("options", opt);
//            out.println("<div class='containerxx'> \n");
            out.println("<canvas id='" + lmnid + "' draggable='true' ondragstart='drag(event)' ></canvas>\n");
//            out.println("</div> \n");
            out.println("<script>\n");
            out.println("var ctx" + lmnid + " = document.getElementById('" + lmnid +"').getContext('2d');\n");
            out.println("var " + lmnid + " = new Chart(ctx" + lmnid + ", " + oc.toString() + "); \n");
            out.println("</script>\n");
//            out.println("<br/><br/>");
 //           out.println(oc.toString());
//            out.println("<br/><br/>"+opt.toString());
            
        } catch (Exception e) { 
            String err = e.getMessage();
            
        }
    }

    public void outprint(String teks, HttpServlet s ) { 
        JspWriter out ;
        
    }

    public JSONObject grpQuery(String sql, String xfld, String yfld, String gfld) {
        JSONObject result = new JSONObject();
        try { 
            //String sql = obj.getString(sql);
            try (Connection con = (new Db()).getConnection("jdbc/fz");) {
                try (Statement stm = con.createStatement()) {
                    ResultSet rs = stm.executeQuery(sql);
                    result = rs2Json(rs,xfld,yfld,gfld);
                } catch (Exception e) { 
                    String s = e.getMessage();
                }
            } catch ( Exception e) { 
                String s = e.getMessage();
            }
        } catch (Exception e) { 
            String s = e.getMessage();
        }
        return result;
    }



    public static JSONObject rs2Json(ResultSet rs, String xfld, String yfld, String gfld) throws Exception { 
        JSONObject result = new JSONObject();
        if (rs!=null) {
              String[] bgColor = {
                     "rgba(23, 12, 254, 0.8)",
                     "rgba(0, 128, 255, 0.8)",
                     "rgba(26, 167, 1, 0.8)",
                     "rgba(160, 7, 34, 0.8)",
                     "rgba(128, 128, 128, 0.8)",
                     "rgba(255, 128, 0, 0.8)",
                     "rgba(64, 0, 64, 0.8)"
                };
              int nclr = 0;

            JSONObject odtl;
            JSONArray a = new JSONArray();
            JSONArray ds = new JSONArray();
            List<String> xlbls = new ArrayList<String>();
            List<String> glbls = new ArrayList<String>();
            String xlbl;
            String glbl;
            while (rs.next()) { 
                xlbl = rs.getString(xfld);
                glbl = rs.getString(gfld);
                if (!xlbls.contains(xlbl)) xlbls.add(xlbl);
                if (!glbls.contains(glbl)) glbls.add(glbl);
                odtl = new JSONObject();
                odtl.put("xlabel", rs.getString(xfld));
                odtl.put("value", rs.getInt(yfld));
                odtl.put("glabel",rs.getString(gfld));
                a.put(odtl);
            }
            
            Collections.sort(xlbls);
            glbl = "";
            JSONObject o = null;
            int iper = 0;
            ArrayList<Integer> tons = new ArrayList<Integer>();
            boolean lnjt;
            
            for (int i = 0; i<a.length(); i++) {
                odtl = a.getJSONObject(i);
                if (!glbl.equals(odtl.getString("glabel"))) {
                    glbl = odtl.getString("glabel");
                    o = new JSONObject();
                    o.put("label", glbl);
                    o.put("backgroundColor",bgColor[nclr]);
                    nclr++;
                    if (nclr>=bgColor.length) nclr = 0 ;
                    iper = 0;
                    tons = new ArrayList<Integer>();
                }
                xlbl = odtl.getString("xlabel");
                lnjt = (iper<xlbls.size()); 


                while (lnjt == true) {
                        if (xlbl.equals(xlbls.get(iper))) {
                            tons.add(odtl.getInt("value"));
                            lnjt = false;
                        } else tons.add(0);
                        iper++;
                        if (iper>=xlbls.size()) lnjt = false;
                }
                    if (iper>=xlbls.size()) {
                        lnjt = false;
                        o.put("data",tons);
                        ds.put(o);
                    } 
            }
            result.put("labels", xlbls);
            result.put("datasets", ds);
        }
        return result;
    }

    public JSONObject grpQuery2(String sql, String tipe, String xfld, String yfld, String gfld) {
        JSONObject result = new JSONObject();
        try { 
            //String sql = obj.getString(sql);
            try (Connection con = (new Db()).getConnection("jdbc/fz");) {
                try (Statement stm = con.createStatement()) {
                    ResultSet rs = stm.executeQuery(sql);
                    result = rs2Json2(rs,tipe,xfld,yfld,gfld);
                } catch (Exception e) { 
                    String s = e.getMessage();
                }
            } catch ( Exception e) { 
                String s = e.getMessage();
            }
        } catch (Exception e) { 
            String s = e.getMessage();
        }
        return result;
    }

    public static JSONObject rs2Json2(ResultSet rs, String tipe, String xfld, String yfld, String gfld) throws Exception { 
        JSONObject result = new JSONObject();
        if (rs!=null) {
              String[] bgColor = {
                     "rgba(23, 12, 254, 0.8)",
                     "rgba(0, 128, 255, 0.8)",
                     "rgba(26, 167, 1, 0.8)",
                     "rgba(160, 7, 34, 0.8)",
                     "rgba(128, 128, 128, 0.8)",
                     "rgba(255, 128, 0, 0.8)",
                     "rgba(64, 0, 64, 0.8)"
                };
              int nclr = 0;

            JSONObject odtl;
            JSONArray a = new JSONArray();
            JSONArray ds = new JSONArray();
            List<String> xlbls = new ArrayList<String>();
            List<String> glbls = new ArrayList<String>();
            String xlbl;
            String glbl;
            while (rs.next()) { 
                xlbl = rs.getString(xfld);
                glbl = rs.getString(gfld);
                if (!xlbls.contains(xlbl)) xlbls.add(xlbl);
                if (!glbls.contains(glbl)) glbls.add(glbl);
                odtl = new JSONObject();
                odtl.put("xlabel", rs.getString(xfld));
                odtl.put("value", rs.getInt(yfld));
                odtl.put("glabel",rs.getString(gfld));
                a.put(odtl);
            }
            
            Collections.sort(xlbls);
            glbl = "";
            JSONObject o = null;
            int iper = 0;
            ArrayList<Integer> tons = new ArrayList<Integer>();
            boolean lnjt;
            
            for (int i = 0; i<a.length(); i++) {
                odtl = a.getJSONObject(i);
                if (!glbl.equals(odtl.getString("glabel"))) {
                    glbl = odtl.getString("glabel");
                    o = new JSONObject();
                    o.put("label", glbl);
                    o.put("backgroundColor",bgColor[nclr]);
                    nclr++;
                    if (nclr>=bgColor.length) nclr = 0 ;
                    iper = 0;
                    tons = new ArrayList<Integer>();
                }
                xlbl = odtl.getString("xlabel");
                lnjt = (iper<xlbls.size()); 


                while (lnjt == true) {
                        if (xlbl.equals(xlbls.get(iper))) {
                            tons.add(odtl.getInt("value"));
                            lnjt = false;
                        } else tons.add(0);
                        iper++;
                        if (iper>=xlbls.size()) lnjt = false;
                }
                    if (iper>=xlbls.size()) {
                        lnjt = false;
                        o.put("data",tons);
                        o.put("type",tipe);
                        ds.put(o);
                    } 
            }
            result.put("labels", xlbls);
            result.put("datasets", ds);
        }
        return result;
    }
%>
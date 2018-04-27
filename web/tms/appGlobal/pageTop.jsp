<%@page import="com.fz.generic.PageTopUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%!
    PageContext pc;
    
    String get(String paramName){
        return PageTopUtils.get(paramName, pc);
    }
    
    Object getList(String paramName){
        return PageTopUtils.getList(paramName, pc);
    }
    
    Object getObj(String paramName){
        return PageTopUtils.getList(paramName, pc);
    }
    
    String makeOption(String param, String value, String text){
        return PageTopUtils.makeOption(param, value, text);
    }
    
    public void run(com.fz.generic.BusinessLogic logic) throws Exception {
        PageTopUtils.run(logic, pc);
    }

//    public void runAPI(com.fz.generic.BusinessLogic logic) throws Exception {
//        PageTopUtils.runAPI(logic, pc);
//    }
//
%>
<%
    pc = pageContext;
    PageTopUtils.checkLogin(pc);
    
    String EmpyID = (String) pc.getSession().getAttribute("EmpyID") == null ? 
            "null" : (String) pc.getSession().getAttribute("EmpyID");
    String UserName = (String) pc.getSession().getAttribute("UserName") == null ?
            "null" : (String) pc.getSession().getAttribute("UserName");
    String UserID = (String) pc.getSession().getAttribute("UserID") == null ? 
            "null" : (String) pc.getSession().getAttribute("UserID");
    String WorkplaceID = (String) pc.getSession().getAttribute("WorkplaceID") == null ?
            "null" : (String) pc.getSession().getAttribute("WorkplaceID");
    String Key = (String) pc.getSession().getAttribute("Key") == null ?
            "null" : (String) pc.getSession().getAttribute("Key");
    
    //System.out.println(EmpyID+"()"+UserName+"()"+UserID+"()"+WorkplaceID);
    String url = request.getRequestURL().toString();
    //System.out.println(url+"()");
    
    String urlLogout = "";
    if (url.contains("/run/") || url.contains("/main2/main.jsp")) {
        urlLogout = "../usrMgt/logout.jsp";
    }if (url.contains("/Params/")) {
        urlLogout = "../../usrMgt/logout.jsp";
    }
%>
<%--<% if(!EmpyID.equalsIgnoreCase("null") && !url.contains("/logout.")){%>
    <br>
    <label clas="FzLabel">Wellcome : <%=UserName%>(<%=EmpyID%>) </label>            
    <a href='<%=urlLogout%>'>Logout</a>
    <br>
<%}%>--%>

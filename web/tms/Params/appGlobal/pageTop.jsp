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
%>

<%@page import="com.fz.ffbv3.service.usermgt.RightsFormatter"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="com.fz.generic.Db"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.fz.util.FZUtil"%>
<%@include file="../appGlobal/pageTop.jsp"%>
<%!
    public void run(
            HttpServletRequest request
            , HttpServletResponse response
            , PageContext pc
    ) throws Exception {

        String result = "";
        
        // get user id and password from http param
        String login = FZUtil.getHttpParam(request, "userID");
        String password = FZUtil.getHttpParam(request, "password");

        // TODO handle empty login
        
        String sql = "";
        
        // get db con from pool
        try (Connection con = (new Db()).getConnection("jdbc/fz")){
            
            try (Statement stm = con.createStatement()){
            
                // create sql
                sql = "select gbUserID, rights "
                        + " from gbUser"
                        + " where userName ='" + login + "'"
                        + " and password ='" + password + "'"
                        // TODO: hash the password in prod
                        //+ " and password ='" + password.hashCode() + "'"
                        ;
                
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    
                    // if record exist
                    if (rs.next()){

                        // format rights string;
                        String rights = 
                                RightsFormatter.format(
                                    FZUtil.getRsString(rs, 2, "")
                                );
                        
                        // keep user profile as session object, for next views
                        request.getSession()
                                .setAttribute("login", login);
                        request.getSession()
                                .setAttribute("userID"
                                        , FZUtil.getRsString(rs, 1, ""));
                        request.getSession()
                                .setAttribute("rights", rights);
                        
                        // remove password from request
                        // so not carried all the time
                        request.removeAttribute("password");
                        
                        // go to welcome page
                        request
                                .getRequestDispatcher("../main2/main2.jsp")
                                .forward(pc.getRequest(), pc.getResponse());
                    }
                    else {

                        // keep login msg to display in login page
                        request
                                .setAttribute("loginResult"
                                        , "Invalid user name or password ..");
                        
                        // forward to login page to display loginResult
                        request
                                .getRequestDispatcher("/FZWeb/ffbv3/usrMgt/login.jsp")
                                .forward(pc.getRequest(), pc.getResponse());
                    }
                }
            }
        }
        catch (Exception e){
            
            // re-throw the exception
            throw e;
            //new Exception("Error login. SQL = " + sql, e);
        }

    }

%>
<%run(request, response, pageContext);%>
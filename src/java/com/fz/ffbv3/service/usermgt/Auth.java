/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fz.ffbv3.service.usermgt;

import com.fz.generic.PageTopUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 *
 */
public class Auth {

    /***
     * Check authorization. Requires session attrib login & rights to be set
     * @param pc jsp page context
     * @param semiColonedRights right objects to be chekced, semi-coloned
     * @param throwError if true, and not authorized, throw error
     * @return empty string if OK, error message if not and throwError false
     * @throws Exception if not authorized, or any other error
     */
    public static String check(PageContext pc
            , String semiColonedRights
            , boolean throwError
    ) throws Exception {
        HttpServletRequest request = (HttpServletRequest) pc.getRequest();
        
        // get session login attrib 
        String login = getSessionAttr(request, "login");
        String rights = getSessionAttr(request, "rights");
        
        // init return value
        String msg = "";
        
        // if admin ok
        if (rights.trim().isEmpty() || rights.equals(";;")){
            msg = "Not authorized, empty rights";
        }
        else if (rights.contains("administrator") || rights.contains("super_user")){
            msg = "";
        }
        else {
            
            // if any item not contained in rights, not auth, break
            String[] rgs = semiColonedRights.split(";");
            for (String rg : rgs){

                String formattedRg = rg.trim().toLowerCase();

                if (!rights.contains(";" + formattedRg + ";")){

                    msg = "Not authorized"
                            + ", object " + rg
                            + ", user " + login;
                    break;
                }
            }
        }
        
        // if msg empty, ok
        if (msg.length() == 0) return "";
        
        // if not empty, error, check should throw or return err msg
        if (throwError){
            throw new Exception(msg);
        }
        else {
            return msg;
        }
    }
    
    private static String getSessionAttr(HttpServletRequest request
            , String param) {
        String r = (String) request.getSession().getAttribute(param);
        if (r == null) r = "";
        return r;
    }
}

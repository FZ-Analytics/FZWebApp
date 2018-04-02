/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.progress;

import com.fz.generic.BusinessLogic;
import static com.fz.tms.progress.progress.rId;
import static com.fz.tms.progress.progress.runLoad;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author dwi.rangga
 */
public class progressView implements BusinessLogic {
    public static String rId = "";
    
    @Override
    public void run(
            HttpServletRequest request
            , HttpServletResponse response
            , PageContext pc
    ) throws Exception {
        try{
            loadRun(request);
        }catch(Exception e){
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            System.out.println(">>>>>progressView.java<<<<<");
            System.out.println(">>>>>DATE NOW : "+dateFormat.format(date)+"<<<<<"); //2016/11/16 12:08:43
            System.out.println("ERROR : "+e.getMessage());
        }
    }
    
    public static void loadRun(HttpServletRequest request){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        request.setAttribute("runDate", dateFormat.format(date).toString());
        request.setAttribute("branch", "0321");
    }
}

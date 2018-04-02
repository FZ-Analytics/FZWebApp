/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.progress;

import com.fz.generic.BusinessLogic;
import com.fz.util.FZUtil;
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
public class progressre  implements BusinessLogic {
    
    @Override
    public void run(
            HttpServletRequest request
            , HttpServletResponse response
            , PageContext pc
    ) throws Exception {
        runBtn(request, pc);
    }
    
    public static void runBtn(HttpServletRequest request, PageContext pc){
        try{
            String item1 = FZUtil.getHttpParam(request, "item");
            String rId = (String) request.getSession().getAttribute("runId");

            String btn = FZUtil.getHttpParam(request, "btn");

            if(btn.equalsIgnoreCase("close")){
                request.getRequestDispatcher("../main/main.jsp")
                    .forward(pc.getRequest(), pc.getResponse());
            }else{
                 if(btn.equalsIgnoreCase("finish")){
                     String str = progressLogic.updateData(rId);
                 }
                request.getRequestDispatcher("progress.jsp")
                    .forward(pc.getRequest(), pc.getResponse());
            }
            
        }catch(Exception e){
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            System.out.println(">>>>>progressre.java<<<<<");
            System.out.println(">>>>>DATE NOW : "+dateFormat.format(date)+"<<<<<"); //2016/11/16 12:08:43
            System.out.println("ERROR : "+e.getMessage());
        }
    }
}

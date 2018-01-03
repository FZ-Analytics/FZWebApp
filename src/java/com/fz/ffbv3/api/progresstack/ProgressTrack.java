/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.api.progresstack;

import com.fz.generic.ReturnValue;
import com.fz.tms.model.RunIdModel;
import com.fz.tms.model.jsonGetShift;
import com.fz.tms.model.returnTmProgress;
import com.fz.tms.model.tmProgress;
import com.fz.tms.progress.progressLogic;
import com.fz.tms.service.routing.RoutingLogic;
import com.fz.util.FZUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Eri Fizal
 */
@Path("progressTrackAPI")
public class ProgressTrack {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ProgressTrack
     */
    public ProgressTrack() {
    }

    /**
     * Retrieves representation of an instance of com.fz.ffbv3.api.progresstack.ProgressTrack
     * @return an instance of java.lang.String
     */
    /**
     * Retrieves representation of an instance of com.fz.ffbv3.service.harvestestm.HarvestEstmSaveAPI
     * @return an instance of java.lang.String
     */
    @POST
    @Path("getData")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(String content) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        //setting return jsonObject
        returnTmProgress rt = new returnTmProgress();
        try{
            RunIdModel he = gson.fromJson(content, RunIdModel.class);
            progressLogic pl = new progressLogic();
            List<tmProgress> tr = (List<tmProgress>) progressLogic.getData(he.getRunId());            
            
            if(tr.size() > 0){
                rt.msg = "OK";
                rt.success = true;
                rt.setTm(tr);
            }else{
                rt.msg = "Error select "+he.getRunId();
                rt.success = false;
            }
            
        }catch(Exception e){
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            System.out.println(">>>>>ProgressTrack<<<<<");
            System.out.println(">>>>>progressTrackAPI.getData<<<<<");
            System.out.println(">>>>>DATE NOW : "+dateFormat.format(date)+"<<<<<"); //2016/11/16 12:08:43
            System.out.println("ERROR : "+e.getMessage());
            
            rt.msg = FZUtil.toStackTraceText(e);
            rt.success = false;
        }
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
        String jsonOutput = gson.toJson(rt, returnTmProgress.class);
        return jsonOutput;
    }

    /**
     * PUT method for updating or creating an instance of ProgressTrack
     * @param content representation for the resource
     */
    @POST
    @Path("updateData")
    @Produces(MediaType.APPLICATION_JSON)
    public String updateData(String content) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        returnTmProgress rt = new returnTmProgress();        
        try{
            RunIdModel he = gson.fromJson(content, RunIdModel.class);
            progressLogic pl = new progressLogic();
            String tr = (String) progressLogic.updateData(he.getRunId());            
            
            if(tr.equalsIgnoreCase("OK")){
                rt.msg = tr;
                rt.success = true;
            }else{
                rt.msg = tr;
                rt.success = false;
            }
            
        }catch(Exception e){
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            System.out.println(">>>>>ProgressTrack<<<<<");
            System.out.println(">>>>>progressTrackAPI.updateData<<<<<");
            System.out.println(">>>>>DATE NOW : "+dateFormat.format(date)+"<<<<<"); //2016/11/16 12:08:43
            System.out.println("ERROR : "+e.getMessage());
            
            rt.msg = FZUtil.toStackTraceText(e);
            rt.success = false;
        }
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
        String jsonOutput = gson.toJson(rt, returnTmProgress.class);
        return jsonOutput;
    }
    
    @POST
    @Path("runAPIRouting")
    @Produces(MediaType.APPLICATION_JSON)
    public String runAPIRouting(String content) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        ReturnValue returnValue = new ReturnValue();       
        try{
            jsonGetShift he = gson.fromJson(decodeContent(content), jsonGetShift.class);
            progressLogic pl = new progressLogic();
            String tr = (String) RoutingLogic.runAPI(he.getShift());            
            
            if(tr.equalsIgnoreCase("OK")){
                returnValue.msg = tr;
                returnValue.success = true;
            }else{
                returnValue.msg = tr;
                returnValue.success = false;
            }
            
        }catch(Exception e){
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            System.out.println(">>>>>ProgressTrack<<<<<");
            System.out.println(">>>>>progressTrackAPI.runAPIRouting<<<<<");
            System.out.println(">>>>>DATE NOW : "+dateFormat.format(date)+"<<<<<"); //2016/11/16 12:08:43
            System.out.println("ERROR : "+e.getMessage());
            
            returnValue.msg = FZUtil.toStackTraceText(e);
            returnValue.success = false;
        }
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
        String jsonOutput = gson.toJson(returnValue, ReturnValue.class);
        return jsonOutput;
    }
    
    //untuk decoding json
    public static String decodeContent(String content) throws UnsupportedEncodingException{
        content = java.net.URLDecoder.decode(content, "UTF-8");
        content = content.substring(5);
        
        return content;
    }
}

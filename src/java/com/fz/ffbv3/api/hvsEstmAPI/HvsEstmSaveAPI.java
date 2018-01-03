/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.api.hvsEstmAPI;

import com.fz.ffbv3.service.hvsEstm.HvsEstm;
import com.fz.ffbv3.service.hvsEstm.HvsEstmDAO;
import com.fz.ffbv3.service.hvsEstm.HvsEstmDtl;
import com.fz.generic.Db;
import com.fz.generic.ReturnValue;
import com.fz.util.FZUtil;
import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Eri Fizal
 */
@Path("hvsEstmSaveAPI")
public class HvsEstmSaveAPI {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of HvsEstmSaveAPI
     */
    public HvsEstmSaveAPI() {
    }

    /**
     * Retrieves representation of an instance of com.fz.ffbv3.api.hvsEstmAPI.HvsEstmSaveAPI
     * @return an instance of java.lang.String
     */
    /**
     * Retrieves representation of an instance of com.fz.ffbv3.service.harvestestm.HarvestEstmSaveAPI
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(
            @QueryParam("json") String jsonInput
            , @Context HttpServletRequest request) {

        // initialize standard return value
        ReturnValue returnValue = new ReturnValue();
        
        // create json parser using Gson library from Google
        Gson gson = new Gson();
        
        try {
            
            // parse JSON input into java object
            HvsEstm he = gson.fromJson(jsonInput, HvsEstm.class);
            HvsEstmDAO heDAO = new HvsEstmDAO();
            heDAO.save(he);
            
            // create success returnValue 
            returnValue.success = true;
            returnValue.msg = "Success!";
        }
        catch(Exception e){
            
            // convert stack trace to string
            String msg = FZUtil.toStackTraceText(e);
            
            // create error returnValue 
            returnValue.success = false;
            returnValue.msg = msg;
            
            // print to console for debug
            System.out.println(msg);
        }

        // convert returnValue to json, then return it
        String jsonOutput = gson.toJson(returnValue, ReturnValue.class);
        return jsonOutput;
    }

}

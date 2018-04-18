/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.api.TMS;

import static com.fz.ffbv3.api.TMS.PopupEditCustAPI.decodeContent;
import com.fz.tms.params.model.OptionModel;
import com.fz.tms.params.model.PreRouteJobSubmitCustomer;
import com.fz.tms.params.model.history;
import com.fz.tms.params.service.Other;
import com.fz.tms.params.service.PreRouteJobDB;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.UnsupportedEncodingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author dwi.oktaviandi
 */
@Path("popupEditCustBfror")
public class popupEditCustBfrorAPI {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of popupEditCustBfrorAPI
     */
    public popupEditCustBfrorAPI() {
    }

    /**
     * Retrieves representation of an instance of com.fz.ffbv3.api.TMS.popupEditCustBfrorAPI
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of popupEditCustBfrorAPI
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
    
    @POST
    @Path("excludeDO")
    @Produces(MediaType.APPLICATION_JSON)
    public String excludeDO(String content) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String tr = "";
        try{
            PreRouteJobSubmitCustomer he = gson.fromJson(content.contains("json") ? 
                    decodeContent(content) : content, PreRouteJobSubmitCustomer.class);
            tr =  PreRouteJobDB.submitEditDO(he);              
        }catch(Exception e){
            System.out.println(e.getMessage());
            tr = "";
        }
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
        String jsonOutput = gson.toJson(tr);
        return jsonOutput;
    }
    
    @POST
    @Path("savehistory")
    @Produces(MediaType.APPLICATION_JSON)
    public String SaveHistory(String content) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String tr = "";
        try{
            history he = gson.fromJson(content.contains("json") ? 
                    decodeContent(content) : content, history.class);
            if(he.Value.length() > 0){
                Other ot = new Other();
                tr =  ot.InsertHistory(he);
            }                          
        }catch(Exception e){
            System.out.println(e.getMessage());
            tr = "";
        }
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
        String jsonOutput = gson.toJson(tr);
        return jsonOutput;
    }
    
    public static String decodeContent(String content) throws UnsupportedEncodingException{
        content = java.net.URLDecoder.decode(content, "UTF-8");
        content = content.substring(5);
        
        return content;
    }
}

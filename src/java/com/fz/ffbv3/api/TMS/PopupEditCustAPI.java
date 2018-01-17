/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.api.TMS;

import com.fz.tms.params.model.PreRouteJobGetDO;
import com.fz.tms.params.model.PreRouteJobGetStatusDO;
import com.fz.tms.params.model.OptionModel;
import com.fz.tms.params.model.PreRouteJobSubmitCustomer;
import com.fz.tms.params.service.PreRouteJobDB;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
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
 * @author dwi.rangga
 */
@Path("popupEditCust")
public class PopupEditCustAPI {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PopupEditCustAPI
     */
    public PopupEditCustAPI() {
    }

    /**
     * Retrieves representation of an instance of com.fz.ffbv3.api.TMS.PopupEditCustAPI
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of PopupEditCustAPI
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
    
    @POST
    @Path("getDO")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDO(String content) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<OptionModel> tr = new ArrayList<OptionModel>();
        try{
            PreRouteJobGetDO he = gson.fromJson(content.contains("json") ? 
                    decodeContent(content) : content, PreRouteJobGetDO.class);
            tr = (List<OptionModel>) PreRouteJobDB.getDOPreRouteJob(he.RunId, he.Customer_ID);              
        }catch(Exception e){
            System.out.println(e.getMessage());
            tr = new ArrayList<OptionModel>();
        }
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
        String jsonOutput = gson.toJson(tr);
        return jsonOutput;
    }
    
    @POST
    @Path("getStatusDO")
    @Produces(MediaType.APPLICATION_JSON)
    public String getStatusDO(String content) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String tr = "";
        try{
            PreRouteJobGetStatusDO he = gson.fromJson(content.contains("json") ? 
                    decodeContent(content) : content, PreRouteJobGetStatusDO.class);
            tr =  PreRouteJobDB.getStatusDO(he.RunId, he.Customer_ID, he.DO_Number);              
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
    @Path("submitCustomer")
    @Produces(MediaType.APPLICATION_JSON)
    public String submitCustomer(String content) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String tr = "";
        try{
            PreRouteJobSubmitCustomer he = gson.fromJson(content.contains("json") ? 
                    decodeContent(content) : content, PreRouteJobSubmitCustomer.class);
            tr =  PreRouteJobDB.submitEdit(he);              
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

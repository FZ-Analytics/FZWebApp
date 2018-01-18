/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.api.params;

import com.fz.tms.params.model.Branch;
import com.fz.tms.params.model.PreRouteJobGetDO;
import com.fz.tms.params.model.PreRouteJobGetStatusDO;
import com.fz.tms.params.model.OptionModel;
import com.fz.tms.params.service.CustomerAttrDB;
import com.fz.tms.params.service.PreRouteJobDB;
import com.fz.tms.params.service.VehicleAttrDB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * REST Web Service
 *
 * @author dwi.rangga
 */
@Path("Params")
public class PramsAPI {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PramsAPI
     */
    public PramsAPI() {
    }

    /**
     * Retrieves representation of an instance of com.fz.ffbv3.api.params.PramsAPI
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of PramsAPI
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
    /*
    @POST
    @Path("getCustomerId")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCustomerId(String content) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<OptionModel> tr = new ArrayList<OptionModel>();
        try{
            Branch he = gson.fromJson(content.contains("json") ? 
                    decodeContent(content) : content, Branch.class);
            tr = (List<OptionModel>) CustomerAttrDB.getCustomerId(he.branchId);              
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
    @Path("getVehicleId")
    @Produces(MediaType.APPLICATION_JSON)
    public String runAPIRouting(String content) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<OptionModel> tr = new ArrayList<OptionModel>();
        try{
            Branch he = gson.fromJson(content.contains("json") ? 
                    decodeContent(content) : content, Branch.class);
            tr = (List<OptionModel>) VehicleAttrDB.getVehicleId(he.branchId);              
        }catch(Exception e){
            System.out.println(e.getMessage());
            tr = new ArrayList<OptionModel>();
        }
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
        String jsonOutput = gson.toJson(tr);
        return jsonOutput;
    }
    */
    
    public static String decodeContent(String content) throws UnsupportedEncodingException{
        content = java.net.URLDecoder.decode(content, "UTF-8");
        content = content.substring(5);
        
        return content;
    }
}

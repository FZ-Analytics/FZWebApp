/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.api.TMS;

import com.fz.tms.params.model.ExportType;
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
@Path("ExportDb")
public class ExportDb {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RunResultAPI
     */
    public ExportDb() {
    }

    /**
     * Retrieves representation of an instance of com.fz.ffbv3.api.TMS.RunResultAPI
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of RunResultAPI
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
    
    @POST
    @Path("ExportDb")
    @Produces(MediaType.APPLICATION_JSON)
    public String ExportDb(String content) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String ret = "";
        try{
            if(decodeContent(content).equals("truck")) {
                exportTruck();
            } else if(decodeContent(content).equals("customer")) {
                exportCustomer();
            } else if(decodeContent(content).equals("salesman")) {
                exportSalesman();
            }

        }catch(Exception e){
            System.out.println(e.getMessage()); 
        }
        String jsonOutput = gson.toJson(ret);
        return jsonOutput;        
    }
    
    public static String decodeContent(String content) throws UnsupportedEncodingException{
        content = java.net.URLDecoder.decode(content, "UTF-8");
        String[] contentSplit = content.split(":");
        System.out.println(contentSplit[1].replaceAll("\"", "").replace(" ", "").replace("}", ""));

        return content;
    }

    private void exportTruck() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void exportCustomer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void exportSalesman() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

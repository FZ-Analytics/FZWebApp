/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.api.TMS;

import com.fz.tms.params.model.ForwadingAgent;
import com.fz.tms.params.service.ForwadingAgentDB;
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
@Path("ForwadingAgent")
public class ForwadingAgentAPI {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ForwadingAgentAPI
     */
    public ForwadingAgentAPI() {
    }

    /**
     * Retrieves representation of an instance of com.fz.ffbv3.api.TMS.ForwadingAgentAPI
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of ForwadingAgentAPI
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
    
    @POST
    @Path("submit")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDO(String content) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String str = "ERROR";
        try {
            ForwadingAgentDB db = new ForwadingAgentDB();
            ForwadingAgent he = gson.fromJson(content.contains("json")
                    ? decodeContent(content) : content, ForwadingAgent.class);
            str = cek(he);
            if (str == "OK") {
                str = db.insert(he, he.flag);
            }

        } catch (Exception e) {
            str = "ERROR";
        }
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
        String jsonOutput = gson.toJson(str);
        return jsonOutput;
    }
    public String cek(ForwadingAgent c) {
        String str = "OK";

        if (c.Service_agent_id.length() == 0 || c.Driver_Name.length() == 0 || c.Branch.length() == 0
                || c.Status.length() == 0 || c.inc.length() == 0) {
            str = "Lengkapi parameter";
        }
        return str;
    }
    public static String decodeContent(String content) throws UnsupportedEncodingException {
        content = java.net.URLDecoder.decode(content, "UTF-8");
        content = content.substring(5);

        return content;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.api.division;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author Eko
 */
@Path("division")
public class APIMill {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of APIMill
     */
    public APIMill() {
    }

    /**
     * Retrieves representation of an instance of com.fz.ffbv3.api.division.APIMill
     * @return an instance of java.lang.String
     */
    @GET
    @Path("tes")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        return "{'tes':'coba'}";
    }

    /**
     * PUT method for updating or creating an instance of APIMill
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}

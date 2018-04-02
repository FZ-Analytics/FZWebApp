/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.api.progresstack;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
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
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(
            @QueryParam("json") String jsonInput
            , @Context HttpServletRequest request) {

        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of ProgressTrack
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}

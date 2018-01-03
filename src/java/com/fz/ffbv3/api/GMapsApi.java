/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.api;

import com.fz.ffbv3.service.googlemgt.maps.GMapsModel;
import com.fz.ffbv3.service.usermgt.UserModel;
import com.fz.generic.DBConnector;
import com.fz.generic.ResponseMessege;
import com.fz.generic.StatusHolder;
import com.fz.util.FixMessege;
import com.fz.util.FixValue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Connection;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST
 * Web
 * Service
 *
 * @author
 * Agustinus
 * Ignat
 */
@Path("google")
public
	class GMapsApi
{

	@Context
	private
		UriInfo context;

	/**
	 * Creates
	 * a
	 * new
	 * instance
	 * of
	 * GMapsApi
	 */
	public
		GMapsApi()
	{
	}

	/**
	 * Retrieves representation of an instance of com.fz.ffbv3.api.GMapsApi
	 * @return an instance of java.lang.String
	 */
	@GET
  @Produces(MediaType.APPLICATION_JSON)
	public
	String getJson()
	{
		//TODO return proper representation object
		throw new UnsupportedOperationException();
	}

	/**
	 * PUT method for updating or creating an instance of GMapsApi
	 * @param content representation for the resource
	 */
	@PUT
  @Consumes(MediaType.APPLICATION_JSON)
	public
	void putJson(String content)
	{
	}

  @GET
  @Path("maps")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response postGMapsJson(String content)
  {
		
		// Get Gson object and parse json string to object
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		GMapsModel gMapsModel = gson.fromJson(content, GMapsModel.class);
    
    return Response.status(200).entity(gMapsModel.toString()).build();
  }
}

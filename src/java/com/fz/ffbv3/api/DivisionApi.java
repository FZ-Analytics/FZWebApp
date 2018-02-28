/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.api;

import com.fz.ffbv3.service.divisionmgt.DivisionLogic;
import com.fz.ffbv3.service.divisionmgt.DivisionModel;
import com.fz.ffbv3.service.usermgt.UserLogic;
import com.fz.ffbv3.service.usermgt.UserModel;
import com.fz.generic.DBConnector;
import com.fz.generic.Db;
import com.fz.generic.ResponseMessege;
import com.fz.generic.StatusHolder;
import com.fz.util.FixMessege;
import com.fz.util.FixValue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * REST Web Service
 *
 * @author ignat
 */
@Path("v2/divisi")
public class DivisionApi 
{
  private final Logger logger = Logger.getLogger(this.getClass().getPackage().getName());

  @Context
  private UriInfo context;

  /**
   * Creates a new instance of DivisionApi
   */
  public DivisionApi() {
  }

  /**
   * Retrieves representation of an instance of com.fz.ffbv3.api.DivisionApi
   * @return an instance of java.lang.String
   */
  @GET
  @Produces(MediaType.APPLICATION_XML)
  public String getXml() {
    //TODO return proper representation object
    throw new UnsupportedOperationException();
  }

  /**
   * PUT method for updating or creating an instance of DivisionApi
   * @param content representation for the resource
   */
  @PUT
  @Consumes(MediaType.APPLICATION_XML)
  public void putXml(String content) {
  }
  
  @POST
  @Path("dashboard")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response postDashboardJson(String content)
  {
    logger.severe("[Path] -> v2/divisi/dashboard");

		// Get Gson object and parse json string to object
    logger.severe("[JSON] -> " + content);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		DivisionModel divisionModel = gson.fromJson(content, DivisionModel.class);
    logger.severe("[Parsing] -> Parsing request with GSON done");

    StatusHolder statusHolder = new StatusHolder();
    
		try(Connection conn = (new Db()).getConnection("jdbc/fz"))
		{
      logger.severe("[Open] -> Open database done");
      DivisionLogic divisionLogic = new DivisionLogic(conn, logger); 
      statusHolder = divisionLogic.DashboarPerDivisi(divisionModel.getDashboardData());
    }
		catch (Exception ex)
		{
      statusHolder.setCode(FixValue.intResponError);
      statusHolder.setRsp(new ResponseMessege().CoreMsgResponse(FixValue.intFail, FixMessege.strDashboardDivisiFailed));
      logger.log(Level.SEVERE, "[Stack Trace] -> {0}", ex.toString());
		}
    
    logger.severe("[Close] -> Close database done");
    logger.severe("[" + statusHolder.getCode() + "] -> " + statusHolder.getRsp());
    return Response.status(statusHolder.getCode()).entity(statusHolder.getRsp()).build();
  }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.api;

import com.fz.ffbv3.service.taskmgt.TaskLogic;
import com.fz.ffbv3.service.taskmgt.UploadModel;
import com.fz.ffbv3.service.trackmgt.GraberModel;
import com.fz.ffbv3.service.trackmgt.TrackingLogic;
import com.fz.ffbv3.service.trackmgt.TrackingModel;
import com.fz.generic.DBConnector;
import com.fz.generic.Db;
import com.fz.generic.ResponseMessege;
import com.fz.generic.StatusHolder;
import com.fz.util.FixMessege;
import com.fz.util.FixValue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
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
 * @author Agustinus Ignat
 */
@Path("v2/track")
public class Track2Api 
{
  private final Logger logger = Logger.getLogger(this.getClass().getPackage().getName());
//  FileHandler fh = null;
//  final String DATE_FORMAT = "yyyyMMdd.HHmm";
//  Random rand = new Random();

  @Context
  private UriInfo context;

    /**
     * Creates a new instance of TrackApi
     */
    public Track2Api() 
    {
/*      
			try 
      {
        DateTimeFormatter dateTimeformatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDateTime localDateTime = LocalDateTime.now();

        this.fh = new FileHandler(FixValue.strLogPath + "TrackApi." + dateTimeformatter.format(localDateTime) + ".log", true);
      }
      catch (IOException ex)
      {
        Logger.getLogger(UsersApi.class.getName()).log(Level.SEVERE, null, ex);
      } 
      catch (SecurityException ex) 
      {
        Logger.getLogger(UsersApi.class.getName()).log(Level.SEVERE, null, ex);
      }

      fh.setFormatter(new SimpleFormatter());
      logger.addHandler(fh); 
*/      
		}

    /**
     * Retrieves representation of an instance of com.fz.ffbv3.api.TrackApi
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of TrackApi
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

  @POST
  @Path("position")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response postJsonUpload(String content)
  {
    logger.severe("[Path] -> /track/position");
    
    // Get Gson object and parse json string to object
    logger.severe("[JSON] -> " + content);
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
		TrackingModel trackingModel = gson.fromJson(content, TrackingModel.class);
    logger.severe("[Parsing] -> Parsing request with GSON done");

    StatusHolder statusHolder = new StatusHolder();

		try(Connection conn = (new Db()).getConnection("jdbc/fz"))
		{
	    logger.severe("[Open] -> Open database done");
      TrackingLogic trackingLogic = new TrackingLogic(conn, logger);
      statusHolder = trackingLogic.GPSDataUpload(trackingModel);
		}
		catch (Exception ex)
		{
      statusHolder.setCode(FixValue.intResponError);
      statusHolder.setRsp(new ResponseMessege().CoreMsgResponse(FixValue.intFail, FixMessege.strUploadFailed));
      logger.log(Level.SEVERE, "[Stack Trace] -> {0}", ex.toString());
		}

    logger.severe("[Close] -> Close database done");
    logger.severe("[" + statusHolder.getCode() + "] -> " + statusHolder.getRsp());
//    fh.close();
    return Response.status(statusHolder.getCode()).entity(statusHolder.getRsp()).build();
  }

  @POST
  @Path("graber")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response postJsonGrabber(String content)
  {
    logger.severe("[Path] -> /track/graber");
    
    // Get Gson object and parse json string to object
    logger.severe("[JSON] -> " + content);
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
		GraberModel graberModel = gson.fromJson(content, GraberModel.class);
    logger.severe("[Parsing] -> Parsing request with GSON done");

    StatusHolder statusHolder = new StatusHolder();

		try(Connection conn = (new Db()).getConnection("jdbc/fz"))
		{
	    logger.severe("[Open] -> Open database done");
      TrackingLogic trackingLogic = new TrackingLogic(conn, logger);
      statusHolder = trackingLogic.GPSGraberUpload(graberModel);
		}
		catch (Exception ex)
		{
      statusHolder.setCode(FixValue.intResponError);
      statusHolder.setRsp(new ResponseMessege().CoreMsgResponse(FixValue.intFail, FixMessege.strUploadFailed));
      logger.log(Level.SEVERE, "[Stack Trace] -> {0}", ex.toString());
		}
    
    logger.severe("[Close] -> Close database done");
    logger.severe("[" + statusHolder.getCode() + "] -> " + statusHolder.getRsp());
//    fh.close();
    return Response.status(statusHolder.getCode()).entity(statusHolder.getRsp()).build();
  }
}

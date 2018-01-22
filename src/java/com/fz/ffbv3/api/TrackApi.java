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
@Path("v1/track")
public class TrackApi 
{
  private final Logger logger = Logger.getLogger(this.getClass().getPackage().getName());
//  FileHandler fh = null;
//  final String DATE_FORMAT = "yyyyMMdd";

  @Context
  private UriInfo context;

    /**
     * Creates a new instance of TrackApi
     */
    public TrackApi() 
    {
/*
			try 
      {
        DateTimeFormatter dateTimeformatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDateTime localDateTime = LocalDateTime.now();

        this.fh = new FileHandler("D:\\fza\\log\\TrackApi." + dateTimeformatter.format(localDateTime) + ".log", true);
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

    DBConnector dBConnector = new DBConnector();
    Connection conn = dBConnector.ConnectToDatabase();
    logger.severe("[Open] -> Open database done");

    StatusHolder statusHolder = new StatusHolder();
    
    if(conn != null)
    {
      TrackingLogic trackingLogic = new TrackingLogic(conn, logger);
      statusHolder = trackingLogic.GPSDataUpload(trackingModel);
    }
    else
    {
      statusHolder.setCode(FixValue.intResponError);
      statusHolder.setRsp(new ResponseMessege().CoreMsgResponse(FixValue.intFail, FixMessege.strUploadFailed));
    }   
    
    dBConnector.CloseDatabase(conn);
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

    DBConnector dBConnector = new DBConnector();
    Connection conn = dBConnector.ConnectToDatabase();
    logger.severe("[Open] -> Open database done");

    StatusHolder statusHolder = new StatusHolder();
    
    if(conn != null)
    {
      TrackingLogic trackingLogic = new TrackingLogic(conn, logger);
      statusHolder = trackingLogic.GPSGraberUpload(graberModel);
    }
    else
    {
      statusHolder.setCode(FixValue.intResponError);
      statusHolder.setRsp(new ResponseMessege().CoreMsgResponse(FixValue.intFail, FixMessege.strUploadFailed));
    }   
    
    dBConnector.CloseDatabase(conn);
    logger.severe("[Close] -> Close database done");
    logger.severe("[" + statusHolder.getCode() + "] -> " + statusHolder.getRsp());
//    fh.close();
    return Response.status(statusHolder.getCode()).entity(statusHolder.getRsp()).build();
  }
}

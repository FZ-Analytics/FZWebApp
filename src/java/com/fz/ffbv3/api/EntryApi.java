/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.api;

import com.fz.ffbv3.service.entrymgt.EntryLogic;
import com.fz.ffbv3.service.entrymgt.EntryModel;
import com.fz.ffbv3.service.hvsEstm.HvsEstm;
import com.fz.ffbv3.service.hvsEstm.HvsEstmDAO;
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
 * REST
 * Web
 * Service
 *
 * @author
 * Agustinus Ignat
 */
@Path("v2/entry")
public class EntryApi
{
  private final Logger logger = Logger.getLogger(this.getClass().getPackage().getName());
//  FileHandler fh = null;
//  final String DATE_FORMAT = "yyyyMMdd.HHmm";
//  Random rand = new Random();
	
	@Context private UriInfo context;

	/**
	 * Creates
	 * a
	 * new
	 * instance
	 * of
	 * OrdersApi
	 */
	public EntryApi()
	{
/*    
    try 
    {
      DateTimeFormatter dateTimeformatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
      LocalDateTime localDateTime = LocalDateTime.now();

      this.fh = new FileHandler(FixValue.strLogPath + "EntryApi." + dateTimeformatter.format(localDateTime) + ".log", true);
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
	 * Retrieves representation of an instance of com.fz.ffbv3.api.OrdersApi
	 * @return an instance of java.lang.String
	 */
	@GET
  @Produces(MediaType.APPLICATION_JSON)
	public String getJson()
	{
		//TODO return proper representation object
		throw new UnsupportedOperationException();
	}

	/**
	 * PUT method for updating or creating an instance of OrdersApi
	 * @param content representation for the resource
	 */
	@PUT
  @Consumes(MediaType.APPLICATION_JSON)
	public void putJson(String content)
	{
	}
	
  @GET
  @Path("estimation")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response getEstimation()
  {
    logger.severe("[Path] -> v2/entry/estimation");
    
    StatusHolder statusHolder = new StatusHolder();
    
		try(Connection conn = (new Db()).getConnection("jdbc/fz"))
		{
      logger.severe("[Open] -> Open database done");
      EntryLogic entryLogic = new EntryLogic(conn, logger); 
      statusHolder = entryLogic.DataEntry(1);
    }
		catch (Exception ex)
		{
      statusHolder.setCode(FixValue.intResponError);
      statusHolder.setRsp(new ResponseMessege().CoreMsgResponse(FixValue.intFail, FixMessege.strDataEstFailed));
      logger.log(Level.SEVERE, "[Stack Trace] -> {0}", ex.toString());
		}

    logger.severe("[Close] -> Close database done");
    logger.severe("[" + statusHolder.getCode() + "] -> " + statusHolder.getRsp());
//    fh.close();
    return Response.status(statusHolder.getCode()).entity(statusHolder.getRsp()).build();
  }

	@POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("estimation")
	public Response postEstimationJson(String content)
	{
    logger.severe("[Path] -> v2/entry/estimation");

		// Get Gson object and parse json string to object
    logger.severe("[JSON] -> " + content);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
    HvsEstm he = gson.fromJson(content, HvsEstm.class);
    logger.severe("[Parsing] -> Parsing request with GSON done");
    
    StatusHolder statusHolder = new StatusHolder();

		try
		{
      HvsEstmDAO heDAO = new HvsEstmDAO();
      heDAO.save(he);
      statusHolder.setCode(FixValue.intResponSuccess);
      statusHolder.setRsp(new ResponseMessege().CoreMsgResponse(FixValue.intFail, FixMessege.strEstimationSuccess));
	    logger.severe("[Estimasi entry] -> Sukses");
		}
		catch(Exception ex)
		{
      statusHolder.setCode(FixValue.intResponFail);
      statusHolder.setRsp(new ResponseMessege().CoreMsgResponse(FixValue.intFail, FixMessege.strEstimationFailed));
	    logger.severe("[Estimasi entry] -> Gagal");
      logger.log(Level.SEVERE, "[Stack Trace] -> {0}", ex.toString());
		}

//		fh.close();
    logger.severe("[Close] -> Close database done");
    logger.severe("[" + statusHolder.getCode() + "] -> " + statusHolder.getRsp());
    return Response.status(statusHolder.getCode()).entity(statusHolder.getRsp()).build();
	}

  @GET
  @Path("dirloc")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response getJobJson()
  {
    logger.severe("[Path] -> v2/entry/dirloc");
    
    StatusHolder statusHolder = new StatusHolder();
    
		try(Connection conn = (new Db()).getConnection("jdbc/fz"))
		{
      logger.severe("[Open] -> Open database done");
      EntryLogic entryLogic = new EntryLogic(conn, logger); 
      statusHolder = entryLogic.DataEntry(2);
    }
		catch (Exception ex)
		{
      statusHolder.setCode(FixValue.intResponError);
      statusHolder.setRsp(new ResponseMessege().CoreMsgResponse(FixValue.intFail, FixMessege.strDataJobFailed));
      logger.log(Level.SEVERE, "[Stack Trace] -> {0}", ex.toString());
		}

    logger.severe("[Close] -> Close database done");
    logger.severe("[" + statusHolder.getCode() + "] -> " + statusHolder.getRsp());
//    fh.close();
    return Response.status(statusHolder.getCode()).entity(statusHolder.getRsp()).build();
  }

  @POST
  @Path("job")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response postJobJson(String content)
  {
    logger.severe("[Path] -> v2/entry/job");
    
		// Get Gson object and parse json string to object
    logger.severe("[JSON] -> " + content);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		EntryModel entryModel = gson.fromJson(content, EntryModel.class);
    logger.severe("[Parsing] -> Parsing request with GSON done");

    StatusHolder statusHolder = new StatusHolder();

		try(Connection conn = (new Db()).getConnection("jdbc/fz"))
		{
      logger.severe("[Open] -> Open database done");
      EntryLogic entryLogic = new EntryLogic(conn, logger); 
      statusHolder = entryLogic.JobEntry(entryModel);
    }
		catch (Exception ex)
		{
      statusHolder.setCode(FixValue.intResponError);
      statusHolder.setRsp(new ResponseMessege().CoreMsgResponse(FixValue.intFail, FixMessege.strDataJobEntryFailed));
      logger.log(Level.SEVERE, "[Stack Trace] -> {0}", ex.toString());
		}
    
    logger.severe("[Close] -> Close database done");
    logger.severe("[" + statusHolder.getCode() + "] -> " + statusHolder.getRsp());
//    fh.close();
    return Response.status(statusHolder.getCode()).entity(statusHolder.getRsp()).build();
  }
}

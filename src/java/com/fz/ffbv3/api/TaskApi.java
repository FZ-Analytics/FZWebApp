/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.api;

import com.fz.ffbv3.service.taskmgt.TaskLogic;
import com.fz.ffbv3.service.taskmgt.TaskPlanModel;
import com.fz.ffbv3.service.taskmgt.UploadModel;
import com.fz.ffbv3.service.taskmgt.UploadPlanData;
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
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Ignat
 */
@Path("v1/tasks")
public class TaskApi
{
  private final Logger logger = Logger.getLogger(this.getClass().getPackage().getName());
  FileHandler fh = null;
  final String DATE_FORMAT = "yyyyMMdd";

  @Context
  private UriInfo context;

  /**
   * Creates a new instance of TaskApi
   */
  public TaskApi()
  {		
    try 
    {
      DateTimeFormatter dateTimeformatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
      LocalDateTime localDateTime = LocalDateTime.now();

      this.fh = new FileHandler("D:\\fza\\log\\TaskApi." + dateTimeformatter.format(localDateTime) + ".log", true);
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
	}

  /**
   * Retrieves representation of an instance of com.fz.ffbv3.api.TaskApi
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
   * PUT method for updating or creating an instance of TaskApi
   * @param content representation for the resource
   */
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public void putJson(String content)
  {
  }
  
  @POST
  @Path("synchronize")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response postJsonSynchronize(String content)
  {
    logger.severe("[Path] -> /tasks/synchronize");
    
    // Get Gson object and parse json string to object
    logger.severe("[JSON] -> " + content);
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    TaskPlanModel taskModel = gson.fromJson(content, TaskPlanModel.class);
    logger.severe("[Parsing] -> Parsing request with GSON done");

    StatusHolder statusHolder = new StatusHolder();

		try(Connection conn = (new Db()).getConnection("jdbc/fz"))
		{
	    logger.severe("[Open] -> Open database done");
      TaskLogic taskLogic = new TaskLogic(conn, taskModel.getTaskListData().getVehicleID(), logger);
      Integer JobID = taskLogic.getJobID();
      
      if(JobID == -1) 
      {
        statusHolder.setCode(FixValue.intResponError);
        statusHolder.setRsp(new ResponseMessege().CoreMsgResponse(FixValue.intFail, FixMessege.strTaskEmpty));
      }
      else
      if(JobID == -2) 
      {
        statusHolder.setCode(FixValue.intResponFail);
        statusHolder.setRsp(new ResponseMessege().CoreMsgResponse(FixValue.intFail, FixMessege.strCanGoGome));
      }
      else
        statusHolder = taskLogic.TaskList(JobID);
		}
		catch (Exception ex)
		{
      statusHolder.setCode(FixValue.intResponError);
      statusHolder.setRsp(new ResponseMessege().CoreMsgResponse(FixValue.intFail, FixMessege.strTaskFailed));
		}
    
    logger.severe("[Close] -> Close database done");
    logger.severe("[" + statusHolder.getCode() + "] -> " + statusHolder.getRsp());
    fh.close();
    return Response.status(statusHolder.getCode()).entity(statusHolder.getRsp()).build(); 
  }

  @POST
  @Path("upload")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response postJsonUpload(String content)
  {
    logger.severe("[Path] -> /tasks/upload");
    
    // Get Gson object and parse json string to object
    logger.severe("[JSON] -> " + content);
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    UploadModel uploadModel = gson.fromJson(content, UploadModel.class);

    StatusHolder statusHolder = new StatusHolder();

		try(Connection conn = (new Db()).getConnection("jdbc/fz"))
		{
	    logger.severe("[Open] -> Open database done");
      TaskLogic taskLogic = new TaskLogic(conn, 0, logger);
      statusHolder = taskLogic.TaskListUpload(uploadModel);
		}
		catch (Exception ex)
		{
      statusHolder.setCode(FixValue.intResponError);
      statusHolder.setRsp(new ResponseMessege().CoreMsgResponse(FixValue.intFail, FixMessege.strUploadFailed));
		}

    
    logger.severe("[Close] -> Close database done");
    logger.severe("[" + statusHolder.getCode() + "] -> " + statusHolder.getRsp());
    fh.close();
    return Response.status(statusHolder.getCode()).entity(statusHolder.getRsp()).build(); 
  }

  @POST
  @Path("jobstate")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response postJobState(String content)
  {
    logger.severe("[Path] -> /tasks/jobstate");
    
    // Get Gson object and parse json string to object
    logger.severe("[JSON] -> " + content);
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    TaskPlanModel taskPlanModel = gson.fromJson(content, TaskPlanModel.class);

    StatusHolder statusHolder = new StatusHolder();

		try(Connection conn = (new Db()).getConnection("jdbc/fz"))
		{
	    logger.severe("[Open] -> Open database done");
      TaskLogic taskLogic = new TaskLogic(conn, 0, logger);
      statusHolder = taskLogic.MobileJobState(taskPlanModel);
		}
		catch (Exception ex)
		{
      statusHolder.setCode(FixValue.intResponError);
      statusHolder.setRsp(new ResponseMessege().CoreMsgResponse(FixValue.intFail, FixMessege.strMobileJobStateFailed));
		}
    
    logger.severe("[Close] -> Close database done");
    logger.severe("[" + statusHolder.getCode() + "] -> " + statusHolder.getRsp());
    fh.close();
    return Response.status(statusHolder.getCode()).entity(statusHolder.getRsp()).build(); 
  }

  @POST
  @Path("jobhistory")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response postJobHistory(String content)
  {
    logger.severe("[Path] -> /tasks/jobhistory");
    
    // Get Gson object and parse json string to object
    logger.severe("[JSON] -> " + content);
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    TaskPlanModel taskPlanModel = gson.fromJson(content, TaskPlanModel.class);

    StatusHolder statusHolder = new StatusHolder();
    
		try(Connection conn = (new Db()).getConnection("jdbc/fz"))
		{
	    logger.severe("[Open] -> Open database done");
      TaskLogic taskLogic = new TaskLogic(conn, 0, logger);
      statusHolder = taskLogic.MobileHistoryState(taskPlanModel);
		}
		catch (Exception ex)
		{
      statusHolder.setCode(FixValue.intResponError);
      statusHolder.setRsp(new ResponseMessege().CoreMsgResponse(FixValue.intFail, FixMessege.strMobileHistoryStateFailed));
		}

    logger.severe("[Close] -> Close database done");
    logger.severe("[" + statusHolder.getCode() + "] -> " + statusHolder.getRsp());
    fh.close();
    return Response.status(statusHolder.getCode()).entity(statusHolder.getRsp()).build(); 
  }
}

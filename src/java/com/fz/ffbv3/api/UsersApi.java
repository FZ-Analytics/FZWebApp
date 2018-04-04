/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.api;

import com.fz.ffbv3.service.usermgt.MenuModel;
import com.fz.ffbv3.service.usermgt.UserLogic;
import com.fz.ffbv3.service.usermgt.UserModel;
import com.fz.generic.DBConnector;
import com.fz.generic.Db;
import com.fz.generic.ResponseMessege;
import com.fz.generic.StatusHolder;
import com.fz.generic.UsersAll;
import com.fz.util.FZUtil;
import com.fz.util.FixMessege;
import com.fz.util.FixValue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javax.ejb.Stateless;

/**
 * REST Web Service
 *
 * @author Ignat
 */

@Stateless
@Api(value = "/users", description = "Api yang berhubungan dengan user, role dan menu")
@Path("v1/users")
public class UsersApi 
{
  private final Logger logger = Logger.getLogger(this.getClass().getPackage().getName());
//  FileHandler fh = null;
//  final String DATE_FORMAT = "yyyyMMdd.HHmm";
//  Random rand = new Random();

  @Context
  private UriInfo context;

  /**
   * Creates a new instance of UsersApi
   */
  public UsersApi()
  {
/*    
		try 
    {
      DateTimeFormatter dateTimeformatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
      LocalDateTime localDateTime = LocalDateTime.now();

      this.fh = new FileHandler(FixValue.strLogPath + "UsersApi." + dateTimeformatter.format(localDateTime) + ".log", true);
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
   * Retrieves representation of an instance of com.fz.ffbv3.api.TestResource
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
   * PUT method for updating or creating an instance of TestResource
   * @param content representation for the resource
   */
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public void putJson(String content)
  {
  }

  @ApiOperation(value = "/login", notes = "Api yang berhubungan dengan user login")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK"),
  @ApiResponse(code = 500, message = "Something wrong in Server")})
	@POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response postLoginJson(String content)
  {
//    UsersAll usersAll = new UsersAll(content, logger, fh);
    UsersAll usersAll = new UsersAll(content, logger);
    return usersAll.UsersLogin(1);
  }

  @POST
  @Path("logout")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response postLogoutJson(String content)
  {
//    UsersAll usersAll = new UsersAll(content, logger, fh);
    UsersAll usersAll = new UsersAll(content, logger);
    return usersAll.UsersLogout();
  }
}

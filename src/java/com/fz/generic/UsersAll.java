/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.generic;

import com.fz.ffbv3.service.usermgt.UserLogic;
import com.fz.ffbv3.service.usermgt.UserModel;
import com.fz.util.FZUtil;
import com.fz.util.FixMessege;
import com.fz.util.FixValue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Connection;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;

/**
 *
 * @author Agustinus Ignat
 */
public class UsersAll
{
  private String content;
  private Logger logger;
//  private FileHandler fh;

//  public UsersAll(String content, Logger logger, FileHandler fh)
  public UsersAll(String content, Logger logger)
  {
    this.content = content;
    this.logger = logger;
//    this.fh = fh;
  }

  public Response UsersLogin(Integer version)
  {
    logger.severe("[Path] -> /users/login");
    
    // Get Gson object and parse json string to object
    logger.log(Level.SEVERE, "[JSON] -> {0}", content);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		UserModel userModel = gson.fromJson(content, UserModel.class);
    logger.severe("[Parsing] -> Parsing request with GSON done");

    StatusHolder statusHolder = new StatusHolder();    

		try(Connection conn = (new Db()).getConnection("jdbc/fz"))
		{
      UserLogic userLogic = new UserLogic(conn, logger); 
      
      if(version == 1)
        statusHolder = userLogic.Login(userModel.getUserData().getUsername(), userModel.getUserData().getPassword());
      else
      if(version == 2)
        statusHolder = userLogic.LoginV2(userModel.getUserData().getUsername(), userModel.getUserData().getPassword());
		}
		catch (Exception ex)
		{
      statusHolder.setCode(FixValue.intResponError);
      statusHolder.setRsp(new ResponseMessege().CoreMsgResponse(FixValue.intFail, FixMessege.strLoginFailed));
      logger.log(Level.SEVERE, "[Stack Trace] -> {0}", ex.toString());
		}
    
    logger.severe("[Close] -> Close database done");
    logger.log(Level.SEVERE, "[{0}] -> {1}", new Object[]{statusHolder.getCode(), statusHolder.getRsp()});
//    fh.close();
    return Response.status(statusHolder.getCode()).entity(statusHolder.getRsp()).build();
  }         

  public Response UsersLogout()
  {
    logger.severe("[Path] -> /users/logout");
    
		// Get Gson object and parse json string to object
    logger.severe("[JSON] -> " + content);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		UserModel userModel = gson.fromJson(content, UserModel.class);
    logger.severe("[Parsing] -> Parsing request with GSON done");

    StatusHolder statusHolder = new StatusHolder();

		try(Connection conn = (new Db()).getConnection("jdbc/fz"))
		{
	    logger.severe("[Open] -> Open database done");
      UserLogic userLogic = new UserLogic(conn, logger); 
      statusHolder = userLogic.logout(userModel);
		}
		catch (Exception ex)
		{
      statusHolder.setCode(FixValue.intResponError);
      statusHolder.setRsp(new ResponseMessege().CoreMsgResponse(FixValue.intFail, FixMessege.strLogoutFailed));
      logger.log(Level.SEVERE, "[Stack Trace] -> {0}", ex.toString());
		}
    
    logger.severe("[Close] -> Close database done");
    logger.severe("[" + statusHolder.getCode() + "] -> " + statusHolder.getRsp());
//    fh.close();
    return Response.status(statusHolder.getCode()).entity(statusHolder.getRsp()).build();
  }
}

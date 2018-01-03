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
 * REST Web Service
 *
 * @author ignat
 */
@Path("division")
public class DivisionApi {

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
  @Path("gantipathnya")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response postDivisionJson(String content)
  {
		// Get Gson object and parse json string to object
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		DivisionModel divisionModel = gson.fromJson(content, DivisionModel.class);

    DBConnector dBConnector = new DBConnector();
    Connection conn = dBConnector.ConnectToDatabase();

    StatusHolder statusHolder = new StatusHolder();
    
    if(conn != null)
    {
      DivisionLogic divisionLogic = new DivisionLogic(conn); 
      statusHolder = divisionLogic.ContohProses(divisionModel.getRequestData().getField1(), divisionModel.getRequestData().getField2());
    }
    else
    {
      statusHolder.setCode(FixValue.intResponError);
      statusHolder.setRsp(new ResponseMessege().CoreMsgResponse(FixValue.intFail, FixMessege.strLoginFailed));
    }
    
    dBConnector.CloseDatabase(conn);    
    return Response.status(statusHolder.getCode()).entity(statusHolder.getRsp()).build();
  }
}

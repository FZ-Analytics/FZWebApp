/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.trackmgt;

import com.fz.ffbv3.service.usermgt.*;
import com.fz.generic.Db;
import com.fz.generic.StatusHolder;
import com.fz.util.FixValue;
import com.fz.generic.ResponseMessege;
import com.fz.util.FixMessege;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.*;
import java.util.logging.Logger;

/**
 *
 * @author Agustinus Ignat
 */
public class TrackingLogic
{              
  private Logger logger;

  private Statement st;
	private ResultSet res;
  private String strQuery;
  private ResponseMessege rspMsg;
  private StatusHolder sendRsp;
  private Connection conn;

  public TrackingLogic(Connection conn, Logger logdata)
  {
    this.conn = conn;
    this.logger = logdata;
  }

  public StatusHolder GPSDataUpload(TrackingModel trackingModel)
  {
    rspMsg = new ResponseMessege();
    sendRsp = new StatusHolder();
   
    strQuery = "INSERT INTO fbGPSTracker(Latitude, Longitude, EndDate, UserID, VehicleID, Status) VALUES('" + trackingModel.getTrackingData().getLatitude() +
							 "', '" + trackingModel.getTrackingData().getLongitude() + "', '" + trackingModel.getTrackingData().getEndDate() +
							 "', " + trackingModel.getTrackingData().getUserID() + ", " + trackingModel.getTrackingData().getVehicleID() + ", 1)";

    logger.severe("[Insert fbGPSTracker] -> " + strQuery);

    try
    {
      st = conn.createStatement();
  	  st.execute(strQuery);
      logger.severe("[Execute] -> Execute done");

      sendRsp.setCode(FixValue.intResponSuccess);
      sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intSuccess, FixMessege.strUploadSuccess));
		}
    catch (SQLException ex)
    {
      sendRsp.setCode(FixValue.intResponFail);
      sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intFail, FixMessege.strUploadFailed));
    }

    return sendRsp;
  }  

  public StatusHolder GPSGraberUpload(GraberModel graberModel)
  {
    rspMsg = new ResponseMessege();
    sendRsp = new StatusHolder();
   
    strQuery = "INSERT INTO fbGPSGraber(Latitude, Longitude, EndDate, Name, Division, Status) VALUES('" + graberModel.getGraberData().getLatitude() +
							 "', '" + graberModel.getGraberData().getLongitude() + "', '" + graberModel.getGraberData().getEndDate() + "', '" +
							 graberModel.getGraberData().getName() + "', " + graberModel.getGraberData().getDivision() + ", 1)";

    logger.severe("[Insert fbGPSGraber] -> " + strQuery);

    try
    {
      st = conn.createStatement();
  	  st.execute(strQuery);
      logger.severe("[Execute] -> Execute done");

      sendRsp.setCode(FixValue.intResponSuccess);
      sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intSuccess, FixMessege.strUploadSuccess));
		}
    catch (SQLException ex)
    {
      sendRsp.setCode(FixValue.intResponFail);
      sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intFail, FixMessege.strUploadFailed));
    }

    return sendRsp;
  }  
}

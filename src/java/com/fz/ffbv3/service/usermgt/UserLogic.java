/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.usermgt;

import com.fz.generic.Db;
import com.fz.generic.StatusHolder;
import com.fz.util.FixValue;
import com.fz.generic.ResponseMessege;
import com.fz.util.FixMessege;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 *
 * @author Agustinus Ignat
 */
public class UserLogic
{              
  private Logger logger;

  private Statement st;
	private ResultSet res;
  private String strQuery;
  private ResponseMessege rspMsg;
  private StatusHolder sendRsp;
  private final Connection conn;
  private FileHandler fh = null;

  public UserLogic(Connection conn, Logger logdata)
  {
    this.conn = conn;
    this.logger = logdata;
  }

  public StatusHolder Login(String Username, String Password)
  {
		Integer VehicleID;
    Integer rows;
    
    rspMsg = new ResponseMessege();
    sendRsp = new StatusHolder();
/*   
    strQuery = "SELECT c.gbUserID as UserID, a.Name, a.Phone, a.lnkRoleID, b.Brand, b.Type, d.VehicleID, d.VehicleName FROM fbuser as a, " +
                "fbdevice as b, gbuser AS c, fbvehicle AS d WHERE c.Username=\"" + Username + "\" AND c.Password=\"" +
                Password + "\" AND a.lnkDeviceID=b.DeviceID AND a.gbUserID=c.gbUserID AND a.VehicleID=d.VehicleID";
*/
		strQuery = "SELECT a.VehicleID, a.Name, a.Phone, a.lnkRoleID, b.Brand, b.Type, c.gbUserID as UserID FROM fbuser as a, fbdevice as b, gbuser AS c " + 
							 "WHERE a.lnkDeviceID=b.DeviceID AND a.gbUserID=c.gbUserID AND c.Username=\"" + Username + "\" AND c.Password=\"" + Password + "\"";

    logger.severe("[Query Vehicle] -> " + strQuery);

    try
    {
      st = conn.createStatement();
  	  res = st.executeQuery(strQuery);
      logger.severe("[Execute Vehicle] -> Execute done");

      res.last();
			VehicleID = res.getInt("VehicleID");

			if((VehicleID != 0) && (res != null))
			{
				strQuery = "SELECT a.VehicleID, a.Name, a.Phone, a.lnkRoleID, b.Brand, b.Type, c.gbUserID as UserID, d.VehicleName FROM fbuser as a, " +
                "fbdevice as b, gbuser AS c, fbvehicle AS d WHERE c.Username=\"" + Username + "\" AND c.Password=\"" +
                Password + "\" AND a.lnkDeviceID=b.DeviceID AND a.gbUserID=c.gbUserID AND a.VehicleID=d.VehicleID";

  			logger.severe("[Query Username] -> " + strQuery);
    	  res = st.executeQuery(strQuery);
        logger.severe("[Execute Username] -> Execute done");
  			res.last();
      }
      
      rows = res.getRow();
      logger.severe("[Rows] -> " + rows);
      
      if(rows == 0)
      {
        sendRsp.setCode(FixValue.intResponFail);
        sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intFail, FixMessege.strLoginEmpty));
      }
      else
      {
        sendRsp.setCode(FixValue.intResponSuccess);
        sendRsp.setRsp(rspMsg.LoginMsgResponse(FixValue.intSuccess, FixMessege.strLoginSuccess, res, rows, VehicleID));

				strQuery = "UPDATE gbuser SET StartTime=CURRENT_TIMESTAMP()" + ", lnkLoginID=" + 
									 FixValue.intLoginStatus + " WHERE Username=\"" + Username + "\" AND Password=\"" + Password + "\"";
				
        logger.severe("[Update gbuser] -> " + strQuery);
				UpdateUserByUserID(strQuery);
			}
    }
    catch (SQLException ex)
    {
      sendRsp.setCode(FixValue.intResponFail);
      sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intFail, FixMessege.strLoginFailed));
    }

    return sendRsp;
  }  

  public StatusHolder logout(UserModel userModel)
	{
    rspMsg = new ResponseMessege();
    sendRsp = new StatusHolder();
   
    strQuery = "UPDATE gbuser SET EndTime=CURRENT_TIMESTAMP()" + ", lnkLoginID=" + 
							 FixValue.intLogoutStatus + " WHERE gbUserID=" + userModel.getLogoutuser().getUserid();
		
    logger.severe("[Update gbuser] -> " + strQuery);

    if(!UpdateUserByUserID(strQuery))
		{
      sendRsp.setCode(FixValue.intResponFail);
      sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intFail, FixMessege.strLogoutFailed));
		}
		else
		{
      sendRsp.setCode(FixValue.intResponSuccess);
      sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intSuccess, FixMessege.strLogoutSuccess));
		}
		
		return sendRsp;
	}
	
	private boolean UpdateUserByUserID(String strQuery)
	{
    boolean success=true;
    
    try
    {
      st = conn.createStatement();
  	  st.execute(strQuery);
      logger.severe("[UpdateUserByUserID] -> Update gbuser done");
    }
    catch (SQLException ex)
    {
      success = false;
      logger.severe("[UpdateUserByUserID] -> " + ex.getMessage());
    }
    
    return success;
	}
}

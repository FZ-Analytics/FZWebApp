/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.datamgt;

import com.fz.ffbv3.service.usermgt.*;
import com.fz.generic.Db;
import com.fz.generic.StatusHolder;
import com.fz.util.FixValue;
import com.fz.generic.ResponseMessege;
import com.fz.util.FixMessege;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Agustinus Ignat
 */
public class DataLogic
{              
  private Logger logger;

  private Statement st;
	private ResultSet res, res1;
  private String strQuery;
  private ResponseMessege rspMsg;
  private StatusHolder sendRsp;
  private final Connection conn;
  private FileHandler fh = null;

  public DataLogic(Connection conn, Logger logdata)
  {
    this.conn = conn;
    this.logger = logdata;
  }

  public StatusHolder AmbilSemuaData(String strParams1, String strParams2)
  {
    String strData;
    Integer rows;
    rspMsg = new ResponseMessege();
    sendRsp = new StatusHolder();
   
    if(strParams1.matches("Users"))
    {
      if(strParams2.matches("All"))
        strData = "";
      else
        strData = " AND UserID=" + strParams2;
        
      strQuery = "SELECT a.UserID, a.Name, a.Phone, a.FullName, b.Password, b.Username, b.rights, c.VehicleName FROM fbuser a, "
              + "gbuser b, fbvehicle c WHERE a.gbUserID=b.gbUserID AND a.VehicleID=c.VehicleID" + strData;
    }
    else
    if(strParams1.matches("VehicleRole"))
    {
      if(strParams2.matches("All"))
        strData = "";
      else
        strData = " AND VehicleID=" + strParams2;
        
      strQuery = "SELECT a.VehicleID, a.VehicleName, a.Type, a.Weight, a.DefDivCode, a.StartLocation, "
                + "a.remark, a.Description FROM fbvehicle a WHERE VehicleID<>0" + strData + ";";

      strQuery += "SELECT b.Id, b.RoleName, b.DisplayName, b.MobileMenuID, b.DivisiID, b.RightsID, "
                + "b.Description FROM fbrole b" + strData;
    }
    
    logger.log(Level.SEVERE, "[Query Data] -> {0}", strQuery);

    try
    {
      CallableStatement cs = conn.prepareCall(strQuery);
      boolean isResultSet = cs.execute();

      if (!isResultSet)
      {
        logger.log(Level.SEVERE, "[Query Data] -> {0}", "Masuk sini 4");
        sendRsp.setCode(FixValue.intResponFail);
        sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intFail, FixMessege.strAmbilDataFailed));
        return sendRsp;
      }

      res = cs.getResultSet();
      res.last();
      rows = res.getRow();
      logger.severe("[Rows] -> " + rows);

      if(rows == 0)
      {
        sendRsp.setCode(FixValue.intResponFail);
        sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intFail, FixMessege.strAmbilDataKosong));
      }
      else
      {
        sendRsp.setCode(FixValue.intResponSuccess);
        
        if(strParams1.matches("Users"))
          sendRsp.setRsp(rspMsg.DataUserLoginMsgResponse(FixValue.intSuccess, FixMessege.strAmbilDataSuccess, res, rows));
        else
        if(strParams1.matches("VehicleRole"))
          sendRsp.setRsp(rspMsg.DataVehicleMsgResponse(FixValue.intSuccess, FixMessege.strAmbilDataSuccess, res, rows, cs, logger));
			}
    }
    catch (SQLException ex)
    {
      sendRsp.setCode(FixValue.intResponFail);
      sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intFail, FixMessege.strAmbilDataFailed));
    }

    return sendRsp;
  }  

  public StatusHolder AmbilData(String strParams)
  {
    Integer rows;
    rspMsg = new ResponseMessege();
    sendRsp = new StatusHolder();
    StatusHolder sendRsp = new StatusHolder();
   
    if(strParams.matches("VehicleRole"))
    {
      logger.log(Level.SEVERE, "[Ambil data vehicle] -> {0}", "All");
      sendRsp = AmbilSemuaData(strParams, "All");
    }

    return sendRsp;
  }  
}

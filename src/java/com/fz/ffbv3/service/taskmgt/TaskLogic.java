/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.taskmgt;

import com.fz.generic.StatusHolder;
import com.fz.util.FixValue;
import com.fz.generic.ResponseMessege;
import com.fz.util.FixMessege;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Agustinus Ignat
 */
public class TaskLogic
{              
  private Logger logger;

  private Statement st;
  private ResultSet res;
  private String strQuery;
  private ResponseMessege rspMsg;
  private StatusHolder sendRsp;
  private final Connection conn;
  private final Integer TruckID;
  Gson gson;

  public TaskLogic(Connection conn, Integer TruckID, Logger logdata)
  {
    this.conn = conn;
    this.TruckID = TruckID;
    this.logger = logdata;
  }
  
  public Integer getJobID()
  {
    Integer rows;
    Integer JobID;
    
    strQuery = "SELECT JobID FROM fbjob WHERE DATE(hvsDt) = CURDATE() AND ActualTruckID=" + TruckID + " AND DoneStatus = 'ASGN'";
    logger.severe("[Query JobID] -> " + strQuery);

    try
    {
      st = conn.createStatement();
  	  res = st.executeQuery(strQuery);
      logger.severe("[Execute] -> Execute done");

      res.last();
      rows = res.getRow();
      logger.severe("[Rows] -> " + rows);
      
      if(rows == 0)
        JobID = -1;
      else
        JobID = res.getInt("JobID");
    }
    catch (SQLException ex)
    {
      JobID = -1;
    }
   
    strQuery = "SELECT canGoHome FROM fbvehicle WHERE VehicleID=" + TruckID;
    logger.severe("[Query VehicleID] -> " + strQuery);

    try
    {
      st = conn.createStatement();
  	  res = st.executeQuery(strQuery);
      logger.severe("[Execute] -> Execute done");

      res.last();
      rows = res.getRow();
      logger.severe("[Rows] -> " + rows);

			if(JobID == -1)
			{
				if(res.getInt("canGoHome") == 1)
		      JobID = -2;
			}
    }
    catch (SQLException ex)
    {
      JobID = -1;
    }

		return JobID;
  }
  
  public StatusHolder TaskList(Integer JobID)
  {
    Integer rows;
    rspMsg = new ResponseMessege();
    sendRsp = new StatusHolder();
   
    strQuery = "SELECT a.divID, a.JobID, a.dirLoc as BinLocation, a.remark as BinEmpty, b.TaskID, a.RequesterID, b.From1 as 'From', b.To1 as 'To', DATE_FORMAT(b.PlanStart, " +
               "'%Y-%m-%d %H:%i:%s') as Start, DATE_FORMAT(b.PlanEnd, '%Y-%m-%d %H:%i:%s') as End, b.Tonnage, b.Blocks, c.canGoHome, e.Name " +
               "FROM fbjob AS a, fbtask2 AS b, fbvehicle as c, gbuser as d, fbuser as e WHERE a.JobID=b.JobID AND a.ActualTruckID=c.VehicleID AND " +
               "a.RequesterID=d.gbUserID AND d.gbUserID=e.gbUserID AND a.ActualTruckID=" + TruckID + " AND a.JobID=" + JobID;

    logger.severe("[Query fbjob] -> " + strQuery);

    try
    {
      st = conn.createStatement();
  	  res = st.executeQuery(strQuery);
      logger.severe("[Execute] -> Execute done");

      res.last();
      rows = res.getRow();
      logger.severe("[Rows] -> " + rows);
      
      if(rows == 0)
      {
        sendRsp.setCode(FixValue.intResponFail);
        sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intFail, FixMessege.strTaskEmpty));
      }
      else
      {			
        sendRsp.setCode(FixValue.intResponSuccess);
        sendRsp.setRsp(rspMsg.TaskListMsgResponse(conn, FixValue.intSuccess, FixMessege.strTaskSuccess, res, rows));
        strQuery = "UPDATE fbjob SET takenDt=CURRENT_TIMESTAMP(), DoneStatus=\"TKEN\" WHERE JobID=" + JobID;

        logger.severe("[UPDATE fbjob] -> " + strQuery);

        UpdateJobByJobID(strQuery);
				UpdateVehicleByVehicleID(TruckID, "TKEN");
      }
    }
    catch (SQLException ex)
    {
      sendRsp.setCode(FixValue.intResponFail);
      sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intFail, FixMessege.strTaskFailed));
    }

    return sendRsp;
  }  
  
  private boolean UpdateJobByJobID(String strField)
  {
    boolean success=true;
    logger.severe("[Query UpdateJobByJobID] -> " + strField);

    try
    {
      st = conn.createStatement();
  	  st.execute(strField);
      logger.severe("[UpdateJobByJobID] -> Execute done");
    }
    catch (SQLException ex)
    {
      success=false;
      logger.severe("[UpdateJobByJobID] -> " + ex.getMessage());
    }
    
    try {
      st.close();
    } catch (SQLException ex) {
      logger.severe("[UpdateJobByJobID] -> " + ex.getMessage());
    }
    
    return success;
  }

  public StatusHolder TaskListUpload(UploadModel uploadModel)
  {
    rspMsg = new ResponseMessege();
    sendRsp = new StatusHolder();

    boolean success=true;
    UploadPlanData uploadPlanData = new UploadPlanData();
    
    if(uploadModel.getUploadData().isEmpty())
    {
      sendRsp.setCode(FixValue.intResponFail);
      sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intFail, FixMessege.strUploadEmpty));
      success = false;
      logger.severe("[Update fbtask2] -> All data empty");
    }
    else
    {
      for (int i=0; i<uploadModel.getUploadData().size(); i++)
      {
        uploadPlanData = uploadModel.getUploadData().get(i);
      
        if(uploadPlanData.getActualStart().trim().matches("") || uploadPlanData.getActualEnd().trim().matches(""))
        {
          if(uploadPlanData.getActualStart().trim().matches(""))
            logger.severe("[Update fbtask2] -> ActualStart empty");
          else
            logger.severe("[Update fbtask2] -> ActualEnd empty");

          success=false;
          break;
        }        
      }
    }
      
    if(success)
    {
      Integer mJobID = uploadModel.getUploadData().get(0).getJobID();
      Integer VehicleID = uploadModel.getUploadData().get(0).getVehicleID();
      
      strQuery="UPDATE fbtask2 SET ActualStart = (CASE TaskID WHEN " + uploadModel.getUploadData().get(0).getTaskID() + " THEN \"";
      strQuery += uploadModel.getUploadData().get(0).getActualStart() + "\" WHEN " + uploadModel.getUploadData().get(1).getTaskID() + " THEN \"";
      strQuery += uploadModel.getUploadData().get(1).getActualStart() + "\" END), ";
      
      strQuery += "ActualEnd = (CASE TaskID WHEN " + uploadModel.getUploadData().get(0).getTaskID() + " THEN \"";
      strQuery += uploadModel.getUploadData().get(0).getActualEnd() + "\" WHEN " + uploadModel.getUploadData().get(1).getTaskID() + " THEN \"";
      strQuery += uploadModel.getUploadData().get(1).getActualEnd() + "\" END), ";

      strQuery += "ReasonState = (CASE TaskID WHEN " + uploadModel.getUploadData().get(0).getTaskID() + " THEN ";
      strQuery += uploadModel.getUploadData().get(0).getReasonState() + " WHEN " + uploadModel.getUploadData().get(1).getTaskID() + " THEN ";
      strQuery += uploadModel.getUploadData().get(1).getReasonState() + " END), ";

      strQuery += "ReasonID = (CASE TaskID WHEN " + uploadModel.getUploadData().get(0).getTaskID() + " THEN ";
      strQuery += uploadModel.getUploadData().get(0).getReasonID() + " WHEN " + uploadModel.getUploadData().get(1).getTaskID() + " THEN ";
      strQuery += uploadModel.getUploadData().get(1).getReasonID() + " END) ";

			strQuery += "WHERE TaskID IN (" + uploadModel.getUploadData().get(0).getTaskID() + ", " + uploadModel.getUploadData().get(1).getTaskID() + 
                   ") AND JobID=" + mJobID;

			logger.severe("[Query Update fbtask2] -> " + strQuery);

      try
      {
        st = conn.createStatement();
        st.execute(strQuery);
        logger.severe("[Query Update fbtask2] -> Execute done");
      }
      catch (SQLException ex)
      {
        success = false;
        sendRsp.setCode(FixValue.intResponFail);
        sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intFail, FixMessege.strUploadFailed));
        logger.severe("[Query Update fbtask2] -> " + ex.getMessage());
      }
      
      try {
        st.close();
      } catch (SQLException ex) {
        logger.severe("[Query Update fbtask2] -> " + ex.getMessage());
      }

      if(success)
      {
        if(UpdateJobByJobID("UPDATE fbjob SET doneDt=CURRENT_TIMESTAMP(), DoneStatus=\"DONE\", ActualKg=" + uploadModel.getActualKg() + " WHERE JobID=" + mJobID))
        {
          String strStatus;
          Integer intStatus = uploadModel.getUploadData().get(1).getReasonState();
          
          if(intStatus == -1)
            strStatus = "Unknown";
          else
          if(intStatus == 0)
            strStatus = "DONE";
          else
          if(intStatus == 1)
            strStatus = "STOP";
          else
          if(intStatus == 2)
            strStatus = "LATE";
          else
            strStatus = FixMessege.strStatusUploadDataSuccess;
          
          sendRsp.setCode(FixValue.intResponSuccess);
					sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intSuccess, strStatus));
          UpdateVehicleByVehicleID(VehicleID, "AVLB");
        }
        else
        {
          sendRsp.setCode(FixValue.intResponFail);
          sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intFail, FixMessege.strUploadFailed));
        }		
      }
    }
    else
    {
      sendRsp.setCode(FixValue.intResponFail);
      sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intFail, FixMessege.strUploadFailed));
    }
    
    return sendRsp;
  }  

  private void UpdateVehicleByVehicleID(Integer VehicleID, String Status)
  {
    strQuery = "UPDATE fbvehicle SET Status=\"" + Status + "\" WHERE VehicleID=" + VehicleID;
    logger.severe("[UPDATE fbvehicle] -> " + strQuery);

    try
    {
      st = conn.createStatement();
  	  st.execute(strQuery);
      logger.severe("[UpdateVehicleByVehicleID] -> Execute done");
    }
    catch (SQLException ex)
    {
      logger.severe("[UpdateVehicleByVehicleID] -> " + ex.getMessage());
    }
    
    try {
      st.close();
    } catch (SQLException ex) {
      logger.severe("[UpdateVehicleByVehicleID] -> " + ex.getMessage());
    }
  }
	
  public StatusHolder MobileJobState(TaskPlanModel taskPlanModel)
  {
    Integer rows;
    rspMsg = new ResponseMessege();
    sendRsp = new StatusHolder();
   
    strQuery = "SELECT b.ReasonState FROM fbjob a, fbtask2 b WHERE a.JobID=b.JobID AND TaskSeq=2 AND a.JobID=" +
							 taskPlanModel.getJobStateData().getJobID() + " AND a.ActualTruckID=" +
               taskPlanModel.getJobStateData().getVehicleID();

    logger.severe("[Query fbjob] -> " + strQuery);

    try
    {
      st = conn.createStatement();
  	  res = st.executeQuery(strQuery);
      logger.severe("[Execute] -> Execute done");

      res.last();
      rows = res.getRow();
      logger.severe("[Rows] -> " + rows);
      
      if(rows == 0)
      {
        sendRsp.setCode(FixValue.intResponFail);
        sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intFail, FixMessege.strMobileJobStateFailed));
      }
      else
      {			
        sendRsp.setCode(FixValue.intResponSuccess);
        sendRsp.setRsp(rspMsg.MobileJobStateMsgResponse(conn, FixValue.intSuccess, FixMessege.strMobileJobStateSuccess, res, rows));
      }
    }
    catch (SQLException ex)
    {
      sendRsp.setCode(FixValue.intResponFail);
      sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intFail, FixMessege.strMobileJobStateFailed));
    }

    return sendRsp;
  }  

  public StatusHolder MobileHistoryState(TaskPlanModel taskPlanModel)
  {
    Integer rows;
    rspMsg = new ResponseMessege();
    sendRsp = new StatusHolder();
   
		strQuery = "SELECT CONCAT(\"[\", GROUP_CONCAT(JSON_OBJECT(\n" +
							 "	\"JobSeq\", a.JobSeq,\n" +
							 "	\"divID\", a.divID,\n" +
							 "	\"EstmKg\", a.EstmKg,\n" +
							 "	\"ActualKg\", a.ActualKg,\n" +
							 "	\"takenDt\", a.takenDt,\n" +
							 "	\"ActualEnd\", b.ActualEnd,\n" +
							 "	\"Blocks\", b.Blocks\n" +
							 ")), \"]\") AS History\n" +
							 "FROM fbjob a\n" +
							 "LEFT JOIN fbtask2 b ON a.JobID = b.JobID\n" +
							 "WHERE\n" +
							 "a.DoneStatus = \"DONE\"\n" +
							 "AND TaskSeq = 2\n" +
							 "AND a.ActualTruckID = " + taskPlanModel.getJobStateData().getVehicleID() + "\n" +
							 "AND convert(b.ActualEnd,Date) = \"" + taskPlanModel.getJobStateData().getActualEnd() + "\"";

    logger.severe("[Query fbjob] -> " + strQuery);

    try
    {
      st = conn.createStatement();
  	  res = st.executeQuery(strQuery);
      logger.severe("[Execute] -> Execute done");

      res.last();
      rows = res.getRow();
      logger.severe("[Rows] -> " + rows);
      
      if((rows == 0) || (res.getString("History") == null))
      {
        sendRsp.setCode(FixValue.intResponFail);
        sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intFail, FixMessege.strMobileHistoryStateEmpty));
      }
      else
      {			
        sendRsp.setCode(FixValue.intResponSuccess);
        sendRsp.setRsp(rspMsg.MobileJobHistoryMsgResponse(conn, FixValue.intSuccess, FixMessege.strMobileHistoryStateSuccess, res, rows));
      }
    }
    catch (SQLException ex)
    {
			logger.severe(ex.getMessage());
      sendRsp.setCode(FixValue.intResponFail);
      sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intFail, FixMessege.strMobileHistoryStateFailed));
    }

    return sendRsp;
  }  
}

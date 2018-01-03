/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.entrymgt;

import com.fz.ffbv3.service.usermgt.*;
import com.fz.generic.Db;
import com.fz.generic.StatusHolder;
import com.fz.util.FixValue;
import com.fz.generic.ResponseMessege;
import com.fz.util.FZUtil;
import com.fz.util.FixMessege;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.sql.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Agustinus Ignat
 */
public class EntryLogic
{              
  private Integer rows, rows1, rows2;
  private Logger logger;

  private Statement st;
	private ResultSet res, res1, res2;
  private String strQuery;
  private ResponseMessege rspMsg;
  private StatusHolder sendRsp;
  private final Connection conn;
  private FileHandler fh = null;

  public EntryLogic(Connection conn, Logger logdata)
  {
    this.conn = conn;
    this.logger = logdata;
  }

  public StatusHolder DataEntry(Integer intEntry)
	{
    rspMsg = new ResponseMessege();
    sendRsp = new StatusHolder();
   
		if(intEntry == 1)
		{
		  strQuery = "SELECT estimation FROM fbpanen";
	    logger.severe("[SELECT fbpanen] -> " + strQuery);
		}
		else
		if(intEntry == 2)
		{
			strQuery = "SELECT direction FROM fbdirection";
			logger.severe("[SELECT fbdirection] -> " + strQuery);
		}

    try
    {
      st = conn.createStatement();
			res = st.executeQuery(strQuery);

			if(intEntry == 1)
				logger.severe("[Execute fbpanen] -> Execute done");
			else
			if(intEntry == 2)
				logger.severe("[Execute fbdirection] -> Execute done");

      res.last();
      rows = res.getRow();

			if(intEntry == 1)
				logger.severe("[Rows fbpanen] -> " + rows);
			else
			if(intEntry == 2)
				logger.severe("[Rows fbdirection] -> " + rows);
			
			if(rows == 0)
      {
        sendRsp.setCode(FixValue.intResponFail);
				
				if(intEntry == 1)
	        sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intFail, FixMessege.strDataEstEmpty));
				else
				if(intEntry == 2)
	        sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intFail, FixMessege.strDataJobEmpty));
      }
      else
      {
        sendRsp.setCode(FixValue.intResponSuccess);

				if(intEntry == 1)
					sendRsp.setRsp(rspMsg.DataEntryResponse(FixValue.intSuccess, FixMessege.strDataEstSuccess, res, rows, intEntry));
				else
				if(intEntry == 2)
					sendRsp.setRsp(rspMsg.DataEntryResponse(FixValue.intSuccess, FixMessege.strDataJobSuccess, res, rows, intEntry));
			}
    }
    catch (SQLException ex)
    {
      sendRsp.setCode(FixValue.intResponFail);

			if(intEntry == 1)
				sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intFail, FixMessege.strDataEstFailed));
			else
			if(intEntry == 2)
				sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intFail, FixMessege.strDataJobFailed));
    }

    return sendRsp;
	}

  public StatusHolder JobEntry(EntryModel entryModel)
	{
    rspMsg = new ResponseMessege();
    sendRsp = new StatusHolder();

    // get input
    String hvsDate = (new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));
    String divID = entryModel.getJobData().getDivID().trim().toUpperCase();
    String block1 = entryModel.getJobData().getBlock1().trim().toUpperCase();
    String block2 = entryModel.getJobData().getBlock2().trim().toUpperCase();
    String readyTime = entryModel.getJobData().getReadyTime().trim();

    String estmKg = entryModel.getJobData().getEstmKg().toString();
    String dirLoc = entryModel.getJobData().getDirLoc();
    String isLastOrder = entryModel.getJobData().getIsLastOrder();
    String remark = entryModel.getJobData().getRemark();

    Integer userID = entryModel.getJobData().getUserID();
		
		if(validateClock(readyTime) == -1)
		{
      sendRsp.setCode(FixValue.intResponFail);
			sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intFail, FixMessege.strBinReadyFailed));
		}		
		else
		if (!(isLastOrder.equals("yes") ||  isLastOrder.equals("no"))) 
		{
      sendRsp.setCode(FixValue.intResponFail);
			sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intFail, FixMessege.strBinReadyFailed));
		}
		else
    if (remark.trim().length() == 0) 
		{
      sendRsp.setCode(FixValue.intResponFail);
			sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intFail, FixMessege.strRemarkFailed));
		}
		else
		{
//			Auth.check(pc, "Div_" + divID + ";Order_Edit", true);
        
      String block2Criteria = "";

			if (block2.trim().length() > 0)
        block2Criteria = " and betweenBlock2 = '" + block2 + "'";
        
      // check similar job
      strQuery = "select JobID from fbJob "
                 + " where "
                 + " hvsDt = '" + hvsDate + "'"
                 + " and divID = '" + divID + "'"
                 + " and betweenBlock1 = '" + block1 + "'"
                 + block2Criteria
                 + " and dirLoc = '" + dirLoc + "'"
                 + " and doneStatus <> 'PLAN'"
                 + " and reorderToJobID is null";
						
			logger.severe("[Query fbJob] -> " + strQuery);

			try
			{
				st = conn.createStatement();
				res = st.executeQuery(strQuery);
				logger.severe("[Execute Vehicle] -> Execute done");

				res.last();				
				rows = res.getRow();
				logger.severe("[Rows JobID] -> " + rows);

				if(rows != 0)
				{
					sendRsp.setCode(FixValue.intResponFail);
					sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intFail, FixMessege.strDuplicateIDFailed + res.getInt("JobID")));
				}
				else
				{
					// insert
					strQuery = "insert into fbjob("
									+ "doneStatus"
									+ ", divID"
									+ ", betweenBlock1"
									+ ", betweenBlock2"
									+ ", readyTime"
                  + ", EstmKg"
                  + ", createDt"
                  + ", hvsDt"
                  + ", requesterID"
                  + ", createSource"
                  + ", runID"
                  + ", dirLoc"
                  + ", remark"
                  + ", isLastOrder"
                  + ")"
                  + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

					PreparedStatement ps = conn.prepareStatement(strQuery);

          // get sequence number of job for a division, and runID
          StringBuffer retVal = new StringBuffer();
          getSeqAndRunID(hvsDate, divID, conn, retVal);
					
					String[] seqAndRunID = retVal.toString().split(";");
          String runID = seqAndRunID[0];
					logger.severe("[Isi RunID] -> " + runID);

          int divblock = validateDivBlock(divID, block1, conn);
          userID = validateGBUser(userID);
					
					if((divblock == 0) || (userID == 0))
					{
						sendRsp.setCode(FixValue.intResponFail);
						sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intFail, FixMessege.strBlockEmptyFailed));
					}
					else
					if((divblock == -1) || (userID == -1))
					{
						sendRsp.setCode(FixValue.intResponFail);
						sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intFail, FixMessege.strDataJobEntryFailed));
					}
					else
					{
	          String todayStr = FZUtil.toDateString(new java.util.Date(), "yyyy-MM-dd");

						try
						{
			        int i=1;
				      ps.setString(i++, "NEW");
					    ps.setString(i++, divID);
					    ps.setString(i++, block1);
					    ps.setString(i++, block2);
						  ps.setString(i++, readyTime);
							ps.setString(i++, estmKg);
							ps.setTimestamp(i++, FZUtil.getCurSQLTimeStamp());
				      ps.setTimestamp(i++, FZUtil.toSQLTimeStamp(todayStr, "yyyy-MM-dd"));
		          ps.setString(i++, userID.toString());
			        ps.setString(i++, "ORDR");
				      ps.setString(i++, runID);
					    ps.setString(i++, dirLoc);
						  ps.setString(i++, remark);
							ps.setString(i++, isLastOrder);

							ps.executeUpdate();
							logger.severe("[Insert fbJob] -> " + ps.toString());

							sendRsp.setCode(FixValue.intResponSuccess);
							sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intSuccess, FixMessege.strDataJobEntrySuccess));
						}
						catch (Exception ex)
						{
							sendRsp.setCode(FixValue.intResponFail);
							sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intFail, FixMessege.strDataJobEntryFailed));
						}
					}
				}
			}
			catch (SQLException ex)
			{
				sendRsp.setCode(FixValue.intResponFail);
				sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intFail, FixMessege.strLoginFailed));
			}
		}

		return sendRsp;
	}

  private int validateClock(String readyTime) 
	{
		int readyTimeInt = FZUtil.clockToMin(readyTime);
    if ((readyTimeInt < 0) || (readyTimeInt > 24 * 60))
			return -1;

		return readyTimeInt;
  }

  private int validateDivBlock(String divID, String block1, Connection con)
	{
    String sql = "select blockID "
                + " from fbBlock b"
                + " where "
                + "     blockID = '" + block1 + "'"
                + "     and divID = '" + divID + "'";
        
		logger.severe("[Query fbBlock] -> " + sql);

		try
		{
			res = st.executeQuery(sql);
			logger.severe("[Execute fbBlock] -> Execute done");

			res.last();				
			rows = res.getRow();
			logger.severe("[Rows fbBlock] -> " + rows);
			return rows;
		}
		catch (SQLException ex)
		{
			return -1;
		}
  }

  private void getSeqAndRunID(String hvsDate, String divID, Connection con, StringBuffer retVal)
	{
    String sql = "select max(runID) as RunID"
              + " from fbJob"
              + " where hvsDt = '" + hvsDate + "'"
              + "     and divID = '" + divID + "'"
              + "     and reorderToJobID is null";
				
		logger.severe("[Query fbJob] -> " + sql);

		String runID = "";

		try
		{
			res = st.executeQuery(sql);
			logger.severe("[Execute fbJob] -> Execute done");

			res.last();				
			rows = res.getRow();
			logger.severe("[Rows JobID] -> " + rows);
      runID = res.getString("RunID");
			
			if(runID == null)
				runID = "-2";
		}
		catch (SQLException ex)
		{
      runID = "-1";
		}

		retVal.append(runID);
  }

  private int validateGBUser(Integer UserID)
	{
    String sql = "SELECT gbUserID "
                + " FROM fbuser"
                + " WHERE UserID=" + UserID;
        
		logger.severe("[Query fbuser] -> " + sql);

		try
		{
			res = st.executeQuery(sql);
			logger.severe("[Execute fbuser] -> Execute done");

			res.last();				
			rows = res.getRow();
			logger.severe("[Rows fbuser] -> " + rows);
			
			if(rows != 0)
				return res.getInt("gbUserID");
			else
				return rows;
		}
		catch (SQLException ex)
		{
			return -1;
		}
  }
}

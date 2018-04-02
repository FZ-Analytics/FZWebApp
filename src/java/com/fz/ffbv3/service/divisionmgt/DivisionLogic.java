/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.divisionmgt;

import com.fz.ffbv3.service.usermgt.*;
import com.fz.generic.Db;
import com.fz.generic.StatusHolder;
import com.fz.util.FixValue;
import com.fz.generic.ResponseMessege;
import com.fz.util.FixMessege;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Agustinus Ignat
 */
public class DivisionLogic
{              
  private Logger logger;
	private Statement st;
	private ResultSet res;
  private String strQuery;
  private ResponseMessege rspMsg;
  private StatusHolder sendRsp;
  private Connection conn;

  public DivisionLogic(Connection conn, Logger logger)
  {
    this.logger = logger;
    this.conn = conn;
  }

  public StatusHolder DashboarPerDivisi(DivisionData divisionData)
  {
    Integer rows;
    rspMsg = new ResponseMessege();
    sendRsp = new StatusHolder();
   
    strQuery = "select \n" +
"		a.millID,a.estateID,a.divID,b.*,c.Kgs KgsTax, \n" +
"               (case when ifnull(b.TripsCount,0)=0 then 0.00 else ifnull(b.ActualKgs,0)/ifnull(b.TripsCount,0) end) avgTrip, \n" +
"               (case when ifnull(c.Kgs,0)=0 then 0 else ifnull(b.ActualKgs,0)/c.Kgs end)*100 avgTax \n" + 
"	from fbdiv a\n" +
"	left join ( \n" +
"		select \n" +
"                       ba.divID, COUNT(*) TripsCount, sum(ba.ActualKg) ActualKgs, \n" +
"          		SUM( CASE WHEN CONVERT( bb.ActualEnd, TIME )< '10:00:00' THEN ifnull(ba.ActualKg,0) ELSE 0 END ) kg1,     \n" +
"          		SUM( CASE WHEN CONVERT( bb.ActualEnd, TIME )>= '10:00:00' and CONVERT( bb.ActualEnd, TIME )< '14:00:00' THEN ifnull(ba.ActualKg,0) ELSE 0 END ) kg2,     \n" +
"          		SUM( CASE WHEN CONVERT( bb.ActualEnd, TIME )>= '14:00:00' and CONVERT( bb.ActualEnd, TIME )< '18:00:00' THEN ifnull(ba.ActualKg,0) ELSE 0 END ) kg3,\n" +
"          		SUM( CASE WHEN CONVERT( bb.ActualEnd, TIME )>= '18:00:00' THEN ifnull(ba.ActualKg,0) ELSE 0 END ) kg4,\n" +
"          		SUM( CASE WHEN CONVERT( bb.ActualEnd, TIME )< '10:00:00' THEN 1 ELSE 0 END ) trip1,     \n" +
"          		SUM( CASE WHEN CONVERT( bb.ActualEnd, TIME )>= '10:00:00' and CONVERT( bb.ActualEnd, TIME )< '14:00:00' THEN 1 ELSE 0 END ) trip2,     \n" +
"          		SUM( CASE WHEN CONVERT( bb.ActualEnd, TIME )>= '14:00:00' and CONVERT( bb.ActualEnd, TIME )< '18:00:00' THEN 1 ELSE 0 END ) trip3,\n" +
"          		SUM( CASE WHEN CONVERT( bb.ActualEnd, TIME )>= '18:00:00' THEN 1 ELSE 0 END ) trip4\n" +
"		from fbjob ba \n" +
"		left join fbtask2 bb on ba.JobID=bb.JobID\n" +
"		where CONVERT(ba.hvsDt, DATE)='" + divisionData.getTanggal() + "'\n" +
"          		AND ba.DoneStatus = 'DONE' and ba.reorderToJobID is null \n" +
"          		AND bb.TaskSeq = 2     \n" +
"		group by ba.divID\n" +
"	) b on a.divID=b.divID\n" +
"	LEFT JOIN(     \n" +
"		SELECT ca.divID, SUM( cb.size1 ) Kgs     \n" +
"		FROM fbhvsestm ca     \n" +
"		LEFT JOIN fbhvsestmdtl cb ON ca.HvsEstmID = cb.hvsEstmID \n" +
"		WHERE \n" +
"			CONVERT(ca.hvsDt,DATE)='" + divisionData.getTanggal() + "'\n" +
"			AND ca.status = 'FNAL' \n" +
"		GROUP BY ca.divID \n" +
"	) c ON a.divID = c.divID     \n" +
"       where a.divID='" + divisionData.getDivisi() +"'\n" +
"	order by a.millID,a.estateID,a.divID";

    logger.severe("[Query Vehicle] -> " + strQuery);

    try
    {
      st = conn.createStatement();
  	  res = st.executeQuery(strQuery);

      res.last();
      rows = res.getRow();
      
      if(rows == 0)
      {
        sendRsp.setCode(FixValue.intResponFail);
        sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intFail, FixMessege.strDashboardDivisiEmpty));
      }
      else
      {
        sendRsp.setCode(FixValue.intResponSuccess);
        sendRsp.setRsp(rspMsg.DashbordDivisiMsgResponse(FixValue.intSuccess, FixMessege.strDashboardDivisiSuccess, res, rows));
			}
    }
    catch (SQLException ex)
    {
      sendRsp.setCode(FixValue.intResponFail);
      sendRsp.setRsp(rspMsg.CoreMsgResponse(FixValue.intFail, FixMessege.strDashboardDivisiFailed));
      logger.log(Level.SEVERE, "[Stack Trace] -> {0}", ex.toString());
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
    }
    catch (SQLException ex)
    {
      success = false;
      System.out.println(ex.getMessage());
    }
    
    return success;
	}
}

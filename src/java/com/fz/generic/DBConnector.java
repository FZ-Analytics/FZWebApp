/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.generic;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Agustinus Ignat
 */
public class DBConnector
{  
  public Connection ConnectToDatabase()
  {
	  Connection conn = null;

    try 
    {	  
		  conn = (new Db()).getConnection("jdbc/fz");
    }
    catch (Exception e)
    {
      // e.printStackTrace();
  	}

    return conn;
  }

  public void CloseDatabase(Connection conn)
  {
    try
    {
      conn.close();
    } catch (SQLException ex)
    {
      Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}

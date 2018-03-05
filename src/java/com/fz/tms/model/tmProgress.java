/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.model;

import java.sql.Date;

/**
 *
 * @author dwi.rangga
 */
public class tmProgress {
   public String runID;
   public String status;
   public String mustFinish;
   public int iter;
   public int maxIter;
   public int subIter;
   public int maxSubIter;
   public String msg;
   public int pct;
   public Date lastUpd;
   public Date created;

}

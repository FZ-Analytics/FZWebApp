/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.reasonmgt;

import com.fz.ffbv3.service.usermgt.*;

/**
 *
 * @author Agustinus Ignat
 */
public class ReasonModule
{
  private int ReasonID;
  private String ReasonName;
  private String ReasonDesc;

  public int getReasonID()
  {
    return ReasonID;
  }

  public void setReasonID(int ReasonID)
  {
    this.ReasonID = ReasonID;
  }

  public String getReasonName()
  {
    return ReasonName;
  }

  public void setReasonName(String ReasonName)
  {
    this.ReasonName = ReasonName;
  }

  public String getReasonDesc()
  {
    return ReasonDesc;
  }

  public void setReasonDesc(String ReasonDesc)
  {
    this.ReasonDesc = ReasonDesc;
  }

  
}

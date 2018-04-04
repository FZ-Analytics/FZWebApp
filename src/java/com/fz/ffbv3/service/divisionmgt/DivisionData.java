/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.divisionmgt;

import com.fz.ffbv3.service.usermgt.*;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Agustinus Ignat
 */
public class DivisionData
{
  @SerializedName("MillID")
  @Expose
  private String MillID;
  @SerializedName("Estate")
  @Expose
  private String Estate;
  @SerializedName("Divisi")
  @Expose
  private String Divisi;
  @SerializedName("Tanggal")
  @Expose
  private String Tanggal;

  public String getMillID() {
    return MillID;
  }

  public void setMillID(String MillID) {
    this.MillID = MillID;
  }

  public String getEstate() {
    return Estate;
  }

  public void setEstate(String Estate) {
    this.Estate = Estate;
  }

  public String getDivisi() {
    return Divisi;
  }

  public void setDivisi(String Divisi) {
    this.Divisi = Divisi;
  }

  public String getTanggal() {
    return Tanggal;
  }

  public void setTanggal(String Tanggal) {
    this.Tanggal = Tanggal;
  } 
}

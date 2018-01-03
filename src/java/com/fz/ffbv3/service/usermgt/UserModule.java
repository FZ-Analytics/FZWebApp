/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.usermgt;

/**
 *
 * @author Agustinus Ignat
 */
public class UserModule
{
  private Integer UserID;
  private String Name;
  private String Phone;
  private Integer lnkRoleID;
  private String Brand; 
  private String Type;
  private Integer VehicleID;
  private String VehicleName;
  private long TimeTrackLocation;

  public Integer getUserID()
  {
    return UserID;
  }

  public void setUserID(Integer UserID)
  {
    this.UserID = UserID;
  }

  public String getName()
  {
    return Name;
  }

  public void setName(String Name)
  {
    this.Name = Name;
  }

  public String getPhone()
  {
    return Phone;
  }

  public void setPhone(String Phone)
  {
    this.Phone = Phone;
  }

  public Integer getLnkRoleID()
  {
    return lnkRoleID;
  }

  public void setLnkRoleID(Integer lnkRoleID)
  {
    this.lnkRoleID = lnkRoleID;
  }

  public String getBrand()
  {
    return Brand;
  }

  public void setBrand(String Brand)
  {
    this.Brand = Brand;
  }

  public String getType()
  {
    return Type;
  }

  public void setType(String Type)
  {
    this.Type = Type;
  }

  public Integer getVehicleID()
  {
    return VehicleID;
  }

  public void setVehicleID(Integer VehicleID)
  {
    this.VehicleID = VehicleID;
  }

  public String getVehicleName()
  {
    return VehicleName;
  }

  public void setVehicleName(String VehicleName)
  {
    this.VehicleName = VehicleName;
  }

  public long getTimeTrackLocation()
  {
    return TimeTrackLocation;
  }

  public void setTimeTrackLocation(long TimeTrackLocation)
  {
    this.TimeTrackLocation = TimeTrackLocation;
  }

}

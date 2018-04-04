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
  private String Tipe;
  private Integer VehicleID;
  private String VehicleName;
  private long TimeTrackLocation;
  private String DoneStatus;
  private String Divisi;
  private String Estate;
  private String MillID;
  private String Description;
  private String Type;
  private long TimeSyncJob;

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

	public String getDoneStatus() {
		return DoneStatus;
	}

	public void setDoneStatus(String DoneStatus) {
		this.DoneStatus = DoneStatus;
	}	

  public String getDivisi() {
    return Divisi;
  }

  public void setDivisi(String Divisi) {
    this.Divisi = Divisi;
  }

  public String getEstate() {
    return Estate;
  }

  public void setEstate(String Estate) {
    this.Estate = Estate;
  }

  public String getMillID() {
    return MillID;
  }

  public void setMillID(String MillID) {
    this.MillID = MillID;
  }

  public String getDescription() {
    return Description;
  }

  public void setDescription(String Description) {
    this.Description = Description;
  }

  public String getTipe() {
    return Tipe;
  }

  public void setTipe(String Tipe) {
    this.Tipe = Tipe;
  }

  public long getTimeSyncJob() {
    return TimeSyncJob;
  }

  public void setTimeSyncJob(long TimeSyncJob) {
    this.TimeSyncJob = TimeSyncJob;
  }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.trackmgt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Agustinus Ignat
 */
public class TrackingData
{
  @SerializedName("Latitude")
  @Expose
  private String Latitude;
  @SerializedName("Longitude")
  @Expose
  private String Longitude;
  @SerializedName("EndDate")
  @Expose
  private String EndDate;
  @SerializedName("UserID")
  @Expose
  private Integer UserID;
  @SerializedName("VehicleID")
  @Expose
  private Integer VehicleID;
  @SerializedName("Name")
  @Expose
  private String Name;
  @SerializedName("Division")
  @Expose
  private Integer Division;

	public String getLatitude()
	{
		return Latitude;
	}

	public void setLatitude(String Latitude)
	{
		this.Latitude = Latitude;
	}

	public String getLongitude()
	{
		return Longitude;
	}

	public void setLongitude(String Longitude)
	{
		this.Longitude = Longitude;
	}

	public String getEndDate()
	{
		return EndDate;
	}

	public void setEndDate(String EndDate)
	{
		this.EndDate = EndDate;
	}

	public Integer getUserID()
	{
		return UserID;
	}

	public void setUserID(Integer UserID)
	{
		this.UserID = UserID;
	}

	public Integer getVehicleID()
	{
		return VehicleID;
	}

	public void setVehicleID(Integer VehicleID)
	{
		this.VehicleID = VehicleID;
	}

	public String getName()
	{
		return Name;
	}

	public void setName(String Name)
	{
		this.Name = Name;
	}

	public Integer getDivision()
	{
		return Division;
	}

	public void setDivision(Integer Division)
	{
		this.Division = Division;
	}
}

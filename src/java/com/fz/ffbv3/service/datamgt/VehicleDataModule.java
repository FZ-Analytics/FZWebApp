/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.datamgt;

import com.fz.ffbv3.service.usermgt.*;

/**
 *
 * @author Agustinus Ignat
 */
public class VehicleDataModule
{
  private Integer VehicleID;
  private String VehicleName;
  private String Type;
  private Integer Weight;
  private String DefDivCode;
  private String StartLocation;
  private String remark;
  private String Description;

  public Integer getVehicleID() {
    return VehicleID;
  }

  public void setVehicleID(Integer VehicleID) {
    this.VehicleID = VehicleID;
  }

  public String getVehicleName() {
    return VehicleName;
  }

  public void setVehicleName(String VehicleName) {
    this.VehicleName = VehicleName;
  }

  public String getType() {
    return Type;
  }

  public void setType(String Type) {
    this.Type = Type;
  }

  public Integer getWeight() {
    return Weight;
  }

  public void setWeight(Integer Weight) {
    this.Weight = Weight;
  }

  public String getDefDivCode() {
    return DefDivCode;
  }

  public void setDefDivCode(String DefDivCode) {
    this.DefDivCode = DefDivCode;
  }

  public String getStartLocation() {
    return StartLocation;
  }

  public void setStartLocation(String StartLocation) {
    this.StartLocation = StartLocation;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getDescription() {
    return Description;
  }

  public void setDescription(String Description) {
    this.Description = Description;
  }
  
  
}

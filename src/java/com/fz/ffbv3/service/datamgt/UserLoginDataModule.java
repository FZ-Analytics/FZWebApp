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
public class UserLoginDataModule
{
  private Integer UserID;
  private String Name;
  private String Phone;
  private String FullName;
  private String VehicleName;
  private String Username;
  private String Password;
  private String rights;

  public Integer getUserID() {
    return UserID;
  }

  public void setUserID(Integer UserID) {
    this.UserID = UserID;
  }

  public String getName() {
    return Name;
  }

  public void setName(String Name) {
    this.Name = Name;
  }

  public String getVehicleName() {
    return VehicleName;
  }

  public void setVehicleName(String VehicleName) {
    this.VehicleName = VehicleName;
  }

  public String getUsername() {
    return Username;
  }

  public void setUsername(String Username) {
    this.Username = Username;
  }

  public String getPassword() {
    return Password;
  }

  public void setPassword(String Password) {
    this.Password = Password;
  }

  public String getRights() {
    return rights;
  }

  public void setRights(String rights) {
    this.rights = rights;
  }

  public String getPhone() {
    return Phone;
  }

  public void setPhone(String Phone) {
    this.Phone = Phone;
  }

  public String getFullName() {
    return FullName;
  }

  public void setFullName(String FullName) {
    this.FullName = FullName;
  }
  
  
}

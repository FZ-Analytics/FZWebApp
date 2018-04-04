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
public class RoleDataModule
{
  private Integer Id;
  private String RoleName;
  private String DisplayName;
  private String MobileMenuID;
  private String DivisiID;
  private String RightsID;
  private String Description;

  public Integer getId() {
    return Id;
  }

  public void setId(Integer Id) {
    this.Id = Id;
  }

  public String getRoleName() {
    return RoleName;
  }

  public void setRoleName(String RoleName) {
    this.RoleName = RoleName;
  }

  public String getDisplayName() {
    return DisplayName;
  }

  public void setDisplayName(String DisplayName) {
    this.DisplayName = DisplayName;
  }

  public String getMobileMenuID() {
    return MobileMenuID;
  }

  public void setMobileMenuID(String MobileMenuID) {
    this.MobileMenuID = MobileMenuID;
  }

  public String getDivisiID() {
    return DivisiID;
  }

  public void setDivisiID(String DivisiID) {
    this.DivisiID = DivisiID;
  }

  public String getRightsID() {
    return RightsID;
  }

  public void setRightsID(String RightsID) {
    this.RightsID = RightsID;
  }

  

  public String getDescription() {
    return Description;
  }

  public void setDescription(String Description) {
    this.Description = Description;
  }
  
  
}

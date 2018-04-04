/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.datamgt;

import com.fz.ffbv3.service.usermgt.*;
import com.fz.generic.CoreModule;
import com.fz.generic.CoreModule;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Agustinus Ignat
 */
public class UserLoginDataHolder
{
  private final CoreModule CoreResponse;
  List<UserLoginDataModule> UserLoginRsp;
  List<VehicleDataModule> VehicleRsp;
  List<RoleDataModule> RoleRsp;

  public UserLoginDataHolder(CoreModule CoreResponse, List<UserLoginDataModule> UserLoginRsp,
         List<VehicleDataModule> VehicleRsp, List<RoleDataModule> RoleRsp)
  {
    this.CoreResponse = CoreResponse;
    this.UserLoginRsp = UserLoginRsp;
    this.VehicleRsp = VehicleRsp;
    this.RoleRsp = RoleRsp;
  }

  
}

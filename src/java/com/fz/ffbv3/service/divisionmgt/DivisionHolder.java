/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.divisionmgt;

import com.fz.ffbv3.service.usermgt.*;
import com.fz.generic.CoreModule;
import com.fz.generic.CoreModule;

/**
 *
 * @author Agustinus Ignat
 */
public class DivisionHolder
{
  private final CoreModule CoreResponse;
  private final DivisionModule DashboardResponse;

  public DivisionHolder(CoreModule CoreResponse, DivisionModule DashboardResponse)
  {
    this.CoreResponse = CoreResponse;
    this.DashboardResponse = DashboardResponse;
  }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.reasonmgt;

import com.fz.ffbv3.service.usermgt.*;
import com.fz.generic.CoreModule;
import com.fz.generic.CoreModule;
import java.util.List;

/**
 *
 * @author Agustinus Ignat
 */
public class ReasonHolder
{
  private final CoreModule CoreResponse;
  private final List<ReasonModule> ReasonResponse;

  public ReasonHolder(CoreModule CoreResponse, List<ReasonModule> ReasonResponse)
  {
    this.CoreResponse = CoreResponse;
    this.ReasonResponse = ReasonResponse;
  }

  
}

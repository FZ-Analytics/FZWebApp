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
public class DivisionModel
{
  @SerializedName("RequestData")
  @Expose
  private DivisionData RequestData;

  public DivisionData getRequestData() {
    return RequestData;
  }

  public void setRequestData(DivisionData RequestData) {
    this.RequestData = RequestData;
  }
}

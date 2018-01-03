/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.taskmgt;

import com.fz.ffbv3.service.usermgt.*;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 *
 * @author Agustinus Ignat
 */
public class UploadModel
{
  @SerializedName("ActualKg")
  @Expose
  private Integer ActualKg = null;
  @SerializedName("UploadData")
  @Expose
  private List<UploadPlanData> UploadData = null;

  public List<UploadPlanData> getUploadData()
  {
    return UploadData;
  }

  public void setUploadData(List<UploadPlanData> UploadData)
  {
    this.UploadData = UploadData;
  }

	public Integer getActualKg()
	{
		return ActualKg;
	}

	public void setActualKg(Integer ActualKg)
	{
		this.ActualKg = ActualKg;
	}	
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.trackmgt;

import com.fz.ffbv3.service.taskmgt.*;
import com.fz.ffbv3.service.usermgt.*;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 *
 * @author Agustinus Ignat
 */
public class GraberModel
{
	@SerializedName("GraberData")
  @Expose
  private TrackingData graberData;

	public TrackingData getGraberData()
	{
		return graberData;
	}

	public void setGraberData(TrackingData graberData)
	{
		this.graberData = graberData;
	}
	
/*
	@SerializedName("TrackingData")
  @Expose
  private List<TrackingData> UploadData = null;

    public List<TrackingData> getUploadData() {
        return UploadData;
    }

    public void setUploadData(List<TrackingData> UploadData) {
        this.UploadData = UploadData;
    }
*/
}

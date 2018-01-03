/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.vesselMgt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 *
 * @author
 * Agustinus
 * Ignat
 */
public class VesselModel
{
	@SerializedName("gpsVessel")
	@Expose
	private List<GpsVessel> gpsVessel = null;

	public List<GpsVessel> getGpsVessel() 
	{
		return gpsVessel;
	}
	
	public void setGpsVessel(List<GpsVessel> gpsVessel) 
	{
		this.gpsVessel = gpsVessel;	
	}
}

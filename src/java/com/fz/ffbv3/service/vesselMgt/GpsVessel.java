/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.vesselMgt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author
 * Agustinus
 * Ignat
 */
public class GpsVessel
{
	@SerializedName("MMSI")
	@Expose
	private String mMSI;
	@SerializedName("STATUS")
	@Expose
	private String sTATUS;
	@SerializedName("SPEED")
	@Expose
	private String sPEED;
	@SerializedName("LON")
	@Expose
	private String lON;
	@SerializedName("LAT")
	@Expose	
	private String lAT;
	@SerializedName("COURSE")
	@Expose
	private String cOURSE;
	@SerializedName("HEADING")
	@Expose
	private String hEADING;
	@SerializedName("TIMESTAMP")
	@Expose
	private String tIMESTAMP;
	@SerializedName("SHIP_ID")
	@Expose
	private String sHIPID;

	public String getMMSI() {
		return mMSI;
	}

	public void setMMSI(String mMSI) {
		this.mMSI = mMSI;
	}

	public String getSTATUS() {
		return sTATUS;
	}

	public void setSTATUS(String sTATUS) {
		this.sTATUS = sTATUS;
	}

	public String getSPEED() {
		return sPEED;
	}

	public void setSPEED(String sPEED) {
		this.sPEED = sPEED;	
	}

	public String getLON() {
		return lON;
	}

	public void setLON(String lON) {
		this.lON = lON;	
	}

	public String getLAT() {
		return lAT;
	}

	public void setLAT(String lAT) {
		this.lAT = lAT;
	}

	public String getCOURSE() {
		return cOURSE;
	}

	public void setCOURSE(String cOURSE) {
		this.cOURSE = cOURSE;
	}

	public String getHEADING() {
		return hEADING;
	}

	public void setHEADING(String hEADING) {
		this.hEADING = hEADING;
	}

	public String getTIMESTAMP() {
		return tIMESTAMP;
	}

	public void setTIMESTAMP(String tIMESTAMP) {
		this.tIMESTAMP = tIMESTAMP;
	}

	public String getSHIPID() {
		return sHIPID;
	}

	public void setSHIPID(String sHIPID) {
		this.sHIPID = sHIPID;
	}
}

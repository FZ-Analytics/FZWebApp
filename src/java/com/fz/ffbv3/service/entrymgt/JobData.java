/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.entrymgt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Agustinus Ignat
 */
public class JobData
{
  @SerializedName("divID")
  @Expose
  private String divID;
  @SerializedName("block1")
  @Expose
  private String block1;
  @SerializedName("block2")
  @Expose
  private String block2;
  @SerializedName("readyTime")
  @Expose
  private String readyTime;
  @SerializedName("estmKg")
  @Expose
  private Integer estmKg;
  @SerializedName("dirLoc")
  @Expose
  private String dirLoc;
  @SerializedName("isLastOrder")
  @Expose
  private String isLastOrder;
  @SerializedName("remark")
  @Expose
  private String remark;
  @SerializedName("UserID")
  @Expose
  private Integer UserID;

	public String getDivID()
	{
		return divID;
	}

	public void setDivID(String divID)
	{
		this.divID = divID;
	}

	public String getBlock1()
	{
		return block1;
	}

	public void setBlock1(String block1)
	{
		this.block1 = block1;
	}

	public String getBlock2()
	{
		return block2;
	}

	public void setBlock2(String block2)
	{
		this.block2 = block2;
	}

	public String getReadyTime()
	{
		return readyTime;
	}

	public void setReadyTime(String readyTime)
	{
		this.readyTime = readyTime;
	}

	public Integer getEstmKg()
	{
		return estmKg;
	}

	public void setEstmKg(Integer estmKg)
	{
		this.estmKg = estmKg;
	}

	public String getIsLastOrder()
	{
		return isLastOrder;
	}

	public void setIsLastOrder(String isLastOrder)
	{
		this.isLastOrder = isLastOrder;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public String getDirLoc()
	{
		return dirLoc;
	}

	public void setDirLoc(String dirLoc)
	{
		this.dirLoc = dirLoc;
	}

	public
	Integer getUserID()
	{
		return UserID;
	}

	public
	void setUserID(Integer UserID)
	{
		this.UserID = UserID;
	}
	
	
}

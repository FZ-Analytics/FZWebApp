/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.taskmgt;

import com.fz.ffbv3.service.usermgt.*;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Agustinus Ignat
 */
public class UploadPlanData
{
  @SerializedName("JobID")
  @Expose
  private Integer jobID;
  @SerializedName("TaskID")
  @Expose
  private Integer taskID;
  @SerializedName("DoneStatus")
  @Expose
  private String doneStatus;
  @SerializedName("TaskSeq")
  @Expose
  private Integer taskSeq;
  @SerializedName("ActualStart")
  @Expose
  private String ActualStart;
  @SerializedName("ActualEnd")
  @Expose
  private String ActualEnd;
  @SerializedName("ReasonState")
  @Expose
  private Integer reasonState;
  @SerializedName("ReasonID")
  @Expose
  private Integer reasonID;
  @SerializedName("VehicleID")
  @Expose
  private Integer vehicleID;

  public Integer getJobID() {
  return jobID;
  }

  public void setJobID(Integer jobID) {
  this.jobID = jobID;
  }

  public Integer getTaskID() {
  return taskID;
  }

  public void setTaskID(Integer taskID) {
  this.taskID = taskID;
  }

  public String getDoneStatus() {
  return doneStatus;
  }

  public void setDoneStatus(String doneStatus) {
  this.doneStatus = doneStatus;
  }

  public Integer getTaskSeq() {
  return taskSeq;
  }

  public void setTaskSeq(Integer taskSeq) {
  this.taskSeq = taskSeq;
  }

  public Integer getReasonState() {
  return reasonState;
  }

  public void setReasonState(Integer reasonState) {
  this.reasonState = reasonState;
  }

  public Integer getReasonID() {
  return reasonID;
  }

  public void setReasonID(Integer reasonID) {
  this.reasonID = reasonID;
  }

  public String getActualStart()
  {
    return ActualStart;
  }

  public void setActualStart(String ActualStart)
  {
    this.ActualStart = ActualStart;
  }

  public String getActualEnd()
  {
    return ActualEnd;
  }

  public void setActualEnd(String ActualEnd)
  {
    this.ActualEnd = ActualEnd;
  }

	public
	Integer getVehicleID()
	{
		return vehicleID;
	}

	public
	void setVehicleID(Integer vehicleID)
	{
		this.vehicleID = vehicleID;
	} 
}

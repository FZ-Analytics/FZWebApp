/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.entrymgt;

import com.fz.ffbv3.service.usermgt.*;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Agustinus Ignat
 */
public class EntryModel
{
  @SerializedName("JobData")
  @Expose
  private JobData jobData;

	public JobData getJobData()
	{
		return jobData;
	}

	public void setJobData(JobData jobData)
	{
		this.jobData = jobData;
	}
}

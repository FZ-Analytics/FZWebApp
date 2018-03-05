/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.taskmgt;

import com.fz.ffbv3.service.usermgt.*;
import com.fz.generic.CoreModule;
import com.fz.generic.CoreModule;
import com.google.gson.JsonArray;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Agustinus Ignat
 */
public class JobStateHolder
{
  private final CoreModule CoreResponse;
  private final JobStateModule JobStateResponse;
  private final String JobHistoryResponse;

	public JobStateHolder(CoreModule CoreResponse, JobStateModule JobStateResponse, String JobHistoryResponse)
	{
		this.CoreResponse = CoreResponse;
		this.JobStateResponse = JobStateResponse;
		this.JobHistoryResponse = JobHistoryResponse;
	}

	
}

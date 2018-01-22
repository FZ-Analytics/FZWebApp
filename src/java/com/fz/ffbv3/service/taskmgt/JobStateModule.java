/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.taskmgt;

import com.fz.ffbv3.service.usermgt.*;

/**
 *
 * @author Agustinus Ignat
 */
public class JobStateModule
{
  private String DoneStatus;

	public String getDoneStatus()
	{
		return DoneStatus;
	}

	public void setDoneStatus(String DoneStatus)
	{
		this.DoneStatus = DoneStatus;
	}
}

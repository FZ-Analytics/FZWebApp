/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.entrymgt;

import com.fz.ffbv3.service.reasonmgt.*;
import com.fz.ffbv3.service.usermgt.*;

/**
 *
 * @author Agustinus Ignat
 */
public class EntryModule
{
  private String estimation;
  private String direction;

	public String getEstimation()
	{
		return estimation;
	}

	public void setEstimation(String estimation)
	{
		this.estimation = estimation;
	}

	
	public String getDirection()
	{
		return direction;
	}

	public void setDirection(String direction)
	{
		this.direction = direction;
	}
}

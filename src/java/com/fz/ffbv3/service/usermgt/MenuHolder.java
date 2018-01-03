/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.usermgt;

import com.fz.generic.CoreModule;
import com.fz.generic.CoreModule;
import java.util.List;

/**
 *
 * @author Agustinus Ignat
 */
public class MenuHolder
{
  private final CoreModule CoreResponse;
  private final List<MenuModule> MobileMenuRsp;
  private final List<DivisiModule> DivisiRsp;

	public MenuHolder(CoreModule CoreResponse, List<MenuModule> MobileMenuRsp, List<DivisiModule> DivisiRsp)
	{
		this.CoreResponse = CoreResponse;
		this.MobileMenuRsp = MobileMenuRsp;
		this.DivisiRsp = DivisiRsp;
	}
}

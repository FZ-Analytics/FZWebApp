/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.entrymgt;

import com.fz.ffbv3.service.reasonmgt.*;
import com.fz.ffbv3.service.usermgt.*;
import com.fz.generic.CoreModule;
import com.fz.generic.CoreModule;
import java.util.List;

/**
 *
 * @author Agustinus Ignat
 */
public class EntryHolder
{
  private final CoreModule CoreResponse;
  private final List<EntryModule> EntryResponse;
  private final List<EntryModule> DirLocResponse;

	public EntryHolder(CoreModule CoreResponse, List<EntryModule> EntryResponse, List<EntryModule> DirLocResponse)
	{
		this.CoreResponse = CoreResponse;
		this.EntryResponse = EntryResponse;
    this.DirLocResponse = DirLocResponse;
	}
}

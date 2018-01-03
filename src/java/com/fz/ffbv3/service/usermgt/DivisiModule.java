/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.usermgt;

/**
 *
 * @author Agustinus Ignat
 */
public class DivisiModule
{
  private Integer id;
  private Integer ProdAstUserID;
  private String millID;
  private String estateID;
  private String divID;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getProdAstUserID()
	{
		return ProdAstUserID;
	}

	public void setProdAstUserID(Integer ProdAstUserID)
	{
		this.ProdAstUserID = ProdAstUserID;
	}

	public String getMillID()
	{
		return millID;
	}

	public void setMillID(String millID)
	{
		this.millID = millID;
	}

	public String getEstateID()
	{
		return estateID;
	}

	public void setEstateID(String estateID)
	{
		this.estateID = estateID;
	}

	public String getDivID()
	{
		return divID;
	}

	public void setDivID(String divID)
	{
		this.divID = divID;
	}
}

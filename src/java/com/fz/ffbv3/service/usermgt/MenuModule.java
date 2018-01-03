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
public class MenuModule
{
  private Integer id;
  private String Title;
  private String WebURL;
  private String ImageURL;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return Title;
	}

	public void setTitle(String Title)
	{
		this.Title = Title;
	}

	public String getWebURL()
	{
		return WebURL;
	}

	public void setWebURL(String WebURL)
	{
		this.WebURL = WebURL;
	}

	public String getImageURL()
	{
		return ImageURL;
	}

	public void setImageURL(String ImageURL)
	{
		this.ImageURL = ImageURL;
	}
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.usermgt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Agustinus Ignat
 */
public class UserData
{
  @SerializedName("Username")
  @Expose
  private String username;
  @SerializedName("Password")
  @Expose
  private String password;
  @SerializedName("UserID")
  @Expose
  private Integer userid;

  public String getUsername() {
  return username;
  }

  public void setUsername(String username) {
  this.username = username;
  }

  public String getPassword() {
  return password;
  }

  public void setPassword(String password) {
  this.password = password;
  }

	public Integer getUserid()
	{
		return userid;
	}

	public void setUserid(Integer userid)
	{
		this.userid = userid;
	}
}

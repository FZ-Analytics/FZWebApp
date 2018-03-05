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
public class TaskPlanModule
{
  private Integer TaskID;
  private Integer JobID;
  private String From;
  private String To;
  private String Start;
  private String End;
  private String divID;
  private String Tonnage;
  private String Blocks;
  private Integer TaskSeq;
  private Integer RequesterID;
  private String Name;
  private String BinLocation;
  private String BinEmpty;

  public Integer getTaskID()
  {
    return TaskID;
  }

  public void setTaskID(Integer TaskID)
  {
    this.TaskID = TaskID;
  }

  public Integer getJobID()
  {
    return JobID;
  }

  public void setJobID(Integer JobID)
  {
    this.JobID = JobID;
  }

  public String getFrom()
  {
    return From;
  }

  public void setFrom(String From)
  {
    this.From = From;
  }

  public String getTo()
  {
    return To;
  }

  public void setTo(String To)
  {
    this.To = To;
  }

  public String getStart()
  {
    return Start;
  }

  public void setStart(String Start)
  {
    this.Start = Start;
  }

  public String getEnd()
  {
    return End;
  }

  public void setEnd(String End)
  {
    this.End = End;
  }

  public String getDivID()
  {
    return divID;
  }

  public void setDivID(String divID)
  {
    this.divID = divID;
  }

  public String getTonnage()
  {
    return Tonnage;
  }

  public void setTonnage(String Tonnage)
  {
    this.Tonnage = Tonnage;
  }

  public String getBlocks()
  {
    return Blocks;
  }

  public void setBlocks(String Blocks)
  {
    this.Blocks = Blocks;
  }

  public Integer getTaskSeq()
  {
    return TaskSeq;
  }

  public void setTaskSeq(Integer TaskSeq)
  {
    this.TaskSeq = TaskSeq;
  }

  public Integer getRequesterID() {
    return RequesterID;
  }

  public void setRequesterID(Integer RequesterID) {
    this.RequesterID = RequesterID;
  }

  public String getName() {
    return Name;
  }

  public void setName(String Name) {
    this.Name = Name;
  }

  public String getBinLocation() {
    return BinLocation;
  }

  public void setBinLocation(String BinLocation) {
    this.BinLocation = BinLocation;
  }

  public String getBinEmpty() {
    return BinEmpty;
  }

  public void setBinEmpty(String BinEmpty) {
    this.BinEmpty = BinEmpty;
  }
 }

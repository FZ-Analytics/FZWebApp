/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.taskmgt;

import com.fz.ffbv3.service.usermgt.*;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Agustinus Ignat
 */
public class TaskPlanModel
{
  @SerializedName("TaskListData")
  @Expose
  private TaskPlanData taskListData;

  public TaskPlanData getTaskListData()
  {
    return taskListData;
  }

  public void setTaskListData(TaskPlanData taskListData)
  {
    this.taskListData = taskListData;
  }
}

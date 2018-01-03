/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.taskmgt;

import com.fz.ffbv3.service.usermgt.*;
import com.fz.generic.CoreModule;
import com.fz.generic.CoreModule;
import java.util.List;

/**
 *
 * @author Agustinus Ignat
 */
public class TaskPlanHolder
{
  private final CoreModule CoreResponse;
  private final List<TaskPlanModule> TaskListResponse;

  public TaskPlanHolder(CoreModule CoreResponse, List<TaskPlanModule> TaskListResponse)
  {
    this.CoreResponse = CoreResponse;
    this.TaskListResponse = TaskListResponse;
  }
}

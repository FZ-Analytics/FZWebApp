/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.order;

import com.fz.ffbv3.service.task.Task;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Eko
 */
public class Order {
    public String JobID = "";
    public String divID="";
    public int JobSeq=0;
    public String TruckID="";
    public String blockId1 = "";
    public String blockId2 = "";
    public String estTime = "";
    public String DoneStatus="";
    public String rundID = "";
    public List<Task> tasks = new ArrayList<Task>();
}

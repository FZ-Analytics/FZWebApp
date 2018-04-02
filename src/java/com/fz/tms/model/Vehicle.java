/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.model;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author dwi.rangga
 */
public class Vehicle {
    public String Vehicle_Code;
    public String Vehicle_Type;
    public double Total_KG_Vehicle;
    public String Total_KG_Vehicle_Unit;
    public double Total_Volume_Vehicle;
    public String Total_Volume_Vehicle_Unit;
    public double Volume;
    public String Volume_Unit;
    public double Weight;
    public String Weight_Unit;
    public String Plant;
    public Date Create_Date;
    public Timestamp Create_Time;
    public int Flag;
}

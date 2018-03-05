/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.divisionmgt;

import com.fz.ffbv3.service.usermgt.*;

/**
 *
 * @author Agustinus Ignat
 */
public class DivisionModule
{                    
  private String divID;
  private Integer TripsCount;
  private Integer kg1;
  private Integer kg2;
  private Integer kg3;
  private Integer kg4;
  private Integer trip1;
  private Integer trip2;
  private Integer trip3;
  private Integer trip4;
  private Double ActualKgs;
  private Double avgTrip;
  private Double KgsTax;
  private Double avgTax;

  public String getDivID() {
    return divID;
  }

  public void setDivID(String divID) {
    this.divID = divID;
  }

  public Integer getTripsCount() {
    return TripsCount;
  }

  public void setTripsCount(Integer TripsCount) {
    this.TripsCount = TripsCount;
  }

  public Integer getKg1() {
    return kg1;
  }

  public void setKg1(Integer kg1) {
    this.kg1 = kg1;
  }

  public Integer getKg2() {
    return kg2;
  }

  public void setKg2(Integer kg2) {
    this.kg2 = kg2;
  }

  public Integer getKg3() {
    return kg3;
  }

  public void setKg3(Integer kg3) {
    this.kg3 = kg3;
  }

  public Integer getKg4() {
    return kg4;
  }

  public void setKg4(Integer kg4) {
    this.kg4 = kg4;
  }

  public Integer getTrip1() {
    return trip1;
  }

  public void setTrip1(Integer trip1) {
    this.trip1 = trip1;
  }

  public Integer getTrip2() {
    return trip2;
  }

  public void setTrip2(Integer trip2) {
    this.trip2 = trip2;
  }

  public Integer getTrip3() {
    return trip3;
  }

  public void setTrip3(Integer trip3) {
    this.trip3 = trip3;
  }

  public Integer getTrip4() {
    return trip4;
  }

  public void setTrip4(Integer trip4) {
    this.trip4 = trip4;
  }

  public Double getActualKgs() {
    return ActualKgs;
  }

  public void setActualKgs(Double ActualKgs) {
    this.ActualKgs = ActualKgs;
  }

  public Double getAvgTrip() {
    return avgTrip;
  }

  public void setAvgTrip(Double avgTrip) {
    this.avgTrip = avgTrip;
  }

  public Double getKgsTax() {
    return KgsTax;
  }

  public void setKgsTax(Double KgsTax) {
    this.KgsTax = KgsTax;
  }

  public Double getAvgTax() {
    return avgTax;
  }

  public void setAvgTax(Double avgTax) {
    this.avgTax = avgTax;
  }

  
}

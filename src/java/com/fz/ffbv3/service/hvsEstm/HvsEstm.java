/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fz.ffbv3.service.hvsEstm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 */
public class HvsEstm {
    public String hvsDate = "";
    public String status = "";
    public String divID = "";
    public String remark = "";
    public double kg = 0;
    public String hvsEstmID = "";
    public Location millLoc = new Location();
    public List<HvsEstmDtl> dtl = new ArrayList<HvsEstmDtl>();
    public HashMap<String, String> params = new HashMap<String, String>();
}

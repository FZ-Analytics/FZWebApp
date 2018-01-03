/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fz.ffbv3.service.task2;

import com.fz.generic.BusinessLogic;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 */
public class Task2FilterLogic implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response
            , PageContext pc) throws Exception {
        
        String sql = "select "
                + "\n j.divID"
                + "\n, v.vehicle_name"
                + "\n, t.planStart"
                + "\n, t.doneStatus"
                + "\n from fbTask2 t"
                + "\n   left outer join fbJob j"
                + "\n       on t.jobID = j.jobID"
                + "\n   left outer join fbVehicle v"
                + "\n       on v.vechileID = t.planTruckID"
                + "\n where t.createDt "
                ;
        
    }

}

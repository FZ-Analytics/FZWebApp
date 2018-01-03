/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.division;

import com.fz.generic.BusinessLogic;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import org.primefaces.json.JSONArray;


/**
 *
 * @author Eko
 */
public class millLogic implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response, PageContext pc) throws Exception {
            JSONArray j = divisionDAO.lstMill();
            request.setAttribute("oMill", j);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params.PopUp;

import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.tms.params.model.Customer;
import com.fz.tms.params.service.Other;
import com.fz.util.FZUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author dwi.rangga
 */
public class popupEditCust implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response,
             PageContext pc) throws Exception {
        String runId = FZUtil.getHttpParam(request, "runId");
        String custId = FZUtil.getHttpParam(request, "custId");
        try {
            HashMap<String, String> pl = getCustData(runId, custId);

            request.setAttribute("runId", runId);
            request.setAttribute("custId", custId);
            request.setAttribute("Name1", pl.get("Name1"));
            request.setAttribute("Long", pl.get("Long"));
            request.setAttribute("Lat", pl.get("Lat"));
            request.setAttribute("Is_Exclude", pl.get("Is_Exclude"));
            request.setAttribute("Service_time", pl.get("Service_time"));
            request.setAttribute("deliv_start", pl.get("deliv_start"));
            request.setAttribute("deliv_end", pl.get("deliv_end"));
            request.setAttribute("Customer_priority", pl.get("Customer_priority"));
            request.setAttribute("vehicle_type_list", pl.get("vehicle_type_list"));
            request.setAttribute("Name1", pl.get("Name1"));
            request.setAttribute("Street", pl.get("Street"));
            request.setAttribute("Distribution_Channel", pl.get("Distribution_Channel"));
        } catch (Exception e) {
            HashMap<String, String> pl = new HashMap<String, String>();
            pl.put("ID", runId);
            pl.put("fileNmethod", "popupEditCust&run Ex");
            pl.put("datas", "");
            pl.put("msg", e.getMessage());
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            Date date = new Date();
            pl.put("dates", dateFormat.format(date).toString());
            Other.insertLog(pl);
        }

    }

    public HashMap<String, String> getCustData(String runId, String custId) throws Exception {
        HashMap<String, String> pl = new HashMap<String, String>();
        String c = "ERROR";

        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                // create sql
                String sql = "select\n"
                        + "	distinct b.Name1,\n"
                        + "	a.Long,\n"
                        + "	a.Lat,\n"
                        + "	a.Is_Exclude,\n"
                        + "	a.Service_time,\n"
                        + "	a.deliv_start,\n"
                        + "	a.deliv_end,\n"
                        + "	a.Customer_priority,\n"
                        + "	a.vehicle_type_list,\n"
                        + "	a.Name1,\n"
                        + "	a.Street,\n"
                        + "	a.Distribution_Channel\n"
                        + " from\n"
                        + "	bosnet1.dbo.TMS_PreRouteJob a left outer join\n"
                        + "	(\n"
                        + "		select\n"
                        + "			distinct Customer_ID,\n"
                        + "			Name1\n"
                        + "		from\n"
                        + "			bosnet1.dbo.Customer\n"
                        + "	) b on a.Customer_ID = b.Customer_ID\n"
                        + " where "
                        + "	a.RunId = '" + runId + "'\n"
                        + "	and a.Customer_ID = '" + custId + "'\n"
                        + "	and Is_Edit = 'edit'";

                // query
                try (ResultSet rs = stm.executeQuery(sql)) {
                    if (rs.next()) {
                        int i = 1;
                        pl.put("Name1", FZUtil.getRsString(rs, i++, ""));
                        pl.put("Long", FZUtil.getRsString(rs, i++, ""));
                        pl.put("Lat", FZUtil.getRsString(rs, i++, ""));
                        pl.put("Is_Exclude", FZUtil.getRsString(rs, i++, ""));
                        pl.put("Service_time", FZUtil.getRsString(rs, i++, ""));
                        pl.put("deliv_start", FZUtil.getRsString(rs, i++, ""));
                        pl.put("deliv_end", FZUtil.getRsString(rs, i++, ""));
                        pl.put("Customer_priority", FZUtil.getRsString(rs, i++, ""));
                        pl.put("vehicle_type_list", FZUtil.getRsString(rs, i++, ""));
                        pl.put("Name1", FZUtil.getRsString(rs, i++, ""));
                        pl.put("Street", FZUtil.getRsString(rs, i++, ""));
                        pl.put("Distribution_Channel", FZUtil.getRsString(rs, i++, ""));
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return pl;
    }
    /*
    public String getParams(String param) throws Exception{
        String str = "";
        
        String sql = "SELECT\n" +
                "	value\n" +
                "FROM\n" +
                "	bosnet1.dbo.TMS_Params\n" +
                "WHERE\n" +
                "	param = '"+param+"'\n" ;

        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)) {
            List<Customer> js = new ArrayList<Customer>();
            Customer c = new Customer();
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    c = new Customer();
                    int i = 1;
                    str = FZUtil.getRsString(rs, i++, "");
                }
            }
        }
        
        return str;
    }
     */
}

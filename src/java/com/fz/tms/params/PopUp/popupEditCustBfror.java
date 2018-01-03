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
import com.fz.tms.params.service.VehicleAttrDB;
import com.fz.tms.service.run.RouteJob;
import com.fz.util.FZUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author Administrator
 */
public class popupEditCustBfror implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response,
            PageContext pc
    ) throws Exception {
        String branchCode = FZUtil.getHttpParam(request, "branchCode");
        String dateDeliv = FZUtil.getHttpParam(request, "dateDeliv");
        String shift = FZUtil.getHttpParam(request, "shift");
        String reRun = FZUtil.getHttpParam(request, "reRun");
        String runId = FZUtil.getHttpParam(request, "runId");
        String oriRunID = FZUtil.getHttpParam(request, "oriRunID");
        String err = FZUtil.getHttpParam(request, "errMsg");
        String channel = FZUtil.getHttpParam(request, "channel");
        request.getSession().setAttribute("errMsg"
                        , err);        
        
        String cds = "ERROR insertPreRouteJob";

        String sql = "SELECT\n" +
                "	distinct\n" +
                "	pr.customer_id,\n" +
                "	pr.do_number,\n" +
                "	pr.long,\n" +
                "	pr.lat,\n" +
                "	pr.Customer_Priority,\n" +
                "	pr.Request_Delivery_Date,\n" +
                "	pr.service_time,\n" +
                "	pr.deliv_start,\n" +
                "	pr.deliv_end,\n" +
                "	pr.vehicle_type_list,\n" +
                "	pr.Is_Exclude\n" +
                "FROM\n" +
                "	bosnet1.dbo.TMS_PreRouteJob pr\n" +
                "LEFT OUTER JOIN(\n" +
                "		SELECT\n" +
                "			a.*\n" +
                "		FROM\n" +
                "			(\n" +
                "				SELECT\n" +
                "					ROW_NUMBER() OVER(\n" +
                "						PARTITION BY Customer_ID\n" +
                "					ORDER BY\n" +
                "						Customer_ID\n" +
                "					) AS noId,\n" +
                "					*\n" +
                "				FROM\n" +
                "					bosnet1.dbo.customer\n" +
                "			) a\n" +
                "		WHERE\n" +
                "			a.noid = 1\n" +
                "	) cs ON\n" +
                "	pr.customer_id = cs.customer_id\n" +
                "LEFT OUTER JOIN bosnet1.dbo.TMS_CustAtr ca ON\n" +
                "	pr.Customer_ID = ca.customer_id\n" +
                "WHERE\n" +
                "	pr.isactive = 1\n" +
                "	AND pr.is_edit = 'edit'\n" +
                "	AND pr.runid = '"+runId+"'";

        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)) {
            List<Customer> js = new ArrayList<Customer>();
            Customer c = new Customer();
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    c = new Customer();
                    int i = 1;
                    c.customer_id = FZUtil.getRsString(rs, i++, "");
                    c.do_number = FZUtil.getRsString(rs, i++, "");
                    c.lng = FZUtil.getRsString(rs, i++, "");
                    c.lat = FZUtil.getRsString(rs, i++, "");
                    c.customer_priority = FZUtil.getRsString(rs, i++, "");
                    c.rdd = FZUtil.getRsString(rs, i++, "");
                    c.service_time = Integer.parseInt(FZUtil.getRsString(rs, i++, ""));//Integer.parseInt(FZUtil.getRsString(rs, i++, getParams("DefaultCustServiceTime")));
                    c.deliv_start = FZUtil.getRsString(rs, i++, "");//FZUtil.getRsString(rs, i++, getParams("DefaultCustStartTime"));
                    c.deliv_end = FZUtil.getRsString(rs, i++, "");//FZUtil.getRsString(rs, i++, getParams("DefaultCustEndTime"));
                    c.vehicle_type_list = FZUtil.getRsString(rs, i++, "");//FZUtil.getRsString(rs, i++, getParams("DefaultCustVehicleTypes"));
                    c.isInc = FZUtil.getRsString(rs, i++, "");//FZUtil.getRsString(rs, i++, "");
                    js.add(c);
                }
                
                request.setAttribute("CustList", js);
                request.setAttribute("branchCode", branchCode);
                request.setAttribute("dateDeliv", dateDeliv);
                request.setAttribute("shift", shift);
                request.setAttribute("reRun", reRun);
                request.setAttribute("runId", runId);
                request.setAttribute("oriRunID", oriRunID);
                request.setAttribute("channel", channel);
            }
        }catch(Exception e){
            HashMap<String, String> pl = new HashMap<String, String>();
            pl.put("ID", runId);
            pl.put("fileNmethod", "popupEditCustBfror&run Exc");
            pl.put("datas", "");
            pl.put("msg", e.getMessage());
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            Date date = new Date();
            pl.put("dates", dateFormat.format(date).toString());
            Other.insertLog(pl);
        }
    }
    
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
    
    public String cekError(ResultSet rs, int i, String prm){
        String str = "";
        
        
        return str;
        
    }
    
}

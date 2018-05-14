/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params.service;

import com.fz.generic.Db;
import com.fz.tms.params.model.OptionModel;
import com.fz.tms.params.model.PopUpEditCustBfror;
import com.fz.tms.params.model.PreRouteJobSubmitCustomer;
import com.fz.util.FZUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dwi.rangga
 */
public class PreRouteJobDB {

    public static List<OptionModel> getDOPreRouteJob(String runId, String custId) throws Exception {
        List<OptionModel> tr = new ArrayList<OptionModel>();
        OptionModel ap = new OptionModel();
        String c = "ERROR";

        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                // create sql
                String sql = "select\n"
                        + "	distinct DO_Number\n"
                        + " from\n"
                        + "	bosnet1.dbo.TMS_PreRouteJob a\n"
                        + " where\n"
                        + "	a.RunId = '" + runId + "'\n"
                        + "	and a.Customer_ID = '" + custId + "'\n"
                        + "	and Is_Edit = 'edit'"
                        ;

                // query
                try (ResultSet rs = stm.executeQuery(sql)) {
                    while (rs.next()) {
                        ap = new OptionModel();
                        int i = 1;
                        String str = ap.Display = FZUtil.getRsString(rs, i++, "");
                        ap.Display = str;
                        ap.Value = str;
                        tr.add(ap);
                    }
                }
            }
        }
        return tr;
    }

    public static String getStatusDO(String runId, String custId, String doNum) throws Exception {
        String tr = "";

        try (Connection con = (new Db()).getConnection("jdbc/fztms")) {
            try (Statement stm = con.createStatement()) {
                // create sql
                String sql = "select\n"
                        + "	distinct Is_Exclude\n"
                        + "from\n"
                        + "	bosnet1.dbo.TMS_PreRouteJob a\n"
                        + "where\n"
                        + "	a.RunId = '" + runId + "'\n"
                        + "	and a.Customer_ID = '" + custId + "'\n"
                        + "	and DO_Number = '" + doNum + "'\n"
                        + "	and Is_Edit = 'edit'";

                // query
                try (ResultSet rs = stm.executeQuery(sql)) {
                    if (rs.next()) {
                        int i = 1;
                        tr = FZUtil.getRsString(rs, i++, "");
                    }
                }
            }
        }
        return tr;
    }

    public static String submitEdit(PreRouteJobSubmitCustomer he) throws Exception {
        String tr = "ERROR";
        
        tr = submitEditDO(he);
        if(tr.equalsIgnoreCase("OK"))   tr = submitEditCustAttr(he);
        
        return tr;
    }
    
    public static String submitEditDO(PreRouteJobSubmitCustomer he) throws Exception {
        String tr = "ERROR";

        String sql = "update\n"
                + "	bosnet1.dbo.TMS_PreRouteJob\n"
                + " set\n"
                + "	Is_Exclude = '" + he.ExcInc + "',\n"
                + "	UpdatevDate = cast(FORMAT(getdate(),'yyyy-MM-dd hh-mm') as varchar)\n"
                + " where\n"
                + "	RunId = '" + he.RunId + "'\n"
                + "	and Customer_ID = '" + he.Customer_ID + "'\n"
                + "	and DO_Number = '" + he.DO_Number + "'\n"
                + "	and Is_Edit = 'edit'";
        try (
            Connection con = (new Db()).getConnection("jdbc/fztms");
            PreparedStatement psHdr = con.prepareStatement(sql
                    , Statement.RETURN_GENERATED_KEYS);
            )  {
            con.setAutoCommit(false);

            psHdr.executeUpdate();
            
             con.setAutoCommit(true);
             tr = "OK";
        }
        return tr;
    }
    
    public static String submitEditDoIncExc(String runId, String excInc, String custId, String doNum) throws Exception {
        String tr = "ERROR";

        String sql = "update\n"
                + "	bosnet1.dbo.TMS_PreRouteJob\n"
                + " set\n"
                + "	Is_Exclude = '" + excInc + "',\n"
                + "	UpdatevDate = cast(FORMAT(getdate(),'yyyy-MM-dd hh-mm') as varchar)\n"
                + " where\n"
                + "	RunId = '" + runId + "'\n"
                + "	and Customer_ID = '" + custId + "'\n"
                + "	and DO_Number = '" + doNum + "'\n"
                + "	and Is_Edit = 'edit'";
        try (
            Connection con = (new Db()).getConnection("jdbc/fztms");
            PreparedStatement psHdr = con.prepareStatement(sql
                    , Statement.RETURN_GENERATED_KEYS);
            )  {
            con.setAutoCommit(false);

            psHdr.executeUpdate();
            
            con.setAutoCommit(true);
            tr = "OK";
        }
        return tr;
    }
    
    public static String submitEditCustAttr(PreRouteJobSubmitCustomer he) throws Exception {
        String tr = "ERROR";

        String sql = "update\n"
                + "	bosnet1.dbo.TMS_PreRouteJob\n"
                + " set\n"
                + "	Long = '" + he.lng + "',\n"
                + "	Lat = '" + he.lat + "',\n"
                + "	Customer_priority = '" + he.Customer_priority + "',\n"
                + "	Service_time = " + he.Service_time + ",\n"
                + "	deliv_start = '" + he.deliv_start + "',\n"
                + "	deliv_end = '" + he.deliv_end + "',\n"
                + "	vehicle_type_list = '" + he.vehicle_type_list + "',\n"
                + "	UpdatevDate = cast(FORMAT(getdate(),'yyyy-MM-dd hh-mm') as varchar)\n"
                + " where\n"
                + "	RunId = '" + he.RunId + "'\n"
                + "	and Customer_ID = '" + he.Customer_ID + "'\n"
                + "	and Is_Edit = 'edit'";
        try (
            Connection con = (new Db()).getConnection("jdbc/fztms");
            PreparedStatement psHdr = con.prepareStatement(sql
                    , Statement.RETURN_GENERATED_KEYS);
            )  {
            con.setAutoCommit(false);

            psHdr.executeUpdate();
            
             con.setAutoCommit(true);
             tr = "OK";
        }
        return tr;
    }
}

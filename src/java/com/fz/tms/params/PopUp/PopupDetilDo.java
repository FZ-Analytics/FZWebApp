/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params.PopUp;

import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.tms.params.model.DODetil;
import com.fz.tms.params.model.SummaryVehicle;
import com.fz.tms.service.run.RouteJobListing;
import com.fz.util.FZUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author dwi.rangga
 */
public class PopupDetilDo  implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response
            , PageContext pc) throws Exception {
        String custId = FZUtil.getHttpParam(request, "custId");
        String runId = FZUtil.getHttpParam(request, "runId");
        String br = "";
        /*String sql = "select p.Product_Description, p.Total_KG_Item, p.DOQty, p.DOQtyUOM, p.Plant, inv.szCompUomId "
                + "from BOSNET1.dbo.TMS_ShipmentPlan p, "
                + "(SELECT szName, szUomId, szCompUomId FROM BOSNET.dbo.BOS_INV_Product ) inv"
                + " where p.Product_Description = inv.szName and p.DOQtyUOM = inv.szUomId"
                + " and p.NotUsed_Flag is null and p.DO_Number = '"+doID+"';";*/
        String sql = "SELECT\n" +
                "	rb.DO_Number,\n" +
                "	rb.Product_Description,\n" +
                "	rb.Total_KG,\n" +
                "	rb.DOQty,\n" +
                "	rb.DOQtyUOM,\n" +
                "	rj.branch\n" +
                //"	CASE\n" +
                //"		WHEN rb.batch IS NULL THEN 'no'\n" +
                //"		ELSE 'yes'\n" +
                //"	END sendSap\n" +
                "FROM\n" +
                "	BOSNET1.dbo.TMS_PreRouteJob rb\n" +
                "INNER JOIN(\n" +
                "		SELECT\n" +
                "			DISTINCT branch,\n" +
                "			RunId\n" +
                "		FROM\n" +
                "			BOSNET1.dbo.TMS_RouteJob\n" +
                "	) rj ON\n" +
                "	rb.RunId = rj.RunId\n" +
                "WHERE\n" +
                "	is_edit = 'edit'\n" +
                "	AND rb.RunId = '"+runId+"'\n" +
                "	AND rb.Customer_id = '"+custId+"'";
        
        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)){

            try (ResultSet rs = ps.executeQuery()){
                List<DODetil> ar = new ArrayList<DODetil>();
                DODetil dt = new DODetil();
                // populate list
                
                BigDecimal totalkg = new BigDecimal(0);
                int a = 1;
                while (rs.next()) {
                    dt = new DODetil();
                    int i = 1;
                    dt.no = String.valueOf(a);
                    a++;
                    dt.DO_Number = FZUtil.getRsString(rs, i++, "");
                    dt.Product_Description = FZUtil.getRsString(rs, i++, "");
                    dt.Total_KG_Item = FZUtil.getRsString(rs, i++, "");  
                    dt.DOQty = String.valueOf(new BigDecimal(FZUtil.getRsString(rs, i++, "")).intValue());
                    dt.DOQtyUOM = FZUtil.getRsString(rs, i++, "");  
                    br = FZUtil.getRsString(rs, i++, ""); 
                    //dt.sap = FZUtil.getRsString(rs, i++, "");  
                    /*                    
                    String str = FZUtil.getRsString(rs, i++, "");
                    DecimalFormat df = new DecimalFormat("##.0");
                    dt.pck = String.valueOf(Double.parseDouble(df.format((new BigDecimal(dt.DOQty)).divide(new BigDecimal(str.substring(3, str.indexOf("/"))),1, RoundingMode.HALF_EVEN)))) +" "+ str.substring(0, 3);
                    
                    */
                    totalkg = totalkg.add(new BigDecimal(dt.Total_KG_Item));
                    ar.add(dt);
                }
                RouteJobListing rj = new RouteJobListing();
                List<HashMap<String, String>> px = rj.cekData(runId, custId);
                int x = 0;
                String str = "no";
                while(x < ar.size()){
                    int y = 0;
                    Boolean cek = true;
                    while(y < px.size()){
                        //cek jika do sama
                        if(ar.get(x).DO_Number.equalsIgnoreCase(px.get(y).get("DOPR"))){                                                        
                            if(px.get(y).get("DOSP") == null)           str = "cek data ShipmentPlant";
                            else if(px.get(y).get("DOSS") != null)      str = "cek data StatusShipment";
                            else if(px.get(y).get("DORS") != null)      str = "cek data ResultShipment";
                            
                            if(!str.equalsIgnoreCase("no")){
                                cek = false;
                                break;
                            }else{
                                cek = true;
                                break;
                            }
                        }
                        y++;
                    }
                    
                    if(!cek)    ar.get(x).sap = str;
                    else        ar.get(x).sap = "yes";
                    x++;
                }
                
                request.setAttribute("ListDODetil", ar);
                request.setAttribute("Name", getName(dt.DO_Number, runId));
                request.setAttribute("branch", br);
                request.setAttribute("total", totalkg.toString());
                
            }
        }
    }
    
    public String getName(String doNumber, String runId) throws Exception{
        String str = "";
        String sql = "SELECT Name1 FROM BOSNET1.dbo.TMS_PreRouteJob "
                + "where runid = '"+runId+"' and DO_Number = '"+doNumber+"'";
        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)){

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    int i = 1;
                    str = FZUtil.getRsString(rs, i++, "");
                }
            }
        }
        return str;
    }
    

}

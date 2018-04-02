/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params;

import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.tms.params.model.DODetil;
import com.fz.tms.params.model.SummaryVehicle;
import com.fz.util.FZUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
        String sql = "select\n" +
                "	do.DO_Number,\n" +
                "	do.Product_Description,\n" +
                "	do.Total_KG_Item,\n" +
                "	do.DOQty,\n" +
                "	do.DOQtyUOM,\n" +
                "	rt.branch\n" +
                "from\n" +
                "	(\n" +
                "		select\n" +
                "			sp.Customer_ID,\n" +
                "			sp.DO_Number,\n" +
                "			sp.Shift,\n" +
                "			sp.Product_Description,\n" +
                "			sp.Total_KG_Item,\n" +
                "			sp.DOQty,\n" +
                "			sp.DOQtyUOM\n" +
                "		from\n" +
                "			bosnet1.dbo.TMS_ShipmentPlan sp left outer join bosnet1.dbo.customer cs on\n" +
                "			sp.customer_id = cs.customer_id left join bosnet1.dbo.TMS_CustLongLat cl on\n" +
                "			sp.customer_id = cl.custID left outer join bosnet1.dbo.TMS_CustAtr ca on\n" +
                "			sp.customer_id = ca.customer_id\n" +
                "		where\n" +
                "			sp.already_shipment = 'N'\n" +
                "			and sp.notused_flag is null\n" +
                "			and(\n" +
                "				select\n" +
                "					DATEPART(\n" +
                "						dw,\n" +
                "						getdate()\n" +
                "					)\n" +
                "			) between case\n" +
                "				when ca.customer_id is null then(\n" +
                "					SELECT\n" +
                "						value\n" +
                "					FROM\n" +
                "						BOSNET1.dbo.TMS_Params\n" +
                "					where\n" +
                "						param = 'DayWinStart'\n" +
                "				)\n" +
                "				else CASE\n" +
                "					ca.DayWinStart\n" +
                "					WHEN 'SUN' THEN 1\n" +
                "					WHEN 'MON' THEN 2\n" +
                "					WHEN 'TUE' THEN 3\n" +
                "					WHEN 'WED' THEN 4\n" +
                "					WHEN 'THR' THEN 5\n" +
                "					WHEN 'FRI' THEN 6\n" +
                "					ELSE 7\n" +
                "				END\n" +
                "			end and case\n" +
                "				when ca.customer_id is null then(\n" +
                "					SELECT\n" +
                "						value\n" +
                "					FROM\n" +
                "						BOSNET1.dbo.TMS_Params\n" +
                "					where\n" +
                "						param = 'DayWinEnd'\n" +
                "				)\n" +
                "				else CASE\n" +
                "					ca.DayWinEnd\n" +
                "					WHEN 'SUN' THEN 1\n" +
                "					WHEN 'MON' THEN 2\n" +
                "					WHEN 'TUE' THEN 3\n" +
                "					WHEN 'WED' THEN 4\n" +
                "					WHEN 'THR' THEN 5\n" +
                "					WHEN 'FRI' THEN 6\n" +
                "					ELSE 7\n" +
                "				END\n" +
                "			end\n" +
                "			and(\n" +
                "				(\n" +
                "					ca.DeliveryDeadLine =(\n" +
                "						case\n" +
                "							when ca.customer_id is null then(\n" +
                "								SELECT\n" +
                "									value\n" +
                "								FROM\n" +
                "									BOSNET1.dbo.TMS_Params\n" +
                "								where\n" +
                "									param = 'DeliveryDeadline'\n" +
                "							)\n" +
                "							else 'BFOR'\n" +
                "						end\n" +
                "					)\n" +
                "					and sp.Request_Delivery_Date >= getdate()\n" +
                "				)\n" +
                "				or(\n" +
                "					ca.DeliveryDeadLine = 'AFTR'\n" +
                "					and sp.Request_Delivery_Date < getdate()\n" +
                "				)\n" +
                "				or(\n" +
                "					ca.DeliveryDeadLine = 'ONDL'\n" +
                "					and sp.Request_Delivery_Date = getdate()\n" +
                "				)\n" +
                "			)\n" +
                "		group by\n" +
                "			sp.Customer_ID,\n" +
                "			sp.DO_Number,\n" +
                "			sp.Shift,\n" +
                "			sp.Product_Description,\n" +
                "			sp.Total_KG_Item,\n" +
                "			sp.DOQty,\n" +
                "			sp.DOQtyUOM\n" +
                "	) do inner join BOSNET1.dbo.TMS_RouteJob rt on\n" +
                "	do.Customer_ID = rt.customer_id\n" +
                "where\n" +
                "	do.Customer_ID = '"+custId+"'\n" +
                "	and rt.runID = '"+runId+"'";
        
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
                    /*                    
                    String str = FZUtil.getRsString(rs, i++, "");
                    DecimalFormat df = new DecimalFormat("##.0");
                    dt.pck = String.valueOf(Double.parseDouble(df.format((new BigDecimal(dt.DOQty)).divide(new BigDecimal(str.substring(3, str.indexOf("/"))),1, RoundingMode.HALF_EVEN)))) +" "+ str.substring(0, 3);
                    
                    */
                    totalkg = totalkg.add(new BigDecimal(dt.Total_KG_Item));
                    ar.add(dt);
                }
                
                request.setAttribute("ListDODetil", ar);
                request.setAttribute("Name", getName(dt.DO_Number));
                request.setAttribute("branch", br);
                request.setAttribute("total", totalkg.toString());
                
            }
        }
    }
    
    public String getName(String n) throws Exception{
        String str = "";
        String sql = "select distinct aw.Name1 from BOSNET1.dbo.Customer aw "
                + "inner join BOSNET1.dbo.TMS_ShipmentPlan aq on aq.Customer_ID = aw.Customer_ID where DO_Number = '"+n+"';";
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

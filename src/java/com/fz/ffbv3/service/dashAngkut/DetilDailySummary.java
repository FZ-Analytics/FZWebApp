/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.dashAngkut;

import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.util.FZUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import org.json.JSONArray;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class DetilDailySummary {

    public static JSONArray rdbTrips01(String tgl) {
        JSONArray result = new JSONArray();
        String sql = "SELECT\n"
                + "	p.*,\n"
                + "	t.Kgs,\n"
                + "	t.Kgs /(\n"
                + "		CASE\n"
                + "			p.TripsCount\n"
                + "			WHEN 0 THEN 1\n"
                + "			ELSE p.TripsCount\n"
                + "		END\n"
                + "	) AvgKgs\n"
                + "FROM\n"
                + "	(\n"
                + "		SELECT\n"
                + "			v.VehicleName,\n"
                + "			COUNT(*) TripsCount\n"
                + "		FROM\n"
                + "			fbjob a\n"
                + "		LEFT JOIN fbtask2 b ON\n"
                + "			a.JobID = b.JobID\n"
                + "		LEFT JOIN fbvehicle v ON\n"
                + "			a.ActualTruckID = v.VehicleID\n"
                + "		WHERE\n"
                + "			CONVERT(\n"
                + "				a.hvsDt,\n"
                + "				DATE\n"
                + "			)= '" + tgl + "'\n"
                + "			AND a.DoneStatus = \"DONE\"\n"
                + "			AND b.TaskSeq = 2\n"
                + "		GROUP BY\n"
                + "			v.VehicleName\n"
                + "	) p\n"
                + "LEFT JOIN(\n"
                + "		SELECT\n"
                + "			ta.VehicleID,\n"
                + "			SUM( ta.QtyTerima ) Kgs\n"
                + "		FROM\n"
                + "			fbtransportsap ta\n"
                + "		WHERE\n"
                + "			ta.Tgl = '" + tgl + "'\n"
                + "		GROUP BY\n"
                + "			ta.VehicleID\n"
                + "	) t ON\n"
                + "	p.VehicleName = t.VehicleID";

        try (Connection con = (new Db()).getConnection("jdbc/fz");) {
            try (Statement stm = con.createStatement()) {
                ResultSet rs = stm.executeQuery(sql);
                JSONObject o;
                while (rs.next()) {
                    o = new JSONObject();
                    o.put("VehicleName", rs.getString("VehicleName"));
                    o.put("TripsCount", rs.getInt("TripsCount"));
                    o.put("Kgs", rs.getInt("Kgs"));
                    o.put("AvgKgs", rs.getInt("AvgKgs"));
                    result.put(o);
                }
            } catch (Exception e) {
                String err = e.getMessage();
            }
        } catch (Exception e) {
            String err = e.getMessage();
        }
        rdbTrips02(tgl);
        return result;
    }
    
    public static JSONArray rdbTrips02(String tgl) {
        JSONArray result = new JSONArray();
        String sql = "SELECT\n" +
                "	w.*,\n" +
                "	x.Kgs ActualKgs,\n" +
                "	x.kgs /(\n" +
                "		CASE\n" +
                "			WHEN w.TripsCount = 0 THEN 1\n" +
                "			ELSE w.TripsCount\n" +
                "		END\n" +
                "	) avgTrip,\n" +
                "	e.Kgs KgsTax,\n" +
                "	(\n" +
                "		x.kgs - ifnull(\n" +
                "			e.Kgs,\n" +
                "			0\n" +
                "		)\n" +
                "	)/(\n" +
                "		CASE\n" +
                "			WHEN ifnull(\n" +
                "				x.Kgs,\n" +
                "				0\n" +
                "			)= 0 THEN ifnull(\n" +
                "				e.kgs,\n" +
                "				1\n" +
                "			)\n" +
                "			ELSE x.Kgs\n" +
                "		END\n" +
                "	)* 100 avgTax,\n" +
                "	r.Qty kgsRestan\n" +
                "FROM\n" +
                "	(\n" +
                "		SELECT\n" +
                "			a.divID,\n" +
                "			COUNT(*) TripsCount,\n" +
                "			SUM( CASE WHEN CONVERT( b.ActualEnd, TIME )< '10:00:00' THEN 1 ELSE 0 END ) trip1,\n" +
                "			SUM( CASE WHEN CONVERT( b.ActualEnd, TIME )< '13:00:00' THEN 1 ELSE 0 END ) trip2,\n" +
                "			SUM( CASE WHEN CONVERT( b.ActualEnd, TIME )< '18:00:00' THEN 1 ELSE 0 END ) trip3\n" +
                "		FROM\n" +
                "			fbjob a\n" +
                "		LEFT JOIN fbtask2 b ON\n" +
                "			a.JobID = b.JobID\n" +
                "		WHERE\n" +
                "			CONVERT(\n" +
                "				a.hvsDt,\n" +
                "				DATE\n" +
                "			)= '"+tgl+"'\n" +
                "			AND a.DoneStatus = 'DONE'\n" +
                "			AND b.TaskSeq = 2\n" +
                "		GROUP BY\n" +
                "			a.divID\n" +
                "	) w\n" +
                "LEFT JOIN(\n" +
                "		SELECT\n" +
                "			a.divID,\n" +
                "			SUM( b.size1 ) Kgs\n" +
                "		FROM\n" +
                "			fbhvsestm a\n" +
                "		LEFT JOIN fbhvsestmdtl b ON\n" +
                "			a.HvsEstmID = b.hvsEstmID\n" +
                "		WHERE\n" +
                "			CONVERT(\n" +
                "				a.hvsDt,\n" +
                "				DATE\n" +
                "			)= '"+tgl+"'\n" +
                "			AND a.status = 'FNAL'\n" +
                "		GROUP BY\n" +
                "			a.divID\n" +
                "	) e ON\n" +
                "	w.divID = e.divID\n" +
                "LEFT JOIN(\n" +
                "		SELECT\n" +
                "			ca.divID,\n" +
                "			SUM( ca.QtyTerima ) Kgs\n" +
                "		FROM\n" +
                "			fbtransportsap ca\n" +
                "		WHERE\n" +
                "			ca.tgl = '"+tgl+"'\n" +
                "		GROUP BY\n" +
                "			ca.divID\n" +
                "	) x ON\n" +
                "	w.divid = x.divID\n" +
                "LEFT JOIN(\n" +
                "		SELECT\n" +
                "			ra.divID,\n" +
                "			SUM( ra.qty ) Qty\n" +
                "		FROM\n" +
                "			fbleftover ra\n" +
                "		WHERE\n" +
                "			ra.PostgDate = '"+tgl+"'\n" +
                "		GROUP BY\n" +
                "			ra.divID\n" +
                "	) r ON\n" +
                "	w.divID = r.divID";

        try (Connection con = (new Db()).getConnection("jdbc/fz");) {
            try (Statement stm = con.createStatement()) {
                ResultSet rs = stm.executeQuery(sql);
                JSONObject o;
                while (rs.next()) {
                    o = new JSONObject();
                    o.put("divID", rs.getString("divID"));
                    o.put("TripsCount", rs.getInt("TripsCount"));
                    o.put("trip1", rs.getInt("trip1"));
                    o.put("trip2", rs.getInt("trip2"));
                    o.put("trip3", rs.getInt("trip3"));
                    o.put("ActualKgs", rs.getDouble("ActualKgs"));
                    o.put("avgTrip", rs.getDouble("avgTrip"));
                    o.put("KgsTax", rs.getDouble("KgsTax"));
                    o.put("avgTax", rs.getDouble("avgTax"));
                    o.put("kgsRestan", rs.getDouble("kgsRestan"));
                    result.put(o);
                }
            } catch (Exception e) {
                String err = e.getMessage();
            }
        } catch (Exception e) {
            String err = e.getMessage();
        }
        return result;
    }

}

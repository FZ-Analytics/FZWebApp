/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.DashAngkut;

import com.fz.generic.Db;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class DetilSummary_old{
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
    
    public static JSONArray rdbTrip3(String fromDate, String toDate) {
        DecimalFormat df = new DecimalFormat("##.0");
        JSONArray result = new JSONArray();
        String sql = "SELECT\n" +
                "	t.*,\n" +
                "	x.KgsTrip ActualKgs,\n" +
                "	x.KgsTrip /(\n" +
                "		CASE\n" +
                "			WHEN ifnull(\n" +
                "				t.DaysCount,\n" +
                "				0\n" +
                "			)= 0 THEN 1\n" +
                "			ELSE t.avgTrip\n" +
                "		END\n" +
                "	) avgKgs\n" +
                "FROM\n" +
                "	(\n" +
                "		SELECT\n" +
                "			v.VehicleName,\n" +
                "			COUNT(*) DaysCount,\n" +
                "			SUM( t1.TripCount ) TripCount,\n" +
                "			AVG( t1.TripCount ) avgTrip\n" +
                "		FROM\n" +
                "			(\n" +
                "				SELECT\n" +
                "					a.ActualTruckID,\n" +
                "					CONVERT(\n" +
                "						a.hvsDt,\n" +
                "						DATE\n" +
                "					) Tgl,\n" +
                "					COUNT(*) TripCount\n" +
                "				FROM\n" +
                "					fbjob a\n" +
                "				LEFT JOIN fbtask2 b ON\n" +
                "					a.jobid = b.JobID\n" +
                "				WHERE\n" +
                "					CONVERT(\n" +
                "						a.hvsDt,\n" +
                "						DATE\n" +
                "					) BETWEEN '"+fromDate+"' AND '"+toDate+"'\n" +
                "					AND a.DoneStatus = 'DONE'\n" +
                "					AND b.TaskSeq = 2\n" +
                "				GROUP BY\n" +
                "					a.ActualTruckID,\n" +
                "					CONVERT(\n" +
                "						a.hvsDt,\n" +
                "						DATE\n" +
                "					)\n" +
                "			) t1\n" +
                "		LEFT JOIN fbvehicle v ON\n" +
                "			t1.ActualTruckID = v.VehicleID\n" +
                "		GROUP BY\n" +
                "			v.VehicleName\n" +
                "	) t\n" +
                "LEFT JOIN(\n" +
                "		SELECT\n" +
                "			x1.VehicleID,\n" +
                "			SUM( x1.QtyTerima ) KgsTrip\n" +
                "		FROM\n" +
                "			fbtransportsap x1\n" +
                "		WHERE\n" +
                "			x1.tgl BETWEEN '"+fromDate+"' AND '"+toDate+"'\n" +
                "		GROUP BY\n" +
                "			x1.VehicleID\n" +
                "	) x ON\n" +
                "	t.VehicleName = x.vehicleID";
        try (Connection con = (new Db()).getConnection("jdbc/fz");) {
            try (Statement stm = con.createStatement()) {
                ResultSet rs = stm.executeQuery(sql);
                JSONObject o;
                while (rs.next()) {
                    o = new JSONObject();
                    o.put("VehicleName", rs.getString("VehicleName"));
                    o.put("DaysCount", rs.getInt("DaysCount"));
                    o.put("TripCount", rs.getInt("TripCount"));
                    o.put("avgTrip", df.format(rs.getDouble("avgTrip")));
                    o.put("ActualKgs", df.format(rs.getDouble("ActualKgs")));
                    o.put("avgKgs", df.format(rs.getDouble("avgKgs")));
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

    public static JSONArray rdbTrip4(String fromDate, String toDate) {
        JSONArray result = new JSONArray();
        DecimalFormat df = new DecimalFormat("##.0");
        String sql = "SELECT\n" +
                "	t.*,\n" +
                "	w.ActKgs ActKgs,\n" +
                "	w.ActKgs /(\n" +
                "		CASE\n" +
                "			WHEN ifnull(\n" +
                "				t.TripsCount,\n" +
                "				0\n" +
                "			)= 0 THEN 1\n" +
                "			ELSE t.TripsCount\n" +
                "		END\n" +
                "	) kgPerTrip,\n" +
                "	h.EstKgs TaxKgs,\n" +
                "	(\n" +
                "		CASE\n" +
                "			WHEN ifnull(\n" +
                "				w.ActKgs,\n" +
                "				0\n" +
                "			)= 0 THEN 0\n" +
                "			ELSE(\n" +
                "				ifnull(\n" +
                "					w.ActKgs,\n" +
                "					0\n" +
                "				)- ifnull(\n" +
                "					h.EstKgs,\n" +
                "					0\n" +
                "				)\n" +
                "			)/ w.ActKgs\n" +
                "		END\n" +
                "	)* 100 TaxPersen,\n" +
                "	r.KgsRes\n" +
                "FROM\n" +
                "	(\n" +
                "		SELECT\n" +
                "			t1.divID,\n" +
                "			COUNT(*) DaysCount,\n" +
                "			SUM( t1.TripsCount ) TripsCount,\n" +
                "			SUM( t1.nTrip1 ) nTrip1,\n" +
                "			SUM( t1.nTrip2 ) nTrip2,\n" +
                "			SUM( t1.nTrip3 ) nTrip3\n" +
                "		FROM\n" +
                "			(\n" +
                "				SELECT\n" +
                "					a.divID,\n" +
                "					CONVERT(\n" +
                "						a.hvsDt,\n" +
                "						DATE\n" +
                "					) Tgl,\n" +
                "					COUNT(*) TripsCount,\n" +
                "					SUM( CASE WHEN CONVERT( b.ActualEnd, TIME )< '10:00:00' THEN 1 ELSE 0 END ) nTrip1,\n" +
                "					SUM( CASE WHEN CONVERT( b.ActualEnd, TIME )< '13:00:00' THEN 1 ELSE 0 END ) nTrip2,\n" +
                "					SUM( CASE WHEN CONVERT( b.ActualEnd, TIME )< '18:00:00' THEN 1 ELSE 0 END ) nTrip3\n" +
                "				FROM\n" +
                "					fbjob a\n" +
                "				LEFT JOIN fbtask2 b ON\n" +
                "					a.jobid = b.jobid\n" +
                "				WHERE\n" +
                "					CONVERT(\n" +
                "						a.hvsDt,\n" +
                "						DATE\n" +
                "					) BETWEEN '"+fromDate+"' AND '"+toDate+"'\n" +
                "					AND a.DoneStatus = 'DONE'\n" +
                "					AND b.TaskSeq = 2\n" +
                "				GROUP BY\n" +
                "					a.divID,\n" +
                "					CONVERT(\n" +
                "						a.hvsDt,\n" +
                "						DATE\n" +
                "					)\n" +
                "			) t1\n" +
                "		GROUP BY\n" +
                "			t1.divID\n" +
                "	) t\n" +
                "LEFT JOIN(\n" +
                "		SELECT\n" +
                "			wa.divID,\n" +
                "			SUM( wa.QtyTerima ) ActKgs\n" +
                "		FROM\n" +
                "			fbtransportsap wa\n" +
                "		WHERE\n" +
                "			wa.Tgl BETWEEN '"+fromDate+"' AND '"+toDate+"'\n" +
                "		GROUP BY\n" +
                "			wa.divID\n" +
                "	) w ON\n" +
                "	t.divID = w.divID\n" +
                "LEFT JOIN(\n" +
                "		SELECT\n" +
                "			ha.divID,\n" +
                "			SUM( hb.size1 ) EstKgs\n" +
                "		FROM\n" +
                "			fbhvsestm ha\n" +
                "		INNER JOIN fbhvsestmdtl hb ON\n" +
                "			ha.HvsEstmID = hb.hvsEstmID\n" +
                "		WHERE\n" +
                "			CONVERT(\n" +
                "				ha.hvsDt,\n" +
                "				DATE\n" +
                "			) BETWEEN '"+fromDate+"' AND '"+toDate+"'\n" +
                "			AND ha.status = 'FNAL'\n" +
                "		GROUP BY\n" +
                "			ha.divID\n" +
                "	) h ON\n" +
                "	t.divID = h.divID\n" +
                "LEFT JOIN(\n" +
                "		SELECT\n" +
                "			ra.divID,\n" +
                "			SUM( qty ) KgsRes\n" +
                "		FROM\n" +
                "			fbleftover ra\n" +
                "		WHERE\n" +
                "			ra.PostgDate BETWEEN '"+fromDate+"' AND '"+toDate+"'\n" +
                "		GROUP BY\n" +
                "			ra.divID\n" +
                "	) r ON\n" +
                "	t.divID = r.divID";
        try (Connection con = (new Db()).getConnection("jdbc/fz");) {
            try (Statement stm = con.createStatement()) {
                ResultSet rs = stm.executeQuery(sql);
                JSONObject o;
                while (rs.next()) {
                    o = new JSONObject();
                    o.put("divID", rs.getString("divID"));
                    o.put("DaysCount", rs.getInt("DaysCount"));
                    o.put("TripsCount", rs.getInt("TripsCount"));
                    o.put("nTrip1", rs.getInt("nTrip1"));
                    o.put("nTrip2", rs.getInt("nTrip2"));
                    o.put("nTrip3", rs.getInt("nTrip3"));
                    o.put("ActKgs", df.format(rs.getDouble("ActKgs")));
                    o.put("kgPerTrip", df.format(rs.getDouble("kgPerTrip")));
                    o.put("TaxKgs", df.format(rs.getDouble("TaxKgs")));
                    o.put("TaxPersen", df.format(rs.getDouble("TaxPersen")));
                    o.put("KgsRes", df.format(rs.getDouble("KgsRes")));
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

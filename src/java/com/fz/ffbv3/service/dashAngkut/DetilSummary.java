/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.dashAngkut;

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
public class DetilSummary {
    public static JSONArray rdbTrips01(String tgl, String millId, String estateId) {
        JSONArray result = new JSONArray();
        String whr = "";
        if (millId!=null && !millId.isEmpty() && !millId.equals("<All>")) whr = whr + " and d.millId='" + millId + "'";
        if (estateId!=null && !estateId.isEmpty() && !estateId.equals("<All>")) whr = whr + " and d.estateId='" + estateId + "'";
        String sql = "SELECT p.*,     \n" +
"     		( CASE p.TripsCount WHEN 0 THEN 0 ELSE p.Kgs/p.TripsCount END ) AvgKgs \n" +
"		FROM     \n" +
"			(     \n" +
"				SELECT     \n" +
"					v.VehicleName, COUNT(*) TripsCount, sum(a.ActualKg) Kgs     \n" +
"				FROM fbjob a     \n" +
"				LEFT JOIN fbtask2 b ON a.JobID = b.JobID     \n" +
"				LEFT JOIN fbvehicle v ON a.ActualTruckID = v.VehicleID\n" +
"				left join fbdiv d on a.divID=d.divID\n" +
"				WHERE     \n" +
"					CONVERT( a.hvsDt, DATE )= '" + tgl + "'\n" +
"					AND a.DoneStatus = 'DONE' and a.reorderToJobID is null\n" +
"					AND b.TaskSeq = 2 \n" +
"					" + whr + " \n" + 
"				GROUP BY     \n" +
"					v.VehicleName     \n" +
"			) p";

/*
        String sql = "SELECT\n"
                + "	p.*,p.Kgs /(\n"
                + "		CASE p.TripsCount WHEN 0 THEN 1 ELSE p.TripsCount END\n"
                + "	) AvgKgs\n"
                + "FROM\n"
                + "	(\n"
                + "		SELECT\n"
                + "			v.VehicleName, COUNT(*) TripsCount, sum(ifnull(a.ActualKg,0)) Kgs\n"
                + "		FROM fbjob a\n"
                + "		LEFT JOIN fbtask2 b ON a.JobID = b.JobID\n"
                + "		LEFT JOIN fbvehicle v ON a.ActualTruckID = v.VehicleID\n"
                + "		WHERE\n"
                + "			CONVERT(a.hvsDt,DATE)= '" + tgl + "'\n"
                + "			AND a.DoneStatus = \"DONE\"\n"
                + "			AND b.TaskSeq = 2\n"
                + "		GROUP BY v.VehicleName\n"
                + "	) p";
*/
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
        rdbTrips02(tgl,millId,estateId);
        return result;
    }
    
    public static JSONArray rdbTrips02(String tgl, String millId, String estateId) {
        JSONArray result = new JSONArray();
        String whr = "";
        if (millId!=null && !millId.isEmpty() && !millId.equals("<All>")) 
            whr = ((whr.isEmpty())?" where ":" and ") + " a.millId='" + millId + "'";
        if (estateId!=null && !estateId.isEmpty() && !estateId.equals("<All>")) 
            whr = whr + ((whr.isEmpty())?" where ":" and ") + " a.estateId='" + estateId + "'";
        String sql = "select \n" +
"		a.millID,a.estateID,a.divID,b.*,c.Kgs KgsTax, \n" +
"               ifnull(d.remainingBin,9999) remainingBin, ifnull(b.restanKg,0) restanKg, \n" +
"               (case when ifnull(b.TripsCount,0)=0 then 0.00 else ifnull(b.ActualKgs,0)/ifnull(b.TripsCount,0) end) avgTrip, \n" +
"               (case when ifnull(c.Kgs,0)=0 then 0 else ifnull(b.ActualKgs,0)/c.Kgs end)*100 avgTax \n" + 
"	from fbdiv a\n" +
"	left join ( \n" +
"		select \n" +
"                       ba.divID, COUNT(*) TripsCount, sum(ba.ActualKg) ActualKgs, sum(ba.restanKg) restanKg, \n" +
"          		SUM( CASE WHEN CONVERT( bb.ActualEnd, TIME )< '10:00:00' THEN ifnull(ba.ActualKg,0) ELSE 0 END ) kg1,     \n" +
"          		SUM( CASE WHEN CONVERT( bb.ActualEnd, TIME )>= '10:00:00' and CONVERT( bb.ActualEnd, TIME )< '14:00:00' THEN ifnull(ba.ActualKg,0) ELSE 0 END ) kg2,     \n" +
"          		SUM( CASE WHEN CONVERT( bb.ActualEnd, TIME )>= '14:00:00' and CONVERT( bb.ActualEnd, TIME )< '18:00:00' THEN ifnull(ba.ActualKg,0) ELSE 0 END ) kg3,\n" +
"          		SUM( CASE WHEN CONVERT( bb.ActualEnd, TIME )>= '18:00:00' THEN ifnull(ba.ActualKg,0) ELSE 0 END ) kg4,\n" +
"          		SUM( CASE WHEN CONVERT( bb.ActualEnd, TIME )< '10:00:00' THEN 1 ELSE 0 END ) trip1,     \n" +
"          		SUM( CASE WHEN CONVERT( bb.ActualEnd, TIME )>= '10:00:00' and CONVERT( bb.ActualEnd, TIME )< '14:00:00' THEN 1 ELSE 0 END ) trip2,     \n" +
"          		SUM( CASE WHEN CONVERT( bb.ActualEnd, TIME )>= '14:00:00' and CONVERT( bb.ActualEnd, TIME )< '18:00:00' THEN 1 ELSE 0 END ) trip3,\n" +
"          		SUM( CASE WHEN CONVERT( bb.ActualEnd, TIME )>= '18:00:00' THEN 1 ELSE 0 END ) trip4\n" +
"		from fbjob ba \n" +
"		left join fbtask2 bb on ba.JobID=bb.JobID\n" +
"		where CONVERT(ba.hvsDt, DATE)='" + tgl + "'\n" +
"          		AND ba.DoneStatus = 'DONE' and ba.reorderToJobID is null \n" +
"          		AND bb.TaskSeq = 2     \n" +
"		group by ba.divID\n" +
"	) b on a.divID=b.divID\n" +
"	LEFT JOIN(     \n" +
"		SELECT ca.divID, SUM( cb.size1 ) Kgs     \n" +
"		FROM fbhvsestm ca     \n" +
"		LEFT JOIN fbhvsestmdtl cb ON ca.HvsEstmID = cb.hvsEstmID \n" +
"		WHERE \n" +
"			CONVERT(ca.hvsDt,DATE)= '" + tgl + "' \n" +
"			AND ca.status = 'FNAL' \n" +
"		GROUP BY ca.divID \n" +
"	) c ON a.divID = c.divID     \n" +
"       left join ( select \n" +
"                       da.divID,sum(ifnull(da.remainingBin,9999)) remainingBin\n" +
"                   from fbremainBin da \n" +
"                   where da.runID like DATE_FORMAT(now(),'%Y%m%d%')\n" +
"                   group by da.divID \n " + 
"                  ) d on a.divID=d.divID \n" +
"       " + whr + "\n" +
"	order by a.millID,a.estateID,a.divID";

        try (Connection con = (new Db()).getConnection("jdbc/fz");) {
            try (Statement stm = con.createStatement()) {
                ResultSet rs = stm.executeQuery(sql);
                JSONObject o;
                while (rs.next()) {
                    o = new JSONObject();
                    o.put("divID", rs.getString("divID"));
                    o.put("TripsCount", rs.getInt("TripsCount"));
                    o.put("kg1", rs.getInt("kg1"));
                    o.put("kg2", rs.getInt("kg2"));
                    o.put("kg3", rs.getInt("kg3"));
                    o.put("kg4", rs.getInt("kg4"));
                    o.put("trip1", rs.getInt("trip1"));
                    o.put("trip2", rs.getInt("trip2"));
                    o.put("trip3", rs.getInt("trip3"));
                    o.put("trip4", rs.getInt("trip4"));
                    o.put("ActualKgs", rs.getDouble("ActualKgs"));
                    o.put("avgTrip", rs.getDouble("avgTrip"));
                    o.put("KgsTax", rs.getDouble("KgsTax"));
                    o.put("avgTax", rs.getDouble("avgTax"));
                    o.put("remainingBin",rs.getInt("remainingBin"));
                    o.put("restanKg", rs.getDouble("restanKg"));
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
                "	t.ActualKgs /(\n" +
                "		CASE\n" +
                "			WHEN ifnull(t.DaysCount,0)= 0 THEN 1\n" +
                "			ELSE t.avgTrip\n" +
                "		END\n" +
                "	) avgKgs\n" +
                "FROM\n" +
                "	(\n" +
                "		SELECT\n" +
                "			v.VehicleName,\n" +
                "			COUNT(*) DaysCount,\n" +
                "			SUM( t1.TripCount ) TripCount,\n" +
                "			AVG( t1.TripCount ) avgTrip, \n" +
                "                       sum( t1.KgsTrip ) ActualKgs \n" +
                "		FROM\n" +
                "			(\n" +
                "				SELECT\n" +
                "					a.ActualTruckID, sum(ifnull(a.ActualKg,0)) KgsTrip, \n" +
                "					CONVERT(a.hvsDt,DATE) Tgl,\n" +
                "					COUNT(*) TripCount\n" +
                "				FROM fbjob a\n" +
                "				LEFT JOIN fbtask2 b ON a.jobid = b.JobID\n" +
                "				WHERE\n" +
                "					CONVERT(a.hvsDt,DATE) BETWEEN '"+fromDate+"' AND '"+toDate+"'\n" +
                "					AND a.DoneStatus = 'DONE'  and a.reorderToJobID is null \n" +
                "					AND b.TaskSeq = 2\n" +
                "				GROUP BY a.ActualTruckID,\n" +
                "					CONVERT(a.hvsDt,DATE)\n" +
                "			) t1\n" +
                "		LEFT JOIN fbvehicle v ON\n" +
                "			t1.ActualTruckID = v.VehicleID\n" +
                "		GROUP BY\n" +
                "			v.VehicleName\n" +
                "	) t\n" +
                "  order by t.VehicleName";

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
/*
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
                "			SUM( case when t1.nTrip1>0 then 1 else 0 end ) nTrip1,\n" +
                "			SUM( case when t1.nTrip2>0 then 1 else 0 end ) nTrip2,\n" +
                "			SUM( case when t1.nTrip3>0 then 1 else 0 end ) nTrip3\n" +
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
                "			fbtransport wa\n" +
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
                "	t.divID = r.divID \n" +
                " order by t.divID";
*/
        String sql = "SELECT\n" +
                "	t.*,\n" +
                "	t.ActKgs /(\n" +
                "		CASE WHEN ifnull(t.TripsCount,0)= 0 THEN 1" +
                "                     ELSE t.TripsCount\n" +
                "		END\n" +
                "	) kgPerTrip,\n" +
                "	h.EstKgs TaxKgs,\n" +
                "	(CASE WHEN ifnull(t.ActKgs,0)= 0 THEN 0\n" +
                "             ELSE (ifnull(t.ActKgs,0)- ifnull(h.EstKgs,0))/ t.ActKgs\n" +
                "	 END)* 100 TaxPersen,\n" +
                "	r.KgsRes\n" +
                "FROM\n" +
                "	(\n" +
                "		SELECT\n" +
                "			t1.divID, COUNT(*) DaysCount,\n" +
                "			SUM( t1.TripsCount ) TripsCount, sum(ActKgs) ActKgs,\n" +
                "			SUM( case when t1.nTrip1>0 then 1 else 0 end ) nTrip1,\n" +
                "			SUM( case when t1.nTrip2>0 then 1 else 0 end ) nTrip2,\n" +
                "			SUM( case when t1.nTrip3>0 then 1 else 0 end ) nTrip3\n" +
                "		FROM\n" +
                "			(\n" +
                "				SELECT\n" +
                "					a.divID, CONVERT(a.hvsDt,DATE) Tgl,\n" +
                "					COUNT(*) TripsCount, sum(ifnull(a.ActualKg,0)) ActKgs,\n" +
                "					SUM( CASE WHEN CONVERT( b.ActualEnd, TIME )< '10:00:00' THEN 1 ELSE 0 END ) nTrip1,\n" +
                "					SUM( CASE WHEN CONVERT( b.ActualEnd, TIME )< '13:00:00' THEN 1 ELSE 0 END ) nTrip2,\n" +
                "					SUM( CASE WHEN CONVERT( b.ActualEnd, TIME )< '18:00:00' THEN 1 ELSE 0 END ) nTrip3\n" +
                "				FROM fbjob a\n" +
                "				LEFT JOIN fbtask2 b ON a.jobid = b.jobid\n" +
                "				WHERE CONVERT(a.hvsDt, DATE) BETWEEN '"+fromDate+"' AND '"+toDate+"'\n" +
                "					AND a.DoneStatus = 'DONE'  and a.reorderToJobID is null\n" +
                "					AND b.TaskSeq = 2\n" +
                "				GROUP BY a.divID, CONVERT(a.hvsDt,DATE)\n" +
                "			) t1\n" +
                "		GROUP BY t1.divID\n" +
                "	) t\n" +
                "LEFT JOIN(\n" +
                "		SELECT ha.divID, SUM( hb.size1 ) EstKgs\n" +
                "		FROM fbhvsestm ha\n" +
                "		INNER JOIN fbhvsestmdtl hb ON ha.HvsEstmID = hb.hvsEstmID\n" +
                "		WHERE CONVERT(ha.hvsDt,DATE) BETWEEN '"+fromDate+"' AND '"+toDate+"'\n" +
                "			AND ha.status = 'FNAL'\n" +
                "		GROUP BY ha.divID\n" +
                "	) h ON t.divID = h.divID\n" +
                "LEFT JOIN(\n" +
                "		SELECT ra.divID, SUM( qty ) KgsRes\n" +
                "		FROM fbleftover ra\n" +
                "		WHERE ra.PostgDate BETWEEN '"+fromDate+"' AND '"+toDate+"'\n" +
                "		GROUP BY ra.divID\n" +
                "	) r ON t.divID = r.divID \n" +
                " order by t.divID";

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
    
    public static JSONObject rdbTripsMobile01(String tgl, String divID) {
        JSONObject result = null;
        String stgl = (tgl==null || tgl.isEmpty())?"curdate()":"'"+tgl+"'";

        String sql = "select \n" +
"		a.millID,a.estateID,a.divID,b.*,c.Kgs KgsTax, \n" +
"               (case when ifnull(b.TripsCount,0)=0 then 0.00 else ifnull(b.ActualKgs,0)/ifnull(b.TripsCount,0) end) avgTrip, \n" +
"               (case when ifnull(c.Kgs,0)=0 then 0 else ifnull(b.ActualKgs,0)/c.Kgs end)*100 avgTax \n" + 
"	from fbdiv a\n" +
"	left join ( \n" +
"		select \n" +
"                       ba.divID, COUNT(*) TripsCount, sum(ba.ActualKg) ActualKgs, \n" +
"          		SUM( CASE WHEN CONVERT( bb.ActualEnd, TIME )< '10:00:00' THEN ifnull(ba.ActualKg,0) ELSE 0 END ) kg1,     \n" +
"          		SUM( CASE WHEN CONVERT( bb.ActualEnd, TIME )>= '10:00:00' and CONVERT( bb.ActualEnd, TIME )< '14:00:00' THEN ifnull(ba.ActualKg,0) ELSE 0 END ) kg2,     \n" +
"          		SUM( CASE WHEN CONVERT( bb.ActualEnd, TIME )>= '14:00:00' and CONVERT( bb.ActualEnd, TIME )< '18:00:00' THEN ifnull(ba.ActualKg,0) ELSE 0 END ) kg3,\n" +
"          		SUM( CASE WHEN CONVERT( bb.ActualEnd, TIME )>= '18:00:00' THEN ifnull(ba.ActualKg,0) ELSE 0 END ) kg4,\n" +
"          		SUM( CASE WHEN CONVERT( bb.ActualEnd, TIME )< '10:00:00' THEN 1 ELSE 0 END ) trip1,     \n" +
"          		SUM( CASE WHEN CONVERT( bb.ActualEnd, TIME )>= '10:00:00' and CONVERT( bb.ActualEnd, TIME )< '14:00:00' THEN 1 ELSE 0 END ) trip2,     \n" +
"          		SUM( CASE WHEN CONVERT( bb.ActualEnd, TIME )>= '14:00:00' and CONVERT( bb.ActualEnd, TIME )< '18:00:00' THEN 1 ELSE 0 END ) trip3,\n" +
"          		SUM( CASE WHEN CONVERT( bb.ActualEnd, TIME )>= '18:00:00' THEN 1 ELSE 0 END ) trip4\n" +
"		from fbjob ba \n" +
"		left join fbtask2 bb on ba.JobID=bb.JobID\n" +
"		where CONVERT(ba.hvsDt, DATE)=" + stgl + "\n" +
"          		AND ba.DoneStatus = 'DONE' and ba.reorderToJobID is null \n" +
"          		AND bb.TaskSeq = 2     \n" +
"		group by ba.divID\n" +
"	) b on a.divID=b.divID\n" +
"	LEFT JOIN(     \n" +
"		SELECT ca.divID, SUM( cb.size1 ) Kgs     \n" +
"		FROM fbhvsestm ca     \n" +
"		LEFT JOIN fbhvsestmdtl cb ON ca.HvsEstmID = cb.hvsEstmID \n" +
"		WHERE \n" +
"			CONVERT(ca.hvsDt,DATE)= " + stgl + " \n" +
"			AND ca.status = 'FNAL' \n" +
"		GROUP BY ca.divID \n" +
"	) c ON a.divID = c.divID     \n" +
"       where divID='" + divID +"'\n" +
"	order by a.millID,a.estateID,a.divID";

        try (Connection con = (new Db()).getConnection("jdbc/fz");) {
            try (Statement stm = con.createStatement()) {
                ResultSet rs = stm.executeQuery(sql);
                JSONObject o;
                if (rs.next()) {
                    o = new JSONObject();
                    o.put("divID", rs.getString("divID"));
                    o.put("TripsCount", rs.getInt("TripsCount"));
                    o.put("kg1", rs.getInt("kg1"));
                    o.put("kg2", rs.getInt("kg2"));
                    o.put("kg3", rs.getInt("kg3"));
                    o.put("kg4", rs.getInt("kg4"));
                    o.put("trip1", rs.getInt("trip1"));
                    o.put("trip2", rs.getInt("trip2"));
                    o.put("trip3", rs.getInt("trip3"));
                    o.put("trip4", rs.getInt("trip4"));
                    o.put("ActualKgs", rs.getDouble("ActualKgs"));
                    o.put("avgTrip", rs.getDouble("avgTrip"));
                    o.put("KgsTax", rs.getDouble("KgsTax"));
                    o.put("avgTax", rs.getDouble("avgTax"));
//                    o.put("kgsRestan", rs.getDouble("kgsRestan"));
                    result = o;
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

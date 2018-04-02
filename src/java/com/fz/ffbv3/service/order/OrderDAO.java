/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.order;

import com.fz.ffbv3.service.hvsEstm.HvsEstm;
import com.fz.ffbv3.service.hvsEstm.HvsEstmDAO;
import com.fz.ffbv3.service.hvsEstm.HvsEstmDtl;
import com.fz.ffbv3.service.task.Task;
import com.fz.ffbv3.service.task.TaskDAO;
import com.fz.generic.Db;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.validation.constraints.AssertTrue.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Eko
 */
public class OrderDAO {
    
    public String Save(Order o) throws Exception {

        String sql = "insert into fbjob(JobSeq, PlanTruckId, DoneStatus, divID, blockId1, blockId2)"
                    + " values(" + o.JobSeq + ", " + o.TruckID + ", '" + o.DoneStatus + "','" 
                    + o.divID + "', '" + o.blockId1 + "', '" +o.blockId2 + "')";
        String d = "";
        Integer i = 0;
        String rslt = "";
        int err = 0 ;
            // query insert 
            try (Connection con = (new Db()).getConnection("jdbc/fz")){
                try (Statement stm = con.createStatement()){
                    i = stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                    ResultSet rs = stm.getGeneratedKeys();
                    if(rs.next()) rslt = rs.getString(1);
                } catch(Exception e) {
                    String s = e.getMessage();
                }
            } catch (Exception e) { 
                String s = e.getMessage();
            }
        return rslt;
    }

    public void FromEstm(String id) throws Exception {
        HvsEstmDAO hDAO = new HvsEstmDAO();
        HvsEstm he = hDAO.loadByID(id);
        

        if (he!=null && he.hvsEstmID!=null) {
            
            HvsEstmDtl d;
            int n = he.dtl.size();
            for(int i = 0; i<n; i++) {
                d = he.dtl.get(i);

            }
//            Order o = new Order();
//            o.divID =  he.divID;
        }
    }
    
    public void SaveJobTask(Order o) {
        try { 
            TaskDAO tDAO = new TaskDAO();
            String i = Save(o);
            if (i!="")
            for(Task t : o.tasks) {
                t.JobID = i; 
                tDAO.Save(t);
            }
        } catch (Exception ex) { 
            String s = ex.getMessage();
        }
    }
    
    public Order findByID(String jobID) { 
        Order o = null;
        String sql = "select * from fbjob where JobID = " + jobID ;
        try (Connection con = (new Db()).getConnection("jdbc/fz");){
            try (Statement stm = con.createStatement()) { 
                ResultSet rs = stm.executeQuery(sql);
                if (rs!=null && rs.next()) {
                    o = new Order();
                    o.JobID = rs.getString("JobID");
                    o.JobSeq = rs.getInt("JobSeq");
                    o.TruckID = rs.getString("ActualTruckID");
                    o.blockId1 = rs.getString("BetweenBlock1");
                    o.blockId2 = rs.getString("BetweenBlock2");
                    o.DoneStatus = rs.getString("DoneStatus");
                    o.divID = rs.getString("divID");
                    o.rundID = rs.getString("runID");
                }
            } catch(Exception ex) { 
                String s = ex.getMessage();
            }
        } catch (Exception ex) { 
            String s = ex.getMessage();
        }

        return o;
    }
    
    public static JSONArray rdbTrips(String sDateStart, String sDateEnd, int pil) { 
        JSONArray result = null;
        String fld = "", grp = "";
        switch (pil) { 
            case 0: fld = "a.divID, DATE_FORMAT(a.Tgl,'%Y%m%d') hvsDt, ";
                    grp = "a.divID, convert(a.hvsDt,Date)";
                    break;
            case 1: fld = "a.divID";
                    grp = "a.divID";
                    break;
            case 2: fld = "v.VehicleName";
                    grp = "v.VehicleName";
                    break;
        } 
        String sql = "select " + fld + " \n" +
                    ",sum(case when convert(b.ActualEnd,time)<='10:00:00' then 1 else 0 end) a1 \n" +
                    ",sum(case when convert(b.ActualEnd,time)<='13:00:00' then 1 else 0 end) a2 \n" +
                    ",sum(case when convert(b.ActualEnd,time)<='18:00:00' then 1 else 0 end) a3 \n" +
                    ",1 t1, 3 t2, 6 t3 \n" +
                    "from fbjob a \n" +
                    "left join fbtask2 b on a.JobID=b.JobID \n" +
                    "left join fbvehicle v on a.actualtruckid=v.vehicleid \n" +
                    "where convert(a.hvsDt,Date) between '" + sDateStart + "' and '" + sDateEnd +  
                    "' and a.DoneStatus='DONE' and b.TaskSeq=2" ;
        if (!grp.isEmpty()) sql += " group by " + grp;
        try (Connection con = (new Db()).getConnection("jdbc/fz");){
            try (Statement stm = con.createStatement()) { 
                ResultSet rs = stm.executeQuery(sql);
                JSONObject o;
                result = new JSONArray();
                while (rs.next()) {
                    o = new JSONObject();
                    switch (pil) {
                        case 0:
                            o.put("divID",rs.getString("divID"));
                            o.put("Date",rs.getString("hvsDt"));
                            break;
                        case 1: 
                            o.put("divID",rs.getString("divID"));
                            break;
                        case 2: 
                            o.put("VehicleName",rs.getString("VehicleName"));
                            break;
                    }
                    o.put("j1",rs.getInt("a1"));
                    o.put("j2",rs.getInt("a2"));
                    o.put("j3",rs.getInt("a3"));
                    result.put(o);
                }
            } catch ( Exception e) { 
                String err = e.getMessage();
            }
        } catch (Exception e) { 
            String err = e.getMessage();
        }
        return result;
    }
    
    public static JSONArray rdbTrips01(String tgl) {
        JSONArray result = new JSONArray();
        String sql = "select \n" +
"		p.*,t.Kgs,t.Kgs/(case p.TripsCount when 0 then 1 else p.TripsCount end) AvgKgs\n" +
"	from ( \n" +
"			select \n" +
"					v.VehicleName,count(*) TripsCount\n" +
"				from fbjob a \n" +
"				left join fbtask2 b on a.JobID=b.JobID\n" +
"				left join fbvehicle v on a.ActualTruckID=v.VehicleID\n" +
"				where convert(a.hvsDt,Date)='" + tgl + "' and a.DoneStatus=\"DONE\" and b.TaskSeq=2 \n" +
"				group by v.VehicleName\n" +
"			) p \n" +
"	left join (\n" +
"			select ta.VehicleID,sum(ta.QtyTerima) Kgs\n" +
"				from fbtransportsap ta \n" +
"				where ta.Tgl='" + tgl + "'\n" +
"				group by ta.VehicleID\n" +
"						) t on p.VehicleName=t.VehicleID";
        try (Connection con = (new Db()).getConnection("jdbc/fz");){
            try (Statement stm = con.createStatement()) { 
                ResultSet rs = stm.executeQuery(sql);
                JSONObject o;
                while (rs.next()) {
                    o = new JSONObject();
                    o.put("VehicleName",rs.getString("VehicleName"));
                    o.put("TripsCount",rs.getInt("TripsCount"));
                    o.put("Kgs", rs.getInt("Kgs"));
                    o.put("AvgKgs",rs.getInt("AvgKgs"));
                    result.put(o);
                }
            } catch (Exception e) { 
                String err = e.getMessage();
            }
        } catch(Exception e) { 
            String err = e.getMessage();
        }
        return result;
    }

    public static JSONArray rdbTrip3(String fromDate, String toDate) {
        JSONArray result = new JSONArray();
        String sql = "select \n" +
"		t.*, x.KgsTrip ActualKgs, x.KgsTrip/(case when ifnull(t.DaysCount,0)=0 then 1 else t.avgTrip end) avgKgs\n" +
"	from ( \n" +
"		select \n" +
"					v.VehicleName,count(*) DaysCount, sum(t1.TripCount) TripCount,avg(t1.TripCount) avgTrip\n" +
"			from ( \n" +
"					select \n" +
"								a.ActualTruckID,convert(a.hvsDt,Date) Tgl, count(*) TripCount\n" +
"							from fbjob a \n" +
"							left join fbtask2 b on a.jobid=b.JobID\n" +
"							where convert(a.hvsDt,Date) between '" + fromDate + "' and '"+toDate+"' \n" +
"									and a.DoneStatus='DONE' and b.TaskSeq=2\n" +
"							group by a.ActualTruckID,convert(a.hvsDt,Date)\n" +
"					) t1 \n" +
"				left join fbvehicle v on t1.ActualTruckID=v.VehicleID\n" +
"				group by v.VehicleName\n" +
"		) t\n" +
"	left join ( \n" +
"			select \n" +
"						x1.VehicleID,sum(x1.QtyTerima) KgsTrip \n" +
"					from fbtransportsap x1 \n" +
"					where x1.tgl \n" +
"						between '" + fromDate + "' and '"+toDate+"'\n" +
"					group by x1.VehicleID\n" +
"		) x on t.VehicleName=x.vehicleID";
        try (Connection con = (new Db()).getConnection("jdbc/fz");){
            try (Statement stm = con.createStatement()) { 
                ResultSet rs = stm.executeQuery(sql);
                JSONObject o;
                while (rs.next()) {
                    o = new JSONObject();
                    o.put("VehicleName",rs.getString("VehicleName"));
                    o.put("TripsCount",rs.getInt("TripsCount"));
                    o.put("Kgs", rs.getInt("Kgs"));
                    o.put("AvgKgs",rs.getInt("AvgKgs"));
                    result.put(o);
                }
            } catch (Exception e) { 
                String err = e.getMessage();
            }
        } catch(Exception e) { 
            String err = e.getMessage();
        }
        return result;
    }

    public static JSONArray rdbTrip4(String fromDate, String toDate) {
        JSONArray result = new JSONArray();
        String sql = "select \n" +
"		t.*,w.ActKgs ActKgs, w.ActKgs/(case when ifnull(t.TripsCount,0)=0 then 1 else t.TripsCount end) kgPerTrip\n" +
"		,h.EstKgs TaxKgs, (case when ifnull(w.ActKgs,0)=0 then 0 else (ifnull(w.ActKgs,0)-ifnull(h.EstKgs,0))/w.ActKgs end) * 100  \n" +
"		,r.KgsRes\n" +
"-- 		,sum(t.nTrip1) nTrip1, sum(t.nTrip2) nTrip2, sum(t.nTrip3) nTrip3\n" +
"	from ( \n" +
"		select \n" +
"				t1.divID,count(*) DaysCount,sum(t1.TripsCount) TripsCount, \n" +
"				sum(t1.nTrip1) nTrip1, sum(t1.nTrip2) nTrip2, sum(t1.nTrip3) nTrip3\n" +
"			from ( \n" +
"					select \n" +
"							a.divID,convert(a.hvsDt,Date) Tgl,count(*) TripsCount\n" +
"							,sum(case when convert(b.ActualEnd,time)<'10:00:00' then 1 else 0 end) nTrip1\n" +
"							,sum(case when convert(b.ActualEnd,time)<'13:00:00' then 1 else 0 end) nTrip2\n" +
"							,sum(case when convert(b.ActualEnd,time)<'18:00:00' then 1 else 0 end) nTrip3\n" +
"						from fbjob a \n" +
"						left join fbtask2 b on a.jobid=b.jobid\n" +
"						where convert(a.hvsDt,Date) between '" + fromDate + "' and '"+toDate+"'\n" +
"								and a.DoneStatus='DONE' and b.TaskSeq=2\n" +
"						group by a.divID,convert(a.hvsDt,Date)\n" +
"					) t1 \n" +
"			group by t1.divID\n" +
"		) t \n" +
"	left join ( \n" +
"			select \n" +
"						wa.divID,sum(wa.QtyTerima) ActKgs\n" +
"				from fbtransportsap wa \n" +
"				where wa.Tgl between '" + fromDate + "' and '"+toDate+"'\n" +
"				group by wa.divID\n" +
"		) w on t.divID=w.divID\n" +
"	left join (\n" +
"			select \n" +
"						ha.divID,sum(hb.size1) EstKgs\n" +
"					from fbhvsestm ha \n" +
"					inner join fbhvsestmdtl hb on ha.HvsEstmID=hb.hvsEstmID\n" +
"					where convert(ha.hvsDt,Date) between '" + fromDate + "' and '"+toDate+"'\n" +
"							and ha.status='FNAL'\n" +
"					group by ha.divID\n" +
"		) h on t.divID=h.divID\n" +
"	left join ( \n" +
"		select \n" +
"				ra.divID,sum(qty) KgsRes \n" +
"			from fbleftover ra \n" +
"			where ra.PostgDate between '" + fromDate + "' and '"+toDate+"'\n" +
"			group by ra.divID\n" +
"		) r on t.divID=r.divID";
        try (Connection con = (new Db()).getConnection("jdbc/fz");){
            try (Statement stm = con.createStatement()) { 
                ResultSet rs = stm.executeQuery(sql);
                JSONObject o;
                while (rs.next()) {
                    o = new JSONObject();
                    o.put("VehicleName",rs.getString("VehicleName"));
                    o.put("TripsCount",rs.getInt("TripsCount"));
                    o.put("Kgs", rs.getInt("Kgs"));
                    o.put("AvgKgs",rs.getInt("AvgKgs"));
                    result.put(o);
                }
            } catch (Exception e) { 
                String err = e.getMessage();
            }
        } catch(Exception e) { 
            String err = e.getMessage();
        }
        return result;
    }
    
    public static JSONObject rdbTrip5(String tgl) {
        JSONObject result = new JSONObject();
        String sql = "select \n" +
                     "      sum(ifnull(t.nTrip,0)) nTrips, count(*) nMovers \n" +
                     "	from ( \n" +
                     "          select\n" +
                     "              a.ActualTruckID,count(*) nTrip\n" +
                     "			from fbjob a\n" +
                     "		where convert(a.hvsDt,Date)='"+tgl+
                     "' and a.DoneStatus='DONE' and a.reorderToJobID is null\n" +
                     "		group by a.ActualTruckID\n" +
                     "      ) t ";
        try (Connection con = (new Db()).getConnection("jdbc/fz")) { 
            try (Statement stm = con.createStatement()) {
                ResultSet rs = stm.executeQuery(sql);
                if (rs.next()) { 
                    result.put("nTrips",rs.getInt("nTrips"));
                    result.put("nMovers",rs.getInt("nMovers"));
                } else { 
                    result.put("nTrips",0);
                    result.put("nMovers",0);

                }
            } catch (Exception e) { 
                String err = e.getMessage();
            }
        } catch (Exception e) { 
            String err = e.getMessage();
        }
        return result;
    }

    public static JSONObject rdbTrip6(String tgl) {
        JSONObject result = new JSONObject();
        String sql = "select \n" +
                     "	GROUP_CONCAT(if(t1>=1,t.divID,null) separator ', ') target1,\n" +
                     "	GROUP_CONCAT(if(t2>=3,t.divID,null) separator ', ') target2,\n" +
                     "	GROUP_CONCAT(if(t3>=6,t.divID,null) separator ', ') target3 \n" +
                     "	from (\n" +
                     "		select \n" +
                     "			a.divID\n" +
                     "			,sum(case when convert(b.ActualEnd,time)<'10:00:00' then 1 else 0 end) t1 \n" +
                     "			,sum(case when convert(b.ActualEnd,time)<'13:00:00' then 1 else 0 end) t2 \n" +
                     "			,sum(case when convert(b.ActualEnd,time)<'18:00:00' then 1 else 0 end) t3 \n" +
                     "		from fbjob a \n" +
                     "		inner join fbtask2 b on a.JobID=b.JobID\n" +
                     "		where convert(a.hvsDt,Date)='" + tgl +"' and a.DoneStatus='DONE' and a.reorderToJobID is null\n" +
                     "			and b.TaskSeq=2\n" +
                     "		group by a.divID	\n" +
                     "		) t ";
        try (Connection con = (new Db()).getConnection("jdbc/fz")) { 
            try (Statement stm = con.createStatement()) {
                ResultSet rs = stm.executeQuery(sql);
                if (rs.next()) {
                    result.put("t1", rs.getString("target1"));
                    result.put("t2", rs.getString("target2"));
                    result.put("t3", rs.getString("target3"));
                } else { 
                    result.put("t1", "");
                    result.put("t2", "");
                    result.put("t3", "");
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params.Backgrounder;

import com.fz.generic.Db;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author dwi.oktaviandi
 */
public class UpdateCostDistGoogleApi {
    Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public void finalizeCust() throws Exception{
        List<HashMap<String, String>> px = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> py = cekParam();
        
        
        if(py.get("stat").equalsIgnoreCase("TRUE")
                && py.get("run").equalsIgnoreCase("FALSE")){
            String branch = py.get("branch");
            //jumlah cust yang dicek
            int sent = Integer.valueOf(py.get("sent"));
            try{
                log.info("------- Process Start ----------------------");
                setUpdateCostDistGoogleApi("TRUE");
                log.info("------- Process Get Data Customer Start ----------------------");
                px = getCustCombination(branch, sent);
                log.info("------- Process Get Data Customer End ----------------------");
                //System.out.println(px.get(0).toString());
                log.info("------- Process Get Data Google Start ----------------------");
                String str = googleAPI(px, sent, branch);
                log.info("------- Process Get Data Google End ----------------------");
                if(str.equalsIgnoreCase("OK")){
                    setUpdateCostDistGoogleApi("FALSE");
                    log.info("------- Process End ----------------------");
                }
            }catch(Exception e){
                //throw new Exception(e); 
                log.info("------- Process Get Data Google ERROR ----------------------");
                log.info("------- Process Get Data Google End ----------------------");
                setUpdateCostDistGoogleApi("FALSE");
                log.info("------- Process End ----------------------");
            }
        }
    }
    
    public HashMap<String, String> cekParam() throws Exception{
        HashMap<String, String> py = new HashMap<String, String>();
        String str = "ERROR";

        String sql = "SELECT\n" +
                "	pa.value as stat,\n" +
                "	ps.value as branch,\n" +
                "	pd.value as sent,\n" +
                "	pf.value as run\n" +
                "FROM\n" +
                "	BOSNET1.dbo.TMS_Params pa,\n" +
                "	BOSNET1.dbo.TMS_Params ps,\n" +
                "	BOSNET1.dbo.TMS_Params pd,\n" +
                "	BOSNET1.dbo.TMS_Params pf\n" +
                "WHERE\n" +
                "	pa.param = 'UpdateCostDistGoogleApi'\n" +
                "	AND ps.param = 'UpdateCostDistBranch'\n" +
                "	AND pd.param = 'UpdateCostDistSent'\n" +
                "	AND pf.param = 'UpdateCostDistIsRunning'";
        try (Connection con = (new Db()).getConnection("jdbc/fztms");
            PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    py = new HashMap<String, String>();
                    py.put("stat", rs.getString("stat"));
                    py.put("branch", rs.getString("branch"));
                    py.put("sent", rs.getString("sent"));
                    py.put("run", rs.getString("run"));
                }
                //System.out.println("getCustCombi" + "()" + px.size());
            }
        }
        return py;
    }
    
    public String setUpdateCostDistGoogleApi(String tr) throws Exception{
        String str = "ERROR";

        String sql = "update BOSNET1.dbo.TMS_Params set value = '"+tr+"' where param = 'UpdateCostDistIsRunning'\n";
        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)) {

            con.setAutoCommit(false);
            ps.executeUpdate();
            con.setAutoCommit(true);

            str = "OK";
        } 
        return str;
    }
    
    public String googleAPI(List<HashMap<String, String>> px, int cek, String branch) throws MalformedURLException, IOException, Exception{
        String str = "ERROR";
        List<HashMap<String, String>> pz = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> py = new HashMap<String, String>();
        HashMap<String, String> pj = new HashMap<String, String>();
        
        List<HashMap<String, String>> pk = new ArrayList<HashMap<String, String>>();
        
        String cust = "";
        int x = 0;
        
        //contoh data yang diproses 
        //{lat1=-6.168805, long2=106.869, lat2=-6.29533, long1=106.829789, cust1=5810000365, cust2=5810003293}
        while(x < px.size()){
            py = new HashMap<String, String>();
            py = px.get(x);
            
            int y = 0;
            String origins = "";
            String destinations = "";
            Boolean isNew = false;
            int xy = 0;
            Boolean run = true;
            
            origins = py.get("lat1")+","+py.get("long1");
            destinations = py.get("lat2")+","+py.get("long2");
            
            if(origins.equalsIgnoreCase("-6.168805,106.829789") 
                    && destinations.equalsIgnoreCase("-6.13907,106.87")){
                System.out.println("double() " + x);
            }
            while(run){  
                //new
                if(pz.size() == 0){
                    String key = "AIzaSyBOsad8CCGx7acE9H_c-27JVH-qqKzei20";
                    String urlString = "https://maps.googleapis.com/maps/api/distancematrix/json"
                            + "?origins=" + origins
                            + "&destinations=" + destinations
                            + "&departure_time=now"
                            + "&traffic_model=best_guess"
                            + "&key=" + key;

                    pj = new HashMap<String, String>();
                    pj.put("cust", py.get("cust1") + "|" + py.get("cust2"));
                    pj.put("link", urlString);
                    //System.out.println(pj.toString());
                    pz.add(pj);
                    run = false;
                }else if(y < pz.size()){
                    String from = "";
                    String to = "";
                    String link = "";
                
                    link = pz.get(y).get("link");
                    from = link.substring((link.indexOf("=")+1),link.indexOf("&destinations"));
                    to = link.substring((link.indexOf("destinations=")+13),link.indexOf("&departure"));
                
                    String[] ary = to.split("\\|");
                    
                    for (String n : ary){
                        Boolean up = false;
                        if(ary.length == 25){
                            up = true;
                        }
                        
                        if((from.equalsIgnoreCase(origins) &&
                            !n.equalsIgnoreCase(destinations)) && !up){//!to.contains(destinations)
                            // origin sama, dest belum include
                            isNew = false;
                            xy = y;
                            //System.out.println(from + "from()" + origins + "<>" + n + "()" + destinations);
                        }else if((!from.equalsIgnoreCase(origins) &&
                                !n.equalsIgnoreCase(destinations)) || up){//!to.contains(destinations)
                            //new
                            isNew = true;
                        }
                    }                    
                    
                    if((y+1) == pz.size()){
                        
                        if(isNew){
                            //add new
                            origins = py.get("lat1")+","+py.get("long1");
                            destinations = py.get("lat2")+","+py.get("long2");

                            String key = "AIzaSyBOsad8CCGx7acE9H_c-27JVH-qqKzei20";
                            String urlString = "https://maps.googleapis.com/maps/api/distancematrix/json"
                                    + "?origins=" + origins
                                    + "&destinations=" + destinations
                                    + "&departure_time=now"
                                    + "&traffic_model=best_guess"
                                    + "&key=" + key;

                            pj = new HashMap<String, String>();
                            pj.put("link", urlString);
                            pj.put("cust", py.get("cust1") + "|" + py.get("cust2"));
                            //System.out.println(pj.toString());
                            pz.add(pj);
                            run = false;
                        }else if(!isNew){
                            // +latlon to existing one 
                            pj = new HashMap<String, String>();
                            pj = pz.get(xy);
                            link = pz.get(xy).get("link");
                            String cust2 = pz.get(xy).get("cust") + "|" + py.get("cust2");
                            pz.remove(xy);
                            
                            from = link.substring((link.indexOf("=")+1),link.indexOf("&destinations"));
                            to = link.substring((link.indexOf("destinations=")+13),link.indexOf("&departure"))
                                    + "|" + destinations;
                            
                            String key = "AIzaSyBOsad8CCGx7acE9H_c-27JVH-qqKzei20";
                            String urlString = "https://maps.googleapis.com/maps/api/distancematrix/json"
                                    + "?origins=" + from
                                    + "&destinations=" + to
                                    + "&departure_time=now"
                                    + "&traffic_model=best_guess"
                                    + "&key=" + key;

                            pj = new HashMap<String, String>();
                            pj.put("link", urlString);
                            pj.put("cust", cust2);
                            //System.out.println(pj.toString());
                            pz.add(pj);
                            run = false;
                        }
                    }
                    
                }else{
                    run = false;
                }
                
                y++;
            }
            
            
            x++;
        }
        
        String js = getGoogleData(px, pz, branch);        
        
        //System.out.println(wCust);
        
        return js;
    }
    
    public String getGoogleData(List<HashMap<String, String>> fx, List<HashMap<String, String>> fz, String branch) throws Exception{
        String str = "ERROR";
        ArrayList<JSONObject> finalCostDists = new ArrayList<>();
        int x = 0;
        while(x < fz.size()){   
            String urlString = fz.get(x).get("link").toString();
            String u = urlString.substring((urlString.indexOf("destinations=")+13),urlString.indexOf("&departure"));
            String[] from = urlString.substring((urlString.indexOf("=")+1),urlString.indexOf("&destinations")).split("\\,");
            String[] to = u.split("\\|");
            String cust = fz.get(x).get("cust").toString();
            String[] cust2 = cust.split("\\|");
            //System.out.println(fz.get(x).get("link").toString());
            try{
                URL url = new URL(urlString);
                String finalURL = url.toString();
                log.info(finalURL);
                //System.out.println(finalURL);
                URL obj = new URL(finalURL);
                HttpURLConnection htCon = (HttpURLConnection) obj.openConnection();
                htCon.setConnectTimeout(5000);
                htCon.setRequestMethod("GET");
                htCon.setRequestProperty("User-Agent", "Mozilla/5.0");
                String resultJson = "";
                
                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(htCon.getInputStream()))){
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                    }
                    in.close();
                    resultJson = response.toString();
                    //System.out.println("resultJson : " + resultJson);
                    
                    //mining jason google map api
                    JSONObject obj1 = new JSONObject(resultJson);
                    String status = obj1.getString("status");
                    if (!status.equalsIgnoreCase("OK")){
                        continue;
                    }else if (status.equalsIgnoreCase("OK")){
                        // parse ok, get rows
                        JSONArray arr = obj1.getJSONArray("rows");
                        if (arr.length() >= 1) {
                            // for each destinations
                            JSONObject row = arr.getJSONObject(0);
                            JSONArray elms = row.getJSONArray("elements");
                            for (int i =0 ;i < to.length; i++){
                                String[] ary = to[i].split("\\,");
                                
                                //JSONObject destCostDist = ary;
                                JSONObject elm = elms.getJSONObject(i);
                                
                                //System.out.println(elm.get("status"));
                                if(elm.get("status").equals("OK")){
                                    // get dur & dist
                                    JSONObject durElm = elm.getJSONObject("duration");
                                    String durVal = durElm.getString("value");

                                    JSONObject distElm = elm.getJSONObject("distance");
                                    String distVal = distElm.getString("value");

                                    String durTrfVal = durVal;
                                    if (elm.has("duration_in_traffic")){
                                        JSONObject durTrfElm = elm.getJSONObject(
                                                "duration_in_traffic");
                                        durTrfVal = durTrfElm.getString("value");
                                    }
                                    else {
                                        //System.out.println("");
                                    }

                                    // convert second to min
                                    double durValDbl = Double.parseDouble(durVal) / 60;

                                    //String[] cust = getCustArry(fx, from, ary);
                                    // add to list
                                    JSONObject custCostDist = new JSONObject();
                                    custCostDist.put("lon1", from[1]);
                                    custCostDist.put("lat1", from[0]);
                                    custCostDist.put("lon2", ary[1]);
                                    custCostDist.put("lat2", ary[0]);
                                    custCostDist.put("dist", distVal);
                                    custCostDist.put("dur", durValDbl);                                
                                    custCostDist.put("from", cust2 [0]);
                                    custCostDist.put("to", cust2 [i+1]);
                                    finalCostDists.add(custCostDist);


                                    // save to db
                                    String sql = "insert into bosnet1.dbo.TMS_CostDist"
                                        + "(lon1, lat1, lon2, lat2, dist, dur, branch"
                                        + ", from1, to1, source1)"
                                        + " values("
                                        + "'" + custCostDist.getString("lon1") + "'"
                                        + ",'" + custCostDist.getString("lat1") + "'"
                                        + ",'" + custCostDist.getString("lon2") + "'"
                                        + ",'" + custCostDist.getString("lat2") + "'"
                                        + ",'" + custCostDist.getString("dist") + "'"
                                        + ",'" + custCostDist.getString("dur") + "'"
                                        + ",'" + branch + "'"
                                        + ",'" + custCostDist.getString("from") + "'"
                                        + ",'" + custCostDist.getString("to") + "'"
                                        + ",'BackDoor'"
                                        + ")"
                                        ;
                                    //System.out.println(sql);
                                    try (Connection con = (new Db()).getConnection("jdbc/fztms")){
                                        try (PreparedStatement ps = con.prepareStatement(sql) ){
                                            ps.executeUpdate();
                                            str = "OK";
                                        }
                                    }
                                }

                                
                            }
                        }
                    }
                }
            }catch(Exception e){
                str = "ERROR";
                throw new Exception(fz.toString()); 
            }
            x++;
        }
        return str;
    }
    
    public String[] getCustArry(List<HashMap<String, String>> px, String[] from, String[] to){
        String[] cust = new String[2];
        
        int x = 0;
        while(x < px.size()){
            HashMap<String, String> py = px.get(x);
            if(py.get("long1").equalsIgnoreCase(from[1])
                    && py.get("lat1").equalsIgnoreCase(from[0])
                    && py.get("long2").equalsIgnoreCase(to[1])
                    && py.get("lat2").equalsIgnoreCase(to[0])){
                cust[0] = py.get("cust1");
                cust[1] = py.get("cust2");
            }
            x++;
        }
        
        return cust;
    }
    
    public List<HashMap<String, String>> getCustCombination(String branch, int sent) throws Exception{
        List<HashMap<String, String>> px = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> py = new HashMap<String, String>();
        
        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                java.sql.CallableStatement stmt =
                        con.prepareCall("{call bosnet1.dbo.TMS_UpdateCostDistGoogleApi(?,?)}")) {
            stmt.setString(1, branch);
            stmt.setInt(2, sent);
            //stmt.execute();
            //ps.setEscapeProcessing(true);
            //ps.setQueryTimeout(150);
            //ps.setString(1, "D312");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                py = new HashMap<String, String>();
                py.put("cust1", rs.getString("cust1"));
                py.put("long1", rs.getString("long1"));
                py.put("lat1", rs.getString("lat1"));
                py.put("cust2", rs.getString("cust2"));
                py.put("long2", rs.getString("long2"));
                py.put("lat2", rs.getString("lat2"));
                System.out.println(py.toString());
                px.add(py);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return px;
    }
}

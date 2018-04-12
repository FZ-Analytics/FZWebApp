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
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author dwi.oktaviandi
 */
public class UpdateCostDistGoogleApi {
    
    public List<HashMap<String, String>> finalizeCust() throws Exception{
        List<HashMap<String, String>> px = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> py = new HashMap<String, String>();

        String branch = "D312";
        int sent = 10;
        try{
            px = getCustCombi(branch, sent);
            List<HashMap<String, String>> str = googleAPI(px, sent, branch);
        }catch(Exception e){
            throw new Exception(e); 
        }

        return px;
    }
    
    public List<HashMap<String, String>> googleAPI(List<HashMap<String, String>> px, int cek, String branch) throws MalformedURLException, IOException, Exception{
        String str = "ERROR";
        List<HashMap<String, String>> pz = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> py = new HashMap<String, String>();
        HashMap<String, String> pj = new HashMap<String, String>();
        
        List<HashMap<String, String>> pk = new ArrayList<HashMap<String, String>>();
        
        String cust = "";
        int x = 0;
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
            
            if(origins.equalsIgnoreCase("-6.12689584239623,106.721545986395") 
                    && destinations.equalsIgnoreCase("-6.18545875197853,106.753304794418")){
                //System.out.println(origins + "(A)" + destinations);
                //System.out.println(to + "(B)" + destinations);
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
                            + "&key=" + key
                            ;

                    pj = new HashMap<String, String>();
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
                    
                    //System.out.println(from + "()" + origins);
                    //System.out.println(to + "()" + destinations);
                    // origin sama, dest belum include
                    for (String n : ary){
                        if(from.equalsIgnoreCase(origins) &&
                            !n.equalsIgnoreCase(destinations)){//!to.contains(destinations)
                            isNew = false;
                            xy = y;
                            //System.out.println(from + "from()" + origins + "<>" + n + "()" + destinations);
                        }else if(!from.equalsIgnoreCase(origins) &&
                                !n.equalsIgnoreCase(destinations)){//!to.contains(destinations)
                            //new
                            isNew = true;
                        }
                    }                    
                    
                    if((y+1) == pz.size()){
                        if(isNew){
                            origins = py.get("lat1")+","+py.get("long1");
                            destinations = py.get("lat2")+","+py.get("long2");

                            String key = "AIzaSyBOsad8CCGx7acE9H_c-27JVH-qqKzei20";
                            String urlString = "https://maps.googleapis.com/maps/api/distancematrix/json"
                                    + "?origins=" + origins
                                    + "&destinations=" + destinations
                                    + "&departure_time=now"
                                    + "&traffic_model=best_guess"
                                    + "&key=" + key
                                    ;

                            pj = new HashMap<String, String>();
                            pj.put("link", urlString);
                            //System.out.println(pj.toString());
                            pz.add(pj);
                            run = false;
                        }else if(!isNew){
                            pj = new HashMap<String, String>();
                            pj = pz.get(xy);
                            link = pz.get(xy).get("link");
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
                                    + "&key=" + key
                                    ;

                            pj = new HashMap<String, String>();
                            pj.put("link", urlString);
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
        
        List<HashMap<String, String>> js = getGoogleData(px, pz, branch);        
        
        //System.out.println(wCust);
        
        return js;
    }
    
    public List<HashMap<String, String>> getGoogleData(List<HashMap<String, String>> fx, List<HashMap<String, String>> fz, String branch) throws Exception{
        List<HashMap<String, String>> js = new ArrayList<HashMap<String, String>>();
        ArrayList<JSONObject> finalCostDists = new ArrayList<>();
        int x = 0;
        while(x < fz.size()){   
            String urlString = fz.get(x).get("link").toString();
            String u = urlString.substring((urlString.indexOf("destinations=")+13),urlString.indexOf("&departure"));
            String from = u.substring((u.indexOf("=")+1),u.indexOf("&destinations"));
            String[] to = u.split("\\|");
            System.out.println(fz.get(x).get("link").toString());
            try{
                URL url = new URL(urlString);
                String finalURL = url.toString();
                //System.out.println(finalURL);
                URL obj = new URL(finalURL);
                HttpURLConnection htCon = (HttpURLConnection) obj.openConnection();
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
                                
                                //JSONObject destCostDist = ary;
                                JSONObject elm = elms.getJSONObject(i);

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

                                // add to list
                                JSONObject custCostDist = new JSONObject();
                                //custCostDist.put("lon1", destCostDist.getString("lon1"));
                                //custCostDist.put("lat1", destCostDist.getString("lat1"));
                                //custCostDist.put("lon2", destCostDist.getString("lon2"));
                                //custCostDist.put("lat2", destCostDist.getString("lat2"));
                                custCostDist.put("dist", distVal);
                                custCostDist.put("dur", durValDbl);
                                //custCostDist.put("from", destCostDist.getString("from"));
                                //custCostDist.put("to", destCostDist.getString("to"));
                                finalCostDists.add(custCostDist);
                                                    //cx.log("finalCostDists : " + finalCostDists.toString());

                                // save to db
                                String sql = "insert into bosnet1.dbo.TMS_CostDist"
                                    + "(lon1, lat1, lon2, lat2, dist, dur, branch"
                                    + ", from1, to1)"
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
                                    + ")"
                                    ;

                                //try (PreparedStatement ps = con.prepareStatement(sql)){
                                    //ps.executeUpdate();
                                //}

                            }
                        }
                    }
                }
            }catch(Exception e){
                throw new Exception(fz.toString()); 
            }
            x++;
        }
        return js;
    }
    
    public List<HashMap<String, String>> getCustCombi(String branch, int sent) throws Exception{
        List<HashMap<String, String>> px = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> py = new HashMap<String, String>();

        String str = "ERROR";
        int x = 0;
        while(x == 0){
            String sql = "BEGIN DECLARE @sent INT = "+sent+";\n" +
                    "\n" +
                    "DECLARE @BrId VARCHAR(5)= '"+branch+"';\n" +
                    "\n" +
                    "DECLARE @Txt VARCHAR(100)= 'D312';\n" +
                    "\n" +
                    "DECLARE @rnd VARCHAR(5)=(\n" +
                    "	SELECT\n" +
                    "		CAST(\n" +
                    "			RAND()*(\n" +
                    "				CAST(\n" +
                    "					(\n" +
                    "						SELECT\n" +
                    "							COUNT( DISTINCT Customer_ID )\n" +
                    "						FROM\n" +
                    "							BOSNET1.dbo.Customer\n" +
                    "						WHERE\n" +
                    "							Sales_Office = @BrId\n" +
                    "					) AS INT\n" +
                    "				)- 1\n" +
                    "			)+ 1 AS INT\n" +
                    "		)\n" +
                    ");\n" +
                    "\n" +
                    "PRINT '@rnd ' + @rnd;\n" +
                    "\n" +
                    "DECLARE @cust AS TABLE\n" +
                    "	(\n" +
                    "		cust1 VARCHAR(100) NOT NULL,\n" +
                    "		long1 VARCHAR(100) NOT NULL,\n" +
                    "		lat1 VARCHAR(100) NOT NULL,\n" +
                    "		cust2 VARCHAR(100) NOT NULL,\n" +
                    "		long2 VARCHAR(100) NOT NULL,\n" +
                    "		lat2 VARCHAR(100) NOT NULL\n" +
                    "	);\n" +
                    "\n" +
                    "DECLARE @tCust AS TABLE\n" +
                    "	(\n" +
                    "		SNO VARCHAR(100) NOT NULL,\n" +
                    "		cust VARCHAR(100) NOT NULL,\n" +
                    "		long VARCHAR(100) NOT NULL,\n" +
                    "		lat VARCHAR(100) NOT NULL\n" +
                    "	);\n" +
                    "\n" +
                    "INSERT\n" +
                    "	INTO\n" +
                    "		@tCust SELECT\n" +
                    "			DISTINCT aq.SNO,\n" +
                    "			aq.customer_id AS cust,\n" +
                    "			aq.Long AS long,\n" +
                    "			aq.Lat AS lat\n" +
                    "		FROM\n" +
                    "			(\n" +
                    "				SELECT\n" +
                    "					ROW_NUMBER() OVER(\n" +
                    "					ORDER BY\n" +
                    "						(\n" +
                    "							SELECT\n" +
                    "								100\n" +
                    "						)\n" +
                    "					) AS SNO,\n" +
                    "					*\n" +
                    "				FROM\n" +
                    "					(\n" +
                    "						SELECT\n" +
                    "							DISTINCT oi.Customer_ID,\n" +
                    "							oi.sales_office,\n" +
                    "							po.Long,\n" +
                    "							po.Lat\n" +
                    "						FROM\n" +
                    "							BOSNET1.dbo.Customer oi\n" +
                    "						INNER JOIN BOSNET1.dbo.TMS_CustLongLat po ON\n" +
                    "							oi.Customer_ID = po.CustId\n" +
                    "						WHERE\n" +
                    "							oi.sales_office = @BrId\n" +
                    "							AND(\n" +
                    "								po.Long NOT IN(\n" +
                    "									'0',\n" +
                    "									'n/a',\n" +
                    "									''\n" +
                    "								)\n" +
                    "								OR po.Lat NOT IN(\n" +
                    "									'0',\n" +
                    "									'n/a',\n" +
                    "									''\n" +
                    "								)\n" +
                    "							)\n" +
                    "					) ar\n" +
                    "			) aq\n" +
                    "		WHERE\n" +
                    "			aq.SNO BETWEEN @rnd AND(\n" +
                    "				@rnd + @sent\n" +
                    "			);\n" +
                    "\n" +
                    "--select count(*) from @tCust;\n" +
                    " INSERT\n" +
                    "	INTO\n" +
                    "		@cust SELECT\n" +
                    "			*\n" +
                    "		FROM\n" +
                    "			(\n" +
                    "				SELECT\n" +
                    "					*\n" +
                    "				FROM\n" +
                    "					(\n" +
                    "						SELECT\n" +
                    "							*\n" +
                    "						FROM\n" +
                    "							(\n" +
                    "								SELECT\n" +
                    "									cust AS cust1,\n" +
                    "									long AS long1,\n" +
                    "									lat AS lat1\n" +
                    "								FROM\n" +
                    "									@tCust\n" +
                    "							) yu\n" +
                    "					) aq,\n" +
                    "					(\n" +
                    "						SELECT\n" +
                    "							*\n" +
                    "						FROM\n" +
                    "							(\n" +
                    "								SELECT\n" +
                    "									cust AS cust2,\n" +
                    "									long AS long2,\n" +
                    "									lat AS lat2\n" +
                    "								FROM\n" +
                    "									@tCust\n" +
                    "							) yu\n" +
                    "					) sw\n" +
                    "			) op\n" +
                    "		WHERE\n" +
                    "			cust1 <> cust2;\n" +
                    "\n" +
                    "DELETE\n" +
                    "FROM\n" +
                    "	BOSNET1.dbo.TMS_CustCombination;\n" +
                    "\n" +
                    "INSERT\n" +
                    "	INTO\n" +
                    "		BOSNET1.dbo.TMS_CustCombination SELECT\n" +
                    "			cs.*\n" +
                    "		FROM\n" +
                    "			@cust cs\n" +
                    "		LEFT OUTER JOIN BOSNET1.dbo.TMS_CostDist sd ON\n" +
                    "			cs.cust1 = SUBSTRING( from1, 1, 10 )\n" +
                    "			AND cs.cust2 = SUBSTRING( to1, 1, 10 )\n" +
                    "		WHERE\n" +
                    "			from1 IS NULL\n" +
                    "			AND to1 IS NULL;\n" +
                    "END;";
            try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)) {

                //tr = rj.DeleteResultShipment(he);              

                //con.setAutoCommit(false);
                //ps.executeUpdate();
                //con.setAutoCommit(true);
                str = "OK";

                if(str.equalsIgnoreCase("OK")){
                    px = getCustCombination();
                    x = px.size();
                }
            }
        }
        return px;
    }
    
    public List<HashMap<String, String>> getCustCombination() throws Exception{
        List<HashMap<String, String>> px = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> py = new HashMap<String, String>();

        String sql = "SELECT\n" +
                "	cc.*\n" +
                "FROM\n" +
                "	BOSNET1.dbo.TMS_CustCombination cc\n" +
                "LEFT OUTER JOIN BOSNET1.dbo.TMS_CustCostDist cd ON\n" +
                "	cc.cust1 = cd.cust1\n" +
                "	AND cc.cust2 = cd.cust2\n" +
                "WHERE\n" +
                "	cd.cust1 IS NULL\n" +
                "	AND cd.cust2 IS NULL\n" +
                "order by cc.cust1 asc";
        try (Connection con = (new Db()).getConnection("jdbc/fztms");
            PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    py = new HashMap<String, String>();
                    py.put("cust1", rs.getString("cust1"));
                    py.put("long1", rs.getString("long1"));
                    py.put("lat1", rs.getString("lat1"));
                    py.put("cust2", rs.getString("cust2"));
                    py.put("long2", rs.getString("long2"));
                    py.put("lat2", rs.getString("lat2"));
                    px.add(py);
                }
                System.out.println("getCustCombi" + "()" + px.size());
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return px;
    }
}

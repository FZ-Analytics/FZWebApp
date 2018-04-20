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
        HashMap<String, String> py = cekParam();
        
        if(py.get("stat").equalsIgnoreCase("TRUE")){
            String branch = py.get("branch");
            //jumlah cust yang dicek
            int sent = Integer.valueOf(py.get("sent"));
            try{
                setUpdateCostDistGoogleApi("FALSE");
                px = getCustCombi(branch, sent);
                System.out.println(px.get(0).toString());
                String str = googleAPI(px, sent, branch);
                if(str.equalsIgnoreCase("OK")){
                    setUpdateCostDistGoogleApi("TRUE");
                }
            }catch(Exception e){
                throw new Exception(e); 
            }
        }
        return px;
    }
    
    public HashMap<String, String> cekParam() throws Exception{
        HashMap<String, String> py = new HashMap<String, String>();
        String str = "ERROR";

        String sql = "SELECT\n" +
                "	pa.value as stat,\n" +
                "	ps.value as branch,\n" +
                "	pd.value as sent\n" +
                "FROM\n" +
                "	BOSNET1.dbo.TMS_Params pa,\n" +
                "	BOSNET1.dbo.TMS_Params ps,\n" +
                "	BOSNET1.dbo.TMS_Params pd\n" +
                "WHERE\n" +
                "	pa.param = 'UpdateCostDistGoogleApi'\n" +
                "	AND ps.param = 'UpdateCostDistBranch'\n" +
                "	AND pd.param = 'UpdateCostDistSent'\n";
        try (Connection con = (new Db()).getConnection("jdbc/fztms");
            PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    py = new HashMap<String, String>();
                    py.put("stat", rs.getString("stat"));
                    py.put("branch", rs.getString("branch"));
                    py.put("sent", rs.getString("sent"));
                }
                //System.out.println("getCustCombi" + "()" + px.size());
            }
        }
        return py;
    }
    
    public String setUpdateCostDistGoogleApi(String tr) throws Exception{
        String str = "ERROR";

        String sql = "update BOSNET1.dbo.TMS_Params set value = '"+tr+"' where param = 'UpdateCostDistGoogleApi'\n";
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
                        Boolean up = false;
                        if(ary.length == 25){
                            up = true;
                        }
                        
                        if((from.equalsIgnoreCase(origins) &&
                            !n.equalsIgnoreCase(destinations)) && !up){//!to.contains(destinations)
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
        
        /*String to = "";
        for(int a = 0; a<pz.size();a++){
            System.out.println(pz.get(a).get("link").toString());
            String link = pz.get(a).get("link");
            to += ""+ link.substring((link.indexOf("destinations=")+13),link.indexOf("&departure"));  
        }
        String[] ary = to.split("\\|");
        int cn = 0;
        for(int s = 0;ary.length > s;s++){
            cn = 0;
            for(int t = 0;ary.length > t;t++){
                if(ary[s].equalsIgnoreCase(ary[t])){
                    cn++;
                }
                
                if(cn > 1){
                    System.out.println(cn+"."+s+"."+ary[s]);
                }
            }
        }*/
        
        
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

                                    String[] cust = getCustArry(fx, from, ary);
                                    // add to list
                                    JSONObject custCostDist = new JSONObject();
                                    custCostDist.put("lon1", from[1]);
                                    custCostDist.put("lat1", from[0]);
                                    custCostDist.put("lon2", ary[1]);
                                    custCostDist.put("lat2", ary[0]);
                                    custCostDist.put("dist", distVal);
                                    custCostDist.put("dur", durValDbl);                                
                                    custCostDist.put("from", cust [0]);
                                    custCostDist.put("to", cust [1]);
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
                                    System.out.println(sql);
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
    
    public List<HashMap<String, String>> getCustCombi(String branch, int sent) throws Exception{
        List<HashMap<String, String>> px = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> py = new HashMap<String, String>();

        String str = "ERROR";
        int x = 0;
        while(x == 0){
            String sql = "DECLARE @BrId VARCHAR(5)= '"+branch+"';\n" +
                    "\n" +
                    "DECLARE @tcust AS TABLE\n" +
                    "	(\n" +
                    "		cust VARCHAR(100) NOT NULL,\n" +
                    "		long VARCHAR(100) NOT NULL,\n" +
                    "		lat VARCHAR(100) NOT NULL\n" +
                    "	);\n" +
                    "\n" +
                    "DECLARE @zcust AS TABLE\n" +
                    "	(\n" +
                    "		cust1 VARCHAR(100) NOT NULL,\n" +
                    "		long1 VARCHAR(100) NOT NULL,\n" +
                    "		lat1 VARCHAR(100) NOT NULL,\n" +
                    "		cust2 VARCHAR(100) NOT NULL,\n" +
                    "		long2 VARCHAR(100) NOT NULL,\n" +
                    "		lat2 VARCHAR(100) NOT NULL\n" +
                    "	);\n" +
                    "\n" +
                    "DECLARE @ycust AS TABLE\n" +
                    "	(\n" +
                    "		cust1 VARCHAR(100) NOT NULL,\n" +
                    "		long1 VARCHAR(100) NOT NULL,\n" +
                    "		lat1 VARCHAR(100) NOT NULL,\n" +
                    "		cust2 VARCHAR(100) NOT NULL,\n" +
                    "		long2 VARCHAR(100) NOT NULL,\n" +
                    "		lat2 VARCHAR(100) NOT NULL\n" +
                    "	);\n" +
                    "\n" +
                    "--ambil TMS_ShipmentPlan + TMS_CustLongLat\n" +
                    " INSERT\n" +
                    "	INTO\n" +
                    "		@tCust SELECT\n" +
                    "			DISTINCT sp.Customer_ID,\n" +
                    "			cl.Long,\n" +
                    "			cl.Lat\n" +
                    "		FROM\n" +
                    "			BOSNET1.dbo.TMS_ShipmentPlan sp\n" +
                    "		INNER JOIN BOSNET1.dbo.TMS_CustLongLat cl ON\n" +
                    "			sp.Customer_ID = cl.CustId\n" +
                    "		WHERE\n" +
                    "			sp.Plant = @BrId\n" +
                    "			AND cl.Long IS NOT NULL\n" +
                    "			AND cl.Lat IS NOT NULL\n" +
                    "			AND cl.Long NOT IN(\n" +
                    "				'n/a',\n" +
                    "				'0'\n" +
                    "			)\n" +
                    "			AND cl.Lat NOT IN(\n" +
                    "				'n/a',\n" +
                    "				',',\n" +
                    "				'0'\n" +
                    "			)\n" +
                    "			AND cl.Long NOT LIKE('%,%')\n" +
                    "			AND cl.Lat NOT LIKE('%,%')\n" +
                    "			AND cl.Long LIKE('106.%')\n" +
                    "			AND cl.Lat LIKE('-6.%');\n" +
                    "\n" +
                    "--cek hasil combinasi (TMS_ShipmentPlan + TMS_CustLongLat) X (TMS_ShipmentPlan + TMS_CustLongLat) dengan TMS_CostDist\n" +
                    " --select * from BOSNET1.dbo.TMS_CustCombination aq left outer join BOSNET1.dbo.TMS_CostDist aw on aq.cust1 = aw.from1 and aq.cust2 = aw.to1\n" +
                    " --where aw.from1 is null and aw.to1 is null\n" +
                    " --select * from BOSNET1.dbo.TMS_CostDist where from1 = '5810000365' and to1 = '5810002824'\n" +
                    " DELETE\n" +
                    "FROM\n" +
                    "	BOSNET1.dbo.TMS_CustCombination;\n" +
                    "\n" +
                    "INSERT\n" +
                    "	INTO\n" +
                    "		@zcust --TMS_CustCombination \n" +
                    " SELECT\n" +
                    "			top 1000 cx.*\n" +
                    "		FROM\n" +
                    "			(\n" +
                    "				SELECT\n" +
                    "					DISTINCT c1.cust AS cust1,\n" +
                    "					c1.long AS long1,\n" +
                    "					c1.lat AS lat1,\n" +
                    "					c2.cust AS cust2,\n" +
                    "					c2.long AS long2,\n" +
                    "					c2.lat AS lat2\n" +
                    "				FROM\n" +
                    "					@tCust c1,\n" +
                    "					@tCust c2\n" +
                    "				WHERE\n" +
                    "					c1.cust <> c2.cust\n" +
                    "			) cx\n" +
                    "		LEFT OUTER JOIN(\n" +
                    "				SELECT\n" +
                    "					DISTINCT lon1,\n" +
                    "					lat1,\n" +
                    "					SUBSTRING( from1, 1, 10 ) AS from1,\n" +
                    "					lon2,\n" +
                    "					lat2,\n" +
                    "					SUBSTRING( to1, 1, 10 ) AS to1\n" +
                    "				FROM\n" +
                    "					BOSNET1.dbo.TMS_CostDist\n" +
                    "				WHERE\n" +
                    "					branch = @BrId\n" +
                    "			) cy ON\n" +
                    "			cx.cust1 = cy.from1\n" +
                    "			AND cx.cust2 = cy.to1\n" +
                    "		WHERE\n" +
                    "			cy.from1 IS NULL\n" +
                    "			AND cy.to1 IS NULL;\n" +
                    "\n" +
                    "INSERT\n" +
                    "	INTO\n" +
                    "		TMS_CustCombination SELECT\n" +
                    "			top "+sent+" cust1,\n" +
                    "			long1,\n" +
                    "			lat1,\n" +
                    "			cust2,\n" +
                    "			long2,\n" +
                    "			lat2\n" +
                    "		FROM\n" +
                    "			(\n" +
                    "				SELECT\n" +
                    "					cust1,\n" +
                    "					long1,\n" +
                    "					lat1,\n" +
                    "					cust2,\n" +
                    "					long2,\n" +
                    "					lat2,\n" +
                    "					CONCAT(\n" +
                    "						cust1,\n" +
                    "						long1,\n" +
                    "						lat1,\n" +
                    "						cust2,\n" +
                    "						long2,\n" +
                    "						lat2\n" +
                    "					) AS cs\n" +
                    "				FROM\n" +
                    "					@zcust\n" +
                    "			) cx\n" +
                    "		INNER JOIN(\n" +
                    "				SELECT\n" +
                    "					cs\n" +
                    "				FROM\n" +
                    "					(\n" +
                    "						SELECT\n" +
                    "							CONCAT(\n" +
                    "								cust1,\n" +
                    "								long1,\n" +
                    "								lat1,\n" +
                    "								cust2,\n" +
                    "								long2,\n" +
                    "								lat2\n" +
                    "							) AS cs\n" +
                    "						FROM\n" +
                    "							@zcust\n" +
                    "					) aw\n" +
                    "				GROUP BY\n" +
                    "					cs\n" +
                    "				HAVING\n" +
                    "					COUNT( cs )= 1\n" +
                    "			) cy ON\n" +
                    "			cx.cs = cy.cs";
            try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)) {

                //tr = rj.DeleteResultShipment(he);              

                con.setAutoCommit(false);
                ps.executeUpdate();
                con.setAutoCommit(true);
                str = "OK";

                if(str.equalsIgnoreCase("OK")){
                    px = getCustCombination();
                    //loop jika source yang ada 0
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
                "LEFT OUTER JOIN BOSNET1.dbo.TMS_CostDist cd ON\n" +
                "	cc.cust1 = SUBSTRING( cd.from1, 1, 10 )\n" +
                "	AND cc.cust2 = SUBSTRING( cd.to1, 1, 10 )\n" +
                "WHERE\n" +
                "	cd.from1 IS NULL\n" +
                "	AND cd.to1 IS NULL\n" +
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
        }
        return px;
    }
}

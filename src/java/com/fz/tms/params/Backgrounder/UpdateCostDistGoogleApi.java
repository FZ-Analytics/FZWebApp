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
            String str = googleAPI(px, sent);
        }catch(Exception e){
            throw new Exception(e); 
        }
        
        
        
        /*
        List<HashMap<String, String>> cust = new ArrayList<HashMap<String, String>>();
        cust = getDataCust(branch);
        List<HashMap<String, String>> shipment = new ArrayList<HashMap<String, String>>();
        shipment = getShipment(branch);
        List<HashMap<String, String>> costdist = new ArrayList<HashMap<String, String>>();
        costdist = getCostDist(branch);
        
        System.out.println(cust.size() + "getShipment ()" + shipment.size() + "getCostDist ()" + costdist.size());

        int x = 0;
        int cek = 24;
        while(x < cust.size()){
            int y = 0;
            py = new HashMap<String, String>();
            Boolean find = false;
            //cek data udah ada di costdist
            while(y < costdist.size()){
                if(!cust.get(x).get("cust1").equalsIgnoreCase(costdist.get(y).get("cust1"))
                        && !cust.get(x).get("cust2").equalsIgnoreCase(costdist.get(y).get("cust2"))){
                    find = false;
                }else{
                    find = true;
                    //System.out.println("find()" + find);
                    y = costdist.size();
                }
                y++;
            }
            
            if(find){
                //udah ada di costdist di exclude
                //break;
            }else{
                int z = 0;
                while(z < shipment.size()){
                    //bandingkan cust dengan shipmentplan + prior 1
                    if(cust.get(x).get("cust1").equalsIgnoreCase(shipment.get(z).get("cust1"))
                            && cust.get(x).get("cust2").equalsIgnoreCase(shipment.get(z).get("cust2"))){
                        py.put("priority", "1");
                        z = shipment.size();
                        //System.out.println(cust.size() + "(as)" + x);
                    }else{
                        py.put("priority", "2");
                        //System.out.println(cust.size() + "(aa)" + x);
                    }                    
                    z++;
                }
                if(py.get("priority").equalsIgnoreCase("1")){
                    String prior = py.get("priority");
                    py = cust.get(x);                    
                    py.put("priority", prior);
                    System.out.println("()" + py.toString());
                    px.add(py);
                }
                //System.out.println(cust.size() + "()" + x);
                
            }           
            x++;
        }*/
        //System.out.println(px.size());
        
        //px = googleAPI(px, cek);
        //System.out.println("com.fz.tms.params.Backgrounder.UpdateCostDistGoogleApi.finalizeCust()");
        /*
        List<HashMap<String, String>> pz = new ArrayList<HashMap<String, String>>();
        String urlString = "";/*
            "https://maps.googleapis.com/maps/api/distancematrix/json"
                + "?origins=" + origLat + "," + origLon
                + "&destinations=" + destList
                + "&departure_time=now"
                + "&traffic_model=best_guess"
                ;*/
        /*
        String key = "AIzaSyBOsad8CCGx7acE9H_c-27JVH-qqKzei20";
        
        URL url = new URL(urlString + "&key=" + key);
        String finalURL = url.toString();
        URL obj = new URL(finalURL);
        
        try{
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
                System.out.println("resultJson : " + resultJson);
            }
        }catch(Exception e){
            
        }
        
        while(pz.size() < send){
            x = 0;
            while(x < px.size()){
                int y = 0;
                while(y < px.size()){
                    py = new HashMap<String, String>();
                    if(!px.get(x).get("customer_id").equalsIgnoreCase(px.get(y).get("customer_id"))){
                        
                    }
                    y++;
                }
                x++;
            }            
        }*/

        return px;
    }
    
    public String googleAPI(List<HashMap<String, String>> px, int cek) throws MalformedURLException, IOException, Exception{
        String str = "ERROR";
        List<HashMap<String, String>> pz = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> py = new HashMap<String, String>();
        
        List<HashMap<String, String>> pj = new ArrayList<HashMap<String, String>>();
        List<HashMap<String, String>> pk = new ArrayList<HashMap<String, String>>();
        
        String cust = "";
        int x = 0;
        while(x <= px.size()){
            py = new HashMap<String, String>();
            py = px.get(x);
            
            String key = "AIzaSyBOsad8CCGx7acE9H_c-27JVH-qqKzei20";
            String urlString = "https://maps.googleapis.com/maps/api/distancematrix/json"
                    //+ "?origins=" + lat1 + "," + lon1
                    //+ "&destinations=" + destList
                    + "&departure_time=now"
                    + "&traffic_model=best_guess"
                    ;
            
            URL url = new URL(urlString + "&key=" + key);
            String finalURL = url.toString();
            System.out.println(finalURL);
            URL obj = new URL(finalURL);

            try{
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
                    System.out.println("resultJson : " + resultJson);
                }
            }catch(Exception we){
                
            }
            //
            x++;
        }
        //System.out.println(wCust);
        
        return str;
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

                con.setAutoCommit(false);
                ps.executeUpdate();
                con.setAutoCommit(true);
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
                "	AND cd.cust2 IS NULL";
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

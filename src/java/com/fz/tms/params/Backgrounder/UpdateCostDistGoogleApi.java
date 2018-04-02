/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params.Backgrounder;

import com.fz.generic.Db;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
        List<HashMap<String, String>> cust = new ArrayList<HashMap<String, String>>();
        cust = getDataCust(branch);
        List<HashMap<String, String>> shipment = new ArrayList<HashMap<String, String>>();
        shipment = getShipment(branch);
        List<HashMap<String, String>> costdist = new ArrayList<HashMap<String, String>>();
        costdist = getCostDist(branch);
        
        int send = 25;

        System.out.println(cust.size() + "getShipment ()" + shipment.size() + "getCostDist ()" + costdist.size());

        int x = 0;
        int cek = 24;
        while(x < cust.size()){
            int y = 0;
            py = new HashMap<String, String>();
            Boolean find = false;
            //cek data udah ada di costdist
            while(y < costdist.size()){
                if(!cust.get(x).get("customer_id").equalsIgnoreCase(costdist.get(y).get("customer_id"))){
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
                    if(cust.get(x).get("customer_id").equalsIgnoreCase(shipment.get(z).get("customer_id"))){
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
                    py.put("customer_id", cust.get(x).get("customer_id"));            
                    py.put("Long", cust.get(x).get("Long"));
                    py.put("Lat", cust.get(x).get("Lat"));
                    System.out.println("()" + py.toString());
                    px.add(py);
                }
                //System.out.println(cust.size() + "()" + x);
                
            }           
            x++;
        }
        System.out.println(px.size());

        List<HashMap<String, String>> pz = new ArrayList<HashMap<String, String>>();
        String urlString = "";/*
            "https://maps.googleapis.com/maps/api/distancematrix/json"
                + "?origins=" + origLat + "," + origLon
                + "&destinations=" + destList
                + "&departure_time=now"
                + "&traffic_model=best_guess"
                ;*/
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
        }

        return px;
    }

    public List<HashMap<String, String>> getDataCust(String branch) throws Exception{
        List<HashMap<String, String>> px = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> py = new HashMap<String, String>();

        String sql = "SELECT\n" +
            "	DISTINCT aq.customer_id,\n" +
            "	sw.Long,\n" +
            "	sw.Lat\n" +
            "FROM\n" +
            "	BOSNET1.dbo.Customer aq\n" +
            "INNER JOIN BOSNET1.dbo.TMS_CustLongLat sw ON\n" +
            "	aq.Customer_ID = sw.CustId\n" +
            "WHERE\n" +
            "	aq.sales_office = '"+branch+"'\n" +
            "	AND(\n" +
            "		sw.Long NOT IN(\n" +
            "			'0',\n" +
            "			'n/a'\n" +
            "		)\n" +
            "		OR sw.Lat NOT IN(\n" +
            "			'0',\n" +
            "			'n/a'\n" +
            "		)\n" +
            "	)";

        try (Connection con = (new Db()).getConnection("jdbc/fztms");
            PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    py = new HashMap<String, String>();
                    py.put("customer_id", rs.getString("customer_id"));
                    py.put("Long", rs.getString("Long"));
                    py.put("Lat", rs.getString("Lat"));
                    px.add(py);
                }
            }
        }
        return px;
    }

    public List<HashMap<String, String>> getShipment(String branch) throws Exception{
        List<HashMap<String, String>> px = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> py = new HashMap<String, String>();

        String sql = "select distinct Customer_ID from BOSNET1.dbo.TMS_ShipmentPlan where Plant = '"+branch+"'";

        try (Connection con = (new Db()).getConnection("jdbc/fztms");
            PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    py = new HashMap<String, String>();
                    py.put("customer_id", rs.getString("Customer_ID"));
                    px.add(py);
                }
            }
        }
        return px;
    }

    public List<HashMap<String, String>> getCostDist(String branch) throws Exception{
        List<HashMap<String, String>> px = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> py = new HashMap<String, String>();

        String sql = "select distinct SUBSTRING(from1, 1, 10) as from1 from BOSNET1.dbo.TMS_CostDist where branch = '"+branch+"'";

        try (Connection con = (new Db()).getConnection("jdbc/fztms");
            PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    py = new HashMap<String, String>();
                    py.put("customer_id", rs.getString("from1"));
                    px.add(py);
                }
            }
        }
        return px;
    }
}

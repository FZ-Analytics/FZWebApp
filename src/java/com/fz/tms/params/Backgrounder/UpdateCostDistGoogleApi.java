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
        }
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
    
    public List<HashMap<String, String>> googleAPI(List<HashMap<String, String>> px, int cek) throws MalformedURLException, IOException, Exception{
        List<HashMap<String, String>> pz = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> py = new HashMap<String, String>();
        
        List<HashMap<String, String>> pj = new ArrayList<HashMap<String, String>>();
        List<HashMap<String, String>> pk = new ArrayList<HashMap<String, String>>();
        
        int x = 0;
        x = 0;
        while(x <= cek){
            py = new HashMap<String, String>();
            py = px.get(x);
            pz.add(py);
            //System.out.println(py.toString());
            x++;
        }
        
        System.out.println(pz.size());
        
        String wCust = "";
        x = 0;
        String cust1, cust2;
        String lon1, lat1, lon2, lat2;
        while(x <= cek){
            wCust = "";
            py = new HashMap<String, String>();
            py = pz.get(x);
            lon1 = py.get("Long");
            lat1 = py.get("Lat");
            cust1 = py.get("customer_id");
            int y = 0;
            String destList = "";
            //lon lat ori to dest
            while(y <= cek){
                py = new HashMap<String, String>();
                py = pz.get(y);
                lon2 = py.get("Long");
                lat2 = py.get("Lat");
                cust2 = py.get("customer_id");
                //System.out.println(cust1+"()"+cust2);                
                if(!cust1.equalsIgnoreCase(cust2)){
                    if(wCust.length() > 0) wCust += " or ";
                    wCust += " (from1 = '"+cust1+"' and to1 = '"+cust2+"')";
                    if(destList.length() > 0) destList += "|";
                    destList += lat2 + "," + lon2;
                }
                y++;
            }
            
            String sql = "select * from BOSNET1.dbo.TMS_CostDist where " + wCust;
            System.out.println(sql);
            /*String str = "ERROR";
            try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)) {
                try (ResultSet rs = ps.executeQuery()){
                    if (rs.next()) {
                        str = "OK";
                    }
                }
            }*/
            
            
            
            /*
            
            String key = "AIzaSyBOsad8CCGx7acE9H_c-27JVH-qqKzei20";
            String urlString = "https://maps.googleapis.com/maps/api/distancematrix/json"
                    + "?origins=" + lat1 + "," + lon1
                    + "&destinations=" + destList
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
                
            }*/
            //
            x++;
        }
        //System.out.println(wCust);
        
        return pz;
    }

    public List<HashMap<String, String>> getDataCust(String branch) throws Exception{
        List<HashMap<String, String>> px = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> py = new HashMap<String, String>();
        List<HashMap<String, String>> pz = new ArrayList<HashMap<String, String>>();

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
                
                //set kombinasi cust
                int x = 0;
                while(x <= px.size()){
                    py = new HashMap<String, String>();
                    py.put("cust1", px.get(x).get("customer_id"));
                    py.put("long1", px.get(x).get("Long"));
                    py.put("lat1", px.get(x).get("Lat"));
                    
                    int y = 0;
                    while(y <= px.size()){
                        if(!py.get("cust1").equalsIgnoreCase(px.get(y).get("customer_id"))){
                            py.put("cust2", px.get(y).get("customer_id"));
                            py.put("long2", px.get(y).get("Long"));
                            py.put("lat2", px.get(y).get("Lat"));
                            pz.add(py);
                        }
                    }
                }
                System.out.println("getDataCust()"+pz.size());
            }
        }
        return pz;
    }

    public List<HashMap<String, String>> getShipment(String branch) throws Exception{
        List<HashMap<String, String>> px = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> py = new HashMap<String, String>();
        List<HashMap<String, String>> pz = new ArrayList<HashMap<String, String>>();

        String sql = "select distinct Customer_ID from BOSNET1.dbo.TMS_ShipmentPlan where Plant = '"+branch+"'";

        try (Connection con = (new Db()).getConnection("jdbc/fztms");
            PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    py = new HashMap<String, String>();
                    py.put("customer_id", rs.getString("Customer_ID"));
                    px.add(py);
                }
                
                //set kombinasi cust
                int x = 0;
                while(x <= px.size()){
                    py = new HashMap<String, String>();
                    py.put("cust1", px.get(x).get("customer_id"));
                    
                    int y = 0;
                    while(y <= px.size()){
                        if(!py.get("cust1").equalsIgnoreCase(px.get(y).get("customer_id"))){
                            py.put("cust2", px.get(y).get("customer_id"));
                            pz.add(py);
                        }
                    }
                }
                System.out.println("getShipment()" + pz.size());
            }
        }
        return pz;
    }

    public List<HashMap<String, String>> getCostDist(String branch) throws Exception{
        List<HashMap<String, String>> px = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> py = new HashMap<String, String>();

        String sql = "select distinct SUBSTRING(to1, 1, 10) as to1, SUBSTRING(from1, 1, 10) as from1 from BOSNET1.dbo.TMS_CostDist where branch = '"+branch+"'";

        try (Connection con = (new Db()).getConnection("jdbc/fztms");
            PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    py = new HashMap<String, String>();
                    py.put("cust1", rs.getString("from1"));
                    py.put("cust2", rs.getString("to1"));
                    px.add(py);
                }
                System.out.println("getCostDist" + "()" + px.size());
            }
        }
        return px;
    }
}

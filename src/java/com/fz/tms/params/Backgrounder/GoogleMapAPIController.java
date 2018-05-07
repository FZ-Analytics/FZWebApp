/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params.Backgrounder;

import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.util.FZUtil;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import org.json.JSONObject;

/**
 *
 * @author dwi.oktaviandi
 */
public class GoogleMapAPIController implements BusinessLogic {
    
    @Override
    public void run(
            HttpServletRequest request,
            HttpServletResponse response,
            PageContext pc
    ) throws Exception {
        String branch = FZUtil.getHttpParam(request, "branch");
        String runId = FZUtil.getHttpParam(request, "runId");

        String str = "";
        str = finalizeCust(branch, runId, str);
        System.out.println("com.fz.tms.params.Backgrounder.GoogleMapAPIController.run()");
        if(str.length() > 0)    request.setAttribute("StringUrlGoogle", str);
    }

    public String finalizeCust(String branch, String runId, String str) throws Exception {
        List<HashMap<String, String>> px = new ArrayList<HashMap<String, String>>();
        List<HashMap<String, String>> pz = new ArrayList<HashMap<String, String>>();
        System.out.println("Get data cust start");
        px = getCustCombi(branch, runId);
        System.out.println("Get data cust end " + px.size());
        System.out.println("Get data google start");
        if (px.size() > 0) {
            str = googleAPI(px, branch, str);
        }

        System.out.println("Get data cust end");
        return str;
    }

    public List<HashMap<String, String>> getCustCombi(String branch, String runId) throws Exception {
        List<HashMap<String, String>> px = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> py = new HashMap<String, String>();

        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                java.sql.CallableStatement stmt
                = con.prepareCall("{call bosnet1.dbo.TMS_GetCustCombinatiion(?,?)}")) {
            stmt.setString(1, branch);
            stmt.setString(2, runId);
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
                //System.out.println(py.toString());
                px.add(py);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return px;
    }

    public String googleAPI(List<HashMap<String, String>> px, String branch, String str) throws MalformedURLException, IOException, Exception {
        List<HashMap<String, String>> pz = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> py = new HashMap<String, String>();
        HashMap<String, String> pj = new HashMap<String, String>();

        List<HashMap<String, String>> pk = new ArrayList<HashMap<String, String>>();

        String cust = "";
        int x = 0;

        //contoh data yang diproses 
        //{lat1=-6.168805, long2=106.869, lat2=-6.29533, long1=106.829789, cust1=5810000365, cust2=5810003293}
        while (x < px.size()) {
            py = new HashMap<String, String>();
            py = px.get(x);

            int y = 0;
            String origins = "";
            String destinations = "";
            Boolean isNew = false;
            int xy = 0;
            Boolean run = true;

            origins = py.get("lat1") + "," + py.get("long1");
            destinations = py.get("lat2") + "," + py.get("long2");

            if (origins.equalsIgnoreCase("-6.168805,106.829789")
                    && destinations.equalsIgnoreCase("-6.13907,106.87")) {
                System.out.println("double() " + x);
            }
            while (run) {
                //new
                if (pz.size() == 0) {
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
                } else if (y < pz.size()) {
                    String from = "";
                    String to = "";
                    String link = "";

                    link = pz.get(y).get("link");
                    from = link.substring((link.indexOf("=") + 1), link.indexOf("&destinations"));
                    to = link.substring((link.indexOf("destinations=") + 13), link.indexOf("&departure"));

                    String[] ary = to.split("\\|");

                    for (String n : ary) {
                        Boolean up = false;
                        if (ary.length == 25) {
                            up = true;
                        }

                        if ((from.equalsIgnoreCase(origins)
                                && !n.equalsIgnoreCase(destinations)) && !up) {//!to.contains(destinations)
                            // origin sama, dest belum include
                            isNew = false;
                            xy = y;
                            //System.out.println(from + "from()" + origins + "<>" + n + "()" + destinations);
                        } else if ((!from.equalsIgnoreCase(origins)
                                && !n.equalsIgnoreCase(destinations)) || up) {//!to.contains(destinations)
                            //new
                            isNew = true;
                        }
                    }

                    if ((y + 1) == pz.size()) {

                        if (isNew) {
                            //add new
                            origins = py.get("lat1") + "," + py.get("long1");
                            destinations = py.get("lat2") + "," + py.get("long2");

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
                        } else if (!isNew) {
                            // +latlon to existing one 
                            pj = new HashMap<String, String>();
                            pj = pz.get(xy);
                            link = pz.get(xy).get("link");
                            String cust2 = pz.get(xy).get("cust") + "|" + py.get("cust2");
                            pz.remove(xy);

                            from = link.substring((link.indexOf("=") + 1), link.indexOf("&destinations"));
                            to = link.substring((link.indexOf("destinations=") + 13), link.indexOf("&departure"))
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

                } else {
                    run = false;
                }

                y++;
            }

            x++;
        }

        for(int qw = 0;qw<pz.size();qw++){
            System.out.println(pz.get(qw).get("link"));
            if(!str.equalsIgnoreCase(""))   str += "||";
            str += pz.get(qw).get("link");
        }
        
        System.out.println(str);

        //System.out.println(wCust);
        return str;
    }

}

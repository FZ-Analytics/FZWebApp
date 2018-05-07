/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.api.TMS;

import static com.fz.ffbv3.api.TMS.PopupEditCustAPI.decodeContent;
import com.fz.generic.Db;
import com.fz.tms.params.model.PreRouteJobGetStatusDO;
import com.fz.tms.params.service.PreRouteJobDB;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author dwi.oktaviandi
 */
@Path("GoogleMapAPI")
public class GoogleMapAPI {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GoogleMapAPI
     */
    public GoogleMapAPI() {
    }

    /**
     * Retrieves representation of an instance of com.fz.ffbv3.api.TMS.GoogleMapAPI
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of GoogleMapAPI
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
    
    @POST
    @Path("submit")
    @Produces(MediaType.APPLICATION_JSON)
    public String getStatusDO(String content) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String tr = "";
        try{
            System.out.println(content.toString());
            String resultJson = decodeContent(content);
            //mining jason google map api
            JSONObject obj1 = new JSONObject(resultJson);
            System.out.println("com.fz.ffbv3.api.TMS.GoogleMapAPI.getStatusDO()");
            //String status = obj1.getString("status");
            
            ArrayList<JSONObject> finalCostDists = new ArrayList<>();
            
            // parse ok, get rows
            JSONArray arr = obj1.getJSONArray("rows");
            if (arr.length() >= 1) {
                String urlString = obj1.get("url").toString();
                String u = urlString.substring((urlString.indexOf("destinations=")+13),urlString.indexOf("&departure"));
                String[] from = urlString.substring((urlString.indexOf("=")+1),urlString.indexOf("&destinations")).split("\\,");
                String[] to = u.split("\\|");
                String cust = obj1.get("cust").toString();
                String[] cust2 = cust.split("\\|");
                String branch = obj1.get("branch").toString();
                
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
                            + ",'Algo_3'"
                            + ")"
                            ;
                        //System.out.println(sql);
                        try (Connection con = (new Db()).getConnection("jdbc/fztms")){
                            try (PreparedStatement ps = con.prepareStatement(sql) ){
                                ps.executeUpdate();
                                tr = "OK";
                                System.out.println(tr);
                                //trys = false;
                            }
                        }
                    }


                }
            }
            
        }catch(Exception e){
            System.out.println(e.getMessage());
            tr = "";
        }
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
        String jsonOutput = gson.toJson(tr);
        return jsonOutput;
    }
    
    public static String decodeContent(String content) throws UnsupportedEncodingException{
        content = java.net.URLDecoder.decode(content, "UTF-8");
        content = content.substring(5);
        
        return content;
    }
}

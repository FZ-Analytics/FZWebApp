/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params.map;

import static com.fz.ffbv3.api.TMS.CustomerAttrViewAPI.decodeContent;
import com.fz.generic.Db;
import com.fz.tms.params.model.Polyline_Decoder.Point;
import com.fz.tms.params.model.Polyline_Decoder.PolylineDecoder;
import com.fz.tms.params.model.googleMap.GMapsModel;
import com.fz.util.FZUtil;
import com.fz.util.UrlResponseGetter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class AccessGoogleDirection {

    String key = Constava.googleKey;

    public String renderLngLat() throws Exception {
        String rrt = "";
        String bCD = "D314";
        String rId = "20171124_101452110";

        String sql = "SELECT\n"
                + "	top 1 va.vehicle_code AS id,\n"
                + "	va.startLat,\n"
                + "	va.startLon\n"
                + "FROM\n"
                + "	BOSNET1.dbo.TMS_VehicleAtr va\n"
                + "WHERE\n"
                + "	va.branch = '" + bCD + "'\n"
                + "UNION ALL SELECT\n"
                + "	DISTINCT pr.Customer_ID AS id,\n"
                + "	pr.Lat,\n"
                + "	pr.Long\n"
                + "FROM\n"
                + "	BOSNET1.dbo.TMS_PreRouteJob pr\n"
                + "WHERE\n"
                + "	pr.RunId = '" + rId + "'\n"
                + "	AND pr.Is_Exclude = 'inc'\n"
                + "	AND pr.Is_Edit = 'edit'";

        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                List<HashMap<String, String>> asd = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> pl = new HashMap<String, String>();

                while (rs.next()) {
                    pl = new HashMap<String, String>();
                    int i = 1;
                    pl.put("id", FZUtil.getRsString(rs, i++, ""));
                    pl.put("startLat", FZUtil.getRsString(rs, i++, ""));
                    pl.put("startLon", FZUtil.getRsString(rs, i++, ""));
                    asd.add(pl);
                }

                for (int a = 0; a < asd.size(); a++) {
                    int i = 1;
                    for (int b = 0; b < asd.size(); b++) {                        
                        if (!asd.get(a).get("startLat").equalsIgnoreCase(asd.get(b).get("startLat"))
                                && !asd.get(a).get("startLon").equalsIgnoreCase(asd.get(b).get("startLon"))) {
                            i++;
                            System.out.println(i);
                            System.out.println(asd.get(a).get("startLat")+","+asd.get(a).get("startLon")
                                +"<>"+asd.get(b).get("startLat")+","+asd.get(b).get("startLon"));
                            
                            polyWaypoint(asd.get(a).get("startLat"), asd.get(a).get("startLon"), 
                                    asd.get(b).get("startLat"), asd.get(b).get("startLon")); 
                        }
                        if (i == 20) {
                            System.out.println("sleep ");
                            i = 1;
                            Thread.sleep(1000);
                        }
                    }
                }

                ArrayList<ArrayList<String>> tmp = new ArrayList<ArrayList<String>>();
                ArrayList<String> lt = new ArrayList<String>();
                /*
                for (int b = 0; b < nodes.size(); b++) {
                    if (b > 0) {
                        ArrayList<String> lonlats = nodes.get(b - 1);
                        String latSrc = lonlats.get(0);
                        String lngSrc = lonlats.get(1);
                        lonlats = nodes.get(b);
                        String latDes = lonlats.get(0);
                        String lngDes = lonlats.get(1);
                        List<Point> lg = poly(latSrc, lngSrc, latDes, lngDes);

                        for (int a = 0; a < lg.size(); a++) {
                            lt = new ArrayList<String>();
                            lt.add(String.valueOf(lg.get(a).getLat()));
                            lt.add(String.valueOf(lg.get(a).getLng()));
                            tmp.add(lt);
                        }
                        //System.out.println("com.fz.tms.params.map.LeafLetmap.run()");
                    }
                }
                 */
 /*
                String str = "";
                for (int b = 0; b < nodes.size(); b++) {
                    if (b > 0 && b != nodes.size()) {
                        ArrayList<String> lonlats = nodes.get(b - 1);
                        str = str + (str.equalsIgnoreCase("") ? "" : "|") + lonlats.get(0) + "," + lonlats.get(1);
                        System.out.println("waypoint " + str);
                    }
                }

                ArrayList<String> lonlats = nodes.get(0);
                List<Point> lg = polyWaypoint(lonlats.get(0), lonlats.get(1), lonlats.get(0), lonlats.get(1), str);
                for (int a = 0; a < lg.size(); a++) {
                    lt = new ArrayList<String>();
                    lt.add(String.valueOf(lg.get(a).getLat()));
                    lt.add(String.valueOf(lg.get(a).getLng()));
                    tmp.add(lt);
                }
                 */
            }
        }

        return rrt;
    }

    public String polyWaypoint(String latSrc, String lngSrc, String latDes, String lngDes) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        StringBuffer url = new StringBuffer();

        try {
            url.append("https://maps.googleapis.com/maps/api/directions/json?origin=" + latSrc + "," + lngSrc + "&destination=" + latDes + "," + lngDes + "&key=" + key);
        } catch (Exception e) {
            polyWaypoint(latSrc, lngSrc, latDes, lngDes);
        }

        String content = UrlResponseGetter.getURLResponse(url.toString());
        GMapsModel gAPI = gson.fromJson(content.contains("json")
                ? decodeContent(content) : content, GMapsModel.class);

        int dis = gAPI.getRoutes().get(0).getLegs().get(0).getDistance().getValue();
        int dur = gAPI.getRoutes().get(0).getLegs().get(0).getDuration().getValue();
        System.out.println(dis + "," + dur);
        //PolylineDecoder p = new PolylineDecoder();
        //List<Point> lp = p.decode(gAPI.getRoutes().get(0).getOverviewPolyline().getPoints());

        return "";
    }
}

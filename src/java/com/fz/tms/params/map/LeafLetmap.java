/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params.map;

import static com.fz.ffbv3.api.TMS.CustomerAttrViewAPI.decodeContent;
import com.fz.generic.BusinessLogic;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 *
 * @author dwi.rangga
 */
public class LeafLetmap implements BusinessLogic {

    String key = Constava.googleKey;

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response,
            PageContext pc) throws Exception {
        String runID = FZUtil.getHttpParam(request, "runID");
        String vCode = FZUtil.getHttpParam(request, "vCode");

        String sql = "SELECT\n"
                + "	lat,\n"
                + "	lon\n"
                + "FROM\n"
                + "	BOSNET1.dbo.TMS_RouteJob\n"
                + "where\n"
                + "	runID = '" + runID + "'\n"
                + "	and vehicle_code = '" + vCode + "'\n"
                + "order by\n"
                + "	routeNb,\n"
                + "	jobNb,\n"
                + "	arrive;";

        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                ArrayList<ArrayList<String>> nodes = new ArrayList<ArrayList<String>>();
                ArrayList<String> lonlat = new ArrayList<String>();

                while (rs.next()) {
                    System.out.println(nodes.size());
                    lonlat = new ArrayList<String>();

                    lonlat.add(rs.getString("lat"));
                    lonlat.add(rs.getString("lon"));
                    nodes.add(lonlat);

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

                //String str = asd.toString();
                System.out.println("tmp" + tmp.toString());
                request.setAttribute("testMap", tmp.toString());
                //request.setAttribute("branch", br);

            }
        }
    }

    public List<Point> poly(String latSrc, String lngSrc, String latDes, String lngDes) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        StringBuffer url = new StringBuffer();

        try {
            url.append("https://maps.googleapis.com/maps/api/directions/json?origin=" + latSrc + "," + lngSrc + "&destination=" + latDes + "," + lngDes + "&key=" + key);
        } catch (Exception e) {
            poly(latSrc, lngSrc, latDes, lngDes);
        }

        String content = UrlResponseGetter.getURLResponse(url.toString());
        GMapsModel gAPI = gson.fromJson(content.contains("json")
                ? decodeContent(content) : content, GMapsModel.class);

        System.out.println(gAPI.getRoutes().get(0).getOverviewPolyline().getPoints());
        PolylineDecoder p = new PolylineDecoder();
        List<Point> lp = p.decode(gAPI.getRoutes().get(0).getOverviewPolyline().getPoints());

        return lp;
    }

    public List<Point> polyWaypoint(String latSrc, String lngSrc, String latDes, String lngDes, String wayPoint) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        StringBuffer url = new StringBuffer();

        try {
            url.append("https://maps.googleapis.com/maps/api/directions/json?origin=" + latSrc + "," + lngSrc + "&destination=" + latDes + "," + lngDes + "&waypoints=" + wayPoint + "&key=" + key);
        } catch (Exception e) {
            polyWaypoint(latSrc, lngSrc, latDes, lngDes, wayPoint);
        }

        String content = UrlResponseGetter.getURLResponse(url.toString());
        GMapsModel gAPI = gson.fromJson(content.contains("json")
                ? decodeContent(content) : content, GMapsModel.class);

        System.out.println(gAPI.getRoutes().get(0).getOverviewPolyline().getPoints());
        PolylineDecoder p = new PolylineDecoder();
        List<Point> lp = p.decode(gAPI.getRoutes().get(0).getOverviewPolyline().getPoints());

        return lp;
    }

}

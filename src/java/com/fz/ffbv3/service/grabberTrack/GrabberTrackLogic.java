/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.service.grabberTrack;

import com.fz.generic.BusinessLogic;
import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Eko
 */
public class GrabberTrackLogic implements BusinessLogic{
    
    @Override
    public void run(HttpServletRequest request, HttpServletResponse response, PageContext pc) throws Exception {
        
        JSONArray aTrip = null;
        String s = request.getParameter("optTrip");

        if (s.equals("opt1")) aTrip=procGrabTrip();
        else if (s.equals("opt2")) aTrip = procHarvesterTrip("03.01.2017");
        else if (s.equals("opt3")) aTrip = procTruckTrip();
        
        request.setAttribute("jGrab", aTrip);
    }
    
    JSONArray procGrabTrip() { 
        JSONArray a = GrabberTrackDAO.lstTrip();

        JSONObject o;
        JSONObject oTrip;
        JSONArray aTrip = null;
        String oldDiv = "", curDiv;
        Date oldDate = new Date(), curDate, d2;
        d2 = new Date();
        SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try  {
            Date tanggal = null;
            for(int i=0;i<a.length();i++) {
                o = a.getJSONObject(i);
                if (o.isNull("Tanggal") || o.getString("Tanggal")=="") 
                    tanggal = null;
                else  {
                        curDiv = o.getString("divID");
                        curDate = fd.parse(o.getString("Tanggal"));
                        if (curDiv.equals(oldDiv) && oldDate.getDate()==curDate.getDate()) {
                            oldDate.setTime(oldDate.getTime() + 666000);
                        } else {
                            oldDiv = curDiv;
                            curDate.setHours(7);
                            curDate.setMinutes(40);
                            curDate.setSeconds(0);
                            oldDate = curDate;
                        }

//                        aTrip = simGrabberTrip(o.getDouble("x1"),o.getDouble("y1"),
//                                       o.getDouble("x2"), o.getDouble("y2"), oldDate);
                    d2.setTime(oldDate.getTime() + 6660000);
                    int iddiv = Integer.parseInt(o.getString("divID").substring(4));
                    aTrip = simGrabberTrip("BS29",o.getDouble("x1"),o.getDouble("y1"),
                                        o.getDouble("x2"), o.getDouble("y2"), 
                                        oldDate,d2);
                      for(int j=0;j<aTrip.length();j++) {
                            oTrip = aTrip.getJSONObject(j);
                            Date d = ft.parse(oTrip.getString("Time"));
                            java.sql.Timestamp tgl = new java.sql.Timestamp(d.getTime());
                            
                            GrabberTrackDAO.saveGPSGrabber(iddiv, o.getString("block"), 
                                        tgl, oTrip.getDouble("Latitude"), 
                                        oTrip.getDouble("Longitude"));
                        }
                }
            }
        } catch (Exception e) {
            String err = e.getMessage();
        }
        return a;
    }

    JSONArray procHarvesterTrip(String tanggal) throws Exception { 
     //Harvester
        JSONArray aD = GrabberTrackDAO.lstHarvest(tanggal);
        JSONArray aB;
        JSONObject oD, oB;
        int wkta;
        for (int i=0;i<aD.length();i++) {
            oD = aD.getJSONObject(i);
            simHarvesterTrip(oD.getString("Tanggal"),oD.getString("divID"),oD);
        }
        return aD;
    }
    
    JSONArray procTruckTrip() throws Exception { 
//Truck
        JSONArray aV = GrabberTrackDAO.lstTmpGPSTruck();
        JSONObject oV;
        JSONArray aT, aR ;
        aT = new JSONArray();
        aR = new JSONArray();
        for (int i=0;i<aV.length();i++){
            oV = aV.getJSONObject(i);
            aT = simTruckTrip(oV);
            if (aT!=null) aR.put(aT);
        }
        return aV;
    }

    public JSONArray simGrabberTrip(double lat1, double lon1, double lat2, double lon2, Date startTime) {
        JSONArray result = null;
        
        try {
            String[] aTrp = {"Start","TPH 1","TPH 2","TPH 3","TPH 4","TPH 5",
                             "TPH 6","TPH 7","TPH 8","TPH 9","TPH 10",
                             "TPH 11","jalan 0","load bin","load bin","load bin",
                             "load bin","load bin","jalan 0","jalan 1","TPH 12",
                             "TPH 13","TPH 14","TPH 15","TPH 16","TPH 17",
                             "TPH 18","TPH 19","TPH 20","TPH 21","TPH 22",
                             "jalan 1","jalan 0","load bin","load bin","load bin",
                             "load bin","load bin","jalan 0","jalan 1","jalan 2",
                             "TPH 23","TPH 24","TPH 25","TPH 26","TPH 27",
                             "TPH 28","TPH 29","TPH 30","TPH 31","TPH 32",
                             "jalan 2","jalan 1","jalan 0","load bin","load bin",
                             "load bin","load bin","load bin","TPH 33",
                             "TPH 34","TPH 35","TPH 36","TPH 37","TPH 38",
                             "TPH 39","TPH 40","TPH 41","TPH 42","TPH 43",
                             "load bin","load bin","load bin","load bin",
                             "load bin","jalan 4","TPH 44","TPH 45","TPH 46",
                             "TPH 47","TPH 48","TPH 49","TPH 50","TPH 51",
                             "TPH 52","THP 53","THP 54","jalan 4","load bin",
                             "load bin","load bin","load bin","load bin",
                             "jalan 4","jalan 5","TPH 55","TPH 56","TPH 57",
                             "TPH 58","TPH 59","TPH 60","TPH 61","TPH 62",
                             "TPH 63","TPH 64","jalan 5","jalan 4","load bin",
                             "load bin","load bin","load bin","load bin"
                        };
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cal.setTime(startTime);
            double spc = (lon2-lon1)/32;
            double[] longs = new double[33];
            double[] cpts = new double[4];
            longs[0] = (double)0;
            cpts[0] = (double)0;
            for (int i=1;i<=32; i++) {
                longs[i] = lon1 + spc * i;
                if (i==11) {
                    cpts[1] = longs[i];
                }
                else if(i==22) {
                    cpts[2] = longs[i];
                }
                else  if (i==32) {
                    cpts[3] = longs[i];
                    
                }
            }
            String sTrp = "";
            JSONObject o;
            result =  new JSONArray();
            double lon = 0;
            double lat = 0;
            int na = 0;
            boolean add;
            for (int i = 1; i<aTrp.length; i++) {
                sTrp = aTrp[i];
                o = new JSONObject();
                o.put("No",i);
                o.put("Desc", sTrp);
                //o.put("Latitude", lat1);
                lon = 0;
                add = false;
                if (sTrp.contains("TPH")) {
                    na = Integer.parseInt(sTrp.substring(4));
                    if (na>32) {
                        na = na - 32;
                        lat = lat2;
                    } 
                    else lat = lat1;
                    lon = longs[na];
                }
                else if (sTrp.contains("jalan")) {
                    na = Integer.parseInt(sTrp.substring(6));
                    if (na>3) {
                        na = na - 3;
                        lat = lat2;
                    } else { 
                        lat = lat1;
                    }
                    if (na==0) lon = lon1; //o.put("Longitude", lat1);
                    else lon = longs[na]; //o.put("Longitude", longs[na]);
                }
                else if (sTrp.contains("load bin")) { 
                    lat = lat2;
                    //o.put("Longitude",lon1);
                    lon = lon1;
                }
                o.put("Latitude",lat);
                o.put("Longitude",lon);
                
                cal.add(Calendar.MINUTE, 1);
                o.put("Time", ft.format(cal.getTime()));
                result.put(o);
                
            }
        } catch (Exception e) { 
            String err = e.getMessage();
        }
        return result;
    }

    public JSONArray simGrabberTrip(String vName,double lat1, double lon1, double lat2, double lon2, 
                                    Date startTime, Date endTime) {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONArray result = new JSONArray();
        JSONObject o;
        try 
        { 
            long itv = (endTime.getTime() - startTime.getTime())/60000;
            double y = Math.abs(lon2-lon1);
            double x = Math.abs(lat2-lat1);
            double sgn = (lon1<0)?-1:1;
            double yy1 = sgn*(Math.abs(lon1) + y/3);
            double yy2 = sgn*(Math.abs(lon1) + (y*2)/3);
            double pmnt = ((5*x) + (10*y) ) / itv;
            Date wkt = startTime;
            o = new JSONObject();
            double[][] aF = {{lat1,lon1,yy1,1,1}
                            ,{lat1,yy1,lon1,1,-1}
                            ,{lat1,lon1,lat2,0,1}
                            ,{lat2,lon1,lat1,0,-1}
                            ,{lat1,lon1,yy2,1,1}
                            ,{lat1,yy2,lon1,1,-1}
                            ,{lat1,lon1,lat2,0,1}
                            ,{lat2,lon1,lat1,0,-1}
                            ,{lat1,lon1,lon2,1,1}
                            ,{lat1,lon2,lon1,1,-1}
                            ,{lat1,lon1,lat2,0,1}
                            ,{lat2,lon1,yy1,1,1}
                            ,{lat2,yy1,lon1,1,-1}
                            ,{lat2,lon1,yy2,1,1}
                            ,{lat2,yy2,lon1,1,-1}
                            ,{lat2,lon1,lon2,1,1}
                            ,{lat2,lon2,lon1,1,-1}};

            int n = 0 ;
            double xcur = lat1;
            double ycur = lon1;
            double ncarry = 0;
            double sign;
            for (long l=0; l<=itv; l++) {
                ncarry = 0 ;
                sign = aF[n][4];
                if (aF[n][3]==0) {
                    xcur = xcur + pmnt*sign;
                    if (sign<0 && xcur<aF[n][2]) ncarry = 1; 
                    else if (sign>0 && xcur>aF[n][2]) ncarry = 1; 

                } else {
                    ycur = ycur - pmnt*sign;
                    if (sign<0 && ycur>aF[n][2]) ncarry = 1; 
                    else if (sign>0 && ycur<aF[n][2]) ncarry = 1; 

                }
              
                if (ncarry>0) {
                    n = n + 1;
                    xcur = aF[n][0];
                    ycur = aF[n][1];
                    sign = aF[n][4];

///*                   
                    if (aF[n][3]==0) { 
//                        xcur = xcur + ncarry*aF[n][4]*sign;
                    } else {
//                        ycur = ycur - ncarry*aF[n][4]*sign;
                    }
//*/
                }
                
                wkt.setTime(wkt.getTime()+60000);
                
                o = new JSONObject();
                o.put("Name",vName);
                o.put("Time", ft.format(wkt));
                o.put("Latitude",xcur);
                o.put("Longitude",ycur);
                result.put(o);
            }
            System.out.println(aF);
        } catch (Exception e) { 
            String err = e.getMessage();
        }
        return result;
    }
    
    public JSONArray simTruckTrip(JSONObject oV) {
        JSONArray result = null;
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try { 
            int VID = oV.getInt("VehicleID");
            Date tStart = ft.parse(oV.getString("TimeStart"));
            Date tEnd = ft.parse(oV.getString("TimeEnd"));
            double x1 = oV.getDouble("X1");
            double y1 = oV.getDouble("Y1");
            double x2 = oV.getDouble("X2");
            double y2 = oV.getDouble("Y2");
            double wkt = (tEnd.getTime()-tStart.getTime())/60000;//tEnd.compareTo(tStart);
            double xcur = x1;
            double ycur = y1;
            Date tcur = tStart;
            JSONObject oR = new JSONObject();
            boolean go = true;
            if (wkt>0) {
                double jrk = (Math.abs(x2-x1)+Math.abs(y2-y1));
                double pmnt = jrk/wkt;
                result = new JSONArray();
                
                if (oV.getInt("TaskSeq")==1) {
                    while (go == true) {
                        tcur.setTime(tcur.getTime()+60000);
                        oR = TruckMove(xcur,y1,x2,y1,pmnt,tcur,VID);
                        if (oR==null) go = false;
                        else {
                            xcur = oR.getDouble("Latitude");
                            result.put(oR);
                            if ((x1>x2 && xcur<=x2) || (x1<x2 && xcur>=x2))
                                go = false;
                        }
                    }
                    go = true;
                    while (go ==true) {
                        tcur.setTime(tcur.getTime()+60000);
                        oR = TruckMove(x2,ycur,x2,y2,pmnt,tcur,VID);
                        if (oR==null) go = false;
                        else {
                            ycur = oR.getDouble("Longitude");
                            result.put(oR);
                            if ((y1>y2 && ycur<=y2) || (y1<y2 && ycur>=y2))
                                go = false;
                        }
                    }
                } else { 
                    while (go == true) {
                        tcur.setTime(tcur.getTime()+60000);
                        oR = TruckMove(x1,ycur,x1,y2,pmnt,tcur,VID);
                        if (oR==null) go = false;
                        else {
                            ycur = oR.getDouble("Longitude");
                            result.put(oR);
                            if ((y1>y2 && ycur<=y2) || (y1<y2 && ycur>=y2))
                                go = false;
                        }
                    }
                    go = true;
                    while (go ==true) {
                        tcur.setTime(tcur.getTime()+60000);
                        oR = TruckMove(xcur,y2,x2,y2,pmnt,tcur,VID);
                        if (oR==null) go = false;
                        else {
                            xcur = oR.getDouble("Latitude");
                            result.put(oR);
                            if ((x1>x2 && xcur<=x2) || (x1<x2 && xcur>=x2))
                                go = false;
                        }
                    }
                }
            }
            
            
        } catch (Exception e) { 
            String err = e.getMessage();
        }
        return result;
    }

    public JSONArray simHarvesterTrip(String name, double lat1, double lon1, double lat2, double lon2, Date startTime, Date endTime) {
        JSONArray result = new JSONArray();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try { 
            long itv = (endTime.getTime() - startTime.getTime())/60000;
            double x = (lat2-lat1)/2;
            double y = Math.abs(lon2 - lon1);
//            double pmnt = (64*x)/itv;
            double xcur = lat1;
            double ycur = lon1;
//            int c = -1;
//            int r = 32;
//            int sign = 1;
            double x0 = lat1;
            double x2 = lat1 + x;
            JSONObject o;
            Date wkt = startTime;
///*
            o = new JSONObject();
            o.put("Name",name);
            o.put("Time", ft.format(wkt));
            o.put("Latitude",lat1);
            o.put("Longitude",lon1);
            result.put(o);
            int tr = 27;
            
            for (int i=1;i<=2;i++) {
                if (i==2) { 
                    x0 = lat1 + x;
                    x2 = lat2;
                }
                for (int j=1;j<=tr;j++) {
                    xcur = ((j%2)==0)?x0:x2;
                    wkt.setTime(wkt.getTime()+60000);

                    o = new JSONObject();
                    o.put("Name",name);
                    o.put("Time", ft.format(wkt));
                    o.put("Latitude",xcur);
                    o.put("Longitude",ycur);
                    result.put(o);

                    if ((i==1 && j<tr) || (i==2 && j>0)) {
                        ycur =  ycur + ((i==1)?-1:1) * y / tr;
                        wkt.setTime(wkt.getTime()+60000);

                        o = new JSONObject();
                        o.put("Name",name);
                        o.put("Time", ft.format(wkt));
                        o.put("Latitude",xcur);
                        o.put("Longitude",ycur);
                        result.put(o);
                    }

                }
            }
//*/
/*            
            for (long l=0; l<=itv; l++) {
                xcur = xcur + pmnt * sign;
                if (sign>0 && xcur>x2) {
                    sign = -1;
                    xcur = x2;
                    ycur = ycur + y/32 * c;
                } else if (sign<0 && xcur<x0) {
                    sign = 1;
                    xcur = x0;
                    ycur = ycur + y/32 * c;
                }
                
                if (c<0 && ycur<lon2) {
                    ycur = lon2;
                    c = 1;
                    x0 = lat1 + x/2;
                    x2 = lat2;
                } else if (c>0 && ycur>lon1) { 
                    ycur = lon1;
                    c = -1;
                }
                
                    wkt.setTime(wkt.getTime()+60000);

                    o = new JSONObject();
                    o.put("Name",name);
                    o.put("Time", ft.format(wkt));
                    o.put("Latitude",xcur);
                    o.put("Longitude",ycur);
                    result.put(o);

            }
//*/
        } catch (Exception e) {
            
        }
        return result;
    }

    public static JSONArray simHarvesterTrip(String tanggal, String divID, JSONObject dta) { 
        JSONArray result = null;
        try {
            JSONArray aB = dta.getJSONArray("Blocks");
            JSONObject oB;
            if (aB.length()>0) {
                double totalwkt = 250 + (int)(100*Math.random())%100;
                double totalkgs = dta.getDouble("totalKgs");
//                Random rand = new Random();
                if (totalkgs==0) totalkgs=1;
                //double wkt = 300/aB.length();
                double x1, y1, x2, y2, x, y, xcur, ycur;
                Date dtgl = new Date();
                dtgl.setHours(6);
                dtgl.setMinutes(30);
                dtgl.setSeconds(0);
                for (int j=0;j<aB.length();j++) {
                    //simHarvesterTrip("Grb1",105.49995,-1.88048,105.50281,-1.88955,d1,d2)
                    oB = aB.getJSONObject(j);
                    double wkt = totalwkt * oB.getDouble("Kgs") / totalkgs;
                    double rnd = Math.random();
                    x1 = oB.getDouble("x1");
                    y1 = oB.getDouble("y1");
                    x2 = oB.getDouble("x2");
                    y2 = oB.getDouble("y2");
                    x = Math.abs(x2-x1);
                    y = Math.abs(y2-y1);
                    double yitv = y/32;
                    xcur = x1;
                    ycur = y2 + yitv*GrabberTrackDAO.getLastTph(tanggal, divID, oB.getString("blockID"));
                    double pmnt = 3*x/wkt ;
                    int sign = 1;
                    int d = 0;
                    for (int i = 1;i<wkt;i++) {
                        xcur += pmnt*sign;
                        if (d==0) {
                            if (xcur>=x1 + x/2) {
                                xcur = x1 + x/2;
                                ycur = ycur + yitv;
                                d = 1;
                                sign = -1;
                            }
                        } else if (d==1) {
                            if (xcur<=x1) { 
                                xcur = x1;
                                d = 2;
                                sign = 1;
                            }
                        } else if (d==2) { 
                            if (xcur>x2) {
                                xcur = x2;
                                d = 3;
                                sign = -1;
                            }
                        } else if (d==3) { 
                            if (xcur<=x1+x/2) {
                                xcur = xcur + x + x/2;
                                ycur = ycur - yitv;
                                d = 4;
                                sign = 1;
                            }
                        }
                        
                        java.sql.Timestamp tgl = new java.sql.Timestamp(dtgl.getTime());
                        dtgl.setTime(dtgl.getTime() +60000);
                        GrabberTrackDAO.saveGPSHarvester(1, dta.getString("Harvester"),tgl, xcur,ycur);

                    }
                }
            }
            
        } catch (Exception e) {
            String err = e.getMessage();
        }
        return result;
    }

    public JSONObject TruckMoveX(double x1, double x2, double jrk) {
        JSONObject result = null;
        
        return result;
    }

    public JSONObject TruckMoveY(double y1, double y2, double jrk) {
        JSONObject result = null;
        
        return result;
    }
    
    public static JSONObject TruckMove(double x1, double y1, double x2, double y2, double jrk, Date dtgl, int vID) {
        JSONObject result = null;
        try {
            boolean ok = false;
            double x = x1;
            double y = y1;
            if (x1==x2) 
            {
                if (y1>y2) {
                    y -= jrk;
                    if (y<y2) y = y2;
                    ok = true;
                } else if (y1<y2) {
                    y += jrk;
                    if (y>y2) y = y2;
                    ok = true;
                }
            } else 
            {
                if (x1>x2) {
                    x -= jrk;
                    if (x<x2) x = x2;
                    ok = true;
                } else if(x1<x2) { 
                    x += jrk;
                    if (x>x2) x = x2;
                    ok = true;
                }
            }
            
            if (ok==true) { 
                result = new JSONObject();
                result.put("Latitude",x);
                result.put("Longitude",y);
                java.sql.Timestamp time = new java.sql.Timestamp(dtgl.getTime());
                boolean sv = GrabberTrackDAO.saveGPSTruck(x, y, time, vID);
            }
        } catch (Exception e) { 
            String err = e.getMessage();
        }
        return result;
    }
}

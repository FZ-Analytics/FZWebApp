/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params;

import com.fz.generic.BusinessLogic;
import com.fz.generic.Db;
import com.fz.tms.params.model.Branch;
import com.fz.tms.params.model.DODetil;
import com.fz.util.UrlResponseGetter;
import com.itextpdf.text.log.LoggerFactory;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.ProtocolException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author dwi.rangga
 */
public class test implements BusinessLogic {

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response,
            PageContext pc) throws Exception {
        String u = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=-6.236273,106.816233&destinations=-6.391124,106.837528|-6.221239,106.781893|-6.234788,106.805652|-6.372337,106.793464|-6.348418,106.855999 |-6.227749,106.857123|-6.413609,106.863557|-6.419865,106.858444|-6.255974,106.761070|-6.314218,106.805510|-6.316445,106.796720|-6.431286,106.868647|-6.255207,106.824848|-6.333716,106.806929|-6.325863,106.811193|-6.58174241,106.76761026|-6.480787,106.862208|-6.646448,106.837969|-6.569334,106.80768|-6.394623,106.793617|-6.287822,106.795386|-6.28539,106.840732|-6.285465,106.840812|-6.556346,106.778663|-6.265180,106.784351&departure_time=now&traffic_model=best_guess&key=AIzaSyBOsad8CCGx7acE9H_c-27JVH-qqKzei20";
        //String u = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=-6.254175,106.825343&destinations=-6.285465,106.840812&departure_time=now&traffic_model=best_guess&key=AIzaSyBOsad8CCGx7acE9H_c-27JVH-qqKzei20";
        //String u = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=-6.236273,106.816233&destinations=-6.391124,106.837528%7C-6.221239,106.781893%7C-6.234788,106.805652%7C-6.372337,106.793464%7C-6.348418,106.855999 %7C-6.227749,106.857123%7C-6.413609,106.863557%7C-6.419865,106.858444%7C-6.255974,106.761070%7C-6.314218,106.805510%7C-6.316445,106.796720%7C-6.431286,106.868647%7C-6.255207,106.824848%7C-6.333716,106.806929%7C-6.325863,106.811193%7C-6.58174241,106.76761026%7C-6.480787,106.862208%7C-6.646448,106.837969%7C-6.569334,106.80768%7C-6.394623,106.793617%7C-6.287822,106.795386%7C-6.28539,106.840732%7C-6.285465,106.840812%7C-6.556346,106.778663%7C-6.265180,106.784351&departure_time=now&traffic_model=best_guess&key=AIzaSyBOsad8CCGx7acE9H_c-27JVH-qqKzei20";
        //String g = test(u);
        //URL url = new URL(u);
        //JSONObject g = urlToJson(url);
        //art(u);
        //awr(u);
        art();
    }

    public String test(String u) {
        //Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            String ter = t(u);
            System.out.println("test()");
            JSONObject json = readJsonFromUrl(u);
            //String ter = aq(u);
        } catch (Exception e) {
            System.out.println("Exception parent + " + e.getMessage());
        }

        return "";
    }

    public String t(String u) {
        //Gson gson = new GsonBuilder().setPrettyPrinting().create();
        StringBuffer url = new StringBuffer();

        try {
            url.append(u);
            String content = UrlResponseGetter.getURLResponse(url.toString());
            System.out.println(content.toString());
        } catch (Exception e) {
            System.out.println("Exception()");
            System.out.println(e.getMessage());
        }
        return "";
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException, InterruptedException {

        JSONObject json = new JSONObject();
        try {
            //InputStream is = new URL(url).openStream();     
            BufferedReader rd = new BufferedReader(new InputStreamReader(((HttpURLConnection) (new URL(url)).openConnection()).getInputStream(), Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject j = new JSONObject(jsonText);
            System.out.println(j.toString());

        } catch (Exception e) {
            System.out.println("Exception() InputStream " + e.getMessage());
            Thread.sleep(3000);
            readJsonFromUrl(url);
        }
        return json;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public String aq(String url) {
        //Path rn_demo = Paths.get(url);

        //using NIO.2 unbuffered stream
        int n;
        try (InputStream in = new URL(url).openStream()) {
            System.err.println("aq try ");
            while ((n = in.read()) != -1) {
                System.out.print((char) n);
            }
        } catch (IOException e) {
            System.err.println("aq(String url) Ex " + e);
        }
        return "";
    }

    //private static final Logger LOGGER = LoggerFactory.getLogger(JsonFetcher.class);
    public static JSONObject urlToJson(URL urlString) throws JSONException {
        StringBuilder sb = null;
        URL url;
        URLConnection urlCon;
        try {
            url = urlString;
            urlCon = url.openConnection();
            BufferedReader in = null;
            if (urlCon.getHeaderField("Content-Encoding") != null
                    && urlCon.getHeaderField("Content-Encoding").equals("gzip")) {
                //LOGGER.info("reading data from URL as GZIP Stream");
                in = new BufferedReader(new InputStreamReader(new GZIPInputStream(urlCon.getInputStream())));
            } else {
                //LOGGER.info("reading data from URL as InputStream");
                in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
            }
            String inputLine;
            sb = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            System.out.println("IOException " + e.getMessage());
            //LOGGER.info("Exception while reading JSON from URL - {}", e);
        }
        if (sb != null) {
            return new JSONObject(sb.toString());
        } else {
            //System.out.println("IOException " + e.getMessage());
            //LOGGER.warn("No JSON Found in given URL");
            return new JSONObject("");
        }
    }

    public void art(String str) throws MalformedURLException, ProtocolException {
        String url = str;
        //String file = "C://file.txt";

        ReadableByteChannel readableBC = null;
        FileOutputStream fileOS = null;
        try {
            URL urlObj = new URL(url);
            readableBC = Channels.newChannel(urlObj.openStream());

            //fileOS = new FileOutputStream(file);
            fileOS.getChannel().transferFrom(readableBC, 0, Long.MAX_VALUE);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOS != null) {
                    fileOS.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (readableBC != null) {
                    readableBC.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void awr(String u) {
        Document doc = null;
        try {
            String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=-6.236273,106.816233&destinations=-6.391124,106.837528%7C-6.221239,106.781893%7C-6.234788,106.805652%7C-6.372337,106.793464%7C-6.348418,106.855999%20%7C-6.227749,106.857123%7C-6.413609,106.863557%7C-6.419865,106.858444%7C-6.255974,106.761070%7C-6.314218,106.805510%7C-6.316445,106.796720%7C-6.431286,106.868647%7C-6.255207,106.824848%7C-6.333716,106.806929%7C-6.325863,106.811193%7C-6.58174241,106.76761026%7C-6.480787,106.862208%7C-6.646448,106.837969%7C-6.569334,106.80768%7C-6.394623,106.793617%7C-6.287822,106.795386%7C-6.28539,106.840732%7C-6.285465,106.840812%7C-6.556346,106.778663%7C-6.265180,106.784351&departure_time=now&traffic_model=best_guess&key=AIzaSyBOsad8CCGx7acE9H_c-27JVH-qqKzei20";
            //url = "https://stackoverflow.com/";
            url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=-6.236273,106.816233&destinations=-6.391124,106.837528&departure_time=now&traffic_model=best_guess&key=AIzaSyBOsad8CCGx7acE9H_c-27JVH-qqKzei20";
            String ua = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.56 Safari/537.17";
            doc = Jsoup.connect(url).timeout(60 * 1000).ignoreHttpErrors(true).followRedirects(true).userAgent(ua).get();
            System.out.println("doc.toString() " + doc.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void art() throws Exception {
        URL obj = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?origins=-6.236273,106.816233&destinations=-6.391124,106.837528|-6.221239,106.781893|-6.234788,106.805652|-6.372337,106.793464|-6.348418,106.855999 |-6.227749,106.857123|-6.413609,106.863557|-6.419865,106.858444|-6.255974,106.761070|-6.314218,106.805510|-6.316445,106.796720|-6.431286,106.868647|-6.255207,106.824848|-6.333716,106.806929|-6.325863,106.811193|-6.58174241,106.76761026|-6.480787,106.862208|-6.646448,106.837969|-6.569334,106.80768|-6.394623,106.793617|-6.287822,106.795386|-6.28539,106.840732|-6.285465,106.840812|-6.556346,106.778663|-6.265180,106.784351&departure_time=now&traffic_model=best_guess&key=AIzaSyBOsad8CCGx7acE9H_c-27JVH-qqKzei20");
        //URL obj = new URL("https://www.google.co.id/");
        HttpURLConnection htCon = (HttpURLConnection) obj.openConnection();
        htCon.setRequestMethod("GET");
        htCon.setConnectTimeout(5000);
        htCon.setRequestProperty("Content-Type", "Mozilla/5.0");    
        //htCon.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        //htCon.setRequestProperty("content-encoding", "gzip");
        //htCon.setReadTimeout(5000);
        System.out.println(htCon.getConnectTimeout());
        String resultJson = "";
        System.out.println("xxxMessage() ");
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(htCon.getInputStream()))) {
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            System.out.println("zxcMessage() ");
            in.close();
            resultJson = response.toString();
        } catch (Exception e) {
            System.out.println("e.getMessage() " + e.getMessage());
            //cx.log(e.getMessage());
            //continue;
        }
    }
}

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
import java.sql.CallableStatement;
import java.sql.SQLException;
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
        selectCust();
    }

    public List<HashMap<String, String>> selectCust() throws SQLException {
        List<HashMap<String, String>> px = new ArrayList<HashMap<String, String>>();
        String sql = "EXEC bosnet1.dbo.TMS_GetCustLongLat ?";

        HashMap<String, String> pl = new HashMap<String, String>();
        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                CallableStatement cs = con.prepareCall(sql)) {
            cs.setString(1, "D312");
            cs.execute();
            //ps.setEscapeProcessing(true);
            //ps.setQueryTimeout(150);
            //ps.setString(1, "D312");
            ResultSet rs = (ResultSet) cs.getResultSet();
            while (rs.next()) {
                System.out.println(rs.getString("cust1"));
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return px;
    }
}

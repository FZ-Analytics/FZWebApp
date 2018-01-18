/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.api.TMS;

import static com.fz.ffbv3.api.TMS.PopupEditCustAPI.decodeContent;
import com.fz.tms.params.App;
import com.fz.tms.params.model.Customer;
import com.fz.tms.params.service.CustomerAttrDB;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

/**
 * REST Web Service
 *
 * @author Administrator
 */
@Path("customerAttrView")
public class CustomerAttrViewAPI {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CustomerAttrView
     */
    public CustomerAttrViewAPI() {
    }

    /**
     * Retrieves representation of an instance of
     * com.fz.ffbv3.api.TMS.CustomerAttrView
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of CustomerAttrView
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }

    @POST
    @Path("submitCust")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDO(String content) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String str = "ERROR";
        try {
            CustomerAttrDB db = new CustomerAttrDB();
            Customer he = gson.fromJson(content.contains("json")
                    ? decodeContent(content) : content, Customer.class);
            str = cek(he);
            if (str == "OK") {
                str = db.insert(he, he.flag);
            }

        } catch (Exception e) {
            str = "ERROR";
        }
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
        String jsonOutput = gson.toJson(str);
        return jsonOutput;
    }

    @POST
    @Path("submitTest")
    @Produces(MediaType.APPLICATION_JSON)
    public String test(String content) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String str = "ERROR";
        try {
            CustomerAttrDB db = new CustomerAttrDB();
            Customer he = gson.fromJson(content.contains("json")
                    ? decodeContent(content) : content, Customer.class);
            Document document = new Document();
            // step 2
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("runjsp.pdf"));
            // step 3
            document.open();
            // step 4
            XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                    new FileInputStream("runResult.jsp"));
            //step 5
            document.close();

            System.out.println("PDF Created!");

        } catch (Exception e) {
            str = "ERROR";
        }
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
        String jsonOutput = gson.toJson(str);
        return jsonOutput;
    }

    public String cek(Customer c) {
        String str = "OK";

        if (c.customer_id.length() == 0 || c.service_time == 0 || c.deliv_start.length() == 0
                || c.deliv_end.length() == 0 || c.vehicle_type_list.length() == 0) {
            str = "Lengkapi parameter";
        }
        return str;
    }

    public static String decodeContent(String content) throws UnsupportedEncodingException {
        content = java.net.URLDecoder.decode(content, "UTF-8");
        content = content.substring(5);

        return content;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.api.TMS;

import static com.fz.ffbv3.api.TMS.PopupEditCustAPI.decodeContent;
import com.fz.tms.params.model.Customer;
import com.fz.tms.params.model.OptionModel;
import com.fz.tms.params.model.Vehicle;
import com.fz.tms.params.service.CustomerAttrDB;
import com.fz.tms.params.service.VehicleAttrDB;
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

/**
 * REST Web Service
 *
 * @author Administrator
 */
@Path("vehicleAttrView")
public class VehicleAttrViewAPI {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of VehicleAttrViewAPI
     */
    public VehicleAttrViewAPI() {
    }

    /**
     * Retrieves representation of an instance of com.fz.ffbv3.api.TMS.VehicleAttrViewAPI
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of VehicleAttrViewAPI
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
    
    @POST
    @Path("submitVehi")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDO(String content) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String str = "ERROR";
        try{
            VehicleAttrDB db = new VehicleAttrDB();
            Vehicle he = gson.fromJson(content.contains("json") ? 
                    decodeContent(content) : content, Vehicle.class);
            str = cek(he);
            if(str == "OK"){
                str = db.insert(he, he.flag);
            }
                      
        }catch(Exception e){
            str = "ERROR";
            System.out.println(e.getMessage());
        }
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
        String jsonOutput = gson.toJson(str);
        return jsonOutput;
    }
    
    @POST
    @Path("getNamaDriver")
    @Produces(MediaType.APPLICATION_JSON)
    public String getNamaDriver(String content) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String str = "ERROR";
        List<OptionModel> ay = new ArrayList<OptionModel>();
        try{
            VehicleAttrDB db = new VehicleAttrDB();
            Vehicle he = gson.fromJson(content.contains("json") ? 
                    decodeContent(content) : content, Vehicle.class);
            
            List<Vehicle> st = db.getDriver(he.branch, he.IdDriver);
            
            if(st.size() > 0){
                OptionModel om = new OptionModel();
                om.Display = st.get(0).NamaDriver;
                om.Value = st.get(0).IdDriver;
                ay.add(om);
                //str = st.get(0).NamaDriver;
            }
                      
        }catch(Exception e){
            str = "ERROR";
        }
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
        String jsonOutput = gson.toJson(ay);
        return jsonOutput;
    }
    
    public String cek(Vehicle c){
        String str = "OK";
        
        if(c.vehicle_code.length() == 0 || c.branch.length() == 0 || c.startLon.length() == 0 || 
                c.startLat.length() == 0 || c.endLon.length() == 0 || c.endLat.length() == 0 || 
                c.startTime.length() == 0 || c.endTime.length() == 0 || c.source1.length() == 0 ||
                c.vehicle_type.length() == 0 || c.weight.length() == 0 || c.volume.length() == 0 ||
                c.included.length() == 0 || c.costPerM.length() == 0 || c.fixedCost.length() == 0 ||
                c.Channel.length() == 0 || c.IdDriver.length() == 0 || c.NamaDriver.length() == 0){
            str = "Lengkapi parameter";
        }
        
        return str;
    }
    
    public static String decodeContent(String content) throws UnsupportedEncodingException{
        content = java.net.URLDecoder.decode(content, "UTF-8");
        content = content.substring(5);
        
        return content;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.api.dashProdAPI;

import com.fz.generic.Db;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author Eko
 */
@Path("coba")
public class dashProdAPI01 {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of dashProdAPI01
     */
    public dashProdAPI01() {
    }

    @GET
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String getJson() {
        String sql = "";
        
        // init resulting JSON 
        JSONObject result = new JSONObject();
        
        // get db con from pool
        try (Connection con = (new Db()).getConnection("jdbc/fz")){
            
            try (Statement stm = con.createStatement()){
            
                // create sql
                sql = "select 1+1" ;
                
                // query
                try (ResultSet rs = stm.executeQuery(sql)){
                    
                    // if record exist
                    if (rs.next())
                        
                        // add to resulting json
                        result.append("result", rs.getString(1));
                    
                    // return the JSON
                    return result.toString();
                }
            }
        }
        catch(Exception e){
            // do err handling
        }
        return result.toString();

    }

    /**
     * PUT method for updating or creating an instance of APISample
     * @param content representation for the resource
     */
    @PUT
    @Consumes(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}

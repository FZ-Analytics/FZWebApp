/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.api.TMS;

import com.fz.generic.Db;
import com.fz.tms.params.model.Vehicle;
import com.fz.util.FZUtil;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * REST Web Service
 *
 * @author dwi.oktaviandi
 */
@Path("ShowPreRouteVehicle")
public class ShowPreRouteVehicleAPI {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ShowPreRouteVehicleAPI
     */
    public ShowPreRouteVehicleAPI() {
    }

    /**
     * Retrieves representation of an instance of com.fz.ffbv3.api.TMS.ShowPreRouteVehicleAPI
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of ShowPreRouteVehicleAPI
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
    
    @POST
    @Path("AddVehicle")
    @Produces(MediaType.APPLICATION_JSON)
    public String AddVehicle(String content) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String tr = "ERROR";
        
        String sql = "";
        
        try{
            Vehicle he = gson.fromJson(content.contains("json") ? 
                    decodeContent(content) : content, Vehicle.class);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            
            String cek = isExits(he);
            if(cek.equalsIgnoreCase("OK")){
                sql = "update BOSNET1.dbo.TMS_PreRouteVehicle set isActive = '1' "
                    + "where RunId = '"+he.RunId+"' and vehicle_code = '"+he.vehicle_code+"' and isActive = '0'";
            }else{
                sql = "INSERT\n" +
                    "	INTO\n" +
                    "		bosnet1.dbo.TMS_PreRouteVehicle(\n" +
                    "			RunId,\n" +
                    "			vehicle_code,\n" +
                    "			weight,\n" +
                    "			volume,\n" +
                    "			vehicle_type,\n" +
                    "			branch,\n" +
                    "			startLon,\n" +
                    "			startLat,\n" +
                    "			endLon,\n" +
                    "			endLat,\n" +
                    "			startTime,\n" +
                    "			endTime,\n" +
                    "			source1,\n" +
                    "			UpdatevDate,\n" +
                    "			CreateDate,\n" +
                    "			isActive,\n" +
                    "			fixedCost,\n" +
                    "			costPerM,\n" +
                    "			costPerServiceMin,\n" +
                    "			costPerTravelMin,\n" +
                    "			IdDriver,\n" +
                    "			NamaDriver,\n" +
                    "			agent_priority,\n" +
                    "			max_cust\n" +
                    "		) SELECT\n" +
                    "			'"+he.RunId+"' AS RunId,\n" +
                    "			v.vehicle_code,\n" +
                    "			CASE\n" +
                    "				WHEN vh.vehicle_code IS NULL THEN va.weight\n" +
                    "				ELSE vh.weight\n" +
                    "			END AS weight,\n" +
                    "			CAST(\n" +
                    "				CASE\n" +
                    "					WHEN vh.vehicle_code IS NULL THEN va.volume\n" +
                    "					ELSE vh.volume\n" +
                    "				END AS NUMERIC(\n" +
                    "					18,\n" +
                    "					3\n" +
                    "				)\n" +
                    "			)* 1000000 AS volume," +
                    "			CASE\n" +
                    "				WHEN vh.vehicle_code IS NULL THEN va.vehicle_type\n" +
                    "				ELSE vh.vehicle_type\n" +
                    "			END AS vehicle_type,\n" +
                    "			CASE\n" +
                    "				WHEN vh.vehicle_code IS NULL THEN va.branch\n" +
                    "				ELSE vh.plant\n" +
                    "			END AS branch,\n" +
                    "			va.startLon,\n" +
                    "			va.startLat,\n" +
                    "			va.endLon,\n" +
                    "			va.endLat,\n" +
                    "			va.startTime,\n" +
                    "			CONVERT(\n" +
                    "				VARCHAR(5),\n" +
                    "				dateadd(\n" +
                    "					HOUR,\n" +
                    "					- 1,\n" +
                    "					CAST(\n" +
                    "						va.endTime AS TIME\n" +
                    "					)\n" +
                    "				)\n" +
                    "			) endTime,\n" +
                    "			va.source1,\n" +
                    "			'"+sdf.format(date)+"' AS UpdatevDate,\n" +
                    "			'"+sdf.format(date)+"' AS CreateDate,\n" +
                    "			'1' AS isActive,\n" +
                    "			va.fixedCost,\n" +
                    "			pr.value /(\n" +
                    "				va.costPerM * 1000\n" +
                    "			) AS costPerM,\n" +
                    "			0 AS costPerServiceMin,\n" +
                    "			0 AS costPerTravelMin,\n" +
                    "			va.IdDriver,\n" +
                    "			va.NamaDriver,\n" +
                    "			CASE\n" +
                    "				WHEN va.agent_priority is null THEN rt.value\n" +
                    "				ELSE va.agent_priority\n" +
                    "			END AS agent_priority,\n" +
                    "			CASE\n" +
                    "				WHEN va.max_cust is null THEN ry.value\n" +
                    "				ELSE va.max_cust\n" +
                    "			END AS max_cust\n" +
                    "		FROM\n" +
                    "			(\n" +
                    "				SELECT\n" +
                    "					DISTINCT vehicle_code\n" +
                    "				FROM\n" +
                    "					(\n" +
                    "						SELECT\n" +
                    "							vehicle_code\n" +
                    "						FROM\n" +
                    "							bosnet1.dbo.vehicle\n" +
                    "						WHERE\n" +
                    "							plant = '"+he.branch+"'\n" +
                    "					UNION SELECT\n" +
                    "							vehicle_code\n" +
                    "						FROM\n" +
                    "							bosnet1.dbo.TMS_VehicleAtr\n" +
                    "						WHERE\n" +
                    "							branch = '"+he.branch+"'\n" +
                    //"							AND included = 1\n" +
                    "					) vi\n" +
                    "			) v\n" +
                    "		LEFT OUTER JOIN bosnet1.dbo.vehicle vh ON\n" +
                    "			v.vehicle_code = vh.vehicle_code\n" +
                    "		LEFT OUTER JOIN bosnet1.dbo.TMS_vehicleAtr va ON\n" +
                    "			v.vehicle_code = va.vehicle_code\n" +
                    "		LEFT OUTER JOIN bosnet1.dbo.TMS_Params pr ON\n" +
                    "			pr.param = 'HargaSolar'\n" +
                    "		LEFT OUTER JOIN bosnet1.dbo.TMS_Params bm ON\n" +
                    "			bm.param = 'DefaultKonsumsiBBm'\n" +
                    "		LEFT OUTER JOIN bosnet1.dbo.TMS_Params rt ON\n" +
                    "			rt.param = 'Defaultagentpriority'\n" +
                    "		LEFT OUTER JOIN bosnet1.dbo.TMS_Params ry ON\n" +
                    "			ry.param = 'DefaultMaxCust'\n" +
                    "		WHERE\n" +
                    "			va.vehicle_code = '"+he.vehicle_code+"'\n";
            }
            
            
            
            try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)) {
            
                //tr = rj.DeleteResultShipment(he);              

                con.setAutoCommit(false);
                ps.executeUpdate();
                con.setAutoCommit(true);

                tr = "OK";

            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }      
        
        String jsonOutput = gson.toJson(tr);
        return jsonOutput;        
    }
    
    public String isExits(Vehicle he) throws Exception{
        String str = "ERROR";
        String sql = "select count(*) as tr from BOSNET1.dbo.TMS_PreRouteVehicle "
                + "where RunId = '"+he.RunId+"' and vehicle_code = '"+he.vehicle_code+"'";
        
        try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)){
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    Double tr = FZUtil.getRsDouble(rs, 1, 0);
                    str  = tr > 0 ? "OK" : "ERROR";
                }
            }
        }
        return str;
    }
    
    @POST
    @Path("AddVehicleEx")
    @Produces(MediaType.APPLICATION_JSON)
    public String AddVehicleEx(String content) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String tr = "ERROR";
        
        String sql = "";
        
        try{
            Vehicle he = gson.fromJson(content.contains("json") ? 
                    decodeContent(content) : content, Vehicle.class);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String str = he.vehicle_code;
            str = str.substring(0, (str.length() - 1));
            
            sql = "BEGIN DECLARE @VCode VARCHAR(50);\n" +
                "\n" +
                "SELECT\n" +
                "	@VCode =(\n" +
                "		concat(\n" +
                "			(\n" +
                "				SELECT\n" +
                "					SUBSTRING( vehicle_code, 1,( SELECT LEN( vehicle_code )- 1 ))\n" +
                "			),\n" +
                "			(\n" +
                "				SELECT\n" +
                "					(\n" +
                "						CAST(\n" +
                "							RIGHT(\n" +
                "								MAX( vehicle_code ),\n" +
                "								1\n" +
                "							) AS INT\n" +
                "						)+ 1\n" +
                "					)\n" +
                "				FROM\n" +
                "					[BOSNET1].[dbo].[TMS_VehicleAtr]\n" +
                "				WHERE\n" +
                "					vehicle_code LIKE('"+str+"%')\n" +
                "			)\n" +
                "		)\n" +
                "	)\n" +
                "FROM\n" +
                "	BOSNET1.dbo.TMS_PreRouteVehicle\n" +
                "WHERE\n" +
                "	RunId = '"+he.RunId+"'\n" +
                "	AND vehicle_code = '"+he.vehicle_code+"';\n" +
                "\n" +
                "INSERT\n" +
                "	INTO\n" +
                "		bosnet1.dbo.TMS_PreRouteVehicle SELECT\n" +
                "			RunId,\n" +
                "			@VCode AS vehicle_code,\n" +
                "			weight,\n" +
                "			volume,\n" +
                "			vehicle_type,\n" +
                "			branch,\n" +
                "			startLon,\n" +
                "			startLat,\n" +
                "			endLon,\n" +
                "			endLat,\n" +
                "			startTime,\n" +
                "			endTime,\n" +
                "			source1,\n" +
                "			FORMAT(\n" +
                "				GETDATE(),\n" +
                "				'yyyy-mm-dd'\n" +
                "			) AS startTime,\n" +
                "			FORMAT(\n" +
                "				GETDATE(),\n" +
                "				'yyyy-mm-dd'\n" +
                "			) AS endTime,\n" +
                "			isActive,\n" +
                "			fixedCost,\n" +
                "			costPerM,\n" +
                "			costPerServiceMin,\n" +
                "			costPerTravelMin,\n" +
                "			IdDriver,\n" +
                "			NamaDriver,\n" +
                "			agent_priority,\n" +
                "			max_cust\n" +
                "		FROM\n" +
                "			BOSNET1.dbo.TMS_PreRouteVehicle\n" +
                "		WHERE\n" +
                "			RunId = '"+he.RunId+"'\n" +
                "			AND vehicle_code = '"+he.vehicle_code+"';\n" +
                "\n" +
                "INSERT\n" +
                "	INTO\n" +
                "		bosnet1.dbo.TMS_VehicleAtr SELECT\n" +
                "			@VCode AS vehicle_code,\n" +
                "			branch,\n" +
                "			startLon,\n" +
                "			startLat,\n" +
                "			endLon,\n" +
                "			endLat,\n" +
                "			startTime,\n" +
                "			endTime,\n" +
                "			source1,\n" +
                "			vehicle_type,\n" +
                "			weight,\n" +
                "			volume,\n" +
                "			included,\n" +
                "			costPerM,\n" +
                "			fixedCost,\n" +
                "			Channel,\n" +
                "			IdDriver,\n" +
                "			NamaDriver,\n" +
                "			DriverDates,\n" +
                "			agent_priority,\n" +
                "			max_cust\n" +
                "		FROM\n" +
                "			BOSNET1.dbo.TMS_VehicleAtr\n" +
                "		WHERE\n" +
                "			vehicle_code = '"+he.vehicle_code+"'\n" +
                "		END";
            
            try (Connection con = (new Db()).getConnection("jdbc/fztms");
                PreparedStatement ps = con.prepareStatement(sql)) {
            
                //tr = rj.DeleteResultShipment(he);              

                con.setAutoCommit(false);
                ps.executeUpdate();
                con.setAutoCommit(true);

                tr = "OK";

            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }      
        
        String jsonOutput = gson.toJson(tr);
        return jsonOutput;        
    }
    
    public static String decodeContent(String content) throws UnsupportedEncodingException{
        content = java.net.URLDecoder.decode(content, "UTF-8");
        content = content.substring(5);
        
        return content;
    }
}

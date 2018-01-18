/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.api;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 */
//api/v1
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.fz.ffbv3.api.DivisionApi.class);
        resources.add(com.fz.ffbv3.api.EntryApi.class);
        resources.add(com.fz.ffbv3.api.GMapsApi.class);
        resources.add(com.fz.ffbv3.api.ReasonApi.class);
        resources.add(com.fz.ffbv3.api.TMS.CustomerAttrViewAPI.class);
        resources.add(com.fz.ffbv3.api.TMS.PopupEditCustAPI.class);
        resources.add(com.fz.ffbv3.api.TMS.VehicleAttrViewAPI.class);
        resources.add(com.fz.ffbv3.api.TaskApi.class);
        resources.add(com.fz.ffbv3.api.TrackApi.class);
        resources.add(com.fz.ffbv3.api.UsersApi.class);
        resources.add(com.fz.ffbv3.api.dashProdAPI.dashProdAPI01.class);
        resources.add(com.fz.ffbv3.api.division.APIMill.class);
        resources.add(com.fz.ffbv3.api.exampleAPI.APISample.class);
        resources.add(com.fz.ffbv3.api.hvsEstmAPI.HvsEstmSaveAPI.class);
        resources.add(com.fz.ffbv3.api.params.PramsAPI.class);
        resources.add(com.fz.ffbv3.api.progresstack.ProgressTrack.class);
    }
    
}

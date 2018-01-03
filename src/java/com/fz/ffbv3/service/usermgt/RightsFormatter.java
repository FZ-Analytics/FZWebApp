/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fz.ffbv3.service.usermgt;

/**
 *
 */
public class RightsFormatter {
    
    public static String format(String rights){
        
        String cleanedRights = rights 
                .replaceAll("\n", "")
                .replaceAll("\r", "")
                .replaceAll("\t", "")
                .replaceAll(" ", "")
                .toLowerCase()
                ;
        
        String rgs[] = cleanedRights.split(";");
        String formattedRights = ";";
        for (String rg : rgs){
            formattedRights += rg + ";"; 
        }
        
        return formattedRights;
    }
}

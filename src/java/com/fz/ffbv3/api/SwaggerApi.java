/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.ffbv3.api;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import com.wordnik.swagger.config.ConfigFactory;
import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.config.SwaggerConfig;
import com.wordnik.swagger.jaxrs.config.DefaultJaxrsScanner;
import com.wordnik.swagger.jaxrs.reader.DefaultJaxrsApiReader;
import com.wordnik.swagger.reader.ClassReaders;
import javax.servlet.annotation.WebServlet;


/**
 *
 * @author
 * Agustinus Ignat
 */
@WebServlet(name = "SwaggerApi", loadOnStartup = 1)
public class SwaggerApi extends HttpServlet
{
  @Override
  public void init(ServletConfig servletConfig)
	{
    try
		{
      super.init(servletConfig);
      SwaggerConfig swaggerConfig = new SwaggerConfig();
      ConfigFactory.setConfig(swaggerConfig);
      swaggerConfig.setBasePath("http://localhost:8084/fz/api/v1");
      swaggerConfig.setApiVersion("1.0.0");
      ScannerFactory.setScanner(new DefaultJaxrsScanner());
      ClassReaders.setReader(new DefaultJaxrsApiReader());
    }
		catch (ServletException e)
		{
      System.out.println(e.getMessage());
    }
  }
}

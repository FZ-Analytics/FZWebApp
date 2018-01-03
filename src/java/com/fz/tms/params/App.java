/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fz.tms.params;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
/**
 *
 * @author Administrator
 */
public class App 
{
    public static void main( String[] args ) throws DocumentException, IOException
    {
      // step 1
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

        System.out.println( "PDF Created!" );
    }
}
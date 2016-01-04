package com.mycompany.mavenproject2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
 
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
 
/**
 * FXML Controller class
 *
 * @author Third Ev
 */
public class ItemHistoryController implements Initializable {
    public FinalDemo f4=new FinalDemo();
        @FXML
        public void OnPrintButtonAction(ActionEvent a){
             Document document = new Document();
          //JavaPdfHelloWorld jp=new JavaPdfHelloWorld();
             
             try{
          PdfWriter.getInstance(document,
                new FileOutputStream("javaHello.pdf"));

            document.open();
            document.add(new Paragraph("A Hello World PDF document."));
            
            document.close();
           
        final File file = new File("C:/Users/Third Ev/Documents/NetBeansProjects/FinalDemo/src/finaldemo/Controller/javaHello.pdf");  
       
        f4.getHostServices().showDocument(file.toURI().toString());  

      // no need to close PDFwriter?
             }
             catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

          


        }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

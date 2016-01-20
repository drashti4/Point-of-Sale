/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.sun.jndi.toolkit.url.Uri;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.bson.Document;

/**
 * FXML Controller class
 *
 * @author Third Ev
 */
public class TenderAddController implements Initializable {
@FXML ImageView imageLabel;
@FXML Button save;
@FXML CheckBox AuthenticationCheck;
@FXML ComboBox PaymentCombo,CardCombo;
@FXML TextField TenderName,ERRate,ConvRate,CurSymbol,MinPayAmount,NoteText;    
static String path;
int flag=0,count;
MongoClient client = new MongoClient();
MongoDatabase  db=client.getDatabase("FinalDemo");
MongoCollection<Document> col=db.getCollection("TenderDetail");
int GlobalFlag=0;
static int EID=1,UID=1,PID=0;
BasicDBObject sort1,sort2,condition;

public void EditButton(String Desc,String Type) throws URISyntaxException{
        GlobalFlag=1;        
        TenderName.setText(Desc);       
        sort2=new BasicDBObject();
        sort2.put("TenderName", Desc);
	MongoCursor<Document> cursor = col.find(sort2).iterator();      
        while (cursor.hasNext()) {       
            Document dr=cursor.next();            
            PID=dr.getInteger("ID");
            PaymentCombo.setValue(dr.getString("PaymentMode"));
            ERRate.setText(dr.getString("ExchangeRate"));
            ConvRate.setText(dr.getString("ConversionRate"));
            CurSymbol.setText(dr.getString("Currency"));
            MinPayAmount.setText(dr.getString("MinPayAmount"));
            NoteText.setText(dr.getString("Notes"));       
            if (!dr.getString("Image").equalsIgnoreCase("")) {               
                URI uri = new URI(dr.getString("Image"));         
                imageLabel.setImage(new Image(uri.toString()));
            }
            if(dr.getInteger("AuthenticationCheck")==1){
                AuthenticationCheck.setSelected(true);                    
            }
            else{
                AuthenticationCheck.setSelected(false);
               }              
        }           
     }       
        
     @FXML public void handleAddImageButtonAction(ActionEvent a){
        System.out.println("dialoge box open");
        /*FileChooser fileChooser = new FileChooser();
        Stage stage=new Stage();
        fileChooser.setTitle("Selct file Sale Label");        
        fileChooser.getExtensionFilters().addAll( 
           new FileChooser.ExtensionFilter("jpg", "*.jpg"),
            new FileChooser.ExtensionFilter("png", "*.png"));           
            File file1 = fileChooser.showOpenDialog(stage);
            System.out.println(file1);
            if (file1 != null) {               
                Image image = new Image(file1.toURI().toString());                
                imageLabel.setImage(image);           
          
            }
            else{
            }*/
        JFileChooser chooser=new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("jpeg", "jpg", "png");                
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Select Image");
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
            File file1=chooser.getSelectedFile();
            path=chooser.getSelectedFile().getAbsolutePath();
            path.replaceAll("\\", "//");
            System.out.println("patyh is "+path);
            if (file1 != null) {               
                Image image = new Image(file1.toURI().toString());                
                imageLabel.setImage(image);  
                
            }
        } else {
            System.out.println("No Selection ");
    }
     }
    @FXML public void handleRemoveImageButtonAction(ActionEvent ae){
       imageLabel.setImage(null);
       path="";
    }
    @FXML public void handleCloseButtonAction(ActionEvent ad){
        Stage stage=(Stage) save.getScene().getWindow();
        stage.close();
    }
    @FXML public void handleSaveCloseButtonAction(ActionEvent ad){
        InsertMongo();
        Stage stage=(Stage) save.getScene().getWindow();
        stage.close();  
    }
    @FXML
    public void handleSaveNewButtonAction(ActionEvent ae){
        InsertMongo();
        TenderName.clear();
        ERRate.clear();
        ConvRate.clear();
        CurSymbol.clear();
        MinPayAmount.clear();
        NoteText.clear();
    }
    public void InsertMongo(){         
        if(GlobalFlag==1){
                 UpdateMongo();                     
            }
         else{
        count= (int) col.count();
        if(count==0){
            Document input=createSaleSeedData();
            col.insertOne(input);   
        }
        else{        
        sort1=new BasicDBObject();        
	MongoCursor<Document> cursor = col.find().sort(sort1).limit(1).skip((int)count-1).iterator();        
        try {
            while (cursor.hasNext()) {                
                EID=cursor.next().getInteger("ID");
                System.out.println("last EID "+EID);
                EID++;
                System.out.println("Inserted "+EID);
            }
        } finally {
            cursor.close();
        }
        final Document seedData = createSaleSeedData();
        col.insertOne(seedData);
        }
    }
}
    public void UpdateMongo(){   
        MongoCursor<Document> cursor4 = col.find().iterator();
        try {
            while (cursor4.hasNext()) {
                System.out.println("ID=> "+cursor4.next().getInteger("ID"));                
            }
        } finally {
            cursor4.close();
        }            
        System.out.println("Updated Selected _id is "+PID);
        col.updateOne(new Document("ID", PID),
         new Document("$set", UpdateSeedData()));
            //.append("$currentDate", new Document("lastModified", true)));
        MongoCursor<Document> cursor5 = col.find().iterator();
         try {
            while (cursor5.hasNext()) {
                System.out.println("After UpdateID=> "+cursor5.next().getInteger("ID"));                
            }
        } finally {
            cursor5.close();
        }    
    }
     private Document UpdateSeedData(){
        if(AuthenticationCheck.isSelected()){
            flag=1;
        }        
       Document d=new Document();
       d.append("ID",PID);
       d.append("TenderName", TenderName.getText());       
       d.append("PaymentMode", PaymentCombo.getValue());
       d.append("CardType",CardCombo.getValue());
       d.append("ExchangeRate", ERRate.getText());
       d.append("ConversionRate", ConvRate.getText());
       d.append("Currency", CurSymbol.getText());
       d.append("MinPayAmount", MinPayAmount.getText());
       d.append("Notes", NoteText.getText());
       d.append("AuthenticationCheck", flag);        
       d.append("Image", path);
        return d;
    }
 public Document createSaleSeedData(){
        if(AuthenticationCheck.isSelected()){
            flag=1;
        }        
       Document d=new Document();
       d.append("ID",EID);
       d.append("TenderName", TenderName.getText());       
       d.append("PaymentMode", PaymentCombo.getValue());
       d.append("CardType",CardCombo.getValue());
       d.append("ExchangeRate", ERRate.getText());
       d.append("ConversionRate", ConvRate.getText());
       d.append("Currency", CurSymbol.getText());
       d.append("MinPayAmount", MinPayAmount.getText());
       d.append("Notes", NoteText.getText());
       d.append("AuthenticationCheck", flag);
       d.append("Image", path);
        return d;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

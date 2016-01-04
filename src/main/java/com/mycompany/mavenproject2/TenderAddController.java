/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import java.io.File;
import java.net.URL;
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
int flag=0;
MongoClient client = new MongoClient();
 MongoDatabase  db=client.getDatabase("FinalDemo");
 @FXML ComboBox PaymentCombo,CardCombo;
   @FXML TextField TenderName,ERRate,ConvRate,CurSymbol,MinPayAmount,NoteText;    
     @FXML
    public void handleAddImageButtonAction(ActionEvent a){
        System.out.println("dialoge box open");
        FileChooser fileChooser = new FileChooser();
        Stage stage=new Stage();
        fileChooser.setTitle("Selct file Sale Label");
        //fileChooser.showOpenDialog(stage);
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
            }
    }
    @FXML public void handleRemoveImageButtonAction(ActionEvent ae){
        
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
         
            Document input=createSaleSeedData();
            db.getCollection("TenderDetail").insertOne(input);
            
     
    }
 public Document createSaleSeedData(){
        if(AuthenticationCheck.isSelected()){
            flag=1;
        }        
       Document d=new Document();
       d.append("TenderName", TenderName.getText());       
       d.append("PaymentMode", PaymentCombo.getValue());
       d.append("CardType",CardCombo.getValue());
       d.append("ExchangeRate", ERRate.getText());
       d.append("ConversionRate", ConvRate.getText());
       d.append("Currency", CurSymbol.getText());
       d.append("MinPayAmount", MinPayAmount.getText());
       d.append("Notes", NoteText.getText());
       d.append("AuthenticationCheck", flag);
        return d;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

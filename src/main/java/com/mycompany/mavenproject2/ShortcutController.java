/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.bson.Document;
public class ShortcutController implements Initializable {
    @FXML Button AppendButton;
    @FXML Button InsertButton;
    @FXML RadioButton ItemRadio;
    @FXML RadioButton GroupRadio;
    @FXML Pane ItemPane;
    @FXML ComboBox PackCombo;
    @FXML Pane GroupPane;
    @FXML Button ClearButton;
    @FXML TextField SKUText,ItemName,SizeText,ButtonCaptionText,QuantityText;
    @FXML Button DeptKeyButton; MongoClient client = new MongoClient();
    MongoDatabase  db = client.getDatabase("FinalDemo");   
    @FXML
    private void handleAppendButtonAction(ActionEvent ae){
        System.out.println("You clicked Append button!");
       try {
           AppendButton.setText("");
           AppendButton.setText("Save");
           ClearButton.setDisable(false);
           ItemPane.setDisable(false);
           InsertButton.setDisable(true);
           MongoInsert();
        } catch(Exception e) {
           e.printStackTrace();
          }  
    }
    public void MongoInsert(){
        Document insert=createSeedData();
        db.getCollection("ButtonDetail").insertOne(insert);
    }
    public Document createSeedData(){
        Document d=new Document();
        d.append("Caption", ButtonCaptionText.getText());
        d.append("Quanity",QuantityText.getText());
        if(ItemRadio.isSelected()){
            d.append("ItemShortcut", new Document().append("SKU", SKUText.getText()).append("ItemName", ItemName.getText())
            .append("Size", SizeText.getText()).append("Pack", PackCombo.getValue()));
            
             d.append("GroupShortcut", "No");
        }
       
        return d;
    }
    @FXML
    private void handleItemRadioAction(ActionEvent ae){
    if(ItemRadio.isSelected()){
               System.out.println("Item Radio Selected");
               ItemPane.setDisable(false);
               GroupPane.setDisable(true);
           }
    }
    @FXML
    private void handleGroupRadioAction(ActionEvent ae){
        if(GroupRadio.isSelected()){
               System.out.println("Group Radio Selected");
               GroupPane.setDisable(false);
               ItemPane.setDisable(true);
           }
    }
    
    @FXML
    private void handleDeptKeyButtonAction(ActionEvent ae){
        System.out.println("You clicked Department key step 1!");
       try {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/DeptKey1.fxml"));
                Parent root3 = (Parent) fxmlLoader.load();                
                stage.setScene(new Scene(root3));  
                stage.setTitle("Create Department Key Step 1");
                stage.show();
        } catch(Exception e) {
           e.printStackTrace();
          }  
    }
    
    @FXML
    private void handleButtonPropertyActtion(ActionEvent ae){
        System.out.println("You clicked Button Property setting!");
       try {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ButtonPropertySetting.fxml"));
                Parent root3 = (Parent) fxmlLoader.load();                
                stage.setScene(new Scene(root3));  
                stage.setTitle("Button Property Setting");
                stage.show();
        } catch(Exception e) {
           e.printStackTrace();
          }  
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         MongoCursor<Document> cursor4 =  db.getCollection("PackDetail").find().iterator();
            try {
            while (cursor4.hasNext()) {                
                String rs=cursor4.next().getString("Name");
                PackCombo.setValue(rs);
                PackCombo.getItems().addAll(rs);                
            }
        } finally {
            cursor4.close();
        }   
    }    
    
}

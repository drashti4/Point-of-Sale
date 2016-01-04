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
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bson.Document;

/**
 * FXML Controller class
 *
 * @author Third Ev
 */
public class AddLocationController implements Initializable {
    @FXML
    Button Save;
    @FXML
    TextField AreaName;
    MongoClient client = new MongoClient();
    MongoDatabase  db = client.getDatabase("FinalDemo");
    MongoCollection<Document> col = db.getCollection("LocationDetail");
    BasicDBObject ref;
    int repeat=0;
    @FXML
    public void handleSaveNewButton(ActionEvent a){        
          /*  java.sql.Connection conn=Connection.getConnect();
            String Add1="INSERT INTO location_detail(`name`)VALUES ?)";          
            PreparedStatement inQuery=conn.prepareStatement(Add1);
            inQuery.setString(1, AreaName.getText().toString());           
            inQuery.executeUpdate();
            AreaName.clear();           */
        ref=new BasicDBObject();
        ref.put("Name", Pattern.compile(AreaName.getText() , Pattern.CASE_INSENSITIVE));
        MongoCursor<Document> myDoc2 =  col.find(ref).iterator();
        if(myDoc2.hasNext()){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error ");
           alert.setContentText("Area already exist!");
           alert.showAndWait();
           AreaName.clear();           
           repeat=1;
       }
       else{
           repeat=0;
       }
       if(repeat==0){
                InsertMongo();
               // InsertSQl(conn);
       }           
    }
    @FXML
    public void handleSaveCloseButtonAction(ActionEvent a){
        ref=new BasicDBObject();
        ref.put("Name", Pattern.compile(AreaName.getText() , Pattern.CASE_INSENSITIVE));
        MongoCursor<Document> myDoc2 =  col.find(ref).iterator();
        if(myDoc2.hasNext()){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error ");
           alert.setContentText("Area already exist!");
           alert.showAndWait();
           AreaName.clear();           
           repeat=1;
       }
       else{
           repeat=0;
       }
       if(repeat==0){
                InsertMongo();
               // InsertSQl(conn);
       }            
           
       Stage stage=(Stage) Save.getScene().getWindow();
       stage.close();        
    }
    public  Document createSeedData(){           
        Document d=new Document();
        d.append("Name", AreaName.getText());  
        
        return d;
    }  
    public void InsertMongo(){        
        final Document seedData = createSeedData();
        col.insertOne(seedData);        
        
        AreaName.clear();        
   }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

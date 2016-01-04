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
public class AddPackController implements Initializable {
    @FXML 
    TextField name,LocalName,unit;
    @FXML
    Button Save;
    MongoClient client = new MongoClient();
    MongoDatabase  db = client.getDatabase("FinalDemo");
    MongoCollection<Document> col = db.getCollection("PackDetail");
    BasicDBObject ref,sort1;
    static int ID=1;
    int repeat=0;
    Document seedData;
    int count;
    @FXML
    public void handleSaveNewButtonAction(ActionEvent a){
      
         /*java.sql.Connection conn=Connection.getConnect();
          String Add1="INSERT INTO pack_detail "
                  +"(`name`,`local_name`,`unit`) "
                  + "VALUES (?,?,?)";
          PreparedStatement inQuery=conn.prepareStatement(Add1);
          inQuery.setString(1, name.getText().toString());
          inQuery.setString(2, LocalName.getText().toString());  
          inQuery.setInt(3, Integer.parseInt(unit.getText().toString()));  
         
          inQuery.executeUpdate();
          name.clear();
          LocalName.clear();
          unit.clear();*/
        ref=new BasicDBObject();
        ref.put("Name", Pattern.compile(name.getText() , Pattern.CASE_INSENSITIVE));
        MongoCursor<Document> myDoc2 =  col.find(ref).iterator();
        if(myDoc2.hasNext()){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error ");
           alert.setContentText("Area already exist!");
           alert.showAndWait();
           name.clear();
           LocalName.clear();
           unit.clear();
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
        ref.put("Name", Pattern.compile(name.getText() , Pattern.CASE_INSENSITIVE));
        MongoCursor<Document> myDoc2 =  col.find(ref).iterator();
        if(myDoc2.hasNext()){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error ");
           alert.setContentText("Area already exist!");
           alert.showAndWait();
           name.clear();
           LocalName.clear();
           unit.clear();
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
        d.append("Name", name.getText());       
        d.append("LocalName",LocalName.getText());
        d.append("Unit", Integer.parseInt(unit.getText()));
        d.append("ID", ID);
        return d;
    }  
    public void InsertMongo(){        
         count= (int) col.count();
        if(count==0){
            seedData = createSeedData();
            col.insertOne(seedData);
        }
        else{        
        sort1=new BasicDBObject();        
	MongoCursor<Document> cursor = col.find().sort(sort1).limit(1).skip((int)count-1).iterator();        
        try {
            while (cursor.hasNext()) {
                
                ID=cursor.next().getInteger("ID");
                System.out.println("last ID "+ID);
                ID++;
                System.out.println("Inserted "+ID);
            }
        } finally {
            cursor.close();
        }
        
        final Document seedData = createSeedData();
        col.insertOne(seedData);
        }
        name.clear();
        LocalName.clear();
        unit.clear();
   }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

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
import static com.mycompany.mavenproject2.AddDeptController.ID;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class AddUOMController implements Initializable {
  @FXML
  TextField name,local_name;
  @FXML
  Button Save;
  MongoClient client = new MongoClient();
    MongoDatabase  db = client.getDatabase("FinalDemo");
    BasicDBObject ref;
    ArrayList aryList;
    BasicDBObject b;
    int repeat=0, count;
    Document d;
    Document seedData;
    
    static int ID=1;
    BasicDBObject sort1;
    MongoCollection<Document> col = db.getCollection("UOMDetail");
    @FXML
    public void handleSaveNewButtonAction(ActionEvent a){       
           ref=new BasicDBObject();
            ref.put("Name", Pattern.compile(name.getText() , Pattern.CASE_INSENSITIVE));            
            MongoCursor<Document> myDoc2 =  col.find(ref).iterator();            
            if(myDoc2.hasNext()){
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error ");
                alert.setContentText("UOM already exist!");
                alert.showAndWait();
                name.clear();
                local_name.clear();
                repeat=1;                
            }
            else{
                repeat=0;
            }
            if(repeat==0){
                InsertMongo();
             
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
                alert.setContentText("UOM already exist!");
                alert.showAndWait();
                name.clear();
                local_name.clear();
                repeat=1;                
            }
            else{
                repeat=0;
            }
            if(repeat==0){
                InsertMongo();             
            }       
            Stage stage=(Stage) Save.getScene().getWindow();
            stage.close();    
            
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
        name.clear();
                local_name.clear();
        col.insertOne(seedData);
    }
 }
public  Document createSeedData(){        
        Document d=new Document();
        d.append("Name", name.getText());
        d.append("LocalName", local_name.getText());
        
        d.append("ID",  ID);         
        System.out.println("Id is "+ID);
    
        return d;
    }     
  private void  insertSql() throws ClassNotFoundException{        
        try {
          java.sql.Connection conn=Connection.getConnect();
          String Add1="INSERT INTO uom_detail "
                  +"(`name`,`local_name`) "
                  + "VALUES (?,?)";
          PreparedStatement inQuery=conn.prepareStatement(Add1);
          inQuery.setString(1, name.getText().toString());
          inQuery.setString(2, local_name.getText().toString());          
          inQuery.executeUpdate();
          name.clear();
          local_name.clear();
      } catch (SQLException ex) {
          Logger.getLogger(AddUOMController.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

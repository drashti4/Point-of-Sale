/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import java.net.URL;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
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
public class AddBrandController implements Initializable {
    @FXML
    TextField ManName,BrandName;
    @FXML Button Save;
    static int ID=1;
    int repeat=0;
    java.sql.Connection conn;
    MongoClient client = new MongoClient();
    MongoDatabase  db=client.getDatabase("FinalDemo");
    MongoCollection<Document> col=db.getCollection("BrandDetail");
    BasicDBObject ref;
    Document seedData;
    int count;
    BasicDBObject sort1;
    @FXML
   
    public void handleSaveCloseButton(ActionEvent a) throws UnknownHostException{
        ref=new BasicDBObject();
        ref.put("Name", Pattern.compile(BrandName.getText() , Pattern.CASE_INSENSITIVE));
        MongoCursor<Document> myDoc2 =  col.find(ref).iterator();
        if(myDoc2.hasNext()){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error ");
           alert.setContentText("Brand already exist!");
           alert.showAndWait();
           ManName.clear();
           BrandName.clear();           
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
    @FXML
    public void handleSaveNewButton(ActionEvent a) throws UnknownHostException{
        ref=new BasicDBObject();
        ref.put("Name", Pattern.compile(BrandName.getText() , Pattern.CASE_INSENSITIVE));
        MongoCursor<Document> myDoc2 =  col.find(ref).iterator();
        if(myDoc2.hasNext()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setContentText("Brand already exist!");
            alert.showAndWait();
            ManName.clear();
            BrandName.clear();
            repeat=1;
        }
        else{
            repeat=0;
        }
        if(repeat==0){
                InsertMongo();
                // InsertSQl();
        }
        
    }
    
     public void InsertSQL() throws ClassNotFoundException{
        try
        {
           java.sql.Connection conn=Connection.getConnect();
            String Add1="INSERT INTO brand_detail " 
                    +"(`name`,`man_name`) "
                    + "VALUES (?,?)";          
            PreparedStatement inQuery=conn.prepareStatement(Add1);
            inQuery.setString(1, BrandName.getText().toString());
            inQuery.setString(2, ManName.getText().toString());
            
            inQuery.executeUpdate();
              BrandName.clear();
            ManName.clear(); 
        } catch (SQLException ex) {
            Logger.getLogger(AddDeptController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public void InsertMongo() throws UnknownHostException{
            //MongoInsert           
                  
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

for (Map.Entry<String, Object> entry : seedData.entrySet())
{
    System.out.println(entry.getKey() + "/" + entry.getValue());
}
            BrandName.clear();
            ManName.clear();        
     }
    public  Document createSeedData(){      
        /*BasicDBObject obj = new BasicDBObject();  
        obj.put("Name", BrandName.getText());
        obj.put("ManuName", ManName.getText());
        obj.put("DeptID", 5);               
        final BasicDBObject[] seedData = {obj};        
        return seedData;*/
        Document d=new Document();
        d.append("Name", BrandName.getText());
        d.append("ManuName", ManName.getText());
        d.append("ID",  ID);         
        System.out.println("Id is "+ID);
        return d;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}

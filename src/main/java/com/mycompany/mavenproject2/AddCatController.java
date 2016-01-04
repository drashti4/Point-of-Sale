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
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.bson.Document;


/**
 * FXML Controller class
 *
 * @author Third Ev
 */
public class AddCatController implements Initializable  {
    @FXML ComboBox DeptCombo;
    @FXML TextField LocalName,CatName;
    @FXML Button Save;
            
    int id=0;
    java.sql.Connection conn;            
    MongoClient client = new MongoClient();
    MongoDatabase  db = client.getDatabase("FinalDemo");
    BasicDBObject ref;
    ArrayList aryList;
    BasicDBObject b;
    int repeat=0,i=0;
    Document d;
    MongoCollection<Document> col = db.getCollection("CategorytDetail");
    MongoCollection<BasicDBObject> col1 = db.getCollection("DeptDetail", BasicDBObject.class);   
    @FXML
    public void handleSaveNewButtonAction(ActionEvent a) throws UnknownHostException{
       ref=new BasicDBObject();
       ref.put("Name", Pattern.compile(CatName.getText() , Pattern.CASE_INSENSITIVE));
       MongoCursor<Document> myDoc2 =  col.find(ref).iterator();
       if(myDoc2.hasNext()){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error ");
           alert.setContentText("Department already exist!");
           alert.showAndWait();
           LocalName.clear();
           CatName.clear();           
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
    public void handleSavenCloseButton(ActionEvent a){
        ref=new BasicDBObject();
       ref.put("Name", Pattern.compile(CatName.getText() , Pattern.CASE_INSENSITIVE));
       MongoCursor<Document> myDoc2 =  col.find(ref).iterator();
       if(myDoc2.hasNext()){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error ");
           alert.setContentText("Category already exist!");
           alert.showAndWait();
           LocalName.clear();
           CatName.clear();           
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
        d=new Document();
        b=new BasicDBObject();
        b.put("Name",DeptCombo.getValue());              	
	MongoCursor<Document> cursor = db.getCollection("DeptDetail").find(b).iterator();
        try {
            while (cursor.hasNext()) {
                i=cursor.next().getInteger("ID");
                System.out.println("Searched Output "+i);
            }
        } finally {
            cursor.close();
        }
     
        d.append("Name", CatName.getText());
        d.append("LocalName", LocalName.getText());
        d.append("Dept_ID", i);
        return d;
    }  
   public void InsertMongo(){
        client = new MongoClient();       
        db = client.getDatabase("FinalDemo");        
        MongoCollection  col = db.getCollection("CategoryDetail");
        final Document seedData = createSeedData();
        col.insertOne(seedData);        
        
        LocalName.clear();
        CatName.clear();
   }
   public void insertSQL() throws ClassNotFoundException{
        try {
            String qry="select id from dept_detail where name='"+DeptCombo.getValue()+"'";
            conn=Connection.getConnect();
            PreparedStatement pst2=conn.prepareStatement(qry);
            ResultSet rs = pst2.executeQuery(qry);
            while(rs.next()){
                System.out.println("Selected id is "+rs.getInt("id"));
                id= rs.getInt("id");
            } 
            String Add1="INSERT INTO category_detail "
                    +"(`name`,`local_name`,dept_id) "
                    + "VALUES (?,?,?)";
            PreparedStatement inQuery=conn.prepareStatement(Add1);
            inQuery.setString(1, CatName.getText().toString());
            inQuery.setString(2, LocalName.getText().toString());
            inQuery.setInt(3, id);
            inQuery.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AddCatController.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
         /*   String qry="select name from dept_detail";
            java.sql.Connection conn=Connection.getConnect();
            
            PreparedStatement pst2=conn.prepareStatement(qry);
            ResultSet rs1 = pst2.executeQuery(qry);
            while(rs1.next()){
               DeptCombo.setValue(rs1.getString("name"));   
               DeptCombo.getItems().addAll(rs1.getString("name"));            
            }       */
            MongoCursor<Document> cursor4 = db.getCollection("DeptDetail").find().iterator();
            try {
            while (cursor4.hasNext()) {
                
                String rs=cursor4.next().getString("Name");
                DeptCombo.setValue(rs);
                DeptCombo.getItems().addAll(rs);
                System.out.println("Intialize out put is "+rs);
            }
        } finally {
            cursor4.close();
        }

         }
        
   }   



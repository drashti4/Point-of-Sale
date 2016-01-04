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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bson.Document;

public class AddSizeController implements Initializable {
    @FXML ComboBox UOMCombo;
    @FXML Button Save;
    @FXML TextField  SizeName,LocalName,UnitText;
    MongoClient client = new MongoClient();
    MongoDatabase  db = client.getDatabase("FinalDemo");
    static int ID=1;
    Document seedData,d;
    BasicDBObject ref, b;
    
    BasicDBObject sort1,sort2;
    int id=0,repeat=0,count=0,i=0;
    MongoCollection<Document> col = db.getCollection("SizeDetail");
    MongoCollection<Document> ucol = db.getCollection("UOMDetail");
    @FXML
    public void handleUOMButtonAction(ActionEvent a){
        try {
           Stage stg=new Stage();
           FXMLLoader fxml1=new FXMLLoader(getClass().getResource("/fxml/SelectUOM.fxml"));
           Parent root1=(Parent)fxml1.load();
           stg.setScene(new Scene(root1));
           stg.setTitle("Select Unit of Measure");
           stg.show();
       } catch (Exception ex) {
           Logger.getLogger(SelectDeptController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    @FXML
    public void handleSaveNewButtonAction(ActionEvent a){
       ref=new BasicDBObject();
            ref.put("Name", Pattern.compile(SizeName.getText() , Pattern.CASE_INSENSITIVE));            
            MongoCursor<Document> myDoc2 =  db.getCollection("SizeDetail").find(ref).iterator();            
            if(myDoc2.hasNext()){
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error ");
                alert.setContentText("Size Name already exist!");
                alert.showAndWait();
                LocalName.clear();
                SizeName.clear();
                UnitText.clear();
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
            ref.put("Name", Pattern.compile(SizeName.getText() , Pattern.CASE_INSENSITIVE));            
            MongoCursor<Document> myDoc2 =  col.find(ref).iterator();            
            if(myDoc2.hasNext()){
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error ");
                alert.setContentText("Size Name already exist!");
                alert.showAndWait();
                LocalName.clear();
                SizeName.clear();
                UnitText.clear();
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
    public void insertSQL() throws ClassNotFoundException{
       try {
          java.sql.Connection conn=Connection.getConnect();
          String uom=UOMCombo.getValue().toString();
          System.out.println("selcted item is "+uom);             
          String qry="select id from uom_detail where name='"+uom+"'";
          PreparedStatement pst2=conn.prepareStatement(qry);
          ResultSet rs = pst2.executeQuery(qry);
          while(rs.next()){              
              id= rs.getInt("id");
             System.out.println("selected id is"+id);
         }
          String Add1="INSERT INTO size_detail "
                  +"(`name`,`local_name`,`unit`,`uom_id`) "
                  + "VALUES (?,?,?,?)";
          PreparedStatement inQuery=conn.prepareStatement(Add1);
          inQuery.setString(1, SizeName.getText().toString());
          inQuery.setString(2, LocalName.getText().toString());   
          inQuery.setString(3, UnitText.getText().toString());    
          inQuery.setInt(4, id);
          inQuery.executeUpdate();
          LocalName.clear();
          SizeName.clear();
          UnitText.clear();
      } catch (SQLException ex) {
          Logger.getLogger(AddUOMController.class.getName()).log(Level.SEVERE, null, ex);
      } 
    }
    public void InsertMongo(){
        count= (int) col.count();
        if(count==0){
            seedData = createSeedData();
            col.insertOne(seedData);
        }
        else{        
        sort2=new BasicDBObject();        
	MongoCursor<Document> cursor = col.find().sort(sort2).limit(1).skip((int)count-1).iterator();        
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
       
        LocalName.clear();
          SizeName.clear();
          UnitText.clear();
        }
 }
    public  Document createSeedData(){     
        d=new Document();
        b=new BasicDBObject();
        b.put("Name",UOMCombo.getValue());              	
	MongoCursor<Document> cursor = ucol.find(b).iterator();
        try {
            while (cursor.hasNext()) {
                i=cursor.next().getInteger("ID");
                System.out.println("Searched Output "+i);
            }
        } finally {
            cursor.close();
        }
     
        Document d=new Document();
        d.append("Name", SizeName.getText());
        d.append("LocalName", LocalName.getText());
        d.append("Unit", Integer.parseInt(UnitText.getText()));
        d.append("UOM_ID",  i);  
        d.append("ID",  ID);    
        System.out.println("UOM Id is "+i);    
        return d;
    }     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
      /*      String qry="select name from uom_detail";
            
            java.sql.Connection conn=Connection.getConnect();            
            PreparedStatement pst2=conn.prepareStatement(qry);
            
            ResultSet rs = pst2.executeQuery(qry);
            
            while(rs.next()){
              UOMCombo.setValue(rs.getString("name"));
              UOMCombo.getItems().addAll(rs.getString("name"));
            }            */
           MongoCursor<Document> cursor4 = ucol.find().iterator();
            try {
            while (cursor4.hasNext()) {
                
                String rs=cursor4.next().getString("Name");
                UOMCombo.getItems().addAll(rs);
                 UOMCombo.setValue(rs);
            }
        } finally {
            cursor4.close();
        }
        
    }    
    
}

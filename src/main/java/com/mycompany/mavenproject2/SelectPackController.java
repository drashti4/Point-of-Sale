/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.bson.Document;

/**
 * FXML Controller class
 *
 * @author Third Ev
 */
public class SelectPackController implements Initializable {
 MongoClient client = new MongoClient();
    MongoDatabase  db = client.getDatabase("FinalDemo");
    MongoCollection<Document> col = db.getCollection("PackDetail");
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
           /* String qry="select name from pack_detail";
            java.sql.Connection conn=Connection.getConnect();            
            PreparedStatement pst2=conn.prepareStatement(qry);
            ResultSet rs = pst2.executeQuery(qry);
            while(rs.next()){
                
               List.getItems().addAll(rs.getString("name"));
            }*/
           
        MongoCursor<Document> cursor4 = col.find().iterator();
            try {
            while (cursor4.hasNext()) {
                
                String rs=cursor4.next().getString("Name");
                 List.getItems().addAll(rs);
            }
        } finally {
            cursor4.close();
        }
    }
       @FXML ListView List;
    int id;
    Statement stmt;
    @FXML
    public void handleDeleteButtonAction(ActionEvent a){
      String sel=List.getSelectionModel().getSelectedItem().toString();     
        List.getItems().removeAll(sel); 
        col.deleteOne(eq("Name", sel));            
        List.getSelectionModel().clearSelection();
        /*  String pck=List.getSelectionModel().getSelectedItem().toString();
          System.out.println("selcted item is "+pck);             
          String qry="select id from pack_detail where name='"+pck+"'";
          java.sql.Connection conn=Connection.getConnect();
          stmt = conn.createStatement();
          PreparedStatement pst2=conn.prepareStatement(qry);
          ResultSet rs = pst2.executeQuery(qry);
          while(rs.next()){              
              id= rs.getInt("id");
             System.out.println("selected id is"+id);
         }
          String del="delete from pack_detail where id='"+id+"'";          
          List.getItems().removeAll(pck);         
          stmt.executeUpdate(del);           
          List.getSelectionModel().clearSelection();*/
      
    }
    @FXML
   public void handleAddButtonAction(ActionEvent a){
       try {
           Stage stg=new Stage();
           FXMLLoader fxml1=new FXMLLoader(getClass().getResource("/fxml/AddPack.fxml"));
           Parent root1=(Parent)fxml1.load();
           stg.setScene(new Scene(root1));
           stg.setTitle("Add Pack");
           stg.show();
       } catch (Exception ex) {
           Logger.getLogger(SelectDeptController.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
    }    
    


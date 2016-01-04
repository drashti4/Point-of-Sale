package com.mycompany.mavenproject2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.net.URL;
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


public class SelectDeptController implements Initializable {
    @FXML ListView List;
    int id;
    Statement stmt;
    MongoClient client = new MongoClient();
    MongoDatabase  db = client.getDatabase("FinalDemo");
    MongoCollection<Document> col = db.getCollection("DeptDetail");
   @FXML ListView idlist,rolelist;
    @FXML
    public void handleDeleteButtonAction(ActionEvent a){
        String sel=List.getSelectionModel().getSelectedItem().toString();     
        List.getItems().removeAll(sel); 
        col.deleteOne(eq("Name", sel));            
        List.getSelectionModel().clearSelection();
    }
   
    @FXML
   public void handleAddButtonAction(ActionEvent a){
       try {
           Stage stg=new Stage();
           FXMLLoader fxml1=new FXMLLoader(getClass().getResource("/fxml/AddDept.fxml"));
           Parent root1=(Parent)fxml1.load();
           stg.setTitle("Add Department");
           stg.setScene(new Scene(root1));
           stg.show();
       } catch (Exception ex) {
           Logger.getLogger(SelectDeptController.class.getName()).log(Level.SEVERE, null, ex);
       }
    
   }
   public void update(){
        
            /*Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddDept.fxml"));
            Parent root4=(Parent) fxmlLoader.load();
            AddDeptController controller=fxmlLoader.<AddDeptController>getController();
            stage.setScene(new Scene(root4));
            System.out.println("dept name entered is "+ controller.getData());
            List.getItems().add("Hello");*/
            /*String qry="select name from dept_detail";
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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            /*   List<String> values = Arrays.asList("one", "two", "three");
        list.setItems(FXCollections.observableList(values));*/
        
        /*    String qry="select name from dept_detail";
            java.sql.Connection conn=Connection.getConnect();            
            PreparedStatement pst2=conn.prepareStatement(qry);
            ResultSet rs = pst2.executeQuery(qry);
            while(rs.next()){                
               List.getItems().addAll(rs.getString("name"));
            }*/
         
        
       MongoCursor<Document> cursor4 = db.getCollection("DeptDetail").find().iterator();
            try {
            while (cursor4.hasNext()) {                
                String rs=cursor4.next().getString("Name");
                 List.getItems().addAll(rs);
            }
        } finally {
            cursor4.close();
        }

        
    }
    
    
}

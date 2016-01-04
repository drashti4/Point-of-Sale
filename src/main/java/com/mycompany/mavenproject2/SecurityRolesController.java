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
import static com.mongodb.client.model.Filters.eq;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import org.bson.Document;


/**
 * FXML Controller class
 *
 * @author Third Ev
 */
public class SecurityRolesController implements Initializable {
     static int ID=1;
    MongoClient client = new MongoClient();
    MongoDatabase  db=client.getDatabase("FinalDemo");
    static String input;
     BasicDBObject ref;
    Document seedData;
    int count;
    BasicDBObject sort1;
     int repeat=0;
      @FXML ListView rolelist;
      MongoCollection<Document> col=db.getCollection("SecurityRole");
    @FXML   
    private void handleAddAction(ActionEvent event) {
        System.out.println("You clicked add of role security button!");
        try {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Role Data");
               // dialog.setHeaderText("Look, a Text Input Dialog");
                dialog.setContentText("Security Role Name");
                // Traditional way to get the response value.
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()){
                    
                    input=result.get();
                }
// The Java 8 way to get the response value (with lambda expression).
        result.ifPresent(name -> System.out.println("Your name: " + name));
        
         ref=new BasicDBObject();
       ref.put("Name", Pattern.compile(input , Pattern.CASE_INSENSITIVE));
       MongoCursor<Document> myDoc2 =  col.find(ref).iterator();
       if(myDoc2.hasNext()){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error ");
           alert.setContentText("Role already exist!");
           alert.showAndWait();
             
           repeat=1;
       }
       else{
           repeat=0;
       }
       if(repeat==0){
                InsertMongo();
               // InsertSQl(conn);
       }
       
        } catch(Exception e) {
           e.printStackTrace();
          }      
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
            Document input=createSeedData();
            db.getCollection("SecurityRole").insertOne(input);
            
        }       
    }
    public Document createSeedData(){
               
       Document d=new Document();
       d.append("Role", input);
       d.append("ID", ID);
        return d;
    }
     @FXML
    public void handleDeleteButtonAction(ActionEvent a){    
        
            String sel=rolelist.getSelectionModel().getSelectedItem().toString();     
        rolelist.getItems().removeAll(sel); 
        col.deleteOne(eq("Role", sel));            
        rolelist.getSelectionModel().clearSelection();
      
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("intialize");
       MongoCursor<Document> cursor4 = db.getCollection("SecurityRole").find().iterator();
         
            try {
            while (cursor4.hasNext()) {
                
                String rs=cursor4.next().getString("Role");
                
                rolelist.getItems().addAll(rs.toString());
                
             //   System.out.println("output is "+String.valueOf(rs1)));
            }
        } finally {
            cursor4.close();
        }
        
    
    }    
}

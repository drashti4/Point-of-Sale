/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.bson.Document;

/**
 * FXML Controller class
 *
 * @author Third Ev
 */
public class ItemGroup6Controller implements Initializable {
    @FXML TableView<Person> GroupTable;
    @FXML TableColumn<Person,String> groupcol,updatecol;
    @FXML TableColumn<Person,Integer> itemcol;
    public static ObservableList<Person> data= FXCollections.observableArrayList();
    MongoClient client=new MongoClient();
    int count=0;
    MongoDatabase db=client.getDatabase("FinalDemo");
    @FXML
    private void handleSelectButtonAction(ActionEvent eve){      

      /*   try {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ItemList.fxml"));
                Parent root = (Parent) fxmlLoader.load();                
                stage.setScene(new Scene(root));  
                stage.setTitle("ItemList");
                stage.show();
        } catch(Exception e) {
           e.printStackTrace();
          } */
            /*MongoCursor<Document> cursor=db.getCollection("ItemGroup").find().iterator();
            while(cursor.hasNext()){
            Document d=(Document) cursor.next().get("Items");
            count=d.keySet().size());
            /*DBObject report = (BasicDBObject) cursor.next().get("report");
    String reportName = (String) report.get("name");
    System.out.println(reportName);*/
    //    }
              
    }
    @FXML public void handleEditButtonAction(ActionEvent r){
        try {
            Person p=GroupTable.getSelectionModel().getSelectedItem();
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddGroup.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            AddGroupController controller=fxmlLoader.<AddGroupController>getController();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit Item");
            System.out.println("Edit is called");
            controller.EditButton(p.getGroupNames());            
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ItemGroup6Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML public void handleDeleteButtonAction(ActionEvent ah){
        Person person = GroupTable.getSelectionModel().getSelectedItem();
        System.out.println("Deleted "+person.getGroupNames());
        
        db.getCollection("ItemGroup").deleteOne(eq("GroupName",person.getGroupNames()));
        
        GroupTable.getSelectionModel().clearSelection();
    }
    @FXML public void handleAddNewGroupButtonAction(ActionEvent as){
         try {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddGroup.fxml"));
                Parent root = (Parent) fxmlLoader.load();                
                stage.setScene(new Scene(root));  
                stage.setTitle("ItemList");
                stage.show();
        } catch(Exception e) {
           e.printStackTrace();
          } 
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        groupcol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("GroupNames"));
        itemcol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,Integer>("Item"));
        updatecol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("AllowUpdates"));  
        data.removeAll(data);
        GroupTable.getItems().removeAll(data);
        GroupTable.setItems(data);
        MongoCursor<Document> cursorFind = db.getCollection("ItemGroup").find().iterator();
        while(cursorFind.hasNext()){
            Document g=cursorFind.next();         
            Document d=(Document)g.get("Items");
            count=d.keySet().size();
         data.add(new Person(g.getString("GroupName"), count,g.getString("Update")));
        }     
        GroupTable.setItems(data);
        cursorFind.close();
        
    } 
    public static class Person {

        private final SimpleStringProperty GroupNames;
        private final SimpleIntegerProperty Item;
        private final SimpleStringProperty AllowUpdates;
       
         
        
        
        private Person(String SKU, int Desc,String Size) {
            this.GroupNames = new SimpleStringProperty(SKU);
            this.Item = new SimpleIntegerProperty(Desc);         
            this.AllowUpdates = new SimpleStringProperty(Size);
           
        }      
       
        
        public String getGroupNames() {
            return GroupNames.get();
        }

        public void setGroupNames(String uName) {
            GroupNames.set(uName);
        }

        public int getItem() {
            return Item.get();
        }

        public void setItem(int val) {
            Item.set(val);
        }
        public String getAllowUpdates() {
            return AllowUpdates.get();
        }

        public void setAllowUpdates(String uName) {
            AllowUpdates.set(uName);
        }

       
     }
}




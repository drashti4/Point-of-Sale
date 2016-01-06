/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import java.net.URL;
import java.util.ResourceBundle;
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

/**
 * FXML Controller class
 *
 * @author Third Ev
 */
public class ItemGroup6Controller implements Initializable {
    @FXML TableView<Person> GroupTable;
    @FXML TableColumn<Person,String> groupcol,itemcol,updatecol;
    public static ObservableList<Person> data= FXCollections.observableArrayList(new Person("ds","das","asd"));
    @FXML
    private void handleSelectButtonAction(ActionEvent eve){      
    System.out.println("You click Select Item");
         try {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ItemList.fxml"));
                Parent root = (Parent) fxmlLoader.load();                
                stage.setScene(new Scene(root));  
                stage.setTitle("ItemList");
                stage.show();
        } catch(Exception e) {
           e.printStackTrace();
          } 
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
        itemcol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Item"));
        updatecol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("AllowUpdates"));  
        GroupTable.setItems(data);
    } 
    public static class Person {

        private final SimpleStringProperty GroupNames;
        private final SimpleStringProperty Item;
        private final SimpleStringProperty AllowUpdates;
       
         
        
        
        private Person(String SKU, String Desc,String Size) {
            this.GroupNames = new SimpleStringProperty(SKU);
            this.Item = new SimpleStringProperty(Desc);         
            this.AllowUpdates = new SimpleStringProperty(Size);
           
        }      
       
        
        public String getGroupNames() {
            return GroupNames.get();
        }

        public void setGroupNames(String uName) {
            GroupNames.set(uName);
        }

        public String getItem() {
            return Item.get();
        }

        public void setItem(String val) {
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




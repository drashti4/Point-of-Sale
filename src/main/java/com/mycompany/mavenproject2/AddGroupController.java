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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import org.bson.Document;
/**
 * FXML Controller class
 *
 * @author Third Ev
 */
public class AddGroupController implements Initializable {
    @FXML TableColumn<Person,String> skucol,desc,size,pack,price;
    public static ObservableList<Person> data= FXCollections.observableArrayList();
    MongoClient client=new MongoClient();
    MongoDatabase db=client.getDatabase("FinalDemo");
    @FXML TableView<Person> ItemTable;
    
    @FXML public void hanldeShowItemAction(ActionEvent as){
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     skucol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("SKU"));
       desc.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Desc"));
       size.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Size"));  
       pack.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Pack"));      
       price.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Price"));
       MongoCursor<Document> cursorFind = db.getCollection("ItemDetail").find().iterator();
       while(cursorFind.hasNext()){
         Document g=cursorFind.next();
         data.add(new Person(g.getString("SKU"), g.getString("ItemDesc"),g.getString("Size_Name"), g.getString("Pack_Name"), g.getString("UnitCost")));
        }     
         ItemTable.setItems(data);
           
       
    
            
       
    }    
     public static class Person {

        private final SimpleStringProperty SKU;
        private final SimpleStringProperty Desc;
        private final SimpleStringProperty Size;
        private final SimpleStringProperty Pack;
        private final SimpleStringProperty Price;
        
         
        
        
        private Person(String SKU, String Desc,String Size,String Pack,String Price) {
            this.SKU = new SimpleStringProperty(SKU);
            this.Desc = new SimpleStringProperty(Desc);         
            this.Size = new SimpleStringProperty(Size);
            this.Pack = new SimpleStringProperty(Pack);
            this.Price = new SimpleStringProperty(Price);
        }      
       
        
        public String getSKU() {
            return SKU.get();
        }

        public void setSKU(String uName) {
            SKU.set(uName);
        }

        public String getDesc() {
            return Desc.get();
        }

        public void setDesc(String val) {
            Desc.set(val);
        }
        public String getSize() {
            return Size.get();
        }

        public void setSize(String uName) {
            Size.set(uName);
        }

        public String getPack() {
            return Pack.get();
        }

        public void setPack(String val) {
            Pack.set(val);
        }
        public String getPrice() {
            return Price.get();
        }

        public void setPrice(String val) {
            Price.set(val);
        }
     }
}

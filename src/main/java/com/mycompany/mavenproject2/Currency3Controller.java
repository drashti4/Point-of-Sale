/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bson.Document;

/**
 * FXML Controller class
 *
 * @author Third Ev
 */
public class Currency3Controller implements Initializable {
    @FXML TableView<Person> table;
    @FXML TableColumn<Person,String> amtcol;
    @FXML TableColumn<Person,String> captioncol;
    @FXML TextField AmountTextField,CaptionTextField;
     private final ObservableList<Person> data
            = FXCollections.observableArrayList(     
                    
            );
    MongoClient client = new MongoClient();
    MongoDatabase  db = client.getDatabase("FinalDemo");
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        captioncol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Value"));
        amtcol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("UnitName"));
        MongoCursor<Document> cursor4 = db.getCollection("CurrancyDetail").find().iterator();
            try {
                while (cursor4.hasNext()) {                
                    Document rs=cursor4.next();               
                    data.add(new Person((rs.getString("Caption")), (rs.getString("Amount"))));
                }
            } finally {
            cursor4.close();
        }  
       table.setItems(data);
    }    
    @FXML
    public void handleEditResetButtonAction(ActionEvent as){
        AmountTextField.clear();
        CaptionTextField.clear();
        Person person = table.getSelectionModel().getSelectedItem();
        AmountTextField.setText(person.getUnitName());
        CaptionTextField.setText(person.getValue());
    }
    @FXML
    public void handleEditSaveButtonAction(ActionEvent as){
        
        MongoCursor<Document> cursor4 = db.getCollection("CurrancyDetail").find().iterator();
         try {
            while (cursor4.hasNext()) {
                Document k=cursor4.next();
                System.out.println("before update => "+k.getString("Caption")+" "+k.getString("Amount"));                
            }
        } finally {
            cursor4.close();
        }       
     //Person person = table.getSelectionModel().getSelectedItem();
        //table.getSelectionModel().clearSelection();            
        
       //db.getCollection("CurrancyDetail").updateOne(new Document("Caption", person.getUnitName()),
        //new Document("$set", UpdateSeedData()));
      db.getCollection("CurrancyDetail").updateOne(eq("Caption",CaptionTextField.getText()), new Document("$set",new Document("Amount",AmountTextField.getText())) );
       
        MongoCursor<Document> cursor5 = db.getCollection("CurrancyDetail").find().iterator();
         try {
            while (cursor5.hasNext()) {
                Document y=cursor5.next();
                System.out.println("After Update => "+y.getString("Caption")+" " +y.getString("Amount"));
                
            }
        } finally {
            cursor5.close();
        }
       AmountTextField.clear();
       CaptionTextField.clear();
    }
    private Document UpdateSeedData(){
        Document d1=new Document();               
        d1.append("Caption",  CaptionTextField.getText());    
        d1.append("Amount",AmountTextField.getText() );
        return d1;
    }
    @FXML
    public void handleResetButtonAction(ActionEvent af){
        Person person = table.getSelectionModel().getSelectedItem();
        //table.getSelectionModel().clearSelection();                       
        AmountTextField.setText(person.getUnitName());
        CaptionTextField.setText(person.getValue());        
    }
     public static class Person {

        private final SimpleStringProperty UnitName;
        private final SimpleStringProperty Value;
        
        private Person(String val, String uName ) {
            this.UnitName = new SimpleStringProperty(uName);
            this.Value = new SimpleStringProperty(val);
         
        }

        public String getUnitName() {
            return UnitName.get();
        }

        public void setUnitName(String uName) {
            UnitName.set(uName);
        }

        public String getValue() {
            return Value.get();
        }

        public void setValue(String val) {
            Value.set(val);
        }

     }
}

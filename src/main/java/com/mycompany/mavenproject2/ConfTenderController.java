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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bson.Document;

public class ConfTenderController implements Initializable {
    MongoClient client = new MongoClient();
    MongoDatabase  db=client.getDatabase("FinalDemo");
    MongoCollection<Document> col=db.getCollection("TenderDetail");
    @FXML TableView<Person> TenderTable;
    private final ObservableList<Person> data
            = FXCollections.observableArrayList();
    @FXML TableColumn<Person,String> TDesc ;
    @FXML TableColumn<Person,String> TType ;
    @FXML TextField filterField;
    public void handleAddButtonAction(ActionEvent ev)
    {
         System.out.println("You click Add Tender");
         try {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/TenderAdd.fxml"));
                Parent root = (Parent) fxmlLoader.load();                
                stage.setScene(new Scene(root));  
                stage.setTitle("Add Tender");
                stage.show();
        } catch(Exception e) {
           e.printStackTrace();
          } 
    }
    private void initFilter() {       
        filterField.setPromptText("Filter");         
         filterField.textProperty().addListener(new InvalidationListener() {         
             @Override
            public void invalidated(javafx.beans.Observable observable) {
                if(filterField.textProperty().get().isEmpty()) {
                    TenderTable.setItems(data);
                    return;
                }
                ObservableList<Person> tableItems = FXCollections.observableArrayList();
                ObservableList<TableColumn<Person, ?>> cols = TenderTable.getColumns();
                for(int i=0; i<data.size(); i++) {                   
                    for(int j=0; j<cols.size(); j++) {
                        TableColumn col = cols.get(j);
                        String cellValue = col.getCellData(data.get(i)).toString();
                        cellValue = cellValue.toLowerCase();
                        if(cellValue.contains(filterField.textProperty().get().toLowerCase())) {
                            tableItems.add(data.get(i));
                            break;
                        }                        
                    }
                }
                TenderTable.setItems(tableItems);
            }
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         TDesc.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("TDesc"));
      TType.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("TType"));
        MongoCursor<Document> cursor5 = col.find().iterator();
                 try {
                    while (cursor5.hasNext()) {
                   Document rs=cursor5.next();
             System.out.println("Tender Name is "+rs.getString("TenderName"));
              data.add(new Person(rs.getString("TenderName"),rs.getString("PaymentMode")));
                }
        } finally {
            cursor5.close();
        } 
         TenderTable.setItems(data);
         filterField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                    
                //updateFilteredData();
                    initFilter();
            }
        });
    }   
     public static class Person {

        private final SimpleStringProperty first;
        private final SimpleStringProperty second;
       
        private Person(String TDesc1,String TType1) {
            first = new SimpleStringProperty(TDesc1);
            second = new SimpleStringProperty(TType1);
            
          //  System.out.println("person Data "+first +" "+second+" "+third+" "+four+" "+five );
           // System.out.println("data pass in constructor are "+uName +" "+val+" "+type+" "+nc+" "+xtra);
        }

        public String getTDesc() {
            return first.get();
        }

        public void setTDesc(String uName) {
            first.set(uName);
        }

        public String getTType() {
            return second.get();
        }

        public void setTType(String val) {
            second.set(val);
        }

        
    }
    
}

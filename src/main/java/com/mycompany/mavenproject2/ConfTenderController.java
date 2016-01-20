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
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

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
    public static Stage stage = null;
    @FXML public void handleEditButtonAction(ActionEvent ak){
       Person person = TenderTable.getSelectionModel().getSelectedItem();       
         try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/TenderAdd.fxml"));
            Parent root44 = (Parent) fxmlLoader.load();
            TenderAddController controller=fxmlLoader.<TenderAddController>getController();
            stage.setScene(new Scene(root44));
            stage.setTitle("Edit Employee");
            controller.EditButton(person.getTDesc(),person.getTType());
            stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(Sys1Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(ConfTenderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML public void handleDeleteButtonAction(ActionEvent ak){
       org.controlsfx.control.action.Action response =  Dialogs.create()
        .owner(stage)
        .title("Confirm Dialog with Custom Actions")
        .masthead("Look, a Confirm Dialog with Custom Actions")
        .message("Are you ok with this?")
        .actions(Dialog.ACTION_OK, Dialog.ACTION_CANCEL)
        .showConfirm();
        

if (response == Dialog.ACTION_OK) {
        List items =  new ArrayList (TenderTable.getSelectionModel().getSelectedItems());  
        Person person = TenderTable.getSelectionModel().getSelectedItem();         
        col.deleteOne(eq("TenderName",person.getTDesc()));
        data.removeAll(items);
        TenderTable.getSelectionModel().clearSelection();
        }
else{
    System.out.println("You click cancel confirm");
}
    }
    @FXML public void handleAddButtonAction(ActionEvent ev)
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

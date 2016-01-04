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
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bson.Document;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;


/**
 * FXML Controller class
 *
 * @author Third Ev
 */
public class Employees7Controller implements Initializable {
    
    @FXML TableView<Person> empTable;
    @FXML TableColumn<Person,String> id ;
    @FXML TableColumn<Person,String> fname ;
    @FXML TableColumn<Person,String> lname ;
    @FXML TableColumn<Person,String> uid ;
    @FXML TableColumn<Person,String> phone ;
    @FXML ComboBox SearchBy;
    @FXML TextField filterField;
    private final ObservableList<Person> data
            = FXCollections.observableArrayList();     
    public static Stage stage = null;
    MongoClient client = new MongoClient();
    MongoDatabase  db = client.getDatabase("FinalDemo");
    MongoCollection<Document> col = db.getCollection("EmployeeDetail");
    final ObservableList<Person> masterData = FXCollections.observableArrayList();     
    final ObservableList<Person> filteredData = FXCollections.observableArrayList();    
    @FXML
    public void handleAddButtonAction(ActionEvent ev)
    {
         System.out.println("You click Add Employee");
         try {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddUser8.fxml"));
                Parent root = (Parent) fxmlLoader.load();                   
                stage.setScene(new Scene(root));  
                stage.setTitle("Add Employee");
                stage.show();
        } catch(Exception e) {
           e.printStackTrace();
          } 
    }
    public void handleSearchComboAction(ActionEvent af){
        switch(SearchBy.getSelectionModel().getSelectedItem().toString()){
            case "First Name":
                 MongoCursor<Document> cursor4 = col.find().iterator();
                 try {
                     masterData.clear();
                    while (cursor4.hasNext()) {
                    System.out.println("First Name=> "+cursor4.next().getString("FirstName"));                     
                    
                }
                   
        } finally {
            cursor4.close();
        } 
            break;
            case "Last Name":
                MongoCursor<Document> cursor5 = col.find().iterator();
                 try {
                    while (cursor5.hasNext()) {
                    System.out.println("Last Name=> "+cursor5.next().getString("LastName"));                
                }
        } finally {
            cursor5.close();
        } 
            break;
            case "User ID":
                 MongoCursor<Document> cursor6 = col.find().iterator();
                 try {
                    while (cursor6.hasNext()) {
                    System.out.println("User ID=> "+cursor6.next().getString("UserEID"));                
                }
        } finally {
            cursor6.close();
        } 
            break;
            case "Phone":
                MongoCursor<Document> cursor7 = col.find().iterator();
                 try {
                    while (cursor7.hasNext()) {
                    System.out.println("Phone => "+cursor7.next().getString("Phone"));                
                }
        } finally {
            cursor7.close();
        } 
            break;
        }
    }
    @FXML
    public void handleDeleteEmpButtonAction(ActionEvent aw){
       /*   org.controlsfx.control.action.Action response =  Dialogs.create()
        .owner(stage)
        .title("Confirm Dialog with Custom Actions")
        .masthead("Look, a Confirm Dialog with Custom Actions")
        .message("Are you ok with this?")
        .actions(Dialog.ACTION_OK, Dialog.ACTION_CANCEL)
        .showConfirm();
        

if (response == Dialog.ACTION_OK) {*/
         System.out.println("You click okay confirm");
        List items =  new ArrayList (empTable.getSelectionModel().getSelectedItems());  
        Person person = empTable.getSelectionModel().getSelectedItem();
        System.out.println(person.getFName());       
        db.getCollection("EmployeeDetail").deleteOne(eq("UserEID",person.getUserId()));
        data.removeAll(items);
        empTable.getSelectionModel().clearSelection();
/*}
else{
    System.out.println("You click cancel confirm");
}*/
    }
    @FXML
    public void handleEditButtonAction(ActionEvent ae){
          Person person = empTable.getSelectionModel().getSelectedItem();
       // System.out.println("id is "+person.getId());    
        //System.out.println("first "+person.getFName());    
       // System.out.println("last name is "+person.getLName());    
        //System.out.println("phone "+person.getPhone());    
         try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddUser8.fxml"));
            Parent root44 = (Parent) fxmlLoader.load();
            AddUser8Controller controller=fxmlLoader.<AddUser8Controller>getController();
            stage.setScene(new Scene(root44));
            stage.setTitle("Edit Employee");
            controller.EditButton(person.getId(),person.getFName(),person.getLName(),person.getUserId(),person.getPhone());
            stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(Sys1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void initFilter() {       
        filterField.setPromptText("Filter");         
         filterField.textProperty().addListener(new InvalidationListener() {         
             @Override
            public void invalidated(javafx.beans.Observable observable) {
                if(filterField.textProperty().get().isEmpty()) {
                    empTable.setItems(data);
                    return;
                }
                ObservableList<Person> tableItems = FXCollections.observableArrayList();
                ObservableList<TableColumn<Person, ?>> cols = empTable.getColumns();
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
                empTable.setItems(tableItems);
            }
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      id.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("id"));
      fname.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("FName"));
      lname.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("LName"));
      uid.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("UserId"));
      phone.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("phone"));     
      MongoCursor<Document> cursor4 = col.find().iterator();
            try {
            while (cursor4.hasNext()) {                
                Document rs=cursor4.next();
               // System.out.println("Id is "+rs.getInteger("ID")+" fname is "+rs.getString("FirstName")+" last name is "+rs.getString("LastName")+" User ID is "+rs.getString("UserEID")+" Phone is "+rs.getString("Phone"));
              data.add(new Person((rs.getInteger("ID")), (rs.getString("FirstName")), (rs.getString("LastName")), (rs.getString("UserEID")), (rs.getString("Phone"))) );
            }
        } finally {
            cursor4.close();
        }  
        empTable.setItems(data);
        masterData.addAll(data);
        filteredData.addAll(masterData);
        System.out.println("Master data value is "+masterData.get(1).getFName());
      
        
        masterData.addListener(new ListChangeListener<Person>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Person> change) {
                System.out.println("Onchanged event of data");
               // updateFilteredData();
            }
        });
        filterField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                    System.out.println("Onchanged event of filterField");
                //updateFilteredData();
                    initFilter();
            }
        });
    }    



     public static class Person {

        private final SimpleIntegerProperty first;
        private final SimpleStringProperty second;
        private final SimpleStringProperty third;
        private final SimpleStringProperty four;
        private final SimpleStringProperty five;
        private Person(int uName, String val, String type,String nc,String xtra) {
            first = new SimpleIntegerProperty(uName);
            second = new SimpleStringProperty(val);
            third = new SimpleStringProperty(type);
            four = new SimpleStringProperty(nc);
            five=new SimpleStringProperty(xtra);
          //  System.out.println("person Data "+first +" "+second+" "+third+" "+four+" "+five );
           // System.out.println("data pass in constructor are "+uName +" "+val+" "+type+" "+nc+" "+xtra);
        }

        public int getId() {
            return first.get();
        }

        public void setId(int uName) {
            first.set(uName);
        }

        public String getFName() {
            return second.get();
        }

        public void setFName(String val) {
            second.set(val);
        }

        public String getLName() {
            return third.get();
        }

        public void setLName(String type) {
            third.set(type);
        }
        
        public String getUserId(){
            return four.get();
        }
        
        public void setUserId(String nc){
            four.set(nc);
        }
        public String getPhone(){
            return five.get();
        }
        
        public void setPhone(String nc){
            five.set(nc);
        }
    }
    
} 



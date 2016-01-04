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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import javafx.stage.Stage;
import javafx.util.Callback;
import org.bson.Document;

/**
 * FXML Controller class
 *
 * @author Third Ev
 */
public class Security4Controller implements Initializable {    
    @FXML private TableView<Person> EntityTable;
    @FXML private TableView<Person2> roleTable;
    @FXML private ComboBox roleCombo;
    @FXML private TableColumn<Person, String> entitycol;
    @FXML private TableColumn<Person2, String> uidcol;
    @FXML private TableColumn<Person2, String> fnmcol;
    @FXML private TableColumn<Person2, String> lnmcol;
    @FXML private TableColumn<Person, Boolean> addcol;
    @FXML private TableColumn<Person, Boolean> editcol;
    @FXML private TableColumn<Person, Boolean> delcol;
   
    ObservableList<Person2> empData = FXCollections.observableArrayList();
    int RoleID;
    MongoClient client = new MongoClient();
    MongoDatabase  db = client.getDatabase("FinalDemo");
    BasicDBObject obj=new BasicDBObject();
    BasicDBObject basicdb=new BasicDBObject();
    MongoCollection<Document> col = db.getCollection("SecurityRole");    
    final ObservableList<Person> data = FXCollections.observableArrayList( 
        		new Person( "Customer", false,false,false),
                        new Person( "Customer Group", false,false,false),
                        new Person( "Gift Card", false,false,false),
                        new Person( "Item Group", false,false,false),
                        new Person( "Lookup Table", false,false,false),
                        new Person( "Purchase Order", false,false,false),
                        new Person( "Purchase Invoice", false,false,false),
                        new Person( "Role", false,false,false),
                        new Person( "Sales Promotion", false,false,false),
                        new Person( "Sales Person", false,false,false),
                        new Person( "Tender Type", false,false,false),
                        new Person( "User", false,false,false),                        
                        new Person( "Vendor", false,false,false),                
                       new Person( "Tax Class", false,false,false));
    final ObservableList<Person2> data1 = FXCollections.observableArrayList();
    @FXML
    public void handleButtonAction(ActionEvent e){
        System.err.println("hello");
    }     
    
    @FXML
    private void handleManageButtonAction(ActionEvent eve) {
        System.out.println("You clicked manage role of security button!");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SecurityRoles.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));  
            stage.setTitle("Security Roles");
            stage.show();
        } catch(Exception e) {
           e.printStackTrace();
          }      
    }
    @FXML public void handleRoleCOmboAction(ActionEvent af){
           
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
       entitycol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("EntityName"));      
       addcol.setCellValueFactory(new PropertyValueFactory("Add"));
       editcol.setCellValueFactory(new PropertyValueFactory("Edit"));
       delcol.setCellValueFactory(new PropertyValueFactory("Delete"));
       uidcol.setCellValueFactory(new PropertyValueFactory("UID"));
       fnmcol.setCellValueFactory(new PropertyValueFactory("FirstName"));
       lnmcol.setCellValueFactory(new PropertyValueFactory("LastName"));
       roleTable.setItems(empData);
      /* MongoCursor<Document> cur=db.getCollection("EmployeeDetail").find().iterator();
       while(cur.hasNext()){
           Document rs=cur.next();
           System.out.println("UserEID is "+rs.getString("UserEID")+" first name is "+rs.getString("FirstName")+" Last Name is "+rs.getString("LastName"));
           //empData.add(new Person2(rs.getString("UserEID"),rs.getString("FirstName"),rs.getString("LastName")));
       } */   
       roleCombo.setOnAction((event) -> {                
            String selectedPerson = roleCombo.getSelectionModel().getSelectedItem().toString();
            System.out.println("ComboBox Action (selected: " + selectedPerson + ")");
            basicdb.clear();
            data1.clear();
            RoleID=0;
            basicdb.put("Role", selectedPerson);
            MongoCursor<Document> cursorFind = db.getCollection("EmployeeDetail").find(basicdb).iterator();
            while(cursorFind.hasNext()){
                Document g=cursorFind.next();
                //RoleID=g.getInteger("ID");
         data1.add(new Person2(g.getString("UserEID"), g.getString("FirstName"),g.getString("LastName")));
                System.out.println("usereid "+g.getString("FirstName"));
        }
     roleTable.setItems(data1);
               System.out.println("Set data of table");
});
       addcol.setCellFactory(new Callback<TableColumn<Person, Boolean>, TableCell<Person, Boolean>>() {         
            public TableCell<Person, Boolean> call(TableColumn<Person, Boolean> p) { 
                return new CheckBoxTableCell<Person, Boolean>(); 
            }
 
        });
       editcol.setCellFactory(new Callback<TableColumn<Person, Boolean>, TableCell<Person, Boolean>>() {        
            public TableCell<Person, Boolean> call(TableColumn<Person, Boolean> p) { 
                return new CheckBoxTableCell<Person, Boolean>(); 
            } 
        });
       delcol.setCellFactory(new Callback<TableColumn<Person, Boolean>, TableCell<Person, Boolean>>() { 
        
 
            public TableCell<Person, Boolean> call(TableColumn<Person, Boolean> p) {
 
                return new CheckBoxTableCell<Person, Boolean>();
 
            }
 
        });
        
        EntityTable.setItems(data);
        MongoCursor<Document> cursor4 = col.find().iterator();
            try {
            while (cursor4.hasNext()) {
                
                String rs=cursor4.next().getString("Role");
                roleCombo.setValue(rs);
                roleCombo.getItems().addAll(rs); 
            }
        } finally {
            cursor4.close();
        }  
    }    
     public static class CheckBoxTableCell<S, T> extends TableCell<S, T> { 
        private final CheckBox checkBox; 
        private ObservableValue<T> ov;
 
        public CheckBoxTableCell() { 
            this.checkBox = new CheckBox(); 
            this.checkBox.setAlignment(Pos.CENTER);   
            setAlignment(Pos.CENTER); 
            setGraphic(checkBox); 
        }      
 
        @Override public void updateItem(T item, boolean empty) { 
            super.updateItem(item, empty); 
            if (empty) { 
                setText(null); 
                setGraphic(null);
                            } else { 
                setGraphic(checkBox); 
                if (ov instanceof BooleanProperty) { 
                    checkBox.selectedProperty().unbindBidirectional((BooleanProperty) ov); 
                } 
                ov = getTableColumn().getCellObservableValue(getIndex()); 
                if (ov instanceof BooleanProperty) { 
                    checkBox.selectedProperty().bindBidirectional((BooleanProperty) ov); 
                } 
            } 
        } 
    }   
     public class Person2{
         
         private StringProperty UID,FirstName,LastName;
         public Person2(String id1,String fnm,String lnm){
             UID=new SimpleStringProperty(id1);
             FirstName=new SimpleStringProperty(fnm);
             LastName=new SimpleStringProperty(lnm);            
            }
         public String getUID(){
             return UID.get();                  
            }
         public void setUID(String ID){    
              UID.set(ID);
            }
         public String getFirstName(){
             return FirstName.get();                  
            }
         public void setFirstName(String ID){    
              FirstName.set(ID);
            }
         public String getLastName(){
             return LastName.get();                  
            }
         public void setLastName(String lname1){    
              LastName.set(lname1);
            }
     }
     public static class Person {     
        private StringProperty EntityName;   
        private BooleanProperty Add;
        private BooleanProperty Edit;
        private BooleanProperty VarD;

    public Person(String firstName,boolean add1,boolean edit2,boolean del3) {
        EntityName = new SimpleStringProperty(firstName);
        Add= new SimpleBooleanProperty(add1);
        Edit = new SimpleBooleanProperty(edit2);
        VarD = new SimpleBooleanProperty(del3);
        System.out.println("value of delete is "+VarD+" del3 is"+del3);
        System.out.println("value of Add is "+Add+" add1 is"+del3);
         VarD.addListener(new ChangeListener<Boolean>() {
 
                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
 
                    System.out.println(EntityNameProperty().get() + " Del checked: " + t1);
 
                }
 
            }); 
       Add.addListener(new ChangeListener<Boolean>() {
 
                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
 
                    System.out.println(EntityNameProperty().get() + " add checked: " + t1);
 
                }
 
            }); 
       Edit.addListener(new ChangeListener<Boolean>() {
 
                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
 
                    System.out.println(EntityNameProperty().get() + " Edit checked: " + t1);
 
                }
 
            }); 
      
    }

    public String getEntityName() {
      return EntityName.get();
    }


    public boolean isAdd() {
      return Add.get();
    }
     public boolean isEdit() {
      return Edit.get();
    }
      public boolean isVarD() {
      return VarD.get();
    }

    public void setEntityName(String firstName) {
      EntityName.set(firstName);
    }

    public void setAdd(boolean add1) {
      Add.set(add1);
    }
    
    public void setEdit(boolean add1) {
      Edit.set(add1);
    }
    
    public void setVarD(boolean add1) {
      VarD.set(add1);
    }

    public StringProperty EntityNameProperty() {
      return EntityName;
    }
    public BooleanProperty AddProperty() {
      return Add;
    }
    public BooleanProperty EditProperty() {
      return Edit;
    }
    public BooleanProperty DeleteProperty() {
      return VarD;
    }
    
 }

}

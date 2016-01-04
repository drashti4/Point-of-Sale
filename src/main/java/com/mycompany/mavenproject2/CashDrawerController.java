/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;


import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import static com.mycompany.mavenproject2.Sys1Controller.stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;


import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
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
public class CashDrawerController implements Initializable {
    @FXML TextField UnitText;
    @FXML CheckBox NCCheck;
    @FXML TextField CurrencyText;
    @FXML  RadioButton BillRadio;
    @FXML RadioButton CoinRadio;
    @FXML Button RemoveButton;
    static String select="";
    static String NC="N";
    @FXML TableView<Person> table;
    @FXML TableColumn<Person,String> unitcol ;
    @FXML TableColumn<Person,String> valuecol ;
    @FXML TableColumn<Person,String> typecol ;
    @FXML TableColumn<Person,String> nccol ;
    private final ObservableList<Person> data
            = FXCollections.observableArrayList(     
                    
            );
    int repeat=0;
    BasicDBObject ref;
    MongoClient client = new MongoClient();
    MongoDatabase  db=client.getDatabase("FinalDemo");
@FXML
private void handleRemoveButtonAction(ActionEvent ev){
     /*org.controlsfx.control.action.Action response =  Dialogs.create()
        .owner(stage)
        .title("Confirm Dialog with Custom Actions")
        .masthead("Look, a Confirm Dialog with Custom Actions")
        .message("Are you ok with this?")
        .actions(Dialog.ACTION_OK, Dialog.ACTION_CANCEL)
        .showConfirm();
        

if (response == Dialog.ACTION_OK) {*/
         System.out.println("You click okay confirm");
         List items =  new ArrayList (table.getSelectionModel().getSelectedItems());  
         data.removeAll(items);
         Person person = table.getSelectionModel().getSelectedItem();
         table.getSelectionModel().clearSelection();                       
        db.getCollection("CashDrawerDetail").deleteOne(eq("UnitName",person.getUnitName()));
        data.removeAll(items);
        table.getSelectionModel().clearSelection();
/*}
else{
    System.out.println("You click cancel confirm");
}*/
   
}

    @FXML
    private void  handleAddButtonAction(ActionEvent eve){
         ref=new BasicDBObject();
        ref.put("UnitName", Pattern.compile(UnitText.getText() , Pattern.CASE_INSENSITIVE));
        MongoCursor<Document> myDoc2 =  db.getCollection("CashDrawerDetail").find(ref).iterator();
        if(myDoc2.hasNext()){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error ");
           alert.setContentText("ChargeName Already exist!");
           alert.showAndWait();
           UnitText.clear();
           CurrencyText.clear();
           repeat=1;
       }
       else{
           repeat=0;
       }
       if(repeat==0){
                InsertMongo();
               // InsertSQl(conn);
             
       
        if(BillRadio.isSelected()){
            select="Bill";
        }
        if(CoinRadio.isSelected()){
          select="Coin";
        }
        if(NCCheck.isSelected()){
            NC="Y";
        }    else
        {
             NC="N";
        }
       data.add(new Person(
                UnitText.getText(),
                CurrencyText.getText(),
                select,NC));
       
       }
        UnitText.clear();
        CurrencyText.clear();
          
     }
    public void InsertMongo(){
            Document input=createSaleSeedData();
            db.getCollection("CashDrawerDetail").insertOne(input);
            
    }
    public Document createSaleSeedData(){
               
       Document d=new Document();
       d.append("UnitName", UnitText.getText());
       d.append("Currency", CurrencyText.getText());
       d.append("UnitType", select);
       d.append("NonCashUnit",NC);
        return d;
    }
     @FXML
    public void handleCloseButtonAction(ActionEvent a){
        Stage stage=(Stage) RemoveButton.getScene().getWindow();
            stage.close();     
    }
    
    
         @Override
    public void initialize(URL url, ResourceBundle rb) {
      unitcol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("UnitName"));
      valuecol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Value"));
      typecol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Type"));
      nccol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("NC"));
      MongoCursor<Document> cursor = db.getCollection("CashDrawerDetail").find().iterator();
             System.out.println("Intilization");
        try {
            while (cursor.hasNext()) {
                Document i=cursor.next();
                System.out.println("Unit name is "+i.getString("UnitName"));
                data.add(new Person(i.getString("UnitName"),i.getString("Currency"),i.getString("UnitType"),i.getString("NonCashUnit")));
                
            }
        } finally {
            cursor.close();
        }
       table.setItems(data);
     
    }    
    public static class Person {

        private final SimpleStringProperty UnitName;
        private final SimpleStringProperty Value;
        private final SimpleStringProperty Type;
         private final SimpleStringProperty NC;
        private Person(String uName, String val, String type,String nc) {
            this.UnitName = new SimpleStringProperty(uName);
            this.Value = new SimpleStringProperty(val);
            this.Type = new SimpleStringProperty(type);
            this.NC = new SimpleStringProperty(nc);
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

        public String getType() {
            return Type.get();
        }

        public void setType(String type) {
            Type.set(type);
        }
        
        public String getNC(){
            return NC.get();
        }
        
        public void setNC(String nc){
            NC.set(nc);
        }
    }

}

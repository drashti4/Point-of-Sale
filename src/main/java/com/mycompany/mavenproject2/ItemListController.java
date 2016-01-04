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
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.bson.Document;
public class ItemListController implements Initializable {
    @FXML TableView<Person> ItemTable;
    @FXML TableColumn<Person,String> first,second,third,fourth,fifth,sixth;
    public static Person personSelection;
    @FXML Button close;
    MongoClient client = new MongoClient();
    MongoDatabase  db = client.getDatabase("FinalDemo");
        private final ObservableList<Person> data
            = FXCollections.observableArrayList();   
    @FXML
    public void handleSelectButtonAction(ActionEvent as){
        try {
            personSelection = ItemTable.getSelectionModel().getSelectedItem();
            Stage stg=new Stage();
            FXMLLoader fxml1=new FXMLLoader(getClass().getResource("/fxml/SalePromoAdd.fxml"));
            Parent root1=(Parent)fxml1.load();
            SalePromoAddController controller=fxml1.<SalePromoAddController>getController();
            stg.setScene(new Scene(root1));
          //  controller.setSelectedItem();
            Stage stage3=(Stage) close.getScene().getWindow();
            stage3.close();            
        } catch (Exception ex) {
            Logger.getLogger(ItemListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    public void handleDeleteButtonAction(ActionEvent ads){
          /*org.controlsfx.control.action.Action response =  Dialogs.create()
        .owner(stage)
        .title("Confirm Dialog with Custom Actions")
        .masthead("Look, a Confirm Dialog with Custom Actions")
        .message("Are you ok with this?")
        .actions(Dialog.ACTION_OK, Dialog.ACTION_CANCEL)
        .showConfirm();
        

if (response == Dialog.ACTION_OK) {*/
         List items =  new ArrayList (ItemTable.getSelectionModel().getSelectedItems());  
         data.removeAll(items);
         Person person = ItemTable.getSelectionModel().getSelectedItem();
         ItemTable.getSelectionModel().clearSelection();                       
         db.getCollection("ItemDetail").deleteOne(eq("SKU",person.getSKU()));
        data.removeAll(items);
        ItemTable.getSelectionModel().clearSelection();
        /*}
else{
    System.out.println("You click cancel confirm");
}*/
   
    }
    @FXML
    public void handleAddInItemListButtonAction(ActionEvent a){
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddItemList.fxml"));
            Parent root44 = (Parent) fxmlLoader.load();
            stage.setScene(new Scene(root44));
            stage.setTitle("Item Entry");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ItemListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    public void handleHistoryButtonAction(ActionEvent a){
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ItemHistory.fxml"));
            Parent root44 = (Parent) fxmlLoader.load();
            stage.setScene(new Scene(root44));
            stage.setTitle("Item History");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ItemListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    public void handleCustomFilterButtonAction(ActionEvent a){
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CustomFilter.fxml"));
            Parent root44 = (Parent) fxmlLoader.load();
            stage.setScene(new Scene(root44));
            stage.setTitle("Custom Filter");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ItemListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      first.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("SKU"));
      second.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("ItemName"));
      third.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Size"));
      fourth.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Pack"));
      fifth.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Price"));     
      sixth.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Qty"));     
      MongoCursor<Document> cursor4 = db.getCollection("ItemDetail").find().iterator();
            try {
            while (cursor4.hasNext()) {                
                Document rs=cursor4.next();
               System.out.println("SKU is "+rs.getString("SKU")+" iname is "+rs.getString("ItemName")+" Margin is "+rs.getString("Margin")+" Unit Cost is "+rs.getString("UnitCost")+"MSRP "+rs.getString("MSRP"));
              data.add(new Person((rs.getString("SKU")), (rs.getString("ItemName")), (rs.getString("Size_Name")), (rs.getString("Pack_Name")), (rs.getString("UnitCost")),(rs.getString("Quantity"))) );
            }
        } finally {
            cursor4.close();
        }  
        ItemTable.setItems(data);
    }    
    public static class Person {
        private final SimpleStringProperty SKU;
        private final SimpleStringProperty ItemName;
        private final SimpleStringProperty Size;
        private final SimpleStringProperty  Pack;
        private final SimpleStringProperty Price;
        private final SimpleStringProperty Qty;
        private Person(String uName, String val, String type,String nc,String xtra,String Qty1) {
            SKU = new SimpleStringProperty(uName);
            ItemName = new SimpleStringProperty(val);
            Size = new SimpleStringProperty(type);
            Pack = new SimpleStringProperty(nc);
            Price = new SimpleStringProperty(xtra);
            Qty = new SimpleStringProperty(Qty1);
            System.out.println("person Data "+SKU +" "+ItemName+" "+Size+" "+Pack+" "+Price+" "+Qty );
            System.out.println("data pass in constructor are "+uName +" "+val+" "+type+" "+nc+" "+xtra+" "+Qty1);
        }

        public String getSKU() {
            return SKU.get();
        }

        public void setfirst(String first) {
            this.SKU.set(first);
        }

        public String getItemName() {
            return ItemName.get();
        }

        public void setItemName(String second) {
            this.ItemName.set(second);
        }

        public String getSize() {
            return Size.get();
        }

        public void setSize(String third) {
            this.Size.set(third);
        }
        
        public String getPrice(){
            return Price.get();
        }
        
        public void setPrice(String fourth){
            this.Price.set(fourth);
        }
        public String getQty(){
            return Qty.get();
        }
        
        public void setQty(String fifth){
            this.Qty.set(fifth);
        }
        
        public String getPack(){
            return Pack.get();
        }
        
        public void setPack(String sixth){
            this.Pack.set(sixth);
        }
       
    }
    
} 


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
import static com.mycompany.mavenproject2.Sys1Controller.stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.application.Platform;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
public class SalePromoAddController extends Thread implements Initializable{
    @FXML TextField SKUText,CostText,ItemNameText,PriceText,FormulaQuantity,DisLevelText;
    @FXML ComboBox PromoTarget,TargetCombo,CountType,DiscountType;
    @FXML Button SelectFormulaButton;
    @FXML Tab ListTab,FormulaTab;
    @FXML TabPane TabPane;
    @FXML TableView<Person> FormulaTable;
    @FXML TableColumn<Person,String> discol ;
    @FXML TableColumn<Person,Integer> valcol ;
    @FXML TableColumn<Person,String> qtypecol ;
    @FXML TableColumn<Person,String> quantity ;
    String selectedItem;
    BasicDBObject ref;
    MongoClient client = new MongoClient();
    MongoDatabase  db = client.getDatabase("FinalDemo");
    public static ObservableList<Person> data= FXCollections.observableArrayList();
    public static ObservableList<Person> data1= FXCollections.observableArrayList();
    @FXML public void handleAddSaleFormulaButtonAction(ActionEvent a){
        data1.add(new Person(CountType.getValue().toString(),Integer.parseInt(FormulaQuantity.getText()) , DiscountType.getValue().toString(), DisLevelText.getText()));
        FormulaTable.setItems(data1);
        FormulaQuantity.clear();
        DisLevelText.clear();
    }
    @FXML public void handleRemoveButtonAction(ActionEvent aj){
        org.controlsfx.control.action.Action response =  Dialogs.create()
        .owner(stage)
        .title("Confirm Actions")        
        .message("Are you ok with this?")
        .actions(Dialog.ACTION_OK, Dialog.ACTION_CANCEL)
        .showConfirm();
        

if (response == Dialog.ACTION_OK) {
         List items =  new ArrayList (FormulaTable.getSelectionModel().getSelectedItems());  
         data1.removeAll(items);
         //Person person = FormulaTable.getSelectionModel().getSelectedItem();
         FormulaTable.getSelectionModel().clearSelection();                               
        data1.removeAll(items);
        FormulaTable.getSelectionModel().clearSelection();
}
else{
    System.out.println("cANCEL");
}
    }
    @FXML    public void handleSeelectButtonAction(ActionEvent af){
        try {
            System.out.println("All Item button");
            Stage stg=new Stage();
            FXMLLoader fxml1=new FXMLLoader(getClass().getResource("/fxml/AllItem.fxml"));
            Parent root1=(Parent)fxml1.load();            
            stg.setScene(new Scene(root1));
            stg.setTitle("Select Item");
            stg.show();            
        } catch (IOException ex) {
            Logger.getLogger(SalePromoAddController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setSelectedItem(){
        try {
            //System.out.println("Thread in Running");
            Stage stg=new Stage();
            FXMLLoader fxml1=new FXMLLoader(getClass().getResource("/fxml/AllItem.fxml"));
            Parent root1=(Parent)fxml1.load();
            AllItemController controller=fxml1.<AllItemController>getController();
            stg.setScene(new Scene(root1));
            selectedItem=controller.ItemName;
            System.out.println("Selcted Item is "+selectedItem);
            ref=new BasicDBObject();
            ref.put("ItemName", selectedItem );
            MongoCursor<Document> myDoc2 =  db.getCollection("ItemDetail").find(ref).iterator();
            if(myDoc2.hasNext()){
            Document pos=myDoc2.next();           
            ItemNameText.setText(selectedItem);
            SKUText.setText(pos.getString("SKU"));
            CostText.setText(pos.getString("UnitCost"));            
            PriceText.setText(pos.getString("UnitPrice"));
            /*FXMLLoader fxml=new FXMLLoader(getClass().getResource("/fxml/SalePromoAdd.fxml"));
            Parent root=(Parent)fxml.load();
            Stage r=new Stage(); FXMLLoader fxml1=new FXMLLoader(getClass().getResource("/fxml/AllItem.fxml"));
            Parent
            r.setScene(new Scene(root));            
            r.show();*/
            System.out.println("Name is "+selectedItem+" SKU is "+pos.getString("SKU")+" Cost is "+pos.getString("UnitCost"));            
            System.out.println("ItemNameText "+ItemNameText.getText()+" SKUText "+SKUText.getText()+" Cost Text "+CostText.getText());
            
        }
        } catch (IOException ex) {
            Logger.getLogger(SalePromoAddController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
  @Override
  public void run() {
     Platform.runLater(new Runnable() {

         @Override
         public void run() {
             try{
             System.out.println("Thread is Runing");
              Stage stg=new Stage();
                 System.out.println("1");
              FXMLLoader fxml1=new FXMLLoader(getClass().getResource("/fxml/AllItem.fxml"));
              System.out.println("2");
            Parent root1=(Parent)fxml1.load();
            System.out.println("3");
            AllItemController controller=fxml1.<AllItemController>getController();
            stg.setScene(new Scene(root1));
            selectedItem=controller.ItemName;
            System.out.println("Selcted Item is "+selectedItem);
            ref=new BasicDBObject();
            ref.put("ItemName", selectedItem );
            MongoCursor<Document> myDoc2 =  db.getCollection("ItemDetail").find(ref).iterator();
            if(myDoc2.hasNext()){
            Document pos=myDoc2.next();  
            System.out.println("Final Ouput");
            FXMLLoader fxml=new FXMLLoader(getClass().getResource("/fxml/SalePromoAdd.fxml"));
            Parent root=(Parent)fxml.load();
            Stage r=new Stage();
            System.out.println("kk");
            r.setScene(new Scene(root));  
            System.out.println("Final 2 Ouput "+selectedItem);
            ItemNameText.setText(selectedItem);
            SKUText.setText(pos.getString("SKU"));
            CostText.setText(pos.getString("UnitCost"));            
            PriceText.setText(pos.getString("UnitPrice"));
            
                       
            r.show();
            System.out.println("Name is "+selectedItem+" SKU is "+pos.getString("SKU")+" Cost is "+pos.getString("UnitCost"));            
            //System.out.println("ItemNameText "+ItemNameText.getText()+" SKUText "+SKUText.getText()+" Cost Text "+CostText.getText());
            }
            } catch (IOException ex) {
            Logger.getLogger(SalePromoAddController.class.getName()).log(Level.SEVERE, null, ex);
        }
         }
     });
     }
  

    @Override
    public void initialize(URL url, ResourceBundle rb){
        discol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("DiscountType"));
        valcol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,Integer>("Value"));
        qtypecol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("QuantityType"));
        quantity.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Quantity"));
        FormulaTable.setItems(data1);
        SKUText.setDisable(true);
        ItemNameText.setDisable(true);
        PriceText.setDisable(true);
        CostText.setDisable(true);
        SelectFormulaButton.setDisable(true);
        TargetCombo.setDisable(true);
        TabPane.getSelectionModel().selectLast();        
      // System.out.println("FX Vesion "+com.sun.javafx.runtime.VersionInfo.getRuntimeVersion());
        PromoTarget.setOnAction((event) -> {            
            String selectedPerson = PromoTarget.getSelectionModel().getSelectedItem().toString();            
            if(selectedPerson.equalsIgnoreCase("Single Item")){
                System.out.println("ComboBox Action (selected: " + selectedPerson + ")");
                TargetCombo.setDisable(true);
                SelectFormulaButton.setDisable(false);                
            }
            else if(selectedPerson.equalsIgnoreCase("Item Group")){
                System.out.println("ComboBox Action (selected: " + selectedPerson + ")");                           
                SelectFormulaButton.setDisable(true);                
                TargetCombo.getSelectionModel().clearSelection();
                TargetCombo.getItems().clear();
                MongoCursor<Document> cursor=db.getCollection("ItemGroup").find().iterator();
                while(cursor.hasNext()){
                    String rs=cursor.next().getString("GroupName");
                    TargetCombo.setValue(rs);
                     TargetCombo.getItems().addAll(rs);  
                }    
                ListTab.disableProperty().setValue(Boolean.TRUE);
                FormulaTab.disableProperty().setValue(Boolean.FALSE);
                TabPane.getSelectionModel().selectLast();
                 TargetCombo.setDisable(false);
            }
            else if(selectedPerson.equalsIgnoreCase("Custom List")){
                System.out.println("ComboBox Action (selected: " + selectedPerson + ")");
                TargetCombo.setDisable(true);
                
                 ListTab.disableProperty().setValue(Boolean.FALSE);
                 FormulaTab.disableProperty().setValue(Boolean.TRUE);
                TabPane.getSelectionModel().selectFirst();
            }
            else if(selectedPerson.equalsIgnoreCase("Department")){
                System.out.println("ComboBox Action (selected: " + selectedPerson + ")");                           
                SelectFormulaButton.setDisable(true);                
                TargetCombo.getSelectionModel().clearSelection();
                TargetCombo.getItems().clear();
                MongoCursor<Document> cursor=db.getCollection("DeptDetail").find().iterator();
                while(cursor.hasNext()){
                    String rs=cursor.next().getString("Name");
                    TargetCombo.setValue(rs);
                     TargetCombo.getItems().addAll(rs);  
                }    
                ListTab.disableProperty().setValue(Boolean.TRUE);
                FormulaTab.disableProperty().setValue(Boolean.FALSE);
                TabPane.getSelectionModel().selectLast();
                TargetCombo.setDisable(false);
            }
            else if(selectedPerson.equalsIgnoreCase("Category")){
                System.out.println("ComboBox Action (selected: " + selectedPerson + ")");                           
                SelectFormulaButton.setDisable(true);                
                TargetCombo.getSelectionModel().clearSelection();
                TargetCombo.getItems().clear();
                MongoCursor<Document> cursor=db.getCollection("CategoryDetail").find().iterator();
                while(cursor.hasNext()){
                    String rs=cursor.next().getString("Name");
                    TargetCombo.setValue(rs);
                     TargetCombo.getItems().addAll(rs);  
                }    
                ListTab.disableProperty().setValue(Boolean.TRUE);
                FormulaTab.disableProperty().setValue(Boolean.FALSE);
                TabPane.getSelectionModel().selectLast();
                 TargetCombo.setDisable(false);
        }
            else{
                ListTab.disableProperty().setValue(Boolean.TRUE);
                FormulaTab.disableProperty().setValue(Boolean.FALSE);
                TabPane.getSelectionModel().selectLast();
            }
           /* basicdb.clear();
            basicdb.put("Cat_Name", selectedPerson);
            MongoCursor<Document> cursorFind = db.getCollection("ItemDetail").find(basicdb).iterator();
            while(cursorFind.hasNext()){
                Document g=cursorFisnd.next();
            // System.out.println("Item is "+cursorFind.next().getString("ItemName"));
                data.add(new Person(g.getString("SKU"), g.getString("ItemDesc"),g.getString("Size_Name"), g.getString("Pack_Name"), g.getString("UnitCost")));
            //System.out.println("result data is "+g.getString("SKU")+ g.getString("ItemDesc")+g.getString("Size_Name")+ g.getString("Pack_Name")+" " +g.getString("UnitCost"));
        }*/
     
});    
    }   
     public static class Person {

        private final SimpleStringProperty DiscountType;
        private final SimpleIntegerProperty Value;
        private final SimpleStringProperty QuantityType;
         private final SimpleStringProperty Quantity;
        private Person(String uName, int val, String type,String nc) {
            this.DiscountType = new SimpleStringProperty(uName);
            this.Value = new SimpleIntegerProperty(val);
            this.QuantityType = new SimpleStringProperty(type);
            this.Quantity = new SimpleStringProperty(nc);
        }

        public String getDiscountType() {
            return DiscountType.get();
        }

        public void setDiscountType(String uName) {
            DiscountType.set(uName);
        }

        public int getValue() {
            return Value.get();
        }

        public void setValue(int val) {
            Value.set(val);
        }

        public String getQuantityType() {
            return QuantityType.get();
        }

        public void setQuantityType(String type) {
            QuantityType.set(type);
        }
        
        public String getQuantity(){
            return Quantity.get();
        }
        
        public void setQuantity(String nc){
            Quantity.set(nc);
        }
    }
}

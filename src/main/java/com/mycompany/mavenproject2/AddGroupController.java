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
    @FXML TableColumn<Person,String> sku,desccol,sizecol,packcol,pricecol;
    //@FXML ComboBox DeptCombo,SizeCombo,CatCombo,PackCombo,BrandCombo,VendorCombo;
    @FXML TextField filterField,GroupNameText,GroupDescText;
    public static ObservableList<Person> data= FXCollections.observableArrayList();
    MongoClient client=new MongoClient();
    MongoDatabase db=client.getDatabase("FinalDemo");
    @FXML TableView<Person> ItemTable,ItemTable2;
    public static ObservableList<Person> obv= FXCollections.observableArrayList();
    public static Document d,ItemDoc;
    @FXML public void hanldeShowItemAction(ActionEvent as){
        
    }
     @FXML public void handlePutButtonAction(ActionEvent af){
        System.out.println("You selected put button");
        Person person = ItemTable.getSelectionModel().getSelectedItem();
        System.out.println("Selected row is "+person.getDesc());
        obv.add(person);     
        ItemTable2.setItems(obv);
       /* System.out.println("total rows are in item table 2 "+ ItemTable2.getItems().size());      
        System.out.println("total rows are in item table 1 "+ ItemTable.getItems().size());  */           
    }
     @FXML public void handleAddAllButtonAction(ActionEvent af){
         ItemTable2.getItems().clear();
         ItemTable2.getItems().addAll(ItemTable.getItems());
     }
      @FXML public void handleRemoveAllSelectionButton(ActionEvent ad){
        ItemTable2.getItems().clear();
    }
       @FXML public void handleGetButtonAction(ActionEvent ah){
        System.out.println("You selected get button");
        Person person = ItemTable2.getSelectionModel().getSelectedItem();
//        System.out.println("Selected row is "+person.getDesc());
        //obv.add(person);     
        
      //  ItemTable.getItems().add(person);
        ItemTable2.getItems().remove(person);
    }
       @FXML public void handleSaveButtonAction(ActionEvent ag){
           /*int flag=0;
           for (Node r: ItemTable2.lookupAll(".table-row-cell")){
               System.out.print("New row");               
               flag=0;
               for (Node c: r.lookupAll(".table-cell")){
                   if(flag==0){
                        System.out.println(c.getId()+"first cell is "+c.toString());
                        flag=1;          
                       if(c.){
                            System.out.println(" Now null");
                            flag=2;
                        }
                                
                   }
                   else{
                       if(flag==1){
                       System.out.println("cell is "+c.getUserData().toString());
                       if(c.toString().trim().isEmpty()){
                           System.out.println("Empty String");
                       }
                       }
                   }
         }
     }*/
           d=new Document();
           ItemDoc=new Document();
           d.append("GroupName", GroupNameText.getText());
           d.append("GroupDesc", GroupDescText.getText()); 
           ItemTable2.getItems().forEach(item -> CreateSeedData(item));  //diffi
           
           db.getCollection("ItemGroup").insertOne(d);
       }
       private void CreateSeedData(Person p){  
            
           System.out.println("Sku Are "+p.getSKU());
           ItemDoc.append(p.getSKU(),  p.getSKU());           
          //  db.getCollection("ItemGroup").insertOne(ItemDoc);
           // System.out.println("Added "+p.getSKU());
           d.append("Items",ItemDoc);
       }
    private void initFilter() {       
        filterField.setPromptText("Filter");         
         filterField.textProperty().addListener(new InvalidationListener() {         
             @Override
            public void invalidated(javafx.beans.Observable observable) {
                if(filterField.textProperty().get().isEmpty()) {
                    ItemTable.setItems(data);
                    return;
                }
                ObservableList<Person> tableItems = FXCollections.observableArrayList();
                ObservableList<TableColumn<Person, ?>> cols = ItemTable.getColumns();
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
                ItemTable.setItems(tableItems);
            }
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       skucol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("SKU"));
       desc.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Desc"));
       size.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Size"));  
       pack.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Pack"));      
       price.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Price"));
       sku.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("SKU"));
       desccol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Desc"));
       sizecol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Size"));  
       packcol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Pack"));      
       pricecol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Price"));
       MongoCursor<Document> cursorFind = db.getCollection("ItemDetail").find().iterator();
       while(cursorFind.hasNext()){
         Document g=cursorFind.next();
         data.add(new Person(g.getString("SKU"), g.getString("ItemDesc"),g.getString("Size_Name"), g.getString("Pack_Name"), g.getString("UnitCost")));
        }     
        ItemTable.setItems(data);
      /*  MongoCursor<Document> cur = db.getCollection("CategoryDetail").find().iterator();
        CatCombo.getItems().clear();
            try {
            while (cur.hasNext()) {                
                String rs1=cur.next().getString("Name");              
                CatCombo.setValue(rs1);
                CatCombo.getItems().addAll(rs1);           
            }             
        } finally {
            cur.close();
        }   
        cur =  db.getCollection("DeptDetail").find().iterator();
            try {
            while (cur.hasNext()) {                
                String rs=cur.next().getString("Name");
                DeptCombo.setValue(rs);
                DeptCombo.getItems().addAll(rs);                
            }
        } finally {
            cur.close();
        }     
       cur =  db.getCollection("BrandDetail").find().iterator();
            try {
            while (cur.hasNext()) {                
                String rs=cur.next().getString("Name");
                BrandCombo.setValue(rs);
                BrandCombo.getItems().addAll(rs);                
            }
        } finally {
            cur.close();
        }   
        cur =  db.getCollection("PackDetail").find().iterator();
            try {
                while (cur.hasNext()) {                
                String rs=cur.next().getString("Name");
                PackCombo.setValue(rs);
                PackCombo.getItems().addAll(rs);                
            }
        } finally {
            cur.close();
        }   
        cur =  db.getCollection("SizeDetail").find().iterator();
            try {
            while (cur.hasNext()) {                
                String rs=cur.next().getString("Name");
                SizeCombo.setValue(rs);
                SizeCombo.getItems().addAll(rs);                
            }
        } finally {
            cur.close();
        }*/   
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

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
public class GlobalChanges7Controller implements Initializable {
    @FXML TextField filterField;
    @FXML RadioButton CatRadio;
    @FXML ComboBox CatCombo,DeptCombo,ItemCombo,PackCombo,SupplierCombo,SizeCombo;
    @FXML ComboBox GroupCombo,LocationCombo,AddToCatCombo,AddToDeptCombo,StockCombo,Salemsg;
    @FXML ComboBox idCombo,SaleMsgCombo,WebCombo,Tax1Combo,Tax2Combo,Tax3Combo,AddQCombo,FoodStampCombo;
    @FXML TextField PriceText,CostText,BuyDownText,SKUText,ItemText,QuantityText,ReOrderText,UnitText;
    @FXML ToggleGroup t1;
    @FXML TableView<Person> ItemTable;
    @FXML TableView<Person> ItemTable2;
    @FXML TableColumn<Person,String> skucol ,skucol1;
    @FXML TableColumn<Person,String> desc,desc1;
    static int id;    
    @FXML TableColumn<Person,String> size,size1;
    @FXML TableColumn<Person,String> pack,pack1;
    @FXML TableColumn<Person,String> price,price1;
    public static ObservableList<Person> data= FXCollections.observableArrayList();
    public static ObservableList<Person> obv= FXCollections.observableArrayList(   
    );
    MongoClient client=new MongoClient();
    MongoDatabase db=client.getDatabase("FinalDemo");
    MongoCursor<Document> cursor4 ;
    BasicDBObject basicdb=new BasicDBObject();
    BasicDBObject Itemdb=new BasicDBObject();
    BasicDBObject deptBasicdb=new BasicDBObject();
    @FXML public void handleApplyButtonAction(ActionEvent ag){
        
         ItemTable2.getItems().forEach(item -> System.out.println("fetched sku is "+item.getSKU()));
         ObservableList<Person> item=ItemTable2.getItems();
         for (Person item1 : item) {
            /*Itemdb.clear();
            Itemdb.put("SKU", item1.getSKU());
            MongoCursor<Document> myDoc2 =  db.getCollection("ItemDetailDetail").find(Itemdb).iterator();
            if(myDoc2.hasNext()){
              //update query
            }*/
             System.out.println("updated sku is "+item1.getSKU());
              db.getCollection("ItemDetail").updateOne(new Document("SKU", item1.getSKU()),new Document("$set", UpdateSeedData()));
              
            
        }
         /*working for each cell for (Node r: ItemTable2.lookupAll(".table-row-cell")){
         for (Node c: r.lookupAll(".table-cell")){
          System.out.println(c);
         }
     }*/
    }
     private Document UpdateSeedData(){
        Document d=new Document();               
       
       /* d.append("ItemDesc", DescriptionTextField.getText());
        d.append("ItemName",ItemNameText.getText() );
        d.append("Dept_ID",dept_id );
        d.append("Size_Name",size_name );      
        d.append("Pack_Name",pack_name );
        d.append("Cat_Name", CatCombo.getValue());
        d.append("Brand_Name",brand_name );
        
        d.append("Model_Num",ModelText.getText() );
        d.append("Part_Num",PartText.getText() );
        d.append("NonStock",nonstock );
        d.append("UnitType",IventoryTypeCombo.getValue() );
        d.append("NonText", nontax);
        d.append("Tax1",Tax1Combo.get() );
        d.append("Tax2",Tax2Text.getText() );
        d.append("Tax3",Tax3Text.getText() );
        d.append("QualityPrompt", priceF);
        d.append("UnitCost",CostText.getText() );
        d.append("UnitPrice",PriceText.getText() );
        d.append("Margin",MarginText.getText());
        d.append("MSRP",MSRPText.getText() );       
        d.append("BuyDownAmount",BuyDownText.getText() );
        d.append("MarkUP",MarkUpText.getText() );       
        d.append("SalesPrice",SaleText.getText() );
        d.append("WeightedItem",check1 );       
        d.append("WebItem",check2 );
        d.append("ExcludeSale", check3);       
        d.append("WIC",check4 );        
        d.append("HealthCard",check5 );       */
        d.append("FoodStamp", (FoodStampCombo.getValue().equals("Add"))?1:0);
        /*d.append("NonRevenueItem", check7); 
        if(SaleMsgCombo.getValue().equals("add")){
            d.append("SaleMessage", Salemsg.getValue().toString());
        }
        
        table.getItems().forEach(item -> d.append("Quantity",item.getValue()));
        costTable.getItems().forEach(item -> d.append("CostQuantity",item.getUnitName()));
        costTable.getItems().forEach(item -> d.append("Price",item.getValue()));     */
        
        return d;
    }
    @FXML public void handleCloseButtonAction(ActionEvent ah){
        
    }
    @FXML public void handleCatComboAction(ActionEvent ad){
       // System.out.println("selcted item is "+CatCombo.getSelectionModel().getSelectedItem().toString());
    }
    @FXML
    public void handleCatRadioAction(ActionEvent d){  
        System.out.println("cat radio action");
        DeptCombo.setDisable(true);
        SizeCombo.setDisable(true);
        PackCombo.setDisable(true);
        ItemCombo.setDisable(true);
        SupplierCombo.setDisable(true);
        CatCombo.setDisable(false);
        filterField.setDisable(true);
        cursor4 = db.getCollection("CategoryDetail").find().iterator();
        
            try {
            while (cursor4.hasNext()) {                
                String rs=cursor4.next().getString("Name");
                
                CatCombo.setValue(rs);
                CatCombo.getItems().addAll(rs);   
                CatCombo.setValue("");
            }
            CatCombo.setOnAction((event) -> {
            data.clear();
            String selectedPerson = CatCombo.getSelectionModel().getSelectedItem().toString();
            System.out.println("ComboBox Action (selected: " + selectedPerson + ")");
            basicdb.clear();
            basicdb.put("Cat_Name", selectedPerson);
            MongoCursor<Document> cursorFind = db.getCollection("ItemDetail").find(basicdb).iterator();
            while(cursorFind.hasNext()){
                Document g=cursorFind.next();
            // System.out.println("Item is "+cursorFind.next().getString("ItemName"));
                data.add(new Person(g.getString("SKU"), g.getString("ItemDesc"),g.getString("Size_Name"), g.getString("Pack_Name"), g.getString("UnitCost")));
            //System.out.println("result data is "+g.getString("SKU")+ g.getString("ItemDesc")+g.getString("Size_Name")+ g.getString("Pack_Name")+" " +g.getString("UnitCost"));
        }
     
});    
       } finally {
                ItemTable.setItems(data);
                cursor4.close();
        }   
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
    @FXML public void handleDeptRadioButtonAction(ActionEvent af){
       DeptCombo.setDisable(false);
       CatCombo.setDisable(true);
       SizeCombo.setDisable(true);
       PackCombo.setDisable(true);
       ItemCombo.setDisable(true);
       SupplierCombo.setDisable(true);
       filterField.setDisable(true);
       cursor4 =  db.getCollection("DeptDetail").find().iterator();
       try {
            while (cursor4.hasNext()) {                
                String rs=cursor4.next().getString("Name");
                DeptCombo.setValue(rs);
                DeptCombo.getItems().addAll(rs);                
            }
            DeptCombo.setOnAction((event) -> {
                data.clear();
                String selectedPerson = DeptCombo.getSelectionModel().getSelectedItem().toString();
                System.out.println("ComboBox Action (selected: " + selectedPerson + ")");
                basicdb.clear();
                deptBasicdb.put("Name",selectedPerson);
                MongoCursor<Document> findDept=db.getCollection("DeptDetail").find(deptBasicdb).iterator();
                while(findDept.hasNext()){
                    Document g=findDept.next();
                    id=g.getInteger("ID");
                }
            basicdb.put("Dept_ID", id);
            MongoCursor<Document> cursorFind = db.getCollection("ItemDetail").find(basicdb).iterator();
            while(cursorFind.hasNext()){
            Document g=cursorFind.next();
            // System.out.println("Item is "+cursorFind.next().getString("ItemName"));
            data.add(new Person(g.getString("SKU"), g.getString("ItemDesc"),g.getString("Size_Name"), g.getString("Pack_Name"), g.getString("UnitCost")));
            //System.out.println("result data is "+g.getString("SKU")+ g.getString("ItemDesc")+g.getString("Size_Name")+ g.getString("Pack_Name")+" " +g.getString("UnitCost"));
     }
     
});
        } finally {
                ItemTable.setItems(data);
            cursor4.close();
        }   
    }

    @FXML public void handleGroupRadioAction(ActionEvent as){
        
    }
    @FXML public void handleItemRadioAction(ActionEvent as){
        DeptCombo.setDisable(true);
       CatCombo.setDisable(true);
       SizeCombo.setDisable(true);
       PackCombo.setDisable(false);
       ItemCombo.setDisable(true);
       SupplierCombo.setDisable(true);
       filterField.setDisable(true);
        cursor4 =  db.getCollection("PackDetail").find().iterator();
            try {
            while (cursor4.hasNext()) {                
                String rs=cursor4.next().getString("Name");
                PackCombo.setValue(rs);
                PackCombo.getItems().addAll(rs);                
            }
            PackCombo.setOnAction((event) -> {
                data.clear();
    String selectedPerson = PackCombo.getSelectionModel().getSelectedItem().toString();
    System.out.println("ComboBox Action (selected: " + selectedPerson + ")");
    basicdb.clear();
    basicdb.put("Pack_Name", selectedPerson);
     MongoCursor<Document> cursorFind = db.getCollection("ItemDetail").find(basicdb).iterator();
     while(cursorFind.hasNext()){
         Document g=cursorFind.next();
        // S    ystem.out.println("Item is "+cursorFind.next().getString("ItemName"));
         data.add(new Person(g.getString("SKU"), g.getString("ItemDesc"),g.getString("Size_Name"), g.getString("Pack_Name"), g.getString("UnitCost")));
         //System.out.println("result data is "+g.getString("SKU")+ g.getString("ItemDesc")+g.getString("Size_Name")+ g.getString("Pack_Name")+" " +g.getString("UnitCost"));
     }
     
});
    
        } finally {
                ItemTable.setItems(data);
            cursor4.close();
        }   
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
    @FXML public void handleGetButtonAction(ActionEvent ah){
        System.out.println("You selected get button");
        Person person = ItemTable2.getSelectionModel().getSelectedItem();
//        System.out.println("Selected row is "+person.getDesc());
        //obv.add(person);     
        
      //  ItemTable.getItems().add(person);
        ItemTable2.getItems().remove(person);
    }
    @FXML public void handleRemoveAllSelectionButton(ActionEvent ad){
        ItemTable2.getItems().removeAll(obv);
    }
    @FXML public void handleSupplierRadioAction(ActionEvent as){
        
    }
    @FXML public void handleAllItemRadioAction(ActionEvent aj){
        try{
            DeptCombo.setDisable(true);
       CatCombo.setDisable(true);
       SizeCombo.setDisable(true);
       PackCombo.setDisable(true);
       ItemCombo.setDisable(true);
       SupplierCombo.setDisable(true);
       filterField.setDisable(false);
            System.out.println("Selected radio is all item");
            data.clear();
            MongoCursor<Document> cursorFind = db.getCollection("ItemDetail").find().iterator();
            while(cursorFind.hasNext()){
            Document g=cursorFind.next();
         data.add(new Person(g.getString("SKU"), g.getString("ItemDesc"),g.getString("Size_Name"), g.getString("Pack_Name"), g.getString("UnitCost")));
         //System.out.println("result data is "+g.getString("SKU")+ g.getString("ItemDesc")+g.getString("Size_Name")+ g.getString("Pack_Name")+" " +g.getString("UnitCost"));
     }
        }
         finally {
                ItemTable.setItems(data);

        }   
    }
    @FXML public void handleSizeRadioAction(ActionEvent as){
        DeptCombo.setDisable(true);
       CatCombo.setDisable(true);
       SizeCombo.setDisable(false);
       PackCombo.setDisable(true);
       ItemCombo.setDisable(true);
       SupplierCombo.setDisable(true);
       filterField.setDisable(true);
        cursor4 =  db.getCollection("SizeDetail").find().iterator();
            try {
            while (cursor4.hasNext()) {                
                String rs=cursor4.next().getString("Name");
                SizeCombo.setValue(rs);
                SizeCombo.getItems().addAll(rs);                
            }
            SizeCombo.setOnAction((event) -> {
                data.clear();
    String selectedPerson = SizeCombo.getSelectionModel().getSelectedItem().toString();
    System.out.println("ComboBox Action (selected: " + selectedPerson + ")");
    basicdb.clear();
    basicdb.put("Size_Name", selectedPerson);
     MongoCursor<Document> cursorFind = db.getCollection("ItemDetail").find(basicdb).iterator();
     while(cursorFind.hasNext()){
         Document g=cursorFind.next();
        // System.out.println("Item is "+cursorFind.next().getString("ItemName"));
         data.add(new Person(g.getString("SKU"), g.getString("ItemDesc"),g.getString("Size_Name"), g.getString("Pack_Name"), g.getString("UnitCost")));
         //System.out.println("result data is "+g.getString("SKU")+ g.getString("ItemDesc")+g.getString("Size_Name")+ g.getString("Pack_Name")+" " +g.getString("UnitCost"));
     }
     
});
        } finally {
                ItemTable.setItems(data);
            cursor4.close();
        }   
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       skucol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("SKU"));
       desc.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Desc"));
       size.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Size"));  
       pack.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Pack"));      
       price.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Price"));  
       skucol1.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("SKU"));
       desc1.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Desc"));
       size1.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Size"));  
       pack1.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Pack"));      
       price1.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Price")); 
       DeptCombo.setDisable(true);
       CatCombo.setDisable(true);
       SizeCombo.setDisable(true);
       PackCombo.setDisable(true);
       ItemCombo.setDisable(true);
       SupplierCombo.setDisable(true);
       filterField.setDisable(true);
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

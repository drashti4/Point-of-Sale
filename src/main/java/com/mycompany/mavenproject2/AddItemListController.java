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
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.bson.Document;
 

/**
 * FXML Controller class
 *
 * @author Third Ev
 */
public class AddItemListController implements Initializable {
    @FXML TableView<Person> table,costTable;
    public static SimpleIntegerProperty val1;
    @FXML CheckBox buy,sold,NonStockCheck,NonTaxCheck,InactiveCheck,QuantityCheck,PriceCheck;
    @FXML TableColumn<Person,String> facilitycol ;
    @FXML TableColumn<Person,String> quantitycol;
    @FXML TableColumn<Person,String> costQuantity;
    @FXML TableColumn<Person,String> Price;
    private final ObservableList<Person> data
            = FXCollections.observableArrayList(     
                   new Person("In Store", "0"),
                     new Person("Waehouse", "0")
            );
    int repeat=0;
    BasicDBObject ref;
    MongoClient client = new MongoClient();
    MongoDatabase  db = client.getDatabase("FinalDemo");
    @FXML ComboBox IventoryTypeCombo;
    @FXML TextField SKUTextField,DescriptionTextField,ItemNameText,ModelText,PartText;
    @FXML TextField Tax3Text,Tax1Text,Tax2Text,UnitCostText,UnitPriceText,MarginText,MSRPText,MarkUpText;
    @FXML TextField BuyDownText,SaleText,CaseText,UnitText;
    @FXML Label label;
    @FXML
    TitledPane titledPane;
    @FXML
    ComboBox DeptCombo,CatCombo,SizeCombo,PackCombo,BrandCombo,LocationCombo;
    @FXML
    Button DeptButton,NewR;
    @FXML
    Button AutoButton;
    @FXML CheckBox WeightedItem,WebItem,NonDiscountable,NonRevenueItem,FoodStamp,HealthCard,WIC,ExcludeSale;
    int check1,check2,check3,check4,check5,check6,check7,check8;
    DecimalFormat df = new DecimalFormat("#0.###");
    @FXML AnchorPane AnchorPane;
    int cat_id,location_id,priceF,qua,ADept;
    String size_name,pack_name,brand_name;
    Document d;
    BasicDBObject b;
    BasicDBObject obj=new BasicDBObject();
     BasicDBObject obj1=new BasicDBObject();
    int i,dept_id,nonstock,nontax;
    String SelctedDept;
    Person person;
    public ObservableList<String> a2
            = FXCollections.observableArrayList(     
                   
                     
            );
    public long generateNumber()
    {
        return (long)(Math.random()*1000000000 + 0000000000L);
    }
    @FXML
    public void handlePriceCalcAction(ActionEvent a){
         try {     
            Stage stg=new Stage();
           FXMLLoader fxml=new FXMLLoader(getClass().getResource("/fxml/PriceCalculator1.fxml"));
           Parent root=(Parent)fxml.load();
           stg.setScene(new Scene(root));
           stg.setTitle("Calculate Price");
           stg.show();           
       } catch (IOException ex) {
           Logger.getLogger(SelectDeptController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    @FXML    
    public void handleTestButtonAction(ActionEvent a){
        try {
           Stage stg=new Stage();
           FXMLLoader fxml=new FXMLLoader(getClass().getResource("/fxml/Virtualkey.fxml"));
           Parent root=(Parent)fxml.load();
           stg.setScene(new Scene(root));
           stg.setTitle("Virtual key");
           stg.show();           
       } catch (IOException ex) {
           Logger.getLogger(SelectDeptController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    @FXML
    public void handleSaveitemButtonAction(ActionEvent a){
        //sku,description,item name,combo(brand,location,dept,size,cat,pack),
        //model no.,part no.,check(inactive item,non plu,non stock item,prompt for quality,prompt for price)
        table.getItems().forEach(item -> System.out.println("fetched quanity is "+item.getValue()));
        ref=new BasicDBObject();
        ref.put("ItemName", Pattern.compile(UnitText.getText() , Pattern.CASE_INSENSITIVE));
        MongoCursor<Document> myDoc2 =  db.getCollection("ItemDetailDetail").find(ref).iterator();
        if(myDoc2.hasNext()){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error ");
           alert.setContentText("Item Name Already exist!");
           alert.showAndWait();
           
           repeat=1;
       }
       else{
           repeat=0;
       }
       if(repeat==0){
                InsertMongo();
               // InsertSQl(conn);
       }
    }
     public void InsertMongo(){
        client = new MongoClient();       
        db = client.getDatabase("FinalDemo");        
        MongoCollection  col = db.getCollection("ItemDetail");
         System.out.println("colection created "+col.toString());
        final Document seedData = createSeedData();
        col.insertOne(seedData);        
        SKUTextField.clear();
        DescriptionTextField.clear();
        ItemNameText.clear();
        
   }
      public  Document createSeedData(){           
        d=new Document();
        b=new BasicDBObject();
        b.put("Name",DeptCombo.getValue());              	
	MongoCursor<Document> cursor = db.getCollection("DeptDetail").find(b).iterator();
        try {
            while (cursor.hasNext()) {
                dept_id=cursor.next().getInteger("ID");
                System.out.println("Searched Output "+dept_id);
            }
        } finally {
            cursor.close();
        }
       
         b.clear();
        b.put("Name", SizeCombo.getValue().toString());
          System.out.println("Size outtput is "+SizeCombo.getValue().toString());
        cursor = db.getCollection("SizeDetail").find(b).iterator();
        try {
            while (cursor.hasNext()) {
                size_name=cursor.next().getString("Name");
                System.out.println("size Output "+size_name);
                
            }
        } finally {
            cursor.close();
        }
        b.clear();
        b.put("Name", PackCombo.getValue().toString());
        cursor = db.getCollection("PackDetail").find(b).iterator();
        try {
            while (cursor.hasNext()) {
                pack_name=cursor.next().getString("Name");
                System.out.println("pack Output "+pack_name);
            }
        } finally {
            cursor.close();
        }
        b.clear();
        b.put("Name", BrandCombo.getValue().toString());
        cursor = db.getCollection("BrandDetail").find(b).iterator();
        try {
            while (cursor.hasNext()) {
                brand_name=cursor.next().getString("Name");
                System.out.println("brand Output "+brand_name);
            }
        } finally {
            cursor.close();
        }
      if(NonStockCheck.isSelected()){nonstock=1;}else{nonstock=0;}
      if(NonTaxCheck.isSelected()){nontax=1;}else{nontax=0;}
      if(QuantityCheck.isSelected()){qua=1;}else{qua=0;}
      if(PriceCheck.isSelected()){priceF=1;}else{priceF=0;}
      if(WeightedItem.isSelected()){check1=1;}else{check1=0;}
      if(WebItem.isSelected()){check2=1;}else{check2=0;}
      if(ExcludeSale.isSelected()){check3=1;}else{check3=0;}
      if(WIC.isSelected()){check4=1;}else{check4=0;}
      if(HealthCard.isSelected()){check5=1;}else{check5=0;}
      if(FoodStamp.isSelected()){check6=1;}else{check6=0;}
      if(NonRevenueItem.isSelected()){check7=1;}else{check7=0;}
      if(NonDiscountable.isSelected()){check8=1;}else{check8=0;}
     
        d.append("SKU", SKUTextField.getText());
        d.append("ItemDesc", DescriptionTextField.getText());
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
        d.append("Tax1",Tax1Text.getText() );
        d.append("Tax2",Tax2Text.getText() );
        d.append("Tax3",Tax3Text.getText() );
        d.append("QualityPrompt", priceF);
        d.append("PricePrompt",priceF );
        d.append("UnitCost",UnitCostText.getText() );
        d.append("UnitPrice",UnitPriceText.getText() );
        d.append("Margin",MarginText.getText());
        d.append("MSRP",MSRPText.getText() );       
        d.append("BuyDownAmount",BuyDownText.getText() );
        d.append("MarkUP",MarkUpText.getText() );       
        d.append("SalesPrice",SaleText.getText() );
        d.append("WeightedItem",check1 );       
        d.append("WebItem",check2 );
        d.append("ExcludeSale", check3);       
        d.append("WIC",check4 );        
        d.append("HealthCard",check5 );       
        d.append("FoodStamp", check6);
        d.append("NonRevenueItem", check7);       
        d.append("NonDiscountable", check8);
        table.getItems().forEach(item -> d.append("Quantity",item.getValue()));
        costTable.getItems().forEach(item -> d.append("CostQuantity",item.getUnitName()));
        costTable.getItems().forEach(item -> d.append("Price",item.getValue()));       
        return d;
    }  
     @FXML
     public void handleNonStockCheckAction(ActionEvent a){
         if(NonStockCheck.isSelected()){
             table.setDisable(true);
         }
         else{
             table.setDisable(false);
         }
     }
     @FXML
     public void handleNonTaxCheckAction(ActionEvent a){
         if(NonTaxCheck.isSelected()){
             Tax1Text.setDisable(true);
             Tax2Text.setDisable(true);
             Tax3Text.setDisable(true);             
         }
         else{
             Tax1Text.setDisable(false);
             Tax2Text.setDisable(false);
             Tax3Text.setDisable(false);             
         }
     }
     @FXML
     public void handleQuantityCheckAtion(ActionEvent a){
         if(QuantityCheck.isSelected()){
             UnitCostText.setDisable(true);
             UnitPriceText.setDisable(true);
             MarginText.setDisable(true);
             MarkUpText.setDisable(true);
             MSRPText.setDisable(true);
             BuyDownText.setDisable(true);
             SaleText.setDisable(true);
             costTable.setDisable(true);
         }
         else{
             UnitCostText.setDisable(false);
             UnitPriceText.setDisable(false);
             MarginText.setDisable(false);
             MarkUpText.setDisable(false);
             MSRPText.setDisable(false);
             BuyDownText.setDisable(false);
             SaleText.setDisable(false);
             costTable.setDisable(false);
         }
     }
    @FXML
    public void handleBuyCheckAction(ActionEvent a)  {
        if(buy.isSelected()){
            CaseText.setDisable(false);
            UnitText.setDisable(false);
            sold.setDisable(false);
        }
        else{
            CaseText.setDisable(true);
            UnitText.setDisable(true);
            sold.setDisable(true);
        }
    }
    @FXML
    public void handleAutoCodeButtonAction(ActionEvent a){
        
        label.setText(df.format(generateNumber()));
        AutoButton.setDisable(true);
    }
    @FXML
    public void handleLocationButtonAction(ActionEvent a){
        try {
           Stage stg=new Stage();
           FXMLLoader fxml=new FXMLLoader(getClass().getResource("/fxml/SelectLocation.fxml"));
           Parent root=(Parent)fxml.load();
           stg.setScene(new Scene(root));
           stg.setTitle("Select Location");
           stg.show();           
       } catch (IOException ex) {
           Logger.getLogger(SelectDeptController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    @FXML
    public void handleBrandButtonAction(ActionEvent a){
        try {
           Stage stg=new Stage();
           FXMLLoader fxml=new FXMLLoader(getClass().getResource("/fxml/SelectBrand.fxml"));
           Parent root=(Parent)fxml.load();
           stg.setScene(new Scene(root));
           stg.setTitle("Select Brand");
           stg.show();           
       } catch (IOException ex) {
           Logger.getLogger(SelectDeptController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    @FXML
    public void handleAddUPCButtonAction(ActionEvent a){
         try {
           Stage stg=new Stage();
           FXMLLoader fxml=new FXMLLoader(getClass().getResource("/fxml/NumericKeyPad.fxml"));
           Parent root=(Parent)fxml.load();
           stg.setScene(new Scene(root));
           stg.setTitle("Numeric Keypad");
           stg.show();           
       } catch (IOException ex) {
           Logger.getLogger(SelectDeptController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    @FXML
   public void handleAdvanceButtonAction(ActionEvent a){
      
       try {
           Stage stg=new Stage();
           FXMLLoader fxml=new FXMLLoader(getClass().getResource("/fxml/AdvanceItemList.fxml"));
           Parent root=(Parent)fxml.load();
           stg.setScene(new Scene(root));
           stg.setTitle("Advance ItemList");
           stg.show();
           
       } catch (IOException ex) {
           Logger.getLogger(SelectDeptController.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
  
   @FXML
   public void handleDeptButtonAction(ActionEvent A){
         try {
           Stage stg=new Stage();
           FXMLLoader fxml=new FXMLLoader(getClass().getResource("/fxml/SelectDept.fxml"));
           Parent root=(Parent)fxml.load();
           stg.setScene(new Scene(root));
           stg.setTitle("Select Department");
           stg.show();           
       } catch (IOException ex) {
           Logger.getLogger(SelectDeptController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
   @FXML
   public void handleSizeButtonAction(ActionEvent a){     
        try {
           Stage stg=new Stage();
           FXMLLoader fxml=new FXMLLoader(getClass().getResource("/fxml/SelectSize.fxml"));
           Parent root=(Parent)fxml.load();
           stg.setScene(new Scene(root));
           stg.setTitle("Select Size");
           stg.show();           
       } catch (IOException ex) {
           Logger.getLogger(SelectDeptController.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
   @FXML
   public void handlePackButtonAction(ActionEvent a){
       try {
           Stage stg=new Stage();
           FXMLLoader fxml=new FXMLLoader(getClass().getResource("/fxml/SelectPack.fxml"));
           Parent root=(Parent)fxml.load();
           stg.setScene(new Scene(root));
           stg.setTitle("Select Pack");
           stg.show();           
       } catch (IOException ex) {
           Logger.getLogger(SelectDeptController.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
   @FXML
   public void handleCatButtonAction(ActionEvent A){
       try {
           Stage stg=new Stage();
           FXMLLoader fxml=new FXMLLoader(getClass().getResource("/fxml/SelectCat.fxml"));
           Parent root=(Parent)fxml.load();
           stg.setScene(new Scene(root));
           stg.setTitle("Select Category");
           stg.show();
           
       } catch (IOException ex) {
           Logger.getLogger(SelectDeptController.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
   
   
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {               
     // quantitycol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Value"));
      /*worked facilitycol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("UnitName"));
      
     quantitycol.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
        table.setEditable(true);                
      table.setItems(data);*/
             // quantitycol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Value"));
         table.setEditable(true);

        Callback<TableColumn<Person, String>, TableCell<Person, String>> cellFactory = (TableColumn<Person, String> p) -> new EditCell3();

      facilitycol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("UnitName"));
      
    // quantitycol.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
        quantitycol.setCellValueFactory(new PropertyValueFactory<>("Qantity"));
        quantitycol.setCellFactory(cellFactory);
       /* quantitycol.setOnEditCommit((CellEditEvent<Person, String> t) -> {
            ((Person) t.getTableView().getItems().get(t.getTablePosition().getRow())).setValue(t.getNewValue());
        });*/
     table.setItems(data);
     /*table.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {

                if( e.getCode() == KeyCode.TAB) { // commit should be performed implicitly via focusedProperty, but isn't
                    table.getSelectionModel().selectNext();
                    e.consume();
                    return;
                }
                else if( e.getCode() == KeyCode.ENTER) { // commit should be performed implicitly via focusedProperty, but isn't
                    table.getSelectionModel().selectBelowCell();
                    e.consume();
                    return;
                }

                // switch to edit mode on keypress, but only if we aren't already in edit mode
                if( table.getEditingCell() == null) {
                    if( e.getCode().isLetterKey() || e.getCode().isDigitKey()) {  

                        TablePosition focusedCellPosition = table.getFocusModel().getFocusedCell();
                        table.edit(focusedCellPosition.getRow(), focusedCellPosition.getTableColumn());

                    }
                }

            }
        });*/
     

quantitycol.setOnEditCommit(new EventHandler<CellEditEvent<Person, String>>() {
            @Override
            public void handle(CellEditEvent<Person, String> t) {
                ((Person) t.getTableView().getItems().get(t.getTablePosition().getRow())).setValue(t.getNewValue());
               
            }
        });
        // single cell selection mode
        table.getSelectionModel().setCellSelectionEnabled(true);
        table.getSelectionModel().selectFirst();
    DeptCombo.valueProperty().addListener(new ChangeListener<String>() {
        @Override 
        public void changed(ObservableValue ov, String t, String t1) {
          System.out.println("Observation value is "+ov);
            System.out.println("Last selected is "+t);
            System.out.println("current selection "+t1);
            SelctedDept=t1;
         obj.put("Name",SelctedDept);
     MongoCursor<Document> cur = db.getCollection("DeptDetail").find(obj).iterator();
            try {
            while (cur.hasNext()) {                
                int rs=cur.next().getInteger("ID");
                System.out.println("first Selcted dept is "+SelctedDept+ " id is   "+rs);
                 ADept=rs;
            }
             
        } finally {
            cur.close();
        }   
      obj1.put("Dept_ID", ADept);
       cur = db.getCollection("CategoryDetail").find(obj1).iterator();
       CatCombo.getItems().clear();
            try {
            while (cur.hasNext()) {                
                String rs1=cur.next().getString("Name");
               System.out.println("Category for dept is "+SelctedDept+ " Category is   "+rs1);                 
                
               CatCombo.setValue(rs1);
                CatCombo.getItems().addAll(rs1);           
            }
             
        } finally {
            cur.close();
        }   
        }    
    });
   
      



        
     /*       String qry="select name from category_detail";
            String qry1="select name from dept_detail";
            String qry4="select name from size_detail";
            String qry5="select name from pack_detail";
            String qry6="select name from brand_detail";
            String qry7="select name from location_detail";
            java.sql.Connection conn=Connection.getConnect();            
            PreparedStatement pst2=conn.prepareStatement(qry);
            PreparedStatement pst1=conn.prepareStatement(qry1);
            PreparedStatement pst4=conn.prepareStatement(qry4);
            PreparedStatement pst5=conn.prepareStatement(qry5);
            PreparedStatement pst6=conn.prepareStatement(qry6);
            PreparedStatement pst7=conn.prepareStatement(qry7);
            ResultSet rs = pst2.executeQuery(qry);
            ResultSet rs1 = pst1.executeQuery(qry1);
            ResultSet rs4 = pst4.executeQuery(qry4);            
            ResultSet rs5 = pst5.executeQuery(qry5);            
            ResultSet rs6 = pst6.executeQuery(qry6);            
            ResultSet rs7 = pst7.executeQuery(qry7);            
            while(rs.next()){
              CatCombo.setValue(rs.getString("name"));
              CatCombo.getItems().addAll(rs.getString("name"));
            }            
           while(rs1.next()){
             DeptCombo.setValue(rs1.getString("name"));
             DeptCombo.getItems().addAll(rs1.getString("name"));
            }
           while(rs4.next()){
             SizeCombo.setValue(rs4.getString("name"));
             SizeCombo.getItems().addAll(rs4.getString("name"));
            }
           while(rs5.next()){
             PackCombo.setValue(rs5.getString("name"));
             PackCombo.getItems().addAll(rs5.getString("name"));
            }
           while(rs6.next()){
             BrandCombo.setValue(rs6.getString("name"));
             BrandCombo.getItems().addAll(rs6.getString("name"));
            }
           while(rs7.next()){
             LocationCombo.setValue(rs7.getString("name"));
             LocationCombo.getItems().addAll(rs7.getString("name"));
            }*/
            MongoCursor<Document> cursor4 = db.getCollection("CategoryDetail").find().iterator();
            try {
            while (cursor4.hasNext()) {                
                String rs=cursor4.next().getString("Name");
                CatCombo.setValue(rs);
                CatCombo.getItems().addAll(rs);                
            }
        } finally {
            cursor4.close();
        }   
            cursor4 =  db.getCollection("DeptDetail").find().iterator();
            try {
            while (cursor4.hasNext()) {                
                String rs=cursor4.next().getString("Name");
                DeptCombo.setValue(rs);
                DeptCombo.getItems().addAll(rs);                
            }
        } finally {
            cursor4.close();
        }   
            cursor4 =  db.getCollection("LocationDetail").find().iterator();
            try {
            while (cursor4.hasNext()) {                
                String rs=cursor4.next().getString("Name");
                LocationCombo.setValue(rs);
                LocationCombo.getItems().addAll(rs);                
            }
        } finally {
            cursor4.close();
        }   
            cursor4 =  db.getCollection("BrandDetail").find().iterator();
            try {
            while (cursor4.hasNext()) {                
                String rs=cursor4.next().getString("Name");
                BrandCombo.setValue(rs);
                BrandCombo.getItems().addAll(rs);                
            }
        } finally {
            cursor4.close();
        }   
            cursor4 =  db.getCollection("PackDetail").find().iterator();
            try {
            while (cursor4.hasNext()) {                
                String rs=cursor4.next().getString("Name");
                PackCombo.setValue(rs);
                PackCombo.getItems().addAll(rs);                
            }
        } finally {
            cursor4.close();
        }   
           cursor4 =  db.getCollection("SizeDetail").find().iterator();
            try {
            while (cursor4.hasNext()) {                
                String rs=cursor4.next().getString("Name");
                SizeCombo.setValue(rs);
                SizeCombo.getItems().addAll(rs);                
            }
        } finally {
            cursor4.close();
        }   
    } 
       public class EditCell3<T, E> extends TableCell<T, String> {

private TextField textField;

@Override
public void startEdit() {
    if (!isEmpty()) {
        super.startEdit();
        createTextField();
        setText(null);
        setGraphic(textField);
        textField.selectAll();
    }
}

@Override
public void cancelEdit() {
    super.cancelEdit();
    
    setText((String) getItem());
    setGraphic(null);
}

@Override
public void updateItem(String item, boolean empty) {
super.updateItem(item, empty);

if (empty) {
    setText(null);
    setGraphic(null);
    } else {
        if (isEditing()) {
            if (textField != null) {
            textField.setText(getString());
            }
            setText(null);
            setGraphic(textField);
        } else {
            setText(getString());
            setGraphic(null);
        }
    }
}

private void createTextField() {
    textField = new TextField(getString());
    textField.setOnAction(evt -> { // enable ENTER commit
        commitEdit(textField.getText());
    });

    textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
    
    ChangeListener<? super Boolean> changeListener = (observable, oldSelection, newSelection) ->
    {
        if (! newSelection) {
            commitEdit(textField.getText());
        }
    };
    textField.focusedProperty().addListener(changeListener);
    
    textField.setOnKeyPressed((ke) -> {
        if (ke.getCode().equals(KeyCode.ESCAPE)) {
            textField.focusedProperty().removeListener(changeListener);
            cancelEdit();
        }
    });
}

private String getString() {
    return getItem() == null ? "" : getItem().toString();
}


@Override
public void commitEdit(String item) {

if (isEditing()) {
    super.commitEdit(item);
} else {
    final TableView table = getTableView();
    if (table != null) {
        TablePosition position = new TablePosition(getTableView(), getTableRow().getIndex(), getTableColumn());
        CellEditEvent editEvent = new CellEditEvent(table, position, TableColumn.editCommitEvent(), item);
        Event.fireEvent(getTableColumn(), editEvent);
    }
        updateItem(item, false);
        if (table != null) {
            table.edit(-1, null);
        }

    }
}

}
 

     class EditingCell extends TableCell<XYChart.Data, Number> {
          
        private TextField textField;
          
        public EditingCell() {}
          
        @Override
        public void startEdit() {
              
            super.startEdit();
              
            if (textField == null) {
                createTextField();
            }
              
            setGraphic(textField);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            textField.selectAll();
        }
          
        @Override
        public void cancelEdit() {
            super.cancelEdit();
              
            setText(String.valueOf(getItem()));
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
          
        @Override
        public void updateItem(Number item, boolean empty) {
            super.updateItem(item, empty);
              
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setGraphic(textField);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                } else {
                    setText(getString());
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                }
            }
        }
          
        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                  
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(Integer.parseInt(textField.getText()));
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });
        }
          
        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
     }
 
     public static class Person {

        private final SimpleStringProperty UnitName;
        private final SimpleStringProperty Value;
        
         
        
        
        private Person(String uName, String val ) {
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
    


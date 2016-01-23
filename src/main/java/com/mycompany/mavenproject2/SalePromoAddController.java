
package com.mycompany.mavenproject2;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mycompany.mavenproject2.Sys1Controller.stage;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.LocalDateStringConverter;
import org.bson.Document;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
public class SalePromoAddController extends Thread implements Initializable{
    @FXML TextField SKUText,CostText,ItemNameText,PriceText,FormulaQuantity,DisLevelText,filterField;
    @FXML TextField QTYText,SaleText,MinPurText,LimitText,PromoNameText;
    @FXML ComboBox PromoTarget,TargetCombo,CountType,DiscountType,CustTarget;
    @FXML Button SelectFormulaButton;
    @FXML Tab ListTab,FormulaTab;
    @FXML TabPane TabPane;
    @FXML TableView<Person> FormulaTable;
    @FXML TableColumn<Person,String> discol ;
    @FXML TableColumn<Person,Integer> valcol ;
    @FXML TableColumn<Person,String> qtypecol ;
    @FXML TableColumn<Person,String> quantity ;
    @FXML TableView<ListPerson> ListItemTable;
    @FXML TableColumn<ListPerson,String> skucol,namecol,sizecol,packcol,qtycol,salecol,limitcol,purcol,costcol1,pricecol1; 
    @FXML TableView<ListAllItem> ItemTable;
    @FXML private TableColumn<ListAllItem, Boolean> sel;
    @FXML TableColumn<ListAllItem,String> skucol1,name,size,pack,price;
    @FXML DatePicker StartDate,EndDate;
    @FXML CheckBox LimitPromoCheck,CostCheck,MixMatchCheck;
    int count;
    String selectedItem;
    BasicDBObject ref;
    MongoClient client = new MongoClient();
    MongoDatabase  db = client.getDatabase("FinalDemo");
    MongoCollection<Document> col=db.getCollection("SalePromoDetail");
    public static ObservableList<Person> data= FXCollections.observableArrayList();
    public static ObservableList<Person> data1= FXCollections.observableArrayList();
    public static ObservableList<Person> SaveFormula= FXCollections.observableArrayList();
    public static ObservableList<ListPerson> data2= FXCollections.observableArrayList();    
    public static ObservableList<ListPerson> data4= FXCollections.observableArrayList();
    public static ObservableList<ListPerson> data8= FXCollections.observableArrayList();
    public static ObservableList<ListAllItem> ItemData= FXCollections.observableArrayList();
    public static ObservableList<ListPerson> obv= FXCollections.observableArrayList();
    public static ArrayList<String> arr=new ArrayList<String>();
    public static SalePromoAddController s=new SalePromoAddController();
    BasicDBObject obj=new BasicDBObject();
    BasicDBObject sort1,sort2,condition;
    static int EID=1,UID=1,PID=0;
    int GlobalFlag=0;
    @FXML public void handleListRemoveButtonAction(ActionEvent a){
        org.controlsfx.control.action.Action response =  Dialogs.create()
        .owner(stage)
        .title("Confirm Actions")        
        .message("Item will get Removed from Table")
        .actions(Dialog.ACTION_OK, Dialog.ACTION_CANCEL)
        .showConfirm();      
        if (response == Dialog.ACTION_OK) {
            List items =  new ArrayList (ListItemTable.getSelectionModel().getSelectedItems());  
            System.out.println("Selected item is "+items.toString());
            data4.removeAll(items);         
            data8.removeAll(items);
            arr.removeAll(items);
            ListItemTable.getSelectionModel().clearSelection();                              
        }
        else{
            System.out.println("cANCEL");
        }
    }
    @FXML public void handleAddItemButtonAction(ActionEvent ak){
        if(GlobalFlag==1){
            for(String item:arr){ //Problem diffi         
                obj.put("SKU",item);
                MongoCursor<Document> cur=db.getCollection("ItemDetail").find(obj).iterator();
                while(cur.hasNext()){   
                    Document p=cur.next();
                    data8.add(new ListPerson(p.getString("SKU"),p.getString("ItemName"),p.getString("Size_Name"),p.getString("Pack_Name"),QTYText.getText() , SaleText.getText(),p.getString("UnitCost"), MinPurText.getText(), LimitText.getText()));
                }
            }      
         ListItemTable.getItems().addAll(data8);
        }
            
        else{
            for(String item:arr){ //Problem diffi         
                obj.put("SKU",item);
                MongoCursor<Document> cur=db.getCollection("ItemDetail").find(obj).iterator();
                while(cur.hasNext()){   
                    Document p=cur.next();
                    data4.add(new ListPerson(p.getString("SKU"),p.getString("ItemName"),p.getString("Size_Name"),p.getString("Pack_Name"),QTYText.getText() , SaleText.getText(),p.getString("UnitCost"), MinPurText.getText(), LimitText.getText()));
                }
            }       
            ListItemTable.setItems(data4);        
        }       
    }
    @FXML public void handleSavePromoButtonAction(ActionEvent ag){
        InsertMongo();
        CostText.clear();
        DisLevelText.clear();
        FormulaQuantity.clear();
        FormulaQuantity.clear();
        ItemNameText.clear();
        LimitText.clear();
        PromoNameText.clear();
    }
    private void InsertMongo(){
        if(GlobalFlag==1){
            System.out.println("UpdatrMOngo()");
                 UpdateMongo();                    
            }
         else{
        count= (int) col.count();
        if(count==0){
            if(PromoTarget.getValue().toString().equalsIgnoreCase("Custom List")){                 
                Document d=createListData();
                col .insertOne(d);                       
            }
            else{
                Document d=createSeedData();
                col.insertOne(d);                  
            }
        }
        else{        
            sort1=new BasicDBObject();        
            MongoCursor<Document> cursor = col.find().sort(sort1).limit(1).skip((int)count-1).iterator();        
            try {
                while (cursor.hasNext()) {                
                    EID=cursor.next().getInteger("ID");                    
                    EID++;                    
                }
             } finally {
                cursor.close();
            }
               if(PromoTarget.getValue().toString().equalsIgnoreCase("Custom List")){                 
                Document d=createListData();
                col .insertOne(d);                       
            }
            else{
                Document d=createSeedData();
                col.insertOne(d);                  
            }
            }        
        }
    }
    public void EditButton(int para1,String para2,String para3,String para4,String para5){
        GlobalFlag=1;        
        PromoNameText.setText(para2);       
        sort2=new BasicDBObject();
        sort2.put("ID", para1);
	MongoCursor<Document> cursor = col.find(sort2).iterator();      
         while (cursor.hasNext()) {       
            Document dr=cursor.next();
            if(dr.getString("PromoTarget").equalsIgnoreCase("Custom List")){
                TabPane.getSelectionModel().selectFirst();
                 ListTab.disableProperty().setValue(Boolean.FALSE);
                QTYText.setText(dr.getString("Quantity"));
                SaleText.setText(dr.getString("Price"));
                MinPurText.setText(dr.getString("MinimumPAmount"));
                LimitText.setText(dr.getString("Limit"));
                CustTarget.setValue(dr.getString("TargetCust"));
                PromoTarget.setValue(dr.getString("PromoTarget"));
                TargetCombo.setValue(dr.getString("SubPromoTarget"));
         //remove       LimitText.setText(dr.getString("Limit"));
                if(dr.getString("MixMatchPromo").equalsIgnoreCase("Yes")){
                    MixMatchCheck.setSelected(true);                    
                }
                else{
                    MixMatchCheck.setSelected(false);
                }
                if(dr.getString("AllowBelowCost").equalsIgnoreCase("Yes")){
                    CostCheck.setSelected(true);                    
                }
                else{
                    CostCheck.setSelected(false);
                }
                if(dr.getString("MixMatchPromo").equalsIgnoreCase("Yes")){
                    MixMatchCheck.setSelected(true);                    
                }
                else{
                    MixMatchCheck.setSelected(false);
                }                      
            Document d=(Document)dr.get("Items");
            count=d.keySet().size();
            MongoCursor<Document> cursor1 = db.getCollection("ItemDetail").find().iterator();
            while(cursor1.hasNext()){
                Document g1=cursor1.next();                
                if(d.getString(g1.getString("SKU"))!=null){                                    
                    obv.add(new ListPerson(g1.getString("SKU"),g1.getString("ItemName"),g1.getString("Size_Name"),g1.getString("Pack_Name"), QTYText.getText(),SaleText.getText(),g1.getString("UnitPrice"),MinPurText.getText(),LimitText.getText()));                                            
                    ListItemTable.setItems(obv);
                }
            PID=para1;    
         
            }
        }           
     }       
 }    
    
    private Document createSeedData(){
        System.out.println("creayeSeed date");
        Document p=new Document();   
        p.append("ID",EID);
        p.append("Name",PromoNameText.getText());   
        LocalDate localDate = StartDate.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        System.out.println("date is "+date);
        p.append("StartDate", date.toString());
        LocalDate localDate1 = EndDate.getValue();
        Instant instant1 = Instant.from(localDate1.atStartOfDay(ZoneId.systemDefault()));
        Date EndDate1 = Date.from(instant1);
        System.out.println("Added "+EndDate1);
        p.append("EndDate", EndDate1);        
        p.append("TargetCust", CustTarget.getValue().toString());
        p.append("PromoTarget",PromoTarget.getValue().toString());
        if(!PromoTarget.getValue().toString().equalsIgnoreCase("Single Item")){
        p.append("SubPromoTarget", TargetCombo.getValue().toString());}
        p.append("LimitedPromo",LimitPromoCheck.isSelected()?"Yes":"No");
        p.append("AllowBelowCost", CostCheck.isSelected()?"Yes":"No");
        p.append("MixMatchPromo", MixMatchCheck.isSelected()?"Yes":"No"); //diffi Item
        p.append("Items", FormulaItem());        
        return p;        
    }
    private Document FormulaItem(){
        Document y=new Document();
        for(int i=0;i<data1.size();i++){
            y.append("Count Type", data1.get(i).getDiscountType());
            y.append("Value", data1.get(i).getValue());
            y.append("Quntity Type", data1.get(i).getQuantityType());
            y.append("Quantity", data1.get(i).getQuantity());
        }        
        return  y;
    }    
    private Document createListData(){
        Document p=new Document(); 
        p.append("ID",EID);
        p.append("Name",PromoNameText.getText());  
        LocalDate localDate = StartDate.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        p.append("StartDate", date);
        p.append("StartDate", date.toString());
        LocalDate localDate1 = EndDate.getValue();
        Instant instant1 = Instant.from(localDate1.atStartOfDay(ZoneId.systemDefault()));
        Date EndDate1 = Date.from(instant1);
        p.append("EndDate", EndDate1);
        p.append("TargetCust", CustTarget.getValue().toString());
        p.append("PromoTarget","Custom List");
        p.append("LimitedPromo",LimitPromoCheck.isSelected()?"Yes":"No");
        p.append("AllowBelowCost", CostCheck.isSelected()?"Yes":"No");
        p.append("MixMatchPromo", MixMatchCheck.isSelected()?"Yes":"No");
        p.append("Items",SKU());
        p.append("Quantity", QTYText.getText());
        p.append("Price", SaleText.getText().toString());
        p.append("MinimumPAmount", MinPurText.getText().toString());
        p.append("Limit",LimitText.getText());
        return p;        
    }
    private Document SKU(){
        Document r=new Document();
         for(String item:arr){ 
              r.append(item, item); //diffi
            }
         return r;
    }
    private Document UpdateSeedData(){
        Document d1=new Document();               
        d1.append("ID",  PID);    
        d1.append("Name",PromoNameText.getText());      
        d1.append("StartDate", StartDate.getValue().toString());
        d1.append("EndDate", EndDate.getValue().toString());
        d1.append("TargetCust", CustTarget.getValue().toString());
        d1.append("PromoTarget","Custom List");
        d1.append("LimitedPromo",LimitPromoCheck.isSelected()?"Yes":"No");
        d1.append("AllowBelowCost", CostCheck.isSelected()?"Yes":"No");
        d1.append("MixMatchPromo", MixMatchCheck.isSelected()?"Yes":"No");
        d1.append("Items",SKU());
        d1.append("Quantity", QTYText.getText());
        d1.append("Price", SaleText.getText().toString());
        d1.append("MinimumPAmount", MinPurText.getText().toString());
        return d1;
    }
    public void UpdateMongo(){             
        System.out.println("Updated Selected _id is "+PID);
        col.updateOne(new Document("ID", PID),
        new Document("$set", UpdateSeedData()));           
    }
    @FXML public void handleAddSaleFormulaButtonAction(ActionEvent a){
        data1.add(new Person(DiscountType.getValue().toString(),Integer.parseInt(DisLevelText.getText()) , CountType.getValue().toString(), FormulaQuantity.getText()));
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
   private void initFilter() {       
         
         filterField.textProperty().addListener(new InvalidationListener() {         
             @Override
            public void invalidated(javafx.beans.Observable observable) {
                if(filterField.textProperty().get().isEmpty()) {
                    ListItemTable.setItems(data2);
                    return;
                }
                ObservableList<ListPerson> tableItems = FXCollections.observableArrayList();
                ObservableList<TableColumn<ListPerson, ?>> cols = ListItemTable.getColumns();
                for(int i=0; i<data.size(); i++) {                   
                    for(int j=0; j<cols.size(); j++) {
                        TableColumn col = cols.get(j);
                        String cellValue = col.getCellData(data.get(i)).toString();
                        cellValue = cellValue.toLowerCase();
                        if(cellValue.contains(filterField.textProperty().get().toLowerCase())) {
                            tableItems.add(data2.get(i));
                            break;
                        }                        
                    }
                }
                ListItemTable.setItems(tableItems);
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        discol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("DiscountType"));
        valcol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,Integer>("Value"));
        qtypecol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("QuantityType"));
        quantity.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Quantity"));
        skucol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<ListPerson,String>("SKU"));
        namecol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<ListPerson,String>("Name"));
        sizecol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<ListPerson,String>("Size"));
        packcol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<ListPerson,String>("Pack"));
        qtycol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<ListPerson,String>("QTY"));
        salecol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<ListPerson,String>("Sale"));
        pricecol1.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<ListPerson,String>("Price"));        
        purcol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<ListPerson,String>("MinPurchase"));
        limitcol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<ListPerson,String>("LimitQT"));
        skucol1.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<ListAllItem,String>("SKU"));
        name.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<ListAllItem,String>("Name"));
        size.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<ListAllItem,String>("Size"));  
        pack.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<ListAllItem,String>("Pack"));      
        price.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<ListAllItem,String>("Price"));
        sel.setCellValueFactory(new PropertyValueFactory("Select"));
        FormulaTable.setItems(data1);
        SKUText.setDisable(true);
        ItemNameText.setDisable(true);
        PriceText.setDisable(true);
        CostText.setDisable(true);
        SelectFormulaButton.setDisable(true);
        TargetCombo.setDisable(true);
        TabPane.getSelectionModel().selectLast();        
      // System.out.println("FX Vesion "+com.sun.javafx.runtime.VersionInfo.getRuntimeVersion());
        ItemData.removeAll(ItemData);
        ItemTable.getItems().removeAll(ItemData);
        MongoCursor<Document> cur1=db.getCollection("ItemDetail").find().iterator();
         while(cur1.hasNext()){
             Document p=cur1.next();             
            try {             
                ItemData.add(new ListAllItem(false, p.getString("SKU"), p.getString("ItemName"), p.getString("Size_Name"), p.getString("Pack_Name"), p.getString("UnitCost")));
            } catch (IOException ex) {
                Logger.getLogger(SalePromoAddController.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
         ItemTable.setItems(ItemData);
        sel.setCellFactory(new Callback<TableColumn<ListAllItem, Boolean>, TableCell<ListAllItem, Boolean>>() {        
            public TableCell<ListAllItem, Boolean> call(TableColumn<ListAllItem, Boolean> p) { 
                return new CheckBoxTableCell<ListAllItem, Boolean>(); 
            } 
        });
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
});   
         filterField.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable,
                  String oldValue, String newValue) {                    
                //updateFilteredData();
                    initFilter();
            }
        });      
         
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
     public static class ListAllItem {
        private final BooleanProperty sel;
        private final SimpleStringProperty SKU;
        private final SimpleStringProperty Name;
        private final SimpleStringProperty Size;
        private final SimpleStringProperty Pack;
        private final SimpleStringProperty Price;
       /* FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SalePromoAdd.fxml"));
            Parent root;
            SalePromoAddController controller=fxmlLoader.<SalePromoAddController>getController*/
         
        
        
        private ListAllItem(Boolean b,String SKU, String da,String Size,String Pack,String Price) throws IOException {
          //  this.root = (Parent) fxmlLoader.load();
            this.sel=new SimpleBooleanProperty(b);
            this.SKU = new SimpleStringProperty(SKU);
            this.Name = new SimpleStringProperty(da);         
            this.Size = new SimpleStringProperty(Size);
            this.Pack = new SimpleStringProperty(Pack);
            this.Price = new SimpleStringProperty(Price);
            
            sel.addListener(new ChangeListener<Boolean>() { 
                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                     if(t1.toString().equalsIgnoreCase("false")) {                        
                        System.out.println("Removed "+getName() );
                        arr.remove(getSKU());                        
                    }
                    else if(t1.toString().equalsIgnoreCase("true")){                        
                         System.out.println("Added "+getName() );
                         arr.add(getSKU());
                    }
                    else{
                        System.out.println("NOTHING");
                    }
                }
 
            }); 
        }    
      
        public void setSelect(){
           sel.get();
        }
        public Boolean getSelect(){
            return sel.get();
        }
       
        public String getSKU() {
            return SKU.get();
        }

        public void setSKU(String uName) {
            SKU.set(uName);
        }

        public String getName() {
            return Name.get();
        }

        public void setName(String val) {
            Name.set(val);
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
         public BooleanProperty SelectProperty() {
              return sel;
        }
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
  public static class ListPerson {
        private final SimpleStringProperty SKU;
        private final SimpleStringProperty Name;
        private final SimpleStringProperty Size;
        private final SimpleStringProperty Pack;
        private final SimpleStringProperty QTY;
        private final SimpleStringProperty Sale;
        private final SimpleStringProperty Price;
        private final SimpleStringProperty MinPurchase;
        private final SimpleStringProperty LimitQ;
        private ListPerson(String uName, String val, String type,String nc,String a,String b,String x,String y,String z) {
            this.SKU = new SimpleStringProperty(uName);
            this.Name = new SimpleStringProperty(val);
            this.Size = new SimpleStringProperty(type);
            this.Pack = new SimpleStringProperty(nc);
            this.QTY = new SimpleStringProperty(a);
            this.Sale = new SimpleStringProperty(b);
            this.Price = new SimpleStringProperty(x);
            this.MinPurchase = new SimpleStringProperty(y);
            this.LimitQ = new SimpleStringProperty(z);
            
        }

        public String getSKU() {
            return SKU.get();
        }

        public void setSKU(String uName) {
            SKU.set(uName);
        }

        public String getName() {
            return Name.get();
        }

        public void setName(String val) {
            Name.set(val);
        }

        public String getSize() {
            return Size.get();
        }

        public void setSize(String type) {
            Size.set(type);
        }
        
        public String getPack(){
            return Pack.get();
        }
        
        public void setPack(String nc){
            Pack.set(nc);
        }
         public String getQTY(){
            return QTY.get();
        }
        
        public void setQTY(String nc){
            QTY.set(nc);
        }
        public String getSale(){
            return Sale.get();
        }
        
        public void setSale(String nc){
            Sale.set(nc);
        }
        public String getPrice(){
            return Price.get();
        }
        
        public void setPrice(String nc){
            Price.set(nc);
        }
        public String getMinPurchase(){
            return MinPurchase.get();
        }
        
        public void setMinPurchase(String nc){
            MinPurchase.set(nc);
        }
        public String getLimitQT(){
            return LimitQ.get();
        }
        
        public void setLimitQT(String nc){
            LimitQ.set(nc);
        }
  }
}


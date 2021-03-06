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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.bson.Document;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.css.PseudoClass;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
public class SalesPromotionController implements Initializable {
    @FXML TableView<Person> SalesTable;
    @FXML TableColumn<Person,Integer> idcol;
    @FXML TableColumn<Person,String> namecol,sdatecol,edatecol,targetcol,couponcol ;
    MongoClient client = new MongoClient();
    MongoDatabase  db = client.getDatabase("FinalDemo");
    MongoCollection<Document> col=db.getCollection("SalePromoDetail");
    public static ObservableList<Person> data= FXCollections.observableArrayList();
    public static int cnt=0;
    @FXML public void handleDeleteButtonAction(ActionEvent al){
        
        List items =  new ArrayList (SalesTable.getSelectionModel().getSelectedItems());  
        Person person = SalesTable.getSelectionModel().getSelectedItem();
        System.out.println(person.getID());       
        db.getCollection("SalePromoDetail").deleteOne(eq("ID",person.getID()));
        data.removeAll(items);
        SalesTable.getSelectionModel().clearSelection();
    }
    @FXML
    public void handleAddButtonAction(ActionEvent ev)
    {         
         try {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SalePromoAdd.fxml"));                
                Parent root = (Parent) fxmlLoader.load();                
                stage.setScene(new Scene(root));  
                stage.setTitle("Add Sales Promotion");
                stage.show();
        } catch(Exception e) {
           e.printStackTrace();
          } 
    }
    @FXML public void handleEditButtonAction(ActionEvent ak){
        Person person = SalesTable.getSelectionModel().getSelectedItem();
         System.out.println("id is "+person.getID());   
        
         try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SalePromoAdd.fxml"));
            Parent root44 = (Parent) fxmlLoader.load();
            SalePromoAddController controller=fxmlLoader.<SalePromoAddController>getController();
            stage.setScene(new Scene(root44));
            stage.setTitle("Edit Sale Promo");
            controller.EditButton(person.getID(),person.getSaleName(),person.getStartDate(),person.getEndDate(),person.getTarget());
            stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(Sys1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     @FXML     
     public void handleSelectButtonAction(ActionEvent ev)
    {         
         try {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AllItem.fxml"));
                Parent root = (Parent) fxmlLoader.load();                
                stage.setScene(new Scene(root));  
                stage.setTitle("ItemList");
                stage.show();
        } catch(Exception e) {
           e.printStackTrace();
          } 
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idcol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,Integer>("ID"));
        namecol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("SaleName"));
        sdatecol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("StartDate"));
        edatecol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("EndDate"));
        targetcol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Target"));
        couponcol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Coupon"));
        data.removeAll(data);
        SalesTable.getItems().removeAll(data);
        MongoCursor<Document> cur=col.find().iterator();
        while(cur.hasNext()){
            Document d=cur.next();
            data.add(new Person(d.getInteger("ID"),d.getString("Name"),d.getString("StartDate"),d.getDate("EndDate").toString(),d.getString("TargetCust"),"c"));
        }
        cur.close();
        final ObservableList<Integer> highlightRows = FXCollections.observableArrayList();
        SalesTable.setItems(data);
        SalesTable.getItems().forEach(item -> Date(item));
        
    }    
    private void Date(Person item) {    
        PseudoClass up = PseudoClass.getPseudoClass("up");
    PseudoClass down = PseudoClass.getPseudoClass("down");
        try {
            Date now=new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
            Date date = simpleDateFormat.parse(item.getEndDate());   
            cnt++;
            if(now.before(date)){
                System.out.println("After ");     
                //diffi
                TableRow<Person> row = new TableRow<>();
                row.pseudoClassStateChanged(up, true);          
          
     

            }
            else if(now==date)
                System.out.println("equal");
            else
                System.out.println("BEfore");
        } catch (ParseException ex) {
            Logger.getLogger(SalesPromotionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public static class Person {
        private final SimpleIntegerProperty ID;
        private final SimpleStringProperty SaleName;
        private final SimpleStringProperty StartDate;
        private final SimpleStringProperty EndDate;
        private final SimpleStringProperty Target;
        private final SimpleStringProperty Coupon;
        private Person(int id,String uName, String val, String type,String nc,String cpn) {
            this.ID = new SimpleIntegerProperty(id);
            this.SaleName = new SimpleStringProperty(uName);
            this.StartDate = new SimpleStringProperty(val);
            this.EndDate = new SimpleStringProperty(type);
            this.Target = new SimpleStringProperty(nc);
            this.Coupon = new SimpleStringProperty(cpn);
        }

        public int getID() {
            return ID.get();
        }

        public void setDiscountType(int uName) {
            ID.set(uName);
        }

        public String getSaleName() {
            return SaleName.get();
        }

        public void setSaleName(String val) {
            SaleName.set(val);
        }

        public String getStartDate() {
            return StartDate.get();
        }

        public void setStartDate(String type) {
            StartDate.set(type);
        }
        
        public String getEndDate(){
            return EndDate.get();
        }
        
        public void setQuantity(String nc){
            EndDate.set(nc);
        }
        public String getTarget() {
            return Target.get();
        }

        public void setTarget(String type) {
            Target.set(type);
        }
        
        public String getCoupon(){
            return Coupon.get();
        }
        
        public void setCoupon(String nc){
            Coupon.set(nc);
        }
    }
}

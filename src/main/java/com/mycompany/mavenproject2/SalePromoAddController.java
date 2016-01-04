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
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bson.Document;

/**
 * FXML Controller class
 *
 * @author Third Ev
 */
public class SalePromoAddController extends Thread implements Initializable{
    @FXML TextField SKUText,CostText,ItemNameText,PriceText;
    String selectedItem;
    BasicDBObject ref;
    MongoClient client = new MongoClient();
    MongoDatabase  db = client.getDatabase("FinalDemo");
    @FXML
    public void handleSeelectButtonAction(ActionEvent af){
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
    }    
  
    
}

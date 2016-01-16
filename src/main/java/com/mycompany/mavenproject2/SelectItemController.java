/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import com.mycompany.mavenproject2.ItemListController;
import com.mycompany.mavenproject2.SalePromoAddController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.bson.Document;

/**
 * FXML Controller class
 *
 * @author Third Ev
 */
public class SelectItemController implements Initializable {
     @FXML Button close;
     @FXML ListView ItemList;
     MongoClient client=new MongoClient();
     MongoDatabase db=client.getDatabase("FinalDemo");
     MongoCollection<Document> col=db.getCollection("ItemDetail");
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
    public void handleSelectButtonAction(ActionEvent as){
        try {
            String s= ItemList.getSelectionModel().getSelectedItem().toString();
            /*Stage stg=new Stage();
            FXMLLoader fxml1=new FXMLLoader(getClass().getResource("/fxml/SalePromoAdd.fxml"));
            Parent root1=(Parent)fxml1.load();
            SalePromoAddController controller=fxml1.<SalePromoAddController>getController();
            stg.setScene(new Scene(root1));
            controller.setSelectedItem();*/
            Stage stage3=(Stage) close.getScene().getWindow();
            /*Thread t1=new Thread();
            t1.start();
            t1.join();*/
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
          String sel=ItemList.getSelectionModel().getSelectedItem().toString();     
        ItemList.getItems().removeAll(sel); 
        col.deleteOne(eq("Name", sel));            
        ItemList.getSelectionModel().clearSelection();
        /*}
else{
    System.out.println("You click cancel confirm");
}*/
   
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MongoCursor<Document> cursor4 = db.getCollection("ItemDetail").find().iterator();
            try {
            while (cursor4.hasNext()) {                
                String rs=cursor4.next().getString("ItemName");
                 ItemList.getItems().addAll(rs);
            }
        } finally {
            cursor4.close();
        }
    }    
    
}

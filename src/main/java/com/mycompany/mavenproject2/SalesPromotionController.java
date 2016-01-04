/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Third Ev
 */
public class SalesPromotionController implements Initializable {
    @FXML
    public void handleAddButtonAction(ActionEvent ev)
    {
         System.out.println("You click Add Sales Promo");
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
     @FXML     
     public void handleSelectButtonAction(ActionEvent ev)
    {
         System.out.println("You clicked Select Button");
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
        // TODO
    }    
    
}

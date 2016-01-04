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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Third Ev
 */
public class FXMLDocumentController implements Initializable {      
   @FXML ComboBox PackCombo;
    @FXML
    private void handleSystemButtonAction(ActionEvent event) {
        System.out.println("You clicked System button!");
        try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Sys1.fxml"));
                Parent root4 = (Parent) fxmlLoader.load();
                
                Stage stage = new Stage();
                 Scene scene = new Scene(root4);        
                stage.setScene(scene);
                stage.setTitle("System Setting");
                scene.getStylesheets().add("/styles/SysSet.css");
                stage.show();
        } catch(Exception e) {
           e.printStackTrace();
          }      
    }
    @FXML
    private void handleShortcutButtonAction(ActionEvent eve){
        System.out.println("You clicked Shortcut button!");
       try {
           Stage stage = new Stage();
           FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Shortcut.fxml"));
           Parent root2 = (Parent) fxmlLoader.load();                
           stage.setScene(new Scene(root2));  
           stage.setTitle("Shortcut Button Setting");
           stage.show();
        
        } catch(Exception e) {
           e.printStackTrace();
          }  
    }
    @FXML
    private void handleCurrencyButtonAction(ActionEvent eve){
        System.out.println("You clicked Currency button!");
       try {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Currency3.fxml"));
                Parent root3 = (Parent) fxmlLoader.load();                
                stage.setScene(new Scene(root3)); 
                stage.setTitle("Currency Setting");
                stage.show();
        } catch(Exception e) {
           e.printStackTrace();
          }  
    }
     @FXML
    private void handleSecurityButtonAction(ActionEvent eve){
      
     try {
         System.out.println("You click on security setting buttton");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Security4.fxml"));
                Parent root4 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root4));  
                stage.setTitle("Security Setting");
                stage.show();
        } catch(Exception e) {
           e.printStackTrace();
          }  
    }
    @FXML
    private void handleItemListButtonAction(ActionEvent eve){
      
     try {
         System.out.println("You click on ItemList buttton");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ItemList.fxml"));
                Parent root5 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root5));
                
                stage.setTitle("Itemlist");
                stage.show();
        } catch(Exception e) {
           e.printStackTrace();
          }  
    }
    @FXML
    private void handleItemGroupButtonAction(ActionEvent eve){
      
     try {
         System.out.println("You click on ItemGroup6 buttton");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ItemGroup6.fxml"));
                Parent root6 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root6));  
                stage.setTitle("Item Group");
                stage.show();
        } catch(Exception e) {
           e.printStackTrace();
          }  
    }
    @FXML
    private void handleGlobalButtonAction(ActionEvent eve){
      
     try {
         System.out.println("You click on GlobalChanges7 buttton");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/GlobalChanges7.fxml"));
                Parent root7 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root7));  
                stage.setTitle("Global Changes");
                stage.show();
        } catch(Exception e) {
           e.printStackTrace();
          }  
    }
    
    @FXML
    private void handleEmployeeButtonAction(ActionEvent eve){
      
     try {
         System.out.println("You click on Employees7 buttton");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Employees7.fxml"));
                Parent root34 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root34));  
                stage.setTitle("Employee Detail");
                stage.show();
        } catch(Exception e) {
           e.printStackTrace();
          }  
    }
    @FXML
    private void handleSalePromoButtonAction(ActionEvent eve){
      
     try {
         System.out.println("You click on SalesPromotion buttton");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SalesPromotion.fxml"));
                Parent root55 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root55));  
                stage.setTitle("Sales Promotion");
                stage.show();
        } catch(Exception e) {
           e.printStackTrace();
          }  
    }
    @FXML
    private void handleConfTenderButtonAction(ActionEvent eve){
      
     try {
         System.out.println("You click on ConfTender buttton");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ConfTender.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));  
                stage.setTitle("Configure Tender");
                stage.show();
        } catch(Exception e) {
           e.printStackTrace();
          }  
    }
    @FXML
    private void handleAdjustTimeButtonAction(ActionEvent eve){
      
     try {
         System.out.println("You click on AdjustTime buttton");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AdjustTime.fxml"));
                Parent root11 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root11));
                stage.setTitle("Adjust Time");
                stage.show();
        } catch(Exception e) {
           e.printStackTrace();
          }  
    }
    @FXML
    private void handleReconcileButtonAction(ActionEvent eve){
      
     try {
         System.out.println("You click on ReConcileDrawer buttton");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ReConcileDrawer.fxml"));
                Parent root12 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root12));  
                stage.setTitle("Reconcile Drawer");
                stage.show();
        } catch(Exception e) {
           e.printStackTrace();
          }  
    }
    @FXML
    private void handleBackUpButtonAction(ActionEvent eve){
      
     try {
         System.out.println("You click on Backup buttton");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Security4.fxml"));
                Parent root4 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root4));  
                stage.setTitle("Backup");
                stage.show();
        } catch(Exception e) {
           e.printStackTrace();
          }  
    }
    @FXML
    private void handleImportDataButtonAction(ActionEvent eve){
      
     try {
         System.out.println("You click on Import Data buttton");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Security4.fxml"));
                Parent root4 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root4));  
                stage.setTitle("Import Data");
                stage.show();
        } catch(Exception e) {
           e.printStackTrace();
          }  
    }
     @FXML
    private void handleViewReportButtonAction(ActionEvent eve){
      
     try {
         System.out.println("You click on ViewReport buttton");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ViewReport.fxml"));
                Parent root4 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root4));  
                stage.setTitle("View Report");
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

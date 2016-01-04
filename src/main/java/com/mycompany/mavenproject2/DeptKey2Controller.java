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
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Third Ev
 */
public class DeptKey2Controller implements Initializable {
@FXML Button BackButton;
   @FXML
    private void handleBackButtonAction(ActionEvent ae){
        System.out.println("You clicked Department key step 2 back!");
         Stage stage1=(Stage) BackButton.getScene().getWindow();
            stage1.close();      
        
       try {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/DeptKey1.fxml"));
                Parent root3 = (Parent) fxmlLoader.load();                
                stage.setScene(new Scene(root3));  
                stage.setTitle("Create Department Key Step 1");
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

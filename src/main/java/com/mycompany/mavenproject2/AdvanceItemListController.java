/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Third Ev
 */
public class AdvanceItemListController implements Initializable {
    @FXML ImageView ImageLabel;
    @FXML
    public void handleAddImageButtonAction(ActionEvent a){
        System.out.println("dialoge box open");
        FileChooser fileChooser = new FileChooser();
        Stage stage=new Stage();
        fileChooser.setTitle("Selct file Sale Label");
        //fileChooser.showOpenDialog(stage);
        fileChooser.getExtensionFilters().addAll( 
           new FileChooser.ExtensionFilter("jpg", "*.jpg"),
            new FileChooser.ExtensionFilter("png", "*.png"));           
            File file1 = fileChooser.showOpenDialog(stage);
            System.out.println(file1);
            if (file1 != null) {               
                Image image = new Image(file1.toURI().toString());                
                ImageLabel.setImage(image);
               
            }
            else{
            }

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

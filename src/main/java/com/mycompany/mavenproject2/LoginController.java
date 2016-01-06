/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Third Ev
 */
public class LoginController implements Initializable {
    @FXML Button submit;
    @FXML TextField UserNameText,PasswordText;
    @FXML
    MeshView mesh;
    public boolean  supported=Platform.isSupported(ConditionalFeature.SCENE3D);
    private static final double MODEL_SCALE_FACTOR = 6;
    @FXML Box box;
   @FXML
   public void handleSubmitButtonAction(ActionEvent a){       
       try {
          // if(PasswordText.getText().equals("admin") && UserNameText.getText().equals("admin")){
           Stage stg=new Stage();
           FXMLLoader fxml=new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
           Parent root=(Parent)fxml.load();
           Scene scene = new Scene(root);
           stg.setScene( scene);
           stg.setTitle("Home");
           scene.getStylesheets().add("/styles/SysSet.css");
           stg.show();           
           Stage stage = (Stage) submit.getScene().getWindow();            
       stage.close();
      /* }
           else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error ");
                alert.setContentText("User Name or Password is incorrect!");
                alert.showAndWait();
        //   }*/
           
           
       } catch (IOException ex) {
           Logger.getLogger(SelectDeptController.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
   @FXML
   public void hanleCancelButtonAction(ActionEvent a){
      /* if(supported){
           System.out.println("3D supported");
       }
       if(!supported){
           System.out.println("3D not supported");
       }
       final PhongMaterial redMaterial = new PhongMaterial();
       redMaterial.setSpecularColor(Color.AZURE);
       redMaterial.setDiffuseColor(Color.RED);
       
       final PhongMaterial blueMaterial = new PhongMaterial();
       blueMaterial.setDiffuseColor(Color.BLUE);
       blueMaterial.setSpecularColor(Color.LIGHTBLUE);

       final PhongMaterial greyMaterial = new PhongMaterial();
       greyMaterial.setDiffuseColor(Color.DARKGREY);
       greyMaterial.setSpecularColor(Color.GREY);

      
//s       box.setMaterial(redMaterial);
 
      /* final Sphere blue = new Sphere(200);
       blue.setMaterial(blueMaterial);
 
       final Cylinder grey = new Cylinder(5, 100);
       grey.setMaterial(greyMaterial);*/

       Stage stage = (Stage) submit.getScene().getWindow();            
       stage.close();
   }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
   
    }    
    
}

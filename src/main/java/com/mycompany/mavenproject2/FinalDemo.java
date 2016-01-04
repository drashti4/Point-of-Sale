/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Third Ev
 */
public class FinalDemo extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
       /* Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        int screenWidth = d.width;
        int screenHeight = d.height;*/
        System.out.println("javafx.runtime.version: " + System.getProperties().get("javafx.runtime.version"));
   
//        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
  FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
                Parent root = (Parent) fxmlLoader.load();   
       Scene scene = new Scene(root);        
        stage.setScene(scene);
        stage.setTitle("Login");
        
      /*File f = new File("SysSet.css");
scene.getStylesheets().clear();
scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
        stage.show();*/
        
       // scene.getStylesheets().add
         
 //(mavenproject2.class.getResource("/styles/SysSet.css").toExternalForm());
                scene.getStylesheets().add("/styles/SysSet.css");
stage.show();
//setUserAgentStylesheet(STYLESHEET_MODENA);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

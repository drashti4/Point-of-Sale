/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finaldemo.Controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Third Ev
 */
public class color extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ColorPicker");
        Scene scene=new Scene(new HBox(20),400,100);
        HBox box=(HBox) scene.getRoot();
         box.setPadding(new Insets(5, 5, 5, 5));
        final ColorPicker cp=new ColorPicker();
        cp.setValue(Color.AQUA);
          final Text text = new Text("Try the color picker!");
        text.setFont(Font.font ("Verdana", 20));
        text.setFill(cp.getValue());
        cp.setOnAction(new EventHandler(){
            
            @Override
            public void handle(Event event) {
                 text.setFill(cp.getValue());               
            }
        });
          box.getChildren().addAll(cp, text);
 
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

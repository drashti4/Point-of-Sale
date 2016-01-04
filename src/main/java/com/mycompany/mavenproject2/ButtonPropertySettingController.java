/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;


import java.awt.Panel;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JColorChooser;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
/**
 * FXML Controller class
 *
 * @author Third Ev
 */
public class ButtonPropertySettingController implements Initializable {
    
      
       @FXML
       Button ShortcutButton;
       @FXML
       Label ItemBackLabel;
      
        @FXML
        Button WindowButton;
        @FXML
        Button GroupButton;
        @FXML
        AnchorPane myLayout;
        @FXML
        Text Text,text2,Shortcut,Group,gtext1,gtext2,Window,wtext1,wtext2;
        @FXML
        Button ShortcutFont;
        @FXML
        public void handleItemTextAction(ActionEvent aw){
            final ColorPicker colorPicker = new ColorPicker(Color.CYAN);      
            colorPicker.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
               Color color = colorPicker.getValue();
               String hex = String.format("#%02x02x02x",
                       (int) (color.getRed() * 255),
                       (int) (color.getGreen() * 255),
                       (int) (color.getBlue() * 255));
               System.out.println("Selected color is "+hex);
               text2.setText(hex);
               //ShortcutButton.setStyle("-fx-background-color: " + hex + ";");
               //ShortcutButton.setBackground(new BackgroundFill(hex, null, null));         
             //  ShortcutButton.setStyle( " -fx-base: "+hex + ";");
               Shortcut.setFill(color);
               }
       });
        myLayout.getChildren().add(colorPicker);
        }
        @FXML
        public void handleGroupTextAction(ActionEvent ae){
            final ColorPicker colorPicker = new ColorPicker(Color.CYAN);      
            colorPicker.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
               Color color = colorPicker.getValue();
               String hex = String.format("#%02x02x02x",
                       (int) (color.getRed() * 255),
                       (int) (color.getGreen() * 255),
                       (int) (color.getBlue() * 255));
               System.out.println("Selected color is "+hex);
               gtext2.setText(hex);
               //ShortcutButton.setStyle("-fx-background-color: " + hex + ";");
               //ShortcutButton.setBackground(new BackgroundFill(hex, null, null));         
             //  ShortcutButton.setStyle( " -fx-base: "+hex + ";");
               Group.setFill(color);
               }
       });
        myLayout.getChildren().add(colorPicker);
        }
        @FXML
        public void handleWindowTextAction(ActionEvent ae){
            final ColorPicker colorPicker = new ColorPicker(Color.CYAN);      
            colorPicker.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
               Color color = colorPicker.getValue();
               String hex = String.format("#%02x02x02x",
                       (int) (color.getRed() * 255),
                       (int) (color.getGreen() * 255),
                       (int) (color.getBlue() * 255));
               System.out.println("Selected color is "+hex);
               wtext2.setText(hex);
               //ShortcutButton.setStyle("-fx-background-color: " + hex + ";");
               //ShortcutButton.setBackground(new BackgroundFill(hex, null, null));         
             //  ShortcutButton.setStyle( " -fx-base: "+hex + ";");
               Window.setFill(color);
               }
       });
        myLayout.getChildren().add(colorPicker);
        }
        @FXML
        private void  handleItemBackAction(ActionEvent eve){
            /* System.out.println("You clicked Set Background Color of Item!");     
            java.awt.Color color;
            color = JColorChooser.showDialog(null,"Select a color",java.awt.Color.CYAN);
            //ColorSelectionModel model = jcc.getSelectionModel();
            String hex = Integer.toHexString(color.getRGB() & 0xffffff);
            //hex= hex.substring(2,hex.length());
            hex="#"+hex;
            Text.setText(hex);
            ShortcutButton.setStyle("-fx-background-color: " + hex + ";");*/
            final ColorPicker colorPicker = new ColorPicker(Color.CYAN);      
            colorPicker.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
               Color color = colorPicker.getValue();
               String hex = String.format("#%02x02x02x",
                       (int) (color.getRed() * 255),
                       (int) (color.getGreen() * 255),
                       (int) (color.getBlue() * 255));
               System.out.println("Selected color is "+hex);
               Text.setText(hex);
               //ShortcutButton.setStyle("-fx-background-color: " + hex + ";");
               //ShortcutButton.setBackground(new BackgroundFill(hex, null, null));              
               ShortcutButton.setBackground(
            new Background(new BackgroundFill(color, null, null)));
           }
       });
        myLayout.getChildren().add(colorPicker);
      /* JColorChooser colorChooser = new JColorChooser(java.awt.Color.CYAN);
Stage mainStage=new Stage();
SwingNode colorChooserNode = new SwingNode();
colorChooserNode.setContent(colorChooser);

Alert dialog = new Alert(Alert.AlertType.NONE);
// Guarantees dialog will be above (and will block input to) mainStage.
dialog.initOwner(mainStage);
dialog.setTitle("Select a color");

dialog.getDialogPane().setContent(colorChooserNode);

dialog.getDialogPane().getButtonTypes().setAll(
    ButtonType.OK, ButtonType.CANCEL);

Optional<ButtonType> response = dialog.showAndWait();
if (response.filter(r -> r == ButtonType.OK).isPresent()) {
    int rgb = colorChooser.getColor().getRGB();
    String hex = String.format("#%06x", rgb & 0xffffff);

    Text.setText(hex);
    ShortcutButton.setStyle("-fx-background-color: " + hex + ";");
} else {
    System.out.println("User canceled");
} */
    }
    @FXML
    public void handleGroupBackAction(ActionEvent ae){
         final ColorPicker colorPicker = new ColorPicker(Color.CYAN);      
            colorPicker.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
               Color color = colorPicker.getValue();
               String hex = String.format("#%02x02x02x",
                       (int) (color.getRed() * 255),
                       (int) (color.getGreen() * 255),
                       (int) (color.getBlue() * 255));
               System.out.println("Selected color is "+hex);
               gtext1.setText(hex);
               //ShortcutButton.setStyle("-fx-background-color: " + hex + ";");
               //ShortcutButton.setBackground(new BackgroundFill(hex, null, null));              
               GroupButton.setBackground(
            new Background(new BackgroundFill(color, null, null)));
           }
       });
        myLayout.getChildren().add(colorPicker);
    }
    @FXML
    public void handleWindowBackAction(ActionEvent ae){
         final ColorPicker colorPicker = new ColorPicker(Color.CYAN);      
            colorPicker.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
               Color color = colorPicker.getValue();
               String hex = String.format("#%02x02x02x",
                       (int) (color.getRed() * 255),
                       (int) (color.getGreen() * 255),
                       (int) (color.getBlue() * 255));
               System.out.println("Selected color is "+hex);
               wtext1.setText(hex);
               //ShortcutButton.setStyle("-fx-background-color: " + hex + ";");
               //ShortcutButton.setBackground(new BackgroundFill(hex, null, null));              
               WindowButton.setBackground(
            new Background(new BackgroundFill(color, null, null)));
           }
       });
        myLayout.getChildren().add(colorPicker);
    }
    @FXML
    private void  handleShortcutFontAction(ActionEvent eve){
        Stage stage = new Stage();
        Optional<Font> response = Dialogs.create()
        .owner(stage)
        .masthead("Choose what you like")
        .showFontSelector(null);
           Font result=response.get();
           Shortcut.setFont(result);
           
    }
     @FXML
    private void  handleWindowFontAction(ActionEvent eve){
        Stage stage = new Stage();
        Optional<Font> response = Dialogs.create()
        .owner(stage)
        .masthead("Choose what you like")
        .showFontSelector(null);
           Font result=response.get();
           String resultT=result.toString();
           
           Window.setFont(result);
           
    }
     @FXML
    private void  handleGroupFontAction(ActionEvent eve){
          Stage stage = new Stage();
        Optional<Font> response = Dialogs.create()
        .owner(stage)
        .masthead("Choose what you like")
        .showFontSelector(null);
           Font result=response.get();
           Group.setFont(result);
          
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }        
}
         
 /* try {
            ColorPicker colorpicker=new ColorPicker();
            colorpicker.setValue(Color.RED);
            colorpicker.show();
            Text.setText((colorpicker.getValue()).toString());
            System.out.println((colorpicker.getValue()).toString() + " color");
            colorpicker.setOnAction((ActionEvent t) -> {
            Text.setText((colorpicker.getValue()).toString());     
    });
            
        } catch(Exception e) {
           e.printStackTrace();
          }  */
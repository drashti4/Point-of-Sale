/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class PriceCalculator1Controller implements Initializable {
    @FXML TextField UnitText,totalText;
    @FXML TextField QuantityText;
    String lastFocusedTextField;
    int total=0;
    @FXML
            public void TextButtonAction(ActionEvent a){   
             UnitText.focusedProperty().addListener(new ChangeListener<Boolean>()
{
    @Override
    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
    {
        if (newPropertyValue)
        {
            System.out.println("Textfield on focus");
        }
        else
        {
            System.out.println("Textfield out focus");
        }
    }
});
            }
   
@FXML
public void handleOneButtonAction(ActionEvent a){
    totalText.setText("");
    
    System.out.println("Focued text is "+lastFocusedTextField);
   String digit = ((Button) a.getSource()).getText();
   if(lastFocusedTextField.equals("unitText")){
       if(UnitText.getText().equals("0")){
           UnitText.setText("");
       }
        String oldText = UnitText.getText();
        String newText = oldText + digit;
        if(UnitText.isFocusTraversable()){
        UnitText.setText(newText);}
   }
   else if(lastFocusedTextField.equals("quantityText")){
       if(QuantityText.getText().equals("0")){
           QuantityText.setText("");
       }
         String oldText = QuantityText.getText();
        String newText = oldText + digit;
        if(QuantityText.isFocusTraversable()){
        QuantityText.setText(newText);}
   }
   total=Integer.parseInt(UnitText.getText())*Integer.parseInt(QuantityText.getText());
 //  Integer ij=Integer.valueOf(UnitText.getText());
    System.out.println("unit text string is "+UnitText.getText()+"total is  "+total);
    totalText.setText(String.valueOf(total));
        
}
@FXML
public void DeleteButtonActionhandle(ActionEvent a){
        QuantityText.setText("0");
        UnitText.setText("0");
        totalText.clear();
        }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       lastFocusedTextField = "unitText" ;

    UnitText.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
        if (isNowFocused) {
            lastFocusedTextField = "unitText" ;
        }
    });

    QuantityText.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
        if (isNowFocused) {
            lastFocusedTextField = "quantityText";
        }
    });


    }    
    
}

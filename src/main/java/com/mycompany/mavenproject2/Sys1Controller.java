package com.mycompany.mavenproject2;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import java.util.logging.Level;
import java.util.logging.Logger;


import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.ComboBox;

import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;



import java.awt.print.*;
import javax.print.*;


import javax.swing.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;

import javafx.scene.control.PasswordField;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;


import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;



import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;

import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import org.bson.Document;
import org.controlsfx.dialog.Dialog;

import org.controlsfx.dialog.Dialogs;


public class Sys1Controller implements Initializable {
   // @FXML private Spinner<Integer> spinner;
    @FXML DatePicker StartDate;    
    @FXML Label NotFoundLabel,PromptLabel,SaveLabel,LabelFile;
    @FXML ProgressIndicator process;
    @FXML ComboBox DateF;
    @FXML ComboBox PhoneF,CurName,CustNameText,DefaultSearch,ZipCode,CurSym,ItemNameText,CurDigit;
    @FXML ComboBox ReorderLevel,PrimaryField,DefaultHistory;
    @FXML TextField ATSID,ATSSubID,OverridePIN,MerchantPIN,ServerName,PortName;    
    @FXML CheckBox Tax1, InDialoge2,ReadOnly3,SwipeCard4,ProcessCard5,SupressAccepted6;
    @FXML CheckBox DisablePIN7,DisableApp,HideReceipt,PrintItem,AllowDebit,AllowEBT,PrintReceipt;
    @FXML CheckBox GroupSale,EmpSale,CashDrawer,SumSale,TenderSale,Other;
    @FXML ComboBox DeptCombo1,PrinterCombo,DeptCombo2,DeptCombo3,PrinterCombo1;
    @FXML TextField UserName,FromAdd,SenderName;
    @FXML TextField minTobacco,minAlco,line1,line2,minSaleamt,minHold;
    @FXML PasswordField Password;
    @FXML CheckBox SSL,TLS,CurrencyCheck,AllowSlipCheck;
    @FXML CheckBox monday,tuesday,wednesday,thursday,friday,saturday;
    @FXML ComboBox ReceiptFormatCombo,LineCombo;
    @FXML TableView<Person> table;
    @FXML TableColumn<Person,String> addoncol ;
    @FXML TableColumn<Person,String> valuecol ;
    @FXML TableColumn<Person,String> percol ;
    @FXML TableColumn<Person,String> taxcol ;
    @FXML ToggleGroup t1,t2;
    @FXML Button Close;
    @FXML CheckBox MSRPCheck;
    public static String demo="hi";
    private final ObservableList<Person> data
            = FXCollections.observableArrayList(     
                   
            );
    @FXML TextField Footer1,Footer2,Footer3,Footer4,Footer5,Footer6;
    @FXML TextField CheckText1,CheckText2,CheckText3,CheckText4,CheckText5,CheckText6;
    MongoClient client = new MongoClient();
    MongoDatabase  db=client.getDatabase("FinalDemo");
    @FXML TextField MFotter1,MFotter2,MFotter3;
    @FXML Spinner hourSpinner,minuteSpinner,secondSpinner,hourSpinner1,minuteSpinner1,secondSpinner1;
    public static Stage stage = null;
    public static FXMLLoader fxmlLoader = null;
    public static Parent root4 = null;
    public static PriceAddonnController controller=null;            
    int day1=0,day2=0,day3=0,day4=0,day5=0,day6=0,msrp=0;
    String nm="name",to="";
    int flag1=0,flag2=0,flag3=0,flag4=0,flag5=0,flag6=0,flag7=0,flag8=0,flag9=0,flag10=0,flag11=0,flag12=0,flag13=0;
    int rflag1=0,rflag2=0,rflag3=0,rflag4=0,rflag5=0,rflag6=0,sflag=0,tflag=0,sec=0,rcflag=0,asflag=0;
    Scene scene=null;
    
   /* public void priceadd(){    
        try {
            stage=new Stage();
            fxmlLoader = new FXMLLoader(getClass().getResource("PriceAddonn.fxml"));
            root4=(Parent) fxmlLoader.load();
            PriceAddonnController controller=fxmlLoader.<PriceAddonnController>getController();
            stage.setScene(new Scene(root4));  
            controller.getData();
            System.out.println(" charge nm is update in sys1 "+controller.chrgenm + " "+controller.chrgeval+" "+controller.var);
            //demo="hello";
             add();
            // data.add(new Person(
            //controller.chrgenm,controller.chrgeval,controller.var,"Tax"));                       
            System.out.println("Close function");
          //  controller.handleCancelButtonAction( null);
        } catch (IOException ex) {
            Logger.getLogger(Sys1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
          add()  ;
     
    }*/
    @FXML
    public void handleSaveSalesButtonAction(ActionEvent a){
        System.out.println("Spinner value is "+hourSpinner.getValue());
        InsertSalesMongo();
        line1.clear();
        line2.clear();
        minAlco.clear();
        minTobacco.clear();
        minHold.clear();
        minSaleamt.clear();
        
    }
    public void InsertSalesMongo(){
            Document input=createSaleSeedData();
            db.getCollection("SaleControlSetting").insertOne(input);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Successfully Added");
            alert.setHeaderText(null)  ;
            alert.setContentText("Click okay to add more data!");
            alert.showAndWait();
    }
    public Document createSaleSeedData(){
            if(monday.isSelected()){day1=1;}else{day1=0;}
            if(tuesday.isSelected()){day2=1;}else{day2=0;}
            if(wednesday.isSelected()){day3=1;}else{day3=0;}
            if(thursday.isSelected()){day4=1;}else{day4=0;}
            if(friday.isSelected()){day5=1;}else{day5=0;}
            if(saturday.isSelected()){day6=1;}else{day6=0;}
            Document d=new Document();
            
            d.append("Tobacco", minTobacco.getText());
            d.append("Alcohole", minAlco.getText());
            d.append("MinAmount", minSaleamt.getText());
            d.append("MinHold",minHold.getText());
            d.append("Department", DeptCombo2.getValue());
            d.append("StartHour",hourSpinner.getValue() );
            d.append("StartMin", minuteSpinner.getValue());
            d.append("StartSec", secondSpinner.getValue());
            d.append("EndHour", hourSpinner1.getValue());
            d.append("EndMin", minuteSpinner1.getValue());
            d.append("EndSec", secondSpinner1.getValue());
            d.append("Monday", day1);
            d.append("Tuesday",day2);
            d.append("Wednesday",day3);
            d.append("Thursday",day4);
            d.append("Friday",day5);
            d.append("Saturday",day6);
            return d;
    }
    
    
    public void priceadd() throws SQLException, ClassNotFoundException{
        String qry="select ChargeName,ChargeValue from demo  ORDER BY id DESC LIMIT 1";
        java.sql.Connection conn=Connection.getConnect();     
        String nam="",val="";
        PreparedStatement pst2=conn.prepareStatement(qry);
        ResultSet rs = pst2.executeQuery(qry);
        while(rs.next()){
            System.out.println("result is "+rs.getString("ChargeName")+" "+rs.getString("ChargeValue"));
            nam=rs.getString("ChargeName");
            val=rs.getString("ChargeValue");
        }
        data.add(new Person(nam,val,"gd","lk"));
        
        data.add(new Person("hgf","hf","jf","gf"))      ;
                
    }
    public void add(){
      
        data.add(new Person("hey", "ho", "to", "nm"));
        
    }
    public void Update(String charge_nm,String charge_val,String var,String Tax){   
           
            data.add(new Person(
                    charge_nm,charge_val,var,Tax));
      
    }
    @FXML
    public void dummyAdd(ActionEvent a){
       /* System.out.println("you click add button");
        
        add();*/
        Person person = table.getSelectionModel().getSelectedItem();
        System.out.println("AddOn name "+person.getAddOn());    
        System.out.println("val "+person.getValue());    
        System.out.println("Percentage "+person.getPer());    
        System.out.println("Tax "+person.getTax());    
         try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/PriceAddonn.fxml"));
            Parent root44 = (Parent) fxmlLoader.load();
            PriceAddonnController controller=fxmlLoader.<PriceAddonnController>getController();
            stage.setScene(new Scene(root44));
            stage.setTitle("Add Price");
            controller.EditButton(person.getAddOn(),person.getValue(),person.getPer(),person.getTax());
            stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(Sys1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    @FXML
    public void handleCostRemoveButtonAction(ActionEvent ae){
        /*org.controlsfx.control.action.Action response =  Dialogs.create()
        .owner(stage)
        .title("Confirm Dialog with Custom Actions")
        .masthead("Look, a Confirm Dialog with Custom Actions")
        .message("Are you ok with this?")
        .actions(Dialog.ACTION_OK, Dialog.ACTION_CANCEL)
        .showConfirm();
        

if (response == Dialog.ACTION_OK) {*/
         System.out.println("You click okay confirm");
        List items =  new ArrayList (table.getSelectionModel().getSelectedItems());  
        Person person = table.getSelectionModel().getSelectedItem();
        //System.out.println(person.getAddOn());       
        db.getCollection("PriceDetail").deleteOne(eq("ChargeName",person.getAddOn()));
        data.removeAll(items);
        table.getSelectionModel().clearSelection();
/*}
else{
    System.out.println("You click cancel confirm");
}*/
    }
    
     @FXML
    public void handleCloseButtonAction(ActionEvent ae){
        Stage stage=(Stage) Close.getScene().getWindow();
            stage.close();     
    }
    
    @FXML
    public void handleReceiptSaveButtonAction(ActionEvent er){
        InsertReceiptMongo();
        Footer1.clear();
        Footer2.clear();
        Footer3.clear();
        Footer4.clear();
        Footer5.clear();
        Footer6.clear();
        CheckText1.clear();
        CheckText2.clear();
        CheckText3.clear();
        CheckText4.clear();
        CheckText5.clear();
        CheckText6.clear();
        MFotter1.clear();
        MFotter2.clear();
        MFotter3.clear();
       
    }
    public void InsertReceiptMongo(){
            Document input=createReceiptSeedData();
            db.getCollection("ReceiptSetting").insertOne(input);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Successfully Added");
            alert.setHeaderText(null)  ;
            alert.setContentText("Click okay to add more data!");
            alert.showAndWait();
            
    }
    public Document createReceiptSeedData(){
        if(CurrencyCheck.isSelected()){rcflag=1;}else{rcflag=0;}
            if(AllowSlipCheck.isSelected()){asflag=1;}else{asflag=0;}
            Document d=new Document();
            d.append("ReceiptFormat",ReceiptFormatCombo.getValue());
            d.append("Line",LineCombo.getValue());
            d.append("Currency", rcflag);
            d.append("AllowSlipCheck", asflag);
            d.append("Footer1", Footer1.getText());
            d.append("Footer2", Footer2.getText());
            d.append("Footer3", Footer3.getText());
            d.append("Footer4", Footer4.getText());
            d.append("Footer5", Footer5.getText());
            d.append("Footer6", Footer6.getText());
            d.append("CheckText1", CheckText1.getText());
            d.append("CheckText2", CheckText2.getText());
            d.append("CheckText3", CheckText3.getText());
            d.append("CheckText4", CheckText4.getText());
            d.append("CheckText5", CheckText5.getText());
            d.append("CheckText6", CheckText6.getText());
            d.append("MFooter1",MFotter1.getText());
            d.append("MFooter2",MFotter2.getText());
            d.append("MFooter3",MFotter3.getText());
            return d;
    }
    public void ReceiptInsertSQL(){
        try{
            System.out.println("You clicked save receipt button");
        PreparedStatement ReceiptQuery=null;
            java.sql.Connection conn=Connection.getConnect();  
            String Add1="INSERT INTO receiptsetting " 
                    +"(`ReceiptFormat`,`Line`,`Currency`,`AllowSlipCheck`,`Footer1`,`Footer2`,`Footer3`,`Footer4`,`Footer5`,`Footer6`,`CheckText1`,`CheckText2`,`CheckText3`,`CheckText4`,`CheckText5`,`CheckText6`,`MFotter1`,`MFotter2`,`MFotter3`) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";          
            ReceiptQuery=conn.prepareStatement(Add1);
            if(CurrencyCheck.isSelected()){rcflag=1;}else{rcflag=0;}
            if(AllowSlipCheck.isSelected()){asflag=1;}else{asflag=0;}
            ReceiptQuery.setString(1,ReceiptFormatCombo.getValue().toString());
            ReceiptQuery.setInt(2, Integer.parseInt(LineCombo.getValue().toString()));
            ReceiptQuery.setInt(3, rcflag);
            ReceiptQuery.setInt(4, asflag);
            ReceiptQuery.setString(5, Footer1.getText());
            ReceiptQuery.setString(6, Footer2.getText());
            ReceiptQuery.setString(7, Footer3.getText());
            ReceiptQuery.setString(8, Footer4.getText());
            ReceiptQuery.setString(9, Footer5.getText());
            ReceiptQuery.setString(10, Footer6.getText());
            ReceiptQuery.setString(11, CheckText1.getText());
            ReceiptQuery.setString(12, CheckText2.getText());
            ReceiptQuery.setString(13, CheckText3.getText());
            ReceiptQuery.setString(14, CheckText4.getText());
            ReceiptQuery.setString(15, CheckText5.getText());
            ReceiptQuery.setString(16, CheckText6.getText());            
            ReceiptQuery.setString(17, MFotter1.getText());  
            ReceiptQuery.setString(18, MFotter2.getText());  
            ReceiptQuery.setString(19, MFotter3.getText());    
            ReceiptQuery.executeUpdate();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Successfully Added");
            alert.setHeaderText(null)  ;
            alert.setContentText("Click okay to add more data!");
            alert.showAndWait();
            }
        catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    @FXML
    public void handleCostSaveButton(ActionEvent a){       
       InsertCostSaveMongo();
       
       
    }
    public void InsertCostSaveMongo(){
             Document input=createCostSaveSeedData();
            db.getCollection("CostPricingSetting").insertOne(input);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Successfully Added");
            alert.setHeaderText(null)  ;
            alert.setContentText("Click okay to add more data!");
            alert.showAndWait();
    }
    public Document createCostSaveSeedData(){
           if(MSRPCheck.isSelected()){msrp=1;}else{msrp=0;}
           t1.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
        @Override
        public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {

            RadioButton chk = (RadioButton)t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
            System.out.println("Selected Radio Button - "+chk.getText());

        }
    });
            
             final Document d=new Document();
            d.append("Printer",PrinterCombo1.getValue());
            d.append("SaleLabelFile", SaveLabel.getText());
            t1.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
        @Override
        public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {

            RadioButton chk = (RadioButton)t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
            System.out.println("Selected Radio Button - "+chk.getText());
            d.append("ConfigureMethod", chk.getText());

        }
    });
            t2.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
        @Override
        public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {

            RadioButton chk = (RadioButton)t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
            System.out.println("Selected Radio Button - "+chk.getText());
            d.append("PriceCalculation", chk.getText());
        }
    });
            return d; 
    }
    @FXML
    public void handlePriceSaveButton(ActionEvent a){
        System.out.println("You click Cost n pricing setting");
         try {
                
               /* fxmlLoader1.setControllerFactory(new Callback<Class<?>, Object>() {
                 @Override
                public Object call(Class<?> controllerClass) {
                    if (controllerClass == PriceAddonnController.class) {
                        PriceAddonnController controller = new PriceAddonnController();
                        controller.getCharge();
                        return controller ;
                    } else {
                    try {
                            return controllerClass.newInstance();
                    } catch (Exception exc) {
                        throw new RuntimeException(exc); // just bail
            }
        }
    }
});*/        
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/PriceAddonn.fxml"));
                Parent root44 = (Parent) fxmlLoader.load();  
                
                PriceAddonnController controller=fxmlLoader.<PriceAddonnController>getController();             
                stage.setScene(new Scene(root44));               
                stage.setTitle("Add Price");
                stage.show();
               // System.out.println("handlePriceSaveButton get over extr a var is "+controller.var);
              //  controller.print();
              /* try {
                    Thread.sleep(5000);                 //1000 milliseconds is one second.
                    } catch(InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }*/
              //creating custom dialog.
               
        
   /*remove     Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Login Dialog");
        dialog.setHeaderText("Look, a Custom Login Dialog");

// Set the icon (must be included in the project).
//dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

// Set the button types.
        ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

// Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Extra Charge Name");
        final ToggleGroup group = new ToggleGroup();
        RadioButton per=new RadioButton("As Per Percentage");
        RadioButton amt=new RadioButton("As Per Amount");
        TextField password = new TextField();
        password.setPromptText("Extra Value of Amount");
        CheckBox charge=new CheckBox("Inactive Charge");
        CheckBox tax=new CheckBox("Apply this charge as tax exclusive");
        charge.setSelected(true);
        per.setToggleGroup(group);
        amt.setToggleGroup(group);
        grid.add(new Label("Extra Charge Name"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Extra Value of Amount"), 0, 1);
        grid.add(password, 1, 1);
        grid.add(per, 0,2 );
        grid.add(amt, 1, 2);
        grid.add(charge, 0,3 );
        grid.add(tax, 1,3 );

// Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);
// Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
        loginButton.setDisable(newValue.trim().isEmpty());
});

dialog.getDialogPane().setContent(grid);

// Request focus on the username field by default.
Platform.runLater(() -> username.requestFocus());

// Convert the result to a username-password-pair when the login button is clicked.
dialog.setResultConverter(dialogButton -> {
    if (dialogButton == loginButtonType) {
        return new Pair<>(username.getText(), password.getText());
    }
    return null;
});

Optional<Pair<String, String>> result = dialog.showAndWait();

result.ifPresent(usernamePassword -> {
    System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
}); 

           remove*/  
        } catch(Exception e) {
           e.printStackTrace();
          }  
    }
    
    @FXML
    public void handleTestMailButtonAction(ActionEvent ac){       
        try {
            /* FXMLLoader fxmlLoader = new FXMLLoader();
            URL myUrl = getClass().getResource("SendMail.fxml");
            fxmlLoader.setLocation(myUrl);*/
            //SendMailController controller=fxmlLoader1.<SendMailController>getController();
            //SendMailController c=(SendMailController)fxmlLoader.getController();
            
            // System.out.println("Called send mail");
            Stage stage = new Stage();
            Optional<String> response = Dialogs.create()
            .owner(stage)
            .title("Enter Below field")
            .masthead(null)
            .message("To:")
            .showTextInput("");            
         
            if (response.isPresent()) {
                    System.out.println("Your name: " + response.get());
                    to=response.get();
                }
            if(SSL.isSelected()){sec=1;}
            if(TLS.isSelected()){sec=2;}
// The Java 8 way to get the response value (with lambda expression).
        //response.ifPresent(name -> System.out.println("Your name: " + name));
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SendMail.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();//must here even if not used othrwse null pointer exception
            SendMailController controller=fxmlLoader.<SendMailController>getController();            
           // stage.setScene(new Scene(root1));
            //stage.show();           
/*            process.setProgress(0);
            process.progressProperty().unbind();*/
            controller.print();
            controller.SendMail(ServerName.getText(),Integer.parseInt(PortName.getText()),UserName.getText(),Password.getText(),SenderName.getText(),FromAdd.getText(),to,sec);
            
        } catch (Exception ex) {
            Logger.getLogger(Sys1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
  
    @FXML
    public void handleSaveMailButtonAction(ActionEvent ac){          
         InsertMailMongo();
        
   }
    public void InsertMailMongo(){
       final Document seedData = createMailSeedData();
        db.getCollection("MailSetting").insertOne(seedData);  
         Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Successfully Added");
            alert.setHeaderText(null)  ;
            alert.setContentText("Your setting is saved click ok!");
            alert.showAndWait();
        ServerName.clear();
        PortName.clear();
        UserName.clear();
        Password.clear();
        SenderName.clear();
        FromAdd.clear();
    }
    public Document createMailSeedData(){
         Document d1=new Document();
        d1.append("ServerName", ServerName.getText());
        d1.append("Port", Integer.parseInt(PortName.getText()));
        d1.append("UserName", UserName.getText());
        d1.append("Password", Password.getText());
        d1.append("SenderName", SenderName.getText());
        d1.append("FromAdd", FromAdd.getText());
        d1.append("SSL", sflag);
        d1.append("TLS", tflag);
        return  d1;
    }
   public void InsertMailSql(){
       System.out.println("You clicked Report Save Button");
        PreparedStatement MailQuery=null;
        try{
            java.sql.Connection conn=Connection.getConnect();  
            String Add1="INSERT INTO mailsetting " 
                    +"(`ServerName`,`Port`,`UserName`,`Password`,`SenderName`,FromAdd,`SSL`,`TLS`) "
                    + "VALUES (?,?,?,?,?,?,?,?)";
           
           
            MailQuery=conn.prepareStatement(Add1);
            if(SSL.isSelected()){sflag=1;}else{sflag=0;}
            if(TLS.isSelected()){tflag=1;}else{tflag=0;}
            MailQuery.setString(1,ServerName.getText().toString());
            MailQuery.setInt(2, Integer.parseInt(PortName.getText()));
            MailQuery.setString(3, UserName.getText().toString());
            MailQuery.setString(4, Password.getText().toString());
            MailQuery.setString(5, SenderName.getText().toString());
            MailQuery.setString(6, FromAdd.getText().toString());           
            MailQuery.setInt(7, sflag);
            MailQuery.setInt(8,tflag);
            MailQuery.executeUpdate();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Successfully Added");
            alert.setHeaderText(null)  ;
            alert.setContentText("Your setting is saved click ok!");
            alert.showAndWait();
        }
        catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }         
   }
   
    public class OutputPrinter implements Printable 
    {
            private String printData;

            public OutputPrinter(String printDataIn)
            {
                this.printData = printDataIn;
            }
            @Override
            public int print(Graphics g, PageFormat pf, int page) throws PrinterException
            {
                    // Should only have one page, and page # is zero-based.
                if (page > 0)
                {
                    return NO_SUCH_PAGE;
                }
                // Adding the "Imageable" to the x and y puts the margins on the page.
                    // To make it safe for printing.
            Graphics2D g2d = (Graphics2D)g;
            int x = (int) pf.getImageableX();
            int y = (int) pf.getImageableY();        
            g2d.translate(x, y); 

            // Calculate the line height
            Font font = new Font("Serif", Font.PLAIN, 10);
            FontMetrics metrics = g.getFontMetrics(font);
            int lineHeight = metrics.getHeight();
                
            BufferedReader br = new BufferedReader(new StringReader(printData));

    // Draw the page:
            try
            {
                String line;
        // Just a safety net in case no margin was added.
                x += 50;
                y += 50;
                while ((line = br.readLine()) != null)
                {
                    y += lineHeight;
                    g2d.drawString(line, x, y);
                }
            }
            catch (IOException e)
            {
                System.err.println("error is "+ e.toString()); 
            }

    return PAGE_EXISTS;
        }
    }
        private void printToPrinter()
    {
            String printData = ServerName.getText().toString()+"\n"+PortName.getText().toString();
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPrintable(new OutputPrinter(printData));
            boolean doPrint = job.printDialog();
             if (doPrint)
                { 
             try 
        {
            job.print();
        }
        catch (PrinterException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        JOptionPane.showMessageDialog(null, "InProcess");
    }
    else{
        JOptionPane.showMessageDialog(null, "Complete");
    }
}
        
    @FXML
    public void handleEmailCancelAction(ActionEvent e) throws PrinterException{

        System.out.println("You clicked Email tab Cancel Button");
         /*PrinterJob job = PrinterJob.getPrinterJob();
         job.setPrintable(null);
         boolean ok = job.printDialog();
         if (ok) {
             try {
                  job.print();
             } catch (PrinterException ex) {
              System.out.print(" The job did not successfully complete ");
             }
         }*/
      /*  PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
AttributeSet attributes = printService.getAttributes();
String printerState = attributes.get(Sys1Controller.class).toString();
String printerStateReason = attributes.get(PrinterStateReason.class).toString();
System.out.print("Button Pressed");

System.out.print("printerState = " + printerState); // May be IDLE, PROCESSING, STOPPED or UNKNOWN
System.out.println("printerStateReason = " + printerStateReason); // If your printer state returns STOPPED, for example, you can identify the reason 

if (printerState.equals(PrinterState.STOPPED.toString())) {

    if (printerStateReason.equals(PrinterStateReason.TONER_LOW.toString())) {

        System.out.println("Toner level is low.");
    }
}*/
    
        
      /* PrinterJob pjob = PrinterJob.getPrinterJob();
        PageFormat pf = pjob.defaultPage();
        pjob.setPrintable(null, pf);

        if (pjob.printDialog()) {
            try {
                pjob.print();
            } catch (PrinterException ex) {
                Logger.getLogger(Sys1Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
        String PrinterName="";
        int PFlag=0;
        String printData = ServerName.getText().toString()+"\n"+PortName.getText().toString();
       try{
            java.sql.Connection con1=Connection.getConnect();  
            String qry="select ReportPrinter from reportsetting ORDER BY id DESC LIMIT 1";
            PreparedStatement pst2=con1.prepareStatement(qry);
            ResultSet rs = pst2.executeQuery(qry);
            while(rs.next()){
                PrinterName=rs.getString("ReportPrinter");
            }
            System.out.println("Last Printer Added is "+PrinterName);
       }
        catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }  
        PrinterJob pj = PrinterJob.getPrinterJob();
    PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
    System.out.println("Number of printers configured: " + printServices.length);
    for (PrintService printer : printServices) {
        
        if (printer.getName().equals(PrinterName)) {  
            if(PrinterName=="Fax"){
                
            }
            else{
                
            
            pj.setPrintable(new OutputPrinter(printData));
            pj.print();            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Printing");
            alert.setHeaderText(null);
            alert.setContentText("In process of priniting click ok!");
            alert.showAndWait();
            PFlag=1;
            break;
            }
        }
        else{
            PFlag=2;
        }
    }
    if(PFlag==1){
                
    }
    if(PFlag==2){
        Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Look, Printer Connected");
            alert.setContentText("Admin Slected Printer Not Found!");

            alert.showAndWait();
            
    }
        //printToPrinter();
    }
    
 @FXML
            public void handleSaleCheckAction(ActionEvent a){
                boolean selected1 = GroupSale.isSelected();
                System.out.println("CheckBox Action (selected1: " + selected1 + ")");
                 if(selected1==true){
        DeptCombo1.setDisable(false);
    
    }
    if(selected1==false){
        DeptCombo1.setDisable(true);
    
    }
                GroupSale.setOnAction((event) -> {
    boolean selected = GroupSale.isSelected();
    
    
    if(selected==true){
        DeptCombo1.setDisable(false);
       
    }
    if(selected==false){
        DeptCombo1.setDisable(true);
       
    }
});
            }
    @FXML
    public void handleSaveReportButtonAction(ActionEvent ac){
        InsertReportMongo();
    }
    public void InsertReportMongo(){
         final Document seedData2 = createReportSeedData();
            db.getCollection("ReportSetting").insertOne(seedData2);           
  Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Successfully Added");
                alert.setHeaderText(null)  ;
                alert.setContentText("Click okay to add more data!");
                alert.showAndWait();
    }
   public Document createReportSeedData(){
        String res="";     
        if(EmpSale.isSelected()){rflag1=1;}else{rflag1=0;}
                 if(CashDrawer.isSelected()){rflag2=1;}else{rflag2=0;}
                 if(SumSale.isSelected()){rflag3=1;}else{rflag3=0;}
                 if(TenderSale.isSelected()){rflag4=1;}else{rflag4=0;}
                 if(Other.isSelected()){rflag5=1;}else{rflag5=0;}
                 if(GroupSale.isSelected()){rflag6=1;}else{rflag6=0;}
                 if(rflag6==1)
                 {
                     
                     res=DeptCombo1.getValue().toString();
                 }
                 else{
                     res="Group Sale Not Selected";
                 }
       Document d4=new Document();
       d4.append("EmpSale",rflag1);
       d4.append("CashDrawer",rflag2);
       d4.append("SumSale",rflag3);
       d4.append("TenderSale",rflag4);
       d4.append("Other",rflag5);
       d4.append("GroupSale",rflag6);
       d4.append("Dept",res);
       d4.append("StartDate",StartDate.getValue().toString());
       d4.append("ReportPrinter",PrinterCombo.getValue().toString());
       return d4;
       
   }
    public void InsertReportSQL(){
        String res="";        
        System.out.println("You clicked Report Save Button");
        PreparedStatement ReportQuery=null;
        try{
            java.sql.Connection con1=Connection.getConnect();  
            String Add="INSERT INTO reportsetting"
                    +"(EmpSale,CashDrawer,SumSale,TenderSale,Other,GroupSale,Dept,StartDate,ReportPrinter) VALUES"
                    +"(?,?,?,?,?,?,?,?,?)";
           
                 if(EmpSale.isSelected()){rflag1=1;}else{rflag1=0;}
                 if(CashDrawer.isSelected()){rflag2=1;}else{rflag2=0;}
                 if(SumSale.isSelected()){rflag3=1;}else{rflag3=0;}
                 if(TenderSale.isSelected()){rflag4=1;}else{rflag4=0;}
                 if(Other.isSelected()){rflag5=1;}else{rflag5=0;}
                 if(GroupSale.isSelected()){rflag6=1;}else{rflag6=0;}
                 if(rflag6==1)
                 {
                     
                     res=DeptCombo1.getValue().toString();
                 }
                 else{
                     res="Group Sale Not Selected";
                 }               

                 ReportQuery=con1.prepareStatement(Add);   
                 ReportQuery.setInt(1, rflag1);
                 ReportQuery.setInt(2, rflag2);
                 ReportQuery.setInt(3, rflag3);
                 ReportQuery.setInt(4, rflag4);
                 ReportQuery.setInt(5, rflag5);
                 ReportQuery.setInt(6,rflag6);
                 ReportQuery.setString(7, res);               
                 ReportQuery.setString(8,StartDate.getValue().toString());
                 ReportQuery.setString(9, PrinterCombo.getValue().toString());
                 ReportQuery.executeUpdate();
        //JOptionPane.showMessageDialog(, "Sucessfully Inserted");
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Successfully Added");
                alert.setHeaderText(null)  ;
                alert.setContentText("Click okay to add more data!");
                alert.showAndWait();
        }
        catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    @FXML
    public void handleFMMSaveButtonAction(ActionEvent ac){
         InsertFMMMongo();
    }
    public void InsertFMMMongo(){
         final Document seedData1 = createFMMSeedData();
            db.getCollection("FMMCharge").insertOne(seedData1);    
            ATSID.clear();
            MerchantPIN.clear();
            ATSSubID.clear();
            OverridePIN.clear();
            
    }
    public Document createFMMSeedData(){
        if(Tax1.isSelected()){flag1=1;}else{flag1=0;}
                 if(InDialoge2.isSelected()){flag2=1;}else{flag2=0;}
                 if(ReadOnly3.isSelected()){flag3=1;}else{flag3=0;}
                 if(SwipeCard4.isSelected()){flag4=1;}else{flag4=0;}
                 if(ProcessCard5.isSelected()){flag5=1;}else{flag5=0;}
                 if(SupressAccepted6.isSelected()){flag6=1;}else{flag6=0;}
                 if(DisablePIN7.isSelected()){flag7=1;}else{flag7=0;}
                 if(DisableApp.isSelected()){flag8=1;}else{flag8=0;}
                 if(HideReceipt.isSelected()){flag9=1;}else{flag9=0;}
                 if(PrintItem.isSelected()){flag10=1;}else{flag10=0;}
                 if(AllowDebit.isSelected()){flag11=1;}else{flag11=0;}
                 if(AllowEBT.isSelected()){flag12=1;}else{flag12=0;}
                 if(PrintReceipt.isSelected()){flag13=1;}else{flag13=0;}
        Document d3=new Document();
        d3.append("ATSID", ATSID.getText());
        d3.append("MerchantPIN", MerchantPIN.getText());
        d3.append("ATSSubID", ATSSubID.getText());
        d3.append("OverridePIN", OverridePIN.getText());
        d3.append("Tax1",flag1 );
        d3.append("InDialoge2",flag2 );
        d3.append("ReadOnly3",flag3 );
        d3.append("SwipeCard4",flag4 );
        d3.append("ProcessCard5",flag5 );
        d3.append("SupressAccepted6", flag6);
        d3.append("DisablePIN7", flag7);
        d3.append("DisableApp", flag8);
        d3.append("HideReceipt",flag9 );
        d3.append("PrintItem", flag10 );
        d3.append("AllowDebit",flag11 );
        d3.append("AllowEBT", flag12);
        d3.append("PrintReceipt", flag13);
        
    return d3;    
    }
    public void InsertFMMSQL(){
         System.out.println("You click FMMMMMM Charge and saving data ");
        PreparedStatement pst1=null;      
        try {
                 java.sql.Connection con1=Connection.getConnect();  
                 String add1="INSERT INTO fmmcharge"
				+ "(ATSID,MerchantPIN,ATSSubID, OverridePIN, Tax1, InDialoge2,ReadOnly3,SwipeCard4,"
                         + "ProcessCard5,SupressAccepted6,DisablePIN7,"
                         + "DisableApp,HideReceipt,PrintItem,AllowDebit,AllowEBT,PrintReceipt) VALUES"
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                 if(Tax1.isSelected()){flag1=1;}else{flag1=0;}
                 if(InDialoge2.isSelected()){flag2=1;}else{flag2=0;}
                 if(ReadOnly3.isSelected()){flag3=1;}else{flag3=0;}
                 if(SwipeCard4.isSelected()){flag4=1;}else{flag4=0;}
                 if(ProcessCard5.isSelected()){flag5=1;}else{flag5=0;}
                 if(SupressAccepted6.isSelected()){flag6=1;}else{flag6=0;}
                 if(DisablePIN7.isSelected()){flag7=1;}else{flag7=0;}
                 if(DisableApp.isSelected()){flag8=1;}else{flag8=0;}
                 if(HideReceipt.isSelected()){flag9=1;}else{flag9=0;}
                 if(PrintItem.isSelected()){flag10=1;}else{flag10=0;}
                 if(AllowDebit.isSelected()){flag11=1;}else{flag11=0;}
                 if(AllowEBT.isSelected()){flag12=1;}else{flag12=0;}
                 if(PrintReceipt.isSelected()){flag13=1;}else{flag13=0;}
                 pst1=con1.prepareStatement(add1);                 
                 pst1.setString(1, ATSID.getText());
                 pst1.setString(2, MerchantPIN.getText());
                 pst1.setString(3, ATSSubID.getText());
                 pst1.setString(4, OverridePIN.getText());
                 pst1.setInt(5, flag1);
                 pst1.setInt(6, flag2);
                 pst1.setInt(7, flag3);
                 pst1.setInt(8, flag4);
                 pst1.setInt(9, flag5);
                 pst1.setInt(10,flag6);
                 pst1.setInt(11, flag7);               
                 pst1.setInt(12,flag8);
                 pst1.setInt(13,flag9);
                 pst1.setInt(14,flag10);
                 pst1.setInt(15, flag11);
                 pst1.setInt(16,flag12);
                 pst1.setInt(17,flag13);
                 pst1.executeUpdate();
                /* ResultSet rs=pst1.executeQuery(add);
                 while(rs.next()){
                     System.out.println(" selectn "+rs.getString("DateF"));
                 }*/                 
             } 
           catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    public void handleSelectLabelButtonAction(ActionEvent a){        
        System.out.println("select label button");
        FileChooser fileChooser = new FileChooser();
        Stage stage=new Stage();
        fileChooser.setTitle("Selct file Sale Label");
        //fileChooser.showOpenDialog(stage);
        fileChooser.getExtensionFilters().addAll( 
           new FileChooser.ExtensionFilter("Smart Pole Labels File", "*.spl"),
            new FileChooser.ExtensionFilter("All Files(*.*)", "*.*"));
           
            File file1 = fileChooser.showOpenDialog(stage);
            System.out.println(file1);
            if (file1 != null) { 
                LabelFile.setText(" ");
                LabelFile.setText(file1.toString());}
            else{
            LabelFile.setText("No file selected");}
}
    @FXML
    public void handleDrawerButtonAction(ActionEvent ac){
        System.out.println("You click cash drawer setting");
         try {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CashDrawer.fxml"));
                Parent root = (Parent) fxmlLoader.load();                
                stage.setScene(new Scene(root));  
                stage.setTitle("Cash Drawer");
                stage.show();
        } catch(Exception e) {
           e.printStackTrace();
          }  
    }
    @FXML
    public void handleGeneralSaveButtonAction(ActionEvent ac){
       InsertGeneralMongo();
    }
    public void InsertGeneralMongo(){
         final Document seedData = creategeneralSeedData();
            db.getCollection("SystemSetting").insertOne(seedData);           
        SaveLabel.setText("");
        PromptLabel.setText("");
        NotFoundLabel.setText("");
        Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Sucess");
            alert.setHeaderText(null);
            alert.setContentText("Data Sucessfully added click OK!");
            alert.showAndWait();
        
   }
    public Document creategeneralSeedData(){
        Document d2=new Document();
        d2.append("DateF", DateF.getValue());
        d2.append("PhoneF", PhoneF.getValue());
        d2.append("CurName", CurName.getValue());
        d2.append("CurNameText", CustNameText.getValue());
        d2.append("DefaultSearch", DefaultSearch.getValue());
        d2.append("ZipCode", Integer.parseInt(ZipCode.getValue().toString()));
        d2.append("CurSym", CurSym.getValue());
        d2.append("CurDigit", Integer.parseInt(CurDigit.getValue().toString()));
        d2.append("ReorderLevel", ReorderLevel.getValue());
        d2.append("PrimaryField", PrimaryField.getValue());
        d2.append("DefaultHistory", DefaultHistory.getValue());
        d2.append("SucessScan", SaveLabel.getText());
        d2.append("NotFound", NotFoundLabel.getText());
        d2.append("PromptPrice", PromptLabel.getText());
        d2.append("ItemNameText", ItemNameText.getValue());
        return d2;
    }
    public void InsertGeneralSQL(){
         System.out.println("You click General Setting and saving data ");
        PreparedStatement pst=null;
        //System.out.println("'"+DateF.getValue().toString()+"','"+Integer.parseInt(PhoneF.getValue().toString())+"','"+CurName.getValue().toString()+"','"+CustNameText.getValue().toString()+"','"+DefaultSearch.getValue().toString()+"','"+Integer.parseInt(ZipCode.getValue().toString())+"','"+CurSym.getValue().toString()+"','"+CurDigit.getValue().toString()+"','"+ReorderLevel.getValue().toString()+"','"+PrimaryField.getValue().toString()+"','"+DefaultHistory.getValue().toString()+"','"+PromptLabel.getText()+"','"+NotFoundLabel.getText().toString()+"','"+SaveLabel.getText().toString()+"','"+ItemNameText.getValue().toString()+"'");             
        try {
                 java.sql.Connection con1=Connection.getConnect();  
                 String add="INSERT INTO syssetting"
				+ "(DateF, PhoneF, CurName, CustNameText,DefaultSearch,ZipCode,CurSym,CurDigit,ReorderLevel,PrimaryField,DefaultHistory,SucessScan,NotFound,PromptPrice,ItemNameText) VALUES"
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                //String add1 = "insert into syssetting values('"+DateF.getValue().toString()+"','"+Integer.parseInt(PhoneF.getValue().toString())
                   // +"','"+CurName.getValue().toString()+"','"+CustNameText.getValue().toString()+"','"+DefaultSearch.getValue().toString()+"','"+Integer.parseInt(ZipCode.getValue().toString())+"','"+CurSym.getValue().toString()+"','"+CurDigit.getValue().toString()+"','"+ReorderLevel.getValue().toString()+"','"+PrimaryField.getValue()+"','"+DefaultHistory.getValue()+"','"+PromptLabel.getText()+"','"+NotFoundLabel.getText()+"','"+SaveLabel.getText()+"','"+ItemNameText.getValue().toString()+"')";       
                 pst=con1.prepareStatement(add);                 
                 pst.setString(1, DateF.getValue().toString());
                 pst.setString(2, PhoneF.getValue().toString());
                 pst.setString(3, CurName.getValue().toString());
                 pst.setString(4, CustNameText.getValue().toString());
                 pst.setString(5, DefaultSearch.getValue().toString());
                 pst.setInt(6, Integer.parseInt(ZipCode.getValue().toString()));
                 pst.setString(7, CurSym.getValue().toString());
                 pst.setInt(8, Integer.parseInt(CurDigit.getValue().toString()));
                 pst.setString(9, ReorderLevel.getValue().toString());
                 pst.setString(10,PrimaryField.getValue().toString());
                 pst.setString(11, DefaultHistory.getValue().toString());               
                 pst.setString(12,SaveLabel.getText());
                 pst.setString(13,NotFoundLabel.getText());
                 pst.setString(14,PromptLabel.getText());
                 pst.setString(15, ItemNameText.getValue().toString());
                 pst.executeUpdate();
                /* ResultSet rs=pst.executeQuery(add);
                 while(rs.next()){
                     System.out.println(" selectn "+rs.getString("DateF"));
                 }*/                 
             } 
           catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    @FXML
    public void handleSuccessButtonAction(ActionEvent ac){
        
        System.out.println("sucees button");
        FileChooser fileChooser = new FileChooser();
        Stage stage=new Stage();
        fileChooser.setTitle("Selct file for sucessful scan");
        //fileChooser.showOpenDialog(stage);
        fileChooser.getExtensionFilters().addAll( 
           new FileChooser.ExtensionFilter("*.wav", "*.wav"),
            new FileChooser.ExtensionFilter("*.mp3", "*.mp3"), 
            new FileChooser.ExtensionFilter("*.arc", "*.arc"));
            File file = fileChooser.showOpenDialog(stage);
            System.out.println(file);
            if (file != null) { 
                SaveLabel.setText(" ");
                SaveLabel.setText(file.toString());}
            else{
            SaveLabel.setText("No file selected");}
            
    }
    
    @FXML
    public void handleNotFoundButtonAction(ActionEvent e){
         System.out.println("Not Found button");
        FileChooser fileChooser = new FileChooser();
        Stage stage=new Stage();
        fileChooser.setTitle("Selct file for Not Found scan");
        //fileChooser.showOpenDialog(stage);
        fileChooser.getExtensionFilters().addAll( 
           new FileChooser.ExtensionFilter("*.wav", "*.wav"),
            new FileChooser.ExtensionFilter("*.mp3", "*.mp3"), 
            new FileChooser.ExtensionFilter("*.arc", "*.arc"));
            File file = fileChooser.showOpenDialog(stage);
            System.out.println(file);
             if (file != null) { 
                 NotFoundLabel.setText(" ");
            NotFoundLabel.setText(file.toString());}
            else{
            NotFoundLabel.setText("No file selected");}
    }
    @FXML
    public void handlePromptButtonAction(ActionEvent e){
         System.out.println("Prompt price button");
        FileChooser fileChooser = new FileChooser();
        Stage stage=new Stage();
        fileChooser.setTitle("Select file for prompt price scan");
        //fileChooser.showOpenDialog(stage);
        fileChooser.getExtensionFilters().addAll( 
           new FileChooser.ExtensionFilter("*.wav", "*.wav"),
            new FileChooser.ExtensionFilter("*.mp3", "*.mp3"), 
            new FileChooser.ExtensionFilter("*.arc", "*.arc"));
            File file = fileChooser.showOpenDialog(stage);
            System.out.println(file);
            if (file != null) { 
                PromptLabel.setText(" ");
            PromptLabel.setText(file.toString());}
            else{
            PromptLabel.setText("No file selected");}
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       // StartDate.setValue(LocalDate.now());
       addoncol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("AddOn"));
      valuecol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Value"));
      percol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("per"));
      taxcol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<Person,String>("Tax"));
      MongoCursor<Document> cursor =  db.getCollection("PriceDetail").find().iterator();
            try {
            while (cursor.hasNext()) {                
                Document i=cursor.next();
                String name=i.getString("ChargeName");
                String val=i.getString("ChargeValue");
                String per="No";
                String tax="No";
               if(i.getInteger("PercentageCheck")==1){
                    per="Yes";
                }
                else{
                    per="No";
                }
                if(i.getInteger("Tax")==1){
                    tax="Yes";
                }
                else{
                    tax="No";
                }
                
                data.add(new Person(name, val, per,tax));
            }
            table.setItems(data);
        } finally {
            cursor.close();
        }   
       table.setItems(data);
      /*AddOnCol.setCellValueFactory(new PropertyValueFactory<Person, String>("AddOn Name1"));
      TaxCol.setCellValueFactory(new PropertyValueFactory<Person, String>("Tax Ex"));
      ValueCol.setCellValueFactory(new PropertyValueFactory<Person, String>("value"));
      ByPercentageCol.setCellValueFactory(new PropertyValueFactory<Person, String>("percentage"));*/
       MongoCursor<Document> cursor4 =  db.getCollection("DeptDetail").find().iterator();
            try {
            while (cursor4.hasNext()) {                
                String rs=cursor4.next().getString("Name");
                DeptCombo2.setValue(rs);
                DeptCombo1.setValue(rs);
                DeptCombo3.setValue(rs);
                DeptCombo2.getItems().addAll(rs);    
                DeptCombo1.getItems().addAll(rs);      
                DeptCombo3.getItems().addAll(rs);      
            }
        } finally {
            cursor4.close();
        }   
        
      //  spinner = new Spinner(0, 120, 30);
       DocFlavor myFormat = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        PrintService[] services =PrintServiceLookup.lookupPrintServices(myFormat, aset);
        System.out.println("The following printers are available ");
        if(services.length==0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error ");
                alert.setContentText("No printers are available");
                alert.showAndWait();
        }
        else{
        for (int i=0;i<services.length;i++) {
            System.out.println("  service name: "+services[i].getName());
            PrinterCombo.getItems().add(
                    services[i].getName()
            );
            PrinterCombo1.getItems().add(
                    services[i].getName()
            );
        }
           PrinterCombo.setValue(services[0].getName());
           PrinterCombo1.setValue(services[0].getName());
        }
    } 
        
    public static class Person {

        private final SimpleStringProperty first;
        private final SimpleStringProperty second;
        private final SimpleStringProperty third;
         private final SimpleStringProperty four;
        private Person(String uName, String val, String type,String nc) {
            first = new SimpleStringProperty(uName);
            second = new SimpleStringProperty(val);
            third = new SimpleStringProperty(type);
            four = new SimpleStringProperty(nc);
            System.out.println("person Data "+first +" "+second+" "+third+" "+four);
            System.out.println("data pass in constructor are "+uName +" "+val+" "+type+" "+nc);
        }

        public String getAddOn() {
            return first.get();
        }

        public void setAddOn(String uName) {
            first.set(uName);
        }

        public String getValue() {
            return second.get();
        }

        public void setValue(String val) {
            second.set(val);
        }

        public String getPer() {
            return third.get();
        }

        public void setPer(String type) {
            third.set(type);
        }
        
        public String getTax(){
            return four.get();
        }
        
        public void setTax(String nc){
            four.set(nc);
        }
    }
    
} 



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;


import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bson.Document;

/**
 * FXML Controller class
 *
 * @author Third Ev
 */
public class PriceAddonnController implements Initializable {
    int GlobalFlag=0;
    @FXML
    public TextField ChargeNameText,ChargeValue;
    String var="hello";
    @FXML
    public CheckBox InactiveCheck,TaxCheck;
    @FXML
    RadioButton PercentageRadio,AmountRadio;
    Document updateDocument ;
    @FXML
    Button SaveButton;
    static int IFlag=0,TFlag=0,cost1=0,cost2=0,dummy=1;
    int repeat=0;
    public static String chrgenm="",chrgeval="";
    MongoClient client = new MongoClient();
    public static String addon="";
    MongoDatabase  db=client.getDatabase("FinalDemo");
    MongoCollection<Document> col=db.getCollection("PriceDetail");
    BasicDBObject ref;
    Document up=new Document();
   /* private ObservableList<Person1> data = FXCollections.observableArrayList();
    FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("Sys1.fxml"));
    Sys1Controller controller=fxmlLoader1.<Sys1Controller>getController();*/
       
    @FXML
    public void handleAddOnButtonAction(ActionEvent a) throws UnknownHostException{   
        
        ref=new BasicDBObject();
        ref.put("Name", Pattern.compile(ChargeNameText.getText() , Pattern.CASE_INSENSITIVE));
        MongoCursor<Document> myDoc2 =  col.find(ref).iterator();
        if(myDoc2.hasNext()){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error ");
           alert.setContentText("ChargeName Already exist!");
           alert.showAndWait();
           ChargeNameText.clear();
           ChargeValue.clear();
           repeat=1;
       }
       else{
           repeat=0;
       }
       if(repeat==0 && GlobalFlag==0){
                InsertMongo();
               // InsertSQl(conn);
       }      
       if(GlobalFlag==1){
          /*db.getCollection("PriceDetail").updateOne(eq("ChargeName", addon), up.append("$set", new Document("ChargeName", ChargeNameText.getText())));
          db.getCollection("PriceDetail").updateOne(eq("ChargeName", addon), up.append("$set", new Document("ChargeValue", ChargeValue.getText())));
          db.getCollection("PriceDetail").updateOne(eq("ChargeName", addon),new Document(var, var)*/
          BasicDBObject b=new BasicDBObject();
       	b.put("ChargeName", addon);
        
	MongoCursor<Document> cursor = db.getCollection("PriceDetail").find(b).iterator();
        
            while (cursor.hasNext()) {               
            Document d1 = cursor.next();
            System.out.println("output is "+d1.toString());
            
            if(PercentageRadio.isSelected()){
           cost1=1;
       }
       if(AmountRadio.isSelected()){
           cost2=1;
       }
       
       if(InactiveCheck.isSelected()){
           IFlag=1;
       }
       if(TaxCheck.isSelected()){
           TFlag=1;
       }
        
          /*  updateDocument.put("ChargeName",ChargeNameText.getText());            
            updateDocument.put("ChargeValue", ChargeValue.getText());
            updateDocument.put("PercentageCheck", cost1);
       updateDocument.put("Amountcheck", cost2);
        updateDocument.put("Inactive", IFlag);
        updateDocument.put("Tax", TFlag);        */
      updateDocument.put("$set", up.append("ChargeName", ChargeNameText.getText()));
       updateDocument.put("$set", up.append("ChargeValue", ChargeValue.getText()));
       updateDocument.put("$set", up.append("PercentageCheck", cost1));
       updateDocument.put("$set", up.append("Amountcheck", cost2));
       updateDocument.put("$set", up.append("Inactive", IFlag));
         updateDocument.put("$set", up.append("Tax", TFlag));
    
       db.getCollection("PriceDetail").updateOne(d1, up);
		//MongoUtils.printAllDocument("founder", "mongodb");
		 
      
          //  db.getCollection("PriceDetail").updateOne(d1,updateDocument);

            }
       }
       System.out.println("global value is "+GlobalFlag);
        Stage stage = (Stage) SaveButton.getScene().getWindow();     
    stage.close();   
    }
   
    @FXML 
    public void handleCancelButtonAction(ActionEvent a){
        System.out.println("close button");
        //((Node)(a.getSource())).getScene().getWindow().hide();
        Stage stage = (Stage) SaveButton.getScene().getWindow();            
        stage.close();
        
    }
    public void EditButton(String para1,String para2,String para3,String para4){
        addon=para1;
        GlobalFlag=1;
        System.out.println("global value is edit button"+GlobalFlag);
        int op=0,amt=0;
        System.out.println("Percentage "+para3+ " Tax "+para4);
        BasicDBObject b=new BasicDBObject();
       	b.put("ChargeName", para1);
	MongoCursor<Document> cursor = db.getCollection("PriceDetail").find(b).iterator();
        try {
            while (cursor.hasNext()) {
                 Document d=cursor.next();
                 amt=d.getInteger("Amountcheck");
                 op=d.getInteger("Inactive");
            }
        } finally {
            cursor.close();
        }
        if(amt==1){
            AmountRadio.setSelected(true);
        }
        else{
            AmountRadio.setSelected(false);
        }
        if(op==1){
            InactiveCheck.setSelected(true);
        }
        else{
            InactiveCheck.setSelected(false);
        }
        if(para3.equals("Yes")){
            PercentageRadio.setSelected(true);
            System.out.println("Percentage should be checked ");
        }
        else
        {
            PercentageRadio.setSelected(false);
        }
        if(para4.equals("Yes")){
            TaxCheck.setSelected(true);
            System.out.println("CheckBox should check");
        }
        else{
            TaxCheck.setSelected(false);
        }
        ChargeNameText.setText(para1);
        ChargeValue.setText(para2);
    }
    public void getData(){            
       System.out.println("Charge Name is "+chrgenm);
       System.out.println("Charge Value is "+chrgeval);
       if(PercentageRadio.isSelected()){
           cost1=1;
       }
       if(AmountRadio.isSelected()){
           cost2=1;
       }
       
       if(InactiveCheck.isSelected()){
           IFlag=1;
       }
       if(TaxCheck.isSelected()){
           TFlag=1;
       }
       System.out.println("Cost is "+cost1+"Inactive chek "+IFlag+"TaxCheck is "+TFlag);
   }
   public void close(){       
      //((Node)(a.getSource())).getScene().getWindow().hide();
        Stage stage = (Stage) SaveButton.getScene().getWindow();     
    stage.close();        
    }
   public void InsertSQL(){
        try {          
            Stage  stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Sys1.fxml"));
            Parent root44 = (Parent) fxmlLoader.load();   
            Sys1Controller controller=fxmlLoader.<Sys1Controller>getController();             
            stage.setScene(new Scene(root44));     
            stage.setTitle("System Setting");
            chrgenm=ChargeNameText.getText().toString();
            chrgeval=ChargeValue.getText().toString();
            java.sql.Connection conn=Connection.getConnect();  
            String Add1="INSERT INTO demo " 
                    +"(`ChargeName`,`ChargeValue`) "
                    + "VALUES (?,?)";          
            PreparedStatement ReceiptQuery=conn.prepareStatement(Add1);
            ReceiptQuery.setString(1, chrgenm);
            ReceiptQuery.setString(2, chrgeval);
            ReceiptQuery.executeUpdate();
            ChargeNameText.clear();
            ChargeValue.clear();
            Stage stage1 = (Stage) SaveButton.getScene().getWindow();   
            stage1.close();
            System.out.println("call function");
            controller.priceadd();
        } catch (IOException ex) {
            Logger.getLogger(Sys1Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PriceAddonnController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PriceAddonnController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
   }
   public void UpdateMongo(){
       try{
       System.out.println("global 1 update called");
       GlobalFlag=1;
      // db.getCollection("PriceDetail").updateOne(eq("ChargeName", addon), new Document("$set", new Document("ChargeName", ChargeNameText.getText())));
   /*   BasicDBObject b=new BasicDBObject();
       	b.put("ChargeName", addon);
        
	MongoCursor<Document> cursor = db.getCollection("PriceDetail").find(b).iterator();
        
            while (cursor.hasNext()) {               
            Document d1 = cursor.next();
            System.out.println("output is "+d1.toString());
            Document updateDocument = d1;
            if(PercentageRadio.isSelected()){
           cost1=1;
       }
       if(AmountRadio.isSelected()){
           cost2=1;
       }
       
       if(InactiveCheck.isSelected()){
           IFlag=1;
       }
       if(TaxCheck.isSelected()){
           TFlag=1;
       }
            updateDocument.put("ChargeName",ChargeNameText.getText());            
            updateDocument.put("ChargeValue", ChargeValue.getText());
            updateDocument.put("PercentageCheck", cost1);
       updateDocument.put("Amountcheck", cost2);
        updateDocument.put("Inactive", IFlag);
        updateDocument.put("Tax", TFlag);        
            //col1.update(updateDocument); 
            db.getCollection("PriceDetail").updateOne(d1,updateDocument);
            }*/
   }
        catch(Exception e){
	     System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	  }
      
 }
        
    public void InsertMongo() throws UnknownHostException{
            if(GlobalFlag==1){
                 UpdateMongo();      
                 
            }
            //MongoInsert    
            else{
            final Document seedData = createSeedData();
            col.insertOne(seedData); 
            ChargeNameText.clear();
            ChargeValue.clear();
            }
    }
     public  Document createSeedData(){      
          if(PercentageRadio.isSelected()){
           cost1=1;
       }
       if(AmountRadio.isSelected()){
           cost2=1;
       }
       
       if(InactiveCheck.isSelected()){
           IFlag=1;
       }
       if(TaxCheck.isSelected()){
           TFlag=1;
       }
        Document d=new Document();
        d.append("ChargeName", ChargeNameText.getText());
        d.append("ChargeValue", ChargeValue.getText());
        d.append("PercentageCheck", cost1);
        d.append("Amountcheck", cost2);
        d.append("Inactive", IFlag);
        d.append("Tax", TFlag);        
        return d;        
    }
    
 
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        } 
}



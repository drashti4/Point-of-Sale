/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.excludeId;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bson.Document;

/**
 * FXML Controller class
 *
 * @author Third Ev
 */
public class AddUser8Controller implements Initializable {
    @FXML TextField fname,lname,add1,add2,city,state,country,phone,zip,ext,email,uid,pwd,cpwd;
    @FXML Button Save;
    @FXML ComboBox roleCombo,GenderCombo;
    int count;
    static int EID=1,UID=1,PID=0;
   
    BasicDBObject sort1,sort2;
    Document seedData;
    BasicDBObject ref;
    int GlobalFlag=0;
    MongoClient client = new MongoClient();
    MongoDatabase  db = client.getDatabase("FinalDemo");
    BasicDBObject condition;
    static String UserId;
    MongoCollection<Document> col = db.getCollection("EmployeeDetail");
    @FXML
    public void handleSaveButtonAction(ActionEvent a){
        InsertMongo();
        fname.clear();
        lname.clear();
        add1.clear();
        add2.clear();
        city.clear();
        state.clear();
        country.clear();
        phone.clear();
        zip.clear();
        ext.clear();
        email.clear();
        uid.clear();
        pwd.clear();
        cpwd.clear();        
    }
    private void InsertMongo(){
         if(GlobalFlag==1){
                 UpdateMongo();    
                 
            }
         else{
        count= (int) col.count();
        if(count==0){
            seedData = createSeedData();
            col.insertOne(seedData);
        }
        else{        
        sort1=new BasicDBObject();        
	MongoCursor<Document> cursor = col.find().sort(sort1).limit(1).skip((int)count-1).iterator();        
        try {
            while (cursor.hasNext()) {                
                EID=cursor.next().getInteger("ID");
                System.out.println("last EID "+EID);
                EID++;
                System.out.println("Inserted "+EID);
            }
        } finally {
            cursor.close();
        }
        final Document seedData = createSeedData();
        col.insertOne(seedData);
        }
    }
    }
    public void EditButton(int para1,String para2,String para3,String para4,String para5){
        GlobalFlag=1;
        UserId=para4;
        fname.setText(para2);
        lname.setText(para3);
        uid.setText(para4);
        phone.setText(para5);
        sort2=new BasicDBObject();    
        sort2.put("FirstName", para2);
        sort2.put("LastName", para3);
        sort2.put("UserEID",para4);
	MongoCursor<Document> cursor = col.find(sort2).iterator();      
         while (cursor.hasNext()) {       
         Document dr=cursor.next();
         add1.setText(dr.getString("Address1"));
         add2.setText(dr.getString("Address2"));
         city.setText(dr.getString("City"));
         state.setText(dr.getString("State"));
         country.setText(dr.getString("Country"));
         zip.setText(dr.getString("ZIP"));
         ext.setText(dr.getString("EXT"));
         pwd.setText(dr.getString("Password"));
         cpwd.setText(dr.getString("ConfirmPWD"));
         roleCombo.setValue(dr.getString("Role"));
         GenderCombo.setValue(dr.getString("Gender"));
         email.setText(dr.getString("Email"));
         
        condition=new BasicDBObject();    
         condition.put("UserEID", para4);
         System.out.println("Value of UserEID is "+para4);
       MongoCursor<Document> cursor4 = col.find(condition).iterator();      
         while (cursor4.hasNext()) {       
              
             
             PID=cursor4.next().getInteger("ID");
              //UID=cursor4.next().getInteger("ID");
             System.out.println("Updated Selected _id is "+PID+ "IUd is "+UID);
         }
      }
    
    }
    private Document createSeedData(){
        Document d=new Document();
         System.out.println("Id is "+EID);
        d.append("ID",  EID);    
        d.append("FirstName",fname.getText() );
        d.append("LastName", lname.getText());
        d.append("Gender", GenderCombo.getValue());
        d.append("Address1", add1.getText());
        d.append("Address2", add2.getText());
        d.append("City", city.getText());
        d.append("State", state.getText());
        d.append("Country", country.getText());
        d.append("ZIP",zip.getText() );
        d.append("EXT", ext.getText());
        d.append("Phone",phone.getText() );
        d.append("Email",email.getText() );
        d.append("UserEID", uid.getText());
        if((pwd.getText()).equals(cpwd.getText())){
        d.append("Password", pwd.getText());
        d.append("ConfirmPWD",cpwd.getText() );}        
        d.append("Role", roleCombo.getValue());
        // d.append("EID",  EID);    
       
        
        return d;
    }
     private Document UpdateSeedData(){
        Document d1=new Document();       
        
         d1.append("ID",  PID);    
        d1.append("FirstName",fname.getText() );
        d1.append("LastName", lname.getText());
        d1.append("Gender", GenderCombo.getValue());
        d1.append("Address1", add1.getText());
        d1.append("Address2", add2.getText());
        d1.append("City", city.getText());
        d1.append("State", state.getText());
        d1.append("Country", country.getText());
        d1.append("ZIP",zip.getText() );
        d1.append("EXT", ext.getText());
        d1.append("Phone",phone.getText() );
        d1.append("Email",email.getText() );
        d1.append("UserEID", uid.getText());
        if((pwd.getText()).equals(cpwd.getText())){
        d1.append("Password", pwd.getText());
        d1.append("ConfirmPWD",cpwd.getText() );}        
        d1.append("Role", roleCombo.getValue());
        // d.append("EID",  EID);    
         System.out.println(" "+PID+" "+UID+" "+fname.getText()+" "+lname.getText()+" "+uid.getText());
       
        
        return d1;
    }
    public void UpdateMongo(){
        
       System.out.println("global 1 update called");
     /*  condition=new BasicDBObject();    
       condition.put("UserEID", uid.getText());
        
       MongoCursor<Document> cursor = col.find(condition).iterator();      
         while (cursor.hasNext()) {       
              System.out.println("Updated Selected _id is "+Eid);
             Eid=cursor.next().getObjectId("_id").toString();
             System.out.println("Updated Selected _id is "+Eid);
         }*/
        MongoCursor<Document> cursor4 = col.find().iterator();
         try {
            while (cursor4.hasNext()) {
                System.out.println("ID=> "+cursor4.next().getString("UserEID"));                
            }
        } finally {
            cursor4.close();
        }       
     //  col.updateOne(eq("_id",Eid), new Document("$set",new Document("UserEID","U25")) );
       //col.replaceOne(eq("_id",Eid),UpdateSeedData());
         System.out.println("Updated Selected _id is "+PID);
         col.updateOne(new Document("ID", PID),
         new Document("$set", UpdateSeedData()));
            //.append("$currentDate", new Document("lastModified", true)));
        MongoCursor<Document> cursor5 = col.find().iterator();
         try {
            while (cursor5.hasNext()) {
                System.out.println("After UpdateID=> "+cursor5.next().getString("UserEID"));
                
            }
        } finally {
            cursor5.close();
        }
       
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
    @FXML
    public void handleCancelButtonAction(ActionEvent a){
        Stage f=(Stage) Save.getScene().getWindow();
        f.close();
        
    }
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        
        
        MongoCursor<Document> cursor4 = db.getCollection("SecurityRole").find().iterator();
            try {
            while (cursor4.hasNext()) {                
                String rs=cursor4.next().getString("Role");
                roleCombo.setValue(rs);
                roleCombo.getItems().addAll(rs); 
            }
        } finally {
            cursor4.close();
        }  
    }    
    
}

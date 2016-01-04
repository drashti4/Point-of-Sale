/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import com.mongodb.BasicDBObject;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mongodb.MongoClient;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import java.io.IOException;
import java.text.ParseException;
import java.util.regex.Pattern;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
public class AddDeptController implements Initializable {
    @FXML
    public  TextField DeptName,LocalName,value;
    @FXML
    ToggleGroup t1;
    @FXML 
    public  RadioButton Margin,MarkUp;
    @FXML     public  CheckBox Taxable,Price,FoodStamp;
    public  int rFlag1=0,rFlag2=0,tFlag=0,pFlag=0,fFlag=0;
    public int repeat=0;
    int count;
    @FXML Button Save;
    static String nm="borough";
    java.sql.Connection conn;
    MongoClient client = new MongoClient();
    MongoDatabase  db = client.getDatabase("FinalDemo");
    static int ID=1;
    Document seedData;
    BasicDBObject ref;
    BasicDBObject sort1;
  //MongoCollection  col = db.getCollection("DeptDetail");
    MongoCollection<Document> col = db.getCollection("DeptDetail");
    MongoCollection<Document> col2 =db.getCollection("restaurants");
  //MongoCollection<BasicDBObject> col1=db.getCollection("CategoryDetail");
    MongoCollection<BasicDBObject> col1 = db.getCollection("CategoryDetail", BasicDBObject.class);   
    @FXML
    public void handleSaveButtonAction(ActionEvent a) throws SQLException, ClassNotFoundException, ParseException, IOException{                
            System.out.println("You clicked me!");                   
           // DBCursor cursor = col.find();
           // int i=1;
            /*while (cursor.hasNext()) { 
            System.out.println("Inserted Document: "+i); 
            System.out.println(cursor.next()); 
            i++;
            }*/
            
           /* Iterator<DBObject> fields = cursor.iterator(); 
            while(fields.hasNext()){ //
            BasicDBList geoList = (BasicDBList) fields.next().get("Name");
            BasicDBObject object = (BasicDBObject) geoList.get(0); // this should return {"5": "Continent_Name"}
            Object value = object.get("Name"); // value should contain "Continent_Name"
                System.out.println("Colum name value is "+value);
            }*/        
           /* String qry1="select name from dept_detail";
            conn=Connection.getConnect();            
            PreparedStatement pst1=conn.prepareStatement(qry1);
            ResultSet rs1 = pst1.executeQuery(qry1);
            while(rs1.next()){
              if(DeptName.getText().equals(rs1.getString("name"))){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error ");
                alert.setContentText("Department already exist!");
                alert.showAndWait();
                LocalName.clear();
                DeptName.clear();
                value.clear();
                repeat=1;
                break;
              }               
            }  */      
    
            //MongoCursor<Document> myDoc2 =  col.find(eq("Name",DeptName.getText())).iterator();            
            ref=new BasicDBObject();
            ref.put("Name", Pattern.compile(DeptName.getText() , Pattern.CASE_INSENSITIVE));            
            MongoCursor<Document> myDoc2 =  col.find(ref).iterator();            
            if(myDoc2.hasNext()){
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error ");
                alert.setContentText("Department already exist!");
                alert.showAndWait();
                LocalName.clear();
                DeptName.clear();
                value.clear();
                repeat=1;                
            }
            else{
                repeat=0;
            }
            if(repeat==0){
                InsertMongo();
               // InsertSQl(conn);
            }
    }
    public String getData(){
        return nm;
    }
    
    public void InsertMongo() throws ParseException, IOException{
        if(Margin.isSelected()){ rFlag1=1; }
        else{rFlag1=0;}
        if(MarkUp.isSelected()){ rFlag2=1; }
        else{rFlag2=0;}
        if(Taxable.isSelected()){ tFlag=1;}
        else{tFlag=0;}
        if(Price.isSelected()){ pFlag=1;}
        else{pFlag=0;}
        if(FoodStamp.isSelected()){ fFlag=1;}
        else{fFlag=0;}       
        
       /* DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        db.getCollection("restaurants").insertOne(  new Document("address",
			                new Document()
			                        .append("street", "2 Avenue")
			                        .append("zipcode", "10075")
			                        .append("building", "1480")
			                        .append("coord", asList(-73.9557413, 40.7720266)))
			                .append("borough", "Manhattan")
			                .append("cuisine", "Italian")
			                .append("grades", asList(
			                        new Document()
			                                .append("date", format.parse("2014-10-01T00:00:00Z"))
			                                .append("grade", "A")
			                                .append("score", 11),
			                        new Document()
			                                .append("date", format.parse("2014-01-16T00:00:00Z"))
			                                .append("grade", "B")
			                                .append("score", 17)))
			                .append("name", "Vella")
			                .append("restaurant_id", "41704620"));
        System.out.println("Data are Inserted");*/
        //Find all document
         /*b=new BasicDBObject();
         b.put("Name", "sdfg");
        MongoCursor<Document> cursor = db.getCollection("CategoryDetail").find(b).iterator();
        try {
            while (cursor.hasNext()) {
                System.out.println("Searched Output "+cursor.next().toJson() );
                
            }
        } finally {
            cursor.close();
        }*/
       /* MongoCursor<Document> cursor4 = db.getCollection("CategoryDetail").find().iterator();
         try {
            while (cursor4.hasNext()) {
                System.out.println("column val is "+cursor4.next().getString("LocalName"));
                
            }
        } finally {
            cursor.close();
        }*/
         //Find First Document
        
     /*   Document myDoc = db.getCollection("restaurants").find().first();
        System.out.println("First Column is "+myDoc.toJson());*/
        //Where Clause
        /*Document myDoc = db.getCollection("restaurants").find(eq("borough", "ManhattanFirst")).first();
        System.out.println("sd" +myDoc.toJson());*/  
        //col.find({}).sort({_id:-1}).limit(1);
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
                ID=cursor.next().getInteger("ID");
                System.out.println("last ID "+ID);
                ID++;
                System.out.println("Inserted "+ID);
            }
        } finally {
            cursor.close();
        }
        final Document seedData = createSeedData();
        col.insertOne(seedData);
        
        }
        /*count= (long) col.count();
	MongoCursor<Document> cursor = col.find().sort(sort1).limit(1).skip((int)count-1).iterator();        
        try {
            while (cursor.hasNext()) {
                System.out.println("Searched Output "+cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }*/
        
       /* MongoCursor<Document> cursor = col.find().limit(3).sort({timestamp:-1}); 
        try {
            while (cursor.hasNext()) {
                System.out.println("Searched Output "+cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }*/
       Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SelectDept.fxml"));
            Parent root4=(Parent) fxmlLoader.load();
            SelectDeptController controller=fxmlLoader.<SelectDeptController>getController();
            stage.setScene(new Scene(root4));
            stage.setTitle("Select Department");
            controller.update();
        LocalName.clear();
        DeptName.clear();
        value.clear();        
    }
    
    public  Document createSeedData(){        
        Document d=new Document();
        d.append("Name", DeptName.getText());
        d.append("LocalName", LocalName.getText());
        d.append("Taxable", tFlag);
        d.append("FoodStamp", fFlag);
        d.append("Price", pFlag);
        d.append("Margin", rFlag1);
        d.append("MarkUp", rFlag2);
        d.append("Value",  value.getText());  
        d.append("ID",  ID);         
        System.out.println("Id is "+ID);
    
        return d;
    }     
    @FXML
    public void handleSaveCloseButtonAction(ActionEvent e) throws ParseException{
     
            /* t1.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
            
            RadioButton chk = (RadioButton)t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
            System.out.println("Selected Radio Button - "+chk.getText());
            
            }
            });*/
            
            /*java.sql.Connection conn=Connection.getConnect();
            String Add1="INSERT INTO dept_detail " 
                    +"(`name`,`local_name`,taxable,foodStamp,Price,margin,markup,value) "
                    + "VALUES (?,?,?,?,?,?,?,?)";          
            PreparedStatement ReceiptQuery=conn.prepareStatement(Add1);
            ReceiptQuery.setString(1, DeptName.getText().toString());
            ReceiptQuery.setString(2, LocalName.getText().toString());
            ReceiptQuery.setInt(3, tFlag);
            ReceiptQuery.setInt(4, fFlag);
            ReceiptQuery.setInt(5, pFlag);
            ReceiptQuery.setInt(6, rFlag1);
            ReceiptQuery.setInt(7, rFlag2);
            ReceiptQuery.setString(8, value.getText().toString());       
            ReceiptQuery.executeUpdate();*/
            ref=new BasicDBObject();
            ref.put("Name", Pattern.compile(DeptName.getText() , Pattern.CASE_INSENSITIVE));            
            MongoCursor<Document> myDoc2 =  col.find(ref).iterator();            
            if(myDoc2.hasNext()){
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error ");
                alert.setContentText("Department already exist!");
                alert.showAndWait();
                LocalName.clear();
                DeptName.clear();
                value.clear();
                repeat=1;                
            }
            else{
                repeat=0;
            }
            if(repeat==0){
                try {
                    InsertMongo();
                    // InsertSQl(conn);
                } catch (IOException ex) {
                    Logger.getLogger(AddDeptController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Stage stage=(Stage) Save.getScene().getWindow();
            stage.close();                
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    private void InsertSQl(java.sql.Connection conn) {
         try
        {
            if(Margin.isSelected()){ rFlag1=1; }
            else{rFlag1=0;}
            if(MarkUp.isSelected()){ rFlag2=1; }
            else{rFlag2=0;}
            if(Taxable.isSelected()){ tFlag=1;}
            else{tFlag=0;}
            if(Price.isSelected()){ pFlag=1;}
            else{pFlag=0;}
            if(FoodStamp.isSelected()){ fFlag=1;}
            else{fFlag=0;}
            
            String Add1="INSERT INTO dept_detail " 
                    +"(`name`,`local_name`,taxable,foodStamp,Price,margin,markup,value) "
                    + "VALUES (?,?,?,?,?,?,?,?)";          
            PreparedStatement ReceiptQuery=conn.prepareStatement(Add1);
            ReceiptQuery.setString(1, DeptName.getText().toString());
            ReceiptQuery.setString(2, LocalName.getText().toString());
            ReceiptQuery.setInt(3, tFlag);
            ReceiptQuery.setInt(4, fFlag);
            ReceiptQuery.setInt(5, pFlag);
            ReceiptQuery.setInt(6, rFlag1);
            ReceiptQuery.setInt(7, rFlag2);
            ReceiptQuery.setString(8, value.getText().toString());            
            nm=DeptName.getText().toString();
            ReceiptQuery.executeUpdate();                              
            LocalName.clear();
            DeptName.clear();
            value.clear();
            /*Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SelectDept.fxml"));
            Parent root4=(Parent) fxmlLoader.load();
            SelectDeptController controller=fxmlLoader.<SelectDeptController>getController();
            stage.setScene(new Scene(root4));
            controller.update();*/
        } catch (SQLException ex) {
            Logger.getLogger(AddDeptController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    
    
}

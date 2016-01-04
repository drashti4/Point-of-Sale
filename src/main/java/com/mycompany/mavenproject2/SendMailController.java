/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class SendMailController implements Initializable  {
    String var="hell";
    public void print(){
        System.out.println("thi sis printin function");
         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Sys1.fxml"));
      /*      Parent root1 = (Parent) fxmlLoader.load();//must here even if not used othrwse null pointer exception
            Sys1Controller controller=fxmlLoader.<Sys1Controller>getController();       
        controller.process.progressProperty().bind(worker.progressProperty());*/
    }
   /* public Task createWorker() {
        return new Task() {
            // call() should be overridden

            @Override
            protected Object call() throws Exception {
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(2000);
                  //updateMessage() is used to pass the values to be printed on screen or console 
                    updateMessage("2000 milliseconds");
                  //this is used to show the progress
                    updateProgress(i + 1, 10);
                }
                 worker.cancel(true);
                return true;
            }
        };
    }*/
    public void SendMail(String server_name,int port,final String user_name,final String password,String sender_name,String from_add,String to_add,int sec) throws IOException{
        try {
           
                   //String[] attachFiles = new String[3];
            //attachFiles[0] = "C:/Users/Third Ev/Documents/NetBeansProjects/JavaFXApplication12/src/Email/backup.png";
            //attachFiles[1] = "C:/Users/Third Ev/Documents/NetBeansProjects/JavaFXApplication12/src/Email/clock.png";
            //      attachFiles[2] = "C:/Users/Third Ev/Documents/NetBeansProjects/JavaFXApplication12/src/Email/close.png";
          
            Properties properties = new Properties();
            properties.put("mail.smtp.host",server_name );//server name "mail.germanium.co.in"
            properties.put("mail.smtp.port", port);//port 25
            properties.put("mail.smtp.auth", "true");            
            properties.put("mail.user",user_name );//User name "drashti@germanium.co.in"
            properties.put("mail.password", password);//password 123456
            //if you want to apply ssl
            if(sec==1){
            properties.put("mail.smtp.socketFactory.port", port);
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp.socketFactory.fallback", "false");
            }
            else if(sec==2){
                properties.put("mail.smtp.starttls.enable", "true");
            }
            else{
                
            }
            Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user_name, password);
                }
            };
            Session session = Session.getInstance(properties, auth);
            Session.getDefaultInstance(properties);
            // creates a new e-mail message
            Message msg = new MimeMessage(session);
            
            try {
                msg.setFrom(new InternetAddress(from_add,sender_name));//From Address,sender name
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(SendMailController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            InternetAddress[] toAddresses = { new InternetAddress(to_add) };//to
            msg.setRecipients(Message.RecipientType.TO, toAddresses);
            msg.setSubject("Test Mail");
            msg.setSentDate(new Date());
            
            // creates message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent("Test Mail send", "text/html");
            
            
            // creates multi-part
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
           /* if (attachFiles != null && attachFiles.length >= 0) {
                
                //for(int counter=0;counter<attachFiles.length;counter++)
                // {
                for (String filePath : attachFiles) {
                    if(filePath!=null)
                    {
                        MimeBodyPart attachPart = new MimeBodyPart();
                        
                        try {
                            attachPart.attachFile(filePath);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        
                        multipart.addBodyPart(attachPart);
                    }
                }
            }*/
            Transport transport = session.getTransport("smtp");
            msg.setContent(multipart);
           // System.out.println("user name"+user_name+" port"+port+" server_name"+server_name+" password"+password+" sec"+sec);
            transport.connect(server_name, port, user_name, password);
            // sends the e-mail
            transport.send(msg);           
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Successfully Send");
            alert1.setHeaderText(null)  ;
            alert1.setContentText("Your mail is sucessfully sent!");
            alert1.showAndWait();
            
            System.out.print("send");
        } catch (MessagingException ex) {
            Logger.getLogger(SendMailController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Intialize");
    }    
    
}

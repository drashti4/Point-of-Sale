/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
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
import com.mycompany.mavenproject2.Sys1Controller;
/**
 *
 * @author Third Ev
 */
public class demo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MessagingException {
        String[] attachFiles = new String[3];
        attachFiles[0] = "C:/Users/Third Ev/Documents/NetBeansProjects/JavaFXApplication12/src/Email/backup.png";
        attachFiles[1] = "C:/Users/Third Ev/Documents/NetBeansProjects/JavaFXApplication12/src/Email/clock.png";
 //      attachFiles[2] = "C:/Users/Third Ev/Documents/NetBeansProjects/JavaFXApplication12/src/Email/close.png";
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "mail.germanium.co.in");
        properties.put("mail.smtp.port", 25);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", "drashti@germanium.co.in");
        properties.put("mail.password", 123456);
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("drashti@germanium.co.in", "123456");
            }
        };
        Session session = Session.getInstance(properties, auth);
 
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
 
        msg.setFrom(new InternetAddress("drashti@germanium.co.in"));
        InternetAddress[] toAddresses = { new InternetAddress("drashtipandya37@gmail.com") };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject("Java Mail");
        msg.setSentDate(new Date());
 
        // creates message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("i send this mail through java", "text/html");
        
 
        // creates multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        if (attachFiles != null && attachFiles.length >= 0) {
        
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
        }
        Transport transport = session.getTransport("smtp"); 
        msg.setContent(multipart);
        transport.connect("mail.germanium.co.in", 25, "drashti@germanium.co.in", "123456"); 
        // sends the e-mail
        transport.send(msg);
     
        
        
        System.out.print("send");
    }
   
    
}

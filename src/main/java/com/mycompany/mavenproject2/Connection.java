/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Third Ev
 */
public class Connection {
    public static java.sql.Connection getConnect() throws ClassNotFoundException, SQLException {
    //public static void main(String a[])throws ClassNotFoundException, SQLException {

	String url1="jdbc:mysql://localhost:3306/test1";
    String user1="root";
    String pwd1="root";      
	Class.forName("com.mysql.jdbc.Driver");
	//System.out.println("driver loaded");
	java.sql.Connection con=DriverManager.getConnection(url1, user1, pwd1);
	//System.out.println("connectin fired");
  return con;
	
	
}
    
}

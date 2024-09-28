package com.jdbcConcepts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

public class HowToDealWithDatabases {
	
	//static block runs even before main method
	static {
		try {
			//load the driver for database
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException e) {
			//print if the driver is not loaded
			System.out.println("unable to load driver class");
		}
	}
	
	public static void main(String[] args) {
		//create connection object for connection
		//statement object for queries
		//resultset object for fetching the output after querying
		//savepoint for saving in case of any unexpected ending of program.
		//everything comes from java.sql package
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		Savepoint savePoint=null;
		try {
			//pass in the url for connecting to database
			//pass in username for database
			//pass in password for db
			String url="jdbc:oracle:thin:@localhost:1521:xe";
			String userName="SYSTEM";
			String password="Pass1234";
			
			//establish connection using drivermanager class
			conn=DriverManager.getConnection(url, userName, password);
			
			//set auto commit to false else by default it is true for every sql statement
			conn.setAutoCommit(false);
			
			//create statement
			stmt = conn.createStatement();
			
			//drop tables before creating any
			String dropSQL="drop table employee";
			
			//run the query
			int i = stmt.executeUpdate(dropSQL);
			
			if(i==0) {
				System.out.println("table dropped");
			}else {
				System.out.println("table could not be dropped");
			}
			
			//create table
			String createTable= "create table employee(" +
			"ID varchar(200), " +
					"FIRSTNAME varchar(200), "+
			"LASTNAME varchar(200), " +
					"DESIGNATION varchar(200))";
			
			//run the query
			 i = stmt.executeUpdate(createTable);
			
			//see how to check if table created
			//created is zero and not created is non zero
			if(i==0) {
				System.out.println("table created");
			}else {
				System.out.println("table not created");
			}
			
			//lets insert into table
			String insertSQL="insert into employee values(" + " 1, 'matt', 'bret', 'tester')";
			i=stmt.executeUpdate(insertSQL);
			if(i!=0) {
				System.out.println("row is inserted");
			}else {
				System.out.println("row is not inserted");
			}
			
			//to see inserts and deletes and updates we need to commit.
			conn.commit();
			
		}catch(SQLException e) {
			e.printStackTrace();//catch errors related to sql db
		}
	}

}

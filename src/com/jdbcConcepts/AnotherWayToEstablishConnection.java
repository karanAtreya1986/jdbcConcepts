package com.jdbcConcepts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AnotherWayToEstablishConnection {

		
		public static void main(String[] args) {
			
			Connection conn = null;
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
				//give user name and password separately
				//another way to establish connection
				
				String dbUrl="jdbc:oracle:thin:@localhost:1521:xe";
				String userName="SYSTEM";
				String password="Pass1234";
				
				try {
					conn=DriverManager.getConnection(dbUrl, userName, password);
					if(conn !=null) {
						System.out.println("connection established successfully");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}catch(ClassNotFoundException e) {
				e.printStackTrace();
			}
			finally {
				//if conn is open and not closed then close it
				try {
					if(conn !=null && !conn.isClosed()) {
						conn.close();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}

	}

package com.jdbcConcepts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HowThinDriverWorks {
	
	public static void main(String[] args) {
		
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//for sql replace oracle with sql
			
			//mention url, username, password to connect to db
			String dbUrl="jdbc:oracle:thin:SYSTEM/Pass1234@localhost:1521:xe";
			
			try {
				conn=DriverManager.getConnection(dbUrl);
				if(conn !=null) {
					System.out.println("connection established successfully");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}

}

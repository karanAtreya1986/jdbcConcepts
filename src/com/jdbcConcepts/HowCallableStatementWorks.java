package com.jdbcConcepts;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLType;
import java.util.Scanner;

public class HowCallableStatementWorks {

	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws SQLException {

		Connection conn = null;
		CallableStatement callstmt = null;

		String dbUrl = "jdbc:oracle:thin:@localhost:1521:xe";
		String userName = "SYSTEM";
		String password = "Pass1234";

		try {

			conn = DriverManager.getConnection(dbUrl, userName, password);

			// get the data from user using scanner api
			Scanner input = new Scanner(System.in);

			System.out.println("enter the employee id");
			int id = Integer.parseInt(input.nextLine());
			System.out.println("enter the employee name");
			String name = input.nextLine();
			System.out.println("enter the employee role");
			String role = input.nextLine();
			System.out.println("enter the employee city");
			String city = input.nextLine();
			System.out.println("enter the employee country");
			String country = input.nextLine();

			// use callable
			// call is keyword and call the store procedure named insertEmployee
			// we give five parameters
			callstmt = conn.prepareCall("{call insertEmployee(?, ?, ?, ?, ?, ?)}");

			// what to put in every question mark
			// in first place put id, in second place put name and so on.
			callstmt.setInt(1, id);
			callstmt.setString(2, name);
			callstmt.setString(3, role);
			callstmt.setString(4, city);
			callstmt.setString(5, country);

			// register the out parameter before calling stored procedure
			callstmt.registerOutParameter(6, java.sql.Types.VARCHAR);

			// call the stored procedure
			callstmt.executeUpdate();

			// to see the output we need to use the sixth parameter we created
			String result = callstmt.getString(6);

			System.out.println("Employee record inserted and saved ----- " + result);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { // close all open objects
			try {
				if (callstmt != null) {
					callstmt.close();
					callstmt = null;
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

}

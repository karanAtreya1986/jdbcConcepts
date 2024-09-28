package com.jdbcConcepts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

public class HowPreparedStatementWorks {

	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		Connection conn = null;
		java.sql.PreparedStatement prepstmt = null;
		ResultSet rs = null;
		try {
			String dbUrl = "jdbc:oracle:thin:@localhost:1521:xe";
			String userName = "SYSTEM";
			String password = "Pass1234";

			conn = DriverManager.getConnection(dbUrl, userName, password);

			String updateTable = "update employee set FIRSTNAME=? where id = ?";

			// without this step, the code will fail
			prepstmt = conn.prepareStatement(updateTable);

			// tell what to update in first question mark and second question mark
			prepstmt.setString(1, "tiger");
			prepstmt.setInt(2, 111);

			// call the query
			int rows = prepstmt.executeUpdate();
			System.out.println("updated rows --------- " + rows);

			// select query
			String selectQuery = "select * from employee where id = ?";

			// without this step, the code will fail
			prepstmt = conn.prepareStatement(selectQuery);

			// replace the question mark with 111
			prepstmt.setInt(1, 111);

			// run query
			rs = prepstmt.executeQuery();

			// for output formatting
			System.out.println("id\tfirstname\tlastname\tdesignation");
			System.out.println("-----\t---------------\t---------------\t------------");

			while (rs.next()) {
				// lets get the values of the row
				// \t means tab space
				System.out.println(
						rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t" + rs.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { // close all open objects
			try {
				if (prepstmt != null) {
					prepstmt.close();
					prepstmt = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
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

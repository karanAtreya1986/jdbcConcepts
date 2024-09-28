package com.resultSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ResultSetScrollableExample {

	public static void main(String[] args) {

		try {
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM", "Pass1234");
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			ResultSet rs = stmt.executeQuery("select books.* from books");

			// scrolling the records.
			rs.last();// move to the last row
			System.out.println("last row is -------------->");
			// getrow returns the row index number which comes at corner
			System.out.println(rs.getRow() + " : " + rs.getInt("id") + ":" + rs.getString("title") + " : "
					+ rs.getString("author") + ":" + rs.getDouble("price") + " :" + rs.getInt("quantity"));

			// go to the top of the table
			rs.beforeFirst();

			// print all rows
			System.out.println("print all the rows -------------->");
			while (rs.next()) {
				System.out.println(rs.getRow() + " : " + rs.getInt("id") + ":" + rs.getString("title") + " : "
						+ rs.getString("author") + ":" + rs.getDouble("price") + " :" + rs.getInt("quantity"));
			}

			// reach to particular row
			rs.beforeFirst();
			rs.absolute(2);
			System.out.println("using absolute method to reach a particular row ----------->");
			System.out.println(rs.getRow() + " : " + rs.getInt("id") + ":" + rs.getString("title") + " : "
					+ rs.getString("author") + ":" + rs.getDouble("price") + " :" + rs.getInt("quantity"));

			// we can go back also with relative
			rs.last();
			rs.relative(-2); // go two rows back from last record
			System.out.println("using relative method to reach a particular row ----------->");
			System.out.println(rs.getRow() + " : " + rs.getInt("id") + ":" + rs.getString("title") + " : "
					+ rs.getString("author") + ":" + rs.getDouble("price") + " :" + rs.getInt("quantity"));

			// we can go front also with relative
			rs.first();
			rs.relative(4); // go four rows front from first record
			System.out.println("using relative method to reach a particular row ----------->");
			System.out.println(rs.getRow() + " : " + rs.getInt("id") + ":" + rs.getString("title") + " : "
					+ rs.getString("author") + ":" + rs.getDouble("price") + " :" + rs.getInt("quantity"));

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

package com.resultSet;

import java.sql.SQLException;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;

public class HowRowSetAPIWorks {

	public static void main(String[] args) {
		// create connection object
		String databaseUrl = "jdbc:oracle:thin:@localhost:1521:xe";
		String username = "SYSTEM";
		String password = "Pass1234";

		// add this api for jdbc row set
		JdbcRowSet rs = null;

		try {
			// provide implementation class
//			rs = new JdbcRowSetImpl(); //this doesnt work with newer jdks
			rs = RowSetProvider.newFactory().createJdbcRowSet(); // use this way
			rs.setUrl(databaseUrl); // give connection details
			rs.setUsername(username); // give connection details
			rs.setPassword(password); // give connection details
			// fire query
			rs.setCommand("SELECT books.* FROM books");
			// run the above query
			rs.execute();

			// RowSet is scrollable and updatable
			// Update a row
			// same code as result set scrollable (result set scrollable example.java)
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

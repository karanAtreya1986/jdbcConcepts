package com.resultSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ResultSetUpdatingExample {

	public static void main(String[] args) {

		try {
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM", "Pass1234");
			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			// type scroll insensitive means commit wont happen automatically.
			// concur update table means result set can be updated

			// get all the records
			ResultSet rs = stmt.executeQuery("select books.* from books");

			// move to first record
			rs.first();

			// show the record values
			System.out.println(rs.getRow() + ":" + rs.getInt("id") + ":" + rs.getString("title") + ":"
					+ rs.getString("author") + ":" + rs.getDouble("price") + ":" + rs.getInt("quantity") + ":");

			// update records
			// change price to some value
			// change quantity to some value

			rs.updateDouble("price", 999.99);
			rs.updateInt("quantity", 8450);

			// to make the above updates, update the row
			rs.updateRow();

			// print data if record updated
			System.out.println("After update------------------->");
			System.out.println(rs.getRow() + ":" + rs.getInt("id") + ":" + rs.getString("title") + ":"
					+ rs.getString("author") + ":" + rs.getDouble("price") + ":" + rs.getInt("quantity") + ":");

//			rs.first();

			// lets delete the row
			System.out.println("row deletion ---------->");
			System.out.println(rs.getRow() + ":" + rs.getInt("id") + ":" + rs.getString("title") + ":"
					+ rs.getString("author") + ":" + rs.getDouble("price") + ":" + rs.getInt("quantity") + ":");
			rs.deleteRow();

			// insert new row
			// move cursor to insert new row
			// it knows the current position of the cursor and stays there
			rs.moveToInsertRow();
			// lets update the records in the row
			rs.updateInt(1, 623423434);
			rs.updateString(2, "Hi title");
			rs.updateString(3, "hi author");
			rs.updateDouble(4, 300.10);
			rs.updateInt(5, 20010);
			rs.insertRow(); // insert into the row.
			rs.beforeFirst(); // mandatory to move the cursor to the top after insert
			conn.commit();// commit the code

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

package com.resultSet;

import java.sql.SQLException;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

public class CanIUseRowSetForUpdatingTable {

	public static void main(String[] args) {

		// same code as result set scrollable (ResultSetUpdatingExample.java)
		// here in jdbc row set no need of connection object to be created.
		// jdbc is connected row set.

		String databaseUrl = "jdbc:oracle:thin:@localhost:1521:xe";
		String username = "SYSTEM";
		String password = "Pass1234";

		// Get an instance of RowSetFactory
		RowSetFactory rowSetFactory = null;
		try {
			rowSetFactory = RowSetProvider.newFactory();
		} catch (SQLException ex) {
			ex.printStackTrace();
			return;
		}

		try (
				// Use RowSetFactory to allocate a RowSet instance.
				JdbcRowSet rowSet = rowSetFactory.createJdbcRowSet();) {
			rowSet.setUrl(databaseUrl);
			rowSet.setUsername(username);
			rowSet.setPassword(password);
			rowSet.setCommand("SELECT books.* FROM books");
			rowSet.execute();

			// RowSet is scrollable and updatable
			// Update a row
			rowSet.last();
			System.out.println("-- Update a row --");
			System.out.println(rowSet.getRow() + ": " + rowSet.getInt("ID") + ", " + rowSet.getString("title") + ", "
					+ rowSet.getString("author") + ", " + rowSet.getDouble("price") + ", " + rowSet.getInt("QUANTITY"));

			rowSet.updateDouble("price", 99.99); // update cells
			rowSet.updateInt("QUANTITY", 99);
			rowSet.updateRow(); // update the row in the data source
			System.out.println(rowSet.getRow() + ": " + rowSet.getInt("ID") + ", " + rowSet.getString("title") + ", "
					+ rowSet.getString("author") + ", " + rowSet.getDouble("price") + ", " + rowSet.getInt("QUANTITY"));

			// Delete a row
			rowSet.first();
			System.out.println("-- Delete a row --");
			System.out.println(rowSet.getRow() + ": " + rowSet.getInt("ID") + ", " + rowSet.getString("title") + ", "
					+ rowSet.getString("author") + ", " + rowSet.getDouble("price") + ", " + rowSet.getInt("QUANTITY"));
			rowSet.deleteRow(); // delete the current row

			// A updatable ResultSet has a special row that serves as a staging area for
			// building a row to be inserted.
			rowSet.moveToInsertRow();
			rowSet.updateInt(1, 8303); // Use column number
			rowSet.updateString(2, "Even More Programming");
			rowSet.updateString(3, "Kumar");
			rowSet.updateDouble(4, 77.88);
			rowSet.updateInt(5, 77);
			rowSet.insertRow();
			rowSet.beforeFirst();

			// note - in jdbc row set commit is not mandatory unlike result set.

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

}

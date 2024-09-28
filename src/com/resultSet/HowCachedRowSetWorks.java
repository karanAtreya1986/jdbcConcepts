package com.resultSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//import com.sql.rowset.CachedRowSetImpl; // import this for CachedRowSetImpl

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

public class HowCachedRowSetWorks {

	public static void main(String[] args) {

		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String username = "SYSTEM";
		String password = "Pass1234";
		Connection conn = null;
		CachedRowSet crs = null;

		try {
			conn = DriverManager.getConnection(url, username, password);

			// cache row set is not connected to database
			// so we will switch off auto commit
			// cache row set is unconnected row set
			// uses cache memory as its disconnected

			conn.setAutoCommit(false);

			// crs = new CachedRowSet(); // implements this class
			// there are some changes in cached row set, see notes for more information.
			// Use RowSetProvider to get CachedRowSet instance
			crs = RowSetProvider.newFactory().createCachedRowSet();
			crs.setUrl(url);
			crs.setUrl(username);
			crs.setUrl(password);
			crs.setCommand("select books.* from books");

			// we create array
			// column number 1 is key column in row set
			int[] keys = { 1 };
			crs.setKeyColumns(keys);

			// execute connection
			crs.execute(conn);

			crs.first();
			System.out.println(crs.getRow() + ":" + crs.getInt("id") + ":" + crs.getString("title") + ":"
					+ crs.getString("author") + ":" + crs.getDouble("price") + ":" + crs.getInt("quantity") + ":");

			crs.updateDouble("price", 999.99);
			crs.updateInt("quantity", 8450);

			crs.updateRow();

			System.out.println("After update------------------->");
			System.out.println(crs.getRow() + ":" + crs.getInt("id") + ":" + crs.getString("title") + ":"
					+ crs.getString("author") + ":" + crs.getDouble("price") + ":" + crs.getInt("quantity") + ":");

			crs.moveToInsertRow();
			crs.updateInt(1, 7);
			crs.updateString(2, "Hi title ringo");
			crs.updateString(3, "hi author bingo");
			crs.updateDouble(4, 43.75);
			crs.updateInt(5, 205);
			crs.insertRow();

			// important things to remember for committing in caching
			crs.moveToCurrentRow();
			crs.acceptChanges(); // this is the commit for caching
			// dont use normal commit here.
			// it will update here in console but not in db since not committed
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

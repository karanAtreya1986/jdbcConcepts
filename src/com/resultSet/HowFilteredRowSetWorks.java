package com.resultSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

public class HowFilteredRowSetWorks {

	// these are the values to add using insert
	static final String[] NAMES = { "MBill Gates", "Steve Jobs", "Mark Zuckerberg", "PAlan Turing", "Linus Torlvalds" };

	public static void main(String[] args) throws SQLException {

		// establish connection

		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String username = "SYSTEM";
		String password = "Pass1234";
		Connection conn = DriverManager.getConnection(url, username, password);

		// prepare data will create table and add data
		prepareData(conn);

		// create row set object using row set factory
		RowSetFactory rsf = RowSetProvider.newFactory();
		FilteredRowSet usersRS = rsf.createFilteredRowSet();

		// apply the where clause search criteria
		usersRS.setCommand("select * from USER1");
		usersRS.execute(conn);

		// apply filter
		// anything which does not begin between A-L
		// . means one character will be there
		// * means any number of characters can come
		usersRS.setFilter(new SearchFilterAPI("[A-L].*"));

		// to see the output
		dumpRS(usersRS);
	}

	static void prepareData(Connection conn) throws SQLException {
		// lets ensure we start clean and drop all tables
		conn.createStatement().execute("drop table USER1");

		// create new table
		conn.createStatement().execute("create table USER1 (name varchar(256))");

		// insert into record
		// add at runtime using ?
		PreparedStatement prepareStatement = conn.prepareStatement("insert into USER1 (name) values (?)");

		// use enhance for loop
		// replace the question mark with names on each iteration
		for (String name : NAMES) {
			// replace first question mark from array
			prepareStatement.setString(1, name);
			// execute the query
			prepareStatement.execute();
		}
	}

	static void dumpRS(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		// get all the columns and returns column count
		int cc = rsmd.getColumnCount();
		while (rs.next()) {
			for (int i = 1; i <= cc; i++) {
				// column label will give column name
//					get object will give the value in the column
				System.out.println(rsmd.getColumnLabel(i) + " = " + rs.getObject(i) + " ");
			}
			System.out.println("");
		}
	}
}

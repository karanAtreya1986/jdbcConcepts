package com.resultSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JoinRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

public class HowJoinRowSetWorks {

	// this is the replica of joins in sql

	private final static String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private final static String USR = "SYSTEM";
	private final static String PWD = "Pass1234";

	public static void main(String[] args) throws SQLException {

		// create connection
		Connection con = getDbConnection();

		// two cache row set objects because we deal with two tables here
		// get data for both tables

		CachedRowSet crs1 = getCachedRowset();
		// populate data into tables
		// con is needed to connect to db
		populateRowset(crs1, con, "emp_table", "EMPID", "NAME");
		CachedRowSet crs2 = getCachedRowset();
		// populate second table data
		// con is needed to connect to db
		populateRowset(crs2, con, "emp_addr_table", "EMPID", "CITY");

		// apply the join between tables
		JoinRowSet jrs = getJoinRowset(crs1, crs2);
		queryJoinRowset(jrs); // for querying the database based on join

		// close all open objects as we are done.
		crs1.close();
		crs2.close();
		jrs.close();
		System.out.println("- Close rowsets.");
	}

	private static Connection getDbConnection() throws SQLException {

		System.out.println("- Get connection to database: " + DB_URL);
		Connection con = DriverManager.getConnection(DB_URL, USR, PWD);
		return con;
	}

	private static CachedRowSet getCachedRowset() throws SQLException {

		System.out.println("- Create cached rowset");
		// way to create object for cache row set -- use row set factory.
		RowSetFactory rsFactory = RowSetProvider.newFactory();
		CachedRowSet rowset = rsFactory.createCachedRowSet();
		return rowset;
	}

	private static void populateRowset(CachedRowSet crs, Connection con, String tableName, String col1, String col2)
			throws SQLException {

		System.out.println("- Populate rowset with database table rows: " + tableName);
		String sql = "SELECT * FROM " + tableName;
		crs.setCommand(sql);
		crs.execute(con);
		crs.last(); // go to the last object, so as to print number of rows

		System.out.println("Total rows: " + crs.getRow()); // get row to get count of rows

		queryCrs(crs, col1, col2); // query the database
	}

	private static void queryCrs(CachedRowSet crs, String col1, String col2) throws SQLException {

		crs.beforeFirst(); // go before the first record and then start scanning

		while (crs.next()) {

			String s1 = crs.getString(col1); // get the data of first column
			String s2 = crs.getString(col2); // get the data of second column
			System.out.println("[" + col1 + ", " + col2 + "]: " + s1 + ", " + s2);
		}
	}

	private static JoinRowSet getJoinRowset(CachedRowSet crs1, CachedRowSet crs2) throws SQLException {

		System.out.println("- Create join rowset");
		// create new join row set using row set factory
		RowSetFactory rsFactory = RowSetProvider.newFactory();
		JoinRowSet jrs = rsFactory.createJoinRowSet();

		// club two tables
		System.out.println("- Add two cached rowsets, joined on: " + "EMPID");
		crs1.setMatchColumn("EMPID"); // match based on column id in crs1
		jrs.addRowSet(crs1); // add the row set to join
		jrs.addRowSet(crs2, "EMPID"); // clubbed abovtwo statements into one for crs2
		return jrs;
	}

	private static void queryJoinRowset(JoinRowSet jrs) throws SQLException {
		System.out.println("- Query join rowset:");
		jrs.last(); // go to the last record and get the number of rows
		System.out.println("Total rows: " + jrs.getRow()); // get row to get the total count of rows
		jrs.beforeFirst(); // go before the first record to scan the result set
		while (jrs.next()) {
			// this will print the column numbers instead of names
//			String s1 = jrs.getString(1); // print the first column values
//			String s2 = jrs.getString(2); // print the second column values
//			String s3 = jrs.getString(3); // print the third column values
			// this will print column names instead of numbers.
			String s1 = jrs.getString("EMPID"); // print the first column values
			String s2 = jrs.getString("NAME"); // print the second column values
			String s3 = jrs.getString("CITY"); // print the third column values
			System.out.println("[ EMPID,  " + "NAME" + ", " + "CITY" + "]: " + s1 + ", " + s2 + ", " + s3);
		}
	}

}

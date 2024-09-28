package com.resultSet;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.rowset.RowSetProvider;
import javax.sql.rowset.WebRowSet;

public class HowWebRowSetAPIWorks {

	public static void main(String[] args) throws Exception {
		Connection conn = getOracleConnection();
		System.out.println("Got Connection.");
		// create statement class object
		Statement st = conn.createStatement();
//		st.executeUpdate("drop table survey"); // drop the table first if existing
		// create table after dropping
		st.executeUpdate("create table survey (id int,name varchar(50))");
		// insert records
		st.executeUpdate("insert into survey (id,name ) values (1,'nameValue')");
		st.executeUpdate("insert into survey (id,name ) values (2,'anotherValue')");

		// lets use web row set
		WebRowSet webRS;
		// lets comment all below statements.
//		ResultSet rs = null;
//		Statement stmt = null;
//		stmt = conn.createStatement();
		webRS = null;
		// fire query which will select record whose id is 1
		String sqlQuery = "SELECT * FROM survey WHERE id='1'";
//		webRS = new WebRowSetImpl(); this doesnt work with newer jdk
		webRS = RowSetProvider.newFactory().createWebRowSet(); // use this way
		// pass the command
		webRS.setCommand(sqlQuery);
		// execute the query
		webRS.execute(conn);

		// put in files, so use file writer, for writing into file.
		FileWriter fw = null;
		// which file to write to.
		// create a file called as sample.xml in d drive and keep
		File file = new File("D:/Sample.xml");
//		File file = new File("D:/Sample.txt"); //this will also work.
		fw = new FileWriter(file);
//		file.getabsolutepath -- gives the path of the file.
		System.out.println("Writing db data to file " + file.getAbsolutePath());
		// write into file in xml format.
		webRS.writeXml(fw);

		// convert xml to a String object. use string writer.
		StringWriter sw = new StringWriter();
		// use web row set and write in string format in xml file.
		webRS.writeXml(sw);
		// print everything
		System.out.println("==============");
		// tostring used for converting to string.
		System.out.println(sw.toString());
		System.out.println("==============");
		fw.flush();
		fw.close();
		// rs.close();
//		stmt.close();
		conn.close();
		webRS.close();

	}

	// create connection
	public static Connection getOracleConnection() throws Exception {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String username = "SYSTEM";
		String password = "Pass1234";

		Class.forName(driver); // load Oracle driver
		Connection conn = DriverManager.getConnection(url, username, password);
		return conn;
	}
}

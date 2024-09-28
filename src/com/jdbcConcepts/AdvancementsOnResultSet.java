package com.jdbcConcepts;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdvancementsOnResultSet {

	public static void main(String[] args) throws SQLException {

		String dbUrl = "jdbc:oracle:thin:@localhost:1521:xe";
		String userName = "SYSTEM";
		String password = "Pass1234";

		Connection conn = DriverManager.getConnection(dbUrl, userName, password);
		// put restrictions on result set
		//lot of options there
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
		ResultSet rs=stmt.executeQuery("select * from employee");
		
		rs.absolute(3); //this will jump to third row
//		System.out.println(rs.getInt(1)+ " " +  rs.getString(2) + " " +  rs.getString(3)+ " " + rs.getString(4));
		
		
		//meta data
		//gives information about entire data like resultset
		
		DatabaseMetaData md=conn.getMetaData();
		System.out.println("Result set types --------");
		
//		System.out.println(md.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY));
		
		//Type_scroll_insensitive --> code wont commit itself.
		System.out.println(md.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE));
		
		//Type_scroll_sensitive --> code will commit itself.
		System.out.println(md.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE));
		
		//Concur_read_only --> no one has rights to commit.
		System.out.println(md.supportsResultSetType(ResultSet.CONCUR_READ_ONLY));
		
		//Concur_updatetable --> everyone has rights to commit.
		System.out.println(md.supportsResultSetType(ResultSet.CONCUR_UPDATABLE));
		
		//Close_cursors_at_commit --> at the time of commit every transaction will be closed.
		System.out.println(md.supportsResultSetType(ResultSet.CLOSE_CURSORS_AT_COMMIT));
		
		//Hold_cursors_over_commit --> commits wont happen.
		System.out.println(md.supportsResultSetType(ResultSet.HOLD_CURSORS_OVER_COMMIT));
		
	}

}

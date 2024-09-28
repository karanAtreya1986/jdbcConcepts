package com.jdbcConcepts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

public class QueryIntoDatabase {

	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null; // to process the output
		Savepoint savePoint = null;

		String dbUrl = "jdbc:oracle:thin:@localhost:1521:xe";
		String userName = "SYSTEM";
		String password = "Pass1234";

		try {
			conn = DriverManager.getConnection(dbUrl, userName, password);
		
		if (conn != null) {
			System.out.println("connection established successfully");
		}

		conn.setAutoCommit(false);

		stmt = conn.createStatement();

		// drop table
		String dropTable = "drop table employee";

		int i = stmt.executeUpdate(dropTable);

		if (i == 0) {
			System.out.println("table dropped");
		} else {
			System.out.println("table does not exist");
		}

		String createTable = "create table employee(" + "id number not null, " + "firstname varchar(200), "
				+ "lastname varchar(250), " + "designation varchar(200))";

		i = stmt.executeUpdate(createTable);

		if (i == 0) {
			System.out.println("table created");
		} else {
			System.out.println("table not created");
		}

		String insertData = "insert into employee values(111, 'test', 'mcmachon', 'chairman')";

		i = stmt.executeUpdate(insertData);

		if (i != 0) {
			System.out.println("record inserted");
		} else {
			System.out.println("record not inserted");
		}

		// here we will put a intermediate break to save our work
		savePoint = conn.setSavepoint("SAVEINTERMEDIATE");
		conn.commit();

		// select query
		String selectQuery = "select * from employee";

		rs = stmt.executeQuery(selectQuery);

		while (rs.next()) {
			// how to fetch records from db tables
//			index starts from one -- column or row index
			System.out.println("id " + rs.getInt(1));
			System.out.println("firstname " + rs.getString(1));
			System.out.println("lastname " + rs.getString(1));
			System.out.println("designation " + rs.getString(1));
		}

		// update query
		String updateData = "update employee set FIRSTNAME ='tiger' where DESIGNATION = 'chairman'";

		i = stmt.executeUpdate(updateData);

		if (i != 0) {
			System.out.println("record updated");
		} else {
			System.out.println("record not updated");
		}
		
		conn.commit();
		
		//delete data
		
		String deleteData = "delete from employee where id=111";

		i = stmt.executeUpdate(deleteData);

		if (i != 0) {
			System.out.println("record deleted");
		} else {
			System.out.println("record not deleted");
		}
		conn.commit();
		
		}catch (SQLException e) {
			e.printStackTrace();
			//if we have not reached till intermediate break then rollback everything.
			try {
				if(savePoint==null) {
					conn.rollback();
					System.out.println("rolled back successfully");
				}else {
					conn.commit();//if we crossed the intermediate save then commit that much
				}
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
			finally {
				//to close all open objects
				if(stmt!=null) {
					try {
						stmt.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					stmt=null;
				}
				if(conn!=null) {
					try {
						conn.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					conn=null;
				}
			}
	}
	}
}

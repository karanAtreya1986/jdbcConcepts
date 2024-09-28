package com.resultSet;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

public class WhatAreResultSetTypes {

	public static void main(String[] args) {

		try {
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM", "Pass1234");

			// to fire queries we need stmt object
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

			// to write the queries
			ResultSet rs = stmt.executeQuery("select * from employee");

			// type scroll sensitive -- cursor can move in both directions.
			// it can also move to an absolute position.
			// code auto committed.

			// please move to record 3
			rs.absolute(3);

			// print third row
			System.out.println(rs.getInt(1) + " " + rs.getString(2) + "" + rs.getString(3));

			// to check if properties are enabled or not use metadata
			DatabaseMetaData md = conn.getMetaData();
			System.out.println("supported result types-------------->");
			// check which result set types are supported

			// these three are for cursor manipulation
			// type forward only -- cursor moves in forward direction
			// type scroll insensitive -- cursor can move back, forth and to any absolute
			// position
			// commits wont happen in this case
			// TYPE_SCROLL_SENSITIVE -- commits happen automatically
			// cursor can move in forward, backward and absolute direction.

			System.out.println(md.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY));
			System.out.println(md.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE));
			System.out.println(md.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE));

			// how much updates can be done
			// CONCUR_READ_ONLY -- result set is read only.
			// CONCUR_UPDATABLE -- result set can be updated.
			// default is read only.
			System.out.println(md.supportsResultSetType(ResultSet.CONCUR_READ_ONLY));
			System.out.println(md.supportsResultSetType(ResultSet.CONCUR_UPDATABLE));

			// HOLD_CURSORS_OVER_COMMIT -- when commit is done cursors and rs objects should
			// not be closed
			// CLOSE_CURSORS_AT_COMMIT -- close cursors and rs objects over commit
			System.out.println(md.supportsResultSetType(ResultSet.HOLD_CURSORS_OVER_COMMIT));
			System.out.println(md.supportsResultSetType(ResultSet.CLOSE_CURSORS_AT_COMMIT));

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}

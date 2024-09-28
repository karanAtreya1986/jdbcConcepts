package com.resultSet;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.RowSet;
import javax.sql.rowset.Predicate;

public class SearchFilterAPI implements Predicate{
	
	//this is the code for the where clause in sql.

	//define search criteria using pattern matcher class.
	Pattern pattern;
	
	//constructor
	//give the regex to search
	public SearchFilterAPI(String searchRegex) {
		if (searchRegex != null && !searchRegex.isEmpty()) {
			pattern = Pattern.compile(searchRegex);
		}
	}
	
	
	@Override
	public boolean evaluate(RowSet rs) {
		try {
			if (!rs.isAfterLast()) {//if we are not at last record
				String name = rs.getString("name"); //read the pending records
				System.out.println(String.format(
						"Searching for pattern '%s' in %s", pattern.toString(),
						name));
				//pattern.matcher compares input string against regex
				Matcher m = pattern.matcher(name);
				return m.matches(); //returns if string matched against pattern
			} else {
				return false; //no matches and last record also fetched.
			}
			} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean evaluate(Object value, int column) throws SQLException {
		//we are not going to use this
		//so just write some throw exception
		throw new UnsupportedOperationException("Not supported yet.");
		//since throwing exception removed the return.
	}

	@Override
	public boolean evaluate(Object value, String columnName) throws SQLException {
		//we are not going to use this
		//so just write some throw exception
		throw new UnsupportedOperationException("Not supported yet.");
	}

	//in this class we will add the where clause to filter.
}

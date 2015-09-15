package org.rikai.dictionary;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SqliteDatabase {

	boolean loadEdict(String path);

	/**
	 * @return the searchQuery
	 */
	String getSearchQuery();

	/**
	 * @param searchQuery the searchQuery to set
	 */
	void setSearchQuery(String searchQuery);

	/**
	 * close the database connection to the dictionary. this should be called if loadData() is called
	 */
	void close();
	
	boolean isLoaded();
	
	ResultSet findWord(String word) throws SQLException;

}
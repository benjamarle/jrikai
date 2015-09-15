package org.rikai.dictionary.db;

public interface SqliteDatabase {

	boolean loadEdict(String path);

	/**
	 * @return the searchQuery
	 */
	String getSearchQuery();

	/**
	 * @param searchQuery
	 *            the searchQuery to set
	 */
	void setSearchQuery(String searchQuery);

	/**
	 * close the database connection to the dictionary. this should be called if loadData() is called
	 */
	void close();

	boolean isLoaded();

	ResultCursor findWord(String word);

}
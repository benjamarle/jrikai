package org.rikai.dictionary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class SqliteDictionary implements Dictionary {

	private Connection m_DictDB = null;
	
	private String searchQuery;
	
	protected PreparedStatement statement = null;
	

	protected boolean loadEdict(String path) {
		try {
			// load the sqlite-JDBC driver using the current class loader
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			m_DictDB = DriverManager.getConnection("jdbc:sqlite:" + path);
			statement = prepareSearchQuery();
	
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				if (m_DictDB != null) {
					m_DictDB.close();
				}
			} catch (SQLException f) {
				f.printStackTrace();
				// nothing to be done at this point…
			}
			return false;
		}
		return true;
	}
	
	protected PreparedStatement prepareSearchQuery() throws SQLException {
		return  m_DictDB.prepareStatement(searchQuery);
	}
	
	/**
	 * @return the searchQuery
	 */
	public String getSearchQuery() {
		return searchQuery;
	}

	/**
	 * @param searchQuery the searchQuery to set
	 */
	public void setSearchQuery(String searchQuery) {
		this.searchQuery = searchQuery;
	}

	/**
	 * close the database connection to the dictionary. this should be called if loadData() is called
	 */
	public void close() {
		if (m_DictDB != null) {
			try {
				m_DictDB.close();
			} catch (SQLException e) {
				e.printStackTrace();
				// nothing to be done here
			}
		}
	}

}

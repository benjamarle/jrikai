package org.rikai.dictionary.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.rikai.dictionary.DictionaryNotLoadedException;

public class JdbcSqliteDatabase implements SqliteDatabase {

	private Connection database = null;

	private String searchQuery;

	private boolean loaded;

	private PreparedStatement statement = null;

	public boolean loadEdict(String path) {
		try {
			// load the sqlite-JDBC driver using the current class loader
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			this.database = DriverManager.getConnection("jdbc:sqlite:" + path);
			this.statement = prepareSearchQuery();
			this.loaded = true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				if (this.database != null) {
					this.database.close();
				}
			} catch (SQLException f) {
				f.printStackTrace();
				// nothing to be done at this point…
			}
			return false;
		}
		return true;
	}

	public boolean isLoaded() {
		return this.loaded;
	}

	protected PreparedStatement prepareSearchQuery() throws SQLException {
		return this.database.prepareStatement(this.searchQuery);
	}

	/**
	 * @return the searchQuery
	 */
	public String getSearchQuery() {
		return this.searchQuery;
	}

	/**
	 * @param searchQuery
	 *            the searchQuery to set
	 */
	public void setSearchQuery(String searchQuery) {
		this.searchQuery = searchQuery;
	}

	/**
	 * close the database connection to the dictionary. this should be called if loadData() is called
	 */
	public void close() {
		if (this.database != null) {
			try {
				this.database.close();
			} catch (SQLException e) {
				e.printStackTrace();
				// nothing to be done here
			}
		}
	}

	/**
	 * find the word in the dictionary database that matches either the word or the kana column
	 *
	 * @param word
	 *            the word
	 * @return cursor
	 * @throws SQLException
	 */
	public ResultCursor findWord(String word) {
		if (!this.isLoaded()) {
			throw new DictionaryNotLoadedException();
		}
		try {
			this.statement.setString(1, word);
			this.statement.setString(2, word);
			return new JdbcResultCursor(this.statement.executeQuery());
		} catch (SQLException e) {
			throw new DatabaseException("Could not complete the query for word : " + word, e);
		}
	}

	public static class JdbcResultCursor implements ResultCursor {
		private ResultSet resultSet;

		public JdbcResultCursor(ResultSet resultSet) {
			super();
			this.resultSet = resultSet;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.rikai.dictionary.ResultCursor#next()
		 */
		public boolean next() {
			try {
				return this.resultSet.next();
			} catch (SQLException e) {
				throw new DatabaseException("Error while getting the next value of the result set", e);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.rikai.dictionary.ResultCursor#getValue(java.lang.String)
		 */
		public String getValue(String columnName) {
			try {
				return this.resultSet.getString(columnName);
			} catch (SQLException e) {
				throw new DatabaseException("Error while getting the value for colmn name [" + columnName + "]", e);
			}
		}

		public void close() {
			try {
				this.resultSet.close();
			} catch (SQLException e) {
				throw new DatabaseException("Error while closing the result set", e);
			}
		}

	}

}

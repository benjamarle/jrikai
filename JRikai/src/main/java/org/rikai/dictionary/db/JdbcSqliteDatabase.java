/*
This file is part of JRikai.

JRikai is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

JRikai is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with JRikai.  If not, see <http://www.gnu.org/licenses/>.

Author: Benjamin Marlé
*/
package org.rikai.dictionary.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

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
	public ResultCursor findWord(String... param) {
		if (!this.isLoaded()) {
			throw new DictionaryNotLoadedException();
		}
		try {
			for (int i = 0; i < param.length; i++) {
				this.statement.setString(i + 1, param[i]);
			}

			return new JdbcResultCursor(this.statement.executeQuery());
		} catch (SQLException e) {
			throw new DatabaseException("Could not complete the query for word : " + Arrays.toString(param), e);
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
				throw new DatabaseException("Error while getting the value for column name [" + columnName + "]", e);
			}
		}

		public int getIntValue(String columnName) {
			try {
				return this.resultSet.getInt(columnName);
			} catch (SQLException e) {
				throw new DatabaseException("Error while getting the value for column name [" + columnName + "]", e);
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

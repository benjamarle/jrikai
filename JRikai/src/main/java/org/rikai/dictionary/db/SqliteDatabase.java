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

	ResultCursor findWord(String... param);

	ResultCursor select(String query, String... param);

}
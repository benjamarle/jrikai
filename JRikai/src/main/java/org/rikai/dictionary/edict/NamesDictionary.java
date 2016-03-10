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
package org.rikai.dictionary.edict;

import org.rikai.dictionary.Entries;
import org.rikai.dictionary.db.SqliteDatabase;

public class NamesDictionary extends EdictDictionary {

	private static final int DEFAULT_MAX_COUNT = 10;

	public NamesDictionary(String path) {
		super(path);
	}

	public NamesDictionary(String path, SqliteDatabase sqliteDatabaseImpl) {
		super(path, sqliteDatabaseImpl);
	}

	/**
	 * find all variants of the specified word, if any
	 *
	 * @param word
	 *            the base word
	 * @return all the variants of the specified word
	 */
	@Override
	public Entries<EdictEntry> wordSearch(String word) {
		return wordSearch(word, DEFAULT_MAX_COUNT);
	}

}

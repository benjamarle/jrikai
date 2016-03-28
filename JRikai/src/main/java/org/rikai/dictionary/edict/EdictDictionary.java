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
/*
Copyright (C) 2013 Ray Zhou

JadeRead is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

JadeRead is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with JadeRead.  If not, see <http://www.gnu.org/licenses/>

Author: Ray Zhou
Date: 2013 04 26

MOTE that the code in the file ported from the RikaiChan plugin for Firefox
which is the algorithm on how to searh and deinflect Japanese word

If you are interested in the FireFox plugin, please visit www.polarcloud.com/rikaichan/
*/
package org.rikai.dictionary.edict;

import org.rikai.deinflector.DeinflectedWord;
import org.rikai.deinflector.Deinflector;
import org.rikai.dictionary.db.ResultCursor;
import org.rikai.dictionary.db.SqliteDatabase;
import org.rikai.dictionary.deinflectable.DeinflectableDictionary;

public abstract class EdictDictionary<T extends EdictEntry> extends DeinflectableDictionary<T> {

	private static final String SEARCH_QUERY = " SELECT DISTINCT * " + " FROM dict" + " WHERE kanji = ? OR kana = ?";

	public EdictDictionary(String path, Deinflector deinflector, SqliteDatabase sqliteDatabaseImpl) {
		super(path, deinflector, sqliteDatabaseImpl);
	}

	public EdictDictionary(String path, Deinflector deinflector) {
		super(path, deinflector);
	}

	public EdictDictionary(String path, SqliteDatabase sqliteDatabaseImpl) {
		super(path, sqliteDatabaseImpl);
	}

	public EdictDictionary(String path) {
		super(path);
	}

	protected T buildEntry(ResultCursor cursor, DeinflectedWord variant) {
		String reason = "";
		if (!variant.getReason().equals("")) {
			reason = "< " + variant.getReason() + " < " + variant.getOriginalWord();
		}

		// the Table value
		// (1, 2, 3, 4, 5, 6, 7)
		// (_id, word, wmark, kana, kmark, show, defn)
		return makeEntry(variant, cursor.getValue("kanji"), cursor.getValue("kana"), cursor.getValue("entry"), reason);
	}

	protected abstract T makeEntry(DeinflectedWord variant, String kanji, String kana, String entry, String reason);

	public String getSearchQuery() {
		return SEARCH_QUERY;
	}

	protected ResultCursor findWord(DeinflectedWord variant) {
		String variantWord = variant.getWord();
		ResultCursor cursor = this.sqliteDatabase.findWord(variantWord, variantWord);
		return cursor;
	}

}

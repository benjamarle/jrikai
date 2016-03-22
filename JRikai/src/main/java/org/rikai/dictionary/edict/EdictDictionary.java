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

import java.util.Collections;
import java.util.List;

import org.rikai.deinflector.DeinflectedWord;
import org.rikai.dictionary.Dictionary;
import org.rikai.dictionary.Entries;
import org.rikai.dictionary.db.JdbcSqliteDatabase;
import org.rikai.dictionary.db.ResultCursor;
import org.rikai.dictionary.db.SqliteDatabase;
import org.rikai.utils.JapaneseConverter;

public class EdictDictionary implements Dictionary<EdictEntry> {

	private static final String SEARCH_QUERY = " SELECT DISTINCT * " + " FROM dict" + " WHERE kanji = ? OR kana = ?";
	private static final int DEFAULT_MAX_COUNT = 10;

	private String path;

	private SqliteDatabase sqliteDatabase;

	public EdictDictionary(String path) {
		this(path, new JdbcSqliteDatabase());
	}

	public EdictDictionary(String path, SqliteDatabase sqliteDatabaseImpl) {
		this.sqliteDatabase = sqliteDatabaseImpl;
		this.sqliteDatabase.setSearchQuery(SEARCH_QUERY);
		this.path = path;
	}

	public void load() {
		this.sqliteDatabase.loadEdict(this.path);
	}

	public boolean isLoaded() {
		return this.sqliteDatabase.isLoaded();
	}

	/**
	 * find all variants of the specified word, if any
	 *
	 * @param word
	 *            the base word
	 * @return all the variants of the specified word
	 */

	public Entries<EdictEntry> wordSearch(String word) {
		return wordSearch(word, DEFAULT_MAX_COUNT);
	}

	public Entries<EdictEntry> query(String q) {
		return wordSearch(q);
	}

	/**
	 * finds all possible deinflected words of the given word
	 *
	 * @param word
	 *            the word to search for
	 * @param maxCount
	 *            maximum number of variants to return, default to DEFAULT_MAX_COUNT
	 * @return an Entries encompassing the results.
	 */
	public Entries<EdictEntry> wordSearch(String word, int maxCount) {

		int[] trueLen = new int[word.length()]; // modify by toHiragana
		word = JapaneseConverter.toHiragana(word, true, trueLen);

		maxCount = maxCount > 0 ? maxCount : DEFAULT_MAX_COUNT;
		int count = 0;

		// final result
		Entries<EdictEntry> result = new Entries<EdictEntry>();

		SEARCH_WORDS: while (word.length() > 0) {
			// current word, plus all of its possible deinflected words
			List<DeinflectedWord> variants = deinflectWord(word);

			// search dictionary and see if each deinflected word exists
			for (int i = 0; i < variants.size(); i++) {

				DeinflectedWord variant = variants.get(i);

				// find this deinflected word in the dictionary.
				// if cursor.count() > 0, that means this is a valid word
				String variantWord = variant.getWord();
				ResultCursor cursor = this.sqliteDatabase.findWord(variantWord, variantWord);

				while (cursor.next()) {

					String definition = cursor.getValue("entry");

					// check if the entry is a valid de-inflected word
					boolean valid = isValid(variant, definition);

					if (valid) {
						result.add(buildEntry(cursor, variant));

						if (result.size() == 1) {
							// the first word has the longest length of the variants because
							// this loop find words by keep taking away characters from the end
							result.setMaxLen(word.length());
						}

						if (++count >= maxCount) {
							result.setComplete(true);
							cursor.close();
							break SEARCH_WORDS;
							// no need to search anymore, break out of all loops
						}
					}
				}
				cursor.close();
			}
			word = word.substring(0, word.length() - 1);
		}

		return result;
	}

	protected List<DeinflectedWord> deinflectWord(String word) {
		return Collections.singletonList(new DeinflectedWord(word));
	}

	protected boolean isValid(DeinflectedWord variant, String definition) {
		return true;
	}

	protected EdictEntry buildEntry(ResultCursor cursor, DeinflectedWord variant) {
		String reason = "";
		if (!variant.getReason().equals("")) {
			reason = "< " + variant.getReason() + " < " + variant.getOriginalWord();
		}

		// the Table value
		// (1, 2, 3, 4, 5, 6, 7)
		// (_id, word, wmark, kana, kmark, show, defn)
		return makeEntry(variant, cursor.getValue("kanji"), cursor.getValue("kana"), cursor.getValue("entry"), reason);
	}

	protected EdictEntry makeEntry(DeinflectedWord variant, String kanji, String kana, String entry, String reason) {
		return new EdictEntry(variant, kanji, kana, entry, reason);
	}

	public void close() {
		this.sqliteDatabase.close();
	}

}

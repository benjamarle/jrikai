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
import org.rikai.dictionary.Entries;
import org.rikai.dictionary.SqliteDictionary;
import org.rikai.utils.JapaneseConverter;

public class EdictDictionary extends SqliteDictionary{

	private static final String SEARCH_QUERY = " SELECT * " + " FROM dict" + " WHERE kanji = ? OR kana = ?";
	private static final int DEFAULT_MAX_COUNT = 10;

	private boolean isLoaded;
	
	private String path;
	
	
	
	public EdictDictionary(String path) {
		setSearchQuery(SEARCH_QUERY);
		this.path = path;
	}
	
	public void load() {
		loadEdict(path);
		isLoaded = true;
	}

	public boolean isLoaded() {
		return isLoaded;
	}

	/**
	 * find the word in the dictionary database that matches either the word or the kana column
	 *
	 * @param word
	 *            the word
	 * @return cursor
	 * @throws SQLException
	 */
	protected ResultSet findWord(String word) throws SQLException {
		statement.setString(1, word);
		statement.setString(2, word);
		return statement.executeQuery();
	}

	/**
	 * find all variants of the specified word, if any
	 *
	 * @param word
	 *            the base word
	 * @return all the variants of the specified word
	 */
	public Entries wordSearch(String word) {
		return wordSearch(word, DEFAULT_MAX_COUNT);
	}
	
	public Entries query(String q) {
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
	public Entries wordSearch(String word, int maxCount) {

		int[] trueLen = new int[word.length()]; // modify by toHiragana
		word = JapaneseConverter.toHiragana(word, true, trueLen);

		maxCount = maxCount > 0 ? maxCount : DEFAULT_MAX_COUNT;
		int count = 0;

		// final result
		Entries result = new Entries();

		try {
			SEARCH_WORDS: while (word.length() > 0) {
				// current word, plus all of its possible deinflected words
				List<DeinflectedWord> variants = deinflectWord(word);

				// search dictionary and see if each deinflected word exists
				for (int i = 0; i < variants.size(); i++) {

					DeinflectedWord variant = variants.get(i);

					// find this deinflected word in the dictionary.
					// if cursor.count() > 0, that means this is a valid word
					String variantWord = variant.getWord();
					ResultSet cursor = findWord(variantWord);

					while (cursor.next()) {

						String definition = cursor.getString("entry");

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

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	protected List<DeinflectedWord> deinflectWord(String word) {
		return Collections.singletonList(new DeinflectedWord(word));
	}

	protected boolean isValid(DeinflectedWord variant, String definition) {
		return true;
	}

	protected EdictEntry buildEntry(ResultSet cursor, DeinflectedWord variant) throws SQLException {
		String reason = "";
		if (variant.getReason() != "") {
			reason = "< " + variant.getReason() + " < " + variant.getOriginalWord();
		}

		// the Table value
		// (1, 2, 3, 4, 5, 6, 7)
		// (_id, word, wmark, kana, kmark, show, defn)
		return new EdictEntry(cursor.getString("kanji"), cursor.getString("kana"), cursor.getString("entry"), reason);
	}

}

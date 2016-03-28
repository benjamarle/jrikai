package org.rikai.dictionary.deinflectable;

import java.util.Collections;
import java.util.List;

import org.rikai.deinflector.DeinflectedWord;
import org.rikai.deinflector.Deinflector;
import org.rikai.dictionary.Dictionary;
import org.rikai.dictionary.Entries;
import org.rikai.dictionary.db.JdbcSqliteDatabase;
import org.rikai.dictionary.db.ResultCursor;
import org.rikai.dictionary.db.SqliteDatabase;
import org.rikai.utils.JapaneseConverter;

public abstract class DeinflectableDictionary<T extends DeinflectedEntry> implements Dictionary<T> {

	private static final int DEFAULT_MAX_COUNT = 16;

	private int maxNbResults = DEFAULT_MAX_COUNT;

	private Deinflector deinflector;

	private String path;

	protected SqliteDatabase sqliteDatabase;

	public DeinflectableDictionary(String path) {
		this(path, new JdbcSqliteDatabase());
	}

	public DeinflectableDictionary(String path, SqliteDatabase sqliteDatabaseImpl) {
		this(path, null, sqliteDatabaseImpl);
	}

	public DeinflectableDictionary(String path, Deinflector deinflector) {
		this(path, deinflector, new JdbcSqliteDatabase());
	}

	public DeinflectableDictionary(String path, Deinflector deinflector, SqliteDatabase sqliteDatabaseImpl) {
		this.sqliteDatabase = sqliteDatabaseImpl;
		this.sqliteDatabase.setSearchQuery(getSearchQuery());
		this.path = path;
		this.deinflector = deinflector;
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

	public Entries<T> wordSearch(String word) {
		return wordSearch(word, maxNbResults);
	}

	public Entries<T> query(String q) {
		return wordSearch(q);
	}

	public void close() {
		this.sqliteDatabase.close();
	}

	protected List<DeinflectedWord> deinflectWord(String word) {
		if (deinflector == null)
			return Collections.singletonList(new DeinflectedWord(word));
		else
			return this.deinflector.deinflect(word);
	}

	protected boolean isValid(DeinflectedWord variant, T entry) {
		return true;
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
	public Entries<T> wordSearch(String word, int maxCount) {
		word = JapaneseConverter.toHiragana(word, true);

		maxCount = maxCount > 0 ? maxCount : maxNbResults;
		int count = 0;

		// final result
		Entries<T> result = new Entries<T>();

		SEARCH_WORDS: while (word.length() > 0) {
			// current word, plus all of its possible deinflected words
			List<DeinflectedWord> variants = deinflectWord(word);

			// search dictionary and see if each deinflected word exists
			for (int i = 0; i < variants.size(); i++) {

				DeinflectedWord variant = variants.get(i);

				// find this deinflected word in the dictionary.
				// if cursor.count() > 0, that means this is a valid word
				ResultCursor cursor = findWord(variant);

				while (cursor.next()) {

					// check if the entry is a valid de-inflected word
					T newEntry = buildEntry(cursor, variant);
					boolean valid = isValid(variant, newEntry);

					if (valid) {
						result.add(newEntry);

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

	/**
	 * @param maxNbResults
	 *            the maxNbResults to set
	 */
	public void setMaxNbResults(int maxNbResults) {
		this.maxNbResults = maxNbResults;
	}

	protected abstract ResultCursor findWord(DeinflectedWord variant);

	public abstract String getSearchQuery();

	protected abstract T buildEntry(ResultCursor cursor, DeinflectedWord variant);

}
package org.rikai.dictionary.wordnet;

import org.rikai.dictionary.Dictionary;
import org.rikai.dictionary.Entries;
import org.rikai.dictionary.db.JdbcSqliteDatabase;
import org.rikai.dictionary.db.ResultCursor;
import org.rikai.dictionary.db.SqliteDatabase;

public class WordnetDictionary implements Dictionary<WordnetEntry> {

	private static String SEARCH_QUERY = "SELECT DISTINCT w.lemma, s.rank, s.freq, s.lexid, s.src, d.lang, d.def, d.sid FROM word w LEFT JOIN sense s ON (w.wordid = s.wordid) LEFT JOIN synset_def d ON (s.synset = d.synset) WHERE w.lemma = ? AND w.lang = 'jpn' AND d.lang = ? AND s.lang = 'jpn'";

	// TODO Add examples query

	private String path;

	private SqliteDatabase sqliteDatabase;

	private Lang lang = Lang.ENG;

	public WordnetDictionary(String path) {
		this(path, new JdbcSqliteDatabase());
	}

	public WordnetDictionary(String path, SqliteDatabase sqliteDatabaseImpl) {
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

	public void close() {
		this.sqliteDatabase.close();
	}

	public Entries<WordnetEntry> query(String q) {
		return query(q, this.lang);
	}

	public Entries<WordnetEntry> query(String q, Lang lang) {
		Entries<WordnetEntry> result = new Entries<WordnetEntry>();
		while (q.length() > 0) {
			ResultCursor cursor = this.sqliteDatabase.findWord(q, lang.toString());
			while (cursor.next()) {
				result.add(buildEntry(cursor));
			}
			cursor.close();
			q = q.substring(0, q.length() - 1);
		}
		return result;
	}

	protected WordnetEntry buildEntry(ResultCursor cursor) {
		return makeEntry(cursor.getValue("lemma"), cursor.getValue("def"), cursor.getValue("rank"),
				cursor.getIntValue("lexid"), cursor.getIntValue("freq"));
	}

	protected WordnetEntry makeEntry(String word, String gloss, String rank, int lexid, int freq) {
		return new WordnetEntry(word, gloss, rank, lexid, freq);
	}

	/**
	 * @return the lang
	 */
	public Lang getLang() {
		return lang;
	}

	/**
	 * @param lang
	 *            the lang to set
	 */
	public void setLang(Lang lang) {
		this.lang = lang;
	}

}

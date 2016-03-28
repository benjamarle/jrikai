package org.rikai.dictionary.wordnet;

import java.util.ArrayList;
import java.util.List;

import org.rikai.deinflector.DeinflectedWord;
import org.rikai.deinflector.Deinflector;
import org.rikai.dictionary.db.ResultCursor;
import org.rikai.dictionary.db.SqliteDatabase;
import org.rikai.dictionary.deinflectable.DeinflectableDictionary;

public class WordnetDictionary extends DeinflectableDictionary<WordnetEntry> {

	private static final int DEFAULT_MAX_NB_RESULT = 64;

	private static String SEARCH_QUERY = "SELECT DISTINCT w.lemma, w.pos, s.synset, s.rank, s.freq, s.lexid, s.src, d.lang, d.def, d.sid FROM word w LEFT JOIN sense s ON (w.wordid = s.wordid) LEFT JOIN synset_def d ON (s.synset = d.synset) WHERE w.lemma = ? AND w.lang = 'jpn' AND d.lang = ? AND s.lang = 'jpn'";

	private static String EXAMPLE_QUERY = "SELECT exeng.synset, exeng.sid, exeng.def engdef, exjpn.def jpndef from synset_ex exeng, synset_ex exjpn where exeng.lang = 'eng' and exjpn.lang = 'jpn' and  exeng.sid = exjpn.sid and exeng.synset = exjpn.synset and exeng.synset = ? order by exeng.sid";

	private Lang lang = Lang.ENG;

	public WordnetDictionary(String path, Deinflector deinflector, SqliteDatabase sqliteDatabaseImpl) {
		super(path, deinflector, sqliteDatabaseImpl);
		setMaxNbResults(DEFAULT_MAX_NB_RESULT);
	}

	public WordnetDictionary(String path, Deinflector deinflector) {
		super(path, deinflector);
		setMaxNbResults(DEFAULT_MAX_NB_RESULT);
	}

	public WordnetDictionary(String path, SqliteDatabase sqliteDatabaseImpl) {
		super(path, sqliteDatabaseImpl);
		setMaxNbResults(DEFAULT_MAX_NB_RESULT);
	}

	public WordnetDictionary(String path) {
		super(path);
		setMaxNbResults(DEFAULT_MAX_NB_RESULT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rikai.dictionary.edict.DeinflectableDictionary#getSearchQuery()
	 */
	@Override
	public String getSearchQuery() {
		return SEARCH_QUERY;
	}

	@Override
	protected boolean isValid(DeinflectedWord variant, WordnetEntry entry) {
		if (variant.isOriginal()) {
			return true;
		}
		String partOfSpeech = entry.getPartOfSpeech();
		return partOfSpeech.equals("a") || partOfSpeech.equals("v");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rikai.dictionary.edict.DeinflectableDictionary#buildEntry(org.rikai.dictionary.db.ResultCursor, org.rikai.deinflector.DeinflectedWord)
	 */
	@Override
	protected WordnetEntry buildEntry(ResultCursor cursor, DeinflectedWord variant) {
		String reason = "";
		if (!variant.getReason().equals("")) {
			reason = "< " + variant.getReason() + " < " + variant.getOriginalWord();
		}

		return makeEntry(variant, cursor.getValue("lemma"), cursor.getValue("synset"), reason, cursor.getValue("pos"),
				cursor.getValue("def"), cursor.getValue("rank"), cursor.getIntValue("lexid"),
				cursor.getIntValue("freq"));
	}

	protected WordnetEntry makeEntry(DeinflectedWord variant, String word, String synset, String reason, String pos,
			String gloss, String rank, int lexid, int freq) {
		return new WordnetEntry(variant, word, synset, reason, pos, gloss, rank, lexid, freq);
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

	protected ResultCursor findWord(DeinflectedWord variant) {
		String variantWord = variant.getWord();
		ResultCursor cursor = this.sqliteDatabase.findWord(variantWord, getLang().toString());
		return cursor;
	}

	public List<WordnetExample> getExamples(WordnetEntry entry) {
		List<WordnetExample> examples = new ArrayList<>();
		ResultCursor examplesResult = this.sqliteDatabase.select(EXAMPLE_QUERY, entry.getSynset());
		while (examplesResult.next()) {
			examples.add(new WordnetExample(entry, examplesResult.getIntValue("sid") + 1,
					examplesResult.getValue("jpndef"), examplesResult.getValue("engdef")));
		}
		return examples;
	}

}

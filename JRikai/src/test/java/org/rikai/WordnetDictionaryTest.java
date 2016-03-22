package org.rikai;

import org.junit.Assert;
import org.junit.Test;
import org.rikai.dictionary.Entries;
import org.rikai.dictionary.wordnet.WordnetDictionary;
import org.rikai.dictionary.wordnet.WordnetEntry;

public class WordnetDictionaryTest {

	private static final String KANJI_WORD = "駐車場";

	@Test
	public void testBasicSearch() {
		WordnetDictionary wDic = new WordnetDictionary("C:\\Users\\Benjamin\\Desktop\\wnjpn.db");
		wDic.load();
		Entries<WordnetEntry> query = wDic.query(KANJI_WORD);
		for (WordnetEntry wordnetEntry : query) {
			System.out.println(wordnetEntry.toStringCompact());
		}
		Assert.assertTrue(query.size() > 0);
	}

}

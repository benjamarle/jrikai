package org.rikai;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.rikai.dictionary.DictionaryNotLoadedException;
import org.rikai.dictionary.Entries;
import org.rikai.dictionary.kanji.KanjiDictionary;
import org.rikai.dictionary.kanji.KanjiEntry;

public class KanjiDictionaryTest {

	private KanjiDictionary kanjiDictionary;

	private int initialSize = -1;

	@Before
	public void setUp() throws Exception {
		kanjiDictionary = new KanjiDictionary("C:\\kanji.dat");
		kanjiDictionary.load();
		initialSize = kanjiDictionary.size();
	}

	@Test(expected = DictionaryNotLoadedException.class)
	public void searchNotLoaded() {
		KanjiDictionary notLoaded = new KanjiDictionary("C:\\kanji.dat");
		notLoaded.query("明");
	}

	@Test
	public void search() {
		Entries<KanjiEntry> query = kanjiDictionary.query("明");
		System.out.println(query);
		assertEquals(query.size(), 1);
	}

	@Test
	public void failedSearch() {
		Entries<KanjiEntry> query = kanjiDictionary.query(" ");
		assertEquals(query.size(), 0);
	}

	@Test
	public void reload() {
		reload(false);
	}

	@Test
	public void reloadLazy() {
		reload(true);
	}

	public void reload(boolean lazy) {
		KanjiEntry.lazyLoad = lazy;
		long a = System.currentTimeMillis();
		kanjiDictionary.load();
		long b = System.currentTimeMillis();

		System.out.println("Kanji Dictionary" + (lazy ? " lazy" : "") + " load time : " + (b - a) + " ms");
		assertEquals(kanjiDictionary.size(), initialSize);
	}

}

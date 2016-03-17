package org.rikai;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.Test;
import org.rikai.dictionary.DictionaryNotLoadedException;
import org.rikai.dictionary.Entries;
import org.rikai.dictionary.kanji.KanjiDictionary;
import org.rikai.dictionary.kanji.KanjiEntry;

public class KanjiDictionaryTest {

	private KanjiDictionary getNewDic() {
		InputStream kanjiDictionaryInputStream = getKanjiDictionaryInputStream();
		return new KanjiDictionary(kanjiDictionaryInputStream);
	}

	private InputStream getKanjiDictionaryInputStream() {
		return this.getClass().getResourceAsStream("/kanji.dat");
	}

	@Test(expected = DictionaryNotLoadedException.class)
	public void searchNotLoaded() throws FileNotFoundException {
		KanjiDictionary notLoaded = getNewDic();
		notLoaded.query("明");
	}

	@Test
	public void search() {
		KanjiDictionary kanjiDictionary = getNewDic();
		kanjiDictionary.load();
		Entries<KanjiEntry> query = kanjiDictionary.query("明");
		System.out.println(query);
		assertEquals(query.size(), 1);
	}

	@Test
	public void failedSearch() {
		KanjiDictionary kanjiDictionary = getNewDic();
		kanjiDictionary.load();
		Entries<KanjiEntry> query = kanjiDictionary.query(" ");
		assertEquals(query.size(), 0);
	}

	@Test
	public void reload() throws FileNotFoundException {
		reload(false);
	}

	@Test
	public void reloadLazy() throws FileNotFoundException {
		reload(true);
	}

	public void reload(boolean lazy) throws FileNotFoundException {
		KanjiEntry.lazyLoad = lazy;
		long a = System.currentTimeMillis();
		KanjiDictionary kanjiDictionary = getNewDic();
		kanjiDictionary.load();
		long b = System.currentTimeMillis();

		System.out.println("Kanji Dictionary" + (lazy ? " lazy" : "") + " load time : " + (b - a) + " ms");
		assertTrue(kanjiDictionary.size() > 0);
	}

}

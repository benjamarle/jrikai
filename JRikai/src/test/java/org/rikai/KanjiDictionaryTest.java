package org.rikai;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.rikai.dictionary.AbstractEntry;
import org.rikai.dictionary.Entries;
import org.rikai.dictionary.kanji.KanjiDictionary;

public class KanjiDictionaryTest {
	
	private KanjiDictionary kanjiDictionary;

	@Before
	public void setUp() throws Exception {
		kanjiDictionary = new KanjiDictionary("C:\\kanji.dat");
		kanjiDictionary.load();
	}

	@Test
	public void printAll() {
		System.out.println(kanjiDictionary.size());
		//System.out.println(kanjiDictionary.values());
	}
	
	@Test
	public void search(){
		Entries query = kanjiDictionary.query("明");
//		for (AbstractEntry abstractEntry : query) {
//			if(abstractEntry != null )
//			System.out.println(abstractEntry.toString());
//		}
		assertEquals(query.size(), 1);
	}
	
	@Test
	public void failedSearch(){
		Entries query = kanjiDictionary.query(" ");
		assertEquals(query.size(), 0);
	}

}

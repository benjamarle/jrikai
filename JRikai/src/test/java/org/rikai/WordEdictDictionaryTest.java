package org.rikai;

import static org.junit.Assert.assertFalse;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.rikai.deinflector.Deinflector;
import org.rikai.dictionary.Entries;
import org.rikai.dictionary.edict.EdictEntry;
import org.rikai.dictionary.edict.WordEdictDictionary;

public class WordEdictDictionaryTest {

	private WordEdictDictionary wordDictionary;

	@Before
	public void setUp() throws Exception {
		wordDictionary = new WordEdictDictionary("C:\\polaredict.sqlite", new Deinflector("C:\\deinflect.dat"));
		wordDictionary.load();
	}

	@Test
	public void testWordSearch() throws SQLException {
		Entries<EdictEntry> wordSearch = wordDictionary.wordSearch("食べられる");
		assertFalse(wordSearch.isEmpty());
	}

}

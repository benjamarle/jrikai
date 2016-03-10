package org.rikai;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import org.rikai.dictionary.Entries;
import org.rikai.dictionary.edict.EdictEntry;
import org.rikai.dictionary.edict.NamesDictionary;

public class NamesDictionaryTest {

	private NamesDictionary namesDictionary;

	@Before
	public void setUp() throws Exception {
		namesDictionary = new NamesDictionary("C:\\polarnames.sqlite");
		namesDictionary.load();
	}

	@Test
	public void test() {
		Entries<EdictEntry> wordSearch = namesDictionary.wordSearch("田中");
		assertFalse(wordSearch.isEmpty());
	}

}

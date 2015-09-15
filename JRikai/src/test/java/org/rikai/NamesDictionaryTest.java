package org.rikai;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.rikai.dictionary.Entries;
import org.rikai.dictionary.edict.NamesDictionary;

public class NamesDictionaryTest {
	
	private NamesDictionary namesDictionary;

	@Before
	public void setUp() throws Exception {
		namesDictionary = new NamesDictionary("C:\\polarnames.sqlite");
	}

	@Test
	public void test() {
		Entries wordSearch = namesDictionary.wordSearch("田中");
	}

}

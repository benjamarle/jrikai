package org.rikai;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.rikai.deinflector.Deinflector;
import org.rikai.dictionary.Entries;
import org.rikai.dictionary.edict.WordEdictDictionary;

public class WordEdictDictionaryTest {
	
	private WordEdictDictionary wordDictionary;

	@Before
	public void setUp() throws Exception {
		wordDictionary = new WordEdictDictionary("C:\\polaredict.sqlite", new Deinflector("C:\\deinflect.dat"));
	}

	@Test
	public void testWordSearch() throws SQLException {
		Entries wordSearch = wordDictionary.wordSearch("食べられる");
	}

}

package org.rikai;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rikai.dictionary.DictionaryNotLoadedException;
import org.rikai.dictionary.Entries;
import org.rikai.dictionary.edict.EdictDictionary;

/**
 * @author Benjamin
 *
 */
public class EdictDictionaryTest {

	private static final String NON_INFLECTED_VERB = "漬ける";
	private static final String INFLECTED_VERB = "漬けた";
	private static final String EDICT_PATH = "C:\\polaredict.sqlite";
	private EdictDictionary edictDictionary;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		edictDictionary = new EdictDictionary(EDICT_PATH);
		edictDictionary.load();
	}

	/**
	 * Test method for {@link org.rikai.dictionary.edict.EdictDictionary#wordSearch(java.lang.String)}.
	 */
	@Test(expected = DictionaryNotLoadedException.class)
	public void testNotLoaded() {
		EdictDictionary notLoaded = new EdictDictionary(EDICT_PATH);
		notLoaded.wordSearch(INFLECTED_VERB);
	}

	/**
	 * Test method for {@link org.rikai.dictionary.edict.EdictDictionary#wordSearch(java.lang.String)}.
	 */
	@Test
	public void testWordSearchInflected() {
		Entries wordSearch = edictDictionary.wordSearch(INFLECTED_VERB);
		assertEquals(wordSearch.size(), 1);
	}

	/**
	 * Test method for {@link org.rikai.dictionary.edict.EdictDictionary#wordSearch(java.lang.String)}.
	 */
	@Test
	public void testWordSearch() {
		Entries wordSearch = edictDictionary.wordSearch(NON_INFLECTED_VERB);
		assertEquals(wordSearch.size(), 2);
	}

	@After
	public void close() {
		edictDictionary.close();
	}

}

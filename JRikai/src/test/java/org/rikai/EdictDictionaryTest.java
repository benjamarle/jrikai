/**
 * 
 */
package org.rikai;

import org.junit.Before;
import org.junit.Test;
import org.rikai.dictionary.SqliteDatabase;
import org.rikai.dictionary.SqliteDictionary;
import org.rikai.dictionary.edict.EdictDictionary;

/**
 * @author Benjamin
 *
 */
public class EdictDictionaryTest {
	
	private SqliteDictionary edictDictionary;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		edictDictionary = new EdictDictionary("C:\\edict2.sql");
	}

	/**
	 * Test method for {@link org.rikai.dictionary.edict.EdictDictionary#wordSearch(java.lang.String)}.
	 */
	@Test
	public void testWordSearch() {
		edictDictionary.close();
	}

}

package org.rikai;

import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.rikai.deinflector.Deinflector;
import org.rikai.dictionary.Entries;
import org.rikai.dictionary.wordnet.WordnetDictionary;
import org.rikai.dictionary.wordnet.WordnetEntry;
import org.rikai.dictionary.wordnet.WordnetExample;

public class WordnetDictionaryTest {

	private static final String KANJI_WORD = "駐車場";
	private static final String INFLECTED_VERB = "食べられる";
	private static final String NON_INFLECTED_VERB = "食べる";

	@Test
	public void testBasicSearch() throws IOException {
		WordnetDictionary wDic = newDic();
		wDic.load();
		Entries<WordnetEntry> query = wDic.query(KANJI_WORD);
		for (WordnetEntry wordnetEntry : query) {
			System.out.println(wordnetEntry.toString());
		}
		Assert.assertTrue(query.size() > 0);
	}

	@Test
	public void testInflectedWordSearch() throws SQLException, IOException {
		WordnetDictionary wDic = newDic();
		wDic.load();
		Entries<WordnetEntry> wordSearch = wDic.wordSearch(INFLECTED_VERB);
		assertFalse(wordSearch.isEmpty());
		boolean originalDiffMatch = false;
		for (WordnetEntry wordnetEntry : wordSearch) {
			originalDiffMatch |= !wordnetEntry.getOriginalWord().equals(wordnetEntry.getWord());
			System.out.println(wordnetEntry.toStringCompact());
		}
		Assert.assertTrue(originalDiffMatch);
	}

	@Test
	public void testExamples() throws IOException {
		WordnetDictionary wDic = newDic();
		wDic.setMaxNbResults(5);
		wDic.load();
		Entries<WordnetEntry> query = wDic.query(NON_INFLECTED_VERB);
		for (WordnetEntry wordnetEntry : query) {
			System.out.println(wordnetEntry.toStringCompact());
			List<WordnetExample> examples = wDic.getExamples(wordnetEntry);
			Assert.assertTrue(examples.size() > 0);
			for (WordnetExample wordnetExample : examples) {
				System.out.println(wordnetExample);
			}
		}
		Assert.assertTrue(query.size() > 0);

	}

	private WordnetDictionary newDic() throws IOException {
		return new WordnetDictionary("C:\\Users\\Benjamin\\Desktop\\wordnet.sqlite",
				new Deinflector(this.getClass().getResourceAsStream("/deinflect.dat")));
	}

}

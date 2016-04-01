package org.rikai;

import org.junit.Assert;
import org.junit.Test;
import org.rikai.dictionary.Entries;
import org.rikai.dictionary.epwing.DefaultEpwingDictionary;
import org.rikai.dictionary.epwing.EpwingEntry;

public class EpwingDictionaryTest {

	private static final String AMERIKA_HIRAGANA = "あめりか";
	private static final String VOCAB = "意見";

	@Test
	public void basicSearch() {
		DefaultEpwingDictionary edic = newDic();
		Entries<EpwingEntry<String>> query = edic.query(VOCAB);

		for (EpwingEntry<String> epwingEntry : query) {
			System.out.println(epwingEntry.getText());
		}
		Assert.assertTrue(query.size() > 0);
	}

	@Test
	public void hiraganaSearch() {
		DefaultEpwingDictionary edic = newDic();
		Entries<EpwingEntry<String>> query = edic.query(AMERIKA_HIRAGANA);

		for (EpwingEntry<String> epwingEntry : query) {
			System.out.println(epwingEntry.getText());
		}
		Assert.assertTrue(query.size() > 0);
	}

	private DefaultEpwingDictionary newDic() {
		DefaultEpwingDictionary edic = new DefaultEpwingDictionary("C:\\Users\\Benjamin\\Desktop\\Kenkyusha");
		edic.load();
		return edic;
	}
}

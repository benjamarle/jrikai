package org.rikai;

import org.junit.Assert;
import org.junit.Test;
import org.rikai.dictionary.Entries;
import org.rikai.dictionary.epwing.EpwingDictionary;
import org.rikai.dictionary.epwing.EpwingEntry;

public class EpwingDictionaryTest {

	private static final String VOCAB = "意見";

	@Test
	public void basicSearch() {
		EpwingDictionary edic = new EpwingDictionary("C:\\Users\\Benjamin\\Desktop\\Kenkyusha");
		edic.load();
		Entries<EpwingEntry> query = edic.query(VOCAB);
		Assert.assertTrue(query.size() > 0);
	}
}

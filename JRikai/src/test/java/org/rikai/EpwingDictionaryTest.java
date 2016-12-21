package org.rikai;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.rikai.dictionary.Entries;
import org.rikai.dictionary.epwing.DefaultEpwingDictionary;
import org.rikai.dictionary.epwing.EpwingEntry;

import fuku.eb4j.EBException;
import fuku.eb4j.ExtFont;
import fuku.eb4j.SubBook;
import fuku.eb4j.util.ImageUtil;

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

	@Test
	public void imageTest() {
		int code = 46695;
		DefaultEpwingDictionary edic = newDic();
		SubBook subBook = edic.getSubBook();
		ExtFont font = subBook.getFont();
		try {
			byte[] fontImage = null;
			int width = 0, height;

			fontImage = font.getWideFont(code);
			width = font.getWideFontWidth();
			height = font.getFontHeight();

			byte[] pngImage = ImageUtil.bitmapToPNG(fontImage, width, height);
			Assert.assertEquals(
					"[-119, 80, 78, 71, 13, 10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 16, 0, 0, 0, 16, 8, 6, 0, 0, 0, 31, -13, -1, 97, 0, 0, 0, 89, 73, 68, 65, 84, 120, -100, -83, -111, 91, 10, 0, 32, 8, 4, -9, -2, -105, 46, -16, 35, -124, -42, -57, 102, 66, 125, 40, 13, -45, -118, 53, 44, -40, 5, -56, -25, 0, 94, 30, 123, 8, 60, -19, -46, 83, 0, -63, 112, 14, -56, -52, -2, 26, -116, 50, 16, 18, -41, 0, 89, -107, 95, -56, 114, -72, 0, -107, 110, -71, -123, -18, 6, 24, 24, -127, -106, -106, 65, 103, -9, 108, 102, -67, 64, -117, 102, -64, 122, 27, -53, -14, 24, 5, -126, -33, 77, 110, 0, 0, 0, 0, 73, 69, 78, 68, -82, 66, 96, -126]",
					Arrays.toString(pngImage));
		} catch (EBException e) {
			e.printStackTrace();
		}

	}
}

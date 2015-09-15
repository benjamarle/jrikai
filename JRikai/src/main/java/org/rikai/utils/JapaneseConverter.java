package org.rikai.utils;

public class JapaneseConverter {

	private final static char[] ch = new char[] { '\u3092', '\u3041', '\u3043', '\u3045', '\u3047', '\u3049', '\u3083',
			'\u3085', '\u3087', '\u3063', '\u30FC', '\u3042', '\u3044', '\u3046', '\u3048', '\u304A', '\u304B',
			'\u304D', '\u304F', '\u3051', '\u3053', '\u3055', '\u3057', '\u3059', '\u305B', '\u305D', '\u305F',
			'\u3061', '\u3064', '\u3066', '\u3068', '\u306A', '\u306B', '\u306C', '\u306D', '\u306E', '\u306F',
			'\u3072', '\u3075', '\u3078', '\u307B', '\u307E', '\u307F', '\u3080', '\u3081', '\u3082', '\u3084',
			'\u3086', '\u3088', '\u3089', '\u308A', '\u308B', '\u308C', '\u308D', '\u308F', '\u3093' };
	private final static char[] cv = new char[] { '\u30F4', '\uFF74', '\uFF75', '\u304C', '\u304E', '\u3050', '\u3052',
			'\u3054', '\u3056', '\u3058', '\u305A', '\u305C', '\u305E', '\u3060', '\u3062', '\u3065', '\u3067',
			'\u3069', '\uFF85', '\uFF86', '\uFF87', '\uFF88', '\uFF89', '\u3070', '\u3073', '\u3076', '\u3079',
			'\u307C' };
	private final static char[] cs = new char[] { '\u3071', '\u3074', '\u3077', '\u307A', '\u307D' };

	/**
	 * converts all katakana and half-width kana to full-width hiragana
	 *
	 * @param word
	 *            the word to convert
	 * @return the hiragana
	 */
	public static String toHiragana(String word) {
		return toHiragana(word, false, null);
	}

	/**
	 * convert katakana and half-width kana to full-width hiragana
	 *
	 * @param word
	 *            the word to convert
	 * @param discard
	 *            if true, conversion is stopped when a non-japanese char is encountered
	 * @return the hiragana
	 */
	public static String toHiragana(String word, boolean discard, int[] trueLen) {
		char u, v;
		char previous = 0;
		String result = "";

		for (int i = 0; i < word.length(); ++i) {
			u = v = word.charAt(i);

			if (u <= 0x3000) {
				if (discard) {
					break;
				}
				// else nothing to do for this char
			}
			// full-width katakana to hiragana
			else if ((u >= 0x30A1) && (u <= 0x30F3)) {
				u -= 0x60;
			}
			// half-width katakana to hiragana
			else if ((u >= 0xFF66) && (u <= 0xFF9D)) {
				u = ch[u - 0xFF66];
			}
			// voiced (used in half-width katakana) to hiragana
			else if (u == 0xFF9E) {
				if ((previous >= 0xFF73) && (previous <= 0xFF8E)) {
					result = result.substring(0, result.length() - 1);
					u = cv[previous - 0xFF73];
				}
			}
			// semi-voiced (used in half-width katakana) to hiragana
			else if (u == 0xFF9F) {
				if ((previous >= 0xFF8A) && (previous <= 0xFF8E)) {
					result = result.substring(0, result.length() - 1);
					u = cs[previous - 0xFF8A];
				}
			}
			// ignore J~
			else if (u == 0xFF5E) {
				previous = 0;
				continue;
			}

			result += u;
			if (trueLen != null) {
				trueLen[result.length() - 1] = i + 1;
			}
			previous = v;
		}

		return result;
	}

}

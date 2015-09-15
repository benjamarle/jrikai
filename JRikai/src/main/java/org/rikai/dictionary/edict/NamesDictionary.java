package org.rikai.dictionary.edict;

import org.rikai.dictionary.Entries;

public class NamesDictionary extends EdictDictionary {

	private static final int DEFAULT_MAX_COUNT = 10;

	public NamesDictionary(String path) {
		super(path);
	}

	/**
	 * find all variants of the specified word, if any
	 *
	 * @param word
	 *            the base word
	 * @return all the variants of the specified word
	 */
	@Override
	public Entries wordSearch(String word) {
		return wordSearch(word, DEFAULT_MAX_COUNT);
	}

}

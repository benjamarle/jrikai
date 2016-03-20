package org.rikai.dictionary.epwing;

import org.rikai.dictionary.AbstractEntry;

public class EpwingEntry extends AbstractEntry {

	private String originalWord;

	private String heading;

	private String text;

	public EpwingEntry(String originalWord, String heading, String text) {
		super();
		this.originalWord = originalWord;
		this.heading = heading;
		this.text = text;
	}

	@Override
	public String toStringCompact() {
		return heading + "\n" + text;
	}

	@Override
	public int getLength() {
		return originalWord.length();
	}

}

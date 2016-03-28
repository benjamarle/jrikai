package org.rikai.dictionary.deinflectable;

import org.rikai.deinflector.DeinflectedWord;
import org.rikai.dictionary.AbstractEntry;

public abstract class DeinflectedEntry extends AbstractEntry {

	/** variant */
	protected DeinflectedWord variant;

	/** the word **/
	protected String word = "";

	/** the inflection reason */
	protected String reason = "";

	public DeinflectedEntry() {

	}

	/**
	 * constructor
	 * 
	 * @param word
	 *            the word
	 * @param reason
	 *            how the original inflected word transformed to the given word
	 */
	public DeinflectedEntry(DeinflectedWord variant, String word, String reason) {
		this.variant = variant;
		this.word = word;
		this.reason = reason;
	}

	/**
	 * returns the word of this entry
	 * 
	 * @return the word of this entry
	 */
	public String getWord() {
		return this.word;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return this.reason;
	}

	/**
	 * @param reason
	 *            the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the original word prior to deinflection if there was one, otherwise gives back the matched word
	 */
	public String getOriginalWord() {
		if (variant == null)
			this.getWord();
		return variant.getOriginalWord();
	}

	/**
	 * @param word
	 *            the word to set
	 */
	public void setWord(String word) {
		this.word = word;
	}

}

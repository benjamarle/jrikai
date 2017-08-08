/*
Copyright (C) 2013 Ray Zhou

JadeRead is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

JadeRead is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with JadeRead.  If not, see <http://www.gnu.org/licenses/>

Author: Ray Zhou
Date: 2013 04 26

*/
package org.rikai.dictionary.edict;

import org.rikai.deinflector.DeinflectedWord;
import org.rikai.dictionary.deinflectable.DeinflectedEntry;

/**
 * a dictionary entry consists of the word and the definition (optionally the reading)
 * 
 * @author ray
 *
 */
public class EdictEntry extends DeinflectedEntry {

	/** the reading of this word in hiragana, if exists */
	private String reading = "";

	/** the definition of the word */
	private String gloss = "";

	/** the pitch accent of the word, in number format */
	private String pitch = "";

	public EdictEntry() {
	}

	/**
	 * constructor
	 * 
	 * @param word
	 *            the word
	 * @param reading
	 *            the reading of the word
	 * @param gloss
	 *            the definition of the word
	 * @param reason
	 *            how the original inflected word transformed to the given word
	 * @param pitch
	 * 			  the number that conveys the word's pitch accent
	 */
	public EdictEntry(DeinflectedWord variant, String word, String reading, String gloss, String reason, String pitch) {
		if (word == null) {
			word = reading;
		}

		this.variant = variant;
		this.word = word;
		this.reading = reading;
		this.gloss = semicolonGloss(gloss);
		this.reason = reason;
		this.pitch = pitch;
	}

	private String semicolonGloss(String gloss)
	{
		gloss = gloss.replace("/", "; ");
		return gloss;
	}

	private int getToStringMaxLength() {
		return word.length() + gloss.length() + reading.length() + reason.length() + 20;
	}

	/**
	 * return a string representation of this entry
	 * 
	 * @return a string representation of this entry
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder(getToStringMaxLength());

		result.append(this.word).append(' ');

		if (this.reading.length() != 0) {
			result.append(this.reading).append(' ');
		}

		// if (reason.length() != 0) {
		// result.append("(").append(reason).append(") ");
		// }

		result.append(this.gloss);

		return result.toString();
	}

	/**
	 * return a string representation of this entry in a compact form (max 1 newline character)
	 * 
	 * @return a string representation of this entry in a compact form.
	 */
	@Override
	public String toStringCompact() {
		StringBuilder result = new StringBuilder(getToStringMaxLength());

		result.append(this.word).append(' ');

		if (this.reading.length() != 0) {
			result.append('[').append(this.reading).append("] ");
		}
		if (this.reason.length() != 0) {
			result.append("(").append(this.reason).append(")");
		}

		result.append('\n').append(this.gloss);

		return result.toString();
	}

	/**
	 * @return the reading
	 */
	public String getReading() {
		return this.reading;
	}

	/**
	 * @param reading
	 *            the reading to set
	 */
	public void setReading(String reading) {
		this.reading = reading;
	}

	/**
	 * @return the pitch
	 */
	public String getPitch() {
		return this.pitch;
	}

	/**
	 * @param pitch
	 * 			  the pitch to set
     */
	public void setPitch(String pitch) { this.pitch = pitch; }

	/**
	 * @return the gloss
	 */
	public String getGloss() {
		return this.gloss;
	}

	/**
	 * @param gloss
	 *            the gloss to set
	 */
	public void setGloss(String gloss) {
		this.gloss = gloss;
	}

	/**
	 * @return the length
	 */
	public int getLength() {
		return getOriginalWord().length();
	}
}

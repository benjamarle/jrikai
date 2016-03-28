package org.rikai.dictionary.wordnet;

import org.rikai.dictionary.AbstractEntry;

public class WordnetEntry extends AbstractEntry {

	private String word;

	private String gloss;

	private String rank;

	private int lexid;

	private int freq;

	public WordnetEntry() {

	}

	public WordnetEntry(String word, String gloss, String rank, int lexid, int freq) {
		super();
		this.word = word;
		this.gloss = gloss;
		this.rank = rank;
		this.lexid = lexid;
		this.freq = freq;
	}

	public String toStringCompact() {
		StringBuilder result = new StringBuilder();

		result.append(this.word);

		result.append('\n').append(this.gloss);

		return result.toString();
	}

	@Override
	public int getLength() {
		return this.word.length();
	}

	/**
	 * @return the word
	 */
	public String getWord() {
		return word;
	}

	/**
	 * @param word
	 *            the word to set
	 */
	public void setWord(String word) {
		this.word = word;
	}

	/**
	 * @return the gloss
	 */
	public String getGloss() {
		return gloss;
	}

	/**
	 * @param gloss
	 *            the gloss to set
	 */
	public void setGloss(String gloss) {
		this.gloss = gloss;
	}

	/**
	 * @return the rank
	 */
	public String getRank() {
		return rank;
	}

	/**
	 * @param rank
	 *            the rank to set
	 */
	public void setRank(String rank) {
		this.rank = rank;
	}

	/**
	 * @return the lexid
	 */
	public int getLexid() {
		return lexid;
	}

	/**
	 * @param lexid
	 *            the lexid to set
	 */
	public void setLexid(int lexid) {
		this.lexid = lexid;
	}

	/**
	 * @return the freq
	 */
	public int getFreq() {
		return freq;
	}

	/**
	 * @param freq
	 *            the freq to set
	 */
	public void setFreq(int freq) {
		this.freq = freq;
	}

}

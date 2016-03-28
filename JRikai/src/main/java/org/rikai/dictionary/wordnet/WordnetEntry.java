package org.rikai.dictionary.wordnet;

import org.rikai.deinflector.DeinflectedWord;
import org.rikai.dictionary.deinflectable.DeinflectedEntry;

public class WordnetEntry extends DeinflectedEntry {

	private String synset;

	private String gloss;

	private String rank;

	private int lexid;

	private int freq;

	private String partOfSpeech;

	public WordnetEntry() {

	}

	public WordnetEntry(DeinflectedWord variant, String word, String synset, String reason, String pos, String gloss,
			String rank, int lexid, int freq) {
		super(variant, word, reason);
		this.word = word;
		this.synset = synset;
		this.gloss = gloss;
		this.rank = rank;
		this.lexid = lexid;
		this.freq = freq;
		this.partOfSpeech = pos;
	}

	/**
	 * return a string representation of this entry
	 * 
	 * @return a string representation of this entry
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

		result.append(this.word).append(' ');

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
		StringBuilder result = new StringBuilder();

		result.append(this.word).append(' ');

		if (this.reason.length() != 0) {
			result.append("(").append(this.reason).append(")");
		}

		result.append('\n').append(this.gloss);

		return result.toString();
	}

	@Override
	public int getLength() {
		return this.word.length();
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

	/**
	 * @return the partOfSpeech
	 */
	public String getPartOfSpeech() {
		return partOfSpeech;
	}

	/**
	 * @param partOfSpeech
	 *            the partOfSpeech to set
	 */
	public void setPartOfSpeech(String partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
	}

	/**
	 * @return the synset
	 */
	public String getSynset() {
		return synset;
	}

	/**
	 * @param synset
	 *            the synset to set
	 */
	public void setSynset(String synset) {
		this.synset = synset;
	}

}

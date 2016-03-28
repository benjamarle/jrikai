package org.rikai.dictionary.wordnet;

public class WordnetExample {

	private WordnetEntry entry;

	private String japaneseSentence;

	private String englishSentence;

	private int number;

	public WordnetExample(WordnetEntry entry, int number, String japaneseSentence, String englishSentence) {
		super();
		this.entry = entry;
		this.number = number;
		this.japaneseSentence = japaneseSentence;
		this.englishSentence = englishSentence;
	}

	/**
	 * @return the entry
	 */
	public WordnetEntry getEntry() {
		return entry;
	}

	/**
	 * @return the japaneseSentence
	 */
	public String getJapaneseSentence() {
		return japaneseSentence;
	}

	/**
	 * @return the englishSentence
	 */
	public String getEnglishSentence() {
		return englishSentence;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.number + ") " + japaneseSentence + "\n" + englishSentence;
	}

}

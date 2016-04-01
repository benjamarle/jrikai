package org.rikai.dictionary.epwing;

import org.rikai.dictionary.AbstractEntry;

import fuku.eb4j.EBException;
import fuku.eb4j.Result;
import fuku.eb4j.SubBook;
import fuku.eb4j.hook.DefaultHook;
import fuku.eb4j.hook.Hook;

public class EpwingEntry<T> extends AbstractEntry {

	private String originalWord;

	private Result result;

	private SubBook subBook;

	private Hook<T> hook;

	public EpwingEntry(String originalWord, Result result, SubBook subBook, Hook<T> hook) {
		super();
		this.originalWord = originalWord;
		this.result = result;
		this.subBook = subBook;
		this.hook = hook;
	}

	/**
	 * @return the originalWord
	 */
	public String getOriginalWord() {
		return originalWord;
	}

	/**
	 * @param originalWord
	 *            the originalWord to set
	 */
	public void setOriginalWord(String originalWord) {
		this.originalWord = originalWord;
	}

	/**
	 * @return the result
	 */
	public Result getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(Result result) {
		this.result = result;
	}

	public T getHeading() {
		try {
			return result.getHeading(this.hook);
		} catch (EBException e) {
			return null;
		}
	}

	public T getText() {
		try {
			return result.getText(this.hook);
		} catch (EBException e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rikai.dictionary.AbstractEntry#toStringCompact()
	 */
	@Override
	public String toStringCompact() {
		try {
			return result.getText(new DefaultHook(subBook));
		} catch (EBException e) {
			return "Error computing result";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rikai.dictionary.AbstractEntry#getLength()
	 */
	@Override
	public int getLength() {
		return this.getOriginalWord().length();
	}

}

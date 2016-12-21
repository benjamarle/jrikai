package org.rikai.dictionary.epwing;

import org.rikai.dictionary.Dictionary;
import org.rikai.dictionary.DictionaryException;
import org.rikai.dictionary.DictionaryNotLoadedException;
import org.rikai.dictionary.Entries;

import fuku.eb4j.Book;
import fuku.eb4j.EBException;
import fuku.eb4j.Result;
import fuku.eb4j.Searcher;
import fuku.eb4j.SubBook;
import fuku.eb4j.hook.Hook;

public class EpwingDictionary<T> implements Dictionary<EpwingEntry<T>> {

	private String path;

	private Book book;

	private SubBook subBook;

	private int nbSubBooks;

	private Hook<T> hook;

	private boolean loaded = false;

	// TODO Add that in the library as it is useful for all of them
	private int maxQueryLength = Integer.MAX_VALUE;

	public EpwingDictionary(String path, Hook<T> hook) {
		this.path = path;
		this.hook = hook;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rikai.dictionary.Dictionary#load()
	 */
	public void load() {
		try {
			book = new Book(path);

			SubBook[] subbooks = book.getSubBooks();

			nbSubBooks = subbooks.length;
			subBook = subbooks[0];
		} catch (EBException e) {
			throw new DictionaryException(e);
		}
		loaded = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rikai.dictionary.Dictionary#query(java.lang.String)
	 */
	public Entries<EpwingEntry<T>> query(String q) {
		q = q.replaceAll("[A-Za-z0-9 ]", "").trim();
		if (maxQueryLength < q.length())
			q = q.substring(0, maxQueryLength);

		return query(q, subBook);
	}

	public Entries<EpwingEntry<T>> query(String q, SubBook subBook) {
		String query = q;
		Entries<EpwingEntry<T>> entries = new Entries<EpwingEntry<T>>();
		while (query.length() > 0) {
			entries.addAll(singleQuery(query, subBook));
			query = query.substring(0, query.length() - 1);
		}
		return entries;
	}

	public Entries<EpwingEntry<T>> singleQuery(String q, SubBook subBook) {
		if (!loaded)
			throw new DictionaryNotLoadedException();
		Entries<EpwingEntry<T>> entries = new Entries<EpwingEntry<T>>();
		try {

			Searcher searcher = subBook.searchExactword(q); // 前方一致
			while (true) {
				Result result = searcher.getNextResult();
				if (result == null)
					break;

				entries.add(makeEntry(q, result, subBook));
			}
			if(!entries.isEmpty())
				entries.setMaxLen(q.length());
		} catch (EBException e) {
		}
		return entries;
	}

	protected EpwingEntry<T> makeEntry(String originalWord, Result result, SubBook subBook) {
		return new EpwingEntry<T>(originalWord, result, subBook, hook);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rikai.dictionary.Dictionary#isLoaded()
	 */
	public boolean isLoaded() {
		return loaded;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rikai.dictionary.Dictionary#close()
	 */
	public void close() {
		// nothing to be done
	}

	/**
	 * @return the defaultSubBook
	 */
	public SubBook getSubBook() {
		return subBook;
	}

	/**
	 * @param subBook
	 *            the defaultSubBook to set
	 */
	public void setSubBook(int index) {
		if (index >= nbSubBooks)
			throw new IndexOutOfBoundsException();
		SubBook subBook = book.getSubBook(index);
		this.subBook = subBook;
	}

	/**
	 * @return the hook
	 */
	public Hook<T> getHook() {
		return hook;
	}

	/**
	 * @param hook
	 *            the hook to set
	 */
	public void setHook(Hook<T> hook) {
		this.hook = hook;
	}

	/**
	 * @return the maxQueryLength
	 */
	public int getMaxQueryLength() {
		return maxQueryLength;
	}

	/**
	 * @param maxQueryLength
	 *            the maxQueryLength to set
	 */
	public void setMaxQueryLength(int maxQueryLength) {
		this.maxQueryLength = maxQueryLength;
	}

	public String getName() {
		return subBook.getName();
	}

}

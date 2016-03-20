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
import fuku.eb4j.hook.DefaultHook;

public class EpwingDictionary implements Dictionary<EpwingEntry> {

	private String path;

	private Book book;

	private SubBook defaultSubBook;

	private int nbSubBooks;

	private boolean loaded = false;

	public EpwingDictionary(String path) {
		this.path = path;
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
			defaultSubBook = subbooks[0];
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
	public Entries<EpwingEntry> query(String q) {
		return query(q, defaultSubBook);
	}

	public Entries<EpwingEntry> query(String q, SubBook subBook) {
		if (!loaded)
			throw new DictionaryNotLoadedException();
		Entries<EpwingEntry> entries = new Entries<EpwingEntry>();
		try {
			DefaultHook hook = new DefaultHook(subBook);
			Searcher searcher = subBook.searchWord(q); // 前方一致
			while (true) {
				Result result = searcher.getNextResult();
				if (result == null)
					break;
				final String heading = result.getHeading(hook);
				final String text = result.getText(hook);
				entries.add(makeEntry(q, heading, text));
			}
		} catch (EBException e) {
		}
		return entries;
	}

	private String processText(String text) {
		return text.replaceAll("\\[GAIJI=w.{4}\\]", "");
	}

	protected EpwingEntry makeEntry(String originalWord, String heading, String text) {
		return new EpwingEntry(originalWord, heading, processText(text));
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
	public SubBook getDefaultSubBook() {
		return defaultSubBook;
	}

	/**
	 * @param defaultSubBook
	 *            the defaultSubBook to set
	 */
	public void setDefaultSubBook(int index) {
		if (index >= nbSubBooks)
			throw new IndexOutOfBoundsException();
		SubBook subBook = book.getSubBook(index);
		this.defaultSubBook = subBook;
	}

}

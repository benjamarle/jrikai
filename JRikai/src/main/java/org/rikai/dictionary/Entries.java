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
package org.rikai.dictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * thin wrapper that wraps an array of Entry so that a Parceable can be created from multiple number of Entry
 * 
 * Basically, Entries contains all possible deinflected words of a given word (not stored in here).
 */
public class Entries<T extends AbstractEntry> extends ArrayList<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8677504304916843981L;

	/** the length of the longest word in the entries */
	private int maxLen = 0;

	/** whether or not ALL possible deinflected words are stored in this Entries */
	private boolean complete = true;

	public Entries() {

	}

	/**
	 * used by createFromParcel only
	 * 
	 * @param entries
	 * @param complete
	 * @param maxLen
	 */
	protected Entries(ArrayList<T> entries, boolean complete) {
		this.addAll(entries);
		this.complete = complete;
	}

	public void setMaxLen(int len) {
		this.maxLen = len;
	}

	public int getMaxLen() {
		return this.maxLen;
	}

	public boolean addAll(Entries<T> c) {
		setMaxLen(Math.max(c.getMaxLen(), this.getMaxLen()));
		return super.addAll(c);
	}

	/**
	 * 
	 * @return a list of String of each entry
	 */
	public List<String> getAllEntries() {
		ArrayList<String> result = new ArrayList<String>(size());

		for (T entry : this) {
			result.add(entry.toString());
		}

		return result;
	}

	@Override
	public boolean add(T e) {
		// this.maxLen = Math.max(this.maxLen, e.getLength());
		return super.add(e);
	}

	/**
	 * 
	 * @return a list of String of each entry, in compact form
	 */
	public List<String> getAllEntriesCompact() {

		ArrayList<String> result = new ArrayList<String>(size());

		for (T entry : this) {
			result.add(entry.toStringCompact());
		}

		return result;
	}

	/**
	 * 
	 * @return true if This has all possible word, false otherwise.
	 */
	public boolean isComplete() {
		return this.complete;
	}

	public void setComplete(boolean b) {
		this.complete = b;
	}

}

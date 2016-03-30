/*
This file is part of JRikai.

JRikai is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

JRikai is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with JRikai.  If not, see <http://www.gnu.org/licenses/>.

Author: Benjamin Marlé
*/
package org.rikai.dictionary.kanji;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.rikai.dictionary.Dictionary;
import org.rikai.dictionary.DictionaryNotLoadedException;
import org.rikai.dictionary.Entries;

public class KanjiDictionary extends HashMap<Character, KanjiEntry> implements Dictionary<KanjiEntry> {

	private static final long serialVersionUID = -6219548581660831150L;

	private boolean isLoaded;

	private int maxNbQueries = 1;

	private boolean stopAtFirstNonKanji = true;

	private InputStream inputStream;

	public KanjiDictionary(InputStream inputStream) {
		super(13000);
		this.inputStream = inputStream;
	}

	public KanjiDictionary(InputStream inputStream, int maxNbQueries) throws FileNotFoundException {
		this(inputStream);
		this.maxNbQueries = maxNbQueries;
	}

	public KanjiDictionary(String path) throws FileNotFoundException {
		this(new FileInputStream(path));
	}

	public KanjiDictionary(String path, int maxNbQueries) throws FileNotFoundException {
		this(path);
		this.maxNbQueries = maxNbQueries;
	}

	public void load() {
		if (this.isLoaded) {
			return;
		}
		try {
			loadDictionary(this.inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		this.isLoaded = true;
	}

	/**
	 * @return the isLoaded
	 */
	public boolean isLoaded() {
		return this.isLoaded;
	}

	private void loadDictionary(InputStream inputStream) throws IOException {
		if (this.isLoaded()) {
			this.close();
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(this.inputStream, "UTF-8"));

		String line = null;
		while ((line = reader.readLine()) != null) {
			KanjiEntry kanjiEntry = extractEntry(line);
			if (kanjiEntry == null) {
				continue;
			}
			this.put(kanjiEntry.getKanji(), kanjiEntry);
		}
		reader.close();
	}

	private KanjiEntry extractEntry(String line) {
		String[] split = line.split("\\|", -1);
		if (split.length != 6) {
			return null;
		}
		String kanjiStr = split[0];
		char kanjiChar = kanjiStr.charAt(0);
		KanjiEntry kanjiEntry = makeEntry(kanjiChar);

		String miscStr = split[1];
		kanjiEntry.setMiscString(miscStr);

		String yomiStr = split[2];
		kanjiEntry.setYomi(yomiStr);

		String nanoriStr = split[3];
		kanjiEntry.setNanori(nanoriStr);

		String bushumeiStr = split[4];
		kanjiEntry.setBushumei(bushumeiStr);

		String definitionStr = split[5];
		kanjiEntry.setDefinition(definitionStr);

		postParsingProcessing(kanjiEntry);
		return kanjiEntry;
	}

	protected void postParsingProcessing(KanjiEntry entry) {

	}

	protected KanjiEntry makeEntry(char kanjiChar) {
		return new KanjiEntry(kanjiChar);
	}

	public Entries<KanjiEntry> query(String q) {
		return query(q, this.stopAtFirstNonKanji);
	}

	public Entries<KanjiEntry> query(String q, boolean stopAtFirstNonKanji) {
		if (!this.isLoaded()) {
			throw new DictionaryNotLoadedException();
		}
		Entries<KanjiEntry> entries = new Entries<KanjiEntry>();
		int maxIndex = Math.min(q.length(), maxNbQueries);
		for (int i = 0, n = maxIndex; i < n; i++) {
			char c = q.charAt(i);
			KanjiEntry kanjiEntry = get(c);
			if (kanjiEntry == null) {
				if (stopAtFirstNonKanji) {
					entries.setMaxLen(entries.size());
					return entries;
				} else {
					continue;
				}
			}
			entries.add(kanjiEntry);
		}

		entries.setMaxLen(entries.size());
		return entries;
	}

	/**
	 * @return the stopAtFirstNonKanji
	 */
	public boolean isStopAtFirstNonKanji() {
		return stopAtFirstNonKanji;
	}

	/**
	 * @param stopAtFirstNonKanji
	 *            the stopAtFirstNonKanji to set
	 */
	public void setStopAtFirstNonKanji(boolean stopAtFirstNonKanji) {
		this.stopAtFirstNonKanji = stopAtFirstNonKanji;
	}

	public void close() {
		this.clear();
	}

	/**
	 * @return the maxNbQueries
	 */
	public int getMaxNbQueries() {
		return maxNbQueries;
	}

	/**
	 * @param maxNbQueries
	 *            the maxNbQueries to set
	 */
	public void setMaxNbQueries(int maxNbQueries) {
		this.maxNbQueries = maxNbQueries;
	}

}

package org.rikai.dictionary.kanji;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.rikai.dictionary.Dictionary;
import org.rikai.dictionary.DictionaryNotLoadedException;
import org.rikai.dictionary.Entries;

public class KanjiDictionary extends HashMap<Character, KanjiEntry>implements Dictionary {

	private static final long serialVersionUID = -6219548581660831150L;

	private boolean isLoaded;

	private String path;

	public KanjiDictionary(String path) {
		super(13000);
		this.path = path;
	}

	public void load() {
		try {
			loadDictionary(this.path);
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

	private void loadDictionary(String path) throws IOException {
		if (this.isLoaded()) {
			this.close();
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));

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
		KanjiEntry kanjiEntry = new KanjiEntry(kanjiChar);

		String miscStr = split[1];
		kanjiEntry.setMiscString(miscStr);

		String yomiStr = split[2];
		kanjiEntry.setYomi(yomiStr);

		String nanoriStr = split[3];
		kanjiEntry.setNanori(nanoriStr);

		String bushumeiStr = split[4];
		kanjiEntry.setBushmei(bushumeiStr);

		String definitionStr = split[5];
		kanjiEntry.setDefinition(definitionStr);

		return kanjiEntry;
	}

	public Entries query(String q) {
		if (!this.isLoaded()) {
			throw new DictionaryNotLoadedException();
		}
		char firstChar = q.charAt(0);
		Entries entries = new Entries();
		KanjiEntry kanjiEntry = get(firstChar);
		if (kanjiEntry != null) {
			entries.add(kanjiEntry);
		}
		return entries;
	}

	public void close() {
		this.clear();
	}

}

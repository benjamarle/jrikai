package org.rikai.dictionary.kanji;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.rikai.dictionary.Dictionary;
import org.rikai.dictionary.Entries;

public class KanjiDictionary extends HashMap<Character, KanjiEntry>implements Dictionary {

	private Pattern miscPattern = Pattern.compile("^([A-Z]+)(.*)");

	private boolean isLoaded;

	private String path;

	public KanjiDictionary(String path) {
		super(13000);
		this.path = path;
	}

	public void load() {
		try {
			loadDictionary(path);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		isLoaded = true;
	}
	
	/**
	 * @return the isLoaded
	 */
	public boolean isLoaded() {
		return isLoaded;
	}

	private void loadDictionary(String path) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));

		String line = null;
		while ((line = reader.readLine()) != null) {
			KanjiEntry kanjiEntry = extractEntry(line);
			if (kanjiEntry == null)
				continue;
			this.put(kanjiEntry.getKanji(), kanjiEntry);
		}
		reader.close();
	}

	private KanjiEntry extractEntry(String line) {
		String[] split = line.split("\\|", -1);
		if (split.length != 6)
			return null;
		String kanjiStr = split[0];
		char kanjiChar = kanjiStr.charAt(0);
		KanjiEntry kanjiEntry = new KanjiEntry(kanjiChar);

		String miscStr = split[1];
		extractMisc(kanjiEntry, miscStr);

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

	private Set<String> unidentifiedTags = new HashSet<String>();

	private void extractMisc(KanjiEntry kanjiEntry, String miscStr) {
		String[] miscProperties = miscStr.split(" ");
		Map<KanjiTag, String> miscMap = kanjiEntry.getMisc();
		for (String misc : miscProperties) {
			Matcher matcher = miscPattern.matcher(misc);
			if (!matcher.matches())
				continue;
			String tagStr = matcher.group(1);
			KanjiTag tag = null;
			try {
				tag = KanjiTag.valueOf(tagStr);
			} catch (IllegalArgumentException e) {
				if (unidentifiedTags.add(tagStr)) {
					System.err.println("Unidentified kanji tag: [" + tagStr + "]");
				}
				continue;
			}
			String value = matcher.group(2);
			String miscMapValue = miscMap.get(tag);
			if (miscMapValue == null)
				miscMapValue = value;
			else
				miscMapValue += " " + value;
			miscMap.put(tag, miscMapValue);
		}
	}

	public Entries query(String q) {
		char firstChar = q.charAt(0);
		Entries entries = new Entries();
		KanjiEntry kanjiEntry = get(firstChar);
		if (kanjiEntry != null)
			entries.add(kanjiEntry);
		return entries;
	}

}

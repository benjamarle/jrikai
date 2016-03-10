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

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.rikai.dictionary.AbstractEntry;

public class KanjiEntry extends AbstractEntry {
	private static Pattern miscPattern = Pattern.compile("^([A-Z]+)(.*)");
	public static boolean lazyLoad = false;

	private Character kanji;

	private String definition;

	private String miscString = "";

	private Map<KanjiTag, String> misc = new Hashtable<KanjiTag, String>();

	private boolean loaded;

	private Set<String> unidentifiedTags = new HashSet<String>();

	private String yomi;

	private String nanori;

	private String bushmei;

	public KanjiEntry(Character kanji) {
		super();
		this.kanji = kanji;
	}

	/**
	 * @return the kanji
	 */
	public Character getKanji() {
		return this.kanji;
	}

	@Override
	public int getLength() {
		return 1;
	}

	/**
	 * @param kanji
	 *            the kanji to set
	 */
	public void setKanji(Character kanji) {
		this.kanji = kanji;
	}

	/**
	 * @return the definition
	 */
	public String getDefinition() {
		return this.definition;
	}

	/**
	 * @param definition
	 *            the definition to set
	 */
	public void setDefinition(String definition) {
		this.definition = definition;
	}

	/**
	 * @return the misc
	 */
	public Map<KanjiTag, String> getMisc() {
		if (!this.loaded) {
			this.load();
		}
		return this.misc;
	}

	/**
	 * @param misc
	 *            the misc to set
	 */
	public void setMisc(Map<KanjiTag, String> misc) {
		this.misc = misc;
	}

	/**
	 * @return the yomi
	 */
	public String getYomi() {
		return this.yomi;
	}

	/**
	 * @param yomi
	 *            the yomi to set
	 */
	public void setYomi(String yomi) {
		this.yomi = yomi;
	}

	/**
	 * @return the nanori
	 */
	public String getNanori() {
		return this.nanori;
	}

	/**
	 * @param nanori
	 *            the nanori to set
	 */
	public void setNanori(String nanori) {
		this.nanori = nanori;
	}

	/**
	 * @return the bushmei
	 */
	public String getBushmei() {
		return this.bushmei;
	}

	/**
	 * @param bushmei
	 *            the bushmei to set
	 */
	public void setBushmei(String bushmei) {
		this.bushmei = bushmei;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.kanji == null) ? 0 : this.kanji.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		KanjiEntry other = (KanjiEntry) obj;
		if (this.kanji == null) {
			if (other.kanji != null) {
				return false;
			}
		} else if (!this.kanji.equals(other.kanji)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(this.kanji).append('\n');
		result.append(this.definition).append('\n');
		result.append(this.yomi).append('\n');
		result.append("------------Misc-------------").append("\n");
		for (KanjiTag tag : KanjiTag.values()) {
			String value = this.getMisc().get(tag);
			result.append(tag.getDescription() + " : " + value).append("\n");
		}
		result.append("------------Misc-------------").append("\n");
		return result.toString();
	}

	@Override
	public String toStringCompact() {
		Map<KanjiTag, String> prop = this.getMisc();
		String heisigNumber = prop.get(KanjiTag.L);
		StringBuilder result = new StringBuilder();
		result.append(this.kanji);
		if (heisigNumber != null) {
			result.append(" [" + heisigNumber + "]");
		}
		result.append('\n');
		result.append(this.definition).append(" [").append(this.yomi).append("]");
		return result.toString();
	}

	/**
	 * @return the miscString
	 */
	public String getMiscString() {
		return this.miscString;
	}

	/**
	 * @param miscString
	 *            the miscString to set
	 */
	public void setMiscString(String miscString) {
		this.miscString = miscString;
		if (!lazyLoad) {
			load();
		}
	}

	private void load() {
		if (this.loaded) {
			return;
		}
		String[] miscProperties = this.miscString.split(" ");
		Map<KanjiTag, String> miscMap = this.misc;
		for (String misc : miscProperties) {
			Matcher matcher = miscPattern.matcher(misc);
			if (!matcher.matches()) {
				continue;
			}
			String tagStr = matcher.group(1);
			KanjiTag tag = null;
			try {
				tag = KanjiTag.valueOf(tagStr);
			} catch (IllegalArgumentException e) {
				if (this.unidentifiedTags.add(tagStr)) {
					System.err.println("Unidentified kanji tag: [" + tagStr + "]");
				}
				continue;
			}
			String value = matcher.group(2);
			String miscMapValue = miscMap.get(tag);
			if (miscMapValue == null) {
				miscMapValue = value;
			} else {
				miscMapValue += " " + value;
			}
			miscMap.put(tag, miscMapValue);
		}
		this.loaded = true;
	}
}

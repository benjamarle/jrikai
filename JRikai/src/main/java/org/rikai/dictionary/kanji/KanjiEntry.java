package org.rikai.dictionary.kanji;

import java.util.Hashtable;
import java.util.Map;

import org.rikai.dictionary.AbstractEntry;

public class KanjiEntry extends AbstractEntry {
	
	private Character kanji;
	
	private String definition;
	
	private Map<KanjiTag, String> misc = new Hashtable<KanjiTag, String>();
	
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
		return kanji;
	}

	/**
	 * @param kanji the kanji to set
	 */
	public void setKanji(Character kanji) {
		this.kanji = kanji;
	}

	/**
	 * @return the definition
	 */
	public String getDefinition() {
		return definition;
	}

	/**
	 * @param definition the definition to set
	 */
	public void setDefinition(String definition) {
		this.definition = definition;
	}

	/**
	 * @return the misc
	 */
	public Map<KanjiTag, String> getMisc() {
		return misc;
	}

	/**
	 * @param misc the misc to set
	 */
	public void setMisc(Map<KanjiTag, String> misc) {
		this.misc = misc;
	}

	/**
	 * @return the yomi
	 */
	public String getYomi() {
		return yomi;
	}

	/**
	 * @param yomi the yomi to set
	 */
	public void setYomi(String yomi) {
		this.yomi = yomi;
	}

	/**
	 * @return the nanori
	 */
	public String getNanori() {
		return nanori;
	}

	/**
	 * @param nanori the nanori to set
	 */
	public void setNanori(String nanori) {
		this.nanori = nanori;
	}
	
	/**
	 * @return the bushmei
	 */
	public String getBushmei() {
		return bushmei;
	}

	/**
	 * @param bushmei the bushmei to set
	 */
	public void setBushmei(String bushmei) {
		this.bushmei = bushmei;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((kanji == null) ? 0 : kanji.hashCode());
		return result;
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KanjiEntry other = (KanjiEntry) obj;
		if (kanji == null) {
			if (other.kanji != null)
				return false;
		} else if (!kanji.equals(other.kanji))
			return false;
		return true;
	}
	
	public String toString(){
		StringBuilder result = new StringBuilder();
		result.append(this.kanji).append('\n');
		result.append(this.definition).append('\n');
		result.append(this.yomi).append('\n');
		result.append("------------Misc-------------").append("\n");
		for (KanjiTag tag : KanjiTag.values()) {
			String value = this.misc.get(tag);
			result.append(tag.getDescription()+" : "+value).append("\n");
		}
		result.append("------------Misc-------------").append("\n");
		return result.toString();
	}
	
	@Override
	public String toStringCompact() {
		StringBuilder result = new StringBuilder();
		result.append(this.kanji).append('\n');
		result.append(this.definition).append(" [").append(this.yomi).append("]\n");
		return result.toString();
	}
	
}

package org.rikai.dictionary.kanji;

public enum KanjiTag {
	B("Radical number"),
	S("Stroke number"),
	G("Grade"),
	F("Frequency"),
	C( "Classical Radical"),
	DR("Father Joseph De Roo Index"),
	DO("P.G. O\"Neill Index"),
	O( "P.G. O\"Neill Japanese Names Index"),
	Q( "Four Corner Code"),
	MN("Morohashi Daikanwajiten Index"),
	MP("Morohashi Daikanwajiten Volume/Page"),
	K("Gakken Kanji Dictionary Index"),
	W("Korean Reading"),
	H("Halpern"),
	L("Heisig"),
	E("Henshall"),
	DK("Kanji Learners Dictionary"),
	N("Nelson"),
	V("New Nelson"),
	Y("PinYin"),
	P("Skip Pattern"),
	IN("Tuttle Kanji &amp; Kana"),
	I("Tuttle Kanji Dictionary"),
	U("Unicode");
	
	private String description;

	private KanjiTag(String description) {
		this.description = description;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	@Override
	public String toString(){
		return getDescription();
	}

}

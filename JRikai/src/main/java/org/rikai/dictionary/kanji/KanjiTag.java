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
	L("Heisig 4th"),
	LL("Heisig 6th"),
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

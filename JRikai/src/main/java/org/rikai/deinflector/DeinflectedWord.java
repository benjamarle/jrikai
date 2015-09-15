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
package org.rikai.deinflector;

public class DeinflectedWord {

	private String originalWord;
	
	private String word;
	
	private int type = 0xFF;
	
	private String reason = "";

	public DeinflectedWord() {

	}

	public DeinflectedWord(String word) {
		this.word = word;
		this.originalWord = word;
	}

	public DeinflectedWord(String originalWord, String word, int type, String reason) {
		this.originalWord = originalWord;
		this.word = word;
		this.type = type;
		this.reason = reason;
	}
	

	/**
	 * @return the originalWord
	 */
	public String getOriginalWord() {
		return originalWord;
	}

	/**
	 * @param originalWord the originalWord to set
	 */
	public void setOriginalWord(String originalWord) {
		this.originalWord = originalWord;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public boolean isOriginal() {
		return this.type == 0xFF;
	}
}
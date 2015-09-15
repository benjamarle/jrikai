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

public class Rule { 
	
	private String from;
	
	private String to;
	
	private int type;
	
	private int reasonIndex;

	public Rule() {

	}

	public Rule(String[] rule) {
		// no error checking
		from = rule[0];
		to = rule[1];
		type = Integer.parseInt(rule[2]);
		reasonIndex = Integer.parseInt(rule[3]);
	}

	public Rule(String from, String to, int type, int reasonIndex) {
		this.from = from;
		this.to = to;
		this.type = type;
		this.reasonIndex = reasonIndex;
	}

	public int getFromLength() {
		return from.length();
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getReasonIndex() {
		return reasonIndex;
	}

	public void setReasonIndex(int reasonIndex) {
		this.reasonIndex = reasonIndex;
	}

}
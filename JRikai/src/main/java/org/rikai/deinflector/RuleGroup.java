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

import java.util.ArrayList;

public class RuleGroup extends ArrayList<Rule> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1112514782317803681L;
	private int fromLength;

	public RuleGroup(int fromLength) {
		super();
		this.fromLength = fromLength;
	}

	@Override
	public boolean add(Rule e) {
		assert e.getFromLength() == this.fromLength;
		return super.add(e);
	}

	public int getFromLength() {
		return this.fromLength;
	}

}
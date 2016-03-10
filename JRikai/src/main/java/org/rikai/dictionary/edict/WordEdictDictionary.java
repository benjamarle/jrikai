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
package org.rikai.dictionary.edict;

import java.util.List;
import java.util.regex.Pattern;

import org.rikai.deinflector.DeinflectedWord;
import org.rikai.deinflector.Deinflector;
import org.rikai.dictionary.db.SqliteDatabase;

public class WordEdictDictionary extends EdictDictionary {

	private Pattern pattern = Pattern.compile("[,\\(\\)]");
	private Deinflector deinflector;

	public WordEdictDictionary(String path, Deinflector deinflector, SqliteDatabase sqliteDatabaseImpl) {
		super(path, sqliteDatabaseImpl);
		this.deinflector = deinflector;
	}

	public WordEdictDictionary(String path, Deinflector deinflector) {
		super(path);
		this.deinflector = deinflector;
	}

	@Override
	protected List<DeinflectedWord> deinflectWord(String word) {
		return this.deinflector.deinflect(word);
	}

	@Override
	protected boolean isValid(DeinflectedWord variant, String definition) {
		if (variant.isOriginal()) {
			return true;
		}
		String[] parts = this.pattern.split(definition);

		for (String currentPart : parts) {

			// Can it that part of speech be inflected?
			int variantType = variant.getType();

			if ((variantType & 1) != 0 && currentPart.equals("v1")) {
				return true;
			}
			if ((variantType & 4) != 0 && currentPart.equals("adj-i")) {
				return true;
			}
			if ((variantType & 2) != 0 && currentPart.startsWith("v5")) {
				return true;
			}
			if ((variantType & 16) != 0 && currentPart.startsWith("vs-")) {
				return true;
			}
			if ((variantType & 8) != 0 && currentPart.equals("vk")) {
				return true;
			}
		}
		return false;
	}

}

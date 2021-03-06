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
package org.rikai.deinflector;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.IOUtils;

public class Deinflector {

	private ArrayList<String> reasons = new ArrayList<String>();
	private ArrayList<RuleGroup> ruleGroups = new ArrayList<RuleGroup>();

	public Deinflector(String path) throws FileNotFoundException, IOException {
		loadDeinflectionData(new FileInputStream(path));
	}

	public Deinflector(InputStream inputStream) throws IOException {
		loadDeinflectionData(inputStream);
	}

	private boolean loadDeinflectionData(InputStream inputStream) throws IOException {
		List<String> difData = IOUtils.readLines(inputStream, "UTF-8");

		if (difData.size() == 0) {
			return false;
		}

		RuleGroup group = null;

		int prevLen = -1;
		// i = 1, skip header
		for (int i = 1; i < difData.size(); i++) {
			String[] f = difData.get(i).split("\t");

			if (f.length == 1) {
				this.reasons.add(f[0]);
			} else if (f.length == 4) {
				Rule rule = new Rule(f);

				int fromLength = rule.getFromLength();
				if (prevLen != fromLength) {
					group = new RuleGroup(fromLength);
					prevLen = fromLength;
					this.ruleGroups.add(group);
				}
				group.add(rule);
			}
		}

		return true;
	}

	/**
	 * returns a list of deinflected word, including the original word
	 *
	 * @param word
	 *            the word to deinflect
	 * @return a list of deinflected word
	 */
	public ArrayList<DeinflectedWord> deinflect(String word) {

		ArrayList<DeinflectedWord> result = new ArrayList<DeinflectedWord>();

		// All the words added to the result list so far.
		// (key, value) where key is the word, and value is the index
		// of the word in the result
		Map<String, Integer> dws = new TreeMap<String, Integer>();

		DeinflectedWord dw = new DeinflectedWord(word);
		result.add(dw); // add the word itself to the result
		dws.put(word, 0);

		// note that result.size() may increase after each iteration
		for (int i = 0; i < result.size(); i++) {
			word = result.get(i).getWord();

			// check the word against each Rule in each RuleGroup
			for (int j = 0; j < this.ruleGroups.size(); j++) {
				RuleGroup group = this.ruleGroups.get(j);

				if (group.getFromLength() > word.length()) {
					/*
					 * if the word length is shorter than the inflection From-Length, no need to check this group, move to the next group (with a
					 * small length)
					 */
					continue;
				}

				/*
				 * get the last part of the word (precisely the last group.flen characters of the word) and check if it's a valid inflection
				 */
				String tail = word.substring(word.length() - group.getFromLength());

				for (int k = 0; k < group.size(); k++) {
					Rule rule = group.get(k);

					if ((result.get(i).getType() & rule.getType()) == 0 || !tail.equals(rule.getFrom())) {
						continue; // failed, go to the next rule
					}

					String newWord = word.substring(0, word.length() - group.getFromLength()) + rule.getTo();

					if (newWord.length() <= 1) {
						continue;
					}

					dw = new DeinflectedWord();
					if (dws.get(newWord) != null) {
						// deinflected word is same as previous ones
						// but under A different rule
						dw = result.get(dws.get(newWord));
						dw.setType(dw.getType() | (rule.getType() >> 8));
						continue;
					}

					dws.put(newWord, result.size());
					dw.setWord(newWord);
					dw.setOriginalWord(word);
					dw.setType(rule.getType() >> 8);
					if (result.get(i).getReason().length() > 0) {
						dw.setReason(this.reasons.get(rule.getReasonIndex()) + " < " + result.get(i).getReason());
					} else {
						dw.setReason(this.reasons.get(rule.getReasonIndex()));
					}

					result.add(dw);
				}
			}
		}

		return result;
	}

}

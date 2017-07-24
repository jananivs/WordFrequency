package com.yale.wordfrequency.Service;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class ConcordanceBuilderService {

	private Locale locale;
	private Map<String, List<Integer>> concordanceMap; // Key = Word, Value = list of sentence numbers where word
														// appears

	public ConcordanceBuilderService(Locale locale) {
		this.locale = locale;
		this.concordanceMap = new TreeMap<String, List<Integer>>();
	}

	public Map<String, List<Integer>> build(String text) {
		//BreakIterator  implements methods for finding the location of boundaries in text. 
		//Instances of BreakIterator maintain a current position 
		//and scan over text returning the index of characters where boundaries occur.
		BreakIterator sentenceIter = BreakIterator.getSentenceInstance(this.locale);
		BreakIterator wordIter = BreakIterator.getWordInstance(this.locale);

		int sentenceCounter = 0;

		int sentenceIdx = 0;
		sentenceIter.setText(text);

		// Go through sentences
		while (BreakIterator.DONE != sentenceIter.next()) {// DONE -1 is returned by previous() and next() after all
															// valid boundaries have been returned.
			int wordId = 0;

			String sentence = text.substring(sentenceIdx, sentenceIter.current());
			wordIter.setText(sentence);
			sentenceCounter++;

			// Go through words
			while (BreakIterator.DONE != wordIter.next()) {
				String word = sentence.substring(wordId, wordIter.current()).toLowerCase();

				if (Character.isLetterOrDigit(word.charAt(0))) {
					// check to sort only letter or digit ,to remove special chars and white space

					if (this.concordanceMap.containsKey(word)) {
						this.concordanceMap.get(word).add(sentenceCounter);
					} else {
						List<Integer> sentenceNumbers = new ArrayList<Integer>();
						sentenceNumbers.add(sentenceCounter);
						this.concordanceMap.put(word, sentenceNumbers);
					}
				}
				wordId = wordIter.current();
			}
			sentenceIdx = sentenceIter.current();
		}

		return concordanceMap;
	}
}

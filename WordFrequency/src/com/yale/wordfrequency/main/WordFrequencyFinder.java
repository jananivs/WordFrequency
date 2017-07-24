package com.yale.wordfrequency.main;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.yale.wordfrequency.Service.ConcordanceBuilderService;

public class WordFrequencyFinder {

	public static void main(String[] args) {
		String fileName = (args.length == 0 ? "src/com/yale/wordfrequency/test/input.txt" : args[0]);
		Charset inputEncoding = StandardCharsets.UTF_8;

		try {
			String text = new String(Files.readAllBytes(Paths.get(fileName)), inputEncoding);

			if (null != text && !text.trim().isEmpty()) {
				ConcordanceBuilderService concordanceService = new ConcordanceBuilderService(Locale.US);
				// Build concordance Map
				Map<String, List<Integer>> concordanceMap = concordanceService.build(text);
				// Print Result
				System.out.println("********Printing the Concordance********");
				for (String word : concordanceMap.keySet()) {
					List<Integer> sentenceNumbers = concordanceMap.get(word);
					String sentNums = sentenceNumbers.toString();

					System.out.println(word + " {" + sentenceNumbers.size() + ":"
							+ sentNums.substring(1, sentNums.length() - 1) + "}");
				}
			} else {
				throw new IllegalArgumentException();
			}
		} catch (IOException e) {
			//Executes when file is not available in given location
			System.out.println("Error in file loading" + fileName + e.toString());
		} catch (IllegalArgumentException e) {
			//Executes when the file is empty
			System.out.println("Text file '" + fileName + "' is empty!");
		}
	}
}

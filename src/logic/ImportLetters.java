package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ImportLetters {
	private static BufferedReader br;

	public ArrayList<String> importDictionary(String path) throws IOException {
		String line;
		ArrayList<String> dictionary = new ArrayList<String>();
		if (path == null) {
			path = "/resources/letters_kanji";
		} else {
			// System.out.println(path);
		}
		br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(path), "UTF-8"));
		while ((line = br.readLine()) != null) {
			dictionary.add(line.trim());
		}
		return dictionary;
	}

}

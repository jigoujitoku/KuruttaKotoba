package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import gui.Board;

public class JishoGetter {

	static List<String> meaningList = new ArrayList<String>();
	static List<String> wordList = new ArrayList<String>();
	public static Map<String, String> wordMap = new LinkedHashMap<String, String>();
	public static Map<String, String> map = new LinkedHashMap<String, String>();
	public static String word;

	public static String jishoOutput() throws IOException {
		// Connect to the URL using java's native library
		word = MouseListenerCD.getWord().toLowerCase();
		String sURL = "http://jisho.org/api/v1/search/words?keyword=" + URLEncoder.encode(word, "UTF-8");
		URL url = new URL(sURL);
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.connect();

		BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			return inputLine;
		}
		return inputLine;
	}

	public static String parseMeaning(String input) {
		input = input
				.substring(input.indexOf("{\"english_definitions\":", input.indexOf("\"],\"parts_of_speech\":\"")));
		input = input.substring(input.indexOf("\":[\""), input.indexOf("\"],\""));
		input = input.replaceAll("\"", "");
		input = input.replaceAll(":\\[", "");
		return input;
	}

	public static String parseReading(String input) {
		input = input.substring(input.indexOf("\",\"reading\":\"", input.indexOf("\"},{\"word:")));
		input = input.substring(input.indexOf("\":\""), input.indexOf("\"}"));
		input = input.replaceAll(":", "");
		input = input.replaceAll("\"", "");
		return input;
	}

	public static String parseJapanese(String input) {
		input = input.substring(input.indexOf(",\"japanese\":[{\"word\":\"", input.indexOf("reading")));
		input = input.substring(input.indexOf("\":\""), input.indexOf("\"}"));
		return input;
	}

	public static void testJson(String word) throws IOException {
		String sURL = "http://jisho.org/api/v1/search/words?keyword=" + URLEncoder.encode(word, "UTF-8");
		// String sURL = "http://jisho.org/api/v1/search/words?keyword=会館";
		URL url = new URL(sURL);
		System.setProperty("http.agent", "Chrome");
		String key = new String();
		String value = new String();

		try (InputStream is = url.openStream(); JsonParser parser = Json.createParser(is)) {
			Random random = new Random();
			int r = random.nextInt(1000);
			while (parser.hasNext()) {
				Event e = parser.next();
				if (e == Event.KEY_NAME) {
					key = parser.getString();
				}
				if (e == Event.VALUE_STRING) {
					value = parser.getString();
				}
				switch (Board.lang) {
				case 0:
					if (key.equals("word") && !value.isEmpty() && value.contains(SaveLetters.saveWord())) {
						wordMap.put(key + "_" + r, value);
						break;
					}
				case 1:
					if (key.equals("word") && !value.isEmpty() && !value.contains(SaveLetters.saveWord())
							&& !value.contains("[^\\x00-\\x7F]") && !value.contains("wanikani")
							&& !value.contains("http")) {
						wordMap.putIfAbsent(key + "_" + r, value);
					}
					if (key.equals("reading") && !value.isEmpty()) {
						if (e == Event.VALUE_STRING) {
							value = parser.getString();
							wordMap.putIfAbsent(key + "_" + r, value);
						}
					}
					for (Map.Entry<String, String> entry : wordMap.entrySet()) {
						// System.out.println(entry.getKey() + " - " +
						// entry.getValue());
						if (entry.getKey().contains("word"))
							Board.japanskaRijec.setText(entry.getValue());
						if (entry.getKey().contains("reading"))
							Board.citanje.setText(entry.getValue());
					}
					break;
				case 2:
					if (key.equals("word") && !value.isEmpty() && !value.contains("wanikani") && !value.contains("http")
							&& !value.contains("[^\\x00-\\x7F]")) {
						wordMap.putIfAbsent(key + "_" + r, value);
						break;
					}
					for (Map.Entry<String, String> entry : wordMap.entrySet()) {
						if (entry.getKey().contains("word"))
							Board.japanskaRijec.setText(entry.getValue());
					}
				}
			}
		}
		Board.grid.requestFocusInWindow();
	}

	public static List<String> jishoMeaning(String meaning) throws IOException {
		if (!meaningList.contains(meaning))
			meaningList.add(meaning);
		return meaningList;
	}

	public static List<String> jishoWords() throws IOException {
		if (!wordList.contains(SaveLetters.saveWord()) && meaningList.size() - 1 == wordList.size())
			wordList.add(SaveLetters.saveWord());
		return wordList;
	}

}
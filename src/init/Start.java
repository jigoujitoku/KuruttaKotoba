package init;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.swing.UIManager;

import gui.Board;
import logic.ImportLetters;
import logic.JishoGetter;
import logic.MouseListenerCD;
import logic.SaveLetters;
import models.Map;

public class Start {

	public static ArrayList<String> checkDic;

	public static void main(String args[]) throws IOException, IllegalArgumentException, IllegalAccessException,
			NoSuchFieldException, SecurityException {

		System.setProperty("file.encoding", "UTF-8");
		Field charset = Charset.class.getDeclaredField("defaultCharset");
		charset.setAccessible(true);
		charset.set(null, null);

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
		} catch (Exception ignored) {
		}
		ArrayList<String> dictionary = null;
		ImportLetters imp = new ImportLetters();
		dictionary = imp.importDictionary("/resources/letters_cro");

		checkDic = imp.importDictionary("/resources/dictionary_eng.txt");

		Map map = new Map(4, dictionary);
		Board board = new Board(map);

	}

	public static void restart(String dicName, String checkDicName) throws IOException {
		Board.closeWindow();
		MouseListenerCD.chosenSlova.clear();
		SaveLetters.guessedWords.clear();
		JishoGetter.wordMap.clear();
		Board.check = null;
		Board.prevWords = null;
		Board.pw = "";
		Board.engleskaRijec.setText("");
		Board.japanskaRijec.setText("");
		Board.citanje.setText("");
		ArrayList<String> dictionary = null;
		ImportLetters imp = new ImportLetters();
		dictionary = imp.importDictionary(dicName);
		checkDic = imp.importDictionary(checkDicName);

		Map map = new Map(4, dictionary);
		Board board = new Board(map);
	}
}

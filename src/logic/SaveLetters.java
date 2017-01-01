package logic;

import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;

import gui.Board;
import gui.CountdownPanel;
import init.Start;

public class SaveLetters {

	public static String string;
	public static ArrayList<String> checkDictionary;
	public static boolean erase = false;
	public static ArrayList<String> guessedWords = new ArrayList<String>();

	public static String saveWord() {
		string = MouseListenerCD.getWord();
		return string;
	}

	public static boolean checkWord() {

		for (String e : Start.checkDic) {
			if (e.equals(saveWord()) && saveWord() != "") {
				if (!guessedWords.contains(e))
					guessedWords.add(e);
				return true;
			}
		}
		return false;
	}

	public static void submitWord() {
		if (MouseListenerCD.getWord() != "" && CountdownPanel.timer.isRunning()) {
			switch (Board.lang) {
			case 0: {
				Board.japanskaRijec.setFont(new Font("MS Gothic", 0, 16));
				Board.engleskaRijec.setFont(new Font("MS Gothic", 0, 16));
				try {
					// String text = MouseListenerCD.getWord() + "\n("
					// + JishoGetter.parseReading(JishoGetter.jishoOutput()) +
					// ")";
					Board.japanskaRijec.setText(MouseListenerCD.getWord());
					Board.citanje.setText(JishoGetter.parseReading(JishoGetter.jishoOutput()));
				} catch (Exception e1) {
					Board.engleskaRijec.setFont(new Font("MS Gothic", 0, 16));
					Board.engleskaRijec.setText(MouseListenerCD.getWord() + " nije u rječniku.");
					Board.japanskaRijec.setText("");
					Board.citanje.setText("");
				}
				try {
					Board.engleskaRijec.setText(JishoGetter
							.jishoMeaning(JishoGetter.parseMeaning(JishoGetter.jishoOutput())).get(Board.counter));
					for (int i = Board.counter; i < JishoGetter.jishoWords().size(); i++) {
						Board.pw += (JishoGetter.jishoWords().get(i) + " ");
						Board.prevWords.setText("Prethodne riječi: " + Board.pw + "\t");
					}
					Board.counter++;
				} catch (Exception e1) {
					Board.engleskaRijec.setText(MouseListenerCD.getWord() + " nije u rječniku.");
					Board.japanskaRijec.setText("");
					Board.citanje.setText("");
				}
				SaveLetters.checkWord();

				break;
			}
			case 1:
				Board.engleskaRijec.setFont(new Font("MS Gothic", 0, 16));
				if (checkWord()) {
					String text = MouseListenerCD.getWord().toLowerCase();
					Board.engleskaRijec.setText(text);
					try {
						JishoGetter.parseJapanese(JishoGetter.jishoOutput());
					} catch (IOException e) {
						Board.engleskaRijec.setText(MouseListenerCD.getWord() + " nije u rječniku.");
						Board.japanskaRijec.setText("");
						Board.citanje.setText("");
					}
					try {
						Board.engleskaRijec.setText(JishoGetter
								.jishoMeaning(JishoGetter.parseMeaning(JishoGetter.jishoOutput())).get(Board.counter));
						for (int i = Board.counter; i < JishoGetter.jishoWords().size(); i++) {
							Board.pw += (JishoGetter.jishoWords().get(i) + " ");
							Board.prevWords.setText("Prethodne riječi: " + Board.pw + "\t");
						}
						Board.counter++;
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				} else
					Board.check.setText("           Nema u engleskom rječniku.");
			case 2: {
				Board.japanskaRijec.setFont(new Font("MS Gothic", 0, 16));
				try {
					Board.citanje.setText(JishoGetter.parseReading(JishoGetter.jishoOutput()));
				} catch (Exception e1) {
					Board.engleskaRijec.setFont(new Font("MS Gothic", 0, 16));
					Board.engleskaRijec.setText(MouseListenerCD.getWord() + " nije u rječniku.");
					Board.japanskaRijec.setText("");
					Board.citanje.setText("");
				}
				try {
					Board.engleskaRijec.setText(JishoGetter
							.jishoMeaning(JishoGetter.parseMeaning(JishoGetter.jishoOutput())).get(Board.counter));
					for (int i = Board.counter; i < JishoGetter.jishoWords().size(); i++) {
						Board.pw += (JishoGetter.jishoWords().get(i) + " ");
						Board.prevWords.setText("Prethodne riječi: " + Board.pw + "\t");
					}
					Board.counter++;
				} catch (Exception e1) {
					Board.engleskaRijec.setText(MouseListenerCD.getWord() + " nije u rječniku.");
					Board.japanskaRijec.setText("");
					Board.citanje.setText("");
				}
				break;
			}
			}
		} else
			Board.check.setText("Nemoguće predati.");
		Board.reset.doClick();
	}
}
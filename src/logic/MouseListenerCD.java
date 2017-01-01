package logic;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import gui.Board;
import models.Map;

public class MouseListenerCD implements ActionListener {

	JButton label;
	JPanel panel;
	Map map;
	int counter = 0;
	int row;
	int column;
	public static ArrayList<String> chosenSlova = new ArrayList<String>();
	static String string;
	public static boolean erase;

	public MouseListenerCD(JButton label, JPanel panel, Map map, int row, int column) {
		this.label = label;
		this.panel = panel;
		this.map = map;
		this.row = row;
		this.column = column;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		counter = map.fieldFind(row, column).getCounter();
		switch (counter) {
		case 0:
			// label.setBackground(new Color(102, 255, 102));
			panel.setBackground(new Color(102, 255, 102));
			chosenSlova.add(label.getText());
			SaveLetters.saveWord();
			map.fieldFind(row, column).setCounter(1);
			break;
		case 1:
			panel.setBackground(Color.white);
			map.fieldFind(row, column).setCounter(0);
			chosenSlova.remove(label.getText());
			break;
		}

		Board.check.setText("               Unos: " + (getWord()));

	}

	public static String getWord() {
		String word = "";
		for (String s : chosenSlova) {
			word += s;
		}
		word.toLowerCase();
		return word;
	}

}

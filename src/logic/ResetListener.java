package logic;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import gui.Board;
import models.Map;

public class ResetListener implements ActionListener {

	JButton label;
	JPanel[][] panel;
	Map map;
	int counter = 0;
	public static ArrayList<String> checkDictionary;
	public static boolean erase = false;

	public ResetListener(JButton label, JPanel panel[][], Map map) {
		this.label = label;
		this.panel = panel;
		this.map = map;
	}

	public void actionPerformed(ActionEvent e) {

		if (erase == true) {
			MouseListenerCD.chosenSlova.clear();
			if (!Board.submit.isEnabled())
				Board.check.setText("Riječ " + SaveLetters.string + " je izbrisana.");
			erase = false;
		}
		for (int i = 0; i < Board.fn; i++) {
			for (int j = 0; j < Board.fn; j++) {
				panel[i][j].setBackground(Color.white);
				map.fieldFind(i, j).setCounter(0);
			}
		}
	}

}

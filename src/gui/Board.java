package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import init.Start;
import logic.JishoGetter;
import logic.MnemonicWorkaround;
import logic.MouseListenerCD;
import logic.ResetListener;
import logic.SaveLetters;
import models.Map;

public class Board {

	static JFrame frame;
	public static JPanel grid;
	JPanel squares[][];
	public JButton label;
	public static JButton reset;
	public static JButton submit;
	CountdownPanel countdownPanel;
	public static ArrayList<String> chosenSlova;
	public static JLabel check;
	public static JTextArea engleskaRijec;
	public static JTextArea japanskaRijec;
	public static JTextArea prevWords;
	public static JTextArea citanje;
	public static int fn;
	public static String dicName;
	public static String checkDicName;
	public static String pw = new String();
	public static int counter = 0;
	public static int lang = 0;

	public Board(Map map) throws IOException {
		int fieldNum = map.getFieldNum();
		fn = fieldNum;
		frame = new JFrame("狂った言葉　|| くるったことば");
		URL url = this.getClass().getResource("/resources/icon.png");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(url));
		frame.setSize(fieldNum * 200, fieldNum * 100);
		countdownPanel = new CountdownPanel();
		grid = new JPanel();

		squares = new JPanel[fieldNum][fieldNum];
		ArrayList<String> slova = new ArrayList<String>();
		ArrayList<Number> size = new ArrayList<Number>();

		grid.setLayout(new GridLayout(fieldNum, fieldNum));
		for (int i = 0; i < fieldNum; i++) {
			for (int j = 0; j < fieldNum; j++) {

				label = new JButton(map.fieldFind(i, j).getRandomLetter());
				slova.add(label.getText());
				label.setFont(new Font("MS Gothic", 0, 50));
				label.setSize(10, 10);
				squares[i][j] = new JPanel();
				squares[i][j].setBackground(Color.white);
				squares[i][j].setBorder(BorderFactory.createLineBorder(Color.black, 2));
				squares[i][j].add(label);
				label.addActionListener(new MouseListenerCD(label, squares[i][j], map, i, j));
				label.setForeground(null);
				label.setBackground(null);
				label.setBorder(null);
				grid.add(squares[i][j]);
				size.add(slova.size());
				label.setFocusable(false);
			}
		}
		JMenuBar menubar = new JMenuBar();
		reset = new JButton("Deselect (Alt+R)");
		submit = new JButton("Predaj riječ (Alt+ S)");
		submit.setFont(new Font("Arial", 0, 12));
		reset.setFont(new Font("Arial", 0, 12));
		check = new JLabel();
		reset.addActionListener(new ResetListener(label, squares, map));
		reset.addActionListener(e -> ResetListener.erase = true);
		submit.addActionListener(e -> {
			if (SaveLetters.saveWord() != "") {
				try {
					JishoGetter.testJson(SaveLetters.saveWord());
				} catch (Exception e1) {
				}
				SaveLetters.submitWord();
				frame.requestFocusInWindow();
			}
		});

		submit.setMnemonic('S');
		reset.setMnemonic('R');

		JMenu jezik = new JMenu("Restart / Promjena jezika");
		JMenu help = new JMenu("Pomoć");
		jezik.setFont(new Font("Arial", 0, 12));
		help.setFont(new Font("Arial", 0, 12));
		JMenuItem h1 = new JMenuItem("Uputa");
		JMenuItem h2 = new JMenuItem("O aplikaciji");
		help.add(h1);
		help.add(h2);
		h1.addActionListener(l -> {
			JOptionPane.showMessageDialog(frame,
					"1. Pokrenite mjerač vremena stisnuvši razmak. Igra traje tri minute i može se pauzirati klikom na mjerač vremena.\n"
							+ "2. Odaberite slova ili kanjije klikom (moraju biti zeleni) i složite što više riječi.\n"
							+ "Moguće je otkazati izbor ponovnim klikom ili otkazati sve pritiskom Alt+D odnosno odgovarajućeg gumba.\n"
							+ "Možete predati i samo jedan kanji, no pokušajte složiti i riječi.\n"
							+ "3. Prihvaćene riječi se ispisuju iznad mjerača vremena. Pokušajte ih naći što više.\n"
							+ "4. Odaberete li nešto iz izbornika Jezik, započet ćete novu igru. \nTakođer možete stisnuti F5 da osvježite"
							+ "izbor slova/kanjija. Prethodno predane riječi se ne pamte.\n"
							+ "5. Ukoliko nešto ne radi kako treba, provjerite internet vezu.",
					"Uputa", JOptionPane.INFORMATION_MESSAGE);

		});
		h2.addActionListener(l -> {
			JOptionPane.showMessageDialog(frame,
					"Ideja ove igre je razviti intuiciji o slaganju riječi preko kanjija, proširiti vokabular i pobuditi znatiželju u učenju.\nBesplatna je za korištenje, "
							+ "ali svakako želimo čuti vašu povratnu informaciju. \nKomentare i pitanja šaljite na memazija@gmail.com.\n"
							+ "Ova igra koristi API rječnika Jisho i nije odgovorna za greške u njegovom sadržaju.\nMade in 2016.",
					"Kurutta Kotoba by Memazija", JOptionPane.INFORMATION_MESSAGE);

		});

		JMenuItem kanji = new JMenuItem("Kanji");
		JMenuItem hira = new JMenuItem("Hiragana");
		JMenuItem eng = new JMenuItem("Engleski");
		jezik.add(hira);
		jezik.add(kanji);
		jezik.add(eng);
		kanji.setFont(new Font("Arial", 0, 12));
		hira.setFont(new Font("Arial", 0, 12));
		eng.setFont(new Font("Arial", 0, 12));
		kanji.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, InputEvent.CTRL_MASK));
		hira.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
		eng.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		eng.addActionListener(e -> {
			lang = 1;
			dicName = "/resources/letters_eng";
			checkDicName = "/resources/dictionary_eng.txt";
			try {
				Start.restart(dicName, checkDicName);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		kanji.addActionListener(e -> {
			lang = 0;
			dicName = "/resources/letters_kanji";
			checkDicName = "/resources/dictionary_jap.txt";
			try {
				Start.restart(dicName, checkDicName);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		hira.addActionListener(e -> {
			lang = 2;
			dicName = "/resources/letters_hira";
			checkDicName = "/resources/dictionary_jap.txt";
			try {
				Start.restart(dicName, checkDicName);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		frame.setJMenuBar(menubar);
		menubar.setLayout(new FlowLayout(100));
		menubar.add(submit);
		menubar.add(reset);
		menubar.add(jezik);
		menubar.add(help);
		menubar.add(check);
		check.setFont(new Font("Ms Gothic", 0, 14));
		// check.setHorizontalAlignment(JLabel.RIGHT);

		frame.setLayout(new GridLayout());
		frame.add(grid);

		// desna strana
		JPanel rjecnikSpace = new JPanel();
		rjecnikSpace.setLayout(new BoxLayout(rjecnikSpace, BoxLayout.PAGE_AXIS));
		japanskaRijec = new JTextArea("", 2, 5);
		japanskaRijec.setLineWrap(true);
		japanskaRijec.setBackground(null);
		japanskaRijec.setEditable(false);
		japanskaRijec.setFont(new Font("MS Gothic", 0, 16));
		citanje = new JTextArea("", 2, 5);
		citanje.setLineWrap(true);
		citanje.setBackground(null);
		citanje.setEditable(false);
		citanje.setFont(new Font("MS Mincho", 0, 16));
		engleskaRijec = new JTextArea("", 3, 25);
		engleskaRijec.setLineWrap(true);
		engleskaRijec.setBackground(null);
		engleskaRijec.setFont(new Font("Arial", 0, 14));
		engleskaRijec.setEditable(false);
		rjecnikSpace.add(japanskaRijec);
		rjecnikSpace.add(citanje);
		rjecnikSpace.add(engleskaRijec);
		rjecnikSpace.add(countdownPanel);
		engleskaRijec.setText("U izborniku odaberite jezik ili \nstisnite razmak da počnete s kanjijima.");
		frame.add(rjecnikSpace);

		JPanel timer = new JPanel();
		prevWords = new JTextArea("", 5, 45);
		prevWords.setBackground(null);
		prevWords.setLineWrap(true);
		prevWords.setEditable(false);
		prevWords.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CountdownPanel.timer.start();
				Board.grid.setVisible(true);
			}
		});

		timer.setLayout(new FlowLayout());
		timer.add(rjecnikSpace);
		timer.add(prevWords);
		timer.add(countdownPanel);
		if (!CountdownPanel.timer.isRunning())
			grid.setVisible(false);

		frame.add(timer);
		frame.setResizable(false);
		frame.setLocation(100, 100);
		frame.setVisible(true);
		frame.setFocusable(true);
		frame.addKeyListener(new MnemonicWorkaround());
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public static void closeWindow() {
		frame.setVisible(false);
	}

}

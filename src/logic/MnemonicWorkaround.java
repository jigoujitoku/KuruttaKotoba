package logic;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import gui.Board;
import gui.CountdownPanel;
import init.Start;

public class MnemonicWorkaround implements KeyListener {

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			Board.grid.setVisible(true);
			CountdownPanel.timer.start();
			CountdownPanel.paused = true;
			Board.prevWords.setText("Za pauzu klikni na timer, za ponovni početak\nstisni razmak ili klikni tu.");
		}
		if (e.getKeyCode() == KeyEvent.VK_PAUSE) {
			CountdownPanel.timer.stop();
			Board.grid.setVisible(false);
		}
		if (e.getKeyCode() == KeyEvent.VK_R) {
			System.out.println("pressed");
		}
		if (e.getKeyCode() == KeyEvent.VK_F5) {
			try {
				Start.restart(null, "/resources/dictionary_jap.txt");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
}
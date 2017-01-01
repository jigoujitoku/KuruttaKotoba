package gui;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class CountdownPanel extends JPanel {
	public static Timer timer;
	private long startTime = -1;
	private long restarTime;
	private long duration = 180000;
	private long clockTime;
	private SimpleDateFormat df;
	private long stopTime;
	private JLabel label;
	private long razlika;
	public static boolean paused;

	public CountdownPanel() {
		setLayout(new GridBagLayout());
		timer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				long now = System.currentTimeMillis();
				if (startTime < 00) {
					startTime = System.currentTimeMillis();
				}
				clockTime = now - startTime;
				if (clockTime >= duration) {
					clockTime = duration;
					timer.stop();
				}
				df = new SimpleDateFormat("mm:ss:SSS");
				label.setFont(new Font("Arial", 0, 36));
				label.setSize(200, 200);
				restarTime = duration - clockTime;
				razlika = stopTime - restarTime;
				if (paused && razlika > 0) {
					duration += razlika;
				}
				label.setText(df.format(duration - clockTime));
				paused = false;
			}
		});

		timer.setInitialDelay(0);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				timer.stop();
				paused = true;
				stopTime = duration - clockTime;
				Board.grid.setVisible(false);
			}
		});

		label = new JLabel("Stisni razmak za početak, klikni na timer za pauzu. F5 = nova igra");
		add(label);
	}

	// @Override
	// public Dimension getPreferredSize() {
	// return new Dimension(100, 50);
	// }

}

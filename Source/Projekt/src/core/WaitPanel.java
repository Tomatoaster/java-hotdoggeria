package core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JLabel;
import javax.swing.JPanel;

import collection.Order;

public class WaitPanel extends JPanel {
	
	private Order[] orders;
	private Thread thr;
	private Thread endThread;
	private Clip clip;
	private int ind;
	private ScorePanel scorePanel;
	
	public WaitPanel(ScorePanel scorePanel) {
		this.setBackground(Color.BLUE);
		this.scorePanel = scorePanel;
		
		JPanel hintsPanel = new JPanel(new GridLayout(6, 1));
		
		JLabel hintLabel = new JLabel("Hints:");
		JLabel orderHint = new JLabel("You will receive a total of 6 orders, a bell will ring when a new order arrives");
		JLabel grillHint = new JLabel("You can only take a sausage off the grill once an empty bun is ready in the Build Station");
		JLabel popHint = new JLabel("You can only add snacks and drinks once the hot dog is ready and in the Pop Station");
		JLabel trashHint = new JLabel("If you made a mistake, you can send your hot dog all the way to the Pop Station and discard it");
		JLabel rateHint = new JLabel("The rating of your hot dog is based on: Correct ingredients, Grill Level, Wait Time");
		
		hintsPanel.setBackground(Color.BLUE);
		
		hintLabel.setHorizontalAlignment(JLabel.CENTER);
		hintLabel.setForeground(Color.WHITE);
		orderHint.setHorizontalAlignment(JLabel.CENTER);
		orderHint.setForeground(Color.WHITE);
		grillHint.setHorizontalAlignment(JLabel.CENTER);
		grillHint.setForeground(Color.WHITE);
		popHint.setHorizontalAlignment(JLabel.CENTER);
		popHint.setForeground(Color.WHITE);
		rateHint.setHorizontalAlignment(JLabel.CENTER);
		rateHint.setForeground(Color.WHITE);
		trashHint.setHorizontalAlignment(JLabel.CENTER);
		trashHint.setForeground(Color.WHITE);
		
		
		hintsPanel.setPreferredSize(new Dimension(750, 120));
		hintsPanel.add(hintLabel);
		hintsPanel.add(orderHint);
		hintsPanel.add(grillHint);
		hintsPanel.add(popHint);
		hintsPanel.add(rateHint);
		hintsPanel.add(trashHint);
		add(hintsPanel);
		
		orders = new Order[6];
		ind = 0;
		
		
		
		thr = new Thread() {
			public void run() {
				
				try {
					Thread.sleep(11100);
				} catch (InterruptedException e) {
					return;
				}
				
				for (int i = 0; i < 6; i++) {
					orders[i] = new Order();
					
					try {
						AudioInputStream audio = AudioSystem.getAudioInputStream(this.getClass().getResource("/audio/Doorbell.wav"));
						clip = AudioSystem.getClip();
						clip.open(audio);
						clip.start();
					} catch(UnsupportedAudioFileException | LineUnavailableException | IOException e) {
						System.out.println("Error: Couldn't load audio resource!");
					}
					
					WaitPanel.this.add(orders[i]);
					WaitPanel.this.revalidate();
					WaitPanel.this.repaint();
					
					try {
						Thread.sleep(50000);
					} catch (InterruptedException e) {
						return;
					}
				}
			}
		};
		thr.start();
		
		endThread = new Thread() {
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// e.printStackTrace();
				}
				int totalScore = 
						Arrays
						.stream(orders)
						.mapToInt(Order::getFinalScore)
						.sum();
				WaitPanel.this.scorePanel.setActScore(totalScore);
				WaitPanel.this.scorePanel.displayScore();
				WaitPanel.this.interruptThreads();
			}
		};
	}
	
	public Order getNextOrder() {
		ind++;
		if (ind == 6) {
			endThread.start();
		}
		return orders[ind - 1];
	}
	
	public boolean hasNextOrder() {
		return orders[ind] != null;
	}
	
	public void interruptThreads() {
		thr.interrupt();
	}
}

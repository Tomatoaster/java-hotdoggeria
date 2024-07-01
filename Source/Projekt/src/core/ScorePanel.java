package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel{

	private int actScore;
	private GameFrame gameFrame;
	
	public ScorePanel(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		
		setLayout(new GridLayout(5, 1));
		JLabel scoreText = new JLabel("YOUR SCORE IS:");
		scoreText.setHorizontalAlignment(JLabel.CENTER);
		scoreText.setFont(new Font("Arial", Font.BOLD, 40));
		add(scoreText);
	}
	
	public void setActScore(int actScore) {
		this.actScore = actScore;
	}
	
	public void displayScore() {
		
		JLabel scoreValue = new JLabel(String.valueOf(actScore));
		scoreValue.setHorizontalAlignment(JLabel.CENTER);
		scoreValue.setFont(new Font("Arial", Font.BOLD, 40));
		add(scoreValue);
		
		boolean isHighScore = false;
		try {
			FileReader fr = new FileReader(new File("scores.txt"));
			char[] score = new char[128];
			fr.read(score);
			
			int ind = 0;
			while (Character.isDigit(score[ind])) {
				ind++;
			}
			int oldScore = Integer.parseInt(new String(score, 0, ind));
			// System.out.println(oldScore);
			if (actScore > oldScore) {
				isHighScore = true;
			}
			fr.close();
		} catch (IOException e) {
			System.out.println("Cant find SCORE file!");
			isHighScore = true;
		}
		
		if (isHighScore) {
			JLabel recordLabel = new JLabel("New Highscore!");
			recordLabel.setHorizontalAlignment(JLabel.CENTER);
			recordLabel.setFont(new Font("Arial", Font.BOLD, 30));
			recordLabel.setForeground(Color.GREEN);
			add(recordLabel);
			
			try {
				File targetFile = new File("scores.txt");
				if (!targetFile.exists()) {
					targetFile.createNewFile();
				}
				FileWriter fw = new FileWriter("scores.txt");
				fw.write(String.valueOf(actScore));
				fw.close();
			} catch (IOException e) {
				// return;
			}
			
		}
		else {
			add(new JPanel());
		}
		
		JPanel buttonPanel = new JPanel();
		JButton newButton = new JButton("Play again");
		newButton.setHorizontalAlignment(JButton.CENTER);
		newButton.setHorizontalTextPosition(JButton.CENTER);
		newButton.setBackground(Color.RED);
		newButton.setFont(new Font("Arial", Font.BOLD, 40));
		buttonPanel.add(newButton);
		
		ActionListener againListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				gameFrame.stopMusic();
				gameFrame.dispose();
				new GameFrame();
				// gameFrame.dispatchEvent(new WindowEvent(gameFrame, WindowEvent.WINDOW_CLOSING));
			}
		};
		newButton.addActionListener(againListener);
		
		add(buttonPanel);
		
		
		
		
		gameFrame.showEndScreen();
	}
	
}

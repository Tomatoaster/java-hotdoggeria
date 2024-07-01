package core;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameFrame extends JFrame {
	
	private JPanel gamePanel;
	private JPanel buttonPanel;
	private WaitPanel waitPanel;
	private GrillPanel grillPanel;
	private BuildPanel buildPanel;
	private PopPanel popPanel;
	private ScorePanel scorePanel;
	private CardLayout layout;
	private Clip clip;
	
	public GameFrame() {
		this.setBounds(200, 200, 800, 800);
		this.setMinimumSize(new Dimension(300, 300));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Papa\'s Hot Doggeria");
		this.setResizable(false);
		
		// gamePanel
		gamePanel = new JPanel();
		gamePanel.setBackground(Color.RED);
		layout = new CardLayout();
		gamePanel.setLayout(layout);
		
		// buttonPanel
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.setBackground(Color.BLACK);
			
		// Buttons
		JButton gb = new JButton("Build Station");
		JButton rb = new JButton("Grill Station");
		JButton bb = new JButton("Lobby");
		JButton pb = new JButton("Pop Station");
		buttonPanel.add(bb);
		bb.setBackground(Color.BLUE);
		bb.setForeground(Color.WHITE);
		buttonPanel.add(rb);
		rb.setBackground(new Color(255, 102, 0));
		buttonPanel.add(gb);
		gb.setBackground(Color.GREEN);
		buttonPanel.add(pb);
		pb.setBackground(new Color(100, 0, 200));
		pb.setForeground(Color.WHITE);
		
		// button listener
		ActionListener buttonListener = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (ae.getSource() == bb) {
					layout.show(gamePanel, "Lobby");
				}
				else if (ae.getSource() == rb) {
					layout.show(gamePanel, "Grill Station");
				}
				else if (ae.getSource() == gb) {
					layout.show(gamePanel, "Build Station");
				}
				else if (ae.getSource() == pb) {
					layout.show(gamePanel, "Pop Station");
				}
			}
		};
		bb.addActionListener(buttonListener);
		rb.addActionListener(buttonListener);
		pb.addActionListener(buttonListener);
		gb.addActionListener(buttonListener);
		
		// Panels
		scorePanel = new ScorePanel(this);
		waitPanel = new WaitPanel(scorePanel);
		popPanel = new PopPanel(waitPanel);
		buildPanel = new BuildPanel(popPanel);
		grillPanel = new GrillPanel(buildPanel); 
		gamePanel.add(waitPanel, "Lobby");
		gamePanel.add(grillPanel, "Grill Station");
		gamePanel.add(buildPanel, "Build Station");
		gamePanel.add(popPanel, "Pop Station");
		gamePanel.add(scorePanel, "End Screen");
		
		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(this.getClass().getResource("/audio/instrumental.wav"));
			clip = AudioSystem.getClip();
			
			clip.open(audio);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		} catch(UnsupportedAudioFileException | LineUnavailableException | IOException e) {
			System.out.println("Error: Couldn't load audio resource!");
		}
		
		
		
		this.add(gamePanel);
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.setVisible(true);
	}
	
	public void showEndScreen() {
		layout.show(gamePanel, "End Screen");
		this.remove(buttonPanel);
		this.revalidate();
		this.repaint();
	}
	
	public void stopMusic() {
		clip.stop();
	}
	
	public static void main(String[] args) {
		new GameFrame();
	}
}

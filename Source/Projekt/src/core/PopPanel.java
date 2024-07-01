package core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import ingredients.HotDog;
import ingredients.HotDogPanel;
import ingredients.drinks.Fizzo;

public class PopPanel extends JPanel {
	private HotDogPanel hotDogPanel;
	private boolean emptySlot;
	private boolean hasSnack;
	private boolean hasDrink;
	private WaitPanel waitPanel;
	Color bgColor;
	
	public PopPanel(WaitPanel waitPanel) {
		bgColor = new Color(100, 0, 200);
		this.setBackground(bgColor);
		BorderLayout popLayout = new BorderLayout();
		popLayout.setHgap(50);
		this.setLayout(popLayout);
		
		emptySlot = true;
		
		
		
		
		FlowLayout extrasLayout = new FlowLayout();
		extrasLayout.setHgap(50);
		JPanel extrasPanel = new JPanel(extrasLayout);
		extrasPanel.setBackground(bgColor);
		
		JPanel drinksPanel = new JPanel(new GridLayout(2, 2));
		JButton[] drinkButtons = new JButton[4];
		drinkButtons[0] = new JButton("Fizzo");
		drinkButtons[1] = new JButton("Diet Fizzo");
		drinkButtons[2] = new JButton("Dr. Cherry");
		drinkButtons[3] = new JButton("Tangerine Pop");
		ActionListener drinkListener = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (hasDrink || emptySlot) {
					return;
				}
				for (int i = 0; i < 4; i++) {
					if (ae.getSource() == drinkButtons[i]) {
						try {
							hotDogPanel.setHotDog(
								(HotDog) Class
								.forName("ingredients.drinks." + 
										drinkButtons[i]
										.getText()
										.replaceAll(" ", "")
										.replaceAll("\\.", ""))
								.getConstructor(HotDog.class)
								.newInstance(hotDogPanel.getHotDog()));
							hasDrink = true;
						} catch(Exception e) {
							System.out.println("Error: Couldn't load " + drinkButtons[i].getText() + " resource!");
							e.printStackTrace();
						}
						hotDogPanel.repaint();
					}
				}
			}
		};
		for (int i = 0; i < 4; i++) {
			drinkButtons[i].addActionListener(drinkListener);
			drinkButtons[i].setPreferredSize(new Dimension(150, 50));
			drinksPanel.add(drinkButtons[i]);
		}
		
		
		JPanel popPanel = new JPanel(new GridLayout(2, 2));
		JButton[] popButtons = new JButton[4];
		popButtons[0] = new JButton("Butter Popcorn");
		popButtons[1] = new JButton("Cheese Popcorn");
		popButtons[2] = new JButton("Spicy Popcorn");
		popButtons[3] = new JButton("Chocolate Popcorn");
		ActionListener snackListener = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (hasSnack || emptySlot) {
					return;
				}
				for (int i = 0; i < 4; i++) {
					if (ae.getSource() == popButtons[i]) {
						try {
							hotDogPanel.setHotDog(
									(HotDog) Class
									.forName("ingredients.snacks." + 
											popButtons[i]
											.getText()
											.replaceAll(" ", "")
											.replaceAll("corn", "Corn"))
									.getConstructor(HotDog.class)
									.newInstance(hotDogPanel.getHotDog()));
							hasSnack = true;
						} catch (Exception e) {
							System.out.println("Couldn't load " + popButtons[i].getText() + " resource!");
							e.printStackTrace();
						}
						hotDogPanel.repaint();
					}
				}
			}
		};
		for (int i = 0; i < 4; i++) {
			popButtons[i].addActionListener(snackListener);
			popButtons[i].setPreferredSize(new Dimension(150, 50));
			popPanel.add(popButtons[i]);
		}
		
		JPanel fillerPanel = new JPanel();
		fillerPanel.setBackground(bgColor);
		fillerPanel.setPreferredSize(new Dimension(75, 569));
		add(fillerPanel, BorderLayout.WEST);
		
		
		JPanel donePanel = new JPanel(new FlowLayout());
		donePanel.setBackground(bgColor);
		JButton doneButton = new JButton("Done");
		doneButton.setForeground(bgColor);
		doneButton.setFont(new Font("Arial", Font.BOLD, 30));
		doneButton.setPreferredSize(new Dimension(137, 46));
		donePanel.add(doneButton);
		

		JButton trashButton = new JButton("TRASH");
		trashButton.setBackground(Color.RED);
		trashButton.setForeground(Color.WHITE);
		trashButton.setFont(new Font("Arial", Font.BOLD, 30));
		
		ActionListener trashListener = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (!emptySlot) {
					hasSnack = false;
					hasDrink = false;
					emptySlot = true;
					PopPanel.this.remove(hotDogPanel);
					PopPanel.this.revalidate();
					PopPanel.this.repaint();
				}
			}
		};
		trashButton.addActionListener(trashListener);
		donePanel.add(trashButton);
		
		ActionListener doneListener = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (!emptySlot && waitPanel.hasNextOrder()) {
					waitPanel.getNextOrder().giveScore(hotDogPanel.getHotDog());
					hasSnack = false;
					hasDrink = false;
					emptySlot = true;
					PopPanel.this.remove(hotDogPanel);
					PopPanel.this.revalidate();
					PopPanel.this.repaint();
					
				}
			}
		};
		doneButton.addActionListener(doneListener);
		extrasPanel.add(popPanel);
		extrasPanel.add(drinksPanel);
		this.add(extrasPanel, BorderLayout.NORTH);
		this.add(donePanel, BorderLayout.SOUTH);
		
	}

	
	public void addHotDog(HotDog hotDog) {
		hotDogPanel = new HotDogPanel(hotDog, bgColor);
		emptySlot = false;
		this.add(hotDogPanel);
		hotDogPanel.repaint(500, 500, hotDogPanel.getX(), hotDogPanel.getY());
	}
	
	public boolean isEmpty() {
		return emptySlot;
	}
	
}

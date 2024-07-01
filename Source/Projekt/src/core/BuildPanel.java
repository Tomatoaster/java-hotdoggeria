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
import ingredients.sausages.Sausage;

public class BuildPanel extends JPanel {

	private HotDogPanel hotDogPanel;
	private JButton[] bun;
	private JButton done;
	private boolean hasSausage;
	private boolean hasBun;
	private JButton[] ingredients;
	private PopPanel popPanel;
	
	public BuildPanel(PopPanel popPanel) {
		this.popPanel = popPanel;
		
		this.setBackground(new Color(0, 200, 0));
		BorderLayout panelLayout = new BorderLayout();
		panelLayout.setHgap(10);
		this.setLayout(panelLayout);
		
		JPanel bunPanel = new JPanel();
		GridLayout bunLayout = new GridLayout(3, 1);
		bunLayout.setVgap(10);
		bunPanel.setLayout(bunLayout);
		bunPanel.setBackground(new Color(0, 200, 0));
		
		done = new JButton("Done");
		done.setForeground(new Color(60, 200, 60));
		done.setPreferredSize(new Dimension(137, 46));
		done.setFont(new Font("Arial", Font.BOLD, 30));
		
		bun = new JButton[3];
		bun[0] = new JButton("Regular Bun");
		bun[1] = new JButton("White Bun");
		bun[2] = new JButton("Brown Bun");
		
		hasBun = false;
		hasSausage = false;
		
		ActionListener bunListener = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				for (int i = 0; i < 3; i++) {
					if (ae.getSource() == bun[i] && !hasBun) {
						HotDog tmp;
						try {
							tmp = (HotDog) Class
									.forName("ingredients.buns." + bun[i].getText().replaceAll(" ", ""))
									.getConstructor()
									.newInstance();
							hotDogPanel = new HotDogPanel(tmp, new Color(0, 200, 0));
							BuildPanel.this.add(hotDogPanel);
							BuildPanel.this.revalidate();
							hasBun = true;
							
						} catch(Exception e) {
							System.out.println("Error: Can't load bun resource!");
						}
						
					}
				}
			}
		};
		
		for (int i = 0; i < 3; i++) {
			bun[i].addActionListener(bunListener);
			bunPanel.add(bun[i]);
		}
		JPanel donePanel = new JPanel(new FlowLayout());
		donePanel.setBackground(new Color(0, 200, 0));
		donePanel.add(done);
		
		FlowLayout ingredientLayout = new FlowLayout(); // new GridLayout(1, 3);
		ingredientLayout.setHgap(20);
		ingredientLayout.setAlignment(FlowLayout.RIGHT);
		JPanel ingredientPanel = new JPanel(ingredientLayout);
		ingredientPanel.setBackground(new Color(0, 200, 0));
		JPanel[] categories = new JPanel[3];
		for (int i = 0; i < 3; i++) {
			categories[i] = new JPanel(new GridLayout(2, 2));
		}
		
		ingredients = new JButton[12];
		ingredients[0] = new JButton("Ketchup");
		ingredients[1] = new JButton("Mustard");
		ingredients[2] = new JButton("Mayo");
		ingredients[3] = new JButton("Hot Sauce");
		ingredients[4] = new JButton("Cheese");
		ingredients[5] = new JButton("Corn");
		ingredients[6] = new JButton("Onion");
		ingredients[7] = new JButton("Salad");
		ingredients[8] = new JButton("Cucumber");
		ingredients[9] = new JButton("Tomato");
		ingredients[10] = new JButton("Bacon");
		ingredients[11] = new JButton("Jalapeno");
		
		ActionListener ingredientListener = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String[] pkg = new String[3];
				pkg[0] = "sauce.";
				pkg[1] = "garnish.";
				pkg[2] = "toppers.";
				
				for (int i = 0; i < 12; i++) {
					if (ae.getSource() == ingredients[i] && hasFullBun()) {
						try {
							hotDogPanel.setHotDog((HotDog) Class
									.forName("ingredients." + pkg[i/4] + ingredients[i].getText().replaceAll(" ", ""))
								 	.getConstructor(HotDog.class)
									.newInstance(hotDogPanel.getHotDog()));
							hotDogPanel.repaint();
							
						} catch(Exception e) {
							System.out.println("Error: Can't load " + ingredients[i].getText() + " resource!");
							e.printStackTrace();
						}
						
					}
				}
			}
		};
		
		for (int i = 0; i < 12; i++) {
			ingredients[i].setPreferredSize(new Dimension(100, 50));
			ingredients[i].addActionListener(ingredientListener);
			categories[i/4].add(ingredients[i]);
		}
		for (int i = 0; i < 3; i++) {
			ingredientPanel.add(categories[i]);
		}
		
		ActionListener doneListener = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				
				if (hasBun && hasSausage && popPanel.isEmpty()) {
					popPanel.addHotDog(hotDogPanel.getHotDog());
					hasBun = false;
					hasSausage = false;
					BuildPanel.this.remove(hotDogPanel);
					BuildPanel.this.repaint();
				}
			}
		};
		
		done.addActionListener(doneListener);
		
		add(donePanel, BorderLayout.SOUTH);
		add(bunPanel, BorderLayout.WEST);
		add(ingredientPanel, BorderLayout.NORTH);

	}
	
	public void addSausage(Sausage sausage) {
		hotDogPanel.setHotDog(sausage.toHotDog(hotDogPanel.getHotDog()));
		hasSausage = true;
		// System.out.println(hotDogPanel.getHotDog().getGrilledLevel());
	}
	
	public boolean hasEmptyBun() {
		return (!hasSausage && hasBun);
	}
	
	public boolean hasFullBun() {
		return (hasSausage && hasBun);
	}
	
}

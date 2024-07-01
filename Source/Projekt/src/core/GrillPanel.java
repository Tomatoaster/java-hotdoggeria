package core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JPanel;

import ingredients.sausages.Sausage;
import ingredients.sausages.SausageControl;
import ingredients.sausages.SausageModel;
import ingredients.sausages.SausageView;

public class GrillPanel extends JPanel {
	
	private SausageModel[] model;
	private SausageView[] view;
	private SausageControl[] control;
	private Thread[] thr;
	private JPanel hotDogPanel;
	private BuildPanel buildPanel;
	
	private JPanel[] grills;
	private JButton[] take;
	private boolean[] free;
	
	public GrillPanel(BuildPanel buildPanel) {
		this.buildPanel = buildPanel;
		
		this.setBackground(new Color(255, 102, 0));
		BorderLayout panelLayout = new BorderLayout();
		panelLayout.setHgap(10);
		this.setLayout(panelLayout);
		
		// A panel, ahol a hotdogok keszulnek
		hotDogPanel = new JPanel();
		GridLayout hotDogLayout = new GridLayout(3, 3);
		hotDogLayout.setHgap(10);
		hotDogLayout.setVgap(10);
		hotDogPanel.setLayout(hotDogLayout);
		this.add(hotDogPanel);
		hotDogPanel.setBackground(this.getBackground());

		// Tombok helyenek lefoglalasa
		thr = new Thread[9];
		model = new SausageModel[9];
		view = new SausageView[9];
		control = new SausageControl[9];
		free = new boolean[9];
		take = new JButton[9];
		grills = new JPanel[9];
		
		// Gombok es placeholder panelek
		for (int i = 0; i < 9; i++) {
			grills[i] = new JPanel();
			grills[i].setPreferredSize(new Dimension(150, 80));
			grills[i].setBackground(new Color(180, 60, 0));
			grills[i].setLayout(new BorderLayout());
			free[i] = true;
			take[i] = new JButton("Take");
			grills[i].add(take[i], BorderLayout.SOUTH);
			hotDogPanel.add(grills[i]);
		}
		
		// Gombok, amelyek hozzaadjak a virsliket
		JPanel buttonPanel = new JPanel();
		JButton hotDogButton = new JButton("Hot Dog Sausage");
		JButton italianButton = new JButton("Italian Sausage");
		JButton cheeseButton = new JButton("Cheesewurst");
		
		// Leellenorzi, hogy van szabad hely, es letrehozza a megfelelo virslit
		ActionListener addListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sausage sausage = null;
				try {
					sausage = (Sausage) Class
										.forName("ingredients.sausages." + ((JButton) e.getSource()).getText().replaceAll(" ", ""))
										.getConstructor()
										.newInstance();
				} catch (Exception e1) {
					System.out.println("Error: Can't load sausage resource!");
				}
				
				
				// Megkeresi az elso szabad helyet
				int freeGrill = -1;
				for (int i = 0; i < 9; i++) {
					if (free[i]) {
						freeGrill = i;
						break;
					}
				}
				// Ha talalt
				if (freeGrill != -1) {
					// Beallitja, hogy a hely mostantol foglalt
					// Es letrehozza az uj hotdogot
					free[freeGrill] = false;
					model[freeGrill] = new SausageModel(sausage);
					view[freeGrill] = new SausageView(model[freeGrill]);
					control[freeGrill] = new SausageControl(model[freeGrill], view[freeGrill]);
					thr[freeGrill] = new Thread(control[freeGrill]);
					thr[freeGrill].start();
					grills[freeGrill].add(view[freeGrill]);
					grills[freeGrill].revalidate();
				}
				
			}
		};
		hotDogButton.addActionListener(addListener);
		italianButton.addActionListener(addListener);
		cheeseButton.addActionListener(addListener);
		
		GridLayout buttonLayout = new GridLayout(3, 1);
		buttonLayout.setVgap(10);
		
		buttonPanel.setLayout(buttonLayout);
		buttonPanel.setBackground(new Color(255, 102, 0));
		buttonPanel.add(hotDogButton);
		buttonPanel.add(cheeseButton);
		buttonPanel.add(italianButton);
		this.add(buttonPanel, BorderLayout.WEST);
		
		// Gombok, melyekkel le lehet venni a virsliket
		ActionListener takeListener = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (!buildPanel.hasEmptyBun()) {
					return;
				}
				
				int sourceInd = -1;
				for (int i = 0; i < 9; i++) {
					if (ae.getSource() == take[i]) {
						sourceInd = i;
						break;
					}
				}
				if (sourceInd != -1 && !free[sourceInd]) {
					thr[sourceInd].interrupt();
					buildPanel.addSausage(model[sourceInd].getSausage());
					
					control[sourceInd].take();

					grills[sourceInd].remove(view[sourceInd]);
					grills[sourceInd].revalidate();
					grills[sourceInd].repaint();
					
					
					
					free[sourceInd] = true;
					view[sourceInd] = null;
					model[sourceInd] = null;
					control[sourceInd] = null;
					thr[sourceInd] = null;
				}
				
			}
		};
		
		for (int i = 0; i < 9; i++) {
			take[i].addActionListener(takeListener);
		}
		
	}
}

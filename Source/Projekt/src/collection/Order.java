package collection;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ingredients.HotDog;

public class Order extends JPanel {
	private String[] expectedIngr;
	private Random rng;
	private String[] ingr;
	private Thread thr;
	private int timer;
	private int finalScore;
	
	public Order() {
		initializeStrIng();
		this.setLayout(new GridLayout(10, 1));
		this.setPreferredSize(new Dimension(200, 200));
		rng = new Random();
		expectedIngr = new String[9];
		
		int tmp = rng.nextInt(0, 3);
		JLabel bunLabel = new JLabel(ingr[tmp]);
		bunLabel.setHorizontalAlignment(JLabel.CENTER);
		expectedIngr[0] = ingr[tmp];
		
		
		tmp = rng.nextInt(3, 6);
		JLabel sausageLabel = new JLabel(ingr[tmp]);
		sausageLabel.setHorizontalAlignment(JLabel.CENTER);
		expectedIngr[1] = ingr[tmp];

		
		tmp = rng.nextInt(6, 10);
		JLabel sauceLabel1 = new JLabel(ingr[tmp]);
		sauceLabel1.setHorizontalAlignment(JLabel.CENTER);
		expectedIngr[2] = ingr[tmp];

		
		do {
			tmp = rng.nextInt(6, 10);
		} while(ingr[tmp].equals(sauceLabel1.getText()));
		JLabel sauceLabel2 = new JLabel(ingr[tmp]);
		sauceLabel2.setHorizontalAlignment(JLabel.CENTER);
		expectedIngr[3] = ingr[tmp];

		
		tmp = rng.nextInt(10, 14);
		JLabel garnishLabel1 = new JLabel(ingr[tmp]);
		garnishLabel1.setHorizontalAlignment(JLabel.CENTER);
		expectedIngr[4] = ingr[tmp];

		
		do {
			tmp = rng.nextInt(10, 14);
		} while (ingr[tmp].equals(garnishLabel1.getText()));
		JLabel garnishLabel2 = new JLabel(ingr[tmp]);
		garnishLabel2.setHorizontalAlignment(JLabel.CENTER);
		expectedIngr[5] = ingr[tmp];
		
		tmp = rng.nextInt(14, 18);
		JLabel topperLabel = new JLabel(ingr[tmp]);
		topperLabel.setHorizontalAlignment(JLabel.CENTER);
		expectedIngr[6] = ingr[tmp];

		
		tmp = rng.nextInt(18, 22);
		JLabel snackLabel = new JLabel(ingr[tmp]);
		snackLabel.setHorizontalAlignment(JLabel.CENTER);
		expectedIngr[7] = ingr[tmp];

		
		tmp = rng.nextInt(22, 26);
		JLabel drinkLabel = new JLabel(ingr[tmp]);
		drinkLabel.setHorizontalAlignment(JLabel.CENTER);
		expectedIngr[8] = ingr[tmp];

		add(bunLabel);
		add(sausageLabel);
		add(sauceLabel1);
		add(sauceLabel2);
		add(garnishLabel1);
		add(garnishLabel2);
		add(topperLabel);
		add(snackLabel);
		add(drinkLabel);
		
		thr = new Thread() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						return;
					}
					timer++;
				}
			}
		};
		timer = 0;
		thr.start();
	}
	
	public void initializeStrIng() {
		ingr = new String[26];
		
		ingr[0] = "Regular Bun";
		ingr[1] = "Brown Bun";
		ingr[2] = "White Bun";
		
		ingr[3] = "Hot Dog Sausage";
		ingr[4] = "Cheesewurst";
		ingr[5] = "Italian Sausage";
		
		ingr[6] = "Ketchup";
		ingr[7] = "Mayo";
		ingr[8] = "Mustard";
		ingr[9] = "Hot Sauce";
		
		ingr[10] = "Cheese";
		ingr[11] = "Corn";
		ingr[12] = "Onion";
		ingr[13] = "Salad";
		
		ingr[14] = "Bacon";
		ingr[15] = "Cucumber";
		ingr[16] = "Jalapeno";
		ingr[17] = "Tomato";
		
		ingr[18] = "Butter Pop Corn";
		ingr[19] = "Cheese Pop Corn";
		ingr[20] = "Chocolate Pop Corn";
		ingr[21] = "Spicy Pop Corn";
		
		ingr[22] = "Fizzo";
		ingr[23] = "Diet Fizzo";
		ingr[24] = "Dr. Cherry";
		ingr[25] = "Tangerine Pop";
	}

	public int getFinalScore() {
		return finalScore;
	}
	
	public void giveScore(HotDog hotDog) {
		String[] actIngr = new String[hotDog.getIngredientNr()];
		hotDog.getIngredients(actIngr, actIngr.length - 1);
		
		
		// Minden osszetevobol megkapjuk a class neveket
		String compactIngr[] = (String[]) Arrays
		.stream(expectedIngr)
		.map(s -> s.replaceAll(" ", "").replaceAll("\\.", ""))
		.toArray(String[]::new);
		
		// Ami benne van es benne kell legyen - ami benne van es nem kell benne legyen
		int correctIngrNr = Math.max(0, (int) Arrays
				.stream(actIngr)
				.filter(i -> 
						Arrays
						.stream(compactIngr)
						.toList()
						.contains(i))
				.count());
		
		int ingrScore = Math.max(0, correctIngrNr - (actIngr.length - correctIngrNr));
		ingrScore = 100 - (9 - ingrScore) * 11;
		
		int grillScore = Math.max(0, 100 - 3 * Math.abs(hotDog.getGrilledLevel() - hotDog.getGoalLevel()));
		
		int waitScore = Math.max(0, Math.min(100, 100 - timer + 60));
		
		finalScore =  (3 * ingrScore + waitScore + 2 * grillScore) / 6;
		
		JLabel scoreLabel = new JLabel(finalScore + "%");
		scoreLabel.setForeground(new Color((100 - finalScore) * 2, finalScore * 2, 0));
		scoreLabel.setHorizontalAlignment(JLabel.CENTER);
		thr.interrupt();
		add(scoreLabel);
		
	}
}

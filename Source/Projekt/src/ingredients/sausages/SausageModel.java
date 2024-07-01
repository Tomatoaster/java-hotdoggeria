package ingredients.sausages;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class SausageModel {
	
	private Sausage sausage;
	
	public SausageModel(Sausage sausage) {
		this.sausage = sausage;
	}
	
	public Sausage getSausage() {
		return sausage;
	}
	
	public void setSausage(Sausage sausage) {
		this.sausage = sausage;
	}
	
	
	
	public void kirajzol(Graphics g) {
		if (sausage == null) {
			return;
		}
		
		//g.drawImage(img, 20, 80, 150, 80, null);
		sausage.serve(g);
		
		int wOffset = 41;
		int hOffset = 80;
		
		g.setFont(new Font("Arial", Font.BOLD, 15));
		if (sausage.getGrilledLevel() < sausage.getGoalLevel() - 3) {
			g.setColor(Color.RED);
			g.drawString("Undercooked: " + sausage.getGrilledLevel(), wOffset, hOffset);
		}
		else if (sausage.getGrilledLevel() == sausage.getGoalLevel()) {
			g.setColor(Color.GREEN);
			g.drawString("Perfectly Cooked: " + sausage.getGrilledLevel(), wOffset, hOffset);
		}
		else if (sausage.getGrilledLevel() >= sausage.getGoalLevel() - 3 && sausage.getGrilledLevel() <= sausage.getGoalLevel() + 3) {
			g.setColor(Color.YELLOW);
			g.drawString("Well Cooked: " + sausage.getGrilledLevel(), wOffset, hOffset);
		}
		else {
			g.setColor(Color.RED);
			g.drawString("Overcooked: " + sausage.getGrilledLevel(), wOffset, hOffset);
		}
		g.setColor(Color.GREEN);
		g.drawString("Goal: " + sausage.getGoalLevel(), 65, 170);
	}
}

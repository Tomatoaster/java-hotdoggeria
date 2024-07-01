package ingredients.sausages;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import ingredients.HotDog;
import ingredients.HotDogIngredient;

public abstract class Sausage extends HotDogIngredient {

	private BufferedImage img;
	private int grilledLevel;
	private int goalLevel;
	
	public Sausage(HotDog hotDog) {
		super(hotDog);
	}
	
	@Override
	public void serve(Graphics g) {
		super.serve(g);
		g.drawImage(img, 20, 80, 150, 80, null);
	}

	public HotDog toHotDog(HotDog hotDog) {
		return null;
	}
	
	public int getGrilledLevel() {
		return grilledLevel;
	}

	public void setGrilledLevel(int grilledLevel) {
		this.grilledLevel = grilledLevel;
	}

	public int getGoalLevel() {
		return goalLevel;
	}

	public void setGoalLevel(int goalLevel) {
		this.goalLevel = goalLevel;
	}
	
}

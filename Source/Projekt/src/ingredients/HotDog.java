package ingredients;

import java.awt.Graphics;

public interface HotDog {
	public void serve(Graphics g);
	public int getGrilledLevel();
	public int getGoalLevel();
	public int getIngredientNr();
	public void getIngredients(String[] ingr, int ind);
}

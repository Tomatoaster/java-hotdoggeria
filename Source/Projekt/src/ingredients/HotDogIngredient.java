package ingredients;

import java.awt.Graphics;

public abstract class HotDogIngredient implements HotDog {
	private HotDog hotDog;
	
	public HotDogIngredient(HotDog hotDog) {
		this.hotDog = hotDog;
	}
	
	@Override
	public void serve(Graphics g) {
		if (hotDog != null) {
			hotDog.serve(g);
		}
	}
	
	@Override
	public int getGrilledLevel() {
		return hotDog.getGrilledLevel();
	}
	
	@Override
	public int getGoalLevel() {
		return hotDog.getGoalLevel();
	}
	
	@Override
	public int getIngredientNr() {
		return 1 + hotDog.getIngredientNr();
	}
	
	@Override
	public void getIngredients(String[] ingr, int ind) {
		
		ingr[ind] = this.getClass().getName().split("\\.")[this.getClass().getName().split("\\.").length - 1];
		hotDog.getIngredients(ingr, ind - 1);
	}
}
